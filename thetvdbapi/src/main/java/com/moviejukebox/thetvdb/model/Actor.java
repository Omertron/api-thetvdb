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

    @Override
    public String toString() {
        StringBuffer actor = new StringBuffer("[Actor ");
        actor.append("[id=").append(id).append("]");
        actor.append("[name=").append(name).append("]");
        actor.append("[role=").append(role).append("]");
        actor.append("[image=").append(image).append("]");
        actor.append("[sortOrder=").append(sortOrder).append("]");
        actor.append("]");
        return actor.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + sortOrder;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (!(obj instanceof Actor)) {
            return false;
        }
        
        Actor other = (Actor)obj;
        
        if (id != other.id) {
            return false;
        }
        
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        
        if (role == null) {
            if (other.role != null) {
                return false;
            }
        } else if (!role.equals(other.role)) {
            return false;
        }
        
        return true;
    }
}
