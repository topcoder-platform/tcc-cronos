/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;

public class XMLCOOReportSerializerFailureTests {

    /**
     * Instance of DBComponentManager for testing.
     */
    private XMLCOOReportSerializer xmlCooReportSerializer;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        DefaultConfigurationObject configurationObject = new DefaultConfigurationObject("config");
        configurationObject.setPropertyValue("templateFilename", "template");
        xmlCooReportSerializer = new XMLCOOReportSerializer(configurationObject);
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