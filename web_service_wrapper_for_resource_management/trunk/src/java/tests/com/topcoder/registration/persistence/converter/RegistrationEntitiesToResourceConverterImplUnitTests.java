/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.converter;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;
import com.topcoder.registration.persistence.BaseTestCase;
import com.topcoder.registration.persistence.RegistrationEntitiesToResourceConversionException;


/**
 * <p>
 * Unit test for <code>{@link RegistrationEntitiesToResourceConverterImpl}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegistrationEntitiesToResourceConverterImplUnitTests extends BaseTestCase {

    /**
     * <p>
     * The <code>RegistrationEntitiesToResourceConverterImpl</code> to be tested.
     * </p>
     */
    private RegistrationEntitiesToResourceConverterImpl converter;

    /**
     * <p>
     * Set up the test case.
     * </p>
     */
    @Override
    protected void setUp() {
        this.converter = new RegistrationEntitiesToResourceConverterImpl();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     */
    @Override
    protected void tearDown() {
        this.converter = null;
    }

    /**
     * <p>
     * Test constructor {@link RegistrationEntitiesToResourceConverterImpl
     * #RegistrationEntitiesToResourceConverterImpl()}.
     * </p>
     *
     * <p>
     * The instance of <code>RegistrationEntitiesToResourceConverterImpl</code>
     * should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("The converter should be created.", this.converter);
    }

    /**
     * <p>
     * Create array of <code>ResourceRole</code>.
     * </p>
     *
     * @return array of <code>ResourceRole</code>.
     */
    protected ResourceRole[] createResourceRoles() {
        ResourceRole role1 = new ResourceRole(1L);
        role1.setName("developer");
        ResourceRole role2 = new ResourceRole(2L);
        role2.setName("designer");
        return new ResourceRole[]{role1, role2};
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given contest is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullContest() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(null, this.createUser(1L),
                this.createContestRole(1L), this.createResourceRoles());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given user is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullUser() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), null,
                this.createContestRole(1L), this.createResourceRoles());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given contest role is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullContestRole() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                null, this.createResourceRoles());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given array of resource roles is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_NullResourceRolesArray() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                this.createContestRole(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given array of resource roles is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_EmptyResourceRolesArray() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                this.createContestRole(1L), new ResourceRole[0]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given array of resource roles contains null element,
     * <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_ResourceRolesArrayContainsNull() throws Exception {
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                this.createContestRole(1L), new ResourceRole[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given ContestRole.role is null,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     */
    public void testConvertRegistrationEntitiesToResource_NullRole() {
        ContestRole contestRole = this.createContestRole(1L);
        contestRole.setRole(null);
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                contestRole, this.createResourceRoles());
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given ContestRole.role.name is null,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     */
    public void testConvertRegistrationEntitiesToResource_NullRoleName() {
        ContestRole contestRole = this.createContestRole(1L);
        contestRole.getRole().setName(null);
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                contestRole, this.createResourceRoles());
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * Given array of resource roles has a resource whose name is null,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     */
    public void testConvertRegistrationEntitiesToResource_NullResourceRoleName() {
        ResourceRole[] resourceRoles = this.createResourceRoles();
        resourceRoles[0].setName(null);
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                this.createContestRole(1L), resourceRoles);
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * No match resource role found,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     */
    public void testConvertRegistrationEntitiesToResource_NoResourceRoleMatched() {
        ContestRole contestRole = this.createContestRole(1L);
        contestRole.getRole().setName("SomeOtherRole");
        try {
            converter.convertRegistrationEntitiesToResource(this.createContest(1L), this.createUser(1L),
                contestRole, this.createResourceRoles());
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * When constructing <code>Resource</code>, its id will be set as {@link Contest#getId()},
     * but the {@link Contest#getId()} is -1, and -1 is NOT allowed by <code>Resource</code>,
     * <code>RegistrationEntitiesToResourceConversionException</code> expected.
     * </p>
     */
    public void testConvertRegistrationEntitiesToResource_ErrorConstructResource() {
        Contest contest = this.createContest(-1L);
        try {
            converter.convertRegistrationEntitiesToResource(contest, this.createUser(1L),
                this.createContestRole(1L), this.createResourceRoles());
            fail("RegistrationEntitiesToResourceConversionException expected");
        } catch (RegistrationEntitiesToResourceConversionException e) {
            //pass
        }
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * The <code>Resource.project</code> should be set as <code>Contest.id</code>,
     * <code>Resource.resourceRole</code> should be set as the matched role, and
     * <code>Resource.properties</code> should be filled with the properties values
     * of <code>User</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_Accuracy_1() throws Exception {
        User user = this.createUser(2L);
        Contest contest = this.createContest(1L);
        ContestRole contestRole = this.createContestRole(1L);
        ResourceRole[] resourceRoles = this.createResourceRoles();

        Resource resource = converter.convertRegistrationEntitiesToResource(contest, user,
            contestRole, resourceRoles);

        ResourceRole matchedResourceRole = null;
        for (int i = 0; i < resourceRoles.length; i++) {
            ResourceRole resourceRole = resourceRoles[i];
            if (contestRole.getRole().getName().equalsIgnoreCase(resourceRole.getName())) {
                matchedResourceRole = resourceRole;
                break;
            }
        }

        assertEquals("Resource.id should be set.", 2L, user.getId());
        assertEquals("Resource.project should be set.", 1L, resource.getProject().longValue());
        assertEquals("Resource.resourceRole should be set", matchedResourceRole, resource.getResourceRole());

        assertEquals("The createUserName property should be set",
                user.getCreateUserName(), resource.getProperty("user.createUserName"));
        assertEquals("The modifyUsername property should be set",
                user.getModifyUsername(), resource.getProperty("user.modifyUsername"));

        assertEquals("The createDate property should be set",
                user.getCreateDate().toString(), resource.getProperty("user.createDate"));
        assertEquals("The modifyDate property should be set",
                user.getModifyDate().toString(), resource.getProperty("user.modifyDate"));

        assertEquals("The handle property should be set",
                user.getHandle(), resource.getProperty("user.handle"));
        assertEquals("The email property should be set",
                user.getEmail(), resource.getProperty("user.email"));
        assertEquals("The suspended property should be set",
                user.isSuspended() + "", resource.getProperty("user.suspended"));
    }

    /**
     * <p>
     * Test method {@link RegistrationEntitiesToResourceConverterImpl
     * #convertRegistrationEntitiesToResource(Contest, User, ContestRole, ResourceRole[])}.
     * </p>
     *
     * <p>
     * The <code>Resource.project</code> should be set as <code>Contest.id</code>,
     * <code>Resource.resourceRole</code> should be set as the matched role, and
     * <code>Resource.properties</code> should be filled with the properties values
     * of <code>User</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConvertRegistrationEntitiesToResource_Accuracy_2() throws Exception {
        User user = this.createUser(2L);

        //The user's properties are null.
        user.setCreateDate(null);
        user.setModifyDate(null);
        user.setCreateUserName(null);
        user.setModifyUsername(null);
        user.setHandle(null);
        user.setEmail(null);

        Contest contest = this.createContest(1L);
        ContestRole contestRole = this.createContestRole(1L);
        ResourceRole[] resourceRoles = this.createResourceRoles();

        Resource resource = converter.convertRegistrationEntitiesToResource(contest, user,
            contestRole, resourceRoles);

        ResourceRole matchedResourceRole = null;
        for (int i = 0; i < resourceRoles.length; i++) {
            ResourceRole resourceRole = resourceRoles[i];
            if (contestRole.getRole().getName().equalsIgnoreCase(resourceRole.getName())) {
                matchedResourceRole = resourceRole;
                break;
            }
        }

        assertEquals("Resource.id should be set.", 2L, user.getId());
        assertEquals("Resource.project should be set.", 1L, resource.getProject().longValue());
        assertEquals("Resource.resourceRole should be set", matchedResourceRole, resource.getResourceRole());

        assertNull("The createUserName property should be null", resource.getProperty("user.createUserName"));
        assertNull("The modifyUsername property should be null", resource.getProperty("user.modifyUsername"));

        assertNull("The createDate property should be null", resource.getProperty("user.createDate"));
        assertNull("The modifyDate property should be null", resource.getProperty("user.modifyDate"));

        assertNull("The handle property should be null", resource.getProperty("user.handle"));
        assertNull("The email property should be null", resource.getProperty("user.email"));

        assertEquals("The suspended property should be set",
                user.isSuspended() + "", resource.getProperty("user.suspended"));
    }
}
