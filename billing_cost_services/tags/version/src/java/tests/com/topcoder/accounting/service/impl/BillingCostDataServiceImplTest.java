/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.service.BillingCostDataService;

/**
 * <p>
 * Unit tests for class <code>BillingCostDataServiceImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostDataServiceImplTest extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>BillingCostDataServiceImpl</code> instance used to test against.
     * </p>
     */
    private BillingCostDataServiceImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostDataServiceImplTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        impl = (BillingCostDataServiceImpl) APP_CONTEXT.getBean("billingCostDataService");
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BillingCostDataServiceImpl</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BillingCostDataService);
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseService);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostDataServiceImpl()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

//    /**
//     * <p>
//     * Accuracy test for the method <code>getBillingCostReport(BillingCostReportCriteria, int, int)</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test
//    public void testGetBillingCostReport() throws Exception {
//        impl.getBillingCostReport(null, -1, -1);
//    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostReport(BillingCostReportCriteria, int, int)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostReport2() throws Exception {
        BillingCostReportCriteria criteria = new BillingCostReportCriteria();
        criteria.setBillingAccountId(1L);
        criteria.setContestTypeIds(new long[]{1,2,3});
        criteria.setCustomerId(1L);
        criteria.setProjectId(1L);
        criteria.setStartDate(new Date());
        criteria.setEndDate(new Date());

        impl.getBillingCostReport(criteria, -1, -1);
    }

