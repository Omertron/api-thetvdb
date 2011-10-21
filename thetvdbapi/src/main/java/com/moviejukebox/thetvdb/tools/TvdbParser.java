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
package com.moviejukebox.thetvdb.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.moviejukebox.thetvdb.TheTVDB;
import com.moviejukebox.thetvdb.model.Actor;
import com.moviejukebox.thetvdb.model.Banner;
import com.moviejukebox.thetvdb.model.BannerListType;
import com.moviejukebox.thetvdb.model.BannerType;
import com.moviejukebox.thetvdb.model.Banners;
import com.moviejukebox.thetvdb.model.Episode;
import com.moviejukebox.thetvdb.model.Series;

public class TvdbParser {
    private static Logger logger = TheTVDB.getLogger();

    private static final String TYPE_BANNER = "banner";
    private static final String TYPE_FANART = "fanart";
    private static final String TYPE_POSTER = "poster";
    
    private static final String BANNER_PATH = "BannerPath";
    private static final String VIGNETTE_PATH = "VignettePath";
    private static final String THUMBNAIL_PATH = "ThumbnailPath";

    private static final int MAX_EPISODE = 24;  // The anticipated largest episode number
    
    // Hide the constructor
    protected TvdbParser() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Get a list of the actors from the URL
     * @param urlString
     * @return
     */
    public static List<Actor> getActors(String urlString) {
        List<Actor> results = new ArrayList<Actor>();
        Actor actor = null;
        Document doc = null;
        NodeList nlActor;
        Node nActor;
        Element eActor;
        
        try {
            doc = DOMHelper.getEventDocFromUrl(urlString);
        } catch (Throwable tw) {
            return results;
        }

        nlActor = doc.getElementsByTagName("Actor");
        
        for (int loop = 0; loop < nlActor.getLength(); loop++) {
            nActor = nlActor.item(loop);
            
            if (nActor.getNodeType() == Node.ELEMENT_NODE) {
                eActor = (Element) nActor;
                actor = new Actor();
            
                actor.setId(DOMHelper.getValueFromElement(eActor, "id"));
                String image = DOMHelper.getValueFromElement(eActor, "Image");
                if (!image.isEmpty()) {
                    actor.setImage(TheTVDB.getBannerMirror() + image);                    
                }
                actor.setName(DOMHelper.getValueFromElement(eActor, "Name"));
                actor.setRole(DOMHelper.getValueFromElement(eActor, "Role"));
                actor.setSortOrder(DOMHelper.getValueFromElement(eActor, "SortOrder"));
                
                results.add(actor);
            }
        }

        Collections.sort(results);
        
        return results;
    }
    
    /**
     * Get all the episodes from the URL
     * @param urlString
     * @param season 
     * @return
     */
    public static List<Episode> getAllEpisodes(String urlString, int season) {
        List<Episode> episodeList = new ArrayList<Episode>();
        Episode episode = null;
        NodeList nlEpisode;
        Node nEpisode;
        Element eEpisode;
        
        try {
            Document doc = DOMHelper.getEventDocFromUrl(urlString);
            nlEpisode = doc.getElementsByTagName("Episode");
            for (int loop = 0; loop < nlEpisode.getLength(); loop++) {
                nEpisode = nlEpisode.item(loop);
                if (nEpisode.getNodeType() == Node.ELEMENT_NODE) {
                    eEpisode = (Element) nEpisode;
                    episode = parseNextEpisode(eEpisode);
                    if ((episode != null) &&  (season == -1 || episode.getSeasonNumber() == season)) {
                        // Add the episode only if the season is -1 (all seasons) or matches the season
                        episodeList.add(episode);
                    }
                }
            }
        } catch (Exception error) {
            logger.warning("All Episodes error: " + error.getMessage());
        } catch (Throwable tw) {
            // Message is passed to us
            logger.warning(tw.getMessage());
        }
        
        return episodeList;
    }

