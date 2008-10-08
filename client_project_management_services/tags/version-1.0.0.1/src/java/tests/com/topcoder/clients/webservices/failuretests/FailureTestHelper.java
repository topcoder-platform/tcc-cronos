/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.failuretests.mock.MockEntityManager;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;


/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class FailureTestHelper {
    /**
     * <p>
     * Represents the EntityManager.
     * </p>
     */
    private static EntityManager entityManager;

    /**
     * <p>
     * Creates a new instance of <code>FailureTestHelper</code> class. The private constructor prevents the creation of
     * a new instance.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Sets the field value of the given instance. The field name is also given.
     * </p>
     *
     * @param instance the instance whose field value is retrieved.
     * @param field the field name.
     * @param value the field value to be set.
     *
     * @throws Exception if error occurs when retrieving the value.
     */
    public static void setField(Object instance, String field, Object value)
        throws Exception {
        Field info = instance.getClass().getDeclaredField(field);
        boolean accessFlag = info.isAccessible();

        try {
            info.setAccessible(true);
            info.set(instance, value);
        } finally {
            info.setAccessible(accessFlag);
        }
    }

    /**
     * <p>
     * Getter to the entityManager.
     * </p>
     *
     * @return instance of current entityManager
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = new MockEntityManager();
        }

        return entityManager;
    }

    /**
     * Creates the project instance for testing.
     *
     * @return the project.
     */
    public static Project createProject() {
        Project project = new Project();
        project.setParentProjectId(2);

        return project;
    }
}
