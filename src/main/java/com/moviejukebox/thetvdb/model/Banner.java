/*
 *      Copyright (c) 2004-2012 Matthew Altman & Stuart Boston
 *
 *      This software is licensed under a Creative Commons License
 *      See the LICENCE.txt file included in this package
 *
 *      For any reuse or distribution, you must make clear to others the
 *      license terms of this work.
 */
package com.moviejukebox.thetvdb.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author altman.matthew
 */
@Entity
@Table(name = "BANNER")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID
    private int id;
    private String url;
    private BannerListType bannerType;
    private BannerType bannerType2;
    private String colours;
    private String language;
    private boolean seriesName;
    private String thumb;
    private String vignette;
    private int season = 0;

    @Id
    @Column(name = "BANNER_ID")
    public int getId() {
        return id;
    }

    @Column(name = "BANNER_TYPE")
    @Enumerated(EnumType.STRING)
    public BannerListType getBannerType() {
        return bannerType;
    }

    @Column(name = "BANNER_TYPE2")
    @Enumerated(EnumType.STRING)
    public BannerType getBannerType2() {
        return bannerType2;
    }

    @Column(name = "COLORS")
    public String getColours() {
        return colours;
    }

    @Column(name = "BANNER_LANGUAGE")
    public String getLanguage() {
        return language;
    }

    @Column(name = "SEASON")
    public int getSeason() {
        return season;
    }

    @Column(name = "IS_SERIES_NAME")
    public boolean isSeriesName() {
        return seriesName;
    }

    @Column(name = "THUMB")
    public String getThumb() {
        return thumb;
    }

    @Column(name = "BANNER_URL")
    public String getUrl() {
        return url;
    }

    @Column(name = "VIGNETTE")
    public String getVignette() {
        return vignette;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVignette(String vignette) {
        this.vignette = vignette;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setSeason(String season) {
        try {
            this.season = Integer.parseInt(season);
        } catch (Exception error) {
            this.season = 0;
        }
    }

    public void setBannerType(BannerListType bannerType) {
        this.bannerType = bannerType;
    }

    public void setBannerType2(BannerType bannerType2) {
        this.bannerType2 = bannerType2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            this.id = Integer.parseInt(id);
        } catch (Exception ignore) {
            this.id = 0;
        }
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public void setSeriesName(boolean seriesName) {
        this.seriesName = seriesName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Banner=[id=");
        builder.append(id);
        builder.append("], [url=");
        builder.append(url);
        builder.append("], [bannerType=");
        builder.append(bannerType);
        builder.append("], [bannerType2=");
        builder.append(bannerType2);
        builder.append("], [colours=");
        builder.append(colours);
        builder.append("], [language=");
        builder.append(language);
        builder.append("], [seriesName=");
        builder.append(seriesName);
        builder.append("], [thumb=");
        builder.append(thumb);
        builder.append("], [vignette=");
        builder.append(vignette);
        builder.append("], [season=");
        builder.append(season);
        builder.append("]]");
        return builder.toString();
    }
}
