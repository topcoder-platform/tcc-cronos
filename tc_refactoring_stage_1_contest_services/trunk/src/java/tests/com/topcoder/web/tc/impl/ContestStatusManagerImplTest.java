/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.topcoder.web.tc.ContestStatusManager;
import com.topcoder.web.tc.ContestStatusManagerException;
import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.TestHelper;
import com.topcoder.web.tc.dto.ContestStatusDTO;
import com.topcoder.web.tc.dto.ContestStatusFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;

import com.topcoder.web.tc.ContestServicesConfigurationException;

/**
 * <p>
 * Unit tests for class <code>ContestStatusManagerImpl</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestStatusManagerImplTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>ContestStatusManagerImpl</code> instance used to test against.
     * </p>
     */
    private ContestStatusManagerImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestStatusManagerImplTest.class);
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
        impl = new ContestStatusManagerImpl();
        impl.setHibernateTemplate(hibernateTemplate);

        impl.setProjectNameInfoId(1);
        impl.setRegistrationPhaseTypeId(1);
        impl.setSubmissionPhaseTypeId(2);
        impl.setFinalReviewPhaseTypeId(2);
        impl.setOpenPhaseStatusId(1);
        impl.setWinnerIdInfoId(7);
        impl.setExternalReferenceIdInfoId(1);
        impl.setHandleInfoId(1);
        impl.setSecondPlaceIdInfoId(8);
        impl.setFirstPlaceCostInfoId(2);
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
     * Inheritance test, verifies <code>ContestStatusManagerImpl</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof HibernateDaoSupport);
        assertTrue("The instance's subclass is not correct.", impl instanceof ContestStatusManager);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestStatusManagerImpl()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId");
        List<ContestStatusDTO> res = impl.retrieveContestStatuses("projectGroupCategory.name",
            SortingOrder.DESCENDING, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        for (ContestStatusDTO rrr : res) {
            System.out.println(rrr.getCatalog());
            System.out.println(rrr.getSubType());
            System.out.println(rrr.getType());
            System.out.println(rrr.getCurrentPhase());
            System.out.println(rrr.getFirstPlaceHandle());
            System.out.println(rrr.getSecondPlaceHandle());
            System.out.println("****************************************");
        }
        System.out.println();
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord3(res.get(0));
        checkRecord2(res.get(1));
        checkRecord1(res.get(2));

        res = impl.retrieveContestStatuses("projectGroupCategory.name", SortingOrder.DESCENDING, 1, 2,
            filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord3(res.get(0));
        checkRecord2(res.get(1));

        res = impl.retrieveContestStatuses("projectGroupCategory.name", SortingOrder.DESCENDING, 2, 2,
            filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord1(res.get(0));
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     * @param dto the record
     */
    private static void checkRecord1(ContestStatusDTO dto) {
        assertEquals("The return value should be same as ", "Type1", dto.getType());
        assertEquals("The return value should be same as ", "SubType101", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog21", dto.getCatalog());
        assertNotNull("The return date should not be null ", dto.getSubmissionDueDate());
        assertNotNull("The return date should not be null ", dto.getFinalReviewDueDate());
        assertEquals("The return value should be same as ", "phaseType1", dto.getCurrentPhase());
        assertEquals("The return value should be same as ", "winnerId1", dto.getFirstPlaceHandle());
        assertEquals("The return value should be same as ", "runnerUpId1", dto.getSecondPlaceHandle());
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     * @param dto the record
     */
    private static void checkRecord2(ContestStatusDTO dto) {
        assertEquals("The return value should be same as ", "Type2", dto.getType());
        assertEquals("The return value should be same as ", "SubType102", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog22", dto.getCatalog());
        assertNotNull("The return date should not be null ", dto.getSubmissionDueDate());
        assertNotNull("The return date should not be null ", dto.getFinalReviewDueDate());
        assertEquals("The return value should be same as ", "phaseType1", dto.getCurrentPhase());
        assertEquals("The return value should be same as ", "winnerId2", dto.getFirstPlaceHandle());
        assertEquals("The return value should be same as ", "runnerUpId2", dto.getSecondPlaceHandle());
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     * @param dto the record
     */
    private static void checkRecord3(ContestStatusDTO dto) {
        assertEquals("The return value should be same as ", "Type3", dto.getType());
        assertEquals("The return value should be same as ", "SubType103", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog23", dto.getCatalog());
        assertNotNull("The return date should not be null ", dto.getSubmissionDueDate());
        assertNotNull("The return date should not be null ", dto.getFinalReviewDueDate());
        assertEquals("The return value should be same as ", "phaseType1", dto.getCurrentPhase());
        assertEquals("The return value should be same as ", "winnerId3", dto.getFirstPlaceHandle());
        assertEquals("The return value should be same as ", "runnerUpId3", dto.getSecondPlaceHandle());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses3() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        List<String> types = new ArrayList<String>();
        types.add("Type1");
        types.add("Type2");
        filter.setTypes(types);

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses4() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        List<String> subTypes = new ArrayList<String>();
        subTypes.add("SubType101");
        subTypes.add("SubType103");
        filter.setSubTypes(subTypes);

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord3(res.get(1));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses5() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        List<String> catalogs = new ArrayList<String>();
        catalogs.add("ProjectCatalog22");
        filter.setCatalogs(catalogs);

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses6() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId");
        filter.setContestName("ProjectContestName");

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));

        filter.setContestName("ProjectContestName1_");

        res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord1(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses7() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId");
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2011);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 15);
        Date date1 = calendar.getTime();
        dateInterval.setFirstDate(date1);

        filter.setRegistrationStartDate(dateInterval);

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

        dateInterval.setIntervalType(DateIntervalType.AFTER);

        calendar.set(Calendar.DATE, 20);
        Date date2 = calendar.getTime();
        dateInterval.setFirstDate(date2);

        res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord3(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses8() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BETWEEN_DATES);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2011);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 1);
        Date date1 = calendar.getTime();
        dateInterval.setFirstDate(date1);

        calendar.set(Calendar.DATE, 30);
        Date date2 = calendar.getTime();
        dateInterval.setSecondDate(date2);

        filter.setSubmissionEndDate(dateInterval);

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

        calendar.set(Calendar.DATE, 20);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date date3 = calendar.getTime();
        dateInterval.setFirstDate(date3);

        dateInterval.setIntervalType(DateIntervalType.ON);

        res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrievePastContests(String, SortingOrder, int, int, PastContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses9() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId");
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalizationDate(dateInterval);

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));

        dateInterval.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);

        res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses10() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId2");
        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveContestStatuses(String, SortingOrder, int, int,
     ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses11() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId");
        filter.setPrizeLowerBound(new Integer(300));

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord3(res.get(0));

    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveContestStatuses(String, SortingOrder, int, int,
      ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses12() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setPrizeUpperBound(new Integer(300));

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveContestStatuses(String, SortingOrder, int, int,
     ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses13() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setWinnerHandle("winnerId");
        filter.setPrizeLowerBound(new Integer(300));

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(null, null, 1, 2, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord3(res.get(0));

        res = impl.retrieveContestStatuses(null, null, 2, 2, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 0, res.size());
    }
    /**
     * <p>
     * Failure test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code>.<br>
     * The pageNumber is negative and not -1.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveContestStatuses_PageNumberNegative() throws Exception {
        impl.retrieveContestStatuses(null, null, -2, -1, new ContestStatusFilter());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code>.<br>
     * The pageSize is negative and not -1.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveContestStatuses_PageSizeNegative() throws Exception {
        impl.retrieveContestStatuses(null, null, -1, -2, new ContestStatusFilter());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrieveContestStatuses(String, SortingOrder, int, int, ContestStatusFilter)</code>.<br>
     * The filter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveContestStatuses_FilterNull() throws Exception {
        impl.retrieveContestStatuses(null, null, -1, -1, null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveContestStatuses(ContestStatusFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestStatuses_OnlyFilter() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        List<String> types = new ArrayList<String>();
        types.add("Type1");
        types.add("Type2");
        types.add("Type3");
        filter.setTypes(types);

        List<String> subTypes = new ArrayList<String>();
        subTypes.add("SubType101");
        subTypes.add("SubType102");
        subTypes.add("SubType103");
        filter.setSubTypes(subTypes);

        List<String> catalogs = new ArrayList<String>();
        catalogs.add("ProjectCatalog21");
        catalogs.add("ProjectCatalog22");
        catalogs.add("ProjectCatalog23");
        filter.setCatalogs(catalogs);

        filter.setContestName("ProjectContestName");

        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2100);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        Date date1 = calendar.getTime();
        dateInterval.setFirstDate(date1);
        filter.setSubmissionEndDate(dateInterval);

        filter.setWinnerHandle("winnerId");

        filter.setPrizeLowerBound(new Integer(50));

        filter.setPrizeUpperBound(new Integer(500));

        List<ContestStatusDTO> res = impl.retrieveContestStatuses(filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveContestStatuses(ContestStatusFilter)</code>.<br>
     * The filter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveContestStatuses_OnlyFilter_FilterNull() throws Exception {
        impl.retrieveContestStatuses(null);
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveContestStatuses(ContestStatusFilter)</code>.<br>
     * The dateInterval.IntervalType is null.<br>
     * Expect ContestStatusManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestStatusManagerException.class)
    public void testRetrieveContestStatuses_OnlyFilter_IntervalTypeNull() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        filter.setSubmissionEndDate(new DateIntervalSpecification());
        impl.retrieveContestStatuses(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveContestStatuses(ContestStatusFilter)</code>.<br>
     * The dateInterval.firstDate is null.<br>
     * Expect ContestStatusManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestStatusManagerException.class)
    public void testRetrieveContestStatuses_OnlyFilter_FirstDateNull() throws Exception {
        ContestStatusFilter filter = new ContestStatusFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);
        filter.setSubmissionEndDate(dateInterval);
        impl.retrieveContestStatuses(filter);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectNameInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetProjectNameInfoId() throws Exception {
        impl.setProjectNameInfoId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl, "projectNameInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSubmissionPhaseTypeId() throws Exception {
        impl.setSubmissionPhaseTypeId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl,
            "submissionPhaseTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFinalReviewPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFinalReviewPhaseTypeId() throws Exception {
        impl.setFinalReviewPhaseTypeId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl,
            "finalReviewPhaseTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOpenPhaseStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetOpenPhaseStatusId() throws Exception {
        impl.setOpenPhaseStatusId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl, "openPhaseStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWinnerIdInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetWinnerIdInfoId() throws Exception {
        impl.setWinnerIdInfoId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl, "winnerIdInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setExternalReferenceIdInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetExternalReferenceIdInfoId() throws Exception {
        impl.setExternalReferenceIdInfoId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl,
            "externalReferenceIdInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setHandleInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetHandleInfoId() throws Exception {
        impl.setHandleInfoId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl, "handleInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSecondPlaceIdInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSecondPlaceIdInfoId() throws Exception {
        impl.setSecondPlaceIdInfoId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl, "secondPlaceIdInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetRegistrationPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetRegistrationPhaseTypeId() throws Exception {
        impl.setRegistrationPhaseTypeId(999);

        long result = (Long) TestHelper.getField(ContestStatusManagerImpl.class, impl,
            "registrationPhaseTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetFirstPlaceCostInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFirstPlaceCostInfoId() throws Exception {
        impl.setFirstPlaceCostInfoId(999);

        long result = (Long) TestHelper
                        .getField(ContestStatusManagerImpl.class, impl, "firstPlaceCostInfoId");

        assertEquals("The return value should be same as ", 999, result);
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
     * The registrationPhaseTypeId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_RegistrationPhaseTypeIdNegative() throws Exception {
        impl.setRegistrationPhaseTypeId(-1);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The secondPlaceIdInfoId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_SecondPlaceIdInfoIdNegative() throws Exception {
        impl.setSecondPlaceIdInfoId(-1);

        impl.checkConfiguration();
    }

}
