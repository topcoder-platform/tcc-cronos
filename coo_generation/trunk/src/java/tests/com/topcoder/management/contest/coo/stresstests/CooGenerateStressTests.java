/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.contest.coo.stresstests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;

import junit.framework.TestCase;

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

/**
 * <p>
 * Stress test cases for the component.
 * </p>
 *
 * <p>
 * This class tests the multiple thread situation for the component.
 * </p>
 *
 * @author yuanyeyuanye
 * @version 1.0
 */
public class CooGenerateStressTests extends TestCase {

    /**
     * <p>
     * Test project id used by the test cases.
     * </p>
     */
    private static final int TEST_PROJECT_ID = StressTestHelper.TEST_PROJECT_ID;

    /**
     * Number of thread to run the stress test.
     */
    private static int NUM_THREAD;

    /**
     * Number of times to execute the specific action.
     */
    private static int TIME_TO_EXECUTE;

    /**
     * Sets up the test environment.
     */
    @Override
    protected void setUp() throws Exception {
        NUM_THREAD = 4;
        TIME_TO_EXECUTE = 40;
        TestHelper.executeSqlFile("test_files/clean.sql");
        TestHelper.executeSqlFile("test_files/insert.sql");
    }

    /**
     * Tears down the test environment.
     */
    @Override
    protected void tearDown() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
    }

    /**
     * <p>
     * Creates multiple threads to run the stress tests.
     * </p>
     *
     * <p>
     * Stress test case for <code>COOReportGenerator#generateCOOReport(long projectId)</code>.
     * </p>
     *
     * @throws Exception if test fails.
     */
    public void testGenerateCOOReport() throws Exception {
        long startTime = System.currentTimeMillis();

        // create COOReportGenerator implementation
        final ConfigurationObject pdfSerializerConfig = TestHelper.getConfiguration("test_files/config.xml");
        final COOReportGenerator generator = new DefaultCOOReportGenerator(pdfSerializerConfig);

        Action action = new Action() {
            public void act() throws Exception {
                COOReport report = generator.generateCOOReport(TEST_PROJECT_ID);
                assertEquals(TEST_PROJECT_ID, report.getProjectId());
            }
        };

        runActionInMultipleThread(action, TIME_TO_EXECUTE);

        outputTestResult(MessageFormat.format("Generate COOReport for {0} times in {1} thread, cost {2} milliseconds",
                NUM_THREAD * TIME_TO_EXECUTE,
                NUM_THREAD, getCostTime(startTime)));
    }

    /**
     * <p>
     * Creates multiple threads to run the stress tests.
     * </p>
     *
     * <p>
     * Stress test case for <code>PDFCOOReportSerializer#serializeCOOReportToByteArray(COOReport report)</code>.
     * </p>
     *
     * @throws Exception if test fails.
     */
    public void testSerializeCOOReportToPDFByteArray() throws Exception {
        long startTime = System.currentTimeMillis();

        // serialize the report to PDF
        // "Templates.xml" is used as the template
        ConfigurationObject pdfSerializerConfig =
                TestHelper.getConfiguration("test_files/serializer.xml").getChild("default");

        final COOReportSerializer pdfSerializer = new PDFCOOReportSerializer(pdfSerializerConfig);
        final COOReport report = getReport(TEST_PROJECT_ID);
        Action action = new Action() {
            public void act() throws Exception {
                // serialize to byte array
                pdfSerializer.serializeCOOReportToByteArray(report);
            }
        };

        runActionInMultipleThread(action, TIME_TO_EXECUTE);

        outputTestResult(MessageFormat.format(
                "Serialize COOReport to PDF byte array for {0} times in {1} thread, cost {2} milliseconds",
                NUM_THREAD * TIME_TO_EXECUTE,
                NUM_THREAD, getCostTime(startTime)));
    }

    /**
     * <p>
     * Creates multiple threads to run the stress tests.
     * </p>
     *
     * <p>
     * Stress test case for <code>XMLCOOReportSerializer#serializeCOOReportToByteArray(COOReport report)</code>.
     * </p>
     *
     * @throws Exception if test fails.
     */
    public void testSerializeCOOReportToXMLByteArray() throws Exception {
        long startTime = System.currentTimeMillis();

        // serialize the report to XML
        // "XML template.xml" is used as the template
        ConfigurationObject xmlSerializerConfig =
                TestHelper.getConfiguration("test_files/serializer_2.xml").getChild("default");
        final COOReportSerializer xmlSerializer = new XMLCOOReportSerializer(xmlSerializerConfig);
        final COOReport report = getReport(TEST_PROJECT_ID);

        Action action = new Action() {
            public void act() throws Exception {
                xmlSerializer.serializeCOOReportToByteArray(report);
            }
        };

        runActionInMultipleThread(action, TIME_TO_EXECUTE);

        outputTestResult(MessageFormat.format(
                "Serialize COOReport to XML byte array for {0} times in {1} thread, cost {2} milliseconds",
                NUM_THREAD * TIME_TO_EXECUTE,
                NUM_THREAD, getCostTime(startTime)));
    }

    /**
     * <p>
     * Creates multiple threads to run the stress tests.
     * </p>
     *
     * <p>
     * Stress test case for <code>DBComponentManager#addComponents(String)</code>.
     * </p>
     *
     * @throws Exception if test fails.
     */
    public void testAddComponents() throws Exception {
        NUM_THREAD = 1;
        TIME_TO_EXECUTE = 400;

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

        runActionInMultipleThread(action, TIME_TO_EXECUTE);

        outputTestResult(MessageFormat.format(
                "Add component to database for {0} times in {1} thread, cost {2} milliseconds",
                NUM_THREAD * TIME_TO_EXECUTE,
                NUM_THREAD, getCostTime(startTime)));
    }


    /**
     * Writes the test result to file.
     *
     * @param content content to write.
     *
     * @throws IOException to caller.
     */
    private void outputTestResult(String content) throws IOException {
        File file = new File("test_files/stress/result.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        String dateInfo = new Timestamp(System.currentTimeMillis()).toString();
        FileOutputStream stream = new FileOutputStream(file, true);
        stream.write(dateInfo.getBytes());
        stream.write("\n".getBytes());
        stream.write(content.getBytes());
        stream.write("\n".getBytes());
        stream.flush();
        stream.close();
    }

    /**
     * <p>
     * Gets the cost time.
     * </p>
     *
     * @param startTime Start time.
     *
     * @return cost time.
     */
    private long getCostTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * <p>
     * Run multiple thread data base logging.
     * </p>
     *
     * @param action Action to be executed.
     * @param times Times to execute the action.
     *
     * @throws InterruptedException If exception occurs.
     */
    private void runActionInMultipleThread(Action action, int times) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREAD];
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
     * <p>
     * Gets the instance of <code>COOReport</code> for test.
     * </p>
     *
     * @param projectId Id of the project.
     *
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


