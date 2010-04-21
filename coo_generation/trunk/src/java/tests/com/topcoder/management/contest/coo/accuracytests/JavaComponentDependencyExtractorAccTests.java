/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.DependencyCategory;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.dependencyextractor.JavaComponentDependencyExtractor;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Accuracy test case for {@link JavaComponentDependencyExtractor}
 * class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class JavaComponentDependencyExtractorAccTests extends TestCase {
    /** Target. */
    private JavaComponentDependencyExtractor instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        instance = new JavaComponentDependencyExtractor(new DefaultConfigurationObject(
                    "default"));
    }

    /**
     * <p>Accuracy test case for
     * <code>extractDependencies(InputStream)</code> method.</p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependenciesAccuracy() throws Exception {
        InputStream input = new FileInputStream(BaseTestCase.ACCURACY
                + "build-dependencies.xml");

        try {
            List<ComponentDependency> list = instance.extractDependencies(input);
            assertEquals(41, list.size());
            assertEquals("logging_wrapper", list.get(0).getComponent().getName());
            assertEquals("1.2", list.get(0).getComponent().getVersion());
            assertEquals("command_line_utility",
                list.get(40).getComponent().getName());

            List<ComponentDependency> a = new ArrayList<ComponentDependency>();
            List<ComponentDependency> b = new ArrayList<ComponentDependency>();
            List<ComponentDependency> c = new ArrayList<ComponentDependency>();
            List<ComponentDependency> d = new ArrayList<ComponentDependency>();

            for (ComponentDependency cd : list) {
                if ((cd.getCategory() == DependencyCategory.TEST)
                        && (cd.getType() == DependencyType.INTERNAL)) {
                    a.add(cd);
                } else if ((cd.getCategory() == DependencyCategory.TEST)
                        && (cd.getType() == DependencyType.EXTERNAL)) {
                    b.add(cd);
                } else if ((cd.getCategory() == DependencyCategory.COMPILE)
                        && (cd.getType() == DependencyType.EXTERNAL)) {
                    c.add(cd);
                } else if ((cd.getCategory() == DependencyCategory.COMPILE)
                        && (cd.getType() == DependencyType.INTERNAL)) {
                    d.add(cd);
                }
            }

            assertEquals(a.size(), 8);
            assertEquals(b.size(), 3);
            assertEquals(c.size(), 2);
            assertEquals(d.size(), 28);

            for (ComponentDependency cd : list) {
                if (cd.getComponent().getName().equals("dummy")
                        || cd.getComponent().getName().equals("ifxjdbc")) {
                    assertNull(cd.getComponent().getVersion());
                }
            }
        } finally {
            input.close();
        }
    }
}
