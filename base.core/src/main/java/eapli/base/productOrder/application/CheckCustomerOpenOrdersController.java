package eapli.base.productOrder.application;

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.productOrder.repositories.OrderRepository;

import java.util.Objects;

public class CheckCustomerOpenOrdersController {

        private OrderRepository oRepo = PersistenceContext.repositories().orders();
        private CustomerRepository cRepo = PersistenceContext.repositories().customers();

        public CheckCustomerOpenOrdersController(){

        }

        public Iterable<Customer> getListCustomers() {
                return this.cRepo.findAllActive();
        }

        public Iterable<ProductOrder> getListProductOrders() {
                return this.oRepo.findAllActive();
        }

        public boolean checkCustomerHasProductOrders(Customer rCustomer, Iterable<ProductOrder> lProdOrder) {
                for (ProductOrder prodOrder:lProdOrder){
                        if(Objects.equals(prodOrder.getCustomerId(), rCustomer.identity()) && !prodOrder.getOrderState().toString().equals(OrderState.DELIVERED.toString())){
                                return true;
                        }
                }
                return false;
        }
}
