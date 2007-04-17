/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.Depth;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * This is a mock extends of <code>Depth</code> and it is used to help accuracy tests.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MockDepth extends Depth {
    /**
     * <p>
     * Constructs the Depth.
     * </p>
     *
     * @param fields non null array containing non null, non empty(trim'd) field names
     * @param onlyProjectsIdName whether the project info only need id and name.
     * @param setProjects whether the projects property need to be set
     * @param setAddress whether the address property need to be set
     * @param setContact whether the contact property need to be set
     *
     * @throws IllegalArgumentException if the fields is null or containing null/empty field name
     */
    public MockDepth(String[] fields, boolean onlyProjectsIdName, boolean setProjects, boolean setAddress,
        boolean setContact) {
        super(fields, onlyProjectsIdName, setProjects, setAddress, setContact);
    }

    /**
     * <p>
     * Build the client according to current row of the result set.
     * </p>
     *
     * @param result non null result set used to build result
     *
     * @return null always
     * @throws ClientPersistenceException if any error occurred
     */
    protected Client buildClient(CustomResultSet result) throws ClientPersistenceException {
        return null;
    }

}
