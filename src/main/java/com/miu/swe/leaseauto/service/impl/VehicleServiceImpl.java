package com.miu.swe.leaseauto.service.impl;

import com.miu.swe.leaseauto.domain.Vehicle;
import com.miu.swe.leaseauto.repository.VehicleRepository;
import com.miu.swe.leaseauto.service.VehicleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vehicle}.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        log.debug("Request to save Vehicle : {}", vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        log.debug("Request to save Vehicle : {}", vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> partialUpdate(Vehicle vehicle) {
        log.debug("Request to partially update Vehicle : {}", vehicle);

        return vehicleRepository
            .findById(vehicle.getId())
            .map(existingVehicle -> {
                if (vehicle.getModel() != null) {
                    existingVehicle.setModel(vehicle.getModel());
                }
                if (vehicle.getMake() != null) {
                    existingVehicle.setMake(vehicle.getMake());
                }
                if (vehicle.getYear() != null) {
                    existingVehicle.setYear(vehicle.getYear());
                }
                if (vehicle.getLeaseRate() != null) {
                    existingVehicle.setLeaseRate(vehicle.getLeaseRate());
                }
                if (vehicle.getAvailabilityStatus() != null) {
                    existingVehicle.setAvailabilityStatus(vehicle.getAvailabilityStatus());
                }

                return existingVehicle;
            })
            .map(vehicleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vehicle> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicle> findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        return vehicleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.deleteById(id);
    }
}
