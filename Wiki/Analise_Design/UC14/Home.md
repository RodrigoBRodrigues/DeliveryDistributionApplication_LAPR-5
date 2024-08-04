# Análise


### Formato Breve
Como gestor de armazém pretendo Criar uma Entrega.

### Pré-condições
O gestor de armazém deve estar autenticado no sistema. Ter armazéns criados no sistema.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O gestor de armazém clica na opção de criar uma nova entrega e preenche os campos com a informação.
O sistema informa ao gestor de armazém que a entrega foi criada.



# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http GET

#### Dados a serem enviados
* Enviados através de JSON, as informações dos campos serão enviados.

#### Dados a serem recebidos

* Alerta contendo o sucesso/insucesso da operação.

### Endereço de endpoint

##### Criar Entrega

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/criar-entrega

* Para ambiente Deployed
    - https://????/criar-entrega
