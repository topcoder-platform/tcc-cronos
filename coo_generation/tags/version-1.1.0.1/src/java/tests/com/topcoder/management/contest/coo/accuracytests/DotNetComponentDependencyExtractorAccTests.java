/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.DependencyCategory;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.dependencyextractor.DotNetComponentDependencyExtractor;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Accuracy test case for {@link DotNetComponentDependencyExtractor}
 * class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class DotNetComponentDependencyExtractorAccTests extends TestCase {
    /** Target. */
    private DotNetComponentDependencyExtractor instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        instance = new DotNetComponentDependencyExtractor(new DefaultConfigurationObject(
                    "default"));
    }

    /**
     * <p>Accuracy test case for
     * <code>extractDependencies(InputStream)</code> method.</p>
     *
     * @throws Exception to JUNIT.
     */
    public void testExtractDependencies() throws Exception {
        InputStream input = new FileInputStream(BaseTestCase.ACCURACY
                + "Build.dependencies");

        try {
            List<ComponentDependency> list = instance.extractDependencies(input);

            assertEquals(6, list.size());
            assertEquals("log4net", list.get(0).getComponent().getName());

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

            assertEquals(a.size(), 2);
            assertEquals(b.size(), 1);
            assertEquals(c.size(), 1);
            assertEquals(d.size(), 2);

            for (ComponentDependency cd : list) {
                if (cd.getComponent().getName()
                          .equals("TopCoder.Util.ConfigurationManager")) {
//                    assertEquals(cd.getComponent().getName(), "");
                    assertNull(cd.getComponent().getVersion());
                }
            }
        } finally {
            input.close();
        }
    }
}
