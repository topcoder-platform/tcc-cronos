/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.utility;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.topcoder.util.dependency.report.BaseTestCase;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGenerator;


/**
 * <p>
 * Failure test for <code>DependencyReportGeneratorUtility</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependencyReportGeneratorUtilityTestExp extends BaseTestCase {

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
     * Given arguments array is null, error message should be print.
     * </p>
     */
    public void testMain_NullArray() {
        DependencyReportGeneratorUtility.main(null);
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * Given argument is not recognized, error message should be print.
     * </p>
     */
    public void testMain_InvalidArgument() {
        DependencyReportGeneratorUtility.main(new String[]{"XXX"});
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * Given switch is not recognized, error message should be print.
     * </p>
     */
    public void testMain_InvalidSwitch() {
        DependencyReportGeneratorUtility.main(new String[]{"-NoSuchSwitch"});
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * The "-indirect" switch should not have value, error message should be print.
     * </p>
     */
    public void testMain_InvalidSwitchValue_1() {
        DependencyReportGeneratorUtility.main(new String[]{"-indirect", "true"});
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * The "-dcat" switch should not have multiple values(semicolon should be used as separator),
     * error message should be print.
     * </p>
     */
    public void testMain_InvalidSwitchValue_2() {
        DependencyReportGeneratorUtility.main(new String[]{"-dcat", "compile", "test"});
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * The "-c" switch should not have empty value, error message should be print.
     * </p>
     */
    public void testMain_InvalidSwitchValue_3() {
        DependencyReportGeneratorUtility.main(new String[]{"-c", " "});
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * The "-f" switch does not have value either "xml", "csv" or "html"(case sensitive), error message should be print.
     * </p>
     */
    public void testMain_InvalidFormat() {
        DependencyReportGeneratorUtility.main(new String[]{"-f", "HtMl"});
        this.assertErrorMessage(null);
    }

    /**
     * <p>
     * The "-f" and "-gclass" are used together, error message should be print.
     * </p>
     */
    public void testMain_FormatAndGclassTogether() {
        DependencyReportGeneratorUtility.main(new String[]{
            "-f", "xml", "-gclass", XmlDependencyReportGenerator.class.getName()});
        this.assertErrorMessage("The switches [-f, -gclass] can not be used together.");
    }

    /**
     * <p>
     * The "-indirect" and "-noindirect" are used together, error message should be print.
     * </p>
     */
    public void testMain_IndirectAndNoindirectTogether() {
        DependencyReportGeneratorUtility.main(new String[]{"-indirect", "-noindirect"});
        this.assertErrorMessage("The switches [-indirect, -noindirect] can not be used together.");
    }

    /**
     * <p>
     * There is no output file specified(neither switch nor configuration). And the stdout is disallowed
     * in configuration. Error message should be print.
     * </p>
     */
    public void testMain_NoOutputFileAndNoStdout() {
        DependencyReportGeneratorUtility.main(new String[]{"-c", "another_config.xml"});
        this.assertErrorMessage("There is no output report file name specified/configured, and stdout is disallowed.");
    }

    /**
     * <p>
     * The user gives an empty componentID. Error message should be print.
     * </p>
     */
    public void testMain_EmptyComponentID() {
        DependencyReportGeneratorUtility.main(new String[]{"-id", "id1; ;id2"});
        this.assertErrorMessage("Component ID must not be empty(trimmed).");
    }

    /**
     * <p>
     * The user types "-id" switch, but gives no componentID. Error message should be print.
     * </p>
     */
    public void testMain_NoComponentID() {
        DependencyReportGeneratorUtility.main(new String[]{"-id", ";"});
        this.assertErrorMessage("'-id' switch is present, but there is no component ID specified.");
    }

    /**
     * <p>
     * The configuration is missing "com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtility"
     * namespace. Error message should be print.
     * </p>
     */
    public void testMain_InvalidConfig_MissingNamepsace() {
        DependencyReportGeneratorUtility.main(new String[]{"-c", "invalid_config.xml"});
        this.assertErrorMessage(
            "There is no 'com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtility'"
                + " namespace within file: invalid_config.xml");
    }

    /**
     * <p>
     * Assert the error message is print.
     * </p>
     *
     * @param errMsg The expected error message.
     */
    private void assertErrorMessage(String errMsg) {
        String message = byteStream.toString();
        this.previousOut.println(message);
        assertTrue("Error message should be print",
            message.indexOf("Failed to generate report:") > -1);
        if (errMsg != null) {
            assertTrue("Error message should be print",
                message.indexOf(errMsg) > -1);
        }
        assertTrue("Error message should be print",
            message.indexOf("Use -h, -help or -? to show help message") > -1);
    }
}
