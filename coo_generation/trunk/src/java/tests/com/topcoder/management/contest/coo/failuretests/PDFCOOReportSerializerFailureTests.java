/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;

public class PDFCOOReportSerializerFailureTests {

    /**
     * Instance of DBComponentManager for testing.
     */
    private PDFCOOReportSerializer pdfCooReportSerializer;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/WordTemplate.dot");
        config.setPropertyValue("format", "WORD");
        kk.addChild(config);
        pdfCooReportSerializer = new PDFCOOReportSerializer(kk);
    }

    /**
     * Method under test
     * PDFCOOReportSerializer.PDFCOOReportSerializer(ConfigurationObject).
     * Failure testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testPDFCOOReportSerializerFailureNull() throws Exception {
        try {
            new PDFCOOReportSerializer(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * PDFCOOReportSerializer.serializeCOOReportToByteArray(COOReport). Failure
     * testing of exception in case report is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSerializeCOOReportToByteArrayFailureNull() throws Exception {
        try {
            pdfCooReportSerializer.serializeCOOReportToByteArray(null);
            Assert.fail("IllegalArgumentException is expected since report is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}