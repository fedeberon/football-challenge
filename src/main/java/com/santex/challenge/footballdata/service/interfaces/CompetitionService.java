package com.santex.challenge.footballdata.service.interfaces;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Competition;

/**
 * Created by federicoberon on 03/07/2019.
 */
public interface CompetitionService {

    Wrapper<Competition> importCode(String code);

    boolean existCompetitionByCode(String code);
}
