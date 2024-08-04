package eapli.base.productOrder.domain;

import eapli.base.clientusermanagement.domain.Address;
import eapli.base.product.domain.Price;
import eapli.base.product.domain.Product;
import eapli.framework.domain.model.DomainFactory;
import net.bytebuddy.asm.Advice;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderBuilder implements DomainFactory<ProductOrder> {

    private Long customerId;
    private Address billingAddress = null;
    private Address deliveryAddress = null;
    private Price totalAmount = new Price((double) 0, (double) 0);
    private Map<Product, Integer> productIntegerMap = new HashMap<>();
    //both payment and shipment defaulted to PAYPAL and STANDARD respectively since they aren't of much use for the current SPRINT
    private Payment payment = Payment.PAYPAL;
    private Shipment shipment = Shipment.STANDARD;
    private OrderState orderState = OrderState.REGISTERED;

    public OrderBuilder(){

    }

    public OrderBuilder(final Long customerId, final Address deliveryAddress, final Address billingAddress){
        this.customerId = customerId;
        this.deliveryAddress= deliveryAddress;
        this.billingAddress = billingAddress;
    }

    public OrderBuilder addProduct(Product product, Integer quantity){
        if(this.productIntegerMap.containsKey(product)){
            quantity  += this.productIntegerMap.get(product);
            this.productIntegerMap.put(product,quantity);
            this.totalAmount = calculateTotalAmount();
            return this;
        }
        this.productIntegerMap.put(product,quantity);
        this.totalAmount = calculateTotalAmount();
        return this;
    }

    private Price calculateTotalAmount(){
        this.totalAmount.clear();
        Double d = 0.0;
        Double d2 = 0.0;
        for (Product p : productIntegerMap.keySet()) {
            d = d + p.getPrice().priceWithTaxes() * productIntegerMap.get(p);
            d2 = d2 + p.getPrice().priceWithoutTaxes() * productIntegerMap.get(p);

        }
        Price price = new Price(d2,d);
        this.totalAmount = price;
        return this.totalAmount;
    }

    @Override
    public ProductOrder build() {
        return new ProductOrder(LocalDateTime.now().withSecond(0).withNano(0),
                this.customerId, this.deliveryAddress,
                this.billingAddress, this.productIntegerMap,
                this.totalAmount, this.payment, this.shipment, this.orderState);
    }

    @Override
    public String toString(){
        return "ProductOrder: \n" +
                "----------------------------" + "\n" +
                "Customer Id:                " + this.customerId + "\n" +
                "Billing Address:            " + this.billingAddress.shortToString() + "\n" +
                "Delivery Address:           " + this.deliveryAddress.shortToString() + "\n" +
                "Total Amount With Taxes:    " + String.format("%.2f", this.totalAmount.priceWithTaxes()) + "\n" +
                "Total Amount Without Taxes: " + String.format("%.2f", this.totalAmount.priceWithoutTaxes()) + "\n" +
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

    public OrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }
}
