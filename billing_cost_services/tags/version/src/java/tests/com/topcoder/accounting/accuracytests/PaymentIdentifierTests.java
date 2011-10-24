/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dto.PaymentIdentifier;


/**
 * Accuracy tests of PaymentIdentifier.
 *
 * @author gjw99
 * @version 1.0
 */
public class PaymentIdentifierTests {
    /**
     * <p>Represents the PaymentIdentifier used in tests.</p>
     */
    private PaymentIdentifier instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new PaymentIdentifier();
        instance.setContestId(1l);
        instance.setPaymentDetailId(2l);
        instance.setProjectInfoTypeId(3l);
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
        assertTrue("return result is incorrect", json.indexOf("\"paymentDetailId\":2") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"projectInfoTypeId\":3") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"contestId\":1") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
    	instance = new PaymentIdentifier();
        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"paymentDetailId\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"projectInfoTypeId\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"contestId\":null") > -1);
    }
}
