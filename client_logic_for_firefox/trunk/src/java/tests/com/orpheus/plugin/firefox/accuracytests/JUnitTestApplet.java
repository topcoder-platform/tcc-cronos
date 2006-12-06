/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import java.applet.Applet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import junit.textui.TestRunner;

/**
 * Defines a Java applet which executes JUnit test cases within the applet. This is used to provide a real Javascript
 * context in the Mozilla Firefox browser.
 * 
 * @author visualage
 * @version 1.0
 */
public class JUnitTestApplet extends Applet {
    /** Represents the separator used to separate paths. */
    private static final String PATH_SEPARATOR = System.getProperty("path.separator");

    /** Represents the dependancies required by the test cases. */
    private static final String[] DEPENDANCY = new String[] {"../../../build/testClasses", "../../../build/classes",
        "../../../conf", "../../../test_files", "base_exception.jar", "configuration_manager.jar", "bloom_filter.jar",
        "hashing_utility.jar", "typesafe_enum.jar", "rss_generator.jar"};

    /** Represents the current running applet. */
    private static Applet currentApplet;

    /**
     * Gets the current running applet. If there is no running applet, return <code>null</code>.
     * 
     * @return the current running applet.
     */
    public static Applet getCurrentApplet() {
        return currentApplet;
    }

    /**
     * Starts the test runner using the console test runner.
     * 
     * @throws IllegalArgumentException if the code base is not a file.
     */
    public void start() {
        currentApplet = this;

        final File base;

        try {
            // Retrieve the code base
            base = new File(getCodeBase().toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Code base is not a file.", e);
        }

        // Reset the class paths.
        StringBuffer classPath = new StringBuffer(".");

        for (int i = 0; i < DEPENDANCY.length; ++i) {
            classPath.append(PATH_SEPARATOR);
            classPath.append(DEPENDANCY[i]);
        }

        System.setProperty("java.class.path", classPath.toString());

        PrintStream ps = null;

        try {
            ps = new PrintStream(new FileOutputStream(new File(base, "../../../log/accuracy.log")));
            TestRunner runner = new TestRunner(ps);

            runner.doRun(runner.getTest("com.orpheus.plugin.firefox.accuracytests.AccuracyAppletTests"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ps.close();
        }
    }
}
