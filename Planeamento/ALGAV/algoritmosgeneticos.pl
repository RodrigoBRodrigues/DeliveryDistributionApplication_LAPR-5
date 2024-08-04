:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.
:-dynamic custo_min/3.
:-dynamic camiao/1.
:-dynamic melhor_solucao/1.
:-dynamic valor_minimo/1.
:-use_module(library(random)).
:-consult('predicados_SPRINTC.pl').
:- use_module(library(apply)).
:- use_module(library(solution_sequences)).

avaliaTempoSeq(Seq,Tempo):- retractall(custo_min(_, _ ,_)), assertz(custo_min(_, _,100000)), retractall(data(_)), assertz(data(Data))
, melhorEntregaFindAllz([Seq]), custo_min(A,B,Tempo).

% parameterizacão
inicializa(NG,DP,P1,P2):-
	write('Numero de novas Geracoes: '), write(NG), nl, (retract(geracoes(_));true),  asserta(geracoes(NG)),
	write('Dimensao da Populacao: '),    write(DP), nl, (retract(populacao(_));true), DD is (DP - 2) , asserta(populacao(DD)),
	write('Probabilidade de Cruzamento (%):'), write(P1),nl, PC is P1/100,
	(retract(prob_cruzamento(_));true), 	asserta(prob_cruzamento(PC)),
	write('Probabilidade de Mutacao (%):'), write(P2),nl, PM is P2/100, 
	(retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)).


% algoritmoGenetico(20221205,eTruck01,5,5,50,25,L). 
algoritmoGenetico(Dia,Camiao,NumeroGeracoes,DimensaoPopulacao,ProbCruzamento,ProbMutacao, MelhorSolucao):-
				get_time(Ti),
				(retract(camiao(_));true), asserta(camiao(Camiao)),
				(retract(melhor_solucao(_));true), asserta(melhor_solucao(Melhor)),
				gera(Dia,Camiao,NumeroGeracoes,DimensaoPopulacao,ProbCruzamento,ProbMutacao),
				melhor_solucao(MelhorSolucao), write(MelhorSolucao),nl,
				get_time(Tf),TSol is Tf-Ti,write(TSol),nl.

 
gera(Dia,Camiao,NG,DP,P1,P2):-
	inicializa(NG,DP,P1,P2),
	gera_populacao(Dia,Populacao),
	maiorMassa(Dia,Camiao,L1),  delete(L1,5,ListaSemMatosinhos1),
	maisProximo(Dia,Camiao,L2), delete(L2,5,ListaSemMatosinhos2),
	insereInicio(ListaSemMatosinhos1,1,Populacao,Pop1), insereInicio(ListaSemMatosinhos2,1,Pop1,Pop2),
	write('Pop='),write(Pop2),nl,
	avalia_populacao(Pop2,PopAv),
	write('PopAv='),write(PopAv),nl,
	ordena_populacao(PopAv,PopOrd),
	geracoes(NG), gera_geracao(0,NG,PopOrd).

gera_populacao(Dia,Populacao):-
	populacao(TamPop), %Obtém a Dimensão da População
	armazensComEntregas(Dia,ListaTarefas), write('Lista de Armazens com Entregas Inicial: '), write(ListaTarefas),nl,
	length(ListaTarefas,NumT), VM is NumT * 180, (retract(valor_minimo(_));true),  asserta(valor_minimo(VM)),
	gera_populacao(TamPop,ListaTarefas,NumT,Populacao).

gera_populacao(0,_,_,[]):-!.

gera_populacao(TamPop,ListaTarefas,NumT,[Ind|Resto]):-
	TamPop1 is TamPop-1,
	gera_populacao(TamPop1,ListaTarefas,NumT,Resto),
	gera_individuo(ListaTarefas,NumT,Ind),
	not(member(Ind,Resto)).
gera_populacao(TamPop,ListaTarefas,NumT,L):-
	gera_populacao(TamPop,ListaTarefas,NumT,L).

gera_individuo([G],1,[G]):-!.

gera_individuo(ListaTarefas,NumT,[G|Resto]):-
	NumTemp is NumT + 1, % To use with random
	random(1,NumTemp,N),
	retira(N,ListaTarefas,G,NovaLista),
	NumT1 is NumT-1,
	gera_individuo(NovaLista,NumT1,Resto).

retira(1,[G|Resto],G,Resto).
retira(N,[G1|Resto],G,[G1|Resto1]):-
	N1 is N-1,
	retira(N1,Resto,G,Resto1).

