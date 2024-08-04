# Análise

### Formato Breve
US38 - Como gestor de logística pretendo obter o Planeamento de rota de toda a frota e visualização da melhor solução usando algoritmo genético.

### Pré-condições
Existirem entregas e camião de forma a permitir calcular ab rota a realizar.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O Gestor de Logística clica na opção de Planeamento Rota.
Seleciona dia e camião e obtém a rota a realizar e as entregas do dia.

### Requisitos Funcionais

#### Tipo de requisição
* Http GET (receber planeamento)
* Http POST (executar planeamento)

#### Dados a serem enviados
* Enviados através de JSON, camiao e dia de viagem.

#### Dados a serem recebidos

* Rota gerada através do algoritmo genético.

### Endereço de endpoint

##### Inibir Armazém

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/planeamento-rota

* Para ambiente Deployed
    - https://eletricgo.netlify.app/planeamento-rota