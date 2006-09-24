/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.logger.DAOLogger;
import com.cronos.onlinereview.autoscreening.tool.rules.ComponentSpecificationRule;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * * The unit test cases for class DAOLogger.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DAOLoggerTest extends BaseTestCase {

	/**
	 * Represents the name of the id generator.
	 */
	private static final String ID_GENERATOR_NAME = "screening_result_id_seq";

	/**
	 * Represents an instance of InformixResponseDAO, whose methods are going to
	 * be tested.
	 */
	private MockResponseDAO responseDAO;

	/**
	 * Represents an instance of IDGenerator, used to create an instance of
	 * InformixResponseDAO.
	 */
	private IDGenerator idGenerator;

	/**
	 * Aggregates all tests in this class.
	 * 
	 * @return test suite aggregating all tests.
	 */
	public static Test suite() {
		return new TestSuite(DAOLoggerTest.class);
	}

	/**
	 * Sets up the test environment.
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	protected void setUp() throws Exception {
		super.setUp();

		idGenerator = IDGeneratorFactory.getIDGenerator(ID_GENERATOR_NAME);
		responseDAO = new MockResponseDAO(getConnectionFactory(), idGenerator);
	}

	/**
	 * Clean up the test environment.
	 * 
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
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracyCtor1() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		assertNotNull("check the instance", logger);
	}

	/**
	 * <p>
	 * Failure test of the constructor
	 * <code>DAOLogger(ScreeningResponseDAO screeningResponseDAO)</code>.
	 * </p>
	 * <p>
	 * screeningResponseDAO is null. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureCtor1() throws Exception {

		try {
			new DAOLogger(null);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message",
					"screeningResponseDAO should not be null", e.getMessage());
		}
	}

	/**
	 * <p>
	 * Accuracy test of the method <code>setOperator(String operator)</code>
	 * and <code>String getOperator()</code>
	 * </p>
	 * <p>
	 * the operator should be set.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracySetOperatorAndGetOperator1() throws Exception {
		DAOLogger logger = new DAOLogger(responseDAO);
		logger.setOperator("operator1");

		assertEquals("check selected task", "operator1", logger.getOperator());
	}

	/**
	 * <p>
	 * Failure test of the method <code>setOperator(String operator)</code>.
	 * </p>
	 * <p>
	 * operator is null. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureSetOperator1() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.setOperator(null);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message", "operator should not be null.", e
					.getMessage());
		}
	}

	/**
	 * <p>
	 * Failure test of the method <code>setOperator(String operator)</code>.
	 * </p>
	 * <p>
	 * operator is empty. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureSetOperator2() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.setOperator("    ");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message",
					"operator should not be empty (trimmed).", e.getMessage());
		}
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
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracyLogResponse1() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		logger.setOperator("operator1");

		ScreeningTask screeningTask = new ScreeningTask();
		screeningTask.setId(1);

		ScreeningLogic screeningLogic = new ScreeningLogic(
				new ComponentSpecificationRule(), ResponseLevel.PASS,
				ResponseLevel.WARN, ResponseLevel.FAIL, 15, 1, 1);

		RuleResult ruleResult = new RuleResult(new Exception());

		logger.logResponse(screeningTask, screeningLogic,
				new RuleResult[] { ruleResult });

		assertEquals("check result", 1, doScalarQuery(
				"SELECT * FROM screening_result", new Object[] {}));
	}

	/**
	 * <p>
	 * Failure test of the method
	 * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
	 * RuleResult[] ruleResult)</code>.
	 * </p>
	 * <p>
	 * screeningTask is null. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureLogResponse1() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.logResponse(null, new ScreeningLogic(
					new ComponentSpecificationRule(), ResponseLevel.PASS,
					ResponseLevel.WARN, ResponseLevel.FAIL, 1, 1, 1),
					new RuleResult[] {});
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message", "screeningTask should not be null.",
					e.getMessage());
		}
	}

	/**
	 * <p>
	 * Failure test of the method
	 * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
	 * RuleResult[] ruleResult)</code>.
	 * </p>
	 * <p>
	 * screeningLogic is null. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureLogResponse2() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.logResponse(new ScreeningTask(), null, new RuleResult[] {});
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message", "screeningLogic should not be null.",
					e.getMessage());
		}
	}

	/**
	 * <p>
	 * Failure test of the method
	 * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
	 * RuleResult[] ruleResult)</code>.
	 * </p>
	 * <p>
	 * ruleResult is null. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureLogResponse3() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.logResponse(new ScreeningTask(), new ScreeningLogic(
					new ComponentSpecificationRule(), ResponseLevel.PASS,
					ResponseLevel.WARN, ResponseLevel.FAIL, 1, 1, 1), null);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message", "ruleResult should not be null.", e
					.getMessage());
		}
	}

	/**
	 * <p>
	 * Failure test of the method
	 * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
	 * RuleResult[] ruleResult)</code>.
	 * </p>
	 * <p>
	 * ruleResult contains null. IllegalArgumentException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureLogResponse4() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.logResponse(new ScreeningTask(), new ScreeningLogic(
					new ComponentSpecificationRule(), ResponseLevel.PASS,
					ResponseLevel.WARN, ResponseLevel.FAIL, 1, 1, 1),
					new RuleResult[] { null });
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("check message",
					"ruleResult should not contain null elements.", e
							.getMessage());
		}
	}

	/**
	 * <p>
	 * Failure test of the method
	 * <code>void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
	 * RuleResult[] ruleResult)</code>.
	 * </p>
	 * <p>
	 * operator is not set. ScreeningException is expected.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testFailureLogResponse5() throws Exception {
		ScreeningResponseLogger logger = new DAOLogger(responseDAO);

		try {
			logger.logResponse(new ScreeningTask(), new ScreeningLogic(
					new ComponentSpecificationRule(), ResponseLevel.PASS,
					ResponseLevel.WARN, ResponseLevel.FAIL, 1, 1, 1),
					new RuleResult[] {});
			fail("ScreeningException should be thrown.");
		} catch (ScreeningException e) {
			assertEquals("check message",
					"The operator name of this DAOLogger instance has not been specified. "
							+ "Use setOperator() to set the operator name.", e
							.getMessage());
		}
	}
}