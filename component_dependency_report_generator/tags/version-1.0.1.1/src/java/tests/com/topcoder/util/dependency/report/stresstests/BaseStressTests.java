/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.stresstests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.report.DependencyReportGenerator;

/**
 * Base class for stress test cases.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseStressTests extends TestCase {
    /**
     * Low load.
     */
    private static int LOW_LOAD = 10;

    /**
     * High load.
     */
    private static int HIGH_LOAD = 100;

    /**
     * Represents the DependenciesEntry set(small size).
     */
    private List<DependenciesEntry> smallDependenciesEntries;

    /**
     * Represents the DependenciesEntry set(large size).
     */
    private List<DependenciesEntry> largeDependenciesEntries;

    /**
     * Represents the DependenciesEntry set(deep dependency).
     */
    private List<DependenciesEntry> deepDependenciesEntries;

    /**
     * Represents the config object for generator.
     */
    private ConfigurationObject generatorConfig;

    /**
     * Represents the start time of the test.
     */
    private long startTime = 0;

    /**
     * Represents the stop time of the test.
     */
    private long stopTime = 0;

    /**
     * Represents whether the test is running or not.
     */
    private boolean running = false;

    /**
     * Start the stop watch.
     * 
     * @throws IllegalStateException
     *             if the test is already running.
     */
    protected void start() {
        if (running) {
            throw new IllegalStateException(
                    "The test is running, stop it first.");
        } else {
            running = true;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * Stop the stop watch.
     * 
     * @throws IllegalStateException
     *             if the test is not running.
     */
    protected void stop() {
        if (!running) {
            throw new IllegalStateException(
                    "The test is not running, start it first.");
        } else {
            running = false;
            stopTime = System.currentTimeMillis();
        }
    }

    /**
     * Get the test duration.
     * 
     * @return the test duration in milliseconds.
     */
    protected long getMilliseconds() {
        return stopTime - startTime;
    }

    /**
     * <p>
     * 
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {

    }

    /**
     * <p>
     * Prepare test data.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        // create DependenciesEntries
        smallDependenciesEntries = new ArrayList<DependenciesEntry>();
        largeDependenciesEntries = new ArrayList<DependenciesEntry>();
        deepDependenciesEntries = new ArrayList<DependenciesEntry>();
        createDependencyEntries(smallDependenciesEntries, 3);
        createDependencyEntries(largeDependenciesEntries, 1000);
        // create deepDependencyEntries
        createDeepDependencyEntries(100);

        generatorConfig = new DefaultConfigurationObject("stress");
        // drive generator to generate everything
        generatorConfig.setPropertyValue("include_dependency_path", "true");
    }

    /**
     * Create DependencyEntry set for deep dependency.
     * 
     * @param depth
     *            the dependency depth
     */
    private void createDeepDependencyEntries(int depth) {
        for (int i = 1; i <= depth; i++) {
            Component component = new Component("cmp" + i, "1.0.0",
                    ComponentLanguage.JAVA);
            List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
            // create ComponentDependency
            dependencies
                    .add(new ComponentDependency("cmp" + (i + 1),
                            "1.0.0", ComponentLanguage.JAVA,
                            DependencyCategory.COMPILE,
                            DependencyType.INTERNAL, "path"+(i+1)));
            DependenciesEntry entry = new DependenciesEntry(component,
                    dependencies);
            deepDependenciesEntries.add(entry);
        }
    }

    /**
     * Create <code>number</code> DependenciesEntry and add them to given
     * entries.
     * 
     * @param entries
     *            the entries list to fill.
     * @param number
     *            the number of DependenciesEntry to create.
     */
    private void createDependencyEntries(List<DependenciesEntry> entries,
            int number) {
        for (int i = 1; i <= number; i++) {
            Component component = new Component("cmp" + i, "1.0.0",
                    ComponentLanguage.JAVA);
            List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
            // create ComponentDependency
            for (int j = 1; j <= 5; j++) {
                dependencies.add(new ComponentDependency(i + "_depend_" + j,
                        "1.0.0", component.getLanguage(),
                        DependencyCategory.COMPILE, DependencyType.INTERNAL,
                        "path"));
            }
            DependenciesEntry entry = new DependenciesEntry(component,
                    dependencies);
            entries.add(entry);
        }
    }

    /**
     * This method should be override by subclass to identify which kind of
     * generator should be tested.
     * 
     * @return the class type of target generator
     */
    abstract protected Class<? extends DependencyReportGenerator> getGeneratorClass();

    /**
     * This method return an instance of DependencyReportGenerator to test.
     * 
     * @param entries
     *            the entries used to generate report
     * @return an instance of DependencyReportGenerator to test
     * @throws Exception
     *             to JUnit
     */
    private DependencyReportGenerator getGenerator(
            List<DependenciesEntry> entries) throws Exception {
        Class<? extends DependencyReportGenerator> clazz = getGeneratorClass();
        return clazz.getConstructor(List.class, ConfigurationObject.class)
                .newInstance(entries, generatorConfig);

    }

    /**
     * Run generateAll for low load.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_LowLoad() throws Exception {
        DependencyReportGenerator generator = getGenerator(smallDependenciesEntries);
        start();
        for (int i = 0; i < LOW_LOAD; i++) {
            generator.generateAll(true);
        }
        stop();
        System.out.println("Run method generateAll(boolean) for " + LOW_LOAD
                + " times consumes " + getMilliseconds() + " milliseconds.");
    }

    /**
     * Run generateAll for high load.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_HighLoad() throws Exception {
        DependencyReportGenerator generator = getGenerator(smallDependenciesEntries);
        start();
        for (int i = 0; i < HIGH_LOAD; i++) {
            generator.generateAll(true);
        }
        stop();
        System.out.println("Run method generateAll(boolean) for " + HIGH_LOAD
                + " times consumes " + getMilliseconds() + " milliseconds.");
    }

    /**
     * Generate report of a large number of DependenciesEntries.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_LargeEntries() throws Exception {
        DependencyReportGenerator generator = getGenerator(largeDependenciesEntries);
        start();
        for (int i = 0; i < LOW_LOAD; i++) {
            generator.generateAll(true);
        }
        stop();
        System.out.println("Run method generateAll(boolean) for " + LOW_LOAD
                + " times consumes " + getMilliseconds()
                + " milliseconds.(reprot contains "
                + largeDependenciesEntries.size() + " components)");
    }

    /**
     * Generate report of a deep dependency component.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGenerate_DeepEntries() throws Exception {
        DependencyReportGenerator generator = getGenerator(deepDependenciesEntries);
        start();
        generator.generate(Arrays.asList("java-cmp1-1.0.0"), true);
        stop();
        System.out.println("Run method generate(String, boolean) for " + LOW_LOAD
                + " times consumes " + getMilliseconds()
                + " milliseconds.(component has dependency depth of "
                + deepDependenciesEntries.size() + ")");
    }
}
