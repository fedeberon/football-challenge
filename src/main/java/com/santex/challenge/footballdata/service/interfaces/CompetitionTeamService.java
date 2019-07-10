package com.santex.challenge.footballdata.service.interfaces;

import com.santex.challenge.footballdata.domain.CompetitionTeam;

import java.util.List;

/**
 * Created by federicoberon on 09/07/2019.
 */
public interface CompetitionTeamService {
    List<CompetitionTeam> findAllByCompetitionCode(String s);
}
