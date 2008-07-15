/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.service.digitalrun.track.ConfigurationProvider;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link ConfigurationProvider}.
 *
 * @author KLW
 * @version 1.0
  */
public class ConfigurationProviderAccTests extends TestCase {
    /**
     * test the method {@link ConfigurationProvider#retrieveConfigurationFromFile(String, String)}.
     * @throws Exception all exception throw to JUnit.
     */
    public void testGetConfiguration(){
        ConfigurationObject cobj = ConfigurationProvider.getConfiguration();
        assertNotNull("The configuration object should not be null.",cobj);    
    }
    
}