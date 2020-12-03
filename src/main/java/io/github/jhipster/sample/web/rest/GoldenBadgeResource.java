package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.GoldenBadge;
import io.github.jhipster.sample.repository.GoldenBadgeRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.GoldenBadge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GoldenBadgeResource {

    private final Logger log = LoggerFactory.getLogger(GoldenBadgeResource.class);

    private static final String ENTITY_NAME = "goldenBadge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoldenBadgeRepository goldenBadgeRepository;

    public GoldenBadgeResource(GoldenBadgeRepository goldenBadgeRepository) {
        this.goldenBadgeRepository = goldenBadgeRepository;
    }

    /**
     * {@code POST  /golden-badges} : Create a new goldenBadge.
     *
     * @param goldenBadge the goldenBadge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goldenBadge, or with status {@code 400 (Bad Request)} if the goldenBadge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/golden-badges")
    public ResponseEntity<GoldenBadge> createGoldenBadge(@Valid @RequestBody GoldenBadge goldenBadge) throws URISyntaxException {
        log.debug("REST request to save GoldenBadge : {}", goldenBadge);
        if (goldenBadge.getId() != null) {
            throw new BadRequestAlertException("A new goldenBadge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoldenBadge result = goldenBadgeRepository.save(goldenBadge);
        return ResponseEntity.created(new URI("/api/golden-badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /golden-badges} : Updates an existing goldenBadge.
     *
     * @param goldenBadge the goldenBadge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldenBadge,
     * or with status {@code 400 (Bad Request)} if the goldenBadge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goldenBadge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/golden-badges")
    public ResponseEntity<GoldenBadge> updateGoldenBadge(@Valid @RequestBody GoldenBadge goldenBadge) throws URISyntaxException {
        log.debug("REST request to update GoldenBadge : {}", goldenBadge);
        if (goldenBadge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoldenBadge result = goldenBadgeRepository.save(goldenBadge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goldenBadge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /golden-badges} : get all the goldenBadges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goldenBadges in body.
     */
    @GetMapping("/golden-badges")
    public List<GoldenBadge> getAllGoldenBadges() {
        log.debug("REST request to get all GoldenBadges");
        return goldenBadgeRepository.findAll();
    }

    /**
     * {@code GET  /golden-badges/:id} : get the "id" goldenBadge.
     *
     * @param id the id of the goldenBadge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goldenBadge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/golden-badges/{id}")
    public ResponseEntity<GoldenBadge> getGoldenBadge(@PathVariable Long id) {
        log.debug("REST request to get GoldenBadge : {}", id);
        Optional<GoldenBadge> goldenBadge = goldenBadgeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(goldenBadge);
    }

    /**
     * {@code DELETE  /golden-badges/:id} : delete the "id" goldenBadge.
     *
     * @param id the id of the goldenBadge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/golden-badges/{id}")
    public ResponseEntity<Void> deleteGoldenBadge(@PathVariable Long id) {
        log.debug("REST request to delete GoldenBadge : {}", id);
        goldenBadgeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
