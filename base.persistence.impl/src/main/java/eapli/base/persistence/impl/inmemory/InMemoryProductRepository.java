package eapli.base.persistence.impl.inmemory;

import eapli.base.product.domain.Brand;
import eapli.base.product.domain.Product;
import eapli.base.product.domain.StorageArea;
import eapli.base.product.repositories.ProductRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainAutoNumberRepository;

public class InMemoryProductRepository extends InMemoryDomainAutoNumberRepository<Product>
        implements ProductRepository {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";

    static {
        InMemoryInitializer.init();
    }

    @Override
    public boolean validateProductLocation(StorageArea storageArea) { throw new UnsupportedOperationException(); }

    @Override
    public Iterable<Product> findAllProducts(int num){
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterable<Product> findProductsWithCategory(String categoryName,int num) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Product> findProductsWithBrand(String brand,int num) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Product> findProductsWithShelveNumber(int aisleId, int rowId, int shelveNumber) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Product> findProductsWithBrandCategory(String brand,String category,int num){
        throw new UnsupportedOperationException();
    }
    @Override
    public String createSortMode(int num){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Brand> findAllBrands(){
        throw new UnsupportedOperationException();
    }

}
