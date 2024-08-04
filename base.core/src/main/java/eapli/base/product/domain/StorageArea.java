package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class StorageArea implements ValueObject {

    private int aisleId;
    private int rowId;
    private int shelfNumber;

    public StorageArea(int aisleId, int rowId, int shelfNumber) {
        this.aisleId = aisleId;
        this.rowId = rowId;
        this.shelfNumber = shelfNumber;
    }

    public StorageArea() {
    }

    @Override
    public String toString() {
        return "Aisle ID = " + aisleId + " | Row ID = " + rowId + " | Shelf Number = " + shelfNumber;
    }

}
