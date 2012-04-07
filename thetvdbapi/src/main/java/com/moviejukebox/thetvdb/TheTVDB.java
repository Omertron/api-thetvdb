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
package com.moviejukebox.thetvdb;

import com.moviejukebox.thetvdb.model.*;
import com.moviejukebox.thetvdb.tools.FilteringLayout;
import com.moviejukebox.thetvdb.tools.TvdbParser;
import com.moviejukebox.thetvdb.tools.WebBrowser;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.ws.WebServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author altman.matthew
 * @author stuart.boston
 */
public class TheTVDB {

    private static String apiKey = null;
    private static String xmlMirror = null;
    private static String bannerMirror = null;
    private static final Logger LOGGER = Logger.getLogger(TheTVDB.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String XML_EXTENSION = ".xml";
    private static final String SERIES_URL = "/series/";
    private static final String ALL_URL = "/all/";

    public TheTVDB(String apiKey) {
        if (apiKey == null) {
            return;
        }

        TheTVDB.apiKey = apiKey;
        FilteringLayout.addApiKey(apiKey);
    }

    /**
     * Output the API version information to the debug log
     */
    public static void showVersion() {
        String apiTitle = TheTVDB.class.getPackage().getSpecificationTitle();

        if (StringUtils.isNotBlank(apiTitle)) {
            String apiVersion = TheTVDB.class.getPackage().getSpecificationVersion();
            String apiRevision = TheTVDB.class.getPackage().getImplementationVersion();
            StringBuilder sv = new StringBuilder();
            sv.append(apiTitle).append(" ");
            sv.append(apiVersion).append(" r");
            sv.append(apiRevision);
            LOGGER.debug(sv.toString());
        } else {
            LOGGER.debug("API-TheTVDB version/revision information not available");
        }
    }

    /**
     * Return the logger information
     *
     * @return
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Get the mirror information from TheTVDb
     *
     * @return True if everything is OK, false otherwise.
     */
    private static void getMirrors() throws WebServiceException {
        // If we don't need to get the mirrors, then just return
        if (xmlMirror != null && bannerMirror != null) {
            return;
        }

        Mirrors mirrors = new Mirrors(apiKey);
        xmlMirror = mirrors.getMirror(Mirrors.TYPE_XML);
        bannerMirror = mirrors.getMirror(Mirrors.TYPE_BANNER);

        if (xmlMirror == null) {
            throw new WebServiceException("There is a problem getting the xmlMirror data from TheTVDB, this means it is likely to be down.");
        } else {
            xmlMirror += "/api/";
        }

        if (bannerMirror == null) {
            throw new WebServiceException("There is a problem getting the bannerMirror data from TheTVDB, this means it is likely to be down.");
        } else {
            bannerMirror += "/banners/";
        }

        //zipMirror = mirrors.getMirror(Mirrors.TYPE_ZIP);
    }

    /**
     * Set the web browser proxy information
     *
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
     *
     * @param webTimeoutConnect
     * @param webTimeoutRead
     */
    public void setTimeout(int webTimeoutConnect, int webTimeoutRead) {
        WebBrowser.setWebTimeoutConnect(webTimeoutConnect);
        WebBrowser.setWebTimeoutRead(webTimeoutRead);
    }

    /**
     * Get the series information
     *
     * @param id
     * @param language
     * @return
     */
    public Series getSeries(String id, String language) {
        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(id);
            urlString.append("/");
            if (language != null) {
                urlString.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return null;
        }

        List<Series> seriesList = TvdbParser.getSeriesList(urlString.toString());
        if (seriesList.isEmpty()) {
            return null;
        } else {
            return seriesList.get(0);
        }
    }

    /**
     * Get all the episodes for a series. Note: This could be a lot of records
     *
     * @param id
     * @param language
     * @return
     */
    public List<Episode> getAllEpisodes(String id, String language) {
        if (!isValidNumber(id)) {
            return new ArrayList<Episode>();
        }

        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(id);
            urlString.append(ALL_URL);
            if (language != null) {
                urlString.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return null;
        }

        List<Episode> episodeList = TvdbParser.getAllEpisodes(urlString.toString(), -1);
        if (episodeList.isEmpty()) {
            return null;
        } else {
            return episodeList;
        }
    }

    /**
     * Get all the episodes from a specific season for a series. Note: This
     * could be a lot of records
     *
     * @param id
     * @param season
     * @param language
     * @return
     */
    public List<Episode> getSeasonEpisodes(String id, int season, String language) {
        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(id);
            urlString.append(ALL_URL);
            if (language != null) {
                urlString.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return null;
        }

        List<Episode> episodeList = TvdbParser.getAllEpisodes(urlString.toString(), season);
        if (episodeList.isEmpty()) {
            return null;
        } else {
            return episodeList;
        }
    }

    /**
     * Get a specific episode's information
     *
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getEpisode(String seriesId, int seasonNbr, int episodeNbr, String language) {
        if (!isValidNumber(seriesId) || !isValidNumber(seasonNbr) || !isValidNumber(episodeNbr)) {
            // Invalid number passed
            return new Episode();
        }

        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(seriesId);
            urlString.append("/default/");
            urlString.append(seasonNbr);
            urlString.append("/");
            urlString.append(episodeNbr);
            urlString.append("/");
            if (language != null) {
                urlString.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlString.toString());
    }

    /**
     * Get a specific DVD episode's information
     *
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getDVDEpisode(String seriesId, int seasonNbr, int episodeNbr, String language) {
        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(seriesId);
            urlString.append("/dvd/");
            urlString.append(seasonNbr);
            urlString.append("/");
            urlString.append(episodeNbr);
            urlString.append("/");
            if (language != null) {
                urlString.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlString.toString());
    }

    /**
     * Get a specific absolute episode's information
     *
     * @param seriesId
     * @param seasonNbr
     * @param episodeNbr
     * @param language
     * @return
     */
    public Episode getAbsoluteEpisode(String seriesId, int episodeNbr, String language) {
        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(seriesId);
            urlString.append("/absolute/");
            urlString.append(episodeNbr);
            urlString.append("/");
            if (language != null) {
                urlString.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlString.toString());
    }

    /**
     * Get a list of banners for the series id
     *
     * @param id
     * @return
     */
    public String getSeasonYear(String id, int seasonNbr, String language) {
        String year = null;

        Episode episode = getEpisode(id, seasonNbr, 1, language);
        if ((episode != null) && ((episode.getFirstAired() != null && !episode.getFirstAired().isEmpty()))) {
            Date date;

            try {
                date = DATE_FORMAT.parse(episode.getFirstAired());
            } catch (ParseException error) {
                date = null;
            }

            if (date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                year = "" + cal.get(Calendar.YEAR);
            }
        }

        return year;
    }

    public Banners getBanners(String seriesId) {
        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(seriesId);
            urlString.append("/banners.xml");
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new Banners();
        }

        return TvdbParser.getBanners(urlString.toString());
    }

