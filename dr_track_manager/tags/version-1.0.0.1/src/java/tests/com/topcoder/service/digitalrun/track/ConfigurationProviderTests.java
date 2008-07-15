/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.configuration.ConfigurationObject;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for the class <code>ConfigurationProvider.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class ConfigurationProviderTests extends TestCase {
    /**
     * <p>
     * Test the {@link ConfigurationProvider#getConfiguration()} method. It is an accuracy test case.
     * </p>
     */
    public void testRetrieveConfigurationFromFile() {
        ConfigurationObject configuration = ConfigurationProvider.getConfiguration();
        assertNotNull("The configuration is retrieved.", configuration);
    }
}
