# Architecture Background

Foi utilizada uma arquitetura baseada em Onion, visto que é a mais fácil de *aumentar* no futuro, à medida que o sistema evolua ou haja a necessidade para tal.

Esta arquitetura foi aplicada em ambos os módulos MDAE e MDLO. 
Futuramente será abordado a arquitetura usada na aplicação SPA(Visualização).
## Problem Background

### System Overview

A empresa EletricAcme pretende um protótipo para um sistema de gestão de logística, baseado na visualização e manipulação de rotas que permitam alta performance, em termos de eficiência.



### Context

A gestão de logística é um tema bastante recorrente da Indústria 4.0 e a criação deste sistema oferece uma mais valia à equipa.

NB: Entretanto nesta edição do 5º semestre será solicitado um protótipo de um sistema de gestão de logísitca devido as limitações da unidade curricular e que assim, o projeto seja possível de desenvolver.

Este SAD serve de base para debate sobre o sistema a construir (a implementar, testar e implantar), e pretende-se que esteja alinhado com o sistema construído. Para além do óbvio na descrição duma arquitetura de software, deve identificar alternativas de design e ponto de variação.


### Driving Requirements
> This section lists the functional requirements, quality attributes and design constraints. It may point to a separate requirements document.

#### Functional Requirements
(Casos de uso, a completar)

* UC 1: Como Gestor de Armazém, quero criar um Armazém.
* UC 2: Como Gestor de Armazém, quero listar um Armazém.
* UC 3: Como Gestor de Armazém, quero editar um Armazém.
* UC 4: Como Gestor de Armazém, quero criar uma Entrega.
* UC 5: Como Gestor de Armazém, quero listar uma Entrega.
* UC 6: Como Gestor de Armazém, quero editar uma Entrega.
* UC 7: Como Gestor de Logística, quero criar um Camião.
* UC 8: Como Gestor de Logística, quero listar um Camião.
* UC 9: Como Gestor de Logística, quero editar um Camião.
* UC 10: Como Gestor de Logística, quero criar um Camião.
* UC 11: Como Gestor de Logística, quero listar um Camião.
* UC 12: Como Gestor de Logística, quero editar um Camião.


### Quality attributes

