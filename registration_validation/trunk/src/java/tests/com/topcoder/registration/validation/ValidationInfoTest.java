/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import java.util.Date;
import java.io.Serializable;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysFactory;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationInfoImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for ValidationInfo.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationInfoTest extends TestCase {

    /**
     * <p>
     * The registration information of the user.
     * </p>
     */
    private RegistrationInfo registration;

    /**
     * <p>
     * The detailed external information about the user.
     * </p>
     */
    private ExternalUser user;

    /**
     * <p>
     * The details project information.
     * </p>
     */
    private FullProjectData project;

    /**
     * <p>
     * The ValidationInfo instance for testing purpose.
     * </p>
     */
    private ValidationInfo validationInfo;


    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        registration = TestHelper.createValidationInfoForTest().getRegistration();
        user = TestHelper.createValidationInfoForTest().getUser();
        project = TestHelper.createValidationInfoForTest().getProject();
        validationInfo = new ValidationInfo(registration, user, project);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ValidationInfo inheritance Serializable.
     * </p>
     */
    public void testInheritance1() {
        validationInfo = new ValidationInfo();
        assertTrue("validationInfo does not subclass Serializable.",
                validationInfo instanceof Serializable);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ValidationInfo inheritance Serializable.
     * </p>
     */
    public void testInheritance2() {
        validationInfo = new ValidationInfo(registration, user, project);
        assertTrue("validationInfo does not subclass Serializable.",
                validationInfo instanceof Serializable);
    }

    /**
     * <p>
     * Tests ctor ValidationInfo#ValidationInfo() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ValidationInfo instance should not be null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() throws Exception {

        validationInfo = new ValidationInfo();
        assertNotNull("Failed to create a new ValidationInfo instance.",
                validationInfo);
    }

    /**
     * <p>
     * Tests ctor ValidationInfo#ValidationInfo(RegistrationInfo, ExternalUser,
     * FullProjectData) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2() throws Exception {
        validationInfo = new ValidationInfo(registration, user, project);
        assertNotNull("Failed to create a new ValidationInfo instance.",
                validationInfo);
        assertEquals("Failed to create a ValidationInfo instance correctly.",
                registration, validationInfo.getRegistration());
        assertEquals("Failed to create a ValidationInfo instance correctly.",
                user, validationInfo.getUser());
        assertEquals("Failed to create a ValidationInfo instance correctly.",
                project, validationInfo.getProject());
    }

    /**
     * <p>
     * Tests ctor ValidationInfo#ValidationInfo(RegistrationInfo, ExternalUser,
     * FullProjectData) for failure.
     * </p>
     *
     * <p>
     * It tests the case when registration is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullRegistration() throws Exception {
        try {
            new ValidationInfo(null, user, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ValidationInfo#ValidationInfo(RegistrationInfo, ExternalUser,
     * FullProjectData) for failure.
     * </p>
     *
     * <p>
     * It tests the case when user is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullUser() throws Exception {
        try {
            new ValidationInfo(registration, null, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ValidationInfo#ValidationInfo(RegistrationInfo, ExternalUser,
     * FullProjectData) for failure.
     * </p>
     *
     * <p>
     * It tests the case when project is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullProject() throws Exception {
        try {
            new ValidationInfo(registration, user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ValidationInfo#getRegistration() for accuracy.
     * </p>
     *
     * <p>
     * Should have gotten the registration correctly.
     * </p>
     *
     */
    public void testGetRegistration() {

        assertEquals("Failed to get registration correctly.", registration,
                validationInfo.getRegistration());
    }

    /**
     * <p>
     * Tests ValidationInfo#getUser() for accuracy.
     * </p>
     *
     * <p>
     * Should have gotten the user correctly.
     * </p>
     *
     */
    public void testGetUser() {

        assertEquals("Failed to set user correctly.", user, validationInfo
                .getUser());
    }

    /**
     * <p>
     * Tests ValidationInfo#getProject() for accuracy.
     * </p>
     *
     * <p>
     * Should have gotten the project correctly.
     * </p>
     *
     */
    public void testGetProject() {

        assertEquals("Failed to get project correctly.", project,
                validationInfo.getProject());
    }

    /**
     * <p>
     * Tests ValidationInfo#setRegistration(RegistrationInfo) for accuracy.
     * </p>
     *
     * <p>
     * Should have set the registration correctly.
     * </p>
     *
     */
    public void testSetRegistration() {
        RegistrationInfo newRegistration = new RegistrationInfoImpl();
        validationInfo.setRegistration(newRegistration);
        assertEquals("Failed to set registration correctly.", newRegistration,
                validationInfo.getRegistration());
    }

    /**
     * <p>
     * Tests ValidationInfo#setUser(User) for accuracy.
     * </p>
     *
     * <p>
     * Should have set the user correctly.
     * </p>
     *
     */
    public void testSetUser() {
        ExternalUserImpl newUser = new ExternalUserImpl(3, "handle1",
                "firstName", "lastName", "email");
        validationInfo.setUser(newUser);
        assertEquals("Failed to set user correctly.", newUser, validationInfo
                .getUser());
    }

    /**
     * <p>
     * Tests ValidationInfo#setProject(Project) for accuracy.
     * </p>
     *
     * <p>
     * Should have set the project correctly.
     * </p>
     *
     */
    public void testSetProject() {
        Date date = new Date(System.currentTimeMillis());
        WorkdaysFactory factory = new DefaultWorkdaysFactory();
        Workdays workdays = factory.createWorkdaysInstance();
        FullProjectData newProject = new FullProjectData(date, workdays);

        validationInfo.setProject(newProject);
        assertEquals("Failed to set project correctly.", newProject,
                validationInfo.getProject());
    }

    /**
     * <p>
     * Tests ValidationInfo#setRegistration(RegistrationInfo) for failure.
     * </p>
     *
     * <p>
     * It tests the case when registration is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetRegistration_Null() throws Exception {
        try {
            validationInfo.setRegistration(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ValidationInfo#setProject(Project) for failure.
     * </p>
     *
     * <p>
     * It tests the case when project is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetProject_Null() throws Exception {
        try {
            validationInfo.setProject(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ValidationInfo#setUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case when user is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetUser_Null() throws Exception {
        try {
            validationInfo.setUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

}
