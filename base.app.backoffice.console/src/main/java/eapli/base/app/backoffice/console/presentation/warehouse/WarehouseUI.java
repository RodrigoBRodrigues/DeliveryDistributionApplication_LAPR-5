package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehousemanagement.application.WarehouseController;
import eapli.base.warehousemanagement.domain.Aisle;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.base.warehousemanagement.domain.WarehouseInfo;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.io.FileNotFoundException;

public class WarehouseUI extends AbstractUI {

    private final WarehouseController theController = new WarehouseController();

    @Override
    protected boolean doShow() {
        String response = "y";
        boolean alreadyExistsInDatabase = theController.alreadyInDatabase();
        if (alreadyExistsInDatabase) {
            response = Console.readLine("Warehouse plant is already database do you want still to import it?");
        }
        if (((response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) && theController.alreadyInDatabase()) || !theController.alreadyInDatabase()) {
            String fileName;

            boolean passed;
            do {
                fileName = Console.readLine("File Name");

                try {
                    Warehouse warehouse = theController.buildWarehousePlant(fileName);
                    theController.buildShelves(warehouse);
                    warehouse.setJsonPath(fileName);
                    saveWarehouse(warehouse, alreadyExistsInDatabase);
                    System.out.println(warehouse);
                    passed = true;
                } catch (FileNotFoundException e) {
                    System.out.println("There is no file with that name. Please check the name of the JSON and try again!");
                    passed = false;
                }
            } while (!passed);
        }
        return true;
    }

    public void saveWarehouse(Warehouse warehouse, boolean alreadyExistsInDatabase) {
        if (alreadyExistsInDatabase) {
            theController.deletePreviousWarehouse();
        }
        theController.saveWarehouse(warehouse);
    }

    @Override
    public String headline() {
        return "Set up Warehouse Plant";
    }

}
