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
