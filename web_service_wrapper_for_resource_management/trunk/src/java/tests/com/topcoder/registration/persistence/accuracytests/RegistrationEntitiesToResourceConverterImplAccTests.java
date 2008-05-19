/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.accuracytests;

import java.util.Date;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.Role;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.RegistrationEntitiesToResourceConverter;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImpl;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test case for RegistrationEntitiesToResourceConverterImpl class.
 * </p>
 */
public class RegistrationEntitiesToResourceConverterImplAccTests extends TestCase {
    /** RegistrationEntitiesToResourceConverterImpl instance to test against. */
    private RegistrationEntitiesToResourceConverter converter = null;

    /**
     * Create instance.
     */
    protected void setUp() {
        converter = new RegistrationEntitiesToResourceConverterImpl();
    }

    /**
     * <p>
     * Create a new instance of <code>ContestRole</code>.
     * </p>
     *
     * @param id The id to set.
     *
     * @return a new instance of <code>ContestRole</code>.
     */
    private ContestRole createContestRole(long id) {
        ContestRole contestRole = new ContestRole();
        contestRole.setId(id);
        contestRole.setContestId(id);
        contestRole.setMaxRegistrants((int) (1 * id));
        contestRole.setMinRegistrants((int) (id % 2 + 1));
        contestRole.setRegistrationEndOffset(System.currentTimeMillis() / 2 / 2);
        contestRole.setRegistrationStartOffset(System.currentTimeMillis() / 2 / 2 / 2);
        contestRole.setRole(this.createRole(id));
        contestRole.setUnadvertised(true);
        return contestRole;
    }

    /**
     * <p>
     * Create a new instance of <code>Role</code>.
     * </p>
     *
     * @param id The id to set.
     *
     * @return a new instance of <code>Role</code>.
     */
    private Role createRole(long id) {
        Role role = new Role();
        role.setId(id);
        role.setName(id % 2 == 0 ? "pops" : "kyky");
        return role;
    }

    /**
     * <p>
     * Create a new instance of <code>User</code>.
     * </p>
     *
     * @param id The id to set.
     *
     * @return a new instance of <code>User</code>.
     */
    private User createUser(long id) {
        User user = new User();
        user.setId(id);
        user.setHandle("member" + id);
        user.setEmail("email" + id + "@topcoder.com");
        user.setSuspended(id % 2 == 0);
        user.setCreateDate(new Date(100000));
        user.setModifyDate(new Date(100020));
        user.setCreateUserName("createUser");
        user.setModifyUsername("modifyUser");
        return user;
    }

    /**
     * <p>
     * Create a new instance of <code>Contest</code>.
     * </p>
     *
     * @param id The id to set.
     *
     * @return a new instance of <code>Contest</code>.
     */
    private Contest createContest(long id) {
        Contest contest = new Contest();
        contest.setId(id);

        contest.setLink("http://www.topcoder.com/tc?module=ProjectDetail&pj=30004429");
        contest.setName("Registration Framework " + (id % 2 == 0 ? " Develop" : "Design"));
        contest.setRegistrationEnd(new Date());
        contest.setRegistrationStart(new Date());
        contest.setSourceName(id % 2 == 0 ? "Oracle" : "MYSQL");

        return contest;
    }
    
    /**
     * <p>
     * Create array of <code>ResourceRole</code>.
     * </p>
     *
     * @return array of <code>ResourceRole</code>.
     */
    private ResourceRole[] createResourceRoles() {
        ResourceRole role1 = new ResourceRole(1L);
        role1.setName("pops");
        ResourceRole role2 = new ResourceRole(2L);
        role2.setName("kyky");
        return new ResourceRole[]{role1, role2};
    }
    /**
     * <p>
     * Test the convertRegistrationEntitiesToResource method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConvertRegistrationEntitiesToResource()
        throws Exception {
        ContestRole contestRole = createContestRole(1L);
        ResourceRole[] resourceRoles = createResourceRoles();        
        User user = createUser(2L);
        Resource resource = converter.convertRegistrationEntitiesToResource( createContest(1L), user,
            contestRole, resourceRoles);

        ResourceRole matchedResourceRole = null;
        for (int i = 0; i < resourceRoles.length; i++) {
            ResourceRole resourceRole = resourceRoles[i];
            if (contestRole.getRole().getName().equalsIgnoreCase(resourceRole.getName())) {
                matchedResourceRole = resourceRole;
                break;
            }
        }

        assertEquals("The createDate property should be set",
                user.getCreateDate().toString(), resource.getProperty("user.createDate"));
        assertEquals("The modifyDate property should be set",
                user.getModifyDate().toString(), resource.getProperty("user.modifyDate"));
        assertEquals("Resource.id should be set.", 2L, user.getId());
        assertEquals("Resource.project should be set.", 1L, resource.getProject().longValue());
        assertEquals("Resource.resourceRole should be set", matchedResourceRole, resource.getResourceRole());

        assertEquals("The createUserName property should be set",
                user.getCreateUserName(), resource.getProperty("user.createUserName"));
        assertEquals("The modifyUsername property should be set",
                user.getModifyUsername(), resource.getProperty("user.modifyUsername"));
        
        assertEquals("The handle property should be set",
                user.getHandle(), resource.getProperty("user.handle"));
        assertEquals("The email property should be set",
                user.getEmail(), resource.getProperty("user.email"));
        assertEquals("The suspended property should be set",
                user.isSuspended() + "", resource.getProperty("user.suspended"));
    }
}
