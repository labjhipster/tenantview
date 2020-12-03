package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.GoldenBadge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GoldenBadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoldenBadgeRepository extends JpaRepository<GoldenBadge, Long> {
}
