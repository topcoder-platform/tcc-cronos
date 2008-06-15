/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.ComponentDependencyConfigurationException;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for BinaryFileDependenciesEntryPersistence class.
 * 
 * @author King_Bette
 * @version 1.0
 */
public class BinaryFileDependenciesEntryPersistenceFailureTest extends TestCase {
	/**
	 * This instance is used in the test.
	 */
	private ConfigurationObject config = new DefaultConfigurationObject(
			"default");
	/**
	 * this output stream is used in the test.
	 */
	private ObjectOutputStream objectOutputStream;
	/**
	 * file name of temp file.
	 */
	private String fileName = "test_files/failuretests/failuretests.binary";
	/**
	 * This instance is used in the test.
	 */
	private BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence(
			fileName);

	/**
	 * Aggregates all tests in this class.
	 * 
	 * @return Test suite aggregating all tests.
	 */
	public static Test suite() {
		return new TestSuite(
				BinaryFileDependenciesEntryPersistenceFailureTest.class);
	}

	/**
	 * Sets up the environment for the TestCase.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	protected void setUp() throws Exception {
		objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(
				new FileOutputStream(fileName)));
	}

	/**
	 * Tears down the environment for the TestCase.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	protected void tearDown() throws Exception {
		objectOutputStream.close();
		new File(fileName).delete();
	}

	/**
	 * Failure test of
	 * <code>BinaryFileDependenciesEntryPersistence(String filePath)</code>
	 * constructor.
	 * 
	 * <p>
	 * filePath is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testBinaryFileDependenciesEntryPersistence_failure_null_filePath()
			throws Exception {
		try {
			new BinaryFileDependenciesEntryPersistence((String) null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>BinaryFileDependenciesEntryPersistence(String filePath)</code>
	 * constructor.
	 * 
	 * <p>
	 * filePath is empty.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testBinaryFileDependenciesEntryPersistence_failure_empty_filePath()
			throws Exception {
		try {
			new BinaryFileDependenciesEntryPersistence("  ");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>BinaryFileDependenciesEntryPersistence(ConfigurationObject configuration)</code>
	 * constructor.
	 * 
	 * <p>
	 * configuration is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testBinaryFileDependenciesEntryPersistence_failure_null_configuration()
			throws Exception {
		try {
			new BinaryFileDependenciesEntryPersistence(
					(ConfigurationObject) null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>BinaryFileDependenciesEntryPersistence(ConfigurationObject configuration)</code>
	 * constructor.
	 * 
	 * <p>
	 * 'file_name' property not exist.
	 * </p>
	 * 
	 * <p>
	 * Expect ComponentDependencyConfigurationException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testBinaryFileDependenciesEntryPersistence_failure_no_file_name_property()
			throws Exception {
		try {
			new BinaryFileDependenciesEntryPersistence(config);
			fail("Expect ComponentDependencyConfigurationException.");
		} catch (ComponentDependencyConfigurationException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>BinaryFileDependenciesEntryPersistence(ConfigurationObject configuration)</code>
	 * constructor.
	 * 
	 * <p>
	 * file_name is empty.
	 * </p>
	 * 
	 * <p>
	 * Expect ComponentDependencyConfigurationException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testBinaryFileDependenciesEntryPersistence_failure_file_name_property_empty()
			throws Exception {
		config.setPropertyValue("file_name", "  ");
		try {
			new BinaryFileDependenciesEntryPersistence(config);
			fail("Expect ComponentDependencyConfigurationException.");
		} catch (ComponentDependencyConfigurationException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>BinaryFileDependenciesEntryPersistence(ConfigurationObject configuration)</code>
	 * constructor.
	 * 
	 * <p>
	 * file_name property is not String type.
	 * </p>
	 * 
	 * <p>
	 * Expect ComponentDependencyConfigurationException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testBinaryFileDependenciesEntryPersistence_failure_non_string_type_file_name()
			throws Exception {
		config.setPropertyValue("file_name", new Long(123));
		try {
			new BinaryFileDependenciesEntryPersistence(config);
			fail("Expect ComponentDependencyConfigurationException.");
		} catch (ComponentDependencyConfigurationException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * so such file.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_no_such_file() throws Exception {
		objectOutputStream.close();
		new File(fileName).delete();
		try {
			persistence.load();
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }

	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * file structure is invalid.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_structure() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * invalid data.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_data1() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("  ");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component2");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(0);
		objectOutputStream.writeUTF("test_files/xxx");
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}
	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * invalid data.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_data2() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component1");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(100);
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component2");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(0);
		objectOutputStream.writeUTF("test_files/xxx");
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}
	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * invalid data.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_data3() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component1");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("   ");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(0);
		objectOutputStream.writeUTF("test_files/xxx");
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}
	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * invalid data.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_data4() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component1");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component2");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(100);
		objectOutputStream.writeInt(0);
		objectOutputStream.writeUTF("test_files/xxx");
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}
	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * invalid data.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_data5() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component1");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component2");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(100);
		objectOutputStream.writeUTF("test_files/xxx");
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}
	/**
	 * Failure test of <code>load()</code> method.
	 * 
	 * <p>
	 * invalid data.
	 * </p>
	 * 
	 * <p>
	 * Expect DependenciesEntryPersistenceException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testLoad_failure_invalid_data6() throws Exception {
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component1");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(1);
		objectOutputStream.writeUTF("component2");
		objectOutputStream.writeUTF("1.0");
		objectOutputStream.writeInt(0);
		objectOutputStream.writeInt(0);
		objectOutputStream.writeUTF("   ");
		objectOutputStream.close();
		try {
			persistence.load();
			fail("Expect DependenciesEntryPersistenceException.");
		} catch (DependenciesEntryPersistenceException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>save(List<DependenciesEntry> entries)</code>
	 * method.
	 * 
	 * <p>
	 * entries is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
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
	 * Failure test of <code>save(List<DependenciesEntry> entries)</code>
	 * method.
	 * 
	 * <p>
	 * entry is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
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
     * Failure test of <code>save(List<DependenciesEntry> entries)</code>
     * method.
     * 
     * <p>
     * path is invalid.
     * </p>
     * 
     * <p>
     * Expect DependenciesEntryPersistenceException.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    public void testSave_failure_invalid_path() throws Exception {
        persistence = new BinaryFileDependenciesEntryPersistence("test_files");
        try {
            persistence.save(new ArrayList<DependenciesEntry>());
            fail("Expect DependenciesEntryPersistenceException.");
        } catch (DependenciesEntryPersistenceException e) {
            // expect
        }
    }
}