//    /**
//     * <p>
//     * Failure test for the method <code>getBillingCostReport(BillingCostReportCriteria, int, int)</code>.<br>
//     * The pageNo is zero.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testGetBillingCostReport_PagenoZero() throws Exception {
//        impl.getBillingCostReport(null, 0, 1);
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>getBillingCostReport(BillingCostReportCriteria, int, int)</code>.<br>
//     * The pageNo is negative.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testGetBillingCostReport_PagenoNegative() throws Exception {
//        impl.getBillingCostReport(null, -2, 1);
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>getBillingCostReport(BillingCostReportCriteria, int, int)</code>.<br>
//     * The pageSize is zero.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testGetBillingCostReport_PagesizeZero() throws Exception {
//        impl.getBillingCostReport(null, 1, 0);
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>getBillingCostReport(BillingCostReportCriteria, int, int)</code>.<br>
//     * The pageSize is negative.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testGetBillingCostReport_PagesizeNegative() throws Exception {
//        impl.getBillingCostReport(null, 1, -1);
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>exportBillingCostData(List, long, TCSubject)</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test
//    public void testExportBillingCostData() throws Exception {
//        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
//        PaymentIdentifier pid = new PaymentIdentifier();
//        pid.setContestId(1L);
//        pid.setPaymentDetailId(2L);
//        pid.setProjectInfoTypeId(3L);
//        paymentIds.add(pid);
//
//        impl.exportBillingCostData(paymentIds, 4L, new TCSubject(3));
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>exportBillingCostData(List, long, TCSubject)</code>.<br>
//     * The list is null.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testExportBillingCostData_ListNull() throws Exception {
//        impl.exportBillingCostData(null, 4L, new TCSubject(3));
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>exportBillingCostData(List, long, TCSubject)</code>.<br>
//     * The list is empty.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testExportBillingCostData_ListEmpty() throws Exception {
//        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
//        impl.exportBillingCostData(paymentIds, 4L, new TCSubject(3));
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>exportBillingCostData(List, long, TCSubject)</code>.<br>
//     * The list contains null element.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testExportBillingCostData_ListNullElement() throws Exception {
//        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
//        PaymentIdentifier pid = new PaymentIdentifier();
//        pid.setContestId(1L);
//        pid.setPaymentDetailId(2L);
//        pid.setProjectInfoTypeId(3L);
//        paymentIds.add(pid);
//        paymentIds.add(null);
//        impl.exportBillingCostData(paymentIds, 4L, new TCSubject(3));
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>exportBillingCostData(List, long, TCSubject)</code>.<br>
//     * The user is null.<br>
//     * Expect IllegalArgumentException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testExportBillingCostData_UserNull() throws Exception {
//        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
//        PaymentIdentifier pid = new PaymentIdentifier();
//        pid.setContestId(1L);
//        pid.setPaymentDetailId(2L);
//        pid.setProjectInfoTypeId(3L);
//        paymentIds.add(pid);
//        impl.exportBillingCostData(paymentIds, 4L, null);
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>afterPropertiesSet()</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test
//    public void testAfterPropertiesSet() throws Exception {
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The ProjectCategoryIds is null.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_ProjectCategoryIdsNull() throws Exception {
//        impl.setProjectCategoryIds(null);
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The ProjectCategoryIds is empty.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_ProjectCategoryIdsEmpty() throws Exception {
//        impl.setProjectCategoryIds(new ArrayList<Long>());
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The ProjectCategoryIds contains null element.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_ProjectCategoryIdsNullElement() throws Exception {
//        impl = new BillingCostDataServiceImpl();
//        List<Long> ids1 = new ArrayList<Long>();
//        ids1.add(new Long(1));
//        ids1.add(null);
//
//        impl.setProjectCategoryIds(ids1);
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StudioProjectCategoryIds is null.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StudioProjectCategoryIdsNull() throws Exception {
//        impl.setStudioProjectCategoryIds(null);
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StudioProjectCategoryIds is empty.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StudioProjectCategoryIdsEmpty() throws Exception {
//        impl.setStudioProjectCategoryIds(new ArrayList<Long>());
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StudioProjectCategoryIds contains null element.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StudioProjectCategoryIdsNullElement() throws Exception {
//        List<Long> ids2 = new ArrayList<Long>();
//        ids2.add(new Long(2));
//        ids2.add(null);
//
//        impl.setStudioProjectCategoryIds(ids2);
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StatusMapping is null.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StatusMappingNull() throws Exception {
//        impl.setStatusMapping(null);
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StatusMapping is empty.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StatusMappingEmpty() throws Exception {
//        impl.setStatusMapping(new HashMap<String, Long>());
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StatusMapping contains null key.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StatusMappingNullKey() throws Exception {
//        Map<String, Long> map = new HashMap<String, Long>();
//        map.put(null, new Long(4));
//        impl.setStatusMapping(new HashMap<String, Long>());
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StatusMapping contains empty key.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StatusMappingEmptyKey() throws Exception {
//        Map<String, Long> map = new HashMap<String, Long>();
//        map.put("  ", new Long(4));
//        impl.setStatusMapping(new HashMap<String, Long>());
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
//     * The StatusMapping contains empty value.<br>
//     * Expect BillingCostConfigurationException.
//     * </p>
//     *
//     * @throws Exception
//     *             to JUnit
//     */
//    @Test(expected = BillingCostConfigurationException.class)
//    public void testAfterPropertiesSet_StatusMappingEmptyValue() throws Exception {
//        Map<String, Long> map = new HashMap<String, Long>();
//        map.put("key", null);
//        impl.setStatusMapping(new HashMap<String, Long>());
//
//        impl.afterPropertiesSet();
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>getProjectCategoryIds()</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     */
//    @Test
//    public void testGetProjectCategoryIds() {
//        impl = new BillingCostDataServiceImpl();
//        assertNull("The initial value should be null", impl.getProjectCategoryIds());
//        List<Long> expect = new ArrayList<Long>();
//        impl.setProjectCategoryIds(expect);
//        assertEquals("The ids should be same as", expect, impl.getProjectCategoryIds());
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>setProjectCategoryIds(List)</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     */
//    @Test
//    public void testSetProjectCategoryIds() {
//        impl = new BillingCostDataServiceImpl();
//        assertNull("The initial value should be null", impl.getProjectCategoryIds());
//        List<Long> expect = new ArrayList<Long>();
//        impl.setProjectCategoryIds(expect);
//        assertEquals("The ids should be same as", expect, impl.getProjectCategoryIds());
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>getStudioProjectCategoryIds()</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     */
//    @Test
//    public void testGetStudioProjectCategoryIds() {
//        impl = new BillingCostDataServiceImpl();
//        assertNull("The initial value should be null", impl.getStudioProjectCategoryIds());
//        List<Long> expect = new ArrayList<Long>();
//        impl.setStudioProjectCategoryIds(expect);
//        assertEquals("The ids should be same as", expect, impl.getStudioProjectCategoryIds());
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>setStudioProjectCategoryIds(List)</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     */
//    @Test
//    public void testSetStudioProjectCategoryIds() {
//        impl = new BillingCostDataServiceImpl();
//        assertNull("The initial value should be null", impl.getStudioProjectCategoryIds());
//        List<Long> expect = new ArrayList<Long>();
//        impl.setStudioProjectCategoryIds(expect);
//        assertEquals("The ids should be same as", expect, impl.getStudioProjectCategoryIds());
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>getStatusMapping()</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     */
//    @Test
//    public void testGetStatusMapping() {
//        impl = new BillingCostDataServiceImpl();
//        assertNull("The initial value should be null", impl.getStatusMapping());
//        Map<String, Long> expect = new HashMap<String, Long>();
//        impl.setStatusMapping(expect);
//        assertEquals("The ids should be same as", expect, impl.getStatusMapping());
//    }
//
//    /**
//     * <p>
//     * Accuracy test for the method <code>setStatusMapping(Map)</code> .<br>
//     * Expects no error occurs.
//     * </p>
//     */
//    @Test
//    public void testSetStatusMapping() {
//        impl = new BillingCostDataServiceImpl();
//        assertNull("The initial value should be null", impl.getStatusMapping());
//        Map<String, Long> expect = new HashMap<String, Long>();
//        impl.setStatusMapping(expect);
//        assertEquals("The ids should be same as", expect, impl.getStatusMapping());
//    }
}
