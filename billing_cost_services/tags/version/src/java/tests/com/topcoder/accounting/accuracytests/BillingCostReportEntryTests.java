/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dto.BillingCostReportEntry;


/**
 * Accuracy tests of BillingCostReportEntry.
 *
 * @author gjw99
 * @version 1.0
 */
public class BillingCostReportEntryTests {
    /**
     * <p>Represents the BillingCostReportEntry used in tests.</p>
     */
    private BillingCostReportEntry instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new BillingCostReportEntry();

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 10, 10, 10, 10, 10);
        instance.setLaunchDate(cal.getTime());

        Calendar cal0 = Calendar.getInstance();
        cal0.set(2010, 10, 10, 10, 10, 10);

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2012, 10, 10, 10, 10, 10);
        instance.setPaymentDate(cal0.getTime());
        instance.setAmount(1f);
        instance.setBillingAccount("billingAccount");
        instance.setCategory("category");
        instance.setCompletionDate(cal1.getTime());
        instance.setContestId(2L);
        instance.setContestName("contestName");
        instance.setCustomer("customer");
        instance.setExported(true);
        instance.setInvoiceNumber("invoiceNumber");
        instance.setPaymentDetailId(3L);
        instance.setPaymentType("paymentType");
        instance.setProjectName("projectName");
        instance.setProjectInfoTypeId(4L);
        instance.setReferenceId("referenceId");
        instance.setStatus("status");
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
        assertTrue("return result is incorrect", json.indexOf("\"customer\":\"customer\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"referenceId\":\"referenceId\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"amount\":1") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"exported\":true") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
        instance = new BillingCostReportEntry();

        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"customer\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"referenceId\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"amount\":0") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"exported\":false") > -1);
    }
}
