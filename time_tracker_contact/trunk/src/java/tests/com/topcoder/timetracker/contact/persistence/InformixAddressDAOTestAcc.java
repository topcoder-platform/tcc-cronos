/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.util.Date;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressDAO;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.util.idgenerator.IDGenerator;


/**
 * <p>This test case contains accuracy tests for <code>InformixAddressDAO</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixAddressDAOTestAcc extends BaseTestCase {
    /**
     * <p>
     * <code>InformixAddressDAO</code> used in the test case.
     * </p>
     */
    private AddressDAO addressDao = null;

    /**
     * <p>
     * <code>Address</code> used in the test case.
     * </p>
     */
    private Address address = null;

    /**
     * <p>
     * <code>AuditManager</code> used in the test case.
     * </p>
     */
    private AuditManager auditManager = null;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        addressDao = new InformixAddressDAO();
        address = this.getAddress();
        auditManager = (AuditManager) this.getField(addressDao, "auditManager");
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        auditManager = null;
        addressDao = null;
        address = null;
        super.tearDown();
    }
    /**
     * <p>
     * Assert the connection name and IDGenerator name of given <code>InformixAddressDAO</code>.
     * </p>
     * @param dao <code>InformixAddressDAO</code> to assert
     * @throws Exception to JUnit
     */
    private void assertInformixAddressDAO(AddressDAO dao) throws Exception {
        assertNotNull(dao);
        assertTrue(dao instanceof InformixAddressDAO);
        assertEquals("InformixJNDIConnection", this.getField(dao, "connectionName"));
        IDGenerator idGenerator = (IDGenerator) this.getField(dao, "addressIDGenerator");
        assertEquals("AddressIDGenerator", idGenerator.getIDName());
    }

    /**
    * <p>
    * Test constructor <code>InformixAddressDAO()</code>.
    * </p>
    *
    * @throws Exception to JUnit
    */
    public void testCtor1() throws Exception {
        assertInformixAddressDAO(addressDao);
    }

    /**
     * <p>
     * Test constructor <code>InformixAddressDAO()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        addressDao = new InformixAddressDAO(InformixAddressDAO.class.getName());
        assertInformixAddressDAO(addressDao);
    }

    /**
     * <p>
     * Test constructor <code>InformixAddressDAO()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        String namespace = InformixAddressDAO.class.getName();
        addressDao = new InformixAddressDAO(namespace, DAOHelper.getAuditManager(namespace));
        assertInformixAddressDAO(addressDao);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>associate()</code>, <code>retrieveAddress()</code>
     * and <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationWithAudit() throws Exception {
        //Add an address
        addressDao.addAddress(address, true);
        Address addedAddress = addressDao.retrieveAddress(address.getId());

        //Since no association, the address type should be null
        assertNull(addedAddress.getAddressType());
        this.assertAddressEquals(address, addedAddress);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 11, -1, -1, -1, -1);

        //Associate the address with entity id 1
        addressDao.associate(address, 1, false);
        addedAddress = addressDao.retrieveAddress(address.getId());
        //The address type should not be null after association
        assertEquals(address.getAddressType(), addedAddress.getAddressType());
        this.assertAddressEquals(address, addedAddress);

        //Remove the address
        addressDao.removeAddress(address.getId(), true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 11, 1, -1, -1, -1);

        //The deletion of address association should also be audited
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 1, -1, -1, -1);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>retrieveAddress()</code>
     * and <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationWithoutAudit() throws Exception {
        addressDao.addAddress(address, false);
        this.assertAddressEquals(address, addressDao.retrieveAddress(address.getId()));
        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        addressDao.removeAddress(address.getId(), true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code>, <code>associate()</code>, <code>retrieveAddresses()</code>
     * and <code>removeAddresses()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationsWithAudit() throws Exception {
        Address[] addresses = new Address[2];
        addresses[0] = this.getAddress();
        addresses[1] = this.getAddress();

        //Add the addresses
        addressDao.addAddresses(addresses, true);
        long ids[] = new long[]{addresses[0].getId(), addresses[1].getId()};
        Address[] addedAddresses = addressDao.retrieveAddresses(ids);
        assertEquals(2, addedAddresses.length);

        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 11, -1, -1, -1, -1);

        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 11, -1, -1, -1, -1);

        //Associate the addresses with entity id
        addressDao.associate(addresses[0], 1, false);
        addressDao.associate(addresses[1], 2, false);

        //Remove the addresses
        addressDao.removeAddresses(ids, true);

        //Assert Audit record for addresses[0]
        headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 11, 1, -1, -1, -1);
        //The deletion of address association should also be audited
        headers = this.getAuditRecord(auditManager, "address_relation", addresses[0].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 1, -1, -1, -1);

        //Assert Audit record for addresses[1]
        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 11, 2, -1, -1, -1);
        //The deletion of address association should also be audited
        headers = this.getAuditRecord(auditManager, "address_relation", addresses[1].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 2, -1, -1, -1);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code> and <code>retrieveAddresses()</code>
     * and <code>removeAddresses()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationsWithoutAudit() throws Exception {
        Address[] addresses = new Address[2];
        addresses[0] = this.getAddress();
        addresses[1] = this.getAddress();

        addressDao.addAddresses(addresses, false);
        long ids[] = new long[]{addresses[0].getId(), addresses[1].getId()};
        Address[] addedAddresses = addressDao.retrieveAddresses(ids);
        assertEquals(2, addedAddresses.length);

        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        addressDao.removeAddresses(ids, false);
        headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>updateAddress()</code> and
     * <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddressWithAudit() throws Exception {

        //Add an address
        addressDao.addAddress(address, true);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 11, -1, -1, -1, -1);

        //Update the address, not changed, there should be no audit records
        addressDao.updateAddress(address, true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        address.setCity("new city");
        address.setPostalCode("2343252");
        Thread.sleep(1500);
        //Update the address again
        addressDao.updateAddress(address, true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //3 details, city is changed, postal code is changed, modification date is changed
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.UPDATE, 3, -1, -1, -1, -1);

        Address updatedAddress = addressDao.retrieveAddress(address.getId());
        this.assertAddressEquals(updatedAddress, address);

        //No association, no audit when removal
        addressDao.removeAddress(address.getId(), true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>updateAddress()</code> and
     * <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddressWithoutAudit() throws Exception {

        //Add an address
        addressDao.addAddress(address, false);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Update the address, not changed
        addressDao.updateAddress(address, false);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        address.setCity("new city");
        address.setPostalCode("2343252");
        //Update the address again, city is changed, postal code is changed, modification date is changed
        addressDao.updateAddress(address, false);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 0, headers.length);

        Address updatedAddress = addressDao.retrieveAddress(address.getId());
        this.assertAddressEquals(updatedAddress, address);

        //Remove the address
        addressDao.removeAddress(address.getId(), false);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code>, <code>updateAddresses()</code> and
     * <code>removeAddresses()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddressesWithAudit() throws Exception {
        Address[] addresses = new Address[2];
        addresses[0] = this.getAddress();
        addresses[1] = this.getAddress();

        //Add an addresses
        addressDao.addAddresses(addresses, true);

        //Update the addresses, not changed, there should be no audit records
        addressDao.updateAddresses(addresses, true);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        addresses[0].setCity("new city");
        addresses[1].setPostalCode("2343252");
        Thread.sleep(1500);
        //Update the addresses again
        addressDao.updateAddresses(addresses, true);
        headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //2 details, city is changed, modification date is changed
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.UPDATE, 2, -1, -1, -1, -1);

        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //2 details, postal code is changed, modification date is changed
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.UPDATE, 2, -1, -1, -1, -1);

        Address[] updatedAddresses =
            addressDao.retrieveAddresses(new long[]{addresses[0].getId(), addresses[1].getId()});
        this.assertAddressEquals(updatedAddresses[0], addresses[0]);
        this.assertAddressEquals(updatedAddresses[1], addresses[1]);

        //No association, no audit when removal
        addressDao.removeAddresses(new long[]{addresses[0].getId(), addresses[1].getId()}, true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddresses()</code>, <code>updateAddresses()</code> and
     * <code>removeAddresses()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddressesWithoutAudit() throws Exception {
        Address[] addresses = new Address[2];
        addresses[0] = this.getAddress();
        addresses[1] = this.getAddress();

        //Add an addresses
        addressDao.addAddresses(addresses, false);

        //Update the addresses, not changed, there should be no audit records
        addressDao.updateAddresses(addresses, false);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        addresses[0].setCity("new city");
        addresses[1].setPostalCode("2343252");
        //Update the addresses again
        addressDao.updateAddresses(addresses, false);
        headers = this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        Address[] updatedAddresses =
            addressDao.retrieveAddresses(new long[]{addresses[0].getId(), addresses[1].getId()});
        this.assertAddressEquals(updatedAddresses[0], addresses[0]);
        this.assertAddressEquals(updatedAddresses[1], addresses[1]);

        //No association, no audit when removal
        addressDao.removeAddresses(new long[]{addresses[0].getId(), addresses[1].getId()}, false);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>associate()</code>, <code>deassociate()</code>
     * and <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociationWithAudit() throws Exception {
        //Add an address
        addressDao.addAddress(address, true);

        //Associate address with entity id 2
        addressDao.associate(address, 2, true);
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 7, 2, -1, -1, -1);

        //Associate address with entity id 2 again, nothing happen
        addressDao.associate(address, 2, true);
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.INSERT);
        //There are still one record for insertion
        assertEquals("There should be 1 header record", 1, headers.length);

        //Associate address with entity id 3, previous association will be deleted first
        addressDao.associate(address, 3, true);
        //Audti record for deletion of previous association
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 2, -1, -1, -1);
        //Now there are 2 records for insertion
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.INSERT);
        assertEquals("There should be 2 header records", 2, headers.length);
        //7 details
        this.assertAuditHeader(headers[1], ApplicationArea.TT_PROJECT, AuditType.INSERT, 7, 3, -1, -1, -1);

        //Deassociate address with entity id 4, nothing happen
        addressDao.deassociate(address, 4, true);
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        //There are still 1 record for deletion
        assertEquals("There should be 1 header record", 1, headers.length);

        //Deassociate address with entity id 3
        addressDao.deassociate(address, 3, true);
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        //Now there are 2 records for deletion
        assertEquals("There should be 2 header record", 2, headers.length);
        //7 details
        this.assertAuditHeader(headers[1], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 3, -1, -1, -1);

        addressDao.removeAddress(address.getId(), true);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        //No audit as no association
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addAddress()</code>, <code>associate()</code>, <code>deassociate()</code>
     * and <code>removeAddress()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociationWithoutAudit() throws Exception {
        //Add an address
        addressDao.addAddress(address, false);

        //Associate address with entity id 2
        addressDao.associate(address, 2, false);
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Associate address with entity id 2 again, nothing happen
        addressDao.associate(address, 2, false);
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Associate address with entity id 3, previous association will be deleted first
        addressDao.associate(address, 3, false);
        //Audti record for deletion of previous association
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Deassociate address with entity id 4, nothing happen
        addressDao.deassociate(address, 4, false);
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Deassociate address with entity id 3
        addressDao.deassociate(address, 3, false);
        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);

        addressDao.removeAddress(address.getId(), false);
        headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test method <code>addAddresses()</code>. Given array contains duplication(objects with same reference),
     * only one record should be inserted into database for the duplication objects.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddDuplicateAddresses() throws Exception {
        Address[] addresses = new Address[4];
        addresses[0] = this.getAddress();
        addresses[1] = address; //addresses[0] and addresses[1] have different object references, both will be inserted
        addresses[2] = address; //duplicate object reference
        addresses[3] = address; //duplicate object reference

        int preCount = this.getAuditRecordsCount();
        addressDao.addAddresses(addresses, true);
        this.assertAuditRecordsCount(preCount, 2);

        Address[] addedAddresses = addressDao.getAllAddresses();
        assertEquals("There should be 2 addresses added", 2, addedAddresses.length);
        this.assertAddressEquals(addresses[0], addedAddresses[0]);
        this.assertAddressEquals(addresses[1], addedAddresses[1]);

        //Audit record for addresses[0]
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 11, -1, -1, -1, -1);

        //Audit record for addresses[1]
        headers = this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 11, -1, -1, -1, -1);

        addressDao.removeAddresses(new long[]{addresses[0].getId(), addresses[1].getId()}, false);
    }

    /**
     * <p>
     * Test method <code>updateAddresses()</code>. Given array contains duplication(objects with same reference),
     * it will be updated only once and only one audit record created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateDuplicateAddresses() throws Exception {
        Address[] addresses = new Address[2];
        addresses[0] = this.getAddress();
        addresses[1] = this.getAddress();

        //Add the 2 addresses
        addressDao.addAddresses(addresses, false);

        Address[] addressesToBeUpdated = new Address[4];
        addresses[0].setCity("new city0"); //addresses[0] will be updated
        addressesToBeUpdated[0] = addresses[0];
        addresses[1].setCity("new city1"); //addresses[1] will be updated
        addressesToBeUpdated[1] = addresses[1];

        addressesToBeUpdated[2] = addresses[0]; //duplicate object reference, addresses[0] will only be updated once

        address.setId(addresses[1].getId()); //addresses[1] will be updated twice
        address.setCreationDate(addresses[1].getCreationDate());
        address.setCity("new city2");
        addressesToBeUpdated[3] = address;

        int preCount = this.getAuditRecordsCount();
        addressDao.updateAddresses(addressesToBeUpdated, true);
        this.assertAuditRecordsCount(preCount, 3);

        //1 Audit record for addresses[0]
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "address", addresses[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);

        //2 Audit records for addresses[1]
        headers =
            this.getAuditRecord(auditManager, "address", addresses[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 2 header record", 2, headers.length);

        Address[] updatedAddresses = addressDao.getAllAddresses();
        this.assertAddressEquals(addresses[0], updatedAddresses[0]);
        this.assertAddressEquals(address, updatedAddresses[1]);

        addressDao.removeAddresses(new long[]{addresses[0].getId(), addresses[1].getId()}, false);
    }

    /**
     * <p>
     * Test <code>removeAddresses()</code>. Remove the same id twice, the audit should be performed only once.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveDuplicateAddresses() throws Exception {
        //Add an address
        addressDao.addAddress(address, false);

        //Associate the address, so audit will be performed when deleting
        addressDao.associate(address, 3, false);

        int preCount = this.getAuditRecordsCount();
        addressDao.removeAddresses(new long[]{address.getId(), address.getId()}, true);
        //One audit record for deletion of address, one audit record for deletion of association
        this.assertAuditRecordsCount(preCount, 2);

        AuditHeader[] headers = this.getAuditRecord(auditManager, "address", address.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //11 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 11, 3, -1, -1, -1);

        headers = this.getAuditRecord(auditManager, "address_relation", address.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 3, -1, -1, -1);
    }

    /**
     * <p>
     * Test <code>retrieveAddresses()</code>. Given ids contains duplicate, returned array should contain
     * only one address for the duplicate ids.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveDuplicateAddresses() throws Exception {
        //Add an address
        addressDao.addAddress(address, false);

        Address[] retrievedAddresses = addressDao.retrieveAddresses(new long[]{address.getId(), address.getId()});

        assertEquals("Only 1 address returned", 1, retrievedAddresses.length);
        this.assertAddressEquals(address, retrievedAddresses[0]);

        addressDao.removeAddress(address.getId(), false);
    }

    /**
     * <p>
     * Test <code>addeAddresses()</code>, <code>updateAddresses()</code>, <code>removeAddresses()</code>
     * with empty passed in array. Nothing happen.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEmptyArray() throws Exception {
        int preCount = this.getAuditRecordsCount();
        addressDao.addAddresses(new Address[]{}, true);
        addressDao.updateAddresses(new Address[]{}, true);
        addressDao.removeAddresses(new long[]{}, true);
        this.assertAuditRecordsCount(preCount, 0);
    }

    /**
     * <p>
     * Test <code>retrieveAddress()</code>, <code>retrieveAddresses()</code>,<code>getAllAddress()</code>.
     * There is no address in database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieve() throws Exception {
        Address[] addresses = addressDao.retrieveAddresses(new long[]{1, 2});
        assertEquals("No address", 0, addresses.length);
        addresses = addressDao.getAllAddresses();
        assertEquals("No address", 0, addresses.length);
        assertNull("No address", addressDao.retrieveAddress(1));
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
        addressDao.addAddresses(addresses, false);
        Thread.sleep(1000);
        Date to = new Date();

        Address[] results = addressDao.searchAddresses(AddressFilterFactory.createCreatedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createModifiedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        //upperThreshold is null
        results = addressDao.searchAddresses(AddressFilterFactory.createCreatedInFilter(from, null));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        //lowerThreshold is null
        results = addressDao.searchAddresses(AddressFilterFactory.createModifiedInFilter(null, to));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        addressDao.removeAddresses(ids, false);
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

        addressDao.addAddresses(addresses, false);

        Address[] results = addressDao.searchAddresses(AddressFilterFactory.createCreatedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createModifiedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        addressDao.removeAddresses(ids, false);
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

        addressDao.addAddresses(addresses, false);

        Address[] results = addressDao.searchAddresses(AddressFilterFactory.createCityFilter("HangZhou"));
        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createZipCodeFilter("123456"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createStateIDFilter(1));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createStateNameFilter("ZheJiang"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createCountryIDFilter(1));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createCountryNameFilter("China"));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        addressDao.removeAddresses(ids, false);
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

        addressDao.addAddresses(addresses, false);

        for (int i = 0; i < 10; i++) {
            ids[i] = addresses[i].getId();
            addressDao.associate(addresses[i], 1, false);
        }

        Address[] results = addressDao.searchAddresses(AddressFilterFactory.createTypeFilter(AddressType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        results = addressDao.searchAddresses(AddressFilterFactory.createEntityIDFilter(1, AddressType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertAddressEquals(addresses[i], results[i]);
        }

        addressDao.removeAddresses(ids, false);
    }


    /**
     * <p>
     * Test accuracy of method <code>getAllCountries()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllCountries() throws Exception {
        Country[] countries = addressDao.getAllCountries();
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
        State[] states = addressDao.getAllStates();
        assertEquals("There should be 2 states", 2, states.length);
        assertFalse("Changed status should be false", states[0].isChanged());
        assertEquals("ZheJiang", states[0].getName());
        assertEquals("ZJ", states[0].getAbbreviation());
        assertFalse("Changed status should be false", states[1].isChanged());
        assertEquals("Alaska", states[1].getName());
        assertEquals("AK", states[1].getAbbreviation());
    }
}
