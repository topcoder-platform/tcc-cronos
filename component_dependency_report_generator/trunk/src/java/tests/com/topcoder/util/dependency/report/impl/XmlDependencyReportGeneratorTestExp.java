/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.report.BaseTestCase;
import com.topcoder.util.dependency.report.CircularComponentDependencyException;
import com.topcoder.util.dependency.report.ComponentIdNotFoundException;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;

/**
 * <p>
 * Failure test for <code>XmlDependencyReportGenerator</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XmlDependencyReportGeneratorTestExp extends BaseTestCase {

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given list is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testCtor1_NullList() throws Exception {
        try {
            new XmlDependencyReportGenerator((List) null, new DefaultConfigurationObject("test"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given list is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testCtor1_EmptyList() throws Exception {
        try {
            new XmlDependencyReportGenerator(new ArrayList(), new DefaultConfigurationObject("test"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given list contains null element, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_ListContainsNull() throws Exception {
        try {
            new XmlDependencyReportGenerator(Arrays.asList(new DependenciesEntry[]{null}),
                new DefaultConfigurationObject("test"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given configuration object is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_NullConfigurationObject() throws Exception {
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyTypes_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", new Date());
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyTypes_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", " ");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyTypes_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("dependency_types", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has value not recognized,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyTypes_ValueNotRecognized() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", "NoSuchType");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" does not have at least one type specified,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyTypes_NoTypeSpecified() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", ";");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The separator should be ";" , not ",".
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyTypes_WrongSeparator() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", "internal,external");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyCategories_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", new Date());
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyCategories_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", " ");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyCategories_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("dependency_categories", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has value not recognized,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyCategories_ValueNotRecognized() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", "NoSuchCategory");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" does not have at least one category specified,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyCategories_NoCategorySpecified() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", ";");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The separator should be ";" , not ",".
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_DependencyCategories_WrongSeparator() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", "compile,test");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyType_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", new Date());
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyType_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", " ");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyType_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("include_dependency_type", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" have value neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyType_ValueNotRecognized() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "TRuE");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyCategory_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_category", new Date());
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyCategory_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_category", " ");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyCategory_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("include_dependency_category", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" have value neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyCategory_ValueNotRecognized() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_category", "TRuE");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyPath_ValueNotTypeOfString() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", new Date());
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyPath_EmptyValue() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", " ");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyPath_MultipleValues() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("include_dependency_path", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" have value neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_IncludeDependencyPath_ValueNotRecognized() throws Exception {
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "TRuE");
        try {
            new XmlDependencyReportGenerator(
                    Arrays.asList(new DependenciesEntry[]{
                            this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2)}), co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given persistence is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_NullPersistence() throws Exception {
        try {
            new XmlDependencyReportGenerator((DependenciesEntryPersistence) null,
                new DefaultConfigurationObject("test"));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Given configuration object is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_NullConfigurationObject() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Error occurs while loading dependencies from persistence,
     * <code>DependencyReportGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ErrorWhileLoadingDependenciesFromPersistence() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubThrow(new DependenciesEntryPersistenceException("Mock Exception"));
        EasyMock.replay(PERSISTENCE);
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, new DefaultConfigurationObject("test"));
            fail("DependencyReportGenerationException expected");
        } catch (DependencyReportGenerationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyTypes_ValueNotTypeOfString() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", new Date());
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyTypes_EmptyValue() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", " ");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyTypes_MultipleValues() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("dependency_types", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" has value not recognized,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyTypes_ValueNotRecognized() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", "NoSuchType");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_types" does not have at least one type specified,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyTypes_NoTypeSpecified() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", ";");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The separator should be ";" , not ",".
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyTypes_WrongSeparator() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_types", "internal,external");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyCategories_ValueNotTypeOfString() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", new Date());
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyCategories_EmptyValue() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", " ");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyCategories_MultipleValues() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("dependency_categories", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" has value not recognized,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyCategories_ValueNotRecognized() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", "NoSuchCategory");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "dependency_categories" does not have at least one category specified,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyCategories_NoCategorySpecified() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", ";");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The separator should be ";" , not ",".
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_DependencyCategories_WrongSeparator() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("dependency_categories", "compile,test");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyType_ValueNotTypeOfString() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", new Date());
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyType_EmptyValue() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", " ");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyType_MultipleValues() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("include_dependency_type", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_type" have value neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyType_ValueNotRecognized() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "TRuE");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyCategory_ValueNotTypeOfString() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_category", new Date());
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyCategory_EmptyValue() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_category", " ");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyCategory_MultipleValues() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("include_dependency_category", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_category" have value neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyCategory_ValueNotRecognized() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_category", "TRuE");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" has value not type of <code>String</code>,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyPath_ValueNotTypeOfString() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", new Date());
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" has empty value,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyPath_EmptyValue() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", " ");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" has multiple values,
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyPath_MultipleValues() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValues("include_dependency_path", new String[]{"1", "2"});
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link XmlDependencyReportGenerator#XmlDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * The property "include_dependency_path" have value neither "true" nor "false" (case sensitive),
     * <code>DependencyReportConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_IncludeDependencyPath_ValueNotRecognized() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "TRuE");
        try {
            new XmlDependencyReportGenerator(PERSISTENCE, co);
            fail("DependencyReportConfigurationException expected");
        } catch (DependencyReportConfigurationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given list is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_NullList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(null, new ByteArrayOutputStream(), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given list is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testGeneratePartialToOutputStream_EmptyList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(new ArrayList(), new ByteArrayOutputStream(), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given list contains null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_ListContainsNull() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{null}), new ByteArrayOutputStream(), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given list contains empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_ListContainsEmpty() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{" "}), new ByteArrayOutputStream(), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given out put stream is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_NullOutputStream() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0"}), (OutputStream) null, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given component ID is not found, <code>ComponentIdNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_ComponentIDNotFound() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"NoSuchID"}), new ByteArrayOutputStream(), true);
            fail("ComponentIdNotFoundException expected");
        } catch (ComponentIdNotFoundException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Circular component dependency detected, <code>CircularComponentDependencyException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_CircularComponentDependency() throws Exception {

        Component component1 = new Component("Component1", "1.0", ComponentLanguage.JAVA);
        Component component2 = new Component("Component2", "1.0", ComponentLanguage.JAVA);

        //Component1 depends on Component2
        DependenciesEntry entry1 = new DependenciesEntry(component1,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component2, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        //Component2 depends on Component1
        DependenciesEntry entry2 = new DependenciesEntry(component2,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component1, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "java-Component2-1.0"}),
                new ByteArrayOutputStream(), true);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected");
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given list is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_NullList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(null, FILE, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given list is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testGeneratePartialToFile_EmptyList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(new ArrayList(), FILE, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given list contains null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_ListContainsNull() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{null}), FILE, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given list contains empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_ListContainsEmpty() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{" "}), FILE, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given file name is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_NullFileName() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0"}), (String) null, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given file name is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_EmptyFileName() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0"}), " ", true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Given component ID is not found, <code>ComponentIdNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_ComponentIDNotFound() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"NoSuchID"}), FILE, true);
            fail("ComponentIdNotFoundException expected");
        } catch (ComponentIdNotFoundException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Circular component dependency detected, <code>CircularComponentDependencyException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_CircularComponentDependency() throws Exception {

        Component component1 = new Component("Component1", "1.0", ComponentLanguage.JAVA);
        Component component2 = new Component("Component2", "1.0", ComponentLanguage.JAVA);

        //Component1 depends on Component2
        DependenciesEntry entry1 = new DependenciesEntry(component1,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component2, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        //Component2 depends on Component1
        DependenciesEntry entry2 = new DependenciesEntry(component2,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component1, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "java-Component2-1.0"}),
                FILE, true);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected");
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * I/O error occurs, <code>DependencyReportGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_IOException() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0"}), "/folder/folder/file", true);
            fail("DependencyReportGenerationException expected");
        } catch (DependencyReportGenerationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Given list is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_NullList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(null, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Given list is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testGeneratePartialAsString_EmptyList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(new ArrayList(), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Given list contains null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_ListContainsNull() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{null}), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Given list contains empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_ListContainsEmpty() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{" "}), true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Given component ID is not found, <code>ComponentIdNotFoundException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_ComponentIDNotFound() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"NoSuchID"}), true);
            fail("ComponentIdNotFoundException expected");
        } catch (ComponentIdNotFoundException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Circular component dependency detected, <code>CircularComponentDependencyException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_CircularComponentDependency() throws Exception {

        Component component1 = new Component("Component1", "1.0", ComponentLanguage.JAVA);
        Component component2 = new Component("Component2", "1.0", ComponentLanguage.JAVA);

        //Component1 depends on Component2
        DependenciesEntry entry1 = new DependenciesEntry(component1,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component2, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        //Component2 depends on Component1
        DependenciesEntry entry2 = new DependenciesEntry(component2,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component1, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "java-Component2-1.0"}),
                true);
           
        } catch (CircularComponentDependencyException e) {
        	 fail("CircularComponentDependencyException unexpected");
        }
    }


    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Given out put stream is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_NullOutputStream() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll((OutputStream) null, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Circular component dependency detected, <code>CircularComponentDependencyException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_CircularComponentDependency() throws Exception {

        Component component1 = new Component("Component1", "1.0", ComponentLanguage.JAVA);
        Component component2 = new Component("Component2", "1.0", ComponentLanguage.JAVA);

        //Component1 depends on Component2
        DependenciesEntry entry1 = new DependenciesEntry(component1,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component2, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        //Component2 depends on Component1
        DependenciesEntry entry2 = new DependenciesEntry(component2,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component1, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll(new ByteArrayOutputStream(), true);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected");
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Given file name is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_NullFileName() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll((String) null, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Given file name is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_EmptyFileName() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll(" ", true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Circular component dependency detected, <code>CircularComponentDependencyException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_CircularComponentDependency() throws Exception {

        Component component1 = new Component("Component1", "1.0", ComponentLanguage.JAVA);
        Component component2 = new Component("Component2", "1.0", ComponentLanguage.JAVA);

        //Component1 depends on Component2
        DependenciesEntry entry1 = new DependenciesEntry(component1,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component2, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        //Component2 depends on Component1
        DependenciesEntry entry2 = new DependenciesEntry(component2,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component1, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll(FILE, true);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected");
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * I/O error occurs, <code>DependencyReportGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_IOException() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll("/folder/folder/file", true);
            fail("DependencyReportGenerationException expected");
        } catch (DependencyReportGenerationException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * Circular component dependency detected, <code>CircularComponentDependencyException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_CircularComponentDependency() throws Exception {

        Component component1 = new Component("Component1", "1.0", ComponentLanguage.JAVA);
        Component component2 = new Component("Component2", "1.0", ComponentLanguage.JAVA);

        //Component1 depends on Component2
        DependenciesEntry entry1 = new DependenciesEntry(component1,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component2, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        //Component2 depends on Component1
        DependenciesEntry entry2 = new DependenciesEntry(component2,
            Arrays.asList(new ComponentDependency[]{
                this.createComponentDependency(component1, DependencyType.EXTERNAL, DependencyCategory.TEST)}));

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2}),
                new DefaultConfigurationObject("test"));

        try {
            generator.generateAll(true);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected");
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#writeReport(List, OutputStream)}.
     * </p>
     *
     * <p>
     * Given list is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteReport_NullList() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        ExtendedXmlDependencyReportGenerator generator =
            new ExtendedXmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.writeReport(null, new ByteArrayOutputStream());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#writeReport(List, OutputStream)}.
     * </p>
     *
     * <p>
     * Given list is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteReport_ListContainsNull() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        ExtendedXmlDependencyReportGenerator generator =
            new ExtendedXmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.writeReport(Arrays.asList(new DependenciesEntry[]{null}), new ByteArrayOutputStream());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#writeReport(List, OutputStream)}.
     * </p>
     *
     * <p>
     * Given out put stream is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteReport_NullOutputStream() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        ExtendedXmlDependencyReportGenerator generator =
            new ExtendedXmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.writeReport(Arrays.asList(new DependenciesEntry[]{entry1}), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#writeReport(List, OutputStream)}.
     * </p>
     *
     * <p>
     * I/O error occurs while writing report, <code>DependencyReportGenerationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteReport_IOException() throws Exception {

        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        ExtendedXmlDependencyReportGenerator generator =
            new ExtendedXmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        try {
            generator.writeReport(Arrays.asList(new DependenciesEntry[]{entry1}), new MockOutputStream());
            fail("DependencyReportGenerationException expected");
        } catch (DependencyReportGenerationException e) {
            //pass
        }
    }
}
