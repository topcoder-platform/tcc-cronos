/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.io.File;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

/**
 * <p>
 * mock class for configuration object used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockConfigurationObject extends DefaultConfigurationObject {
    /**
     * Constructor with the name of the MockConfigurationObject.
     *
     * @param name the name of this ConfigurationObject.
     * @throws Exception if any error occur.
     */
    public MockConfigurationObject(String name) throws Exception {
        super(name);
        super.setPropertyValue("loggerName", "db_log");
        super.setPropertyValue("connectionName", "informix_connection");

        // build the XMLFilePersistence
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        // load the ConfigurationObject from the input file
        ConfigurationObject configuration = xmlFilePersistence.loadFile("manager",
            new File("test_files/componentManager.xml"));

        super.addChild(configuration.getChild("default"));
    }
}
