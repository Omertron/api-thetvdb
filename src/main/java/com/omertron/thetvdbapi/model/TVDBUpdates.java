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
import java.util.List;

public class TVDBUpdates implements Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID

    private String time;
    private List<SeriesUpdate> seriesUpdates;
    private List<EpisodeUpdate> episodeUpdates;
    private List<BannerUpdate> bannerUpdates;

    public String getTime() {
        return time;
    }

    public List<SeriesUpdate> getSeriesUpdates() {
        return seriesUpdates;
    }

    public List<EpisodeUpdate> getEpisodeUpdates() {
        return episodeUpdates;
    }

    public List<BannerUpdate> getBannerUpdates() {
        return bannerUpdates;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeriesUpdates(List<SeriesUpdate> seriesUpdates) {
        this.seriesUpdates = seriesUpdates;
    }

    public void setEpisodeUpdates(List<EpisodeUpdate> episodeUpdates) {
        this.episodeUpdates = episodeUpdates;
    }

    public void setBannerUpdates(List<BannerUpdate> bannerUpdates) {
        this.bannerUpdates = bannerUpdates;
    }

}