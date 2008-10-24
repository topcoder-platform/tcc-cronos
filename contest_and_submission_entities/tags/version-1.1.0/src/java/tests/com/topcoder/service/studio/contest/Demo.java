/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.contest;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Demo for one entity class <code>{@link FilePath}</code> is given. All other entities can be used in a similar
 * fashion.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Demo showing the CRUD operations on <code>{@link FilePath}</code> entity.
     * </p>
     */
    public void testDemo() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            FilePath entity = new FilePath();
            entity.setModifyDate(new Date());
            entity.setPath("path");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            FilePath persisted = (FilePath) HibernateUtil.getManager()
                    .find(FilePath.class, entity.getFilePathId());

            // update the entity
            entity.setPath("new path");
            HibernateUtil.getManager().merge(entity);

            persisted = (FilePath) HibernateUtil.getManager().find(FilePath.class, entity.getFilePathId());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }

    }
}
