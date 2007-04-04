/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.failuretests;

import java.security.Identity;
import java.security.Principal;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.ejb.AuditSessionBean;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link AuditSessionBean</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class AuditSessionBeanFailureTests extends TestCase {

    /**
     * <p>
     * Represents the audit session bean instance.
     * </p>
     */
    private AuditSessionBean auditSessionBean;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.clearNamespaces();
        // load namespaces.
        FailureTestHelper.loadNamesapces(FailureTestHelper.AUDITSESSIONBEAN_CONFIG);

        auditSessionBean = new AuditSessionBean();
        SessionContext sessionContext = new SessionContext() {

            public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
                return null;
            }

            public EJBObject getEJBObject() throws IllegalStateException {
                return null;
            }

            public MessageContext getMessageContext() throws IllegalStateException {
                return null;
            }

            public Identity getCallerIdentity() {
                return null;
            }

            public Principal getCallerPrincipal() {
                return null;
            }

            public EJBHome getEJBHome() {
                return null;
            }

            public EJBLocalHome getEJBLocalHome() {
                return null;
            }

            public Properties getEnvironment() {
                return null;
            }

            public boolean getRollbackOnly() throws IllegalStateException {
                return false;
            }

            public TimerService getTimerService() throws IllegalStateException {
                return null;
            }

            public UserTransaction getUserTransaction() throws IllegalStateException {
                return null;
            }

            public boolean isCallerInRole(Identity arg0) {
                return false;
            }

            public boolean isCallerInRole(String arg0) {
                return false;
            }

            public Object lookup(String arg0) {
                return null;
            }

            public void setRollbackOnly() throws IllegalStateException {
            }
            
        };
        auditSessionBean.setSessionContext(sessionContext);
        auditSessionBean.ejbCreate();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#ejbCreate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_InvaidConfig_case1() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.FAILURE_CONFIG_BASE + "AuditSessionBean1.xml");
        try {
            auditSessionBean.ejbCreate();
            fail("expect throw CreateException");
        } catch (CreateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#ejbCreate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_InvaidConfig_case2() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.FAILURE_CONFIG_BASE + "AuditSessionBean2.xml");
        try {
            auditSessionBean.ejbCreate();
            fail("expect throw CreateException");
        } catch (CreateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#ejbCreate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_InvaidConfig_case3() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.FAILURE_CONFIG_BASE + "AuditSessionBean3.xml");
        try {
            auditSessionBean.ejbCreate();
            fail("expect throw CreateException");
        } catch (CreateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#ejbCreate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_InvaidConfig_case4() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.FAILURE_CONFIG_BASE + "AuditSessionBean4.xml");
        try {
            auditSessionBean.ejbCreate();
            fail("expect throw CreateException");
        } catch (CreateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#ejbCreate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_InvaidConfig_case5() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.FAILURE_CONFIG_BASE + "AuditSessionBean5.xml");
        try {
            auditSessionBean.ejbCreate();
            fail("expect throw CreateException");
        } catch (CreateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#ejbCreate()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_InvaidConfig_case6() throws Exception {
        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.FAILURE_CONFIG_BASE + "AuditSessionBean6.xml");
        try {
            auditSessionBean.ejbCreate();
            fail("expect throw CreateException");
        } catch (CreateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_AuditManagerException() {
        try {
            auditSessionBean.createAuditRecord(new AuditHeader());
            fail("expect throw AuditManagerException.");
        } catch (AuditManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#searchAudit(com.topcoder.search.builder.filter.Filter)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchAudit_AuditManagerException() {
        try {
            auditSessionBean.searchAudit(null);
            fail("expect throw AuditManagerException.");
        } catch (AuditManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditSessionBean#rollbackAuditRecord(int)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testrollbackAuditRecord_AuditManagerException() {
        try {
            auditSessionBean.rollbackAuditRecord(1);
            fail("expect throw AuditManagerException.");
        } catch (AuditManagerException e) {
            // expected
        }
    }
}
