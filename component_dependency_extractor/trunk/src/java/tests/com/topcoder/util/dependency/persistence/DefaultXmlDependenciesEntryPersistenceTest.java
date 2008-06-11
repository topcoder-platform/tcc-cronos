/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.persistence;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.ComponentDependencyConfigurationException;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.TestHelper;

/**
 * <p>
 * Unit tests for <code>DefaultXmlDependenciesEntryPersistence</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultXmlDependenciesEntryPersistenceTest extends TestCase {
    /**
     * <p>
     * Test file to be used in tests.
     * </p>
     */
    private static final String XML_OUTPUT_FILE = "test_files" + File.separator + "testXMLPersistence.xml";

    /**
     * <p>
     * <code>DefaultXmlDependenciesEntryPersistenceTest</code> instance to be tested.
     * </p>
     */
    private DefaultXmlDependenciesEntryPersistence persistence;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new DefaultXmlDependenciesEntryPersistence(XML_OUTPUT_FILE);
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
        persistence = null;
    }

    /**
     * <p>
     * Gets the test suite for <code>DefaultXmlDependenciesEntryPersistence</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>DefaultXmlDependenciesEntryPersistence</code>
     *         class.
     */
    public static Test suite() {
        return new TestSuite(DefaultXmlDependenciesEntryPersistenceTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DefaultXmlDependenciesEntryPersistence(String)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", persistence);
    }

    /**
     * <p>
     * Failure test for constructor <code>DefaultXmlDependenciesEntryPersistence(String)</code>.
     * </p>
     * <p>
     * Passes in empty file path value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure1() throws Exception {
        try {
            new DefaultXmlDependenciesEntryPersistence(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor2_Accuracy() throws Exception {
        DefaultConfigurationObject config = new DefaultConfigurationObject("test");
        config.setPropertyValue("file_name", XML_OUTPUT_FILE);
        assertNotNull("It should return non-null instance.", new DefaultXmlDependenciesEntryPersistence(config));
    }

    /**
     * <p>
     * Failure test for constructor <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor2_Failure1() throws Exception {
        try {
            DefaultConfigurationObject config = null;
            new DefaultXmlDependenciesEntryPersistence(config);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>DefaultXmlDependenciesEntryPersistence(ConfigurationObject)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>ComponentDependencyConfigurationException</code> should be thrown.
     * </p>
     */
    public void testCtor2_Failure2() {
        try {
            DefaultConfigurationObject config = new DefaultConfigurationObject("test");
            new DefaultXmlDependenciesEntryPersistence(config);
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>load()</code>.
     * </p>
     * <p>
     * Loads existing xml file successfully. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testLoad_Accuracy() throws Exception {
        persistence.save(TestHelper.getEntries());
        assertEquals("it should load 2 entries back.", 2, persistence.load().size());
    }

    /**
     * <p>
     * Failure test for <code>load()</code>.
     * </p>
     * <p>
     * Loads from wrong place. A <code>DependenciesEntryPersistenceException</code> should be thrown.
     * </p>
     */
    public void testLoad_Failure1() {
        try {
            persistence = new DefaultXmlDependenciesEntryPersistence("test_files" + File.separator + "wrong_file.xml");
            persistence.load();
            fail("DependenciesEntryPersistenceException should be thrown.");
        } catch (DependenciesEntryPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>save(List&lt;DependenciesEntry&gt;)</code>.
     * </p>
     * <p>
     * Saves entries into xml file. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSave_Accuracy() throws Exception {
        persistence.save(TestHelper.getEntries());
        // pass. we will use load to verify it
    }

    /**
     * <p>
     * Failure test for <code>save(List&lt;DependenciesEntry&gt;)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSave_Failure1() throws Exception {
        try {
            persistence.save(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>save(List&lt;DependenciesEntry&gt;)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>DependenciesEntryPersistenceException</code> should be thrown.
     * </p>
     */
    public void testSave_Failure2() {
        try {
            persistence = new DefaultXmlDependenciesEntryPersistence("test_files" + File.separator + "wrong_folder"
                + File.separator + "wrong_file.xml");
            persistence.save(TestHelper.getEntries());
            fail("DependenciesEntryPersistenceException should be thrown.");
        } catch (DependenciesEntryPersistenceException e) {
            // pass
        }
    }
}
