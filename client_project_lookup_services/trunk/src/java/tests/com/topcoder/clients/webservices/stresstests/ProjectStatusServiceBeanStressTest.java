/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.stresstests;

import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.ProjectManagerMock;
import com.topcoder.clients.webservices.beans.ProjectStatusServiceBean;
import com.topcoder.clients.webservices.beans.SessionContextMock;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Stress test for class <code>ProjectStatusServiceBean</code>.
 *
 * @author PE
 * @version 1.0
 */
public class ProjectStatusServiceBeanStressTest extends TestCase {
    /**
     * <p>
     * Represents the <code>ProjectStatusServiceBean</code> to be tested.
     * </p>
     */
    private ProjectStatusServiceBean bean;

    /**
     * Set up the environment.
     *
     * @throws Exception to JUnit
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
     * Clean up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * Stress Test for the functionality of class <code>ProjectStatusServiceBean</code>.
     *
     * @throws Exception to JUnit
     */
    public void testProjectStatusServiceBean_Stress() throws Exception {
        List<ProjectStatus> entities = new ArrayList<ProjectStatus>();
        Random random = new Random();

        StressTestHelper.start();

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            int v = Math.abs(random.nextInt()) % 3;

            if (v == 0) {
                entities.add(bean.createProjectStatus(this.createProjectStatus()));
            } else if (v == 1) {
                if (!entities.isEmpty()) {
                    entities.set(0, bean.updateProjectStatus(entities.get(0)));
                }
            } else if (v == 2) {
                if (!entities.isEmpty()) {
                    bean.deleteProjectStatus(entities.remove(0));
                }
            }
        }

        StressTestHelper.printResult("testProjectStatusServiceBean_Stress");

        for (ProjectStatus entity : entities) {
            bean.deleteProjectStatus(entity);
        }
    }

    /**
     * Creates the entity for test.
     *
     * @return the entity for test.
     */
    private ProjectStatus createProjectStatus() {
        ProjectStatus status = new ProjectStatus();
        status.setDescription("Test entity");

        return status;
    }

    /**
     * <p>
     * Set the value into private field.
     * </p>
     *
     * @param fieldName The name of field.
     * @param fieldValue The field value to be set.
     *
     * @throws Exception to JUnit
     */
    private void setField(String fieldName, Object fieldValue)
        throws Exception {
        Field field = ProjectStatusServiceBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this.bean, fieldValue);
    }
}
