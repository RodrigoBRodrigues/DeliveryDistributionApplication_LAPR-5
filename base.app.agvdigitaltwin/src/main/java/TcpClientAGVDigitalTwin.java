import eapli.base.agv.domain.AGVState;
import eapli.base.packet.Packet;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;


public class TcpClientAGVDigitalTwin {

    static InetAddress serverIP;
    static SSLSocket sock;
    private static final int PORT_NUMBER = 2020;

    static final String KEYSTORE_PASS = "SPOMS@G05_2DH";

    //args[0]= server ip
    //args[1]= AGV ID
    public static void main(String[] args) throws Exception {
        while (true) {
            try {

                if (args.length != 3 && args.length != 1) {

                    System.out.println("Server IPv4/IPv6 address or DNS name is required as argument");
                    System.exit(1);
                }

                System.setProperty("javax.net.ssl.trustStore", args[2] + ".jks");
                System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

                // Use this certificate and private key for client certificate when requested by the server
                System.setProperty("javax.net.ssl.keyStore", args[2] + ".jks");
                System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);
                SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
                try {
                    serverIP = InetAddress.getByName(args[0]);
                } catch (UnknownHostException ex) {
                    System.out.println("Invalid server specified: " + args[0]);
                    System.exit(1);
                }

                try {
                    sock = (SSLSocket) sf.createSocket(serverIP, PORT_NUMBER);
                    System.out.println("Client side: Waiting for you to send a request");
                    System.out.println("Connected to: " + args[0] + ":" + PORT_NUMBER);

                } catch (IOException ex) {
                    Thread.sleep(3000);
                }


                sock.startHandshake();

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());

                String conteudo = "";

                byte version = 0, code = 3;


                do {
                    System.out.println("Please type the code of the type of request you wish to change.(check SPOMSP and SPOMSP Code extension)");
                    conteudo = in.readLine();
                    if (Integer.parseInt(conteudo) == 0) {
                        Packet packetCommtest = new Packet((byte) 0, (byte) 0, "".getBytes(StandardCharsets.UTF_8));
                        outputStream.writeObject(packetCommtest);
                        System.out.println("sent packet with data " + packetCommtest.data());
                        Packet packetReceived = (Packet) inputStream.readObject();
                        System.out.println("received packet with data " + packetReceived.data());

                    }
                    if (Integer.parseInt(conteudo) == 1) {
                        Packet packetDisconnect = new Packet((byte) 0, (byte) 1, "".getBytes(StandardCharsets.UTF_8));
                        outputStream.writeObject(packetDisconnect);
                        System.out.println("sent packet with data " + packetDisconnect.data());
                        Packet packetReceived = (Packet) inputStream.readObject();
                        System.out.println("received packet with data " + packetReceived.data());
                        sock.close();
                        System.exit(0);

                        break;
                    }
                    if (Integer.parseInt(conteudo) == 3) {
                        //Packet packet = new Packet(version, code, conteudo.getBytes(StandardCharsets.UTF_8));
                        Packet packetOccupied = buildStateChangeRequestData(AGVState.OCCUPIED_SERVING_A_GIVEN_ORDER, Long.valueOf(args[1]));
                        outputStream.writeObject(packetOccupied);
                        System.out.println("sent packet with data " + packetOccupied.data());
                        Packet packetReceived = (Packet) inputStream.readObject();
                        if (packetReceived.getCode() == 2 && packetReceived.getCode() == 1) {
                            break;
                        }

                        System.out.println("received packet with data " + packetReceived.data());
                        Packet packetFree = buildStateChangeRequestData(AGVState.FREE, Long.valueOf(args[1]));

                        if (packetReceived.getCode() == 4) {
                            Thread.sleep(10000); //simulates work
                            System.out.println("Work has finished");
                            outputStream.writeObject(packetFree);
                        }
                    }
                    if (Integer.parseInt(conteudo) == 10) {
                        Packet packet = new Packet(version, (byte) 10, "us5001".getBytes(StandardCharsets.UTF_8));
                        outputStream.writeObject(packet);
                        System.out.println("sent packet with data " + packet.data());
                        Packet packetReceived = (Packet) inputStream.readObject();


                        System.out.println("received packet with data " + packetReceived.data());

                    }


                }
                while (!conteudo.equals("-1"));
                sock.close();
            } catch (SocketException e) {
                sock = null;
                System.out.println("Server foi desligado!");
            } catch (NullPointerException e) {
                System.out.println("O server não está ligado! A tentar de novo!");
            }
        }
    }


    private static Packet buildStateChangeRequestData(AGVState state, Long id) {
        Packet packet = new Packet((byte) 0, (byte) 3, ("STATE:" + state.toString() + "ID:" + id).getBytes(StandardCharsets.UTF_8));
        return packet;
    }
}
