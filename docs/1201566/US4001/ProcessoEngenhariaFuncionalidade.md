# US4001
=======================================


# 1. Requisitos

**As Project Manager, I want that the team start developing the input communication module of the AGV digital twin to accept requests from the "AGVManager".**

Sockets serão utilizados

# 2. Análise

O AGV digital twin irá ter um "server" e "client" de acordo com o SPOMSP.

**Dependência(s)**

Não existem para esta US. Embora as US 5002,4001 e 4002 tenham que ter um protocolo de comunicação estabelecido.

**Fluxo Básico**

1. O client é ligado.

- 2. Data é transferida para o AGVManager que é um server.

- 3. O AGVManager responde com os dados pedidos.

**Esclarecimento(s) do Cliente**




"For all of those US, the communication between the two involved components must be implemented in accordance with the SPOMS2022. The requests processing can be somehow mocked. For instance, if processing a request implies saving some data to the database, the component can instead write such data to a log (mocking). Latter, on next sprint, the teams implement the interaction to the database.

However, it is not advisable mocking everything, namely the components (internal) state. Notice that by mocking you are letting extra effort to the next sprint.

Finally, all US must be demonstrable."


## 2.1 Futuras implementações para os outros atores

* Não existem.

## 2.2 Sequência das ações

* O server é ligado e espera conexões

## 2.3 Regras de negócio associadas aos atributos de uma category.

* Respeitar as regras do SPOMSP

## 2.4 Pré Condições

* n/a.

## 2.5 Pós Condições

* As requests são atendidas.

## 2.6 SSD

* Não há SSD pois ,em principio não é suposto haver interação com o utilizador.
# 3. Design

## 3.1. Realização da Funcionalidade

![SD_5001](Diagramas/US4001_SD.svg)


## 3.2. Diagrama de Classes

![CD_1005](Diagramas/US4001_CD.svg)


## 3.3. Protoco de Comunicação

* A data transferida está de acordo com o SPOMSP.


## 3.4. Testing

**Teste(s) 1:** Verificar que o Packet fica formatado de acordo com o SPOMSP.

    @Test
    void ensurePacketisWellFormatted() {
        Packet packet = new Packet((byte) 0,(byte) 1,"data".getBytes(StandardCharsets.UTF_8));
       assertEquals(packet.getCode(),1);
       assertEquals("data",packet.data());
    }


# 4. Implementação

    Atende as requests diferentes.

    switch (packet.getCode()) {
                        case 0:
                            System.out.println("==> Request to test the connection sent by Client received with success");
                            //Dizer ao cliente que entendeu
                            System.out.println("==> Send message to the client saying it understood the request");
                            sOut.writeObject(packetWrite);
                            sOut.flush();
                            break;
                        case 1:
                            try {
                                System.out.println("==> Request to end connection sent by Client received with success");
                                //Dizer ao cliente que entendeu
                                System.out.println("==> Send message to the client saying it understood the request");
                                sOut.writeObject(packetWrite);
                                sOut.flush();
                                System.out.println("==> Client " + clientIP.getHostAddress() + ", port number: " + this.s.getPort() + " disconnected");
                            } catch (IOException e) {
                                System.out.println("==> ERROR: " + e.getMessage());
                            } finally {
                                try {
                                    this.s.close();
                                } catch (IOException e) {
                                    System.out.println("ERROR: Error while closing the socket");
                                }
                                System.out.println("==> INFO: Socket closed with Success\n\n");
                            }
                            break;
                        case 3:
                            System.out.println("==> Request to change the state of the AGV sent by digital twin client received with success");
                            Optional<AGV> agv = agvRepository.findById(idPacketParser(packet));
                            agv.get().setAgvState(statePacketParser(packet));
                            agvRepository.save(agv.get());

                            if (statePacketParser(packet)==AGVState.OCCUPIED_SERVING_A_GIVEN_ORDER){
                                Iterable<ProductOrder> orderList=orderRepository.findByDateAscAndState(OrderState.TO_BE_PREPARED);
                                if (orderList.iterator().hasNext()){
                                    ProductOrder order=orderList.iterator().next();
                                    order.setOrderState(OrderState.BEING_PREPARED);
                                    orderRepository.save(order);
                                }

                            }
                            if (statePacketParser(packet)==AGVState.FREE){
                                Iterable<ProductOrder> orderList=orderRepository.findByDateAscAndState(OrderState.BEING_PREPARED);
                                if (orderList.iterator().hasNext()){
                                    ProductOrder order=orderList.iterator().next();
                                    order.setOrderState(OrderState.READY_FOR_CARRIER);
                                    orderRepository.save(order);

                                }
                            }
                            Packet packet1= new Packet((byte) 0,(byte) 4,"Work".getBytes(StandardCharsets.UTF_8));
                            sOut.writeObject(packet1);
                            sOut.flush();
                            System.out.println("==> Send message to the client saying it has to work");

                            break;

                        case 5:
                            System.out.println("Received request of the state of the agv");
                            Optional<AGV> agvCase5 = agvRepository.findById(Long.parseLong(packet.data()));
                            Packet packetStatus= new Packet((byte) 0,(byte)6,agvCase5.get().agvState().toString().getBytes());
                            sOut.writeObject(packetStatus);
                            sOut.flush();
                            break;
                        default:
                            System.out.println("==> ERROR: Error while sending the packet to the client");
                            break;
}

# 5. Integração/Demonstração

    * É mostrada através das US4002 e 2005
    ...
# 6. Observações

    ...