package eapli.base.product.repositories;

import eapli.base.product.domain.Brand;
import eapli.base.product.domain.Product;
import eapli.base.product.domain.StorageArea;
import eapli.framework.domain.repositories.DomainRepository;



public interface ProductRepository extends DomainRepository<Long, Product> {

    static final public int NO_SORTING=1;

    static final public int SORT_BY_NAME=2;

    static final public int SORT_BY_PRICE=3;

    boolean validateProductLocation (StorageArea storageArea);
    Iterable<Product> findAllProducts(int num);
    Iterable<Product> findProductsWithCategory(String category,int num);

    Iterable<Product> findProductsWithBrand(String brand,int num);

    Iterable<Product> findProductsWithShelveNumber(int aisleId, int rowId, int shelveNumber);


    Iterable<Product> findProductsWithBrandCategory(String brand,String category,int num);


     String createSortMode(int num);

     Iterable<Brand> findAllBrands();
}
