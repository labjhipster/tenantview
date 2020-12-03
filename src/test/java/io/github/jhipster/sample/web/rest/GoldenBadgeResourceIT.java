package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterSampleApplicationApp;
import io.github.jhipster.sample.domain.GoldenBadge;
import io.github.jhipster.sample.domain.Identifier;
import io.github.jhipster.sample.repository.GoldenBadgeRepository;

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
 * Integration tests for the {@link GoldenBadgeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoldenBadgeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GoldenBadgeRepository goldenBadgeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoldenBadgeMockMvc;

    private GoldenBadge goldenBadge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldenBadge createEntity(EntityManager em) {
        GoldenBadge goldenBadge = new GoldenBadge()
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
        goldenBadge.setIden(identifier);
        return goldenBadge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldenBadge createUpdatedEntity(EntityManager em) {
        GoldenBadge goldenBadge = new GoldenBadge()
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
        goldenBadge.setIden(identifier);
        return goldenBadge;
    }

    @BeforeEach
    public void initTest() {
        goldenBadge = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoldenBadge() throws Exception {
        int databaseSizeBeforeCreate = goldenBadgeRepository.findAll().size();
        // Create the GoldenBadge
        restGoldenBadgeMockMvc.perform(post("/api/golden-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goldenBadge)))
            .andExpect(status().isCreated());

        // Validate the GoldenBadge in the database
        List<GoldenBadge> goldenBadgeList = goldenBadgeRepository.findAll();
        assertThat(goldenBadgeList).hasSize(databaseSizeBeforeCreate + 1);
        GoldenBadge testGoldenBadge = goldenBadgeList.get(goldenBadgeList.size() - 1);
        assertThat(testGoldenBadge.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGoldenBadgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goldenBadgeRepository.findAll().size();

        // Create the GoldenBadge with an existing ID
        goldenBadge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoldenBadgeMockMvc.perform(post("/api/golden-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goldenBadge)))
            .andExpect(status().isBadRequest());

        // Validate the GoldenBadge in the database
        List<GoldenBadge> goldenBadgeList = goldenBadgeRepository.findAll();
        assertThat(goldenBadgeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGoldenBadges() throws Exception {
        // Initialize the database
        goldenBadgeRepository.saveAndFlush(goldenBadge);

        // Get all the goldenBadgeList
        restGoldenBadgeMockMvc.perform(get("/api/golden-badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goldenBadge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGoldenBadge() throws Exception {
        // Initialize the database
        goldenBadgeRepository.saveAndFlush(goldenBadge);

        // Get the goldenBadge
        restGoldenBadgeMockMvc.perform(get("/api/golden-badges/{id}", goldenBadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goldenBadge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGoldenBadge() throws Exception {
        // Get the goldenBadge
        restGoldenBadgeMockMvc.perform(get("/api/golden-badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoldenBadge() throws Exception {
        // Initialize the database
        goldenBadgeRepository.saveAndFlush(goldenBadge);

        int databaseSizeBeforeUpdate = goldenBadgeRepository.findAll().size();

        // Update the goldenBadge
        GoldenBadge updatedGoldenBadge = goldenBadgeRepository.findById(goldenBadge.getId()).get();
        // Disconnect from session so that the updates on updatedGoldenBadge are not directly saved in db
        em.detach(updatedGoldenBadge);
        updatedGoldenBadge
            .name(UPDATED_NAME);

        restGoldenBadgeMockMvc.perform(put("/api/golden-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoldenBadge)))
            .andExpect(status().isOk());

        // Validate the GoldenBadge in the database
        List<GoldenBadge> goldenBadgeList = goldenBadgeRepository.findAll();
        assertThat(goldenBadgeList).hasSize(databaseSizeBeforeUpdate);
        GoldenBadge testGoldenBadge = goldenBadgeList.get(goldenBadgeList.size() - 1);
        assertThat(testGoldenBadge.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGoldenBadge() throws Exception {
        int databaseSizeBeforeUpdate = goldenBadgeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldenBadgeMockMvc.perform(put("/api/golden-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goldenBadge)))
            .andExpect(status().isBadRequest());

        // Validate the GoldenBadge in the database
        List<GoldenBadge> goldenBadgeList = goldenBadgeRepository.findAll();
        assertThat(goldenBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoldenBadge() throws Exception {
        // Initialize the database
        goldenBadgeRepository.saveAndFlush(goldenBadge);

        int databaseSizeBeforeDelete = goldenBadgeRepository.findAll().size();

        // Delete the goldenBadge
        restGoldenBadgeMockMvc.perform(delete("/api/golden-badges/{id}", goldenBadge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoldenBadge> goldenBadgeList = goldenBadgeRepository.findAll();
        assertThat(goldenBadgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
