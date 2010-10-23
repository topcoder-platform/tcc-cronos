/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.model.LDAPUser;
import hr.leads.services.model.ManagerRole;
import hr.leads.services.model.UserSearchFilter;
import hr.leads.services.model.jpa.EmployeeProfile;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

/**
 * <P>
 * LDAPUserServiceMockBean is a mock up class (ejb) implements LDAPUserServiceLocal for unit tests only.
 * </p>
 *
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class LDAPUserServiceMockBean implements LDAPUserServiceLocal {

    /**
     * <p>
     * Represents the employeeProfileService. It is injected by container.
     * </p>
     */
    @EJB(name = "employeeProfileService")
    private EmployeeProfileService employeeProfileService;

    /**
     * <p>
     * Gets the user of the cnum.
     * </p>
     *
     * @param cnum the cnum.
     * @return the user of the cnum.
     *
     * @throws LDAPUserServiceException if any error occurs.
     */
    @Override
    public LDAPUser getUser(String cnum) throws LDAPUserServiceException {
        LDAPUser ldapUser = new LDAPUser();
        try {
            EmployeeProfile employee = employeeProfileService.getEmployeeProfile(cnum);
            if (null == employee) {
                return null;
            }
            ldapUser.setCnum(employee.getCnum());
            ldapUser.setOrganization("Company");
            if (employee.getCnum().equals("12")) {
                ldapUser.setOrganization("AnotherCompany");
            }
        } catch (EmployeeProfileServiceException e) {
            throw new LDAPUserServiceException("failed to get employee.", e);
        }
        return ldapUser;
    }

    /**
     * <p>
     * Searches the users via a filter.
     * </p>
     *
     * @param filter the filter.
     * @return the list of ldap users.
     *
     * @throws LDAPUserServiceException if any error occurs.
     */
    @Override
    public List<LDAPUser> searchUsers(UserSearchFilter filter)
        throws LDAPUserServiceException {
        List<LDAPUser> ldapUsers = new ArrayList<LDAPUser>();
        try {
            List<EmployeeProfile> employees = employeeProfileService
                    .getEmployeeProfilesByManager(filter.getManagerCnum(),
                            ManagerRole.DIRECT, null, true, null, null);
            for (EmployeeProfile employee : employees) {
                LDAPUser ldapUser = new LDAPUser();
                ldapUser.setCnum(employee.getCnum());
                ldapUser.setOrganization("Company");
                ldapUsers.add(ldapUser);
            }
        } catch (EmployeeProfileServiceException e) {
            throw new LDAPUserServiceException("failed to get employee.", e);
        }
        return ldapUsers;
    }

}
