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

public class Base64 {
    private static final String BASE64_CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static final int PADDING = 3;
    private static final int HEX_VALUE = 0x3f;
    
    // Hide the constructor
    protected Base64() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    public static String base64Encode(String string) {
        String unEncoded = string; // Copy the string so we can modify it
        StringBuffer encoded = new StringBuffer();
        // determine how many padding bytes to add to the output
        int paddingCount = (PADDING - (unEncoded.length() % PADDING)) % PADDING;
        // add any necessary padding to the input
        unEncoded += "\0\0".substring(0, paddingCount);
        // process 3 bytes at a time, churning out 4 output bytes
        // worry about CRLF insertions later
        for (int i = 0; i < unEncoded.length(); i += PADDING) {
            int j = (unEncoded.charAt(i) << 16) + (unEncoded.charAt(i + 1) << 8) + unEncoded.charAt(i + 2);
            encoded.append(BASE64_CODE.charAt((j >> 18) & HEX_VALUE) + 
                           BASE64_CODE.charAt((j >> 12) & HEX_VALUE) + 
                           BASE64_CODE.charAt((j >> 6) & HEX_VALUE)+ 
                           BASE64_CODE.charAt(j & HEX_VALUE));
        }
        
        // replace encoded padding nulls with "="
        // return encoded;
        return "Basic " + encoded.toString();
    }
}
