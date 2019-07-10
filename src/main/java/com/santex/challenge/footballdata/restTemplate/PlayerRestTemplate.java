package com.santex.challenge.footballdata.restTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.santex.challenge.footballdata.bean.Util;
import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Player;
import com.santex.challenge.footballdata.domain.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

/**
 * Created by federicoberon on 04/07/2019.
 */
@Component
public class PlayerRestTemplate implements Function<Team, Wrapper<Player>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRestTemplate.class);

    private final RestTemplate restTemplate;

    @Value("${football-data.url}")
    private String url;

    @Value("${football-data.key}")
    private String key;

    @Value("${football-data.value}")
    private String value;

    @Autowired
    public PlayerRestTemplate(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
        this.restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(key, value);
            return execution.execute(request, body);
        });
    }

    @HystrixCommand(fallbackMethod = "applyDefault")
    public Wrapper<Player> apply(Team team) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(key, value);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity <LinkedHashMap> entity = new HttpEntity<>(headers);
            ResponseEntity<LinkedHashMap> mapResultTeams = restTemplate.exchange(url.concat("teams/").concat(String.valueOf(team.getId())), HttpMethod.GET, entity, LinkedHashMap.class);

            this.checkRequestsAvailable(mapResultTeams.getHeaders());

            List<LinkedHashMap> playersMap = (List<LinkedHashMap>) mapResultTeams.getBody().get("squad");
            List<Player> players = convert(playersMap);
            LOGGER.info("Players from team {} was converted successfuly", new Object[]{team.getShortName()});

            players.forEach(p -> p.setTeam(team));

            return new Wrapper(players);

        } catch (ResourceAccessException e) {
            LOGGER.error("Error when try to connect to player resources", new Object[]{e});
            throw e;
        }
    }

    private List<Player> convert(List<LinkedHashMap> list) {
        List<Player> players = new ArrayList<>();
        if (Objects.isNull(list)) return players;

        list.forEach(
                Util.wrapper(
                        anObject -> {
                            Integer id = (Integer) anObject.get("id");
                            String name = (String) anObject.get("name");
                            String position = (String) anObject.get("position");
                            String dateOfBirth = (String) anObject.get("dateOfBirth");
                            String countryOfBirth = (String) anObject.get("countrUtiyOfBirth");
                            String nationality = (String) anObject.get("nationality");
                            players.add(new Player(id, name, position, LocalDate.parse(dateOfBirth, Util.formatter), countryOfBirth, nationality));
                        }));

        return players;
    }


    public Wrapper<Player> applyDefault(Team teams) {
        LOGGER.error("Intent connectiing with player resources. " + teams.getShortName());

        return null;
    }


    public List<String> get(Map map, String key) {
        return (List)map.get(key);
    }

    void checkRequestsAvailable(Map map){
        List<String> minutesAvailable = get(map, "X-Requests-Available-Minute");
        List<String> counterRequest = get(map, "X-RequestCounter-Reset");

        Integer numberOfRequest = Integer.valueOf(minutesAvailable.get(0));
        Integer secondsBloqued = Integer.valueOf(counterRequest.get(0));

        if(numberOfRequest > 1) return;

        else {
            try {
                LOGGER.warn("Waiting for time available. {} seconds " , new Object[] { secondsBloqued });
                Thread.sleep(secondsBloqued * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
