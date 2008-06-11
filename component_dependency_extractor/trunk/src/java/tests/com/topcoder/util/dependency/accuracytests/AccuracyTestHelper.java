/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test helper class.
 * </p>
 * @author telly12
 * @version 1.0
 */
class AccuracyTestHelper extends TestCase {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    private AccuracyTestHelper() {
        // do nothing
    }

    /**
     * Get entries from the zip file.
     * @param zipFilePath the zip file path
     * @return the entries
     * @throws Exception to test case
     */
    public static List<String> getEntries(String zipFilePath) throws Exception {
        ZipFile zipFile = new ZipFile(zipFilePath);
        try {
            List<String> entries = new ArrayList<String>();
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                if (!entry.isDirectory()) {
                    entries.add(entry.getName());
                }
            }

            return entries;
        } finally {
            zipFile.close();
        }
    }

    /**
     * Validate the input stream with the expected file.
     * @param expectedFile the expected file
     * @param actualIS the actual input stream
     * @throws Exception to test case
     */
    public static void validateIS(String expectedFile, InputStream actualIS) throws Exception {
        assertNotNull("The result is should not be null.", actualIS);
        try {
            byte[] buff = new byte[1024 * 1024];
            int count = actualIS.read(buff);

            File file = new File(expectedFile);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(expectedFile);
                try {
                    int expectedCount = fis.read(buff);
                    assertEquals("not correct result, should be:" + expectedFile, expectedCount, count);
                } finally {
                    fis.close();
                }
            } else {
                fail("wrong expectedFile:" + expectedFile);
            }
        } finally {
            actualIS.close();
        }
    }

    /**
     * <p>
     * Gets the entries.
     * </p>
     *
     * @return the entries for test.
     */
    public static List<DependenciesEntry> getEntries() {
        List<DependenciesEntry> entries = new ArrayList<DependenciesEntry>();

        Component loggingwrapper = new Component("logging_wrapper", "2.0.0", ComponentLanguage.JAVA);
        List<ComponentDependency> loggingwrapperdependencies = new ArrayList<ComponentDependency>();
        loggingwrapperdependencies.add(new ComponentDependency("base_exception", "2.0.0", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.INTERNAL,
                "../tcs/lib/tcs/base_exception/2.0.0/base_exception.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("typesafe_enum", "1.1.0", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.INTERNAL,
                "../tcs/lib/tcs/typesafe_enum/1.1.0/typesafe_enum.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("object_formatter", "1.0.0", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.INTERNAL,
                "../tcs/lib/tcs/object_formatter/1.0.0/object_formatter.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("log4j", "1.2.14", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.EXTERNAL,
                "../tcs/lib/third_party/log4j/1.2.14/log4j-1.2.14.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("junit", "3.8.2", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.EXTERNAL, "../tcs/lib/third_party/junit/3.8.2/junit.jar"));

        loggingwrapperdependencies.add(new ComponentDependency("base_exception", "2.0.0", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.INTERNAL,
                "../tcs/lib/tcs/base_exception/2.0.0/base_exception.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("typesafe_enum", "1.1.0", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.INTERNAL,
                "../tcs/lib/tcs/typesafe_enum/1.1.0/typesafe_enum.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("object_formatter", "1.0.0", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.INTERNAL,
                "../tcs/lib/tcs/object_formatter/1.0.0/object_formatter.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("log4j", "1.2.14", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.EXTERNAL,
                "../tcs/lib/third_party/log4j/1.2.14/log4j-1.2.14.jar"));
        loggingwrapperdependencies.add(new ComponentDependency("junit", "3.8.2", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.EXTERNAL, "../tcs/lib/third_party/junit/3.8.2/junit.jar"));

        entries.add(new DependenciesEntry(loggingwrapper, loggingwrapperdependencies));

        Component commandlineexecutor = new Component("command_line_executor", "1.0", ComponentLanguage.DOT_NET);
        List<ComponentDependency> commandlineexecutordependencies = new ArrayList<ComponentDependency>();
        commandlineexecutordependencies
                .add(new ComponentDependency("NUnit", "2.1", ComponentLanguage.DOT_NET, DependencyCategory.TEST,
                        DependencyType.EXTERNAL, "C:/Program Files/NUnit/2.1/bin/nunit.framework.dll"));

        entries.add(new DependenciesEntry(commandlineexecutor, commandlineexecutordependencies));

        return entries;
    }

    /**
     * <p>
     * Validate dependencies entry list.
     * </p>
     *
     * @param expected
     *            the expected value
     * @param actual
     *            the actual value
     */
    public static void validDependenciesEntry(List<DependenciesEntry> expected, List<DependenciesEntry> actual) {
        assertEquals("entries size", expected.size(), actual.size());

        for (DependenciesEntry expectedEntry : expected) {
            boolean containsComponent = false;
            for (DependenciesEntry actualEntry : actual) {
                if (expectedEntry.getTargetComponent().getName().equals(actualEntry.getTargetComponent().getName())
                        && expectedEntry.getTargetComponent().getVersion().equals(
                                actualEntry.getTargetComponent().getVersion())
                        && expectedEntry.getTargetComponent().getLanguage().equals(
                                actualEntry.getTargetComponent().getLanguage())) {
                    containsComponent = true;
                    for (ComponentDependency expectedDependency : expectedEntry.getDependencies()) {
                        boolean contains = false;
                        for (ComponentDependency actualDependency : actualEntry.getDependencies()) {
                            if (expectedDependency.getName().equals(actualDependency.getName())
                                    && expectedDependency.getVersion().equals(actualDependency.getVersion())
                                    && expectedDependency.getLanguage().equals(actualDependency.getLanguage())
                                    && expectedDependency.getType().equals(actualDependency.getType())
                                    && expectedDependency.getPath().equals(actualDependency.getPath())
                                    && expectedDependency.getCategory().equals(actualDependency.getCategory())) {
                                contains = true;
                                break;
                            }
                        }
                        assertTrue(expectedDependency.getName() + " should exist.", contains);
                    }
                    break;
                }
            }
            assertTrue(expectedEntry.getTargetComponent().getName() + " should exist.", containsComponent);
        }
    }

}
