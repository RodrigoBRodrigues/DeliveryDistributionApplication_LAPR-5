package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.product.domain.Brand;
import eapli.framework.visitor.Visitor;

public class BrandPrinter implements Visitor<Brand> {
    @Override
    public void visit(final Brand visitee){
        System.out.println(visitee.toString());
    }
}
