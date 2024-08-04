# Análise

### Formato Breve
 Como gestor de logística pretendo filtrar e ordenar as viagens pelas suas caraterísticas.

### Pré-condições
O Gestor de Logística deve estar autenticado no sistema.
Existirem Viagens no sistema.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O Gestor de Logística clica na opção de Listar Viagens.
O sistema informa ao Gestor de Armazém os Viagens existentes.
É possível filtrar e ordenador uma qualquer Viagem da listagem.

# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http GET (listagem das Viagens)

#### Dados a serem enviados
* Enviados através de JSON, as informações dos campos serão enviados.

#### Dados a serem recebidos

* Alerta contendo o sucesso/insucesso da operação.

### Endereço de endpoint

##### Inibir Armazém

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/listar-viagens

* Para ambiente Deployed
    - https://eletricgo.netlify.app/listar-viagens
