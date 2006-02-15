/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit test cases for BaseDAO.
 * </p>
 *
 * <p>
 * This class will not test the functionality of the five CRUD(Create, Read, Update, and Delete) database related
 * operation methods, since these methods will use helper methods which will be implemented in the sub-class. They
 * will be tested in the testing of sub-classes such as TimeStatusDAO, TimeEntryDAO and TaskTypeDAO. Argument
 * validation will be test here.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDAOUnitTest extends TestCase {
    /**
     * <p>
     * The ConfigManager instance to load the config file.
     * </p>
     */
    private static ConfigManager cm = ConfigManager.getInstance();

    /**
     * <p>
     * The name of the Connection to fetch from the DBConnectionFactory.
     * </p>
     */
    private static final String CONNAME = "informix";

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.myconfig";

    /**
     * <p>
     * Namespace to be used in the IDGeneration component to load the configuration.
     * </p>
     */
    private static final String IDGENERATION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The MockBaseDAO instance for testing.
     * </p>
     */
    private BaseDAO baseDAO = null;

    /**
     * <p>
     * The TimeStatus instance for testing.
     * </p>
     */
    private DataObject dataObject = new TimeStatus();

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(BaseDAOUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        this.baseDAO = new MockBaseDAO(CONNAME, NAMESPACE);
        ((TimeStatus) dataObject).setDescription("foo");

        // load the configuration
        if (cm.existsNamespace(NAMESPACE)) {
            cm.removeNamespace(NAMESPACE);
        }

        if (cm.existsNamespace(IDGENERATION_NAMESPACE)) {
            cm.removeNamespace(IDGENERATION_NAMESPACE);
        }

        cm.add(NAMESPACE, "SampleXML.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        cm.add(IDGENERATION_NAMESPACE, "SampleXML.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        if (cm.existsNamespace(NAMESPACE)) {
            cm.existsNamespace(NAMESPACE);
        }

        if (cm.existsNamespace(IDGENERATION_NAMESPACE)) {
            cm.removeNamespace(IDGENERATION_NAMESPACE);
        }

    }

    /**
     * <p>
     * Tests the constructor - BaseDAO(String connName, String namespace).
     * </p>
     */
    public void testBaseDAO1() {
        assertNotNull("The constructor does not work well", this.baseDAO);
    }

    /**
     * <p>
     * Tests the constructor - BaseDAO(String connName, String namespace). With null connName, NullPointerException
     * should be thrown
     * </p>
     */
    public void testBaseDAO2() {
        try {
            new MockBaseDAO(null, NAMESPACE);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - BaseDAO(String connName, String namespace). With null namspace, NullPointerException
     * should be thrown
     * </p>
     */
    public void testBaseDAO3() {
        try {
            new MockBaseDAO(CONNAME, null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - BaseDAO(String connName, String namespace). With empty connName,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testBaseDAO4() {
        try {
            new MockBaseDAO(" ", NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - BaseDAO(String connName, String namespace). With empty namspace,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testBaseDAO5() {
        try {
            new MockBaseDAO(CONNAME, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getConnection() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetConnection() throws Exception {
        assertNull("connection is not properly got", baseDAO.getConnection());
    }

    /**
     * <p>
     * Tests the setConnection(Connection connection) method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSetConnection1() throws Exception {
        Connection conn = null;

        try {
            conn = getConnection();
            baseDAO.setConnection(conn);
            assertEquals("connection is not properly set", conn, baseDAO.getConnection());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the setConnection(Connection connection) method. With null connection, NullPointerException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSetConnection2() throws Exception {
        try {
            baseDAO.setConnection(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the removeConnection() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testRemoveConnection() throws Exception {
        Connection conn = null;

        try {
            conn = getConnection();
            baseDAO.setConnection(conn);
            baseDAO.removeConnection();
            assertNull("connection is not properly removed", baseDAO.getConnection());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the setDataObjectPrimaryId(DataObject dataObject) method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSetDataObjectPrimaryId1() throws Exception {
        dataObject.setPrimaryId(-1);
        baseDAO.setDataObjectPrimaryId(dataObject);

        // assert the id has been obtained
        assertFalse("id was not properly obtained from the IDGeneratorFactory", dataObject.getPrimaryId() == -1);
    }

    /**
     * <p>
     * Tests the setDataObjectPrimaryId(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSetDataObjectPrimaryId2() throws Exception {
        try {
            baseDAO.setDataObjectPrimaryId(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the create(DataObject dataObject, String user) method. With null user, NullPointerException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testCreate1() throws Exception {
        try {
            baseDAO.create(dataObject, null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the create(DataObject dataObject, String user) method. With empty user, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testCreate2() throws Exception {
        try {
            baseDAO.create(dataObject, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user) method. With null user, NullPointerException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate1() throws Exception {
        try {
            baseDAO.update(dataObject, null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user) method. With empty user, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate2() throws Exception {
        try {
            baseDAO.update(dataObject, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause) method. With incorrect whereClause, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList1() throws Exception {
        try {
            baseDAO.getList(" where ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause) method. With incorrect whereClause, IllegalArgumentException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList2() throws Exception {
        try {
            baseDAO.getList("Where ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @return the connection created from DBConnectionFactory
     *
     * @throws Exception any exception during the create connection process
     */
    private Connection getConnection() throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(NAMESPACE);

        return factory.createConnection(CONNAME);
    }

}


/**
 * <p>
 * A mock MockBaseDAO that extends the BaseDAO. This class is used for testing only.
 * </p>
 */
class MockBaseDAO extends BaseDAO {
    /**
     * <p>
     * Constructor. Sets the connection name and namespace that the component will use to obtain Connections from the
     * DBConnection Factory
     * </p>
     *
     * @param connName The name of the Connection
     * @param namespace Namespace to be used in the DBConnection Factory component.
     *
     * @throws NullPointerException If connName or namespace is null.
     * @throws IllegalArgumentException If connName or namespace is empty.
     */
    protected MockBaseDAO(String connName, String namespace) {
        super(connName, namespace);
    }

    /**
     * <p>
     * Mock implementation, do nothing here.
     * </p>
     *
     * @param dataObject The DataObject which will be checked
     *
     * @throws NullPointerException If dataObject is null.
     * @throws IllegalArgumentException if DataObject does not match requirements of the DAO implementation.
     */
    protected void verifyCreateDataObject(DataObject dataObject) {
        // do nothing here
    }

    /**
     * <p>
     * Mock implementation, only return null.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for creating a record
     *
     */
    protected String getCreateSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, do nothting here.
     * </p>
     *
     * @param statement the PreparedStatement to fill with values
     * @param dataObject the DataObject which contains the values to fill into PreparedStatement
     * @param creationUser the creation user of this database operation
     * @param creationDate the creation date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void fillCreatePreparedStatement(PreparedStatement statement, DataObject dataObject, String creationUser,
        Date creationDate) throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, do nothing here.
     * </p>
     *
     * @param dataObject the DataObject to set creation fields
     * @param creationUser the creation user of this database operation
     * @param creationDate the creation date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void setCreationParametersInDataObject(DataObject dataObject, String creationUser, Date creationDate)
        throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, do nothing here.
     * </p>
     *
     * @param dataObject t DataObject which will be checked
     *
     * @throws NullPointerException If dataObject is null.
     * @throws IllegalArgumentException if DataObject does not match requirements of the DAO implementation.
     */
    protected void verifyUpdateDataObject(DataObject dataObject) {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, just return null.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for update a record
     *
     */
    protected String getUpdateSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param statement the PreparedStatement to fill with values
     * @param dataObject the DataObject which contains the values to fill into PreparedStatement
     * @param modificationUser the modification user of this database operation
     * @param modificationDate the modification date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void fillUpdatePreparedStatement(PreparedStatement statement, DataObject dataObject,
        String modificationUser, Date modificationDate)
        throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param dataObject the DataObject to set modification fields
     * @param modificationUser the modification user of this database operation
     * @param modificationDate the modification date of this database operation
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected void setModificationParametersInDataObject(DataObject dataObject, String modificationUser,
        Date modificationDate) throws DAOActionException {
        // do nothing
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for delete a record
     *
     */
    protected String getDeleteSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record
     *
     */
    protected String getReadSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param resultSet the resultSet to retrieve DataObject instance
     *
     * @return the retrieved DataObject instance
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected DataObject processReadResultSet(ResultSet resultSet)
        throws DAOActionException {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @return the sql string that will be used in the prepared statement for get a record list
     *
     */
    protected String getReadListSqlString() {
        return null;
    }

    /**
     * <p>
     * Mock implementation, just do nothing here.
     * </p>
     *
     * @param resultSet the resultSet to retrieve DataObject instances
     *
     * @return the retrieved DataObject instances
     *
     * @throws DAOActionException If any other exception is thrown.
     */
    protected List processReadListResultSet(ResultSet resultSet)
        throws DAOActionException {
        return null;
    }
}
