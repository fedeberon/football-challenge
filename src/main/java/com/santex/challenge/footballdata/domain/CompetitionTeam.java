package com.santex.challenge.footballdata.domain;

import javax.persistence.*;

/**
 * Created by federicoberon on 04/07/2019.
 */
@Entity
@Table(name = "COMPETITIONS_TEAMS")
public class CompetitionTeam {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_COM")
    private Competition competition;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_TEA")
    private Team team;

    public CompetitionTeam() {}

    public CompetitionTeam(Team team, Competition competition) {
        this.team = team;
        this.competition = competition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
