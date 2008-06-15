/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractor;
import com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractor;
import com.topcoder.util.dependency.extractor.MultipleFormatDependenciesEntryExtractor;
import com.topcoder.util.dependency.extractor.utility.ComponentDependencyExtractorUtility;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistence;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;

/**
 * <p>
 * Demo for Component Dependency Extractor Component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * demo folder which holds files for demo.
     * </p>
     */
    private static final String DEMO_DIR = "test_files" + File.separator + "demo";

    /**
     * <p>
     * Gets the test suite for <code>Demo</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>Demo</code> class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * This is an accuracy demo which will scan through under test_files/BuildScript/* of more than 1,000 files. To
     * avoid unnecessary long logs, -nolog has been enabled. You can see the result at test_files/accuracy_demo.xml.
     * </p>
     * <p>
     * You could see some details at : http://forums.topcoder.com/?module=Thread&threadID=615395&start=0 and
     * http://forums.topcoder.com/?module=Announcement&annID=871
     * </p>
     */
    public void testAccuracyDemo() {
        ComponentDependencyExtractorUtility.main(new String[] {"-i",
            "test_files/BuildScript/dotnet/*/*/default.build;test_files/BuildScript/java/*/*/build.xml", "-o",
            "test_files/accuracy_demo.xml", "-nolog"});
        // you should see in the log as:
        // Info: It extracts and saves dependencies information for 1365 components successfully.
    }

    /**
     * <p>
     * Demo to display how to use the API.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testDemoAPI() throws Exception {
        // Create dependency entry manually
        // Component A depends on component B
        Component componentA = new Component("A", "1.0", ComponentLanguage.JAVA);
        ComponentDependency componentB = new ComponentDependency("B", "1.0", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "b.jar");
        List<ComponentDependency> dependencyList = new ArrayList<ComponentDependency>();
        dependencyList.add(componentB);
        DependenciesEntry entry = new DependenciesEntry(componentA, dependencyList);

        List<DependenciesEntry> entryList = new ArrayList<DependenciesEntry>();
        entryList.add(entry);

        // Save dependencies to XML file
        DependenciesEntryPersistence persistence = new DefaultXmlDependenciesEntryPersistence(DEMO_DIR + File.separator
            + "dependencies.xml");
        persistence.save(entryList);

        // Load dependencies from binary file
        persistence = new BinaryFileDependenciesEntryPersistence(DEMO_DIR + File.separator + "dependencies.dat");
        persistence.save(entryList);
        entryList = persistence.load();

        // Extract compile dependencies for Java component
        BaseDependenciesEntryExtractor extractor = new JavaDependenciesEntryExtractor(null);
        Set<DependencyCategory> categories = new HashSet<DependencyCategory>();
        categories.add(DependencyCategory.COMPILE);
        extractor.setExtractedCategories(categories);
        // Extract from build.xml
        entry = extractor.extract(DEMO_DIR + File.separator + "file_upload/build.xml");
        // Extract from distribution file
        entry = extractor.extract(DEMO_DIR + File.separator + "file_upload-2.2.0.jar");

        // Extract external component dependencies for .NET component
        extractor = new DotNetDependenciesEntryExtractor(null);
        Set<DependencyType> types = new HashSet<DependencyType>();
        types.add(DependencyType.EXTERNAL);
        extractor.setExtractedTypes(types);
        // Extract from default.build
        entry = extractor.extract(DEMO_DIR + File.separator + "object_factory/default.build");
        // Extract from distribution file
        entry = extractor.extract(DEMO_DIR + File.separator + "object_factory-1.2.1.zip");

        // Extract Java and .NET dependencies with a single multi-format extractor
        extractor = new MultipleFormatDependenciesEntryExtractor(null);
        // Get source file name
        String fileName = DEMO_DIR + File.separator + "build.xml";
        // Check whether file format is supported
        boolean isSupported = extractor.isSupportedFileType(fileName);
        // Extract dependencies
        if (isSupported) {
            entry = extractor.extract(fileName);
        }
    }
}
