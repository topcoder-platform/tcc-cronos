/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactException;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectDAO;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;

/**
 * <p>
 * This is default implementation of the <code>ProjectDAO</code>.
 * </p>
 *
 * <p>
 * It supports all the methods needed to manipulate and retrieve a Project in the data store.
 * </p>
 *
 * <p>
 * Note, The Time Tracker Application will be making use of Container managed transaction so
 * transaction management should be provided by the EJB container.
 * </p>
 *
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner. For database access, the Time Tracker
 * Application will be making use of Container managed transactions, so JDBC transactions
 * are NOT used.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectDAO extends BaseDAO implements ProjectDAO {

	/**
     * <p>
     * This is the application space that will be used and provided to the Time Tracker Auditor
     * if an audit is requested.
     * </p>
     */
    public static final String AUDIT_APPLICATION_AREA = "TT_PROJECT";

    /**
     * <p>
     * Represents the sql script to insert a record to project table.
     * </p>
     */
    private static final String INSERT_PROJECT = "insert into project(project_id, name, company_id, "
        + "active, description, sales_tax, payment_terms_id, start_date, end_date, creation_date, "
        + "creation_user, modification_date, modification_user) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to insert a record to client_project table.
     * </p>
     */
    private static final String INSERT_CLIENT_PROJECT = "insert into client_project(client_id, project_id, "
        + "creation_date, creation_user, modification_date, modification_user) values (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to update a record in project table.
     * </p>
     */
    private static final String UPDATE_PROJECT = "update project set name = ?, company_id = ?, "
        + "active = ?, description = ?, sales_tax = ?, payment_terms_id = ?, start_date = ?, end_date = ?, "
        + "creation_date = ?, creation_user = ?, modification_date = ?, modification_user = ? "
        + "where project_id = ?";

    /**
     * <p>
     * Represents the sql script to update a record in client_project table.
     * </p>
     */
    private static final String UPDATE_CLIENT_PROJECT = "update client_project set client_id = ?, "
        + "creation_date = ?, creation_user = ?, modification_date = ?, modification_user = ? where "
        + "project_id = ? and client_id = ?";

    /**
     * <p>
     * Represents the sql script to select records from client_project table.
     * </p>
     */
    private static final String SELECT_CLIENT_PROJECT = "select project_id, client_id from client_project where ";

    /**
     * <p>
     * Represents the sql script to de-active a project record.
     * </p>
     */
    private static final String DELETE_PROJECT = "update project set active = 0 where project_id = ?";

    /**
     * <p>
     * Represents the sql script to select records from project table.
     * </p>
     */
    private static final String SELECT_PROJECT = "select project.project_id, name, company_id, client_id, active, "
        + "description, sales_tax, payment_terms_id, start_date, end_date, project.creation_date, "
        + "project.creation_user, project.modification_date, project.modification_user from project, client_project "
        + "where project.project_id = client_project.project_id";

    /**
     * <p>
     * Represents the sql script to select the contact id for the given user id.
     * </p>
     */
    private static final String SEL_CONTACT_ID = "select contact_id from contact_relation where entity_id"
        + " = ? and contact_type_id = ?";

    /**
     * <p>
     * Represents the sql script to select the address id for the given user id.
     * </p>
     */
    private static final String SEL_ADDRESS_ID = "select address_id from address_relation where entity_id"
        + " = ? and address_type_id = ?";

    /**
     * <p>
     * This is the class provided by the Time Tracker Common component to
     * persist and retrieve the term into/from the data store.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final CommonManager termManager;

    /**
     * <p>
     * This is the class provided by the Time Tracker Contract component to
     * persist and retrieve the Contact and Address into/from the data store.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ContactManager contactManager;

    /**
     * <p>
     * This is the class provided by the Time Tracker Contract component to
     * persist and retrieve the Address into/from the data store.
     * </p>
     *
     * <p>
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final AddressManager addressManager;

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for
     * searching the data store for Projects using this implementation.
     * <p>
     *
     * <p>
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final DbProjectFilterFactory filterFactory;

    /**
     * <p>
     * Constructor that accepts the necessary parameters needed for the <code>DbProjectDAO</code>
     * to function properly.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (if null,then default connection is used)
     * @param idGen The id generator name that is used.
     * @param contactManager The Time Tracker Contact class that is used to persist the contacts.
     * @param addressManager The Time Tracker address class that is used to address
     * @param termManager The Time Tracker Common class that is used to persist the Payment Terms.
     * @param searchBundleManagerNamespace the configuration namespace used to initialize the search bundle manager.
     * @param searchBundleName the name of the search bundle
     * @param auditor The auditor that is going to be used.
     *
     * @throws IllegalArgumentException if any of the parameters (except connName) is null, or if connName,
     * idGen or searchStrategyNamespace is an empty String.
     * @throws ConfigurationException if fails to create the id generator or database search strategy
     */
    public DbProjectDAO(DBConnectionFactory connFactory, String connName, String idGen, ContactManager contactManager,
        AddressManager addressManager, CommonManager termManager, String searchBundleManagerNamespace,
        String searchBundleName, AuditManager auditor) throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleManagerNamespace, searchBundleName, auditor);

        Util.checkNull(idGen, "idGen");
        Util.checkNull(contactManager, "contactManager");
        Util.checkNull(addressManager, "addressManager");
        Util.checkNull(termManager, "termManager");
        Util.checkNull(searchBundleManagerNamespace, "searchStrategyNamespace");
        Util.checkNull(auditor, "auditor");

        this.contactManager = contactManager;
        this.addressManager = addressManager;
        this.termManager = termManager;

        this.filterFactory = new DbProjectFilterFactory();
    }

    /**
     * <p>
     * Adds the specified projects into the persistent store.
     * </p>
     *
     * <p>
     * Unique project ids will automatically be generated and assigned to the projects.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProjects(Project[] projects, boolean audit) throws DataAccessException {
        checkProjects(projects);

        Connection conn = null;
        try {
            conn = getConnection();

            Contact[] contacts = new Contact[projects.length];
            Address[] addresses = new Address[projects.length];

            for (int i = 0; i < projects.length; i++) {
                projects[i].setId(getNextId());
                contacts[i] = projects[i].getContact();
                addresses[i] = projects[i].getAddress();
            }

            // add the contact and address associations
            contactManager.addContacts(contacts, audit);
            addressManager.addAddresses(addresses, audit);

            // add the payment terms
            for (int i = 0; i < projects.length; i++) {
                termManager.addPaymentTerm(projects[i].getTerms());
            }

            List[] params = new List[projects.length];
            List[] clientProjectParams = new List[projects.length];
            for (int i = 0; i < projects.length; i++) {
                params[i] = createInsertParams(projects[i]);
                clientProjectParams[i] = createClientProjectInsertParams(projects[i]);
            }

            // insert all the projects and client projects
            Util.executeBatchUpdate(conn, INSERT_PROJECT, params);
            Util.executeBatchUpdate(conn, INSERT_CLIENT_PROJECT, clientProjectParams);

            // set up the contact and address relationships
            for (int i = 0; i < projects.length; i++) {
                long projectId = projects[i].getId();
                contactManager.associate(contacts[i], projectId, audit);
                addressManager.associate(addresses[i], projectId, audit);
            }

            // perform the audit if necessary
            if (audit) {
                audit(null, projects);
            }

            Util.resetBeanChangedStates(projects);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (ContactException e) {
            throw new DataAccessException("Failed to handle the contact or address relationship.", e);
        } catch (IDGenerationException e) {
            throw new DataAccessException("Failed to get next id.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the project.", e);
        } catch (CommonManagementException e) {
            throw new DataAccessException("Failed to handle the payment terms relationship.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given projects.
     * </p>
     *
     * @param projects the given projects to check
     *
     * @throws IllegalArgumentException if projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     */
    private void checkProjects(Project[] projects) {
        Util.checkNull(projects, "users");
        if (projects.length == 0) {
            throw new IllegalArgumentException("The given users is empty.");
        }

        for (int i = 0; i < projects.length; i++) {
            Util.checkNull(projects[i], "project in projects");

            // null name is not allowed
            if (projects[i].getName() == null) {
                throw new IllegalArgumentException("Some projects have null name.");
            }

            // the company is not set
            if (projects[i].getCompanyId() <= 0) {
                throw new IllegalArgumentException("Some projects have unset company id.");
            }

            // the client id is not set
            if (projects[i].getClientId() <= 0) {
                throw new IllegalArgumentException("Some projects have unset client id.");
            }

            // null address is not allowed
            if (projects[i].getAddress() == null) {
                throw new IllegalArgumentException("Some projects have null address.");
            }

            // the type of the address must be PROJECT
            if (!AddressType.PROJECT.equals(projects[i].getAddress().getAddressType())) {
                throw new IllegalArgumentException("Some projects have non-project address.");
            }

            // null contact is not allowed
            if (projects[i].getContact() == null) {
                throw new IllegalArgumentException("Some projects have null contact.");
            }

            // the type of the contact must be PROJECT
            if (!ContactType.PROJECT.equals(projects[i].getContact().getContactType())) {
                throw new IllegalArgumentException("Some projects have non-project contact.");
            }

            // null contact is not allowed
            if (projects[i].getTerms() == null) {
                throw new IllegalArgumentException("Some projects have null terms.");
            }

            // null start date is not allowed
            if (projects[i].getStartDate() == null) {
                throw new IllegalArgumentException("Some projects have null start date.");
            }

            // null end date is not allowed
            if (projects[i].getEndDate() == null) {
                throw new IllegalArgumentException("Some projects have null end date.");
            }

            // null creation user is not allowed
            if (projects[i].getCreationUser() == null) {
                throw new IllegalArgumentException("Some projects have null creation user.");
            }

            // null creation date is not allowed
            if (projects[i].getCreationDate() == null) {
                throw new IllegalArgumentException("Some projects have null creation date.");
            }

            // null modification user is not allowed
            if (projects[i].getModificationUser() == null) {
                throw new IllegalArgumentException("Some projects have null modification user.");
            }

            // null modification date is not allowed
            if (projects[i].getModificationDate() == null) {
                throw new IllegalArgumentException("Some projects have null modification date.");
            }
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to insert a project.
     * </p>
     *
     * @param project the <code>Project</code> to insert to database
     * @return the parameters needed to insert a project.
     */
    private List createInsertParams(Project project) {
        List params = new ArrayList();

        params.add(new Long(project.getId()));
        params.add(project.getName());
        params.add(new Long(project.getCompanyId()));
        params.add(new Long(project.isActive() ? 1 : 0));

        // description can be null in the database
        if (project.getDescription().length() == 0) {
            params.add(new SqlType(Types.VARCHAR));
        } else {
            params.add(project.getDescription());
        }

        params.add(new Double(project.getSalesTax()));
        params.add(new Long(project.getTerms().getId()));
        params.add(project.getStartDate());
        params.add(project.getEndDate());
        params.add(project.getCreationDate());
        params.add(project.getCreationUser());
        params.add(project.getModificationDate());
        params.add(project.getModificationUser());

        return params;
    }

    /**
     * <p>
     * This method creates the parameters needed to insert the client and project relationship
     * to database.
     * </p>
     *
     * @param project the <code>Project</code> to insert its relationship with client to database
     * @return the parameters needed to insert the client-project relationship.
     */
    private List createClientProjectInsertParams(Project project) {
        List params = new ArrayList();

        params.add(new Long(project.getClientId()));
        params.add(new Long(project.getId()));
        params.add(project.getCreationDate());
        params.add(project.getCreationUser());
        params.add(project.getModificationDate());
        params.add(project.getModificationUser());

        return params;
    }

    /**
     * <p>
     * This method audit the given projects using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * For detail information, you can refer to <{@link DbProjectDAO#audit(Project, Project)}.
     * </p>
     *
     * @param oldProjects the project instances after update
     * @param newProjects the project instances before update
     *
     * @throws AuditManagerException if fails to audit the projects.
     */
    private void audit(Project[] oldProjects, Project[] newProjects) throws AuditManagerException {
        Project[] projects = (newProjects == null) ? oldProjects : newProjects;
        for (int i = 0; i < projects.length; i++) {
            audit(oldProjects == null ? null : oldProjects[i], newProjects == null ? null : newProjects[i]);
        }
    }

    /**
     * <p>
     * This method audit the given project using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * The <code>oldProject</code> is the project instance before update and the <code>newProject</code>
     * is the project instance after update.
     * </p>
     *
     * <p>
     * For INSERT operation, <code>oldProject</code> should be null while <code>newProject</code> should not be null.
     * For UPDATE operation, both <code>oldProject</code> and <code>newProject</code> should not be null.
     * For DELETE operation, <code>oldProject</code> should not be null while <code>newProject</code> should be null.
     * </p>
     *
     * @param newProject the project instance after update
     * @param oldProject the project instance before update
     *
     * @throws AuditManagerException if fails to audit the project.
     */
    private void audit(Project oldProject, Project newProject) throws AuditManagerException {
        Project project = (newProject == null) ? oldProject : newProject;
        AuditHeader header = new AuditHeader();
        header.setCompanyId(project.getCompanyId());
        header.setApplicationArea(ApplicationArea.TT_PROJECT);
        header.setTableName("project");
        header.setEntityId(project.getId());
        header.setCreationUser(project.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        header.setProjectId(project.getId());

        List auditDetails = new ArrayList();
        if (newProject != null && oldProject == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(Util.createAuditDetail("project_id", null, String.valueOf(newProject.getId())));
            auditDetails.add(Util.createAuditDetail("name", null, newProject.getName()));
            auditDetails.add(Util.createAuditDetail("company_id", null, String.valueOf(newProject.getCompanyId())));
            auditDetails.add(Util.createAuditDetail("active", null, String.valueOf(newProject.getActive())));
            auditDetails.add(Util.createAuditDetail("description", null, newProject.getDescription()));
            auditDetails.add(Util.createAuditDetail("start_date", null, newProject.getStartDate().toString()));
            auditDetails.add(Util.createAuditDetail("end_date", null, newProject.getEndDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_date", null, newProject.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", null, newProject.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", null,
                newProject.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", null, newProject.getModificationUser()));
        } else if (newProject == null && oldProject != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldProject.getId()), null));
            auditDetails.add(Util.createAuditDetail("name", oldProject.getName(), null));
            auditDetails.add(Util.createAuditDetail("company_id", String.valueOf(oldProject.getCompanyId()), null));
            auditDetails.add(Util.createAuditDetail("active", String.valueOf(oldProject.getActive()), null));
            auditDetails.add(Util.createAuditDetail("description", oldProject.getDescription(), null));
            auditDetails.add(Util.createAuditDetail("start_date", oldProject.getStartDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("end_date", oldProject.getEndDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProject.getCreationDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProject.getCreationUser(), null));
            auditDetails.add(Util.createAuditDetail("modification_date", oldProject.getModificationDate().toString(),
                null));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProject.getModificationUser(), null));
        } else {
            // for update operation
            header.setActionType(AuditType.UPDATE);
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldProject.getId()),
                String.valueOf(newProject.getId())));
            auditDetails.add(Util.createAuditDetail("name", oldProject.getName(), newProject.getName()));
            auditDetails.add(Util.createAuditDetail("company_id", String.valueOf(oldProject.getCompanyId()),
                String.valueOf(newProject.getCompanyId())));
            auditDetails.add(Util.createAuditDetail("active", String.valueOf(oldProject.getActive()),
                String.valueOf(newProject.getActive())));
            auditDetails.add(Util.createAuditDetail("description", oldProject.getDescription(),
                newProject.getDescription()));
            auditDetails.add(Util.createAuditDetail("start_date", oldProject.getStartDate().toString(),
                newProject.getStartDate().toString()));
            auditDetails.add(Util.createAuditDetail("end_date", oldProject.getEndDate().toString(),
                newProject.getEndDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProject.getCreationDate().toString(),
                newProject.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProject.getCreationUser(),
                newProject.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", oldProject.getModificationDate().toString(),
                newProject.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProject.getModificationUser(),
                newProject.getModificationUser()));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        this.getAuditManager().createAuditRecord(header);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Project parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptionif projects is null, empty or contains null values.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateProjects(Project[] projects, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        checkProjects(projects);

        Connection conn = null;
        try {
            conn = getConnection();

            Contact[] contacts = new Contact[projects.length];
            Address[] addresses = new Address[projects.length];
            long[] projectIds = new long[projects.length];
            List[] params = new List[projects.length];

            for (int i = 0; i < projects.length; i++) {
                contacts[i] = projects[i].getContact();
                addresses[i] = projects[i].getAddress();
                projectIds[i] = projects[i].getId();
                params[i] = createUpdateParams(projects[i]);
            }

            Project[] oldProjects = getSimpleProjects(conn, projectIds);

            Util.executeBatchUpdate(conn, UPDATE_PROJECT, params);

            long[] clientIds = getClientIds(conn, projectIds);
            updateClients(conn, projects, clientIds);

            for (int i = 0; i < projects.length; i++) {
                termManager.updatePaymentTerm(projects[i].getTerms());
                // update the contact relation if necessary
                long contactId = getContactId(conn, projectIds[i]);
                long addressId = getAddressId(conn, projectIds[i]);
                if (contactId != projects[i].getContact().getId()) {
                    contactManager.deassociate(contactManager.retrieveContact(contactId), projectIds[i], audit);
                    contactManager.associate(projects[i].getContact(), projectIds[i], audit);
                }

                // update the address relation if necessary
                if (addressId != projects[i].getAddress().getId()) {
                    addressManager.deassociate(addressManager.retrieveAddress(addressId), projectIds[i], audit);
                    addressManager.associate(projects[i].getAddress(), projectIds[i], audit);
                }
            }

            // update the contact and address entities
            contactManager.updateContacts(contacts, audit);
            addressManager.updateAddresses(addresses, audit);

            // perform the audit if necessary
            if (audit) {
                audit(oldProjects, projects);
            }

            Util.resetBeanChangedStates(projects);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (ContactException e) {
            throw new DataAccessException("Failed to handle the contact or address relationship.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the project.", e);
        } catch (CommonManagementException e) {
            throw new DataAccessException("Failed to handle the payment terms relationship.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method queries the database to get the contact that is associated with the given project.
     * </p>
     *
     * <p>
     * If the contact record cannot be found, then DataAccessException will be thrown.
     * </p>
     *
     * @param conn the database connection to access the database
     * @param projectId the project id to get its contact id
     * @return the contact id for the user
     *
     * @throws SQLException if a database access error occurs
     * @throws DataAccessException if the contact record cannot be found
     */
    private long getContactId(Connection conn, long projectId) throws SQLException, DataAccessException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SEL_CONTACT_ID);

            List params = new ArrayList();
            params.add(new Long(projectId));
            params.add(new Long(ContactType.PROJECT.getId()));
            Util.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            // get the contact id
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new DataAccessException("Failed to get the contact id for the project with id [" + projectId
                    + "].");
            }

        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method queries the database to get the address that is associated with the given project.
     * </p>
     *
     * <p>
     * If the address record cannot be found, then DataAccessException will be thrown.
     * </p>
     *
     * @param conn the database connection to access the database
     * @param projectId the project id to get its address id
     * @return the address id for the user
     *
     * @throws SQLException if a database access error occurs
     * @throws DataAccessException if the address record cannot be found
     */
    private long getAddressId(Connection conn, long projectId) throws SQLException, DataAccessException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SEL_ADDRESS_ID);

            List params = new ArrayList();
            params.add(new Long(projectId));
            params.add(new Long(AddressType.PROJECT.getId()));
            Util.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            // get the address id
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new DataAccessException("Failed to get the address id for the project with id [" + projectId
                    + "].");
            }

        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method updates the client-project relationships in the database.
     * </p>
     *
     * <p>
     * Note, the update will only be performed when the new client id in each project is different
     * with the old client id for the project in the database.
     * </p>
     *
     * @param conn the database connection to access database
     * @param projects the project instances to update the database
     * @param clientIds the original client ids for the <code>projects</code>
     *
     * @throws SQLException if a database access error occurs
     * @throws DataAccessException if fails to update the database
     */
    private void updateClients(Connection conn, Project[] projects, long[] clientIds) throws SQLException,
        DataAccessException {
        List allParams = new ArrayList();

        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getClientId() != clientIds[i]) {
                List params = new ArrayList();
                params.add(new Long(projects[i].getClientId()));
                params.add(projects[i].getCreationDate());
                params.add(projects[i].getCreationUser());
                params.add(projects[i].getModificationDate());
                params.add(projects[i].getModificationUser());
                params.add(new Long(projects[i].getId()));
                params.add(new Long(clientIds[i]));

                allParams.add(params);
            }
        }

        // update the records in client_project table
        if (allParams.size() != 0) {
            Util.executeBatchUpdate(conn, UPDATE_CLIENT_PROJECT, (List[]) allParams.toArray(new List[allParams.size()]));
        }
    }

    /**
     * <p>
     * This method queries the database for the original client ids for the given project ids in the database.
     * </p>
     *
     * @param conn the database connection to access database
     * @param projectIds the project id array to get their original client ids
     * @return the original client ids for the given project ids in the database.
     *
     * @throws SQLException if a database access error occurs
     * @throws UnrecognizedEntityException if no client id cannot be found for some project id
     */
    private long[] getClientIds(Connection conn, long[] projectIds) throws SQLException, UnrecognizedEntityException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_CLIENT_PROJECT + Util.buildInClause("project_id", projectIds));

            rs = pstmt.executeQuery();

            // this is a mapping from the project id to its client id
            Map result = new HashMap();

            while (rs.next()) {
                int index = 1;
                long projectId = rs.getLong(index++);
                long clientId = rs.getLong(index);
                result.put(new Long(projectId), new Long(clientId));
            }

            // aggregates all the client ids for the given project ids
            long[] clientIds = new long[projectIds.length];
            for (int i = 0; i < projectIds.length; i++) {
                Long value = (Long) result.get(new Long(projectIds[i]));

                // client id for some project is missing
                if (value == null) {
                    throw new UnrecognizedEntityException(projectIds[i], "There is no client for project ["
                        + projectIds[i] + "].");
                }

                clientIds[i] = value.longValue();
            }

            return clientIds;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to update a project.
     * </p>
     *
     * @param project the <code>Project</code> to update to database
     * @return the parameters needed to update a project
     */
    private List createUpdateParams(Project project) {
        List params = new ArrayList();

        params.add(project.getName());
        params.add(new Long(project.getCompanyId()));
        params.add(new Long(project.isActive() ? 1 : 0));

        // description can be null in the database
        if (project.getDescription().length() == 0) {
            params.add(new SqlType(Types.VARCHAR));
        } else {
            params.add(project.getDescription());
        }

        params.add(new Double(project.getSalesTax()));
        params.add(new Long(project.getTerms().getId()));
        params.add(project.getStartDate());
        params.add(project.getEndDate());
        params.add(project.getCreationDate());
        params.add(project.getCreationUser());
        params.add(project.getModificationDate());
        params.add(project.getModificationUser());
        params.add(new Long(project.getId()));

        return params;
    }

    /**
     * <p>
     * Modifies the persistent store so that the projects that have the given project ids are no more active.
     * </p>
     *
     * <p>
     * Note, the project records are not deleted from database, only the <tt>active</tt> states will be set
     * to inactive.
     * </p>
     *
     * @param projectIds An array of projectIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProjects(long[] projectIds, boolean audit) throws DataAccessException {
        checkProjectIds(projectIds);

        Connection conn = null;
        try {
            conn = getConnection();

            Project[] oldProjects = getSimpleProjects(conn, projectIds);

            List[] params = new List[projectIds.length];

            for (int i = 0; i < projectIds.length; i++) {
                params[i] = new ArrayList();
                params[i].add(new Long(projectIds[i]));
            }

            // set the projects to inactive
            Util.executeBatchUpdate(conn, DELETE_PROJECT, params);

            // perform the audit if necessary
            if (audit) {
                audit(oldProjects, null);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the project.", e);
        } catch (CommonManagementException e) {
            throw new DataAccessException("Failed to handle the payment terms relationship.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of <code>Project</code> objects that reflects the data in the persistent
     * store on the Time Tracker Project with the specified <code>projectsIds</code>.
     * </p>
     *
     * @param projectIds An array of projectIds for which the operation should be performed.
     * @return The projects corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] getProjects(long[] projectIds) throws DataAccessException {
        checkProjectIds(projectIds);
        Connection conn = null;
        try {
            conn = getConnection();

            // all the associations of each project will be populated
            Project[] projects = getSimpleProjects(conn, projectIds);
            for (int i = 0; i < projects.length; i++) {
                addProjectAssociations(conn, projects[i]);
            }

            return projects;
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (PersistenceException e) {
            throw new DataAccessException("Failed to handle the contact or address relationship.", e);
        } catch (CommonManagementException e) {
            throw new DataAccessException("Failed to audit the project.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of <code>Project</code> objects that reflects the data in the persistent
     * store on the Time Tracker Project with the specified <code>projectsIds</code>.
     * </p>
     *
     * <p>
     * Note, the returned <code>Project</code> instances will not contain the associations with
     * the contact and address instances.
     * </p>
     *
     * @param projectsIds An array of projectIds for which the operation should be performed.
     * @return The projects corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private Project[] getSimpleProjects(Connection conn, long[] projectIds) throws SQLException,
        UnrecognizedEntityException, CommonManagementException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_PROJECT + " AND " + Util.buildInClause("project.project_id", projectIds));

            rs = pstmt.executeQuery();

            // this is a mapping from the project to its project instance
            Map result = new HashMap();

            while (rs.next()) {
                Project project = getSimpleProject(rs);
                result.put(new Long(project.getId()), project);
            }

            // aggregates all the projects instance according to the project ids
            Project[] projects = new Project[projectIds.length];
            for (int i = 0; i < projectIds.length; i++) {
                projects[i] = (Project) result.get(new Long(projectIds[i]));
                if (projects[i] == null) {
                    throw new UnrecognizedEntityException(projectIds[i]);
                }
            }

            return projects;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method creates a <code>Project</code> instance from the given <code>ResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>Project</code> instance will not contain the associations with
     * the contact and address instances.
     * </p>
     *
     * @param rs the <code>ResultSet</code> instance
     * @return the <code>Project</code> created from the given <code>ResultSet</code> instance
     *
     * @throws SQLException if a database access error occurs
     * @throws CommonManagementException if fails to get the associated <code>PaymentTerm</code>
     * for the project
     */
    private Project getSimpleProject(ResultSet rs) throws SQLException, CommonManagementException {
        Project project = new Project();

        int index = 1;
        project.setId(rs.getLong(index++));
        project.setName(rs.getString(index++));
        project.setCompanyId(rs.getLong(index++));
        project.setClientId(rs.getLong(index++));
        project.setActive(rs.getLong(index++) == 1);
        project.setDescription(rs.getString(index++));
        project.setSalesTax(rs.getDouble(index++));
        project.setTerms(termManager.retrievePaymentTerm(rs.getLong(index++)));
        project.setStartDate(rs.getDate(index++));
        project.setEndDate(rs.getDate(index++));
        project.setCreationDate(rs.getDate(index++));
        project.setCreationUser(rs.getString(index++));
        project.setModificationDate(rs.getDate(index++));
        project.setModificationUser(rs.getString(index++));

        project.setChanged(false);

        return project;
    }

    /**
     * <p>
     * This method loads the contact and address associations for the given project.
     * </p>
     *
     * @param conn the database connection to access database
     * @param project the project to load their contact and address associations
     *
     * @throws SQLException if a database access error occurs
     * @throws PersistenceException if unable to retrieve the address and contact instances
     * @throws DataAccessException if no address or contact id can be found for the project
     */
    private void addProjectAssociations(Connection conn, Project project) throws DataAccessException, SQLException,
        PersistenceException {
        long addressId = getAddressId(conn, project.getId());
        long contactId = getContactId(conn, project.getId());

        try {
            project.setAddress(addressManager.retrieveAddress(addressId));
        } catch (AssociationException ae) {
            throw new PersistenceException("Error occurred while associating project with address information.", ae);
        }
        try {
            project.setContact(contactManager.retrieveContact(contactId));
        } catch (AssociationException ae) {
            throw new PersistenceException("Error occurred while associating project with contact information.", ae);
        }
    }

    /**
     * <p>
     * This method checks the given project id array.
     * </p>
     *
     * @param projectIds the project id array to check
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     */
    private void checkProjectIds(long[] projectIds) {
        Util.checkNull(projectIds, "projectIds");

        // length check
        if (projectIds.length == 0) {
            throw new IllegalArgumentException("The given project ids array is empty.");
        }

        for (int i = 0; i < projectIds.length; i++) {
            Util.checkIdValue(projectIds[i], "project");
        }
    }

    /**
     * <p>
     * Searches the persistent store for any projects that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectFilterFactory</code> returned by {@link ProjectDAO#getProjectFilterFactory()}, or
     * a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter used to search for projects.
     * @return The projects satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] searchProjects(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(filter);

            int size = result.getRecordCount();

            if (size == 0) {
                return new Project[0];
            }

            // aggregate all the satisfied project ids in an array
            long[] projectIds = new long[size];
            for (int i = 0; i < size; i++) {
                result.next();
                projectIds[i] = result.getLong(1);
            }

            // delegates to the getProjects(long[]) method
            return getProjects(projectIds);
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the database according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to retrieve the search result.", e);
        }
    }

    /**
     * <p>
     * Retrieves the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for projects.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters
     * returned by the given factory should be used with {@link ProjectDAO#searchProjects(Filter)}.
     * </p>
     *
     * @return the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for projects.
     */
    public ProjectFilterFactory getProjectFilterFactory() {
        return this.filterFactory;
    }

    /**
     * <p>
     * Retrieves all the projects that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of all projects retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] enumerateProjects() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SELECT_PROJECT);

            rs = pstmt.executeQuery();

            List projects = new ArrayList();

            // get all the projects in the database
            while (rs.next()) {
                Project project = getSimpleProject(rs);
                addProjectAssociations(conn, project);
                projects.add(project);
            }

            return (Project[]) projects.toArray(new Project[projects.size()]);

        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (CommonManagementException e) {
            throw new DataAccessException("Failed to handle the payment terms relationship.", e);
        } catch (PersistenceException e) {
            throw new DataAccessException("Failed to handle the contact or address relationship.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }
}
