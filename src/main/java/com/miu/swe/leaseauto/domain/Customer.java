package com.miu.swe.leaseauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "driving_license_credentials", nullable = false)
    private String drivingLicenseCredentials;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "invoice", "customer", "vehicle" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "booking", "customer" }, allowSetters = true)
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Customer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Customer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public Customer address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDrivingLicenseCredentials() {
        return this.drivingLicenseCredentials;
    }

    public Customer drivingLicenseCredentials(String drivingLicenseCredentials) {
        this.setDrivingLicenseCredentials(drivingLicenseCredentials);
        return this;
    }

    public void setDrivingLicenseCredentials(String drivingLicenseCredentials) {
        this.drivingLicenseCredentials = drivingLicenseCredentials;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        if (this.bookings != null) {
            this.bookings.forEach(i -> i.setCustomer(null));
        }
        if (bookings != null) {
            bookings.forEach(i -> i.setCustomer(this));
        }
        this.bookings = bookings;
    }

    public Customer bookings(Set<Booking> bookings) {
        this.setBookings(bookings);
        return this;
    }

    public Customer addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setCustomer(this);
        return this;
    }

    public Customer removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setCustomer(null);
        return this;
    }

    public Set<Invoice> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        if (this.invoices != null) {
            this.invoices.forEach(i -> i.setCustomer(null));
        }
        if (invoices != null) {
            invoices.forEach(i -> i.setCustomer(this));
        }
        this.invoices = invoices;
    }

    public Customer invoices(Set<Invoice> invoices) {
        this.setInvoices(invoices);
        return this;
    }

    public Customer addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCustomer(this);
        return this;
    }

    public Customer removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", drivingLicenseCredentials='" + getDrivingLicenseCredentials() + "'" +
            "}";
    }
}
