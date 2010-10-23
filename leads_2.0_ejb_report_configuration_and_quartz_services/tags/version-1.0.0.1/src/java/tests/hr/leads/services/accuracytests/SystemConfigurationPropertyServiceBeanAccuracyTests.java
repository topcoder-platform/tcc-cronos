/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.ejb.BaseReportConfigurationServiceBean;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceBean;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceLocal;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceRemote;
import hr.leads.services.model.PipelineCycleStatus;
import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * <p>
 * This class is the accuracy tests for class
 * <code>SystemConfigurationPropertyServiceBean</code>
 * </p>
 *
 * @author ReportServiceBean
 * @version 1.0
 */
public class SystemConfigurationPropertyServiceBeanAccuracyTests extends AccuracyBaseTest {

    /**
     * Represents the logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger("name");

    /**
     * <p>
     * Represents the SystemConfigurationPropertyServiceBean instance for accuracy test.
     * </p>
     */
    private SystemConfigurationPropertyServiceBean bean;

    /**
     * <p>
     * Represents the entity manager for accuracy test.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        clearDB();

        entityManager = createEntityManager();
        setupTest(entityManager);

        bean = createServiceBean();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        bean = null;
        entityManager = null;
        clearDB();
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testCtor1() {
        SystemConfigurationPropertyServiceBean local = new SystemConfigurationPropertyServiceBean();

        assertNotNull(local);
        assertTrue(local instanceof BaseReportConfigurationServiceBean);
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testCtor2() {
        SystemConfigurationPropertyServiceBean local = new SystemConfigurationPropertyServiceBean();

        assertNotNull(local);
        assertTrue(local instanceof SystemConfigurationPropertyServiceLocal);
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testCtor3() {
        SystemConfigurationPropertyServiceBean local = new SystemConfigurationPropertyServiceBean();

        assertNotNull(local);
        assertTrue(local instanceof SystemConfigurationPropertyServiceRemote);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSystemConfigurationPropertyValue()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSystemConfigurationPropertyValueAccuracy1() throws Exception {
        String value = bean.getSystemConfigurationPropertyValue("name");
        assertEquals("value", value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSystemConfigurationPropertyValue()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetSystemConfigurationPropertyValueAccuracy2() throws Exception {
        String value = bean.getSystemConfigurationPropertyValue("invalid_name");

        assertNull(value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSystemConfigurationPropertyValue()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetSystemConfigurationPropertyValueAccuracy1() throws Exception {
        entityManager.getTransaction().begin();
        bean.setSystemConfigurationPropertyValue("name", "new_value");
        entityManager.getTransaction().commit();

        String value = bean.getSystemConfigurationPropertyValue("name");

        assertEquals("new_value", value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSystemConfigurationPropertyValue()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetSystemConfigurationPropertyValueAccuracy2() throws Exception {
        entityManager.getTransaction().begin();
        bean.setSystemConfigurationPropertyValue("new_name", "new_value");
        entityManager.getTransaction().commit();

        String value = bean.getSystemConfigurationPropertyValue("new_name");

        assertEquals("new_value", value);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllSystemConfigurationPropertyValues()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllSystemConfigurationPropertyValuesAccuracy1() throws Exception {
        List<SystemConfigurationProperty> list = bean.getAllSystemConfigurationPropertyValues();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("name", list.get(0).getName());
        assertEquals("value", list.get(0).getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllSystemConfigurationPropertyValues()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllSystemConfigurationPropertyValuesAccuracy2() throws Exception {
        entityManager.getTransaction().begin();
        bean.setSystemConfigurationPropertyValue("new_name", "new_value");
        entityManager.getTransaction().commit();

        List<SystemConfigurationProperty> list = bean.getAllSystemConfigurationPropertyValues();

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("name", list.get(0).getName());
        assertEquals("new_name", list.get(1).getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllSystemConfigurationPropertyValues()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllSystemConfigurationPropertyValuesAccuracy3() throws Exception {
        clearDB();
        List<SystemConfigurationProperty> list = bean.getAllSystemConfigurationPropertyValues();

        assertNotNull(list);
        assertEquals(0, list.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>updatePipelineCycleStatus()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePipelineCycleStatusAccuracy1() throws Exception {
        entityManager.getTransaction().begin();
        bean.updatePipelineCycleStatus(PipelineCycleStatus.OPEN);
        entityManager.getTransaction().commit();

        PipelineCycleStatus status = bean.getPipelineCycleStatus();

        assertNotNull(status);
        assertEquals(PipelineCycleStatus.OPEN, status);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updatePipelineCycleStatus()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdatePipelineCycleStatusAccuracy2() throws Exception {
        entityManager.getTransaction().begin();
        bean.updatePipelineCycleStatus(PipelineCycleStatus.CLOSED);
        entityManager.getTransaction().commit();

        PipelineCycleStatus status = bean.getPipelineCycleStatus();

        assertNotNull(status);
        assertEquals(PipelineCycleStatus.CLOSED, status);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPipelineCycleStatus()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPipelineCycleStatusAccuracy1() throws Exception {
        assertNull(bean.getPipelineCycleStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPipelineCycleStatus()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetPipelineCycleStatusAccuracy2() throws Exception {
        entityManager.getTransaction().begin();
        bean.updatePipelineCycleStatus(PipelineCycleStatus.OPEN);
        entityManager.getTransaction().commit();

        PipelineCycleStatus status = bean.getPipelineCycleStatus();

        assertNotNull(status);
        assertEquals(PipelineCycleStatus.OPEN, status);
    }

    /**
     * <p>
     * Creates the service bean for accuracy test.
     * </p>
     *
     * @return the created system bean
     * @throws Exception if any error occurs
     */
    private SystemConfigurationPropertyServiceBean createServiceBean() throws Exception {
        SystemConfigurationPropertyServiceBean bean = new SystemConfigurationPropertyServiceBean();

        setField(SystemConfigurationPropertyServiceBean.class, bean, "entityManager", entityManager);
        setField(SystemConfigurationPropertyServiceBean.class, bean, "pipelineCycleStatusPropertyName",
            "pipelineCycleStatus");
        setField(BaseReportConfigurationServiceBean.class, bean, "logger", LOGGER);

        return bean;
    }
}
