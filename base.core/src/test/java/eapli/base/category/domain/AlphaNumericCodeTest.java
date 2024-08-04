package eapli.base.category.domain;

import eapli.base.productCategory.domain.AlphaNumericCode;
import org.junit.Assert;
import org.junit.Test;

public class AlphaNumericCodeTest {


    @Test
    public void ensureAlphanumeric(){
        AlphaNumericCode alphaNumericCode= AlphaNumericCode.valueOf(".");
        Assert.assertNull(alphaNumericCode.toString());
    }
    @Test
    public void ensureLengthLessThan10(){
        AlphaNumericCode alphaNumericCode= AlphaNumericCode.valueOf("123456789011");
        Assert.assertNull(alphaNumericCode.toString());
    }
}
