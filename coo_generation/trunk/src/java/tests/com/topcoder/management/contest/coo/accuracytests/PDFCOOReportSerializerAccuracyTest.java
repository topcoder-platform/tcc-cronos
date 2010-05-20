/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.impl.TestHelper;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;
/**
 *
 * <p>
 * Accuracy test case of {@link PDFCOOReportSerializer} for version 1.1.
 * </p>
 *
 * @author Beijing2008
 * @version 1.1
 */
public class PDFCOOReportSerializerAccuracyTest extends TestCase {

    /**
     * Represents the PDFCOOReportSerializer to test.
     */
    private PDFCOOReportSerializer instance;

    /**
     * If you want to check the output manually, you need to set this to false.
     */
    private boolean deleteOutput = false;

    /**
     * <p>
     * Set up test environment.
     * </p>
     *
     * @throws Exception to junit.
     */
    protected void setUp() throws Exception {
        ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/accuracy/WordTemplate.dot");
        config.setPropertyValue("format", "WORD");
        kk.addChild(config);
        instance = new PDFCOOReportSerializer(kk);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to junit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile() throws Exception {
        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDesignProjectId("12345");
        report.getContestData().setDevelopmentProjectId("67890");
        instance.serializeCOOReportToFile(report, null);
        String expectFilePath = "COO_12345_67890_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".rtf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method with no dev id.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile2() throws Exception {
        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDesignProjectId("12345");
        instance.serializeCOOReportToFile(report, null);
        String expectFilePath = "COO_12345_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".rtf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method with no des id.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile3() throws Exception {
        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDevelopmentProjectId("67890");
        instance.serializeCOOReportToFile(report, null);
        String expectFilePath = "COO_67890_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".rtf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method for pdf output.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile_pdf4() throws Exception {
        ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/accuracy/WordTemplate.dot");
        config.setPropertyValue("format", "PDF");
        kk.addChild(config);
        instance = new PDFCOOReportSerializer(kk);


        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDesignProjectId("12345");
        report.getContestData().setDevelopmentProjectId("67890");
        instance.serializeCOOReportToFile(report, null);
        String expectFilePath = "COO_12345_67890_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".pdf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method with no dev id.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile_pdf5() throws Exception {
        ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/accuracy/WordTemplate.dot");
        config.setPropertyValue("format", "PDF");
        kk.addChild(config);
        instance = new PDFCOOReportSerializer(kk);

        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDesignProjectId("12345");
        instance.serializeCOOReportToFile(report, null);
        String expectFilePath = "COO_12345_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".pdf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method with no des id.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile_pdf6() throws Exception {
        ConfigurationObject kk = new DefaultConfigurationObject("default");
        ConfigurationObject config = new DefaultConfigurationObject("default");
        config.setPropertyValue("templateFilename", "test_files/accuracy/WordTemplate.dot");
        config.setPropertyValue("format", "PDF");
        kk.addChild(config);
        instance = new PDFCOOReportSerializer(kk);

        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDevelopmentProjectId("67890");
        instance.serializeCOOReportToFile(report, null);
        String expectFilePath = "COO_67890_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".pdf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }

    /**
     * <p>
     * Accuracy test for <code>serializeCOOReportToByteArray()</code> method with custom file path.
     * Expects the generated file exists.
     * The file content will be checked manually.
     * </p>
     *
     * @throws Exception to junit.
     */

    public void test_serializeCOOReportToFile_custon_outputFile() throws Exception {
        String output = "test_files/accuracy/test_serializeCOOReportToFile_custon_outputFile.pdf";
        COOReport report = TestHelper.getCOOReport(1);
        report.getContestData().setDevelopmentProjectId("67890");
        instance.serializeCOOReportToFile(report, output);
        //the postfix should be changed to pdf, the generation of configured option only works for the ctor
        String expectFilePath = "test_files/accuracy/test_serializeCOOReportToFile_custon_outputFile.pdf";
        assertTrue("The file should exist.", new File(expectFilePath).isFile());
        if (deleteOutput) {
            new File(expectFilePath).delete();
        }
    }
}
