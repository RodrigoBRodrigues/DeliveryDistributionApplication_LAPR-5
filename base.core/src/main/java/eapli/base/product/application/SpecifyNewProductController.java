package eapli.base.product.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.*;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productCategory.application.ListCategories;
import eapli.base.productCategory.domain.Category;
import eapli.framework.general.domain.model.Designation;

public class SpecifyNewProductController {

    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final ListCategories svcCategories = new ListCategories();

    public Product specifyNewProduct (Category category,StorageArea storageArea,Designation name, String photoPath, ProductDescription description, Brand brand,
                                      Price price, Reference reference, InternalCode internalCode,
                                      ProductionCode productionCode, Barcode barcode) {
        return productRepository.save(new Product(storageArea,category,name,photoPath,description,brand,price,reference,internalCode,productionCode,barcode));
    }

    public Iterable<Category> categories () {
        return svcCategories.allCategories();
    }

}