    /**
     * Get a list of banners from the URL
     * @param urlString
     * @return
     */
    public static Banners getBanners(String urlString) {
        Banners banners = new Banners();
        Banner banner = null;
        
        NodeList nlBanners;
        Node nBanner;
        Element eBanner;
        
        try {
            Document doc = DOMHelper.getEventDocFromUrl(urlString);
            
            nlBanners = doc.getElementsByTagName("Banner");
            for (int loop = 0; loop < nlBanners.getLength(); loop++) {
                nBanner = nlBanners.item(loop);
                if (nBanner.getNodeType() == Node.ELEMENT_NODE) {
                    eBanner = (Element) nBanner;
                    banner = parseNextBanner(eBanner);
                    if (banner != null) {
                        banners.addBanner(banner);
                    }
                }
            }
        } catch (Exception error) {
            logger.warning("Banners error: " + error.getMessage());
        } catch (Throwable tw) {
            // Message is passed to us
            logger.warning(tw.getMessage());
        }
        
        return banners;
    }

    /**
     * Get the episode information from the URL
     * @param urlString
     * @return
     */
    public static Episode getEpisode(String urlString) {
        Episode episode = null;
        NodeList nlEpisode;
        Node nEpisode;
        Element eEpisode;
        
        try {
            Document doc = DOMHelper.getEventDocFromUrl(urlString);
            nlEpisode = doc.getElementsByTagName("Episode");
            for (int loop = 0; loop < nlEpisode.getLength(); loop++) {
                nEpisode = nlEpisode.item(loop);
                if (nEpisode.getNodeType() == Node.ELEMENT_NODE) {
                    eEpisode = (Element) nEpisode;
                    episode = parseNextEpisode(eEpisode);
                    if (episode != null) {
                        // We only need the first episode
                        break;
                    }
                }
            }
        } catch (Exception error) {
            logger.warning("Series error: " + error.getMessage());
        } catch (Throwable tw) {
            // Message is passed to us
            logger.warning(tw.getMessage());
        }
        
        return episode;

    }

    /**
     * Get a list of series from the URL
     * @param urlString
     * @return
     */
    public static List<Series> getSeriesList(String urlString) {
        List<Series> seriesList = new ArrayList<Series>();
        Series series = null;
        NodeList nlSeries;
        Node nSeries;
        Element eSeries;
        
        Document doc = null;
        
        try {
            doc = DOMHelper.getEventDocFromUrl(urlString);
        } catch (Throwable tw) {
            return seriesList;
        }
        
        nlSeries = doc.getElementsByTagName("Series");
        for (int loop = 0; loop < nlSeries.getLength(); loop++) {
            nSeries = nlSeries.item(loop);
            if (nSeries.getNodeType() == Node.ELEMENT_NODE) {
                eSeries = (Element) nSeries;
                series = parseNextSeries(eSeries);
                if (series != null) {
                    seriesList.add(series);
                }
            }
        }
        
        return seriesList;
    }

    /**
     * Parse the error message to return a more user friendly message
     * @param errorMessage
     * @return
     */
    public static String parseErrorMessage(String errorMessage) {
        StringBuilder response = new StringBuilder();
        
        Pattern pattern = Pattern.compile(".*?/series/(\\d*?)/default/(\\d*?)/(\\d*?)/.*?");
        Matcher matcher = pattern.matcher(errorMessage);
        
        // See if the error message matches the pattern and therefore we can decode it
        if (matcher.find() && matcher.groupCount() == 3) {
            int seriesId = Integer.parseInt(matcher.group(1));
            int seasonId = Integer.parseInt(matcher.group(2));
            int episodeId = Integer.parseInt(matcher.group(3));
            
            response.append("Series Id: ").append(seriesId);
            response.append(", Season: ").append(seasonId);
            response.append(", Episode: ").append(episodeId);
            response.append(": ");
            
            if (episodeId == 0) {
                // We should probably try an scrape season 0/episode 1
                response.append("Episode seems to be a misnamed pilot episode.");
            } else if (episodeId > MAX_EPISODE) {
                response.append("Episode number seems to be too large.");
            } else if (seasonId == 0 && episodeId > 1) {
                response.append("This special episode does not exist.");
            } else if (errorMessage.toLowerCase().contains("content is not allowed in prolog")) {
                response.append("Unable to retrieve episode information from TheTVDb, try again later.");
            } else {
                response.append("Unknown episode error: ").append(errorMessage);
            }
        } else {
            // Don't recognise the error format, so just return it
            if (errorMessage.toLowerCase().contains("content is not allowed in prolog")) {
                response.append("Unable to retrieve episode information from TheTVDb, try again later.");
            } else {
                response.append("Episode error: ").append(errorMessage);
            }
        }
        
        return response.toString();
    }

