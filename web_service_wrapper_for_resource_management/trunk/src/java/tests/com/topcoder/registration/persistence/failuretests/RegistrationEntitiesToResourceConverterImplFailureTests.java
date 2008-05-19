/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.failuretests;

import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.BaseTestCase;
import com.topcoder.registration.persistence.RegistrationEntitiesToResourceConversionException;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImpl;

/**
 * Failure test for class RegistrationEntitiesToResourceConverterImpl class.
 *
 * @author extra
 * @version 1.0
 */
public class RegistrationEntitiesToResourceConverterImplFailureTests extends BaseTestCase {

    /**
     * Represents instance of RegistrationEntitiesToResourceConverterImpl for
     * test.
     */
    private RegistrationEntitiesToResourceConverterImpl converter;

    /**
     * Represents instance of Contest for test.
     */
    private Contest contest;

    /**
     * Represents instance of User for test.
     */
    private User user;

    /**
     * Represents instance of ContestRole for test.
     */
    private ContestRole contestRole;

    /**
     * Represents instance of ResourceRole array for test.
     */
    private ResourceRole[] resourceRoles;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        converter = new RegistrationEntitiesToResourceConverterImpl();
        contest = createContest(1);
        user = createUser(1);
        contestRole = createContestRole(1);
        resourceRoles = createResourceRoles();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        converter = null;
        contest = null;
        user = null;
        contestRole = null;
        resourceRoles = null;
    }

    /**
     * Create array of ResourceRole.
     *
     * @return array of ResourceRole.
     */
    protected ResourceRole[] createResourceRoles() {
        ResourceRole role1 = new ResourceRole(1L);
        role1.setName("developer");
        ResourceRole role2 = new ResourceRole(2L);
        role2.setName("designer");
        return new ResourceRole[] {role1, role2 };
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With null contest, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullContest() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(null, user, contestRole, resourceRoles);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With null user, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullUser() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(contest, null, contestRole, resourceRoles);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With null contest role,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullContestRole() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, null, this.createResourceRoles());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With null resource roles,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullResourceRoles() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, this.createContestRole(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With empty resource roles,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_EmptyResourceRolesArray() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, this.createContestRole(1L),
                    new ResourceRole[0]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With resource roles contains null element,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_ResourceRolesArrayContainsNull() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, this.createContestRole(1L),
                    new ResourceRole[] {null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With null ContestRole.role,
     * RegistrationEntitiesToResourceConversionException expected.
     */
    public void testConvertRegistrationEntitiesToResource_NullRole() {
        contestRole.setRole(null);
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, contestRole, resourceRoles);
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]). With null ContestRole.role.name,
     * RegistrationEntitiesToResourceConversionException expected.
     */
    public void testConvertRegistrationEntitiesToResource_NullRoleName() {
        contestRole.getRole().setName(null);
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, contestRole, resourceRoles);
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            // expected
        }
    }

    /**
     * Test method convertRegistrationEntitiesToResource(Contest, User,
     * ContestRole, ResourceRole[]).
     * With resource roles has a resource whose name is null,
     * RegistrationEntitiesToResourceConversionException expected.
     *
     */
    public void testConvertRegistrationEntitiesToResource_NullResourceRoleName() {
        resourceRoles[0].setName(null);
        try {
            converter.convertRegistrationEntitiesToResource(contest, user, this.createContestRole(1L), resourceRoles);
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            // expected
        }
    }
}
