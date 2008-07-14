/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.Date;

import org.easymock.EasyMock;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Unit tests for <code>Helper</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests extends BaseTestCase {


    /**
     * <p>
     * Test method {@link Helper#getChild(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * Given ConfigurationObject is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetChild_NullConfigurationObject() throws Exception {
        try {
            Helper.getChild(null, "child");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getChild(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * Given child name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetChild_NullChildName() throws Exception {
        try {
            Helper.getChild(new DefaultConfigurationObject("test"), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getChild(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * Given child name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetChild_EmptyChildName() throws Exception {
        try {
            Helper.getChild(new DefaultConfigurationObject("test"), " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getChild(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * The child does not exist but it is required,
     * <code>BaseException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetChild_ChildNotExist() throws Exception {
        try {
            Helper.getChild(new DefaultConfigurationObject("test"), "child");
            fail("BaseException expected");
        } catch (BaseException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getChild(ConfigurationObject, String)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetChild_Acc() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        ConfigurationObject child = new DefaultConfigurationObject("child");
        co.addChild(child);
        assertEquals(child, Helper.getChild(co, "child"));
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * Given ConfigurationObject is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_NullConfigurationObject() throws Exception {
        try {
            Helper.getConfigString(null, "property", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * Given property name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_NullPropertyName() throws Exception {
        try {
            Helper.getConfigString(new DefaultConfigurationObject("test"), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * Given property name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_EmptyPropertyName() throws Exception {
        try {
            Helper.getConfigString(new DefaultConfigurationObject("test"), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * The property value is not type of String,
     * <code>BaseException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", new Date());
        try {
            Helper.getConfigString(co, "property", false);
            fail("BaseException expected");
        } catch (BaseException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * The property value is empty,
     * <code>BaseException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", " ");
        try {
            Helper.getConfigString(co, "property", false);
            fail("BaseException expected");
        } catch (BaseException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * The property is required but does not exist,
     * <code>BaseException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_RequiredPropertyNotExist() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        try {
            Helper.getConfigString(co, "property", true);
            fail("BaseException expected");
        } catch (BaseException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * The property has multiple values,
     * <code>BaseException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("property", new String[]{"s1", "s2"});
        try {
            Helper.getConfigString(co, "property", true);
            fail("BaseException expected");
        } catch (BaseException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_Acc_1() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", "value");
        assertEquals("value", Helper.getConfigString(co, "property", true));
    }

    /**
     * <p>
     * Test method {@link Helper#getConfigString(ConfigurationObject, String)}.
     * </p>
     *
     * <p>
     * The property is optional and not present, null should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetConfigString_Acc_2() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        assertNull(Helper.getConfigString(co, "property", false));
    }

    /**
     * <p>
     * The value of ENC entry is not string.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLookupENC_NotString() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup("entry")).andStubReturn(Boolean.TRUE);
        EasyMock.replay(CONTEXT);

        try {
            Helper.lookupENC(CONTEXT, "entry");
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * The value of ENC entry is empty.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testLookupENC_EmptyValue() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup("entry")).andStubReturn(" ");
        EasyMock.replay(CONTEXT);

        try {
            Helper.lookupENC(CONTEXT, "entry");
            fail("ContestManagerConfigurationException expected");
        } catch (ContestManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * The object bound is not entity manager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetEntityManager_NotEntityManager() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup("unit")).andStubReturn(new Object());
        EasyMock.replay(CONTEXT);

        try {
            Helper.getEntityManager(CONTEXT, "unit");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * The object bound is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetEntityManager_NullObjectBound() throws Exception {
        EasyMock.reset(CONTEXT);
        EasyMock.expect(CONTEXT.lookup("unit")).andStubReturn(null);
        EasyMock.replay(CONTEXT);

        try {
            Helper.getEntityManager(CONTEXT, "unit");
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given logger is null, nothing happens.
     * </p>
     */
    public void testLogEnter_NullLogger() {
        Helper.logEnter(null, "class", "method");
    }

    /**
     * <p>
     * Given logger is null, nothing happens.
     * </p>
     */
    public void testLogExit_NullLogger() {
        Helper.logExit(null, "class", "method");
    }

    /**
     * <p>
     * Given exception is null, nothing happens.
     * </p>
     */
    public void testLogException_NullLogger() {
        Helper.logException(null, null);
    }
}
