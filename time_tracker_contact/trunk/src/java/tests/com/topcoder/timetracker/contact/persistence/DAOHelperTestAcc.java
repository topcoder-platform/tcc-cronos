/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.informix.jdbc.IfxSqliConnect;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.WrappedConnection;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>Accuracy tests for <code>DAOHelper</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DAOHelperTestAcc extends BaseTestCase {
    /**
     * <p>
     * Test accuracy of methods getConnectionFactory() and createConnection().
     * Connection name is given, connection should be created through JNDI looking up.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConnection1() throws Exception {

        this.getTransaction().begin();

        try {
            DBConnectionFactory factory = DAOHelper.getConnectionFactory(InformixAddressDAO.class.getName());
            Connection con = DAOHelper.createConnection(factory, "InformixJNDIConnection", "test");
            assertTrue(con instanceof WrappedConnection);
        } catch (PersistenceException e) {
            fail("PersistenceException not expected");
        } finally {
            this.getTransaction().commit();
        }
    }
    /**
     * <p>
     * Test accuracy of methods getConnectionFactory() and createConnection().
     * Connection name is null, default JDBC connection should be created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConnection2() throws Exception {

        try {
            DBConnectionFactory factory = DAOHelper.getConnectionFactory(InformixAddressDAO.class.getName());
            Connection con = DAOHelper.createConnection(factory, null, "test");
            assertTrue(con instanceof IfxSqliConnect);
            assertTrue(con.getAutoCommit());
            con.close();
        } catch (PersistenceException e) {
            fail("PersistenceException not expected");
        }
    }

    /**
     * <p>
     * Test accuracy of methods prepareStatement(), setUpPreparedStatement(), executeQuery() and realeaseJDBCResource().
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDBOperations1() throws Exception {
        DBConnectionFactory factory = DAOHelper.getConnectionFactory(InformixAddressDAO.class.getName());
        Connection con = DAOHelper.createConnection(factory, null, "test");
        PreparedStatement ps =
            DAOHelper.prepareStatement(con, "select * from id_sequences where exhausted = ?", "test");
        DAOHelper.setUpPreparedStatement(ps, new Integer[]{new Integer(0)}, false, "test");
        ResultSet rs = DAOHelper.executeQuery(ps, "test");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        assertTrue("There should be 3+ id sequences", i >= 3);
        DAOHelper.realeaseJDBCResource(rs, ps, con);
        try {
            rs.next();
            fail("ResultSet should be closed");
        } catch (SQLException e) {
            //good
        }
        try {
            ps.addBatch();
            fail("Statement should be closed");
        } catch (SQLException e) {
            //good
        }
        try {
            con.createStatement();
            fail("Connection should be closed");
        } catch (SQLException e) {
            //good
        }
    }

    /**
     * <p>
     * Test accuracy of methods prepareStatement(), setUpPreparedStatement(), executeUpdate() and
     * realeaseJDBCResource().
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDBOperations2() throws Exception {
        DBConnectionFactory factory = DAOHelper.getConnectionFactory(InformixAddressDAO.class.getName());
        Connection con = DAOHelper.createConnection(factory, null, "test");
        PreparedStatement ps =
            DAOHelper.prepareStatement(con, "update id_sequences set exhausted = ? where name = ?", "test");
        DAOHelper.setUpPreparedStatement(ps, new Object[]{new Integer(0), "AddressIDGenerator"}, false, "test");
        int count = DAOHelper.executeUpdate(ps, "test");
        assertEquals("There should be 1 record got updated", 1, count);
        DAOHelper.realeaseJDBCResource(null, ps, con);
        try {
            ps.addBatch();
            fail("Statement should be closed");
        } catch (SQLException e) {
            //good
        }
        try {
            con.createStatement();
            fail("Connection should be closed");
        } catch (SQLException e) {
            //good
        }
    }

    /**
     * <p>
     * Test accuracy of methods prepareStatement(), setUpPreparedStatement(), executeBatch() and
     * realeaseJDBCResource().
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDBOperations3() throws Exception {
        DBConnectionFactory factory = DAOHelper.getConnectionFactory(InformixAddressDAO.class.getName());
        Connection con = DAOHelper.createConnection(factory, null, "test");
        PreparedStatement ps =
            DAOHelper.prepareStatement(con, "update id_sequences set exhausted = ? where name = ?", "test");
        DAOHelper.setUpPreparedStatement(ps, new Object[]{new Integer(0), "AddressIDGenerator"}, true, "test");
        DAOHelper.setUpPreparedStatement(ps, new Object[]{new Integer(0), "ContactIDGenerator"}, true, "test");
        DAOHelper.setUpPreparedStatement(ps, new Object[]{new Integer(0), "AuditIDGenerator"}, true, "test");
        int[] counts = DAOHelper.executeBatch(ps, "test");
        assertEquals("There should be 3 batch operation", 3, counts.length);
        for (int i = 0; i < 3; i++) {
            assertEquals("There should be 1 record got updated for each batch operation", 1, counts[i]);
        }
        DAOHelper.realeaseJDBCResource(null, ps, con);
        try {
            ps.addBatch();
            fail("Statement should be closed");
        } catch (SQLException e) {
            //good
        }
        try {
            con.createStatement();
            fail("Connection should be closed");
        } catch (SQLException e) {
            //good
        }
    }

    /**
     * <p>
     * Test accuracy of methods getIDGenerator() and getNextId().
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testIDGenerator() throws Exception {
        IDGenerator idGenerator = DAOHelper.getIDGenerator(InformixAddressDAO.class.getName());
        long id1 = DAOHelper.getNextId(idGenerator, "test");
        long id2 = DAOHelper.getNextId(idGenerator, "test");
        long id3 = DAOHelper.getNextId(idGenerator, "test");
        assertEquals("Consecutive id sequence", 1, id2 - id1);
        assertEquals("Consecutive id sequence", 1, id3 - id2);
    }

    /**
     * <p>
     * Test accuracy of methods getAuditManager(), getAuditDetail(), getAuditHeader(),
     * setApplicationAreaAndRelatedId() and audit().
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAudit1() throws Exception {
        int preCount = this.getAuditRecordsCount();
        int preDetailCount = this.getAuditDetailRecordsCount();

        AuditDetail detail = DAOHelper.getAuditDetail("column", null, "a");
        AuditHeader header = DAOHelper.getAuditHeader(AuditType.INSERT, "user", "table", 1);
        DAOHelper.setApplicationAreaAndRelatedId(header, AddressType.CLIENT, 1);
        header.setDetails(new AuditDetail[]{detail});
        AuditManager auditManager = DAOHelper.getAuditManager(InformixAddressDAO.class.getName());
        DAOHelper.audit(auditManager, header, "test");

        this.assertAuditRecordsCount(preCount, 1);
        this.assertAuditDetailRecordsCount(preDetailCount, 1);
    }

    /**
     * <p>
     * Test accuracy of methods getAuditManager(), getAuditDetail(), getAuditHeader() and
     * audit(). No audit detail records.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAudit2() throws Exception {
        int preCount = this.getAuditRecordsCount();
        int preDetailCount = this.getAuditDetailRecordsCount();

        AuditHeader header = DAOHelper.getAuditHeader(AuditType.INSERT, "user", "table", 1);
        DAOHelper.setApplicationAreaAndRelatedId(header, ContactType.CLIENT, 1);
        header.setDetails(new AuditDetail[]{});
        AuditManager auditManager = DAOHelper.getAuditManager(InformixAddressDAO.class.getName());
        DAOHelper.audit(auditManager, header, "test");

        this.assertAuditRecordsCount(preCount, 1);
        this.assertAuditDetailRecordsCount(preDetailCount, 0);
    }

    /**
     * <p>
     * Test convertIds().
     * </p>
     */
    public void testConvertIds() {
        String result = DAOHelper.convertIds(new StringBuffer(), new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "test");
        assertEquals("(1,2,3,4,5,6,7,8,9)", result);
    }
}
