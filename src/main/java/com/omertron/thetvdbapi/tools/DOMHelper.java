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
package com.omertron.thetvdbapi.tools;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceException;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Generic set of routines to process the DOM model data
 *
 * @author Stuart.Boston
 *
 */
public class DOMHelper {

    private static final Logger logger = Logger.getLogger(DOMHelper.class);
    private static final String YES = "yes";
    private static final String ENCODING = "UTF-8";
    private static final int RETRY_COUNT = 5;
    private static final int RETRY_TIME = 250;  // Milliseconds to retry

    // Hide the constructor
    protected DOMHelper() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the string value of the tag element name passed
     *
     * @param element
     * @param tagName
     * @return
     */
    public static String getValueFromElement(Element element, String tagName) {
        NodeList elementNodeList = element.getElementsByTagName(tagName);
        if (elementNodeList == null) {
            return "";
        } else {
            Element tagElement = (Element) elementNodeList.item(0);
            if (tagElement == null) {
                return "";
            }

            NodeList tagNodeList = tagElement.getChildNodes();
            if (tagNodeList == null || tagNodeList.getLength() == 0) {
                return "";
            }
            return ((Node) tagNodeList.item(0)).getNodeValue();
        }
    }

    /**
     * Get a DOM document from the supplied URL
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static synchronized Document getEventDocFromUrl(String url) throws WebServiceException {
        String webPage = null;
        InputStream in = null;
        int retryCount = 0;     // Count the number of times we download the web page
        boolean valid = false;  // Is the web page valid

        try {
            while (!valid && (retryCount < RETRY_COUNT)) {
                retryCount++;
                webPage = WebBrowser.request(url);

                // See if the ID is null
                if (!webPage.contains("<id>") || webPage.contains("<id></id>")) {
                    // Wait an increasing amount of time the more retries that happen
                    waiting(retryCount * RETRY_TIME);
                    continue;
                } else {
                    valid = true;
                }
            }

            // Couldn't get a valid webPage so, quit.
            if (!valid) {
                throw new WebServiceException("Failed to download data from " + url);
            }

            in = new ByteArrayInputStream(webPage.getBytes(ENCODING));
        } catch (UnsupportedEncodingException error) {
            throw new WebServiceException("Unable to encode URL: " + url, error);
        } catch (IOException error) {
            throw new WebServiceException("Unable to download URL: " + url, error);
        }

        Document doc = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(in);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException error) {
            throw new WebServiceException("Unable to parse TheTVDb response, please try again later.", error);
        } catch (SAXException error) {
            throw new WebServiceException("Unable to parse TheTVDb response, please try again later.", error);
        } catch (IOException error) {
            throw new WebServiceException("Unable to parse TheTVDb response, please try again later.", error);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException error) {
                    // Input Stream was already closed or null
                }
            }
        }

        return doc;
    }

    /**
     * Convert a DOM document to a string
     *
     * @param doc
     * @return
     * @throws TransformerException
     */
    public static String convertDocToString(Document doc) throws TransformerException {
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
        trans.setOutputProperty(OutputKeys.INDENT, YES);

        //create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        return sw.toString();
    }

    /**
     * Write the Document out to a file using nice formatting
     *
     * @param doc The document to save
     * @param localFile The file to write to
     * @return
     */
    public static boolean writeDocumentToFile(Document doc, String localFile) {
        try {
            TransformerFactory transfact = TransformerFactory.newInstance();
            Transformer trans = transfact.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
            trans.setOutputProperty(OutputKeys.INDENT, YES);
            trans.transform(new DOMSource(doc), new StreamResult(new File(localFile)));
            return true;
        } catch (TransformerConfigurationException error) {
            logger.warn("Error writing the document to " + localFile);
            logger.warn("Message: " + error.getMessage());
            return false;
        } catch (TransformerException error) {
            logger.warn("Error writing the document to " + localFile);
            logger.warn("Message: " + error.getMessage());
            return false;
        }
    }

    /**
     * Add a child element to a parent element
     *
     * @param doc
     * @param parentElement
     * @param elementName
     * @param elementValue
     */
    public static void appendChild(Document doc, Element parentElement, String elementName, String elementValue) {
        Element child = doc.createElement(elementName);
        Text text = doc.createTextNode(elementValue);
        child.appendChild(text);
        parentElement.appendChild(child);
    }

    /**
     * Wait for a few milliseconds
     *
     * @param milliseconds
     */
    private static void waiting(int milliseconds) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < milliseconds);
    }
}
