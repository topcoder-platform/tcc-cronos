/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.InvalidConfigurationException;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.XMLFilePersistence;

import java.io.File;
import java.io.IOException;


/**
 * Class to load configuration object from file.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
@SuppressWarnings("serial")
public class FileConfigurationObject extends DefaultConfigurationObject {
    /**
     * Creates configuration object from file.
     *
     * @param name the name of configuration object.
     * @param file the file to load configuration.
     *
     * @throws ConfigurationParserException if any error while parsing file.
     * @throws IOException if any error while reading file.
     * @throws InvalidConfigurationException if the configuration is invalid.
     * @throws ConfigurationAccessException if any error while access the configuration.
     */
    public FileConfigurationObject(String name, String file)
        throws ConfigurationParserException, IOException, InvalidConfigurationException, ConfigurationAccessException {
        super(name);

        // build the XMLFilePersistence
        XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();

        // load the ConfigurationObject from the input file
        ConfigurationObject configuration = xmlFilePersistence.loadFile(name, new File(file));

        super.addChild(configuration.getChild("default"));
    }
}
