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

import java.security.PrivilegedAction;
import java.util.logging.LogRecord;

public class LogFormatter extends java.util.logging.Formatter 
{
    private static String apiKey = null;
    private static final String EOL = (String)java.security.AccessController.doPrivileged(new PrivilegedAction<Object>() {
        public Object run() {
            return System.getProperty("line.separator");
        }
    });

    public synchronized String format(LogRecord logRecord) {
        String logMessage = logRecord.getMessage();

        if (logMessage == null) {
            return "";
        }

        if (apiKey != null) {
            logMessage = "[TheTVDB API] " + logMessage.replace(apiKey, "[APIKEY]") + EOL;
        }
        
        Throwable thrown = logRecord.getThrown();
        if (thrown != null) { 
            logMessage = logMessage + thrown.toString(); 
        }
        return logMessage;
    }
    
    public void addApiKey(String apiKey) {
        LogFormatter.apiKey = apiKey; 
        return;
    }
}
