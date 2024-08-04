package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.agv.domain.AGV;
import eapli.base.agv.domain.AGVState;
import eapli.base.agv.domain.DockingPoint;
import eapli.base.agv.repositories.AGVRepository;
import eapli.base.product.domain.Product;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaAGVRepository extends JpaAutoTxRepository<AGV,Long, Long> implements AGVRepository {

    public JpaAGVRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }

    public JpaAGVRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    @Override
    public Iterable<AGV> findAllActive() {
        return match("e.systemUser.active = true");
    }

    @Override
    public boolean validateAgvBaseLocation(String dockingPoint) {
        final TypedQuery<AGV> query = entityManager().createQuery(
                "SELECT a FROM AGV a where a.agvDocks.agvDockId = :dockingPoint",AGV.class);
        query.setParameter("dockingPoint",dockingPoint);
        List<AGV> a = query.getResultList();
        return a.isEmpty();
    }

    @Override
    public Optional<AGV> findById(final Long number) {
        final Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        return matchOne("e.id=:number", params);
    }
    @Override
    public Iterable<AGV> findFreeAGVS(){
        final TypedQuery<AGV> query = super.createQuery(
                "SELECT a FROM Agv a WHERE a.agv_state = :state",AGV.class);
        query.setParameter("state", AGVState.FREE);
        List<AGV> a  = query.getResultList();
        return a;
    }



}
