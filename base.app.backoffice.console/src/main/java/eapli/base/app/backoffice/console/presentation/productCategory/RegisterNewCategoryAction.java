package eapli.base.app.backoffice.console.presentation.productCategory;

import eapli.base.app.backoffice.console.presentation.productCategory.RegisterNewCategoryUI;
import eapli.framework.actions.Action;

public class RegisterNewCategoryAction  implements Action {
    @Override
    public boolean execute(){
        return new RegisterNewCategoryUI().show();
    }
}
