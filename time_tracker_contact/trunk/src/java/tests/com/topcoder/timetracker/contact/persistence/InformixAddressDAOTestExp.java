/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.util.Date;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressDAO;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;


/**
 * <p>This test case contains failure tests for <code>InformixAddressDAO</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixAddressDAOTestExp extends BaseTestCase {
    /**
     * <p>
     * Indicates whether the audit related tables have been cleared.
     * </p>
     */
    private static boolean auditCleared = false;
    /**
     * <p>
     * <code>InformixAddressDAO</code> used in the test case.
     * </p>
     */
    private AddressDAO dao = null;

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
            this.executeUpdate("delete from audit_detail");
            this.executeUpdate("delete from audit");
            auditCleared = true;
        }
        dao = new InformixAddressDAO();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        dao = null;
        this.closeLastJDBCConnections();
        super.tearDown();
    }

    /**
     * <p>
     * Test constructor <code>InformixAddressDAO()</code>. Namespace is unknow, ConfigurationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixAddressDAO1() throws Exception {
        try {
            this.removeConfigManagerNS();
            new InformixAddressDAO();
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
            this.initialConfigManager();
        }
    }

    /**
     * <p>
     * Test constructor <code>InformixAddressDAO(String)</code>. Namespace is unknow, ConfigurationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixAddressDAO2() throws Exception {
        try {
            new InformixAddressDAO("unknown namespace");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test constructor <code>InformixAddressDAO(String, AuditManager)</code>.
     * Namespace is unknow, ConfigurationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixAddressDAO3() throws Exception {
        try {
            new InformixAddressDAO("unknown namespace", null);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
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
    public void testAddAddress1() throws Exception {
        try {
            this.dao.addAddress(null, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testAddAddress2() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            this.dao.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddAddress3() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            this.dao.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddAddress4() throws Exception {
        try {
            Address address = this.getAddress();
            address.getCountry().setId(-1);
            this.dao.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddAddress5() throws Exception {
        try {
            Address address = this.getAddress();
            address.getState().setId(-1);
            this.dao.addAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>addAddress()</code>.
     * IDGenerator is exhausted, IDGenerationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress_IDGenerationException()
        throws Exception {
        this.exhaustIDGenerator("AddressIDGenerator", 1);
        try {
            Address address = this.getAddress();
            this.dao.addAddress(address, false);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            //good
            this.exhaustIDGenerator("AddressIDGenerator", 0);
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
            this.dao.addAddress(address, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
            this.dao.addAddress(address, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
        }
    }
    /**
     * <p>
     * Test method <code>addAddresses()</code>.
     * Given array contains null member, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses() throws Exception {
        try {
            this.dao.addAddresses(new Address[]{null}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>addAddresses()</code>.
     * IDGenerator is exhausted, IDGenerationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses_IDGenerationException()
        throws Exception {
        this.exhaustIDGenerator("AddressIDGenerator", 1);
        try {
            Address address = this.getAddress();
            this.dao.addAddresses(new Address[]{address}, false);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            //good
            this.exhaustIDGenerator("AddressIDGenerator", 0);
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
        try {
            Address address = this.getAddress();
            address.getCountry().setId(10);
            this.dao.addAddresses(new Address[]{this.getAddress(), address}, false);
            fail("BatchOperationException expected");
        } catch (BatchOperationException e) {
            //good
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
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            Address address = this.getAddress();
            this.dao.addAddresses(new Address[]{address}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
        }
    }

    /**
     * <p>
     * Test method <code>retrieveAddress()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddress() throws Exception {
        try {
            this.dao.retrieveAddress(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").retrieveAddress(1);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.retrieveAddress(address.getId());
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>retrieveAddresses()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddresses() throws Exception {
        try {
            this.dao.retrieveAddresses(new long[]{-1});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").retrieveAddresses(new long[]{1});
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        Address address = this.getAddress();
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.retrieveAddresses(new long[]{address.getId()});
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
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
    public void testRemoveAddress() throws Exception {
        try {
            this.dao.removeAddress(-1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.removeAddress(address.getId(), true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeAddress(address.getId(), false);
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").removeAddress(1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.removeAddress(address.getId(), false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
    public void testRemoveAddresses() throws Exception {
        try {
            this.dao.removeAddresses(new long[]{-1}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
        Address address = this.getAddress();
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.removeAddresses(new long[]{address.getId()}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeAddress(address.getId(), false);
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").removeAddresses(new long[]{1}, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.removeAddresses(new long[]{address.getId()}, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
    public void testupdateAddress1() throws Exception {
        try {
            this.dao.updateAddress(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testUpdateAddress2() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            this.dao.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateAddress3() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            this.dao.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateAddress4() throws Exception {
        try {
            Address address = this.getAddress();
            address.getCountry().setId(-1);
            this.dao.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateAddress5() throws Exception {
        try {
            Address address = this.getAddress();
            address.getState().setId(-1);
            this.dao.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateAddress6() throws Exception {
        try {
            Address address = this.getAddress();
            this.dao.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateAddress7() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            this.dao.updateAddress(address, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            address.setCity("new city");
            this.dao.updateAddress(address, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeAddress(address.getId(), false);
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").updateAddress(address, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            address.setCity("new city");
            this.dao.updateAddress(address, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
            this.dao.updateAddress(address, false);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
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
        Address address = this.getAddress();
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            address.setCity("new city");
            this.dao.updateAddresses(new Address[]{address}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeAddress(address.getId(), false);
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").updateAddresses(new Address[]{address}, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            address.setCity("new city");
            this.dao.updateAddresses(new Address[]{address}, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setId(1);
            this.dao.updateAddresses(new Address[]{address}, false);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").getAllAddresses();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.getAllAddresses();
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
        }
    }
    /**
     * <p>
     * Test method <code>searchAddresses()</code>.
     * Given filter is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchAddresses() throws Exception {
        try {
            this.dao.searchAddresses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>searchAddresses()</code>.
     * Given filter is not searchable, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchAddresses_PersistenceException() throws Exception {
        try {
            this.dao.searchAddresses(new EqualToFilter("address_id", new Long(1)));
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>searchAddresses()</code>.
     * The searched address is associated with mutiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchAddresses_AssociationException() throws Exception {
        Address address = this.getAddress();
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.searchAddresses(AddressFilterFactory.createCreatedByFilter("user"));
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
    public void testassociate1() throws Exception {
        try {
            this.dao.associate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testassociate2() throws Exception {
        try {
            this.dao.associate(new Address(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testassociate3() throws Exception {
        try {
            Address address = new Address();
            this.dao.associate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate4() throws Exception {
        try {
            Address address = new Address();
            address.setId(1);
            this.dao.associate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate5() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationUser(null);
            this.dao.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate6() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            this.dao.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate7() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setModificationDate(new Date());
            address.setCreationDate(null);
            this.dao.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate8() throws Exception {
        try {
            Address address = this.getAddress();
            address.setCreationDate(new Date());
            address.setModificationDate(new Date());
            address.setModificationDate(null);
            this.dao.associate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.associate(address, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeAddress(address.getId(), false);
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
        this.dao.addAddress(address, false);
        try {
            address.setId(address.getId() + 1);
            this.dao.associate(address, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.dao.removeAddress(address.getId() - 1, false);
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.associate(address, 3, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").associate(address, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
    public void testdeassociate1() throws Exception {
        try {
            this.dao.deassociate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testdeassociate2() throws Exception {
        try {
            this.dao.deassociate(new Address(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testdeassociate3() throws Exception {
        try {
            Address address = new Address();
            this.dao.deassociate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testdeassociate4() throws Exception {
        try {
            Address address = new Address();
            address.setId(1);
            this.dao.deassociate(address, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testdeassociate5() throws Exception {
        try {
            Address address = this.getAddress();
            address.setModificationUser(null);
            this.dao.deassociate(address, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
        this.dao.addAddress(address, false);
        try {
            address.setId(address.getId() + 1);
            this.dao.deassociate(address, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.dao.removeAddress(address.getId() - 1, false);
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("insert into address_relation (creation_user,modification_user,"
            + "creation_date,modification_date,address_id,address_type_id,entity_id) values ('u','u',current,current,"
            + address.getId() + ",1,2) ");
        try {
            this.dao.deassociate(address, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from address_relation");
            this.executeUpdate("delete from address");
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").deassociate(address, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addAddress(address, false);
        this.dao.associate(address, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.deassociate(address, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeAddress(address.getId(), false);
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").getAllCountries();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
            new InformixAddressDAO("InformixAddressDAO_Error_4").getAllStates();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        }
    }
}
