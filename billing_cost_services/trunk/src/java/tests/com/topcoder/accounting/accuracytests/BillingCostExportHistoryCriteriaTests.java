/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;


/**
 * Accuracy tests of BillingCostExportHistoryCriteria.
 *
 * @author gjw99
 * @version 1.0
 */
public class BillingCostExportHistoryCriteriaTests {
    /**
     * <p>Represents the BillingCostExportHistoryCriteria used in tests.</p>
     */
    private BillingCostExportHistoryCriteria instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new BillingCostExportHistoryCriteria();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(2011, 10, 10, 10, 10, 10);
        instance.setEndDate(cal.getTime());

        Calendar cal0 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal0.set(2010, 10, 10, 10, 10, 10);
        instance.setStartDate(cal0.getTime());

        instance.setAccountantName("accountantName");
        instance.setPaymentAreaId(2L);
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
        assertTrue("return result is incorrect", json.indexOf("\"paymentAreaId\":2") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"startDate\":\"Wed Nov 10 10:10:10 GMT 2010\"") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
        instance = new BillingCostExportHistoryCriteria();

        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"paymentAreaId\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"startDate\":null") > -1);
    }
}
