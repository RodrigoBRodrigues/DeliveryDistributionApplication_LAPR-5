package eapli.base.questionnaire.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Period implements ValueObject {
    @Column(nullable = false)
    private String days;

    protected Period() {
        //Only ORM
    }

    protected Period(final String days) {
        if (days != null)
            this.days = days;
    }

    public static Period valueOf(final String days) {
        return new Period(days);
    }

    @Override
    public String toString() {
        return this.days;
    }


}
