/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import junit.framework.TestCase;

import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;
import com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtility;

/**
 * <p>
 * This class is the accuracy tests for <code>DependencyReportGeneratorUtility</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependencyReportGeneratorUtilityAccuracyTest extends TestCase {

    /**
     * Accuracy test for <code>main(String[])</code> for the default config.xml file.
     */
    public void testMainAccuracy1() {
        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config.xml", "-o",
                "test_files/report1.xml" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code> method with config2.xml config file.
     */
    public void testMainAccuracy2() {
        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config2.xml" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code> method with depnedenciesInput.xml config file.
     */
    public void testMainAccuracy3() {
        try {
            DependencyReportGeneratorUtility.main(new String[]{"-i", "test_files/dependenciesInput.xml", "-o",
                "test_files/report3.xml" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code>.
     */
    public void testMainAccuracy4() {
        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config.xml", "-pclass",
                BinaryFileDependenciesEntryPersistence.class.getName(), "-i", "test_files/dependenciesInput.dat",
                "-o", "test_files/report4.html", "-f", "html", "-dtype", "internal", "-dcat", "compile", "-id",
                "java-location_service-1.0;dot_net-authentication-1.0", "-indirect" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code> for help information.
     */
    public void testMainAccuracy5() {
        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config.xml", "-h", "-help", "-?",
                "-o", "test_files/report5.xml" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code> for case insensitive.
     */
    public void testMainAccuracy6() {

        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config.xml", "-pclass",
                BinaryFileDependenciesEntryPersistence.class.getName(), "-i", "test_files/dependenciesInput.dat",
                "-o", "test_files/report10.html", "-f", "html", "-dtype", "internal;ExternaL", "-dcat",
                "compilE;teSt", "-id", "java-location_service-1.0;dot_net-authentication-1.0", "-indirect" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code> for no output file.
     */
    public void testMainAccuracy7() {

        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config3.xml" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for <code>main(String[])</code> with CSV format.
     * </p>
     */
    public void testMainAccuracy8() {
        try {
            DependencyReportGeneratorUtility.main(new String[]{"-c", "test_files/config.xml", "-f", "csv", "-o",
                "test_files/report18.dat" });
        } catch (Exception e) {
            fail("No error should occur!");
        }
    }
}
