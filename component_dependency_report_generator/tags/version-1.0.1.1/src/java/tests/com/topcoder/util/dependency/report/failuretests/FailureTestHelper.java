/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

/**
 * Helper class for failure test.
 *
 * @author extra
 * @version 1.0
 */
public final class FailureTestHelper {

    /**
     * Gets the dependencies entries.
     *
     * @return the dependencies entries.
     */
    public static List<DependenciesEntry> getDependenciesEntries() {
        List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();

        // compile
        dependencies.add(new ComponentDependency("base_exception", "2.0.0", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.INTERNAL,
                "../tcs/lib/tcs/base_exception/2.0.0/base_exception.jar"));
        dependencies.add(new ComponentDependency("typesafe_enum", "1.1.0", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.INTERNAL,
                "../tcs/lib/tcs/typesafe_enum/1.1.0/typesafe_enum.jar"));
        dependencies.add(new ComponentDependency("object_formatter", "1.0.0", ComponentLanguage.JAVA,
                DependencyCategory.COMPILE, DependencyType.INTERNAL,
                "../tcs/lib/tcs/object_formatter/1.0.0/object_formatter.jar"));
        dependencies.add(new ComponentDependency("log4j", "1.2.14", ComponentLanguage.JAVA, DependencyCategory.COMPILE,
                DependencyType.EXTERNAL, "../tcs/lib/third_party/log4j/1.2.14/log4j-1.2.14.jar"));
        dependencies.add(new ComponentDependency("junit", "3.8.2", ComponentLanguage.JAVA, DependencyCategory.COMPILE,
                DependencyType.EXTERNAL, "../tcs/lib/third_party/junit/3.8.2/junit.jar"));
        // test
        dependencies.add(new ComponentDependency("base_exception", "2.0.0", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.INTERNAL,
                "../tcs/lib/tcs/base_exception/2.0.0/base_exception.jar"));
        dependencies.add(new ComponentDependency("typesafe_enum", "1.1.0", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.INTERNAL,
                "../tcs/lib/tcs/typesafe_enum/1.1.0/typesafe_enum.jar"));
        dependencies.add(new ComponentDependency("object_formatter", "1.0.0", ComponentLanguage.JAVA,
                DependencyCategory.TEST, DependencyType.INTERNAL,
                "../tcs/lib/tcs/object_formatter/1.0.0/object_formatter.jar"));
        dependencies.add(new ComponentDependency("log4j", "1.2.14", ComponentLanguage.JAVA, DependencyCategory.TEST,
                DependencyType.EXTERNAL, "../tcs/lib/third_party/log4j/1.2.14/log4j-1.2.14.jar"));
        dependencies.add(new ComponentDependency("junit", "3.8.2", ComponentLanguage.JAVA, DependencyCategory.TEST,
                DependencyType.EXTERNAL, "../tcs/lib/third_party/junit/3.8.2/junit.jar"));

        Component loggingWrapper = new Component("logging_wrapper", "2.0.0", ComponentLanguage.JAVA);

        return new ArrayList<DependenciesEntry>(Arrays.asList(new DependenciesEntry(loggingWrapper, dependencies)));
    }

    /**
     * Gets the circle dependencies entries.
     *
     * @return the circle dependencies entries.
     */
    public static List<DependenciesEntry> getCircleDependenciesEntries() {
        ArrayList<DependenciesEntry> entries = new ArrayList<DependenciesEntry>();
        // a -> b
        // b -> c
        // c -> a
        Component a = new Component("a", "1.0", ComponentLanguage.JAVA);
        List<ComponentDependency> aDependencies = new ArrayList<ComponentDependency>();
        aDependencies.add(new ComponentDependency("b", "2.0", ComponentLanguage.JAVA, DependencyCategory.COMPILE,
                DependencyType.INTERNAL, "../tcs/lib/tcs/b/2.0/b.jar"));
        aDependencies.add(new ComponentDependency("b", "2.0", ComponentLanguage.JAVA, DependencyCategory.TEST,
                DependencyType.INTERNAL, "../tcs/lib/tcs/b/2.0/b.jar"));
        entries.add(new DependenciesEntry(a, aDependencies));

        Component b = new Component("b", "2.0", ComponentLanguage.JAVA);
        List<ComponentDependency> bDependencies = new ArrayList<ComponentDependency>();
        bDependencies.add(new ComponentDependency("c", "3.0", ComponentLanguage.JAVA, DependencyCategory.COMPILE,
                DependencyType.INTERNAL, "../tcs/lib/tcs/c/3.0/c.jar"));
        bDependencies.add(new ComponentDependency("c", "3.0", ComponentLanguage.JAVA, DependencyCategory.TEST,
                DependencyType.INTERNAL, "../tcs/lib/tcs/c/3.0/c.jar"));
        entries.add(new DependenciesEntry(b, bDependencies));

        Component c = new Component("c", "3.0", ComponentLanguage.JAVA);
        List<ComponentDependency> cDependencies = new ArrayList<ComponentDependency>();
        cDependencies.add(new ComponentDependency("a", "1.0", ComponentLanguage.JAVA, DependencyCategory.COMPILE,
                DependencyType.INTERNAL, "../tcs/lib/tcs/a/1.0/a.jar"));
        cDependencies.add(new ComponentDependency("a", "1.0", ComponentLanguage.JAVA, DependencyCategory.TEST,
                DependencyType.INTERNAL, "../tcs/lib/tcs/a/1.0/a.jar"));
        entries.add(new DependenciesEntry(c, cDependencies));

        return entries;
    }
}
