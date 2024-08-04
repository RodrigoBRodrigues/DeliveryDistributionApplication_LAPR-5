package eapli.base.category.domain;

import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.productCategory.domain.Category;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import org.junit.Test;

public class CategoryTest {

    private final AlphaNumericCode code = AlphaNumericCode.valueOf("123ab");
    private final Description des = Description.valueOf("Smartphones Category");
    private final Designation designation = Designation.valueOf("Smartphones");
    private final Category CATEGORY = new Category(code,des,designation);

    @Test(expected = IllegalArgumentException.class)
    public void ensureAlphaNumericCodeNotNull(){
        Category category= new Category(null,des,designation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionNotNull(){
        Category category= new Category(code,null,designation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDesignationNotNull(){
        Category category= new Category(code,des,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAlphaNumericCodeNotEmpty(){
        Category category= new Category(AlphaNumericCode.valueOf(""),des,designation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionNotEmpty(){
        Category category= new Category(code, Description.valueOf(""),designation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDesignationNotEmpty(){
        Category category= new Category(code,des,Designation.valueOf(""));
    }

}
