package com.santex.challenge.footballdata.service;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Competition;
import com.santex.challenge.footballdata.domain.CompetitionTeam;
import com.santex.challenge.footballdata.domain.Player;
import com.santex.challenge.footballdata.domain.Team;
import com.santex.challenge.footballdata.repositoty.CompetitionRepository;
import com.santex.challenge.footballdata.repositoty.CompetitionTeamRepository;
import com.santex.challenge.footballdata.repositoty.PlayerRepository;
import com.santex.challenge.footballdata.repositoty.TeamRepository;
import com.santex.challenge.footballdata.service.interfaces.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by federicoberon on 03/07/2019.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private CompetitionRepository dao;

    private TeamRepository teamRepository;

    private Function<String, Wrapper<Competition>> template;

    private Function<Competition, Wrapper<Team>> templateForTeams;

    private Function<Team, Wrapper<Player>> templateForPlayer;

    private PlayerRepository playerRepository;

    private CompetitionTeamRepository competitionTeamRepository;

    @Autowired
    public CompetitionServiceImpl(CompetitionRepository dao,
                                  TeamRepository teamRepository,
                                  Function<String, Wrapper<Competition>> template,
                                  Function<Competition, Wrapper<Team>> templateForTeams,
                                  Function<Team, Wrapper<Player>> templateForPlayer,
                                  PlayerRepository playerRepository,
                                  CompetitionTeamRepository competitionTeamRepository) {
        this.dao = dao;
        this.teamRepository = teamRepository;
        this.template = template;
        this.templateForTeams = templateForTeams;
        this.templateForPlayer = templateForPlayer;
        this.playerRepository = playerRepository;
        this.competitionTeamRepository = competitionTeamRepository;
    }

    /**
     * Steps:
     *  1_ Find and Save Competition
     *  2_ Find and Save all Teams of Competition
     *  3_ Find all Player per Team and Save All Teams
     *  4_ Create asociation between Competition and Team and save all
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public Wrapper<Competition> importCode(String code){
        if(this.existCompetitionByCode(code)){
            LOGGER.warn("The league whith code {} is already imported", new Object[] { code });
            return new Wrapper(Wrapper.LEAGUE_ALREADY_IMPORTED, HttpStatus.CONFLICT);
        }

        Wrapper<Competition> wrapperCompetition = template.apply(code);
        if(Objects.isNull(wrapperCompetition.getT())){
            LOGGER.error("The league whith code {} is not found", new Object[] { code });
            return new Wrapper(Wrapper.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        dao.save(wrapperCompetition.getT());

        Wrapper<Team> wraperTeams = templateForTeams.apply(wrapperCompetition.getT());
        wraperTeams.getList().forEach(team -> teamRepository.saveAndFlush(team));

        wraperTeams.getList().forEach(team -> {
            Wrapper<Player> players = templateForPlayer.apply(team);
            if(Objects.nonNull(players))
                playerRepository.saveAll(players.getList());
        });

        List<CompetitionTeam> competitionTeams = new ArrayList<>();
        wraperTeams.getList().forEach(team -> competitionTeams.add(new CompetitionTeam(team, wrapperCompetition.getT())));
        competitionTeamRepository.saveAll(competitionTeams);

        LOGGER.info("The league whith code {} is successfully imported", new Object[] { code });
        wrapperCompetition.setMessage(Wrapper.SUCCESSFULLY_IMPORTED);
        wrapperCompetition.setStatus(HttpStatus.CREATED);

        return wrapperCompetition;
    }

    @Override
    public boolean existCompetitionByCode(String code) {
        return dao.existCompetitionByCode(code);
    }

}
