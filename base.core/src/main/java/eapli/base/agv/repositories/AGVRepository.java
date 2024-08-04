package eapli.base.agv.repositories;

import eapli.base.agv.domain.AGV;
import eapli.base.agv.domain.AGVState;
import eapli.base.agv.domain.DockingPoint;
import eapli.base.product.domain.StorageArea;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface AGVRepository extends DomainRepository<Long, AGV> {
    Iterable<AGV> findAllActive();
    boolean validateAgvBaseLocation (String dockingPoint);
    Iterable<AGV> findFreeAGVS();

    Optional<AGV> findById(final Long Number);

}
