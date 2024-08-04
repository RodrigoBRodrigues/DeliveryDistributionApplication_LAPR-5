package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class InternalCode implements ValueObject {

    private String internalCode;

    public InternalCode(String internalCode) {
        if (internalCode.length() <= 23 && internalCode.matches("^[a-zA-Z0-9]*$") && !internalCode.isEmpty()) {
            this.internalCode = internalCode;
        }
    }

    public InternalCode() {
    }
    @Override
    public String toString(){
        return this.internalCode;
    }
}
