package eapli.base.persistence.impl.inmemory;

import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.productOrder.repositories.OrderRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainAutoNumberRepository;

public class InMemoryProductOrderRepository extends InMemoryDomainAutoNumberRepository<ProductOrder>
        implements OrderRepository {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<ProductOrder> findAllActive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<ProductOrder> findByState(OrderState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<ProductOrder> findByDateAscAndState(OrderState orderState){throw new UnsupportedOperationException();}
}
