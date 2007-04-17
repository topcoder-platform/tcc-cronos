/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOLocal;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOLocalHome;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailSessionBean;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOLocal;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOLocalHome;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonSessionBean;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>
 * Helper class for tests. This contains common methods used by multiple test classes.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TestHelper extends TestCase {
    /** A reference to ConfigManager. */
    protected static final ConfigManager CM = ConfigManager.getInstance();

    /** The name of id generator. */
    protected static final String ID_GENERATOR_NAME = "reject_reason_sequence";

    /** The connection nam3. */
    protected static final String CONNECTION_NAME = "reject_reason";

    /** The configuration file of DB Connection Factory. */
    private static final String DB_FACTORY_CONFIG = "DBConnectionFactory.xml";

    /** The configuration file of JNDI Utility. */
    private static final String JNDI_UTILS_CONFIG = "JNDIUtils.xml";

    /** The configuration file of Search Bundle. */
    private static final String SEARCH_BUNDLE_CONFIG = "SearchBundle.xml";

    /** The configuration file of Search Strategy. */
    private static final String SEARCH_STRATEGY_CONFIG = "SearchStrategy.xml";

    /** The configuration file of this component. */
    private static final String REJECT_REASON_CONFIG = "TimeTracherRejectReason.xml";

    /** A reference to RejectEmailSessionBean. */
    protected RejectEmailSessionBean emailBean = null;

    /** A reference to RejectReasonSessionBean. */
    protected RejectReasonSessionBean reasonBean = null;

    /** The JNDI context. */
    protected Context context = null;

    /**
     * Cleans up test environment. The configuration namespaces are removed.
     *
     * @throws Exception pass to JUnit.
     */
    protected void tearDown() throws Exception {
        clearConfig();
    }

    /**
     * Loads configuration for tests.
     *
     * @throws Exception pass to JUnit.
     */
    protected void loadConfig() throws Exception {
        clearConfig();
        CM.add(JNDI_UTILS_CONFIG);
        CM.add(DB_FACTORY_CONFIG);
        CM.add(SEARCH_BUNDLE_CONFIG);
        CM.add(SEARCH_STRATEGY_CONFIG);
        CM.add(REJECT_REASON_CONFIG);
    }

    /**
     * Clears configuration namespaces.
     *
     * @throws Exception pass to JUnit.
     */
    protected void clearConfig() throws Exception {
        for (Iterator it = CM.getAllNamespaces(); it.hasNext();) {
            CM.removeNamespace(it.next().toString());
        }
    }

    /**
     * Sets up the test environment for session bean classes. MockEJB variables is initialized.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUpSessionBeanEnvironment() throws Exception {
        // We need to set MockContextFactory as our JNDI provider
        // This method sets the necessary system properties
        MockContextFactory.setAsInitial();

        // Create an instance of the MockContainer
        context = new InitialContext();

        // Create deployment descriptor of our mock beans
        // MockEjb uses it instead of XML deployment descriptors
        // Finally deploy operation creates Homes and binds it to JNDI
        MockContainer container = new MockContainer(context);
        emailBean = new RejectEmailSessionBean();
        container.deploy(new SessionBeanDescriptor(RejectEmailDAOLocalHome.EJB_REF_HOME, RejectEmailDAOLocalHome.class,
                RejectEmailDAOLocal.class, emailBean));
        reasonBean = new RejectReasonSessionBean();
        container.deploy(new SessionBeanDescriptor(RejectReasonDAOLocalHome.EJB_REF_HOME,
                RejectReasonDAOLocalHome.class, RejectReasonDAOLocal.class, new RejectReasonSessionBean()));

        // Bind necessary environment variables
        context.rebind("java:comp/env", context);
        context.rebind("of_namespace", "object.factory");
        context.rebind("audit_manager_key", "AuditManager");
        context.rebind("id_generator_name", ID_GENERATOR_NAME);
        context.rebind("db_connection_factory_key", "connection_factory");
        context.rebind("connection_name", "reject_reason");
        context.rebind("javax.transaction.UserTransaction", new MockTransaction());
    }
}
