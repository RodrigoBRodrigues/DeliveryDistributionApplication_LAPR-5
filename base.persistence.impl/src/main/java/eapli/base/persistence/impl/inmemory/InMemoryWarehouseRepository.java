package eapli.base.persistence.impl.inmemory;

import eapli.base.product.domain.Brand;
import eapli.base.product.domain.Product;
import eapli.base.product.domain.StorageArea;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.base.warehousemanagement.repositories.WarehouseRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainAutoNumberRepository;

public class InMemoryWarehouseRepository extends InMemoryDomainAutoNumberRepository<Warehouse>
        implements WarehouseRepository {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";

    static {
        InMemoryInitializer.init();
    }


    @Override
    public Iterable<Warehouse> findAllActive() {
        return match(e -> e.identity() != null);
    }
}
