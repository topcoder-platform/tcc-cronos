/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.stresstests;

import hr.leads.services.ejb.BaseReportConfigurationServiceBean;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceBean;
import hr.leads.services.model.PipelineCycleStatus;
import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.TestCase;

/**
 * <p>
 * Stress unit test for {@link SystemConfigurationPropertyServiceBean} class.
 * </p>
 * 
 * @author elkhawajah
 * @version 1.0
 */
public class SystemConfigurationPropertyServiceBeanStressUnitTest extends TestCase {
    /**
     * Represents the entity manager for testing.
     */
    private EntityManager entityManager;
    /**
     * Represents the entity manager factory for creating entity manager.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Represents the instance used in tests.
     */
    private SystemConfigurationPropertyServiceBean instance;

    /**
     * <p>
     * Clears all the related tables.
     * </p>
     */
    protected void clearDB() {
        Query query = getEntityManager().createQuery("delete from SystemConfigurationProperty");
        query.executeUpdate();
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        getEntityManager().getTransaction().begin();
        clearDB();

        // Initialize instance and set fields reflectively
        instance = new SystemConfigurationPropertyServiceBean();
        injectField(instance, BaseReportConfigurationServiceBean.class, "loggerName", "StressTests");
        injectField(instance, instance.getClass(), "pipelineCycleStatusPropertyName", "pipelineCycleStatus");
        injectField(instance, instance.getClass(), "entityManager", getEntityManager());
        callAfterBeanInitialized(instance);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        clearDB();
        getEntityManager().getTransaction().rollback();
    }

    /**
     * <p>
     * Test all CRUD operations of the service.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testAllCRUDOperations() throws Exception {
        long start = System.currentTimeMillis();

        List<SystemConfigurationProperty> expected = new ArrayList<SystemConfigurationProperty>();
        final String name = "UnitTestProperty";
        final String value = "UnitTestValue";

        for (int idx = 0; idx < 1000; ++idx) {
            SystemConfigurationProperty property = new SystemConfigurationProperty();
            property.setName(String.format("%s %d", name, idx));
            property.setValue(String.format("%s %d", value, idx));
            instance.setSystemConfigurationPropertyValue(property.getName(), property.getValue());
            expected.add(property);
        }

        List<SystemConfigurationProperty> actual = new ArrayList<SystemConfigurationProperty>(instance
                .getAllSystemConfigurationPropertyValues());
        Collections.sort(actual, new Comparator<SystemConfigurationProperty>() {
            public int compare(SystemConfigurationProperty o1, SystemConfigurationProperty o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Collections.sort(expected, new Comparator<SystemConfigurationProperty>() {
            public int compare(SystemConfigurationProperty o1, SystemConfigurationProperty o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        assertEquals(expected, actual);

        assertNull("getPipelineCycleStatus method is incorrect.", instance.getPipelineCycleStatus());
        for (int idx = 0; idx < expected.size(); ++idx) {
            assertEquals(expected.get(idx).getValue(), instance.getSystemConfigurationPropertyValue(expected.get(idx)
                    .getName()));
        }

        instance.updatePipelineCycleStatus(PipelineCycleStatus.OPEN);
        assertEquals("getPipelineCycleStatus method is incorrect.", PipelineCycleStatus.OPEN, instance
                .getPipelineCycleStatus());

        instance.updatePipelineCycleStatus(PipelineCycleStatus.CLOSED);
        assertEquals("getPipelineCycleStatus method is incorrect.", PipelineCycleStatus.CLOSED, instance
                .getPipelineCycleStatus());

        System.out.println("Testing CRUD operations of SystemConfigurationPropertyServiceBean took "
                + (System.currentTimeMillis() - start) + " milliseconds.");
    }

    /**
     * Call afterBeanInitialized method of the given instance.
     * 
     * @param obj
     *            the object to invoke the method.
     */
    private void callAfterBeanInitialized(Object obj) {
        try {
            Method method = obj.getClass().getDeclaredMethod("afterBeanInitialized", null);
            method.setAccessible(true);
            method.invoke(obj, null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get entity manager instance.
     * 
     * @return the entity manager instance.
     */
    private EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
            entityManager = entityManagerFactory.createEntityManager();
        }

        return entityManager;
    }

    /**
     * Inject instance field reflectively.
     * 
     * @param obj
     *            the object to set it's field.
     * @param clazz
     *            the class type of the object.
     * @param fieldName
     *            the field name.
     * @param fieldValue
     *            the field value.
     */
    @SuppressWarnings("unchecked")
    private void injectField(Object obj, Class clazz, String fieldName, Object fieldValue) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assert equality of the given lists.
     * 
     * @param expected
     *            the expected list.
     * @param actual
     *            the actual list.
     */
    public static void assertEquals(List<SystemConfigurationProperty> expected,
            List<SystemConfigurationProperty> actual) {
        assertEquals("The given lists are not equal.", expected.size(), actual.size());
        for (int idx = 0; idx < expected.size(); ++idx) {
            assertEquals(expected.get(idx), actual.get(idx));
        }
    }

    /**
     * Assert equality of the given entities.
     * 
     * @param expected
     *            the expected entity.
     * @param actual
     *            the actual entity.
     */
    public static void assertEquals(SystemConfigurationProperty expected, SystemConfigurationProperty actual) {
        // assertEquals("The given entities are not equal.", expected.getId(), actual.getId());
        assertEquals("The given entities are not equal.", expected.getName(), actual.getName());
        assertEquals("The given entities are not equal.", expected.getValue(), actual.getValue());
    }
}