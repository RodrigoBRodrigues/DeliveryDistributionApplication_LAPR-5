package eapli.base.productcatalog;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Brand;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.List;

public class CheckProductCatalogService {
    private final AuthorizationService authz= AuthzRegistry.authorizationService();

    private final ProductRepository productRepository= PersistenceContext.repositories().products();

    private final CategoryRepository categoryRepository=PersistenceContext.repositories().categories();
    public Iterable<Product> allProducts(){
        return productRepository.findAll();
    }
    public Iterable<Product> allProductsWithoutRestrictions(int num){
        return productRepository.findAllProducts(num);
    }

    public Iterable<Product> allProductsWithCategory(String category,int num){
        return productRepository.findProductsWithCategory(category,num);
    }

    public Iterable<Product> allProductsWithBrand(String brand,int num){
        return productRepository.findProductsWithBrand(brand,num);
    }
    public Iterable<Product> allProductsWithBrandCategory(String brand,String category,int num){
        return productRepository.findProductsWithBrandCategory(brand,category,num);
    }

    public Iterable<Category> allCategories(){
        return categoryRepository.findAll();
    }
    public Iterable<Brand> allBrands(){
        return productRepository.findAllBrands();
    }
}
