package io.github.jhipster.sample.repository;

import io.github.jhipster.sample.domain.JobHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the JobHistory entity.
 */
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {

    @Query(value = "select distinct jobHistory from JobHistory jobHistory left join fetch jobHistory.departments left join fetch jobHistory.jobs left join fetch jobHistory.emps",
        countQuery = "select count(distinct jobHistory) from JobHistory jobHistory")
    Page<JobHistory> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct jobHistory from JobHistory jobHistory left join fetch jobHistory.departments left join fetch jobHistory.jobs left join fetch jobHistory.emps")
    List<JobHistory> findAllWithEagerRelationships();

    @Query("select jobHistory from JobHistory jobHistory left join fetch jobHistory.departments left join fetch jobHistory.jobs left join fetch jobHistory.emps where jobHistory.id =:id")
    Optional<JobHistory> findOneWithEagerRelationships(@Param("id") Long id);
}
