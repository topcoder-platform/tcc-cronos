/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.failuretests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;
import com.topcoder.util.dependency.report.CircularComponentDependencyException;
import com.topcoder.util.dependency.report.ComponentIdNotFoundException;
import com.topcoder.util.dependency.report.DependencyReportConfigurationException;
import com.topcoder.util.dependency.report.DependencyReportGenerationException;

/**
 * Failure tests for class BaseDependencyReportGenerator.
 *
 * @author extra
 * @version 1.0
 */
public class BaseDependencyReportGeneratorFailureTest extends TestCase {

    /**
     * The generator for test.
     */
    private BaseDependencyReportGenerator generator;

    /**
     * The dependencies entry persistence for test.
     */
    private MockDependenciesEntryPersistence persistence;

    /**
     * The configuration object for test.
     */
    private ConfigurationObject configuration;

    /**
     * The dependencies entries for test.
     */
    private List<DependenciesEntry> dependencies;

    /**
     * The component ids.
     */
    private List<String> componentIds;

    /**
     * The output stream.
     */
    private OutputStream os;

    /**
     * The file name.
     */
    private String fileName;

    /**
     * Represents whether indirect.
     */
    private boolean indirect;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        persistence = new MockDependenciesEntryPersistence();
        configuration = new DefaultConfigurationObject("default");

        dependencies = FailureTestHelper.getDependenciesEntries();
        dependencies.addAll(FailureTestHelper.getCircleDependenciesEntries());
        persistence.save(dependencies);

        generator = new MockDependencyReportGenerator(persistence, configuration);

        componentIds = new ArrayList<String>();
        componentIds.add("java-logging_wrapper-2.0.0");
        os = new ByteArrayOutputStream();

