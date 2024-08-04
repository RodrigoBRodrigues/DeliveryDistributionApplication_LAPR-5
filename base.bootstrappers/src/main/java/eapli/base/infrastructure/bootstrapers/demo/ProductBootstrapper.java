package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.application.SpecifyNewProductController;
import eapli.base.product.domain.*;
import eapli.base.productCategory.application.RegisterNewCategoryController;
import eapli.base.productCategory.domain.AlphaNumericCode;
import eapli.base.productCategory.domain.Category;
import eapli.base.productCategory.repositories.CategoryRepository;
import eapli.framework.actions.Action;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductBootstrapper implements Action {

    private final CategoryRepository repo = PersistenceContext.repositories().categories();

    @Override
    public boolean execute() {
        AlphaNumericCode ac = AlphaNumericCode.valueOf("123");
        Description description = Description.valueOf("Smartphones Category");
        Designation designation = Designation.valueOf("Smartphones");

        registerCategory(ac,description,designation);

        AlphaNumericCode ac1 = AlphaNumericCode.valueOf("124");
        Description description1 = Description.valueOf("Laptops Category (Portable PCs");
        Designation designation1 = Designation.valueOf("Laptops");

        registerCategory(ac1,description1,designation1);

        AlphaNumericCode ac2 = AlphaNumericCode.valueOf("126");
        Description description2 = Description.valueOf("TV's Category(Televisions Category");
        Designation designation2 = Designation.valueOf("Tv's");

        registerCategory(ac2,description2,designation2);
        List<Category> cats = new ArrayList<>();

        Iterator<Category> cat = getCategories();
        while (cat.hasNext()){
            cats.add(cat.next());
        }


        registerProduct(cats.get(0), new StorageArea(3,2,1),Designation.valueOf("Radmi II"), "smarthphone_radmi_II.jpg",new ProductDescription("Smartphone Radmi II",
                "Smartphone MIAOXI Radmi II","Smarthphone Radmi II - ANDROID - RAM 4GB - 48 MP"),
                new Brand("MIAOXI"),new Price(219.0,270.0),new Reference("1111111"),
                new InternalCode("ic0000"),new ProductionCode("pc0000"),new Barcode("1122334455667"));

        registerProduct(cats.get(0),new StorageArea(3,2,2),Designation.valueOf("6W Plus"),"smartphone_6w_plus.png",new ProductDescription("Smarthphone 6W Plus",
                        "Smarthphone PEAR 6W Plus","Smarthphone 6W Plus - YOZ - RAM 2GB - 48 MP"),
                new Brand("PEAR"),new Price(500.0,625.0),new Reference("2222111"),
                new InternalCode("ic0007"),new ProductionCode("pc0007"),new Barcode("0022884455667"));

        registerProduct(cats.get(0),new StorageArea(3,2,3),Designation.valueOf("Galatic"),"smartphone_galatic.jpg",new ProductDescription("Smarthphone Galatic",
                        "Smarthphone ZAMZUNG Galatic","Smarthphone Galatic - ADROID - RAM 3GB - 50 MP"),
                new Brand("ZAMZUNG"),new Price(119.0,150.0),new Reference("1111110"),
                new InternalCode("ic0002"),new ProductionCode("pc0002"),new Barcode("1188334455667"));

        registerProduct(cats.get(1),new StorageArea(3,2,4),Designation.valueOf("Avillon"),"laptop_avillon.jpg",new ProductDescription("Laptop Avillon",
                        "Laptop PeHaga Avillon","Laptop Avillon - RAM 8GB - 512GB SSD"),
                new Brand("PeHaga"),new Price(749.0,900.0),new Reference("1111133"),
                new InternalCode("ic0030"),new ProductionCode("pc0030"),new Barcode("1122334455333"));

        registerProduct(cats.get(1),new StorageArea(3,2,5),Designation.valueOf("LiveBook"),"laptop_livebook.jpg",new ProductDescription("Laptop LiveBook",
                        "Laptop AÇOS LiveBook","Laptop LiveBook - RAM 8GB - 1TB SSD"),
                new Brand("AÇOS"),new Price(612.0,725.0),new Reference("2222181"),
                new InternalCode("ic0009"),new ProductionCode("pc0009"),new Barcode("0022884455997"));

        registerProduct(cats.get(1),new StorageArea(3,2,6),Designation.valueOf("TapperBook"),"laptop_tapperbook.jpg",new ProductDescription("Laptop TapperBook",
                        "Laptop PEAR TapperBook","Laptop TapperBook - RAM 8GB - 512GB SSD"),
                new Brand("PEAR"),new Price(900.0,1080.0),new Reference("1771110"),
                new InternalCode("ic0172"),new ProductionCode("pc0172"),new Barcode("1188337755667"));

        registerProduct(cats.get(2),new StorageArea(3,2,7),Designation.valueOf("QE55Q75AA"),"tv_QE55Q75AA.png",new ProductDescription("TV QE55Q75A",
                        "Tv ZAMZUNG QE55Q75AA","Tv QE55Q75AA - 55' - 4K UHD"),
                new Brand("ZAMZUNG"),new Price(749.0,999.0),new Reference("1811133"),
                new InternalCode("ic3030"),new ProductionCode("pc3030"),new Barcode("1120334455333"));

        registerProduct(cats.get(2),new StorageArea(3,2,8),Designation.valueOf("UE43TU7025"),"tv_UE43TU7025.jpg",new ProductDescription("TV UE43TU7025",
                        "Tv ZAMZUNG UE43TU7025","Tv UE43TU7025 - 43' - 4K UHD"),
                new Brand("ZAMZUNG"),new Price(349.0,479.0),new Reference("2722181"),
                new InternalCode("ic0709"),new ProductionCode("pc0709"),new Barcode("0722884455997"));

        registerProduct(cats.get(2),new StorageArea(3,2,9),Designation.valueOf("55UP78006AZDER"),"tv_55UP78006AZDER.jpg",new ProductDescription("TV 55UP78006AZDER",
                        "TV GL 55UP78006AZDER","TV 55UP78006AZDER - 55' - 4K UHD"),
                new Brand("GL"),new Price(519.0,639.0),new Reference("1771120"),
                new InternalCode("ic9172"),new ProductionCode("pc9172"),new Barcode("1188337755662"));
        return true;
    }

    private void registerCategory (AlphaNumericCode alphanumericCode, Description description, Designation name) {
        final RegisterNewCategoryController controller = new RegisterNewCategoryController();
        controller.registerCategory(alphanumericCode, description, name);
    }

    private Iterator<Category> getCategories() {
        return repo.findAll().iterator();
    }

    private void registerProduct(Category category,StorageArea storageArea, Designation name, String photoPath,ProductDescription description, Brand brand,
                                 Price price, Reference reference, InternalCode internalCode,
                                 ProductionCode productionCode, Barcode barcode) {
        final SpecifyNewProductController controller = new SpecifyNewProductController();
        controller.specifyNewProduct(category,storageArea,name,photoPath,description,brand,price,reference,internalCode,productionCode,barcode);
    }

}
