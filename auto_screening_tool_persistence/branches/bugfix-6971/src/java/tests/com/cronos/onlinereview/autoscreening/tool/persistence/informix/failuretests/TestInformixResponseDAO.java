/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.failuretests;

import com.cronos.onlinereview.autoscreening.tool.DAOException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.persistence.informix.InformixResponseDAO;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

import java.io.File;

import java.util.Date;
import java.util.Iterator;


/**
 * Unit tests for InformixResponseDAO class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestInformixResponseDAO extends TestCase {
    /** The instance of InformixResponseDAO used for testing. */
    private InformixResponseDAO dao = null;

    /** The instance of DBConnectionFactory used for testing. */
    private DBConnectionFactory factory = null;

    /** The namespace of connection. */
    private final String DB_CONN_NS = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** The config file name. */
    private final String CONF_FILE = "test_files/failure/config.xml";

    /** The instance of IDGenerator used for testing. */
    private IDGenerator idg = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        clearAllNamespaces();

        ConfigManager.getInstance().add(new File(CONF_FILE).getAbsolutePath());

        factory = new DBConnectionFactoryImpl(DB_CONN_NS);

        idg = IDGeneratorFactory.getIDGenerator("screening_result_id_seq");
        dao = new InformixResponseDAO(factory, idg);
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
        idg = null;
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
     * Tests InformixResponseDAO(DBConnectionFactory connFact, IDGenerator idGen) method with null
     * DBConnectionFactory connFact, Expected IllegalArgumentException.
     */
    public void testInformixResponseDAO1_NullConnFact() {
        try {
            new InformixResponseDAO(null, idg);
            fail("testInformixResponseDAO_NullConnFact is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixResponseDAO(DBConnectionFactory connFact, IDGenerator idGen) method with null
     * IDGenerator idGen, Expected IllegalArgumentException.
     */
    public void testInformixResponseDAO1_NullIdGen() {
        try {
            new InformixResponseDAO(factory, null);
            fail("testInformixResponseDAO_NullIdGen is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixResponseDAO(DBConnectionFactory connFact, String connName, IDGenerator idGen)
     * method with null DBConnectionFactory connFact, Expected IllegalArgumentException.
     */
    public void testInformixResponseDAO2_NullConnFact() {
        try {
            new InformixResponseDAO(null, "connName", idg);
            fail("testInformixResponseDAO_NullConnFact is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixResponseDAO(DBConnectionFactory connFact, String connName, IDGenerator idGen)
     * method with null String connName, Expected no Exception.
     */
    public void testInformixResponseDAO_NullConnName() {
        new InformixResponseDAO(factory, null, idg);
    }

    /**
     * Tests InformixResponseDAO(DBConnectionFactory connFact, String connName, IDGenerator idGen)
     * method with empty String connName, Expected IllegalArgumentException.
     */
    public void testInformixResponseDAO_EmptyConnName() {
        try {
            new InformixResponseDAO(factory, " ", idg);
            fail("testInformixResponseDAO_EmptyConnName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests InformixResponseDAO(DBConnectionFactory connFact, String connName, IDGenerator idGen)
     * method with null IDGenerator idGen, Expected IllegalArgumentException.
     */
    public void testInformixResponseDAO_NullIdGen() {
        try {
            new InformixResponseDAO(factory, "connName", null);
            fail("testInformixResponseDAO_NullIdGen is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests createScreeningResponse(ScreeningResponse screeningResponse) method with null
     * ScreeningResponse screeningResponse, Expected IllegalArgumentException.
     *
     * @throws DAOException to JUnit.
     */
    public void testCreateScreeningResponse_NullScreeningResponse()
        throws DAOException {
        try {
            dao.createScreeningResponse(null);
            fail("testCreateScreeningResponse_NullScreeningResponse is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests createScreeningResponse(ScreeningResponse screeningResponse) method with invalid
     * ScreeningResponse screeningResponse, Expected DAOException.
     *
     * @throws DAOException to JUnit.
     */
    public void testCreateScreeningResponse_InvalidScreeningResponse()
        throws Exception {
        ScreeningResponse sr = new ScreeningResponse();

        sr.setId(12345);

        // screeningTaskId is not set
        sr.setResponseCodeId(123);
        sr.setDetailMessage("message");

        sr.setCreateUser("ivern");
        sr.setCreateDate(new Date());
        sr.setModificationUser("ivern");
        sr.setModificationDate(new Date());

        try {
            dao.createScreeningResponse(sr);
            fail("testCreateScreeningResponse_InvalidScreeningResponse is failure.");
        } catch (DAOException daoe) {
            // pass
        }
    }
}
