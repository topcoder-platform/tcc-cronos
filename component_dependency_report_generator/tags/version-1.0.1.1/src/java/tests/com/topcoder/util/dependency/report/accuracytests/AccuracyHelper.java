/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;

/**
 * <p>
 * This class is the helper class for accuracy tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class AccuracyHelper extends TestCase {
    /**
     * Represents the byte array output stream used for accuracy test.
     */
    public static ByteArrayOutputStream outputstream = new ByteArrayOutputStream();

    /**
     * Represents the byte array output stream used for accuracy test.
     */
    private static final String CONFIG_FILE = "test_files/accuracytests/BaseGenerator.properties";

    /**
     * Private constructor.
     */
    private AccuracyHelper() {
    }

    /**
     * Retrieves the java component of the given name and version.
     *
     * @param name component name
     * @param version component version
     * @return java <code>ComponentDependency</code> instance
     */
    public static ComponentDependency getJavaComponent(String name, String version) {
        return new ComponentDependency(name, version, ComponentLanguage.JAVA, DependencyCategory.COMPILE,
            DependencyType.INTERNAL, "path_for_" + name);
    }

    /**
     * Retrieves the .dot component dependency of the given name and version.
     *
     * @param name component name
     * @param version component version
     * @return java <code>ComponentDependency</code> instance
     */
    public static ComponentDependency getDotNetComponent(String name, String version) {
        return new ComponentDependency(name, version, ComponentLanguage.DOT_NET, DependencyCategory.COMPILE,
            DependencyType.INTERNAL, "path_for_" + name);
    }

    /**
     * Retrieves the private field of the given class object.
     *
     * @param clazz Class to get declared field
     * @param object instance to get field from
     * @param fieldName name of field
     * @return value of field
     * @throws Exception to JUnit
     */
    public static Object getField(Class<?> clazz, Object object, String fieldName) throws Exception {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);

        return f.get(object);
    }

    /**
     * Creates a common entry list for accuracy test.
     *
     * @return entry list
     */
    public static List<DependenciesEntry> createTestEntries() {
        return initEntries(getEntry(getJavaComponent("A", "1.0"), getJavaComponent("B", "1.0"), getJavaComponent(
            "C", "1.0")), getEntry(getJavaComponent("C", "1.0"), getJavaComponent("D", "1.0")), getEntry(
            getJavaComponent("D", "1.0"), getJavaComponent("B", "1.0")), getEntry(getDotNetComponent("X", "1.0"),
            getDotNetComponent("Y", "1.0"), getDotNetComponent("Z", "1.0")));
    }

    /**
     * Checks whether the given generator has the correct size.
     *
     * @param countOfDependencies count of dependencies
     * @param instance generator instance
     * @throws Exception to JUnit, indicates an error
     */
    @SuppressWarnings("unchecked")
    public static void assertEquals(int countOfDependencies, BaseDependencyReportGenerator instance)
        throws Exception {
        Map<String, DependenciesEntry> dependencies = (Map<String, DependenciesEntry>) AccuracyHelper.getField(
            BaseDependencyReportGenerator.class, instance, "dependencies");
        assertEquals("The number of dependencies is not " + countOfDependencies, countOfDependencies, dependencies
            .size());
    }

    /**
     * Retrieves the configuration for the accuracy test.
     *
     * @param configName configuration name
     * @return configuration object
     * @throws Exception to JUnit, indicates an error
     */
    public static ConfigurationObject getConfig(String configName) throws Exception {
        return getTestNamespace().getChild(configName);
    }

    /**
     * Retrieves the namespace for the accuracy test.
     *
     * @return test configuration object
     * @throws Exception to JUnit, indicates an error
     */
    public static ConfigurationObject getTestNamespace() throws Exception {
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(CONFIG_FILE);
        return configurationFileManager.getConfiguration("BaseGenerator");
    }

    /**
     * Creates the entries with given dependency components.
     *
     * @param dependenciesEntries an array of dependeciesEntry
     * @return a list of entries
     */
    public static List<DependenciesEntry> initEntries(DependenciesEntry... dependenciesEntries) {
        return Arrays.asList(dependenciesEntries);
    }

    /**
     * Retrieves the entry with the given component and its dependencies.
     *
     * @param component component
     * @param componentDependencies dependency list
     * @return <code>DependenciesEntry</code> entry
     */
    public static DependenciesEntry getEntry(Component component, ComponentDependency... componentDependencies) {
        return new DependenciesEntry(component, Arrays.asList(componentDependencies));
    }

    /**
     * Returns all the component ids.
     *
     * @return all ids in test entries
     */
    public static List<String> getAllComponentIds() {
        return new ArrayList<String>(Arrays.asList(new String[]{"java-A-1.0", "java-C-1.0", "java-D-1.0",
            "dot_net-X-1.0" }));
    }
}
