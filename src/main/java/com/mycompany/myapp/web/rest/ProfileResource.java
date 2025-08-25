package com.mycompany.myapp.web.rest;

import com.cloudinary.Api;
import com.mycompany.myapp.repository.ProfileRepository;
import com.mycompany.myapp.service.ProfileQueryService;
import com.mycompany.myapp.service.ProfileService;
import com.mycompany.myapp.service.criteria.ProfileCriteria;
import com.mycompany.myapp.service.dto.ProfileDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.vm.ApiResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Profile}.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileService profileService;

    private final ProfileRepository profileRepository;

    private final ProfileQueryService profileQueryService;

    public ProfileResource(ProfileService profileService, ProfileRepository profileRepository, ProfileQueryService profileQueryService) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.profileQueryService = profileQueryService;
    }

    /**
     * {@code POST  /profiles} : Create a new profile.
     *
     * @param profileDTO the profileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileDTO, or with status {@code 400 (Bad Request)} if the profile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileDTO>> createProfile(@RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        LOG.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        profileDTO = profileService.save(profileDTO);

        ApiResponse<ProfileDTO> response = ApiResponse.success("Profile created successfully", profileDTO);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("entityName", ENTITY_NAME);

        return ResponseEntity.created(new URI("/api/profiles/" + profileDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, profileDTO.getId().toString()))
            .body(response);
    }


    /**
     * {@code PUT  /profiles/:id} : Updates an existing profile.
     *
     * @param id the id of the profileDTO to save.
     * @param profileDTO the profileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDTO,
     * or with status {@code 400 (Bad Request)} if the profileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<ProfileDTO>> updateProfile(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ProfileDTO profileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Profile : {}, {}", id, profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        profileDTO = profileService.update(profileDTO);
        
        ApiResponse<ProfileDTO> response = ApiResponse.success("Profile updated successfully", profileDTO);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("entityName", ENTITY_NAME);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profileDTO.getId().toString()))
            .body(response);
    }
    /**
     * {@code PATCH  /profiles/:id} : Partial updates given fields of an existing profile, field will ignore if it is null
     *
     * @param id the id of the profileDTO to save.
     * @param profileDTO the profileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDTO,
     * or with status {@code 400 (Bad Request)} if the profileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profile/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApiResponse<ProfileDTO>> partialUpdateProfile(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ProfileDTO profileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Profile partially : {}, {}", id, profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfileDTO> result = profileService.partialUpdate(profileDTO);
        
        if (result.isPresent()) {
            ApiResponse<ProfileDTO> response = ApiResponse.success("Profile partially updated successfully", result.get());
            response.addMetadata("requestId", UUID.randomUUID().toString());
            response.addMetadata("entityName", ENTITY_NAME);
            
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profileDTO.getId().toString()))
                .body(response);
        } else {
            ApiResponse<ProfileDTO> response = ApiResponse.error("Failed to update profile");
            response.addMetadata("requestId", UUID.randomUUID().toString());
            response.addMetadata("entityId", id.toString());
            return ResponseEntity.status(500).body(response);
        }
    }


    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse<Page<ProfileDTO>>> getAllProfiles(
        ProfileCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Profiles by criteria: {}", criteria);

        Page<ProfileDTO> page = profileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        
        ApiResponse<Page<ProfileDTO>> response = ApiResponse.success("Profiles retrieved successfully", page);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("totalItems", page.getTotalElements());
        response.addMetadata("totalPages", page.getTotalPages());
        
        return ResponseEntity.ok().headers(headers).body(response);
    }

    /**
     * {@code GET  /profiles/count} : count all the profiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/profiles/count")
    public ResponseEntity<ApiResponse<Long>> countProfiles(ProfileCriteria criteria) {
        LOG.debug("REST request to count Profiles by criteria: {}", criteria);
        Long count = profileQueryService.countByCriteria(criteria);
        
        ApiResponse<Long> response = ApiResponse.success("Profile count retrieved successfully", count);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("criteria", criteria.toString());
        
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /profiles/:id} : get the "id" profile.
     *
     * @param id the id of the profileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<ProfileDTO>> getProfile(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Profile : {}", id);
        Optional<ProfileDTO> profileDTO = profileService.findOne(id);
        
        if (profileDTO.isPresent()) {
            ApiResponse<ProfileDTO> response = ApiResponse.success("Profile retrieved successfully", profileDTO.get());
            // Count is automatically set to 1 for single object in ApiResponse constructor
            response.addMetadata("requestId", UUID.randomUUID().toString());
            response.addMetadata("entityName", ENTITY_NAME);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ProfileDTO> response = ApiResponse.error("Profile not found");
            response.setCount(0); // Explicitly set count to 0 for error case
            response.addMetadata("requestId", UUID.randomUUID().toString());
            response.addMetadata("entityId", id.toString());
            return ResponseEntity.status(404).body(response);
        }
    }

    /**
     * {@code DELETE  /profiles/:id} : delete the "id" profile.
     *
     * @param id the id of the profileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProfile(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Profile : {}", id);
        profileService.delete(id);
        
        ApiResponse<Void> response = ApiResponse.success("Profile deleted successfully", null);
        response.addMetadata("requestId", UUID.randomUUID().toString());
        response.addMetadata("entityId", id.toString());
        response.addMetadata("entityName", ENTITY_NAME);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .body(response);
    }
}
