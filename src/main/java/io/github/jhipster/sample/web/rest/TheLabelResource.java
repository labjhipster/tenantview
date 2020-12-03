package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.TheLabel;
import io.github.jhipster.sample.service.TheLabelService;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.TheLabel}.
 */
@RestController
@RequestMapping("/api")
public class TheLabelResource {

    private final Logger log = LoggerFactory.getLogger(TheLabelResource.class);

    private static final String ENTITY_NAME = "testRootTheLabel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TheLabelService theLabelService;

    public TheLabelResource(TheLabelService theLabelService) {
        this.theLabelService = theLabelService;
    }

    /**
     * {@code POST  /the-labels} : Create a new theLabel.
     *
     * @param theLabel the theLabel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new theLabel, or with status {@code 400 (Bad Request)} if the theLabel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/the-labels")
    public ResponseEntity<TheLabel> createTheLabel(@Valid @RequestBody TheLabel theLabel) throws URISyntaxException {
        log.debug("REST request to save TheLabel : {}", theLabel);
        if (theLabel.getId() != null) {
            throw new BadRequestAlertException("A new theLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TheLabel result = theLabelService.save(theLabel);
        return ResponseEntity.created(new URI("/api/the-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /the-labels} : Updates an existing theLabel.
     *
     * @param theLabel the theLabel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated theLabel,
     * or with status {@code 400 (Bad Request)} if the theLabel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the theLabel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/the-labels")
    public ResponseEntity<TheLabel> updateTheLabel(@Valid @RequestBody TheLabel theLabel) throws URISyntaxException {
        log.debug("REST request to update TheLabel : {}", theLabel);
        if (theLabel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TheLabel result = theLabelService.save(theLabel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, theLabel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /the-labels} : get all the theLabels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of theLabels in body.
     */
    @GetMapping("/the-labels")
    public ResponseEntity<List<TheLabel>> getAllTheLabels(Pageable pageable) {
        log.debug("REST request to get a page of TheLabels");
        Page<TheLabel> page = theLabelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /the-labels/:id} : get the "id" theLabel.
     *
     * @param id the id of the theLabel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the theLabel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/the-labels/{id}")
    public ResponseEntity<TheLabel> getTheLabel(@PathVariable Long id) {
        log.debug("REST request to get TheLabel : {}", id);
        Optional<TheLabel> theLabel = theLabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(theLabel);
    }

    /**
     * {@code DELETE  /the-labels/:id} : delete the "id" theLabel.
     *
     * @param id the id of the theLabel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/the-labels/{id}")
    public ResponseEntity<Void> deleteTheLabel(@PathVariable Long id) {
        log.debug("REST request to delete TheLabel : {}", id);
        theLabelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
