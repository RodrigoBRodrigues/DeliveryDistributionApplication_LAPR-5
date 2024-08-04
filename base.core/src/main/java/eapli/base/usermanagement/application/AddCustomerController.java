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
package eapli.base.usermanagement.application;

import eapli.base.clientusermanagement.domain.*;
import eapli.base.clientusermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class AddCustomerController {

    private final CustomerRepository repo = PersistenceContext.repositories().customers();

    public Customer customerBuilder(final String firstName, final String lastName, final String vatId, final String email,
                                    final String phoneNumber, final String birthday, final Gender gender,
                                    final List<Address> addresses) {
        CustomerBuilder customerBuilder = new CustomerBuilder(firstName, lastName, vatId, email, phoneNumber);
        Customer customer = (customerBuilder.withBirthday(birthday)
                .withGender(gender)
                .withAddress(addresses)
                .build());
        for (Address adr : addresses) {
            adr.setCustomer(customer);
        }
        if (customer != null) {
            return repo.save(customer);
        }
        return null;
    }

    public Customer customerBuilder(final String firstName, final String lastName, final String vatId, final String email,
                                    final String phoneNumber, final String birthday, final Gender gender,
                                    final List<Address> addresses, String username, String password) {
        CustomerBuilder customerBuilder = new CustomerBuilder(firstName, lastName, vatId, email, phoneNumber);
        Customer customer = (customerBuilder.withBirthday(birthday)
                .withGender(gender)
                .withAddress(addresses)
                .build());
        for (Address adr : addresses) {
            adr.setCustomer(customer);
        }

        Set<Role> CUSTOMER = new HashSet<>();
        CUSTOMER.add(BaseRoles.CUSTOMER_USER);
        customer.setUser(registerUser(username, password, firstName, lastName, email, CUSTOMER));

        if (customer != null) {
            return repo.save(customer);
        }
        return null;
    }

    private void registerAdmin(final String username, final String password, final String firstName,
                               final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.ADMIN);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    final AddUserController userController = new AddUserController();
    final ListUsersController listUserController = new ListUsersController();

    protected SystemUser registerUser(final String username, final String password, final String firstName,
                                      final String lastName, final String email, final Set<Role> roles) {
        SystemUser u = null;
        try {
            u = userController.addUser(username, password, firstName, lastName, email, roles);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // assuming it is just a primary key violation due to the tentative
            // of inserting a duplicated user. let's just lookup that user
            u = listUserController.find(Username.valueOf(username)).orElseThrow(() -> e);
        }
        return u;
    }

    public Optional<Customer> getCustomer(Long id) {
        return repo.findById(id);
    }

    public Optional<Customer> findByUsername(Username username) {
        return repo.findByUsername(username);
    }

    public List<Customer> getCustomersWithLessThanAge(int age) {
        return repo.calculateAge(age);
    }

    public List<Customer> getCustomersWithGender(Gender gender) {
        return repo.getCustomerWithGender(gender.toString());
    }

    public void validateEmail(String email) {
        EmailAddress.valueOf(email);
    }

    public void validateVat(String vat) {
        Vat.vatMeetsMinimumRequirements(vat);
    }


    public void validatePhoneNumber(String phoneNumber) {
        PhoneNumber.phoneMeetsMinimumRequirements(phoneNumber);
    }
}
