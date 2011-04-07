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
 * This is the simplistic Person model for TheTVDb.
 * 
 * @author matthew.altman
 * @author stuart.boston
 */
public class Person implements Comparable<Person> {
    private int id;
    private String name;
    private String role;
    private String job;
    private String image;
    private int sortOrder = 0;
    
    /**
     * Constructor with default settings
     */
    public Person() {
        this.id = 0;
        this.name = "";
        this.role = "";
        this.job = "";
        this.image = "";
        this.sortOrder = 0;
    }
    
    /**
     * Constructor for people with a name & job
     * @param name
     * @param job
     */
    public Person(String name, String job) {
        this.id = 0;
        this.name = name;
        this.role = "";
        this.job = job;
        this.image = "";
        this.sortOrder = 0;
    }

    public int compareTo(Person other) {
        // TODO: This needs to be properly implemented using name and role
        return sortOrder - other.getSortOrder();
    }
    
    public int getId() {
        return id;
    }
    
    public String getImage() {
        return image;
    }
    
    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getSortOrder() {
        return sortOrder;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setJob(String job) {
        this.job = job;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public String toString() {
        StringBuffer actor = new StringBuffer("[Person ");
        actor.append("[id=").append(id).append("]");
        actor.append("[name=").append(name).append("]");
        actor.append("[role=").append(role).append("]");
        actor.append("[image=").append(image).append("]");
        actor.append("[sortOrder=").append(sortOrder).append("]");
        actor.append("]");
        return actor.toString();
    }
}
