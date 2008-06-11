/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.ComponentDependencyConfigurationException;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for DefaultXmlDependenciesEntryPersistence class.
 * @author King_Bette
 * @version 1.0
 */
public class DefaultXmlDependenciesEntryPersistenceFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
        "test_files/failuretests/test.xml");
    /**
     * This instance is used in the test.
     */
    private ConfigurationObject config = new DefaultConfigurationObject("default");

    /**
     * Aggregates all tests in this class.
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultXmlDependenciesEntryPersistenceFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        new File("test_files/failuretests/test.xml").delete();
    }

    /**
     * Tears down the environment for the TestCase.
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        new File("test_files/failuretests/test.xml").delete();
    }

    /**
     * Failure test of <code>DefaultXmlDependenciesEntryPersistence(String filePath)</code> constructor.
     * <p>
     * filePath is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDefaultXmlDependenciesEntryPersistence_failure_null_filePath() throws Exception {
        try {
            new DefaultXmlDependenciesEntryPersistence((String) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>DefaultXmlDependenciesEntryPersistence(String filePath)</code> constructor.
     * <p>
     * filePath is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDefaultXmlDependenciesEntryPersistence_failure_empty_filePath() throws Exception {
        try {
            new DefaultXmlDependenciesEntryPersistence("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject configuration)</code>
     * constructor.
     * <p>
     * configuration is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDefaultXmlDependenciesEntryPersistence_failure_null_configuration() throws Exception {
        try {
            new DefaultXmlDependenciesEntryPersistence((ConfigurationObject) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject configuration)</code>
     * constructor.
     * <p>
     * 'file_name' property not exist.
     * </p>
     * <p>
     * Expect ComponentDependencyConfigurationException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDefaultXmlDependenciesEntryPersistence_failure_no_file_name_property() throws Exception {
        try {
            new DefaultXmlDependenciesEntryPersistence(config);
            fail("Expect ComponentDependencyConfigurationException.");
        } catch (ComponentDependencyConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject configuration)</code>
     * constructor.
     * <p>
     * file_name is empty.
     * </p>
     * <p>
     * Expect ComponentDependencyConfigurationException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDefaultXmlDependenciesEntryPersistence_failure_file_name_property_empty() throws Exception {
        config.setPropertyValue("file_name", "  ");
        try {
            new DefaultXmlDependenciesEntryPersistence(config);
            fail("Expect ComponentDependencyConfigurationException.");
        } catch (ComponentDependencyConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject configuration)</code>
     * constructor.
     * <p>
     * file_name property is not String type.
     * </p>
     * <p>
     * Expect ComponentDependencyConfigurationException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDefaultXmlDependenciesEntryPersistence_failure_non_string_type_file_name() throws Exception {
        config.setPropertyValue("file_name", new Long(123));
        try {
            new DefaultXmlDependenciesEntryPersistence(config);
            fail("Expect ComponentDependencyConfigurationException.");
        } catch (ComponentDependencyConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml1() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid1.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml2() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid2.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml3() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid3.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml4() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid4.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml5() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid5.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml6() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid6.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml7() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid7.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml8() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid8.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml is invliad.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_invalid_xml9() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/invalid9.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * xml can not be found.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_file_not_found() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files/failuretests/test.xml");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>load()</code> method.
     * <p>
     * given path is a directory
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLoad_failure_not_file() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files");
        try {
            persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>save(List<DependenciesEntry> entries)</code> method.
     * <p>
     * entries is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSave_failure_null_entries() throws Exception {
        try {
            persistence.save(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>save(List<DependenciesEntry> entries)</code> method.
     * <p>
     * entry is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSave_failure_null_entry() throws Exception {
        List<DependenciesEntry> entries = new ArrayList<DependenciesEntry>();
        entries.add(null);
        try {
            persistence.save(entries);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>save(List<DependenciesEntry> entries)</code> method.
     * <p>
     * file not find.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSave_failure_file_not_found() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_fil/test.xml");
        try {
            persistence.save(new ArrayList<DependenciesEntry>());
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>save(List<DependenciesEntry> entries)</code> method.
     * <p>
     * file is directory.
     * </p>
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSave_failure_not_file() throws Exception {
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(
            "test_files");
        try {
            persistence.save(new ArrayList<DependenciesEntry>());
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }
}
