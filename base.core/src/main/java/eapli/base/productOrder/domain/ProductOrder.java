package eapli.base.productOrder.domain;

import com.google.gson.annotations.SerializedName;
import eapli.base.clientusermanagement.domain.Address;
import eapli.base.product.domain.Price;
import eapli.base.product.domain.Product;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import org.eclipse.persistence.annotations.SerializedObject;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * A Product Order
 * <p>
 *      This class represent orders. It follows the DDD approach where ProductOrder
 *      is the root identity of the ProductOrder Aggregate and some of its
 *      properties are instances of value objects.
 * </p>
 *      This approach may seem a little more complex than just having String or
 *      native type attributes but provides for real semantic of the domain and
 *      follows the Single Responsibility Pattern
 */

@Entity
@Table
public class ProductOrder implements Serializable, AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="product_order_id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address")
    private Address billingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address")
    private Address deliveryAddress;

    @Embedded
    @Column(name = "total_amount", nullable = false)
    private Price totalAmount;

    /*
    @Embedded
    @Column(name = "order_line", nullable = true)
    private OrderLine orderLine;
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "order_state", nullable = true)
    private OrderState orderState;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = true)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipment", nullable = true)
    private Shipment shipment;

    @ElementCollection
    @CollectionTable(name="product_quantity")
    @MapKeyJoinColumn(name="product_id")
    @Column(name="quantity")
    private Map<Product, Integer> productIntegerMap;

    public ProductOrder(LocalDateTime date, Long customerId) {
        this.date = date;
        this.customerId = customerId;
        this.totalAmount = new Price((double) 0, (double) 0);
    }


    public ProductOrder(LocalDateTime now, Long customerId, Address deliveryAddress, Address billingAddress, Map<Product, Integer> productIntegerMap, Price totalAmount) {
        Preconditions.noneNull(customerId,now,billingAddress,deliveryAddress,totalAmount);
        this.customerId = customerId;
        this.date = now;
        this.billingAddress = billingAddress;
        this.deliveryAddress = deliveryAddress;
        this.productIntegerMap = productIntegerMap;
        this.totalAmount = totalAmount;
    }

    public ProductOrder(LocalDateTime now, Long customerId, Address deliveryAddress, Address billingAddress, Map<Product, Integer> productIntegerMap, Price totalAmount, Payment payment, Shipment shipment, OrderState orderState) {
        Preconditions.noneNull(customerId,now,billingAddress,deliveryAddress,totalAmount);
        this.customerId = customerId;
        this.date = now;
        this.billingAddress = billingAddress;
        this.deliveryAddress = deliveryAddress;
        this.productIntegerMap = productIntegerMap;
        this.totalAmount = totalAmount;
        this.shipment = shipment;
        this.payment = Payment.PAYPAL;
        this.orderState = orderState;
    }

    public ProductOrder() {

    }


    public void addProduct(Product product, int quantity){
        this.productIntegerMap.put(product,quantity);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Long identity() {
        return id;
    }

    public Price getTotalAmount() {
        return totalAmount;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getCustomerId() {
        return customerId;
    }

    /*
    public OrderLine getOrderLine() {
        return orderLine;
    }
     */

    public OrderState getOrderState() {
        return orderState;
    }

    public Payment getPayment() {
        return payment;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Map<Product, Integer> getProductIntegerMap() {
        return productIntegerMap;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /*
    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }
     */

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setProductIntegerMap(Map<Product, Integer> productIntegerMap) {
        this.productIntegerMap = productIntegerMap;
    }

    public String shortToString(){
        return "ProductOrder: \n" +
                "----------------------------" + "\n" +
                "Id:                         " + this.id + "\n" +
                "Customer Id:                " + this.customerId + "\n" +
                "Date Created:               " + this.date.toString() + "\n" +
                "Billing Address:            " + this.billingAddress.shortToString() + "\n" +
                "Delivery Address:           " + this.deliveryAddress.shortToString() + "\n" +
                "Total Amount With Taxes:    " + String.format("%.2f", this.totalAmount.priceWithTaxes()) + "\n" +
                "Total Amount Without Taxes: " + String.format("%.2f", this.totalAmount.priceWithoutTaxes()) + "\n" +
                "Order State:                " +this.getOrderState() +"\n" +
                "----------------------------" + "\n" +
                "Products Ordered: \n" +
                productListToString() +
                "----------------------------" + "\n";
    }

    @Override
    public String toString(){
        return "ProductOrder: \n" +
                "----------------------------" + "\n" +
                "Id:                         " + this.id + "\n" +
                "Customer Id:                " + this.customerId + "\n" +
                "Date Created:               " + this.date.toString() + "\n" +
                "Billing Address:            " + this.billingAddress.shortToString() + "\n" +
                "Delivery Address:           " + this.deliveryAddress.shortToString() + "\n" +
                "Total Amount With Taxes:    " + String.format("%.2f", this.totalAmount.priceWithTaxes()) + "\n" +
                "Total Amount Without Taxes: " + String.format("%.2f", this.totalAmount.priceWithoutTaxes()) + "\n" +
                "Payment:                    " + this.payment + "\n" +
                "Shipment:                   " + this.shipment + "\n" +
                "Order State:                " +this.getOrderState() +"\n" +
                "----------------------------" + "\n" +
                "Products Ordered: \n" +
                productListToString() +
                "----------------------------" + "\n";
    }

    private String productListToString() {
        String productList = "";
        for (Product product: this.productIntegerMap.keySet()) {
            productList += String.format(this.productIntegerMap.get(product).toString() + " x " + product.getName() + "  " + product.getUnitPriceDouble() + "EUR"+ "\n");
        }

        return productList;
    }
}
