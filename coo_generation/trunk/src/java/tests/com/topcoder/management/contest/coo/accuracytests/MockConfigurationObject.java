/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

import java.io.File;


/**
 * Mock configuration.
 *
 * @author myxgyy
 * @version 1.0
  */
@SuppressWarnings("serial")
public class MockConfigurationObject extends DefaultConfigurationObject {
/**
     * Constructor.
     *
     * @param name the name of this ConfigurationObject.
     * @throws Exception to JUnit.
     */
    public MockConfigurationObject(String name) throws Exception {
        super(name);
        super.setPropertyValue("loggerName", "db_log");
        super.setPropertyValue("connectionName", "informix_connection");

        // build the XMLFilePersistence
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        // load the ConfigurationObject from the input file
        ConfigurationObject configuration = xmlFilePersistence
            .loadFile("manager", new File(BaseTestCase.ACCURACY + "Config.xml"));

        super.addChild(configuration.getChild("default"));
    }
}
