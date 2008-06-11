/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

/**
 * <p>
 * Unit tests for <code>BuildFileParsingHelper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BuildFileParsingHelperTest extends TestCase {
    /**
     * <p>
     * properties map to be used in tests.
     * </p>
     */
    private Map<String, String> properties;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        properties = new HashMap<String, String>();
        properties.put("property1", "value1");
        properties.put("property2", "value2");
        properties.put("property3", "value3");
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Gets the test suite for <code>BuildFileParsingHelper</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>BuildFileParsingHelper</code> class.
     */
    public static Test suite() {
        return new TestSuite(BuildFileParsingHelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * Passes in value with no properties and it stays what it was. No exception should be thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy1() {
        String input = "no properties";
        String output = "no properties";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * Passes in one property and it is replaced by appropriate value. No exception should be thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy2() {
        String input = "${property1}";
        String output = "value1";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * Passes ${property1} twice and all are replaced by the corresponding values. No exception should be thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy3() {
        String input = "${property1} ${property1}";
        String output = "value1 value1";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * Passes ${property1} twice and another property between and all are replaced by the corresponding values. No
     * exception should be thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy4() {
        String input = "${property1} ${property2} ${property1}";
        String output = "value1 value2 value1";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * Passes in a string with all properties and they are replaced by appropriate values. No exception should be
     * thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy5() {
        String input = "${property1} ${property2} ${property3}";
        String output = "value1 value2 value3";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * property4 doesn't exist and it is removed. No exception should be thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy6() {
        String input = "${property1} ${property4} ${property3}";
        String output = "value1  value3";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>resolveProperties(String,Map&lt;,String&gt;)</code>.
     * </p>
     * <p>
     * property4 doesn't exist and it is removed also since it is just one pass, some $ will still stay. No exception
     * should be thrown.
     * </p>
     */
    public void testResolveProperties_Accuracy7() {
        String input = "${property1} ${property4} ${${property3}";
        String output = "value1  ${value3";
        assertEquals("The resolved value is not as expected.", output, BuildFileParsingHelper.resolveProperties(input,
            properties));
    }

    /**
     * <p>
     * Accuracy test for <code>parseFileNameWithoutExtension(String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testParseFileNameWithoutExtension_Accuracy1() {
        // component_name-component-version
        String fileNameWithoutExt = "component_name-1.0";
        String[] results = BuildFileParsingHelper.parseFileNameWithoutExtension(fileNameWithoutExt);
        assertEquals("component_name", results[0]);
        assertEquals("1.0", results[1]);
    }

    /**
     * <p>
     * Accuracy test for <code>parseFileNameWithoutExtension(String)</code>.
     * </p>
     * <p>
     * Version can not be ended with letter here and version will be "". No exception should be thrown.
     * </p>
     */
    public void testParseFileNameWithoutExtension_Accuracy2() {
        // component_name-component-version
        String fileNameWithoutExt = "component_name-1.0b";
        String[] results = BuildFileParsingHelper.parseFileNameWithoutExtension(fileNameWithoutExt);
        assertEquals("component_name-1.0b", results[0]);
        assertEquals("", results[1]);
    }

    /**
     * <p>
     * Accuracy test for <code>getFileNameWithoutExtension(String)</code>.
     * </p>
     * <p>
     * Passes in ".jar" and return "" value. No exception should be thrown.
     * </p>
     */
    public void testGetFileNameWithoutExtension_Accuracy1() {
        // allow .jar passed but it will be caught down the stream
        assertEquals("It should just return ''", "", BuildFileParsingHelper.getFileNameWithoutExtension(".jar"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileNameWithoutExtension(String)</code>.
     * </p>
     * <p>
     * Gets file name without extension correctly. No exception should be thrown.
     * </p>
     */
    public void testGetFileNameWithoutExtension_Accuracy2() {
        // allow .jar passed but it will be caught down the stream
        assertEquals("It should just return 'component_1.0'", "component_1.0", BuildFileParsingHelper
            .getFileNameWithoutExtension("component_1.0.jar"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileName(String)</code>.
     * </p>
     * <p>
     * Passes in some file path and return file name. No exception should be thrown.
     * </p>
     */
    public void testGetFileName_Accuracy1() {
        assertEquals("It should return 'log.jar'", "log.jar", BuildFileParsingHelper.getFileName("logging/log.jar"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileName(String)</code>.
     * </p>
     * <p>
     * Passes in some file path and return file name. No exception should be thrown.
     * </p>
     */
    public void testGetFileName_Accuracy2() {
        assertEquals("It should return 'log.jar'", "log.jar", BuildFileParsingHelper.getFileName("../logging/log.jar"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileName(String)</code>.
     * </p>
     * <p>
     * Passes in some file path and return file name. No exception should be thrown.
     * </p>
     */
    public void testGetFileName_Accuracy3() {
        assertEquals("It should return 'log.dll'", "log.dll", BuildFileParsingHelper
            .getFileName("..\\logging\\log.dll"));
    }

    /**
     * <p>
     * Accuracy test for <code>contains(List&lt;ComponentDependency&gt;,ComponentDependency)</code>.
     * </p>
     * <p>
     * Passes in an element with same name/version/category and it is considered as included. No exception should be
     * thrown.
     * </p>
     */
    public void testContains_Accuracy1() {
        ComponentDependency d1 = new ComponentDependency("d1", "v1", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
        ComponentDependency d2 = new ComponentDependency("d2", "v2", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
        List<ComponentDependency> list = new ArrayList<ComponentDependency>();
        list.add(d1);
        list.add(d2);

        ComponentDependency searchedOn = new ComponentDependency("d1", "v1", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.EXTERNAL, "pathxx");
        assertTrue("it should be considered as included.", BuildFileParsingHelper.contains(list, searchedOn));
    }

    /**
     * <p>
     * Accuracy test for <code>contains(List&lt;ComponentDependency&gt;,ComponentDependency)</code>.
     * </p>
     * <p>
     * Passes in an element with different name/version/category set and it is considered as not included. No exception
     * should be thrown.
     * </p>
     */
    public void testContains_Accuracy2() {
        ComponentDependency d1 = new ComponentDependency("d1", "v1", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
        ComponentDependency d2 = new ComponentDependency("d2", "v2", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
        List<ComponentDependency> list = new ArrayList<ComponentDependency>();
        list.add(d1);
        list.add(d2);

        ComponentDependency searchedOn = new ComponentDependency("d2", "v1", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.EXTERNAL, "pathxx");
        assertFalse("it should be considered as not included.", BuildFileParsingHelper.contains(list, searchedOn));
    }

    /**
     * <p>
     * Accuracy test for <code>handleChildElements(Element,ChildElementHandler)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testHandleChildElements_Accuracy() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbFactory.newDocumentBuilder();
        Document doc = db.newDocument();
        Element root = doc.createElement("test");
        doc.appendChild(root);
        root.appendChild(doc.createElement("child1"));
        root.appendChild(doc.createElement("child2"));

        final IntegerHolder holder = new IntegerHolder();
        holder.setTimes(0);
        BuildFileParsingHelper.handleChildElements(root, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(Element child) {
                holder.setTimes(holder.getTimes() + 1);
            }
        });

        assertEquals("All child elements should be handled.", 2, holder.getTimes());
    }

    /**
     * <p>
     * A simple holder for integer value.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class IntegerHolder {
        /**
         * <p>
         * Holds a times int value.
         * </p>
         */
        private int times;

        /**
         * <p>
         * Gets times.
         * </p>
         *
         * @return times
         */
        public int getTimes() {
            return times;
        }

        /**
         * <p>
         * Sets times.
         * </p>
         *
         * @param times value
         */
        public void setTimes(int times) {
            this.times = times;
        }

    }
}
