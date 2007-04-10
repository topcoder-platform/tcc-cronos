/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.company.Company}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class CompanyTest extends TestCase {
    /**
     * <p>
     * Represents the company for tesing.
     * </p>
     */
    private Company company;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        company = new Company();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        company = null;
    }

    /**
     * Accuracy test for <code>{@link Company#Company()}</code>.
     */
    public void testConstructor() {
        assertNotNull("failed to create Company", company);
    }

    /**
     * Accuracy test for <code>{@link Company#getAlgorithmName()}</code>.
     * <p>
     * Expects the same which is set.
     * </p>
     */
    public void testMethodGetAlgorithmName() {
        company.setAlgorithmName("algorithmName");
        assertEquals("failed to set/get algorithmName", "algorithmName", company.getAlgorithmName());
    }

    /**
     * Accuracy test for <code>{@link Company#getContact()}</code>.
     * <p>
     * Expects the same which is set.
     * </p>
     */
    public void testMethodGetContact() {
        company.setContact(new Contact());
        assertNotNull("failed to get/set Contact", company.getContact());
    }

    /**
     * Accuracy test for <code>{@link Company#getPassCode()}</code>.
     * <p>
     * Expects the same which is set.
     * </p>
     */
    public void testMethodGetPassCode() {
        company.setPassCode("passCode");
        assertEquals("failed to set/get passCode", "passCode", company.getPassCode());
    }

    /**
     * Accuracy test for <code>{@link Company#getCompanyName()}</code>.
     * <p>
     * Expects the same which is set.
     * </p>
     */
    public void testMethodGetCompanyName() {
        company.setCompanyName("companyName");
        assertEquals("failed to set/get companyName", "companyName", company.getCompanyName());
    }

    /**
     * Accuracy test for <code>{@link Company#getAddress()}</code>.
     * <p>
     * Expects the same which is set.
     * </p>
     */
    public void testMethodGetAddress() {
        company.setAddress(new Address());
        assertNotNull("failed to set/get address", company.getAddress());
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CompanyTest.class);
    }
}
