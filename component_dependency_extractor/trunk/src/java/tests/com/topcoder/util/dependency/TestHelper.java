/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Test Helper.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * Private ctor.
     * </p>
     */
    private TestHelper() {
        // do nothing
    }

    /**
     * <p>
     * Gets a list of entries for tests.
     * </p>
     *
     * @return a list of entries
     */
    public static List<DependenciesEntry> getEntries() {
        List<DependenciesEntry> entries = new ArrayList<DependenciesEntry>();
        entries.add(getEntry(1));
        entries.add(getEntry(2));
        return entries;
    }

    /**
     * <p>
     * Creates some mock entry.
     * </p>
     *
     * @param i some number used to create an entry
     * @return a <code>DependenciesEntry</code> entry
     */
    private static DependenciesEntry getEntry(int i) {
        Component component = new Component("name" + i, "version" + i, ComponentLanguage.DOT_NET);
        ComponentDependency componentDependency = new ComponentDependency("name" + i, "version" + i,
            ComponentLanguage.JAVA, DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
        List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
        dependencies.add(componentDependency);
        return new DependenciesEntry(component, dependencies);
    }

}
