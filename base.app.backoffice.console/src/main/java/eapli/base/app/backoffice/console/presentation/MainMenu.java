/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation;

import eapli.base.app.backoffice.console.presentation.agv.CreateAGVUI;
import eapli.base.app.backoffice.console.presentation.agv.UpdateOrderStateToBePreparedUI;
import eapli.base.app.backoffice.console.presentation.clientuser.CheckCustomerOpenOrdersUI;
import eapli.base.app.backoffice.console.presentation.dashboard.AGVsDashboardUI;
import eapli.base.app.backoffice.console.presentation.order.CreateProductOrderUI;
import eapli.base.app.backoffice.console.presentation.order.UpdateOrderStateDispatchedUI;
import eapli.base.app.backoffice.console.presentation.order.UpdateOrderStateReadyUI;
import eapli.base.app.backoffice.console.presentation.productCategory.RegisterNewCategoryUI;
import eapli.base.app.backoffice.console.presentation.clientuser.AddCustomerUI;
import eapli.base.app.backoffice.console.presentation.productcatalog.CheckProductCatalogUI;
import eapli.base.app.backoffice.console.presentation.products.SpecifyNewProductUI;
import eapli.base.app.backoffice.console.presentation.questionnaire.AnswerToSurvey;
import eapli.base.app.backoffice.console.presentation.questionnaire.DefineSurveyUI;
import eapli.base.app.backoffice.console.presentation.questionnaire.QuestionnaireReportUI;
import eapli.base.app.backoffice.console.presentation.warehouse.WarehouseUI;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.base.questionnaire.domain.Answer;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import java.io.FileNotFoundException;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int CUSTOMERS_OPTION = 12;
    private static final int PRODUCT_CATALOG_OPTION = 5;
    private static final int TRACEABILITY_OPTION = 6;
    private static final int MEALS_OPTION = 7;
    private static final int REPORTING_DISHES_OPTION = 8;
    // CATEGORY
    private static final int REGISTER_NEW_CATEGORY = 1;
    private static final int MAIN_MENU_CATEGORY = 3;
    // PRODUCTS
    private static final int SPECIFY_NEW_PRODUCT = 1;
    private static final int CREATE_PRODUCT_ORDER = 2;
    private static final int UPDATE_ORDERSTATE_DISPATCHED = 3;
    private static final int CHECK_CUSTOMER_OPEN_ORDERS = 4;
    // WAREHOUSE
    private static final int SET_UP_NEW_WAREHOUSE = 1;
    private static final int WAREHOUSE = 2;
    private static final int CREATE_AGV = 2;
    private static final int UPDATE_ORDERSTATE_TO_BE_PREPARED = 3;
    private static final int UPDATE_ORDERSTATE_READY = 4;


    // SALES_MANAGER
    private static final int DEFINE_NEW_QUESTIONNAIRE = 1;
    private static final int SALES_MANAGER = 2;
    private static final int ANSWER_TO_SURVEY = 2;


    //Product Catalog
    private static final int CHECK_PRODUCT_CATALOG = 1;

    private static final int CHECK_PRODUCT_CATALOG_CATEGORY = 2;
    private static final int CHECK_PRODUCT_CATALOG_BRAND = 3;
    private static final int CHECK_PRODUCT_CATALOG_DESCRIPTION = 4;

    private static final int CHECK_PRODUCT_CATALOG_BRAND_CATEGORY = 5;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        //Sales Clerk Menu
        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK)) {
            final Menu salesClerkMenu = buildSalesClerkMenu();
            mainMenu.addSubMenu(USERS_OPTION, salesClerkMenu);

            final Menu registerCategoryMenu = BuildCategoryMenu();
            mainMenu.addSubMenu(MAIN_MENU_CATEGORY, registerCategoryMenu);

            final Menu customerMenu = buildUsersMenu();
            mainMenu.addSubMenu(CUSTOMERS_OPTION, customerMenu);

            final Menu productCatalogMenu = buildProductCatalogMenu();
            mainMenu.addSubMenu(PRODUCT_CATALOG_OPTION, productCatalogMenu);

        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.WAREHOUSE_EMPLOYEE)) {
            final Menu warehouseEmployeeMenu = buildWarehouseEmployeeMenu();
            mainMenu.addSubMenu(WAREHOUSE, warehouseEmployeeMenu);
            final  Menu dashboard = buildDashboardMenu();
            mainMenu.addSubMenu(3,dashboard);

        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_MANAGER)) {
            final Menu salesManagerMenu = buildSalesManagerMenu();
            mainMenu.addSubMenu(SALES_MANAGER, salesManagerMenu);

        }


        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }
    private Menu buildDashboardMenu() {
        final Menu menu = new Menu("AGVs");
        menu.addItem(1,"AGVs Dashboard",new AGVsDashboardUI()::show);
        return menu;
    }
    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");

        menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit",
                new ShowMessageAction("Not implemented yet"));
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Customers >");

        menu.addItem(ADD_USER_OPTION, "Add Customer", new AddCustomerUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Customers", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate Customer", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }


    private Menu buildSalesClerkMenu() {
        final Menu menu = new Menu("Products >");

        menu.addItem(SPECIFY_NEW_PRODUCT, "Specify New Product", new SpecifyNewProductUI()::show);
        menu.addItem(CREATE_PRODUCT_ORDER, "Place New Product ProductOrder", new CreateProductOrderUI()::show);
        menu.addItem(UPDATE_ORDERSTATE_DISPATCHED, "Update DISPATCHED product order", new UpdateOrderStateDispatchedUI()::show);
        menu.addItem(CHECK_CUSTOMER_OPEN_ORDERS, "Check customer's open Product Orders", new CheckCustomerOpenOrdersUI()::show);

        final Menu menuCategory = new Menu("Categories >");
        menuCategory.addItem(REGISTER_NEW_CATEGORY, "Register New Category", new RegisterNewCategoryUI()::show);
        return menu;
    }

    private Menu buildWarehouseEmployeeMenu() {
        final Menu menu = new Menu("Warehouse Management Menu");
        menu.addItem(SET_UP_NEW_WAREHOUSE, "Set Up New Warehouse", new WarehouseUI()::show);

        try {
            menu.addItem(CREATE_AGV, "Configure New AGV", new CreateAGVUI()::show);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        menu.addItem(UPDATE_ORDERSTATE_TO_BE_PREPARED, "Update TO BE PREPARED product order", new UpdateOrderStateToBePreparedUI() :: show);
        menu.addItem(UPDATE_ORDERSTATE_READY, "Update READY product order", new UpdateOrderStateReadyUI() :: show);

        return menu;
    }

    private Menu buildSalesManagerMenu() {
        final Menu menu = new Menu("Sales Manager Menu");
        menu.addItem(DEFINE_NEW_QUESTIONNAIRE, "Define new Questionnaire", new DefineSurveyUI()::show);
        menu.addItem(ANSWER_TO_SURVEY, "Answer to Questionnaire", new AnswerToSurvey()::show);
        menu.addItem(3,"Questionnaire Report",new QuestionnaireReportUI()::show);
        return menu;
    }

    private Menu buildCustomerMenu() {
        final Menu menu = new Menu("Customer Menu");
        menu.addItem(2, "Answer to Survey", new AnswerToSurvey()::show);
        return menu;
    }

    private Menu BuildCategoryMenu() {
        final Menu menuCategory = new Menu("Categories >");
        menuCategory.addItem(REGISTER_NEW_CATEGORY, "Register New Category", new RegisterNewCategoryUI()::show);
        return menuCategory;
    }

    private Menu buildProductCatalogMenu() {
        final Menu menuProductCatalog = new Menu("Product Catalog >");
        menuProductCatalog.addItem(CHECK_PRODUCT_CATALOG, "Check All Items", new CheckProductCatalogUI(CHECK_PRODUCT_CATALOG)::show);
        menuProductCatalog.addItem(CHECK_PRODUCT_CATALOG_CATEGORY, "Check Based On Category", new CheckProductCatalogUI(CHECK_PRODUCT_CATALOG_CATEGORY)::show);
        menuProductCatalog.addItem(CHECK_PRODUCT_CATALOG_BRAND, "Check Based On Brand", new CheckProductCatalogUI(CHECK_PRODUCT_CATALOG_BRAND)::show);
        menuProductCatalog.addItem(CHECK_PRODUCT_CATALOG_DESCRIPTION, "Check Based On Description", new CheckProductCatalogUI(CHECK_PRODUCT_CATALOG_DESCRIPTION)::show);
        menuProductCatalog.addItem(CHECK_PRODUCT_CATALOG_BRAND_CATEGORY, "Check Based On Brand and Categpry", new CheckProductCatalogUI(CHECK_PRODUCT_CATALOG_BRAND_CATEGORY)::show);
        return menuProductCatalog;
    }


}
