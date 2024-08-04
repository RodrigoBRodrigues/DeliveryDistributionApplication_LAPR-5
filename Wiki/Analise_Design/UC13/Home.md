# Análise

### Formato Breve
Como gestor de armazém pretendo Criar um Armazém.

### Pré-condições
O gestor de armazém deve estar autenticado no sistema.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O gestor de armazém clica na opção de criar um novo armazém e preenche os campos com a informação.
O sistema informa ao gestor de armazém que o armazém foi criado.
Em caso de insucesso, o sistema informa qual parâmetros estão incorretos.

# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http POST

#### Dados a serem enviados
* Enviados através de JSON, as informações dos campos serão enviados.

#### Dados a serem recebidos

* Alerta contendo o sucesso/insucesso da operação.

### Endereço de endpoint

##### Criar Armazém

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/criar-armazem

* Para ambiente Deployed
    - https://????/criar-armazem
