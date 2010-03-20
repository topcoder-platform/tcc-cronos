/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.impl.ConfigurationException;
import com.topcoder.management.contest.coo.impl.TestHelper;
import com.topcoder.util.errorhandling.BaseCriticalException;

/**
 * <p>
 * Unit test case of {@link Component}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest extends TestCase {

    /**
     * test method <code>checkNull(String,T)</code>in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckNull() throws Exception {
        String test = Helper.checkNull(null, "testHelper");
        assertEquals("should be 'testHelper'", "testHelper", test);
    }

    /**
     * <p>
     * failure test method <code>checkNull(String,T)</code>in helper class.
     * </p>
     * <p>
     * data is null,IAE should throw.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckNull_fail() throws Exception {
        try {
            Helper.checkNull(null, null);
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * test method <code>checkNull(Log,String,T)</code> in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckNullWithLog() throws Exception {
        assertSame("should be 'hello'", "hello", Helper.checkNull(null, "string", "hello"));
    }

    /**
     * test method <code>checkString(String,String)</code> in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckString() throws Exception {
        assertSame("should be 'hello'", "hello", Helper.checkString("string", "hello"));
    }

    /**
     * <p>
     * failure test method <code>checkString(String,String)</code> in helper
     * class.
     * </p>
     * <p>
     * string is empty.IAE should throw.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckString_fail() throws Exception {
        try {
            Helper.checkString("string", " ");
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }

    }

    /**
     * <p>
     * failure test method <code>checkStringNotEmpty(String,String)</code> in
     * helper class.
     * </p>
     * <p>
     * string is empty.IAE should throw.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckStringNotEmpty() throws Exception {
        assertSame("should be 'hello'", "hello", Helper.checkStringNotEmpty("string", "hello"));
        try {
            Helper.checkStringNotEmpty("string", " ");
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * test method <code>checkString(Log,String,String)</code> in helper
     * class.
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckStringWithLog() throws Exception {
        Helper.checkString(null, "string", "hello");
        try {
            Helper.checkString(null, "string", " ");
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * test method <code>checkId(Log,String,long)</code> in helper class.
     * </p>
     * <p>
     * if id <=0, IAE should throw.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testCheckId() throws Exception {
        Helper.checkId(null, "id", 10);
        try {
            Helper.checkId(null, "id", 0);
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * test method <code>logEnter(Log,String)</code> in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testLogEnter() throws Exception {
        Helper.logEnter(null, "method");
    }

    /**
     * test method <code>logExit(Log,String)</code> in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testLogExit() throws Exception {
        Helper.logExit(null, "method");
    }

    /**
     * test method <code>logInfo(Log,String)</code> in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testLogInfo() throws Exception {
        Helper.logInfo(null, "method");
    }

    /**
     * test method <code>logError(Log,String)</code> in helper class.
     *
     * @throws Exception to JUNIT.
     */
    public void testLogError() throws Exception {
        assertEquals("should be true", true, Helper.logError(null,
            new BaseCriticalException()) instanceof BaseCriticalException);
    }

    /**
     * <p>
     * failure test for <code>getProperty()</code> method.
     * </p>
     * <p>
     * value should be string.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetProperty_fail() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("key", 1);
        try {
            Helper.getProperty(config, "key");
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>getProperty()</code> method.
     * </p>
     * <p>
     * value should be string.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetProperty_fail_1() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("key", 1);
        try {
            Helper.getStringProperty(config, "key", true);
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>getStringProperty()</code> method.
     * </p>
     * <p>
     * value should not be empty.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetStringProperty_fail() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("key", " ");
        try {
            Helper.getStringProperty(config, "key", true);
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * <p>
     * accuracy test for <code>getStringProperty()</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetStringProperty() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("key", "value");
        Helper.getStringProperty(config, "key", true);
    }

    /**
     * <p>
     * failure test for <code>getStringProperty()</code> method.
     * </p>
     * <p>
     * value should not be null.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetProperty_fail_3() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("key", null);
        try {
            Helper.getStringProperty(config, "key", true);
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>getStringProperty()</code> method.
     * </p>
     * <p>
     * value should not be empty.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetProperty_fail_4() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("key", " ");
        try {
            Helper.getStringProperty(config, "key", false);
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>getChild()</code> method.
     * </p>
     * <p>
     * child not exist.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetChild_fail_1() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        try {
            Helper.getChild(config, "abc");
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>getChild()</code> method.
     * </p>
     * <p>
     * child not exist.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetChild() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        ConfigurationObject child = new DefaultConfigurationObject("child");
        config.addChild(child);
        assertNotNull("fail to get child", Helper.getChild(config, "child"));
    }

    /**
     * <p>
     * test for <code>getLogger(ConfiguraionObject)</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGetLogger() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");
        assertNotNull("not be null", Helper.getLogger(config));
    }

    /**
     * <p>
     * failure test for <code>createFactory()</code> method.
     * </p>
     * <p>
     * wrong format when creating factory
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testCreateFactory_fail_2() throws Exception {
        try {
            Helper.createFactory(null);
            fail("ConfigurationException should throw.");
        } catch (ConfigurationException e) {
            // OK
        }
    }

    /**
     * accuracy test <code>releaseDBresource()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testReleaseDBResource() throws Exception {
        Helper.releaseDBResource(null, null, null);
    }

    /**
     * accuracy test <code>getComponentByTypeAndCategory()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testGetComponentByTypeAndCategory() throws Exception {
        int seed = 10000;
        ComponentDependency dep = TestHelper.getComponentDependency(DependencyCategory.COMPILE,
            DependencyType.EXTERNAL, seed);
        List<ComponentDependency> deps = new ArrayList<ComponentDependency>();
        deps.add(dep);

        List<Component> components = Helper.getComponentByTypeAndCategory(deps,
            DependencyCategory.COMPILE, DependencyType.EXTERNAL);
        assertEquals("only one component in list", 1, components.size());
        assertTrue("seed should be in description", components.get(0).getDescription().contains(seed
                + ""));
        assertTrue("seed should be in license", components.get(0).getLicense().contains(seed
                + ""));
        assertTrue("seed should be in version", components.get(0).getVersion().contains(seed
                + ""));
    }

    /**
     * accuracy test <code>setBackValue()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testSetBackValue() throws Exception {
        ComponentDependency dep = TestHelper.getComponentDependency(DependencyCategory.COMPILE,
            DependencyType.EXTERNAL, 100);
        Component component = dep.getComponent();
        component.setDescription("NONE");
        List<Component> components = new ArrayList<Component>();
        components.add(component);
        assertEquals("description should be 'NONE'", "NONE", components.get(0).getDescription());
        Helper.setBackValue(components);
        assertNull("description should be null", components.get(0).getDescription());
    }

    /**
     * accuracy test <code>resetNullValue()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testResetNullValue() throws Exception {
        ComponentDependency dep = TestHelper.getComponentDependency(DependencyCategory.COMPILE,
            DependencyType.EXTERNAL, 100);
        Component component = dep.getComponent();
        component.setDescription(null);
        List<Component> components = new ArrayList<Component>();
        components.add(component);

        assertNull("description should be null", components.get(0).getDescription());
        Helper.resetNullValue(components);
        assertEquals("description should be 'NONE'", "NONE", components.get(0).getDescription());

    }

}
