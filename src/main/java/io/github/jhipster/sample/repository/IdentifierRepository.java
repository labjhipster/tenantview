package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.Identifier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Identifier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentifierRepository extends JpaRepository<Identifier, Long> {
}
