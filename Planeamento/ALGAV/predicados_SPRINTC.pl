:- consult('./baseConhecimento/entregas_ex5').
:- consult('./baseConhecimento/factos_camiao').
:- consult('./baseConhecimento/id_armazem').
:- dynamic custo_min/3.
:- dynamic data/1.
:- dynamic camiao/1.

permutacao(L,R):- permutation(L,L1), tempoListaEntregas(L1,R).

%US2 a soma_pesos
soma_pesos([],[],0).

soma_pesos([5|LC],[PesoAc|LP],PesoAc):-
soma_pesos(LC, LP, PesoAc).

soma_pesos([Cidade|LC],[PesoAc|LP],PesoAc):-
data(Data),
soma_pesos(LC,LP,PesoAc1),entrega(_, Data, Peso, Cidade, _, _), PesoAc is Peso+PesoAc1.


%US2b acrescenta_tara
acrescenta_tara(Tara,[],[Tara]).
acrescenta_tara(Tara,[Peso|LP],[PesoTara|LPT]):-
acrescenta_tara(Tara,LP,LPT),
PesoTara is Peso+Tara.


%c calcula_custo
calcula_custo(LC, Custo, LCcarregamentos):-
idArmazem('Matosinhos', 5),
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
calculosRota(L,_,D):- massaTotalRota(L,S,D).

% Começamos por Obter a Lista dos Armazéns com Entregas no Dia 
% Obtemos o Id do Armazém Principal, neste caso Matosinhos
% maisProximoII
% Inserimos o Armazem Principal na Rota 
% Calculamos a Massa e o Tempo Total das Entregas
maisProximo(Dia,Camiao,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem('Matosinhos',Id),
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
maiorMassa(Dia,_,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem('Matosinhos',Id),
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
combinadoTempoMassa(Dia,Camiao,RotaFinal) :- armazensComEntregas(Dia,Lista), idArmazem('Matosinhos',Id),
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