Os atributos de qualidade são categorizados e sistematizados segundo o modelo [FURPS+](https://pt.wikipedia.org/wiki/FURPS).

#### **Funcionalidade**
1. Cada sistema só poderá aceder aos dados que lhe dizem respeito.
2. Deve ser auditada e verificada a integridade da informação a que os sistemas acedem.
3. Com vista à necessidade de saber e necessidade de conhecer, toda a informação deve estar protegida de acessos indevidos. Ou seja, o princípio de minimização de acesso ao que é essencial para cada utilizador/aplicação, criação de túneis para transferência de informação, avaliação da integridade de dados e aplicações, e encriptação/minimização dos dados.
4. Uma vez que o módulo do SPA se encontra virado para o exterior, é necessário ter especial atenção com a privacidade e proteção de dados à luz do RGPD. Assim é necessário que o sistema cumpra a legislação em vigor e, em especial, disponibilize as informações legais e informe o utilizador aquando do seu registo, bem como permita aceder e cancelar a sua conta nos casos e nas condições legalmente permitidas.

#### **Usabilidade**
1. A SPA deve permitir acesso a todos os módulos do sistema: master data armazém e entrega, master data logística e visualização.
2.  No âmbito do projeto atual, a administração de utilizadores pode ser efetuada diretamente na base de dados não sendo necessário um módulo de gestão de utilizadores.

#### **Confiabilidade (Reliability)**
7. O sistema deve estar operacional 24 horas por dia, isto é, o tempo de paragem deve ser nulo, salvo algum problema ou perda dos serviços externos ao sistema, como é o caso das bases de dados (MongoDB, Azure Server).
8. A estimativa de tempo, no qual o sistema deve estar operacional, é decisão do cliente. O sistema está preparado para existir perpetuamente.

#### **Desempenho (Performance)**
9. Os módulos MDAE e MDLO têm um tempo de resposta quase instantâneo.

#### **Suportabilidade**
12. Embora não esteja no âmbito atual do projeto, deve ser levado em conta na arquitetura da solução, a extensão futura para aplicações móveis.

--------------------

### **Design Constraints**
1. Não foi solicitado pelo cliente um padrão de design para seguir.

### **Implementation constraints**
1. Todos os módulos devem fazer parte do código fonte da mesma SPA e serem disponibilizados como um único artefacto.

### **Interface constraints**
1.   A SPA deve permitir acesso a todos os módulos do sistema: master data armazém e entrega, master data logística e visualização.
2.   O módulo de logística deve consumir dados de rede através da API do planeamento e entrega.
4.   O módulo de logística deve consumir dados de rede através da API do master data armazém e entrega.
5.   O módulo de planeamento deve consumir dados de rede através da API do master data armazém e entrega.
5.   O módulo de planeamento deve consumir dados de rede através da API do master data logística.
6.   O módulo de SPA deve consumir dados de rede através da API do master data logística.
7.   O módulo de SPA deve consumir dados de rede através da API do master data armazém e entrega.

### **Physical constraints**
1.  Existem dois servidores em load balancing, onde estão instaladas as aplicações, serviços e as bases de dados e que se encarregam do armazenamento da informação.
2.  Existem ainda dois servidores em failover que distribuem os endereços a todos os sistemas e se encarregam da autenticação de sistemas e utilizadores (DHCP, DNS (se aplicável) e autenticação de servidores, e eventualmente um servidor Kerberos).
3.  Na demonstração do protótipo é obrigatório recorrer aos servidores virtuais criados para o efeito.
4.  O Serviço MDAE será hospedado em servidores Azure.

------------------

### **Principais Funcionalidades**

De um modo geral, as principais funcionalidades de cada módulo são as seguintes:

- **Master Data Armazém Entrega** – permite a gestão da informação relacionada com os armazéns (localização, designação) e entregas, estando estas associadas aos armzéns.
- **UI** – interface com o utilizador.
- **Analisador de Rede Social** – permite a gestão da informação relacionada à logística, nomeadamente os camiões (características, designação, etc) e e a definição dos percursos entre 2 armazéns.
- **Visualizador 3D/2D** –  permite a visualização 2D e 3D da rede, a navegação pela cena e a consulta gráfica de informação sobre as viagens. Consome a informação gerida no módulo master data armazém entrega e no módulo logística.

No âmbito do projeto atual, a administração de utilizadores pode ser efetuada diretamente na base de dados não sendo necessário um módulo de gestão de utilizadores.

-------------------

### **Architectural Approaches**

Baseado nos requisitos não funcionais e restrições de design, serão adotadas as seguintes abordagens/padrões/estilos:

- Client-Server, porque cada um dos "módulos" MDLO, MDAE, Planeamento são aplicações servidoras de outras aplicações clientes;
- Web Application, em que o frontend é desempenhado por uma SPA (Single Page Application), e que o backend é desempenhado pelos módulos MDLO, MDAE, Planeamento;
- SOA, porque os servidores (cf. anterior) deverão disponibilizar APIs, e particularmente APIs para serem usadas na web, disponibilizando serviços para os clientes respetivos. Serão adotados os nível 0, 1 e 2 do [Modelo de Maturidade de Richardson](https://martinfowler.com/articles/richardsonMaturityModel.html) aplicado a REST;
- N-Tier, pois as várias aplicações devem ser implantadas em diferentes máquinas *on premises* e IaaS e PaaS (*on cloud*), de acordo com os requisitos não funcionais;
- Layered architecture, mais especificamente Onion Architecture, por razões académicas.
- SPA a definir.

Outras abordagens/estilos/padrões, como por exemplo a interligação entre aplicações baseado em mensagens-eventos foram desconsideradas para não violar os requisitos e restrições definidos, mas também por questões académicas.

### **Analysis Results**
Não existem por agora resultados de análise ou avaliação. Estudos qualitativos acerca dos estilos/padrões adotados (nomeadamente Onion em MDLO e MDAE, mas também Dependency Injection na UI), permitem empiricamente advogar que a manutenibilidade, evolutabilidade e testabilidade do software são elevadas, ao mesmo tempo que permitem atingir as funcionalidades desejadas.