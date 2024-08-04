package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.app.backoffice.console.presentation.MainMenu;
import eapli.base.app.backoffice.console.presentation.productCategory.ProductCategoryPrinter;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.application.SpecifyNewProductController;
import eapli.base.product.domain.*;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productCategory.domain.Category;
import eapli.base.warehousemanagement.domain.Aisle;
import eapli.base.warehousemanagement.domain.Row;
import eapli.base.warehousemanagement.domain.Shelf;
import eapli.base.warehousemanagement.domain.WarehouseInfo;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class SpecifyNewProductUI extends AbstractUI {

    private final SpecifyNewProductController theController = new SpecifyNewProductController();
    private final ProductRepository repo = PersistenceContext.repositories().products();

    @Override
    protected boolean doShow() {

        Category theProductCategory;
        do {
            theProductCategory = selectCategory();
            if (theProductCategory == null) {
                MainMenu menu = new MainMenu();
                menu.mainLoop();
            }
        } while (theProductCategory == null);

        System.out.println("Product Location");

        WarehouseInfo warehouseInfo = null;
        boolean validation = false;

        try {
            warehouseInfo = new WarehouseInfo();
            validation = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("To specify a new product you need a warehouse plant for its location. Please upload one first.");
            validation = false;
        }
        if (validation) {
            StorageArea storageArea;
            Aisle theAisle;
            int aisleId;
            Row theRow;
            int rowId;
            Shelf theShelf;
            int shelfPosition;

            do {
                assert warehouseInfo != null;
                do {
                    theAisle = selectAisle(warehouseInfo);
                } while (theAisle == null);
                aisleId = theAisle.getId();

                do {
                    theRow = selectRow(warehouseInfo, theAisle);
                } while (theRow == null);
                rowId = theRow.getId();

                do {
                    theShelf = selectShelf(warehouseInfo, theRow, aisleId, rowId);
                } while (theShelf == null);
                shelfPosition = theShelf.getPosition();

                storageArea = new StorageArea(aisleId, rowId, shelfPosition);
                validation = repo.validateProductLocation(storageArea);
                if (!validation) {
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("There's already a product on this storage area. Please try again and choose other location.");
                    System.out.println("--------------------------------------------------------------------------------------");
                }
            } while (!validation);

            System.out.println(storageArea);
            String name;
            do {
                name = Console.readLine("Name");
                if (name.isEmpty()) {
                    System.out.println("This field can't be empty.");
                }
            } while (name.isEmpty());

            System.out.println("Photo (check the new window opened)");
            String currentDirectory = "photos";
            String photoPath;
            try {
                JFileChooser chooser = new JFileChooser(currentDirectory);
                chooser.showSaveDialog(null);
                photoPath = chooser.getSelectedFile().getName();
            } catch (HeadlessException a) {
                photoPath = "photos/" + Console.readLine("Insert photo name");
            }
            System.out.println(photoPath);

            String shortDescription;
            do {
                shortDescription = Console.readLine("Short Description");
                if (shortDescription.isEmpty() || shortDescription.length() > 30) {
                    System.out.println("This field can't be empty and must have a maximum of 30 chars.");
                }
            } while (shortDescription.isEmpty() || shortDescription.length() > 30);

            String extendedDescription;
            do {
                extendedDescription = Console.readLine("Extended Description");
                if (extendedDescription.length() < 20 || extendedDescription.length() > 100) {
                    System.out.println("This field must have a minimum of 20 chars and a maximum of 100 chars.");
                }
            } while (extendedDescription.length() < 20 || extendedDescription.length() > 100);

            String techDescription = Console.readLine("Technical Description");

            String brand;
            do {
                brand = Console.readLine("Brand");
                if (brand.isEmpty()) {
                    System.out.println("This field can't be empty.");
                }
                if (brand.length() > 50) {
                    System.out.println("Brand name must have a maximum of 50 chars.");
                }
            } while (brand.isEmpty() || brand.length() > 50);

            Double priceWoTaxes = Console.readDouble("Price without Taxes €");
            Double priceWiTaxes = Console.readDouble("Price with Taxes €");

            String barcode;
            do {
                barcode = Console.readLine("Barcode");
                if (barcode.isEmpty()) {
                    System.out.println("This field can´t be empty.");
                }
            } while (barcode.isEmpty());

            String internalCode;
            do {
                internalCode = Console.readLine("Internal Code");
                if (internalCode.isEmpty()) {
                    System.out.println("This field can't be empty.");
                }
                if (!internalCode.matches("^[a-zA-Z0-9]*$")) {
                    System.out.println("Internal Code must be alphanumeric.");
                }
                if (internalCode.length() > 23) {
                    System.out.println("Internal Code must have a maximum of 23 chars.");
                }
            } while (internalCode.isEmpty() || !internalCode.matches("^[a-zA-Z0-9]*$") || internalCode.length() > 23);

            ProductBuilder productBuilder = new ProductBuilder(theProductCategory, storageArea, name, photoPath, shortDescription, extendedDescription, techDescription,
                    brand, priceWiTaxes, priceWoTaxes, internalCode, barcode);

            String productionCode;
            do {
                productionCode = Console.readLine("Production Code");
                if (!productionCode.matches("^[a-zA-Z0-9]*$") && !productionCode.isEmpty()) {
                    System.out.println("Production Code must be alphanumeric.");
                }
                if (productionCode.length() > 23) {
                    System.out.println("Production Code must have a maximum of 23 chars.");
                }
            } while (!productionCode.matches("^[a-zA-Z0-9]*$") && !productionCode.isEmpty() || productionCode.length() > 23);

            productBuilder.withProductionCode(productionCode);

            String reference;
            do {
                reference = Console.readLine("Reference");
                if (!reference.matches("^[a-zA-Z0-9]*$") && !reference.isEmpty()) {
                    System.out.println("Reference must be alphanumeric.");
                }
                if (reference.length() > 23) {
                    System.out.println("Reference must have a maximum of 23 chars.");
                }
            } while (!reference.matches("^[a-zA-Z0-9]*$") && !reference.isEmpty() || reference.length() > 23);

            productBuilder.withReference(reference);

            theController.specifyNewProduct(theProductCategory, storageArea, Designation.valueOf(name), photoPath, new ProductDescription(shortDescription,
                            extendedDescription, techDescription), new Brand(brand), new Price(priceWoTaxes, priceWiTaxes),
                    new Reference(reference), new InternalCode(internalCode), new ProductionCode(productionCode),
                    new Barcode(barcode));
        }
        return true;
    }


    @Override
    public String headline() {
        return "Specify New Product";
    }

    private Category selectCategory() {
        final Iterable<Category> productCategories = this.theController.categories();
        final SelectWidget<Category> selector = new SelectWidget<>("Product Categories:", productCategories,
                new ProductCategoryPrinter());
        selector.show();
        return selector.selectedElement();
    }

    public Aisle selectAisle(WarehouseInfo warehouseInfo) {
        assert warehouseInfo != null;
        final Iterable<Aisle> aisles = warehouseInfo.getAisles();
        final SelectWidget<Aisle> aisleSelector = new SelectWidget<>("Aisles: ", aisles, new AislePrinter());
        aisleSelector.show();
        return aisleSelector.selectedElement();
    }

    private Row selectRow(WarehouseInfo warehouseInfo, Aisle theAisle) {
        assert warehouseInfo != null;
        Iterable<Row> rows = warehouseInfo.getRows(theAisle);
        final SelectWidget<Row> rowsSelector = new SelectWidget<>("Rows: ", rows, new RowsPrinter());
        rowsSelector.show();
        return rowsSelector.selectedElement();
    }

    private Shelf selectShelf(WarehouseInfo warehouseInfo, Row theRow, int aisleId, int rowId) {
        final Iterable<Shelf> shelves = warehouseInfo.getShelves(theRow);
        final SelectWidget<Shelf> shelfSelector = new SelectWidget<>("Shelves: ", shelves, new ShelfPrinter(aisleId, rowId));
        shelfSelector.show();
        return shelfSelector.selectedElement();
    }
}