    /**
     * Get a list of actors from the series id
     *
     * @param SeriesId
     * @return
     */
    public List<Actor> getActors(String seriesId) {
        StringBuilder urlString = new StringBuilder();
        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append(SERIES_URL);
            urlString.append(seriesId);
            urlString.append("/actors.xml");
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new ArrayList<Actor>();
        }
        return TvdbParser.getActors(urlString.toString());
    }

    public List<Series> searchSeries(String title, String language) {
        StringBuilder urlString = new StringBuilder();

        try {
            urlString.append(getXmlMirror());
            urlString.append("GetSeries.php?seriesname=");
            urlString.append(URLEncoder.encode(title, "UTF-8"));
            if (language != null) {
                urlString.append("&language=").append(language);
            }
        } catch (UnsupportedEncodingException e) {
            // Try and use the raw title
            urlString.append(title);
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new ArrayList<Series>();
        }

        return TvdbParser.getSeriesList(urlString.toString());
    }

    /**
     * Get information for a specific episode
     *
     * @param episodeId
     * @param language
     * @return
     */
    public Episode getEpisodeById(String episodeId, String language) {
        StringBuilder urlString = new StringBuilder();

        try {
            urlString.append(getXmlMirror());
            urlString.append(apiKey);
            urlString.append("/episodes/");
            urlString.append(episodeId);
            urlString.append("/");
            if (StringUtils.isNotBlank(language)) {
                urlString.append(language);
                urlString.append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            LOGGER.warn(ex.getMessage());
            return new Episode();
        }
        LOGGER.debug("URL: " + urlString.toString());
        return TvdbParser.getEpisode(urlString.toString());
    }

    /**
     * Get the XML Mirror URL
     *
     * @return
     */
    public static String getXmlMirror() {
        // Force a load of the mirror information if it doesn't exist
        getMirrors();
        return xmlMirror;
    }

    /**
     * Get the Banner Mirror URL
     *
     * @return
     */
    public static String getBannerMirror() {
        // Force a load of the mirror information if it doesn't exist
        getMirrors();
        return bannerMirror;
    }

    private boolean isValidNumber(String number) {
        return StringUtils.isNumeric(number);
    }

    private boolean isValidNumber(int number) {
        return (number >= 0 ? true : false);
    }
}