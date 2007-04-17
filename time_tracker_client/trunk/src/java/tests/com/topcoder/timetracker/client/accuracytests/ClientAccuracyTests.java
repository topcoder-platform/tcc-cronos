/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;

/**
 * <p>
 * Accuracy Unit test cases for Client.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientAccuracyTests extends TestCase {
    /**
     * <p>
     * Client instance for testing.
     * </p>
     */
    private Client instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new Client();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ClientAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor Client#Client() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Client instance.", instance);
    }

    /**
     * <p>
     * Tests Client#getCompanyId() for accuracy.
     * </p>
     */
    public void testGetCompanyId() {
        instance.setCompanyId(8);
        assertEquals("Failed to get the company id.", 8, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests Client#setCompanyId(long) for accuracy.
     * </p>
     */
    public void testSetCompanyId() {
        instance.setCompanyId(8);
        assertEquals("Failed to set the company id.", 8, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests Client#getSalesTax() for accuracy.
     * </p>
     */
    public void testGetSalesTax() {
        instance.setSalesTax(8.0);
        assertEquals("Failed to get the sales tax.", 8.0, instance.getSalesTax(), 0.01);
    }

    /**
     * <p>
     * Tests Client#setSalesTax(double) for accuracy.
     * </p>
     */
    public void testSetSalesTax() {
        instance.setSalesTax(8.0);
        assertEquals("Failed to set the sales tax.", 8.0, instance.getSalesTax(), 0.01);
    }

    /**
     * <p>
     * Tests Client#getStartDate() for accuracy.
     * </p>
     */
    public void testGetStartDate() {
        instance.setStartDate(new Date(1000));
        assertEquals("Failed to get the start date.", new Date(1000), instance.getStartDate());
    }

    /**
     * <p>
     * Tests Client#setStartDate(Date) for accuracy.
     * </p>
     */
    public void testSetStartDate() {
        instance.setStartDate(new Date(1000));
        assertEquals("Failed to set the start date.", new Date(1000), instance.getStartDate());
    }

    /**
     * <p>
     * Tests Client#getEndDate() for accuracy.
     * </p>
     */
    public void testGetEndDate() {
        instance.setEndDate(new Date(1000));
        assertEquals("Failed to get the end date.", new Date(1000), instance.getEndDate());
    }

    /**
     * <p>
     * Tests Client#setEndDate(Date) for accuracy.
     * </p>
     */
    public void testSetEndDate() {
        instance.setEndDate(new Date(1000));
        assertEquals("Failed to set the end date.", new Date(1000), instance.getEndDate());
    }

    /**
     * <p>
     * Tests Client#getContact() for accuracy.
     * </p>
     */
    public void testGetContact() {
        Contact contact = new Contact();
        instance.setContact(contact);
        assertSame("Failed to get the contact.", contact, instance.getContact());
    }

    /**
     * <p>
     * Tests Client#setContact(Contact) for accuracy.
     * </p>
     */
    public void testSetContact() {
        Contact contact = new Contact();
        instance.setContact(contact);
        assertSame("Failed to set the contact.", contact, instance.getContact());
    }

    /**
     * <p>
     * Tests Client#setProjects(Project[]) for accuracy.
     * </p>
     */
    public void testSetProjects() {
        Project project = new Project();
        Project[] projects = new Project[] {project};
        instance.setProjects(projects);
        assertSame("Failed to set the projects.", project, instance.getProjects()[0]);
    }

    /**
     * <p>
     * Tests Client#getProjects() for accuracy.
     * </p>
     */
    public void testGetProjects() {
        Project project = new Project();
        Project[] projects = new Project[] {project};
        instance.setProjects(projects);
        assertSame("Failed to get the projects.", project, instance.getProjects()[0]);
    }

    /**
     * <p>
     * Tests Client#getPaymentTerm() for accuracy.
     * </p>
     */
    public void testGetPaymentTerm() {
        PaymentTerm term = new PaymentTerm();
        instance.setPaymentTerm(term);
        assertSame("Failed to get the payment term.", term, instance.getPaymentTerm());
    }

    /**
     * <p>
     * Tests Client#setPaymentTerm(PaymentTerm) for accuracy.
     * </p>
     */
    public void testSetPaymentTerm() {
        PaymentTerm term = new PaymentTerm();
        instance.setPaymentTerm(term);
        assertSame("Failed to set the payment term.", term, instance.getPaymentTerm());
    }

    /**
     * <p>
     * Tests Client#getAddress() for accuracy.
     * </p>
     */
    public void testGetAddress() {
        Address address = new Address();
        instance.setAddress(address);
        assertSame("Failed to get the address.", address, instance.getAddress());
    }

    /**
     * <p>
     * Tests Client#getName() for accuracy.
     * </p>
     */
    public void testGetName() {
        instance.setName("name");
        assertEquals("Failed to get the name.", "name", instance.getName());
    }

    /**
     * <p>
     * Tests Client#setName(String) for accuracy.
     * </p>
     */
    public void testSetName() {
        instance.setName("name");
        assertEquals("Failed to set the name.", "name", instance.getName());
    }

    /**
     * <p>
     * Tests Client#isActive() for accuracy.
     * </p>
     */
    public void testIsActive() {
        instance.setActive(true);
        assertTrue("Failed to get the active.", instance.isActive());
    }

    /**
     * <p>
     * Tests Client#setActive(boolean) for accuracy.
     * </p>
     */
    public void testSetActive() {
        instance.setActive(true);
        assertTrue("Failed to set the active.", instance.isActive());
    }

    /**
     * <p>
     * Tests Client#setAddress(Address) for accuracy.
     * </p>
     */
    public void testSetAddress() {
        Address address = new Address();
        instance.setAddress(address);
        assertSame("Failed to set the address.", address, instance.getAddress());
    }

}