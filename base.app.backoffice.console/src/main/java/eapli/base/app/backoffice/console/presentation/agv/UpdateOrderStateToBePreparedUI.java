package eapli.base.app.backoffice.console.presentation.agv;

import eapli.base.agv.application.UpdateOrderStateToBePreparedController;
import eapli.base.agv.domain.AGV;
import eapli.base.app.backoffice.console.presentation.Utils.Utils;
import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class UpdateOrderStateToBePreparedUI extends AbstractUI {

    UpdateOrderStateToBePreparedController ctrl = new UpdateOrderStateToBePreparedController();

    @Override
    protected boolean doShow() {

        Iterable<ProductOrder> lProdOrder = ctrl.getListProductOrders();
        int i = 1;


        System.out.print("Order List: \n" +
                "--------------------------------------------------------------\n");
        for (ProductOrder prod : lProdOrder ) {
            if(prod.getOrderState().toString().equals(OrderState.TO_BE_PREPARED.toString())){
                System.out.println(i + " - \n" + prod.shortToString());
                i++;
            }
        }
        System.out.print("\n" +
                "0 - to cancel operation");
        System.out.print("--------------------------------------------------------------\n");
        ProductOrder rProductOrder = (ProductOrder) Utils.selectsObject((List) lProdOrder);

        if (rProductOrder == null){
            System.out.print("No Product Order selected! (Null Product Order)\n");
            return false;
        }


        i = 1;
        Iterable<AGV> lAGV = ctrl.getListAGV();
        System.out.print("AGV List: \n" +
                "--------------------------------------------------------------\n");
        for (AGV agv : lAGV ) {
            System.out.println(i + " - " + agv.toString());
            i++;
        }
        System.out.print("\n" +
                "0 - to cancel operation");
        System.out.print("--------------------------------------------------------------\n");
        AGV rAGV = (AGV) Utils.selectsObject((List) lAGV);

        if(rAGV == null){
            System.out.println("No object selected! (Null AGV)");
            return false;
        }

        System.out.println();

        String confirmation = null;
        do {
            confirmation= Utils.readLineFromConsole("Do you wish to assign AGV#" + rAGV.identity() + " to prepare ProductOrder#" + rProductOrder.identity() + "?(Y/N)\"");
            if(confirmation.equalsIgnoreCase("y")) {
                AGV nAGV = rAGV;
                ProductOrder nProductOrder = rProductOrder;

                System.out.println(rAGV.printTaskOrder());


                if(this.ctrl.checkAssignProductOrder(rProductOrder, rAGV)){
                    rProductOrder = this.ctrl.updateOrderState(rProductOrder);
                    this.ctrl.save(rProductOrder,nProductOrder);
                    this.ctrl.assignProductOrder(rProductOrder,rAGV);
                    this.ctrl.save(rAGV, nAGV);
                    System.out.print("--------------------------------------------------------------\n" +
                            "Operation successful!" + "\n" +
                            "--------------------------------------------------------------\n" +
                            "Here is AGV#" + rAGV.identity() + "'s updated task list:" + "\n" +
                            rAGV.printTaskOrder() + "\n" +
                            "--------------------------------------------------------------\n");
                    break;
                } else {
                    System.out.print("--------------------------------------------------------------\n" +
                            "Operation unsuccessful!" + "\n" +
                            "--------------------------------------------------------------\n");
                    break;
                }

            } else if (confirmation.equalsIgnoreCase("n")) {
                System.out.print("Operation successfully canceled!\n");
                break;
            } else {
                System.out.println("Enter Y to confirm, or N to cancel the order!");
            }

        }while(!confirmation.equalsIgnoreCase("y") || !confirmation.equalsIgnoreCase("n"));

        return false;
    }

    @Override
    public String headline() {
        return "Update Product Order to be Prepared";
    }
}
