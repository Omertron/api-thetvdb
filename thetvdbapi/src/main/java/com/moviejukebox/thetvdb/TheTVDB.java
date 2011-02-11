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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moviejukebox.thetvdb.model.Actor;
import com.moviejukebox.thetvdb.model.Banners;
import com.moviejukebox.thetvdb.model.Episode;
import com.moviejukebox.thetvdb.model.Mirrors;
import com.moviejukebox.thetvdb.model.Series;
import com.moviejukebox.thetvdb.tools.LogFormatter;
import com.moviejukebox.thetvdb.tools.TvdbParser;
import com.moviejukebox.thetvdb.tools.WebBrowser;

/**
 * @author altman.matthew
 * @author stuart.boston
 */
public class TheTVDB {
    private static String apiKey = null;
    private static String xmlMirror = null;
    private static String bannerMirror = null;
    
    private static final String LOGGERNAME = "TheTVDB";
    private static Logger logger = null;
    private static LogFormatter logFormatter = new LogFormatter();
    private static ConsoleHandler logConsoleHandler = new ConsoleHandler();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private static final String XML_EXTENSION = ".xml";
    private static final String SERIES_URL = "/series/";
    
    public TheTVDB(String apiKey) {
        if (apiKey == null) {
            return;
        }
        
        setLogger(LOGGERNAME);
        setApiKey(apiKey);
        
        // Mirror information is called for when the get??Mirror calls are used
    }

