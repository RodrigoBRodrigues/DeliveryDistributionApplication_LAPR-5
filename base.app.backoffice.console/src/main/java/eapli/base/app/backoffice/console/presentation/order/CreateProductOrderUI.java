package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.app.backoffice.console.presentation.Utils.Utils;
import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.productOrder.application.CreateProductOrderController;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.product.domain.Product;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class CreateProductOrderUI extends AbstractUI {

    private final CreateProductOrderController ctrl = new CreateProductOrderController();


    @Override
    public boolean doShow() {
        List<Customer> lCustomer = this.ctrl.getCustomerList();
        int c = 1;

        System.out.print("Customer List: \n" +
                "--------------------------------------------------------------\n");
        for (Customer customer: lCustomer ) {
            System.out.println(c + " - " + customer.identity() + ", " + customer.name());
            c++;
        }
        System.out.print("\n" +
                "0 - to cancel operation");
        System.out.print("--------------------------------------------------------------\n");
        Customer customer = (Customer) Utils.selectsObject(lCustomer);

        if(!customer.getAddress().isEmpty()) {
            this.ctrl.createOrder(customer.identity(), customer.defaultAddress(), customer.defaultAddress());
        } else {
            System.out.print("The chosen customer doesn't have a specified default address!\n"
                            + "Returning to menu..\n");
            return false;
        }

        List<Product> lProd = this.ctrl.getProductList();

        int n = 1;

        System.out.print("\n\nProduct List: \n" +
                            "--------------------------------------------------------------\n");
        for (Product prod: lProd) {
            System.out.println(n + " - " + prod.getName() + " - " + prod.getPrice().priceWithTaxes()+ "EUR");
            n++;
        }
        System.out.print("\n" +
                "0 - to finish adding products\n");
        System.out.print("--------------------------------------------------------------\n");

        int i = -1;
        int quantity;
        while(i != 0) {
            i = Utils.readIntegerFromConsole("Select a Product to add to order: ");
            if(i == 0) {
                break;
            }
            quantity = Utils.readIntegerFromConsole("Select how many: ");
            this.ctrl.addProductToOrder(lProd.get(i-1), quantity);
            System.out.println( quantity + "x " + lProd.get(i-1).getName().toString() + " added successfully!");
        }

        System.out.println("Resulting product order class: ");
        System.out.println(this.ctrl.showOrderBuilder());

        String confirmation = null;
        do {
            confirmation= Utils.readLineFromConsole("Do you wish to create this order?(Y/N)");
            if(confirmation.equalsIgnoreCase("y")) {



                ProductOrder newProductOrder = this.ctrl.saveOrder();

                if(newProductOrder != null) {
                    System.out.println(newProductOrder);
                    System.out.print("Operation successfully completed!\n");
                    break;
                } else {
                    System.out.println("Oh no! Something went wrong when creating the ProductOrder!\n");
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
        return "Place Product Order";
    }
}
