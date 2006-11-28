/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.config.ConfigManager;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>ObjectFactoryHelpers</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class ObjectFactoryHelpersTests extends TestCase {
    /**
     * The configuration manager instance.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * Pre-test setup: initializes the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        MANAGER.add("object_helpers_test.xml");
    }

    /**
     * Post-test cleanup: removes all configuration namespaces.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        for (Iterator it = MANAGER.getAllNamespaces(); it.hasNext();) {
            MANAGER.removeNamespace((String) it.next());
        }
    }

    /**
     * Tests that <code>instantiateObject</code> throws <code>InstantiationException</code> when the namespace does
     * not exist.
     */
    public void test_instantiateObject_missing_namespace() {
        try {
            ObjectFactoryHelpers.instantiateObject("no.such.namespace", "specNamespace", "key", Object.class);
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>instantiateObject</code> throws <code>InstantiationException</code> when the spec namespace
     * property does not exist.
     */
    public void test_instantiateObject_missing_spec_namespace() {
        try {
            ObjectFactoryHelpers.instantiateObject("missing.spec.namespace", "specNamespace", "key", Object.class);
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>instantiateObject</code> throws <code>InstantiationException</code> when the key namespace
     * property does not exist.
     */
    public void test_instantiateObject_missing_key() {
        try {
            ObjectFactoryHelpers.instantiateObject("missing.key", "specNamespace", "key", Object.class);
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>instantiateObject</code> throws <code>InstantiationException</code> when the object cannot
     * be instantiated.
     */
    public void test_instantiateObject_cannot_instantiate() {
        try {
            ObjectFactoryHelpers.instantiateObject("bad.object", "specNamespace", "key", Object.class);
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    // the successful operation of instantiateObject is tested elsewhere, so there is no need for a test here

    /**
     * Tests that <code>getProperty</code> throws <code>InstantiationException</code> when the namespace does not
     * exist.
     */
    public void test_getProperty_missing_namespace() {
        try {
            ObjectFactoryHelpers.getProperty("no.such.namespace", "property");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests that <code>getProperty</code> throws <code>InstantiationException</code> when the property does not
     * exist.
     */
    public void test_getProperty_missing_property() {
        try {
            ObjectFactoryHelpers.getProperty("missing.property", "property");
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    // resolveRemoteEjbReference is tested in <code>RemoteOrpheusMessengerPluginTests</code>

    // resolveLocalEjbReference is tested in <code>LocalOrpheusMessengerPluginTests</code>

    // the successful operation of getProperty is tested elsewhere, so there is no need for a test here
}
