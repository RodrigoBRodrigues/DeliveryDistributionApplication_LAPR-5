package eapli.base.clientusermanagement.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    Customer setUpCustomer;

    @BeforeEach
    void setUp() {
        final Customer setUpCustomer = new CustomerBuilder("Manuel"
                , "Pinto"
                , "242921421"
                , "1201564@isep.ipp.pt"
                , "+3519182523")
                .build();
        Customer customerORM = new Customer();
    }



    @Test
    void getAddressType() {
        Address address = new Address("Avenida dos Reis"
                , "75", "3730-241", "Porto", "Portugal"
                , AddressType.SHIPMENT);
        AddressType actual = address.getAddressType();
        AddressType expected = AddressType.SHIPMENT;
        assertEquals(expected, actual);
    }

    @Test
    void setId() {
        Address address = new Address("Avenida dos Reis"
                , "75", "3730-241", "Porto", "Portugal"
                , AddressType.SHIPMENT);
        address.setId(0L);
        long actual = address.getId();
        long expected = 0;
        assertEquals(expected, actual);
    }


    @Test
    void testToString() {
        Address address = new Address("Avenida dos Reis"
                , "75", "3730-241", "Porto", "Portugal"
                , AddressType.SHIPMENT);
        String expected = "Address{streetName='Avenida dos Reis', doorNumber='75', postalCode='3730-241', city='Porto', country='Portugal', addressType=SHIPMENT}";
        String actual = address.toString();
        assertEquals(expected, actual);
    }

    @Test
    void setCustomer() {
        Address address = new Address();
        address.setCustomer(setUpCustomer);
    }
}