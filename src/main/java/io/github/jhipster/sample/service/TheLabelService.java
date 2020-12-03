package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.TheLabel;
import io.github.jhipster.sample.repository.TheLabelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TheLabel}.
 */
@Service
@Transactional
public class TheLabelService {

    private final Logger log = LoggerFactory.getLogger(TheLabelService.class);

    private final TheLabelRepository theLabelRepository;

    public TheLabelService(TheLabelRepository theLabelRepository) {
        this.theLabelRepository = theLabelRepository;
    }

    /**
     * Save a theLabel.
     *
     * @param theLabel the entity to save.
     * @return the persisted entity.
     */
    public TheLabel save(TheLabel theLabel) {
        log.debug("Request to save TheLabel : {}", theLabel);
        return theLabelRepository.save(theLabel);
    }

    /**
     * Get all the theLabels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TheLabel> findAll(Pageable pageable) {
        log.debug("Request to get all TheLabels");
        return theLabelRepository.findAll(pageable);
    }


    /**
     * Get one theLabel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TheLabel> findOne(Long id) {
        log.debug("Request to get TheLabel : {}", id);
        return theLabelRepository.findById(id);
    }

    /**
     * Delete the theLabel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TheLabel : {}", id);
        theLabelRepository.deleteById(id);
    }
}
