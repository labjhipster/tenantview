package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.Identifier;
import io.github.jhipster.sample.repository.IdentifierRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.Identifier}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IdentifierResource {

    private final Logger log = LoggerFactory.getLogger(IdentifierResource.class);

    private static final String ENTITY_NAME = "identifier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentifierRepository identifierRepository;

    public IdentifierResource(IdentifierRepository identifierRepository) {
        this.identifierRepository = identifierRepository;
    }

    /**
     * {@code POST  /identifiers} : Create a new identifier.
     *
     * @param identifier the identifier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identifier, or with status {@code 400 (Bad Request)} if the identifier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/identifiers")
    public ResponseEntity<Identifier> createIdentifier(@Valid @RequestBody Identifier identifier) throws URISyntaxException {
        log.debug("REST request to save Identifier : {}", identifier);
        if (identifier.getId() != null) {
            throw new BadRequestAlertException("A new identifier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Identifier result = identifierRepository.save(identifier);
        return ResponseEntity.created(new URI("/api/identifiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /identifiers} : Updates an existing identifier.
     *
     * @param identifier the identifier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identifier,
     * or with status {@code 400 (Bad Request)} if the identifier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the identifier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/identifiers")
    public ResponseEntity<Identifier> updateIdentifier(@Valid @RequestBody Identifier identifier) throws URISyntaxException {
        log.debug("REST request to update Identifier : {}", identifier);
        if (identifier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Identifier result = identifierRepository.save(identifier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, identifier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /identifiers} : get all the identifiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identifiers in body.
     */
    @GetMapping("/identifiers")
    public List<Identifier> getAllIdentifiers() {
        log.debug("REST request to get all Identifiers");
        return identifierRepository.findAll();
    }

    /**
     * {@code GET  /identifiers/:id} : get the "id" identifier.
     *
     * @param id the id of the identifier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identifier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/identifiers/{id}")
    public ResponseEntity<Identifier> getIdentifier(@PathVariable Long id) {
        log.debug("REST request to get Identifier : {}", id);
        Optional<Identifier> identifier = identifierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(identifier);
    }

    /**
     * {@code DELETE  /identifiers/:id} : delete the "id" identifier.
     *
     * @param id the id of the identifier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/identifiers/{id}")
    public ResponseEntity<Void> deleteIdentifier(@PathVariable Long id) {
        log.debug("REST request to delete Identifier : {}", id);
        identifierRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
