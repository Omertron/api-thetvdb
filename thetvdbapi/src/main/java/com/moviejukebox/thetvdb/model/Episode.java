/*
 *      Copyright (c) 2004-2011 YAMJ Members
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matthew.altman
 */
public class Episode {

    private String id;
    private String combinedEpisodeNumber;
    private String combinedSeason;
    private String dvdChapter;
    private String dvdDiscId;
    private String dvdEpisodeNumber;
    private String dvdSeason;
    private List<String> directors = new ArrayList<String>();
    private String epImgFlag;
    private String episodeName;
    private int episodeNumber;
    private String firstAired;
    private List<String> guestStars = new ArrayList<String>();
    private String imdbId;
    private String language;
    private String overview;
    private String productionCode;
    private String rating;
    private int seasonNumber;
    private List<String> writers = new ArrayList<String>();
    private String absoluteNumber;
    private int airsAfterSeason;
    private int airsBeforeSeason;
    private int airsBeforeEpisode;
    private String filename;
    private String lastUpdated;
    private String seriesId;
    private String seasonId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombinedEpisodeNumber() {
        return combinedEpisodeNumber;
    }

    public void setCombinedEpisodeNumber(String combinedEpisodeNumber) {
        this.combinedEpisodeNumber = combinedEpisodeNumber;
    }

    public String getCombinedSeason() {
        return combinedSeason;
    }

    public void setCombinedSeason(String combinedSeason) {
        this.combinedSeason = combinedSeason;
    }

    public String getDvdChapter() {
        return dvdChapter;
    }

    public void setDvdChapter(String dvdChapter) {
        this.dvdChapter = dvdChapter;
    }

    public String getDvdDiscId() {
        return dvdDiscId;
    }

    public void setDvdDiscId(String dvdDiscId) {
        this.dvdDiscId = dvdDiscId;
    }

    public String getDvdEpisodeNumber() {
        return dvdEpisodeNumber;
    }

    public void setDvdEpisodeNumber(String dvdEpisodeNumber) {
        this.dvdEpisodeNumber = dvdEpisodeNumber;
    }

    public String getDvdSeason() {
        return dvdSeason;
    }

    public void setDvdSeason(String dvdSeason) {
        this.dvdSeason = dvdSeason;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }
    
    public void addDirector(String director) {
        this.directors.add(director);
    }

    public String getEpImgFlag() {
        return epImgFlag;
    }

    public void setEpImgFlag(String epImgFlag) {
        this.epImgFlag = epImgFlag;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public List<String> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<String> guestStars) {
        this.guestStars = guestStars;
    }

    public void addGuestStar(String guestStar) {
        this.guestStars.add(guestStar);
    }
    
    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public void addWriter(String writer) {
        this.writers.add(writer);
    }
    
    public String getAbsoluteNumber() {
        return absoluteNumber;
    }

    public void setAbsoluteNumber(String absoluteNumber) {
        this.absoluteNumber = absoluteNumber;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public int getAirsAfterSeason() {
        return airsAfterSeason;
    }

    public int getAirsBeforeSeason() {
        return airsBeforeSeason;
    }

    public int getAirsBeforeEpisode() {
        return airsBeforeEpisode;
    }

    public void setAirsAfterSeason(int airsAfterSeason) {
        this.airsAfterSeason = airsAfterSeason;
    }

    public void setAirsBeforeSeason(int airsBeforeSeason) {
        this.airsBeforeSeason = airsBeforeSeason;
    }

    public void setAirsBeforeEpisode(int airsBeforeEpisode) {
        this.airsBeforeEpisode = airsBeforeEpisode;
    }
    
    @Override
    public String toString() {
        StringBuffer sbEpisode = new StringBuffer("[Episode ");
        
        sbEpisode.append("[id=").append(id).append("]");
        sbEpisode.append("[combinedEpisodeNumber=").append(combinedEpisodeNumber).append("]");
        sbEpisode.append("[combinedSeason=").append(combinedSeason).append("]");
        sbEpisode.append("[dvdChapter=").append(dvdChapter).append("]");
        sbEpisode.append("[dvdChapter=").append(dvdDiscId).append("]");
        sbEpisode.append("[dvdEpisodeNumber=").append(dvdEpisodeNumber).append("]");
        sbEpisode.append("[dvdSeason=").append(dvdSeason).append("]");
        sbEpisode.append("[directors=").append(directors.toString()).append("]");
        sbEpisode.append("[epImgFlag=").append(epImgFlag).append("]");
        sbEpisode.append("[episodeName=").append(episodeName).append("]");
        sbEpisode.append("[episodeNumber=").append(episodeNumber).append("]");
        sbEpisode.append("[firstAired=").append(firstAired).append("]");
        sbEpisode.append("[guestStars=").append(guestStars.toString()).append("]");
        sbEpisode.append("[imdbId=").append(imdbId).append("]");
        sbEpisode.append("[language=").append(language).append("]");
        sbEpisode.append("[overview=").append(overview).append("]");
        sbEpisode.append("[productionCode=").append(productionCode).append("]");
        sbEpisode.append("[rating=").append(rating).append("]");
        sbEpisode.append("[seasonNumber=").append(seasonNumber).append("]");
        sbEpisode.append("[writers=").append(writers.toString()).append("]");
        sbEpisode.append("[absoluteNumber=").append(absoluteNumber).append("]");
        sbEpisode.append("[airsAfterSeason=").append(airsAfterSeason).append("]");
        sbEpisode.append("[airsBeforeSeason=").append(airsBeforeSeason).append("]");
        sbEpisode.append("[airsBeforeEpisode=").append(airsBeforeEpisode).append("]");
        sbEpisode.append("[filename=").append(filename).append("]");
        sbEpisode.append("[lastUpdated=").append(lastUpdated).append("]");
        sbEpisode.append("[seriesId=").append(seriesId).append("]");
        sbEpisode.append("[seasonId=").append(seasonId).append("]");
        sbEpisode.append("]");
        
        return sbEpisode.toString();
    }
}
