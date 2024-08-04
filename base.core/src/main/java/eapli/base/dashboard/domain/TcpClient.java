package eapli.base.dashboard.domain;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehousemanagement.domain.Warehouse;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class TcpClient {

    static final int SERVER_PORT=2020;

    static InetAddress serverIP;
    static SSLSocket sock;
    static ObjectOutputStream sOut;
    static ObjectInputStream sIn;
    private static final String TRUSTED_STORE = "A.jks";
    private static final String KEYSTORE_PASS = "secret";


    public int [][] startConnection() throws Exception {
            try {

                System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
                System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

                System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
                System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

                SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

                try {
                    serverIP = InetAddress.getByName("127.0.0.1");
                } catch (UnknownHostException ex) {
                    System.out.println("Invalid server specified: " + "127.0.0.1");
                    System.exit(1);
                }

                try {
                    sock = (SSLSocket) sf.createSocket(serverIP, SERVER_PORT);
                } catch (IOException ex) {
                    Thread.sleep(3000);
                }

                try {
                    sock.startHandshake();
                } catch (SSLHandshakeException e) {
                    Thread.sleep(3000);
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                sOut = new ObjectOutputStream(sock.getOutputStream());
                sIn = new ObjectInputStream(sock.getInputStream());
            } catch (SocketException e) {
                sock = null;
            } catch (NullPointerException e) {
            }

        int [][] warehouse = monoToBidi(18,20);
        // System.out.println(wp);
        return warehouse;
    }

    public int[][] monoToBidi(final int rows, final int cols ) {
        int[][] bidi;
        try {
            PersistenceContext.repositories().newTransactionalContext();
            Warehouse w = PersistenceContext.repositories().warehouse().findAll().iterator().next();
            String wp = w.getWarehouse();
            String[] oo = wp.split(",");
            int[] array = new int[18 * 20];
            for (int i = 0; i < oo.length; i++) {
                if (oo[i].contains("]")) {
                    oo[i] = oo[i].replace("]", "");
                }
                if (oo[i].contains("[")) {
                    oo[i] = oo[i].replace("[", "");
                }
                oo[i] = oo[i].replace(" ", "");
                array[i] = Integer.parseInt(oo[i]);
            }
            if (array.length != (rows * cols))
                throw new IllegalArgumentException("Invalid array length");

            bidi = new int[rows][cols];
            for (int i = 0; i < rows; i++)
                System.arraycopy(array, (i * cols), bidi[i], 0, cols);
        }catch (NoSuchElementException e) {
            bidi = new int[rows][cols];
        }
        return bidi;
    }
}