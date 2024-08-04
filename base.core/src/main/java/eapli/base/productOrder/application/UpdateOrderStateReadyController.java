package eapli.base.productOrder.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.productOrder.repositories.OrderRepository;

public class UpdateOrderStateReadyController {

    private OrderRepository oRepo = PersistenceContext.repositories().orders();

    public UpdateOrderStateReadyController() {

    }

    public Iterable<ProductOrder> getListProductOrders() {
        return this.oRepo.findByState(OrderState.READY_FOR_CARRIER);
    }

    public boolean changeOrderState(ProductOrder prod){
        prod.setOrderState(OrderState.DISPATCHED);
        return prod.getOrderState().equals(OrderState.DISPATCHED);
    }

    public ProductOrder save(ProductOrder nProd, ProductOrder oProd){
        this.oRepo.remove(oProd);
        return this.oRepo.save(nProd);
    }

    public String printDispatchedProductOrders(){
        Iterable<ProductOrder> lProd = this.oRepo.findByState(OrderState.DISPATCHED);
        int i = 1;
        String result = "";
        for (ProductOrder prod: lProd) {
            result += i + " - ProductOrder#" + prod.identity() + ", " + prod.getOrderState().toString() + "\n";
            i++;
        }
        return result;
    }
}
