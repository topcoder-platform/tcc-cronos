/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.util.dependency.extractor.utility.ComponentDependencyExtractorUtility;

/**
 * <p>
 * Accuracy tests for class ComponentDependencyExtractorUtility.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentDependencyExtractorUtilityAccuracyTest extends TestCase {

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file: test_files/accuracytests/all_dependencies_result_default.xml.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_Default() throws Exception {
        String[] args = new String[] {
            "-nolog", "-o", "test_files/accuracytests/all_dependencies_result_default.xml",
            "-i", "test_files/accuracytests/BuildScript/java/*/*/build.xml;"
                        + "test_files/accuracytests/BuildScript/dotnet/*/*/default.build" };
        ComponentDependencyExtractorUtility.main(args);
        assertTrue("test_files/accuracytests/all_dependencies_result_default.xml should exist", new File(
                "test_files/accuracytests/all_dependencies_result_default.xml").exists());
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file: test_files/accuracytests/java_dependencies_result_all_java_internal.xml.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_NoConfig_Java() throws Exception {
        String[] args = new String[] {"-o", "test_files/accuracytests/java_dependencies_result_all_java_internal.xml",
            "-pclass", "com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence",
            "-eclass", "com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractor", "-dtype",
            "internal", "-dcat", "test", "-i", "test_files/accuracytests/BuildScript/java/*/*/build.xml", "-nolog" };
        ComponentDependencyExtractorUtility.main(args);
        assertTrue("test_files/accuracytests/java_dependencies_result_all_java_internal.xml should exist", new File(
                "test_files/accuracytests/java_dependencies_result_all_java_internal.xml").exists());
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file: test_files/accuracytests/java_dependencies_result_all_dotnet_external.xml.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_NoConfig_DotNet() throws Exception {
        String[] args = new String[] {"-o",
            "test_files/accuracytests/java_dependencies_result_all_dotnet_external.xml", "-pclass",
            "com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence", "-eclass",
            "com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractor", "-dtype", "external",
            "-dcat", "test", "-i", "test_files/accuracytests/BuildScript/dotnet/*/*/default.build", "-nolog" };
        ComponentDependencyExtractorUtility.main(args);
        assertTrue("test_files/accuracytests/java_dependencies_result_all_dotnet_external.xml should exist", new File(
                "test_files/accuracytests/java_dependencies_result_all_dotnet_external.xml").exists());
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file in directory: test_files/accuracytests/.
     * </p>
     */
    public void testMain_Java() {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_java.xml", "-nolog" });
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file in directory: test_files/accuracytests/.
     * </p>
     */
    public void testMain_DotNet() {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_dotnet.xml", "-nolog" });
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file in directory: test_files/accuracytests/.
     * </p>
     */
    public void testMain_MultipleFormat() {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_multipleformat.xml",
            "-nolog" });
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check whether the help message is printed.
     * </p>
     */
    public void testMain_Help1() {
        ComponentDependencyExtractorUtility.main(new String[] {"-h" });
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check whether the help message is printed.
     * </p>
     */
    public void testMain_Help2() {
        ComponentDependencyExtractorUtility.main(new String[] {"-?" });
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check whether the help message is printed.
     * </p>
     */
    public void testMain_Help3() {
        ComponentDependencyExtractorUtility.main(new String[] {"-help" });
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file in directory: test_files/accuracytests/.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_OutPut() throws Exception {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_java_simple.xml", "-o",
            "test_files/accuracytests/java_dependencies_result_simple_valid.xml" });
        if (new File("test_files/accuracytests/java_dependencies_result_simple_invalid.xml").getCanonicalFile()
                .exists()) {
            fail("test_files/accuracytests/java_dependencies_result_simple_invalid.xml should not exist.");
        }
        if (!new File("test_files/accuracytests/java_dependencies_result_simple_valid.xml")
            .getCanonicalFile().exists()) {
            fail("test_files/accuracytests/java_dependencies_result_simple_valid.xml should exist.");
        }
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file: test_files/accuracytests/java_dependencies_result_simple_pclass.data.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_PClass() throws Exception {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_java_simple.xml", "-o",
            "test_files/accuracytests/java_dependencies_result_simple_pclass.data", "-pclass",
            "com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence" });
        assertTrue("test_files/accuracytests/java_dependencies_result_simple_pclass.data should exist", new File(
                "test_files/accuracytests/java_dependencies_result_simple_pclass.data").exists());
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file: test_files/accuracytests/java_dependencies_result_simple_valid.data, should not be xml
     * file.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_F1() throws Exception {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_java_simple_binary.xml",
            "-f", "binary" });
        assertTrue("test_files/accuracytests/java_dependencies_result_simple_binary.data should exist", new File(
                "test_files/accuracytests/java_dependencies_result_simple_binary.data").exists());
    }

    /**
     * <p>
     * Test method main().
     * </p>
     * <p>
     * Check the output file: test_files/accuracytests/java_dependencies_result_simple_valid.data, should not be xml
     * file.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testMain_F2() throws Exception {
        ComponentDependencyExtractorUtility.main(new String[] {"-c", "accuracytests/config_java_simple_xml.xml", "-f",
            "xml" });
        assertTrue("test_files/accuracytests/java_dependencies_result_simple_xml.xml should exist", new File(
                "test_files/accuracytests/java_dependencies_result_simple_xml.xml").exists());
    }

}
