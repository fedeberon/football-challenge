package com.santex.challenge.footballdata.restTemplate;

import com.santex.challenge.footballdata.bean.Util;
import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Competition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.function.Function;

/**
 * Created by federicoberon on 01/07/2019.
 */
@Component
public class CompetitionRestTemplate implements Function<String, Wrapper<Competition>>{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionRestTemplate.class);

    private final RestTemplate restTemplate;

    @Value("${football-data.url}")
    private String url;

    @Value("${football-data.key}")
    private String key;

    @Value("${football-data.value}")
    private String value;

    @Autowired
    public CompetitionRestTemplate(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
        this.restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(key, value);
            return execution.execute(request, body);
        });
    }

    @Override
    public Wrapper<Competition> apply(String code) {
        try{
            LinkedHashMap mapResult = restTemplate.getForObject(url.concat("competitions/").concat(code),  LinkedHashMap.class);
            Competition competition = convertCompetition(mapResult);
            LOGGER.info("Competition converted succesfull");

            return new Wrapper(competition);
        }
        catch (ResourceAccessException e){
            LOGGER.error("Error when try to connect to competition resources", new Object[] { e });
            throw e;
        }
    }

    private Competition convertCompetition(LinkedHashMap anObject){
        String name = (String) anObject.get("name");
        String area = Util.getValue((LinkedHashMap<String, String>) anObject.get("area"), "name");
        String code = (String) anObject.get("code");
        Integer idCompetition = (Integer) anObject.get("id");

        return new Competition(idCompetition, name, area, code);
    }

}
