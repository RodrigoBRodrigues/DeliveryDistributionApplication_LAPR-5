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
package eapli.base.app.backoffice.console.presentation.clientuser;

import eapli.base.clientusermanagement.domain.Address;
import eapli.base.clientusermanagement.domain.AddressType;
import eapli.base.clientusermanagement.domain.Customer;
import eapli.base.clientusermanagement.domain.Gender;
import eapli.base.usermanagement.application.AddCustomerController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class AddCustomerUI extends AbstractUI {

    private final AddCustomerController theController = new AddCustomerController();
    private Gender gender;
    private String birthday = null;

    @Override
    protected boolean doShow() {
        final String firstName = Console.readLine("First Name");
        final String lastName = Console.readLine("Last Name");
        final String vatId = inputVat();
        final String email = inputEmail();
        final String phoneNumber = inputPhoneNumber();

        String response = Console.readLine("Birthday: This field is optional. Do you want to define it? (y/n)");
        if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
            boolean passedValidation;
            do {
                try {
                    birthday = inputBirthDate();
                    passedValidation = true;
                } catch (DateTimeException dateTimeException) {
                    passedValidation = false;
                    System.out.println("There was an error while analysing the date introduce. Please try again!");
                }
            } while (!passedValidation);
        }
        response = Console.readLine("Gender: This field is optional. Do you want to define it? (y/n)");
        if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
            gender = selectGender();

        }
        response = Console.readLine("Address: This field is optional. Do you want to define it? (y/n)");
        String moreAddresses;
        Address address;
        List<Address> addresses = new ArrayList<>();
        if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
            do {
                address = askAddress();
                addresses.add(address);
                moreAddresses = Console.readLine("Do you want to define more addresses? (y|n)");
            } while (moreAddresses.equalsIgnoreCase("yes") || moreAddresses.equalsIgnoreCase("y"));
        }
        Customer customer = null;
        try {
            customer = theController.customerBuilder(firstName, lastName, vatId, email, phoneNumber, birthday, gender, addresses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {

            System.out.println(customer);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println("That username is already in use.");
        }

        final String credentialsCreation = Console.readLine("Do you want to create the customers' credentials?");

        if (credentialsCreation.equalsIgnoreCase("yes") || credentialsCreation.equalsIgnoreCase("y")) {
            System.out.println("Use Case 3.1.5: yet to be implemented. (Check the \"system to develop\" pdf)");
        }


        return false;
    }


    private Address askAddress() {
        final String streetName = Console.readLine("Street name:");
        final String doorNumber = Console.readLine("Door Number:");
        final String postalCode = Console.readLine("Postal Code");
        final String city = Console.readLine("City");
        final String country = Console.readLine("Country");
        final AddressType addressType = selectAddressType();

        return new Address(streetName, doorNumber, postalCode, city, country, addressType);
    }

    private Gender selectGender() {
        int i = 1;
        Gender genderResult = null;
        String genderString;
        System.out.println("Select the gender:");
        for (Gender genderValues : Gender.values()) {
            System.out.println(i + ". " + genderValues);
            i++;
        }
        do {
            genderString = Console.readLine("");
            if (genderString.equals("1")) {
                genderResult = Gender.MALE;
            } else if (genderString.equals("2")) {
                genderResult = Gender.FEMALE;
            }
        } while (!(genderString.equals("1") || genderString.equals("2")));
        return genderResult;
    }

    private AddressType selectAddressType() {
        int i = 1;
        AddressType addressType = null;
        String addressTypeString;
        System.out.println("Select the addressType:");
        for (AddressType addressType1 : AddressType.values()) {
            System.out.println(i + ". " + addressType1);
            i++;
        }

        do {
            addressTypeString = Console.readLine("");
            if (addressTypeString.equals("1")) {
                addressType = AddressType.BILLING;
            } else if (addressTypeString.equals("2")) {
                addressType = AddressType.SHIPMENT;
            }
        } while (!(addressTypeString.equals("1") || addressTypeString.equals("2")));
        return addressType;
    }

    private String inputEmail() {
        boolean passedValidation;
        String email = "";
        do {
            try {
                email = Console.readLine("Email");
                theController.validateEmail(email);
                passedValidation = true;
            } catch (IllegalArgumentException e) {
                passedValidation = false;
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                passedValidation = false;
            }
        } while (!passedValidation);
        return email;
    }

    private String inputVat() {
        boolean passedValidation;
        String vat = "";
        do {
            try {
                vat = Console.readLine("Vat");
                theController.validateVat(vat);
                passedValidation = true;
            } catch (IllegalArgumentException e) {
                passedValidation = false;
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                passedValidation = false;
            } catch (IllegalStateException e) {
                passedValidation = false;
                System.out.println(e.getMessage());
            }
        } while (!passedValidation);
        return vat;
    }

    private String inputPhoneNumber() {
        boolean passedValidation;
        String phoneNumber = "";
        do {
            try {
                phoneNumber = Console.readLine("Phone Number");
                theController.validatePhoneNumber(phoneNumber);
                passedValidation = true;
            } catch (IllegalArgumentException e) {
                passedValidation = false;
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                passedValidation = false;
            } catch (IllegalStateException e) {
                passedValidation = false;
                System.out.println(e.getMessage());
            }
        } while (!passedValidation);
        return phoneNumber;
    }

    private String inputBirthDate() {
        boolean passedValidation;
        String birthDate = null;
        do {
            birthDate = Console.readLine("Birth Date (yyyy-mm-dd)");
            if (birthDate != null) {
                if (LocalDate.parse(birthDate).isBefore(LocalDate.now()) && LocalDate.parse(birthDate).isAfter(LocalDate.parse(birthDate).minusYears(150))) {
                    passedValidation = true;
                } else if (!LocalDate.parse(birthDate).isBefore(LocalDate.now())) {
                    passedValidation = false;
                    System.out.println("You have introduced a date in the future. Please try another birth date.");
                } else if (!LocalDate.parse(birthDate).isAfter(LocalDate.parse(birthDate).minusYears(150))) {
                    passedValidation = false;
                    System.out.println("You can't be more than 150 years. Please try another birth date.");
                } else {
                    passedValidation = false;
                    System.out.println("There was some error.");
                }
            } else {
                passedValidation = true;
            }
        } while (!passedValidation);
        return birthDate;
    }


    @Override
    public String headline() {
        return "Add Customer";
    }
}
