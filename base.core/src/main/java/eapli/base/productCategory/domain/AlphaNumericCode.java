package eapli.base.productCategory.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Description;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AlphaNumericCode implements ValueObject, Comparable<AlphaNumericCode> {
    @Column(name="alphanumericcode")
    private String code;

    protected AlphaNumericCode(){

    }
    protected AlphaNumericCode(final String code){
        if (!code.isEmpty()&& code.length()<=10 && code.matches("^[a-zA-Z0-9]*$"))
        this.code=code;
    }

    public String code() {
        return code;
    }

    public static AlphaNumericCode valueOf(final String code) {
        return new AlphaNumericCode(code);
    }
    @Override
    public String toString(){
        return this.code;
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(code).code();
    }

    @Override
    public int compareTo(AlphaNumericCode o) {
        return this.code.compareTo(o.code);
    }
}
