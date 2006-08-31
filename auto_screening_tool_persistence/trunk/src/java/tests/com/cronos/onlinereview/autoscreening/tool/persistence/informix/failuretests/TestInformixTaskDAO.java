/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.failuretests;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixTaskDAO;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.DBUserRetrieval;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import junit.framework.TestCase;

import java.io.File;

import java.util.Date;
import java.util.Iterator;


/**
 * Unit tests for InformixTaskDAO class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestInformixTaskDAO extends TestCase {
    /** The instance of InformixTaskDAO used for testing. */
    private InformixTaskDAO dao = null;

    /** The instance of DBConnectionFactory used for testing. */
    private DBConnectionFactory factory = null;

    /** The instance of ScreeningStatus used for testing. */
    private ScreeningStatus status = ScreeningStatus.PASSED;

    /** The namespace of connection. */
    private final String DB_CONN_NS = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** The config file name. */
    private final String CONF_FILE = "test_files/failure/config.xml";

    /** UserRetrieval instance used for testing. */
    private UserRetrieval userRetr;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        clearAllNamespaces();
        ConfigManager.getInstance().add(new File(CONF_FILE).getAbsolutePath());

        userRetr = new DBUserRetrieval(DB_CONN_NS);
        factory = new DBConnectionFactoryImpl(DB_CONN_NS);
        dao = new InformixTaskDAO(factory, userRetr);
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearAllNamespaces();
        dao = null;
    }

    /**
     * Clear all the namespaces used in this component.
     */
    private void clearAllNamespaces() {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();

            if (cm.existsNamespace(namespace)) {
                // Remove the specified namespace.
                try {
                    cm.removeNamespace(namespace);
                } catch (UnknownNamespaceException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Tests InformixTaskDAO(DBConnectionFactory connFact, UserRetrieval userRetrieval) method with
     * null DBConnectionFactory connFact, Expected IllegalArgumentException.
     */
    public void testInformixTaskDAO1_NullConnFact() {
        try {
            new InformixTaskDAO(null, userRetr);
            fail("testInformixTaskDAO1_NullConnFact is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixTaskDAO(DBConnectionFactory connFact, UserRetrieval userRetrieval) method with
     * null userRetrieval, Expected IllegalArgumentException.
     */
    public void testInformixTaskDAO1_NullUserRetrieval() {
        try {
            new InformixTaskDAO(factory, null);
            fail("testInformixTaskDAO1_NullUserRetrieval is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixTaskDAO(DBConnectionFactory connFact, String connName, UserRetrieval
     * userRetrieval) method with null DBConnectionFactory connFact, Expected
     * IllegalArgumentException.
     */
    public void testInformixTaskDAO2_NullConnFact() {
        try {
            new InformixTaskDAO(null, "connName", userRetr);
            fail("testInformixTaskDAO2_NullConnFact is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixTaskDAO(DBConnectionFactory connFact, String connName, UserRetrieval
     * userRetrieval) method with empty String connName, Expected IllegalArgumentException.
     */
    public void testInformixTaskDAO2_EmptyConnName() {
        try {
            new InformixTaskDAO(factory, " ", userRetr);
            fail("testInformixTaskDAO2_EmptyConnName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixTaskDAO(DBConnectionFactory connFact, String connName, UserRetrieval
     * userRetrieval) method with empty UserRetrieval, Expected IllegalArgumentException.
     */
    public void testInformixTaskDAO2_EmptyUserRetrieval() {
        try {
            new InformixTaskDAO(factory, "connName", null);
            fail("testInformixTaskDAO2_EmptyConnName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests loadScreeningTasks(String screenerId, ScreeningStatus status) method with zero Long
     * screenerId, Expected IllegalArgumentException.
     */
    public void testLoadScreeningTasks_ZeroScreenerId() {
        try {
            dao.loadScreeningTasks(new Long(0), status);
            fail("testLoadScreeningTasks_ZeroScreenerId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }

    /**
     * Tests loadScreeningTasks(String screenerId, ScreeningStatus status) method with negative
     * Long screenerId, Expected IllegalArgumentException.
     */
    public void testLoadScreeningTasks_NegScreenerId() {
        try {
            dao.loadScreeningTasks(new Long(-1), status);
            fail("testLoadScreeningTasks_NegScreenerId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }

    /**
     * Tests updateScreeningTask(ScreeningTask screeningTask) method with null ScreeningTask
     * screeningTask, Expected IllegalArgumentException.
     */
    public void testUpdateScreeningTask_NullScreeningTask() {
        try {
            dao.updateScreeningTask(null);
            fail("testUpdateScreeningTask_NullScreeningTask is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }

    /**
     * Tests updateScreeningTask(ScreeningTask screeningTask) method with UnknowScreeningStatus,
     * Expected DAOException.
     */
    public void testUpdateScreeningTask_UnknowScreeningStatus() {
        ScreeningTask st = new ScreeningTask();

        try {
            dao.updateScreeningTask(st);
            fail("testUpdateScreeningTask_UnknowScreeningStatus is failure.");
        } catch (DAOException e) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }

    /**
     * Tests updateScreeningTask(ScreeningTask screeningTask) method with InvalidScreeningTask,
     * Expected DAOException.
     */
    public void testUpdateScreeningTask_InvalidScreeningTask1()
        throws Exception {
        ScreeningTask st = new ScreeningTask();
        st.setScreeningStatus(ScreeningStatus.FAILED);

        try {
            dao.updateScreeningTask(st);
            fail("testUpdateScreeningTask_InvalidScreeningTask1 is failure.");
        } catch (DAOException e) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }

    /**
     * Tests updateScreeningTask(ScreeningTask screeningTask) method with InvalidScreeningTask,
     * Expected DAOException.
     */
    public void testUpdateScreeningTask_InvalidScreeningTask2()
        throws Exception {
        ScreeningTask st = new ScreeningTask();
        st.setId(12345);
        st.setScreenerId(123);
        st.setStartTimestamp(new Date());
        st.setScreeningStatus(ScreeningStatus.PASSED);

        // the data for screening is missing.
        st.setCreationUser("ivern");
        st.setCreationDate(new Date());
        st.setModificationUser("ivern");
        st.setModificationDate(new Date());

        try {
            dao.updateScreeningTask(st);
            fail("testUpdateScreeningTask_InvalidScreeningTask2 is failure.");
        } catch (DAOException e) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }

    /**
     * Tests updateScreeningTask(ScreeningTask screeningTask) method with invalid ScreeningTask ,
     * Expected DAOException.
     */
    public void testUpdateScreeningTask_InvalidScreeningStatus() {
        ScreeningTask st = new ScreeningTask();
        st.setId(88888);

        try {
            dao.updateScreeningTask(st);
            fail("testUpdateScreeningTask_UnknowScreeningStatus is failure.");
        } catch (DAOException e) {
            // pass
        } catch (Exception e) {
            fail("Another exception throws.");
        }
    }
}
