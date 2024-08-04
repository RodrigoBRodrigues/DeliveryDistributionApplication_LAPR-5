/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.clientusermanagement.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import org.junit.Before;
import org.junit.Test;

import eapli.base.usermanagement.domain.BaseRoles;

import static org.junit.Assert.*;

/**
 * Created by Nuno Bettencourt [NMB] on 03/04/16.
 */
public class CustomerTest {

    private static final Name name = Name.valueOf("Rui", "Pina");
    private static final Vat vat = Vat.valueOf("SK2199230931");
    private static final EmailAddress emailAddress = EmailAddress.valueOf("ruipina@email.com");
    private static final PhoneNumber phoneNumber = PhoneNumber.valueOf("+351963752894");
    private static final LocalDate birthDate = LocalDate.parse("2002-02-02");
    private static final Gender gender = Gender.MALE;
    private static final List<Address> addressList = new ArrayList<>();

    Customer setUpCustomer;
    @Before
    public void setUp() {
        addressList.add(new Address("Avenida dos Reis", "2", "3880-241", "Ovar", "Portugal", AddressType.SHIPMENT));
        setUpCustomer = new CustomerBuilder("Manuel"
                , "Pinto"
                , "242921421"
                , "1201564@isep.ipp.pt"
                , "+3519182523")
                .build();

        new Customer();
        new CustomerBuilder();

    }

    @Test
    public void email() {
        EmailAddress actual = setUpCustomer.email();
        EmailAddress expected = EmailAddress.valueOf("1201564@isep.ipp.pt");
        assertEquals(expected, actual);
    }

    @Test
    public void vat() {
        Vat actual = setUpCustomer.vat();
        Vat expected = Vat.valueOf("242921421");
        assertEquals(expected, actual);
    }

    @Test
    public void name() {
        Name actual = setUpCustomer.name();
        Name expected = Name.valueOf("Manuel", "Pinto");
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        setUpCustomer.equals(setUpCustomer);
    }

    @Test
    public void testToString() {
        String actual = setUpCustomer.toString();
        String expected = "Customer{version=null, id=null, systemUser=null, name=Manuel Pinto, vat=Vat{amount=242921421}, email=1201564@isep.ipp.pt, phoneNumber=PhoneNumber{number='+3519182523'}, birthDate=null, gender=null, address=null}";
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCustomerNullMandatoryAttributesThrowsExcepetion() throws Exception {


        Customer aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , null)
                .withBirthday(null).build();


//      assertTrue(expected);
    }

    @Test
    public void ensureCustomerNonMandatoryNullAttributesDoesNotThrowException() throws Exception {


        Customer aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "+351919153063")
                .withBirthday(null).withAddress(null).build();


//      assertTrue(expected);
    }

    @Test
    public void ensureCustomerEqualsPassesForTheSameAttributes() throws Exception {

        final Customer aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "929197091")
                .build();

        final Customer anotherCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "929197091")
                .build();


        final boolean expected = aCustomer.equals(anotherCustomer);

//      assertTrue(expected);
    }

    @Test
    public void ensureCustomerEqualsFailsForDifferentAttributes() throws Exception {
        final Customer aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "929197091")
                .build();

        final Customer anotherCustomer = new CustomerBuilder("Rui"
                , "Pina"
                , "1201568"
                , "1201568@isep.ipp.pt"
                , "916996827")
                .build();

        final boolean expected = aCustomer.equals(anotherCustomer);

        assertFalse(expected);
    }

    @Test
    public void ensureCustomerEqualsAreTheSameForTheSameInstance() throws Exception {
        final Customer aCustomer = new Customer();

        final boolean expected = aCustomer.equals(aCustomer);

        assertTrue(expected);
    }

    @Test
    public void ensureCustomerWithAllAttributes() throws Exception {
        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address("Avenida dos Reis"
                , "75", "3730-241", "Porto", "Portugal"
                , AddressType.SHIPMENT));

        Customer aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "+351919153063")
                .withBirthday("2000-02-02")
                .withAddress(addressList)
                .withGender(Gender.MALE)
                .build();


    }


    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveName() {
        new Customer(null, vat, emailAddress, phoneNumber, birthDate, gender, addressList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveVat() {
        new Customer(name, null, emailAddress, phoneNumber, birthDate, gender, addressList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveEmailAddress() {
        new Customer(name, vat, null, phoneNumber, birthDate, gender, addressList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHavePhoneNumber() {
        new Customer(name, vat, emailAddress, null, birthDate, gender, addressList);
    }


    @Test
    public void ensureClientUserIsTheSameAsItsInstance() throws Exception {
        final Customer aCustomer = new CustomerBuilder("Jorge"
                , "Ferreira"
                , "1201564"
                , "1201564@isep.ipp.pt"
                , "929197091")
                .build();

        final boolean expected = aCustomer.sameAs(aCustomer);

        assertTrue(expected);
    }


}
