package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.TheLabel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TheLabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TheLabelRepository extends JpaRepository<TheLabel, Long> {
}
