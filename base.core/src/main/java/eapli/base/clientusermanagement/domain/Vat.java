package eapli.base.clientusermanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.FormattedString;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Embeddable
public class Vat implements Comparable<Vat>, Serializable, ValueObject, FormattedString {
    private static final long serialVersionUID = 1L;
    @XmlAttribute
    @JsonProperty
    private String vatId;

    protected Vat() {
        this.vatId = null;
    }

    public Vat(final String vatId) {
        setVat(vatId);
    }

    public static Vat valueOf(String vatId) {
        return new Vat(vatId);
    }


    public String vatId() {
        return vatId;
    }


    public int compareTo(final Vat arg) {
        return this.vatId.compareTo(arg.vatId);
    }

    public static boolean vatMeetsMinimumRequirements(String vat) {
        if (vat != null) {
            vat = vat.replace("[-. ]", "");
            Pattern pattern = Pattern.compile(
                    "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
                            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
                            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
                            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
                            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
                            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
                            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
                            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(vat);
            boolean matchFound = matcher.find();
            if (!matchFound) {
                throw new IllegalStateException("The Vat is not in conformity with the EU regulations. Please try again.");
            }
        }
        return !StringPredicates.isNullOrEmpty(vat);
    }


    /**
     * Sets and validates vat.
     *
     * @param vat The new Vat id.
     */
    private void setVat(final String vat) {
        if (vatMeetsMinimumRequirements(vat)) {
            this.vatId = vat;
        } else {
            throw new IllegalArgumentException("Invalid Vat");
        }
    }


    public boolean equals(final Object arg) {
        if (!(arg instanceof Vat)) {
            return false;
        } else {
            Vat other = (Vat) arg;
            return this.vatId.equals(other.vatId);
        }
    }

    public int hashCode() {
        int result = 11;
        result = 37 * result + this.vatId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vat{" + "amount=" + vatId +
                '}';
    }

}
