/*
 *      Copyright (c) 2004-2015 Matthew Altman & Stuart Boston
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
package com.omertron.thetvdbapi;

import java.io.File;
import java.util.Properties;
import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTests {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTests.class);
    private static final String PROP_FIlENAME = "testing.properties";
    private static final Properties props = new Properties();

    /**
     * Do the initial configuration for the test cases
     *
     * @throws MovieDbException
     */
    protected static final void doConfiguration()  {
        TestLogger.Configure();

        if (props.isEmpty()) {
            File f = new File(PROP_FIlENAME);
            if (f.exists()) {
                LOG.info("Loading properties from '{}'", PROP_FIlENAME);
                TestLogger.loadProperties(props, f);
            } else {
                LOG.info("Property file '{}' not found, creating dummy file.", PROP_FIlENAME);

                props.setProperty("API_Key", "INSERT_YOUR_KEY_HERE");

                TestLogger.saveProperties(props, f, "Properties file for tests");
                fail("Failed to get key information from properties file '" + PROP_FIlENAME + "'");
            }
        }
    }

    /**
     * Get the API Key
     *
     * @return
     */
    protected static String getApiKey() {
        return props.getProperty("API_Key");
    }

    /**
     * Get the named property
     *
     * @param property
     * @return
     */
    protected static String getProperty(String property) {
        return props.getProperty(property);
    }

}
