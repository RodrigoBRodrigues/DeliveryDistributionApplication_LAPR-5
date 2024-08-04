package eapli.base.persistence.impl.jpa;

import eapli.base.product.domain.Brand;
import eapli.base.product.domain.Product;
import eapli.base.product.domain.StorageArea;
import eapli.base.product.repositories.ProductRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.TypedQuery;
import java.util.List;


public class JpaProductRepository extends BasepaRepositoryBase <Product,Long,Long>
implements ProductRepository {

    public JpaProductRepository() {
        super("id");
    }

    public boolean validateProductLocation (StorageArea location) {
        final TypedQuery<Product> query = super.createQuery(
                "SELECT p FROM Product p WHERE p.storageArea = :location1",Product.class);
        query.setParameter("location1",location);
        List<Product> p  = query.getResultList();
        return p.size() <= 0;
    }

    @Override
    public Iterable<Product> findAllProducts(int num){
        final TypedQuery<Product> query=entityManager().createQuery("SELECT p FROM Product p"+createSortMode(num),Product.class);
        return query.getResultList();
    }
    @Override
    public Iterable<Product> findProductsWithCategory(String categoryName,int num) {
        Designation categoryDesignation= Designation.valueOf(categoryName);
        final TypedQuery<Product> query = entityManager().createQuery("SELECT p FROM Product p JOIN p.category cat WHERE cat.name = :name"+createSortMode(num),Product.class);
        query.setParameter("name",categoryDesignation);
        return query.getResultList();

    }
    @Override
    public Iterable<Product> findProductsWithBrand(String brand,int num){
        Brand brandName= new Brand(brand);
        final TypedQuery<Product> query= entityManager().createQuery("SELECT p from Product  p WHERE p.brand = :brand "+createSortMode(num),Product.class);
        query.setParameter("brand",brandName);
        return query.getResultList();
    }

    @Override
    public Iterable<Product> findProductsWithShelveNumber(int aisleId, int rowId, int shelveNumber) {
        final TypedQuery<Product> query= entityManager().createQuery("SELECT p from Product  p WHERE p.storageArea.shelfNumber = :shelveNumber " +
                                                                        "AND  p.storageArea.aisleId = :aisleId AND p.storageArea.rowId = :rowId"
                                                                        ,Product.class);
        query.setParameter("aisleId",aisleId);
        query.setParameter("rowId",rowId);
        query.setParameter("shelveNumber",shelveNumber);
        return query.getResultList();
    }


    @Override
    public Iterable<Product> findProductsWithBrandCategory(String brand,String category,int num){
        Brand brandName= new Brand(brand);
        Designation categoryDesignation= Designation.valueOf(category);
        final TypedQuery<Product> query= entityManager().createQuery("SELECT p from Product  p JOIN p.category cat where  cat.name =:name AND p.brand = :brand "+createSortMode(num),Product.class);
        query.setParameter("name",categoryDesignation);
        query.setParameter("brand",brandName);
        return query.getResultList();
    }
    @Override
    public Iterable<Brand> findAllBrands(){
    final TypedQuery<Brand> query= entityManager().createQuery("select  DISTINCT p.brand from Product p",Brand.class);
    return query.getResultList();
    }


    @Override
    public String createSortMode(int num){
        if (num==NO_SORTING){
            return "";
        }
        if (num==SORT_BY_NAME){
            return " ORDER BY p.name";
        }
        if (num==SORT_BY_PRICE){
            return " ORDER BY p.price";
        }
        return null;
    }

}
