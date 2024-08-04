package eapli.base.app.backoffice.console.presentation.productCategory;

import eapli.base.productCategory.domain.Category;
import eapli.framework.visitor.Visitor;

public class ProductCategoryPrinter implements Visitor<Category> {
    @Override
    public void visit(Category visitee) {
        System.out.print(visitee.identity());
    }
}
