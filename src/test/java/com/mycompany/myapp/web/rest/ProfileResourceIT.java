package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ProfileAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Profile;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.ProfileRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.ProfileService;
import com.mycompany.myapp.service.dto.ProfileDTO;
import com.mycompany.myapp.service.mapper.ProfileMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProfileResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Autowired
    private ProfileMapper profileMapper;

    @Mock
    private ProfileService profileServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    private Profile insertedProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity() {
        return new Profile()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .dob(DEFAULT_DOB)
            .address(DEFAULT_ADDRESS)
            .gender(DEFAULT_GENDER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity() {
        return new Profile()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER);
    }

    @BeforeEach
    void initTest() {
        profile = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedProfile != null) {
            profileRepository.delete(insertedProfile);
            insertedProfile = null;
        }
    }

    @Test
    @Transactional
    void createProfile() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        var returnedProfileDTO = om.readValue(
            restProfileMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profileDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProfileDTO.class
        );

        // Validate the Profile in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProfile = profileMapper.toEntity(returnedProfileDTO);
        assertProfileUpdatableFieldsEquals(returnedProfile, getPersistedProfile(returnedProfile));

        insertedProfile = returnedProfile;
    }

    @Test
    @Transactional
    void createProfileWithExistingId() throws Exception {
        // Create the Profile with an existing ID
        insertedProfile = profileRepository.saveAndFlush(profile);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfiles() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(sameInstant(DEFAULT_DOB))))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(profileRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProfile() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dob").value(sameInstant(DEFAULT_DOB)))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER));
    }

    @Test
    @Transactional
    void getProfilesByIdFiltering() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        UUID id = profile.getId();

        defaultProfileFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName equals to
        defaultProfileFiltering("firstName.equals=" + DEFAULT_FIRST_NAME, "firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName in
        defaultProfileFiltering("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME, "firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName is not null
        defaultProfileFiltering("firstName.specified=true", "firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName contains
        defaultProfileFiltering("firstName.contains=" + DEFAULT_FIRST_NAME, "firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where firstName does not contain
        defaultProfileFiltering("firstName.doesNotContain=" + UPDATED_FIRST_NAME, "firstName.doesNotContain=" + DEFAULT_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName equals to
        defaultProfileFiltering("lastName.equals=" + DEFAULT_LAST_NAME, "lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName in
        defaultProfileFiltering("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME, "lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName is not null
        defaultProfileFiltering("lastName.specified=true", "lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName contains
        defaultProfileFiltering("lastName.contains=" + DEFAULT_LAST_NAME, "lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where lastName does not contain
        defaultProfileFiltering("lastName.doesNotContain=" + UPDATED_LAST_NAME, "lastName.doesNotContain=" + DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllProfilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone equals to
        defaultProfileFiltering("phone.equals=" + DEFAULT_PHONE, "phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllProfilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone in
        defaultProfileFiltering("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE, "phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllProfilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone is not null
        defaultProfileFiltering("phone.specified=true", "phone.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone contains
        defaultProfileFiltering("phone.contains=" + DEFAULT_PHONE, "phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllProfilesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone does not contain
        defaultProfileFiltering("phone.doesNotContain=" + UPDATED_PHONE, "phone.doesNotContain=" + DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void getAllProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where email equals to
        defaultProfileFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where email in
        defaultProfileFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where email is not null
        defaultProfileFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where email contains
        defaultProfileFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfilesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where email does not contain
        defaultProfileFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob equals to
        defaultProfileFiltering("dob.equals=" + DEFAULT_DOB, "dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob in
        defaultProfileFiltering("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB, "dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob is not null
        defaultProfileFiltering("dob.specified=true", "dob.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob is greater than or equal to
        defaultProfileFiltering("dob.greaterThanOrEqual=" + DEFAULT_DOB, "dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob is less than or equal to
        defaultProfileFiltering("dob.lessThanOrEqual=" + DEFAULT_DOB, "dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob is less than
        defaultProfileFiltering("dob.lessThan=" + UPDATED_DOB, "dob.lessThan=" + DEFAULT_DOB);
    }

    @Test
    @Transactional
    void getAllProfilesByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where dob is greater than
        defaultProfileFiltering("dob.greaterThan=" + SMALLER_DOB, "dob.greaterThan=" + DEFAULT_DOB);
    }

    @Test
    @Transactional
    void getAllProfilesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where address equals to
        defaultProfileFiltering("address.equals=" + DEFAULT_ADDRESS, "address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProfilesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where address in
        defaultProfileFiltering("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS, "address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProfilesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where address is not null
        defaultProfileFiltering("address.specified=true", "address.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where address contains
        defaultProfileFiltering("address.contains=" + DEFAULT_ADDRESS, "address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProfilesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where address does not contain
        defaultProfileFiltering("address.doesNotContain=" + UPDATED_ADDRESS, "address.doesNotContain=" + DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProfilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender equals to
        defaultProfileFiltering("gender.equals=" + DEFAULT_GENDER, "gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllProfilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender in
        defaultProfileFiltering("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER, "gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllProfilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender is not null
        defaultProfileFiltering("gender.specified=true", "gender.specified=false");
    }

    @Test
    @Transactional
    void getAllProfilesByGenderContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender contains
        defaultProfileFiltering("gender.contains=" + DEFAULT_GENDER, "gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllProfilesByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender does not contain
        defaultProfileFiltering("gender.doesNotContain=" + UPDATED_GENDER, "gender.doesNotContain=" + DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void getAllProfilesByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            profileRepository.saveAndFlush(profile);
            user = UserResourceIT.createEntity();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        profile.setUser(user);
        profileRepository.saveAndFlush(profile);
        Long userId = user.getId();
        // Get all the profileList where user equals to userId
        defaultProfileShouldBeFound("userId.equals=" + userId);

        // Get all the profileList where user equals to (userId + 1)
        defaultProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    private void defaultProfileFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProfileShouldBeFound(shouldBeFound);
        defaultProfileShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(sameInstant(DEFAULT_DOB))))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)));

        // Check, that the count call also returns 1
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfile() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profileDTO))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfileToMatchAllProperties(updatedProfile);
    }

    @Test
    @Transactional
    void putNonExistingProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfileUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProfile, profile), getPersistedProfile(profile));
    }

    @Test
    @Transactional
    void fullUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .gender(UPDATED_GENDER);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfileUpdatableFieldsEquals(partialUpdatedProfile, getPersistedProfile(partialUpdatedProfile));
    }

    @Test
    @Transactional
    void patchNonExistingProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profile.setId(UUID.randomUUID());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(profileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfile() throws Exception {
        // Initialize the database
        insertedProfile = profileRepository.saveAndFlush(profile);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the profile
        restProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, profile.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return profileRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Profile getPersistedProfile(Profile profile) {
        return profileRepository.findById(profile.getId()).orElseThrow();
    }

    protected void assertPersistedProfileToMatchAllProperties(Profile expectedProfile) {
        assertProfileAllPropertiesEquals(expectedProfile, getPersistedProfile(expectedProfile));
    }

    protected void assertPersistedProfileToMatchUpdatableProperties(Profile expectedProfile) {
        assertProfileAllUpdatablePropertiesEquals(expectedProfile, getPersistedProfile(expectedProfile));
    }
}
