package eapli.base.agv.application;

import eapli.base.agv.domain.AGV;
import eapli.base.agv.domain.AGVBuilder;
import eapli.base.agv.domain.DockingPoint;
import eapli.base.agv.repositories.AGVRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class CreateAGVController {

    private AGVBuilder AB;
    private AGVRepository aRepo = PersistenceContext.repositories().agvs();

    public CreateAGVController()  {
        AB = new AGVBuilder();
    }

    public void createAGV(Integer autonomy, Double capacity, Double weight, Double volume
                        , String shortDescription, DockingPoint dockingPoint) {
        this.AB = AB.createAGV(autonomy, capacity, weight, volume, shortDescription, dockingPoint);
    }

    public String showAGVBuilder() {
        return this.AB.toString();
    }

    public AGV saveAGV() {
        AGV agv = AB.build();
        return this.aRepo.save(agv);
    }

}
