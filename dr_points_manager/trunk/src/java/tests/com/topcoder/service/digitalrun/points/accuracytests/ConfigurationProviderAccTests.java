/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.service.digitalrun.points.ConfigurationProvider;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link ConfigurationProvider}.
 *
 * @author KLW
 * @version 1.0
  */
public class ConfigurationProviderAccTests extends TestCase {
    /**
     * test the method {@link ConfigurationProvider#setConfiguration(com.topcoder.configuration.ConfigurationObject)}.
     */
    public void testSetConfiguration(){
        ConfigurationObject cobj = new DefaultConfigurationObject("test");
        ConfigurationProvider.setConfiguration(cobj);
        assertEquals("the object is incorrect.", cobj,ConfigurationProvider.getConfiguration());
    }
    /**
     * test the method {@link ConfigurationProvider#retrieveConfigurationFromFile()}.
     * @throws Exception all exception throw to JUnit.
     */
    public void testRetrieveConfigurationFromFile() throws Exception{
        ConfigurationProvider.retrieveConfigurationFromFile();
        ConfigurationObject cobj = ConfigurationProvider.getConfiguration();
        assertNotNull("The configuration object should not be null.",cobj);        
    }
    /**
     * test the method {@link ConfigurationProvider#retrieveConfigurationFromFile(String, String)}.
     * @throws Exception all exception throw to JUnit.
     */
    public void testRetrieveConfigurationFromFile_2() throws Exception{
        ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE, ConfigurationProvider.DEFAULT_NAMESPACE);
        ConfigurationObject cobj = ConfigurationProvider.getConfiguration();
        assertNotNull("The configuration object should not be null.",cobj);        
    }
    /**
     * test the method {@link ConfigurationProvider#retrieveConfigurationFromFile(String, String)}.
     * @throws Exception all exception throw to JUnit.
     */
    public void testGetConfiguration(){   
        ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE, ConfigurationProvider.DEFAULT_NAMESPACE);
        ConfigurationObject cobj = ConfigurationProvider.getConfiguration();
        assertNotNull("The configuration object should not be null.",cobj);    
    }
    
}
