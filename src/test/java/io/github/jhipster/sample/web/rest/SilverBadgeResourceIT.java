package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterSampleApplicationApp;
import io.github.jhipster.sample.domain.SilverBadge;
import io.github.jhipster.sample.domain.Identifier;
import io.github.jhipster.sample.repository.SilverBadgeRepository;

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
 * Integration tests for the {@link SilverBadgeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SilverBadgeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SilverBadgeRepository silverBadgeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSilverBadgeMockMvc;

    private SilverBadge silverBadge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SilverBadge createEntity(EntityManager em) {
        SilverBadge silverBadge = new SilverBadge()
            .name(DEFAULT_NAME);
        // Add required entity
        Identifier identifier;
        if (TestUtil.findAll(em, Identifier.class).isEmpty()) {
            identifier = IdentifierResourceIT.createEntity(em);
            em.persist(identifier);
            em.flush();
        } else {
            identifier = TestUtil.findAll(em, Identifier.class).get(0);
        }
        silverBadge.setIden(identifier);
        return silverBadge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SilverBadge createUpdatedEntity(EntityManager em) {
        SilverBadge silverBadge = new SilverBadge()
            .name(UPDATED_NAME);
        // Add required entity
        Identifier identifier;
        if (TestUtil.findAll(em, Identifier.class).isEmpty()) {
            identifier = IdentifierResourceIT.createUpdatedEntity(em);
            em.persist(identifier);
            em.flush();
        } else {
            identifier = TestUtil.findAll(em, Identifier.class).get(0);
        }
        silverBadge.setIden(identifier);
        return silverBadge;
    }

    @BeforeEach
    public void initTest() {
        silverBadge = createEntity(em);
    }

    @Test
    @Transactional
    public void createSilverBadge() throws Exception {
        int databaseSizeBeforeCreate = silverBadgeRepository.findAll().size();
        // Create the SilverBadge
        restSilverBadgeMockMvc.perform(post("/api/silver-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(silverBadge)))
            .andExpect(status().isCreated());

        // Validate the SilverBadge in the database
        List<SilverBadge> silverBadgeList = silverBadgeRepository.findAll();
        assertThat(silverBadgeList).hasSize(databaseSizeBeforeCreate + 1);
        SilverBadge testSilverBadge = silverBadgeList.get(silverBadgeList.size() - 1);
        assertThat(testSilverBadge.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSilverBadgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = silverBadgeRepository.findAll().size();

        // Create the SilverBadge with an existing ID
        silverBadge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSilverBadgeMockMvc.perform(post("/api/silver-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(silverBadge)))
            .andExpect(status().isBadRequest());

        // Validate the SilverBadge in the database
        List<SilverBadge> silverBadgeList = silverBadgeRepository.findAll();
        assertThat(silverBadgeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSilverBadges() throws Exception {
        // Initialize the database
        silverBadgeRepository.saveAndFlush(silverBadge);

        // Get all the silverBadgeList
        restSilverBadgeMockMvc.perform(get("/api/silver-badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(silverBadge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSilverBadge() throws Exception {
        // Initialize the database
        silverBadgeRepository.saveAndFlush(silverBadge);

        // Get the silverBadge
        restSilverBadgeMockMvc.perform(get("/api/silver-badges/{id}", silverBadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(silverBadge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingSilverBadge() throws Exception {
        // Get the silverBadge
        restSilverBadgeMockMvc.perform(get("/api/silver-badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSilverBadge() throws Exception {
        // Initialize the database
        silverBadgeRepository.saveAndFlush(silverBadge);

        int databaseSizeBeforeUpdate = silverBadgeRepository.findAll().size();

        // Update the silverBadge
        SilverBadge updatedSilverBadge = silverBadgeRepository.findById(silverBadge.getId()).get();
        // Disconnect from session so that the updates on updatedSilverBadge are not directly saved in db
        em.detach(updatedSilverBadge);
        updatedSilverBadge
            .name(UPDATED_NAME);

        restSilverBadgeMockMvc.perform(put("/api/silver-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSilverBadge)))
            .andExpect(status().isOk());

        // Validate the SilverBadge in the database
        List<SilverBadge> silverBadgeList = silverBadgeRepository.findAll();
        assertThat(silverBadgeList).hasSize(databaseSizeBeforeUpdate);
        SilverBadge testSilverBadge = silverBadgeList.get(silverBadgeList.size() - 1);
        assertThat(testSilverBadge.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSilverBadge() throws Exception {
        int databaseSizeBeforeUpdate = silverBadgeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSilverBadgeMockMvc.perform(put("/api/silver-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(silverBadge)))
            .andExpect(status().isBadRequest());

        // Validate the SilverBadge in the database
        List<SilverBadge> silverBadgeList = silverBadgeRepository.findAll();
        assertThat(silverBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSilverBadge() throws Exception {
        // Initialize the database
        silverBadgeRepository.saveAndFlush(silverBadge);

        int databaseSizeBeforeDelete = silverBadgeRepository.findAll().size();

        // Delete the silverBadge
        restSilverBadgeMockMvc.perform(delete("/api/silver-badges/{id}", silverBadge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SilverBadge> silverBadgeList = silverBadgeRepository.findAll();
        assertThat(silverBadgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
