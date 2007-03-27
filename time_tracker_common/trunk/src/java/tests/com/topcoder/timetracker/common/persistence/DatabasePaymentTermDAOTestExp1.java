/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.BaseBaseTestCase;
import com.topcoder.timetracker.common.CommonManagerConfigurationException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.NoSuchIDSequenceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This test case contains failure tests for <code>DatabasePaymentTermDAO</code>.
 * </p>
 *
 * <p>
 * All constructors are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class DatabasePaymentTermDAOTestExp1 extends BaseBaseTestCase {

    /**
     * <p>
     * Given <code>DBConnectionFactory</code> is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_NullDBConnectionFactory() throws Exception {
        try {
            new DatabasePaymentTermDAO(null, "", "'");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The connection factory should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Given connection name is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_NullConnectionName() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), null, "'");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The connection name should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Given connection name is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyConnectionName() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), " ", "'");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The connection name should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Given id generator name is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_NullIDGeneratorName() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "connection", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The idGenerator name should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Given id generator name is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyIDGeneratorName() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "connection", " ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The idGenerator name should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Given id generator name does not exist in data store,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_NotExistIDGeneratorName() throws Exception {
        try {
            new DatabasePaymentTermDAO(new DBConnectionFactoryImpl(), "connection", "NotExist");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof NoSuchIDSequenceException);
        }
    }

    /**
     * <p>
     * Given namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_NullNamespace() throws Exception {
        try {
            new DatabasePaymentTermDAO(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("namespace should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is empty on trimming, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyNamespace() throws Exception {
        try {
            new DatabasePaymentTermDAO(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("namespace should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is unknown, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_UnknownNamespace() {
        try {
            new DatabasePaymentTermDAO("UnknownNamespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The namespace 'UnknownNamespace' is unknown") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is missing the namespace of Object Factory,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_MissOFNamespace() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_1");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Missed property 'of_namespace'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an empty namespace of Object Factory,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyOFNamespace() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_2");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The value for property 'of_namespace' is empty") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an unknown namespace of Object Factory,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_UnknownOFNamespace() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_3");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof SpecificationConfigurationException);
            assertTrue(e.getCause().getCause() instanceof UnknownNamespaceException);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * type property is missing for property DBConnectionFactoryKey,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_ErrorOFNamespace1() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_4");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof IllegalReferenceException);
            assertTrue(e.getCause().getMessage()
                    .indexOf("some properties are missing for property DBConnectionFactoryKey") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * parameter type property is missing for property DBConnectionFactoryKey,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_ErrorOFNamespace2() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_5");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof IllegalReferenceException);
            assertTrue(e.getCause().getMessage()
                    .indexOf("some properties are missing") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * parameter type property is int which is wrong,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_ErrorOFNamespace3() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_6");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * parameter value property is is wrong,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_ErrorOFNamespace4() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_7");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * class type is wrong,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_ErrorOFNamespace5() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_8");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof IllegalReferenceException);
            assertTrue(e.getCause().getMessage().indexOf(
                    "class type xxx.com.topcoder.db.connectionfactory.DBConnectionFactoryImpl is invalid") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * class type is java.lang.String,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_ErrorOFNamespace6() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_9");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof ClassCastException);
            assertTrue(e.getMessage().indexOf(
                 "The object created by ObjectFactory is not type of DBConnectionFactory") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * DBConnectinFactory Key is missing,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_MissingDBConnectinFactoryKey() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_10");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Missed property 'db_connection_factory_key'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * DBConnectinFactory Key is empty,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyDBConnectinFactoryKey() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_11");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The value for property 'db_connection_factory_key' is empty") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * DBConnectinFactory Key does not exist,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_NotExistDBConnectinFactoryKey() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_12");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
            assertTrue(e.getCause().getCause().toString().indexOf(
                    "java.lang.ClassNotFoundException: xxxxxxxxxxxx") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * Connection Name is missing,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_MissingConnectionName() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_13");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Missed property 'connection_name'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * Connection Name is empty,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyConnectionName2() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_14");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The value for property 'connection_name' is empty") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * IDGenerator Name is missing,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_MissingIDGeneratorName() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_15");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Missed property 'id_generator_name'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * IDGenerator Name is empty,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_EmptyIDGeneratorName2() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_16");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The value for property 'id_generator_name' is empty") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * IDGenerator Name does not exist in data store,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_NoSuchIDGeneratorName() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_17");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof NoSuchIDSequenceException);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * connections property is missing,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_MissingConnections() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_18");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * Producer property is missing,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_MissingProducer() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_19");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Given namespace have an error namespace of Object Factory,
     * The default connection is not configured,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testDatabasePaymentTermDAO_Ctor_DefaultConnectionNotConfigured() {
        try {
            new DatabasePaymentTermDAO("DatabasePaymentTermDAO_Error_20");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }
}
