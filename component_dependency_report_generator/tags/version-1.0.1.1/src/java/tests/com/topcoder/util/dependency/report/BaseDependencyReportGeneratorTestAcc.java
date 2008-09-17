/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.EasyMock;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

/**
 * <p>
 * Accuracy test for <code>BaseDependencyReportGenerator</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDependencyReportGeneratorTestAcc extends BaseTestCase {

    /**
     * <p>
     * Delete the report file if any.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        File file = new File(FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * <p>
     * Test constructor
     * {@link BaseDependencyReportGenerator#BaseDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Provides a complete empty <code>ConfigurationObject</code>. The default values should be used.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_EmptyConfiguration() throws Exception {
        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}),
                new DefaultConfigurationObject("test"));

        assertTrue("DependencyType should be included by default", generator.isDependencyTypeIncluded());
        assertTrue("DependencyCategory should be included by default", generator.isDependencyCategoryIncluded());
        assertFalse("DependencyPath should not be included by default", generator.isDependencyPathIncluded());

        Set < DependencyCategory > allowedDependencyCategories = generator.getAllowedDependencyCategories();
        assertEquals("There should be 2 allowed dependency categories by default", 2,
            allowedDependencyCategories.size());
        assertTrue("The 'compile' category should be allowed by default",
            allowedDependencyCategories.contains(DependencyCategory.COMPILE));
        assertTrue("The 'test' category should be allowed by default",
            allowedDependencyCategories.contains(DependencyCategory.TEST));

        Set < DependencyType > allowedDependencyTypes = generator.getAllowedDependencyTypes();
        assertEquals("There should be 2 allowed dependency types by default", 2, allowedDependencyTypes.size());
        assertTrue("The 'internal' type should be allowed by default",
            allowedDependencyTypes.contains(DependencyType.INTERNAL));
        assertTrue("The 'external' type should be allowed by default",
            allowedDependencyTypes.contains(DependencyType.EXTERNAL));

        Map < String, DependenciesEntry > dependencies = generator.getDependencies();

        assertEquals("There should be 2 dependencies", 2, dependencies.size());

        assertTrue("There should be a component 'java-Component1-1.0'",
            dependencies.containsKey("java-Component1-1.0"));

        assertTrue("There should be a component 'dot_net-Component2-2.0'",
            dependencies.containsKey("dot_net-Component2-2.0"));
    }

    /**
     * <p>
     * Test constructor
     * {@link BaseDependencyReportGenerator#BaseDependencyReportGenerator(java.util.List, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Provides a <code>ConfigurationObject</code>. The default values should be overridden.
     * </p>
     *
     * <p>
     * Note the boolean value is case sensitive, enum value is case insensitive.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor1_NotEmptyConfiguration() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("test");

        config.setPropertyValue("include_dependency_type", "false");
        config.setPropertyValue("include_dependency_category", "false");
        config.setPropertyValue("include_dependency_path", "true");

        //The enum value is case insensitive
        config.setPropertyValue("dependency_categories", "ComPile");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}),
                config);

        assertFalse("DependencyType should not be included", generator.isDependencyTypeIncluded());
        assertFalse("DependencyCategory should not be included", generator.isDependencyCategoryIncluded());
        assertTrue("DependencyPath should be included", generator.isDependencyPathIncluded());

        Set < DependencyCategory > allowedDependencyCategories = generator.getAllowedDependencyCategories();
        assertEquals("There should be 1 allowed dependency category", 1,
            allowedDependencyCategories.size());
        assertTrue("The 'compile' category should be allowed",
            allowedDependencyCategories.contains(DependencyCategory.COMPILE));

        Set < DependencyType > allowedDependencyTypes = generator.getAllowedDependencyTypes();
        assertEquals("There should be 1 allowed dependency type", 1, allowedDependencyTypes.size());
        assertTrue("The 'internal' type should be allowed",
            allowedDependencyTypes.contains(DependencyType.INTERNAL));

        Map < String, DependenciesEntry > dependencies = generator.getDependencies();

        assertEquals("There should be 2 dependencies", 2, dependencies.size());

        assertTrue("There should be a component 'java-Component1-1.0'",
            dependencies.containsKey("java-Component1-1.0"));

        assertTrue("There should be a component 'dot_net-Component2-2.0'",
            dependencies.containsKey("dot_net-Component2-2.0"));
    }

    /**
     * <p>
     * Test constructor
     * {@link BaseDependencyReportGenerator#BaseDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Provides a complete empty <code>ConfigurationObject</code>. The default values should be used.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_EmptyConfiguration() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(PERSISTENCE, new DefaultConfigurationObject("test"));

        assertTrue("DependencyType should be included by default", generator.isDependencyTypeIncluded());
        assertTrue("DependencyCategory should be included by default", generator.isDependencyCategoryIncluded());
        assertFalse("DependencyPath should not be included by default", generator.isDependencyPathIncluded());

        Set < DependencyCategory > allowedDependencyCategories = generator.getAllowedDependencyCategories();
        assertEquals("There should be 2 allowed dependency categories by default", 2,
            allowedDependencyCategories.size());
        assertTrue("The 'compile' category should be allowed by default",
            allowedDependencyCategories.contains(DependencyCategory.COMPILE));
        assertTrue("The 'test' category should be allowed by default",
            allowedDependencyCategories.contains(DependencyCategory.TEST));

        Set < DependencyType > allowedDependencyTypes = generator.getAllowedDependencyTypes();
        assertEquals("There should be 2 allowed dependency types by default", 2, allowedDependencyTypes.size());
        assertTrue("The 'internal' type should be allowed by default",
            allowedDependencyTypes.contains(DependencyType.INTERNAL));
        assertTrue("The 'external' type should be allowed by default",
            allowedDependencyTypes.contains(DependencyType.EXTERNAL));

        Map < String, DependenciesEntry > dependencies = generator.getDependencies();

        assertEquals("There should be 2 dependencies", 2, dependencies.size());

        assertTrue("There should be a component 'java-Component1-1.0'",
            dependencies.containsKey("java-Component1-1.0"));

        assertTrue("There should be a component 'dot_net-Component2-2.0'",
            dependencies.containsKey("dot_net-Component2-2.0"));
    }

    /**
     * <p>
     * Test constructor
     * {@link BaseDependencyReportGenerator#BaseDependencyReportGenerator(
     * com.topcoder.util.dependency.DependenciesEntryPersistence, ConfigurationObject)}.
     * </p>
     *
     * <p>
     * Provides a <code>ConfigurationObject</code>. The default values should be overridden.
     * </p>
     *
     * <p>
     * Note the boolean value is case sensitive, enum value is case insensitive.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_NotEmptyConfiguration() throws Exception {
        EasyMock.reset(PERSISTENCE);
        EasyMock.expect(PERSISTENCE.load()).andStubReturn(
                Arrays.asList(new DependenciesEntry[]{
                    this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2),
                    this.createDependenciesEntry("Component2", "2.0", ComponentLanguage.DOT_NET, 1)}));
        EasyMock.replay(PERSISTENCE);

        ConfigurationObject config = new DefaultConfigurationObject("test");

        config.setPropertyValue("include_dependency_type", "false");
        config.setPropertyValue("include_dependency_category", "false");
        config.setPropertyValue("include_dependency_path", "true");

        //The enum value is case insensitive
        config.setPropertyValue("dependency_categories", "CoMpiLe");
        config.setPropertyValue("dependency_types", "iNteRnal");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(PERSISTENCE, config);

        assertFalse("DependencyType should not be included", generator.isDependencyTypeIncluded());
        assertFalse("DependencyCategory should not be included", generator.isDependencyCategoryIncluded());
        assertTrue("DependencyPath should be included", generator.isDependencyPathIncluded());

        Set < DependencyCategory > allowedDependencyCategories = generator.getAllowedDependencyCategories();
        assertEquals("There should be 1 allowed dependency category", 1,
            allowedDependencyCategories.size());
        assertTrue("The 'compile' category should be allowed",
            allowedDependencyCategories.contains(DependencyCategory.COMPILE));

        Set < DependencyType > allowedDependencyTypes = generator.getAllowedDependencyTypes();
        assertEquals("There should be 1 allowed dependency type", 1, allowedDependencyTypes.size());
        assertTrue("The 'internal' type should be allowed",
            allowedDependencyTypes.contains(DependencyType.INTERNAL));

        Map < String, DependenciesEntry > dependencies = generator.getDependencies();

        assertEquals("There should be 2 dependencies", 2, dependencies.size());

        assertTrue("There should be a component 'java-Component1-1.0'",
            dependencies.containsKey("java-Component1-1.0"));

        assertTrue("There should be a component 'dot_net-Component2-2.0'",
            dependencies.containsKey("dot_net-Component2-2.0"));
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#setAllowedDependencyTypes(Set)}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testSetAllowedDependencyTypes() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        //First generates reports, this will populate the cache
        generator.generateAll(true);

        //Set allowedDependencyTypes
        Set < DependencyType > types = new HashSet();
        types.add(DependencyType.EXTERNAL);
        generator.setAllowedDependencyTypes(types);

        assertEquals("Cache should be cleared", 0, generator.getCachedResults().size());

        Set < DependencyType > allowedDependencyTypes = generator.getAllowedDependencyTypes();
        assertEquals("There should be 1 allowed dependency type", 1, allowedDependencyTypes.size());
        assertTrue("The 'EXTERNAL' type should be allowed",
            allowedDependencyTypes.contains(DependencyType.EXTERNAL));
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#setAllowedDependencyCategories(Set)}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testSetAllowedDependencyCategories() throws Exception {
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1}),
                new DefaultConfigurationObject("test"));

        //First generates reports, this will populate the cache
        generator.generateAll(true);

        //Set allowedDependencyTypes
        Set < DependencyCategory > types = new HashSet();
        types.add(DependencyCategory.TEST);
        generator.setAllowedDependencyCategories(types);

        assertEquals("Cache should be cleared", 0, generator.getCachedResults().size());

        Set < DependencyCategory > allowedDependencyCategories = generator.getAllowedDependencyCategories();
        assertEquals("There should be 1 allowed dependency category", 1,
            allowedDependencyCategories.size());
        assertTrue("The 'TEST' category should be allowed",
            allowedDependencyCategories.contains(DependencyCategory.TEST));
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_1() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry2(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is false. The indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_2() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(new ByteArrayOutputStream(), false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_3() throws Exception {
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

        //Set "dependency_types" to only allow internal
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry4(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyType should be internal", DependencyType.INTERNAL, cd.getType());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_4() throws Exception {
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

        //Set "dependency_categories" to only allow compile
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_categories", "coMPIle");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry4(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyCategory should be compile", DependencyCategory.COMPILE, cd.getCategory());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * In this test case, we use a same instance of generator, first generate all the reports with only direct
     * dependencies, then generate all the report with indirect dependencies included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_5() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(new ByteArrayOutputStream(), false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }


        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(new ByteArrayOutputStream(), true);

        entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry2(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * Component1 and Component2 have same dependencies. Component3 depends on Component1 and Component2.
     * When generating report for Component3, the duplicate indirect dependencies should be terminated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToOutputStream_6() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents java-Component2-1.0
        //The Component2 also depends on java-Component1_dependency1-1.0
        DependenciesEntry entry2 = new DependenciesEntry(new Component("Component2", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry1.getDependencies().get(1)}));

        //Entry3 represents java-Component3-1.0
        //The Component3 depends on Component1 and Component2
        DependenciesEntry entry3 = new DependenciesEntry(new Component("Component3", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{
                new ComponentDependency(entry1.getTargetComponent().getName(),
                    entry1.getTargetComponent().getVersion(),
                    entry1.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry1.getTargetComponent().getName() + ".jar"),
                new ComponentDependency(entry2.getTargetComponent().getName(),
                    entry2.getTargetComponent().getVersion(),
                    entry2.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry2.getTargetComponent().getName() + ".jar")
            }));

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3", indirect dependencies are included
        generator.generateAll(new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry3(indirect included, duplicate terminated)",
                        FOUR, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_1() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is false. The indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_2() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            new ByteArrayOutputStream(), false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_3() throws Exception {
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

        //Set "dependency_types" to only allow internal
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyType should be internal", DependencyType.INTERNAL, cd.getType());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_4() throws Exception {
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

        //Set "dependency_categories" to only allow compile
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_categories", "coMPIle");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyCategory should be compile", DependencyCategory.COMPILE, cd.getCategory());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * In this test case, we use a same instance of generator, first generate report with only direct
     * dependencies, then generate report with indirect dependencies included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_5() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            new ByteArrayOutputStream(), false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }


        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            new ByteArrayOutputStream(), true);

        entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, java.io.OutputStream, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * Component1 and Component2 have same dependencies. Component3 depends on Component1 and Component2.
     * When generating report for Component3, the duplicate indirect dependencies should be terminated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToOutputStream_6() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents java-Component2-1.0
        //The Component2 has same dependencies as Component1
        DependenciesEntry entry2 = new DependenciesEntry(new Component("Component2", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry1.getDependencies().get(1)}));

        //Entry3 represents java-Component3-1.0
        //The Component3 depends on Component1 and Component2
        DependenciesEntry entry3 = new DependenciesEntry(new Component("Component3", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{
                new ComponentDependency(entry1.getTargetComponent().getName(),
                    entry1.getTargetComponent().getVersion(),
                    entry1.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry1.getTargetComponent().getName() + ".jar"),
                new ComponentDependency(entry2.getTargetComponent().getName(),
                    entry2.getTargetComponent().getVersion(),
                    entry2.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry2.getTargetComponent().getName() + ".jar")
            }));

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}),
                new DefaultConfigurationObject("test"));

        //Generate report for "entry3", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component3-1.0"}),
            new ByteArrayOutputStream(), true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 1 entries to write", 1, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            assertTrue(entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName()));
            assertEquals("There should be 4 dependencies for entry3(indirect included, duplicate terminated)",
                    FOUR, entry.getDependencies().size());
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_1() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry2(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is false. The indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_2() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_3() throws Exception {
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

        //Set "dependency_types" to only allow internal
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry4(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyType should be internal", DependencyType.INTERNAL, cd.getType());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_4() throws Exception {
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

        //Set "dependency_categories" to only allow compile
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_categories", "coMPIle");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry4(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyCategory should be compile", DependencyCategory.COMPILE, cd.getCategory());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * In this test case, we use a same instance of generator, first generate all the reports with only direct
     * dependencies, then generate all the report with indirect dependencies included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_5() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }


        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(true);

        entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry2(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * Component1 and Component2 have same dependencies. Component3 depends on Component1 and Component2.
     * When generating report for Component3, the duplicate indirect dependencies should be terminated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllAsString_6() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents java-Component2-1.0
        //The Component2 also depends on java-Component1_dependency1-1.0
        DependenciesEntry entry2 = new DependenciesEntry(new Component("Component2", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry1.getDependencies().get(1)}));

        //Entry3 represents java-Component3-1.0
        //The Component3 depends on Component1 and Component2
        DependenciesEntry entry3 = new DependenciesEntry(new Component("Component3", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{
                new ComponentDependency(entry1.getTargetComponent().getName(),
                    entry1.getTargetComponent().getVersion(),
                    entry1.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry1.getTargetComponent().getName() + ".jar"),
                new ComponentDependency(entry2.getTargetComponent().getName(),
                    entry2.getTargetComponent().getVersion(),
                    entry2.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry2.getTargetComponent().getName() + ".jar")
            }));

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3", indirect dependencies are included
        generator.generateAll(true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry3(indirect included, duplicate terminated)",
                        FOUR, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_1() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is false. The indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_2() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_3() throws Exception {
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

        //Set "dependency_types" to only allow internal
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyType should be internal", DependencyType.INTERNAL, cd.getType());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_4() throws Exception {
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

        //Set "dependency_categories" to only allow compile
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_categories", "coMPIle");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyCategory should be compile", DependencyCategory.COMPILE, cd.getCategory());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * In this test case, we use a same instance of generator, first generate report with only direct
     * dependencies, then generate report with indirect dependencies included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_5() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }


        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            true);

        entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * Component1 and Component2 have same dependencies. Component3 depends on Component1 and Component2.
     * When generating report for Component3, the duplicate indirect dependencies should be terminated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialAsString_6() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents java-Component2-1.0
        //The Component2 has same dependencies as Component1
        DependenciesEntry entry2 = new DependenciesEntry(new Component("Component2", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry1.getDependencies().get(1)}));

        //Entry3 represents java-Component3-1.0
        //The Component3 depends on Component1 and Component2
        DependenciesEntry entry3 = new DependenciesEntry(new Component("Component3", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{
                new ComponentDependency(entry1.getTargetComponent().getName(),
                    entry1.getTargetComponent().getVersion(),
                    entry1.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry1.getTargetComponent().getName() + ".jar"),
                new ComponentDependency(entry2.getTargetComponent().getName(),
                    entry2.getTargetComponent().getVersion(),
                    entry2.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry2.getTargetComponent().getName() + ".jar")
            }));

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}),
                new DefaultConfigurationObject("test"));

        //Generate report for "entry3", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component3-1.0"}),
            true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 1 entries to write", 1, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            assertTrue(entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName()));
            assertEquals("There should be 4 dependencies for entry3(indirect included, duplicate terminated)",
                    FOUR, entry.getDependencies().size());
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_1() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry2(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(FILE, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is false. The indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_2() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(FILE, false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(FILE, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal".
     * The allowed categories are "compile, test".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_3() throws Exception {
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

        //Set "dependency_types" to only allow internal
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry4(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyType should be internal", DependencyType.INTERNAL, cd.getType());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(FILE, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile".
     * Generate all reports for "entry1, entry2, entry3, entry4, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_4() throws Exception {
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

        //Set "dependency_categories" to only allow compile
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_categories", "coMPIle");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry4(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyCategory should be compile", DependencyCategory.COMPILE, cd.getCategory());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(FILE, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * In this test case, we use a same instance of generator, first generate all the reports with only direct
     * dependencies, then generate all the report with indirect dependencies included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_5() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are not included
        generator.generateAll(FILE, false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }


        //Generate all reports for "entry1, entry2, entry3, entry4, entry5", indirect dependencies are included
        generator.generateAll(FILE, true);

        entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 5 entries to write", FIVE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry2(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry4.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry4(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generateAll(FILE, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * Component1 and Component2 have same dependencies. Component3 depends on Component1 and Component2.
     * When generating report for Component3, the duplicate indirect dependencies should be terminated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateAllToFile_6() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents java-Component2-1.0
        //The Component2 also depends on java-Component1_dependency1-1.0
        DependenciesEntry entry2 = new DependenciesEntry(new Component("Component2", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry1.getDependencies().get(1)}));

        //Entry3 represents java-Component3-1.0
        //The Component3 depends on Component1 and Component2
        DependenciesEntry entry3 = new DependenciesEntry(new Component("Component3", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{
                new ComponentDependency(entry1.getTargetComponent().getName(),
                    entry1.getTargetComponent().getVersion(),
                    entry1.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry1.getTargetComponent().getName() + ".jar"),
                new ComponentDependency(entry2.getTargetComponent().getName(),
                    entry2.getTargetComponent().getVersion(),
                    entry2.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry2.getTargetComponent().getName() + ".jar")
            }));

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}),
                new DefaultConfigurationObject("test"));

        //Generate all reports for "entry1, entry2, entry3", indirect dependencies are included
        generator.generateAll(FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry2.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry2(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry3(indirect included, duplicate terminated)",
                        FOUR, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_1() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is false. The indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_2() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            FILE, false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal".
     * The allowed categories are "compile, test".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_3() throws Exception {
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

        //Set "dependency_types" to only allow internal
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_types", "intErnAl");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(external filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(external filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyType should be internal", DependencyType.INTERNAL, cd.getType());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile".
     * The given componentIds are the ids for "entry1, entry3, entry5".
     * Given <code>indirect</code> is true. The indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_4() throws Exception {
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

        //Set "dependency_categories" to only allow compile
        ConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("dependency_categories", "coMPIle");

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                config);

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry1(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 1 dependencies for entry3(test category filtered out)",
                        1, entry.getDependencies().size());
                assertEquals("Component1_dependency1_dependency2", entry.getDependencies().get(0).getName());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(test category filtered out)",
                        2, entry.getDependencies().size());
                assertEquals("Component2_dependency2", entry.getDependencies().get(0).getName());
                assertEquals("Component2_dependency2_dependency2", entry.getDependencies().get(1).getName());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }

            for (ComponentDependency cd : entry.getDependencies()) {
                assertEquals("DependencyCategory should be compile", DependencyCategory.COMPILE, cd.getCategory());
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * In this test case, we use a same instance of generator, first generate report with only direct
     * dependencies, then generate report with indirect dependencies included.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_5() throws Exception {
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

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3, entry4, entry5}),
                new DefaultConfigurationObject("test"));

        //Generate reports for "entry1, entry3, entry5", indirect dependencies are not included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            FILE, false);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry1(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect not included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry5(indirect not included)",
                        2, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }


        //Generate reports for "entry1, entry3, entry5", indirect dependencies are included
        generator.generate(Arrays.asList(
            new String[]{"java-Component1-1.0", "java-Component1_dependency1-1.0", "dot_net-Component3-3.0"}),
            FILE, true);

        entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 3 entries to write", THREE, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            //Actually, to compare components, the component ID(language-name-version) should be used
            //But we assign different names in this test case, so just comparing name is sufficient
            if (entry.getTargetComponent().getName().equalsIgnoreCase(entry1.getTargetComponent().getName())) {
                assertEquals("There should be 4 dependencies for entry1(indirect included)",
                        FOUR, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName())) {
                assertEquals("There should be 2 dependencies for entry3(indirect included)",
                        2, entry.getDependencies().size());
            } else if (entry.getTargetComponent().getName().equalsIgnoreCase(entry5.getTargetComponent().getName())) {
                assertEquals("There should be 6 dependencies for entry5(indirect included)",
                        SIX, entry.getDependencies().size());
            } else {
                fail(entry.getTargetComponent().getName() + " is not expected");
            }
        }
    }

    /**
     * <p>
     * Test method {@link BaseDependencyReportGenerator#generate(java.util.List, String, boolean)}.
     * </p>
     *
     * <p>
     * The allowed types are "internal, external".
     * The allowed categories are "compile, test".
     * </p>
     *
     * <p>
     * Component1 and Component2 have same dependencies. Component3 depends on Component1 and Component2.
     * When generating report for Component3, the duplicate indirect dependencies should be terminated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGeneratePartialToFile_6() throws Exception {
        //Entry1 represents java-Component1-1.0
        DependenciesEntry entry1 = this.createDependenciesEntry("Component1", "1.0", ComponentLanguage.JAVA, 2);

        //Entry2 represents java-Component2-1.0
        //The Component2 has same dependencies as Component1
        DependenciesEntry entry2 = new DependenciesEntry(new Component("Component2", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{entry1.getDependencies().get(0), entry1.getDependencies().get(1)}));

        //Entry3 represents java-Component3-1.0
        //The Component3 depends on Component1 and Component2
        DependenciesEntry entry3 = new DependenciesEntry(new Component("Component3", "1.0", ComponentLanguage.JAVA),
            Arrays.asList(new ComponentDependency[]{
                new ComponentDependency(entry1.getTargetComponent().getName(),
                    entry1.getTargetComponent().getVersion(),
                    entry1.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry1.getTargetComponent().getName() + ".jar"),
                new ComponentDependency(entry2.getTargetComponent().getName(),
                    entry2.getTargetComponent().getVersion(),
                    entry2.getTargetComponent().getLanguage(),
                    DependencyCategory.COMPILE,
                    DependencyType.INTERNAL,
                    "tcs/" + entry2.getTargetComponent().getName() + ".jar")
            }));

        MockDependencyReportGenerator generator =
            new MockDependencyReportGenerator(
                Arrays.asList(new DependenciesEntry[]{entry1, entry2, entry3}),
                new DefaultConfigurationObject("test"));

        //Generate report for "entry3", indirect dependencies are included
        generator.generate(Arrays.asList(new String[]{"java-Component3-1.0"}),
            FILE, true);

        List < DependenciesEntry > entriesToWrite = generator.getEntriesToWrite();
        assertEquals("There should be 1 entries to write", 1, entriesToWrite.size());

        for (DependenciesEntry entry : entriesToWrite) {
            assertTrue(entry.getTargetComponent().getName().equalsIgnoreCase(entry3.getTargetComponent().getName()));
            assertEquals("There should be 4 dependencies for entry3(indirect included, duplicate terminated)",
                    FOUR, entry.getDependencies().size());
        }
    }
}
