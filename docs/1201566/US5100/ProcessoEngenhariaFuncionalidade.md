# US5100
=======================================


# 1. Requisitos

**As Project Manager, I want that the team to develop and integrate the others components/parts of the AGV digital twin (e.g.: movement, obstacle sensors, control unit).**

Sockets serão utilizados

# 2. Análise

O AGV irá atuar ser um client e irá receber dados da warehouse e realizar tarefas de acordo com as guidelines

**Dependência(s)**

É necessario que o AGVManager esteja pronto e o client do digital twin tambem.

**Fluxo Básico**

1. O digital twin é ligado

- 2. Recebe order do agvmanager

- 3. Prepara a order e aguarda mais orders

**Esclarecimento(s) do Cliente**




 It is NOT foreseen that AGVs communicate between them. However, each AGV can by its own initiative communicate with the AGV Manager. Notice that in your picture this possibility is not depicted. However, this possibility is depicted on Figure 2 of the specifications' document.

 Your question is related to data/information flow. Such flow must be in accordance with Figure 2 of the specifications' document. Notice that, the dashboard shows the current position of each AGV. If the AGVs position is changing then by refreshing the dashboard is enough to show AGV movement.

* Q:the statement says that an AGV has a total of 8 sensors, 2 in each corner of the AGV. Therefore, some doubts have arisen, such as:
What is the difference between having one or two sensors, in each corner? How do we differentiate between them? Are they in the same position?

* A:Each sensor is a source of information to signal (or not) the presence of an obstacle (e.g.: another AGV, an aisle, etc.).
In each corner there is a sensor to evaluate obstacles in the front and another sensor  to evaluate obstacles on the side.


* Q:If two AGVs are 1 square close to each other, they must stop, but what needs to be done next? Create a new route to take the assigned task? And if an AGV passes one block away from another and never collides, should we ignore the "AGV must stop" sentence, or should he stop the AGV and move the AGV again?

* A:The overall idea is to avoid collision between AGVs.
As so, the "must stop" aims to avoid an imminent collision. In such case, new routes should be computed in order to complete the task that in  each AGV has in hands. The strategy for that it is up to each team to decide.


* Q:Referring to the documentation, it is mentioned that the Route Planner module of the AGV Digital twin is responsible for "... (re)computing routes based on a source and target location on thewarehouse considering the warehouse plant only. It is worth notifying that AGV can only movehorizontally or vertically".
What do you mean by source and target location of agv ? We can set agvdock as a starting point but what would be the end point?

* A:When assigning a task to an AGV, the AGV knows which products to collect, right?

So, the source location (starting point) is the position where the AGV is at that moment (as you said, it might be the AGV dock)

The target location (end point) might be the location of a product.

However,  there are other possibilities.

For instance, consider the scenario where the AGV has to collect 2 products (say A and B).

At least three routes have to be computed:

1. From AGV Dock location to the location of product A.

2. From location of product A to the location of product B.

3. From location of product B to the AGV dock location.

## 2.1 Futuras implementações para os outros atores

* Não existem.

## 2.2 Sequência das ações

* O AGV Digital Twin será ligado ao server do AGV M

## 2.3 Regras de negócio associadas aos atributos de uma category.

* Respeitar as regras do SPOMSP

## 2.4 Pré Condições

* n/a.

## 2.5 Pós Condições

* As requests são atendidas.

## 2.6 SSD

* Não há SSD pois ,eM principio não é suposto haver interação com o utilizador.
# 3. Design

    Como a linguagem C é utilizada os patterns aprendidos em EApli não serão totalmente utilizados   


# 4. Implementação
    


}

# 5. Integração/Demonstração

* É mostrada através das US4002 e 2005

# 6. Observações

    ...