/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * The super class of all persistence related class.
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public abstract class TestBase extends TestCase {

    /**
     * <p>
     * Represents the default company id for inserting dummy user account.
     * </p>
     *
     * @since 1.2
     */
    protected static final long DEFAULT_COMPANY_ID = 1;

    /**
     * <p>
     * The query to insert dummy user account.
     * </p>
     *
     * @since 1.2
     */
    protected static final String INSERT_DUMMY_USER_ACCOUNT = "insert into user_account"
            + " (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user,"
            + " modification_date, modification_user) values "
            + "(:userAccountId, " + DEFAULT_COMPANY_ID + ", 1, :userName, '', CURRENT, '', CURRENT, '')";

    /**
     * <p>
     * The query to insert project manager relationship.
     * </p>
     *
     * @since 1.2
     */
    protected static final String INSERT_PROJECT_WORKER = "insert into project_worker (project_id, user_account_id,"
            + " pay_rate, cost, active, start_date, end_date, creation_date, creation_user, modification_date"
            + ", modification_user) values (:projectId, :userAccountId, 0, 0, :active, CURRENT, CURRENT, CURRENT"
            + ", 'System', CURRENT, 'System')";

    /**
     * <p>
     * The query to insert project manager relationship.
     * </p>
     *
     * @since 1.2
     */
    protected static final String INSERT_PROJECT_MANAGER = "insert into project_manager (project_id, user_account_id,"
            + " pay_rate, cost, active, creation_date, creation_user, modification_date, modification_user) "
            + "values (:projectId, :userAccountId, 0, 0, :active, CURRENT, 'System', CURRENT, 'System')";

    /**
     * <p>
     * The key to get IDGenerator instance.
     * </p>
     *
     * @since 1.2
     */
    private static final String ID_KEY = "com.topcoder.clients.model.User";

    /**
     * <p>
     * The query to get the user account id by user name.
     * </p>
     *
     * @since 1.2
     */
    private static final String SEARCH_USER_ACCOUNT_ID_BY_USRENAME = "select user_account_id from user_account"
            + " where user_name = :userName";

    /**
     * An EntityManager instance used in tests.
     */
    private static EntityManager entityManager;

    /**
     * The clear table sql.
     */
    private String[] clearSQLs = new String[] {"delete from project_budget_audit", "delete from project_contest_fee",
        "delete from project_manager", "delete from project_worker", "delete from user_account", "delete from project",
        "delete from client", "delete from client_status", "delete from project_status", "delete from company"};

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        getEntityManager();
        clearDatabase();
    }

    /**
     * <p>
     * tearDown() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        clearDatabase();
    }

    /**
     * Clear database.
     */
    private void clearDatabase() {
        EntityManager myEntityManager = getEntityManager();
        for (String sql : clearSQLs) {
            myEntityManager.createNativeQuery(sql).executeUpdate();
        }
    }

    /**
     * Get EntityManager.
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
     * Create project with client.
     *
     * @param id
     *                the id of project
     * @param client
     *                the client to set
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
        project.setPOBoxNumber("123");
        project.setDescription("Desc");
        project.setCompany(client.getCompany());

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into project (project_id, project_status_id, client_id, "
                        + "company_id,name,active,sales_tax,po_box_number,payment_terms_id,"
                        + "description,creation_date,creation_user,modification_date,"
                        + "modification_user,is_deleted,is_manual_prize_setting)"
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int idx = 1;
        query.setParameter(idx++, project.getId());
        query.setParameter(idx++, project.getProjectStatus().getId());
        query.setParameter(idx++, project.getClient().getId());
        query.setParameter(idx++, project.getCompany().getId());
        query.setParameter(idx++, project.getName());
        query.setParameter(idx++, project.isActive());
        query.setParameter(idx++, project.getSalesTax());
        query.setParameter(idx++, project.getPOBoxNumber());
        query.setParameter(idx++, project.getPaymentTermsId());
        query.setParameter(idx++, project.getDescription());
        query.setParameter(idx++, project.getCreateDate());
        query.setParameter(idx++, project.getCreateUsername());
        query.setParameter(idx++, project.getModifyDate());
        query.setParameter(idx++, project.getModifyUsername());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 0);
        query.executeUpdate();

        return project;
    }

    /**
     * Create project with client.
     *
     * @param id
     *            the id of project
     * @param client
     *            the client to set
     * @param budget
     *            the budget of the project.
     * @return created project
     * @since 1.2
     */
    protected Project createProjectWithInitialBudget(long id, Client client, double budget) {
        Project project = new Project();
        setAuditableEntity(project);
        project.setActive(true);
        project.setClient(client);
        ProjectStatus projectStatus = createProjectStatus(100000);
        project.setProjectStatus(projectStatus);
        project.setId(id);
        project.setPOBoxNumber("123");
        project.setDescription("Desc");
        project.setCompany(client.getCompany());
        project.setBudget(budget);

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into project (project_id, project_status_id, client_id, "
                        + "company_id,name,active,sales_tax,po_box_number,payment_terms_id,"
                        + "description,creation_date,creation_user,modification_date,"
                        + "modification_user,is_deleted,is_manual_prize_setting,budget)"
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int idx = 1;
        query.setParameter(idx++, project.getId());
        query.setParameter(idx++, project.getProjectStatus().getId());
        query.setParameter(idx++, project.getClient().getId());
        query.setParameter(idx++, project.getCompany().getId());
        query.setParameter(idx++, project.getName());
        query.setParameter(idx++, project.isActive());
        query.setParameter(idx++, project.getSalesTax());
        query.setParameter(idx++, project.getPOBoxNumber());
        query.setParameter(idx++, project.getPaymentTermsId());
        query.setParameter(idx++, project.getDescription());
        query.setParameter(idx++, project.getCreateDate());
        query.setParameter(idx++, project.getCreateUsername());
        query.setParameter(idx++, project.getModifyDate());
        query.setParameter(idx++, project.getModifyUsername());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 0);
        query.setParameter(idx, project.getBudget());
        query.executeUpdate();

        return project;
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
        Query userQuery = entityManager.createNativeQuery(SEARCH_USER_ACCOUNT_ID_BY_USRENAME);
        userQuery.setParameter("userName", userName);
        Long userAccountId = null;
        try {
            userAccountId = Long.parseLong(userQuery.getSingleResult().toString());
        } catch (NoResultException nre) {
            // No matching record in user_account table, should insert new records
        }

        if (null == userAccountId && isAdd) {
            // generate the user account id.
            IDGenerator idGen = IDGeneratorFactory.getIDGenerator(ID_KEY);

            userAccountId = idGen.getNextID();

            // and get the userAccountId
            Query insertUserQuery = entityManager.createNativeQuery(INSERT_DUMMY_USER_ACCOUNT);
            insertUserQuery.setParameter("userAccountId", userAccountId);
            insertUserQuery.setParameter("userName", userName);
            int counts = insertUserQuery.executeUpdate();
            if (counts != 1) {
                // cannot insert into user_account table, throw DAOException
                throw new DAOException("Cannot insert user data into user_account!");
            }
        }

        return userAccountId;
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
        Query projectManagerInsertQuery = entityManager.createNativeQuery(INSERT_PROJECT_WORKER);

        // add checking, if user/project is already in the table, we DO NOT insert.
        searchQuery.setParameter("projectId", billingProjectId);
        searchQuery.setParameter("userAccountId", userAccountId);
        List result = searchQuery.getResultList();
        if (result != null && result.size() == 0) {
            projectManagerInsertQuery.setParameter("projectId", billingProjectId);
            projectManagerInsertQuery.setParameter("userAccountId", userAccountId);
            projectManagerInsertQuery.setParameter("active", 1);
            projectManagerInsertQuery.executeUpdate();
        }
    }

    /**
     * <p>
     * Checks the project budget audit.
     * </p>
     * @param userName the username to insert the audit.
     * @param projectId the related project id.
     * @param amount the change amount
     * @return the matched size of the records.
     */
    protected int checkProjectBudgetAudit(String userName, long projectId, double amount) {
        String queryStr = "select project_budget_audit_id from project_budget_audit "
                + "where project_id =:projectId and creation_user=:username and changed_amount=:amount";
        Query searchQuery = entityManager.createNativeQuery(queryStr);

        // add checking, if user/project is already in the table, we DO NOT insert.
        searchQuery.setParameter("projectId", projectId);
        searchQuery.setParameter("username", userName);
        searchQuery.setParameter("amount", amount);
        return searchQuery.getResultList().size();

    }


    /**
     * Set child project.
     * @param parent the parent id
     * @param child the child id
     */
    protected void setChildProject(long parent, long child) {
        Query query = entityManager
                .createNativeQuery("update project set parent_project_id=? where project_id=?");
        query.setParameter(1, parent);
        query.setParameter(2, child);
        query.executeUpdate();
        entityManager.flush();
    }

    /**
     * Create ProjectStatus.
     * @param id the ProjectStatus id
     * @return ProjectStatus created
     */
    protected ProjectStatus createProjectStatus(long id) {
        ProjectStatus projectStatus = entityManager.find(ProjectStatus.class,
                id);
        if (projectStatus != null) {
            return projectStatus;
        }
        projectStatus = new ProjectStatus();
        setAuditableEntity(projectStatus);
        projectStatus.setDescription("des");
        projectStatus.setId(id);

        // persist object
        entityManager.persist(projectStatus);
        entityManager.flush();
        projectStatus.setId(id);
        return projectStatus;
    }

    /**
     * Create client.
     * @param id client id
     * @return client created
     */
    protected Client createClient(long id) {
        Client client = new Client();
        setAuditableEntity(client);
        ClientStatus clientStatus = createClientStatus(10);
        client.setClientStatus(clientStatus);
        client.setCodeName("codename");
        client.setName("CLIENT");
        Company company = createCompany(100);
        client.setCompany(company);
        client.setEndDate(new Date());
        client.setPaymentTermsId(10);
        client.setSalesTax(1.1);
        client.setStartDate(new Date());
        client.setId(id);
        client.setDeleted(false);

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into client "
                        + "(client_id, client_status_id, is_deleted, payment_term_id,company_id"
                        + ",salestax,start_date,end_date,creation_date,creation_user,modification_date,"
                        + "modification_user,code_name, name) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int idx = 1;
        query.setParameter(idx++, client.getId());
        query.setParameter(idx++, client.getClientStatus().getId());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 10);
        query.setParameter(idx++, company.getId());
        query.setParameter(idx++, client.getSalesTax());
        query.setParameter(idx++, client.getStartDate());
        query.setParameter(idx++, client.getEndDate());
        query.setParameter(idx++, client.getCreateDate());
        query.setParameter(idx++, client.getCreateUsername());
        query.setParameter(idx++, client.getModifyDate());
        query.setParameter(idx++, client.getModifyUsername());
        query.setParameter(idx++, client.getCodeName());
        query.setParameter(idx, client.getName());
        query.executeUpdate();

        return client;
    }

    /**
     * Create client.
     * @param name the client name
     * @param id client id
     * @return client created
     */
    protected Client createClient(String name, long id) {
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
        client.setName(name);

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into client "
                        + "(client_id, client_status_id, is_deleted, payment_term_id,company_id"
                        + ",salestax,start_date,end_date,creation_date,creation_user,modification_date,"
                        + "modification_user,code_name, name) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?, ?)");
        int idx = 1;
        query.setParameter(idx++, client.getId());
        query.setParameter(idx++, client.getClientStatus().getId());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 10);
        query.setParameter(idx++, company.getId());
        query.setParameter(idx++, client.getSalesTax());
        query.setParameter(idx++, client.getStartDate());
        query.setParameter(idx++, client.getEndDate());
        query.setParameter(idx++, client.getCreateDate());
        query.setParameter(idx++, client.getCreateUsername());
        query.setParameter(idx++, client.getModifyDate());
        query.setParameter(idx++, client.getModifyUsername());
        query.setParameter(idx++, client.getCodeName());
        query.setParameter(idx, client.getName());
        query.executeUpdate();

        return client;
    }

    /**
     * Create company.
     * @param id the company id
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
        entityManager.persist(company);
        entityManager.flush();
        company.setId(id);
        return company;
    }

    /**
     * Set fields of auditableEntity.
     * @param auditableEntity the auditableEntity to set
     */
    protected void setAuditableEntity(AuditableEntity auditableEntity) {
        auditableEntity.setCreateUsername("createUser");
        auditableEntity.setModifyUsername("modifyUser");
        auditableEntity.setCreateDate(new Date());
        auditableEntity.setModifyDate(new Date());
        auditableEntity.setName("name");
    }

    /**
     * Create ClientStatus.
     * @param id the ClientStatus id
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
        entityManager.persist(clientStatus);
        entityManager.flush();
        clientStatus.setId(id);
        return clientStatus;
    }
}
