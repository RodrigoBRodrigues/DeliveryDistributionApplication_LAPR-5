/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.clientusermanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import java.time.LocalDate;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * A factory for Customer entities.
 *
 * This class demonstrates the use of the factory (DDD) pattern using a fluent
 * interface. it acts as a Builder (GoF).
 *
 * @author Rui Pina 1201568@isep.ipp.pt 21/04/2022
 */
public class CustomerBuilder implements DomainFactory<Customer> {

    private SystemUser systemUser;
    private String firstName;
    private String lastName;
    private String vatId;
    private String email;
    private String phoneNumber;
    private String prefix;
    private String birthday;
    private Gender gender;

    private List<Address> address;

    public CustomerBuilder() {
    }

    public CustomerBuilder(final String firstName, final String lastName, final String vatId, final String email
                           , final String phoneNumber){
        Preconditions.noneNull(firstName, lastName, vatId, email, phoneNumber);

        this.firstName=firstName;
        this.lastName=lastName;
        this.vatId=vatId;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

//    public CustomerBuilder withSystemUser(final SystemUser systemUser) {
//        this.systemUser = systemUser;
//        return this;
//    }
    public CustomerBuilder withBirthday(final String birthday) {
        this.birthday = birthday;
        return this;
    }

    public CustomerBuilder withGender(final Gender gender) {
        this.gender = gender;
        return this;
    }

    public CustomerBuilder withAddress(final List<Address> address) {
        this.address = address;
        return this;
    }

    @Override
    public Customer build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        if(birthday!=null) {
            return new Customer(Name.valueOf(firstName, lastName)
                    , Vat.valueOf(vatId)
                    , EmailAddress.valueOf(email)
                    , PhoneNumber.valueOf(phoneNumber)
                    , LocalDate.parse(birthday, ISO_LOCAL_DATE)
                    , gender
                    , address);
        }else{
            return new Customer(Name.valueOf(firstName, lastName)
                    , Vat.valueOf(vatId)
                    , EmailAddress.valueOf(email)
                    , PhoneNumber.valueOf(phoneNumber)
                    , null
                    , gender
                    , address);
        }
    }
}