avalia_populacao([],[]).
avalia_populacao([Ind|Resto],[Ind*V|Resto1]):-
	avaliaTempoSeq(Ind,V1), V is round(V1),
	avalia_populacao(Resto,Resto1).

ordena_populacao(PopAv,PopAvOrd):-
	bsort(PopAv,PopAvOrd).

bsort([X],[X]):-!.
bsort([X|Xs],Ys):-
	bsort(Xs,Zs),
	btroca([X|Zs],Ys).

btroca([X],[X]):-!.

btroca([X*VX,Y*VY|L1],[Y*VY|L2]):-
	VX>VY,!,
	btroca([X*VX|L1],L2).

btroca([X|L1],[X|L2]):-btroca(L1,L2).

 gera_geracao(G,G,Pop):-!, write('Geracao '), write(G), write(':'), nl, write(Pop), nl,
	first_elements_of_list(1,Pop,[Melhor*V]), valor_minimo(VM), V < VM, !,
	append([5],Melhor,Melhor1),inverte(Melhor1,Melhor2),
	append([5],Melhor2,Melhor3),inverte(Melhor3,Melhor4),
	(retract(melhor_solucao(_));true),  asserta(melhor_solucao(Melhor4*V)).

gera_geracao(N,G,Pop):-
	write('Geracao '), write(N), write(':'), nl, write(Pop), nl,
	melhorIndividuoDaGeracao(Pop,MelhorIndividuo),
	cruzamento(Pop,NPop1),
	mutacao(NPop1,NPop),
	metodoSelecao(Pop,NPop1,NPop,Melhores),
	N1 is N+1,
	gera_geracao(N1,G,Melhores).

metodoSelecao(AtualAv,Cruzados,Mutados,Melhores):- 
			  avalia_populacao(Cruzados,CruzadosAv),
			  avalia_populacao(Mutados,MutadosAv), 
			  append(AtualAv,CruzadosAv,L1), append(L1,MutadosAv,L2), 
			  remove_duplicates(L2,L3), ordena_populacao(L3,Todos),
			  populacao(N), first_elements_of_list(N,Todos,Melhores1),
			  metodoSelecaoNaoElitista(Todos,Melhores1,Selecionados),
			  append(Melhores1, Selecionados, Melhores2), ordena_populacao(Melhores2,Melhores).

metodoSelecaoNaoElitista(Todos,Melhores,Selecionados):- 
		remove_list(Todos,Melhores,SemOsMelhores), 
		random_member(X1, SemOsMelhores), 
		delete(SemOsMelhores, X1, SemOsMelhores1),
		random_member(X2, SemOsMelhores1),
		append([X1],[X2],Selecionados).

cruzamento([],[]).
cruzamento([Ind*_],[Ind2]):- random_permutation(Ind,Ind2).
cruzamento([Ind1*V1,Ind2*V2|Resto],[NInd1,NInd2|Resto1]):-
	prob_cruzamento(Pcruz),random(0.0,1.0,Pc),
	((Pc =< Pcruz,!,
    	random_permutation(Ind1,NInd1),
		random_permutation(Ind2,NInd2));
	(NInd1=Ind1,NInd2=Ind2)),
	cruzamento(Resto,Resto1).

mutacao([],[]).
mutacao([Ind|Rest],[NInd|Rest1]):-
	prob_mutacao(Pmut),
	random(0.0,1.0,Pm),
	((Pm < Pmut,!,mutacao1(Ind,NInd));NInd = Ind),
	mutacao(Rest,Rest1).

mutacao1(Ind,NInd):- random_permutation(Ind,NInd).

melhorIndividuoDaGeracao([H|T],H):- write('Melhor Individuo da Geracao : '), write(H), nl.

insereInicio(_,_,[],[]).
insereInicio(N,P,[Y|L],[N|[Y|M]]):- P==1, P1 is P-1,insereInicio(N,P1,L,M).
insereInicio(N,P,[Y|L],[Y|M]):-P1 is P-1,insereInicio(N,P1,L,M).

list_butlast(List, ListWithoutLast):-
  (append(ListWithoutLast, [_], List)->true).

remove_duplicates([], []).

remove_duplicates([Head | Tail], Result) :-
    member(Head, Tail), !,
    remove_duplicates(Tail, Result).

remove_duplicates([Head | Tail], [Head | Result]) :-
    remove_duplicates(Tail, Result).

