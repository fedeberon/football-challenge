package com.santex.challenge.footballdata.service;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.CompetitionTeam;
import com.santex.challenge.footballdata.domain.Player;
import com.santex.challenge.footballdata.service.interfaces.CompetitionService;
import com.santex.challenge.footballdata.service.interfaces.CompetitionTeamService;
import com.santex.challenge.footballdata.service.interfaces.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by federicoberon on 08/07/2019.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private CompetitionTeamService competitionTeamService;

    private CompetitionService competitionService;

    @Autowired
    public PlayerServiceImpl(CompetitionTeamService competitionTeamService, CompetitionService competitionService) {
        this.competitionTeamService = competitionTeamService;
        this.competitionService = competitionService;
    }

    @Override
    public Wrapper<Player> totalPlayers(String code) {
        if(!competitionService.existCompetitionByCode(code)){
            LOGGER.warn("The league whith code {} is not exist in database", new Object[] { code });
            return new Wrapper(HttpStatus.NOT_FOUND);
        }

        List<CompetitionTeam> competitionTeams = competitionTeamService.findAllByCompetitionCode(code);
        Collection<Player> players = new ArrayList<>();
        competitionTeams.forEach(competitionTeam -> players.addAll(competitionTeam.getTeam().getPlayers()));

        return new Wrapper(null, String.valueOf(players.size()), HttpStatus.OK);
    }
}
