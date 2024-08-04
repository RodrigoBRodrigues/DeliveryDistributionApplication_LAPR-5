package eapli.base.productOrder.application;

import eapli.base.clientusermanagement.domain.Address;
import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productOrder.domain.OrderBuilder;
import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.productOrder.repositories.OrderRepository;
import eapli.base.product.domain.Product;
import eapli.base.productcatalog.CheckProductCatalogService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.ArrayList;
import java.util.List;

public class CreateProductOrderController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private OrderBuilder OB;

    private CustomerRepository cRepo = PersistenceContext.repositories().customers();
    private OrderRepository oRepo = PersistenceContext.repositories().orders();

    private final CheckProductCatalogService productCatalogService= new CheckProductCatalogService();




    public CreateProductOrderController(){
        //authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.SALES_CLERK);
    }

    public List<Customer> getCustomerList(){
        List<Customer> actualList = new ArrayList<>();
        this.cRepo.findAll().iterator().forEachRemaining(actualList::add);
        return actualList;
    }

    public void createOrder(Long customerId, Address delivery, Address billing){
        this.OB = new OrderBuilder(customerId,delivery,billing);
    }

    public List<Product> getProductList(){
        List<Product> actualList = new ArrayList<>();
        productCatalogService.allProducts().iterator().forEachRemaining(actualList::add);
        return actualList;
    }

    public void addProductToOrder(Product product, int quantity){
        this.OB = this.OB.addProduct(product,quantity);
    }

    public String showOrderBuilder(){
        return this.OB.toString();
    }

    public ProductOrder saveOrder(){
        ProductOrder newProductOrder = this.OB.build();
        return this.oRepo.save(newProductOrder);
    }

    public void setOrderState(OrderState orderState){
        this.OB= this.OB.setOrderState(orderState);
    }

}
