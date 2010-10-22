/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.leads.services.LDAPUserService;
import hr.leads.services.model.LDAPUser;
import hr.leads.services.model.UserSearchFilter;

/**
 * <p>
 * A mock implementation of LDAPUserService. used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockLDAPUserService implements LDAPUserService {
    /**
     * <p>
     * Represents the result.
     * </p>
     */
    private Object result;

    /**
     * <p>
     * Creates an instance of MockLDAPUserService.
     * </p>
     */
    public MockLDAPUserService() {
        LDAPUser ldapEmployee1 = new LDAPUser();
        ldapEmployee1.setCnum("ldapEmployee1");
        ldapEmployee1.setOrganization("organization1");
        LDAPUser ldapEmployee2 = new LDAPUser();
        ldapEmployee2.setCnum("ldapEmployee2");
        ldapEmployee2.setOrganization("organization2");

        result = new ArrayList<LDAPUser>(Arrays.asList(ldapEmployee1, ldapEmployee2));
    }

    /**
     * <p>
     * Gets a user from IBM enterprise directory that matches the given CNUM.
     * </p>
     *
     * @param cnum
     *            the CNUM of the user to get.
     *
     * @return the retrieved User.
     *
     * @throws IllegalArgumentException
     *             if cnum is null or empty.
     * @throws LDAPUserServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public LDAPUser getUser(String cnum) {
        checkNullOrEmpty(cnum, "cnum");

        return ((List<LDAPUser>) result).get(0);
    }

    /**
     * <p>
     * Searches users from IBM enterprise directory who match the filtering conditions given by UserSearchFilter.
     * </p>
     *
     * @param filter
     *            the filtering conditions.
     *
     * @return the fully populated LDAPUser objects representing users that match the filtering conditions.
     *
     * @throws IllegalArgumentException
     *             if filter is null or all its fields is null.
     * @throws LDAPUserServiceException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public List<LDAPUser> searchUsers(UserSearchFilter filter) {
        checkNull(filter, "filter");

        return ((List<LDAPUser>) result);
    }

    /**
     * <p>
     * Sets the result.
     * </p>
     *
     * @param result
     *            the result.
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * <p>
     * Validates the value of a string. The value can not be <code>null</code> or an empty string.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the given string is <code>null</code> or an empty string.
     */
    private static void checkNullOrEmpty(String value, String name) {
        checkNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException("'" + name + "' should not be an empty string.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    private static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' should not be null.");
        }
    }
}
