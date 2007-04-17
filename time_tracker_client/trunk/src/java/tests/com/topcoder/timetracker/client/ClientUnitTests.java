/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;


/**
 * Unit test for the <code>Client</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientUnitTests extends TestCase {
    /** Represents eps used for float number. */
    private static final double EPS = 1e-8;

    /** Represents the private instance used for test. */
    private Client client = null;

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        client = new Client();
    }

    /**
     * <p>
     * Accuracy test for Inheritance. Should inherit from TimeTrackerBean.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritanceAccuracy() throws Exception {
        assertTrue("Client should inherit from TimeTrackerBean.", client instanceof TimeTrackerBean);
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Instance should be created.", client);
    }

    /**
     * <p>
     * Accuracy test for method <code>getCompanyId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetCompanyIdAccuracy() throws Exception {
        client.setCompanyId(2);

        assertEquals("The company should be 2.",
            ((Long) UnitTestHelper.getPrivateField(Client.class, client, "companyId")).longValue(),
            client.getCompanyId());
    }

    /**
     * <p>
     * Failure test for method <code>setCompanyId</code>. Set with zero is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanyIdWithZero() throws Exception {
        try {
            client.setCompanyId(0);
            fail("Set with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setCompanyId</code>. Set with negative is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanyIdWithNegative() throws Exception {
        try {
            client.setCompanyId(-1);
            fail("Set with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setCompanId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanIdAccuracy() throws Exception {
        client.setCompanyId(1);

        assertEquals("Company id should be set.", 1,
            ((Long) UnitTestHelper.getPrivateField(Client.class, client, "companyId")).longValue());
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>isActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testisActiveAccuracy() throws Exception {
        client.setActive(false);

        assertEquals("active should be set.",
            ((Boolean) UnitTestHelper.getPrivateField(Client.class, client, "active")).booleanValue(),
            client.isActive());
    }

    /**
     * <p>
     * Accuracy test for method <code>setActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetActiveAccuracy1() throws Exception {
        client.setActive(false);

        assertFalse("active should be false.",
            ((Boolean) UnitTestHelper.getPrivateField(Client.class, client, "active")).booleanValue());
        assertTrue("Client should be changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>setActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetActiveAccuracy2() throws Exception {
        client.setActive(true);

        assertTrue("active should be false.",
            ((Boolean) UnitTestHelper.getPrivateField(Client.class, client, "active")).booleanValue());
        assertTrue("Client should be changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getName</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNameAccuracy() throws Exception {
        client.setName("name");

        assertEquals("name should be get.", UnitTestHelper.getPrivateField(Client.class, client, "name"),
            client.getName());
    }

    /**
     * <p>
     * Failure test for method <code>setName</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetnameWithNull() throws Exception {
        try {
            client.setName(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setName</code>. Set with empty is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetnameWithEmpty() throws Exception {
        try {
            client.setName("        ");
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setName</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetNameAccuracy() throws Exception {
        client.setName("name");

        assertEquals("The name should be set.", "name", UnitTestHelper.getPrivateField(Client.class, client, "name"));
        assertTrue("Client should be changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getSalesTax</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetSalesTaxAccuracy() throws Exception {
        client.setSalesTax(1.0);

        assertEquals("The sales tax should be get.", 1.0, client.getSalesTax(), EPS);
    }

    /**
     * <p>
     * Failure test for method <code>setSalesTax</code>. Set with invalid is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetSalesTaxWith() throws Exception {
        try {
            client.setSalesTax(-1);
            fail("Set with invalid is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setSalesTax</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetSalesTaxAccuracy1() throws Exception {
        client.setSalesTax(0);

        assertEquals("the tax get should be 0.", 0, client.getSalesTax(), EPS);
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>setSalesTax</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetSalesTaxAccuracy2() throws Exception {
        client.setSalesTax(1);

        assertEquals("the tax get should be 1.", 1, client.getSalesTax(), EPS);
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getStartDate</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetStartDateAccuracy() throws Exception {
        Date currentDate = new Date();

        client.setStartDate(currentDate);

        assertEquals("The start get should be right.",
            UnitTestHelper.getPrivateField(Client.class, client, "startDate"), client.getStartDate());
    }

    /**
     * <p>
     * Failure test for method <code>setStartDate</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetStartDateWithNull() throws Exception {
        try {
            client.setStartDate(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setStartDate</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetStartDateAccuracy() throws Exception {
        Date currentDate = new Date();

        client.setStartDate(currentDate);

        assertEquals("The start get should be set.", currentDate,
            UnitTestHelper.getPrivateField(Client.class, client, "startDate"));
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getEndDate</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetEndDateAccuracy() throws Exception {
        Date currentDate = new Date();

        client.setEndDate(currentDate);

        assertEquals("The start get should be right.", UnitTestHelper.getPrivateField(Client.class, client, "endDate"),
            client.getEndDate());
    }

    /**
     * <p>
     * Failure test for method <code>setEndDate</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetEndDateWithNull() throws Exception {
        try {
            client.setEndDate(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setEndDate</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetEndDateAccuracy() throws Exception {
        Date currentDate = new Date();

        client.setEndDate(currentDate);

        assertEquals("The start get should be set.", currentDate,
            UnitTestHelper.getPrivateField(Client.class, client, "endDate"));
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getContact</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetContactAccuracy() throws Exception {
        Contact contact = new Contact();

        client.setContact(contact);

        assertEquals("The contact should be get.", UnitTestHelper.getPrivateField(Client.class, client, "contact"),
            client.getContact());
    }

    /**
     * <p>
     * Failure test for method <code>setContact</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetContactWithNull() throws Exception {
        try {
            client.setContact(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setContact</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetContactAccuracy() throws Exception {
        Contact contact = new Contact();

        client.setContact(contact);

        assertEquals("The contact should be set.", contact,
            UnitTestHelper.getPrivateField(Client.class, client, "contact"));
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getAddress</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAddressAccuracy() throws Exception {
        Address address = new Address();

        client.setAddress(address);

        assertEquals("The address should be get.", UnitTestHelper.getPrivateField(Client.class, client, "address"),
            client.getAddress());
    }

    /**
     * <p>
     * Failure test for method <code>setAddress</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetAddressWithNull() throws Exception {
        try {
            client.setAddress(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setAddress</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetAddressAccuracy() throws Exception {
        Address address = new Address();

        client.setAddress(address);

        assertEquals("The address should be set.", address,
            UnitTestHelper.getPrivateField(Client.class, client, "address"));
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Accuracy test for method <code>getPaymentTerm</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetPaymentTermAccuracy() throws Exception {
        PaymentTerm paymentTerm = new PaymentTerm();

        client.setPaymentTerm(paymentTerm);

        assertEquals("The paymentTerm should be get.",
            UnitTestHelper.getPrivateField(Client.class, client, "paymentTerm"), client.getPaymentTerm());
    }

    /**
     * <p>
     * Failure test for method <code>setPaymentTerm</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetPaymentTermWithNull() throws Exception {
        try {
            client.setPaymentTerm(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setPaymentTerm</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetPaymentTermAccuracy() throws Exception {
        PaymentTerm paymentTerm = new PaymentTerm();

        client.setPaymentTerm(paymentTerm);

        assertEquals("The paymentTerm should be set.", paymentTerm,
            UnitTestHelper.getPrivateField(Client.class, client, "paymentTerm"));
        assertTrue("Client has been changed.", client.isChanged());
    }

    /**
     * <p>
     * Failure test for method <code>setProjects</code>. Set with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetProjectsWithNull() throws Exception {
        try {
            client.setProjects(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setProjects</code>. Set with invalid is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetProjectsWithInvalid() throws Exception {
        try {
            client.setProjects(new Project[] {null, new Project()});
            fail("Set with invalid is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setProjects</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetProjectsAccuracy() throws Exception {
        client.setProjects(new Project[] {new Project(), new Project()});

        assertEquals("The return project should be of size 2.", 2, client.getProjects().length);
    }

    /**
     * <p>
     * Accuracy test for method <code>getProjects</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetProjectsAccuracy() throws Exception {
        client.setProjects(new Project[] {new Project(), new Project()});

        assertEquals("The return project should be of size 2.", 2, client.getProjects().length);
    }
}
