package com.santex.challenge.footballdata.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by federicoberon on 30/06/2019.
 */

@Entity
@Table(name = "TEAMS")
public class Team {

    @Id
    @Column(name = "ID")
    @JsonIgnore
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TLA")
    private String tla;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "EMAIL")
    private String email;

    public Team() {}

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
    private List<Player> players;

    public Team(Integer id, String name) {
        this.id = id;
        this.tla = name;
    }

    public Team(Integer id, String name, String tla, String shortName, String areaName, String email) {
        this.id = id;
        this.name = name;
        this.tla = tla;
        this.shortName = shortName;
        this.areaName = areaName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return id != null ? id.equals(team.id) : team.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", tla='" + tla + '\'' +
                ", shortName='" + shortName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
