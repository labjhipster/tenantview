package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.SilverBadge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SilverBadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SilverBadgeRepository extends JpaRepository<SilverBadge, Long> {
}
