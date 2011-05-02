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
 * Describes the list of banner types that are retuned in the "BannerType2" field from TheTVDB
 * @author Stuart.Boston
 *
 */
public enum BannerType {
    Season("seasonwide"),
    Series("graphical"),
    Blank("blank");
    
    private String type;
    
    private BannerType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    public static BannerType fromString(String type) {
        if (type != null) {
            try {
                return BannerType.valueOf(type.trim().toLowerCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("BannerType " + type + " does not exist");
            }
        }
        throw new IllegalArgumentException("BannerType is null");
    }

}
