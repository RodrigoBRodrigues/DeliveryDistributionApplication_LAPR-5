package eapli.base.clientusermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Address implements ValueObject, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    private String streetName;
    private String doorNumber;
    private String postalCode;
    private String city;
    private String country;
    private AddressType addressType;

    public Long getId() {
        return id;
    }

    public AddressType getAddressType(){
        return this.addressType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address(String streetName
            , String doorNumber
            , String postalCode
            , String city
            , String country
            , AddressType addressType) {
        Preconditions.noneNull(streetName, doorNumber, postalCode, city, country, addressType);
        this.streetName = streetName;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.addressType = addressType;
    }

    protected Address() {
        //Only for ORM
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("streetName='").append(streetName).append('\'');
        sb.append(", doorNumber='").append(doorNumber).append('\'');
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", addressType=").append(addressType);
        sb.append('}');
        return sb.toString();
    }

    public String shortToString(){
        return this.streetName + ", " + this.doorNumber + ", " + this.city;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
