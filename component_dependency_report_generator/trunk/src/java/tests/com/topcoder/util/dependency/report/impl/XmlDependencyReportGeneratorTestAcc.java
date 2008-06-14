/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.report.BaseTestCase;

/**
 * <p>
 * Accuracy test for <code>XmlDependencyReportGenerator</code>.
 * </p>
 *
 * <p>
 * Since <code>XmlDependencyReportGenerator</code> extends
 * <code>BaseDependencyReportGenerator</code> and only override
 * <code>writeReport()</code> method, so this test case focus
 * on the accuracy result of <code>writeReport()</code> method.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XmlDependencyReportGeneratorTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToOutputStream_1() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(os, true);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/AllReports_Indirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToOutputStream_2() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(os, true);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/AllReports_Indirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToOutputStream_3() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(os, false);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/AllReports_NoIndirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToOutputStream_4() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(os, false);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/AllReports_NoIndirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * Write all reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsAsString_1() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        String result = generator.generateAll(true);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_Indirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * Write all reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsAsString_2() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        String result = generator.generateAll(true);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_Indirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * Write all reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsAsString_3() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        String result = generator.generateAll(false);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_NoIndirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * Write all reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsAsString_4() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        String result = generator.generateAll(false);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_NoIndirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToFile_1() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(FILE, true);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_Indirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToFile_2() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(FILE, true);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_Indirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToFile_3() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(FILE, false);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_NoIndirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * Write all reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWriteAllReportsToFile_4() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(FILE, false);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/AllReports_NoIndirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToOutputStream_1() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate reports for "entry1, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), os, true);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_Indirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToOutputStream_2() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate reports for "entry1, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), os, true);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_Indirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToOutputStream_3() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate reports for "entry1, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), os, false);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_NoIndirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to out put stream.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToOutputStream_4() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        OutputStream os = new ByteArrayOutputStream();

        //Generate reports for "entry1, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), os, false);

        String result = os.toString();

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_NoIndirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsAsString_1() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are included
        String result = generator.generate(
            Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), true);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_Indirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsAsString_2() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are included
        String result = generator.generate(
            Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), true);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_Indirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsAsString_3() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are not included
        String result = generator.generate(
            Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), false);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_NoIndirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports as string.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsAsString_4() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are not included
        String result = generator.generate(
            Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), false);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_NoIndirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToFile_1() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), FILE, true);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_Indirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToFile_2() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), FILE, true);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_Indirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToFile_3() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_path" to true. This value is false by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_path", "true");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), FILE, false);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_NoIndirect_WithTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * Test method {@link XmlDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * Write partial reports to file.
     * </p>
     *
     * <p>
     * Indirect dependencies not included.
     * </p>
     *
     * <p>
     * The type, category and path are all not included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWritePartialReportsToFile_4() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);
        //Entry2 represents dot_net-Component2-2.0
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 2);

        //Entry3 represents java-Component1_dependency1-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry(entry1.getDependencies().get(0), 2);
        //Entry4 represents dot_net-Component2_dependency2-2.0
        DependenciesEntry entry4 = this.createDependenciesEntry(entry2.getDependencies().get(1), 2);

        //Entry5 represents dot_net-Component3-3.0
        //The Component3 also depends on java-Component1_dependency1-1.0 and dot_net-Component2_dependency2-2.0
        DependenciesEntry entry5 = new DependenciesEntry(new Component("Component3", "3.0", ComponentLanguage.DOT_NET),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry2.getDependencies().get(1)}));

        //Set "include_dependency_type" and "include_dependency_category" to false. These values are true by default.
        ConfigurationObject co = new DefaultConfigurationObject("test");
        co.setPropertyValue("include_dependency_type", "false");
        co.setPropertyValue("include_dependency_category", "false");

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}), co);

        //Generate reports for "entry1, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(new String[]{"java-Component1-1.0", "dot_net-Component3-3.0"}), FILE, false);

        String result = this.readFile(FILE);

        assertEquals(result.trim(), this.readFile("/xml/PartialReports_NoIndirect_WithoutTypeCategoryPath.xml"));
    }

    /**
     * <p>
     * This test focus on the case when there is empty dependence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testEmptyDependency() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents dot_net-Component2-2.0. Note it has empty dependency
        DependenciesEntry entry2 = this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 0);

        //Entry1 represents java-Component3-1.0
        DependenciesEntry entry3 = this.createDependenciesEntry("Component3", "1.0", ComponentLanguage.JAVA, 2);

        XmlDependencyReportGenerator generator =
            new XmlDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}), new DefaultConfigurationObject("test"));

        String report = generator.generateAll(true);

        assertEquals(report.trim(), this.readFile("/xml/EmptyDependency.xml"));

    }
}
