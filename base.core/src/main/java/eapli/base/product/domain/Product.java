package eapli.base.product.domain;

import eapli.base.productCategory.domain.Category;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Preconditions;
import javax.persistence.*;

@Entity
@Table
public class Product implements AggregateRoot<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Category category;

    @Column(name = "name", nullable = false)
    private Designation name;

    private  String photoPath;

    @Embedded
    @Column(name = "description")
    private ProductDescription description;

    @Embedded
    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    @Column(name = "reference")
    private Reference reference;
    @Embedded
    @Column(name = "internalCode", nullable = false)
    private InternalCode internalCode;
    @Embedded
    @Column(name = "productionCode")
    private ProductionCode productionCode;
    @Embedded
    @Column(name = "barcode", nullable = false)
    private Barcode barcode;

    private boolean active;

    @Embedded
    @Column(name = "storageArea")
    private StorageArea storageArea;

    public Product() {
    }

    public Product(StorageArea storageArea, Category category,Designation name, String photoPath, ProductDescription description, Brand brand, Price price,
                   Reference reference, InternalCode internalCode, ProductionCode productionCode,
                   Barcode barcode) {
        Preconditions.noneNull(category,name,photoPath,brand,price,internalCode,barcode);
        this.storageArea = storageArea;
        this.category = category;
        this.name = name;
        this.photoPath = photoPath;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.reference = reference;
        this.internalCode = internalCode;
        this.productionCode = productionCode;
        this.barcode = barcode;
        this.active = true;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Long identity() {
        return id;
    }

    public Designation getName(){
        return name;
    }

    public String getBarcodeString(){return this.barcode.toString();}

    public String getShortDescriptionString(){return this.description.shortDescription();}
    public String getExtendedDescriptionString(){return this.description.extendedDescription();}
    public String getTechnicalDescriptionString(){return this.description.technicalDescription();}


    public String getBrandString(){return this.brand.toString();}

    public String getCategoryString(){return this.category.toString();}

    public double getUnitPriceDouble(){return this.price.priceWithTaxes();
    }

    public Price getPrice(){
        return this.price;
    }

    public StorageArea getStorageArea() {
        return storageArea;
    }

    @Override
    public String toString(){
        return String.format("Category:%s\nName:%s\nShort Description:%s\nExtended Description:%s\nTechincal Description:%s\nBrand:%s\nPrice without Taxes:%.2f\nPrice with Taxes:%.2f\nBarcode:%s\n",getCategoryString(),name.toString(),description.shortDescription(),description.extendedDescription(),description.technicalDescription(),getBrandString(),price.priceWithoutTaxes(),price.priceWithTaxes(),getBarcodeString());
    }
}
