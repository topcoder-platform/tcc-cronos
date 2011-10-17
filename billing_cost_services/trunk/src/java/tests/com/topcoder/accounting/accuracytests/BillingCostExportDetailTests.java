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

import com.topcoder.accounting.entities.dao.BillingCostExportDetail;


/**
 * Accuracy tests of BillingCostExportDetail.
 *
 * @author gjw99
 * @version 1.0
 */
public class BillingCostExportDetailTests {
    /**
     * <p>Represents the BillingCostExportDetail used in tests.</p>
     */
    private BillingCostExportDetail instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new BillingCostExportDetail();
        instance.setBillingAmount(1f);
        instance.setId(2L);
        instance.setBillingCostExportId(3L);
        instance.setContestId(4L);
        instance.setContestName("contestName");
        instance.setCustomer("customer");
        instance.setInQuickBooks(true);
        instance.setInvoiceNumber("number");
        instance.setPaymentDetailId(5L);
        instance.setPaymentType("type");
        instance.setProjectInfoTypeId(6L);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(2011, 10, 10, 10, 10, 10);
        instance.setQuickBooksImportTimestamp(cal.getTime());
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
        assertTrue("return result is incorrect", json.indexOf("\"quickBooksImportTimestamp\":\"Thu Nov 10 10:10:10 GMT 2011\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"contestName\":\"contestName\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"billingCostExportId\":3") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"paymentType\":\"type\"") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
        instance = new BillingCostExportDetail();

        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"quickBooksImportTimestamp\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"contestName\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"billingCostExportId\":0") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"paymentType\":null") > -1);
    }
}
