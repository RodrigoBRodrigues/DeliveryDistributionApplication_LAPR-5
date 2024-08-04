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
package eapli.base.app.user.console;

import eapli.base.app.user.console.presentation.FrontMenu;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.packet.Packet;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Base User App.
 */
@SuppressWarnings("squid:S106")
public final class BaseUserApp {

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private BaseUserApp() {
    }

    static ServerSocket sock;
    private static final int PORT_NUMBER = 2020;

    public static void main(String[] args) throws Exception {
        Socket cliSock;
        System.out.println("Server side: Waiting for requests");

        try {
            sock = new ServerSocket(PORT_NUMBER);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

        while (true) {
            cliSock = sock.accept();
            new Thread(new TcpServerAGVDigitalTwinThread(cliSock)).start();
        }
    }
}


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
            while (true) {
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
