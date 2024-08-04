/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.app.other.console;

import eapli.base.agv.domain.AGVState;
import eapli.base.app.common.console.presentation.authz.LoginAction;
import eapli.base.app.other.console.presentation.MainMenu;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.packet.Packet;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public final class OtherApp {

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private OtherApp() {
    }


    static InetAddress serverIP;
    static Socket sock;
    private static final int PORT_NUMBER = 2020;

    //args[0]= server ip
    //args[1]= AGV ID
    public static void main(String[] args) throws Exception {
        System.out.println("Client side: Waiting for you to send a request");

        if (args.length != 2 && args.length!=1) {

            System.out.println("Server IPv4/IPv6 address or DNS name is required as argument");
            System.exit(1);
        }

        try {
            serverIP = InetAddress.getByName(args[0]);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + args[0]);
            System.exit(1);
        }

        try {
            sock = new Socket(serverIP, PORT_NUMBER);
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());

        String conteudo = "";

        byte version = 0, code = 3;


        do {
            System.out.println("Please type the code of the type of request you wish to change.(check SPOMSP and SPOMSP Code extension)");
            conteudo = in.readLine();
            if (Integer.parseInt(conteudo)==0){
                Packet packetCommtest= new Packet((byte) 0,(byte) 0,"".getBytes(StandardCharsets.UTF_8));
                outputStream.writeObject(packetCommtest);
                System.out.println("sent packet with data " + packetCommtest.data());
                Packet packetReceived= (Packet) inputStream.readObject();
                System.out.println("received packet with data "+ packetReceived.data());

            }
            if (Integer.parseInt(conteudo)==1){
                Packet packetDisconnect= new Packet((byte) 0,(byte) 1,"".getBytes(StandardCharsets.UTF_8));
                outputStream.writeObject(packetDisconnect);
                System.out.println("sent packet with data " + packetDisconnect.data());
                Packet packetReceived= (Packet) inputStream.readObject();
                System.out.println("received packet with data "+ packetReceived.data());
                break;
            }
            if(Integer.parseInt(conteudo)==3) {
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


        }
        while (!conteudo.equals("-1"));
        sock.close();
    }


    private static Packet buildStateChangeRequestData(AGVState state, Long id) {
        Packet packet = new Packet((byte) 0, (byte) 3, ("STATE:" + state.toString() + "ID:" + id).getBytes(StandardCharsets.UTF_8));
        return packet;
    }
}
