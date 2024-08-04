# US5003
=======================================


# 1. Requisitos

**As Project Manager, I want the input communications (of the AGV Digital Twin) made through the SPOMS2022 protocol to be secured/protected.**

Sockets serão utilizados

# 2. Análise

O AGV irá atuar como um "server" de acordo com o SPOMSP.

**Dependência(s)**

Não existem para esta US. Embora as US 5001,4001 e 4002 tenham que ter um protocolo de comunicação estabelecido. 

**Fluxo Básico**

1. O server é ligado

- 2. O server recebe requests do Digital Twin ou do backoffice

- 3. O server responde

**Esclarecimento(s) do Cliente**




"For all of those US, the communication between the two involved components must be implemented in accordance with the SPOMS2022. The requests processing can be somehow mocked. For instance, if processing a request implies saving some data to the database, the component can instead write such data to a log (mocking). Latter, on next sprint, the teams implement the interaction to the database.

However, it is not advisable mocking everything, namely the components (internal) state. Notice that by mocking you are letting extra effort to the next sprint.

Finally, all US must be demonstrable."


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

* Não há SSD pois ,en principio não é suposto haver interação com o utilizador.
# 3. Design

## 3.1. Realização da Funcionalidade

![SD_5001](Diagramas/US5001_SD.svg)


## 3.2. Diagrama de Classes

![CD_1005](Diagramas/US5001_CD.svg)


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
    class TcpServerAGVDigitalTwinThread implements Runnable {
    private final Socket s;
    private ObjectOutputStream sOut;
    private ObjectInputStream sIn;

    public TcpServerAGVDigitalTwinThread(Socket cli_s) {
        s = cli_s;
    }

    public void run() {
        InetAddress clientIP;

        clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() +
                ", port number " + s.getPort());
        try {
            sOut = new ObjectOutputStream(s.getOutputStream());
            sIn = new ObjectInputStream(s.getInputStream());
            while(true) {
                Packet packet = null;
                try {
                    packet = (Packet) sIn.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Packet packetWrite = new Packet((byte) 0, (byte) 2, "Acknowledged".getBytes(StandardCharsets.UTF_8));

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

                    default:
                        System.out.println("==> ERROR: Error while sending the packet to the client");
                        break;

                }
            }
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }

   
}

# 5. Integração/Demonstração

* É mostrada através das US4002 e 2005

# 6. Observações

    ...