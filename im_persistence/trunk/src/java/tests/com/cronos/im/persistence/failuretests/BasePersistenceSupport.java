/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import junit.framework.TestCase;

import java.sql.Connection;


/**
 * The Support class for all the persistence test.
 * @author waits
 * @version 1.0
 */
public class BasePersistenceSupport extends TestCase {
    /** informix connection name. */
    protected static final String CONN_NAME = "informix";

    /** Datatabase conn. */
    protected Connection conn = null;

    /**
     * setup environement.
     */
    protected void setUp() throws Exception {
        TestHelper.setUpConfiguration();
        this.conn = getConnection();
    }

    /**
     * Create ObjectValidator instance for testing, not null
     *
     * @return ObjectValidator instance
     *
     * @throws Exception fail to create required object
     */
    protected ObjectValidator createObjectValidator() throws Exception {
        return (ObjectValidator) new ObjectFactory(new ConfigManagerSpecificationFactory(TestHelper.OF_NAMESPACE)).createObject(
            "validator");
    }

    /**
     * Create DBConnectionFactory instance for testing.
     *
     * @return DBConnectionFactory instance ,not null
     *
     * @throws Exception fail to create required object
     */
    protected DBConnectionFactory createDBConnectionFactory()
        throws Exception {
        //create dbConnectionFactory for testing
        return (DBConnectionFactory) new ObjectFactory(new ConfigManagerSpecificationFactory(TestHelper.OF_NAMESPACE)).createObject(
            "connection_factory");
    }

    /**
     * Create IDGenerator for testing.
     *
     * @return IDGenerator instance
     *
     * @throws Exception fail to create idGenerator
     */
    protected IDGenerator createIDGenerator() throws Exception {
        return IDGeneratorFactory.getIDGenerator("generator");
    }

    /**
     * Create connection.
     *
     * @return Connection instance
     *
     * @throws Exception fails to create instance
     */
    protected Connection getConnection() throws Exception {
        return this.createDBConnectionFactory().createConnection();
    }

    /**
     * Clear environment.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearTables(conn);

        if (conn != null) {
            conn.close();
        }
    }
}
