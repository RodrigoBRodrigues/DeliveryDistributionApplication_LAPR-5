# Análise

### Formato Breve
Como Gestor de Armazém pretendo Listar Entregas.

### Pré-condições
O Gestor de Armazém deve estar autenticado no sistema.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O Gestor de Armazém clica na opção de Listar Entregas.
O sistema informa ao Gestor de Armazém os Entregas existentes.
Existe filtro de pesquisa por Datas ou Armazém de Chegada.

# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http GET

#### Dados a serem enviados
* Enviados através de JSON, as informações dos filtros serão enviados.

#### Dados a serem recebidos

* Informação das Entregas

### Endereço de endpoint

##### Listar Entregas

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/listar-entregas

* Para ambiente Deployed
    - https://????/listar-entregas
