package com.santex.challenge.footballdata.repositoty;

import com.santex.challenge.footballdata.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by federicoberon on 04/07/2019.
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {



}
