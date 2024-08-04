package eapli.base.dashboard.application;

import eapli.base.agv.domain.AGV;
import eapli.base.agv.domain.AGVState;
import eapli.base.agv.repositories.AGVRepository;
import eapli.base.dashboard.domain.*;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AGVsDashboardController {

    static private AGVRepository agvRepository = PersistenceContext.repositories().agvs();

    public void showDashboard() {
        HttpsServerAjaxVoting server = new HttpsServerAjaxVoting();
        server.start();
    }

    public List<AGVsDashboardInfoDTO> infoAGVs() throws Exception {
        TcpClient tcp = new TcpClient();
        int [] [] warehouse = tcp.startConnection();
        List <AGVsDashboardInfoDTO> agvsDashboardInfoDTOList = new ArrayList<>();
        for (int i = 0; i < 18; i ++) {
            for (int j = 0; j < 20; j++) {
                if (warehouse[i][j] == 3 || warehouse [i][j] == 4) {
                    if (((i == 2) && (j == 0)) || ((i == 4) && (j == 0))   || ((i == 12) && (j ==0))
                    || ((i == 3) && (j ==19))  || ((i == 13) && (j == 19)) || ((i == 14) && (j == 0))) {
                        agvsDashboardInfoDTOList.add(new AGVsDashboardInfoDTO("AGV","FREE",
                                "DOCK"));
                    } else {
                        agvsDashboardInfoDTOList.add(new AGVsDashboardInfoDTO("AGV","OCUPPIED_SERVING_A_GIVEN_ORDER",
                                "" + i + " - " + j));
                    }
                }
            }
        }

        return agvsDashboardInfoDTOList;
    }

    /*
    private AGVState getAgvState(Long identity) throws Exception {
        AGVState agvState = null;
        TcpClient tcp = new TcpClient();
        tcp.startConnection();
        try {
            agvState = tcp.getAgvState(identity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agvState;
    }*/

    public String location(AGV agv) {
        if (agv.agvState().equals(AGVState.FREE)){
            return agv.agvDocks().toString();
        } else {
            return agv.productOrderQueue().get(0).getProductIntegerMap().keySet().iterator().next().getStorageArea().toString();
        }
    }

    public int[][] agvsWarehouse () throws Exception {
        TcpClient tcp = new TcpClient();
        return tcp.startConnection();
    }
}



