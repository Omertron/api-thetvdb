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
public class Banners {

    private List<Banner> seriesList = new ArrayList<Banner>();
    private List<Banner> seasonList = new ArrayList<Banner>();
    private List<Banner> posterList = new ArrayList<Banner>();
    private List<Banner> fanartList = new ArrayList<Banner>();

    public List<Banner> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Banner> seriesList) {
        this.seriesList = seriesList;
    }
    
    public void addSeriesBanner(Banner banner) {
        this.seriesList.add(banner);
    }

    public List<Banner> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Banner> seasonList) {
        this.seasonList = seasonList;
    }
    
    public void addSeasonBanner(Banner banner) {
        this.seasonList.add(banner);
    }

    public List<Banner> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<Banner> posterList) {
        this.posterList = posterList;
    }
    
    public void addPosterBanner(Banner banner) {
        this.posterList.add(banner);
    }

    public List<Banner> getFanartList() {
        return fanartList;
    }

    public void setFanartList(List<Banner> fanartList) {
        this.fanartList = fanartList;
    }
    
    public void addFanartBanner(Banner banner) {
        this.fanartList.add(banner);
    }
    
    public void addBanner(Banner banner) {
        if (banner != null) {
            if (banner.getBannerType() == BannerListType.series) {
                addSeriesBanner(banner);
            } else if (banner.getBannerType() == BannerListType.season) {
                addSeasonBanner(banner);
            } else if (banner.getBannerType() == BannerListType.poster) {
                addPosterBanner(banner);
            } else if (banner.getBannerType() == BannerListType.fanart) {
                addFanartBanner(banner);
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuffer banners = new StringBuffer("[Banners ");
        
        banners.append("[seriesList=");
        for (Banner banner : seriesList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("[seasonList=");
        for (Banner banner : seasonList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("[posterList=");
        for (Banner banner : posterList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("[fanartList=");
        for (Banner banner : fanartList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("]");
        return banners.toString();
    }
    
}
