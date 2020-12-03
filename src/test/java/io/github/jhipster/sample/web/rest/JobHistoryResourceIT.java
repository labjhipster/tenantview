package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterSampleApplicationApp;
import io.github.jhipster.sample.domain.JobHistory;
import io.github.jhipster.sample.repository.JobHistoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static io.github.jhipster.sample.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.domain.enumeration.Language;
/**
 * Integration tests for the {@link JobHistoryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobHistoryResourceIT {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Mock
    private JobHistoryRepository jobHistoryRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobHistoryMockMvc;

    private JobHistory jobHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .language(DEFAULT_LANGUAGE);
        return jobHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createUpdatedEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .language(UPDATED_LANGUAGE);
        return jobHistory;
    }

    @BeforeEach
    public void initTest() {
        jobHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobHistory() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();
        // Create the JobHistory
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isCreated());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJobHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testJobHistory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createJobHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory with an existing ID
        jobHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobHistoryMockMvc.perform(post("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobHistories() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllJobHistoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(jobHistoryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobHistoryMockMvc.perform(get("/api/job-histories?eagerload=true"))
            .andExpect(status().isOk());

        verify(jobHistoryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllJobHistoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(jobHistoryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobHistoryMockMvc.perform(get("/api/job-histories?eagerload=true"))
            .andExpect(status().isOk());

        verify(jobHistoryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJobHistory() throws Exception {
        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Update the jobHistory
        JobHistory updatedJobHistory = jobHistoryRepository.findById(jobHistory.getId()).get();
        // Disconnect from session so that the updates on updatedJobHistory are not directly saved in db
        em.detach(updatedJobHistory);
        updatedJobHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .language(UPDATED_LANGUAGE);

        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobHistory)))
            .andExpect(status().isOk());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJobHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testJobHistory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingJobHistory() throws Exception {
        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc.perform(put("/api/job-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistory)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        int databaseSizeBeforeDelete = jobHistoryRepository.findAll().size();

        // Delete the jobHistory
        restJobHistoryMockMvc.perform(delete("/api/job-histories/{id}", jobHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
