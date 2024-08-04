package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.agv.application.CreateAGVController;
import eapli.base.agv.domain.DockingPoint;
import eapli.base.warehousemanagement.domain.AGVDocks;
import eapli.base.warehousemanagement.domain.WarehouseInfo;
import eapli.framework.actions.Action;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AgvBootstrapper implements Action {
    CreateAGVController ctrl = new CreateAGVController();

    @Override
    public boolean execute() {
        WarehouseInfo warehouseInfo = null;
        try {
            warehouseInfo = new WarehouseInfo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Iterable<AGVDocks> agvDocks = Objects.requireNonNull(warehouseInfo).getAVGDocks();
        List<DockingPoint> dockingPoint = new ArrayList<>();
        Iterator<AGVDocks> agvDocksIterator = agvDocks.iterator();

        for(int i = 0; i < 3; i++){
            dockingPoint.add(new DockingPoint(agvDocksIterator.next().getId()));
            agvDocksIterator.remove();
        }

        ctrl.createAGV(150, 150.0, 150.0, 150.0, "AGV1", dockingPoint.remove(0));
        ctrl.saveAGV();
        ctrl.createAGV(250, 250.0, 250.0, 250.0, "AGV2", dockingPoint.remove(0));
        ctrl.saveAGV();
        ctrl.createAGV(350, 350.0, 350.0, 350.0, "AGV3", dockingPoint.remove(0));
        ctrl.saveAGV();





        return false;
    }
}
