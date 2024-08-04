package eapli.base.productCategory.application;

import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;

@UseCaseController
public class RegisterNewCategoryController {

    private final CategoryRepository repository = PersistenceContext.repositories().categories();

    public Category registerCategory(AlphaNumericCode alphanumericCode, Description description, Designation name){

        final Category newCategory= new Category(alphanumericCode,description,name);
        return repository.save(newCategory);
    }
}