    /**
     * Set the API Key
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        TheTVDB.apiKey = apiKey;
        logFormatter.addApiKey(apiKey);
    }

    /**
     * Return the logger information
     * @return
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Set the logger name
     * @param loggerName
     * @return 
     */
    public void setLogger(String loggerName) {
        // Check to see if we need to initalise the logger or not
        if (logger != null) {
            return;
        }
        
        logger = Logger.getLogger(loggerName);
        logConsoleHandler.setFormatter(logFormatter);
        logConsoleHandler.setLevel(Level.FINE);
        logger.addHandler(logConsoleHandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        
        return;
    }

    /**
     * Get the mirror information from TheTVDb
     * @return True if everything is OK, false otherwise.
     */
    private static void getMirrors() throws Throwable {
        // If we don't need to get the mirrors, then just return
        if (xmlMirror != null && bannerMirror != null) {
            return;
        }
        
        Mirrors mirrors = new Mirrors(apiKey);
        xmlMirror = mirrors.getMirror(Mirrors.TYPE_XML);
        bannerMirror = mirrors.getMirror(Mirrors.TYPE_BANNER);
        
        if (xmlMirror == null) {
            throw new RuntimeException("There is a problem getting the xmlMirror data from TheTVDB, this means it is likely to be down.");
        } else {
            xmlMirror += "/api/";
        }
        
        if (bannerMirror == null) {
            throw new RuntimeException("There is a problem getting the bannerMirror data from TheTVDB, this means it is likely to be down.");
        } else {
            bannerMirror += "/banners/";
        }
        
        //zipMirror = mirrors.getMirror(Mirrors.TYPE_ZIP);
        
        return;
    }
    
    /**
     * Set the web browser proxy information
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public void setProxy(String host, String port, String username, String password) {
        WebBrowser.setProxyHost(host);
        WebBrowser.setProxyPort(port);
        WebBrowser.setProxyUsername(username);
        WebBrowser.setProxyPassword(password);
    }
    
    /**
     * Set the web browser timeout settings
     * @param webTimeoutConnect
     * @param webTimeoutRead
     */
    public void setTimeout(int webTimeoutConnect, int webTimeoutRead) {
        WebBrowser.setWebTimeoutConnect(webTimeoutConnect);
        WebBrowser.setWebTimeoutRead(webTimeoutRead);
    }
    
    /**
     * Get the series information
     * @param id
     * @param language
     * @return
     */
    public Series getSeries(String id, String language) {
        String urlString = null;
        try {
            urlString = getXmlMirror() + apiKey + SERIES_URL + id + "/" + (language!=null?language+XML_EXTENSION:"");
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return null;
        }
        
        List<Series> seriesList = TvdbParser.getSeriesList(urlString);
        if (seriesList.isEmpty()) {
            return null;
        } else {
            return seriesList.get(0);
        }
    }
    
    /**
     * Get a specific episode's information
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getEpisode(String seriesId, int seasonNbr, int episodeNbr, String language) {
        String urlString = null;
        try {
            urlString = getXmlMirror() + apiKey + SERIES_URL + seriesId + "/default/" + seasonNbr + "/" + episodeNbr + "/" + (language!=null?language+XML_EXTENSION:"");
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlString);
    }

    /**
     * Get a specific DVD episode's information
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getDVDEpisode(String seriesId, int seasonNbr, int episodeNbr, String language) {
        String urlString = null;
        try {
            urlString = getXmlMirror() + apiKey + SERIES_URL + seriesId + "/dvd/" + seasonNbr + "/" + episodeNbr + "/" + (language!=null?language+XML_EXTENSION:"");
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlString);
    }
    
    /**
     * Get a specific absolute episode's information
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getAbsoluteEpisode(String seriesId, int episodeNbr, String language) {
        String urlString = null;
        try {
            urlString = getXmlMirror() + apiKey + SERIES_URL + seriesId + "/absolute/" + episodeNbr + "/" + (language!=null?language+XML_EXTENSION:"");
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlString);
    }

    /**
     * Get a list of banners for the series id 
     * @param id
     * @return
     */
    public String getSeasonYear(String id, int seasonNbr, String language) {
        String year = null;

        Episode episode = getEpisode(id, seasonNbr, 1, language);
        if (episode != null) {
            if (episode.getFirstAired() != null && !episode.getFirstAired().isEmpty()) {
                try {
                    Date date = dateFormat.parse(episode.getFirstAired());
                    if (date != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        year = "" + cal.get(Calendar.YEAR);
                    }
                } catch (Exception ignore) {
                    return "";
                }
            }
        }

        return year;
    }

    public Banners getBanners(String seriesId) {
        String urlString = null;
        try {
            urlString = getXmlMirror() + apiKey + SERIES_URL + seriesId + "/banners.xml";
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new Banners();
        }

        return TvdbParser.getBanners(urlString);
    }
    
    /**
     * Get a list of actors from the series id
     * @param SeriesId
     * @return
     */
    public List<Actor> getActors(String seriesId) {
        String urlString = null;
        try {
            urlString = getXmlMirror() + apiKey + SERIES_URL + seriesId + "/actors.xml";
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new ArrayList<Actor>();
        }
        return TvdbParser.getActors(urlString);
    }
    
    public List<Series> searchSeries(String title, String language) {
        StringBuffer urlString = new StringBuffer();
        String mirror = "";
        
        try {
            mirror = getXmlMirror();
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new ArrayList<Series>();
        }

        urlString.append(mirror + "GetSeries.php?seriesname=");

        try {
            urlString.append(URLEncoder.encode(title, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // Try and use the raw title
            urlString.append(title);
        } catch (Throwable tw) {
            logger.severe(tw.getMessage());
            return new ArrayList<Series>();
        }
        
        urlString.append(language!=null?"&language="+language:"");
        return TvdbParser.getSeriesList(urlString.toString());
    }
    
    /**
     * Get the XML Mirror URL
     * @return
     * @throws Throwable 
     */
    public static String getXmlMirror() throws Throwable {
        // Force a load of the mirror information if it doesn't exist
        getMirrors();
        return xmlMirror;
    }
    
    /**
     * Get the Banner Mirror URL
     * @return
     * @throws Throwable 
     */
    public static String getBannerMirror() throws Throwable {
        // Force a load of the mirror information if it doesn't exist
        getMirrors();
        return bannerMirror;
    }
    
}