first_elements_of_list(IntElems, LongLst, ShortLst) :-
    LongLst = [H|T],
    (  nonvar(IntElems) -> Once = true ;
		is_list(ShortLst) -> Once = true ; 
		Once = false ),
    first_elements_of_list_(T, H, 1, IntElems, ShortLst),
    (Once = true -> ! ; true).

first_elements_of_list_([], H, I, I, [H]).
first_elements_of_list_([_|_], H, I, I, [H]).
first_elements_of_list_([H|LongLst], PrevH, Upto, IntElems, [PrevH|ShortLst]) :-
    Upto1 is Upto + 1,
    first_elements_of_list_(LongLst, H, Upto1, IntElems, ShortLst).

remove_list([], _, []).
remove_list([X|Tail], L2, Result):- member(X, L2), !, remove_list(Tail, L2, Result). 
remove_list([X|Tail], L2, [X|Result]):- remove_list(Tail, L2, Result).

inverte(L,LI):-inverte1(L,[ ],LI).
inverte1([ ],L,L).
inverte1([X|L],L2,L3):- 
inverte1(L,[X|L2],L3).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

maplist7(_,[],[],[],[],[],[]).
maplist7(P,[A|As],[B|Bs],[C|Cs],[D|Ds],[E|Es],[F|Fs]) :- call(P,A,B,C,D,E,F),maplist7(P,As,Bs,Cs,Ds,Es,Fs).

listaEntregasDoDia(Data, Lista):- findall(A,(entrega(A,Data,_,_,_,_)),Lista). 
                        
nextArmazem(Data,Lista):- listaEntregasDoDia(Data,ListaEntregas), maplist7(entrega, ListaEntregas,_,Massa,Armazem,_,_), 
                pairs_keys_values(Pares, Massa, Armazem), sort(1, @>=, Pares, OrdemDecrescenteMassa), 
                pairs_values(OrdemDecrescenteMassa, ListaNextArmazens), Lista = ListaNextArmazens.

totalMassaEntregasCamiao([],Data,0).
totalMassaEntregasCamiao([H|T],Data,R):- totalMassaEntregasCamiao(T,Data,R1), entrega(_, Data, R2, H, _, _), R is R1 + R2.

distribuir(16,[H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16],[[H1,H16,H6,H11,H10,H9],[H2,H15,H5,H12,H8],[H3,H14,H4,H13,H7]]).

ordenar(Data,Lista1,Truck01,ListaFinal):- 
		armazensComEntregas(Data,ListaArmazens),
		remove_list(ListaArmazens,Truck01,ArmazensARetirar),
		remove_list(Lista1,ArmazensARetirar,ListaFinal).

% distribuir_entregas(20230109,eTruck01,Rota_eTruck01,Rota_eTruck02,Rota_eTruck03).

distribuir_entregas(Data,eTruck01,ListaTruck01,ListaTruck02,ListaTruck03):- 
	nextArmazem(Data,Lista), distribuir(16,Lista,[Truck01,Truck02,Truck03]),
	write('---------------------------------------'),nl,
	write("Truck01 - "), write(Truck01), nl,
	write('Total Massa das Entregas = '), totalMassaEntregasCamiao(Truck01,Data,Massa1), write(Massa1), nl,
	write('Capacidade do Camiao = '), carateristicasCam(eTruck01,_,Capacidade1,_,_,_), write(Capacidade1), nl,
	write('---------------------------------------'),nl,
	write("Truck02 - "), write(Truck02), nl,
	write('Total Massa das Entregas = '), totalMassaEntregasCamiao(Truck02,Data,Massa2), write(Massa2), nl,
	write('Capacidade do Camiao = '), carateristicasCam(eTruck02,_,Capacidade2,_,_,_), write(Capacidade2), nl,
	write('---------------------------------------'),nl,
	write("Truck03 - "), write(Truck03), nl,
	write('Total Massa das Entregas = '), totalMassaEntregasCamiao(Truck03,Data,Massa3), write(Massa3), nl,
	write('Capacidade do Camiao = '), carateristicasCam(eTruck03,_,Capacidade3,_,_,_), write(Capacidade3), nl,
	write('---------------------------------------'),nl,
	algoritmoGenetico(Data,eTruck01,5,5,50,25,Lista1*_),nl,
	write('---------------------------------------'),nl,
	ordenar(Data,Lista1,Truck01,ListaTruck01),
	ordenar(Data,Lista1,Truck02,ListaTruck02),
	ordenar(Data,Lista1,Truck03,ListaTruck03).
