package com.santex.challenge.footballdata.repositoty;

import com.santex.challenge.footballdata.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by federicoberon on 01/07/2019.
 */
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query(value = "SELECT CASE  WHEN count(code)> 0 THEN true ELSE false END FROM Competition where code = ?1")
    boolean existCompetitionByCode(@PathVariable String code);

}
