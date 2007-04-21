/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.InitialContext;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.MockTransaction;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>This test case contains failure tests for <code>AddressBean</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressBeanTestExp extends BaseTestCase {

    /**
     * <p>
     * <code>AddressLocal</code> used in the test case.
     * </p>
     */
    private static AddressLocal localBean = null;
    /**
     * <p>
     * Indicates whether the audit related tables have been cleared.
     * </p>
     */
    private static boolean auditCleared = false;
    /**
     * <p>
     * <code>MockTransaction</code> used in the test case.
     * </p>
     */
    private MockTransaction transaction = null;

    /**
     * <p>
     * The records count of <em>audit</em> table.
     * </p>
     */
    private int preAuditCount = 0;
    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        if (!auditCleared) {
            localBean = this.getAddressLocal();
            this.executeUpdate("delete from audit_detail");
            this.executeUpdate("delete from audit");
            auditCleared = true;
        }
        transaction = this.getTransaction();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        transaction = null;
        super.tearDown();
    }

    /**
     * <p>
     * Get <code>AddressLocal</code> which will refer to a wrong datasource.
     * </p>
     *
     * @return <code>AddressLocal</code>.
     *
     * @throws Exception to JUnit.
     */
    private AddressLocal getWrongAddressLocal() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_5");
        this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        AddressLocalHome localHome = (AddressLocalHome) new InitialContext().lookup("java:comp/env/ejb/AddressLocal");
        return localHome.create();
    }

    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Unknown namespace for Object Factory.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate1() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "UnknownObjectFactoryNS");
            new AddressBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Object Factory points to an unknown namspace for AddressDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate2() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_1");
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
            new AddressBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Object Factory points to an invalid class type.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate3() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_2");
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
            new AddressBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Wrong object binded to JNDI name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate4() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", new Long(1));
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
            new AddressBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Null binded to JNDI name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate5() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", null);
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
            new AddressBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Null binded to JNDI name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate6() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
            this.bind("java:comp/env/AddressDAOKey", null);
            new AddressBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Given address is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IAE() throws Exception {
        try {
            localBean.addAddress(null, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IPE1() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            localBean.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IPE2() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            localBean.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Id of country is negative, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IPE3() throws Exception {
        try {
            Address address = this.getAddress();
            address.getCountry().setId(-1);
            localBean.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Id of state is negative, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IPE4() throws Exception {
        try {
            Address address = this.getAddress();
            address.getState().setId(-1);
            localBean.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Id got from <code>IDGenerator</code> is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IDGenerationException() throws Exception {
        long id = IDGeneratorFactory.getIDGenerator("AddressIDGenerator").getNextID();
        try {
            this.updateIDGenerator("AddressIDGenerator", -1);
            localBean.addAddress(this.getAddress(), false);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.updateIDGenerator("AddressIDGenerator", id + 1);
        }
    }
    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Foreign key constraint violated on country id, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_PersistenceException()
        throws Exception {
        try {
            Address address = this.getAddress();
            address.getCountry().setId(10);
            localBean.addAddress(address, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_AuditException()
        throws Exception {
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            Address address = this.getAddress();
            localBean.addAddress(address, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>addAddresses()</code>.
     * Given address is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses_IAE() throws Exception {
        try {
            localBean.addAddresses(new Address[]{null}, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses_IPE() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            localBean.addAddresses(new Address[]{address}, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Id got from <code>IDGenerator</code> is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses_IDGenerationException() throws Exception {
        int preAddressCount = this.getAddressRecordsCount();
        long id = IDGeneratorFactory.getIDGenerator("AddressIDGenerator").getNextID();
        try {
            this.updateIDGenerator("AddressIDGenerator", Long.MAX_VALUE - 1);
            localBean.addAddresses(new Address[]{this.getAddress(), this.getAddress(), this.getAddress()},
                true);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.assertAddressRecordsCount(preAddressCount, 0);
            this.updateIDGenerator("AddressIDGenerator", id + 1);
        }
    }
    /**
     * <p>
     * Test method <code>addAddresses()</code>.
     * Foreign key constraint violated on country id, BatchOperationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses_BatchOperationException()
        throws Exception {
        int preAddressCount = this.getAddressRecordsCount();
        try {
            Address address = this.getAddress();
            address.getCountry().setId(10);
            localBean.addAddresses(new Address[]{this.getAddress(), this.getAddress(), address}, true);
            fail("BatchOperationException expected");
        } catch (BatchOperationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.assertAddressRecordsCount(preAddressCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>addAddresses()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses_AuditException()
        throws Exception {
        int preAddressCount = this.getAddressRecordsCount();
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            Address address1 = this.getAddress();
            address1.setAddressType(AddressType.CLIENT);
            Address address2 = this.getAddress();
            address2.setAddressType(AddressType.COMPANY);
            Address address3 = this.getAddress();
            address3.setAddressType(AddressType.USER);
            Address address4 = this.getAddress();
            address4.setAddressType(AddressType.PROJECT);
            localBean.addAddresses(new Address[]{address1, address2, address3, address4}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.assertAddressRecordsCount(preAddressCount, 0);
        }
    }

    /**
     * <p>
     * Test method <code>retrieveAddress()</code>.
     * Id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddress_IAE() throws Exception {
        try {
            localBean.retrieveAddress(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveAddress()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddress_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().retrieveAddress(1);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveAddress()</code>.
     * Address is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddress_AssociationException() throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            localBean.retrieveAddress(address.getId());
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>retrieveAddresses()</code>.
     * Id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddresses_IAE() throws Exception {
        try {
            localBean.retrieveAddresses(new long[]{-1});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveAddresses()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddresses_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().retrieveAddresses(new long[]{1});
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveAddresses()</code>.
     * Address is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddresses_AssociationException() throws Exception {
        Address address1 = this.getAddress();
        localBean.addAddress(address1, false);
        localBean.associate(address1, 1, false);
        Address address2 = this.getAddress();
        localBean.addAddress(address2, false);
        localBean.associate(address2, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address2.getId() + ",1,2) ");
        try {
            localBean.retrieveAddresses(new long[]{address1.getId(), address2.getId()});
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Given address is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddress_IAE() throws Exception {
        try {
            localBean.updateAddress(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress_IPE1() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            localBean.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress_IPE2() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            localBean.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Id of country is negative, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress_IPE3() throws Exception {
        try {
            Address address = this.getAddress();
            address.getCountry().setId(-1);
            localBean.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Id of state is negative, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress_IPE4() throws Exception {
        try {
            Address address = this.getAddress();
            address.getState().setId(-1);
            localBean.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Creation date is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress_IPE5() throws Exception {
        try {
            Address address = this.getAddress();
            localBean.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Id is negative, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress_IPE6() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            localBean.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddress_AuditException()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            address.setCity("new city");
            localBean.updateAddress(address, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeAddress(address.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddress_PersistenceException() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setId(1);
            this.getWrongAddressLocal().updateAddress(address, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * Address is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddress_AssociationException() throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            address.setCity("new city");
            localBean.updateAddress(address, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>updateAddress()</code>.
     * No exsiting record in database, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddress_EntityNotFoundException() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setId(1);
            localBean.updateAddress(address, true);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddresses()</code>.
     * Given address is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddresses_IAE() throws Exception {
        try {
            localBean.updateAddresses(new Address[]{null}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddresses()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddresses_IPE() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            localBean.updateAddresses(new Address[]{address}, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddresses()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddresses_AuditException()
        throws Exception {
        Address address1 = this.getAddress();
        address1.setAddressType(AddressType.CLIENT);
        Address address2 = this.getAddress();
        address2.setAddressType(AddressType.COMPANY);
        Address address3 = this.getAddress();
        address3.setAddressType(AddressType.USER);
        Address address4 = this.getAddress();
        address4.setAddressType(AddressType.PROJECT);
        localBean.addAddress(address1, false);
        localBean.addAddress(address2, false);
        localBean.addAddress(address3, false);
        localBean.addAddress(address4, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            address1.setCity("new city");
            address2.setCity("new city");
            address3.setCity("new city");
            address4.setCity("new city");
            localBean.updateAddresses(new Address[]{address1, address2, address3, address4}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>updateAddresses()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddresses_PersistenceException() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setId(1);
            this.getWrongAddressLocal().updateAddresses(new Address[]{address}, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>updateAddresses()</code>.
     * Address is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddresses_AssociationException() throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            address.setCity("new city");
            localBean.updateAddresses(new Address[]{address}, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>updateAddresses()</code>.
     * No exsiting record in database, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateAddresses_EntityNotFoundException() throws Exception {
        Address address1 = this.getAddress();
        localBean.addAddress(address1, false);
        address1.setCity("new city");
        Address address2 = this.getAddress();
        localBean.addAddress(address2, false);
        address2.setCity("new city");
        Address address3 = this.getAddress();
        address3.setCreationDate(new Date());
        address3.setId(address2.getId() + 2); // does not exist
        try {
            localBean.updateAddresses(new Address[]{address1, address2, address3}, true);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>removeAddress()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddress_IAE() throws Exception {
        try {
            localBean.removeAddress(-1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeAddress()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddress_AuditException()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            localBean.removeAddress(address.getId(), true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeAddress(address.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>removeAddress()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddress_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().removeAddress(1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeAddress()</code>.
     * Address is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddress_AssociationException() throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            localBean.removeAddress(address.getId(), true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>removeAddresses()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddresses_IAE() throws Exception {
        try {
            localBean.removeAddresses(new long[]{-1}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeAddresses()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddresses_AuditException()
        throws Exception {
        Address address1 = this.getAddress();
        address1.setAddressType(AddressType.CLIENT);
        Address address2 = this.getAddress();
        address2.setAddressType(AddressType.COMPANY);
        Address address3 = this.getAddress();
        address3.setAddressType(AddressType.USER);
        Address address4 = this.getAddress();
        address4.setAddressType(AddressType.PROJECT);

        localBean.addAddress(address1, false);
        localBean.addAddress(address2, false);
        localBean.addAddress(address3, false);
        localBean.addAddress(address4, false);
        localBean.associate(address1, 1, false);
        localBean.associate(address2, 1, false);
        localBean.associate(address3, 1, false);
        localBean.associate(address4, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            localBean.removeAddresses(new long[]{address1.getId(), address2.getId(), address3.getId(),
                address4.getId()}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>removeAddresses()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddresses_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().removeAddresses(new long[]{1}, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeAddresses()</code>.
     * Address is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddresses_AssociationException() throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            localBean.removeAddresses(new long[]{address.getId()}, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>getAllCountries()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllCountries_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().getAllCountries();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>getAllStates()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllStates_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().getAllStates();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>getAllAddresses()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllAddresses_PersistenceException() throws Exception {
        try {
            this.getWrongAddressLocal().getAllAddresses();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>getAllAddresses()</code>.
     * The address is associated with mutiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllAddresses_AssociationException() throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            localBean.getAllAddresses();
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given address is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IAE1() throws Exception {
        try {
            localBean.associate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given entity id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IAE2() throws Exception {
        try {
            localBean.associate(new Address(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Id of address is negative, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE1() throws Exception {
        try {
            Address address = new Address();
            localBean.associate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Type of address is null, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE2() throws Exception {
        try {
            Address address = new Address();
            address.setId(1);
            localBean.associate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE3() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            localBean.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>associate()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE4() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            localBean.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Creation date is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE5() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setModificationDate(new Date());
            address.setCreationDate(null);
            localBean.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>associate()</code>.
     * Modification date is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE6() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setModificationDate(new Date());
            address.setModificationDate(null);
            localBean.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_AuditException()
        throws Exception {
        Address address = this.getAddress();
        address.setAddressType(AddressType.USER);
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            address.setAddressType(AddressType.PROJECT);
            localBean.associate(address, 2, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeAddress(address.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given address is not an existing record in database, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_AssociationException1()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        try {
            address.setId(address.getId() + 1);
            localBean.associate(address, 1, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            localBean.removeAddress(address.getId() - 1, false);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given address is currently associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_AssociationException2()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            localBean.associate(address, 3, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_PersistenceException() throws Exception {
        Address address = this.getAddress();
        address.setId(1);
        address.setCreationDate(new Date());
        address.setModificationDate(new Date());
        try {
            this.getWrongAddressLocal().associate(address, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Given address is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IAE1() throws Exception {
        try {
            localBean.deassociate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Given entity id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IAE2() throws Exception {
        try {
            localBean.deassociate(new Address(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Id of address is negative, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IPE1() throws Exception {
        try {
            Address address = new Address();
            localBean.deassociate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Type of address is null, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IPE2() throws Exception {
        try {
            Address address = new Address();
            address.setId(1);
            localBean.deassociate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IPE3() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            localBean.deassociate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given address is not an existing record in database, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_AssociationException1()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        try {
            address.setId(address.getId() + 1);
            localBean.deassociate(address, 1, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            localBean.removeAddress(address.getId() - 1, false);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Given address is currently associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_AssociationException2()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            localBean.deassociate(address, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_AuditException()
        throws Exception {
        Address address = this.getAddress();
        localBean.addAddress(address, false);
        localBean.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            localBean.deassociate(address, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeAddress(address.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_PersistenceException() throws Exception {
        Address address = this.getAddress();
        address.setId(1);
        try {
            this.getWrongAddressLocal().deassociate(address, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
}
