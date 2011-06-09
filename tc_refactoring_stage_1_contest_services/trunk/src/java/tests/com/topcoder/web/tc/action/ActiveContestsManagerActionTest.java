/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.ActiveContestsManager;
import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.TestHelper;
import com.topcoder.web.tc.dto.ActiveContestDTO;
import com.topcoder.web.tc.impl.ActiveContestsManagerImpl;

/**
 * <p>
 * Unit tests for class <code>ActiveContestsManagerAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestsManagerActionTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>ActiveContestsManagerAction</code> instance used to test against.
     * </p>
     */
    private ActiveContestsManagerAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActiveContestsManagerActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @BeforeClass
    public static void beforeClass() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("test_files" + File.separator
            + "ApplicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ActiveContestsManagerAction();

        ActiveContestsManagerImpl activeContestsManagerImpl = new ActiveContestsManagerImpl();
        activeContestsManagerImpl.setHibernateTemplate(hibernateTemplate);

        activeContestsManagerImpl.setActiveStatusId(11);
        activeContestsManagerImpl.setProjectNameInfoId(1);
        activeContestsManagerImpl.setContestSubmissionTypeId(1);
        activeContestsManagerImpl.setActiveSubmissionStatusId(1);
        activeContestsManagerImpl.setFailedScreeningSubmissionStatusId(2);
        activeContestsManagerImpl.setFailedReviewSubmissionStatusId(3);
        activeContestsManagerImpl.setCompletedWithoutWinSubmissionStatusId(4);
        activeContestsManagerImpl.setSubmitterRoleId(1);
        activeContestsManagerImpl.setRegistrationPhaseTypeId(1);
        activeContestsManagerImpl.setSubmissionPhaseTypeId(2);
        activeContestsManagerImpl.setFinalReviewPhaseTypeId(3);
        activeContestsManagerImpl.setFirstPlaceCostInfoId(2);
        activeContestsManagerImpl.setReliabilityBonusCostInfoId(3);
        activeContestsManagerImpl.setDigitalRunPointInfoId(4);
        activeContestsManagerImpl.setPaymentsInfoId(5);
        activeContestsManagerImpl.setDigitalRunFlagInfoId(6);
        activeContestsManagerImpl.setOpenPhaseStatusId(2);

        impl.setActiveContestsManager(activeContestsManagerImpl);
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @AfterClass
    public static void afterClass() {
        hibernateTemplate = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ActiveContestsManagerAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseJSONParameterAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestsManagerAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        String jsonStr = "{\"columnName\":\"projectGroupCategory.name\","
            + "\"sortingOrder\":\"ASCENDING\", \"pageNumber\":1, \"pageSize\":\"2\","
            + " \"filter\":{\"contestName\":\"ProjectContestName\","
            + "\"submissionEndDate\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2021/04/01\"},"
            + "\"prizeUpperBound\":500,\"contestFinalizationDate\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
            + "\"catalogs\":[\"ProjectCatalog21\",\"ProjectCatalog22\",\"ProjectCatalog23\"],"
            + "\"subTypes\":[\"SubType101\",\"SubType102\",\"SubType103\"],"
            + "\"types\":[\"Type1\",\"Type2\",\"Type3\"],\"prizeLowerBound\":-1,"
            + "\"registrationStartDate\":{\"intervalType\":\"BETWEEN_DATES\","
            + "\"firstDate\":\"2011/04/01\",\"secondDate\":\"2100/05/01\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();

        List<ActiveContestDTO> activeContests = impl.getActiveContests();
        assertNotNull("The return list should not be null ", activeContests);
        assertEquals("The return list's size should be same as ", 2, activeContests.size());

        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute2() throws Exception {
        String jsonStr = "{}";
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();

        List<ActiveContestDTO> activeContests = impl.getActiveContests();
        assertNotNull("The return list should not be null ", activeContests);
        assertEquals("The return list's size should be same as ", 3, activeContests.size());

        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The sortingOrder is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_SortingOrderInvalid() throws Exception {
        String jsonStr = "{\"sortingOrder\":\"abc\"}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The pageNumber is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_PageNumberInvalid() throws Exception {
        String jsonStr = "{\"pageNumber\":\"abc\"}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The date interval is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_DateIntervalInvalid() throws Exception {
        String jsonStr = "{\"filter\":{\"submissionEndDate\":{\"firstDate\":\"2021/04/01\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The date interval is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_DateIntervalInvalid2() throws Exception {
        String jsonStr = "{\"filter\":{\"submissionEndDate\":{\"intervalType\":\"abc\","
            + "\"firstDate\":\"2021/04/01\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The date format is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_DateFormatInvalid() throws Exception {
        impl.setDateFormatString("xxxxxx");
        String jsonStr = "{\"filter\":{\"submissionEndDate\":{\"intervalType\":\"BEFORE\","
            + "\"firstDate\":\"2021/04/01\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The date interval is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_DateIntervalInvalid3() throws Exception {
        String jsonStr = "{\"filter\":{\"submissionEndDate\":{\"intervalType\":\"BEFORE\","
            + "\"firstDate\":\"2021-04-01\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The date interval is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_DateIntervalInvalid4() throws Exception {
        String jsonStr = "{\"filter\":{\"submissionEndDate\":{\"intervalType\":\"BEFORE\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The date interval is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_DateIntervalInvalid5() throws Exception {
        String jsonStr = "{\"filter\":{\"submissionEndDate\":{\"intervalType\":\"BETWEEN_DATES\","
            + "\"firstDate\":\"2021/04/01\"}}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The prizeLowerBound is invalid.<br>
     * Expect ContestServicesActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_PrizeLowerBoundInvalid() throws Exception {
        String jsonStr = "{\"filter\":{\"prizeLowerBound\":\"abc\"}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getActiveContests()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetActiveContests() {
        assertNull("The initial value should be null ", impl.getActiveContests());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setActiveContestsManager(ActiveContestsManager)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetActiveContestsManager() throws Exception {
        ActiveContestsManagerImpl activeContestsManagerImpl = new ActiveContestsManagerImpl();

        impl.setActiveContestsManager(activeContestsManagerImpl);

        ActiveContestsManager result = (ActiveContestsManager) TestHelper.getField(
            ActiveContestsManagerAction.class, impl, "activeContestsManager");

        assertEquals("The return value should be same as ", activeContestsManagerImpl, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkConfiguration()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckConfiguration() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The activeContestsManager is null.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_ActiveContestsManagerNull() throws Exception {
        impl.setActiveContestsManager(null);

        impl.checkConfiguration();
    }

}
