package eapli.base.dashboard.domain;

import eapli.base.dashboard.application.AGVsDashboardController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import javax.net.ssl.SSLServerSocket;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author ANDRE MOREIRA (asc@isep.ipp.pt)
 */

public class HttpsServerAjaxVoting extends Thread {
    private final static AuthorizationService authz = AuthzRegistry.authorizationService();
    private final static String username = authz.session().get().authenticatedUser().username().toString();
    private final static String email = authz.session().get().authenticatedUser().email().toString();

    static private final String BASE_FOLDER = "base.core/src/main/java/eapli/base/dashboard/domain/www";
    static private SSLServerSocket sock;

    @Override
    public void run() {
        SSLSocket cliSock = null;

        System.setProperty("javax.net.ssl.keyStore", "B.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","secret");
/*
        System.setProperty("javax.net.ssl.trustStore", "B.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "secret");
*/
        try {
            SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sock = (SSLServerSocket) sslF.createServerSocket(11557);
        } catch (IOException ex) {
            System.out.println("Server failed to open local port " + 11557);
            System.exit(1);
        }

        while (true) {
            try {
                cliSock = (SSLSocket) sock.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpAjaxVotingRequest req = new HttpAjaxVotingRequest(cliSock, BASE_FOLDER);
            req.start();
        }
    }



        public static synchronized String getPersonalInfo () {
        return " <div class=\"topnav\" id=\"personalInformation\">\n" +
                "    <a class=\"active\" href=\"#home\">Personal Information</a>\n" +
                "    <a href=> Name: " + username + "</a>\n" +
                "    <a href=> Email: " + email + "</a>\n" +
                "</div> ";
    }

    public static synchronized String refreshAGVsDashboardInfo() {
        AGVsDashboardController controller = new AGVsDashboardController();
        try {
            List<AGVsDashboardInfoDTO> agvsDashboard = controller.infoAGVs();
            if (agvsDashboard != null) {
                StringBuilder s = new StringBuilder();
                for (AGVsDashboardInfoDTO agv : agvsDashboard) {
                    s.append("<tr class=\"active-row\">" +
                            "<td>" + agv.AGVDescription + "</td>" +
                            "<td>" + agv.AGVStatus + "</td>" +
                            "<td>" + agv.AGVPosition + "</td>" +
                            "</tr>");
                }
                return s.toString();
            } else {
                return " ";
            }
        } catch (NullPointerException ne) {
            return " ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized String refreshAgvsWarehouse () throws Exception {
        AGVsDashboardController controller = new AGVsDashboardController();
        int[][] warehouse = controller.agvsWarehouse();
        StringBuilder s = new StringBuilder("<div class=\"grid-container\">\n");
        for (int i = 0; i < 18 ; i++){
            for (int j = 0; j <20 ;j++) {
                if (warehouse[i][j] == 0){
                    s.append("<div class=\"grid-item\"></div>\n");
                }
                if (warehouse[i][j] == 1){
                    s.append("<div class=\"grid-item1\"></div>\n");
                }
                if (warehouse[i][j] == 3){
                    s.append("<div class=\"grid-item3\"></div>\n");
                }
                if (warehouse[i][j] == 4){
                    s.append("<div class=\"grid-item3\"></div>\n");
                }
                if (warehouse[i][j] == 2){
                    s.append("<div class=\"grid-item2\"></div>\n");
                }
            }
        }
        return s + "</div>";
    }

}

