# Análise

### Formato Breve
Como Gestor de Armazém pretendo Listar Armazéns.

### Pré-condições
O Gestor de Armazém deve estar autenticado no sistema.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O Gestor de Armazém clica na opção de Listar Armazéns.
O sistema informa ao Gestor de Armazém os Armazéns existentes.
Existe filtro de pesquisa por Id ou Designação.

# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http GET

#### Dados a serem enviados
* Enviados através de JSON, as informações dos campos serão enviados.

#### Dados a serem recebidos

* Alerta contendo o sucesso/insucesso da operação.

### Endereço de endpoint

##### Listar Armazéns

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/listar-armazens

* Para ambiente Deployed
    - https://????/listar-armazens
