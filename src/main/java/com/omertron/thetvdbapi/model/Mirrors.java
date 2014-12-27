/*
 *      Copyright (c) 2004-2014 Matthew Altman & Stuart Boston
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

import com.omertron.thetvdbapi.tools.DOMHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author altman.matthew
 * @author stuart.boston
 */
public class Mirrors {

    public static final String TYPE_XML = "XML";
    public static final String TYPE_BANNER = "BANNER";
    public static final String TYPE_ZIP = "ZIP";

    private static final String MIRROR_URL = "http://thetvdb.com/";

    // Keep both constructors to maintain compatibility with existing code, but there's no need to supply the API key anymore
    public Mirrors(){}
    public Mirrors(String apiKey) {}

    public String getMirror(String type) {
        return MIRROR_URL;
    }
}