package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.warehousemanagement.domain.Aisle;
import eapli.framework.visitor.Visitor;

public class AislePrinter implements Visitor<Aisle> {

    @Override
    public void visit(final Aisle visitee){
        System.out.print(visitee.aislePrinter());
    }

}
