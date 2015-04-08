/*
 *      Copyright (c) 2004-2013 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      This file is part of the Yet Another Media Jukebox (YAMJ).
 *
 *      The YAMJ is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      YAMJ is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with the YAMJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 */
package com.omertron.thetvdbapi.model;

import com.omertron.thetvdbapi.TestLogger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stuart
 */
public class BannerTypeTest {

    private static final Logger LOG = LoggerFactory.getLogger(BannerTypeTest.class);
    @Rule
    public ExpectedException exception = ExpectedException.none();

    public BannerTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getType method, of class BannerType.
     */
    @Test
    public void testGetType() {
        LOG.info("getType");
        BannerType instance = BannerType.ARTWORK;
        String expResult = "artwork";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of fromString method, of class BannerType.
     */
    @Test
    public void testFromString() {
        LOG.info("fromString");
        String type = "1920x1080";
        BannerType expResult = BannerType.FANART_HD;
        BannerType result = BannerType.fromString(type);
        assertEquals(expResult, result);

        // Test a simple name
        assertEquals(BannerType.ARTWORK, BannerType.fromString("artwork"));

        // Test a "x" name
        assertEquals(BannerType.FANART_SD, BannerType.fromString("1280x720"));

        // Test an unknown "x"
        assertEquals(BannerType.ARTWORK, BannerType.fromString("1024x768"));

        // Test an unknown value
        exception.expect(IllegalArgumentException.class);
        BannerType.fromString("Silly");

        // Test a empty value
        exception.expect(IllegalArgumentException.class);
        BannerType.fromString("");

        // Test a null value
        exception.expect(IllegalArgumentException.class);
        BannerType.fromString(null);

    }

}
