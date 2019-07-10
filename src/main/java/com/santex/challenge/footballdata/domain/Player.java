package com.santex.challenge.footballdata.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by federicoberon on 30/06/2019.
 */

@Entity
@Table(name = "PLAYERS")
public class Player {


    @Id
    @Column(name = "ID")
    @JsonIgnore
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "DATE_OF_BITH")
    private LocalDate dateOfBirth;

    @Column(name = "COUNTRY_OF_BIRTH")
    private String countryOfBirth;

    @Column(name = "NATIONALITY")
    private String nationality;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "PL_ID_TEAM", nullable = false)
    private Team team;

    public Player() {}

    public Player(Integer id, String name, String position, LocalDate dateOfBirth, String countryOfBirth, String nationality) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.countryOfBirth = countryOfBirth;
        this.nationality = nationality;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", countryOfBirth='" + countryOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
