/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.stresstests;

import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;

import junit.framework.TestCase;

import java.io.File;

import java.util.ArrayList;
import java.util.List;


/**
 * Stress test for class <code>DefaultXmlDependenciesEntryPersistence</code>.
 *
 * @author PE
 * @version 1.0
 */
public class DefaultXmlDependenciesEntryPersistenceStressTest extends TestCase {
    /**
     * <p>
     * Represents the test file to be used in tests.
     * </p>
     */
    private static final String OUTPUT_FILE = "test_files/stress_test_files/persistence.xml";

    /**
     * <p>
     * Represents the persistence to be tested.
     * </p>
     */
    private DefaultXmlDependenciesEntryPersistence persistence;

    /**
     * Set up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        persistence = new DefaultXmlDependenciesEntryPersistence(OUTPUT_FILE);
    }

    /**
     * Clean up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        new File(OUTPUT_FILE).delete();
    }

    /**
     * Stress Test for the functionality of class <code>DefaultXmlDependenciesEntryPersistence</code>.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultXmlDependenciesEntryPersistence_SaveLoad()
        throws Exception {
        List<DependenciesEntry> entries = new ArrayList<DependenciesEntry>();

        int dependencyCount = 20;

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            Component targetComponent = new Component("name" + i, "version" + i,
                    ((i % 2) == 0) ? ComponentLanguage.JAVA : ComponentLanguage.DOT_NET);
            List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();

            for (int j = 0; j < dependencyCount; j++) {
                ComponentDependency dependency = new ComponentDependency("name" + j, "version" + j,
                        ((j % 2) == 0) ? ComponentLanguage.JAVA : ComponentLanguage.DOT_NET,
                        ((j % 2) == 0) ? DependencyCategory.COMPILE : DependencyCategory.TEST,
                        ((j % 2) == 0) ? DependencyType.EXTERNAL : DependencyType.INTERNAL, "path" + j);

                dependencies.add(dependency);
            }

            DependenciesEntry entry = new DependenciesEntry(targetComponent, dependencies);
            entries.add(entry);
        }

        StressTestHelper.start();

        persistence.save(entries);
        entries = persistence.load();

        StressTestHelper.printResult("testDefaultXmlDependenciesEntryPersistence_SaveLoad");

        // verify the response
        assertEquals("The result should be correct.", StressTestHelper.TIMES, entries.size());

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            assertEquals("The result should be correct.", dependencyCount, entries.get(i).getDependencies().size());
        }
    }
}
