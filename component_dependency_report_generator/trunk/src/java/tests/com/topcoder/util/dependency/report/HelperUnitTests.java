/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;


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
     * Test method {@link Helper#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given object is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNull_NullObject() {
        try {
            Helper.checkNull(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkNull(Object, String)}.
     * </p>
     *
     * <p>
     * Given object is not null, it should be returned.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        Object obj = new Object();
        assertEquals(obj, Helper.checkNull(obj, "test"));
    }

    /**
     * <p>
     * Test method {@link Helper#checkNullOrEmpty(Object, String)}.
     * </p>
     *
     * <p>
     * Given string is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNullOrEmpty_NullString() {
        try {
            Helper.checkNullOrEmpty(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkNullOrEmpty(Object, String)}.
     * </p>
     *
     * <p>
     * Given string is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckNullOrEmpty_EmptyString() {
        try {
            Helper.checkNullOrEmpty(" ", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkNullOrEmpty(Object, String)}.
     * </p>
     *
     * <p>
     * Given string is not null and not empty, it should be returned.
     * </p>
     */
    public void testCheckNullOrEmpty_Accuracy() {
        String str = "string";
        assertEquals(str, Helper.checkNullOrEmpty(str, "test"));
    }

    /**
     * <p>
     * Test method {@link Helper#checkCollection(java.util.Collection, String, boolean)}.
     * </p>
     *
     * <p>
     * Given collection is empty and empty collection is allowed, no exception expected.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testCheckCollection_EmptyCollectionAllowed() {
        Helper.checkCollection(new ArrayList(), "test", true);
    }

    /**
     * <p>
     * Test method {@link Helper#checkCollection(java.util.Collection, String, boolean)}.
     * </p>
     *
     * <p>
     * Given collection is empty and empty collection is not allowed, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testCheckCollection_EmptyCollectionNotAllowed() {
        try {
            Helper.checkCollection(new HashSet(), "test", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkCollection(java.util.Collection, String, boolean)}.
     * </p>
     *
     * <p>
     * Given collection is null, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    public void testCheckCollection_Null() {
        try {
            Helper.checkCollection(null, "test", true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkCollection(java.util.Collection, String, boolean)}.
     * </p>
     *
     * <p>
     * Given collection contains null element, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testCheckCollection_NullElement() {
        List list = new ArrayList();
        list.add(null);
        try {
            Helper.checkCollection(list, "test", true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkCollection(java.util.Collection, String, boolean)}.
     * </p>
     *
     * <p>
     * Given collection contains empty string, <code>IllegalArgumentException</code> expected.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testCheckCollection_EmptyString() {
        List list = new ArrayList();
        list.add(" ");
        try {
            Helper.checkCollection(list, "test", true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#checkCollection(java.util.Collection, String, boolean)}.
     * </p>
     *
     * <p>
     * Given collection contains duplicate element, should be terminated.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testCheckCollection_DuplicateElement() {
        List list = new ArrayList();
        list.add("string");
        list.add("string");
        assertEquals("duplicate element should be terminated", 1, Helper.checkCollection(list, "test", true).size());
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * Given ConfigurationObject is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_NullConfigurationObject() throws Exception {
        try {
            Helper.getStringProperty(null, "property", "default");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * Given property name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_NullPropertyName() throws Exception {
        try {
            Helper.getStringProperty(new DefaultConfigurationObject("test"), null, "default");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * Given property name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_EmptyPropertyName() throws Exception {
        try {
            Helper.getStringProperty(new DefaultConfigurationObject("test"), " ", "default");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * The property value is not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", new Date());
        try {
            Helper.getStringProperty(co, "property", "default");
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * The property value is null,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_NullValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", null);
        try {
            Helper.getStringProperty(co, "property", "default");
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * The property value is empty,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", " ");
        try {
            Helper.getStringProperty(co, "property", "default");
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * The property has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("property", new String[]{"1", "2"});
        try {
            Helper.getStringProperty(co, "property", "default");
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * <p>
     * The property does not exist, the default value should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_PropertyNotExist() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        String defaultValue = "default";
        assertEquals(defaultValue, Helper.getStringProperty(co, "property", defaultValue));
    }

    /**
     * <p>
     * Test method {@link Helper#getStringProperty(ConfigurationObject, String, String)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetStringProperty_PropertyExist() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", "value");
        assertEquals("value", Helper.getStringProperty(co, "property", "default"));
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * Given ConfigurationObject is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_NullConfigurationObject() throws Exception {
        try {
            Helper.getBooleanProperty(null, "property", Boolean.TRUE);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * Given property name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_NullPropertyName() throws Exception {
        try {
            Helper.getBooleanProperty(new DefaultConfigurationObject("test"), null, Boolean.TRUE);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * Given property name is null,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_EmptyPropertyName() throws Exception {
        try {
            Helper.getBooleanProperty(new DefaultConfigurationObject("test"), " ", Boolean.TRUE);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property value is not type of String,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", new Date());
        try {
            Helper.getBooleanProperty(co, "property", Boolean.TRUE);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property value is null,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_NullValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", null);
        try {
            Helper.getBooleanProperty(co, "property", Boolean.TRUE);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property value is empty,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", " ");
        try {
            Helper.getBooleanProperty(co, "property", Boolean.TRUE);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("property", new String[]{"1", "2"});
        try {
            Helper.getBooleanProperty(co, "property", Boolean.TRUE);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property value is neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_ValueNotRecognized() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", "TRuE");
        try {
            Helper.getBooleanProperty(co, "property", Boolean.TRUE);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property value is "true", true should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_PropertyExist_True() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", "true");
        assertTrue(Helper.getBooleanProperty(co, "property", Boolean.FALSE));
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property value is "false", true should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_PropertyExist_False() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("property", "false");
        assertFalse(Helper.getBooleanProperty(co, "property", Boolean.TRUE));
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property does not exist, the default value is true, true should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_PropertyNotExist_True() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        assertTrue(Helper.getBooleanProperty(co, "property", Boolean.TRUE));
    }

    /**
     * <p>
     * Test method {@link Helper#getBooleanProperty(ConfigurationObject, String, boolean)}.
     * </p>
     *
     * <p>
     * The property does not exist, the default value is false, false should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetBooleanProperty_PropertyNotExist_False() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        assertFalse(Helper.getBooleanProperty(co, "property", Boolean.FALSE));
    }
}
