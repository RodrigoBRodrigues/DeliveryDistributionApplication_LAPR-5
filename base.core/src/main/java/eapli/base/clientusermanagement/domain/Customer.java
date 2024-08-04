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

import javax.persistence.*;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A Customer.
 * <p>
 * This class represents customers. It follows a DDD approach where Customer
 * is the root entity of the Customer Aggregate and all of its properties
 * are instances of value objects. A Customer references a System User
 * <p>
 * This approach may seem a little more complex than just having String or
 * native type attributes but provides for real semantic of the domain and
 * follows the Single Responsibility Pattern
 *
 *
 */
@Entity
@Table
public class Customer implements Serializable, AggregateRoot<Long> {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    /**
     * cascade = CascadeType.NONE as the systemUser is part of another aggregate
     */
    @OneToOne()
    private SystemUser systemUser;


    @Embedded
    @Column(name = "name", nullable = false)
    private Name name;

    @Embedded
    @Column(name = "vat", nullable = false)
    private Vat vat;

    @Embedded
    @Column(name = "email", nullable = false)
    private EmailAddress email;

    @Embedded
    @Column(name = "phone_number", nullable = false)
    private PhoneNumber phoneNumber;


    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @Column(name = "address")
    private List<Address> address;

    public EmailAddress email() {
        return email;
    }

    public Vat vat() {
        return vat;
    }

    public Name name() {
        return name;
    }


    protected Customer() {
        // for ORM only
    }

    public Customer(Name name, Vat vat, EmailAddress emailAddress, PhoneNumber phoneNumber, LocalDate birthday, Gender gender, List<Address> address) {
        Preconditions.noneNull(name, vat, emailAddress, phoneNumber);
        this.name = name;
        this.vat = vat;
        this.email = emailAddress;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthday;
        this.gender = gender;
        this.address = address;
    }

    public List<Address> getAddress(){
        return address;
    }


    public void setUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }


    public SystemUser user() {
        return this.systemUser;
    }


    @Override
    public int hashCode()
    {

        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Long identity() {
        return this.id;
    }

    public Address defaultAddress(){
        return address.get(0);
    }

    @Override
    public String toString() {
        return "Customer{" + "version=" + version +
                ", id=" + id +
                ", systemUser=" + systemUser +
                ", name=" + name +
                ", vat=" + vat +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address=" + address +
                '}';
    }
}
