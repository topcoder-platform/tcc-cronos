/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate;

import junit.framework.TestCase;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>Failure test cases for the class AddressManagerLocalDelegate.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class AddressManagerLocalDelegateFailureTests extends TestCase {
    /** AddressManagerLocalDelegate instance for testing. */
    private AddressManagerLocalDelegate delegate = null;

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** Database connection. */
    private Connection conn = null;
    /**
     * The MockUserTransaction.
     */
    private MockUserTransaction userTransaction = new MockUserTransaction();
    /**
     * setup environment
     */
    protected void setUp() throws Exception {
        // clear the talble and setup the environment.
        TestHelper.clearConfiguration();
        TestHelper.setUpConfiguration();
        conn = TestHelper.getConnection();
        TestHelper.clearTable(conn);
        TestHelper.insertData(conn);

        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();
        context.rebind("java:comp/env/SpecificationNamespace", TestHelper.OF_NAMESPACE);
        context.rebind("java:comp/env/AddressDAOKey", "addressDAO");

        context.rebind("javax.transaction.UserTransaction", userTransaction);

        AddressBeanFailureTests.deployEJB(context);

        this.delegate = new AddressManagerLocalDelegate();
    }

    /**
     * Test the ctor with null namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_nullNS() throws Exception {
        try {
            new AddressManagerLocalDelegate(null);
            fail("The ns is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with empty namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_emptyNS() throws Exception {
        try {
            new AddressManagerLocalDelegate(" ");
            fail("The ns is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid namespace, ConfigurationException expected.
     */
    public void testCtor_invalidNS() {
        try {
            new AddressManagerLocalDelegate("com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate.failure1");
            fail("The ns is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid namespace, ConfigurationException expected.
     */
    public void testCtor_invalidNS2() {
        try {
            new AddressManagerLocalDelegate("com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate.failure2");
            fail("The ns is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_nullAddress() throws Exception {
        try {
            this.delegate.addAddress(null, false);
            fail("The address to add is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress1() throws Exception {
        //address without setPostalCode
        Address address = TestHelper.getAddress(1);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress2() throws Exception {
        //address without line1
        Address address = TestHelper.getAddress(2);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress3() throws Exception {
        //address without setCreationUser
        Address address = TestHelper.getAddress(4);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress4() throws Exception {
        //address without setModifyUser
        Address address = TestHelper.getAddress(5);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress5() throws Exception {
        //address without state
        Address address = TestHelper.getAddress(6);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress6() throws Exception {
        //address without country
        Address address = TestHelper.getAddress(7);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress7() throws Exception {
        //address without AddressType
        Address address = TestHelper.getAddress(8);

        try {
            this.delegate.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddresses(Address[] addresses, boolean doAudit) with null addresses. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testAddAddress_nullArray() throws Exception {
        try {
            this.delegate.addAddresses(null, false);
            fail("The addresses is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addAddresses(Address[] addresses, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testAddAddress_nullElementAddresses() throws Exception {
        try {
            this.delegate.addAddresses(new Address[] { null }, false);
            fail("The addresses contains null element.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddress_invalidId() throws Exception {
        try {
            this.delegate.retrieveAddress(0);
            fail("The address id is invalid.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddresses_nullIds() throws Exception {
        try {
            this.delegate.retrieveAddresses(null);
            fail("The addresses ids is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddresses_invalidIds() throws Exception {
        try {
            this.delegate.retrieveAddresses(new long[] { 1, 0 });
            fail("The addresses ids contains invalid id.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddress_invalidParams() throws Exception {
        try {
            this.delegate.removeAddress(0, false);
            fail("The address is invalid.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddresses_invalidParams() throws Exception {
        try {
            this.delegate.removeAddresses(null, false);
            fail("The addresses is invalid.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddresses_invalidParams2() throws Exception {
        try {
            this.delegate.removeAddresses(new long[] { 0 }, false);
            fail("The addresses is invalid.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the removeAddress method.  The address has associate,  PersistenceException expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddress_hasAssociate() throws Exception {
        Address address = TestHelper.getAddress(9);

        //create address
        this.delegate.addAddress(address, false);

        //associate it
        this.delegate.associate(address, 1, false);

        this.delegate.removeAddress(address.getId(), false);
           
    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with null address, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_nullAddess() throws Exception {
        try {
            this.delegate.associate(null, 1, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with negative id. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_negativeEntityId() throws Exception {
        try {
            this.delegate.associate(TestHelper.getAddress(9), -1, false);
            fail("The entityId is negative.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with invalid address, AssociationException
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_invalidAddess() throws Exception {
        //the address without the AddressType
    	Address address = TestHelper.getAddress(22);
    	address.setId(100);
    	address.setModificationDate(new java.util.Date());
    	address.setCreationDate(new java.util.Date());
        try {
            this.delegate.associate(address, 1, false);
            fail("The address is invalid.");
        } catch (AssociationException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test deassociate(Address address, long entityId, boolean doAudit) with null address. iae expectd.
     *
     * @throws Exception into jUnit
     */
    public void testDeassociate_nullAddress() throws Exception {
        try {
            this.delegate.deassociate(null, 1, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test deassociate(Address address, long entityId, boolean doAudit)  with negative id. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testDeassociate_negativeEntityId() throws Exception {
        try {
            this.delegate.deassociate(TestHelper.getAddress(9), -1, false);
            fail("The entityId is negative.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdateAddress_nullAddress() throws Exception {
        try {
            this.delegate.updateAddress(null, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress1() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without setPostalCode
        Address address = TestHelper.getAddress(1);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress2() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without line1
        Address address = TestHelper.getAddress(2);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress3() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address with too large line2
        Address address = TestHelper.getAddress(3);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress4() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without setCreationUser
        Address address = TestHelper.getAddress(4);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress5() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without setModifyUser
        Address address = TestHelper.getAddress(5);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress6() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without state
        Address address = TestHelper.getAddress(6);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress7() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without country
        Address address = TestHelper.getAddress(7);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress8() throws Exception {
        delegate.addAddress(TestHelper.getAddress(9), false);

        //address without AddressType
        Address address = TestHelper.getAddress(8);

        try {
            this.delegate.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the updateAddresses with null address array, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_nullAddresses() throws Exception {
        try {
            this.delegate.updateAddresses(null, false);
            fail("The addresses to add is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the updateAddresses with null address, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_nullAddress() throws Exception {
        try {
            this.delegate.updateAddresses(new Address[] { null }, false);
            fail("The address to add is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the updateAddresses with not exist address,EntityNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_notExistAddress() throws Exception {
    	Address to = TestHelper.getAddress(9);
    	to.setId(100);
    	to.setCreationDate(new java.util.Date());
        try {
            this.delegate.updateAddresses(new Address[] { to }, false);
            fail("The address to update does not exist.");
        } catch (EntityNotFoundException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test searchAddresses(Filter filter) with null filter, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testSearchAddress_nullFilter() throws Exception {
        try {
            this.delegate.searchAddresses(null);
            fail("The address to search is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Clear the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTable(conn);

        if (conn != null) {
            conn.close();
        }

        TestHelper.clearConfiguration();
    }
}
