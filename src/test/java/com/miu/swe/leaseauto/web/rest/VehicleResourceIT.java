package com.miu.swe.leaseauto.web.rest;

import static com.miu.swe.leaseauto.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.miu.swe.leaseauto.IntegrationTest;
import com.miu.swe.leaseauto.domain.Vehicle;
import com.miu.swe.leaseauto.repository.VehicleRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final BigDecimal DEFAULT_LEASE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LEASE_RATE = new BigDecimal(2);

    private static final String DEFAULT_AVAILABILITY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_AVAILABILITY_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .model(DEFAULT_MODEL)
            .make(DEFAULT_MAKE)
            .year(DEFAULT_YEAR)
            .leaseRate(DEFAULT_LEASE_RATE)
            .availabilityStatus(DEFAULT_AVAILABILITY_STATUS);
        return vehicle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .model(UPDATED_MODEL)
            .make(UPDATED_MAKE)
            .year(UPDATED_YEAR)
            .leaseRate(UPDATED_LEASE_RATE)
            .availabilityStatus(UPDATED_AVAILABILITY_STATUS);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();
        // Create the Vehicle
        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testVehicle.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testVehicle.getLeaseRate()).isEqualByComparingTo(DEFAULT_LEASE_RATE);
        assertThat(testVehicle.getAvailabilityStatus()).isEqualTo(DEFAULT_AVAILABILITY_STATUS);
    }

    @Test
    @Transactional
    void createVehicleWithExistingId() throws Exception {
        // Create the Vehicle with an existing ID
        vehicle.setId(1L);

        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setModel(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMakeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setMake(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setYear(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLeaseRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setLeaseRate(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvailabilityStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setAvailabilityStatus(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].leaseRate").value(hasItem(sameNumber(DEFAULT_LEASE_RATE))))
            .andExpect(jsonPath("$.[*].availabilityStatus").value(hasItem(DEFAULT_AVAILABILITY_STATUS)));
    }

    @Test
    @Transactional
    void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.leaseRate").value(sameNumber(DEFAULT_LEASE_RATE)))
            .andExpect(jsonPath("$.availabilityStatus").value(DEFAULT_AVAILABILITY_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .model(UPDATED_MODEL)
            .make(UPDATED_MAKE)
            .year(UPDATED_YEAR)
            .leaseRate(UPDATED_LEASE_RATE)
            .availabilityStatus(UPDATED_AVAILABILITY_STATUS);

        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVehicle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testVehicle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testVehicle.getLeaseRate()).isEqualByComparingTo(UPDATED_LEASE_RATE);
        assertThat(testVehicle.getAvailabilityStatus()).isEqualTo(UPDATED_AVAILABILITY_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleWithPatch() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle using partial update
        Vehicle partialUpdatedVehicle = new Vehicle();
        partialUpdatedVehicle.setId(vehicle.getId());

        partialUpdatedVehicle.leaseRate(UPDATED_LEASE_RATE).availabilityStatus(UPDATED_AVAILABILITY_STATUS);

        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testVehicle.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testVehicle.getLeaseRate()).isEqualByComparingTo(UPDATED_LEASE_RATE);
        assertThat(testVehicle.getAvailabilityStatus()).isEqualTo(UPDATED_AVAILABILITY_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateVehicleWithPatch() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle using partial update
        Vehicle partialUpdatedVehicle = new Vehicle();
        partialUpdatedVehicle.setId(vehicle.getId());

        partialUpdatedVehicle
            .model(UPDATED_MODEL)
            .make(UPDATED_MAKE)
            .year(UPDATED_YEAR)
            .leaseRate(UPDATED_LEASE_RATE)
            .availabilityStatus(UPDATED_AVAILABILITY_STATUS);

        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testVehicle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testVehicle.getLeaseRate()).isEqualByComparingTo(UPDATED_LEASE_RATE);
        assertThat(testVehicle.getAvailabilityStatus()).isEqualTo(UPDATED_AVAILABILITY_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
