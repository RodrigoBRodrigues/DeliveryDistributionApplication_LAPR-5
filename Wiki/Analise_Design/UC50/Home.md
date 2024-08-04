# Análise

### Formato Breve
Como gestor de armazéns, pretendo inibir um armazém.

### Pré-condições
O Gestor de Armazém deve estar autenticado no sistema.
Existirem Armazéns no sistema.

### Fórum Cliente

https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=19869
https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=19942
https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=19987

### Pós-condições
n/a

### Cenário de sucesso principal (ou fluxo básico)
O Gestor de Armazém clica na opção de Listar Armazéns.
O sistema informa ao Gestor de Armazém os Armazéns existentes.
É possível inibir/desinibir um qualquer Armazém da listagem.

# Design

### Requisitos Funcionais

#### Tipo de requisição
* Http GET (listagem dos Armazéns)
* Http PUT (alterar inibição do Armazém)

#### Dados a serem enviados
* Enviados através de JSON, as informações dos campos serão enviados.

#### Dados a serem recebidos

* Alerta contendo o sucesso/insucesso da operação.

### Endereço de endpoint

##### Inibir Armazém

* Para ambiente dev, usa-se esses dois urls
    - http://localhost:4200/listar-armazens

* Para ambiente Deployed
    - https://????/listar-armazens
