/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.dependencyextractor;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.ComponentDependencyExtractorException;

import junit.framework.TestCase;

/**
 *
 * <p>
 * Unit test case of {@link DotNetComponentDependencyExtractor}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DotNetComponentDependencyExtractorTest extends TestCase {

    /**
     * instance created for testing.
     */
    private DotNetComponentDependencyExtractor instance;

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("default");
        instance = new DotNetComponentDependencyExtractor(config);
    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * accuracy test for constructor.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testCtor() throws Exception {
        assertNotNull("fail to create instance.", instance);
    }

    /**
     * <p>
     * accuracy test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies1() throws Exception {
        InputStream input = new FileInputStream("test_files/Build.dependencies");
        List<ComponentDependency> list = instance.extractDependencies(input);
        input.close();
        assertEquals("should be 6 dependencies", 6, list.size());
        assertEquals("should be log4net.", "log4net", list.get(0).getComponent().getName());
    }

    /**
     * <p>
     * accuracy test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies3() throws Exception {
        InputStream input = new FileInputStream("test_files/dotNet.xml");
        List<ComponentDependency> list = instance.extractDependencies(input);
        input.close();
        assertEquals("should be 7 dependencies", 7, list.size());
        assertEquals("should be log4net.", "log4net", list.get(0).getComponent().getName());
    }

    /**
     * <p>
     * accuracy test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies2() throws Exception {
        InputStream input = new FileInputStream("test_files/config.xml");
        List<ComponentDependency> list = instance.extractDependencies(input);
        input.close();
        assertEquals("should be 0 dependencies", 0, list.size());
    }

    /**
     * <p>
     * failure test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     * <p>
     * no element under root node.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies_fail_3() throws Exception {
        InputStream input = new FileInputStream("test_files/dotNet_invalid_2.xml");
        try {
            instance.extractDependencies(input);
            fail("ComponentDependencyExtractorException should throw");
        } catch (ComponentDependencyExtractorException e) {
            // expected
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * <p>
     * failure test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     * <p>
     * no "When" element under "Choose" node.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies_fail_4() throws Exception {
        InputStream input = new FileInputStream("test_files/dotNet_invalid_1.xml");
        try {
            instance.extractDependencies(input);
            fail("ComponentDependencyExtractorException should throw");
        } catch (ComponentDependencyExtractorException e) {
            // expected
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * <p>
     * failure test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     * <p>
     * stream can not be parsed.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies_fail_1() throws Exception {
        InputStream input = null;
        try {
            input = new ByteArrayInputStream(new byte[0]);
            instance.extractDependencies(input);
            fail("ComponentDependencyExtractorException should throw");
        } catch (ComponentDependencyExtractorException e) {
            // expected
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * <p>
     * failure test for <code>extractDependencies(InputStream)</code> method.
     * </p>
     * <p>
     * invalid .NET build file.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies_fail_2() throws Exception {
        InputStream input = null;
        try {
            input = new FileInputStream("test_files/third_party_list.xls");
            instance.extractDependencies(input);
            fail("ComponentDependencyExtractorException should throw");
        } catch (ComponentDependencyExtractorException e) {
            // expected
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

}
