package eapli.base.agv.application;

import eapli.base.agv.domain.AGV;
import eapli.base.agv.repositories.AGVRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productOrder.repositories.OrderRepository;

public class AssignOrdersToAGVController {
    private OrderRepository orderRepository= PersistenceContext.repositories().orders();
    private AGVRepository agvRepository= PersistenceContext.repositories().agvs();

    public AssignOrdersToAGVController(){

    }

    public Iterable<AGV> getFreeAGVS(){
        Iterable<AGV> agvsList = agvRepository.findFreeAGVS();
        return agvsList;
    }
}
