/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.JPATestBase;
import hr.leads.services.LeadsServiceConfigurationException;
import hr.leads.services.LeadsServiceException;
import hr.leads.services.SystemConfigurationPropertyService;
import hr.leads.services.model.PipelineCycleStatus;
import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * All tests for <code>SystemConfigurationPropertyServiceBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SystemConfigurationPropertyServiceBeanTest extends JPATestBase {

    /**
     * <p>
     * Represents the SystemConfigurationPropertyServiceBean for unit tests.
     * </p>
     */
    private SystemConfigurationPropertyServiceBean systemConfigurationPropertyServiceBean = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        openSession();

        systemConfigurationPropertyServiceBean =
            (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


        clearTables();

        // insert the object for test
        SystemConfigurationProperty property = new SystemConfigurationProperty();
        property.setName("UnitTestProperty");
        property.setValue("UnitTestValue");
        insertObject(property);

        closeSession();

    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        openSession();
        clearTables();
        closeSession();

        super.tearDown();

    }

    /**
     * <p>
     * Tests constructor: {@link SystemConfigurationPropertyServiceBean# SystemConfigurationPropertyServiceBean()}.
     * </p>
     *
     * <p>
     * Tests if the instance can be successfully created and it has correct hierarchy.
     * </p>
     */
    public void testSystemConfigurationPropertyServiceBean() {
        assertNotNull("the instance of SystemConfigurationPropertyServiceBean should not be null.",
                systemConfigurationPropertyServiceBean);
        assertTrue("Incorrect hierarchy.",
                systemConfigurationPropertyServiceBean instanceof  SystemConfigurationPropertyService);
        assertTrue("Incorrect hierarchy.",
                systemConfigurationPropertyServiceBean instanceof SystemConfigurationPropertyServiceBean);
        assertTrue("Incorrect hierarchy.",
                systemConfigurationPropertyServiceBean instanceof SystemConfigurationPropertyServiceLocal);
        assertTrue("Incorrect hierarchy.",
                systemConfigurationPropertyServiceBean instanceof SystemConfigurationPropertyServiceRemote);
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#getSystemConfigurationPropertyValue(String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the property exists.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSystemConfigurationPropertyValueExist() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            String value = systemConfigurationPropertyServiceBean
                    .getSystemConfigurationPropertyValue("UnitTestProperty");


            assertNotNull("The property should be not null.", value);
            assertEquals("value incorrect.", "UnitTestValue", value);

        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#getSystemConfigurationPropertyValue(String)}.
     * </p>
     *
     * <p>
     * Throws LeadsServiceException if the property is duplicated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSystemConfigurationPropertyValueDuplicated() throws Exception {

        openSession();
        // insert another one
        SystemConfigurationProperty property = new SystemConfigurationProperty();
        property.setName("UnitTestProperty");
        property.setValue("NewValue");
        insertObject(property);

        try {


            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            systemConfigurationPropertyServiceBean
                    .getSystemConfigurationPropertyValue("UnitTestProperty");


            fail("LeadsServiceException should thrown.");


        } catch (LeadsServiceException e) {
            // success
        } finally {
            closeSession();
        }

        openSession();
        systemConfigurationPropertyServiceBean =
            (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");
        List<SystemConfigurationProperty> properties =
            systemConfigurationPropertyServiceBean.getAllSystemConfigurationPropertyValues();
        int count = 0;
        for (SystemConfigurationProperty configurationProperty : properties) {
            if (configurationProperty.getName().equals("UnitTestProperty")) {
                count++;
            }
        }
        assertTrue(count > 1);
        closeSession();
    }


    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#getSystemConfigurationPropertyValue(String)}.
     * </p>
     *
     * <p>
     * Accuracy test if the property not exists.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSystemConfigurationPropertyValueNotExist() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            String value = systemConfigurationPropertyServiceBean
                    .getSystemConfigurationPropertyValue("NotExist");

            assertNull("The property should be null.", value);
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String, String)}.
     * </p>
     *
     * <p>
     * Accuracy test for the existed property, the value should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetSystemConfigurationPropertyValueAlreadyExist() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            beginTransaction();
            systemConfigurationPropertyServiceBean
                    .setSystemConfigurationPropertyValue("UnitTestProperty", "NewValue");
            commitTransaction();

            // checks the value
            String value = systemConfigurationPropertyServiceBean.getSystemConfigurationPropertyValue(
                    "UnitTestProperty");
            assertNotNull("Should not be null.", value);
            assertEquals("should be updated.", value, "NewValue");
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String, String)}.
     * </p>
     *
     * <p>
     * If add failed, LeadsServiceException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetSystemConfigurationPropertyValueAddFailed() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest(
                        "systemConfigurationPropertyService");


            //  beginTransaction();
            closeSession();
            systemConfigurationPropertyServiceBean.setSystemConfigurationPropertyValue(
                    "UnitTestProperty2",
                        "TooLooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooog");
            // commitTransaction();
            fail("Exception should be thrown.");
        } catch (LeadsServiceException e) {
            // success
            // rollbackTransaction();
        } finally {
            // closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String, String)}.
     * </p>
     *
     * <p>
     * If update failed, LeadsServiceException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetSystemConfigurationPropertyValueAddUpdate() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest(
                        "systemConfigurationPropertyService");


            //  beginTransaction();
            closeSession();
            systemConfigurationPropertyServiceBean.setSystemConfigurationPropertyValue(
                    "UnitTestProperty",
                        "TooLooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooog");
            // commitTransaction();
            fail("Exception should be thrown.");
        } catch (LeadsServiceException e) {
            // success
            // rollbackTransaction();
        } finally {
            // closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String, String)}.
     * </p>
     *
     * <p>
     * Accuracy test for the for not existed property, the new property should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetSystemConfigurationPropertyValueNotExist() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            beginTransaction();
            systemConfigurationPropertyServiceBean
                    .setSystemConfigurationPropertyValue("NotExist", "NewValue");
            commitTransaction();

            // checks the value
            String value = systemConfigurationPropertyServiceBean.getSystemConfigurationPropertyValue("NotExist");
            assertNotNull("Should not be null.", value);
            assertEquals("should be updated.", value, "NewValue");
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Test the method: {@link SystemConfigurationPropertyServiceBean#getAllSystemConfigurationPropertyValues()}.
     * </p>
     *
     * <p>
     * Accuracy tests to check if all the values can returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllSystemConfigurationPropertyValues() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            beginTransaction();
            systemConfigurationPropertyServiceBean
                    .setSystemConfigurationPropertyValue("NotExist", "NewValue");
            commitTransaction();

            // checks the value
            List<SystemConfigurationProperty> value =
                systemConfigurationPropertyServiceBean.getAllSystemConfigurationPropertyValues();
            assertNotNull("Should not be null.", value);
            // should have two values
            assertEquals("Should have 2 values", 2, value.size());
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#updatePipelineCycleStatus(PipelineCycleStatus)}.
     * </p>
     *
     * <p>
     * Accuracy tests for the updatePipelineCycleStatus.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePipelineCycleStatusAccuracy() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            beginTransaction();
            systemConfigurationPropertyServiceBean
                    .updatePipelineCycleStatus(PipelineCycleStatus.OPEN);
            commitTransaction();

            // checks the value
            String value =
                systemConfigurationPropertyServiceBean.getSystemConfigurationPropertyValue("CycleStatusProperty");
            assertNotNull("Should not be null.", value);
            // should have two values
            assertEquals("status should be open", "OPEN", value);
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#updatePipelineCycleStatus(PipelineCycleStatus)}.
     * </p>
     *
     * <p>
     * Accuracy tests for the updatePipelineCycleStatus.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPipelineCycleStatusAccuracy() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            beginTransaction();
            systemConfigurationPropertyServiceBean
                    .updatePipelineCycleStatus(PipelineCycleStatus.OPEN);
            commitTransaction();

            // checks the value
            PipelineCycleStatus value =
                systemConfigurationPropertyServiceBean.getPipelineCycleStatus();

            assertNotNull("Should not be null.", value);
            // should have two values
            assertEquals("status should be open", "OPEN", value.name());
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#updatePipelineCycleStatus(PipelineCycleStatus)}.
     * </p>
     *
     * <p>
     * If the status name is invalid , throws LeadsServiceException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPipelineCycleStatusInvalidStatus() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");


            beginTransaction();
            systemConfigurationPropertyServiceBean.setSystemConfigurationPropertyValue(
                    "CycleStatusProperty", "invalidStatus");
            commitTransaction();

            systemConfigurationPropertyServiceBean.getPipelineCycleStatus();

            fail("LeadsServiceException should be thrown.");
        } catch (LeadsServiceException e) {
            // success
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#updatePipelineCycleStatus(PipelineCycleStatus)}.
     * </p>
     *
     * <p>
     * Accuracy tests for the updatePipelineCycleStatus.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPipelineCycleStatusAccuracyNull() throws Exception {
        try {
            openSession();

            systemConfigurationPropertyServiceBean =
                (SystemConfigurationPropertyServiceBean) lookupForTest("systemConfigurationPropertyService");

            // checks the value
            PipelineCycleStatus value =
                systemConfigurationPropertyServiceBean.getPipelineCycleStatus();

            assertNull("Should not be null.", value);
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * If EntityManager is null, LeadsServiceConfigurationException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedEntityManagerNull() throws Exception {
        try {
            systemConfigurationPropertyServiceBean
                = new SystemConfigurationPropertyServiceBean();
            Field field = systemConfigurationPropertyServiceBean.getClass().getDeclaredField("entityManager");
            field.setAccessible(true);
            //field.set(systemConfigurationPropertyServiceBean, getEntityManager());

            field = systemConfigurationPropertyServiceBean.getClass().getDeclaredField(
                    "pipelineCycleStatusPropertyName");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean, new String("123"));

            systemConfigurationPropertyServiceBean.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should be thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * If PipelineCycleStatusPropertyName is null, LeadsServiceConfigurationException should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedPipelineCycleStatusPropertyNameNull() throws Exception {
        try {
            openSession();
            systemConfigurationPropertyServiceBean
                = new SystemConfigurationPropertyServiceBean();
            Field field = systemConfigurationPropertyServiceBean.getClass().getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean, getEntityManager());

            field = systemConfigurationPropertyServiceBean.getClass().getDeclaredField(
                    "pipelineCycleStatusPropertyName");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean, null);

            systemConfigurationPropertyServiceBean.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should be thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        } finally {
            closeSession();
        }

        try {
            openSession();
            systemConfigurationPropertyServiceBean
                = new SystemConfigurationPropertyServiceBean();
            Field field = systemConfigurationPropertyServiceBean.getClass().getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean, getEntityManager());

            field = systemConfigurationPropertyServiceBean.getClass().getDeclaredField(
                    "pipelineCycleStatusPropertyName");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean, "   ");

            systemConfigurationPropertyServiceBean.afterBeanInitialized();
            fail("LeadsServiceConfigurationException should be thrown.");
        } catch (LeadsServiceConfigurationException e) {
            // success
        } finally {
            closeSession();
        }
    }

    /**
     * <p>
     * Tests method: {@link SystemConfigurationPropertyServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * Accuracy test, no exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedAccuracy() throws Exception {
        try {
            openSession();
            systemConfigurationPropertyServiceBean = new SystemConfigurationPropertyServiceBean();
            Field field = systemConfigurationPropertyServiceBean.getClass()
                    .getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean,
                    getEntityManager());

            field = systemConfigurationPropertyServiceBean.getClass()
                    .getDeclaredField("pipelineCycleStatusPropertyName");
            field.setAccessible(true);
            field.set(systemConfigurationPropertyServiceBean, "123");

            systemConfigurationPropertyServiceBean.afterBeanInitialized();

        } catch (LeadsServiceConfigurationException e) {
            fail("LeadsServiceConfigurationException should not be thrown.");
        } finally {
            closeSession();
        }
    }



}
