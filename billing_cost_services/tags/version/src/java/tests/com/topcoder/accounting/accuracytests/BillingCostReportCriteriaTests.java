/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;


/**
 * Accuracy tests of BillingCostReportCriteria.
 *
 * @author gjw99
 * @version 1.0
 */
public class BillingCostReportCriteriaTests {
    /**
     * <p>Represents the BillingCostReportCriteria used in tests.</p>
     */
    private BillingCostReportCriteria instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new BillingCostReportCriteria();

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 10, 10, 10, 10, 10);
        instance.setEndDate(cal.getTime());

        Calendar cal0 = Calendar.getInstance();
        cal0.set(2010, 10, 10, 10, 10, 10);
        instance.setStartDate(cal0.getTime());

        instance.setBillingAccountId(1L);
        instance.setContestTypeIds(new long[] { 2L, 3L });
        instance.setCustomerId(4L);
        instance.setProjectId(5L);
        instance.setStatusIds(new long[] { 6L, 7L });
    }

    /**
     * <p>Cleans up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString1() throws Exception {
        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"statusIds\":[6,7]") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"projectId\":5") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"contestTypeIds\":[2,3]") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
        instance = new BillingCostReportCriteria();

        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"statusIds\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"projectId\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"contestTypeIds\":null") > -1);
    }
}
