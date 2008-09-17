/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.report.failuretests.FailureTestHelper;

/**
 * Failure tests for class HtmlDependencyReportGenerator.
 *
 * @author extra
 * @version 1.0
 */
public class HtmlDependencyReportGeneratorFailureTest extends TestCase {

    /**
     * The Html dependency report generator.
     */
    private HtmlDependencyReportGenerator generator;

    /**
     * The configuration object for test.
     */
    private ConfigurationObject configuration;

    /**
     * The dependencies entries for test.
     */
    private List<DependenciesEntry> entries;

    /**
     * The output stream.
     */
    private OutputStream os;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        configuration = new DefaultConfigurationObject("default");
        entries = FailureTestHelper.getDependenciesEntries();
        generator = new HtmlDependencyReportGenerator(entries, configuration);
        os = new ByteArrayOutputStream();
        super.setUp();
    }

    /**
     * Failure test for method writeReport(entries, os). If entries is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testWriteReport_Null_entries() throws Exception {
        entries = null;
        try {
            generator.writeReport(entries, os);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method writeReport(entries, os). If entries contains null element, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testWriteReport_NullElement_entries() throws Exception {
        entries.add(null);
        try {
            generator.writeReport(entries, os);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method writeReport(entries, os). If os is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testWriteReport_Null_os() throws Exception {
        os = null;
        try {
            generator.writeReport(entries, os);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
