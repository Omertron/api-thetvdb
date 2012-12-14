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

    private static final int MASK_XML = 1;
    private static final int MASK_BANNER = 2;
    private static final int MASK_ZIP = 4;


    private static final Random RNDM = new Random();

    private List<String> xmlList = new ArrayList<String>();
    private List<String> bannerList = new ArrayList<String>();
    private List<String> zipList = new ArrayList<String>();

    public Mirrors(String apiKey) {
        // Make this synchronized so that only one
        synchronized (this) {
            String urlString = "http://www.thetvdb.com/api/" + apiKey + "/mirrors.xml";
            Document doc;

            doc = DOMHelper.getEventDocFromUrl(urlString);
            int typeMask;
            String url;

            NodeList nlMirror = doc.getElementsByTagName("Mirror");
            for (int nodeLoop = 0; nodeLoop < nlMirror.getLength(); nodeLoop++) {
                Node nMirror = nlMirror.item(nodeLoop);

                if (nMirror.getNodeType() == Node.ELEMENT_NODE) {
                    Element eMirror = (Element) nMirror;
                    url = DOMHelper.getValueFromElement(eMirror, "mirrorpath");
                    typeMask = Integer.parseInt(DOMHelper.getValueFromElement(eMirror, "typemask"));
                    addMirror(typeMask, url);
                }
            }
        }
    }

    public String getMirror(String type) {
        String url = null;
        if (type.equals(TYPE_XML) && !xmlList.isEmpty()) {
            url = xmlList.get(RNDM.nextInt(xmlList.size()));
        } else if (type.equals(TYPE_BANNER) && !bannerList.isEmpty()) {
            url = bannerList.get(RNDM.nextInt(bannerList.size()));
        } else if (type.equals(TYPE_ZIP) && !zipList.isEmpty()) {
            url = zipList.get(RNDM.nextInt(zipList.size()));
        }
        return url;
    }

    private void addMirror(int typeMask, String url) {
        switch (typeMask) {
            case MASK_XML:
                xmlList.add(url);
                break;
            case MASK_BANNER:
                bannerList.add(url);
                break;
            case (MASK_XML + MASK_BANNER):
                xmlList.add(url);
                bannerList.add(url);
                break;
            case MASK_ZIP:
                zipList.add(url);
                break;
            case (MASK_XML + MASK_ZIP):
                xmlList.add(url);
                zipList.add(url);
                break;
            case (MASK_BANNER + MASK_ZIP):
                bannerList.add(url);
                zipList.add(url);
                break;
            case (MASK_XML + MASK_BANNER + MASK_ZIP):
                xmlList.add(url);
                bannerList.add(url);
                zipList.add(url);
                break;
            default:
                break;
        }
    }
}