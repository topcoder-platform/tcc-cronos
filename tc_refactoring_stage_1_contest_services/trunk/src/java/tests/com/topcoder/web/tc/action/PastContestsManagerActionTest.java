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

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.TestHelper;
import com.topcoder.web.tc.dto.PastContestDTO;
import com.topcoder.web.tc.impl.PastContestsManagerImpl;

/**
 * <p>
 * Unit tests for class <code>PastContestsManagerAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PastContestsManagerActionTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>PastContestsManagerAction</code> instance used to test against.
     * </p>
     */
    private PastContestsManagerAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PastContestsManagerActionTest.class);
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
        impl = new PastContestsManagerAction();

        PastContestsManagerImpl pastContestsManagerImpl = new PastContestsManagerImpl();
        pastContestsManagerImpl.setHibernateTemplate(hibernateTemplate);

        pastContestsManagerImpl.setCompletedStatusId(11);
        pastContestsManagerImpl.setProjectNameInfoId(1);
        pastContestsManagerImpl.setContestSubmissionTypeId(1);
        pastContestsManagerImpl.setActiveSubmissionStatusId(1);
        pastContestsManagerImpl.setRegistrationPhaseTypeId(1);
        pastContestsManagerImpl.setSubmissionPhaseTypeId(2);
        pastContestsManagerImpl.setFailedScreeningSubmissionStatusId(2);
        pastContestsManagerImpl.setFailedReviewSubmissionStatusId(3);
        pastContestsManagerImpl.setCompletedWithoutWinSubmissionStatusId(4);
        pastContestsManagerImpl.setSubmitterRoleId(1);
        pastContestsManagerImpl.setApprovalPhaseTypeId(1);
        pastContestsManagerImpl.setPassingScreeningScore(55.5);
        pastContestsManagerImpl.setWinnerIdInfoId(7);
        pastContestsManagerImpl.setExternalReferenceIdInfoId(1);
        pastContestsManagerImpl.setHandleInfoId(1);
        pastContestsManagerImpl.setWinnerProfileLinkTemplate("topcoder/%id%");

        impl.setPastContestsManager(pastContestsManagerImpl);
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
     * Inheritance test, verifies <code>PastContestsManagerAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseJSONParameterAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PastContestsManagerAction()</code>.<br>
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
            + "\"firstDate\":\"2011/04/01\",\"secondDate\":\"2100/05/01\"},"
            + "\"winnerHandle\":\"winnerId2\"}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();

        List<PastContestDTO> pastContests = impl.getPastContests();
        assertNotNull("The return list should not be null ", pastContests);
        assertEquals("The return list's size should be same as ", 1, pastContests.size());

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

        List<PastContestDTO> pastContests = impl.getPastContests();
        assertNotNull("The return list should not be null ", pastContests);
        assertEquals("The return list's size should be same as ", 3, pastContests.size());

        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPastContests()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPastContests() {
        assertNull("The initial value should be null ", impl.getPastContests());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPastContestsManager(PastContestsManager)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetPastContestsManager() throws Exception {
        PastContestsManagerImpl pastContestsManagerImpl = new PastContestsManagerImpl();

        impl.setPastContestsManager(pastContestsManagerImpl);

        PastContestsManagerImpl result = (PastContestsManagerImpl) TestHelper.getField(
            PastContestsManagerAction.class, impl, "pastContestsManager");

        assertEquals("The return value should be same as ", pastContestsManagerImpl, result);
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
     * The pastContestsManager is null.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_PastContestsManagerNull() throws Exception {
        impl.setPastContestsManager(null);

        impl.checkConfiguration();
    }

}
