/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import junit.framework.TestCase;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ejb3.GenericEJB3DAO;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Base test cases used for accuracy tests.
 * </p>
 *
 * <p>
 * Changes in 1.2: Added the createProjectWithClientAndBudget, addProjectWorker and addProjectWorker
 * helper methods.
 * </p>
 *
 * @param <T>
 *            the type param for ejb bean
 * @param <V>
 *            the type param for the entity
 *
 * @author cyberjag, akinwale
 * @version 1.2
 * @since 1.0
 */
public abstract class BaseTest<T extends GenericEJB3DAO<V, Long>, V extends AuditableEntity> extends TestCase {

    /**
     * An EntityManager instance used in tests.
     */
    private static EntityManager entityManager;

    /**
     * The clear table sql.
     */
    private static final String[] clearSQLs = new String[] { "delete from project_budget_audit",
        "delete from project_manager", "delete from project_worker", "delete from user_account",
        "delete from project", "delete from client", "delete from client_status", "delete from project_status",
        "delete from company"};

    /**
     * The searchBundleManagerNamespace used in tests.
     */
    private static final String searchBundleManagerNamespace = "com.topcoder.search.builder";

    /**
     * The configFileName used in tests.
     */
    private static final String configFileName = "test_files/accuracy_tests/test.properties";

    /**
     * The configNamespace used in tests.
     */
    private static final String configNamespace = "ACCURACY_CPE_DAO";

    /**
     * Represents the bean to test.
     */
    private T testBean;

