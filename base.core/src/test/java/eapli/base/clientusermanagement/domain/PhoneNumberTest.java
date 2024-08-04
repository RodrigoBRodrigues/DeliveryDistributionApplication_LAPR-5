package eapli.base.clientusermanagement.domain;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PhoneNumberTest {
    Customer aCustomer;

    @BeforeEach
    void setUp() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address("Avenida dos Reis"
                , "75", "3730-241", "Porto", "Portugal"
                , AddressType.SHIPMENT));
        aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "+351919153063")
                .withBirthday("2000-02-02")
                .withAddress(addressList)
                .withGender(Gender.MALE)
                .build();
        new PhoneNumber();
    }

    @Test
    public void phoneMeetsMinimumRequirements() {
        PhoneNumber.phoneMeetsMinimumRequirements("+351919153063");
    }

    @Test(expected = IllegalStateException.class)
    public void phoneDoesNotMeetMinimumRequirements() {
        PhoneNumber.phoneMeetsMinimumRequirements("+22");
    }
}