/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.lang.reflect.Proxy;
import java.util.Date;

import javax.ejb.SessionContext;

import org.mockejb.MockEjbObject;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressDAO;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.MockTransaction;
import com.topcoder.timetracker.contact.State;

/**
 * <p>This test case contains accuracy tests for <code>AddressBean</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressBeanTestAcc extends BaseTestCase {
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
     * <code>AddressLocal</code> used in the test case.
     * </p>
     */
    private AddressLocal localBean = null;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        localBean = this.getAddressLocal();
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
        localBean.remove();
        localBean = null;
        preCount = 0;
        transaction = null;
        address = null;
        super.tearDown();
    }
    /**
     * Accuracy test for the method <code>ejbRemove()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbRemove_Accuracy() throws Exception {
        new AddressBean().ejbRemove();
    }

    /**
     * Accuracy test for the method <code>ejbActivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbActivate_Accuracy() throws Exception {
        new AddressBean().ejbActivate();
    }

    /**
     * Accuracy test for the method <code>ejbPassivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbPassivate_Accuracy() throws Exception {
        new AddressBean().ejbPassivate();
    }

    /**
     * Accuracy test for the method <code>setSessionContext(SessionContext)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetSessionContext_Accuracy() throws Exception {
        SessionContext context = ((MockEjbObject) Proxy.getInvocationHandler(localBean)).getEjbContext();
        AddressBean bean = new AddressBean();

        bean.setSessionContext(context);
        assertEquals("The context should be set properly.", context, this.getField(bean, "sessionContext"));
    }

    /**
     * <p>
     * Test accuracy of ctor.
     * </p>
     */
    public void testCtor() {
        assertNotNull("AddressBean should be instantiated", new AddressBean());
    }

    /**
     * <p>
     * Test accuracy of method <code>ejbCreate()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        AddressBean bean = new AddressBean();
        bean.ejbCreate();
        assertTrue(this.getField(bean, "addressDAO") instanceof AddressDAO);
    }

    /**
     * <p>
     * Test accuracy of method <code>getAllCountries()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllCountries() throws Exception {
        Country[] countries = localBean.getAllCountries();
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
        State[] states = localBean.getAllStates();
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
        localBean.addAddress(address, true);
        assertFalse("Changed status should be false after adding", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Address addedAddress = localBean.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", addedAddress.isChanged());
        this.assertAddressEquals(address, addedAddress);

        //line2 is null-able
        address.setLine2(null);
        localBean.updateAddress(address, true);
        assertFalse("Changed status should be false after updating", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Address updatedAddress = localBean.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", updatedAddress.isChanged());
        this.assertAddressEquals(address, updatedAddress);

        localBean.removeAddress(address.getId(), true);
        this.assertTransactionCommited(transaction);
        //address is associated with no entity, no audit performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localBean.retrieveAddress(address.getId()));
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
        localBean.addAddress(address, true);
        assertFalse("Changed status should be false after adding", address.isChanged());
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);
        this.assertTransactionCommited(transaction);

        Address addedAddress = localBean.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", addedAddress.isChanged());
        this.assertAddressEquals(address, addedAddress);

        //change city
        address.setCity("NewYork");
        localBean.updateAddress(address, true);
        assertFalse("Changed status should be false after updating", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Address updatedAddress = localBean.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", updatedAddress.isChanged());
        this.assertAddressEquals(address, updatedAddress);

        //Associate the address with enitity id 1, so audit will be performed when removing
        localBean.associate(address, 1, true);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        localBean.removeAddress(address.getId(), true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed. Note the association will also be removed, so the increased audit count is 2
        preCount = this.assertAuditRecordsCount(preCount, 2);
        assertNull(localBean.retrieveAddress(address.getId()));
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
        localBean.addAddress(address, false);
        assertFalse("Changed status should be false after adding", address.isChanged());
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        this.assertTransactionCommited(transaction);

        Address addedAddress = localBean.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", addedAddress.isChanged());
        this.assertAddressEquals(address, addedAddress);

        //Associate the address with enitity id 1
        address.setAddressType(AddressType.USER);
        localBean.associate(address, 1, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //change city
        address.setCity("NewYork");
        localBean.updateAddress(address, false);
        assertFalse("Changed status should be false after updating", address.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        Address updatedAddress = localBean.retrieveAddress(address.getId());
        assertFalse("Changed status should be false after retrieving", updatedAddress.isChanged());
        this.assertAddressEquals(address, updatedAddress);

        localBean.removeAddress(address.getId(), false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localBean.retrieveAddress(address.getId()));
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
        localBean.addAddress(address, true);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the address with entity 1
        localBean.associate(address, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the same address with same entity 1 again, method is simply returned, no audit performed
        localBean.associate(address, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same address with another entity 2, the previous association should be deleted
        localBean.associate(address, 2, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, one for deletion of the previous association, one for insertion of
        //new association
        preCount = this.assertAuditRecordsCount(preCount, 2);

        localBean.deassociate(address, 3, true);
        //association does not exist, method is simply returned, no audit performed
        this.assertAuditRecordsCount(preCount, 0);

        //Deassociate address with entity 2
        localBean.deassociate(address, 2, true);
        //Audit should performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Remove the address
        localBean.removeAddress(address.getId(), true);
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
        localBean.addAddress(address, false);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the address with entity 1
        localBean.associate(address, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same address with same entity 1 again, method is simply returned, no audit performed
        localBean.associate(address, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same address with another entity 2, the previous association should be deleted
        localBean.associate(address, 2, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        localBean.deassociate(address, 3, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Deassociate address with entity 2
        localBean.deassociate(address, 2, false);
        //Audit should not be performed
        this.assertAuditRecordsCount(preCount, 0);

        //Remove the address
        localBean.removeAddress(address.getId(), false);
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
        localBean.addAddresses(addressesToAdd, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        long ids[] = new long[10];

        //Get all added addresses
        Address[] addedAddresses = localBean.getAllAddresses();
        assertTrue("There should be 10 address", addedAddresses.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addressesToAdd[i], addedAddresses[i]);
            assertFalse("Changed status should be false after adding", addressesToAdd[i].isChanged());
            ids[i] = addressesToAdd[i].getId();
            addedAddresses[i].setCity("City" + i);
            addedAddresses[i].setAddressType(AddressType.CLIENT);
        }

        //update the 10 addresses
        localBean.updateAddresses(addedAddresses, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        //Get all updated addresses
        Address[] updatedAddresses = localBean.getAllAddresses();
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(updatedAddresses[i], addedAddresses[i]);
            assertFalse("Changed status should be false after updating", addedAddresses[i].isChanged());
        }

        //Remove the 10 addresses
        localBean.removeAddresses(ids, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed, as no association
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no address after removal
        assertTrue("There should be 0 address", localBean.getAllAddresses().length == 0);
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
        localBean.addAddresses(addressesToAdd, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        long ids[] = new long[10];

        //Get all added addresses
        Address[] addedAddresses = localBean.getAllAddresses();
        assertTrue("There should be 10 address", addedAddresses.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addressesToAdd[i], addedAddresses[i]);
            assertFalse("Changed status should be false after adding", addressesToAdd[i].isChanged());
            ids[i] = addressesToAdd[i].getId();
            addedAddresses[i].setCity("City" + i);
        }

        //update the 10 addresses
        localBean.updateAddresses(addedAddresses, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Get all updated addresses
        Address[] updatedAddresses = localBean.getAllAddresses();
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(updatedAddresses[i], addedAddresses[i]);
            assertFalse("Changed status should be false after updating", addedAddresses[i].isChanged());
        }

        //Remove the 10 addresses
        localBean.removeAddresses(ids, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no address after removal
        assertTrue("There should be 0 address", localBean.getAllAddresses().length == 0);
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
        localBean.addAddresses(duplicateAddresses, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            ids[i] = duplicateAddresses[i].getId();
        }

        //Retrieve with duplicate ids
        Address[] addedAddresses = localBean.retrieveAddresses(ids);
        assertEquals("There should be only one record inserted into database", 1, addedAddresses.length);
        this.assertAddressEquals(address, addedAddresses[0]);

        address.setLine1("new line1");

        //Update with duplication objects
        localBean.updateAddresses(duplicateAddresses, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the address, so audit will be performed when removing
        localBean.associate(address, 1, false);

        localBean.removeAddresses(ids, true);
        this.assertTransactionCommited(transaction);
        //There should be only 2 audit record, including the record for deletion of association
        this.assertAuditRecordsCount(preCount, 2);

        //There should be no records after removal
        assertEquals(0, localBean.retrieveAddresses(ids).length);
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
        localBean.addAddresses(addresses, false);
        Thread.sleep(1000);
        Date to = new Date();

        Address[] results = localBean.searchAddresses(AddressFilterFactory.createCreatedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createModifiedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        //upperThreshold is null
        results = localBean.searchAddresses(AddressFilterFactory.createCreatedInFilter(from, null));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        //lowerThreshold is null
        results = localBean.searchAddresses(AddressFilterFactory.createModifiedInFilter(null, to));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localBean.removeAddresses(ids, false);
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

        localBean.addAddresses(addresses, false);

        Address[] results = localBean.searchAddresses(AddressFilterFactory.createCreatedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createModifiedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localBean.removeAddresses(ids, false);
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

        localBean.addAddresses(addresses, false);

        Address[] results = localBean.searchAddresses(AddressFilterFactory.createCityFilter("HangZhou"));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createZipCodeFilter("123456"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createStateIDFilter(1));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createStateNameFilter("ZheJiang"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createCountryIDFilter(1));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createCountryNameFilter("China"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localBean.removeAddresses(ids, false);
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

        localBean.addAddresses(addresses, false);

        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            localBean.associate(addresses[i], 1, false);
        }

        Address[] results = localBean.searchAddresses(AddressFilterFactory.createTypeFilter(AddressType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = localBean.searchAddresses(AddressFilterFactory.createEntityIDFilter(1, AddressType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        localBean.removeAddresses(ids, false);
    }
}
