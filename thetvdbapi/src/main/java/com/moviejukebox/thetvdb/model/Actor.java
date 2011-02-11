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
 *
 * @author matthew.altman
 */
public class Actor implements Comparable<Actor> {
    private int id;
    private String name;
    private String role;
    private String image;
    private int sortOrder = 0;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setId(String id) {
        try {
            this.id = Integer.parseInt(id);
        } catch (Exception ignore) {
            this.id = 0;
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        try {
            this.sortOrder = Integer.parseInt(sortOrder);
        } catch (Exception ignore) {
            this.sortOrder = 0;
        }
    }

    public int compareTo(Actor other) {
        return sortOrder - other.getSortOrder();
    }
    
}
