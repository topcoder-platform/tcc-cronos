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

import com.topcoder.web.tc.PastContestsManager;
import com.topcoder.web.tc.PastContestsManagerException;
import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.TestHelper;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.PastContestDTO;
import com.topcoder.web.tc.dto.PastContestFilter;

import com.topcoder.web.tc.ContestServicesConfigurationException;

/**
 * <p>
 * Unit tests for class <code>PastContestsManagerImpl</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PastContestsManagerImplTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>PastContestsManagerImpl</code> instance used to test against.
     * </p>
     */
    private PastContestsManagerImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PastContestsManagerImplTest.class);
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
        impl = new PastContestsManagerImpl();
        impl.setHibernateTemplate(hibernateTemplate);

        impl.setCompletedStatusId(11);
        impl.setProjectNameInfoId(1);
        impl.setContestSubmissionTypeId(1);
        impl.setActiveSubmissionStatusId(1);
        impl.setRegistrationPhaseTypeId(1);
        impl.setSubmissionPhaseTypeId(2);
        impl.setFailedScreeningSubmissionStatusId(2);
        impl.setFailedReviewSubmissionStatusId(3);
        impl.setCompletedWithoutWinSubmissionStatusId(4);
        impl.setSubmitterRoleId(1);
        impl.setApprovalPhaseTypeId(1);
        impl.setPassingScreeningScore(55.5);
        impl.setWinnerIdInfoId(7);
        impl.setExternalReferenceIdInfoId(1);
        impl.setHandleInfoId(1);
        impl.setWinnerProfileLinkTemplate("topcoder/%id%");
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
     * Inheritance test, verifies <code>PastContestsManagerImpl</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof HibernateDaoSupport);
        assertTrue("The instance's subclass is not correct.", impl instanceof PastContestsManager);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PastContestsManagerImpl()</code>.<br>
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
     * <code>retrievePastContests(String, SortingOrder, int, int, PastContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrievePastContests() throws Exception {
        List<PastContestDTO> res = impl.retrievePastContests("projectGroupCategory.name",
            SortingOrder.DESCENDING, -1, -1, new PastContestFilter());
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord3(res.get(0));
        checkRecord2(res.get(1));
        checkRecord1(res.get(2));

        res = impl.retrievePastContests("projectGroupCategory.name", SortingOrder.DESCENDING, 1, 2,
            new PastContestFilter());
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord3(res.get(0));
        checkRecord2(res.get(1));

        res = impl.retrievePastContests("projectGroupCategory.name", SortingOrder.DESCENDING, 2, 2,
            new PastContestFilter());
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
    private static void checkRecord1(PastContestDTO dto) {
        assertEquals("The return value should be same as ", "Type1", dto.getType());
        assertEquals("The return value should be same as ", "SubType101", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog21", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName11", dto.getContestName());
        assertEquals("The return value should be same as ", 4, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 3, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getCompletionDate());
        assertEquals("The return value should be same as ", 2, dto.getPassedScreeningCount());
        assertEquals("The return value should be same as ", "topcoder/winnerId1", dto.getWinnerProfileLink());
        assertEquals("The return value should be same as ", 99.0, dto.getWinnerScore(), 0.001);
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     * @param dto the record
     */
    private static void checkRecord2(PastContestDTO dto) {
        assertEquals("The return value should be same as ", "Type2", dto.getType());
        assertEquals("The return value should be same as ", "SubType102", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog22", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName21", dto.getContestName());
        assertEquals("The return value should be same as ", 1, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 2, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getCompletionDate());
        assertEquals("The return value should be same as ", 1, dto.getPassedScreeningCount());
        assertEquals("The return value should be same as ", "topcoder/winnerId2", dto.getWinnerProfileLink());
        assertEquals("The return value should be same as ", 85.0, dto.getWinnerScore(), 0.001);
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     * @param dto the record
     */
    private static void checkRecord3(PastContestDTO dto) {
        assertEquals("The return value should be same as ", "Type3", dto.getType());
        assertEquals("The return value should be same as ", "SubType103", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog23", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName31", dto.getContestName());
        assertEquals("The return value should be same as ", 1, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 2, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getCompletionDate());
        assertEquals("The return value should be same as ", 0, dto.getPassedScreeningCount());
        assertEquals("The return value should be same as ", "topcoder/winnerId3", dto.getWinnerProfileLink());
        assertEquals("The return value should be same as ", 80.0, dto.getWinnerScore(), 0.001);
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
    public void testRetrievePastContests3() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        List<String> types = new ArrayList<String>();
        types.add("Type1");
        types.add("Type2");
        filter.setTypes(types);

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
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
    public void testRetrievePastContests4() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        List<String> subTypes = new ArrayList<String>();
        subTypes.add("SubType101");
        subTypes.add("SubType103");
        filter.setSubTypes(subTypes);

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord3(res.get(1));
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
    public void testRetrievePastContests5() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        List<String> catalogs = new ArrayList<String>();
        catalogs.add("ProjectCatalog22");
        filter.setCatalogs(catalogs);

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
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
    public void testRetrievePastContests6() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        filter.setContestName("ProjectContestName");

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));

        filter.setContestName("ProjectContestName1_");

        res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord1(res.get(0));
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
    public void testRetrievePastContests7() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2011);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 15);
        Date date1 = calendar.getTime();
        dateInterval.setFirstDate(date1);

        filter.setRegistrationStartDate(dateInterval);

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

        dateInterval.setIntervalType(DateIntervalType.AFTER);

        calendar.set(Calendar.DATE, 20);
        Date date2 = calendar.getTime();
        dateInterval.setFirstDate(date2);

        res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord3(res.get(0));
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
    public void testRetrievePastContests8() throws Exception {
        PastContestFilter filter = new PastContestFilter();
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

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
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

        res = impl.retrievePastContests(null, null, -1, -1, filter);
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
    public void testRetrievePastContests9() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalizationDate(dateInterval);

        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));

        dateInterval.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);

        res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 0, res.size());
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
    public void testRetrievePastContests10() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        filter.setWinnerHandle("winnerId2");
        List<PastContestDTO> res = impl.retrievePastContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrievePastContests(String, SortingOrder, int, int, PastContestFilter)</code>.<br>
     * The pageNumber is negative and not -1.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrievePastContests_PageNumberNegative() throws Exception {
        impl.retrievePastContests(null, null, -2, -1, new PastContestFilter());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrievePastContests(String, SortingOrder, int, int, PastContestFilter)</code>.<br>
     * The pageSize is negative and not -1.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrievePastContests_PageSizeNegative() throws Exception {
        impl.retrievePastContests(null, null, -1, -2, new PastContestFilter());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrievePastContests(String, SortingOrder, int, int, PastContestFilter)</code>.<br>
     * The filter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrievePastContests_FilterNull() throws Exception {
        impl.retrievePastContests(null, null, -1, -1, null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrievePastContests(PastContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrievePastContests_OnlyFilter() throws Exception {
        PastContestFilter filter = new PastContestFilter();
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

        List<PastContestDTO> res = impl.retrievePastContests(filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));
    }

    /**
     * <p>
     * Failure test for the method <code>retrievePastContests(PastContestFilter)</code>.<br>
     * The filter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrievePastContests_OnlyFilter_FilterNull() throws Exception {
        impl.retrievePastContests(null);
    }

    /**
     * <p>
     * Failure test for the method <code>retrievePastContests(PastContestFilter)</code>.<br>
     * The dateInterval.IntervalType is null.<br>
     * Expect PastContestsManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = PastContestsManagerException.class)
    public void testRetrievePastContests_OnlyFilter_IntervalTypeNull() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        filter.setSubmissionEndDate(new DateIntervalSpecification());
        impl.retrievePastContests(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>retrievePastContests(PastContestFilter)</code>.<br>
     * The dateInterval.firstDate is null.<br>
     * Expect PastContestsManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = PastContestsManagerException.class)
    public void testRetrievePastContests_OnlyFilter_FirstDateNull() throws Exception {
        PastContestFilter filter = new PastContestFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);
        filter.setSubmissionEndDate(dateInterval);
        impl.retrievePastContests(filter);
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

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl, "projectNameInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCompletedStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetCompletedStatusId() throws Exception {
        impl.setCompletedStatusId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl, "completedStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setApprovalPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetApprovalPhaseTypeId() throws Exception {
        impl.setApprovalPhaseTypeId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl, "approvalPhaseTypeId");

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

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl, "winnerIdInfoId");

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

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
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

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl, "handleInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmitterRoleId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSubmitterRoleId() throws Exception {
        impl.setSubmitterRoleId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl, "submitterRoleId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestSubmissionTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetContestSubmissionTypeId() throws Exception {
        impl.setContestSubmissionTypeId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "contestSubmissionTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setActiveSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetActiveSubmissionStatusId() throws Exception {
        impl.setActiveSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "activeSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFailedScreeningSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFailedScreeningSubmissionStatusId() throws Exception {
        impl.setFailedScreeningSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "failedScreeningSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFailedReviewSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFailedReviewSubmissionStatusId() throws Exception {
        impl.setFailedReviewSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "failedReviewSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCompletedWithoutWinSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetCompletedWithoutWinSubmissionStatusId() throws Exception {
        impl.setCompletedWithoutWinSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "completedWithoutWinSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setWinnerProfileLinkTemplate(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetWinnerProfileLinkTemplate() throws Exception {
        impl.setWinnerProfileLinkTemplate("test");

        String result = (String) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "winnerProfileLinkTemplate");

        assertEquals("The return value should be same as ", "test", result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPassingScreeningScore(double)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetPassingScreeningScore() throws Exception {
        impl.setPassingScreeningScore(88.8);

        double result = (Double) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "passingScreeningScore");

        assertEquals("The return value should be same as ", 88.8, result, 0.001);
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

        long result = (Long) TestHelper.getField(PastContestsManagerImpl.class, impl,
            "registrationPhaseTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetSubmissionPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSubmissionPhaseTypeId() throws Exception {
        impl.setSubmissionPhaseTypeId(999);

        long result = (Long) TestHelper
                        .getField(PastContestsManagerImpl.class, impl, "submissionPhaseTypeId");

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
     * The submitterRoleId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_SubmitterRoleIdNegative() throws Exception {
        impl.setSubmitterRoleId(-2);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The passingScreeningScore is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_PassingScreeningScoreNegative() throws Exception {
        impl.setPassingScreeningScore(-0.1);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The winnerProfileLinkTemplate is null.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_WinnerProfileLinkTemplateNull() throws Exception {
        impl.setWinnerProfileLinkTemplate(null);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The winnerProfileLinkTemplate is empty.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_WinnerProfileLinkTemplateEmpty() throws Exception {
        impl.setWinnerProfileLinkTemplate("");

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The winnerProfileLinkTemplate is invalid.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_WinnerProfileLinkTemplateInvalid() throws Exception {
        impl.setWinnerProfileLinkTemplate("abc");

        impl.checkConfiguration();
    }

}
