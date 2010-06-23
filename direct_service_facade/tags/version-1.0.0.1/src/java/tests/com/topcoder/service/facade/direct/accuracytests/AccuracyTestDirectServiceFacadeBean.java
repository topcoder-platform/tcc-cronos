/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.accuracytests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.management.project.Project;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestReceiptData;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.ProjectBudget;
import com.topcoder.service.facade.direct.SpecReviewState;
import com.topcoder.service.facade.direct.SpecReviewStatus;
import com.topcoder.service.facade.direct.ejb.DirectServiceFacadeBean;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.StudioCompetition;

/**
 * Unit tests for the DirectServiceFacadeBean class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestDirectServiceFacadeBean extends TestCase {
	/**
	 * DirectServiceFacadeBean instance used to test.
	 */
	private DirectServiceFacadeBean target;

	/**
	 * AccuracyMockContestServiceFacade instance used to test.
	 */
	private AccuracyMockContestServiceFacade f;

	/**
	 * <p>
	 * Sets up the test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	protected void setUp() throws Exception {
		target = new DirectServiceFacadeBean();

		setPrivateField("loggerName", "direct_service_facade_log");

		setPrivateField("projectManagerClassName",
				AccuracyMockProjectManager.class.getName());
		setPrivateField("projectLinkManagerClassName",
				AccuracyMockProjectLinkManager.class.getName());
		setPrivateField("phaseManagerClassName", AccuracyMockPhaseManager.class
				.getName());

		setPrivateField("receiptEmailTitleTemplateText",
				"Receipt for contest \"%contestName%\"");
		String budgetbody = "Project Name %billingProjectName% Old Budget Amount %oldBudget%"
				+ "New Budget Amount %newBudget%";
		setPrivateField("receiptEmailBodyTemplateText", budgetbody);

		setPrivateField("budgetUpdateEmailTitleTemplateText", "Budget Updated");
		String recbody = "Project Name %projectName% Contest Fee %contestFee%"
				+ "Start Date %startDate% Prizes %prizes% Total Cost %contestTotalCost%"
				+ "See details here topcoder.com/tc?module%contestId%";
		setPrivateField("budgetUpdateEmailBodyTemplateText", recbody);

		setPrivateField("emailSender", "tc@topcoder.com");
		f = new AccuracyMockContestServiceFacade();
		setPrivateField("contestServiceFacade", f);
		setPrivateField("userService", new AccuracyMockUserService());
		setPrivateField("projectDAO", new AccuracyMockProjectDAO());
		callInitialize();
	}

	/**
	 * <p>
	 * Tears down the test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	protected void tearDown() throws Exception {
		target = null;
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getContestReceiptData(TCSubject tcSubject, long contestId, boolean isStudio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetContestReceiptDataAccuracy1() throws Exception {
		TCSubject tcSubject = new TCSubject(1);
		ContestReceiptData contestReceiptData = target.getContestReceiptData(
				tcSubject, 1, true);

		assertNotNull("ContestReceiptData invalid.", contestReceiptData);
		assertEquals("Invalid ContestId", 1, contestReceiptData.getContestId());
		assertEquals("Invalid Email", "tc@topcoder.com", contestReceiptData
				.getEmail());
		assertEquals("Invalid ContestName", "dev", contestReceiptData
				.getContestName());
		assertEquals("Invalid Phone", "0571-77777777", contestReceiptData
				.getPhone());
		assertTrue("Invalid Studio", contestReceiptData.isStudio());

		assertEquals("Invalid ContestId", "STUDIO - c", contestReceiptData
				.getContestType());
		assertEquals("Invalid ContestId", "1", contestReceiptData
				.getReferenceNo());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getContestReceiptData(TCSubject tcSubject, long contestId, boolean isStudio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetContestReceiptDataAccuracy2() throws Exception {
		TCSubject tcSubject = new TCSubject(1);
		ContestReceiptData contestReceiptData = target.getContestReceiptData(
				tcSubject, 1, false);

		assertNotNull("ContestReceiptData invalid.", contestReceiptData);
		assertEquals("Invalid ContestId", 1, contestReceiptData.getContestId());
		assertEquals("Invalid Email", "tc@topcoder.com", contestReceiptData
				.getEmail());
		assertEquals("Invalid Phone", "0571-77777777", contestReceiptData
				.getPhone());
		assertFalse("Invalid Studio", contestReceiptData.isStudio());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>updateActiveContestPrize(TCSubject tcSubject, ContestPrize contestPrize)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testUpdateActiveContestPrizeAccuracy() throws Exception {
		ContestPrize prize = new ContestPrize();
		prize.setContestPrizes(new double[] { 500, 250 });
		prize.setStudio(true);
		prize.setContestId(1);
		target.updateActiveContestPrize(new TCSubject(1), prize);

		StudioCompetition sc = f.getStudioCompetition();
		assertNotNull("updateActiveContestPrize invalid.", sc);

		/*
		 * List<CompetitionPrize> prizes = sc.getPrizes();
		 * assertEquals("Invalid prizes", 2, prizes.size()); for
		 * (CompetitionPrize p : prizes) { if (p.getPlace() == 1) {
		 * assertEquals("Invalid prizes", 500, p.getAmount()); } else if
		 * (p.getPlace() == 2) { assertEquals("Invalid prizes", 250,
		 * p.getAmount()); } else { fail("invalid prize place."); } }
		 */
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getContestPrize(TCSubject tcSubject, long contestId, boolean studio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetContestPrizeAccuracy1() throws Exception {
		ContestPrize contestPrize = target.getContestPrize(new TCSubject(1), 1,
				true);
		assertNotNull("contestPrize invalid.", contestPrize);
		assertEquals("Invalid ContestId", 1, contestPrize.getContestId());
		assertTrue("Invalid Studio", contestPrize.isStudio());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getContestPrize(TCSubject tcSubject, long contestId, boolean studio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetContestPrizeAccuracy2() throws Exception {
		ContestPrize contestPrize = target.getContestPrize(new TCSubject(1), 9,
				false);
		assertNotNull("contestPrize invalid.", contestPrize);
		assertEquals("Invalid ContestId", 9, contestPrize.getContestId());
		assertFalse("Invalid Studio", contestPrize.isStudio());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetContestScheduleAccuracy() throws Exception {
		ContestSchedule sch = target.getContestSchedule(new TCSubject(1), 8,
				true);
		assertNotNull("contestPrize invalid.", sch);
		assertEquals("getContestId invalid.", 8, sch.getContestId());
		assertEquals("getRegistrationDuration invalid.", 0, sch
				.getRegistrationDuration());
		assertTrue("Invalid Studio", sch.isStudio());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetContestScheduleAccuracy2() throws Exception {
		ContestSchedule sch = target.getContestSchedule(new TCSubject(1), 8,
				false);
		assertNotNull("contestPrize invalid.", sch);
		assertEquals("getContestId invalid.", 8, sch.getContestId());
		assertEquals("getRegistrationDuration invalid.", 0, sch
				.getRegistrationDuration());
		assertFalse("Invalid Studio", sch.isStudio());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>extendActiveContestSchedule(TCSubject tcSubject, ContestScheduleExtension extension)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testExtendActiveContestScheduleAccuracy1() throws Exception {
		ContestScheduleExtension extension = new ContestScheduleExtension();
		extension.setContestId(1);
		extension.setExtendMilestoneBy(2000);
		extension.setExtendRegistrationBy(3000);
		extension.setExtendSubmissionBy(4000);
		target.extendActiveContestSchedule(new TCSubject(1), extension);
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>repostSoftwareContest(TCSubject tcSubject, long contestId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testRepostSoftwareContestAccuracy() throws Exception {
		long result = target.repostSoftwareContest(new TCSubject(1), 1);
		assertEquals("repostSoftwareContest invalid.", 100, result);
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testCreateNewVersionForDesignDevContestAccuracy()
			throws Exception {
		long result = target.createNewVersionForDesignDevContest(new TCSubject(
				1), 14);
		assertEquals("createNewVersionForDesignDevContest invalid.", 110,
				result);
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>deleteContest(TCSubject tcSubject, long contestId, boolean studio)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testDeleteContestAccuracy1() throws Exception {
		target.deleteContest(new TCSubject(1), 10, true);
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getProjectGamePlan(TCSubject tcSubject, long projectId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetProjectGamePlanAccuracy() throws Exception {
		List<ContestPlan> ret = target
				.getProjectGamePlan(new TCSubject(1), 100);
		assertEquals("getProjectGamePlan invalid.", 2, ret.size());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getParentProjects(TCSubject tcSubject, long projectId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetParentProjectsAccuracy() throws Exception {
		List<Project> projects = target
				.getParentProjects(new TCSubject(1), 111);
		assertEquals("getParentProjects invalid.", 2, projects.size());
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getChildProjects(TCSubject tcSubject, long projectId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetChildProjectsAccuracy() throws Exception {
		List<Project> projects = target.getChildProjects(new TCSubject(1), 111);
		assertEquals("getChildProjects invalid.", 2, projects.size());
	}

	/**
	 * <p>
	 * Accuracy test for the <code>updateProjectLinks()</code> method, expects
	 * the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testUpdateProjectLinksAccuracy() throws Exception {
		target.updateProjectLinks(new TCSubject(1), 1, new long[] { 1 },
				new long[] { 2 });
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getProjectBudget(TCSubject tcSubject, long billingProjectId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetProjectBudgetAccuracy() throws Exception {
		double budget = target.getProjectBudget(new TCSubject(1), 111);
		assertEquals("getProjectBudget invalid.", 99.5, budget);
	}

	/**
	 * <p>
	 * Accuracy test for the
	 * <code>getSpecReviewState(TCSubject tcSubject, long contestId)</code>
	 * method, expects the value is returned properly.
	 * </p>
	 * 
	 * @throws Exception
	 *             exception.
	 */
	public void testGetSpecReviewStateAccuracy() throws Exception {
		SpecReviewState state = target
				.getSpecReviewState(new TCSubject(1), 100);
		assertNotNull("extendActiveContestSchedule invalid.", state);
		assertEquals("getSpecReviewState invalid.",
				SpecReviewStatus.FINAL_FIX_ACCEPTED, state.getStatus());
	}

	/**
	 * Used to set contents of private field in tested target (inject
	 * resources).
	 * 
	 * @param fieldName
	 *            name of the field to set
	 * @param value
	 *            value to set in the field
	 * @throws Exception
	 *             when it occurs deeper
	 */
	private void setPrivateField(String fieldName, Object value)
			throws Exception {
		Field f = DirectServiceFacadeBean.class.getDeclaredField(fieldName);
		f.setAccessible(true);
		f.set(target, value);
	}

	/**
	 * Simple routine to call private method initialize through reflection api.
	 * 
	 * @throws Exception
	 *             when it occurs deeper
	 */
	private void callInitialize() throws Exception {
		Method m = target.getClass().getDeclaredMethod("initialize");
		m.setAccessible(true);
		m.invoke(target);
	}
}