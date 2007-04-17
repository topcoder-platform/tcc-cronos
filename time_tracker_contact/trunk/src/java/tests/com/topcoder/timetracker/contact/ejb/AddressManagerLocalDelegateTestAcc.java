/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.util.Date;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.MockTransaction;
import com.topcoder.timetracker.contact.State;

/**
 * <p>This test case contains accuracy tests for <code>AddressManagerLocalDelegate</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressManagerLocalDelegateTestAcc extends BaseTestCase {
    /**
     * <p>
     * <code>Address</code> used in the test case.
     * </p>
     */
    private Address address = null;

    /**
     * <p>
     * <code>MockTransaction</code> used in the test case.
     * </p>
     */
    private MockTransaction transaction = null;

    /**
     * <p>
     * Previous count of audit records.
     * </p>
     */
    private int preCount = 0;

    /**
     * <p>
     * <code>AddressManagerLocalDelegate</code> used in the test case.
     * </p>
     */
    private AddressManagerLocalDelegate localDelegate = null;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        localDelegate = new AddressManagerLocalDelegate();
        preCount = this.getAuditRecordsCount();
        transaction = this.getTransaction();
        address = this.getAddress();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.unBind("java:comp/env/SpecificationNamespace");
        this.unBind("java:comp/env/AddressDAOKey");
        localDelegate = null;
        preCount = 0;
        transaction = null;
        address = null;
        super.tearDown();
    }
    /**
     * Accuracy test for the constructor <code>AddressManagerLocalDelegate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCtor1() throws Exception {
        assertNotNull(this.localDelegate);
    }
    /**
     * Accuracy test for the constructor <code>AddressManagerLocalDelegate(String)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCtor2() throws Exception {
        assertNotNull(new AddressManagerLocalDelegate(AddressManagerLocalDelegate.class.getName()));
    }

    /**
     * <p>
     * Test accuracy of method <code>getAllCountries()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllCountries() throws Exception {
        Country[] countries = localDelegate.getAllCountries();
        this.assertTransactionCommited(transaction);
        assertEquals("There should be 2 countries", 2, countries.length);
        assertFalse("Changed status should be false", countries[0].isChanged());
        assertEquals("China", countries[0].getName());
        assertFalse("Changed status should be false", countries[1].isChanged());
        assertEquals("USA", countries[1].getName());
    }
    /**
     * <p>
     * Test accuracy of method <code>getAllStates()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllStates() throws Exception {
        State[] states = localDelegate.getAllStates();
        this.assertTransactionCommited(transaction);
        assertEquals("There should be 2 states", 2, states.length);
        assertFalse("Changed status should be false", states[0].isChanged());
        assertEquals("ZheJiang", states[0].getName());
        assertEquals("ZJ", states[0].getAbbreviation());
        assertFalse("Changed status should be false", states[1].isChanged());
        assertEquals("Alaska", states[1].getName());
        assertEquals("AK", states[1].getAbbreviation());
    }
    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>retrieveAddress()</code>
     * <code>updateAddress()</code> and <code>removeAddress()</code> with audit.
     * Since the type of address is set, so audit will be performed when <code>addAddress()</code>
     * and <code>updateAddress()</code>.
     * But the address is not associated with any entity, so no audit will be performed
     * when <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationsWithAudit1() throws Exception {
        localDelegate.addAddress(address, true);
        assertFalse("Changed status should be false after adding", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Address addedAddress = localDelegate.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", addedAddress.isChanged());
        this.assertAddressEquals(address, addedAddress);

        //line2 is null-able
        address.setLine2(null);
        localDelegate.updateAddress(address, true);
        assertFalse("Changed status should be false after updating", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Address updatedAddress = localDelegate.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", updatedAddress.isChanged());
        this.assertAddressEquals(address, updatedAddress);

        localDelegate.removeAddress(address.getId(), true);
        this.assertTransactionCommited(transaction);
        //address is associated with no entity, no audit performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localDelegate.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>retrieveAddress()</code>
     * <code>updateAddress()</code> and <code>removeAddress()</code> with audit.
     * The address is associated with an entity, audit will be performed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationsWithAudi2() throws Exception {
        //Add the address first
        localDelegate.addAddress(address, true);
        assertFalse("Changed status should be false after adding", address.isChanged());
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);
        this.assertTransactionCommited(transaction);

        Address addedAddress = localDelegate.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", addedAddress.isChanged());
        this.assertAddressEquals(address, addedAddress);

        //change city
        address.setCity("NewYork");
        localDelegate.updateAddress(address, true);
        assertFalse("Changed status should be false after updating", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Address updatedAddress = localDelegate.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", updatedAddress.isChanged());
        this.assertAddressEquals(address, updatedAddress);

        //Associate the address with enitity id 1, so audit will be performed when removing
        localDelegate.associate(address, 1, true);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        localDelegate.removeAddress(address.getId(), true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed. Note the association will also be removed, so the increased audit count is 2
        preCount = this.assertAuditRecordsCount(preCount, 2);
        assertNull(localDelegate.retrieveAddress(address.getId()));
    }
    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>retrieveAddress()</code>
     * <code>updateAddress()</code> and <code>removeAddress()</code> with audit.
     * The address is associated with an entity, but doAudit flag is false, audit will not be performed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationsWithoutAudit() throws Exception {
        //Add the address first
        localDelegate.addAddress(address, false);
        assertFalse("Changed status should be false after adding", address.isChanged());
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        this.assertTransactionCommited(transaction);

        Address addedAddress = localDelegate.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", addedAddress.isChanged());
        this.assertAddressEquals(address, addedAddress);

        //Associate the address with enitity id 1
        address.setAddressType(AddressType.USER);
        localDelegate.associate(address, 1, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //change city
        address.setCity("NewYork");
        localDelegate.updateAddress(address, false);
        assertFalse("Changed status should be false after updating", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        Address updatedAddress = localDelegate.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", updatedAddress.isChanged());
        this.assertAddressEquals(address, updatedAddress);

        localDelegate.removeAddress(address.getId(), false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localDelegate.retrieveAddress(address.getId()));
    }
    /**
     * <p>
     * Test accuracy of methods <code>associate()</code> and <code>deassociate()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociateWithAudit() throws Exception {
        //Add the address first
        address.setAddressType(AddressType.USER);
        localDelegate.addAddress(address, true);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the address with entity 1
        localDelegate.associate(address, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the same address with same entity 1 again, method is simply returned, no audit performed
        localDelegate.associate(address, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same address with another entity 2, the previous association should be deleted
        localDelegate.associate(address, 2, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, one for deletion of the previous association, one for insertion of
        //new association
        preCount = this.assertAuditRecordsCount(preCount, 2);

        localDelegate.deassociate(address, 3, true);
        //association does not exist, method is simply returned, no audit performed
        this.assertAuditRecordsCount(preCount, 0);

        //Deassociate address with entity 2
        localDelegate.deassociate(address, 2, true);
        //Audit should performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Remove the address
        localDelegate.removeAddress(address.getId(), true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed as no association
        preCount = this.assertAuditRecordsCount(preCount, 0);
    }
    /**
     * <p>
     * Test accuracy of methods <code>associate()</code> and <code>deassociate()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociateWithoutAudit() throws Exception {
        //Add the address first
        localDelegate.addAddress(address, false);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the address with entity 1
        localDelegate.associate(address, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same address with same entity 1 again, method is simply returned, no audit performed
        localDelegate.associate(address, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same address with another entity 2, the previous association should be deleted
        localDelegate.associate(address, 2, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        localDelegate.deassociate(address, 3, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Deassociate address with entity 2
        localDelegate.deassociate(address, 2, false);
        //Audit should not be performed
        this.assertAuditRecordsCount(preCount, 0);

        //Remove the address
        localDelegate.removeAddress(address.getId(), false);
        this.assertTransactionCommited(transaction);
    }
    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code>, <code>getAllAddresses()</code>,
     * <code>updateAddresses()</code> and <code>removeAddresses()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationWithAudit() throws Exception {
        Address[] addressesToAdd = new Address[10];
        for (int i = 0; i < 10; i++) {
            addressesToAdd[i] = this.getAddress();
        }

        //Add 10 addresses
        localDelegate.addAddresses(addressesToAdd, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        long ids[] = new long[10];

        //Get all added addresses
        Address[] addedAddresses = localDelegate.getAllAddresses();
        assertTrue("There should be 10 address", addedAddresses.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addressesToAdd[i], addedAddresses[i]);
            assertFalse("Changed status should be false after adding", addressesToAdd[i].isChanged());
            ids[i] = addressesToAdd[i].getId();
            addedAddresses[i].setCity("City" + i);
            addedAddresses[i].setAddressType(AddressType.CLIENT);
        }

        //update the 10 addresses
        localDelegate.updateAddresses(addedAddresses, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        //Get all updated addresses
        Address[] updatedAddresses = localDelegate.getAllAddresses();
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(updatedAddresses[i], addedAddresses[i]);
            assertFalse("Changed status should be false after updating", addedAddresses[i].isChanged());
        }

        //Remove the 10 addresses
        localDelegate.removeAddresses(ids, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed, as no association
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no address after removal
        assertTrue("There should be 0 address", localDelegate.getAllAddresses().length == 0);
    }
    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code>, <code>getAllAddresses()</code>,
     * <code>updateAddresses()</code> and <code>removeAddresses()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationWithoutAudit() throws Exception {
        Address[] addressesToAdd = new Address[10];
        for (int i = 0; i < 10; i++) {
            addressesToAdd[i] = this.getAddress();
        }

        //Add 10 addresses
        localDelegate.addAddresses(addressesToAdd, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        long ids[] = new long[10];

        //Get all added addresses
        Address[] addedAddresses = localDelegate.getAllAddresses();
        assertTrue("There should be 10 address", addedAddresses.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addressesToAdd[i], addedAddresses[i]);
            assertFalse("Changed status should be false after adding", addressesToAdd[i].isChanged());
            ids[i] = addressesToAdd[i].getId();
            addedAddresses[i].setCity("City" + i);
        }

        //update the 10 addresses
        localDelegate.updateAddresses(addedAddresses, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Get all updated addresses
        Address[] updatedAddresses = localDelegate.getAllAddresses();
        this.assertTransactionCommited(transaction);
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(updatedAddresses[i], addedAddresses[i]);
            assertFalse("Changed status should be false after updating", addedAddresses[i].isChanged());
        }

        //Remove the 10 addresses
        localDelegate.removeAddresses(ids, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no address after removal
        assertTrue("There should be 0 address", localDelegate.getAllAddresses().length == 0);
        this.assertTransactionCommited(transaction);
    }
    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code>, <code>updateAddresses()</code>
     * <code>retrieveAddresses()</code> and <code>removeAddresses()</code> with duplication in passed in array.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationWithDuplication() throws Exception {
        address.setAddressType(AddressType.COMPANY);
        Address[] duplicateAddresses = new Address[10];
        for (int i = 0; i < 10; i++) {
            duplicateAddresses[i] = address;
        }

        //Add with duplication objects
        localDelegate.addAddresses(duplicateAddresses, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            ids[i] = duplicateAddresses[i].getId();
        }

        //Retrieve with duplicate ids
        Address[] addedAddresses = localDelegate.retrieveAddresses(ids);
        assertEquals("There should be only one record inserted into database", 1, addedAddresses.length);
        this.assertAddressEquals(address, addedAddresses[0]);
        this.assertTransactionCommited(transaction);

        address.setLine1("new line1");

        //Update with duplication objects
        localDelegate.updateAddresses(duplicateAddresses, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the address, so audit will be performed when removing
        localDelegate.associate(address, 1, false);
        this.assertTransactionCommited(transaction);

        localDelegate.removeAddresses(ids, true);
        this.assertTransactionCommited(transaction);
        //There should be only 2 audit record, including the record for deletion of association
        this.assertAuditRecordsCount(preCount, 2);

        //There should be no records after removal
        assertEquals(0, localDelegate.retrieveAddresses(ids).length);
        this.assertTransactionCommited(transaction);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchAddresses()</code>. Search addresses by creation/modification date.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch1() throws Exception {
        Address[] addresses = new Address[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            addresses[i] = this.getAddress();
        }

        Date from = new Date();
        Thread.sleep(1000);
        localDelegate.addAddresses(addresses, false);
        Thread.sleep(1000);
        Date to = new Date();

        Address[] results = localDelegate.searchAddresses(AddressFilterFactory.createCreatedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createModifiedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        //upperThreshold is null
        results = localDelegate.searchAddresses(AddressFilterFactory.createCreatedInFilter(from, null));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        //lowerThreshold is null
        results = localDelegate.searchAddresses(AddressFilterFactory.createModifiedInFilter(null, to));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localDelegate.removeAddresses(ids, false);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchAddresses()</code>. Search addresses by creation/modification user.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch2() throws Exception {
        Address[] addresses = new Address[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            addresses[i] = this.getAddress();
        }

        localDelegate.addAddresses(addresses, false);

        Address[] results = localDelegate.searchAddresses(AddressFilterFactory.createCreatedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createModifiedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localDelegate.removeAddresses(ids, false);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchAddresses()</code>. Search addresses by postal code, city, state and country.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch3() throws Exception {
        Address[] addresses = new Address[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            addresses[i] = this.getAddress();
        }

        localDelegate.addAddresses(addresses, false);

        Address[] results = localDelegate.searchAddresses(AddressFilterFactory.createCityFilter("HangZhou"));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createZipCodeFilter("123456"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createStateIDFilter(1));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createStateNameFilter("ZheJiang"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createCountryIDFilter(1));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createCountryNameFilter("China"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localDelegate.removeAddresses(ids, false);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchAddresses()</code>. Search addresses by type and entity id.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch4() throws Exception {
        Address[] addresses = new Address[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            addresses[i] = this.getAddress();
        }

        localDelegate.addAddresses(addresses, false);

        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            localDelegate.associate(addresses[i], 1, false);
        }

        Address[] results = localDelegate.searchAddresses(AddressFilterFactory.createTypeFilter(AddressType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localDelegate.searchAddresses(AddressFilterFactory.createEntityIDFilter(1, AddressType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localDelegate.removeAddresses(ids, false);
    }
}
