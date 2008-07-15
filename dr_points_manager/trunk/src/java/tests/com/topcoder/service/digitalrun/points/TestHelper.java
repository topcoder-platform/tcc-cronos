/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.service.digitalrun.points.dao.implementations.MockEntityManager;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class for unit test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;
    /**
     * This private constructor prevents the creation of a new instance.
     */
    private TestHelper() {
        // empty
    }

    /**
     * Gets the value of a private field in the given class. The field has the given name. The value
     * is retrieved from the given instance. If the instance is null, the field is a static field.
     * If any error occurs, null is returned.
     *
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be retrieved
     * @return the value of the private field
     */
    public static Object getPrivateField(Class type, Object instance, String name) {
        Field field = null;
        Object obj = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }

        return obj;
    }

    /**
     * Sets the value of a private field in the given class.
     *
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName
     *            config file to set up environment
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadConfig(String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * Get entity manager.
     * @return the entity manager.
     */
    public static EntityManager getEntityManager() {
        if (em == null) {
            EntityManager manager = null;
            try {
                Ejb3Configuration cfg = new Ejb3Configuration();
                EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                manager = emf.createEntityManager();
            } catch (Exception e) {
                // ignore
            }
            em = new MockEntityManager(manager);
            
        }
        return em;
    }
    
    /**
     * Helper method to persist the entity with transaction.
     *
     * @param em
     *            the entity manager
     * @param entity
     *            the entity to persist
     * @return the entity
     */
    public static Object persist(EntityManager em, Object entity) {
        if (em == null) {
            em = getEntityManager();
        }
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    /**
     * Helper method to remove the entity with transaction.
     *
     * @param em
     *            the entity manager
     * @param entity
     *            the entity to persist
     */
    public static void delete(EntityManager em, Object entity) {
        if (em == null) {
            em = getEntityManager();
        }
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        em.remove(entity);
        em.getTransaction().commit();
    }
}
