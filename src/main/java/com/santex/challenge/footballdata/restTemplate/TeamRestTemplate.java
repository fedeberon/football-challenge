package com.santex.challenge.footballdata.restTemplate;

import com.santex.challenge.footballdata.bean.Util;
import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Competition;
import com.santex.challenge.footballdata.domain.Team;
import com.santex.challenge.footballdata.repositoty.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;

/**
 * Created by federicoberon on 03/07/2019.
 */

@Component
public class TeamRestTemplate implements Function<Competition, Wrapper<Team>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRestTemplate.class);

    private final RestTemplate restTemplate;

    private TeamRepository dao;

    @Value("${football-data.url}")
    private String url;

    @Value("${football-data.key}")
    private String key;

    @Value("${football-data.value}")
    private String value;

    @Autowired
    public TeamRestTemplate(TeamRepository dao, RestTemplateBuilder restTemplate) {
        this.dao = dao;
        this.restTemplate = restTemplate.build();
        this.restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(key, value);
            return execution.execute(request, body);
        });
    }


    @Override
    public Wrapper<Team> apply(Competition competitions) {
        try{
        Map<String, Object> mapResultTeams = restTemplate.getForObject(url.concat("competitions/").concat(competitions.getCode()).concat("/teams"),  Map.class);
        List<LinkedHashMap> matchesMap = (List<LinkedHashMap>) mapResultTeams.get("teams");
        List<Team> teams = convert(matchesMap);
        LOGGER.info("Teams converted succesfull");

        return new Wrapper(teams);
        }
        catch (ResourceAccessException e){
            LOGGER.error("Error when try to connect to team resources", new Object[] { e });
            throw e;
        }
    }

    private List<Team> convert(List<LinkedHashMap> list){
        List<Team> teams = new ArrayList<>();
        if(Objects.isNull(list)) return teams;

        list.forEach(anObject -> {
            Integer id = (Integer) anObject.get("id");
            String name = (String) anObject.get("name");
            String tla = (String) anObject.get("tla");
            String shortName = (String) anObject.get("shortName");
            String areaName =  Util.getValue((LinkedHashMap<String, String>) anObject.get("area"), "name");
            String email = (String) anObject.get("email");

            teams.add(new Team(id, name, tla, shortName, areaName, email));
        });

        return teams;
    }

}
