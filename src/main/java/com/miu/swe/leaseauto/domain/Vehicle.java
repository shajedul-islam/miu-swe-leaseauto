package com.miu.swe.leaseauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "make", nullable = false)
    private String make;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "lease_rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal leaseRate;

    @NotNull
    @Column(name = "availability_status", nullable = false)
    private String availabilityStatus;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnoreProperties(value = { "invoice", "customer", "vehicle" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return this.model;
    }

    public Vehicle model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return this.make;
    }

    public Vehicle make(String make) {
        this.setMake(make);
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Integer getYear() {
        return this.year;
    }

    public Vehicle year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getLeaseRate() {
        return this.leaseRate;
    }

    public Vehicle leaseRate(BigDecimal leaseRate) {
        this.setLeaseRate(leaseRate);
        return this;
    }

    public void setLeaseRate(BigDecimal leaseRate) {
        this.leaseRate = leaseRate;
    }

    public String getAvailabilityStatus() {
        return this.availabilityStatus;
    }

    public Vehicle availabilityStatus(String availabilityStatus) {
        this.setAvailabilityStatus(availabilityStatus);
        return this;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        if (this.bookings != null) {
            this.bookings.forEach(i -> i.setVehicle(null));
        }
        if (bookings != null) {
            bookings.forEach(i -> i.setVehicle(this));
        }
        this.bookings = bookings;
    }

    public Vehicle bookings(Set<Booking> bookings) {
        this.setBookings(bookings);
        return this;
    }

    public Vehicle addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setVehicle(this);
        return this;
    }

    public Vehicle removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setVehicle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", make='" + getMake() + "'" +
            ", year=" + getYear() +
            ", leaseRate=" + getLeaseRate() +
            ", availabilityStatus='" + getAvailabilityStatus() + "'" +
            "}";
    }
}
