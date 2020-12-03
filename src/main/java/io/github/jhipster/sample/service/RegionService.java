package io.github.jhipster.sample.service;

import io.github.jhipster.sample.domain.Region;
import io.github.jhipster.sample.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Region}.
 */
@Service
@Transactional
public class RegionService {

    private final Logger log = LoggerFactory.getLogger(RegionService.class);

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Save a region.
     *
     * @param region the entity to save.
     * @return the persisted entity.
     */
    public Region save(Region region) {
        log.debug("Request to save Region : {}", region);
        return regionRepository.save(region);
    }

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Region> findAll() {
        log.debug("Request to get all Regions");
        return regionRepository.findAll();
    }


    /**
     * Get one region by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Region> findOne(Long id) {
        log.debug("Request to get Region : {}", id);
        return regionRepository.findById(id);
    }

    /**
     * Delete the region by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.deleteById(id);
    }
}
