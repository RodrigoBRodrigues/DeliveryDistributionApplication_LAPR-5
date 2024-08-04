package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class ProductDescription implements ValueObject {

    private String shortDescription;

    private String extendedDescription;

    private String technicalDescription;

    public ProductDescription(String shortDescription, String extendedDescription, String technicalDescription) {
        if (!shortDescription.isEmpty() && !extendedDescription.isEmpty() &&
             shortDescription.length() <= 30 &&
             extendedDescription.length() >= 20 && extendedDescription.length() <= 100 ) {
            this.shortDescription = shortDescription;
            this.extendedDescription = extendedDescription;
            this.technicalDescription = technicalDescription;
        }
    }

    public ProductDescription() {

    }

    public String shortDescription(){
        return this.shortDescription;
    }
    public String extendedDescription(){
        return this.extendedDescription;
    }

    public String technicalDescription(){
        return this.technicalDescription;
    }
}
