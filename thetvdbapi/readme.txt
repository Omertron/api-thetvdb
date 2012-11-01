Originally written by: altman.matthew (sTyLeS)
Maintained by: Stuart Boston (Omertron AT Gmail DOT com)

Originally written for use by YetAnotherMovieJukebox (YAMJ) http://code.google.com/p/moviejukebox/
But anyone can feel free to use it for other projects as well.

This uses TheTVDB.com API as specified here http://www.thetvdb.com/wiki/index.php/Programmers_API
TheTVDB.com is an awesome open database for television content. I encourage you to check it out and contribute to keep it growing.
http://www.thetvdb.com

=====
Usage
=====
// create an instance of TheTVDB using your apiKey which must be requested here http://thetvdb.com/?tab=apiregister
TheTVDB tvDB = new TheTVDB("your_api_key");

// search for Series by name and language
// the language is optional and may be null, which would end up defaulting to english
// the Resulting Series objects from this call have limited information since it is a generic search
// searchSeries(String title, String language)
List<Series> results = tvDB.searchSeries("Lost", "en");

// obtain full Series details by id
// may use the series.getId() method from the previous search
// the language is optional and will default to english if null, but if present the resulting descriptions will be in that language
// getSeries(String id, String language)
Series series = tvDB.getSeries("73739", "en");

// obtain detailed Episode data
// again, the language is optional and will default to english if null, but will return details in that language if present
// getEpisode(String id, int seasonNbr, int episodeNbr, String language)
Episode episode = tvDB.getEpisode("73739", 2, 3, "en");

// obtain image references
// returns a Banners object which contains separate lists for series, season, poster, and fanart images
// getBanners(String id)
Banners banners = tvDB.getBanners("73739");

// obtain list of more detailed Actor data
// the Series object does contain a list of actor names, but the Actor object has more details including roles and image references
// getActors(String id)
List<Actor> actors = tvDB.getActors("73739");


