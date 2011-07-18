/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
/**
 * <p>
 * A simple accuracy mock of UserDAO.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class UserDAOAccuracyMock implements UserDAO {
    public UserDAOAccuracyMock() {
        user = null;
    }
    private static User user;

    /**
     * @return the user
     */
    public static User getUser() {
        return user;
    }

    public boolean canChangeHandle(String handle) {
        return false;
    }

    public User find(Long id) {
        User user = new User();
        Set<UserGroup> securityGroups = new HashSet<UserGroup>();
        UserGroup o1 = new UserGroup();
        o1.setSecurityStatusId(SecurityGroup.ACTIVE);
        securityGroups.add(o1);
        SecurityGroup securityGroup = new SecurityGroup() {
            @Override
            public Set<RegistrationType> getRegistrationTypes() {
                Set<RegistrationType> types = new HashSet<RegistrationType>();
                RegistrationType type1 = new RegistrationType(RegistrationType.HIGH_SCHOOL_ID);
                types.add(type1);
                type1 = new RegistrationType(RegistrationType.COMPETITION_ID);
                types.add(type1);
                type1 = new RegistrationType(RegistrationType.SOFTWARE_ID);
                types.add(type1);
                return types;
            }
        };
        o1.setSecurityGroup(securityGroup);

        user.setSecurityGroups(securityGroups);

        HashSet<Email> emailAddresses = new HashSet<Email>();
        Email email = new Email();
        email.setPrimary(true);
        email.setAddress("accuracy@topcoder.com");
        emailAddresses.add(email);email = new Email();
        email.setPrimary(false);
        email.setAddress("accuracynotprimary@topcoder.com");
        emailAddresses.add(email);
        user.setEmailAddresses(emailAddresses);
        return user;
    }

    public User find(String userName, boolean ignoreCase) {
        User user = new User();
        user.setId(1L);
        return user;
    }

    public User find(String userName, boolean ignoreCase, boolean activeRequired) {
        return null;
    }

    public List<User> find(String handle, String firstName, String lastName, String email) {
        return null;
    }

    public User find(String email) {
        return null;
    }

    public void saveOrUpdate(User user) {
        this.user = user;
    }

}
