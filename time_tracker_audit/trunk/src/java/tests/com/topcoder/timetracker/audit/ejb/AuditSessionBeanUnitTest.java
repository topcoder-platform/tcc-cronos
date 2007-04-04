/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.ejb;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;

import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditPersistence;
import com.topcoder.timetracker.audit.AuditPersistenceException;
import com.topcoder.timetracker.audit.UnitTestHelper;

import junit.framework.TestCase;

import java.security.Identity;
import java.security.Principal;

import java.sql.Connection;
import java.sql.Timestamp;

import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;

import javax.transaction.UserTransaction;

import javax.xml.rpc.handler.MessageContext;


/**
 * <p>
 * Tests functionality and error cases of <code>AuditSessionBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AuditSessionBeanUnitTest extends TestCase {
    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>AuditSessionBean</code> instance used for testing. */
    private AuditSessionBean bean;

    /** Represents the <code>SessionContext</code> instance used for testing. */
    private SessionContext context;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);
        context = new MockSessionContext();
        bean = new AuditSessionBean();
        bean.ejbCreate();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Accuracy test for the constructor <code>AuditSessionBean()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAuditSessionBean_Accuracy() throws Exception {
        new AuditSessionBean();
    }

    /**
     * <p>
     * Accuracy test for the method <code>ejbCreate()</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_Accuracy() throws Exception {
        assertNotNull("The dao should be created.", UnitTestHelper.getPrivateField(bean.getClass(), bean, "dao"));
    }

    /**
     * Accuracy test for the method <code>ejbRemove()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbRemove_Accuracy() throws Exception {
        bean.ejbRemove();
    }

    /**
     * Accuracy test for the method <code>ejbActivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbActivate_Accuracy() throws Exception {
        bean.ejbActivate();
    }

    /**
     * Accuracy test for the method <code>ejbPassivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbPassivate_Accuracy() throws Exception {
        bean.ejbPassivate();
    }

    /**
     * Accuracy test for the method <code>setSessionContext(SessionContext)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetSessionContext_Accuracy() throws Exception {
        bean.setSessionContext(context);
        assertEquals("The context should be set properly.", context,
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "sessionContext"));
    }

    /**
     * Accuracy test for the method <code>getSessionContext()</code> with default value. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetSessionContext_Default_Accuracy() throws Exception {
        assertEquals("The context should be null in default.", null, bean.getSessionContext());
    }

    /**
     * Accuracy test for the method <code>getSessionContext()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetSessionContext_Accuracy() throws Exception {
        UnitTestHelper.setPrivateField(bean.getClass(), bean, "sessionContext", context);
        assertEquals("The context should be got properly.", context, bean.getSessionContext());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDao()</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetDao_Accuracy() throws Exception {
        assertNotNull("The dao should be got properly.", bean.getDao());
    }

    /**
     * <p>
     * Test for the method of <code>createAuditRecord(AuditHeader)</code> when the dao throws
     * AuditPersistenceException, AuditManagerException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_DAOError() throws Exception {
        UnitTestHelper.setPrivateField(bean.getClass(), bean, "dao", new MockAuditPersistence());
        bean.setSessionContext(context);

        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        try {
            bean.createAuditRecord(auditHeader);
            fail("AuditManagerException should be thrown.");
        } catch (AuditManagerException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code>. The persisted value should be the
     * same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy1() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        // add it
        bean.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("client id", new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with no audit details. The persisted
     * value should be the same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy2() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(0);

        // add it
        bean.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("client id", new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with no audit details. The persisted
     * value should be the same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy3() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setDetails(null);

        // add it
        bean.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("client id", new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);

        auditHeader.setDetails(new AuditDetail[0]);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with some option values for audit
     * details are not set. The persisted value should be the same as the retrieved value, the default values for
     * audit details should be persisted.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy4() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        AuditDetail[] details = auditHeader.getDetails();

        for (int i = 0; i < details.length; i++) {
            if ((i % 2) == 0) {
                details[i].setOldValue(null);
            }

            if ((i % 2) == 1) {
                details[i].setNewValue(null);
            }
        }

        // add it
        bean.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("client id", new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);

        for (int i = 0; i < details.length; i++) {
            if ((i % 2) == 0) {
                // it is the default value for DefaultValuesContainer
                details[i].setOldValue("oldValue");
            }

            if ((i % 2) == 1) {
                // it is the default value for DefaultValuesContainer
                details[i].setNewValue("newValue");
            }
        }

        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Test for the method of <code>searchAudit(Filter)</code> when the dao throws AuditPersistenceException,
     * AuditManagerException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_DAOError() throws Exception {
        UnitTestHelper.setPrivateField(bean.getClass(), bean, "dao", new MockAuditPersistence());
        bean.setSessionContext(context);

        try {
            bean.searchAudit(null);
            fail("AuditManagerException should be thrown.");
        } catch (AuditManagerException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is null. All the records
     * in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_NullAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            bean.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(null);

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by resource id. All
     * the records for given resource id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByResourceIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            bean.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("resource id",
                    new Long(auditHeaders[0].getResourceId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by application area
     * id. All the records for given application area id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByApplicationAreaIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            bean.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("application area id",
                    new Long(auditHeaders[0].getApplicationArea().getId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by client id. All the
     * records for given client id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByClientIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            bean.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("client id",
            new Long(auditHeaders[0].getClientId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by project id. All the
     * records for given project id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByProjectIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            bean.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = bean.searchAudit(new EqualToFilter("project id",
            new Long(auditHeaders[0].getProjectId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by creation date. All
     * the records for given range of creation date in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByCreationDateAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            bean.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        Filter filter = new AndFilter(new GreaterThanFilter("creation date", new Timestamp(1000)),
                new LessThanFilter("creation date", new Timestamp(5000)));
        AuditHeader[] ret = bean.searchAudit(filter);

        assertEquals("The audit header should be got properly.", auditHeaders.length - 2, ret.length);

        for (int i = 1; i < (auditHeaders.length - 1); i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i - 1]);
        }
    }

    /**
     * <p>
     * Test for the method of <code>rollbackAuditRecord(int)</code> when the dao throws AuditPersistenceException,
     * AuditManagerException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testEollbackAuditRecord_DAOError() throws Exception {
        UnitTestHelper.setPrivateField(bean.getClass(), bean, "dao", new MockAuditPersistence());
        bean.setSessionContext(context);

        try {
            bean.rollbackAuditRecord(1);
            fail("AuditManagerException should be thrown.");
        } catch (AuditManagerException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>rollbackAuditRecord(int)</code>. All the records in the table should be
     * removed under this audit id.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_Accuracy() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        // add it
        bean.createAuditRecord(auditHeader);

        // remove it
        boolean success = bean.rollbackAuditRecord(auditHeader.getId());

        // validate
        assertEquals("There should be records in the audit table.", true, success);
        assertEquals("The records in table audit_detail should be removed.", 0,
            UnitTestHelper.getAuditDetailRecords(connection));
        assertEquals("The records in table audit should be removed.", 0, UnitTestHelper.getAuditRecords(connection));
    }

    /**
     * <p>
     * Accuracy test for the method of <code>rollbackAuditRecord(int)</code> when there is no record in the database.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_NoRecordAccuracy() throws Exception {
        // remove it
        boolean success = bean.rollbackAuditRecord(1);

        // validate
        assertEquals("There should be no records in the audit table.", false, success);
    }

    /**
     * <p>
     * A mock class which implements AuditPersistence for testing.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 3.2
     */
    private class MockAuditPersistence implements AuditPersistence {
        /**
         * <p>
         * A mock method which always throw AuditPersistenceException.
         * </p>
         *
         * @param record ignored.
         *
         * @throws AuditPersistenceException always.
         */
        public void createAuditRecord(AuditHeader record) throws AuditPersistenceException {
            throw new AuditPersistenceException("AuditPersistenceException");
        }

        /**
         * <p>
         * A mock method which always throw AuditPersistenceException.
         * </p>
         *
         * @param auditHeaderId ignored.
         *
         * @return no return.
         *
         * @throws AuditPersistenceException always.
         */
        public boolean rollbackAuditRecord(long auditHeaderId) throws AuditPersistenceException {
            throw new AuditPersistenceException("AuditPersistenceException");
        }

        /**
         * <p>
         * A mock method which always throw AuditPersistenceException.
         * </p>
         *
         * @param filter ignored.
         *
         * @return no return.
         *
         * @throws AuditPersistenceException always.
         */
        public AuditHeader[] searchAudit(Filter filter) throws AuditPersistenceException {
            throw new AuditPersistenceException("AuditPersistenceException");
        }
    }

    /**
     * <p>
     * A mock class which extends SessionContext for testing.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 3.2
     */
    private class MockSessionContext implements SessionContext {
        /** Represents the RollbackOnly value. */
        private boolean rollbackOnly = false;

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBLocalObject()
         */
        public EJBLocalObject getEJBLocalObject() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBObject()
         */
        public EJBObject getEJBObject() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getMessageContext()
         */
        public MessageContext getMessageContext() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg
         *
         * @return null.
         *
         * @see SessionContext#getBusinessObject(java.lang.Class)
         */
        public Object getBusinessObject(Class arg0) {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBHome()
         */
        public EJBHome getEJBHome() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEJBLocalHome()
         */
        public EJBLocalHome getEJBLocalHome() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getEnvironment()
         */
        public Properties getEnvironment() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getCallerIdentity()
         * @deprecated
         */
        public Identity getCallerIdentity() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getCallerPrincipal()
         */
        public Principal getCallerPrincipal() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg
         *
         * @return null.
         *
         * @see javax.ejb.SessionContext#isCallerInRole(Identity)
         * @deprecated
         */
        public boolean isCallerInRole(Identity arg0) {
            return false;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg
         *
         * @return null.
         *
         * @see javax.ejb.SessionContext#isCallerInRole(String)
         */
        public boolean isCallerInRole(String arg0) {
            return false;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getUserTransaction()
         */
        public UserTransaction getUserTransaction() {
            return null;
        }

        /**
         * @see SessionContext#setRollbackOnly()
         */
        public void setRollbackOnly() {
            rollbackOnly = true;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getRollbackOnly()
         */
        public boolean getRollbackOnly() {
            return rollbackOnly;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @return null.
         *
         * @see SessionContext#getTimerService()
         */
        public TimerService getTimerService() {
            return null;
        }

        /**
         * <p>
         * Mock method.
         * </p>
         *
         * @param arg0 arg.
         *
         * @return null.
         *
         * @see SessionContext#lookup(String)
         */
        public Object lookup(String arg0) {
            return null;
        }
    }
}
