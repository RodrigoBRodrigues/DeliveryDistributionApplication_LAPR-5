package eapli.base.persistence.impl.inmemory;

import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryCategoryRepository extends InMemoryDomainRepository<Category,String> implements CategoryRepository {
static {
InMemoryInitializer.init();
}
@Override
    public Iterable<Category> activeCategories(){
return match(Category::isActive);
}
}
