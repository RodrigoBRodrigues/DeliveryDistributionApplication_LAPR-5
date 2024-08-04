package eapli.base.productOrder.domain;

import eapli.base.clientusermanagement.domain.Address;
import eapli.base.clientusermanagement.domain.AddressType;
import eapli.base.product.domain.Price;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class ProductOrderTest {

    private final LocalDateTime DATE = LocalDateTime.now();
    private final Long CUSTOMERID = 1L;
    private final Address BILLINGADDRESS = new Address("Rua Areosa","52","4444-444","Porto","Portugal", AddressType.SHIPMENT);
    private final Address DELIVERYADDRESS = new Address("Rua Areosa","52","4444-444","Porto","Portugal", AddressType.SHIPMENT);
    private final Price TOTALAMOUNT = new Price(0.0,0.0);
    private final Payment PAYMENT = Payment.PAYPAL;
    private final Shipment SHIPMENT = Shipment.STANDARD;
    private final OrderState ORDERSTATE = OrderState.REGISTERED;

    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed() {
        new ProductOrder(null,null,null,null,null,null);
    }

    @Test
    public void productOrder(){
        new ProductOrder(DATE,CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,ORDERSTATE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCustomerID(){
        new ProductOrder(DATE , null,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,ORDERSTATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveDate(){
        new ProductOrder(null , CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,ORDERSTATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveBillingAddress(){
        new ProductOrder(DATE , CUSTOMERID,DELIVERYADDRESS,null,null,TOTALAMOUNT,PAYMENT,SHIPMENT,ORDERSTATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveDeliveryAddress(){
        new ProductOrder(DATE , CUSTOMERID,null,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,ORDERSTATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveTotalAmount(){
        new ProductOrder(DATE , CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,null,PAYMENT,SHIPMENT,ORDERSTATE);
    }

    @Test
    public void ensureProductOrderIsSameAsItsInstance(){
        ProductOrder p = new ProductOrder(DATE,CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,ORDERSTATE);
        boolean expected = p.sameAs(p);
        assertTrue(expected);
    }

    @Test
    public void updateProductOrderStateToBEINGPREPARED(){
        ProductOrder p = new ProductOrder(DATE,CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,OrderState.TO_BE_PREPARED);
        p.setOrderState(OrderState.BEING_PREPARED);
        boolean expected = OrderState.BEING_PREPARED.equals(p.getOrderState());
        assertTrue(expected);
    }

    @Test
    public void updateProductOrderStateToDISPATCHED(){
        ProductOrder p = new ProductOrder(DATE,CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,OrderState.READY_FOR_CARRIER);
        p.setOrderState(OrderState.DISPATCHED);
        boolean expected = OrderState.DISPATCHED.equals(p.getOrderState());
        assertTrue(expected);
    }

    @Test
    public void updateProductOrderStateToDELIVERED(){
        ProductOrder p = new ProductOrder(DATE,CUSTOMERID,DELIVERYADDRESS,BILLINGADDRESS,null,TOTALAMOUNT,PAYMENT,SHIPMENT,OrderState.DISPATCHED);
        p.setOrderState(OrderState.DELIVERED);
        boolean expected = OrderState.DELIVERED.equals(p.getOrderState());
        assertTrue(expected);
    }

}