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
package com.omertron.thetvdbapi.model;

/**
 * Describes the list of banner types that are returned in the "BannerType2" field from TheTVDB
 * @author Stuart.Boston
 *
 */
public enum BannerType {
    Graphical("graphical"),
    Season("season"),
    SeasonWide("seasonwide"),
    Blank("blank"),
    Text("text"),
    FanartHD("1920x1080"),
    FanartSD("1280x720"),
    Poster("680x1000"),
    Artwork("artwork");

    private String type;

    private BannerType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    /**
     * Set the banner type from a string.
     * If the banner type isn't found, but the type contains an "x" as in 1920x1080 then the type will be set to Artwork
     * @param type
     * @return
     */
    public static BannerType fromString(String type) {
        if (type != null) {
            try {
                for (BannerType bannerType : BannerType.values()) {
                    if (type.equalsIgnoreCase(bannerType.type)) {
                        return bannerType;
                    }
                }

                // If we've not found the type, then try a generic Artwork for the 1920x1080, 1280x720 or 680x1000 values
                if (type.toLowerCase().contains("x")) {
                    return BannerType.Artwork;
                }
            } catch (IllegalArgumentException ex) {
                if (type.toLowerCase().contains("x")) {
                    return BannerType.Artwork;
                } else {
                    throw new IllegalArgumentException("BannerType '" + type + "' does not exist", ex);
                }
            }
        }
        throw new IllegalArgumentException("BannerType is null");
    }

}
