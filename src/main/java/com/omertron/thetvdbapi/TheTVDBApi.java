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
import com.omertron.thetvdbapi.model.Mirrors;
import com.omertron.thetvdbapi.model.Series;
import com.omertron.thetvdbapi.model.TVDBUpdates;
import com.omertron.thetvdbapi.tools.FilteringLayout;
import com.omertron.thetvdbapi.tools.TvdbParser;
import com.omertron.thetvdbapi.tools.WebBrowser;
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
public class TheTVDBApi {

    private static final Logger logger = Logger.getLogger(TheTVDBApi.class);
    private String apiKey = null;
    private static String xmlMirror = null;
    private static String bannerMirror = null;
    private static final String XML_EXTENSION = ".xml";
    private static final String SERIES_URL = "/series/";
    private static final String ALL_URL = "/all/";
    private static final String WEEKLY_UPDATES_URL = "/updates/updates_week.xml";

    public TheTVDBApi(String apiKey) {
        if (apiKey == null) {
            return;
        }

        this.apiKey = apiKey;
        FilteringLayout.addApiKey(apiKey);
    }

    /**
     * Get the mirror information from TheTVDb
     *
     * @return True if everything is OK, false otherwise.
     */
    private static void getMirrors(String apiKey) throws WebServiceException {
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
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(id);
            urlBuilder.append("/");
            if (language != null) {
                urlBuilder.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return null;
        }

        List<Series> seriesList = TvdbParser.getSeriesList(urlBuilder.toString(), getBannerMirror(apiKey));
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

        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(id);
            urlBuilder.append(ALL_URL);
            if (language != null) {
                urlBuilder.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return null;
        }

        List<Episode> episodeList = TvdbParser.getAllEpisodes(urlBuilder.toString(), -1, getBannerMirror(apiKey));
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
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(id);
            urlBuilder.append(ALL_URL);
            if (language != null) {
                urlBuilder.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return null;
        }

        List<Episode> episodeList = TvdbParser.getAllEpisodes(urlBuilder.toString(), season, getBannerMirror(apiKey));
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

        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(seriesId);
            urlBuilder.append("/default/");
            urlBuilder.append(seasonNbr);
            urlBuilder.append("/");
            urlBuilder.append(episodeNbr);
            urlBuilder.append("/");
            if (language != null) {
                urlBuilder.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlBuilder.toString(), getBannerMirror(apiKey));
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
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(seriesId);
            urlBuilder.append("/dvd/");
            urlBuilder.append(seasonNbr);
            urlBuilder.append("/");
            urlBuilder.append(episodeNbr);
            urlBuilder.append("/");
            if (language != null) {
                urlBuilder.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlBuilder.toString(), getBannerMirror(apiKey));
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
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(seriesId);
            urlBuilder.append("/absolute/");
            urlBuilder.append(episodeNbr);
            urlBuilder.append("/");
            if (language != null) {
                urlBuilder.append(language).append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new Episode();
        }

        return TvdbParser.getEpisode(urlBuilder.toString(), getBannerMirror(apiKey));
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.parse(episode.getFirstAired());
            } catch (ParseException error) {
                date = null;
            }

            if (date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                year = String.valueOf(cal.get(Calendar.YEAR));
            }
        }

        return year;
    }

    public Banners getBanners(String seriesId) {
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(seriesId);
            urlBuilder.append("/banners.xml");
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new Banners();
        }

        return TvdbParser.getBanners(urlBuilder.toString(), getBannerMirror(apiKey));
    }

    /**
     * Get a list of actors from the series id
     *
     * @param SeriesId
     * @return
     */
    public List<Actor> getActors(String seriesId) {
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append(SERIES_URL);
            urlBuilder.append(seriesId);
            urlBuilder.append("/actors.xml");
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new ArrayList<Actor>();
        }
        return TvdbParser.getActors(urlBuilder.toString(), getBannerMirror(apiKey));
    }

    public List<Series> searchSeries(String title, String language) {
        StringBuilder urlBuilder = new StringBuilder();

        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append("GetSeries.php?seriesname=");
            urlBuilder.append(URLEncoder.encode(title, "UTF-8"));
            if (language != null) {
                urlBuilder.append("&language=").append(language);
            }
        } catch (UnsupportedEncodingException e) {
            // Try and use the raw title
            urlBuilder.append(title);
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new ArrayList<Series>();
        }

        return TvdbParser.getSeriesList(urlBuilder.toString(), getBannerMirror(apiKey));
    }

    /**
     * Get information for a specific episode
     *
     * @param episodeId
     * @param language
     * @return
     */
    public Episode getEpisodeById(String episodeId, String language) {
        StringBuilder urlBuilder = new StringBuilder();

        try {
            urlBuilder.append(getXmlMirror(apiKey));
            urlBuilder.append(apiKey);
            urlBuilder.append("/episodes/");
            urlBuilder.append(episodeId);
            urlBuilder.append("/");
            if (StringUtils.isNotBlank(language)) {
                urlBuilder.append(language);
                urlBuilder.append(XML_EXTENSION);
            }
        } catch (WebServiceException ex) {
            logger.warn(ex.getMessage());
            return new Episode();
        }
        logger.debug("URL: " + urlBuilder.toString());
        return TvdbParser.getEpisode(urlBuilder.toString(), getBannerMirror(apiKey));
    }

    public TVDBUpdates getWeeklyUpdates() {
        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append(getXmlMirror(apiKey));
        urlBuilder.append(apiKey);
        urlBuilder.append(WEEKLY_UPDATES_URL);

        logger.debug("URL: " + urlBuilder.toString());
        return TvdbParser.getUpdates(urlBuilder.toString());
    }

    /**
     * Get the XML Mirror URL
     *
     * @return
     */
    public static String getXmlMirror(String apiKey) {
        // Force a load of the mirror information if it doesn't exist
        getMirrors(apiKey);
        return xmlMirror;
    }

    /**
     * Get the Banner Mirror URL
     *
     * @return
     */
    public static String getBannerMirror(String apiKey) {
        // Force a load of the mirror information if it doesn't exist
        getMirrors(apiKey);
        return bannerMirror;
    }

    private boolean isValidNumber(String number) {
        return StringUtils.isNumeric(number);
    }

    private boolean isValidNumber(int number) {
        return (number >= 0 ? true : false);
    }
}