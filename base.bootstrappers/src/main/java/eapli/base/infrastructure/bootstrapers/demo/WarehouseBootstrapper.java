package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehousemanagement.application.WarehouseController;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.base.warehousemanagement.domain.WarehouseInfo;
import eapli.base.warehousemanagement.repositories.WarehouseRepository;
import eapli.framework.actions.Action;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

public class WarehouseBootstrapper implements Action {
    WarehouseController ctrl = new WarehouseController();
    private final WarehouseRepository pRepo = PersistenceContext.repositories().warehouse();
    private static final String defaultFile = "warehouse1";

    @Override
    public boolean execute() {
        Warehouse warehouse = null;
        try {
            warehouse = ctrl.buildWarehousePlant(defaultFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ctrl.buildShelves(Objects.requireNonNull(warehouse));
        warehouse.setJsonPath(defaultFile);
        warehouse.setWarehouse(Arrays.deepToString(warehouseC()));
        if (ctrl.alreadyInDatabase()) {
            ctrl.deletePreviousWarehouse();
        }
        ctrl.saveWarehouse(warehouse);


        return false;
    }

    public Integer [][] warehouseC () {
        int Width = 18;
        int Length = 20;
        int down = 1;
        Integer[][] warehouse = new Integer[Width][Length];
        warehouse [3-down] [1-down] = 4;  warehouse [5-down][1-down] = 4;  warehouse [13-down][1-down]  =4;
        warehouse [15-down][1-down] = 2; warehouse [4-down][20-down] = 2;  warehouse [14-down][20-down] =2;
        for (int i = 5; i < 17; i++) {
            warehouse [0] [i-down] = 1;
            warehouse [7] [i-down] = 1;
            warehouse [8] [i-down] = 1;
            warehouse [17][i-down] = 1;
            warehouse [9] [i-down] = 1;
            warehouse [10][i-down] = 1;
        }
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j <20 ; j ++){
                if (warehouse[i][j] == null) {
                    warehouse [i][j] = 0;
                }
            }
        }
        return warehouse;
    }
}
