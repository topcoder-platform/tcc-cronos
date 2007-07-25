/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationServiceConfigurationException;

/**
 * <p>
 * This is a helper class for the whole component.
 * </p>
 * <p>
 * Thread safety: This class is thread safe.
 * </p>
 * @author moonli
 * @version 1.0
 */
final class Util {

    /**
     * <p>
     * Private constructor preventing this class from being instantiated.
     * </p>
     */
    private Util() {
    }

    /**
     * <p>
     * Checks whether given object is null or not.
     * </p>
     * @param obj
     *            given object to be checked
     * @param name
     *            name of the specified object
     * @throws IllegalArgumentException
     *             if specified object is null
     */
    static void checkObjNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("The parameter [" + name + "] should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether given string is null or empty.
     * </p>
     * @param str
     *            the string to be checked
     * @param name
     *            name of the string
     * @throws IllegalArgumentException
     *             if given string is null or empty
     */
    static void checkStrNotNullOrEmpty(String str, String name) {
        checkObjNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter [" + name + "] should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether given Object array is null or has null elements.
     * </p>
     * @param array
     *            the Object array to be checked
     * @param name
     *            name of the array
     * @throws IllegalArgumentException
     *             if given Object array is null or has null elements
     */
    static void checkArrrayNullOrHasNull(Object[] array, String name) {
        checkObjNotNull(array, name);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException("The parameter [" + name
                    + "] array should not contain null elements.");
            }
        }
    }

    /**
     * <p>
     * Checks whether given ID is negative or not.
     * </p>
     * @param id
     *            the id to be checked
     * @param name
     *            name of the id
     * @throws IllegalArgumentException
     *             if given ID is negative
     */
    static void checkIDNotNegative(long id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("[" + name + "] should not be negative.");
        }
    }

    /**
     * <p>
     * Checks whether error messages array is valid.
     * </p>
     * @param errors
     *            the error messages array
     * @throws IllegalArgumentException
     *             if given array contains null or empty elements
     */
    static void checkErrorsArray(String[] errors) {
        if (errors != null) {
            for (int i = 0; i < errors.length; i++) {
                if (errors[i] == null || errors[i].trim().length() == 0) {
                    throw new IllegalArgumentException(
                        "[errors] array should not contain null or empty elements.");
                }
            }
        }
    }

    /**
     * <p>
     * Converts an array of strings to an array of long values.
     * </p>
     * @param numbers
     *            the long values in string representations
     * @return an array of long values
     * @throws NumberFormatException
     *             if any string can not be converted into 'long' type
     */
    static long[] strArrayToLongArray(String[] numbers) {
        if (numbers == null) {
            return new long[0];
        }
        long[] nums = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            nums[i] = Long.parseLong(numbers[i]);
        }
        return nums;
    }

    /**
     * <p>
     * Gets ResourceRole instances correspond to given roles ids.
     * </p>
     * @param resourceManager
     *            the ResourceManager instance
     * @param ids
     *            the roles ids
     * @return an array of instances of ResourceRole
     * @throws RegistrationServiceConfigurationException
     *             if there is no corresponding ResourceRole instance for certain id
     */
    static ResourceRole[] getResourceRoles(ResourceManager resourceManager, long[] ids) {
        ResourceRole[] roles = new ResourceRole[ids.length];
        try {
            ResourceRole[] allRoles = resourceManager.getAllResourceRoles();
            for (int i = 0; i < ids.length; i++) {
                // flag indicating whether corresponding ResourceRole instance for specific id is
                // found
                boolean found = false;
                // iterates through all ResourceRoles to find the one corresponding to specific id
                for (int j = 0; j < allRoles.length; j++) {
                    if (allRoles[j].getId() == ids[i]) {
                        roles[i] = allRoles[j];
                        found = true;
                        break;
                    }
                }
                // if not found, throw an exception to indicate this invalid case
                if (!found) {
                    throw new RegistrationServiceConfigurationException(
                        "Corresponding ResourceRole instance can't be found for id=" + ids[i]);
                }
            }
        } catch (ResourcePersistenceException ex) {
            throw new RegistrationServiceConfigurationException(
                "Error occurred when retrieving all resource roles from persistence.", ex);
        }
        return roles;
    }

    /**
     * <p>
     * Checks whether given RegistrationInfo instance is valid.
     * </p>
     * @param info
     *            the RegistrationInfo instance
     * @param name
     *            name of the instance
     * @throws IllegalArgumentException
     *             if given RegistrationInfo instance is null or contains negative IDs
     */
    static void checkRegistrationInfo(RegistrationInfo info, String name) {
        checkObjNotNull(info, name);
        checkIDNotNegative(info.getProjectId(), "Project ID");
        checkIDNotNegative(info.getRoleId(), "Role ID");
        checkIDNotNegative(info.getUserId(), "User ID");
    }
}
