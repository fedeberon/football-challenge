package com.santex.challenge.footballdata.repositoty;

import com.santex.challenge.footballdata.domain.CompetitionTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by federicoberon on 04/07/2019.
 */
public interface CompetitionTeamRepository extends JpaRepository<CompetitionTeam, Integer>{

    @Query("from CompetitionTeam where competition.code = ?1")
    List<CompetitionTeam> findCompetitionTeamsByList(@PathVariable String code);

}
