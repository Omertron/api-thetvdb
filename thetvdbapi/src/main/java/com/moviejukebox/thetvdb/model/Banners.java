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
import javax.persistence.*;

/**
 *
 * @author matthew.altman
 */
@Entity
@Table(name = "BANNERS")
public class Banners implements Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID
    private int seriesId = 0;
    private List<Banner> seriesList = new ArrayList<Banner>();
    private List<Banner> seasonList = new ArrayList<Banner>();
    private List<Banner> posterList = new ArrayList<Banner>();
    private List<Banner> fanartList = new ArrayList<Banner>();

    @Id
    @Column(name = "SERIES_ID")
    public int getSeriesId() {
        return seriesId;
    }

    @ElementCollection
    @OneToMany
    public List<Banner> getSeriesList() {
        return seriesList;
    }

    @ElementCollection
    @OneToMany
    public List<Banner> getSeasonList() {
        return seasonList;
    }

    @ElementCollection
    @OneToMany
    public List<Banner> getPosterList() {
        return posterList;
    }

    @ElementCollection
    @OneToMany
    public List<Banner> getFanartList() {
        return fanartList;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public void setSeriesList(List<Banner> seriesList) {
        this.seriesList = seriesList;
    }

    public void addSeriesBanner(Banner banner) {
        this.seriesList.add(banner);
    }

    public void setSeasonList(List<Banner> seasonList) {
        this.seasonList = seasonList;
    }

    public void addSeasonBanner(Banner banner) {
        this.seasonList.add(banner);
    }

    public void setPosterList(List<Banner> posterList) {
        this.posterList = posterList;
    }

    public void addPosterBanner(Banner banner) {
        this.posterList.add(banner);
    }

    public void setFanartList(List<Banner> fanartList) {
        this.fanartList = fanartList;
    }

    public void addFanartBanner(Banner banner) {
        this.fanartList.add(banner);
    }

    public void addBanner(Banner banner) {
        if (banner != null) {
            if (seriesId == 0) {
                seriesId = banner.getId();
            }

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
        StringBuilder banners = new StringBuilder("[Banners ");

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
