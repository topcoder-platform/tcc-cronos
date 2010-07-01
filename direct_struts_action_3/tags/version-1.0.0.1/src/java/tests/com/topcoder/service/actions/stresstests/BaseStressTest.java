/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.actions.stresstests;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;

/**
 * <p> Base class for Stress test cases. </p>
 *
 * <p> This class tests the multiple thread situation for the component. </p>
 *
 * @author yuanyeyuanye, TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class BaseStressTest extends StrutsSpringTestCase {

    /**
     * Number of thread.
     */
    protected static int numThreads;

    /**
     * Number of thread.
     */
    protected static int timesToExecute;

    /**
     * <p> Sets up test environment. </p>
     *
     * @throws Exception to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        numThreads = 5;
        timesToExecute = 5;
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to Junit.
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Makes sure that beans are only loaded from spring one time in order to prevent any memory leaks and speed up unit
     * testing.
     *
     * @throws Exception to JUnit
     */
    @Override
    protected void setupBeforeInitDispatcher() throws Exception {
        // only load beans from spring once
        if (applicationContext == null) {
            applicationContext = new GenericXmlContextLoader().loadContext(getContextLocations());
        }

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
    }

    /**
     * Gets the log message.
     *
     * @param startTime      time to start.
     * @param numThreads     number of threads to perform the actions.
     * @param timesToExecute times to execute the actions.
     *
     * @return Log message.
     */
    protected String getLogMessage(long startTime, int numThreads, int timesToExecute) {
        return MessageFormat.format("Perform operations {0} times in {1} thread, cost {2} milliseconds\n",
                                    numThreads * timesToExecute, numThreads, getCostTime(startTime));
    }


    /**
     * Writes the test result to file.
     *
     * @param content content to write.
     *
     * @throws java.io.IOException to caller.
     */
    protected void outputTestResult(String content) throws IOException {
        File file = new File("test_files/stress/result.log");
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
     * <p> Gets the cost time. </p>
     *
     * @param startTime Start time.
     *
     * @return cost time.
     */
    private long getCostTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * <p> Run action with multiple thread. </p>
     *
     * @param action Action to be executed.
     * @param times  Times to execute the action.
     *
     * @throws Exception to JUnit.
     * @throws Error     If any error occurs.
     */
    protected void runActionForMultipleTimes(Action action, int times) throws Exception {
        for (int i = 0; i < times; i++) {
            action.act();
        }
    }
}


