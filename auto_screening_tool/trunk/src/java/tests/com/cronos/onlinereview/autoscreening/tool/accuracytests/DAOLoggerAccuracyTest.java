/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests;

import java.sql.ResultSet;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.BaseTestCase;
import com.cronos.onlinereview.autoscreening.tool.ResponseLevel;
import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningLogic;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponseLogger;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.logger.DAOLogger;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixResponseDAO;
import com.cronos.onlinereview.autoscreening.tool.rules.ComponentSpecificationRule;

import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Accuracy test cases for <code>DAOLogger</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DAOLoggerAccuracyTest extends BaseTestCase {

    /**
     * Represents the name of the id generator.
     */
    private static final String ID_GENERATOR_NAME = "screening_result_id_seq";

    /**
     * Represents an instance of InformixResponseDAO, whose methods are going to
     * be tested.
     */
    private InformixResponseDAO responseDAO;

    /**
     * Represents an instance of IDGenerator, used to create an instance of
     * InformixResponseDAO.
     */
    private IDGenerator idGenerator;

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DAOLoggerAccuracyTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        idGenerator = IDGeneratorFactory.getIDGenerator(ID_GENERATOR_NAME);
        responseDAO = new InformixResponseDAO(getConnectionFactory(), idGenerator);
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
     * <code>DAOLogger(ScreeningResponseDAO screeningResponseDAO)</code>.
     * </p>
     * <p>
     * An instance of DAOLogger should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor() throws Exception {
        ScreeningResponseLogger logger = new DAOLogger(responseDAO);

        assertNotNull("check the instance", logger);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
     * RuleResult[] ruleResult)</code>.
     * </p>
     * <p>
     * the response is logged into database correctly.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLogResponse1() throws Exception {
        ScreeningResponseLogger logger = new DAOLogger(responseDAO);

        logger.setOperator("operator1");

        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);

        ScreeningLogic screeningLogic = new ScreeningLogic(new ComponentSpecificationRule(),
            ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL, 15, 1, 1);

        RuleResult ruleResult = new RuleResult(new Exception());

        logger.logResponse(screeningTask, screeningLogic, new RuleResult[] {ruleResult});

        assertEquals("check result", 1, doScalarQuery("SELECT * FROM screening_result",
            new Object[] {}));

        printResult("SELECT * FROM screening_result");
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
     * RuleResult[] ruleResult)</code>.
     * </p>
     * <p>
     * the response is logged into database correctly.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLogResponse2() throws Exception {
        ScreeningResponseLogger logger = new DAOLogger(responseDAO);

        logger.setOperator("operator2");

        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);

        ScreeningLogic screeningLogic = new ScreeningLogic(new ComponentSpecificationRule(),
            ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL, 15, 1, 1);

        RuleResult ruleResult = new RuleResult(true, "OK, passed.");

        logger.logResponse(screeningTask, screeningLogic, new RuleResult[] {ruleResult});

        assertEquals("check result", 1, doScalarQuery("SELECT * FROM screening_result",
            new Object[] {}));

        printResult("SELECT * FROM screening_result");
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
     * RuleResult[] ruleResult)</code>.
     * </p>
     * <p>
     * the response is logged into database correctly.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLogResponse3() throws Exception {
        ScreeningResponseLogger logger = new DAOLogger(responseDAO);

        logger.setOperator("operator3");

        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);

        ScreeningLogic screeningLogic = new ScreeningLogic(new ComponentSpecificationRule(),
            ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL, 15, 1, 1);

        RuleResult ruleResult = new RuleResult(false, "Failed, not passed.");

        logger.logResponse(screeningTask, screeningLogic, new RuleResult[] {ruleResult});

        assertEquals("check result", 1, doScalarQuery("SELECT * FROM screening_result",
            new Object[] {}));

        printResult("SELECT * FROM screening_result");
    }

    /**
     * Print the result to outputConsole.
     *
     * @param sqlString the command to get the data from database.
     * @throws Exception to JUnit.
     */
    private void printResult(String sqlString) throws Exception {
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
