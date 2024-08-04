# Análise


### Formato Breve
Como gestor de logística pretendo obter o Planeamento da Rota  para 1 Camião e 1 dado dia.  

### Pré-condições
O gestor de logística deve estar autenticado no sistema. Existir Percursos, Camiões e Entregas no sistema e ligação ao Módulo Planeamento.

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O gestor de logística clica na opção de obter Planeamento de Rota e insere o dia e o camião de forma que o planeamento seja realizado.
O sistema informa ao gestor de logística que o planeamento de rota para o camião no dia inserido.

# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http GET

#### Dados a serem enviados
* Enviados através de JSON, as informações dos campos serão enviados.

#### Dados a serem recebidos

* Alerta contendo o sucesso/insucesso da operação.

### Endereço de endpoint

##### Planeamento Rota

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/planeamento-rota

* Para ambiente Deployed
    - https://????/planeamento-rota
