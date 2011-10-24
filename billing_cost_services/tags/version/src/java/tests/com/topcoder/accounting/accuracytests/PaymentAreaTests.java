/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dao.PaymentArea;


/**
 * Accuracy tests of PaymentArea.
 *
 * @author gjw99
 * @version 1.0
 */
public class PaymentAreaTests {
    /**
     * <p>Represents the PaymentArea used in tests.</p>
     */
    private PaymentArea instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new PaymentArea();
        instance.setId(2L);
        instance.setName("name");
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
        assertTrue("return result is incorrect", json.indexOf("\"name\":\"name\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"id\":2") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
        instance = new PaymentArea();

        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"name\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"id\":0") > -1);
    }
}
