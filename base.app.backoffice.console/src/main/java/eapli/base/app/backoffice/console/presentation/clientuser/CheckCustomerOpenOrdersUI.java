package eapli.base.app.backoffice.console.presentation.clientuser;

import eapli.base.agv.application.UpdateOrderStateToBePreparedController;
import eapli.base.app.backoffice.console.presentation.Utils.Utils;
import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.productOrder.application.CheckCustomerOpenOrdersController;
import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.framework.presentation.console.AbstractUI;
import java.util.List;
import java.util.Objects;

public class CheckCustomerOpenOrdersUI extends AbstractUI {

    CheckCustomerOpenOrdersController ctrl = new CheckCustomerOpenOrdersController();

    @Override
    protected boolean doShow() {
        Iterable<Customer> lCustomer = ctrl.getListCustomers();
        int i = 1;


        System.out.print("Customer List: \n" +
                "--------------------------------------------------------------\n");
        for (Customer customer : lCustomer ) {
            System.out.println(i + " - \n" + customer.name() +", " + customer.identity() + ", " + customer.email());
            i++;
        }
        System.out.print("\n" +
                "0 - to cancel operation");
        System.out.print("--------------------------------------------------------------\n");
        Customer rCustomer = (Customer) Utils.selectsObject((List) lCustomer);

        if (rCustomer == null){
            System.out.print("No Customer selected! (Null Customer)\n");
            return false;
        }

        Iterable<ProductOrder> lProdOrder = ctrl.getListProductOrders();
        i = 1;

        if(!ctrl.checkCustomerHasProductOrders(rCustomer,lProdOrder)){
            System.out.println("\nCustomer has no open Product Orders in the database!");
            return false;
        }

        System.out.print("\nOrder List: \n" +
                "--------------------------------------------------------------\n");
        for (ProductOrder prod : lProdOrder ) {
            if(Objects.equals(prod.getCustomerId(), rCustomer.identity()) && !prod.getOrderState().toString().equals(OrderState.DELIVERED.toString())){
                System.out.println(i + " - \n" + prod.toString());
                i++;
            }
        }
        System.out.print("\n--------------------------------------------------------------\n");
        return false;
    }

    @Override
    public String headline() {
        return "Check Customer's Open Product Orders";
    }

}
