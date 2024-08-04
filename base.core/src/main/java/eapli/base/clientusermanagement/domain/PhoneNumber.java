package eapli.base.clientusermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class PhoneNumber implements ValueObject, Serializable {
    private String number;

    public PhoneNumber(String number) {
        this.number = number;
    }

    protected PhoneNumber() {
        //ORM only
    }

    public static PhoneNumber valueOf(String phoneNumber) {
        return new PhoneNumber(phoneNumber);
    }
    public static void phoneMeetsMinimumRequirements(String phoneNumber) {
        phoneNumber = phoneNumber.replace("[-. ]", "");
        Pattern pattern = Pattern.compile(
                "^\\+[0-9]{1,3}[0-9]{4,14}(?:x.+)?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new IllegalStateException("The Phone number is not in conformity with the regulations. Please try again.");
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PhoneNumber{");
        sb.append("number='").append(number).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
