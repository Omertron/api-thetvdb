The TVDB API
============
__Originally written by__: Matthew Altman (sTyLeS)

__Maintained by__: Stuart Boston (Omertron AT Gmail DOT com)

Originally written for use by Yet Another Movie Jukebox [(YAMJ)](http://code.google.com/p/moviejukebox/), but anyone can use it for other projects as well.

This API uses [TheTVDB.com API](http://www.thetvdb.com/wiki/index.php/Programmers_API)

[![Flattr this git repo](http://api.flattr.com/button/flattr-badge-large.png)](https://flattr.com/submit/auto?user_id=Omertron&url=https://github.com/Omertron/api-thetvdb&title=TheTVDB API&language=&tags=github&category=software)

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/Omertron/api-thetvdb/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

***
### TheTVDB.com
TheTVDB.com is an awesome open database for television content.

I *strongly* encourage you to check it out and contribute to keep it growing.

### http://www.thetvdb.com
***
Usage
=====
Create an instance of TheTVDB using your apiKey which must be requested [HERE](http://thetvdb.com/?tab=apiregister)

`TheTVDBApi tvDB = new TheTVDBApi("your_api_key");`

### Search for Series by name and language
The language is optional and may be null, which would end up defaulting to English

The Resulting Series objects from this call have limited information since it is a generic search

__Syntax__

`searchSeries(String title, String language)`

__Example__

`List<Series> results = tvDB.searchSeries("Lost", "en");`

### Obtain full Series details by ID
May use the series.getId() method from the previous search
The language is optional and will default to english if null, but if present the resulting descriptions will be in that language

__Syntax__

`getSeries(String id, String language)`

__Example__

`Series series = tvDB.getSeries("73739", "en");`

### Obtain detailed Episode data
The language is optional and will default to english if null, but will return details in that language if present

__Syntax__

`getEpisode(String id, int seasonNbr, int episodeNbr, String language)`

__Example__

`Episode episode = tvDB.getEpisode("73739", 2, 3, "en");`

### Obtain image references
Returns a Banners object which contains separate lists for series, season, poster, and fanart images

__Syntax__

`getBanners(String id)`

__Example__

`Banners banners = tvDB.getBanners("73739");`

### Obtain list of more detailed Actor data
The Series object does contain a list of actor names, but the Actor object has more details including roles and image references

__Syntax__

`getActors(String id)`

__Example__

`List<Actor> actors = tvDB.getActors("73739");`

Project Logging
---------------
This project uses [SLF4J](http://www.slf4j.org) to abstract the logging in the project.

To use the logging in your own project you should add one of the bindings listed [HERE](http://www.slf4j.org/manual.html#swapping)

Project Documentation
---------------------
The automatically generated documentation can be found [HERE](http://omertron.github.com/api-thetvdb/)
