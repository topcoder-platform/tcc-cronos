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

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;


/**
 * Accuracy tests of AccountingAuditRecord.
 *
 * @author gjw99
 * @version 1.0
 */
public class AccountingAuditRecordTests {
    /**
     * <p>Represents the AccountingAuditRecord used in tests.</p>
     */
    private AccountingAuditRecord instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new AccountingAuditRecord();
        instance.setAction("action");
        instance.setId(2L);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(2011, 10, 10, 10, 10, 10);
        instance.setTimestamp(cal.getTime());
        instance.setUserName("user");
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
        assertTrue("return result is incorrect", json.indexOf("\"userName\":\"user\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"action\":\"action\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"timestamp\":\"Thu Nov 10 10:10:10 GMT 2011\"") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"id\":2") > -1);
    }

    /**
     * <p>Accuracy test toJSONString method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_toJSONString2() throws Exception {
    	instance = new AccountingAuditRecord();
        String json = instance.toJSONString();
        assertTrue("return result is incorrect", json.indexOf("\"userName\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"action\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"timestamp\":null") > -1);
        assertTrue("return result is incorrect", json.indexOf("\"id\":0") > -1);
    }
}
