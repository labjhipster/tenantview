package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.SilverBadge;
import io.github.jhipster.sample.repository.SilverBadgeRepository;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.SilverBadge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SilverBadgeResource {

    private final Logger log = LoggerFactory.getLogger(SilverBadgeResource.class);

    private static final String ENTITY_NAME = "silverBadge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SilverBadgeRepository silverBadgeRepository;

    public SilverBadgeResource(SilverBadgeRepository silverBadgeRepository) {
        this.silverBadgeRepository = silverBadgeRepository;
    }

    /**
     * {@code POST  /silver-badges} : Create a new silverBadge.
     *
     * @param silverBadge the silverBadge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new silverBadge, or with status {@code 400 (Bad Request)} if the silverBadge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/silver-badges")
    public ResponseEntity<SilverBadge> createSilverBadge(@Valid @RequestBody SilverBadge silverBadge) throws URISyntaxException {
        log.debug("REST request to save SilverBadge : {}", silverBadge);
        if (silverBadge.getId() != null) {
            throw new BadRequestAlertException("A new silverBadge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SilverBadge result = silverBadgeRepository.save(silverBadge);
        return ResponseEntity.created(new URI("/api/silver-badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /silver-badges} : Updates an existing silverBadge.
     *
     * @param silverBadge the silverBadge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated silverBadge,
     * or with status {@code 400 (Bad Request)} if the silverBadge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the silverBadge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/silver-badges")
    public ResponseEntity<SilverBadge> updateSilverBadge(@Valid @RequestBody SilverBadge silverBadge) throws URISyntaxException {
        log.debug("REST request to update SilverBadge : {}", silverBadge);
        if (silverBadge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SilverBadge result = silverBadgeRepository.save(silverBadge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, silverBadge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /silver-badges} : get all the silverBadges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of silverBadges in body.
     */
    @GetMapping("/silver-badges")
    public List<SilverBadge> getAllSilverBadges() {
        log.debug("REST request to get all SilverBadges");
        return silverBadgeRepository.findAll();
    }

    /**
     * {@code GET  /silver-badges/:id} : get the "id" silverBadge.
     *
     * @param id the id of the silverBadge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the silverBadge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/silver-badges/{id}")
    public ResponseEntity<SilverBadge> getSilverBadge(@PathVariable Long id) {
        log.debug("REST request to get SilverBadge : {}", id);
        Optional<SilverBadge> silverBadge = silverBadgeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(silverBadge);
    }

    /**
     * {@code DELETE  /silver-badges/:id} : delete the "id" silverBadge.
     *
     * @param id the id of the silverBadge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/silver-badges/{id}")
    public ResponseEntity<Void> deleteSilverBadge(@PathVariable Long id) {
        log.debug("REST request to delete SilverBadge : {}", id);
        silverBadgeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
