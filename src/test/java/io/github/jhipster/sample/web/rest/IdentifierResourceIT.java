package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterSampleApplicationApp;
import io.github.jhipster.sample.domain.Identifier;
import io.github.jhipster.sample.repository.IdentifierRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IdentifierResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IdentifierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IdentifierRepository identifierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentifierMockMvc;

    private Identifier identifier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identifier createEntity(EntityManager em) {
        Identifier identifier = new Identifier()
            .name(DEFAULT_NAME);
        return identifier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identifier createUpdatedEntity(EntityManager em) {
        Identifier identifier = new Identifier()
            .name(UPDATED_NAME);
        return identifier;
    }

    @BeforeEach
    public void initTest() {
        identifier = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentifier() throws Exception {
        int databaseSizeBeforeCreate = identifierRepository.findAll().size();
        // Create the Identifier
        restIdentifierMockMvc.perform(post("/api/identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identifier)))
            .andExpect(status().isCreated());

        // Validate the Identifier in the database
        List<Identifier> identifierList = identifierRepository.findAll();
        assertThat(identifierList).hasSize(databaseSizeBeforeCreate + 1);
        Identifier testIdentifier = identifierList.get(identifierList.size() - 1);
        assertThat(testIdentifier.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createIdentifierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identifierRepository.findAll().size();

        // Create the Identifier with an existing ID
        identifier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentifierMockMvc.perform(post("/api/identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identifier)))
            .andExpect(status().isBadRequest());

        // Validate the Identifier in the database
        List<Identifier> identifierList = identifierRepository.findAll();
        assertThat(identifierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifierRepository.findAll().size();
        // set the field null
        identifier.setName(null);

        // Create the Identifier, which fails.


        restIdentifierMockMvc.perform(post("/api/identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identifier)))
            .andExpect(status().isBadRequest());

        List<Identifier> identifierList = identifierRepository.findAll();
        assertThat(identifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdentifiers() throws Exception {
        // Initialize the database
        identifierRepository.saveAndFlush(identifier);

        // Get all the identifierList
        restIdentifierMockMvc.perform(get("/api/identifiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identifier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getIdentifier() throws Exception {
        // Initialize the database
        identifierRepository.saveAndFlush(identifier);

        // Get the identifier
        restIdentifierMockMvc.perform(get("/api/identifiers/{id}", identifier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identifier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingIdentifier() throws Exception {
        // Get the identifier
        restIdentifierMockMvc.perform(get("/api/identifiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentifier() throws Exception {
        // Initialize the database
        identifierRepository.saveAndFlush(identifier);

        int databaseSizeBeforeUpdate = identifierRepository.findAll().size();

        // Update the identifier
        Identifier updatedIdentifier = identifierRepository.findById(identifier.getId()).get();
        // Disconnect from session so that the updates on updatedIdentifier are not directly saved in db
        em.detach(updatedIdentifier);
        updatedIdentifier
            .name(UPDATED_NAME);

        restIdentifierMockMvc.perform(put("/api/identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdentifier)))
            .andExpect(status().isOk());

        // Validate the Identifier in the database
        List<Identifier> identifierList = identifierRepository.findAll();
        assertThat(identifierList).hasSize(databaseSizeBeforeUpdate);
        Identifier testIdentifier = identifierList.get(identifierList.size() - 1);
        assertThat(testIdentifier.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = identifierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentifierMockMvc.perform(put("/api/identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identifier)))
            .andExpect(status().isBadRequest());

        // Validate the Identifier in the database
        List<Identifier> identifierList = identifierRepository.findAll();
        assertThat(identifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdentifier() throws Exception {
        // Initialize the database
        identifierRepository.saveAndFlush(identifier);

        int databaseSizeBeforeDelete = identifierRepository.findAll().size();

        // Delete the identifier
        restIdentifierMockMvc.perform(delete("/api/identifiers/{id}", identifier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Identifier> identifierList = identifierRepository.findAll();
        assertThat(identifierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
