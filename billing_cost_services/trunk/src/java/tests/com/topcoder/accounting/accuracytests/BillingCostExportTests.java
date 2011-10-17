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

import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dao.PaymentArea;

/**
 * Accuracy tests of BillingCostExport.
 *
 * @author gjw99
 * @version 1.0
 */
public class BillingCostExportTests {
    /**
     * <p>Represents the BillingCostExport used in tests.</p>
     */
    private BillingCostExport instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new BillingCostExport();
        instance.setAccountantName("account_name");
        instance.setId(2L);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(2011, 10, 10, 10, 10, 10);
        instance.setTimestamp(cal.getTime());
        instance.setRecordsCount(2);

        PaymentArea area = new PaymentArea();
        area.setId(2L);
        area.setName("name");
        instance.setPaymentArea(area);
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
        assertTrue("return result is incorrect", json.indexOf("\"recordsCount\":2") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"timestamp\":\"Thu Nov 10 10:10:10 GMT 2011\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"paymentArea\":{\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"accountantName\":\"account_name\"") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
        instance = new BillingCostExport();

        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"recordsCount\":0") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"timestamp\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"paymentArea\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"accountantName\":null") > -1);
    }
}
