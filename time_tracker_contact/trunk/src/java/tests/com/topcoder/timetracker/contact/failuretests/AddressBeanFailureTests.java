/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.ejb.AddressBean;
import com.topcoder.timetracker.contact.ejb.AddressLocal;
import com.topcoder.timetracker.contact.ejb.AddressLocalHome;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.TransactionManager;
import org.mockejb.TransactionPolicy;

import org.mockejb.interceptor.AspectSystemFactory;
import org.mockejb.interceptor.ClassPointcut;
import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 * <p>Failure test cases for the class AddressLocal.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class AddressBeanFailureTests extends TestCase {
    /** The AddressLocal. */
    private AddressLocal address = null;

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
       

        deployEJB(context);

        context.rebind("java:comp/env/SpecificationNamespace", TestHelper.OF_NAMESPACE);
        context.rebind("java:comp/env/AddressDAOKey", "addressDAO");

        context.rebind("javax.transaction.UserTransaction", userTransaction);
        
        this.address = lookUpEJB();
    }
    
    /**
     * Lookup ejb.
     *
     * @return AddressLocal instance
     *
     * @throws NamingException fails to find ejb
     * @throws CreateException fails to find ejb
     */
    private AddressLocal lookUpEJB() throws NamingException, CreateException {
        // Lookup the home
    	AddressLocalHome addressLocalHomeObj = (AddressLocalHome)context.lookup("timetracker/addressEJB");

        // create the bean
        return addressLocalHomeObj.create();
    }

    /**
     * Deploy the ejb with the context
     *
     * @param context context to deploy
     *
     * @throws NamingException fails to deploy ejb
     */
    public static void deployEJB(Context context) throws NamingException {
        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("timetracker/addressEJB",
                AddressLocalHome.class, AddressLocal.class,AddressBean.class);
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);
        AspectSystemFactory.getAspectSystem().addFirst(new ClassPointcut(AddressBean.class, false),
                new TransactionManager(TransactionPolicy.REQUIRED));
    }

    /**
     * Test ejb create method. One property is missing, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext() throws Exception {
        context.unbind("java:comp/env/SpecificationNamespace");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is missing, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext1() throws Exception {
        context.unbind("java:comp/env/AddressDAOKey");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is invalid, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext2() throws Exception {
        context.rebind("java:comp/env/AddressDAOKey", "invalid");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is invalid, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext4() throws Exception {
        context.rebind("java:comp/env/AddressDAOKey", "contactDAO");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is invalid, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext3() throws Exception {
        context.rebind("java:comp/env/SpecificationNamespace", "notExist");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }
    
    /**
     * <p>
     * Assert the transaction was rolled back.
     * </p>
     *
     * @param transaction to assert
     */
    public static void assertTransactionRollbacked(MockUserTransaction transaction) {
        assertTrue("Transaction should be rolled back", transaction.wasRollbackOnlyCalled());
    }

    

    /**
     * Test addAddress(Address address, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_nullAddress() throws Exception {
        try {
            this.address.addAddress(null, false);
            fail("The address to add is null.");
        } catch (IllegalArgumentException e) {
        	assertTransactionRollbacked(this.userTransaction);
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
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
        	assertTransactionRollbacked(this.userTransaction);
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
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
        	assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress3() throws Exception {
        //address with large line2
        Address address = TestHelper.getAddress(3);

        try {
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
        	assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress4() throws Exception {
        //address without setCreationUser
        Address address = TestHelper.getAddress(4);

        try {
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress5() throws Exception {
        //address without setModifyUser
        Address address = TestHelper.getAddress(5);

        try {
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress6() throws Exception {
        //address without state
        Address address = TestHelper.getAddress(6);

        try {
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress7() throws Exception {
        //address without country
        Address address = TestHelper.getAddress(7);

        try {
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_invalidAddress8() throws Exception {
        //address without AddressType
        Address address = TestHelper.getAddress(8);

        try {
            this.address.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddresses(Address[] addresses, boolean doAudit) with null addresses. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testAddAddress_nullArray() throws Exception {
        try {
            this.address.addAddresses(null, false);
            fail("The addresses is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test addAddresses(Address[] addresses, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testAddAddress_nullElementAddresses() throws Exception {
        try {
            this.address.addAddresses(new Address[] { null }, false);
            fail("The addresses contains null element.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddress_invalidId() throws Exception {
        try {
            this.address.retrieveAddress(0);
            fail("The address id is invalid.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddresses_nullIds() throws Exception {
        try {
            this.address.retrieveAddresses(null);
            fail("The addresses ids is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddresses_invalidIds() throws Exception {
        try {
            this.address.retrieveAddresses(new long[] { 1, 0 });
            fail("The addresses ids contains invalid id.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddress_invalidParams() throws Exception {
        try {
            this.address.removeAddress(0, false);
            fail("The address is invalid.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddresses_invalidParams() throws Exception {
        try {
            this.address.removeAddresses(null, false);
            fail("The addresses is invalid.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddresses_invalidParams2() throws Exception {
        try {
            this.address.removeAddresses(new long[] { 0 }, false);
            fail("The addresses is invalid.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
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
        this.address.addAddress(address, false);

        //associate it
        this.address.associate(address, 1, false);

        this.address.removeAddress(address.getId(), false);

    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with null address, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_nullAddess() throws Exception {
        try {
            this.address.associate(null, 1, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with negative id. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_negativeEntityId() throws Exception {
        try {
            this.address.associate(TestHelper.getAddress(9), -1, false);
            fail("The entityId is negative.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
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
    	Address address = TestHelper.getAddress(21);
    	address.setId(100);
    	address.setCreationDate(new java.util.Date());
    	address.setModificationDate(new java.util.Date());
        try {
            this.address.associate(address, 1, false);
            fail("The address is invalid.");
        } catch (AssociationException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test deassociate(Address address, long entityId, boolean doAudit) with null address. iae expectd.
     *
     * @throws Exception into jUnit
     */
    public void testDeassociate_nullAddress() throws Exception {
        try {
            this.address.deassociate(null, 1, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test deassociate(Address address, long entityId, boolean doAudit)  with negative id. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testDeassociate_negativeEntityId() throws Exception {
        try {
            this.address.deassociate(TestHelper.getAddress(9), -1, false);
            fail("The entityId is negative.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdateAddress_nullAddress() throws Exception {
        try {
            this.address.updateAddress(null, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress1() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without setPostalCode
        Address address = TestHelper.getAddress(1);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress2() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without line1
        Address address = TestHelper.getAddress(2);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress3() throws Exception {
    	Address one = TestHelper.getAddress(9);
    	address.addAddress(one, false);

        //address with too large line2
        Address address = TestHelper.getAddress(3);
        address.setId(one.getId());
        address.setModificationDate(one.getModificationDate());
        address.setModificationUser(one.getModificationUser());
        
        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress4() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without setCreationUser
        Address address = TestHelper.getAddress(4);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress5() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without setModifyUser
        Address address = TestHelper.getAddress(5);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress6() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without state
        Address address = TestHelper.getAddress(6);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress7() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without country
        Address address = TestHelper.getAddress(7);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress8() throws Exception {
        address.addAddress(TestHelper.getAddress(9), false);

        //address without AddressType
        Address address = TestHelper.getAddress(8);

        try {
            this.address.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test the updateAddresses with null address array, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_nullAddresses() throws Exception {
        try {
            this.address.updateAddresses(null, false);
            fail("The addresses to add is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test the updateAddresses with null address, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_nullAddress() throws Exception {
        try {
            this.address.updateAddresses(new Address[] { null }, false);
            fail("The address to add is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test the updateAddresses with not exist address,EntityNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_notExistAddress() throws Exception {
    	Address address = TestHelper.getAddress(9);
    	address.setId(100);
    	address.setCreationDate(new java.util.Date());
    	
        try {
            this.address.updateAddresses(new Address[] { address }, false);
            fail("The address to update does not exist.");
        } catch (EntityNotFoundException e) {
            assertTransactionRollbacked(this.userTransaction);
        }
    }

    /**
     * Test searchAddresses(Filter filter) with null filter, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testSearchAddress_nullFilter() throws Exception {
        try {
            this.address.searchAddresses(null);
            fail("The address to search is null.");
        } catch (IllegalArgumentException e) {
            assertTransactionRollbacked(this.userTransaction);
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
