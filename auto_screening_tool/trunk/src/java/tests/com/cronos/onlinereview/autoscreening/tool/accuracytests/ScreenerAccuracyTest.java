/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests;

import java.sql.ResultSet;

import com.cronos.onlinereview.autoscreening.tool.Screener;
import com.cronos.onlinereview.autoscreening.tool.BaseTestCase;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * Accuracy test cases for <code>Screener</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScreenerAccuracyTest extends BaseTestCase {

    /**
     * Represents the path of the screener config file.
     */
    protected static final String DEFAULT_SCREENER_CONFIG_FILE = "accuracytests/screener.xml";

    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE_ONE = "accuracytests/screener1.xml";

    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE_TWO = "accuracytests/screener2.xml";

    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE_THREE = "accuracytests/screener3.xml";

    /**
     * Represents the path of the object factory config file.
     */
    protected static final String OBJECT_FACTORY_CONFIG_FILE = "accuracytests/object_factory.xml";

    /**
     * Represents the config namespace of the screener.
     */
    protected static final String SCREENER_NAMESPACE = "com.cronos.autoscreening";

    /**
     * Screener instance for test.
     */
    private Screener screener;

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreenerAccuracyTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager.getInstance().add(OBJECT_FACTORY_CONFIG_FILE);
        ConfigManager.getInstance().add(DEFAULT_SCREENER_CONFIG_FILE);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * An instance of Screener should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        screener = new Screener(222, SCREENER_NAMESPACE);

        assertNotNull("Screener's constructor failed.", screener);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * An instance of Screener should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor2() throws Exception {
        // add another config file
        ConfigManager.getInstance().removeNamespace(SCREENER_NAMESPACE);
        ConfigManager.getInstance().add(SCREENER_CONFIG_FILE_ONE);

        screener = new Screener(222, SCREENER_NAMESPACE);

        assertNotNull("Screener's constructor failed.", screener);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * An instance of Screener should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor3() throws Exception {
        // add another config file
        ConfigManager.getInstance().removeNamespace(SCREENER_NAMESPACE);
        ConfigManager.getInstance().add(SCREENER_CONFIG_FILE_TWO);

        screener = new Screener(222, SCREENER_NAMESPACE);

        assertNotNull("Screener's constructor failed.", screener);
    }

    /**
     * <p>
     * Accuracy test of the method <code>void initialize()</code>.
     * </p>
     * <p>
     * the tasks with status 'screening' of this screener will be rollbacked to
     * 'pending'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyInitialize() throws Exception {
        screener = new Screener(2, SCREENER_NAMESPACE);

        int count = doScalarQuery("SELECT COUNT(*) FROM screening_task WHERE screener_id=2",
            new Object[0]);
        assertEquals("check # of task of screener id 2 before init", 1, count);

        screener.initialize();

        count = doScalarQuery("SELECT COUNT(*) FROM screening_task WHERE screener_id=2",
            new Object[0]);
        assertEquals("check # of task of screener id 2 after init", 0, count);

        // after screener 2 does not change
        count = doScalarQuery("SELECT COUNT(*) FROM screening_task WHERE screener_id=1",
            new Object[0]);
        assertEquals("check # of task of screener id 1 before init", 1, count);
    }

    /**
     * <p>
     * Accuracy test of the method <code>void screen()</code>.
     * </p>
     * <p>
     * perform the screening. The tester should check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        Screener screener = new Screener(123, SCREENER_NAMESPACE);

        screener.screen();

        // have a look at the database
        ResultSet resultSet = doSQLQuery("SELECT * FROM screening_result", new Object[] {});
        int count = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i < count; ++i) {
                System.out.print(resultSet.getObject(i));
                System.out.print('\t');
            }
            System.out.println();
        }
        resultSet.close();
    }

    /**
     * <p>
     * Accuracy test of the method <code>void screen()</code>.
     * </p>
     * <p>
     * perform the screening. The tester should check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen2() throws Exception {
        // add another config file
        ConfigManager.getInstance().removeNamespace(SCREENER_NAMESPACE);
        ConfigManager.getInstance().add(SCREENER_CONFIG_FILE_THREE);

        Screener screener = new Screener(123, SCREENER_NAMESPACE);

        screener.screen();

        // have a look at the database
        ResultSet resultSet = doSQLQuery("SELECT * FROM screening_result", new Object[] {});
        int count = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i < count; ++i) {
                System.out.print(resultSet.getObject(i));
                System.out.print('\t');
            }
            System.out.println();
        }
        resultSet.close();
    }
}
