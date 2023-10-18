package com.miu.swe.leaseauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "total_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @NotNull
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @JsonIgnoreProperties(value = { "invoice", "customer", "vehicle" }, allowSetters = true)
    @OneToOne(mappedBy = "invoice")
    private Booking booking;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bookings", "invoices" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public Invoice totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public Invoice paymentStatus(String paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public Invoice dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Booking getBooking() {
        return this.booking;
    }

    public void setBooking(Booking booking) {
        if (this.booking != null) {
            this.booking.setInvoice(null);
        }
        if (booking != null) {
            booking.setInvoice(this);
        }
        this.booking = booking;
    }

    public Invoice booking(Booking booking) {
        this.setBooking(booking);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Invoice customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
}