    /**
     * Represents the main entity related to this bean.
     */
    private V entity;

    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to junit
     */
    @Override
    protected void setUp() throws Exception {
        clearDatabase();
        createTestBean();
        createEntity();
        setPrivateField(GenericEJB3DAO.class, testBean, "searchBundleManagerNamespace",
                searchBundleManagerNamespace);
        setPrivateField(GenericEJB3DAO.class, testBean, "configFileName", configFileName);
        setPrivateField(GenericEJB3DAO.class, testBean, "configNamespace", configNamespace);

        clearConfig();
        addConfig("config.xml");
        addConfig("hibernateSearchStrategyConfig.xml");

        invoke(GenericEJB3DAO.class, testBean, "initialize");
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception
     *             to junit
     */
    @Override
    protected void tearDown() throws Exception {
        clearConfig();
        clearDatabase();
    }

    /**
     * Tests the {@link GenericEJB3DAO#save(AuditableEntity)} for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testSave() throws Exception {
        Date date = new Date();
        entity.setModifyDate(date);
        V saved = testBean.save(entity);
        assertEquals("Failed to save", saved.getModifyDate(), date);
    }

    /**
     * Tests the {@link GenericEJB3DAO#retrieveAll()} for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAll() throws Exception {
        List<V> list = testBean.retrieveAll();
        assertEquals("Failed to retrieveAll", 1, list.size());
        assertEntity(list.get(0), entity);
    }

    /**
     * Tests the {@link GenericEJB3DAO#retrieveById(java.io.Serializable)} for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveById() throws Exception {
        V persisted = testBean.retrieveById(entity.getId());
        assertEntity(persisted, entity);
    }

    /**
     * Tests the {@link GenericEJB3DAO#delete(AuditableEntity)} for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testDelete() throws Exception {
        testBean.delete(entity);
        assertTrue("Failed to delete.", testBean.retrieveById(entity.getId()).isDeleted());
        // save it back for further tests.
        entity.setDeleted(false);
        testBean.save(entity);
    }

    /**
     * Tests the {@link GenericEJB3DAO#getEntityBeanType()} for accuracy.
     */
    public void testGetEntityBeanType() {
        assertEquals("Failed to get the entityBeanType", entity.getClass(), getTestBean().getEntityBeanType());
    }

    /**
     * Asserts the actual and expected entity properties.
     *
     * @param actual
     *            the actual entity
     * @param expected
     *            the expected entity
     */
    protected void assertEntity(AuditableEntity actual, AuditableEntity expected) {
        assertEquals("Failed to get the entity #getId mismatch", actual.getId(), expected.getId());
        assertEquals("Failed to get the entity #getCreateDate mismatch", actual.getCreateDate(), expected
                .getCreateDate());
        assertEquals("Failed to get the entity #getCreateUsername mismatch", actual.getCreateUsername(),
                expected.getCreateUsername());
        assertEquals("Failed to get the entity #getModifyDate mismatch", actual.getModifyDate(), expected
                .getModifyDate());
        assertEquals("Failed to get the entity #getModifyUsername mismatch", actual.getModifyUsername(),
                expected.getModifyUsername());
        assertEquals("Failed to get the entity #getName mismatch", actual.getName(), expected.getName());
    }

    /**
     * Creates the bean. The concrete test classes should actually create the bean.
     */
    protected abstract void createTestBean();

    /**
     * Creates the entity. The concrete test classes should actually create the entity.
     */
    protected abstract void createEntity();

    /**
     * Sets the bean.
     *
     * @param testBean
     *            the test bean
     */
    public void setTestBean(T testBean) {
        this.testBean = testBean;
    }

    /**
     * Sets the entity.
     *
     * @param entity
     *            the entity
     */
    public void setEntity(V entity) {
        this.entity = entity;
    }

    /**
     * Gets the testBean.
     *
     * @return the testBean
     */
    public T getTestBean() {
        return testBean;
    }

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public V getEntity() {
        return entity;
    }

    /**
     * Clear database.
     */
    private void clearDatabase() {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        for (String sql : clearSQLs) {
            em.createNativeQuery(sql).executeUpdate();
        }
        em.getTransaction().commit();
    }

    /**
     * Create client.
     *
     * @param id
     *            client id
     * @return client created
     */
    protected Client createClient(long id) {
        Client client = new Client();
        setAuditableEntity(client);
        ClientStatus clientStatus = createClientStatus(10);
        client.setClientStatus(clientStatus);
        client.setCodeName("codename");
        Company company = createCompany(100);
        client.setCompany(company);
        client.setEndDate(new Date());
        client.setPaymentTermsId(10);
        client.setSalesTax(1.1);
        client.setStartDate(new Date());
        client.setId(id);
        client.setDeleted(false);

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        return client;
    }

    /**
     * Create project with client.
     *
     * @param id
     *            the id of project
     * @param client
     *            the client to set
     * @return created project
     */
    protected Project createProjectWithClient(long id, Client client) {
        Project project = new Project();
        setAuditableEntity(project);
        project.setActive(true);
        project.setClient(client);
        ProjectStatus projectStatus = createProjectStatus(100000);
        project.setProjectStatus(projectStatus);
        project.setId(id);
        project.setCompany(client.getCompany());
        project.setDeleted(false);

        // persist object
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(project);
        entityManager.getTransaction().commit();

        return project;
    }

    /**
     * Create project with client and budget.
     *
     * @param id
     *            the id of project
     * @param client
     *            the client to set
     * @param budget
     *            the budget to set for the project
     * @return created project
     */
    protected Project createProjectWithClientAndBudget(long id, Client client, double budget) {
        Project project = new Project();
        setAuditableEntity(project);
        project.setActive(true);
        project.setClient(client);
        ProjectStatus projectStatus = createProjectStatus(100000);
        project.setProjectStatus(projectStatus);
        project.setId(id);
        project.setCompany(client.getCompany());
        project.setBudget(budget);
        project.setDeleted(false);

        // persist object
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(project);
        entityManager.getTransaction().commit();

        return project;
    }

    /**
     * Create ProjectStatus.
     *
     * @param id
     *            the ProjectStatus id
     * @return ProjectStatus created
     */
    protected ProjectStatus createProjectStatus(long id) {
        ProjectStatus projectStatus = entityManager.find(ProjectStatus.class, id);
        if (projectStatus != null) {
            return projectStatus;
        }
        projectStatus = new ProjectStatus();
        setAuditableEntity(projectStatus);
        projectStatus.setDescription("des");
        projectStatus.setId(id);

        // persist object
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(projectStatus);
        entityManager.getTransaction().commit();

        projectStatus.setId(id);
        return projectStatus;
    }

    /**
     * Create company.
     *
     * @param id
     *            the company id
     * @return company created
     */
    protected Company createCompany(long id) {
        Company company = entityManager.find(Company.class, id);
        if (company != null) {
            return company;
        }
        company = new Company();

        setAuditableEntity(company);
        company.setId(id);
        company.setPasscode("passcode");

        // persist object
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(company);
        entityManager.getTransaction().commit();

        company.setId(id);
        return company;
    }

    /**
     * Set fields of auditableEntity.
     *
     * @param auditableEntity
     *            the auditableEntity to set
     */
    private static void setAuditableEntity(AuditableEntity auditableEntity) {
        auditableEntity.setCreateUsername("accuracyReviewer");
        auditableEntity.setModifyUsername("accuracyReviewer");
        auditableEntity.setCreateDate(new Date());
        auditableEntity.setModifyDate(new Date());
        auditableEntity.setName("name");
    }

    /**
     * Create ClientStatus.
     *
     * @param id
     *            the ClientStatus id
     * @return ClientStatus created
     */
    protected ClientStatus createClientStatus(long id) {
        ClientStatus clientStatus = entityManager.find(ClientStatus.class, id);
        if (clientStatus != null) {
            return clientStatus;
        }
        clientStatus = new ClientStatus();

        setAuditableEntity(clientStatus);
        clientStatus.setDescription("description");
        clientStatus.setId(id);
        // persist object
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(clientStatus);
        entityManager.getTransaction().commit();

        return clientStatus;
    }

    /**
     * Get EntityManager.
     *
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            // create entityManager
            try {
                Ejb3Configuration cfg = new Ejb3Configuration();
                cfg.configure("hibernate.cfg.xml");

                EntityManagerFactory emf = cfg.buildEntityManagerFactory();
                entityManager = emf.createEntityManager();

            } catch (Exception e) {
            }
        }

        entityManager.clear();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        return entityManager;
    }

    /**
     * Remove all the namespace.
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    private static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Add the namespace.
     *
     * @param filename
     *            the config filename
     * @throws Exception
     *             to JUnit
     */
    public static void addConfig(String filename) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(filename);
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
    @SuppressWarnings("unchecked")
    private static void setPrivateField(Class type, Object instance, String name, Object value) {
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
     * Invokes the function.
     *
     * @param type
     *            the class which the method belongs to
     * @param instance
     *            the instance which the method belongs to
     * @param name
     *            the name of the method
     */
    @SuppressWarnings("unchecked")
    private static void invoke(Class type, Object instance, String name) {
        Method method = null;

        try {
            // get the reflection of the field
            method = type.getDeclaredMethod(name);
            method.setAccessible(true);
            method.invoke(instance);
        } catch (Exception e) {
            // ignore
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Adds the user with given user name to the billing projects that are specified with project IDs.
     * </p>
     * <p>
     * Note that if the user doesn't exist, a new record will be created.
     * </p>
     *
     * @param userName
     *            the user name.
     * @param billingProjectId
     *            the project ID to which the user is added
     * @throws Exception  pass any unexpected exception to JUnit.
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    protected void addProjectWorker(String userName, long billingProjectId) throws Exception {
        Long userAccountId = getUserAccountId(userName, true);
        String queryStr = "select project_id from project_worker "
                + "where project_id =:projectId and user_account_id=:userAccountId";
        Query searchQuery = entityManager.createNativeQuery(queryStr);
        // Insert a record to project_manager table for each specified project id
        Query projectManagerInsertQuery = entityManager.createNativeQuery(
              "insert into project_worker (project_id, user_account_id,"
            + " pay_rate, cost, active, start_date, end_date, creation_date, creation_user, modification_date"
            + ", modification_user) values (:projectId, :userAccountId, 0, 0, :active, CURRENT, CURRENT, CURRENT"
            + ", 'System', CURRENT, 'System')");

        // add checking, if user/project is already in the table, we DO NOT insert.
        searchQuery.setParameter("projectId", billingProjectId);
        searchQuery.setParameter("userAccountId", userAccountId);
        List result = searchQuery.getResultList();
        if (result != null && result.size() == 0) {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            projectManagerInsertQuery.setParameter("projectId", billingProjectId);
            projectManagerInsertQuery.setParameter("userAccountId", userAccountId);
            projectManagerInsertQuery.setParameter("active", 1);
            projectManagerInsertQuery.executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    /**
     * <p>
     * Gets the user account id according to given user name.
     * </p>
     *
     * @param userName
     *            the username to find the user account id.
     * @param isAdd
     *            whether to add a new user account if not found.
     * @return the matched user account id.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    protected Long getUserAccountId(String userName, boolean isAdd) throws Exception {
        // Query the user account id.
        Query userQuery = entityManager.createNativeQuery("select user_account_id from user_account"
            + " where user_name = :userName");
        userQuery.setParameter("userName", userName);
        Long userAccountId = null;
        try {
            userAccountId = Long.parseLong(userQuery.getSingleResult().toString());
        } catch (NoResultException nre) {
            // No matching record in user_account table, should insert new records
        }

        if (null == userAccountId && isAdd) {
            Random random = new Random();
            userAccountId = random.nextLong();

            // and get the userAccountId
            Query insertUserQuery = entityManager.createNativeQuery("insert into user_account"
                + " (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user,"
                + " modification_date, modification_user) values "
                + "(:userAccountId, 1, 1, :userName, '', CURRENT, '', CURRENT, '')");
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            insertUserQuery.setParameter("userAccountId", userAccountId);
            insertUserQuery.setParameter("userName", userName);
            int counts = insertUserQuery.executeUpdate();
            entityManager.getTransaction().commit();
            if (counts != 1) {
                // cannot insert into user_account table, throw DAOException
                throw new DAOException("Cannot insert user data into user_account!");
            }
        }

        return userAccountId;
    }
}
