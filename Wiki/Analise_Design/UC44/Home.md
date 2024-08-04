# Análise

### Formato Breve
US44 - Como gestor de logística pretendo uma solução para o problema usando Algoritmos Genéticos

### A Fazer
Adaptar algoritmo genético fornecido para o problema atual e obter uma solução.

### Adaptação Genérica

• Garantir que, pelo menos, o melhor Indivíduo entre apopulação corrente e a nova população gerada passa para a população seguinte.
• Evitar que a sequência de cruzamentos se dê sempre entre 1º e 2º elementos da população, depois entre 3º e 4º,5º e 6º.
• Aplicar um método de seleção que não seja puramente elitista, dando alguma probabilidade de um indivíduo com pior avaliação possa passar à geração seguinte, mesmo não estando entre os DP (dimensão da população) melhor avaliados da população anterior e dos seus descendentes.
• A condição de término poder ser diferente (para além de ser por nº de gerações podemos considerar outras possibilidades tais como tempo, estabilização da solução, etc).

Gene -> Armazém
Indivíduo -> Rota dos Armazém para Entrega
População -> Conjunto de Rotas

• O problema de sequenciamento de entregas é muito similar ao do sequenciamento de tarefas.
• Muda é a avaliação da solução, que no caso ilustrado era um somatório dos atrasos pesados e no caso da sequência de entregas foi feito no sprint anterior, considerando diversos tipos de tempos (deslocação, descarga demercadoria, carga de baterias e cargas adicionaisao meio das deslocações).
• Teremos de mudar a parte de avaliação dos indivíduos da população.

### Relatório

2) Criação da população inicial do Algoritmo Genético (AG) considerando 2 indivíduos (sequência das entregas) diferentes gerados com 2 heurísticas diferentes que possam criar boas soluções segundo as adotadas no sprint anterior. Caso os 2 indivíduos referidos sejam iguais poderá efetuar a transformação de um deles, por exemplo trocando a ordem de 2 genes (entregas) consecutivos. Para além dos 2 elementos referidos os restantes indivíduos da população deverão ser gerados aleatoriamente. Ter em conta que não deverá haver indivíduos repetidos numa geração da população.
3) Aleatoriedade no cruzamento entre indivíduos da população, evitando que a sequência de cruzamentos se dê sempre entre 1º e 2º elementos da população, depois entre 3º e 4º , 5º e6º, etc.
4) Seleção da nova geração da população tomando como base a população que se sujeitou aos cruzamentos e os seus descendentes obtidos por cruzamento e sujeitos à mutação. Ter em conta que a mutação poderá alterar um descendente que até pode ser o melhor indivíduo até ao momento. Deverá ser garantida a passagem para a população seguinte dos 2 melhores indivíduos entre os que estão no conjunto da geração corrente, dos seus descendentes obtidos por cruzamento e dos descendentes sujeitos à mutação. Os restantes indivíduos a considerar para a geração seguinte deverão resultar de torneios onde seja dada a oportunidade dequalquer indivíduo entre os restantes ter a possibilidade passar para a geração seguinte,embora em cada torneio haja maior probabilidade do melhor passar.
5) Análise de eficácia (obtenção de um melhor indivíduo) do AG criado (assumindo um mesmonº de gerações) quando comparado com a versão inicial do AG por comparação entre o melhor indivíduo da população final de ambos AG e do valor médio das avaliações de todos osindivíduos da população final em ambos AG. Para problemas de dimensão entre 6 e 12 entregas comparar a solução obtida pelo AG com a melhor solução do problema que poderá ser obtida através de um algoritmo que gere todas as soluções e as avalie (como foi feito no Sprint B),escolhendo a melhor. Expresse a comparação através de uma tabela com 6 colunas (nº de entregas – de 6 a 12, valor da melhor solução total do problema; valor da melhor solução/indivíduo da população final do AG desenvolvido; valor da melhor solução/indivíduo dapopulação final do AG base; valor médio de todas as soluções/indivíduos da população final do AG desenvolvido; valor médio de todas as soluções/indivíduos da população final do AG base.6) Parametrização da condição de término do AG segundo 2 condições de término diferentes: nº de gerações e { obtenção de um indivíduo com valor menor ou igual a um valorindicado ou estabilização da população}.

### Planeamento Rota com Algortimo Genético

* Para ambiente Dev
    - http://localhost:4200/planeamento-rota

* Para ambiente Deployed
    - https://????/planeamento-rota