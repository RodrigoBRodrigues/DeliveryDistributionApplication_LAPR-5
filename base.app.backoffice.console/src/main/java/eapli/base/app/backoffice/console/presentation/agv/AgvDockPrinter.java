package eapli.base.app.backoffice.console.presentation.agv;

import eapli.base.warehousemanagement.domain.AGVDocks;
import eapli.framework.visitor.Visitor;

public class AgvDockPrinter implements Visitor<AGVDocks> {


    @Override
    public void visit(AGVDocks visitee) {
        System.out.print(visitee.toString());
    }
}