    /**
     * Create a List from a delimited string
     * @param input
     * @param delim
     * @return
     */
    private static List<String> parseList(String input, String delim) {
        List<String> result = new ArrayList<String>();
        
        StringTokenizer st = new StringTokenizer(input, delim);
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            if (token.length() > 0) {
                result.add(token);
            }
        }
        
        return result;
    }
    
    /**
     * Parse the banner record from the document
     * @param eBanner
     * @return
     * @throws Throwable
     */
    private static Banner parseNextBanner(Element eBanner) throws Throwable {
        String bannerMirror = TheTVDB.getBannerMirror();
        Banner banner = new Banner();
        String artwork;
        
        artwork = DOMHelper.getValueFromElement(eBanner, BANNER_PATH);
        if (!artwork.isEmpty()) {
            banner.setUrl(bannerMirror + artwork);
        }
        
        artwork = DOMHelper.getValueFromElement(eBanner, VIGNETTE_PATH);
        if (!artwork.isEmpty()) {
            banner.setVignette(bannerMirror + artwork);
        }
        
        artwork = DOMHelper.getValueFromElement(eBanner, THUMBNAIL_PATH);
        if (!artwork.isEmpty()) {
            banner.setThumb(bannerMirror + artwork);
        }
        
        banner.setId(DOMHelper.getValueFromElement(eBanner, "id"));
        banner.setBannerType(BannerListType.fromString(DOMHelper.getValueFromElement(eBanner, "BannerType")));
        banner.setBannerType2(BannerType.fromString(DOMHelper.getValueFromElement(eBanner, "BannerType2")));
        banner.setLanguage(DOMHelper.getValueFromElement(eBanner, "Language"));
        banner.setSeason(DOMHelper.getValueFromElement(eBanner, "Season"));
        banner.setColours(DOMHelper.getValueFromElement(eBanner, "Colors"));
        
        try {
            banner.setSeriesName(Boolean.parseBoolean(DOMHelper.getValueFromElement(eBanner, "SeriesName")));
        } catch (Exception error) {
            banner.setSeriesName(false);
        }
        
        return banner;
    }

    /**
     * Parse the document for episode information
     * @param doc
     * @return
     * @throws Throwable 
     */
    private static Episode parseNextEpisode(Element eEpisode) throws Throwable {
        Episode episode = new Episode();
        
        episode.setId(DOMHelper.getValueFromElement(eEpisode, "id"));
        episode.setCombinedEpisodeNumber(DOMHelper.getValueFromElement(eEpisode, "Combined_episodenumber"));
        episode.setCombinedSeason(DOMHelper.getValueFromElement(eEpisode, "Combined_season"));
        episode.setDvdChapter(DOMHelper.getValueFromElement(eEpisode, "DVD_chapter"));
        episode.setDvdDiscId(DOMHelper.getValueFromElement(eEpisode, "DVD_discid"));
        episode.setDvdEpisodeNumber(DOMHelper.getValueFromElement(eEpisode, "DVD_episodenumber"));
        episode.setDvdSeason(DOMHelper.getValueFromElement(eEpisode, "DVD_season"));
        episode.setDirectors(parseList(DOMHelper.getValueFromElement(eEpisode, "Director"), "|,"));
        episode.setEpImgFlag(DOMHelper.getValueFromElement(eEpisode, "EpImgFlag"));
        episode.setEpisodeName(DOMHelper.getValueFromElement(eEpisode, "EpisodeName"));
        try {
            episode.setEpisodeNumber(Integer.parseInt(DOMHelper.getValueFromElement(eEpisode, "EpisodeNumber")));
        } catch (Exception ignore) {
            episode.setEpisodeNumber(0);
        }
        episode.setFirstAired(DOMHelper.getValueFromElement(eEpisode, "FirstAired"));
        episode.setGuestStars(parseList(DOMHelper.getValueFromElement(eEpisode, "GuestStars"), "|,"));
        episode.setImdbId(DOMHelper.getValueFromElement(eEpisode, "IMDB_ID"));
        episode.setLanguage(DOMHelper.getValueFromElement(eEpisode, "Language"));
        episode.setOverview(DOMHelper.getValueFromElement(eEpisode, "Overview"));
        episode.setProductionCode(DOMHelper.getValueFromElement(eEpisode, "ProductionCode"));
        episode.setRating(DOMHelper.getValueFromElement(eEpisode, "Rating"));
        try {
            episode.setSeasonNumber(Integer.parseInt(DOMHelper.getValueFromElement(eEpisode, "SeasonNumber")));
        } catch (Exception ignore) {
            episode.setSeasonNumber(0);
        }
        episode.setWriters(parseList(DOMHelper.getValueFromElement(eEpisode, "Writer"), "|,"));
        episode.setAbsoluteNumber(DOMHelper.getValueFromElement(eEpisode, "absolute_number"));
        String s = DOMHelper.getValueFromElement(eEpisode, "filename");
        if (!s.isEmpty()) {
            episode.setFilename(TheTVDB.getBannerMirror() + s);
        }
        episode.setLastUpdated(DOMHelper.getValueFromElement(eEpisode, "lastupdated"));
        episode.setSeasonId(DOMHelper.getValueFromElement(eEpisode, "seasonid"));
        episode.setSeriesId(DOMHelper.getValueFromElement(eEpisode, "seriesid"));

        try {
            episode.setAirsAfterSeason(Integer.parseInt(DOMHelper.getValueFromElement(eEpisode, "airsafter_season")));
        } catch (Exception ignore) {
            episode.setAirsAfterSeason(0);
        }
        
        try {
            episode.setAirsBeforeEpisode(Integer.parseInt(DOMHelper.getValueFromElement(eEpisode, "airsbefore_episode")));
        } catch (Exception ignore) {
            episode.setAirsBeforeEpisode(0);
        }

        try {
            episode.setAirsBeforeSeason(Integer.parseInt(DOMHelper.getValueFromElement(eEpisode, "airsbefore_season")));
        } catch (Exception ignore) {
            episode.setAirsBeforeSeason(0);
        }
        
        return episode;
    }

    /**
     * Parse the series record from the document
     * @param eSeries
     * @return
     * @throws Throwable
     */
    private static Series parseNextSeries(Element eSeries) {
        String bannerMirror = TheTVDB.getBannerMirror();
        
        Series series = new Series();
        
        series.setId(DOMHelper.getValueFromElement(eSeries, "id"));
        series.setActors(parseList(DOMHelper.getValueFromElement(eSeries, "Actors"),"|,"));
        series.setAirsDayOfWeek(DOMHelper.getValueFromElement(eSeries, "Airs_DayOfWeek"));
        series.setAirsTime(DOMHelper.getValueFromElement(eSeries, "Airs_Time"));
        series.setContentRating(DOMHelper.getValueFromElement(eSeries, "ContentRating"));
        series.setFirstAired(DOMHelper.getValueFromElement(eSeries, "FirstAired"));
        series.setGenres(parseList(DOMHelper.getValueFromElement(eSeries, "Genre"), "|,"));
        series.setImdbId(DOMHelper.getValueFromElement(eSeries, "IMDB_ID"));
        series.setLanguage(DOMHelper.getValueFromElement(eSeries, "language"));
        series.setNetwork(DOMHelper.getValueFromElement(eSeries, "Network"));
        series.setOverview(DOMHelper.getValueFromElement(eSeries, "Overview"));
        series.setRating(DOMHelper.getValueFromElement(eSeries, "Rating"));
        series.setRuntime(DOMHelper.getValueFromElement(eSeries, "Runtime"));
        series.setSeriesId(DOMHelper.getValueFromElement(eSeries, "SeriesID"));
        series.setSeriesName(DOMHelper.getValueFromElement(eSeries, "SeriesName"));
        series.setStatus(DOMHelper.getValueFromElement(eSeries, "Status"));
        
        String artwork = DOMHelper.getValueFromElement(eSeries, TYPE_BANNER);
        if (!artwork.isEmpty()) {
            series.setBanner(bannerMirror + artwork);
        }
        
        artwork = DOMHelper.getValueFromElement(eSeries, TYPE_FANART);
        if (!artwork.isEmpty()) {
            series.setFanart(bannerMirror + artwork);
        }
        
        artwork = DOMHelper.getValueFromElement(eSeries, TYPE_POSTER);
        if (!artwork.isEmpty()) {
            series.setPoster(bannerMirror + artwork);
        }
                
        series.setLastUpdated(DOMHelper.getValueFromElement(eSeries, "lastupdated"));
        series.setZap2ItId(DOMHelper.getValueFromElement(eSeries, "zap2it_id"));

        return series;
    }
}
