package eapli.base.productOrder.domain;

import eapli.framework.domain.model.ValueObject;
import org.hibernate.criterion.Order;

import java.io.Serializable;

public enum OrderState implements ValueObject, Serializable {
    REGISTERED,
    PAYMENT_PENDING,
    TO_BE_PREPARED,
    BEING_PREPARED,
    READY_FOR_PACKAGING,
    READY_FOR_CARRIER,
    DISPATCHED,
    DELIVERED,
    RECEIVED;
}
