package com.santex.challenge.footballdata.service.interfaces;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Player;

/**
 * Created by federicoberon on 08/07/2019.
 */
public interface PlayerService {

    Wrapper<Player> totalPlayers(String code);

}
