package eapli.base.productOrder.domain;

import eapli.framework.domain.model.ValueObject;

import java.io.Serializable;

public enum Shipment implements ValueObject, Serializable {
    GREEN,
    BLUE,
    STANDARD;
}
