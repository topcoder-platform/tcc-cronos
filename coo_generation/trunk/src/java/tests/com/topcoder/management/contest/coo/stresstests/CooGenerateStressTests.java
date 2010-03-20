/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.stresstests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.COOReportGenerator;
import com.topcoder.management.contest.coo.COOReportSerializer;
import com.topcoder.management.contest.coo.ComponentManager;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;
import com.topcoder.management.contest.coo.impl.TestHelper;
import com.topcoder.management.contest.coo.impl.componentmanager.DBComponentManager;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;
import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.MessageFormat;

/**
 * Stress test cases for the component.<p/>
 *
 * This class tests the multiple thread situation for the component.
 *
 * @author yuanyeyuanye, DixonD
 * @version 1.1
 */
public class CooGenerateStressTests {

    /**
     * Test project id used by the test cases.
     */
    private static final int TEST_PROJECT_ID = StressTestHelper.TEST_PROJECT_ID;

    /**
     * Number of thread to run the stress test.
     */
    private static int numThread;

    /**
     * Number of times to execute the specific action.
     */
    private static int timesToExecute;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CooGenerateStressTests.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {
        numThread = 4;
        timesToExecute = 40;
        TestHelper.executeSqlFile("test_files/clean.sql");
        TestHelper.executeSqlFile("test_files/insert.sql");
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception if any error occurs
     */
    @After
    public void tearDown() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
    }

    /**
     * Creates multiple threads to run the stress tests.<p/>
     *
     * Stress test case for <code>COOReportGenerator#generateCOOReport(long projectId)</code>.
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void generateCOOReport() throws Exception {
        long startTime = System.currentTimeMillis();

        // create COOReportGenerator implementation
        final ConfigurationObject pdfSerializerConfig = TestHelper.getConfiguration("test_files/config.xml");
        final COOReportGenerator generator = new DefaultCOOReportGenerator(pdfSerializerConfig);

        Action action = new Action() {
            public void act() throws Exception {
                COOReport report = generator.generateCOOReport(TEST_PROJECT_ID);
                Assert.assertEquals(TEST_PROJECT_ID, report.getProjectId());
            }
        };

        runActionInMultipleThread(action, timesToExecute);

        outputTestResult(MessageFormat.format("Generate COOReport for {0} times in {1} thread, cost {2} milliseconds",
                numThread * timesToExecute,
                numThread, getCostTime(startTime)));
    }

    /**
     * Creates multiple threads to run the stress tests.<p/>
     *
     * Stress test case for <code>PDFCOOReportSerializer#serializeCOOReportToByteArray(COOReport report)</code>.
     *
     * @throws Exception if test fails.
     */
    @Test
    public void serializeCOOReportToPDFByteArray() throws Exception {
        long startTime = System.currentTimeMillis();

        // serialize the report to PDF
        // "Templates.xml" is used as the template
        ConfigurationObject pdfSerializerConfig =
                TestHelper.getConfiguration("test_files/serializer.xml");

        final COOReportSerializer pdfSerializer = new PDFCOOReportSerializer(pdfSerializerConfig);
        final COOReport report = getReport(TEST_PROJECT_ID);
        Action action = new Action() {
            public void act() throws Exception {
                // serialize to byte array
                pdfSerializer.serializeCOOReportToByteArray(report);
            }
        };

        runActionInMultipleThread(action, timesToExecute);

        outputTestResult(MessageFormat.format(
                "Serialize COOReport to PDF byte array for {0} times in {1} thread, cost {2} milliseconds",
                numThread * timesToExecute,
                numThread, getCostTime(startTime)));
    }

    /**
     * Creates multiple threads to run the stress tests.<p/>
     *
     * Stress test case for <code>XMLCOOReportSerializer#serializeCOOReportToByteArray(COOReport report)</code>.
     *
     * @throws Exception if test fails.
     */
    @Test
    public void serializeCOOReportToXMLByteArray() throws Exception {
        long startTime = System.currentTimeMillis();

        // serialize the report to XML
        // "XML template.xml" is used as the template
        ConfigurationObject xmlSerializerConfig =
                TestHelper.getConfiguration("test_files/serializer_2.xml");
        final COOReportSerializer xmlSerializer = new XMLCOOReportSerializer(xmlSerializerConfig);
        final COOReport report = getReport(TEST_PROJECT_ID);

        Action action = new Action() {
            public void act() throws Exception {
                xmlSerializer.serializeCOOReportToByteArray(report);
            }
        };

        runActionInMultipleThread(action, timesToExecute);

        outputTestResult(MessageFormat.format(
                "Serialize COOReport to XML byte array for {0} times in {1} thread, cost {2} milliseconds",
                numThread * timesToExecute,
                numThread, getCostTime(startTime)));
    }

    /**
     * Creates multiple threads to run the stress tests.<p/>
     *
     * Stress test case for <code>DBComponentManager#addComponents(String)</code>.
     *
     * @throws Exception if test fails.
     */
    @Test
    public void addComponents() throws Exception {
        numThread = 1;
        timesToExecute = 400;

        long startTime = System.currentTimeMillis();

        // exporting the third party list XML file into database
        // assume componentManagerConfig already exists
        ConfigurationObject componentManagerConfig = TestHelper.getConfiguration("test_files/componentManager.xml");

        final ComponentManager manager = new DBComponentManager(componentManagerConfig);

        Action action = new Action() {
            public void act() throws Exception {
                // add the list defined in "third_party_list.xls" to the database
                manager.addComponents("test_files/third_party_list.xls");

            }
        };

        runActionInMultipleThread(action, timesToExecute);

        outputTestResult(MessageFormat.format(
                "Add component to database for {0} times in {1} thread, cost {2} milliseconds",
                numThread * timesToExecute,
                numThread, getCostTime(startTime)));
    }


    /**
     * Writes the test result to file.
     *
     * @param content content to write.
     *
     * @throws IOException to caller.
     */
    private void outputTestResult(String content) throws IOException {
        File file = new File("test_files/stress_tests/result.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        String dateInfo = new Timestamp(System.currentTimeMillis()).toString();
        PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
        writer.println(dateInfo);
        writer.println(content);
        writer.flush();
        writer.close();
    }

    /**
     * Gets the cost time.
     *
     * @param startTime Start time.
     *
     * @return cost time.
     */
    private long getCostTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Run multiple thread data base logging.
     *
     * @param action Action to be executed.
     * @param times Times to execute the action.
     *
     * @throws InterruptedException If exception occurs.
     */
    private void runActionInMultipleThread(Action action, int times) throws InterruptedException {
        Thread[] threads = new Thread[numThread];
        for (int i = 0; i < threads.length; i++) {
            threads[i] =
                    new Thread(
                            new ActionThreadRunner(
                                    action, times));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    /**
     * Gets the instance of <code>COOReport</code> for test.
     *
     * @param projectId Id of the project.
     * @return Instance of <code>COOReport</code>.
     *
     * @throws Exception to caller directly.
     */
    private COOReport getReport(int projectId) throws Exception {
        // create COOReportGenerator implementation
        // assume configuration already exists
        ConfigurationObject pdfSerializerConfig = TestHelper.getConfiguration("test_files/config.xml");
        COOReportGenerator generator = new DefaultCOOReportGenerator(pdfSerializerConfig);

        return generator.generateCOOReport(projectId);
    }
}