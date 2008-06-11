/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor.utility;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.Utils;
import com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractor;
import com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractor;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>ComponentDependencyExtractorUtility</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDependencyExtractorUtilityTest extends TestCase {
    /**
     * <p>
     * Gets the test suite for <code>ComponentDependencyExtractorUtility</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>ComponentDependencyExtractorUtility</code>
     *         class.
     */
    public static Test suite() {
        return new TestSuite(ComponentDependencyExtractorUtilityTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * Specifies no switches. Uses settings in config.xml (in classpath). Result is in report1.xml. No exception should
     * be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain1() throws Exception {
        // should handle test_files/scripts/default.build,build.xml
        ComponentDependencyExtractorUtility.main(new String[] {});

        // let's load the file and see
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence("test_files"
            + File.separator + "report1.xml");
        assertEquals("It should load 2 entries.", 2, persistence.load().size());
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * Uses config2.xml. Result is in report2.xml. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain2() throws Exception {
        // should handle test_files/scripts/default.build,build.xml plus a valid java jar file
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "config2.xml"});

        // let's load the file and see
        DefaultXmlDependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence("test_files"
            + File.separator + "report2.xml");
        assertEquals("It should load 3 entries.", 3, persistence.load().size());
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * See test 1 and we override it by using binary persistence and save the result into report1.binary. Result is in
     * report1.xml. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain3() throws Exception {
        // should handle test_files/scripts/default.build,build.xml
        ComponentDependencyExtractorUtility.main(new String[] {"-pclass",
            BinaryFileDependenciesEntryPersistence.class.getName(), "-o", "test_files/report1.binary"});

        // let's load the file and see
        BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence("test_files"
            + File.separator + "report1.binary");
        assertEquals("It should load 2 entries.", 2, persistence.load().size());
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * See test 1 and we override it by using customized settings through switches and save the result into
     * report1a.binary. Result is in report1a.binary. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain4() throws Exception {
        // should handle test_files/scripts/build.xml only
        ComponentDependencyExtractorUtility.main(new String[] {"-f", "binary", "-o", "test_files/report1a.binary",
            "-eclass", JavaDependenciesEntryExtractor.class.getName(), "-dtype", "internal", "-dcat", "compile", "-i",
            "test_files/scripts/*", "-nolog"});

        // let's load the file and see
        BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence("test_files"
            + File.separator + "report1a.binary");
        assertEquals("It should load only 1 entry.", 1, persistence.load().size());
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * See test 1 and we override it by using customized settings through switches and save the result into
     * report1b.binary. Result is supposed in report1b.binary but it will never be created due to a wrongfile.zip error.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain5() throws Exception {
        // should handle test_files/scripts/build.xml only
        ComponentDependencyExtractorUtility.main(new String[] {"-f", "binary", "-o", "test_files/report1b.binary",
            "-eclass", DotNetDependenciesEntryExtractor.class.getName(), "-dtype", "internal", "-dcat", "compile",
            "-i", "test_files/scripts/*;;test_files/scripts/dotnet/*"});

        try {
            BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence(
                "test_files" + File.separator + "report1b.binary");
            persistence.load();
            fail("report1b.binary should never be created.");
        } catch (DependenciesEntryPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * See above test and we override it by using customized settings through switches and save the result into
     * report1c.binary. Result is in report1c.binary. It still proceeds because there is a "-ignore_errors" specified.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain6() throws Exception {
        // should handle test_files/scripts/build.xml only
        ComponentDependencyExtractorUtility.main(new String[] {"-f", "binary", "-o", "test_files/report1c.binary",
            "-eclass", DotNetDependenciesEntryExtractor.class.getName(), "-dtype", "internal", "-dcat", "compile",
            "-i", "test_files/scripts/*;test_files/scripts/dotnet/*", "-ignore_errors"});

        // let's load the file and see
        BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence("test_files"
            + File.separator + "report1c.binary");
        assertEquals("It should load 2 entries.", 2, persistence.load().size());
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * specify -h/-help/? to show help.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain7() throws Exception {
        // should handle test_files/scripts/build.xml only
        ComponentDependencyExtractorUtility.main(new String[] {"-help", "-?", "-h"});
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * -f and -pclass can not set in the same time. An exception will be logged.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain8() throws Exception {
        // should handle test_files/scripts/build.xml only
        ComponentDependencyExtractorUtility.main(new String[] {"-f", "xml", "-pclass",
            BinaryFileDependenciesEntryPersistence.class.getName(), "-o", "test_files/report1d.binary"});

        try {
            BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence(
                "test_files" + File.separator + "report1d.binary");
            persistence.load();
            fail("report1d.binary should never be created.");
        } catch (DependenciesEntryPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>main(String[])</code>.
     * </p>
     * <p>
     * -f values only be xml or binary. An exception will be logged.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testMain9() throws Exception {
        // should handle test_files/scripts/build.xml only
        ComponentDependencyExtractorUtility.main(new String[] {"-f", "not_good_type", "-o",
            "test_files/report1e.binary"});

        try {
            BinaryFileDependenciesEntryPersistence persistence = new BinaryFileDependenciesEntryPersistence(
                "test_files" + File.separator + "report1d.binary");
            persistence.load();
            fail("report1e.binary should never be created.");
        } catch (DependenciesEntryPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test to see if a private Ctor exists.
     * </p>
     *
     * @throws Exception to JUnit, indicates error
     */
    public void testPrivateCtor() throws Exception {
        try {
            Constructor privateCtor = Utils.class.getDeclaredConstructors()[0];
            assertTrue(Modifier.isPrivate(privateCtor.getModifiers()));
            privateCtor.setAccessible(true);
            privateCtor.newInstance(new Object[] {});
        } catch (SecurityException e) {
            System.out.println("Skip test private constructor due to security reason.");
        }
    }
}
