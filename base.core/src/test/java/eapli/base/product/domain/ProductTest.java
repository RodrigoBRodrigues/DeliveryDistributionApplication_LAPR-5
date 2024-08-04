package eapli.base.product.domain;

import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.domain.CustomerBuilder;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.productCategory.domain.Category;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ProductTest {


    private final AlphaNumericCode code = AlphaNumericCode.valueOf("123ab");
    private final Description des = Description.valueOf("Smartphones Category");
    private final Designation designation = Designation.valueOf("Smartphones");
    private final Category CATEGORY = new Category(code,des,designation);
    private final StorageArea STORAGEAREA = new StorageArea(0,0,0);
    private static final Designation DESIGNATION = Designation.valueOf("Radmi II");
    private static final String PHOTO = "smarthphone_radmi_II.jpg";
    private static final ProductDescription DESCRIPTION = new ProductDescription("Smartphone Radmi II","Smartphone MIAOXI Radmi II","Smarthphone Radmi II - ANDROID - RAM 4GB - 48 MP");
    private static final Brand BRAND = new Brand("MIAOXI");
    private static final Price PRICE = new Price(219.0,270.0);
    private static final Reference REFERENCE = new Reference("1111111");
    private static final InternalCode INTERNAL_CODE = new InternalCode("ic0000");
    private static final ProductionCode PRODUCTION_CODE = new ProductionCode("pc0000");
    private static final Barcode BARCODE = new Barcode("1122334455667");


    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed() {
        new Product(null, null,null, null,null, null, null, null, null, null, null);
    }

    @Test
    public void product() {
        new Product(STORAGEAREA,CATEGORY,DESIGNATION,PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductMustHavePrice() {
        new Product(STORAGEAREA,CATEGORY,DESIGNATION,PHOTO,DESCRIPTION,BRAND,null,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductMustHaveCategory() {
        new Product(STORAGEAREA,null,DESIGNATION,PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductMustHaveInternalCode() {
        new Product(STORAGEAREA,CATEGORY,DESIGNATION,PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,null,PRODUCTION_CODE,BARCODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductMustHaveBrand() {
        new Product(STORAGEAREA,CATEGORY,DESIGNATION,PHOTO,DESCRIPTION,null,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductMustHaveName() {
        new Product(STORAGEAREA,CATEGORY,Designation.valueOf(" "),PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
        new Product(STORAGEAREA,CATEGORY,null,PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductMustHaveBarcode() {
        new Product(STORAGEAREA,CATEGORY,DESIGNATION,PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,null);
    }

    @Test
    public void ensureProductIsTheSameAsItsInstance() {
        Product p =new Product(STORAGEAREA,CATEGORY,DESIGNATION,PHOTO,DESCRIPTION,BRAND,PRICE,REFERENCE,INTERNAL_CODE,PRODUCTION_CODE,BARCODE);
        final boolean expected = p.sameAs(p);
        assertTrue(expected);
    }
}