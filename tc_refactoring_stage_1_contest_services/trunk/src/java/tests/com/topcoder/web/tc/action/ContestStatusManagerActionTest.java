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
import com.topcoder.web.tc.dto.ContestStatusDTO;
import com.topcoder.web.tc.impl.ContestStatusManagerImpl;

/**
 * <p>
 * Unit tests for class <code>ContestStatusManagerAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestStatusManagerActionTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>ContestStatusManagerAction</code> instance used to test against.
     * </p>
     */
    private ContestStatusManagerAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestStatusManagerActionTest.class);
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
        impl = new ContestStatusManagerAction();

        ContestStatusManagerImpl contestStatusManagerImpl = new ContestStatusManagerImpl();
        contestStatusManagerImpl.setHibernateTemplate(hibernateTemplate);

        contestStatusManagerImpl.setProjectNameInfoId(1);
        contestStatusManagerImpl.setRegistrationPhaseTypeId(1);
        contestStatusManagerImpl.setSubmissionPhaseTypeId(2);
        contestStatusManagerImpl.setFinalReviewPhaseTypeId(2);
        contestStatusManagerImpl.setOpenPhaseStatusId(1);
        contestStatusManagerImpl.setWinnerIdInfoId(7);
        contestStatusManagerImpl.setExternalReferenceIdInfoId(1);
        contestStatusManagerImpl.setHandleInfoId(1);
        contestStatusManagerImpl.setSecondPlaceIdInfoId(8);
        contestStatusManagerImpl.setFirstPlaceCostInfoId(2);

        impl.setContestStatusManager(contestStatusManagerImpl);
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
     * Inheritance test, verifies <code>ContestStatusManagerAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseJSONParameterAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestStatusManagerAction()</code>.<br>
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
            + "\"winnerHandle\":\"winnerId\"}}";

        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);

        EasyMock.replay(servletRequest);

        impl.execute();

        List<ContestStatusDTO> contestStatuses = impl.getContestStatuses();
        assertNotNull("The return list should not be null ", contestStatuses);
        assertEquals("The return list's size should be same as ", 2, contestStatuses.size());

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

        List<ContestStatusDTO> contestStatuses = impl.getContestStatuses();
        assertNotNull("The return list should not be null ", contestStatuses);
        assertEquals("The return list's size should be same as ", 6, contestStatuses.size());

        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestStatuses()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestStatuses() {
        assertNull("The initial value should be null ", impl.getContestStatuses());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestStatusManager(ContestStatusManager)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetContestStatusManager() throws Exception {
        ContestStatusManagerImpl contestStatusManagerImpl = new ContestStatusManagerImpl();

        impl.setContestStatusManager(contestStatusManagerImpl);

        ContestStatusManagerImpl result = (ContestStatusManagerImpl) TestHelper.getField(
            ContestStatusManagerAction.class, impl, "contestStatusManager");

        assertEquals("The return value should be same as ", contestStatusManagerImpl, result);
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
     * The contestStatusManager is null.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_ContestStatusManagerNull() throws Exception {
        impl.setContestStatusManager(null);

        impl.checkConfiguration();
    }
}
