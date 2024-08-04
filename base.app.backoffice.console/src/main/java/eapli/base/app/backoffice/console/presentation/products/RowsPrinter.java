package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.warehousemanagement.domain.Row;
import eapli.framework.visitor.Visitor;

public class RowsPrinter implements Visitor<Row> {

    @Override
    public void visit(final Row visitee){
        System.out.printf(visitee.rowPrinter());
    }
}
