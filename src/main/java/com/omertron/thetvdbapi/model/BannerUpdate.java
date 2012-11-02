/*
 *      Copyright (c) 2004-2012 Matthew Altman & Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.omertron.thetvdbapi.model;

import java.io.Serializable;

public class BannerUpdate implements Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID

    private String seasonNum;
    private String series;
    private String format;
    private String language;
    private String path;
    private String time;
    private String type;

    public String getSeasonNum() {
        return seasonNum;
    }

    public String getSeries() {
        return series;
    }

    public String getFormat() {
        return format;
    }

    public String getLanguage() {
        return language;
    }

    public String getPath() {
        return path;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public void setSeasonNum(String seasonNum) {
        this.seasonNum = seasonNum;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

}