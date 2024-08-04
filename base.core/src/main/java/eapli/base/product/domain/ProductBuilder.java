package eapli.base.product.domain;

import eapli.base.productCategory.domain.Category;
import eapli.framework.general.domain.model.Designation;

public class ProductBuilder {

    private Category category;
    private StorageArea storageArea;
    private String name;
    private String photoPath;
    private String shortDescription;
    private String extendedDescription;
    private String technicalDescription;
    private String brandName;
    private Double priceWiTaxes;
    private Double priceWoTaxes;
    private String reference;
    private String internalCode;
    private String productionCode;
    private String barcode;

    public ProductBuilder() {
    }

    public ProductBuilder(Category category, StorageArea storageArea, String name, String photoPath, String shortDescription, String extendedDescription, String technicalDescription, String brandName, Double priceWiTaxes, Double priceWoTaxes, String internalCode, String barcode) {
        this.storageArea = storageArea;
        this.category = category;
        this.name = name;
        this.photoPath = photoPath;
        this.shortDescription = shortDescription;
        this.extendedDescription = extendedDescription;
        this.technicalDescription = technicalDescription;
        this.brandName = brandName;
        this.priceWiTaxes = priceWiTaxes;
        this.priceWoTaxes = priceWoTaxes;
        this.internalCode = internalCode;
        this.barcode = barcode;
    }

    public ProductBuilder withReference(final String reference) {
        this.reference = reference;
        return this;
    }

    public ProductBuilder withProductionCode(final String productionCode) {
        this.productionCode = productionCode;
        return this;
    }

    public Product build() {
        return new Product(storageArea,category,Designation.valueOf(name), photoPath,
                new ProductDescription(shortDescription,extendedDescription,technicalDescription),
                new Brand(brandName),new Price(priceWoTaxes,priceWoTaxes),new Reference(reference),
                new InternalCode(internalCode), new ProductionCode(productionCode),
                new Barcode(barcode));
    }

}
