/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import java.util.Date;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.validation.ValidationInfo;

import junit.framework.TestCase;
/**
 * Failure Tests for ValidationInfo class.
 * @author slion
 * @version 1.0
 */
public class ValidationInfoFailureTests extends TestCase {
    /**
     * Represents the ValidationInfo instance.
     */
    private ValidationInfo info = null;

    /**
     * Represents the FullProjectData instance.
     */
    private FullProjectData project;
    
    /**
     * Represents the ExternalUser instance.
     */
    private ExternalUser user;
    
    /**
     * Represents the RegistrationInfo instance.
     */
    private RegistrationInfo registration;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        info = new ValidationInfo();
        project = new FullProjectData(new Date(), new DefaultWorkdays());
        user = new ExternalUserImpl(1, "test", "handle", "fn", "ln");
        registration = new RegistrationInfoImpl();
    }

    /**
     * Teardown the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        info = null;
        project = null;
        user = null;
        registration = null;
    }

    /**
     * Tests ValidationInfo(RegistrationInfo registration, ExternalUser user,
     *  FullProjectData project) method with null RegistrationInfo registration,
     * IllegalArgumentException should be thrown.
     */
    public void testValidationInfo_NullRegistration() {
        try {
            new ValidationInfo(null, user, project);
            fail("testValidationInfo_NullRegistration is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidationInfo_NullRegistration.");
        }
    }

    /**
     * Tests ValidationInfo(RegistrationInfo registration, ExternalUser user, 
     * FullProjectData project) method with null ExternalUser user,
     * IllegalArgumentException should be thrown.
     */
    public void testValidationInfo_NullUser() {
        try {
            new ValidationInfo(registration, null, project);
            fail("testValidationInfo_NullUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidationInfo_NullUser.");
        }
    }

    /**
     * Tests ValidationInfo(RegistrationInfo registration, ExternalUser user, 
     * FullProjectData project) method with null FullProjectData project,
     * IllegalArgumentException should be thrown.
     */
    public void testValidationInfo_NullProject() {
        try {
            new ValidationInfo(registration, user, null);
            fail("testValidationInfo_NullProject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidationInfo_NullProject.");
        }
    }

    /**
     * Tests setRegistration(RegistrationInfo registration) method with null 
     * RegistrationInfo registration,
     * IllegalArgumentException should be thrown.
     */
    public void testSetRegistration_NullRegistration() {
        try {
            info.setRegistration(null);
            fail("testSetRegistration_NullRegistration is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetRegistration_NullRegistration.");
        }
    }

    /**
     * Tests setUser(ExternalUser user) method with null ExternalUser user,
     * IllegalArgumentException should be thrown.
     */
    public void testSetUser_NullUser() {
        try {
            info.setUser(null);
            fail("testSetUser_NullUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetUser_NullUser.");
        }
    }

    /**
     * Tests setProject(FullProjectData project) method with null FullProjectData project,
     * IllegalArgumentException should be thrown.
     */
    public void testSetProject_NullProject() {
        try {
            info.setProject(null);
            fail("testSetProject_NullProject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetProject_NullProject.");
        }
    }   
}