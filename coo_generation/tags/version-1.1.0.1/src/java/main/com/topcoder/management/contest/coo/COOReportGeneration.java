/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.io.File;
import java.io.IOException;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.XMLFilePersistence;

/**
 * <p>
 * Class with main method to provide command line usage of
 * class DefaultCOOReportGenerator.
 * </p>
 * <p>
 * Here is the Sample Usage:
 * COOReportGeneration projectId.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>It is mutable so not thread-safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class COOReportGeneration {

    /** Configuration file. */
    private static final String CONFIG = "config.xml";

    /** Serialize configuration file. */
    private static final String SERIALIZE_CONFIG = "serializer.xml";

    /**
     * Main method to provide command line usage.
     * @param args as the command line parameters
     * @throws Exception if any error occurs.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: COOReportGeneration <projectId>.");
            return;
        }
        long projectId;
        try {
            projectId = Long.parseLong(args[0]);

            ConfigurationObject pdfSerializerConfig = getConfiguration(CONFIG);
            COOReportGenerator generator = new DefaultCOOReportGenerator(pdfSerializerConfig);
            COOReport report = generator.generateCOOReport(projectId);

            pdfSerializerConfig = getConfiguration(SERIALIZE_CONFIG);
            COOReportSerializer pdfSerializer = new PDFCOOReportSerializer(pdfSerializerConfig);
            pdfSerializer.serializeCOOReportToFile(report, null);

        } catch (NumberFormatException e) {
            System.out.println("<projectId> must be integer value.");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

    /**
     * create configuration file from file.
     *
     * @param file the configuration file name.
     * @return the configuration object from xml file.
     * @throws Exception to JUnit.
     */
    public static ConfigurationObject getConfiguration(String file) throws Exception {
        // load the ConfigurationObject from the input file
        ConfigurationObject cfgObject = null;
        try {
            // build the XMLFilePersistence
            XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();
            cfgObject = xmlFilePersistence.loadFile("test", new File(file));
        } catch (ConfigurationParserException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        }
        return cfgObject;
    }
}
