:- consult('./baseConhecimento/entregas_ex1').
:- consult('./baseConhecimento/factos_camiao').
:- consult('./baseConhecimento/id_armazem').
:- dynamic custo_min/3.
:- dynamic data/1.

permutacao(L,R):- permutation(L,L1), tempoListaEntregas(L1,R), write('Percurso: '), write(L1), write(' - '), write(R), write(' min'),nl.

%US2 a soma_pesos
soma_pesos([],[],0).

soma_pesos([5|LC],[PesoAc|LP],PesoAc):-
soma_pesos(LC, LP, PesoAc).

soma_pesos([Cidade|LC],[PesoAc|LP],PesoAc):-
data(Data),
soma_pesos(LC,LP,PesoAc1),entrega(_, Data, Peso, Cidade, _, _), PesoAc is Peso+PesoAc1.

/*
soma_pesos([1,9,3,8,11],LPesos,PesoTotal).
*/

%US2b acrescenta_tara
acrescenta_tara(Tara,[],[Tara]).
acrescenta_tara(Tara,[Peso|LP],[PesoTara|LPT]):-
acrescenta_tara(Tara,LP,LPT),
PesoTara is Peso+Tara.

/*
acrescenta_tara(6,[9.0, 8.5, 6.7, 5.7, 3.2, 2],LPesosTara).
*/


%c calcula_custo
calcula_custo(LC, Custo, LCcarregamentos):-
idArmazem('Matosinhos', 5),
carateristicasCam(_, _, _, Carga_Total_Baterias, _, _),
custo(LC, Carga_Total_Baterias, Custo, LCcarregamentos).

custo([_], _, 0, []).
custo([C1,C2|LC], Carga_Atual_Baterias, Custo, LCcarregamentos):-
dadosCam_t_e_ta(_,C1,C2,Tempo, Energia, TempoAdicional),
carateristicasCam(_, Tara, Capacidade_Carga, Carga_Total_Baterias, _, _),
soma_pesos([C1,C2|LC], LPesos, _),
acrescenta_tara(Tara, LPesos, [PesoComCarga|_]),
calcular_carga_baterias(Energia, PesoComCarga, Tara, Capacidade_Carga, CargaNecessariaProximaViagem),

