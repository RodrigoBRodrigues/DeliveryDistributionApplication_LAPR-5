package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Reference implements ValueObject {

    private String reference;

    public Reference (String reference) {
            this.reference = reference;
    }

    public Reference() {
    }
}
