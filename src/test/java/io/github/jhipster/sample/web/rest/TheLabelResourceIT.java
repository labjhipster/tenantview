package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterSampleApplicationApp;
import io.github.jhipster.sample.domain.TheLabel;
import io.github.jhipster.sample.repository.TheLabelRepository;
import io.github.jhipster.sample.service.TheLabelService;

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
 * Integration tests for the {@link TheLabelResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TheLabelResourceIT {

    private static final String DEFAULT_LABEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_NAME = "BBBBBBBBBB";

    @Autowired
    private TheLabelRepository theLabelRepository;

    @Autowired
    private TheLabelService theLabelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTheLabelMockMvc;

    private TheLabel theLabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TheLabel createEntity(EntityManager em) {
        TheLabel theLabel = new TheLabel()
            .labelName(DEFAULT_LABEL_NAME);
        return theLabel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TheLabel createUpdatedEntity(EntityManager em) {
        TheLabel theLabel = new TheLabel()
            .labelName(UPDATED_LABEL_NAME);
        return theLabel;
    }

    @BeforeEach
    public void initTest() {
        theLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTheLabel() throws Exception {
        int databaseSizeBeforeCreate = theLabelRepository.findAll().size();
        // Create the TheLabel
        restTheLabelMockMvc.perform(post("/api/the-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(theLabel)))
            .andExpect(status().isCreated());

        // Validate the TheLabel in the database
        List<TheLabel> theLabelList = theLabelRepository.findAll();
        assertThat(theLabelList).hasSize(databaseSizeBeforeCreate + 1);
        TheLabel testTheLabel = theLabelList.get(theLabelList.size() - 1);
        assertThat(testTheLabel.getLabelName()).isEqualTo(DEFAULT_LABEL_NAME);
    }

    @Test
    @Transactional
    public void createTheLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = theLabelRepository.findAll().size();

        // Create the TheLabel with an existing ID
        theLabel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTheLabelMockMvc.perform(post("/api/the-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(theLabel)))
            .andExpect(status().isBadRequest());

        // Validate the TheLabel in the database
        List<TheLabel> theLabelList = theLabelRepository.findAll();
        assertThat(theLabelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = theLabelRepository.findAll().size();
        // set the field null
        theLabel.setLabelName(null);

        // Create the TheLabel, which fails.


        restTheLabelMockMvc.perform(post("/api/the-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(theLabel)))
            .andExpect(status().isBadRequest());

        List<TheLabel> theLabelList = theLabelRepository.findAll();
        assertThat(theLabelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTheLabels() throws Exception {
        // Initialize the database
        theLabelRepository.saveAndFlush(theLabel);

        // Get all the theLabelList
        restTheLabelMockMvc.perform(get("/api/the-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].labelName").value(hasItem(DEFAULT_LABEL_NAME)));
    }
    
    @Test
    @Transactional
    public void getTheLabel() throws Exception {
        // Initialize the database
        theLabelRepository.saveAndFlush(theLabel);

        // Get the theLabel
        restTheLabelMockMvc.perform(get("/api/the-labels/{id}", theLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(theLabel.getId().intValue()))
            .andExpect(jsonPath("$.labelName").value(DEFAULT_LABEL_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTheLabel() throws Exception {
        // Get the theLabel
        restTheLabelMockMvc.perform(get("/api/the-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTheLabel() throws Exception {
        // Initialize the database
        theLabelService.save(theLabel);

        int databaseSizeBeforeUpdate = theLabelRepository.findAll().size();

        // Update the theLabel
        TheLabel updatedTheLabel = theLabelRepository.findById(theLabel.getId()).get();
        // Disconnect from session so that the updates on updatedTheLabel are not directly saved in db
        em.detach(updatedTheLabel);
        updatedTheLabel
            .labelName(UPDATED_LABEL_NAME);

        restTheLabelMockMvc.perform(put("/api/the-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTheLabel)))
            .andExpect(status().isOk());

        // Validate the TheLabel in the database
        List<TheLabel> theLabelList = theLabelRepository.findAll();
        assertThat(theLabelList).hasSize(databaseSizeBeforeUpdate);
        TheLabel testTheLabel = theLabelList.get(theLabelList.size() - 1);
        assertThat(testTheLabel.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTheLabel() throws Exception {
        int databaseSizeBeforeUpdate = theLabelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTheLabelMockMvc.perform(put("/api/the-labels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(theLabel)))
            .andExpect(status().isBadRequest());

        // Validate the TheLabel in the database
        List<TheLabel> theLabelList = theLabelRepository.findAll();
        assertThat(theLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTheLabel() throws Exception {
        // Initialize the database
        theLabelService.save(theLabel);

        int databaseSizeBeforeDelete = theLabelRepository.findAll().size();

        // Delete the theLabel
        restTheLabelMockMvc.perform(delete("/api/the-labels/{id}", theLabel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TheLabel> theLabelList = theLabelRepository.findAll();
        assertThat(theLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
