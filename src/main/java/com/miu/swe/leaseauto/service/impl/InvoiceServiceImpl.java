package com.miu.swe.leaseauto.service.impl;

import com.miu.swe.leaseauto.domain.Invoice;
import com.miu.swe.leaseauto.repository.InvoiceRepository;
import com.miu.swe.leaseauto.service.InvoiceService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> partialUpdate(Invoice invoice) {
        log.debug("Request to partially update Invoice : {}", invoice);

        return invoiceRepository
            .findById(invoice.getId())
            .map(existingInvoice -> {
                if (invoice.getTotalAmount() != null) {
                    existingInvoice.setTotalAmount(invoice.getTotalAmount());
                }
                if (invoice.getPaymentStatus() != null) {
                    existingInvoice.setPaymentStatus(invoice.getPaymentStatus());
                }
                if (invoice.getDueDate() != null) {
                    existingInvoice.setDueDate(invoice.getDueDate());
                }

                return existingInvoice;
            })
            .map(invoiceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable);
    }

    /**
     *  Get all the invoices where Booking is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Invoice> findAllWhereBookingIsNull() {
        log.debug("Request to get all invoices where Booking is null");
        return StreamSupport
            .stream(invoiceRepository.findAll().spliterator(), false)
            .filter(invoice -> invoice.getBooking() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
