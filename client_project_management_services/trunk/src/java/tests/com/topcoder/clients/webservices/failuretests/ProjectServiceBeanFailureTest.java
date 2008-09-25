/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;

import junit.framework.TestCase;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ProjectServiceException;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.clients.webservices.beans.ProjectServiceBean;
import com.topcoder.clients.webservices.beans.ProjectServiceBeanConfigurationException;
import com.topcoder.clients.webservices.beans.Roles;
import com.topcoder.clients.webservices.failuretests.mock.MockProjectManager;
import com.topcoder.clients.webservices.failuretests.mock.MockUserMappingRetriever;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;


/**
 * This is a test case for <code>ProjectServiceBean</code>.
 *
 * @author PE
 * @version 1.0
 */
public class ProjectServiceBeanFailureTest extends TestCase {
    /** Represents an instance of ProjectServiceBean. */
    private ProjectServiceBean bean;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        bean = new ProjectServiceBean();

        FailureTestHelper.setField(bean, "projectManagerFile", "failure/config.properties");
        FailureTestHelper.setField(bean, "projectManagerNamespace", "ProjectServiceBean");
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", "failure/config.properties");
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "UserMappingRetriever");
        FailureTestHelper.setField(bean, "administratorRole", Roles.ADMIN);
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", Roles.USER);

        initialize();

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        SessionContextMock.setRoles(new String[] { Roles.ADMIN, Roles.USER });
        FailureTestHelper.setField(bean, "sessionContext", context);

        // Set default managers behavior
        MockUserMappingRetriever.setFail(false);
        MockProjectManager.throwPME(false);
        MockProjectManager.throwIAE(false);
    }

    /**
     * <p>
     * initialize.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void initialize() throws Exception {
        Method method = ProjectServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);
    }

    /**
     * <p>
     * Test for <code>initialize</code> method.
     * </p>
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig1() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerFile", null);

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * <p>
     * Checks the ProjectServiceBeanConfigurationException for invalid configuration.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    private void checkProjectServiceBeanConfigurationException()
        throws Throwable {
        try {
            try {
                initialize();
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }

            fail("ProjectServiceBeanConfigurationException should be thrown.");
        } catch (ProjectServiceBeanConfigurationException ex) {
            // success
        }
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig2() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerFile", " ");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig3() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerFile", "invalid");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig4() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", null);

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig5() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", " ");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig6() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", "invalid");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig7() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", "InvalidProjectServiceBean1");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig8() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", "InvalidProjectServiceBean2");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig9() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", "InvalidProjectServiceBean3");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig10() throws Throwable {
        FailureTestHelper.setField(bean, "projectManagerNamespace", "InvalidProjectServiceBean4");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * <p>
     * Test for <code>initialize</code> method.
     * </p>
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig11() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", null);

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig12() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", " ");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig13() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverFile", "invalid");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig14() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", null);

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig15() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", " ");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig16() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "invalid");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig17() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever1");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig18() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever2");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig19() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever3");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>initialize</code> method.
     * 
     * <p>
     * Tests it against the invalid configuration, expects ProjectServiceBeanConfigurationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testInitialize_InvalidConfig20() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetrieverNamespace", "InvalidUserMappingRetriever4");

        checkProjectServiceBeanConfigurationException();
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_InvalidArguments1() throws Throwable {
        try {
            bean.createProject(null, new Client());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_InvalidArguments2() throws Throwable {
        try {
            bean.createProject(FailureTestHelper.createProject(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "projectManager", null);

        try {
            bean.createProject(FailureTestHelper.createProject(), new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.createProject(FailureTestHelper.createProject(), new Client());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.createProject(FailureTestHelper.createProject(), new Client());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_Errors1() throws Throwable {
        MockProjectManager.throwIAE(true);

        try {
            bean.createProject(FailureTestHelper.createProject(), new Client());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCreateProject_Errors2() throws Throwable {
        MockProjectManager.throwPME(true);

        try {
            bean.createProject(FailureTestHelper.createProject(), new Client());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_InvalidArguments1() throws Throwable {
        try {
            bean.updateProject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "projectManager", null);

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_Errors1() throws Throwable {
        MockProjectManager.throwIAE(true);

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testUpdateProject_Errors2() throws Throwable {
        MockProjectManager.throwPME(true);

        try {
            bean.updateProject(FailureTestHelper.createProject());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_InvalidArguments1() throws Throwable {
        try {
            bean.deleteProject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "projectManager", null);

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_Errors1() throws Throwable {
        MockProjectManager.throwIAE(true);

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>deleteProject</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testDeleteProject_Errors2() throws Throwable {
        MockProjectManager.throwPME(true);

        try {
            bean.deleteProject(FailureTestHelper.createProject());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidArguments1()
        throws Throwable {
        try {
            bean.setProjectStatus(null, new ProjectStatus());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidArguments2()
        throws Throwable {
        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidStates1() throws Throwable {
        FailureTestHelper.setField(bean, "projectManager", null);

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid states, expects IllegalStateException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidStates2() throws Throwable {
        FailureTestHelper.setField(bean, "userMappingRetriever", null);

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidAuthorizations1()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        FailureTestHelper.setField(bean, "clientAndProjectUserRole", "");

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidAuthorizations2()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the invalid authorizations, expects AuthorizationFailedException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_InvalidAuthorizations3()
        throws Throwable {
        FailureTestHelper.setField(bean, "administratorRole", "");
        MockUserMappingRetriever.setFail(true);

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("AuthorizationFailedException should be thrown.");
        } catch (AuthorizationFailedException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_Errors1() throws Throwable {
        MockProjectManager.throwIAE(true);

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }

    /**
     * Test for <code>setProjectStatus</code> method.
     * 
     * <p>
     * Tests it against the errors, expects ProjectServiceException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testSetProjectStatus_Errors2() throws Throwable {
        MockProjectManager.throwPME(true);

        try {
            bean.setProjectStatus(FailureTestHelper.createProject(), new ProjectStatus());
            fail("ProjectServiceException should be thrown.");
        } catch (ProjectServiceException ex) {
            // success
        }
    }
}