((Carga_Atual_Baterias < CargaNecessariaProximaViagem, !, carateristicasCam(_, _, _, _, _, TCarga),
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


%US2d seq_custo_min

/*
calcula_custo([1,9,3,8,11], Custo). [1,3,8,9,11]
*/

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
((Custo<CustoMin,!,retract(custo_min(_,_,_)),assertz(custo_min(LCPerm, LCcarregamentos, Custo)),
write('Tempo='),write(Custo), write(' '),write(LCPerm), write(' com carregamentos em '),write(LCcarregamentos),nl);true).
% o write(Custo),nl so para ver a atualizacao do menor custo    

melhorEntregaFindAll(Data):-retractall(custo_min(_, _ ,_)), assertz(custo_min(_, _,100000)), retractall(data(_)), assertz(data(Data)), 
encontrarTrajetos(Data, LP), melhorEntregaFindAllz(LP).

melhorEntregaFindAllz([]):-!.
melhorEntregaFindAllz([H | L]):-
append([5 | H] , [5] , ListaComMatosinhos),
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
calculosRota(L,_,D):- massaTotalRota(L,S,D), write('Massa Total das Entregas = '),write(S),nl.

% Começamos por Obter a Lista dos Armazéns com Entregas no Dia 
% Obtemos o Id do Armazém Principal, neste caso Matosinhos
% maisProximoII
% Inserimos o Armazem Principal na Rota 
% Calculamos a Massa e o Tempo Total das Entregas
maisProximo(Dia,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem('Matosinhos',Id),
                write('Tempo Entre Armazens = '),maisProximoII(Lista,Rota,Id),insereArmazemInicialFinal(Id,Rota,RotaFinal),
                nl,write('Percurso = '),write(RotaFinal),write('\n'),
                calculosRota(RotaFinal,_,Dia),!.

% Define o Armazem de Partida com para comparar o Tempo com os restantes Armazéns de forma a receber o mais próximo.
% Recebe o Armazém mais próximo e apaga da lista e coloca esse Armazém como de Partida e repete o processo. 
% <Lista>, <Lista com ArmazemInicial>, <ArmazemInicial> 
maisProximoII([],[ArmazemInicial],ArmazemInicial):-!.
maisProximoII(Lista,[ArmazemInicial|L1],ArmazemInicial):- maisProximoIII(ArmazemInicial,Lista,ArmazemProximo,T),
    write(T),write(' '),
                delete(Lista,ArmazemProximo,L2), maisProximoII(L2,L1,ArmazemProximo).                

% Compara o Armazém de Partida com todos os restantes, e retorna o Armazém mais Próximo. 
% <ArmazemI>, <Lista>, <ArmazemProximo>, <Tempo>
maisProximoIII(_,[],_,0):- !.
maisProximoIII(ArmazemI,[H|T],ArmazemProximo, Tempo) :- dadosCam_t_e_ta(_,ArmazemI,H,T1,_,_), maisProximoIII(ArmazemI,T,A1,T2),
                        ((T2==0,!,Tempo is T1,ArmazemProximo = H);
                        (T1<T2,!,Tempo is T1, ArmazemProximo = H);
                        Tempo is T2,ArmazemProximo = A1).
                        

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

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
maiorMassa(Dia,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem('Matosinhos',Id),
                write('Massa Entrega Entre Armazens = '),maiorMassaII(Lista,Rota,Id,Dia),insereArmazemInicialFinal(Id,Rota,RotaFinal),
                nl,write('Percurso = '),write(RotaFinal),write('\n'),
                calculosRota(RotaFinal,_,Dia),!.

% Define o Armazem de Partida com para comparar o Tempo com os restantes Armazéns de forma a receber o mais próximo.
% Recebe o Armazém cuja Entrega tem maior Massa no dado dia 
% Apaga da lista e coloca esse Armazém como de Partida e repete o processo. 
% <Lista>, <Lista com ArmazemInicial>, <ArmazemInicial> 
maiorMassaII([],[ArmazemInicial],ArmazemInicial,_):-!.
maiorMassaII(Lista,[ArmazemInicial|L1],ArmazemInicial,Dia):- maiorMassaIII(ArmazemInicial,Lista,ArmazemProximo,T,Dia),
    write(T),write(' '),
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
combinadoTempoMassa(Dia,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem('Matosinhos',Id),
                write('Custo Entre Armazens = '),combinadoTempoMassaII(Lista,Rota,Id),insereArmazemInicialFinal(Id,Rota,RotaFinal),
                nl,write('Percurso = '),write(RotaFinal),write('\n'),
                calculosRota(RotaFinal,_,Dia),!.

% Define o Armazem de Partida com para comparar o Tempo com os restantes Armazéns de forma a receber o mais próximo.
% Recebe o Armazém mais próximo e apaga da lista e coloca esse Armazém como de Partida e repete o processo. 
% <Lista>, <Lista com ArmazemInicial>, <ArmazemInicial> 
combinadoTempoMassaII([],[ArmazemInicial],ArmazemInicial):-!.
combinadoTempoMassaII(Lista,[ArmazemInicial|L1],ArmazemInicial):- combinadoTempoMassaIII(ArmazemInicial,Lista,ArmazemProximo,T),
    write(T),write(' '),
                delete(Lista,ArmazemProximo,L2), combinadoTempoMassaII(L2,L1,ArmazemProximo).                

% Compara o Armazém de Partida com todos os restantes, e retorna o Armazém mais Próximo. 
% <ArmazemI>, <Lista>, <ArmazemProximo>, <Tempo>
combinadoTempoMassaIII(_,[],_,0):- !.
combinadoTempoMassaIII(ArmazemI,[H|T],ArmazemProximo, Tempo) :- entrega(_,Dia,Massa,H,_,_), dadosCam_t_e_ta(_,ArmazemI,H,T1,_,_), combinadoTempoMassaIII(ArmazemI,T,A1,T2),
                        ((T2==0,!,Tempo is T1 * Massa,ArmazemProximo = H);
                        (T1<T2,!,Tempo is T1 * Massa, ArmazemProximo = H);
                        Tempo is T2,ArmazemProximo = A1).