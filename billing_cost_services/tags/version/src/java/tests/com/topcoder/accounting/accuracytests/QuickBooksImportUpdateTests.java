/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;


/**
 * Accuracy tests of QuickBooksImportUpdate.
 *
 * @author gjw99
 * @version 1.0
 */
public class QuickBooksImportUpdateTests {
    /**
     * <p>Represents the QuickBooksImportUpdate used in tests.</p>
     */
    private QuickBooksImportUpdate instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new QuickBooksImportUpdate();
        instance.setBillingCostExportDetailIds(new long[]{1, 2});
        instance.setInvoiceNumber("invoiceNumber");
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
        assertTrue("return result is incorrect", json.indexOf("\"billingCostExportDetailIds\":[1,2]") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"invoiceNumber\":\"invoiceNumber\"") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
    	instance = new QuickBooksImportUpdate();
        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"billingCostExportDetailIds\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"invoiceNumber\":null") > -1);
    }
}
