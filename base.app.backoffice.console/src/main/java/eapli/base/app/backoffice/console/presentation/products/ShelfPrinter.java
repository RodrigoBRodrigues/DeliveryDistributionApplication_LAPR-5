package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.warehousemanagement.domain.Shelf;
import eapli.framework.visitor.Visitor;

public class ShelfPrinter implements Visitor<Shelf> {
    int aisleId;
    int rowId;

    public ShelfPrinter(int aisleId, int rowId) {
        this.aisleId = aisleId;
        this.rowId = rowId;
    }

    @Override
    public void visit(final Shelf visitee){
        System.out.print(visitee.shelfPrint(aisleId, rowId));
    }

}
