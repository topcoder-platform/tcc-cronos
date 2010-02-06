/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.DependencyCategory;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;


/**
 * <p>Accuracy test case for {@link DefaultCOOReportGenerator} class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class DefaultCOOReportGeneratorAccTests extends BaseTestCase {
    /** Target. */
    private DefaultCOOReportGenerator instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationObject config = BaseTestCase
            .getConfigurationObject(BaseTestCase.ACCURACY + "Config.xml");

        instance = new DefaultCOOReportGenerator(config);
    }

    /**
     * <p>tear down test environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>accuracy test for <code>generateCOOReport()</code> method.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateCOOReport() throws Exception {
        COOReport report = instance.generateCOOReport(13);
        assertEquals(13, report.getProjectId());
        assertEquals("HBS Singleton Process Manager 1.0.0",
            report.getContestData().getComponentName());
        List<ComponentDependency> list = report.getComponentDependencies();
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
            if (cd.getComponent().getName().equals("junit")) {
                assertEquals(cd.getComponent().getUrl(), "www.junit.org");
                assertEquals(cd.getComponent().getDescription(), "Unit testing");
                assertEquals(cd.getComponent().getLicense(), "CPL");
            }
        }
    }
}
