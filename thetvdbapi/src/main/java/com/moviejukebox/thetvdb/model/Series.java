/*
 *      Copyright (c) 2004-2012 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list 
 *  
 *      Web: http://code.google.com/p/moviejukebox/
 *  
 *      This software is licensed under a Creative Commons License
 *      See this page: http://code.google.com/p/moviejukebox/wiki/License
 *  
 *      For any reuse or distribution, you must make clear to others the 
 *      license terms of this work.  
 */
package com.moviejukebox.thetvdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author altman.matthew
 */
@Entity
@Table(name = "SERIES")
public class Series implements Serializable {

    private String id;
    private String seriesId;
    private String language;
    private String seriesName;
    private String banner;
    private String overview;
    private String firstAired;
    private String imdbId;
    private String zap2ItId;
    private List<String> actors = new ArrayList<String>();
    private String airsDayOfWeek;
    private String airsTime;
    private String contentRating;
    private List<String> genres = new ArrayList<String>();
    private String network;
    private String rating;
    private String runtime;
    private String status;
    private String fanart;
    private String lastUpdated;
    private String poster;

    @Id
    public String getId() {
        return id;
    }

    @ElementCollection
    public List<String> getActors() {
        return actors;
    }

    public String getAirsDayOfWeek() {
        return airsDayOfWeek;
    }

    public String getAirsTime() {
        return airsTime;
    }

    public String getBanner() {
        return banner;
    }

    public String getContentRating() {
        return contentRating;
    }

    public String getFanart() {
        return fanart;
    }

    public String getFirstAired() {
        return firstAired;
    }

    @ElementCollection
    public List<String> getGenres() {
        return genres;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getLanguage() {
        return language;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getNetwork() {
        return network;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }

    public String getRating() {
        return rating;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public String getStatus() {
        return status;
    }

    public String getZap2ItId() {
        return zap2ItId;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public void setAirsDayOfWeek(String airsDayOfWeek) {
        this.airsDayOfWeek = airsDayOfWeek;
    }

    public void setAirsTime(String airsTime) {
        this.airsTime = airsTime;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setZap2ItId(String zap2ItId) {
        this.zap2ItId = zap2ItId;
    }

    @Override
    public String toString() {
        StringBuilder series = new StringBuilder("[Series ");

        series.append("[id=").append(id).append("]");
        series.append("[seriesId=").append(seriesId).append("]");
        series.append("[language=").append(language).append("]");
        series.append("[seriesName=").append(seriesName).append("]");
        series.append("[banner=").append(banner).append("]");
        series.append("[overview=").append(overview).append("]");
        series.append("[firstAired=").append(firstAired).append("]");
        series.append("[imdbId=").append(imdbId).append("]");
        series.append("[zap2ItId=").append(zap2ItId).append("]");
        series.append("[actors=").append(actors.toString()).append("]");
        series.append("[airsDayOfWeek=").append(airsDayOfWeek).append("]");
        series.append("[airsTime=").append(airsTime).append("]");
        series.append("[contentRating=").append(contentRating).append("]");
        series.append("[genres=").append(genres.toString()).append("]");
        series.append("[network=").append(network).append("]");
        series.append("[rating=").append(rating).append("]");
        series.append("[runtime=").append(runtime).append("]");
        series.append("[status=").append(status).append("]");
        series.append("[fanart=").append(fanart).append("]");
        series.append("[lastUpdated=").append(lastUpdated).append("]");
        series.append("[poster").append(poster).append("]");

        series.append("]");
        return series.toString();
    }
}
