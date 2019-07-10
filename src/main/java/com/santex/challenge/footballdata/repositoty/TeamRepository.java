package com.santex.challenge.footballdata.repositoty;

import com.santex.challenge.footballdata.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by federicoberon on 04/07/2019.
 */
public interface TeamRepository extends JpaRepository<Team, Integer> { }
