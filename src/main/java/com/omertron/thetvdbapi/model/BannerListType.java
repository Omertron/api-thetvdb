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
 * Describes the list of possible banner types stored in the "BannerType" field returned from TheTVDB
 * @author Stuart.Boston
 *
 */
public enum BannerListType {
    series, season, poster, fanart;

    public static BannerListType fromString(String type) {
        if (type != null) {
            try {
                return BannerListType.valueOf(type.trim().toLowerCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("BannerListType " + type + " does not exist", ex);
            }
        }
        throw new IllegalArgumentException("BannerListType is null");
    }

}
