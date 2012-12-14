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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author matthew.altman
 */
@Entity
@Table(name = "ACTOR")
public class Actor implements Comparable<Actor>, Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID
    private int id;
    private String name;
    private String role;
    private String image;
    private int sortOrder = 0;

    @Id
    @Column(name = "ACTOR_ID")
    public int getId() {
        return id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @Column(name = "ACTOR_ROLE")
    public String getRole() {
        return role;
    }

    @Column(name = "IMAGE_URL")
    public String getImage() {
        return image;
    }

    @Column(name = "SORT_ORDER")
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

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setImage(String image) {
        this.image = image;
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
    public int compareTo(Actor other) {
        return sortOrder - other.getSortOrder();
    }

    @Override
    public String toString() {
        StringBuilder actor = new StringBuilder("[Actor ");
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

        Actor other = (Actor) obj;

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
