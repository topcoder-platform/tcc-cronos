/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.leads.services.LDAPUserService;
import hr.leads.services.LDAPUserServiceException;
import hr.leads.services.model.LDAPUser;
import hr.leads.services.model.UserSearchFilter;

/**
 * <p>
 * Mock LDAP user service for accuracy test.
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
     * @param cnum the CNUM of the user to get.
     *
     * @return the retrieved User.
     *
     * @throws IllegalArgumentException if cnum is null or empty.
     * @throws LDAPUserServiceException if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public LDAPUser getUser(String cnum) throws LDAPUserServiceException {

        if (result instanceof LDAPUserServiceException) {
            throw (LDAPUserServiceException) result;
        }

        return ((List<LDAPUser>) result).get(0);
    }

    /**
     * <p>
     * Searches users from IBM enterprise directory who match the filtering conditions given by
     * UserSearchFilter.
     * </p>
     *
     * @param filter the filtering conditions.
     *
     * @return the fully populated LDAPUser objects representing users that match the filtering
     *         conditions.
     *
     * @throws IllegalArgumentException if filter is null or all its fields is null.
     * @throws LDAPUserServiceException if any error occurs.
     */
    @SuppressWarnings("unchecked")
    public List<LDAPUser> searchUsers(UserSearchFilter filter) throws LDAPUserServiceException {

        if (result instanceof LDAPUserServiceException) {
            throw (LDAPUserServiceException) result;
        }

        return ((List<LDAPUser>) result);
    }

    /**
     * <p>
     * Sets the result.
     * </p>
     *
     * @param result the result.
     */
    public void setResult(Object result) {
        this.result = result;
    }
}
