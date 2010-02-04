/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.COOReportSerializerException;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;

/**
 *
 * <p>
 * Unit test case of {@link BaseCOOReportSerializer}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseCOOReportSerializerTest extends TestCase {
    /**
     * instance created for testing.
     */
    private BaseCOOReportSerializer instance;
    /**
     * configuration for testing.
     */
    private ConfigurationObject config = new DefaultConfigurationObject("default");

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {

        config.setPropertyValue("templateFilename", "test_files/XML Template.xml");
        instance = new XMLCOOReportSerializer(config);
    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
        TestHelper.deleteFile("COO_12.xml");
        config = null;
        instance = null;
    }

    /**
     * test constructor.
     *
     * @throws Exception to JUNIT.
     */
    public void testCtor() throws Exception {
        assertNotNull("fail to create instance.", instance);
    }

    /**
     * <p>
     * failure test for constructor. some configuration missing.
     * </p>
     * @throws Exception to JUNIT.
     */
    public void testCtor_fail() throws Exception {
        config.setPropertyValue("templateFilename", null);
        try {
            new XMLCOOReportSerializer(config);
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * accuracy test for <code>serializeCOOReportToFile()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testSerializeCOOReportToFile1() throws Exception {
        COOReport report = TestHelper.getCOOReport(12);
        instance.serializeCOOReportToFile(report, "test_files/report_test.xml");
    }

    /**
     * <p>
     * failure test for <code>serializeCOOReportToFile()</code> method.
     * </p>
     * <p>
     * using default file name.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testSerializeCOOReportToFile2() throws Exception {
        COOReport report = TestHelper.getCOOReport(12);
        instance.serializeCOOReportToFile(report, null);

    }

    /**
     * <p>
     * failure test for <code>serializeCOOReportToFile()</code> method.
     * </p>
     * <p>
     * file name can not be empty.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testSerializeCOOReportToFile_fail_1() throws Exception {
        COOReport report = TestHelper.getCOOReport(12);
        try {
            instance.serializeCOOReportToFile(report, " ");
            fail("IAE should throw");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>serializeCOOReportToFile()</code> method.
     * </p>
     * <p>
     * file is an directory.IAE should throw
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testSerializeCOOReportToFile_fail_2() throws Exception {
        COOReport report = TestHelper.getCOOReport(12);
        try {
            instance.serializeCOOReportToFile(report, "test_files");
            fail("COOReportSerializerException should throw");
        } catch (COOReportSerializerException e) {
            // OK
        }
    }

    /**
     * accuracy test for <code>getLogger()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testGetLogger() throws Exception {
        assertNotSame("should be null", null, instance.getLogger());
    }

    /**
     * accuracy test for <code>getTemplateFilename()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testGetTemplateFilename() throws Exception {
        assertSame("should be 'test_files/XML Template.xml'", "test_files/XML Template.xml",
            instance.getTemplateFilename());
    }

}
