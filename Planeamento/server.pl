% Bibliotecas 
:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_ssl_plugin)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_parameters)).
:- use_module(library(http/http_open)).
:- use_module(library(http/http_cors)).
:- use_module(library(date)).
:- use_module(library(random)).
:- use_module(library(apply)).
:- use_module(library(solution_sequences)).
% Bibliotecas JSON
:- use_module(library(http/json_convert)).
:- use_module(library(http/http_json)).
:- use_module(library(http/json)).
:- dynamic entrega/6.
:- dynamic idArmazem/2.
:- json_object entrega_json(id:float,data:string,massa:integer,armazem:string,tempoColocar:float,tempoRetirar:float)+ [type=entrega] ,
	entregas(entregas:list(entrega)).
    %Cors
:- set_setting(http:cors, [*]).


entregasDataUrl("http://localhost:5000/api/Entregas/date/?date=").
entregasUrl("http://localhost:5000/api/Entregas").


processarEntregas(Data):-
    setup_call_cleanup(
        http_open("https://localhost:5001/api/Entregas", In, [cert_verify_hook(cert_accept_any),request_header('Accept'='*/*')]),
        json_read_dict(In, Data),
        close(In)
    ),
	assertEntregas(Data).

assertEntregas([]).
assertEntregas([H|T]):-
sub_atom(H.get(data), 0, 4, After, SubAtom),
sub_atom(H.get(data), 5, 2, After2, SubAtom2), %mes
sub_atom(H.get(data), 8, 2, After3, SubAtom3), %dia
string_concat(SubAtom,SubAtom2,X),
string_concat(X,SubAtom3,Y),
atom_number(Y,Z),
asserta(entrega(H.get(id),Z,H.get(massa),H.get(armazemDesignacao),H.get(tempoColocar),H.get(tempoRetirar))),
assertEntregas(T).

processarArmazens(Data):-
    setup_call_cleanup(
        http_open("https://localhost:5001/api/Armazens", In, [cert_verify_hook(cert_accept_any),request_header('Accept'='/')]),
        json_read_dict(In, Data),
        close(In)
    ),
    assertArmazens(Data).


assertArmazens([]).
assertArmazens([H|T]):-
asserta(idArmazem(H.get(designacao),H.get(id))),
assertArmazens(T).

% Gerir servidor
startServer(Port) :-
    servidor(Port),
    asserta(port(Port)), processarEntregas(X), processarArmazens(_).
	
servidor(Port) :-
        http_server(http_dispatch,
                    [ port(Port),
                      ssl([ certificate_file("cert.pem"),
                            key_file("key.pem"),
                            password(""),
							 cert_verify_hook(cert_accept_any)
                          ])
                    
                    ]).

stopServer:-
    retract(port(Port)),
    http_stop_server(Port,_).

% Funções do Servidor
:- set_setting(http:cors, [*]).


:- http_handler('/prolog/planeamento', getBestPath, []).

getBestPath(Request):-
http_parameters(Request,
                    [ dia(Dia,[]),
                      camiao(Camiao,[])
                    ]),

metodo_melhor(X,LCPerm,LCcarregamentos,Custo),
prolog_to_json(LCPerm,JSONObject),
reply_json(JSONObject, [json_object(dict)]).

metodo_melhor(Dia,LCPerm,LCcarregamentos,Custo):-
melhorEntregaFindAll(Dia),
custo_min(LCPerm,LCcarregamentos,Custo). 


:- http_handler('/prolog/planeamento/heuristica1', armazemMaisProximoPath, []).

armazemMaisProximoPath(Request):-
http_parameters(Request,
                    [ dia(Dia,[integer]),
                      camiao(Camiao,[])
                    ]),
maisProximo(Dia,Camiao,LCaminho1),converterArmazem(LCaminho1,LCaminho2),
prolog_to_json(LCaminho2,JSONObject),
reply_json(JSONObject,[json_object(dict)]).

:- http_handler('/prolog/planeamento/heuristica2', maiorMassaPath, []).

maiorMassaPath(Request):-
http_parameters(Request,
                    [ dia(Dia,[integer]),
                      camiao(Camiao,[])
                    ]),
maiorMassa(Dia,Camiao,LCaminho1),converterArmazem(LCaminho1,LCaminho2),
prolog_to_json(LCaminho2,JSONObject),
reply_json(JSONObject,[json_object(dict)]).

:- http_handler('/prolog/planeamento/heuristica3', tempoCombinadoPath, []).

tempoCombinadoPath(Request):-
http_parameters(Request,
                    [ dia(Dia,[integer]),
                      camiao(Camiao,[])
                    ]),
combinadoTempoMassa(Dia,Camiao,LCaminho1),converterArmazem(LCaminho1,LCaminho2),
prolog_to_json(LCaminho2,JSONObject),
reply_json(JSONObject,[json_object(dict)]).

:- http_handler('/prolog/planeamento/algoritmogenetico', algoritmogenetico, []).

algoritmogenetico(Request):-
http_parameters(Request,
                    [ dia(Dia,[integer]),
                      camiao(Camiao,[])
                    ]),
algoritmoGenetico(Dia,Camiao,5,5,50,25,	LCaminho1),converterArmazem(LCaminho1,LCaminho2),
prolog_to_json(LCaminho2,JSONObject),
reply_json(JSONObject,[json_object(dict)]).

:- http_handler('/prolog/planeamento/algoritmogeneticofrota', algoritmogeneticofrota, []).

algoritmogeneticofrota(Request):-
http_parameters(Request,
                    [ dia(Dia,[integer]),
                      camiao(Camiao,[])
                    ]),
distribuir_entregas(Dia,eTruck01,Rota_eTruck01,Rota_eTruck02,Rota_eTruck03),
converterArmazem(Rota_eTruck01,LCaminho1), converterArmazem(Rota_eTruck02,LCaminho2), converterArmazem(Rota_eTruck03,LCaminho3),
append(LCaminho1,LCaminho2,Rota1), append(Rota1,LCaminho3,Rota2),
prolog_to_json(Rota2,JSONObject),
reply_json(JSONObject,[json_object(dict)]).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- dynamic custo_min/3.
:- dynamic data/1.
:- dynamic camiao/1.

permutacao(L,R):- permutation(L,L1), tempoListaEntregas(L1,R).

soma_pesos([],[],0).
soma_pesos(["A05"|LC],[PesoAc|LP],PesoAc):-
soma_pesos(LC, LP, PesoAc).
soma_pesos([Cidade|LC],[PesoAc|LP],PesoAc):-
data(Data),
soma_pesos(LC,LP,PesoAc1),entrega(_, Data, Peso, Cidade, _, _), PesoAc is Peso+PesoAc1.

acrescenta_tara(Tara,[],[Tara]).
acrescenta_tara(Tara,[Peso|LP],[PesoTara|LPT]):-
acrescenta_tara(Tara,LP,LPT),
PesoTara is Peso+Tara.

calcula_custo(LC, Custo, LCcarregamentos):-
idArmazem("Matosinhos", "A05"),
camiao(Camiao),
carateristicasCam(Camiao, _, _, Carga_Total_Baterias, _, _),
custo(LC, Carga_Total_Baterias, Custo, LCcarregamentos).

custo([_], _, 0, []).
custo([C1,C2|LC], Carga_Atual_Baterias, Custo, LCcarregamentos):-
camiao(Camiao),
dadosCam_t_e_ta(Camiao,C1,C2,Tempo, Energia, TempoAdicional),
carateristicasCam(Camiao, Tara, Capacidade_Carga, Carga_Total_Baterias, _, _),
soma_pesos([C1,C2|LC], LPesos, _),
acrescenta_tara(Tara, LPesos, [PesoComCarga|_]),
calcular_carga_baterias(Energia, PesoComCarga, Tara, Capacidade_Carga, CargaNecessariaProximaViagem),

((Carga_Atual_Baterias < CargaNecessariaProximaViagem, !, (camiao(Camiao),carateristicasCam(Camiao, _, _, _, _, TCarga)),
calcular_TCarga(Carga_Total_Baterias,Carga_Atual_Baterias, TCarga, TResultado),
A1 is Carga_Total_Baterias * 0.8 - CargaNecessariaProximaViagem, LCcarregamentos = [C1|LCcarregou]);
(A1 is Carga_Atual_Baterias  - CargaNecessariaProximaViagem, TResultado is 0, LCcarregamentos=LCcarregou)),
calcular_tempoCargaOuTempoColocarRetirar(TResultado, C2, TCargaResultado),
custo([C2|LC], A1, Custo1, LCcarregou),
Custo is Custo1 + (Tempo * PesoComCarga / (Tara + Capacidade_Carga)) + TCargaResultado + TempoAdicional.


calcular_carga_baterias(CargaBateria, PesoComCarga, Tara, Capacidade_Carga, Resultado):-
Resultado is CargaBateria * PesoComCarga / (Tara + Capacidade_Carga).

calcular_tempoCargaOuTempoColocarRetirar(TCarga, C2, TCargaResultado):-
data(Data),
entrega(_, Data, _, C2, TempoColocar, TempoRetirar),
TCarga < TempoColocar + TempoRetirar, !,
TCargaResultado is TempoColocar + TempoRetirar;
TCargaResultado is TCarga.

calcular_TCarga(Carga_Total_Baterias,Carga_Atual_Baterias, TCarga, TResultado):-
TResultado is Carga_Atual_Baterias * TCarga / (Carga_Total_Baterias * 0.2).

armazensA(D,L):- findall(X,(entrega(_,D,_,X,_,_)),L).
encontrarTrajetos(Dia,LLTrajeto):-
armazensA(Dia,LA),findall(LTrajeto,permutation(LA,LTrajeto),LLTrajeto).

seq_custo_min(LC, LCcarregamentos,Custo):-(run(LC);true).

run(LC):-
calcula_custo(LC, Custo, LCcarregamentos),
atualiza(LC, Custo, LCcarregamentos), !,
fail.

atualiza(LCPerm, Custo, LCcarregamentos):-
custo_min(_,_,CustoMin),
((Custo<CustoMin,!,retract(custo_min(_,_,_)),assertz(custo_min(LCPerm, LCcarregamentos, Custo)));true).
% o write(Custo),nl so para ver a atualizacao do menor custo    

melhorEntregaFindAll(Data,Camiao):-
(retract(camiao(_));true), asserta(camiao(Camiao)),
retractall(custo_min(_, _ ,_)), assertz(custo_min(_, _,100000)), retractall(data(_)), assertz(data(Data)), 
encontrarTrajetos(Data, LP), melhorEntregaFindAllz(LP).

melhorEntregaFindAllz([]):-!.
melhorEntregaFindAllz([H | L]):-
append(["A05" | H] , ["A05"] , ListaComMatosinhos),
seq_custo_min(ListaComMatosinhos, LCcarregamentos,Custo),
melhorEntregaFindAllz(L).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Heurística 1: Entregar no Armazém mais próximo.

% Ids dos Armazens que têm entrega no dado dia.
armazensComEntregas(D,L):-findall(A, entrega(_,D,_,A,_,_), L).

