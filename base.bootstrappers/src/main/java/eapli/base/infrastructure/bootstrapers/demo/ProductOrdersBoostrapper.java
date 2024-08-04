package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.clientusermanagement.domain.Address;
import eapli.base.clientusermanagement.domain.AddressType;
import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productOrder.application.CreateProductOrderController;
import eapli.base.productOrder.domain.OrderState;
import eapli.framework.actions.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ProductOrdersBoostrapper implements Action {
    CreateProductOrderController ctrl = new CreateProductOrderController();
    private final ProductRepository pRepo = PersistenceContext.repositories().products();
    List<Product> lProd = new ArrayList<Product>();
    Iterator<Product> iProd = pRepo.findAll().iterator();


    @Override
    public boolean execute() {

        iProd.forEachRemaining(lProd :: add);

        Optional<Customer> customer1 = PersistenceContext.repositories().customers().findById(13L);
        Optional<Customer> customer2 = PersistenceContext.repositories().customers().findById(16L);
        Optional<Customer> customer3 = PersistenceContext.repositories().customers().findById(19L);

        Address ad1 = new Address("Rua dos Reis", "75", "3880-241", "Ovar", "Portugal", AddressType.SHIPMENT);
        Address ad2 = new Address("Rua dos Maiores", "123", "4910-123", "Aveiro", "Portugal", AddressType.BILLING);
        ad1.setCustomer(customer1.get());
        ad2.setCustomer(customer1.get());

        Address ad3 = new Address("Rua dos Menores", "321", "1234-491", "Mirtilo", "Portugal", AddressType.SHIPMENT);
        Address ad4 = new Address("Rua dos 100 Abrigos", "100", "1000-100", "Darque", "Portugal", AddressType.BILLING);
        ad3.setCustomer(customer2.get());
        ad4.setCustomer(customer2.get());

        Address ad5 = new Address("Rua dos Youtubers", "49", "1234-999", "Nine", "Portugal", AddressType.SHIPMENT);
        Address ad6 = new Address("Rua das Rosas", "40", "4054-500", "Lisboa", "Portugal", AddressType.BILLING);
        ad5.setCustomer(customer3.get());
        ad6.setCustomer(customer3.get());

        Address ad7 = new Address("Rua dos Maléficos", "666", "3333-666", "Faro", "Portugal", AddressType.SHIPMENT);
        Address ad8 = new Address("Rua das Boas Sortes", "77", "3377-129", "Viana", "Portugal", AddressType.BILLING);
        ad7.setCustomer(customer1.get());
        ad8.setCustomer(customer1.get());

        ctrl.createOrder(13L,ad1,ad2);
        ctrl.setOrderState(OrderState.TO_BE_PREPARED);
        ctrl.addProductToOrder(lProd.get(0),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(16L,ad3,ad4);
        ctrl.setOrderState(OrderState.TO_BE_PREPARED);
        ctrl.addProductToOrder(lProd.get(1),2);
        ctrl.addProductToOrder(lProd.get(2),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        ctrl.createOrder(19L,ad5,ad6);
        ctrl.setOrderState(OrderState.TO_BE_PREPARED);
        ctrl.addProductToOrder(lProd.get(3),2);
        ctrl.addProductToOrder(lProd.get(4),5);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        ctrl.createOrder(13L,ad7,ad8);
        ctrl.setOrderState(OrderState.TO_BE_PREPARED);
        ctrl.addProductToOrder(lProd.get(5),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        ctrl.createOrder(13L,ad2,ad1);
        ctrl.setOrderState(OrderState.READY_FOR_CARRIER);
        ctrl.addProductToOrder(lProd.get(1),10);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        ctrl.createOrder(16L,ad4,ad3);
        ctrl.setOrderState(OrderState.READY_FOR_CARRIER);
        ctrl.addProductToOrder(lProd.get(5),4);
        ctrl.addProductToOrder(lProd.get(6),25);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        ctrl.createOrder(19L,ad6,ad5);
        ctrl.setOrderState(OrderState.READY_FOR_CARRIER);
        ctrl.addProductToOrder(lProd.get(2),7);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        ctrl.createOrder(13L,ad8,ad7);
        ctrl.setOrderState(OrderState.READY_FOR_CARRIER);
        ctrl.addProductToOrder(lProd.get(3),5);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(13L,ad3,ad4);
        ctrl.setOrderState(OrderState.DISPATCHED);
        ctrl.addProductToOrder(lProd.get(2),2);
        ctrl.addProductToOrder(lProd.get(1),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(16L,ad3,ad4);
        ctrl.setOrderState(OrderState.DISPATCHED);
        ctrl.addProductToOrder(lProd.get(1),2);
        ctrl.addProductToOrder(lProd.get(5),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(19L,ad3,ad4);
        ctrl.setOrderState(OrderState.DISPATCHED);
        ctrl.addProductToOrder(lProd.get(4),2);
        ctrl.addProductToOrder(lProd.get(3),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(13L,ad3,ad4);
        ctrl.setOrderState(OrderState.DISPATCHED);
        ctrl.addProductToOrder(lProd.get(1),2);
        ctrl.addProductToOrder(lProd.get(4),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(16L,ad3,ad4);
        ctrl.setOrderState(OrderState.DISPATCHED);
        ctrl.addProductToOrder(lProd.get(5),2);
        ctrl.addProductToOrder(lProd.get(2),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        ctrl.createOrder(19L,ad3,ad4);
        ctrl.setOrderState(OrderState.DISPATCHED);
        ctrl.addProductToOrder(lProd.get(4),4);
        ctrl.addProductToOrder(lProd.get(2),1);
        ctrl.saveOrder();
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }

        return false;
    }



}
