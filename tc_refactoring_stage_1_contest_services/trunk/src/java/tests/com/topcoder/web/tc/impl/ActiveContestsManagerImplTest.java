/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.topcoder.web.tc.ActiveContestsManager;
import com.topcoder.web.tc.ActiveContestsManagerException;
import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.TestHelper;
import com.topcoder.web.tc.dto.ActiveContestDTO;
import com.topcoder.web.tc.dto.ActiveContestFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;

import com.topcoder.web.tc.ContestServicesConfigurationException;

/**
 * <p>
 * Unit tests for class <code>ActiveContestsManagerImpl</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestsManagerImplTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>ActiveContestsManagerImpl</code> instance used to test against.
     * </p>
     */
    private ActiveContestsManagerImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActiveContestsManagerImplTest.class);
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
        impl = new ActiveContestsManagerImpl();
        impl.setHibernateTemplate(hibernateTemplate);

        impl.setActiveStatusId(11);
        impl.setProjectNameInfoId(1);
        impl.setContestSubmissionTypeId(1);
        impl.setActiveSubmissionStatusId(1);
        impl.setFailedScreeningSubmissionStatusId(2);
        impl.setFailedReviewSubmissionStatusId(3);
        impl.setCompletedWithoutWinSubmissionStatusId(4);
        impl.setSubmitterRoleId(1);
        impl.setRegistrationPhaseTypeId(1);
        impl.setSubmissionPhaseTypeId(2);
        impl.setFinalReviewPhaseTypeId(3);
        impl.setFirstPlaceCostInfoId(2);
        impl.setReliabilityBonusCostInfoId(3);
        impl.setDigitalRunPointInfoId(4);
        impl.setPaymentsInfoId(5);
        impl.setDigitalRunFlagInfoId(6);
        impl.setOpenPhaseStatusId(2);
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
     * Inheritance test, verifies <code>ActiveContestsManagerImpl</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof HibernateDaoSupport);
        assertTrue("The instance's subclass is not correct.", impl instanceof ActiveContestsManager);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActiveContestsManagerImpl()</code>.<br>
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
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests() throws Exception {
        List<ActiveContestDTO> res = impl.retrieveActiveContests("projectGroupCategory.name",
            SortingOrder.DESCENDING, -1, -1, new ActiveContestFilter());
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord3(res.get(0));
        checkRecord2(res.get(1));
        checkRecord1(res.get(2));

        res = impl.retrieveActiveContests("projectGroupCategory.name", SortingOrder.DESCENDING, 1, 2,
            new ActiveContestFilter());
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord3(res.get(0));
        checkRecord2(res.get(1));

        res = impl.retrieveActiveContests("projectGroupCategory.name", SortingOrder.DESCENDING, 2, 2,
            new ActiveContestFilter());
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord1(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests2() throws Exception {
        impl.setActiveStatusId(12);

        List<ActiveContestDTO> res = impl.retrieveActiveContests("projectGroupCategory.name",
            SortingOrder.ASCENDING, -1, -1, new ActiveContestFilter());
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord4(res.get(0));
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     *
     * @param dto
     *            the record
     */
    private static void checkRecord1(ActiveContestDTO dto) {
        assertEquals("The return value should be same as ", "Type1", dto.getType());
        assertEquals("The return value should be same as ", "SubType101", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog21", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName11", dto.getContestName());
        assertEquals("The return value should be same as ", 4, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 3, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getRegistrationEndDate());
        assertNotNull("The return date should not be null ", dto.getSubmissionEndDate());
        assertEquals("The return value should be same as ", 100.0, dto.getFirstPrize(), 0.001);
        assertEquals("The return value should be same as ", 0.1, dto.getReliabilityBonus(), 0.001);
        assertEquals("The return value should be same as ", 150.0, dto.getDigitalRunPoints(), 0.001);
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     *
     * @param dto
     *            the record
     */
    private static void checkRecord2(ActiveContestDTO dto) {
        assertEquals("The return value should be same as ", "Type2", dto.getType());
        assertEquals("The return value should be same as ", "SubType102", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog22", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName21", dto.getContestName());
        assertEquals("The return value should be same as ", 1, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 2, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getRegistrationEndDate());
        assertNotNull("The return date should not be null ", dto.getSubmissionEndDate());
        assertEquals("The return value should be same as ", 200.0, dto.getFirstPrize(), 0.001);
        assertEquals("The return value should be same as ", 0.15, dto.getReliabilityBonus(), 0.001);
        assertEquals("The return value should be same as ", 2000.0, dto.getDigitalRunPoints(), 0.001);
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     *
     * @param dto
     *            the record
     */
    private static void checkRecord3(ActiveContestDTO dto) {
        assertEquals("The return value should be same as ", "Type3", dto.getType());
        assertEquals("The return value should be same as ", "SubType103", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog23", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName31", dto.getContestName());
        assertEquals("The return value should be same as ", 1, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 2, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getRegistrationEndDate());
        assertNotNull("The return date should not be null ", dto.getSubmissionEndDate());
        assertEquals("The return value should be same as ", 333.3, dto.getFirstPrize(), 0.001);
        assertEquals("The return value should be same as ", 0.2, dto.getReliabilityBonus(), 0.001);
        assertNull("The return date should be null ", dto.getDigitalRunPoints());
    }

    /**
     * <p>
     * Check the accuracy of record.
     * </p>
     *
     * @param dto
     *            the record
     */
    private static void checkRecord4(ActiveContestDTO dto) {
        assertEquals("The return value should be same as ", "Type4", dto.getType());
        assertEquals("The return value should be same as ", "SubType104", dto.getSubType());
        assertEquals("The return value should be same as ", "ProjectCatalog24", dto.getCatalog());
        assertEquals("The return value should be same as ", "ProjectContestName41", dto.getContestName());
        assertEquals("The return value should be same as ", 1, dto.getNumberOfSubmissions());
        assertEquals("The return value should be same as ", 0, dto.getNumberOfRegistrants());
        assertNotNull("The return date should not be null ", dto.getRegistrationEndDate());
        assertNotNull("The return date should not be null ", dto.getSubmissionEndDate());
        assertEquals("The return value should be same as ", 444.4, dto.getFirstPrize(), 0.001);
        assertEquals("The return value should be same as ", 0.2, dto.getReliabilityBonus(), 0.001);
        assertEquals("The return value should be same as ", 450.0, dto.getDigitalRunPoints(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests3() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        List<String> types = new ArrayList<String>();
        types.add("Type1");
        types.add("Type2");
        filter.setTypes(types);

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(String, SortingOrder,
     *  int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests4() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        List<String> subTypes = new ArrayList<String>();
        subTypes.add("SubType101");
        subTypes.add("SubType103");
        filter.setSubTypes(subTypes);

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord3(res.get(1));
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(String, SortingOrder,
     *  int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests5() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        List<String> catalogs = new ArrayList<String>();
        catalogs.add("ProjectCatalog22");
        filter.setCatalogs(catalogs);

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(String, SortingOrder,
     *  int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests6() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        filter.setContestName("ProjectContestName");

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));

        filter.setContestName("ProjectContestName1_");

        res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord1(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests7() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2011);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 15);
        Date date1 = calendar.getTime();
        dateInterval.setFirstDate(date1);

        filter.setRegistrationStartDate(dateInterval);

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

        dateInterval.setIntervalType(DateIntervalType.AFTER);

        calendar.set(Calendar.DATE, 20);
        Date date2 = calendar.getTime();
        dateInterval.setFirstDate(date2);

        res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord3(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests8() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
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

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
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

        res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests9() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalizationDate(dateInterval);

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

        dateInterval.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);

        res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(String, SortingOrder, int, int,
     ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests10() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        filter.setPrizeLowerBound(new Integer(300));

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord3(res.get(0));

    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(String, SortingOrder, int, int,
      ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests11() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        filter.setPrizeUpperBound(new Integer(300));

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, -1, -1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 2, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));

    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(String, SortingOrder, int, int,
      ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests12() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        filter.setPrizeUpperBound(new Integer(300));

        List<ActiveContestDTO> res = impl.retrieveActiveContests(null, null, 1, 1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord1(res.get(0));

        res = impl.retrieveActiveContests(null, null, 2, 1, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 1, res.size());

        checkRecord2(res.get(0));

        res = impl.retrieveActiveContests(null, null, 2, 2, filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code>.<br>
     * The pageNumber is negative and not -1.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveActiveContests_PageNumberNegative() throws Exception {
        impl.retrieveActiveContests(null, null, -2, -1, new ActiveContestFilter());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code>.<br>
     * The pageSize is negative and not -1.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveActiveContests_PageSizeNegative() throws Exception {
        impl.retrieveActiveContests(null, null, -1, -2, new ActiveContestFilter());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>retrieveActiveContests(String, SortingOrder, int, int, ActiveContestFilter)</code>.<br>
     * The filter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveActiveContests_FilterNull() throws Exception {
        impl.retrieveActiveContests(null, null, -1, -1, null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveActiveContests(ActiveContestFilter)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveActiveContests_OnlyFilter() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
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

        List<ActiveContestDTO> res = impl.retrieveActiveContests(filter);
        assertNotNull("The return list should not be null ", res);
        assertEquals("The return list's size should be same as ", 3, res.size());

        checkRecord1(res.get(0));
        checkRecord2(res.get(1));
        checkRecord3(res.get(2));
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveActiveContests(ActiveContestFilter)</code>.<br>
     * The filter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveActiveContests_OnlyFilter_FilterNull() throws Exception {
        impl.retrieveActiveContests(null);
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveActiveContests(ActiveContestFilter)</code>.<br>
     * The dateInterval.IntervalType is null.<br>
     * Expect ActiveContestsManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ActiveContestsManagerException.class)
    public void testRetrieveActiveContests_OnlyFilter_IntervalTypeNull() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        filter.setSubmissionEndDate(new DateIntervalSpecification());
        impl.retrieveActiveContests(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveActiveContests(ActiveContestFilter)</code>.<br>
     * The dateInterval.firstDate is null.<br>
     * Expect ActiveContestsManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ActiveContestsManagerException.class)
    public void testRetrieveActiveContests_OnlyFilter_FirstDateNull() throws Exception {
        ActiveContestFilter filter = new ActiveContestFilter();
        DateIntervalSpecification dateInterval = new DateIntervalSpecification();
        dateInterval.setIntervalType(DateIntervalType.BEFORE);
        filter.setSubmissionEndDate(dateInterval);
        impl.retrieveActiveContests(filter);
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveActiveContests(ActiveContestFilter)</code>.<br>
     * The data is invalid.<br>
     * Expect ActiveContestsManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ActiveContestsManagerException.class)
    public void testRetrieveActiveContests_OnlyFilter_DataInvalid() throws Exception {
        impl.setActiveStatusId(13);
        impl.retrieveActiveContests(new ActiveContestFilter());
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveActiveContests(ActiveContestFilter)</code>.<br>
     * The data is invalid.<br>
     * Expect ActiveContestsManagerException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ActiveContestsManagerException.class)
    public void testRetrieveActiveContests_OnlyFilter_DataInvalid2() throws Exception {
        impl.setActiveStatusId(14);
        impl.retrieveActiveContests(new ActiveContestFilter());
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetProjectNameInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetProjectNameInfoId() throws Exception {
        impl.setProjectNameInfoId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl, "projectNameInfoId");

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

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
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

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "submissionPhaseTypeId");

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

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "firstPlaceCostInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetPaymentsInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetPaymentsInfoId() throws Exception {
        impl.setPaymentsInfoId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl, "paymentsInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetDigitalRunFlagInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetDigitalRunFlagInfoId() throws Exception {
        impl.setDigitalRunFlagInfoId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "digitalRunFlagInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetReliabilityBonusCostInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetReliabilityBonusCostInfoId() throws Exception {
        impl.setReliabilityBonusCostInfoId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "reliabilityBonusCostInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetReliabilityBonusCostInfoId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetDigitalRunPointInfoId() throws Exception {
        impl.setReliabilityBonusCostInfoId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "reliabilityBonusCostInfoId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetActiveStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetActiveStatusId() throws Exception {
        impl.setActiveStatusId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl, "activeStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetFinalReviewPhaseTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFinalReviewPhaseTypeId() throws Exception {
        impl.setFinalReviewPhaseTypeId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "finalReviewPhaseTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetOpenPhaseStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetOpenPhaseStatusId() throws Exception {
        impl.setOpenPhaseStatusId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl, "openPhaseStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetSubmitterRoleId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSubmitterRoleId() throws Exception {
        impl.setSubmitterRoleId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl, "submitterRoleId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetContestSubmissionTypeId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetContestSubmissionTypeId() throws Exception {
        impl.setContestSubmissionTypeId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "contestSubmissionTypeId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetActiveSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetActiveSubmissionStatusId() throws Exception {
        impl.setActiveSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "activeSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetFailedScreeningSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFailedScreeningSubmissionStatusId() throws Exception {
        impl.setFailedScreeningSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "failedScreeningSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetFailedReviewSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetFailedReviewSubmissionStatusId() throws Exception {
        impl.setFailedReviewSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "failedReviewSubmissionStatusId");

        assertEquals("The return value should be same as ", 999, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>SetCompletedWithoutWinSubmissionStatusId(long)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetCompletedWithoutWinSubmissionStatusId() throws Exception {
        impl.setCompletedWithoutWinSubmissionStatusId(999);

        long result = (Long) TestHelper.getField(ActiveContestsManagerImpl.class, impl,
            "completedWithoutWinSubmissionStatusId");

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
     * The failedScreeningSubmissionStatusId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_FailedScreeningSubmissionStatusIdNegative() throws Exception {
        impl.setFailedScreeningSubmissionStatusId(-1);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The firstPlaceCostInfoId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_FirstPlaceCostInfoIdNegative() throws Exception {
        impl.setFirstPlaceCostInfoId(-1);

        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The failedReviewSubmissionStatusId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_FailedReviewSubmissionStatusIdNegative() throws Exception {
        impl.setFailedReviewSubmissionStatusId(-1);

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
     * The digitalRunFlagInfoId is negative.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_DigitalRunFlagInfoIdNegative() throws Exception {
        impl.setDigitalRunFlagInfoId(-1);

        impl.checkConfiguration();
    }

}
