/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.serializer;

import java.io.FileOutputStream;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.impl.TestHelper;

/**
 *
 * <p>
 * Unit test case of {@link XMLCOOReportSerializer}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XMLCOOReportSerializerTest extends TestCase {

    /**
     * create instance for testing.
     */
    private XMLCOOReportSerializer instance;

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
        TestHelper.executeSqlFile("test_files/insert.sql");
    	ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/template.xml");
        config.setPropertyValue("defaultOutputFilename", "test_files/output.xml");
        kk.addChild(config);
        instance = new XMLCOOReportSerializer(kk);

    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
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
     * accuracy test for <code>serializeCOOReportToByteArray()</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */

    public void testSerializeCOOReportToByteArrayForXML() throws Exception {

        COOReport report = TestHelper.getCOOReport(1);
        byte[] array = instance.serializeCOOReportToByteArray(report);
        FileOutputStream out = new FileOutputStream("test_files/output.xml");
        out.write(array);
        out.flush();
        out.close();

    }
}
