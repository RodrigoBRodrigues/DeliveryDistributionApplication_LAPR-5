package eapli.base.app.backoffice.console.presentation.agv;

import eapli.base.agv.application.CreateAGVController;
import eapli.base.agv.domain.AGV;
import eapli.base.agv.domain.DockingPoint;
import eapli.base.agv.repositories.AGVRepository;
import eapli.base.app.backoffice.console.presentation.Utils.Utils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.warehousemanagement.domain.AGVDocks;
import eapli.base.warehousemanagement.domain.WarehouseInfo;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class CreateAGVUI extends AbstractUI {

    private final CreateAGVController ctrl = new CreateAGVController();
    WarehouseInfo warehouseInfo = null;
    boolean validation = false;
    private final AGVRepository repo = PersistenceContext.repositories().agvs();


    public CreateAGVUI() throws FileNotFoundException {
        try {
            warehouseInfo = new WarehouseInfo();
            validation = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException exception) {
            System.out.println("To configure an AGV you need a warehouse plant for its base location. Please upload one first.");
            validation = false;
        }
    }

    @Override
    protected boolean doShow() {

        Integer autonomy = null;
        if (validation) {
            do {
                try {
                    autonomy = Integer.parseInt(Console.readLine("Introduce the AGV autonomy: "));
                    if(autonomy < 0){
                        validation = false;
                        System.out.println("Enter a positive value!");
                    } else {
                        validation = true;
                    }
                } catch (NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    validation = false;
                }

            }
            while (!validation);


            Double capacity = null;
            do {
                try {
                    capacity = Double.parseDouble(Console.readLine("Introduce the AGV capacity: "));
                    if(capacity < 0){
                        validation = false;
                        System.out.println("Enter a positive value!");
                    } else {
                        validation = true;
                    }
                } catch (NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    validation = false;
                }
            } while (!validation);

            Double weight = null;
            do {
                try {
                    weight = Double.parseDouble(Console.readLine("Introduce the AGV weight: "));
                    if(weight < 0){
                        validation = false;
                        System.out.println("Enter a positive value!");
                    } else {
                        validation = true;
                    }
                } catch (NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    validation = false;
                }
            }
            while (!validation);

            Double volume = null;
            do {
                try {
                    volume = Double.parseDouble(Console.readLine("Introduce the AGV volume: "));
                    if(volume < 0){
                        validation = false;
                        System.out.println("Enter a positive value!");
                    } else {
                        validation = true;
                    }
                } catch (NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    validation = false;
                }
            } while (!validation);

            String shortDescription;
            do {
                shortDescription = Console.readLine("Introduce the AGV short description: ");

                if (shortDescription.isEmpty() || shortDescription.length() > 30) {
                    System.out.println("This field can't be empty and must have a maximum of 30 chars.");
                }
            } while (shortDescription.isEmpty() || shortDescription.length() > 30);


            AGVDocks agvDock;
            boolean a = false;
            do {
                agvDock = selectAgvDock(warehouseInfo);

                a = repo.validateAgvBaseLocation(agvDock.getId());

            }while(!a);

            DockingPoint dockingPoint = new DockingPoint(agvDock.getId());


            System.out.println(dockingPoint);


            this.ctrl.createAGV(autonomy, capacity, weight, volume, shortDescription, dockingPoint);

            System.out.println("Resulting AGV class: ");
            System.out.println(this.ctrl.showAGVBuilder());

            String confirmation = null;
            do {
                confirmation= Utils.readLineFromConsole("Do you wish to create this AGV?(Y/N)");
                if(confirmation.equalsIgnoreCase("y")) {
                    AGV newAGV = this.ctrl.saveAGV();

                    if(newAGV != null) {
                        System.out.println(newAGV);
                        System.out.print("Operation successfully completed!\n");
                        break;
                    } else {
                        System.out.println("Oh no! Something went wrong when creating the AGV!\n");
                        break;
                    }
                } else if (confirmation.equalsIgnoreCase("n")) {
                    System.out.print("Operation successfully canceled!\n");
                    break;
                } else {
                    System.out.println("Enter Y to confirm, or N to cancel the AGV!");
                }

            }while(!confirmation.equalsIgnoreCase("y") || !confirmation.equalsIgnoreCase("n"));
        }
        return false;
    }

    public AGVDocks selectAgvDock(WarehouseInfo warehouseInfo) {
        assert warehouseInfo != null;
        final Iterable<AGVDocks> agvDocks = warehouseInfo.getAVGDocks();
        final SelectWidget<AGVDocks> agvDockSelector = new SelectWidget<>("Agv Docks: ", agvDocks, new AgvDockPrinter());
        agvDockSelector.show();
        return agvDockSelector.selectedElement();
    }


    @Override
    public String headline() {
        return "Configure AGV";
    }
}
