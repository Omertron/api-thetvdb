/*
 *      Copyright (c) 2004-2012 Matthew Altman & Stuart Boston
 *
 *      This file is part of TheTVDB API.
 *
 *      TheTVDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheTVDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheTVDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.thetvdbapi;

import com.omertron.thetvdbapi.model.Actor;
import com.omertron.thetvdbapi.model.Banners;
import com.omertron.thetvdbapi.model.Episode;
import com.omertron.thetvdbapi.model.Series;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for TheTvDb class. The tester must enter the API key for these
 * tests to work. Requires JUnit 4.5.
 *
 * @author stuart.boston
 *
 */
public class TheTvDbApiTest {

    private static final Logger logger = Logger.getLogger(TheTvDbApiTest.class);
    private static String apiKey = "2805AD2873519EC5";
    private TheTVDBApi tvdb;
    private static final String LANGUAGE = "en";
    private static final String TVDBID = "80348";
    private static final String SERIES_NAME = "Chuck";
    private static final String EPISODE_ID="1534661";
    private static final String SEASON_ID = "27984";
    private static final String SEASON_YEAR = "2007";
//    private static final String LANGUAGE = "he";
//    private static final String TVDBID = "82716";
//    private static final String SERIES_NAME = "90210";
//    private static final String EPISODE_ID = "380752";
//    private static final String SEASON_ID = "34304";
//    private static final String SEASON_YEAR = "2008";

    @Before
    public void setUp() throws Exception {
        tvdb = new TheTVDBApi(apiKey);
    }

    @Test
    public void testGetSeries() {
        logger.info("testGetSeries");
        Series series = tvdb.getSeries(TVDBID, LANGUAGE);
        assertTrue(series.getSeriesName().equals(SERIES_NAME));
    }

    @Test
    public void testGetAllEpisodes() {
        logger.info("testGetAllEpisodes");
        List<Episode> episodes = tvdb.getAllEpisodes(TVDBID, LANGUAGE);
        assertFalse(episodes.isEmpty());
    }

    @Test
    public void testGetSeasonEpisodes() {
        logger.info("testGetSeasonEpisodes");
        List<Episode> episodes = tvdb.getSeasonEpisodes(TVDBID, 1, LANGUAGE);
        assertFalse(episodes.isEmpty());
    }

    @Test
    public void testGetEpisode() {
        logger.info("testGetEpisode");
        Episode episode = tvdb.getEpisode(TVDBID, 1, 1, LANGUAGE);
        assertTrue(episode.getEpisodeName().length() > 0);
    }

    @Test
    public void testGetDVDEpisode() {
        logger.info("testGetDVDEpisode");
        Episode episode = tvdb.getDVDEpisode(TVDBID, 1, 1, LANGUAGE);
        assertTrue(episode.getDvdEpisodeNumber().length() > 0);
    }

    @Test
    public void testGetAbsoluteEpisode() {
        logger.info("testGetAbsoluteEpisode");
        Episode episode = tvdb.getAbsoluteEpisode(TVDBID, 1, LANGUAGE);
        assertTrue(episode.getAbsoluteNumber().equals("1"));
    }

    @Test
    public void testGetSeasonYear() {
        logger.info("testGetSeasonYear");
        String year = tvdb.getSeasonYear(TVDBID, 1, LANGUAGE);
        assertTrue(year.equals(SEASON_YEAR));
    }

    @Test
    public void testGetBanners() {
        logger.info("testGetBanners");
        Banners banners = tvdb.getBanners("72023");
        assertFalse(banners.getFanartList().isEmpty());
        assertFalse(banners.getPosterList().isEmpty());
        assertFalse(banners.getSeasonList().isEmpty());
        assertFalse(banners.getSeriesList().isEmpty());
    }

    @Test
    public void testGetActors() {
        logger.info("testGetActors");
        List<Actor> actors = tvdb.getActors(TVDBID);
        assertFalse(actors.isEmpty());
    }

    @Test
    public void testSearchSeries() {
        logger.info("testSearchSeries");
        List<Series> seriesList = tvdb.searchSeries(SERIES_NAME, LANGUAGE);
        assertFalse(seriesList.isEmpty());

        boolean found = false;
        for (Series series : seriesList) {
            if (series.getId().equals(TVDBID)) {
                found = true;
                break;
            }
        }
        assertTrue("Series not found", found);
    }

    @Test
    public void testGetXmlMirror() throws Throwable {
        logger.info("testGetXmlMirror");
        String mirror = TheTVDBApi.getXmlMirror(apiKey);
        assertTrue(mirror.length() > 0);
    }

    @Test
    public void testGetBannerMirror() {
        logger.info("testGetBannerMirror");
        String mirror = TheTVDBApi.getBannerMirror(apiKey);
        assertTrue(mirror.length() > 0);
    }

    /**
     * Test of getEpisodeById method, of class TheTVDBApi.
     */
    @Test
    public void testGetEpisodeById() {
        logger.info("getEpisodeById");
        Episode result = tvdb.getEpisodeById(EPISODE_ID, LANGUAGE);
        assertEquals(TVDBID, result.getSeriesId());
        assertEquals(SEASON_ID, result.getSeasonId());
    }
}
