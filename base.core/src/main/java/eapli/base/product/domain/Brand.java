package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class Brand implements ValueObject {

    private String brandName;

    public Brand(String brandName) {
        Preconditions.noneNull(brandName);
        Preconditions.nonEmpty(brandName);
            this.brandName = brandName;
        }


    public Brand() {
    }
    @Override
    public String toString(){
        return this.brandName;
    }
}
