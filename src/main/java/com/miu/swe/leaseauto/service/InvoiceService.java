package com.miu.swe.leaseauto.service;

import com.miu.swe.leaseauto.domain.Invoice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Invoice}.
 */
public interface InvoiceService {
    /**
     * Save a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    Invoice save(Invoice invoice);

    /**
     * Updates a invoice.
     *
     * @param invoice the entity to update.
     * @return the persisted entity.
     */
    Invoice update(Invoice invoice);

    /**
     * Partially updates a invoice.
     *
     * @param invoice the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Invoice> partialUpdate(Invoice invoice);

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Invoice> findAll(Pageable pageable);
    /**
     * Get all the Invoice where Booking is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Invoice> findAllWhereBookingIsNull();

    /**
     * Get the "id" invoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Invoice> findOne(Long id);

    /**
     * Delete the "id" invoice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
