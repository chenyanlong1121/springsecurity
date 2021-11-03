package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Mission)实体类
 *
 * @author makejava
 * @since 2021-09-08 13:28:33
 */
public class Mission implements Serializable {
    private static final long serialVersionUID = 110773791521516049L;

    private Integer id;

    private String missionName;

    private String director;

    private String oppositePerson;

    private String model;

    private String modelId;

    private String describtion;

    private String starttime;

    private String endtime;

    private String timechange;

    private String changreason;

    private String speedOfProgress;

    private String relatedDocumentLinks;

    private String acceptancePassed;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getOppositePerson() {
        return oppositePerson;
    }

    public void setOppositePerson(String oppositePerson) {
        this.oppositePerson = oppositePerson;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDescribe() {
        return describtion;
    }

    public void setDescribe(String describe) {
        this.describtion = describe;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTimechange() {
        return timechange;
    }

    public void setTimechange(String timechange) {
        this.timechange = timechange;
    }

    public String getChangreason() {
        return changreason;
    }

    public void setChangreason(String changreason) {
        this.changreason = changreason;
    }

    public String getSpeedOfProgress() {
        return speedOfProgress;
    }

    public void setSpeedOfProgress(String speedOfProgress) {
        this.speedOfProgress = speedOfProgress;
    }

    public String getRelatedDocumentLinks() {
        return relatedDocumentLinks;
    }

    public void setRelatedDocumentLinks(String relatedDocumentLinks) {
        this.relatedDocumentLinks = relatedDocumentLinks;
    }

    public String getAcceptancePassed() {
        return acceptancePassed;
    }

    public void setAcceptancePassed(String acceptancePassed) {
        this.acceptancePassed = acceptancePassed;
    }

}
