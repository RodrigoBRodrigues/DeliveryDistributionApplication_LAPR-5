package eapli.base.productCategory.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;

public class ListCategories {

    public final CategoryRepository categoryRepository = PersistenceContext.repositories().categories();

    public Iterable<Category> allCategories() {
        return categoryRepository.findAll();
    }
}
