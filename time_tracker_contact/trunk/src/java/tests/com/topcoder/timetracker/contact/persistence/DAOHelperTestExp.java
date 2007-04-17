/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;


/**
 * <p>Failure tests for <code>DAOHelper</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DAOHelperTestExp extends BaseTestCase {

    /**
     * <p>
     * Test getConnectionFactory(String).
     * default connection name is empty, ConfigurationException expected.
     * </p>
     */
    public void testGetConnectionFactory1() {
        try {
            DAOHelper.getConnectionFactory("InformixAddressDAO_Error_0");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getConnectionFactory(String).
     * default connection name is unknown, ConfigurationException expected.
     * </p>
     */
    public void testGetConnectionFactory2() {
        try {
            DAOHelper.getConnectionFactory("InformixAddressDAO_Error_1");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getConnectionFactory(String).
     * "connections" property is missing, ConfigurationException expected.
     * </p>
     */
    public void testGetConnectionFactory3() {
        try {
            DAOHelper.getConnectionFactory("InformixAddressDAO_Error_2");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getConnectionFactory(String).
     * JNDI name is unknown, ConfigurationException expected.
     * </p>
     */
    public void testGetConnectionFactory4() {
        try {
            DAOHelper.getConnectionFactory("InformixAddressDAO_Error_3");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getIDGenerator(String).
     * Given IDGenerator name does not exist, ConfigurationException expected.
     * </p>
     */
    public void testGetIDGenerator() {
        try {
            DAOHelper.getIDGenerator("InformixAddressDAO_Error_0");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getSearchBundle(String).
     * Context is missing, ConfigurationException expected.
     * </p>
     */
    public void testGetSearchBundle1() {
        try {
            DAOHelper.getSearchBundle("InformixAddressDAO_Error_0");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getSearchBundle(String).
     * No SearchBundle configured, ConfigurationException expected.
     * </p>
     */
    public void testGetSearchBundle2() {
        try {
            DAOHelper.getSearchBundle("InformixAddressDAO_Error_1");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getAuditManager(String).
     * Class type is wrong, ConfigurationException expected
     * </p>
     */
    public void testGetAuditManager1() {
        try {
            DAOHelper.getAuditManager("InformixAddressDAO_Error_2");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getAuditManager(String).
     * Namespace for AuditManager is unknown, ConfigurationException expected
     * </p>
     */
    public void testGetAuditManager2() {
        try {
            DAOHelper.getAuditManager("InformixAddressDAO_Error_3");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test audit(). ApplicationArea is missed, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAudit1() throws Exception {
        try {
            AuditHeader header = DAOHelper.getAuditHeader(AuditType.INSERT, "user", "table", 1);
            AuditManager auditManager = DAOHelper.getAuditManager(InformixAddressDAO.class.getName());
            DAOHelper.audit(auditManager, header, "test");
        } catch (AuditException e) {
            //good
        }
    }
    /**
     * <p>
     * Test audit(). Foreign key constraint violated, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAudit2() throws Exception {
        try {
            AuditHeader header = DAOHelper.getAuditHeader(AuditType.INSERT, "user", "table", 1);
            header.setClientId(100000);
            AuditManager auditManager = DAOHelper.getAuditManager(InformixAddressDAO.class.getName());
            DAOHelper.audit(auditManager, header, "test");
        } catch (AuditException e) {
            //good
        }
    }
    /**
     * <p>
     * Test setUpPreparedStatement(PreparedStatement, Object[], boolean, String).
     * Number of input values does not match number of question marks,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetUpPreparedStatement() throws Exception {
        DBConnectionFactory factory =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection con = factory.createConnection("InformixJDBCConnection");
        PreparedStatement ps = con.prepareStatement("select * from address");
        try {
            DAOHelper.setUpPreparedStatement(ps, new Object[]{"a"}, false, "test");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }

    /**
     * <p>
     * Test prepareStatement(Connection, String, String).
     * Syntax error, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testPrepareStatement() throws Exception {
        DBConnectionFactory factory =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection con = factory.createConnection("InformixJDBCConnection");
        try {
            DAOHelper.prepareStatement(con, "s f a", "test");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        } finally {
            DAOHelper.realeaseJDBCResource(null, null, con);
        }
    }

    /**
     * <p>
     * Test executeQuery(PreparedStatement, String).
     * Too many or too few host variables given, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExecuteQuery() throws Exception {
        DBConnectionFactory factory =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection con = factory.createConnection("InformixJDBCConnection");
        PreparedStatement ps = con.prepareStatement("select * from address where address_id=?");
        try {
            DAOHelper.executeQuery(ps, "test");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }
    /**
     * <p>
     * Test executeUpdate(PreparedStatement, String).
     * Cannot execute update a SELECT statement that is PREPARED,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExecuteUpdate() throws Exception {
        DBConnectionFactory factory =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection con = factory.createConnection("InformixJDBCConnection");
        PreparedStatement ps = con.prepareStatement("select * from address");
        try {
            DAOHelper.executeUpdate(ps, "test");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }

    /**
     * <p>
     * Test executeBatch(PreparedStatement, String).
     * Unique constraint violated, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExecuteBatch1() throws Exception {
        DBConnectionFactory factory =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection con = factory.createConnection("InformixJDBCConnection");
        con.setAutoCommit(false);
        PreparedStatement ps =
            con.prepareStatement("insert into address values(?,'a','a','a',1,1,'1',current,'u',current,'u')");
        ps.setLong(1, 100100);
        ps.addBatch();
        ps.setLong(1, 200100);
        ps.addBatch();
        ps.setLong(1, 100100);
        ps.addBatch();
        try {
            DAOHelper.executeBatch(ps, "test");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            con.rollback();
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }
    /**
     * <p>
     * Test executeBatch(PreparedStatement, String).
     * Statement already closed, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testExecuteBatch2() throws Exception {
        DBConnectionFactory factory =
            new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        Connection con = factory.createConnection("InformixJDBCConnection");
        con.setAutoCommit(false);
        Statement ps = con.createStatement();
        ps.addBatch("insert into address values(1,'a','a','a',1,1,'1',current,'u',current,'u')");
        ps.close();
        try {
            DAOHelper.executeBatch(ps, "test");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            con.rollback();
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }
    /**
     * <p>
     * Test getNextId(IDGenerator, String).
     * Next block start is Long.MAX_VALUE, IDGenerationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetNextId1() throws Exception {
        IDGenerator idGenerator = IDGeneratorFactory.getIDGenerator("AddressIDGenerator");
        long id = idGenerator.getNextID();
        this.updateIDGenerator("AddressIDGenerator", Long.MAX_VALUE);
        try {
            DAOHelper.getNextId(idGenerator, "test");
            DAOHelper.getNextId(idGenerator, "test");
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            //good
            this.updateIDGenerator("AddressIDGenerator", id);
        }
    }

    /**
     * <p>
     * Test getNextId(IDGenerator, String).
     * Negative id is returned, IDGenerationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetNextId2() throws Exception {
        IDGenerator idGenerator = IDGeneratorFactory.getIDGenerator("AddressIDGenerator");
        long id = idGenerator.getNextID();
        this.updateIDGenerator("AddressIDGenerator", -2);
        try {
            DAOHelper.getNextId(idGenerator, "test");
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            //good
            this.updateIDGenerator("AddressIDGenerator", id);
        }
    }
}
