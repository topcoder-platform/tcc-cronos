/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.COOReportSerializerException;
import com.topcoder.management.contest.coo.impl.BaseCOOReportSerializer;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;

/**
 * <p>
 * Set of failure tests for BaseCOOReportSerializer,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class BaseCOOReportSerializerFailureTests {

    /**
     * Instance of BaseCOOReportSerializer for testing.
     */
    private BaseCOOReportSerializer baseCOOReportSerializer;

    /**
     * Setting up environment.
     * @throws Exception if any error
     */
    @Before
    public void setUp() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("default");
        ConfigurationObject tmp = new DefaultConfigurationObject("default");
        tmp.addChild(config);
        config.setPropertyValue("templateFilename", "test_files/XML Template.xml");
        baseCOOReportSerializer = new XMLCOOReportSerializer(tmp);
    }

    /**
     * Method under test
     * BaseCOOReportSerializer.BaseCOOReportSerializer(ConfigurationObject).
     * Failure testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testBaseCOOReportSerializerFailureNull() throws Exception {
        try {
            new BaseCOOReportSerializer(null) {

                public byte[] serializeCOOReportToByteArray(COOReport report)
                    throws COOReportSerializerException {
                    return null;
                }

            };
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * BaseCOOReportSerializer.serializeCOOReportToFile(COOReport, String).
     * Failure testing of exception in case report is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSerializeCOOReportToFileFailureReportNull() throws Exception {
        try {
            baseCOOReportSerializer.serializeCOOReportToFile(null, "filename");
            Assert.fail("IllegalArgumentException is expected since report is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * BaseCOOReportSerializer.serializeCOOReportToFile(COOReport, String).
     * Failure testing of exception in case filename is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSerializeCOOReportToFileFailureFilenameEmpty() throws Exception {
        try {
            baseCOOReportSerializer.serializeCOOReportToFile(new COOReport(), "");
            Assert.fail("IllegalArgumentException is expected since filename is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * BaseCOOReportSerializer.serializeCOOReportToFile(COOReport, String).
     * Failure testing of exception in case filename is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSerializeCOOReportToFileFailureFilenameEmptyTrimmed() throws Exception {
        try {
            baseCOOReportSerializer.serializeCOOReportToFile(new COOReport(), "     ");
            Assert.fail("IllegalArgumentException is expected since filename is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}