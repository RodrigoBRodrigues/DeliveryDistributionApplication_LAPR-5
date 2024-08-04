package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class ProductionCode implements ValueObject {

    private String productionCode;

    public ProductionCode(String productionCode) {
            this.productionCode = productionCode;
    }

    public ProductionCode() {
    }
}
