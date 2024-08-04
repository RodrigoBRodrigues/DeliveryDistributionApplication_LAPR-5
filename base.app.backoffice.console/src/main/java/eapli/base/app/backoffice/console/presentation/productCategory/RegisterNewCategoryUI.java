package eapli.base.app.backoffice.console.presentation.productCategory;

import eapli.base.productCategory.application.RegisterNewCategoryController;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import javax.naming.Name;

public class RegisterNewCategoryUI extends AbstractUI {

    private final RegisterNewCategoryController theController= new RegisterNewCategoryController();
    @Override
    protected boolean doShow(){
         String alphanumericCodeString;
         String descriptionString;
         String nameString;
        do {


            alphanumericCodeString = Console.readLine("Category alphanumeric code:");
            if (alphanumericCodeString.isEmpty()){
                System.out.println("Alphanumeric Code must not be empty.");
            }
            if (alphanumericCodeString.length()>10){
                System.out.println("Alphanumeric Code size must be smaller than 10.");
            }
            if (!alphanumericCodeString.matches("^[a-zA-Z0-9]*$")){
                System.out.println("Alphanumeric Code must only contain numbers and/or character");
            }
        }while (alphanumericCodeString.isEmpty() || alphanumericCodeString.length()>10|| !alphanumericCodeString.matches("^[a-zA-Z0-9]*$"));
        do {
            descriptionString= Console.readLine("Category description:");
            if (descriptionString.length()<20||descriptionString.length()>50){
                System.out.println("Description must have between 20 and 50 characters.");
            }
        }while ( descriptionString.length()<20|| descriptionString.length()>50);
        do {
             nameString = Console.readLine("Category name:");
             if (nameString.isEmpty()){
                 System.out.println("Name cannot be empty.");
             }
        }while (nameString.isEmpty());
        AlphaNumericCode alphaNumericCode= AlphaNumericCode.valueOf(alphanumericCodeString);
        Description description= Description.valueOf(descriptionString);
        Designation name= Designation.valueOf(nameString);
        this.theController.registerCategory(alphaNumericCode,description,name);
        return false;
    }

    @Override
    public String headline() {
        return "Register New Category";
    }
}
