package eapli.base.productCategory.repositories;

import eapli.base.productCategory.domain.Category;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.domain.repositories.LockableDomainRepository;

public interface CategoryRepository extends DomainRepository<String, Category>, LockableDomainRepository<String,Category> {

    Iterable<Category> activeCategories();

}
