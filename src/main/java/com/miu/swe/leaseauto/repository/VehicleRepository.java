package com.miu.swe.leaseauto.repository;

import com.miu.swe.leaseauto.domain.Vehicle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {}