% Insere o Armazem Principal no início e no fim da lista de Ids.
insereArmazemInicialFinal(N,[],[N]):-!.
insereArmazemInicialFinal(N,[X|T],[X|L1]):- insereArmazemInicialFinal(N,T,L1).

% Massa Total da Rota.
massaTotalRota([_|T],S,D):- massaTotalRotaI(T,S,D).
massaTotalRotaI([_],0,_):-!.
massaTotalRotaI([X|T],S,D):- massaTotalRotaI(T,S1,D), entrega(_,D,M,X,_,_) ,S is S1 + M.

% Cálculo da Massa e Tempo Total da Rota.
calculosRota(L,_,D):- massaTotalRota(L,S,D).

% Começamos por Obter a Lista dos Armazéns com Entregas no Dia 
% Obtemos o Id do Armazém Principal, neste caso Matosinhos
% maisProximoII
% Inserimos o Armazem Principal na Rota 
% Calculamos a Massa e o Tempo Total das Entregas
maisProximo(Dia,Camiao,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem("Matosinhos",Id),
                maisProximoII(Lista,Camiao,Rota,Id),insereArmazemInicialFinal(Id,Rota,RotaFinal),
                calculosRota(RotaFinal,_,Dia),!.

% Define o Armazem de Partida com para comparar o Tempo com os restantes Armazéns de forma a receber o mais próximo.
% Recebe o Armazém mais próximo e apaga da lista e coloca esse Armazém como de Partida e repete o processo. 
% <Lista>, <Lista com ArmazemInicial>, <ArmazemInicial> 
maisProximoII([],Camiao,[ArmazemInicial],ArmazemInicial):-!.
maisProximoII(Lista,Camiao,[ArmazemInicial|L1],ArmazemInicial):- maisProximoIII(ArmazemInicial,Camiao,Lista,ArmazemProximo,T),
                delete(Lista,ArmazemProximo,L2), maisProximoII(L2,Camiao,L1,ArmazemProximo).                

% Compara o Armazém de Partida com todos os restantes, e retorna o Armazém mais Próximo. 
% <ArmazemI>, <Lista>, <ArmazemProximo>, <Tempo>
maisProximoIII(_,_,[],_,0):- !.
maisProximoIII(ArmazemI,Camiao,[H|T],ArmazemProximo, Tempo) :- dadosCam_t_e_ta(Camiao,ArmazemI,H,T1,_,_), maisProximoIII(ArmazemI,Camiao,T,A1,T2),
                        ((T2==0,!,Tempo is T1,ArmazemProximo = H);
                        (T1<T2,!,Tempo is T1, ArmazemProximo = H);
                        Tempo is T2,ArmazemProximo = A1).
                        

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Heurística 2: Efetuar de seguida a Entrega com maior Massa.

% Ids dos Armazens que têm entrega no dado dia.
% Insere o Armazem Principal no início e no fim da lista de Ids.
% Massa Total da Rota.
% Cálculo da Massa e Tempo Total da Rota.

% Começamos por Obter a Lista dos Armazéns com Entregas no Dia 
% Obtemos o Id do Armazém Principal, neste caso Matosinhos
% maisProximoII
% Inserimos o Armazem Principal na Rota 
% Calculamos a Massa e o Tempo Total das Entregas
maiorMassa(Dia,_,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem("Matosinhos",Id),
                maiorMassaII(Lista,Rota,Id,Dia),insereArmazemInicialFinal(Id,Rota,RotaFinal),
                calculosRota(RotaFinal,_,Dia),!.

% Define o Armazem de Partida com para comparar o Tempo com os restantes Armazéns de forma a receber o mais próximo.
% Recebe o Armazém cuja Entrega tem maior Massa no dado dia 
% Apaga da lista e coloca esse Armazém como de Partida e repete o processo. 
% <Lista>, <Lista com ArmazemInicial>, <ArmazemInicial> 
maiorMassaII([],[ArmazemInicial],ArmazemInicial,_):-!.
maiorMassaII(Lista,[ArmazemInicial|L1],ArmazemInicial,Dia):- maiorMassaIII(ArmazemInicial,Lista,ArmazemProximo,T,Dia),
    
                delete(Lista,ArmazemProximo,L2), maiorMassaII(L2,L1,ArmazemProximo,Dia).                

% Compara o Armazém de Chegada com todos os restantes, e retorna o Armazém cuja Entrega tem maior Massa no dado dia. 
% <ArmazemI>, <Lista>, <ArmazemProximo>, <Tempo>
maiorMassaIII(_,[],_,0,_):- !.
maiorMassaIII(ArmazemI,[H|T],ArmazemProximo, Tempo,Dia) :- entrega(_,Dia,T1,H,_,_), maiorMassaIII(ArmazemI,T,A1,T2,Dia),
                        ((T2==0,!,Tempo is T1,ArmazemProximo = H);
                        (T1>T2,!,Tempo is T1, ArmazemProximo = H);
                        Tempo is T2,ArmazemProximo = A1).
                        

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Heurística 3: Efetuar de seguida a Entrega com menor Tempo e maior Massa.

% Começamos por Obter a Lista dos Armazéns com Entregas no Dia 
% Obtemos o Id do Armazém Principal, neste caso Matosinhos
% maisProximoII
% Inserimos o Armazem Principal na Rota 
% Calculamos a Massa e o Tempo Total das Entregas
combinadoTempoMassa(Dia,Camiao,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem("Matosinhos",Id),
                combinadoTempoMassaII(Lista,Camiao,Rota,Id),insereArmazemInicialFinal(Id,Rota,RotaFinal),
                calculosRota(RotaFinal,_,Dia),!.

% Define o Armazem de Partida com para comparar o Tempo com os restantes Armazéns de forma a receber o mais próximo.
% Recebe o Armazém mais próximo e apaga da lista e coloca esse Armazém como de Partida e repete o processo. 
% <Lista>, <Lista com ArmazemInicial>, <ArmazemInicial> 
combinadoTempoMassaII([],Camiao,[ArmazemInicial],ArmazemInicial):-!.
combinadoTempoMassaII(Lista,Camiao,[ArmazemInicial|L1],ArmazemInicial):- combinadoTempoMassaIII(ArmazemInicial,Camiao,Lista,ArmazemProximo,T),
                delete(Lista,ArmazemProximo,L2), combinadoTempoMassaII(L2,Camiao,L1,ArmazemProximo).                

% Compara o Armazém de Partida com todos os restantes, e retorna o Armazém mais Próximo. 
% <ArmazemI>, <Lista>, <ArmazemProximo>, <Tempo>
combinadoTempoMassaIII(_,_,[],_,0):- !.
combinadoTempoMassaIII(ArmazemI,Camiao,[H|T],ArmazemProximo, Tempo) :- entrega(_,Dia,Massa,H,_,_), dadosCam_t_e_ta(Camiao,ArmazemI,H,T1,_,_), combinadoTempoMassaIII(ArmazemI,Camiao,T,A1,T2),
                        ((T2==0,!,Tempo is T1 * Massa,ArmazemProximo = H);
                        (T1<T2,!,Tempo is T1 * Massa, ArmazemProximo = H);
                        Tempo is T2,ArmazemProximo = A1).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Algoritmo Genético

:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.
:-dynamic custo_min/3.
:-dynamic camiao/1.
:-dynamic melhor_solucao/1.
:-use_module(library(random)).

avaliaTempoSeq(Seq,Tempo):- retractall(custo_min(_, _ ,_)), assertz(custo_min(_, _,100000)), retractall(data(_)), assertz(data(Data))
, melhorEntregaFindAllz([Seq]), custo_min(A,B,Tempo).

% parameterizacão
inicializa(NG,DP,P1,P2):-
	(retract(geracoes(_));true),  asserta(geracoes(NG)),
	(retract(populacao(_));true), DD is (DP - 2) , asserta(populacao(DD)),
	PC is P1/100,(retract(prob_cruzamento(_));true), asserta(prob_cruzamento(PC)),
	PM is P2/100, (retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)).

% algoritmoGenetico(20221205,eTruck01,5,5,50,25,L). 
algoritmoGenetico(Dia,Camiao,NumeroGeracoes,DimensaoPopulacao,ProbCruzamento,ProbMutacao, MelhorSolucao):-
				(retract(camiao(_));true), asserta(camiao(Camiao)),
				(retract(melhor_solucao(_));true), asserta(melhor_solucao(Melhor)),
				gera(Dia,Camiao,NumeroGeracoes,DimensaoPopulacao,ProbCruzamento,ProbMutacao),
				melhor_solucao(MelhorSolucao).

gera(Dia,Camiao,NG,DP,P1,P2):-
	inicializa(NG,DP,P1,P2),
	gera_populacao(Dia,Populacao),
	maiorMassa(Dia,Camiao,L1),  delete(L1,"A05",ListaSemMatosinhos1),
	maisProximo(Dia,Camiao,L2), delete(L2,"A05",ListaSemMatosinhos2),
	insereInicio(ListaSemMatosinhos1,1,Populacao,Pop1), insereInicio(ListaSemMatosinhos2,1,Pop1,Pop2),
	avalia_populacao(Pop2,PopAv),
	ordena_populacao(PopAv,PopOrd),
	geracoes(NG), gera_geracao(0,NG,PopOrd).

gera_populacao(Dia,Populacao):-
	populacao(TamPop), %Obtém a Dimensão da População
	armazensComEntregas(Dia,ListaTarefas),
	length(ListaTarefas,NumT),
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

gera_geracao(G,G,Pop):-!, 
	first_elements_of_list(1,Pop,[Melhor*V]), 
	append(["A05"],Melhor,Melhor1),inverte(Melhor1,Melhor2),
	append(["A05"],Melhor2,Melhor3),inverte(Melhor3,Melhor4),
	(retract(melhor_solucao(_));true),  asserta(melhor_solucao(Melhor4)).

gera_geracao(N,G,Pop):-
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

melhorIndividuoDaGeracao([H|T],H).

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
	algoritmoGenetico(Data,eTruck01,5,5,50,25,Lista1),
	ordenar(Data,Lista1,Truck01,ListaTruck01),
	ordenar(Data,Lista1,Truck02,ListaTruck02),
	ordenar(Data,Lista1,Truck03,ListaTruck03).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

converterArmazem([],[]).
converterArmazem([H1|T1],[H2|T2]):- idArmazem(H2,H1), converterArmazem(T1,T2).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%carateristicasCam(<nome_camiao>,<tara>,<capacidade_carga>,<carga_total_baterias>,<autonomia>,<t_recarr_bat_20a80>).
carateristicasCam(eTruck01,7500,4300,80,100,60).
carateristicasCam(eTruck02,7500,4300,80,120,60).
carateristicasCam(eTruck03,7500,4300,80,100,60).

%dadosCam_t_e_ta(<nome_camiao>,<cidade_origem>,<cidade_destino>,<tempo>,<energia>,<tempo_adicional>).
dadosCam_t_e_ta(eTruck01,"A01","A02",122,42,0).
dadosCam_t_e_ta(eTruck01,"A01","A03",122,46,0).
dadosCam_t_e_ta(eTruck01,"A01","A04",151,54,25).
dadosCam_t_e_ta(eTruck01,"A01","A05",147,52,25).
dadosCam_t_e_ta(eTruck01,"A01","A06",74,24,0).
dadosCam_t_e_ta(eTruck01,"A01","A07",116,35,0).
dadosCam_t_e_ta(eTruck01,"A01","A08",141,46,0).
dadosCam_t_e_ta(eTruck01,"A01","A09",185,74,53).
dadosCam_t_e_ta(eTruck01,"A01","A10",97,30,0).
dadosCam_t_e_ta(eTruck01,"A01","A11",164,64,40).
dadosCam_t_e_ta(eTruck01,"A01","A12",76,23,0).
dadosCam_t_e_ta(eTruck01,"A01","A13",174,66,45).
dadosCam_t_e_ta(eTruck01,"A01","A14",59,18,0).
dadosCam_t_e_ta(eTruck01,"A01","A15",132,51,24).
dadosCam_t_e_ta(eTruck01,"A01","A16",181,68,45).
dadosCam_t_e_ta(eTruck01,"A01","A17",128,45,0).

dadosCam_t_e_ta(eTruck01,"A02","A01",116,42,0).
dadosCam_t_e_ta(eTruck01,"A02","A03",55,22,0).
dadosCam_t_e_ta(eTruck01,"A02","A04",74,25,0).
dadosCam_t_e_ta(eTruck01,"A02","A05",65,22,0).
dadosCam_t_e_ta(eTruck01,"A02","A06",69,27,0).
dadosCam_t_e_ta(eTruck01,"A02","A07",74,38,0).
dadosCam_t_e_ta(eTruck01,"A02","A08",61,18,0).
dadosCam_t_e_ta(eTruck01,"A02","A09",103,44,0).
dadosCam_t_e_ta(eTruck01,"A02","A10",36,14,0).
dadosCam_t_e_ta(eTruck01,"A02","A11",88,41,0).
dadosCam_t_e_ta(eTruck01,"A02","A12",61,19,0).
dadosCam_t_e_ta(eTruck01,"A02","A13",95,42,0).
dadosCam_t_e_ta(eTruck01,"A02","A14",78,34,0).
dadosCam_t_e_ta(eTruck01,"A02","A15",69,30,0).
dadosCam_t_e_ta(eTruck01,"A02","A16",99,38,0).
dadosCam_t_e_ta(eTruck01,"A02","A17",46,14,0).

dadosCam_t_e_ta(eTruck01,"A03","A01",120,45,0).
dadosCam_t_e_ta(eTruck01,"A03","A02",50,22,0).
dadosCam_t_e_ta(eTruck01,"A03","A04",46,15,0).
dadosCam_t_e_ta(eTruck01,"A03","A05",46,14,0).
dadosCam_t_e_ta(eTruck01,"A03","A06",74,37,0).
dadosCam_t_e_ta(eTruck01,"A03","A07",63,23,0).
dadosCam_t_e_ta(eTruck01,"A03","A08",38,8,0).
dadosCam_t_e_ta(eTruck01,"A03","A09",84,36,0).
dadosCam_t_e_ta(eTruck01,"A03","A10",59,28,0).
dadosCam_t_e_ta(eTruck01,"A03","A11",61,27,0).
dadosCam_t_e_ta(eTruck01,"A03","A12",67,32,0).
dadosCam_t_e_ta(eTruck01,"A03","A13",67,29,0).
dadosCam_t_e_ta(eTruck01,"A03","A14",82,38,0).
dadosCam_t_e_ta(eTruck01,"A03","A15",34,8,0).
dadosCam_t_e_ta(eTruck01,"A03","A16",80,30,0).
dadosCam_t_e_ta(eTruck01,"A03","A17",36,10,0).

dadosCam_t_e_ta(eTruck01,"A04","A01",149,54,25).
dadosCam_t_e_ta(eTruck01,"A04","A02",65,24,0).
dadosCam_t_e_ta(eTruck01,"A04","A03",46,16,0).
dadosCam_t_e_ta(eTruck01,"A04","A05",27,10,0).
dadosCam_t_e_ta(eTruck01,"A04","A06",103,47,0).
dadosCam_t_e_ta(eTruck01,"A04","A07",55,27,0).
dadosCam_t_e_ta(eTruck01,"A04","A08",36,10,0).
dadosCam_t_e_ta(eTruck01,"A04","A09",50,26,0).
dadosCam_t_e_ta(eTruck01,"A04","A10",78,34,0).
dadosCam_t_e_ta(eTruck01,"A04","A11",42,19,0).
dadosCam_t_e_ta(eTruck01,"A04","A12",97,42,0).
dadosCam_t_e_ta(eTruck01,"A04","A13",44,11,0).
dadosCam_t_e_ta(eTruck01,"A04","A14",111,48,0).
dadosCam_t_e_ta(eTruck01,"A04","A15",32,13,0).
dadosCam_t_e_ta(eTruck01,"A04","A16",53,14,0).
dadosCam_t_e_ta(eTruck01,"A04","A17",38,11,0).

dadosCam_t_e_ta(eTruck01,"A05","A01",141,51,24).
dadosCam_t_e_ta(eTruck01,"A05","A02",55,20,0).
dadosCam_t_e_ta(eTruck01,"A05","A03",48,14,0).
dadosCam_t_e_ta(eTruck01,"A05","A04",25,9,0).
dadosCam_t_e_ta(eTruck01,"A05","A06",97,44,0).
dadosCam_t_e_ta(eTruck01,"A05","A07",55,28,0).
dadosCam_t_e_ta(eTruck01,"A05","A08",29,7,0).
dadosCam_t_e_ta(eTruck01,"A05","A09",48,24,0).
dadosCam_t_e_ta(eTruck01,"A05","A10",69,30,0).
dadosCam_t_e_ta(eTruck01,"A05","A11",53,26,0).
dadosCam_t_e_ta(eTruck01,"A05","A12",95,36,0).
dadosCam_t_e_ta(eTruck01,"A05","A13",63,20,0).
dadosCam_t_e_ta(eTruck01,"A05","A14",105,45,0).
dadosCam_t_e_ta(eTruck01,"A05","A15",34,14,0).
dadosCam_t_e_ta(eTruck01,"A05","A16",46,18,0).
dadosCam_t_e_ta(eTruck01,"A05","A17",27,7,0).

dadosCam_t_e_ta(eTruck01,"A06","A01",69,23,0).
dadosCam_t_e_ta(eTruck01,"A06","A02",71,27,0).
dadosCam_t_e_ta(eTruck01,"A06","A03",74,38,0).
dadosCam_t_e_ta(eTruck01,"A06","A04",103,46,0).
dadosCam_t_e_ta(eTruck01,"A06","A05",99,44,0).
dadosCam_t_e_ta(eTruck01,"A06","A07",88,48,0).
dadosCam_t_e_ta(eTruck01,"A06","A08",92,38,0).
dadosCam_t_e_ta(eTruck01,"A06","A09",134,66,45).
dadosCam_t_e_ta(eTruck01,"A06","A10",42,14,0).
dadosCam_t_e_ta(eTruck01,"A06","A11",116,56,30).
dadosCam_t_e_ta(eTruck01,"A06","A12",23,9,0).
dadosCam_t_e_ta(eTruck01,"A06","A13",126,58,33).
dadosCam_t_e_ta(eTruck01,"A06","A14",25,9,0).
dadosCam_t_e_ta(eTruck01,"A06","A15",84,44,0).
dadosCam_t_e_ta(eTruck01,"A06","A16",132,60,35).
dadosCam_t_e_ta(eTruck01,"A06","A17",80,38,0).

dadosCam_t_e_ta(eTruck01,"A07","A01",116,36,0).
dadosCam_t_e_ta(eTruck01,"A07","A02",71,38,0).
dadosCam_t_e_ta(eTruck01,"A07","A03",61,22,0).
dadosCam_t_e_ta(eTruck01,"A07","A04",53,26,0).
dadosCam_t_e_ta(eTruck01,"A07","A05",53,28,0).
dadosCam_t_e_ta(eTruck01,"A07","A06",88,48,0).
dadosCam_t_e_ta(eTruck01,"A07","A08",59,26,0).
dadosCam_t_e_ta(eTruck01,"A07","A09",88,48,0).
dadosCam_t_e_ta(eTruck01,"A07","A10",84,44,0).
dadosCam_t_e_ta(eTruck01,"A07","A11",74,22,0).
dadosCam_t_e_ta(eTruck01,"A07","A12",82,42,0).
dadosCam_t_e_ta(eTruck01,"A07","A13",76,31,0).
dadosCam_t_e_ta(eTruck01,"A07","A14",97,49,21).
dadosCam_t_e_ta(eTruck01,"A07","A15",29,16,0).
dadosCam_t_e_ta(eTruck01,"A07","A16",84,42,0).
dadosCam_t_e_ta(eTruck01,"A07","A17",69,30,0).

dadosCam_t_e_ta(eTruck01,"A08","A01",134,46,0).
dadosCam_t_e_ta(eTruck01,"A08","A02",59,18,0).
dadosCam_t_e_ta(eTruck01,"A08","A03",32,6,0).
dadosCam_t_e_ta(eTruck01,"A08","A04",34,10,0).
dadosCam_t_e_ta(eTruck01,"A08","A05",32,7,0).
dadosCam_t_e_ta(eTruck01,"A08","A06",88,38,0).
dadosCam_t_e_ta(eTruck01,"A08","A07",57,26,0).
dadosCam_t_e_ta(eTruck01,"A08","A09",69,30,0).
dadosCam_t_e_ta(eTruck01,"A08","A10",65,26,0).
dadosCam_t_e_ta(eTruck01,"A08","A11",53,22,0).
dadosCam_t_e_ta(eTruck01,"A08","A12",82,34,0).
dadosCam_t_e_ta(eTruck01,"A08","A13",61,24,0).
dadosCam_t_e_ta(eTruck01,"A08","A14",97,40,0).
dadosCam_t_e_ta(eTruck01,"A08","A15",36,12,0).
dadosCam_t_e_ta(eTruck01,"A08","A16",65,23,0).
dadosCam_t_e_ta(eTruck01,"A08","A17",32,6,0).

dadosCam_t_e_ta(eTruck01,"A09","A01",181,72,50).
dadosCam_t_e_ta(eTruck01,"A09","A02",95,41,0).
dadosCam_t_e_ta(eTruck01,"A09","A03",86,35,0).
dadosCam_t_e_ta(eTruck01,"A09","A04",55,24,0).
dadosCam_t_e_ta(eTruck01,"A09","A05",48,23,0).
dadosCam_t_e_ta(eTruck01,"A09","A06",134,65,42).
dadosCam_t_e_ta(eTruck01,"A09","A07",95,47,0).
dadosCam_t_e_ta(eTruck01,"A09","A08",69,28,0).
dadosCam_t_e_ta(eTruck01,"A09","A10",109,51,24).
dadosCam_t_e_ta(eTruck01,"A09","A11",61,29,0).
dadosCam_t_e_ta(eTruck01,"A09","A12",132,57,31).
dadosCam_t_e_ta(eTruck01,"A09","A13",67,19,0).
dadosCam_t_e_ta(eTruck01,"A09","A14",143,66,45).
dadosCam_t_e_ta(eTruck01,"A09","A15",71,34,0).
dadosCam_t_e_ta(eTruck01,"A09","A16",15,3,0).
dadosCam_t_e_ta(eTruck01,"A09","A17",67,28,0).

dadosCam_t_e_ta(eTruck01,"A10","A01",97,30,0).
dadosCam_t_e_ta(eTruck01,"A10","A02",34,14,0).
dadosCam_t_e_ta(eTruck01,"A10","A03",59,27,0).
dadosCam_t_e_ta(eTruck01,"A10","A04",78,33,0).
dadosCam_t_e_ta(eTruck01,"A10","A05",71,30,0).
dadosCam_t_e_ta(eTruck01,"A10","A06",40,14,0).
dadosCam_t_e_ta(eTruck01,"A10","A07",82,42,0).
dadosCam_t_e_ta(eTruck01,"A10","A08",65,24,0).
dadosCam_t_e_ta(eTruck01,"A10","A09",109,52,25).
dadosCam_t_e_ta(eTruck01,"A10","A11",92,46,0).
dadosCam_t_e_ta(eTruck01,"A10","A12",32,6,0).
dadosCam_t_e_ta(eTruck01,"A10","A13",99,46,0).
dadosCam_t_e_ta(eTruck01,"A10","A14",63,17,0).
dadosCam_t_e_ta(eTruck01,"A10","A15",74,34,0).
dadosCam_t_e_ta(eTruck01,"A10","A16",105,46,0).
dadosCam_t_e_ta(eTruck01,"A10","A17",53,23,0).

dadosCam_t_e_ta(eTruck01,"A11","A01",164,65,42).
dadosCam_t_e_ta(eTruck01,"A11","A02",88,41,0).
dadosCam_t_e_ta(eTruck01,"A11","A03",65,28,0).
dadosCam_t_e_ta(eTruck01,"A11","A04",42,18,0).
dadosCam_t_e_ta(eTruck01,"A11","A05",55,25,0).
dadosCam_t_e_ta(eTruck01,"A11","A06",118,57,31).
dadosCam_t_e_ta(eTruck01,"A11","A07",74,23,0).
dadosCam_t_e_ta(eTruck01,"A11","A08",59,23,0).
dadosCam_t_e_ta(eTruck01,"A11","A09",63,28,0).
dadosCam_t_e_ta(eTruck01,"A11","A10",97,46,0).
dadosCam_t_e_ta(eTruck01,"A11","A12",111,52,25).
dadosCam_t_e_ta(eTruck01,"A11","A13",25,7,0).
dadosCam_t_e_ta(eTruck01,"A11","A14",126,58,33).
dadosCam_t_e_ta(eTruck01,"A11","A15",53,25,0).
dadosCam_t_e_ta(eTruck01,"A11","A16",59,27,0).
dadosCam_t_e_ta(eTruck01,"A11","A17",67,27,0).

dadosCam_t_e_ta(eTruck01,"A12","A01",76,23,0).
dadosCam_t_e_ta(eTruck01,"A12","A02",61,19,0).
dadosCam_t_e_ta(eTruck01,"A12","A03",67,32,0).
dadosCam_t_e_ta(eTruck01,"A12","A04",97,41,0).
dadosCam_t_e_ta(eTruck01,"A12","A05",92,38,0).
dadosCam_t_e_ta(eTruck01,"A12","A06",19,8,0).
dadosCam_t_e_ta(eTruck01,"A12","A07",82,42,0).
dadosCam_t_e_ta(eTruck01,"A12","A08",86,33,0).
dadosCam_t_e_ta(eTruck01,"A12","A09",128,61,37).
dadosCam_t_e_ta(eTruck01,"A12","A10",32,6,0).
dadosCam_t_e_ta(eTruck01,"A12","A11",109,50,23).
dadosCam_t_e_ta(eTruck01,"A12","A13",120,53,26).
dadosCam_t_e_ta(eTruck01,"A12","A14",40,10,0).
dadosCam_t_e_ta(eTruck01,"A12","A15",78,38,0).
dadosCam_t_e_ta(eTruck01,"A12","A16",126,54,28).
dadosCam_t_e_ta(eTruck01,"A12","A17",74,32,0).

dadosCam_t_e_ta(eTruck01,"A13","A01",174,65,42).
dadosCam_t_e_ta(eTruck01,"A13","A02",107,35,0).
dadosCam_t_e_ta(eTruck01,"A13","A03",74,29,0).
dadosCam_t_e_ta(eTruck01,"A13","A04",46,11,0).
dadosCam_t_e_ta(eTruck01,"A13","A05",67,20,0).
dadosCam_t_e_ta(eTruck01,"A13","A06",128,57,31).
dadosCam_t_e_ta(eTruck01,"A13","A07",80,30,0).
dadosCam_t_e_ta(eTruck01,"A13","A08",76,20,0).
dadosCam_t_e_ta(eTruck01,"A13","A09",67,20,0).
dadosCam_t_e_ta(eTruck01,"A13","A10",105,47,0).
dadosCam_t_e_ta(eTruck01,"A13","A11",27,7,0).
dadosCam_t_e_ta(eTruck01,"A13","A12",122,52,25).
dadosCam_t_e_ta(eTruck01,"A13","A14",137,58,33).
dadosCam_t_e_ta(eTruck01,"A13","A15",67,17,0).
dadosCam_t_e_ta(eTruck01,"A13","A16",59,15,0).
dadosCam_t_e_ta(eTruck01,"A13","A17",78,22,0).

dadosCam_t_e_ta(eTruck01,"A14","A01",59,18,0).
dadosCam_t_e_ta(eTruck01,"A14","A02",80,35,0).
dadosCam_t_e_ta(eTruck01,"A14","A03",80,38,0).
dadosCam_t_e_ta(eTruck01,"A14","A04",109,46,0).
dadosCam_t_e_ta(eTruck01,"A14","A05",105,45,0).
dadosCam_t_e_ta(eTruck01,"A14","A06",27,9,0).
dadosCam_t_e_ta(eTruck01,"A14","A07",97,48,0).
dadosCam_t_e_ta(eTruck01,"A14","A08",99,38,0).
dadosCam_t_e_ta(eTruck01,"A14","A09",143,66,45).
dadosCam_t_e_ta(eTruck01,"A14","A10",61,17,0).
dadosCam_t_e_ta(eTruck01,"A14","A11",122,57,31).
dadosCam_t_e_ta(eTruck01,"A14","A12",42,10,0).
dadosCam_t_e_ta(eTruck01,"A14","A13",132,58,35).
dadosCam_t_e_ta(eTruck01,"A14","A15",90,44,0).
dadosCam_t_e_ta(eTruck01,"A14","A16",139,61,37).
dadosCam_t_e_ta(eTruck01,"A14","A17",86,38,0).

dadosCam_t_e_ta(eTruck01,"A15","A01",132,51,24).
dadosCam_t_e_ta(eTruck01,"A15","A02",74,30,0).
dadosCam_t_e_ta(eTruck01,"A15","A03",34,8,0).
dadosCam_t_e_ta(eTruck01,"A15","A04",36,12,0).
dadosCam_t_e_ta(eTruck01,"A15","A05",36,14,0).
dadosCam_t_e_ta(eTruck01,"A15","A06",86,44,0).
dadosCam_t_e_ta(eTruck01,"A15","A07",34,16,0).
dadosCam_t_e_ta(eTruck01,"A15","A08",42,13,0).
dadosCam_t_e_ta(eTruck01,"A15","A09",71,35,0).
dadosCam_t_e_ta(eTruck01,"A15","A10",82,36,0).
dadosCam_t_e_ta(eTruck01,"A15","A11",53,25,0).
dadosCam_t_e_ta(eTruck01,"A15","A12",80,38,0).
dadosCam_t_e_ta(eTruck01,"A15","A13",69,18,0).
dadosCam_t_e_ta(eTruck01,"A15","A14",95,45,0).
dadosCam_t_e_ta(eTruck01,"A15","A16",69,29,0).
dadosCam_t_e_ta(eTruck01,"A15","A17",53,17,0).

dadosCam_t_e_ta(eTruck01,"A16","A01",179,68,45).
dadosCam_t_e_ta(eTruck01,"A16","A02",92,37,0).
dadosCam_t_e_ta(eTruck01,"A16","A03",84,31,0).
dadosCam_t_e_ta(eTruck01,"A16","A04",57,16,0).
dadosCam_t_e_ta(eTruck01,"A16","A05",46,18,0).
dadosCam_t_e_ta(eTruck01,"A16","A06",132,60,35).
dadosCam_t_e_ta(eTruck01,"A16","A07",92,42,0).
dadosCam_t_e_ta(eTruck01,"A16","A08",67,23,0).
dadosCam_t_e_ta(eTruck01,"A16","A09",15,3,0).
dadosCam_t_e_ta(eTruck01,"A16","A10",105,46,0).
dadosCam_t_e_ta(eTruck01,"A16","A11",57,28,0).
dadosCam_t_e_ta(eTruck01,"A16","A12",130,52,25).
dadosCam_t_e_ta(eTruck01,"A16","A13",61,15,0).
dadosCam_t_e_ta(eTruck01,"A16","A14",141,61,37).
dadosCam_t_e_ta(eTruck01,"A16","A15",69,29,0).
dadosCam_t_e_ta(eTruck01,"A16","A17",65,24,0).

dadosCam_t_e_ta(eTruck01,"A17","A01",128,46,0).
dadosCam_t_e_ta(eTruck01,"A17","A02",42,14,0).
dadosCam_t_e_ta(eTruck01,"A17","A03",40,11,0).
dadosCam_t_e_ta(eTruck01,"A17","A04",42,13,0).
dadosCam_t_e_ta(eTruck01,"A17","A05",34,10,0).
dadosCam_t_e_ta(eTruck01,"A17","A06",82,38,0).
dadosCam_t_e_ta(eTruck01,"A17","A07",74,30,0).
dadosCam_t_e_ta(eTruck01,"A17","A08",29,6,0).
dadosCam_t_e_ta(eTruck01,"A17","A09",69,31,0).
dadosCam_t_e_ta(eTruck01,"A17","A10",55,24,0).
dadosCam_t_e_ta(eTruck01,"A17","A11",69,29,0).
dadosCam_t_e_ta(eTruck01,"A17","A12",80,30,0).
dadosCam_t_e_ta(eTruck01,"A17","A13",82,23,0).
dadosCam_t_e_ta(eTruck01,"A17","A14",90,38,0).
dadosCam_t_e_ta(eTruck01,"A17","A15",53,18,0).
dadosCam_t_e_ta(eTruck01,"A17","A16",67,25,0).

dadosCam_t_e_ta(eTruck02,"A01","A02",122,42,0).
dadosCam_t_e_ta(eTruck02,"A01","A03",122,46,0).
dadosCam_t_e_ta(eTruck02,"A01","A04",151,54,25).
dadosCam_t_e_ta(eTruck02,"A01","A05",147,52,25).
dadosCam_t_e_ta(eTruck02,"A01","A06",74,24,0).
dadosCam_t_e_ta(eTruck02,"A01","A07",116,35,0).
dadosCam_t_e_ta(eTruck02,"A01","A08",141,46,0).
dadosCam_t_e_ta(eTruck02,"A01","A09",185,74,53).
dadosCam_t_e_ta(eTruck02,"A01","A10",97,30,0).
dadosCam_t_e_ta(eTruck02,"A01","A11",164,64,40).
dadosCam_t_e_ta(eTruck02,"A01","A12",76,23,0).
dadosCam_t_e_ta(eTruck02,"A01","A13",174,66,45).
dadosCam_t_e_ta(eTruck02,"A01","A14",59,18,0).
dadosCam_t_e_ta(eTruck02,"A01","A15",132,51,24).
dadosCam_t_e_ta(eTruck02,"A01","A16",181,68,45).
dadosCam_t_e_ta(eTruck02,"A01","A17",128,45,0).

dadosCam_t_e_ta(eTruck02,"A02","A01",116,42,0).
dadosCam_t_e_ta(eTruck02,"A02","A03",55,22,0).
dadosCam_t_e_ta(eTruck02,"A02","A04",74,25,0).
dadosCam_t_e_ta(eTruck02,"A02","A05",65,22,0).
dadosCam_t_e_ta(eTruck02,"A02","A06",69,27,0).
dadosCam_t_e_ta(eTruck02,"A02","A07",74,38,0).
dadosCam_t_e_ta(eTruck02,"A02","A08",61,18,0).
dadosCam_t_e_ta(eTruck02,"A02","A09",103,44,0).
dadosCam_t_e_ta(eTruck02,"A02","A10",36,14,0).
dadosCam_t_e_ta(eTruck02,"A02","A11",88,41,0).
dadosCam_t_e_ta(eTruck02,"A02","A12",61,19,0).
dadosCam_t_e_ta(eTruck02,"A02","A13",95,42,0).
dadosCam_t_e_ta(eTruck02,"A02","A14",78,34,0).
dadosCam_t_e_ta(eTruck02,"A02","A15",69,30,0).
dadosCam_t_e_ta(eTruck02,"A02","A16",99,38,0).
dadosCam_t_e_ta(eTruck02,"A02","A17",46,14,0).

dadosCam_t_e_ta(eTruck02,"A03","A01",120,45,0).
dadosCam_t_e_ta(eTruck02,"A03","A02",50,22,0).
dadosCam_t_e_ta(eTruck02,"A03","A04",46,15,0).
dadosCam_t_e_ta(eTruck02,"A03","A05",46,14,0).
dadosCam_t_e_ta(eTruck02,"A03","A06",74,37,0).
dadosCam_t_e_ta(eTruck02,"A03","A07",63,23,0).
dadosCam_t_e_ta(eTruck02,"A03","A08",38,8,0).
dadosCam_t_e_ta(eTruck02,"A03","A09",84,36,0).
dadosCam_t_e_ta(eTruck02,"A03","A10",59,28,0).
dadosCam_t_e_ta(eTruck02,"A03","A11",61,27,0).
dadosCam_t_e_ta(eTruck02,"A03","A12",67,32,0).
dadosCam_t_e_ta(eTruck02,"A03","A13",67,29,0).
dadosCam_t_e_ta(eTruck02,"A03","A14",82,38,0).
dadosCam_t_e_ta(eTruck02,"A03","A15",34,8,0).
dadosCam_t_e_ta(eTruck02,"A03","A16",80,30,0).
dadosCam_t_e_ta(eTruck02,"A03","A17",36,10,0).

dadosCam_t_e_ta(eTruck02,"A04","A01",149,54,25).
dadosCam_t_e_ta(eTruck02,"A04","A02",65,24,0).
dadosCam_t_e_ta(eTruck02,"A04","A03",46,16,0).
dadosCam_t_e_ta(eTruck02,"A04","A05",27,10,0).
dadosCam_t_e_ta(eTruck02,"A04","A06",103,47,0).
dadosCam_t_e_ta(eTruck02,"A04","A07",55,27,0).
dadosCam_t_e_ta(eTruck02,"A04","A08",36,10,0).
dadosCam_t_e_ta(eTruck02,"A04","A09",50,26,0).
dadosCam_t_e_ta(eTruck02,"A04","A10",78,34,0).
dadosCam_t_e_ta(eTruck02,"A04","A11",42,19,0).
dadosCam_t_e_ta(eTruck02,"A04","A12",97,42,0).
dadosCam_t_e_ta(eTruck02,"A04","A13",44,11,0).
dadosCam_t_e_ta(eTruck02,"A04","A14",111,48,0).
dadosCam_t_e_ta(eTruck02,"A04","A15",32,13,0).
dadosCam_t_e_ta(eTruck02,"A04","A16",53,14,0).
dadosCam_t_e_ta(eTruck02,"A04","A17",38,11,0).

dadosCam_t_e_ta(eTruck02,"A05","A01",141,51,24).
dadosCam_t_e_ta(eTruck02,"A05","A02",55,20,0).
dadosCam_t_e_ta(eTruck02,"A05","A03",48,14,0).
dadosCam_t_e_ta(eTruck02,"A05","A04",25,9,0).
dadosCam_t_e_ta(eTruck02,"A05","A06",97,44,0).
dadosCam_t_e_ta(eTruck02,"A05","A07",55,28,0).
dadosCam_t_e_ta(eTruck02,"A05","A08",29,7,0).
dadosCam_t_e_ta(eTruck02,"A05","A09",48,24,0).
dadosCam_t_e_ta(eTruck02,"A05","A10",69,30,0).
dadosCam_t_e_ta(eTruck02,"A05","A11",53,26,0).
dadosCam_t_e_ta(eTruck02,"A05","A12",95,36,0).
dadosCam_t_e_ta(eTruck02,"A05","A13",63,20,0).
dadosCam_t_e_ta(eTruck02,"A05","A14",105,45,0).
dadosCam_t_e_ta(eTruck02,"A05","A15",34,14,0).
dadosCam_t_e_ta(eTruck02,"A05","A16",46,18,0).
dadosCam_t_e_ta(eTruck02,"A05","A17",27,7,0).

dadosCam_t_e_ta(eTruck02,"A06","A01",69,23,0).
dadosCam_t_e_ta(eTruck02,"A06","A02",71,27,0).
dadosCam_t_e_ta(eTruck02,"A06","A03",74,38,0).
dadosCam_t_e_ta(eTruck02,"A06","A04",103,46,0).
dadosCam_t_e_ta(eTruck02,"A06","A05",99,44,0).
dadosCam_t_e_ta(eTruck02,"A06","A07",88,48,0).
dadosCam_t_e_ta(eTruck02,"A06","A08",92,38,0).
dadosCam_t_e_ta(eTruck02,"A06","A09",134,66,45).
dadosCam_t_e_ta(eTruck02,"A06","A10",42,14,0).
dadosCam_t_e_ta(eTruck02,"A06","A11",116,56,30).
dadosCam_t_e_ta(eTruck02,"A06","A12",23,9,0).
dadosCam_t_e_ta(eTruck02,"A06","A13",126,58,33).
dadosCam_t_e_ta(eTruck02,"A06","A14",25,9,0).
dadosCam_t_e_ta(eTruck02,"A06","A15",84,44,0).
dadosCam_t_e_ta(eTruck02,"A06","A16",132,60,35).
dadosCam_t_e_ta(eTruck02,"A06","A17",80,38,0).

dadosCam_t_e_ta(eTruck02,"A07","A01",116,36,0).
dadosCam_t_e_ta(eTruck02,"A07","A02",71,38,0).
dadosCam_t_e_ta(eTruck02,"A07","A03",61,22,0).
dadosCam_t_e_ta(eTruck02,"A07","A04",53,26,0).
dadosCam_t_e_ta(eTruck02,"A07","A05",53,28,0).
dadosCam_t_e_ta(eTruck02,"A07","A06",88,48,0).
dadosCam_t_e_ta(eTruck02,"A07","A08",59,26,0).
dadosCam_t_e_ta(eTruck02,"A07","A09",88,48,0).
dadosCam_t_e_ta(eTruck02,"A07","A10",84,44,0).
dadosCam_t_e_ta(eTruck02,"A07","A11",74,22,0).
dadosCam_t_e_ta(eTruck02,"A07","A12",82,42,0).
dadosCam_t_e_ta(eTruck02,"A07","A13",76,31,0).
dadosCam_t_e_ta(eTruck02,"A07","A14",97,49,21).
dadosCam_t_e_ta(eTruck02,"A07","A15",29,16,0).
dadosCam_t_e_ta(eTruck02,"A07","A16",84,42,0).
dadosCam_t_e_ta(eTruck02,"A07","A17",69,30,0).

dadosCam_t_e_ta(eTruck02,"A08","A01",134,46,0).
dadosCam_t_e_ta(eTruck02,"A08","A02",59,18,0).
dadosCam_t_e_ta(eTruck02,"A08","A03",32,6,0).
dadosCam_t_e_ta(eTruck02,"A08","A04",34,10,0).
dadosCam_t_e_ta(eTruck02,"A08","A05",32,7,0).
dadosCam_t_e_ta(eTruck02,"A08","A06",88,38,0).
dadosCam_t_e_ta(eTruck02,"A08","A07",57,26,0).
dadosCam_t_e_ta(eTruck02,"A08","A09",69,30,0).
dadosCam_t_e_ta(eTruck02,"A08","A10",65,26,0).
dadosCam_t_e_ta(eTruck02,"A08","A11",53,22,0).
dadosCam_t_e_ta(eTruck02,"A08","A12",82,34,0).
dadosCam_t_e_ta(eTruck02,"A08","A13",61,24,0).
dadosCam_t_e_ta(eTruck02,"A08","A14",97,40,0).
dadosCam_t_e_ta(eTruck02,"A08","A15",36,12,0).
dadosCam_t_e_ta(eTruck02,"A08","A16",65,23,0).
dadosCam_t_e_ta(eTruck02,"A08","A17",32,6,0).

dadosCam_t_e_ta(eTruck02,"A09","A01",181,72,50).
dadosCam_t_e_ta(eTruck02,"A09","A02",95,41,0).
dadosCam_t_e_ta(eTruck02,"A09","A03",86,35,0).
dadosCam_t_e_ta(eTruck02,"A09","A04",55,24,0).
dadosCam_t_e_ta(eTruck02,"A09","A05",48,23,0).
dadosCam_t_e_ta(eTruck02,"A09","A06",134,65,42).
dadosCam_t_e_ta(eTruck02,"A09","A07",95,47,0).
dadosCam_t_e_ta(eTruck02,"A09","A08",69,28,0).
dadosCam_t_e_ta(eTruck02,"A09","A10",109,51,24).
dadosCam_t_e_ta(eTruck02,"A09","A11",61,29,0).
dadosCam_t_e_ta(eTruck02,"A09","A12",132,57,31).
dadosCam_t_e_ta(eTruck02,"A09","A13",67,19,0).
dadosCam_t_e_ta(eTruck02,"A09","A14",143,66,45).
dadosCam_t_e_ta(eTruck02,"A09","A15",71,34,0).
dadosCam_t_e_ta(eTruck02,"A09","A16",15,3,0).
dadosCam_t_e_ta(eTruck02,"A09","A17",67,28,0).

dadosCam_t_e_ta(eTruck02,"A10","A01",97,30,0).
dadosCam_t_e_ta(eTruck02,"A10","A02",34,14,0).
dadosCam_t_e_ta(eTruck02,"A10","A03",59,27,0).
dadosCam_t_e_ta(eTruck02,"A10","A04",78,33,0).
dadosCam_t_e_ta(eTruck02,"A10","A05",71,30,0).
dadosCam_t_e_ta(eTruck02,"A10","A06",40,14,0).
dadosCam_t_e_ta(eTruck02,"A10","A07",82,42,0).
dadosCam_t_e_ta(eTruck02,"A10","A08",65,24,0).
dadosCam_t_e_ta(eTruck02,"A10","A09",109,52,25).
dadosCam_t_e_ta(eTruck02,"A10","A11",92,46,0).
dadosCam_t_e_ta(eTruck02,"A10","A12",32,6,0).
dadosCam_t_e_ta(eTruck02,"A10","A13",99,46,0).
dadosCam_t_e_ta(eTruck02,"A10","A14",63,17,0).
dadosCam_t_e_ta(eTruck02,"A10","A15",74,34,0).
dadosCam_t_e_ta(eTruck02,"A10","A16",105,46,0).
dadosCam_t_e_ta(eTruck02,"A10","A17",53,23,0).

dadosCam_t_e_ta(eTruck02,"A11","A01",164,65,42).
dadosCam_t_e_ta(eTruck02,"A11","A02",88,41,0).
dadosCam_t_e_ta(eTruck02,"A11","A03",65,28,0).
dadosCam_t_e_ta(eTruck02,"A11","A04",42,18,0).
dadosCam_t_e_ta(eTruck02,"A11","A05",55,25,0).
dadosCam_t_e_ta(eTruck02,"A11","A06",118,57,31).
dadosCam_t_e_ta(eTruck02,"A11","A07",74,23,0).
dadosCam_t_e_ta(eTruck02,"A11","A08",59,23,0).
dadosCam_t_e_ta(eTruck02,"A11","A09",63,28,0).
dadosCam_t_e_ta(eTruck02,"A11","A10",97,46,0).
dadosCam_t_e_ta(eTruck02,"A11","A12",111,52,25).
dadosCam_t_e_ta(eTruck02,"A11","A13",25,7,0).
dadosCam_t_e_ta(eTruck02,"A11","A14",126,58,33).
dadosCam_t_e_ta(eTruck02,"A11","A15",53,25,0).
dadosCam_t_e_ta(eTruck02,"A11","A16",59,27,0).
dadosCam_t_e_ta(eTruck02,"A11","A17",67,27,0).

dadosCam_t_e_ta(eTruck02,"A12","A01",76,23,0).
dadosCam_t_e_ta(eTruck02,"A12","A02",61,19,0).
dadosCam_t_e_ta(eTruck02,"A12","A03",67,32,0).
dadosCam_t_e_ta(eTruck02,"A12","A04",97,41,0).
dadosCam_t_e_ta(eTruck02,"A12","A05",92,38,0).
dadosCam_t_e_ta(eTruck02,"A12","A06",19,8,0).
dadosCam_t_e_ta(eTruck02,"A12","A07",82,42,0).
dadosCam_t_e_ta(eTruck02,"A12","A08",86,33,0).
dadosCam_t_e_ta(eTruck02,"A12","A09",128,61,37).
dadosCam_t_e_ta(eTruck02,"A12","A10",32,6,0).
dadosCam_t_e_ta(eTruck02,"A12","A11",109,50,23).
dadosCam_t_e_ta(eTruck02,"A12","A13",120,53,26).
dadosCam_t_e_ta(eTruck02,"A12","A14",40,10,0).
dadosCam_t_e_ta(eTruck02,"A12","A15",78,38,0).
dadosCam_t_e_ta(eTruck02,"A12","A16",126,54,28).
dadosCam_t_e_ta(eTruck02,"A12","A17",74,32,0).

dadosCam_t_e_ta(eTruck02,"A13","A01",174,65,42).
dadosCam_t_e_ta(eTruck02,"A13","A02",107,35,0).
dadosCam_t_e_ta(eTruck02,"A13","A03",74,29,0).
dadosCam_t_e_ta(eTruck02,"A13","A04",46,11,0).
dadosCam_t_e_ta(eTruck02,"A13","A05",67,20,0).
dadosCam_t_e_ta(eTruck02,"A13","A06",128,57,31).
dadosCam_t_e_ta(eTruck02,"A13","A07",80,30,0).
dadosCam_t_e_ta(eTruck02,"A13","A08",76,20,0).
dadosCam_t_e_ta(eTruck02,"A13","A09",67,20,0).
dadosCam_t_e_ta(eTruck02,"A13","A10",105,47,0).
dadosCam_t_e_ta(eTruck02,"A13","A11",27,7,0).
dadosCam_t_e_ta(eTruck02,"A13","A12",122,52,25).
dadosCam_t_e_ta(eTruck02,"A13","A14",137,58,33).
dadosCam_t_e_ta(eTruck02,"A13","A15",67,17,0).
dadosCam_t_e_ta(eTruck02,"A13","A16",59,15,0).
dadosCam_t_e_ta(eTruck02,"A13","A17",78,22,0).

dadosCam_t_e_ta(eTruck02,"A14","A01",59,18,0).
dadosCam_t_e_ta(eTruck02,"A14","A02",80,35,0).
dadosCam_t_e_ta(eTruck02,"A14","A03",80,38,0).
dadosCam_t_e_ta(eTruck02,"A14","A04",109,46,0).
dadosCam_t_e_ta(eTruck02,"A14","A05",105,45,0).
dadosCam_t_e_ta(eTruck02,"A14","A06",27,9,0).
dadosCam_t_e_ta(eTruck02,"A14","A07",97,48,0).
dadosCam_t_e_ta(eTruck02,"A14","A08",99,38,0).
dadosCam_t_e_ta(eTruck02,"A14","A09",143,66,45).
dadosCam_t_e_ta(eTruck02,"A14","A10",61,17,0).
dadosCam_t_e_ta(eTruck02,"A14","A11",122,57,31).
dadosCam_t_e_ta(eTruck02,"A14","A12",42,10,0).
dadosCam_t_e_ta(eTruck02,"A14","A13",132,58,35).
dadosCam_t_e_ta(eTruck02,"A14","A15",90,44,0).
dadosCam_t_e_ta(eTruck02,"A14","A16",139,61,37).
dadosCam_t_e_ta(eTruck02,"A14","A17",86,38,0).

dadosCam_t_e_ta(eTruck02,"A15","A01",132,51,24).
dadosCam_t_e_ta(eTruck02,"A15","A02",74,30,0).
dadosCam_t_e_ta(eTruck02,"A15","A03",34,8,0).
dadosCam_t_e_ta(eTruck02,"A15","A04",36,12,0).
dadosCam_t_e_ta(eTruck02,"A15","A05",36,14,0).
dadosCam_t_e_ta(eTruck02,"A15","A06",86,44,0).
dadosCam_t_e_ta(eTruck02,"A15","A07",34,16,0).
dadosCam_t_e_ta(eTruck02,"A15","A08",42,13,0).
dadosCam_t_e_ta(eTruck02,"A15","A09",71,35,0).
dadosCam_t_e_ta(eTruck02,"A15","A10",82,36,0).
dadosCam_t_e_ta(eTruck02,"A15","A11",53,25,0).
dadosCam_t_e_ta(eTruck02,"A15","A12",80,38,0).
dadosCam_t_e_ta(eTruck02,"A15","A13",69,18,0).
dadosCam_t_e_ta(eTruck02,"A15","A14",95,45,0).
dadosCam_t_e_ta(eTruck02,"A15","A16",69,29,0).
dadosCam_t_e_ta(eTruck02,"A15","A17",53,17,0).

dadosCam_t_e_ta(eTruck02,"A16","A01",179,68,45).
dadosCam_t_e_ta(eTruck02,"A16","A02",92,37,0).
dadosCam_t_e_ta(eTruck02,"A16","A03",84,31,0).
dadosCam_t_e_ta(eTruck02,"A16","A04",57,16,0).
dadosCam_t_e_ta(eTruck02,"A16","A05",46,18,0).
dadosCam_t_e_ta(eTruck02,"A16","A06",132,60,35).
dadosCam_t_e_ta(eTruck02,"A16","A07",92,42,0).
dadosCam_t_e_ta(eTruck02,"A16","A08",67,23,0).
dadosCam_t_e_ta(eTruck02,"A16","A09",15,3,0).
dadosCam_t_e_ta(eTruck02,"A16","A10",105,46,0).
dadosCam_t_e_ta(eTruck02,"A16","A11",57,28,0).
dadosCam_t_e_ta(eTruck02,"A16","A12",130,52,25).
dadosCam_t_e_ta(eTruck02,"A16","A13",61,15,0).
dadosCam_t_e_ta(eTruck02,"A16","A14",141,61,37).
dadosCam_t_e_ta(eTruck02,"A16","A15",69,29,0).
dadosCam_t_e_ta(eTruck02,"A16","A17",65,24,0).

dadosCam_t_e_ta(eTruck02,"A17","A01",128,46,0).
dadosCam_t_e_ta(eTruck02,"A17","A02",42,14,0).
dadosCam_t_e_ta(eTruck02,"A17","A03",40,11,0).
dadosCam_t_e_ta(eTruck02,"A17","A04",42,13,0).
dadosCam_t_e_ta(eTruck02,"A17","A05",34,10,0).
dadosCam_t_e_ta(eTruck02,"A17","A06",82,38,0).
dadosCam_t_e_ta(eTruck02,"A17","A07",74,30,0).
dadosCam_t_e_ta(eTruck02,"A17","A08",29,6,0).
dadosCam_t_e_ta(eTruck02,"A17","A09",69,31,0).
dadosCam_t_e_ta(eTruck02,"A17","A10",55,24,0).
dadosCam_t_e_ta(eTruck02,"A17","A11",69,29,0).
dadosCam_t_e_ta(eTruck02,"A17","A12",80,30,0).
dadosCam_t_e_ta(eTruck02,"A17","A13",82,23,0).
dadosCam_t_e_ta(eTruck02,"A17","A14",90,38,0).
dadosCam_t_e_ta(eTruck02,"A17","A15",53,18,0).
dadosCam_t_e_ta(eTruck02,"A17","A16",67,25,0).

dadosCam_t_e_ta(eTruck03,"A01","A02",122,42,0).
dadosCam_t_e_ta(eTruck03,"A01","A03",122,46,0).
dadosCam_t_e_ta(eTruck03,"A01","A04",151,54,25).
dadosCam_t_e_ta(eTruck03,"A01","A05",147,52,25).
dadosCam_t_e_ta(eTruck03,"A01","A06",74,24,0).
dadosCam_t_e_ta(eTruck03,"A01","A07",116,35,0).
dadosCam_t_e_ta(eTruck03,"A01","A08",141,46,0).
dadosCam_t_e_ta(eTruck03,"A01","A09",185,74,53).
dadosCam_t_e_ta(eTruck03,"A01","A10",97,30,0).
dadosCam_t_e_ta(eTruck03,"A01","A11",164,64,40).
dadosCam_t_e_ta(eTruck03,"A01","A12",76,23,0).
dadosCam_t_e_ta(eTruck03,"A01","A13",174,66,45).
dadosCam_t_e_ta(eTruck03,"A01","A14",59,18,0).
dadosCam_t_e_ta(eTruck03,"A01","A15",132,51,24).
dadosCam_t_e_ta(eTruck03,"A01","A16",181,68,45).
dadosCam_t_e_ta(eTruck03,"A01","A17",128,45,0).

dadosCam_t_e_ta(eTruck03,"A02","A01",116,42,0).
dadosCam_t_e_ta(eTruck03,"A02","A03",55,22,0).
dadosCam_t_e_ta(eTruck03,"A02","A04",74,25,0).
dadosCam_t_e_ta(eTruck03,"A02","A05",65,22,0).
dadosCam_t_e_ta(eTruck03,"A02","A06",69,27,0).
dadosCam_t_e_ta(eTruck03,"A02","A07",74,38,0).
dadosCam_t_e_ta(eTruck03,"A02","A08",61,18,0).
dadosCam_t_e_ta(eTruck03,"A02","A09",103,44,0).
dadosCam_t_e_ta(eTruck03,"A02","A10",36,14,0).
dadosCam_t_e_ta(eTruck03,"A02","A11",88,41,0).
dadosCam_t_e_ta(eTruck03,"A02","A12",61,19,0).
dadosCam_t_e_ta(eTruck03,"A02","A13",95,42,0).
dadosCam_t_e_ta(eTruck03,"A02","A14",78,34,0).
dadosCam_t_e_ta(eTruck03,"A02","A15",69,30,0).
dadosCam_t_e_ta(eTruck03,"A02","A16",99,38,0).
dadosCam_t_e_ta(eTruck03,"A02","A17",46,14,0).

dadosCam_t_e_ta(eTruck03,"A03","A01",120,45,0).
dadosCam_t_e_ta(eTruck03,"A03","A02",50,22,0).
dadosCam_t_e_ta(eTruck03,"A03","A04",46,15,0).
dadosCam_t_e_ta(eTruck03,"A03","A05",46,14,0).
dadosCam_t_e_ta(eTruck03,"A03","A06",74,37,0).
dadosCam_t_e_ta(eTruck03,"A03","A07",63,23,0).
dadosCam_t_e_ta(eTruck03,"A03","A08",38,8,0).
dadosCam_t_e_ta(eTruck03,"A03","A09",84,36,0).
dadosCam_t_e_ta(eTruck03,"A03","A10",59,28,0).
dadosCam_t_e_ta(eTruck03,"A03","A11",61,27,0).
dadosCam_t_e_ta(eTruck03,"A03","A12",67,32,0).
dadosCam_t_e_ta(eTruck03,"A03","A13",67,29,0).
dadosCam_t_e_ta(eTruck03,"A03","A14",82,38,0).
dadosCam_t_e_ta(eTruck03,"A03","A15",34,8,0).
dadosCam_t_e_ta(eTruck03,"A03","A16",80,30,0).
dadosCam_t_e_ta(eTruck03,"A03","A17",36,10,0).

dadosCam_t_e_ta(eTruck03,"A04","A01",149,54,25).
dadosCam_t_e_ta(eTruck03,"A04","A02",65,24,0).
dadosCam_t_e_ta(eTruck03,"A04","A03",46,16,0).
dadosCam_t_e_ta(eTruck03,"A04","A05",27,10,0).
dadosCam_t_e_ta(eTruck03,"A04","A06",103,47,0).
dadosCam_t_e_ta(eTruck03,"A04","A07",55,27,0).
dadosCam_t_e_ta(eTruck03,"A04","A08",36,10,0).
dadosCam_t_e_ta(eTruck03,"A04","A09",50,26,0).
dadosCam_t_e_ta(eTruck03,"A04","A10",78,34,0).
dadosCam_t_e_ta(eTruck03,"A04","A11",42,19,0).
dadosCam_t_e_ta(eTruck03,"A04","A12",97,42,0).
dadosCam_t_e_ta(eTruck03,"A04","A13",44,11,0).
dadosCam_t_e_ta(eTruck03,"A04","A14",111,48,0).
dadosCam_t_e_ta(eTruck03,"A04","A15",32,13,0).
dadosCam_t_e_ta(eTruck03,"A04","A16",53,14,0).
dadosCam_t_e_ta(eTruck03,"A04","A17",38,11,0).

dadosCam_t_e_ta(eTruck03,"A05","A01",141,51,24).
dadosCam_t_e_ta(eTruck03,"A05","A02",55,20,0).
dadosCam_t_e_ta(eTruck03,"A05","A03",48,14,0).
dadosCam_t_e_ta(eTruck03,"A05","A04",25,9,0).
dadosCam_t_e_ta(eTruck03,"A05","A06",97,44,0).
dadosCam_t_e_ta(eTruck03,"A05","A07",55,28,0).
dadosCam_t_e_ta(eTruck03,"A05","A08",29,7,0).
dadosCam_t_e_ta(eTruck03,"A05","A09",48,24,0).
dadosCam_t_e_ta(eTruck03,"A05","A10",69,30,0).
dadosCam_t_e_ta(eTruck03,"A05","A11",53,26,0).
dadosCam_t_e_ta(eTruck03,"A05","A12",95,36,0).
dadosCam_t_e_ta(eTruck03,"A05","A13",63,20,0).
dadosCam_t_e_ta(eTruck03,"A05","A14",105,45,0).
dadosCam_t_e_ta(eTruck03,"A05","A15",34,14,0).
dadosCam_t_e_ta(eTruck03,"A05","A16",46,18,0).
dadosCam_t_e_ta(eTruck03,"A05","A17",27,7,0).

dadosCam_t_e_ta(eTruck03,"A06","A01",69,23,0).
dadosCam_t_e_ta(eTruck03,"A06","A02",71,27,0).
dadosCam_t_e_ta(eTruck03,"A06","A03",74,38,0).
dadosCam_t_e_ta(eTruck03,"A06","A04",103,46,0).
dadosCam_t_e_ta(eTruck03,"A06","A05",99,44,0).
dadosCam_t_e_ta(eTruck03,"A06","A07",88,48,0).
dadosCam_t_e_ta(eTruck03,"A06","A08",92,38,0).
dadosCam_t_e_ta(eTruck03,"A06","A09",134,66,45).
dadosCam_t_e_ta(eTruck03,"A06","A10",42,14,0).
dadosCam_t_e_ta(eTruck03,"A06","A11",116,56,30).
dadosCam_t_e_ta(eTruck03,"A06","A12",23,9,0).
dadosCam_t_e_ta(eTruck03,"A06","A13",126,58,33).
dadosCam_t_e_ta(eTruck03,"A06","A14",25,9,0).
dadosCam_t_e_ta(eTruck03,"A06","A15",84,44,0).
dadosCam_t_e_ta(eTruck03,"A06","A16",132,60,35).
dadosCam_t_e_ta(eTruck03,"A06","A17",80,38,0).

dadosCam_t_e_ta(eTruck03,"A07","A01",116,36,0).
dadosCam_t_e_ta(eTruck03,"A07","A02",71,38,0).
dadosCam_t_e_ta(eTruck03,"A07","A03",61,22,0).
dadosCam_t_e_ta(eTruck03,"A07","A04",53,26,0).
dadosCam_t_e_ta(eTruck03,"A07","A05",53,28,0).
dadosCam_t_e_ta(eTruck03,"A07","A06",88,48,0).
dadosCam_t_e_ta(eTruck03,"A07","A08",59,26,0).
dadosCam_t_e_ta(eTruck03,"A07","A09",88,48,0).
dadosCam_t_e_ta(eTruck03,"A07","A10",84,44,0).
dadosCam_t_e_ta(eTruck03,"A07","A11",74,22,0).
dadosCam_t_e_ta(eTruck03,"A07","A12",82,42,0).
dadosCam_t_e_ta(eTruck03,"A07","A13",76,31,0).
dadosCam_t_e_ta(eTruck03,"A07","A14",97,49,21).
dadosCam_t_e_ta(eTruck03,"A07","A15",29,16,0).
dadosCam_t_e_ta(eTruck03,"A07","A16",84,42,0).
dadosCam_t_e_ta(eTruck03,"A07","A17",69,30,0).

dadosCam_t_e_ta(eTruck03,"A08","A01",134,46,0).
dadosCam_t_e_ta(eTruck03,"A08","A02",59,18,0).
dadosCam_t_e_ta(eTruck03,"A08","A03",32,6,0).
dadosCam_t_e_ta(eTruck03,"A08","A04",34,10,0).
dadosCam_t_e_ta(eTruck03,"A08","A05",32,7,0).
dadosCam_t_e_ta(eTruck03,"A08","A06",88,38,0).
dadosCam_t_e_ta(eTruck03,"A08","A07",57,26,0).
dadosCam_t_e_ta(eTruck03,"A08","A09",69,30,0).
dadosCam_t_e_ta(eTruck03,"A08","A10",65,26,0).
dadosCam_t_e_ta(eTruck03,"A08","A11",53,22,0).
dadosCam_t_e_ta(eTruck03,"A08","A12",82,34,0).
dadosCam_t_e_ta(eTruck03,"A08","A13",61,24,0).
dadosCam_t_e_ta(eTruck03,"A08","A14",97,40,0).
dadosCam_t_e_ta(eTruck03,"A08","A15",36,12,0).
dadosCam_t_e_ta(eTruck03,"A08","A16",65,23,0).
dadosCam_t_e_ta(eTruck03,"A08","A17",32,6,0).

dadosCam_t_e_ta(eTruck03,"A09","A01",181,72,50).
dadosCam_t_e_ta(eTruck03,"A09","A02",95,41,0).
dadosCam_t_e_ta(eTruck03,"A09","A03",86,35,0).
dadosCam_t_e_ta(eTruck03,"A09","A04",55,24,0).
dadosCam_t_e_ta(eTruck03,"A09","A05",48,23,0).
dadosCam_t_e_ta(eTruck03,"A09","A06",134,65,42).
dadosCam_t_e_ta(eTruck03,"A09","A07",95,47,0).
dadosCam_t_e_ta(eTruck03,"A09","A08",69,28,0).
dadosCam_t_e_ta(eTruck03,"A09","A10",109,51,24).
dadosCam_t_e_ta(eTruck03,"A09","A11",61,29,0).
dadosCam_t_e_ta(eTruck03,"A09","A12",132,57,31).
dadosCam_t_e_ta(eTruck03,"A09","A13",67,19,0).
dadosCam_t_e_ta(eTruck03,"A09","A14",143,66,45).
dadosCam_t_e_ta(eTruck03,"A09","A15",71,34,0).
dadosCam_t_e_ta(eTruck03,"A09","A16",15,3,0).
dadosCam_t_e_ta(eTruck03,"A09","A17",67,28,0).

dadosCam_t_e_ta(eTruck03,"A10","A01",97,30,0).
dadosCam_t_e_ta(eTruck03,"A10","A02",34,14,0).
dadosCam_t_e_ta(eTruck03,"A10","A03",59,27,0).
dadosCam_t_e_ta(eTruck03,"A10","A04",78,33,0).
dadosCam_t_e_ta(eTruck03,"A10","A05",71,30,0).
dadosCam_t_e_ta(eTruck03,"A10","A06",40,14,0).
dadosCam_t_e_ta(eTruck03,"A10","A07",82,42,0).
dadosCam_t_e_ta(eTruck03,"A10","A08",65,24,0).
dadosCam_t_e_ta(eTruck03,"A10","A09",109,52,25).
dadosCam_t_e_ta(eTruck03,"A10","A11",92,46,0).
dadosCam_t_e_ta(eTruck03,"A10","A12",32,6,0).
dadosCam_t_e_ta(eTruck03,"A10","A13",99,46,0).
dadosCam_t_e_ta(eTruck03,"A10","A14",63,17,0).
dadosCam_t_e_ta(eTruck03,"A10","A15",74,34,0).
dadosCam_t_e_ta(eTruck03,"A10","A16",105,46,0).
dadosCam_t_e_ta(eTruck03,"A10","A17",53,23,0).

dadosCam_t_e_ta(eTruck03,"A11","A01",164,65,42).
dadosCam_t_e_ta(eTruck03,"A11","A02",88,41,0).
dadosCam_t_e_ta(eTruck03,"A11","A03",65,28,0).
dadosCam_t_e_ta(eTruck03,"A11","A04",42,18,0).
dadosCam_t_e_ta(eTruck03,"A11","A05",55,25,0).
dadosCam_t_e_ta(eTruck03,"A11","A06",118,57,31).
dadosCam_t_e_ta(eTruck03,"A11","A07",74,23,0).
dadosCam_t_e_ta(eTruck03,"A11","A08",59,23,0).
dadosCam_t_e_ta(eTruck03,"A11","A09",63,28,0).
dadosCam_t_e_ta(eTruck03,"A11","A10",97,46,0).
dadosCam_t_e_ta(eTruck03,"A11","A12",111,52,25).
dadosCam_t_e_ta(eTruck03,"A11","A13",25,7,0).
dadosCam_t_e_ta(eTruck03,"A11","A14",126,58,33).
dadosCam_t_e_ta(eTruck03,"A11","A15",53,25,0).
dadosCam_t_e_ta(eTruck03,"A11","A16",59,27,0).
dadosCam_t_e_ta(eTruck03,"A11","A17",67,27,0).

dadosCam_t_e_ta(eTruck03,"A12","A01",76,23,0).
dadosCam_t_e_ta(eTruck03,"A12","A02",61,19,0).
dadosCam_t_e_ta(eTruck03,"A12","A03",67,32,0).
dadosCam_t_e_ta(eTruck03,"A12","A04",97,41,0).
dadosCam_t_e_ta(eTruck03,"A12","A05",92,38,0).
dadosCam_t_e_ta(eTruck03,"A12","A06",19,8,0).
dadosCam_t_e_ta(eTruck03,"A12","A07",82,42,0).
dadosCam_t_e_ta(eTruck03,"A12","A08",86,33,0).
dadosCam_t_e_ta(eTruck03,"A12","A09",128,61,37).
dadosCam_t_e_ta(eTruck03,"A12","A10",32,6,0).
dadosCam_t_e_ta(eTruck03,"A12","A11",109,50,23).
dadosCam_t_e_ta(eTruck03,"A12","A13",120,53,26).
dadosCam_t_e_ta(eTruck03,"A12","A14",40,10,0).
dadosCam_t_e_ta(eTruck03,"A12","A15",78,38,0).
dadosCam_t_e_ta(eTruck03,"A12","A16",126,54,28).
dadosCam_t_e_ta(eTruck03,"A12","A17",74,32,0).

dadosCam_t_e_ta(eTruck03,"A13","A01",174,65,42).
dadosCam_t_e_ta(eTruck03,"A13","A02",107,35,0).
dadosCam_t_e_ta(eTruck03,"A13","A03",74,29,0).
dadosCam_t_e_ta(eTruck03,"A13","A04",46,11,0).
dadosCam_t_e_ta(eTruck03,"A13","A05",67,20,0).
dadosCam_t_e_ta(eTruck03,"A13","A06",128,57,31).
dadosCam_t_e_ta(eTruck03,"A13","A07",80,30,0).
dadosCam_t_e_ta(eTruck03,"A13","A08",76,20,0).
dadosCam_t_e_ta(eTruck03,"A13","A09",67,20,0).
dadosCam_t_e_ta(eTruck03,"A13","A10",105,47,0).
dadosCam_t_e_ta(eTruck03,"A13","A11",27,7,0).
dadosCam_t_e_ta(eTruck03,"A13","A12",122,52,25).
dadosCam_t_e_ta(eTruck03,"A13","A14",137,58,33).
dadosCam_t_e_ta(eTruck03,"A13","A15",67,17,0).
dadosCam_t_e_ta(eTruck03,"A13","A16",59,15,0).
dadosCam_t_e_ta(eTruck03,"A13","A17",78,22,0).

dadosCam_t_e_ta(eTruck03,"A14","A01",59,18,0).
dadosCam_t_e_ta(eTruck03,"A14","A02",80,35,0).
dadosCam_t_e_ta(eTruck03,"A14","A03",80,38,0).
dadosCam_t_e_ta(eTruck03,"A14","A04",109,46,0).
dadosCam_t_e_ta(eTruck03,"A14","A05",105,45,0).
dadosCam_t_e_ta(eTruck03,"A14","A06",27,9,0).
dadosCam_t_e_ta(eTruck03,"A14","A07",97,48,0).
dadosCam_t_e_ta(eTruck03,"A14","A08",99,38,0).
dadosCam_t_e_ta(eTruck03,"A14","A09",143,66,45).
dadosCam_t_e_ta(eTruck03,"A14","A10",61,17,0).
dadosCam_t_e_ta(eTruck03,"A14","A11",122,57,31).
dadosCam_t_e_ta(eTruck03,"A14","A12",42,10,0).
dadosCam_t_e_ta(eTruck03,"A14","A13",132,58,35).
dadosCam_t_e_ta(eTruck03,"A14","A15",90,44,0).
dadosCam_t_e_ta(eTruck03,"A14","A16",139,61,37).
dadosCam_t_e_ta(eTruck03,"A14","A17",86,38,0).

dadosCam_t_e_ta(eTruck03,"A15","A01",132,51,24).
dadosCam_t_e_ta(eTruck03,"A15","A02",74,30,0).
dadosCam_t_e_ta(eTruck03,"A15","A03",34,8,0).
dadosCam_t_e_ta(eTruck03,"A15","A04",36,12,0).
dadosCam_t_e_ta(eTruck03,"A15","A05",36,14,0).
dadosCam_t_e_ta(eTruck03,"A15","A06",86,44,0).
dadosCam_t_e_ta(eTruck03,"A15","A07",34,16,0).
dadosCam_t_e_ta(eTruck03,"A15","A08",42,13,0).
dadosCam_t_e_ta(eTruck03,"A15","A09",71,35,0).
dadosCam_t_e_ta(eTruck03,"A15","A10",82,36,0).
dadosCam_t_e_ta(eTruck03,"A15","A11",53,25,0).
dadosCam_t_e_ta(eTruck03,"A15","A12",80,38,0).
dadosCam_t_e_ta(eTruck03,"A15","A13",69,18,0).
dadosCam_t_e_ta(eTruck03,"A15","A14",95,45,0).
dadosCam_t_e_ta(eTruck03,"A15","A16",69,29,0).
dadosCam_t_e_ta(eTruck03,"A15","A17",53,17,0).

dadosCam_t_e_ta(eTruck03,"A16","A01",179,68,45).
dadosCam_t_e_ta(eTruck03,"A16","A02",92,37,0).
dadosCam_t_e_ta(eTruck03,"A16","A03",84,31,0).
dadosCam_t_e_ta(eTruck03,"A16","A04",57,16,0).
dadosCam_t_e_ta(eTruck03,"A16","A05",46,18,0).
dadosCam_t_e_ta(eTruck03,"A16","A06",132,60,35).
dadosCam_t_e_ta(eTruck03,"A16","A07",92,42,0).
dadosCam_t_e_ta(eTruck03,"A16","A08",67,23,0).
dadosCam_t_e_ta(eTruck03,"A16","A09",15,3,0).
dadosCam_t_e_ta(eTruck03,"A16","A10",105,46,0).
dadosCam_t_e_ta(eTruck03,"A16","A11",57,28,0).
dadosCam_t_e_ta(eTruck03,"A16","A12",130,52,25).
dadosCam_t_e_ta(eTruck03,"A16","A13",61,15,0).
dadosCam_t_e_ta(eTruck03,"A16","A14",141,61,37).
dadosCam_t_e_ta(eTruck03,"A16","A15",69,29,0).
dadosCam_t_e_ta(eTruck03,"A16","A17",65,24,0).

dadosCam_t_e_ta(eTruck03,"A17","A01",128,46,0).
dadosCam_t_e_ta(eTruck03,"A17","A02",42,14,0).
dadosCam_t_e_ta(eTruck03,"A17","A03",40,11,0).
dadosCam_t_e_ta(eTruck03,"A17","A04",42,13,0).
dadosCam_t_e_ta(eTruck03,"A17","A05",34,10,0).
dadosCam_t_e_ta(eTruck03,"A17","A06",82,38,0).
dadosCam_t_e_ta(eTruck03,"A17","A07",74,30,0).
dadosCam_t_e_ta(eTruck03,"A17","A08",29,6,0).
dadosCam_t_e_ta(eTruck03,"A17","A09",69,31,0).
dadosCam_t_e_ta(eTruck03,"A17","A10",55,24,0).
dadosCam_t_e_ta(eTruck03,"A17","A11",69,29,0).
dadosCam_t_e_ta(eTruck03,"A17","A12",80,30,0).
dadosCam_t_e_ta(eTruck03,"A17","A13",82,23,0).
dadosCam_t_e_ta(eTruck03,"A17","A14",90,38,0).
dadosCam_t_e_ta(eTruck03,"A17","A15",53,18,0).
dadosCam_t_e_ta(eTruck03,"A17","A16",67,25,0).