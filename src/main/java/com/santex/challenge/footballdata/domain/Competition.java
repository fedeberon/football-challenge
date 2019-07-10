package com.santex.challenge.footballdata.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by federicoberon on 30/06/2019.
 */

@Entity
@Table(name = "COMPETITIONS")
public class Competition {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @Column(name = "ID_COMPETITION")
    private Integer idCompetition;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name = "AREA_NAME")
    private String areaName;

    public Competition() {}

    public Competition(Integer idCompetition, String name, String area, String code) {
        this.idCompetition = idCompetition;
        this.name = name;
        this.areaName = area;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(Integer idCompetition) {
        this.idCompetition = idCompetition;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", idCompetition=" + idCompetition +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
