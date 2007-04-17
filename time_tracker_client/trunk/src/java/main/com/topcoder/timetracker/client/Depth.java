/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * <p>
 * This abstract class is the base class of the depth. It defines the general interface for all the depth. Therefore,
 * all depth depending search can be uniformly processed without to know the difference between depths.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public abstract class Depth implements Serializable {
    /**
     * <p>
     * Represents the fields will be included when search with this depth. This variable is set in constructor,&nbsp;
     * is immutable and never be null, and won't contain null/empty(trim'd) string. It is referenced by getFields,
     * setPaymentTerm method.
     * </p>
     */
    private final String[] fields;

    /**
     * <p>
     * Represents whether the project info only need id and name. This variable is set in constructor,&nbsp; is
     * immutable. It is referenced by like named method.
     * </p>
     */
    private final boolean onlyProjectsIdName;

    /**
     * <p>
     * Represents whether the address property need to be set. This variable is set in constructor,&nbsp; is immutable.
     * It is referenced by like named method.
     * </p>
     */
    private final boolean useAddress;

    /**
     * <p>
     * Represents whether the contact property need to be set. This variable is set in constructor,&nbsp; is immutable.
     * It is referenced by like named method.
     * </p>
     */
    private final boolean useContact;

    /**
     * <p>
     * Represents whether the projects property need to be set. This variable is set in constructor,&nbsp; is
     * immutable. It is referenced by like named method.
     * </p>
     */
    private final boolean useProjects;

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
    protected Depth(String[] fields, boolean onlyProjectsIdName, boolean setProjects, boolean setAddress,
        boolean setContact) {
        Helper.checkArrayString(fields, "fields");

        this.fields = (String[]) fields.clone();
        this.onlyProjectsIdName = onlyProjectsIdName;
        this.useProjects = setProjects;
        this.useAddress = setAddress;
        this.useContact = setContact;
    }

    /**
     * <p>
     * Get the fields of this depth.
     * </p>
     *
     * @return non null, non empty list which containing non null, non empty field names
     */
    public List getFields() {
        return asList((String[]) this.fields.clone(), true);
    }

    /**
     * <p>
     * Build the result with the given result set.
     * </p>
     *
     * @param result non null result set used to build result
     *
     * @return non null, possible empty array containing non null clients
     *
     * @throws IllegalArgumentException if any arguments is null
     * @throws ClientPersistenceException if any error occurred
     */
    public Client[] buildResult(CustomResultSet result) throws ClientPersistenceException {
        Helper.checkNull(result, "result");

        Client[] results = new Client[result.getRecordCount()];

        int i = 0;
        while (result.next()) {
            results[i++] = buildClient(result);
        }
        return results;
    }

    /**
     * <p>
     * Build the client according to current row of the result set.
     * </p>
     *
     * @param result non null result set used to build result
     *
     * @return non null Client build from current row of result set
     * @throws ClientPersistenceException if any error occurred
     */
    protected abstract Client buildClient(CustomResultSet result) throws ClientPersistenceException;

    /**
     * <p>
     * Indicate whether the project info only need id and name.
     * </p>
     *
     * @return whether the project info only need id and name.
     */
    public boolean onlyProjectsIdName() {
        return onlyProjectsIdName;
    }

    /**
     * <p>
     * Indicate whether the address property need to be set.
     * </p>
     *
     * @return whether the address property need to be set.
     */
    public boolean useAddress() {
        return useAddress;
    }

    /**
     * <p>
     * Indicate whether the contact property need to be set.
     * </p>
     *
     * @return whether the contact property need to be set.
     */
    public boolean useContact() {
        return useContact;
    }

    /**
     * <p>
     * Indicate whether the projects property need to be set.
     * </p>
     *
     * @return whether the projects property need to be set.
     */
    public boolean useProjects() {
        return useProjects;
    }

    /**
     * Check and convert a string array to List.
     *
     * @param array the array to be converted
     * @param isEmptyAllowed is empty is allowed
     *
     * @return IllegalArgumentException if the argument is null or has null or empty elements
     */
    public List asList(String[] array, boolean isEmptyAllowed) {
        Helper.checkNull(array, "array");

        List list = new ArrayList();

        for (int index = 0; index < array.length; index++) {
            Helper.checkNull(array[index], "array[" + index + "]");

            if (!isEmptyAllowed && (array[index].trim().length() == 0)) {
                throw new IllegalArgumentException("The array should not contains empty String.");
            }

            list.add(array[index]);
        }

        return list;
    }

}
