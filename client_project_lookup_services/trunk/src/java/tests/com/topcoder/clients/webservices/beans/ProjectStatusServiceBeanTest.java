/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;

import junit.framework.TestCase;

import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ProjectStatusServiceException;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test for ProjectStatusServiceBean class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectStatusServiceBeanTest extends TestCase {
    /**
     * <p>
     * Bean instance used in tests.
     * </p>
     */
    private ProjectStatusServiceBean bean;

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    protected void setUp() throws Exception {
        bean = new ProjectStatusServiceBean();
        bean.setVerboseLogging(true);

        // Initialize required resources
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "ProjectStatusServiceBean");
        Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        // Initialize SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);
        this.setField("sessionContext", context);

        // Set default manager behavior
        ProjectManagerMock.setFail(false);
    }

    /**
     * <p>
     * Clears the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * <p>
     * Accuracy test for <code>ProjectStatusServiceBean()</code> constructor.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testProjectStatusServiceBean_Accuracy() throws Exception {
        assertNotNull("ProjectStatusServiceBean should be created.", new ProjectStatusServiceBean());
    }

    /**
     * <p>
     * Accuracy test for <code>initialize()</code> method with valid manager configuration and absent log name
     * resource.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Accuracy1() throws Exception {
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "ProjectStatusServiceBean");
        Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        assertNotNull("The ProjectManager should be created.", this.getField("projectManager"));
        assertNotNull("The Log should be created.", this.getField("log"));
    }

    /**
     * <p>
     * Accuracy test for <code>initialize()</code> method with valid manager configuration and log name
     * resource.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Accuracy2() throws Exception {
        this.setField("projectManagerFile", "config.properties");
        this.setField("projectManagerNamespace", "ProjectStatusServiceBean");
        this.setField("logName", "ProjectStatusServiceBean");
        Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        assertNotNull("The ProjectManager should be created.", this.getField("projectManager"));
        assertNotNull("The Log should be created.", this.getField("log"));
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if the required resources are missing.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure1() throws Exception {
        try {
            // Set empty resource
            this.setField("projectManagerFile", "");
            Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ProjectStatusServiceBeanConfigurationException if the required resources are missing.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ProjectStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ProjectStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if configuration namespace could not be found.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure2() throws Exception {
        try {
            this.setField("projectManagerFile", "broken.properties");
            this.setField("projectManagerNamespace", "ProjectStatusServiceBean");
            Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ProjectStatusServiceBeanConfigurationException if configuration namespace"
                    + " could not be found.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ProjectStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ProjectStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if configuration file could not be found.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure3() throws Exception {
        try {
            this.setField("projectManagerFile", "absent_file");
            this.setField("projectManagerNamespace", "ProjectStatusServiceBean");
            Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ProjectStatusServiceBeanConfigurationException if configuration file could not be found.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ProjectStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ProjectStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceBeanConfigurationException
     * </p>
     * <p>
     * Because of: if manager couldn't be instantiated.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testInitialize_Failure4() throws Exception {
        try {
            this.setField("projectManagerFile", "config.properties");
            this.setField("projectManagerNamespace", "BrokenProjectStatusServiceBean");
            Method method = ProjectStatusServiceBean.class.getDeclaredMethod("initialize", new Class[0]);
            method.setAccessible(true);
            method.invoke(bean, new Object[0]);
            fail("Expected ProjectStatusServiceBeanConfigurationException if manager couldn't be instantiated.");
        } catch (InvocationTargetException e) {
            // expect
            assertTrue("ProjectStatusServiceBeanConfigurationException should be thrown", e
                    .getTargetException() instanceof ProjectStatusServiceBeanConfigurationException);
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createProjectStatus(ProjectStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateProjectStatus_Accuracy() throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setDescription("Test entity");
        ProjectStatus result = bean.createProjectStatus(status);

        assertNotNull("ProjectStatus object should be returned.", result);
        assertEquals("Status description should be " + status.getDescription(), status.getDescription(),
                result.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>createProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the project status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateProjectStatus_Failure1() throws Exception {
        try {
            bean.createProjectStatus(null);
            fail("Expected IllegalArgumentException if the project status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>createProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager is not set (or is set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateProjectStatus_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.createProjectStatus(new ProjectStatus());
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>createProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateProjectStatus_Failure3() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.createProjectStatus(new ProjectStatus());
            fail("Expected ProjectStatusServiceException if error occurs during creating.");
        } catch (ProjectStatusServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>updateProjectStatus(ProjectStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateProjectStatus_Accuracy() throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setDescription("Updated description");
        ProjectStatus result = bean.updateProjectStatus(status);

        assertNotNull("ProjectStatus object should be returned.", result);
        assertEquals("Status description should be " + status.getDescription(), status.getDescription(),
                result.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>updateProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the project status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateProjectStatus_Failure1() throws Exception {
        try {
            bean.updateProjectStatus(null);
            fail("Expected IllegalArgumentException if the project status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>updateProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager is not set (or is set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateProjectStatus_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.updateProjectStatus(new ProjectStatus());
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>updateProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testUpdateProjectStatus_Failure3() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.updateProjectStatus(new ProjectStatus());
            fail("Expected ProjectStatusServiceException if error occurs during updating.");
        } catch (ProjectStatusServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>deleteProjectStatus(ProjectStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteProjectStatus_Accuracy() throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setDescription("To delete");
        ProjectStatus result = bean.deleteProjectStatus(status);

        assertNotNull("ProjectStatus object should be returned.", result);
        assertEquals("Status description should be " + status.getDescription(), status.getDescription(),
                result.getDescription());
    }

    /**
     * <p>
     * Failure test for <code>deleteProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if the project status is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteProjectStatus_Failure1() throws Exception {
        try {
            bean.deleteProjectStatus(null);
            fail("Expected IllegalArgumentException if the project status is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>deleteProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if the projectManager is not set (or is set to a null value).
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteProjectStatus_Failure2() throws Exception {
        try {
            setField("projectManager", null);
            bean.deleteProjectStatus(new ProjectStatus());
            fail("Expected IllegalStateException if the projectManager is not set.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test for <code>deleteProjectStatus(ProjectStatus status)</code> method.
     * </p>
     * <p>
     * Expect: ProjectStatusServiceException
     * </p>
     * <p>
     * Because of: if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDeleteProjectStatus_Failure3() throws Exception {
        try {
            ProjectManagerMock.setFail(true);
            bean.deleteProjectStatus(new ProjectStatus());
            fail("Expected ProjectStatusServiceException if error occurs during deleting.");
        } catch (ProjectStatusServiceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test for <code>isVerboseLogging()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testIsVerboseLogging_Accuracy() throws Exception {
        assertTrue("verboseLogging should be true", bean.isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for <code>setVerboseLogging(boolean verboseLogging)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSetVerboseLogging_Accuracy() throws Exception {
        bean.setVerboseLogging(false);
        assertFalse("verboseLogging should be false", bean.isVerboseLogging());
    }

    /**
     * <p>
     * Accuracy test for <code>getLog()</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testGetLog_Accuracy() throws Exception {
        assertNotNull("Logger field shouldn't be null.", bean.getLog());
    }

    /**
     * <p>
     * Accuracy test for <code>setLog(Log log)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testSetLog_Accuracy() throws Exception {
        Log log = LogManager.getLog("TestLogger");
        bean.setLog(log);
        assertEquals("setLog returns invalid logger.", log, bean.getLog());
    }

    /**
     * <p>
     * Set the value into private field.
     * </p>
     *
     * @param fieldName
     *            The name of field.
     * @param fieldValue
     *            The field value to be set.
     * @throws Exception
     *             to JUnit
     */
    private void setField(String fieldName, Object fieldValue) throws Exception {
        Field field = ProjectStatusServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }

    /**
     * <p>
     * Get the value from private field.
     * </p>
     *
     * @param fieldName
     *            The name of field.
     * @return the value of the field.
     * @throws Exception
     *             to JUnit
     */
    private Object getField(String fieldName) throws Exception {
        Field field = ProjectStatusServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this.bean);
    }

}
