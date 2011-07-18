/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.dao.SecurityGroupDAO;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
/**
 * <p>
 * A simple accuracy mock of SecurityGroupDAO.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class SecurityGroupDAOAccuracyMock implements SecurityGroupDAO {

    @Override
    public List<SecurityGroup> getSecurityGroups(Set registrationTypes) {
        SecurityGroup group1 = new SecurityGroup() {
            @Override
            public Long getId() {
                return 1L;
            }
        };
        SecurityGroup group2 = new SecurityGroup() {
            @Override
            public Long getId() {
                return 2L;
            }
        };
        return Arrays.asList(group1, group2);
    }

    @Override
    public boolean hasGroup(long userId, long groupId) {
        return false;
    }

    @Override
    public boolean hasGroup(User u, SecurityGroup g) {
        return false;
    }

    @Override
    public boolean hasInactiveHSGroup(User u) {
        return false;
    }

    @Override
    public void delete(SecurityGroup entity) {
    }

    @Override
    public SecurityGroup find(Long id) {
        return null;
    }

    @Override
    public List<SecurityGroup> findAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(SecurityGroup entity) {
    }

}
