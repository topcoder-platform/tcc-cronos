/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.serializer;

import java.io.File;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.impl.TestHelper;
/**
 *
 * <p>
 * Unit test case of {@link PDFCOOReportSerializer}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PDFCOOReportSerializerTest extends TestCase {

    /**
     * create instance for testing.
     */
    private PDFCOOReportSerializer instance;

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
        config.setPropertyValue("templateFilename", "test_files/WordTemplate.dot");
        config.setPropertyValue("format", "WORD");
 //       config.setPropertyValue("defaultOutputFilename", "test_files/kk.pdf");
        kk.addChild(config);
        instance = new PDFCOOReportSerializer(kk);
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

    public void testSerializeCOOReportToByteArrayForPDF_RTF() throws Exception {

        COOReport report = TestHelper.getCOOReport(1);
        byte[] array = instance.serializeCOOReportToByteArray(report);
        FileOutputStream out = new FileOutputStream("test_files/new.rtf");
        out.write(array);
        out.flush();
        out.close();
        assertTrue(new File("test_files/new.rtf").exists());
    }

    /**
     * <p>
     * accuracy test for <code>serializeCOOReportToByteArray()</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */

    public void testSerializeCOOReportToByteArrayForPDF_PDF() throws Exception {

    	ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/WordTemplate.dot");
        config.setPropertyValue("format", "PDF");
        config.setPropertyValue("language", "Java");
 //       config.setPropertyValue("defaultOutputFilename", "test_files/kk.pdf");
        kk.addChild(config);
        PDFCOOReportSerializer instance_PDF = new PDFCOOReportSerializer(kk);
        COOReport report = TestHelper.getCOOReport(1);
        byte[] array = instance_PDF.serializeCOOReportToByteArray(report);
        FileOutputStream out = new FileOutputStream("test_files/new.pdf");
        out.write(array);
        out.flush();
        out.close();
        assertTrue(new File("test_files/new.pdf").exists());
    }
}
