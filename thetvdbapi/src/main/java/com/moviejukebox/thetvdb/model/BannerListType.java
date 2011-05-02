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
package com.moviejukebox.thetvdb.model;

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
                throw new IllegalArgumentException("BannerListType " + type + " does not exist");
            }
        }
        throw new IllegalArgumentException("BannerListType is null");
    }

}
