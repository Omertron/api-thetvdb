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

/**
 *
 * @author altman.matthew
 */
public class Banner {

    private int             id;
    private String          url;
    private BannerListType  bannerType;
    private BannerType      bannerType2;
    private String          colours;
    private String          language;
    private boolean         seriesName;
    private String          thumb;
    private String          vignette;
    private int             season = 0;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVignette() {
        return vignette;
    }

    public void setVignette(String vignette) {
        this.vignette = vignette;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSeason() {
        return season;
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

    public BannerListType getBannerType() {
        return bannerType;
    }

    public void setBannerType(BannerListType bannerType) {
        this.bannerType = bannerType;
    }

    public BannerType getBannerType2() {
        return bannerType2;
    }

    public void setBannerType2(BannerType bannerType2) {
        this.bannerType2 = bannerType2;
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

    public int getId() {
        return id;
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

    public String getColours() {
        return colours;
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public boolean isSeriesName() {
        return seriesName;
    }

    public void setSeriesName(boolean seriesName) {
        this.seriesName = seriesName;
    }
}
