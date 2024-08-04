package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

public class JpaCategoryRepository extends JpaAutoTxRepository<Category,Long,String> implements CategoryRepository {

    public JpaCategoryRepository(final TransactionalContext autoTx){ super(autoTx,"name");}

    public JpaCategoryRepository(final String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(),"name");
    }

    @Override
    public Iterable<Category> activeCategories(){
        return match("e.active=true");
    }
}
