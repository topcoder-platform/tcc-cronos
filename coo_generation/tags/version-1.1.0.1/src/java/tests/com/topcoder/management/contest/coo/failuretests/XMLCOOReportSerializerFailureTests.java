/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;

public class XMLCOOReportSerializerFailureTests {

    /**
     * Instance of DBComponentManager for testing.
     */
    private XMLCOOReportSerializer xmlCooReportSerializer;

    /**
     * Setting up environment.
     * @throws Exception if any error
     */
    @Before
    public void setUp() throws Exception {
        ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/template.xml");
        config.setPropertyValue("defaultOutputFilename", "test_files/output.xml");
        kk.addChild(config);
        xmlCooReportSerializer = new XMLCOOReportSerializer(kk);
    }

    /**
     * Method under test
     * XMLCOOReportSerializer.XMLCOOReportSerializer(ConfigurationObject).
     * Failure testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testXMLCOOReportSerializerFailureNull() throws Exception {
        try {
            new XMLCOOReportSerializer(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * XMLCOOReportSerializer.serializeCOOReportToByteArray(COOReport). Failure
     * testing of exception in case report is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSerializeCOOReportToByteArrayFailureNull() throws Exception {
        try {
            xmlCooReportSerializer.serializeCOOReportToByteArray(null);
            Assert.fail("IllegalArgumentException is expected since report is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}