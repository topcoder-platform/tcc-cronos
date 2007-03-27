/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;


/**
 * <p>
 * This test case contains failure tests for <code>SimpleCommonManager</code>.
 * </p>
 *
 * <p>
 * All constructors are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class SimpleCommonManagerTestExp1 extends BaseBaseTestCase {
    /**
     * <p>
     * Given <code>PaymentTermDAO</code> is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_Ctor_NullDAO() throws Exception {
        try {
            new SimpleCommonManager(null, 5);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf(
                   "The PaymentTermDAO used by SimpleCommonManager should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_Ctor_NullNamespace() throws Exception {
        try {
            new SimpleCommonManager(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("namespace should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_Ctor_EmptyNamespace() throws Exception {
        try {
            new SimpleCommonManager(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("namespace should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is unknown, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_Ctor_UnknownNamespace() throws Exception {
        try {
            new SimpleCommonManager("UnknownNamespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The namespace 'UnknownNamespace' is unknown") >= 0);
        }
    }

    /**
     * <p>
     * Default namespace "com.topcoder.timetracker.common.SimpleCommonManager" is unknown,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_Ctor_DefaultNamespaceUnknown() throws Exception {
        try {
            this.removeConfigManagerNS();
            new SimpleCommonManager();
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf(
                 "The namespace 'com.topcoder.timetracker.common.SimpleCommonManager' is unknown") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is missing the namespace of Object Factory,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_MissOFNamespace() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_1");
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
    public void testSimpleCommonManager_Ctor_EmptyOFNamespace() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_2");
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
    public void testSimpleCommonManager_Ctor_UnknownOFNamespace() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_3");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof SpecificationConfigurationException);
            assertTrue(e.getCause().getCause() instanceof UnknownNamespaceException);
        }
    }

    /**
     * <p>
     * Given namespace is missing the key of DAO,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_MissDAOKey() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_4");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Missed property 'payment_term_dao_key'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an empty key of DAO,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_EmptyDAOKey() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_5");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The value for property 'payment_term_dao_key' is empty") >= 0);
        }
    }

    /**
     * <p>
     * Error occurs while creating DAO,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_ErrorWhileCreatingDAO1() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_11");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Error occurs while instantiating PaymentTermDAO") >= 0);
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Error occurs while creating DAO,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_ErrorWhileCreatingDAO2() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_12");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf(
                 "The object created by ObjectFactory is not type of PaymentTermDAO") >= 0);
            assertTrue(e.getCause() instanceof ClassCastException);
        }
    }
}
