package eapli.base.productOrder.repositories;

import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

public interface OrderRepository extends DomainRepository<Long, ProductOrder> {
    default Optional<ProductOrder> findById(final Long number) {
        return ofIdentity(number);
    }

    public Iterable<ProductOrder> findAllActive();

    public Iterable<ProductOrder> findByState(OrderState state);

    public Iterable<ProductOrder> findByDateAscAndState(OrderState state);
}
