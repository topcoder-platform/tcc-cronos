/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>AccountingAuditRecord</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class AccountingAuditRecordTest {
    /**
     * <p>
     * Represents the <code>AccountingAuditRecord</code> instance used to test against.
     * </p>
     */
    private AccountingAuditRecord impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AccountingAuditRecordTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new AccountingAuditRecord();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>AccountingAuditRecord</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof IdentifiableEntity);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AccountingAuditRecord()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAction()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetAction() {
        assertNull("The initial value should be null", impl.getAction());
        String expect = "test";
        impl.setAction(expect);
        assertEquals("The id should be same as", expect, impl.getAction());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAction(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetAction() {
        assertNull("The initial value should be null", impl.getAction());
        String expect = "test";
        impl.setAction(expect);
        assertEquals("The id should be same as", expect, impl.getAction());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUserName() {
        assertNull("The initial value should be null", impl.getUserName());
        String expect = "test";
        impl.setUserName(expect);
        assertEquals("The id should be same as", expect, impl.getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUserName() {
        assertNull("The initial value should be null", impl.getUserName());
        String expect = "test";
        impl.setUserName(expect);
        assertEquals("The id should be same as", expect, impl.getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTimestamp()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetTimestamp() {
        assertNull("The initial value should be null", impl.getTimestamp());
        Date expect = new Date();
        impl.setTimestamp(expect);
        assertEquals("The id should be same as", expect, impl.getTimestamp());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTimestamp(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetTimestamp() {
        assertNull("The initial value should be null", impl.getTimestamp());
        Date expect = new Date();
        impl.setTimestamp(expect);
        assertEquals("The id should be same as", expect, impl.getTimestamp());
    }

    /**
     * <p>
     * Accuracy test for the method <code>toJSONString()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testToJSONString() {
        impl.setId(4);
        String expect = "{\"userName\":null,\"action\":null,\"timestamp\":null,\"id\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());

        impl.setAction("add");
        impl.setUserName("bob");
        expect = "{\"userName\":\"bob\",\"action\":\"add\",\"timestamp\":null,\"id\":4}";
        assertEquals("The json string should be same as", expect, impl.toJSONString());
    }

}
