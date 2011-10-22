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
package com.moviejukebox.thetvdb.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.moviejukebox.thetvdb.TheTVDB;

/**
 * Generic set of routines to process the DOM model data
 * @author Stuart.Boston
 *
 */
public class DOMHelper {
    private static Logger logger = TheTVDB.getLogger();
    
    private static final String YES = "yes";
    private static final String ENCODING = "UTF-8";
    private static final int RETRY_COUNT = 5;

    // Hide the constructor
    protected DOMHelper() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the string value of the tag element name passed
     * @param element
     * @param tagName
     * @return
     */
    public static String getValueFromElement(Element element, String tagName) {
        try {
            NodeList elementNodeList = element.getElementsByTagName(tagName);
            Element tagElement = (Element) elementNodeList.item(0);
            NodeList tagNodeList = tagElement.getChildNodes();
            return ((Node) tagNodeList.item(0)).getNodeValue();
        } catch (NullPointerException error) {
            // The tagName doesn't exist, so exit
            return "";
        }
    }

    /**
     * Get a DOM document from the supplied URL
     * @param url
     * @return
     * @throws Exception 
     */
    public static synchronized Document getEventDocFromUrl(String url) {
        String webPage = null;
        InputStream in = null;
        int retryCount = 0;     // Count the number of times we download the web page
        boolean valid = false;  // Is the web page valid
        
        try {
            while (!valid && (retryCount < RETRY_COUNT)) {
                retryCount++;
//                logger.fine("Try #" + retryCount + " for " + url);  // XXX DEBUG
                webPage = WebBrowser.request(url);
                
                // See if the ID is null
                if (!webPage.contains("<id>") || webPage.contains("<id></id>")) {
                    // Wait an increasing amount of time the more retries that happen
                    waiting(retryCount * 500);
                    continue;
                } else {
                    valid = true;
                }
            }

            // Couldn't get a valid webPage so, quit.
            if (!valid) {
                throw new RuntimeException("Failed to download data from " + url);
            }
            
            in = new ByteArrayInputStream(webPage.getBytes(ENCODING));
        } catch (UnsupportedEncodingException error) {
            throw new RuntimeException("Unable to encode URL: " + url, error);
        } catch (IOException error) {
            throw new RuntimeException("Unable to download URL: " + url, error);
        }
        
        Document doc = null;
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(in);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException error) {
            throw new RuntimeException("Unable to parse TheTVDb response, please try again later.", error);
        } catch (SAXException error) {
            throw new RuntimeException("Unable to parse TheTVDb response, please try again later.", error);
        } catch (IOException error) {
            throw new RuntimeException("Unable to parse TheTVDb response, please try again later.", error);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException error) {
                    // Input Stream was already closed or null
                    in = null;
                }
            }
        }
        
        return doc;
    }

    /**
     * Convert a DOM document to a string
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
     * @param doc   The document to save
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
            logger.warning("Error writing the document to " + localFile);
            logger.warning("Message: " + error.getMessage());
            return false;
        } catch (TransformerException error) {
            logger.warning("Error writing the document to " + localFile);
            logger.warning("Message: " + error.getMessage());
            return false;
        }
    }

    /**
     * Add a child element to a parent element
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

        return;
    }


    /**
     * Wait for a few milliseconds
     * @param milliseconds
     */
    private static void waiting (int milliseconds){
        long t0, t1;
        t0 =  System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < milliseconds);
    }
}
