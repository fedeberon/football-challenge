package com.santex.challenge.footballdata.service;

import com.santex.challenge.footballdata.domain.CompetitionTeam;
import com.santex.challenge.footballdata.repositoty.CompetitionTeamRepository;
import com.santex.challenge.footballdata.service.interfaces.CompetitionTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by federicoberon on 09/07/2019.
 */
@Service
public class CompetitionTeamServiceImpl implements CompetitionTeamService {

    private CompetitionTeamRepository dao;

    @Autowired
    public CompetitionTeamServiceImpl(CompetitionTeamRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<CompetitionTeam> findAllByCompetitionCode(String s) {
        return  dao.findCompetitionTeamsByList(s);
    }
}
