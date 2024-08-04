package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
@Embeddable
public class Barcode implements ValueObject {

    private String barcode;

    public Barcode(String barcode) {
            Preconditions.noneNull(barcode);
            Preconditions.nonEmpty(barcode);
            this.barcode = barcode;
        }

    public Barcode() {}

    @Override
    public String toString(){
        return this.barcode;
    }
}
