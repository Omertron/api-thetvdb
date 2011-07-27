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
package com.moviejukebox.thetvdb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.moviejukebox.thetvdb.model.Actor;
import com.moviejukebox.thetvdb.model.Banners;
import com.moviejukebox.thetvdb.model.Episode;
import com.moviejukebox.thetvdb.model.Series;

/**
 * JUnit tests for TheTvDb class. The tester must enter the API key for these tests to work. 
 * Requires JUnit 4.5.
 * @author stuart.boston
 *
 */
public class TheTvDbTest {

    private static String apikey = "";
    private TheTVDB tvdb;
    private static final String LANGUAGE = "en";
    private static final String ID_CHUCK = "80348";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        tvdb = new TheTVDB(apikey);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetSeries() {
        Series series = tvdb.getSeries(ID_CHUCK, LANGUAGE);
        assertTrue(series.getSeriesName().equals("Chuck"));
    }

    @Test
    public void testGetAllEpisodes() {
        List<Episode> episodes = tvdb.getAllEpisodes(ID_CHUCK, LANGUAGE);
        assertFalse(episodes.isEmpty());
    }

    @Test
    public void testGetSeasonEpisodes() {
        List<Episode> episodes = tvdb.getSeasonEpisodes(ID_CHUCK, 1, LANGUAGE);
        assertFalse(episodes.isEmpty());
    }

    @Test
    public void testGetEpisode() {
        Episode episode = tvdb.getEpisode(ID_CHUCK, 1, 1, LANGUAGE);
        assertTrue(episode.getEpisodeName().length() > 0);
    }

    @Test
    public void testGetDVDEpisode() {
        Episode episode = tvdb.getDVDEpisode(ID_CHUCK, 1, 1, LANGUAGE);
        assertTrue(episode.getDvdEpisodeNumber().length() > 0);
    }

    @Test
    public void testGetAbsoluteEpisode() {
        Episode episode = tvdb.getAbsoluteEpisode(ID_CHUCK, 1, LANGUAGE);
        assertTrue(episode.getAbsoluteNumber().equals("1"));
    }

    @Test
    public void testGetSeasonYear() {
        String year = tvdb.getSeasonYear(ID_CHUCK, 1, LANGUAGE);
        assertTrue(year.equals("2007"));
    }

    @Test
    public void testGetBanners() {
        Banners banners = tvdb.getBanners(ID_CHUCK);
        assertFalse(banners.getFanartList().isEmpty());
        assertFalse(banners.getPosterList().isEmpty());
        assertFalse(banners.getSeasonList().isEmpty());
        assertFalse(banners.getSeriesList().isEmpty());
    }

    @Test
    public void testGetActors() {
        List<Actor> actors = tvdb.getActors(ID_CHUCK);
        assertFalse(actors.isEmpty());
    }

    @Test
    public void testSearchSeries() {
        List<Series> seriesList = tvdb.searchSeries("chuck", LANGUAGE);
        assertFalse(seriesList.isEmpty());
        
        boolean found = false;
        for (Series series : seriesList) {
            if (series.getId().equals(ID_CHUCK)) {
                found = true;
                break;
            }
        }
        assertTrue("Series found", found);
    }

    @Test
    public void testGetXmlMirror() throws Throwable {
        String mirror = TheTVDB.getXmlMirror();
        assertTrue(mirror.length() > 0);
    }

    @Test
    public void testGetBannerMirror() {
        String mirror = TheTVDB.getBannerMirror();
        assertTrue(mirror.length() > 0);
    }

}
