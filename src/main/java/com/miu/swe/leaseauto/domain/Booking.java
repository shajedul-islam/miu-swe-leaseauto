package com.miu.swe.leaseauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_cost", precision = 21, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "location")
    private String location;

    @JsonIgnoreProperties(value = { "booking", "customer" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Invoice invoice;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bookings", "invoices" }, allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bookings" }, allowSetters = true)
    private Vehicle vehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Booking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Booking startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Booking endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalCost() {
        return this.totalCost;
    }

    public Booking totalCost(BigDecimal totalCost) {
        this.setTotalCost(totalCost);
        return this;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getLocation() {
        return this.location;
    }

    public Booking location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Booking invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Booking customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Booking vehicle(Vehicle vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", totalCost=" + getTotalCost() +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