        fileName = "test_files/baseGenerator.txt";
        super.setUp();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        super.tearDown();
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). With null
     * dependencies, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_Null_dependencies() throws Exception {
        dependencies = null;
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If dependencies
     * contains null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_NullElement_dependencies() throws Exception {
        dependencies.add(null);
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If dependencies
     * is empty, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_Empty_dependencies() throws Exception {
        dependencies.clear();
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). With null
     * configuration, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_Null_configuration() throws Exception {
        configuration = null;
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_types configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_dependency_types() throws Exception {
        configuration.setPropertyValue("dependency_types", " ");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_types configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_dependency_types2() throws Exception {
        configuration.setPropertyValue("dependency_types", ";");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_types configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Invalid_dependency_types() throws Exception {
        configuration.setPropertyValue("dependency_types", "internal;invalid");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_types configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_InvalidType_dependency_types() throws Exception {
        configuration.setPropertyValue("dependency_types", new Integer(1));
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_categories configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_dependency_categories() throws Exception {
        configuration.setPropertyValue("dependency_categories", " ");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_categories configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_dependency_categories2() throws Exception {
        configuration.setPropertyValue("dependency_categories", ";");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_categories configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Invalid_dependency_categories() throws Exception {
        configuration.setPropertyValue("dependency_categories", "compile;invalid");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * dependency_categories configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_InvalidType_dependency_categories() throws Exception {
        configuration.setPropertyValue("dependency_categories", new Integer(1));
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_type configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_include_dependency_type() throws Exception {
        configuration.setPropertyValue("include_dependency_type", " ");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_type configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Invalid_include_dependency_type() throws Exception {
        configuration.setPropertyValue("include_dependency_type", "invalid");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_type configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_InvalidType_include_dependency_type() throws Exception {
        configuration.setPropertyValue("include_dependency_type", new Integer(1));
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_category configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_include_dependency_category() throws Exception {
        configuration.setPropertyValue("include_dependency_category", " ");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_category configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Invalid_include_dependency_category() throws Exception {
        configuration.setPropertyValue("include_dependency_category", "invalid");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_category configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_InvalidType_include_dependency_category() throws Exception {
        configuration.setPropertyValue("include_dependency_category", new Integer(1));
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_path configuration is empty, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Empty_include_dependency_path() throws Exception {
        configuration.setPropertyValue("include_dependency_path", " ");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_path configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_Invalid_include_dependency_path() throws Exception {
        configuration.setPropertyValue("include_dependency_path", "invalid");
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(dependencies, configuration). If property
     * include_dependency_path configuration is invalid, DependencyReportConfigurationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_configuration_InvalidType_include_dependency_path() throws Exception {
        configuration.setPropertyValue("include_dependency_path", new Integer(1));
        try {
            new MockDependencyReportGenerator(dependencies, configuration);
            fail("DependencyReportConfigurationException expected.");
        } catch (DependencyReportConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(persistence, configuration). With null
     * persistence, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_Null_persistence() throws Exception {
        persistence = null;
        try {
            new MockDependencyReportGenerator(persistence, configuration);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(persistence, configuration). With null
     * persistence, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_Null_configuration() throws Exception {
        configuration = null;
        try {
            new MockDependencyReportGenerator(persistence, configuration);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for constructor method BaseDependencyReportGenerator(persistence, configuration). If persistence
     * throw exception, DependencyReportGenerationException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_Error_persistence() throws Exception {
        persistence.setError(true);
        try {
            new MockDependencyReportGenerator(persistence, configuration);
            fail("DependencyReportGenerationException expected.");
        } catch (DependencyReportGenerationException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If componentIds is null, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_Null_componentIds() throws Exception {
        componentIds = null;
        try {
            generator.generate(componentIds, os, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If componentIds is empty, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_Empty_componentIds() throws Exception {
        componentIds.clear();
        try {
            generator.generate(componentIds, os, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If componentIds contains null element,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_NullElement_componentIds() throws Exception {
        componentIds.add(null);
        try {
            generator.generate(componentIds, os, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If componentIds contains not exist id,
     * ComponentIdNotFoundException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_NotExist_componentIds() throws Exception {
        componentIds.add("notexist");
        try {
            generator.generate(componentIds, os, indirect);
            fail("ComponentIdNotFoundException expected.");
        } catch (ComponentIdNotFoundException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If indirect is true and circular component
     * dependency exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_Circle_componentIds1() throws Exception {
        componentIds.add("java-a-1.0");
        indirect = true;
        try {
            generator.generate(componentIds, os, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If indirect is true and circular component
     * dependency exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_Circle_componentIds2() throws Exception {
        componentIds.add("java-b-2.0");
        indirect = true;
        try {
            generator.generate(componentIds, os, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If indirect is true and circular component
     * dependency exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_Circle_componentIds3() throws Exception {
        componentIds.add("java-c-3.0");
        indirect = true;
        try {
            generator.generate(componentIds, os, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, os, indirect). If os is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_os_indirect_Null_os() throws Exception {
        os = null;
        try {
            generator.generate(componentIds, os, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If componentIds is null,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_Null_componentIds() throws Exception {
        componentIds = null;
        try {
            generator.generate(componentIds, fileName, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If componentIds is empty,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_Empty_componentIds() throws Exception {
        componentIds.clear();
        try {
            generator.generate(componentIds, fileName, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If componentIds contains null element,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_NullElement_componentIds() throws Exception {
        componentIds.add(null);
        try {
            generator.generate(componentIds, fileName, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If componentIds contains not exist id,
     * ComponentIdNotFoundException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_NotExist_componentIds() throws Exception {
        componentIds.add("notexist");
        try {
            generator.generate(componentIds, fileName, indirect);
            fail("ComponentIdNotFoundException expected.");
        } catch (ComponentIdNotFoundException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If indirect is true and circular component
     * dependency exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_Circle_componentIds1() throws Exception {
        componentIds.add("java-a-1.0");
        indirect = true;
        try {
            generator.generate(componentIds, fileName, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If indirect is true and circular component
     * dependency exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_Circle_componentIds2() throws Exception {
        componentIds.add("java-b-2.0");
        indirect = true;
        try {
            generator.generate(componentIds, fileName, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If indirect is true and circular component
     * dependency exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_Circle_componentIds3() throws Exception {
        componentIds.add("java-c-3.0");
        indirect = true;
        try {
            generator.generate(componentIds, fileName, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, fileName, indirect). If fileName is null, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_fileName_indirect_Null_fileName() throws Exception {
        fileName = null;
        try {
            generator.generate(componentIds, fileName, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If componentIds is null, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_Null_componentIds() throws Exception {
        componentIds = null;
        try {
            generator.generate(componentIds, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If componentIds is empty, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_Empty_componentIds() throws Exception {
        componentIds.clear();
        try {
            generator.generate(componentIds, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If componentIds contains null element,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_NullElement_componentIds() throws Exception {
        componentIds.add(null);
        try {
            generator.generate(componentIds, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If componentIds contains not exist id,
     * ComponentIdNotFoundException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_NotExist_componentIds() throws Exception {
        componentIds.add("notexist");
        try {
            generator.generate(componentIds, indirect);
            fail("ComponentIdNotFoundException expected.");
        } catch (ComponentIdNotFoundException e) {
            // expected
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If indirect is true and circular component dependency
     * exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_Circle_componentIds1() throws Exception {
        componentIds.add("java-a-1.0");
        indirect = true;
        try {
            generator.generate(componentIds, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If indirect is true and circular component dependency
     * exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_Circle_componentIds2() throws Exception {
        componentIds.add("java-b-2.0");
        indirect = true;
        try {
            generator.generate(componentIds, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generate(componentIds, indirect). If indirect is true and circular component dependency
     * exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_componentIds_indirect_Circle_componentIds3() throws Exception {
        componentIds.add("java-c-3.0");
        indirect = true;
        try {
            generator.generate(componentIds, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generateAll(os, indirect). If indirect is true and circular component dependency exist,
     * CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerateAll_os_indirect_Circle_componentIds3() throws Exception {
        indirect = true;
        try {
            generator.generateAll(os, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generateAll(os, indirect). If os is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_os_indirect_Null_os() throws Exception {
        os = null;
        try {
            generator.generateAll(os, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generateAll(fileName, indirect). If indirect is true and circular component dependency
     * exist, CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerateAll_fileName_indirect_Circle_componentIds3() throws Exception {
        indirect = true;
        try {
            generator.generateAll(fileName, indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method generateAll(fileName, indirect). If fileName is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_fileName_indirect_Null_fileName() throws Exception {
        fileName = null;
        try {
            generator.generateAll(fileName, indirect);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method generateAll(indirect). If indirect is true and circular component dependency exist,
     * CircularComponentDependencyException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerateAll_indirect_Circle_componentIds3() throws Exception {
        indirect = true;
        try {
            generator.generateAll(indirect);
            
        } catch (CircularComponentDependencyException e) {
        	fail("CircularComponentDependencyException unexpected.");
        }
    }

    /**
     * Failure test for method setAllowedDependencyTypes(types). If types is null, IllegalArgumentException expected.
     *
     */
    public void testSetAllowedDependencyTypes_Null_types() {
        Set<DependencyType> types = null;
        try {
            generator.setAllowedDependencyTypes(types);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method setAllowedDependencyTypes(types). If types contains null element,
     * IllegalArgumentException expected.
     *
     */
    public void testSetAllowedDependencyTypes_NullElement_types() {
        Set<DependencyType> types = new HashSet<DependencyType>();
        types.add(DependencyType.INTERNAL);
        types.add(null);
        try {
            generator.setAllowedDependencyTypes(types);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method setAllowedDependencyTypes(types). If types is null, IllegalArgumentException expected.
     *
     */
    public void testSetAllowedDependencyTypes_Empty_types() {
        Set<DependencyType> types = new HashSet<DependencyType>();
        try {
            generator.setAllowedDependencyTypes(types);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method setAllowedDependencyCategories(categories). If categories is null,
     * IllegalArgumentException expected.
     *
     */
    public void testSetAllowedDependencyCategories_Null_categories() {
        Set<DependencyCategory> categories = null;
        try {
            generator.setAllowedDependencyCategories(categories);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method setAllowedDependencyCategories(categories). If categories contains null element,
     * IllegalArgumentException expected.
     *
     */
    public void testSetAllowedDependencyCategories_NullElement_categories() {
        Set<DependencyCategory> categories = new HashSet<DependencyCategory>();
        categories.add(DependencyCategory.COMPILE);
        categories.add(null);
        try {
            generator.setAllowedDependencyCategories(categories);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method setAllowedDependencyCategories(categories). If categories is null,
     * IllegalArgumentException expected.
     *
     */
    public void testSetAllowedDependencyCategories_Empty_categories() {
        Set<DependencyCategory> categories = new HashSet<DependencyCategory>();
        try {
            generator.setAllowedDependencyCategories(categories);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
