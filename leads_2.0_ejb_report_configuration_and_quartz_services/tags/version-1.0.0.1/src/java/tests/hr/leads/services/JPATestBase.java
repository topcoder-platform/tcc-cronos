/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.ejb.ReportServiceBean;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import junit.framework.TestCase;
/**
 * <p>
 * Base tests proving helper methods for JPA tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPATestBase extends TestCase {

    /**
     * <p>
     * Represents the array of related tables in database.
     * </p>
     */
    private static final String[] TABLES = {"SystemConfigurationProperty"};

    /**
     * <p>
     * Represents the mock up container for unit tests.
     * </p>
     */
    private DummyEjbContainer container = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        initDefaultContainer();
    }


    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Looks for a ejb via the name.
     * </p>
     * @param name the name of the ejb.
     * @return the ejb.
     *
     * @throws Exception to JUNit.
     */
    protected Object lookupForTest(String name) throws Exception {
        return container.getEjb(name);
    }

    /**
     * <p>
     * Opens a new session.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void openSession() throws Exception {
        container.openSession("persistenceUnit");
    }

    /**
     * <p>
     * Closes the session.
     * </p>
     */
    protected void closeSession() {
        container.closeSession();
    }

    /**
     * <p>
     * Begins the transaction.
     * </p>
     */
    protected void beginTransaction() {
        container.getEntityManager().getTransaction().begin();
    }

    /**
     * <p>
     * Commits a transaction.
     * </p>
     */
    protected void commitTransaction() {
        container.getEntityManager().getTransaction().commit();
    }

    /**
     * <p>
     * Rolls back the transaction.
     * </p>
     */
    protected void rollbackTransaction() {
        container.getEntityManager().getTransaction().commit();
    }

    /**
     * <p>
     * Clears all the related tables.
     * </p>
     */
    protected void clearTables() {
        beginTransaction();
        for (String table : TABLES) {
            Query query = container.getEntityManager().createQuery("delete from " + table);
            query.executeUpdate();
        }
        commitTransaction();
    }

    /**
     * <p>
     * Inserts a object to database.
     * </p>
     *
     * @param obj the object to insert.
     * @param <T> the type of the object.
     */
    protected <T> void insertObject(T obj) {
        beginTransaction();
        container.getEntityManager().persist(obj);
        commitTransaction();
    }

    /**
     * <p>
     * Gets the entity manager.
     * </p>
     *
     * @return the entity manager.
     */
    protected EntityManager getEntityManager() {
        return container.getEntityManager();
    }

    /**
     * <p>
     * Initializes the default container.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void initDefaultContainer() throws Exception {
        container = DummyContainerFactory.getInstance().getContainer("default");
        if (container != null) {
            return;
        }

        container  = new DummyEjbContainer();
        Map<String, EjbInfo> containerConfig = new HashMap<String, EjbInfo>();

        EjbInfo ejbConfig = getReportServiceEjbConfig();
        containerConfig.put("reportService", ejbConfig);

        ejbConfig = getEmployeeProfileServiceEjbConfig();
        containerConfig.put("employeeProfileService", ejbConfig);

        ejbConfig = getLdapUserServiceEjbConfig();
        containerConfig.put("ldapUserService", ejbConfig);

        ejbConfig = getSystemConfigurationPropertyServiceEjbConfig();
        containerConfig.put("systemConfigurationPropertyService", ejbConfig);

        container.setEjbMap(containerConfig);

        DummyContainerFactory.getInstance().setContainer("default", container);
    }

    /**
     * <p>
     * Gets the ldap user service ejb config.
     * </p>
     * @return the ejb config.
     */
    private EjbInfo getLdapUserServiceEjbConfig() {
        EjbInfo ejbInfo = new EjbInfo();
        ejbInfo.setEjbName("ldapUserService");
        ejbInfo.setEjbClass(LDAPUserServiceMockBean.class.getName());
        return ejbInfo;
    }

    /**
     * <p>
     * Gets the employee profile service ejb config.
     * </p>
     *
     * @return the ejb config.
     */
    private EjbInfo getEmployeeProfileServiceEjbConfig() {
        EjbInfo ejbInfo = new EjbInfo();
        ejbInfo.setEjbName("employeeProfileService");
        ejbInfo.setEjbClass(EmployeeProfileServiceMockBean.class.getName());
        return ejbInfo;
    }

    /**
     * <p>
     * Gets the report service ejb config.
     * </p>
     *
     * @return the ejb config.
     */
    private EjbInfo getReportServiceEjbConfig() {
        EjbInfo ejbInfo = new EjbInfo();
        ejbInfo.setEjbName("reportService");
        ejbInfo.setEjbClass(ReportServiceBean.class.getName());
        List<EjbResource> ejbResources = new ArrayList<EjbResource>();

        EjbResource submittedStatusName = new EjbResource();
        submittedStatusName.setResourceName("submittedStatusName");
        submittedStatusName.setResourceType("java.lang.String");
        submittedStatusName.setResourceValue("SubmittedStatus");
        ejbResources.add(submittedStatusName);

        EjbResource notSubmittedStatusName = new EjbResource();
        notSubmittedStatusName.setResourceName("notSubmittedStatusName");
        notSubmittedStatusName.setResourceType("java.lang.String");
        notSubmittedStatusName.setResourceValue("NotSubmitttedStatus");
        ejbResources.add(notSubmittedStatusName);

        EjbResource logName = new EjbResource();
        logName.setResourceName("loggerName");
        logName.setResourceType("java.lang.String");
        logName.setResourceValue(ReportServiceBean.class.getName());
        ejbResources.add(logName);


        ejbInfo.setEjbResources(ejbResources);
        return ejbInfo;
    }

    /**
     * <p>
     * Gets the system configuration property service ejb config.
     * </p>
     *
     * @return the ejb config.
     */
    private EjbInfo getSystemConfigurationPropertyServiceEjbConfig() {
        EjbInfo ejbInfo = new EjbInfo();
        ejbInfo.setEjbName("systemConfigurationPropertyService");
        ejbInfo.setEjbClass(SystemConfigurationPropertyServiceBean.class.getName());

        List<EjbResource> ejbResources = new ArrayList<EjbResource>();
        EjbResource pipelineCycleStatusPropertyName = new EjbResource();
        pipelineCycleStatusPropertyName.setResourceName("pipelineCycleStatusPropertyName");
        pipelineCycleStatusPropertyName.setResourceType("java.lang.String");
        pipelineCycleStatusPropertyName.setResourceValue("CycleStatusProperty");
        ejbResources.add(pipelineCycleStatusPropertyName);

        EjbResource logName = new EjbResource();
        logName.setResourceName("loggerName");
        logName.setResourceType("java.lang.String");
        logName.setResourceValue(SystemConfigurationPropertyServiceBean.class.getName());
        ejbResources.add(logName);

        ejbInfo.setEjbResources(ejbResources);
        return ejbInfo;
    }
}

/**
 * <p>
 * A dummy factory to create containers.
 *
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
class DummyContainerFactory {
    /**
     * <p>
     * Represents the singleton instance.
     * </p>
     */
    private static DummyContainerFactory instance;

    /**
     * <p>
     * Represents the mapping from name to container..
     * </p>
     */
    private Map<String, DummyEjbContainer> mapping =
        new HashMap<String, DummyEjbContainer>();

    /**
     * <p>
     * Creates a instance of factory.
     * </p>
     */
    private DummyContainerFactory() {
        // do nothing
    }

    /**
     * <p>
     * Gets the instance.
     * </p>
     * @return the instance.
     */
    static DummyContainerFactory getInstance() {
        if (instance == null) {
            instance = new DummyContainerFactory();
        }
        return instance;
    }

    /**
     * <p>
     * Gets the container via name.
     * </p>
     * @param name the name.
     * @return the container.
     */
    DummyEjbContainer getContainer(String name) {
        return mapping.get(name);
    }

    /**
     * <p>
     * Sets the container.
     * </p>
     * @param name the name of the container.
     * @param container the container.
     */
    void setContainer(String name, DummyEjbContainer container) {
        mapping.put(name, container);
    }
}
