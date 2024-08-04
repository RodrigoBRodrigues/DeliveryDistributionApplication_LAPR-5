package eapli.base.clientusermanagement.domain;

import org.junit.Assert;
import org.junit.Test;


public class VatTest {
    @Test
    public void ensureVatMeetsMinimums() {
        Vat vat = new Vat("SK1234567890");
        Vat.vatMeetsMinimumRequirements(vat.vatId());
    }

    @Test(expected = IllegalStateException.class)
    public void ensureVatDoesNotMeetMinimums() {
        Vat vat = new Vat("SK12345678");
        Vat.vatMeetsMinimumRequirements(vat.vatId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotBeNull() {
        Vat vat = new Vat(null);
        Vat.vatMeetsMinimumRequirements(vat.vatId());
    }

    @Test
    public void valueOf() {
        Vat.valueOf("242572942");

    }

    @Test
    public void compareTo() {
        Vat vat = new Vat("SK0987654321");
        vat.compareTo(new Vat("SK0987654321"));
    }

    @Test
    public void testEquals() {
        Vat vat1 = new Vat("SK0987654321");
        Vat vat2 = new Vat("SK0987654321");
        vat1.equals(vat2);
    }

    @Test
    public void testEqualsOtherInstance() {
        Vat vat1 = new Vat("SK0987654321");
        Customer customer = new Customer();
        boolean actual = vat1.equals(customer);
        boolean expected = false;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        Vat vat1 = new Vat("SK0987654321");
        String actual = vat1.toString();
        String expected = "Vat{amount=SK0987654321}";
        Assert.assertEquals(expected, actual);

    }



























    @Test
    public void testORMConstructor() {
        Vat vat1 = new Vat();
    }
}