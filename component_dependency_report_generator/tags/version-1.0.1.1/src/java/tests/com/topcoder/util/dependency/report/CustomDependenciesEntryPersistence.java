/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;

/**
 * <p>
 * A simple custom <code>DependenciesEntryPersistence</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CustomDependenciesEntryPersistence implements DependenciesEntryPersistence {

    /**
     * <p>
     * The <code>DependenciesEntryPersistence</code> must have a constructor which takes
     * only <code>ConfigurationObject</code> as argument.
     * </p>
     *
     * @param config Useless argument.
     */
    public CustomDependenciesEntryPersistence(ConfigurationObject config) {
        // empty
    }

    /**
     * <p>
     * Always return an empty list.
     * </p>
     *
     * @return An empty list always.
     *
     * @throws DependenciesEntryPersistenceException Never.
     */
    @SuppressWarnings("unchecked")
    public List < DependenciesEntry > load()
        throws DependenciesEntryPersistenceException {
        return Arrays.asList(new DependenciesEntry[]{
            new DependenciesEntry(new Component("name", "version", ComponentLanguage.JAVA), new ArrayList())});
    }

    /**
     * <p>
     * Empty method.
     * </p>
     *
     * @param entries Useless parameter.
     *
     * @throws DependenciesEntryPersistenceException Never.
     */
    public void save(List < DependenciesEntry > entries)
        throws DependenciesEntryPersistenceException {
        // empty
    }
}
