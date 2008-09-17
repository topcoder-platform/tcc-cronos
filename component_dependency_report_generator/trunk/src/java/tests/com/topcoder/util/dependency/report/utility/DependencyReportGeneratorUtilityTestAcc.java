/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;
import com.topcoder.util.dependency.report.BaseTestCase;
import com.topcoder.util.dependency.report.impl.CsvDependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGenerator;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGenerator;


/**
 * <p>
 * Accuracy test for <code>DependencyReportGeneratorUtility</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependencyReportGeneratorUtilityTestAcc extends BaseTestCase {

    /**
     * <p>
     * Used to remember the previous <code>PrintStream</code>.
     * </p>
     */
    private PrintStream previousOut;

    /**
     * <p>
     * <code>ByteArrayOutputStream</code> used as standard output.
     * </p>
     */
    private ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

    /**
     * <p>
     * The new <code>PrintStream</code> used as standard output.
     * </p>
     */
    private PrintStream newOut = new PrintStream(byteStream);

    /**
     * <p>
     * Setup the test case.
     * </p>
     */
    @Override
    protected void setUp() {
        //Remember the previous System.out
        previousOut = System.out;

        //Set new out PrintStream
        System.setOut(newOut);

        //Reset the byte array stream
        byteStream.reset();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     */
    @Override
    protected void tearDown() {
        //Set back the previous out PrintStream
        System.setOut(previousOut);

        //Reset the byte array stream
        byteStream.reset();
    }

    /**
     * <p>
     * Given switches contain "h", help message should be shown.
     * </p>
     */
    public void testMain_Help_1() {
        DependencyReportGeneratorUtility.main(new String[]{"-h"});
        this.assertHelpMessage();
    }

    /**
     * <p>
     * Given switches contain "?", help message should be shown.
     * </p>
     */
    public void testMain_Help_2() {
        DependencyReportGeneratorUtility.main(new String[]{"-?"});
        this.assertHelpMessage();
    }

    /**
     * <p>
     * Given switches contain "help", help message should be shown.
     * </p>
     */
    public void testMain_Help_3() {
        DependencyReportGeneratorUtility.main(new String[]{"-help"});
        this.assertHelpMessage();
    }

    /**
     * <p>
     * Given config file does not exist, default "config.xml" should be used.
     * </p>
     */
    public void testMain_GivenConfigFileDoesNotExist_UseDefaultConfigFile() {
        DependencyReportGeneratorUtility.main(new String[]{"-c", "NoSuchFile"});

        this.assertMessage("Warning: The config file 'NoSuchFile' does not exist."
                + " Default config file 'config.xml' will be used.");

        //See /test_files/config.xml
        File file = new File("test_files/report.html");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-id" switch is present, only the given component should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_ForSpecificComponent() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-id", "dot_net-command_line_executor-1.0"});

        //See /test_files/config.xml
        File file = new File("test_files/report.html");
        if (file.exists()) {
            String report = this.readFile("test_files/report.html");
            assertTrue("'command_line_executor' component should be included in report",
                report.indexOf("command_line_executor") > -1);
            assertFalse("'logging_wrapper' component should not be included in report",
                report.indexOf("logging_wrapper") > -1);
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-id" switch is present, only the given components should be included in report.
     * </p>
     *
     * <p>
     * Note the component ids are separated by semicolon.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_ForSpecificComponents() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-id",
            "dot_net-command_line_executor-1.0;java-logging_wrapper-2.0.0"});

        //See /test_files/config.xml
        File file = new File("test_files/report.html");
        if (file.exists()) {
            String report = this.readFile("test_files/report.html");
            assertTrue("'command_line_executor' component should be included in report",
                report.indexOf("command_line_executor") > -1);
            assertTrue("'logging_wrapper' component should be included in report",
                report.indexOf("logging_wrapper") > -1);
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-indirect" switch is present, indirect dependencies should be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_IndirectDependenciesIncluded() throws Exception {
        //See /test_files/dependencies.xml
        //The xdsp_file_manager depends on command_line_executor
        //The command_line_executor depends on NUnit
        DependencyReportGeneratorUtility.main(new String[]{"-id", "dot_net-xdsp_file_manager-1.0", "-indirect"});

        //See /test_files/config.xml
        File file = new File("test_files/report.html");
        if (file.exists()) {
            String report = this.readFile("test_files/report.html");

            assertTrue("'xdsp_file_manager' component should be included in report",
                report.indexOf("xdsp_file_manager") > -1);

            assertTrue("'command_line_executor' component should be included in report",
                report.indexOf("command_line_executor") > -1);

            assertTrue("'NUnit' component should be included in report",
                report.indexOf("NUnit") > -1);

            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-noindirect" switch is present, indirect dependencies should not be included in report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_IndirectDependenciesNotIncluded() throws Exception {
        //See /test_files/dependencies.xml
        //The xdsp_file_manager depends on command_line_executor
        //The command_line_executor depends on NUnit
        DependencyReportGeneratorUtility.main(new String[]{"-id", "dot_net-xdsp_file_manager-1.0", "-noindirect"});

        //See /test_files/config.xml
        File file = new File("test_files/report.html");
        if (file.exists()) {
            String report = this.readFile("test_files/report.html");

            assertTrue("'xdsp_file_manager' component should be included in report",
                report.indexOf("xdsp_file_manager") > -1);

            assertTrue("'command_line_executor' component should be included in report",
                report.indexOf("command_line_executor") > -1);

            assertFalse("'NUnit' component should not be included in report",
                report.indexOf("NUnit") > -1);

            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-f xml" switch is present, XML report should be generated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_XML() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-f", "xml", "-o", "test_files/report.xml"});

        File file = new File("test_files/report.xml");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.xml should be generated");
        }
    }

    /**
     * <p>
     * The "-f csv" switch is present, CSV report should be generated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_CSV() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-f", "csv", "-o", "test_files/report.csv"});

        File file = new File("test_files/report.csv");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.csv should be generated");
        }
    }

    /**
     * <p>
     * The "-f html" switch is present, HTML report should be generated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_HTML() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-f", "html", "-o", "test_files/report.html"});

        File file = new File("test_files/report.html");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-dtype internal" switch is present, report should only contain internal dependencies.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_OnlyInternalDependencies() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-dtype", "internal"});

        File file = new File("test_files/report.html");
        if (file.exists()) {
            String report = this.readFile("test_files/report.html");
            assertFalse("NUnit is external and should be excluded", report.indexOf("NUnit") > -1);
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-dcat compile" switch is present, report should only contain compile dependencies.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_OnlyCompileDependencies() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-dcat", "compile"});

        File file = new File("test_files/report.html");
        if (file.exists()) {
            String report = this.readFile("test_files/report.html");
            assertFalse("NUnit is test dependencies and should be excluded", report.indexOf("NUnit") > -1);
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-pclass" switch is present, the given class should be used to load dependencies from persistence.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_SpecifyPersistenceClassExlicitly() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-pclass",
            DefaultXmlDependenciesEntryPersistence.class.getName()});

        File file = new File("test_files/report.html");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-gclass" switch is present, the given class should be used to generate report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_SpecifyGeneratorClassExlicitly_1() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-gclass",
            CsvDependencyReportGenerator.class.getName(), "-o", "test_files/report.csv"});

        File file = new File("test_files/report.csv");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.csv should be generated");
        }
    }

    /**
     * <p>
     * The "-gclass" switch is present, the given class should be used to generate report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_SpecifyGeneratorClassExlicitly_2() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-gclass",
            HtmlDependencyReportGenerator.class.getName(), "-o", "test_files/report.html"});

        File file = new File("test_files/report.html");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.html should be generated");
        }
    }

    /**
     * <p>
     * The "-gclass" switch is present, the given class should be used to generate report.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_SpecifyGeneratorClassExlicitly_3() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-gclass",
            XmlDependencyReportGenerator.class.getName(), "-o", "test_files/report.xml"});

        File file = new File("test_files/report.xml");
        if (file.exists()) {
            file.delete();
        } else {
            fail("report.xml should be generated");
        }
    }

    /**
     * <p>
     * The configuration is completely empty, the user must use the "-i" switch to specify input file for
     * <code>DefaultXmlDependenciesEntryPersistence</code>.
     * </p>
     *
     * <p>
     * In such case:
     *   <ul>
     *      <li><code>DefaultXmlDependenciesEntryPersistence</code> is used by default.</li>
     *      <li><code>XmlDependencyReportGenerator</code> is used by default.</li>
     *      <li>"internal;external" dependencies types are allowed by default.</li>
     *      <li>"compile;test" dependencies categories are allowed by default.</li>
     *      <li>Dependency type is included in report by default.</li>
     *      <li>Dependency category is included in report by default.</li>
     *      <li>Dependency path is not included in report by default.</li>
     *      <li>Indirect dependencies are included in report by default.</li>
     *      <li>All components are included in report by default.</li>
     *      <li>The generated report is printed to stdout by default.</li>
     *   </ul>
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     When the configuration is complete empty, it results same behavior as there is no existing
     *     config file specified and the default "config.xml" does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testMain_GenerateReport_EmptyConfiguration() throws Exception {
        DependencyReportGeneratorUtility.main(new String[]{"-c", "empty_config.xml", "-i", "test_files/dependencies.xml"});

        String report = this.byteStream.toString();

        assertTrue("XML report should be generated by default",
                report.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") > -1);

        assertTrue("'internal;external' dependencies types are allowed by default",
                report.indexOf("internal") > -1 && report.indexOf("external") > -1);

        assertTrue("'compile;test' dependencies categories are allowed by default",
                report.indexOf("compile") > -1 && report.indexOf("test") > -1);

        assertTrue("Dependency category is included in report by default",
                report.indexOf("category=\"compile\"") > -1 && report.indexOf("category=\"test\"") > -1);

        assertTrue("Dependency type is included in report by default",
                report.indexOf("type=\"internal\"") > -1 && report.indexOf("type=\"external\"") > -1);

        assertFalse("Dependency path is not included in report by default", report.indexOf("path=\"") > -1);

        assertTrue("logging_wrapper component should be included in report",
                report.indexOf("<component language=\"java\" name=\"logging_wrapper\" version=\"2.0.0\">") > -1);

        assertTrue("command_line_executor component should be included in report",
                report.indexOf("<component language=\"dot_net\" name=\"command_line_executor\" version=\"1.0\">") > -1);

        assertTrue("xdsp_file_manager component should be included in report",
                report.indexOf("<component language=\"dot_net\" name=\"xdsp_file_manager\" version=\"1.0\">") > -1);
    }

    /**
     * <p>
     * Assert the expected message is print.
     * </p>
     *
     * @param expected The expected message.
     */
    private void assertMessage(String expected) {
        String message = byteStream.toString();
        this.previousOut.println(message);
        assertTrue("Help message should be shown", message.indexOf(expected) > -1);
    }

    /**
     * <p>
     * Assert the help message is print.
     * </p>
     */
    private void assertHelpMessage() {
        this.assertMessage("Usage:");
    }
}
