/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.InvalidPropertyException;

import com.topcoder.timetracker.contact.persistence.InformixAddressDAO;

import junit.framework.TestCase;

import java.sql.Connection;


/**
 * <p>Failure test cases for the class InformixAddressDAO.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class InformixAddressDAOFailureTests extends TestCase {
    /** InformixAddressDAO instance to test against. */
    private InformixAddressDAO dao = null;

    /** Database connection. */
    private Connection conn = null;

    /** AuditManager for testing. */
    private AuditManager auditManager = null;

    /**
     * Setup the environment.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        auditManager = new MockAuditManager();
        TestHelper.clearConfiguration();
        TestHelper.setUpConfiguration();
        conn = TestHelper.getConnection();
        //TestHelper.clearTable(conn);
        
        TestHelper.insertData(conn);
        dao = new InformixAddressDAO(TestHelper.ADDRESS_DAO_NS, auditManager);
    }

    /**
     * Test ctor with null value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new InformixAddressDAO(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with empty value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new InformixAddressDAO(" ");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with not exist value, iae expected.
     */
    public void testCtor_notExistNamespace() {
        try {
            new InformixAddressDAO("notExist");
            fail("The namespace does not exist.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test ctor with null value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace2() throws Exception {
        try {
            new InformixAddressDAO(null, null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with empty value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace2() throws Exception {
        try {
            new InformixAddressDAO(" ", null);
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with not exist value, iae expected.
     */
    public void testCtor_notExistNamespace2() {
        try {
            new InformixAddressDAO("notExist", null);
            fail("The namespace does not exist.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Create informixAddressDAO with invalid configuration.
     *
     * @param namespace the namespace to create InformixAddressDAO instance
     */
    private void createWith_invalidConfiguration(String namespace) {
        try {
            new InformixAddressDAO(namespace, auditManager);
            fail("The namespace is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid configuration, the property 'connection_factory_namespace' is missing.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration1() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure1");
    }

    /**
     * Test the ctor with invalid configuration, the property 'connection_factory_namespace''s value is invalid, it
     * does not exist. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration2() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure2");
    }

    /**
     * Test the ctor with invalid configuration, the property 'connection_name''s value is invalid.
     * @throws Exception into JUnit
     */
    public void testCtor_invalidConfiguration3() throws Exception{
    	new InformixAddressDAO("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure3", auditManager);
    }

    /**
     * Test the ctor with invalid configuration, the property 'idgenerator_name' is missing, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration4() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure4");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_name' is missing, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration5() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure5");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_name''s value is invalid.
     * @throws Exception into JUnit
     */
    public void testCtor_invalidConfiguration6() throws Exception{
    	createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure6");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_namespace' is missing, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration7() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixAddressDAO.failure7");
    }

    /**
     * Test addAddress(Address address, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into jUnit
     */
    public void testAddAddress_nullAddress() throws Exception {
        try {
            this.dao.addAddress(null, false);
            fail("The address to add is null.");
        } catch (IllegalArgumentException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
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
            this.dao.addAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addAddresses(Address[] addresses, boolean doAudit) with null addresses. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testAddAddress_nullArray() throws Exception {
        try {
            this.dao.addAddresses(null, false);
            fail("The addresses is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test addAddresses(Address[] addresses, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testAddAddress_nullElementAddresses() throws Exception {
        try {
            this.dao.addAddresses(new Address[] { null }, false);
            fail("The addresses contains null element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddress_invalidId() throws Exception {
        try {
            this.dao.retrieveAddress(0);
            fail("The address id is invalid.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddresses_nullIds() throws Exception {
        try {
            this.dao.retrieveAddresses(null);
            fail("The addresses ids is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the retrieve address with invalid id. IllegalArgumentException expected.
     *
     * @throws Exception into Junit
     */
    public void testRetrieveAddresses_invalidIds() throws Exception {
        try {
            this.dao.retrieveAddresses(new long[] { 1, 0 });
            fail("The addresses ids contains invalid id.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddress_invalidParams() throws Exception {
        try {
            this.dao.removeAddress(0, false);
            fail("The address is invalid.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddresses_invalidParams() throws Exception {
        try {
            this.dao.removeAddresses(null, false);
            fail("The addresses is invalid.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test removeAddress with inalid id. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddresses_invalidParams2() throws Exception {
        try {
            this.dao.removeAddresses(new long[] { 0 }, false);
            fail("The addresses is invalid.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the removeAddress method.  The address has associate,  all removed.
     *
     * @throws Exception into JUnit
     */
    public void testRemoveAddress_hasAssociate() throws Exception {
        Address address = TestHelper.getAddress(9);

        //create address
        this.dao.addAddress(address, false);

        //associate it
        this.dao.associate(address, 1, false);

        this.dao.removeAddress(address.getId(), false);

    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with null address, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_nullAddess() throws Exception {
        try {
            this.dao.associate(null, 1, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with negative id. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_negativeEntityId() throws Exception {
        try {
            this.dao.associate(TestHelper.getAddress(9), -1, false);
            fail("The entityId is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test associate(Address address, long entityId, boolean doAudit)  with invalid address, InvalidPropertyException
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_invalidAddess() throws Exception {
        //the address without the AddressType
    	Address address = TestHelper.getAddress(20);
    	address.setId(100);
    	address.setModificationDate(new java.util.Date());
    	address.setCreationDate(new java.util.Date());
        try {
            this.dao.associate(address, 1, false);
            fail("The address is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test deassociate(Address address, long entityId, boolean doAudit) with null address. iae expectd.
     *
     * @throws Exception into jUnit
     */
    public void testDeassociate_nullAddress() throws Exception {
        try {
            this.dao.deassociate(null, 1, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test deassociate(Address address, long entityId, boolean doAudit)  with negative id. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testDeassociate_negativeEntityId() throws Exception {
        try {
            this.dao.deassociate(TestHelper.getAddress(9), -1, false);
            fail("The entityId is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with null address. iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdateAddress_nullAddress() throws Exception {
        try {
            this.dao.updateAddress(null, false);
            fail("The address is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress1() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without setPostalCode
        Address address = TestHelper.getAddress(1);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress2() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without line1
        Address address = TestHelper.getAddress(2);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress3() throws Exception {
    	Address toCreate = TestHelper.getAddress(9);
    	dao.addAddress(toCreate, false);

        //address two large line2
        Address address = TestHelper.getAddress(3);
        address.setId(toCreate.getId());
        address.setModificationDate(toCreate.getModificationDate());
        address.setModificationUser(toCreate.getModificationUser());
        
        try {
            this.dao.updateAddress(address, false);
            fail("The address to update is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress4() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without setCreationUser
        Address address = TestHelper.getAddress(4);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress5() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without setModifyUser
        Address address = TestHelper.getAddress(5);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress6() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without state
        Address address = TestHelper.getAddress(6);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress7() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without country
        Address address = TestHelper.getAddress(7);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateAddress(Address address, boolean doAudit) with invalid address. InvalidPropertyException expected.
     *
     * @throws Exception into jUnit
     */
    public void testUpdateAddress_invalidAddress8() throws Exception {
        dao.addAddress(TestHelper.getAddress(9), false);

        //address without AddressType
        Address address = TestHelper.getAddress(8);

        try {
            this.dao.updateAddress(address, false);
            fail("The address to add is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test the updateAddresses with null address array, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_nullAddresses() throws Exception {
        try {
            this.dao.updateAddresses(null, false);
            fail("The addresses to add is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the updateAddresses with null address, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateAddresses_nullAddress() throws Exception {
        try {
            this.dao.updateAddresses(new Address[] { null }, false);
            fail("The address to add is null.");
        } catch (IllegalArgumentException e) {
            //good
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
            this.dao.updateAddresses(new Address[] {to }, false);
            fail("The address to update does not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * Test searchAddresses(Filter filter) with null filter, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testSearchAddress_nullFilter() throws Exception {
        try {
            this.dao.searchAddresses(null);
            fail("The address to search is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Clear the environment.
     *
     * @throws Exception into JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTable(conn);
        conn.close();
        TestHelper.clearConfiguration();
    }
}
