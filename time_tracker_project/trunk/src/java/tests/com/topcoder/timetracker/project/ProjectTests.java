/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.Date;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for Project.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectTests extends TestCase {
    /**
     * <p>
     * The Project instance for testing.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        project = new Project();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        project = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectTests.class);
    }

    /**
     * <p>
     * Tests ctor Project#Project() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created Project instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new Project instance.", project);
    }

    /**
     * <p>
     * Tests Project#getStartDate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getStartDate() is correct.
     * </p>
     */
    public void testGetStartDate() {
        Date startDate = new Date();
        project.setStartDate(startDate);
        assertSame("Failed to get the start date correctly.", startDate, project.getStartDate());
    }

    /**
     * <p>
     * Tests Project#setStartDate(Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setStartDate(Date) is correct.
     * </p>
     */
    public void testSetStartDate() {
        Date startDate = new Date();
        project.setStartDate(startDate);
        assertSame("Failed to set the start date correctly.", startDate, project.getStartDate());
    }

    /**
     * <p>
     * Tests Project#setStartDate(Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when startDate is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetStartDate_NullStartDate() {
        try {
            project.setStartDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getEndDate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getEndDate() is correct.
     * </p>
     */
    public void testGetEndDate() {
        Date endDate = new Date();
        project.setEndDate(endDate);
        assertSame("Failed to get the end date correctly.", endDate, project.getEndDate());
    }

    /**
     * <p>
     * Tests Project#setEndDate(Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setEndDate(Date) is correct.
     * </p>
     */
    public void testSetEndDate() {
        Date endDate = new Date();
        project.setEndDate(endDate);
        assertSame("Failed to set the end date correctly.", endDate, project.getEndDate());
    }

    /**
     * <p>
     * Tests Project#setEndDate(Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when endDate is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetEndDate_NullEndDate() {
        try {
            project.setEndDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getCompanyId() is correct.
     * </p>
     */
    public void testGetCompanyId() {
        project.setCompanyId(8);
        assertEquals("Failed to get the company id correctly.", 8, project.getCompanyId());
    }

    /**
     * <p>
     * Tests Project#setCompanyId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setCompanyId(long) is correct.
     * </p>
     */
    public void testSetCompanyId() {
        project.setCompanyId(8);
        assertEquals("Failed to set the company id correctly.", 8, project.getCompanyId());
    }

    /**
     * <p>
     * Tests Project#setCompanyId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCompanyId_NegativeId() {
        try {
            project.setCompanyId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getSalesTax() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getSalesTax() is correct.
     * </p>
     */
    public void testGetSalesTax() {
        project.setSalesTax(8.0);
        assertEquals("Failed to get the sales tax correctly.", 8.0, project.getSalesTax(), 0.001);
    }

    /**
     * <p>
     * Tests Project#setSalesTax(double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setSalesTax(double) is correct.
     * </p>
     */
    public void testSetSalesTax() {
        project.setSalesTax(8.0);
        assertEquals("Failed to set the sales tax correctly.", 8.0, project.getSalesTax(), 0.001);
    }

    /**
     * <p>
     * Tests Project#setSalesTax(double) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when salesTax is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetSalesTax_Negative() {
        try {
            project.setSalesTax(-8.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getContact() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getContact() is correct.
     * </p>
     */
    public void testGetContact() {
        Contact contact = new Contact();
        project.setContact(contact);
        assertSame("Failed to get the contact correctly.", contact, project.getContact());
    }

    /**
     * <p>
     * Tests Project#setContact(Contact) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setContact(Contact) is correct.
     * </p>
     */
    public void testSetContact() {
        Contact contact = new Contact();
        project.setContact(contact);
        assertSame("Failed to set the contact correctly.", contact, project.getContact());
    }

    /**
     * <p>
     * Tests Project#setContact(Contact) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when contact is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetContact_NullContact() {
        try {
            project.setContact(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getClientId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getClientId() is correct.
     * </p>
     */
    public void testGetClientId() {
        project.setClientId(8);
        assertEquals("Failed to set the client id correctly.", 8, project.getClientId());
    }

    /**
     * <p>
     * Tests Project#setClientId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setClientId(long) is correct.
     * </p>
     */
    public void testSetClientId() {
        project.setClientId(8);
        assertEquals("Failed to set the client id correctly.", 8, project.getClientId());
    }

    /**
     * <p>
     * Tests Project#setClientId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when clientId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetClientId_NegativeClientId() {
        try {
            project.setClientId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getActive() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getActive() is correct.
     * </p>
     */
    public void testGetActive() {
        project.setActive(true);
        assertTrue("Failed to get the active correctly.", project.getActive());
    }

    /**
     * <p>
     * Tests Project#getTerms() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getTerms() is correct.
     * </p>
     */
    public void testGetTerms() {
        PaymentTerm terms = new PaymentTerm();
        project.setTerms(terms);
        assertSame("Failed to get the terms correctly.", terms, project.getTerms());
    }

    /**
     * <p>
     * Tests Project#setTerms(PaymentTerm) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setTerms(PaymentTerm) is correct.
     * </p>
     */
    public void testSetTerms() {
        PaymentTerm terms = new PaymentTerm();
        project.setTerms(terms);
        assertSame("Failed to set the terms correctly.", terms, project.getTerms());
    }

    /**
     * <p>
     * Tests Project#setTerms(PaymentTerm) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when terms is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetTerms_NullTerms() {
        try {
            project.setTerms(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#getAddress() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getAddress() is correct.
     * </p>
     */
    public void testGetAddress() {
        Address address = new Address();
        project.setAddress(address);
        assertSame("Failed to get the address correctly.", address, project.getAddress());
    }

    /**
     * <p>
     * Tests Project#getName() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getName() is correct.
     * </p>
     */
    public void testGetName() {
        project.setName("name");
        assertEquals("Failed to get the name correctly.", "name", project.getName());
    }

    /**
     * <p>
     * Tests Project#setName(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setName(String) is correct.
     * </p>
     */
    public void testSetName() {
        project.setName("name");
        assertEquals("Failed to set the name correctly.", "name", project.getName());
    }

    /**
     * <p>
     * Tests Project#setName(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetName_NullName() {
        try {
            project.setName(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#setName(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when name is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetName_EmptyName() {
        try {
            project.setName(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#isActive() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#isActive() is correct.
     * </p>
     */
    public void testIsActive() {
        project.setActive(true);
        assertTrue("Failed to get the active correctly.", project.isActive());
    }

    /**
     * <p>
     * Tests Project#setActive(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setActive(boolean) is correct.
     * </p>
     */
    public void testSetActive() {
        project.setActive(true);
        assertTrue("Failed to set the active correctly.", project.isActive());
    }

    /**
     * <p>
     * Tests Project#getDescription() for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#getDescription() is correct.
     * </p>
     */
    public void testGetDescription() {
        project.setDescription("description");
        assertEquals("Failed to get the description correctly.", "description", project.getDescription());
    }

    /**
     * <p>
     * Tests Project#setDescription(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setDescription(String) is correct.
     * </p>
     */
    public void testSetDescription() {
        project.setDescription("description");
        assertEquals("Failed to set the description correctly.", "description", project.getDescription());
    }

    /**
     * <p>
     * Tests Project#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        try {
            project.setDescription(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription_EmptyDescription() {
        try {
            project.setDescription(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Project#setAddress(Address) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Project#setAddress(Address) is correct.
     * </p>
     */
    public void testSetAddress() {
        Address address = new Address();
        project.setAddress(address);
        assertSame("Failed to set the address correctly.", address, project.getAddress());
    }

    /**
     * <p>
     * Tests Project#setAddress(Address) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when address is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetAddress_NullAddress() {
        try {
            project.setAddress(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}