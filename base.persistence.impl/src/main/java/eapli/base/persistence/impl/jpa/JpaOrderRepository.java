package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.product.domain.Product;
import eapli.base.productOrder.domain.OrderState;
import eapli.base.productOrder.domain.ProductOrder;
import eapli.base.productOrder.repositories.OrderRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import org.hibernate.criterion.Order;

import javax.persistence.TypedQuery;
import java.util.*;

public class JpaOrderRepository extends JpaAutoTxRepository<ProductOrder,Long, Long>
        implements OrderRepository {

    public JpaOrderRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaOrderRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }

    @Override
    public Iterable<ProductOrder> findAllActive() {
        return match("e.id != NULL");
    }

    @Override
    public List<ProductOrder> findByState(OrderState state) { //TEMPORARY
        Iterable<ProductOrder> lProd = findAllActive();
        List<ProductOrder> rProd = new ArrayList<>();
        for (ProductOrder prod:lProd) {
            if(prod.getOrderState().name().equals(state.name())){
                rProd.add(prod);
            }
        }
        return rProd;
    }

    @Override
    public Optional<ProductOrder> findById(final Long number) {
        final Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        return matchOne("e.id=:number", params);
    }

    public Optional<ProductOrder> findByCustomerId(final Long number) {
        final Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        return matchOne("e.customer_id=:number", params);
    }
    @Override
    public Iterable<ProductOrder> findByDateAscAndState(OrderState orderState){
        final TypedQuery<ProductOrder> query = entityManager().createQuery("SELECT po FROM ProductOrder po  WHERE po.orderState = :orderstate order by po.date asc",ProductOrder.class);
        query.setParameter("orderstate",orderState);
        return query.getResultList();
    }
}

