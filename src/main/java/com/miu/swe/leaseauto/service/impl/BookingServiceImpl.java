package com.miu.swe.leaseauto.service.impl;

import com.miu.swe.leaseauto.domain.Booking;
import com.miu.swe.leaseauto.repository.BookingRepository;
import com.miu.swe.leaseauto.service.BookingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> partialUpdate(Booking booking) {
        log.debug("Request to partially update Booking : {}", booking);

        return bookingRepository
            .findById(booking.getId())
            .map(existingBooking -> {
                if (booking.getStartDate() != null) {
                    existingBooking.setStartDate(booking.getStartDate());
                }
                if (booking.getEndDate() != null) {
                    existingBooking.setEndDate(booking.getEndDate());
                }
                if (booking.getTotalCost() != null) {
                    existingBooking.setTotalCost(booking.getTotalCost());
                }
                if (booking.getLocation() != null) {
                    existingBooking.setLocation(booking.getLocation());
                }

                return existingBooking;
            })
            .map(bookingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }
}
