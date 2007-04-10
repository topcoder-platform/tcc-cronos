/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.daoimplementation;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.AndFragmentBuilder;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;
import com.topcoder.search.builder.database.EqualsFragmentBuilder;
import com.topcoder.search.builder.database.InFragmentBuilder;
import com.topcoder.search.builder.database.LikeFragmentBuilder;
import com.topcoder.search.builder.database.NotFragmentBuilder;
import com.topcoder.search.builder.database.NullFragmentBuilder;
import com.topcoder.search.builder.database.OrFragmentBuilder;
import com.topcoder.search.builder.database.RangeFragmentBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAO;
import com.topcoder.timetracker.company.CompanyDAOException;
import com.topcoder.timetracker.company.CompanyNotFoundException;
import com.topcoder.timetracker.company.InvalidIdException;
import com.topcoder.timetracker.company.TimeTrackerCompanyHelper;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactException;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <p>
 * Database implementation of the <code>CompanyDAO</code> interface. It is capable of persisting and retrieving Time
 * Tracker Company information from the database.
 * </p>
 *
 * <p>
 * This DAO is capable of performing the basic CRUDE operations in non-batch mode. This means that only single entity
 * are processed at a time.
 * </p>
 *
 * <p>
 * The DAO is capable of performing the operations on multiple Companies simultaneously (batch mode). While in batch
 * mode, the operation can be done atomically, or separately. If done atomically, then a failure at any one of the
 * specified entries will mean that the entire batch will be rolled back. Otherwise, only the user where a failure
 * occurred will be rolled back(note this is not supported in current implementation).
 * </p>
 *
 * <p>
 * This class utilizes <code>ContactManager</code> and <code>AddressManager</code> to store and retrieve Contact and
 * Address information of a Company.
 * </p>
 *
 * <p>
 * In addition, it utilizes <code>AuditManager</code> to perform audit of the operations performed by the methods in
 * this class that modify the datastore. All methods that modify the datastore has a boolean doAudit to indicate
 * whether audit will be performed.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The class is not thread safe as the operations performed in its method span multiple
 * connections (as <code>ContactManager</code> and <code>AddressManager</code> create their own connection) and thus
 * it is not atomic (database only guarantee atomicity within single transaction). Therefore, they might interfere ane
 * another.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class DbCompanyDAO implements CompanyDAO {
    /** Represents the column name of company_id. */
    private static final String COMPANY_ID_COLUMN = "company_id";

    /** Represents the column name of name. */
    private static final String NAME_COLUMN = "name";

    /** Represents the column name of passcode. */
    private static final String PASSCODE_COLUMN = "passcode";

    /** Represents the column name of creation_date. */
    private static final String CREATION_DATE_COLUMN = "creation_date";

    /** Represents the column name of creation_user. */
    private static final String CREATION_USER_COLUMN = "creation_user";

    /** Represents the column name of modification_date. */
    private static final String MODIFICATION_DATE_COLUMN = "modification_date";

    /** Represents the column name of modification_user. */
    private static final String MODIFICATION_USER_COLUMN = "modification_user";

    /** Represents the SQL statement used to insert one record of company into the database. */
    private static final String SQL_INSERT_COMPANY = "INSERT INTO company"
        + " (company_id, name, passcode, creation_date, creation_user, modification_date, modification_user)"
        + " VALUES (?,?,?,?,?,?,?)";

    /** Represents the SQL statement used to retrieve one record of company via id from the database. */
    private static final String SQL_RETRIEVE_COMPANY_BY_ID = "SELECT name, passcode, creation_date, creation_user,"
        + " modification_date, modification_user FROM company WHERE company_id = ?";

    /** Represents the SQL statement used to retrieve all the records of company from the database. */
    private static final String SQL_RETRIEVE_ALL_COMPANY = "SELECT company_id, name, passcode, creation_date,"
        + " creation_user, modification_date, modification_user FROM company";

    /** Represents the SQL statement used to update one record of company via id from the database. */
    private static final String SQL_UPDATE_COMPANY_BY_ID = "UPDATE company SET name=?, passcode=?,"
        + " modification_date=?, modification_user=? where company_id=?";

    /** Represents the SQL statement used to delete one record of company via id from the database. */
    private static final String SQL_DELETE_COMPANY_BY_ID = "DELETE FROM company WHERE company_id = ?";

    /**
     * <p>
     * Represents the associations map used for search bundle.
     * </p>
     */
    private static Map associations;

    /**
     * <p>
     * Represents the alias map used for search bundle.
     * </p>
     */
    private static Map alias;

    /**
     * <p>
     * Represents the fields map used for search bundle.
     * </p>
     */
    private static Map fields;

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations.
     * </p>
     *
     * <p>
     * It should be backed by a JNDI connection producer, which simply eases the obtaining of a connection from a
     * datasource via JNDI. This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the name of the connection to obtain from the connection factory that is used for performing CRUD
     * operations.
     * </p>
     *
     * <p>
     * This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the ID Generator instance used to obtain IDs for any entities to be added to the database.
     * </p>
     *
     * <p>
     * This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * Represents the address manager used to retrieve and store the address information of the company to the
     * datastore.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null. It has getter method.
     * </p>
     */
    private final AddressManager addressManager;

    /**
     * <p>
     * Represents the contact manager used to retrieve and store the contact information of the company to the
     * datastore.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null. It has getter method.
     * </p>
     */
    private final ContactManager contactManager;

    /**
     * <p>
     * Represents the search bundle object used to perform search of companies based on a given Filter.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and it will never be null.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * Represents the audit manager to be used to perform audit of the operations performed through this class.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null. It has getter method.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * Creates the new instance of <code>DbCompanyDAO</code> class with given connection factory, connection name, id
     * generator name. The default implementations of <code>ContactManager</code>, <code>AddressManager</code> and
     * <code>AuditManager</code> will be created here, as xxxDelegate classes using the given namespaces.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use when retrieving a connection from the connection factory.
     * @param idGen The Id Generator String name to use.
     * @param contactManagerNamespace the namespace for the default implementation class of
     *        <code>ContactManager</code>.
     * @param addressManagerNamespace the namespace for the default implementation class of
     *        <code>AddressManager</code>.
     * @param auditManagerNamespace the namespace for the default implementation class of <code>AuditManager</code>.
     *
     * @throws IllegalArgumentException if any parameter is null or any string parameter is empty string.
     * @throws InvalidIdException if negative id will be generated.
     * @throws CompanyDAOException if it fails to create the id generator, or the xxxManager instances
     */
    public DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String contactManagerNamespace,
        String addressManagerNamespace, String auditManagerNamespace) throws CompanyDAOException {
        this(connFactory, connName, idGen, createDefaultContactManager(contactManagerNamespace),
            createDefaultAddressManager(addressManagerNamespace), createDefaultAuditManager(auditManagerNamespace));
    }

    /**
     * <p>
     * Creates the new instance of <code>DbCompanyDAO</code> class with given connection factory, connection name, id
     * generator name, as well as the managers used.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use when retrieving a connection from the connection factory.
     * @param idGen The Id Generator String name to use.
     * @param contactManager the contact manager to be used.
     * @param addressManager the address manager to be used.
     * @param auditManager the audit manager to be used.
     *
     * @throws IllegalArgumentException if any parameter is null or any string parameter is empty string.
     * @throws InvalidIdException if negative id will be generated.
     * @throws CompanyDAOException if it fails to create the id generator.
     */
    public DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, ContactManager contactManager,
        AddressManager addressManager, AuditManager auditManager) throws CompanyDAOException {
        // validate arguments
        TimeTrackerCompanyHelper.validateNotNull(connFactory, "connFactory");
        TimeTrackerCompanyHelper.validateString(connName, "connName");
        TimeTrackerCompanyHelper.validateString(idGen, "idGen");
        TimeTrackerCompanyHelper.validateNotNull(contactManager, "contactManager");
        TimeTrackerCompanyHelper.validateNotNull(addressManager, "addressManager");
        TimeTrackerCompanyHelper.validateNotNull(auditManager, "auditManager");

        // assign the private fields
        this.connectionFactory = connFactory;
        this.connectionName = connName;
        this.contactManager = contactManager;
        this.addressManager = addressManager;
        this.auditManager = auditManager;

        // create the idGenerator
        try {
            this.idGenerator = IDGeneratorFactory.getIDGenerator(idGen);

            long id = idGenerator.getNextID();

            if (id <= 0) {
                throw new InvalidIdException("The id generated by the IDGeneratorFactory is not positive.", id);
            }
        } catch (IDGenerationException e) {
            throw new CompanyDAOException("Fails to create the idGenerator from IDGeneratorFactory.", e, null);
        }

        // create the SearchBundle
        this.searchBundle = createSearchBundle(connFactory, connName);
    }

    /**
     * <p>
     * Create the SearchBundle using given connection factory and connection name. The context, searchable fields,
     * alias and associations will also be hard-coded here.
     * </p>
     *
     * @param connFactory the connection factory.
     * @param connName the connection name.
     *
     * @return the created SearchBundle instance.
     */
    private SearchBundle createSearchBundle(DBConnectionFactory connFactory, String connName) {
        String context = "SELECT"
            + "   company.company_id, company.name, company.passcode, company.creation_date, company.creation_user,"
            + "   company.modification_date, company.modification_user,"
            + "   C.first_name, C.last_name, C.phone, C.email,"
            + "   ADR.line1, ADR.line2, ADR.city, ADR.zip_code, SN.name"
            + " FROM company"
            + " INNER JOIN contact_relation CR"
            + "   ON CR.entity_id = company.company_id AND CR.contact_type_id = " + ContactType.COMPANY.getId()
            + " INNER JOIN contact AS C"
            + "   ON C.contact_id = CR.contact_id"
            + " INNER JOIN address_relation AR"
            + "   ON AR.entity_id = company.company_id AND AR.address_type_id = " + AddressType.COMPANY.getId()
            + " INNER JOIN address AS ADR"
            + "   ON ADR.address_id = AR.address_id"
            + " INNER JOIN state_name SN"
            + "   ON SN.state_name_id = ADR.state_name_id WHERE";
        if (alias == null) {
            // the static alias is used here. it will only be intialized once when used.
            alias = new HashMap();
            alias.put(CompanyDAO.SEARCH_COMPANY_NAME, "company.name");
            alias.put(CompanyDAO.SEARCH_CREATED_DATE, "company.creation_date");
            alias.put(CompanyDAO.SEARCH_CREATED_USER, "company.creation_user");
            alias.put(CompanyDAO.SEARCH_MODIFICATION_DATE, "company.modification_date");
            alias.put(CompanyDAO.SEARCH_MODIFICATION_USER, "company.modification_user");
            alias.put(CompanyDAO.SEARCH_CONTACT_FIRST_NAME, "C.first_name");
            alias.put(CompanyDAO.SEARCH_CONTACT_LAST_NAME, "C.last_name");
            alias.put(CompanyDAO.SEARCH_CONTACT_PHONE, "C.phone");
            alias.put(CompanyDAO.SEARCH_CONTACT_EMAIL, "C.email");
            alias.put(CompanyDAO.SEARCH_STREET_ADDRESS1, "ADR.line1");
            alias.put(CompanyDAO.SEARCH_STREET_ADDRESS2, "ADR.line2");
            alias.put(CompanyDAO.SEARCH_CITY, "ADR.city");
            alias.put(CompanyDAO.SEARCH_ZIP_CODE, "ADR.zip_code");
            alias.put(CompanyDAO.SEARCH_STATE, "SN.name");

            fields = new HashMap();

            for (Iterator iter = alias.keySet().iterator(); iter.hasNext();) {
                fields.put(iter.next(), null);
            }

            associations = new HashMap();
            associations.put(AndFilter.class, new AndFragmentBuilder());
            associations.put(OrFilter.class, new OrFragmentBuilder());
            associations.put(LikeFilter.class, new LikeFragmentBuilder());
            associations.put(NotFilter.class, new NotFragmentBuilder());
            associations.put(EqualToFilter.class, new EqualsFragmentBuilder());
            associations.put(InFilter.class, new InFragmentBuilder());
            associations.put(NullFilter.class, new NullFragmentBuilder());
            associations.put(GreaterThanFilter.class, new RangeFragmentBuilder());
            associations.put(GreaterThanOrEqualToFilter.class, new RangeFragmentBuilder());
            associations.put(BetweenFilter.class, new RangeFragmentBuilder());
            associations.put(LessThanOrEqualToFilter.class, new RangeFragmentBuilder());
            associations.put(LessThanFilter.class, new RangeFragmentBuilder());
        }

        DatabaseSearchStrategy stategy = new DatabaseSearchStrategy(connFactory, connName, associations);

        return new SearchBundle("DbCompanyDAO", fields, alias, context, stategy);
    }

    /**
     * <p>
     * Create the <code>ContactManagerLocalDelegate</code> instance with given namespace.
     * </p>
     *
     * @param contactManagerNamespace the given namespace.
     *
     * @return the created instance.
     *
     * @throws IllegalArgumentException if the namespace is null or empty string.
     * @throws CompanyDAOException if it fails to create the instance.
     */
    private static ContactManager createDefaultContactManager(String contactManagerNamespace)
        throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateString(contactManagerNamespace, "contactManagerNamespace");

        try {
            return new ContactManagerLocalDelegate(contactManagerNamespace);
        } catch (ConfigurationException e) {
            throw new CompanyDAOException("Can not create the ContactManagerLocalDelegate instance.", e, null);
        }
    }

    /**
     * <p>
     * Create the <code>AddressManagerLocalDelegate</code> instance with given namespace.
     * </p>
     *
     * @param addressManagerNamespace the given namespace.
     *
     * @return the created instance.
     *
     * @throws IllegalArgumentException if the namespace is null or empty string.
     * @throws CompanyDAOException if it fails to create the instance.
     */
    private static AddressManager createDefaultAddressManager(String addressManagerNamespace)
        throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateString(addressManagerNamespace, "addressManagerNamespace");

        try {
            return new AddressManagerLocalDelegate(addressManagerNamespace);
        } catch (ConfigurationException e) {
            throw new CompanyDAOException("Can not create the addressManagerNamespace instance.", e, null);
        }
    }

    /**
     * <p>
     * Create the <code>AuditDelegate</code> instance with given namespace.
     * </p>
     *
     * @param auditManagerNamespace the given namespace.
     *
     * @return the created instance.
     *
     * @throws IllegalArgumentException if the namespace is null or empty string.
     * @throws CompanyDAOException if it fails to create the instance.
     */
    private static AuditManager createDefaultAuditManager(String auditManagerNamespace) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateString(auditManagerNamespace, "auditManagerNamespace");

        try {
            return new AuditDelegate(auditManagerNamespace);
        } catch (AuditConfigurationException e) {
            throw new CompanyDAOException("Can not create the AuditDelegate instance.", e, null);
        }
    }

    /**
     * <p>
     * Creates a datastore entry for the given company. An id is automatically generated by the DAO and assigned to the
     * company. The Company is also considered to have been created by the specified user. Audit is to be performed if
     * doAudit is true.
     * </p>
     *
     * <p>
     * The id, creationUser, modfiicationUser, creationDate, modificationDate of the Company argument will be updated.
     * The changed flag will be set to false.
     * </p>
     *
     * @param company The company to define within the data store.
     * @param user The user responsible for creating the Company entry within the datastore.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company object with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately.
     *
     * @throws IllegalArgumentException if the company or user is null, or user is an empty String, or if any field in
     *         company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company createCompany(Company company, String user, boolean doAudit) throws CompanyDAOException {
        validateCompany(company);
        TimeTrackerCompanyHelper.validateString(user, "user");

        Connection conn = null;

        try {
            conn = this.createConnection();

            return createCompany(conn, company, user, doAudit);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Validate the company to create or update. All fields, except algorithmName, must not be null (and empty if
     * Strings). comanpyName, address, contact, passCode musy not be null and comanpyName and passCode must not be
     * empty.
     * </p>
     *
     * @param company the company to validate.
     *
     * @throws IllegalArgumentException if the company is invalid as stated above.
     */
    private void validateCompany(Company company) {
        TimeTrackerCompanyHelper.validateNotNull(company, "company");
        TimeTrackerCompanyHelper.validateString(company.getCompanyName(), "company#CompanyName");
        TimeTrackerCompanyHelper.validateString(company.getPassCode(), "company#PassCode");
        TimeTrackerCompanyHelper.validateNotNull(company.getContact(), "company#Contact");
        TimeTrackerCompanyHelper.validateNotNull(company.getAddress(), "company#Address");
    }

    /**
     * <p>
     * Creates a datastore entry for the given company under the given connection. An id is automatically generated by
     * the DAO and assigned to the company. The Company is also considered to have been created by the specified user.
     * Audit is to be performed if doAudit is true.
     * </p>
     *
     * <p>
     * The id, creationUser, modfiicationUser, creationDate, modificationDate of the Company argument will be updated.
     * The changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param company The company to define within the data store.
     * @param user The user responsible for creating the Company entry within the datastore.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company object with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately.
     *
     * @throws InvalidIdException if a negative ID is generated.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private Company createCompany(Connection conn, Company company, String user, boolean doAudit)
        throws CompanyDAOException {
        Date now = new Date();

        // insert the company, exceptions will be directly thrown when any error happens
        long companyId = this.insertCompany(conn, company, user, now);

        // get contact and address from Company and persist them
        try {
            contactManager.addContact(company.getContact(), doAudit);
            addressManager.addAddress(company.getAddress(), doAudit);
            contactManager.associate(company.getContact(), companyId, doAudit);
            addressManager.associate(company.getAddress(), companyId, doAudit);
        } catch (ContactException e) {
            throw new CompanyDAOException("Errors happen when try to persist the contact or address.", e, company);
        }

        // audit if it is required
        if (doAudit) {
            try {
                auditManager.createAuditRecord(buildAuditHeader(user, now, null, company, AuditType.INSERT));
            } catch (AuditManagerException e) {
                throw new CompanyDAOException("Errors happen when try to do the audit.", e, company);
            }
        }

        return company;
    }

    /**
     * <p>
     * Persist an instance of <code>Company</code> into the database with given creation user and creation date under
     * given connection. The changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param company the company to persist.
     * @param user the creation user.
     * @param now the creation date.
     *
     * @return the id of the newly persisted record.
     *
     * @throws InvalidIdException if a negative ID is generated.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private long insertCompany(Connection conn, Company company, String user, Date now) throws CompanyDAOException {
        Timestamp timestamp = this.date2Timestamp(now);

        PreparedStatement statement = null;

        try {
            long companyId = (company.getId() > 0) ? company.getId() : this.getNextID();

            statement = conn.prepareStatement(SQL_INSERT_COMPANY);

            int index = 0;
            statement.setLong(++index, companyId);
            statement.setString(++index, company.getCompanyName());
            statement.setString(++index, company.getPassCode());
            statement.setTimestamp(++index, timestamp);
            statement.setString(++index, user);
            statement.setTimestamp(++index, timestamp);
            statement.setString(++index, user);

            statement.executeUpdate();

            // everything is ok
            company.setId(companyId);
            company.setCreationDate(now);
            company.setCreationUser(user);
            company.setModificationDate(now);
            company.setModificationUser(user);
            company.setChanged(false);

            return companyId;
        } catch (SQLException e) {
            throw new CompanyDAOException("Something wrong in the database related operations.", e, company);
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Build the <code>AuditHeader</code> instance which will be used by the <code>AuditManager</code> to perform the
     * audit. The auditType can be INSERT(columns in the new company will be used to audit), UPDATE((columns in both
     * the old and new companies will be used to audit)) or DELETE(columns in the old company will be used to audit).
     * Note, if the audit type is UPDATE, only the updated columns will be used to audit.
     * </p>
     *
     * @param user the user to do the audit.
     * @param now the current time to do the audit.
     * @param oldCompany the old company record.
     * @param newCompany the new company record.
     * @param auditType the audit type, can be INSERT, UPDATE or DELETE.
     *
     * @return the <code>AuditHeader</code> instance.
     */
    private AuditHeader buildAuditHeader(String user, Date now, Company oldCompany, Company newCompany, int auditType) {
        // build the audit details
        List auditDetails = new ArrayList();

        if (auditType == AuditType.INSERT) {
            addAuditDetail(auditDetails, COMPANY_ID_COLUMN, null,
                String.valueOf(newCompany.getId()), auditType);
            addAuditDetail(auditDetails, NAME_COLUMN, null,
                newCompany.getCompanyName(), auditType);
            addAuditDetail(auditDetails, PASSCODE_COLUMN, null,
                newCompany.getPassCode(), auditType);
            addAuditDetail(auditDetails, CREATION_DATE_COLUMN, null,
                String.valueOf(newCompany.getCreationDate()), auditType);
            addAuditDetail(auditDetails, CREATION_USER_COLUMN, null,
                newCompany.getCreationUser(), auditType);
            addAuditDetail(auditDetails, MODIFICATION_DATE_COLUMN, null,
                String.valueOf(newCompany.getModificationDate()), auditType);
            addAuditDetail(auditDetails, MODIFICATION_USER_COLUMN, null,
                newCompany.getModificationUser(), auditType);
        } else if (auditType == AuditType.DELETE) {
            addAuditDetail(auditDetails, COMPANY_ID_COLUMN, String.valueOf(oldCompany.getId()),
                null, auditType);
            addAuditDetail(auditDetails, NAME_COLUMN, oldCompany.getCompanyName(),
                null, auditType);
            addAuditDetail(auditDetails, PASSCODE_COLUMN, oldCompany.getPassCode(),
                null, auditType);
            addAuditDetail(auditDetails, CREATION_DATE_COLUMN, String.valueOf(oldCompany.getCreationDate()),
                null, auditType);
            addAuditDetail(auditDetails, CREATION_USER_COLUMN, oldCompany.getCreationUser(),
                null, auditType);
            addAuditDetail(auditDetails, MODIFICATION_DATE_COLUMN, String.valueOf(oldCompany.getModificationDate()),
                null, auditType);
            addAuditDetail(auditDetails, MODIFICATION_USER_COLUMN, oldCompany.getModificationUser(),
                null, auditType);
        } else if (auditType == AuditType.UPDATE) {
            addAuditDetail(auditDetails, NAME_COLUMN, oldCompany.getCompanyName(), newCompany.getCompanyName(),
                auditType);
            addAuditDetail(auditDetails, PASSCODE_COLUMN, oldCompany.getPassCode(),
                newCompany.getPassCode(), auditType);
            addAuditDetail(auditDetails, MODIFICATION_DATE_COLUMN, String.valueOf(oldCompany.getModificationDate()),
                String.valueOf(newCompany.getModificationDate()), auditType);
            addAuditDetail(auditDetails, MODIFICATION_USER_COLUMN, oldCompany.getModificationUser(),
                newCompany.getModificationUser(), auditType);
        }

        // build the audit header
        long companyId = auditType == AuditType.INSERT ? newCompany.getId() : oldCompany.getId();
        AuditHeader auditHeader = new AuditHeader();
        auditHeader.setActionType(auditType);
        auditHeader.setApplicationArea(ApplicationArea.TT_COMPANY);
        auditHeader.setCompanyId(companyId);
        auditHeader.setCreationUser(user);
        auditHeader.setCreationDate(this.date2Timestamp(now));
        auditHeader.setEntityId(companyId);
        auditHeader.setResourceId(companyId);
        auditHeader.setTableName("company");
        auditHeader.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));

        return auditHeader;
    }

    /**
     * <p>
     * Build the <code>AuditDetail</code> instance which will be used by the <code>AuditManager</code> to perform the
     * audit. The auditType can be INSERT(columns in the new company will be used to audit), UPDATE((columns in both
     * the old and new companies will be used to audit)) or DELETE(columns in the old company will be used to audit).
     * Note, if the audit type is UPDATE, only the changed columns will be used to audit.
     * </p>
     *
     * @param auditDetails the auditDetails currently hold be audit header.
     * @param columnName the column name to check.
     * @param oldValue the old value of the column.
     * @param newValue the new value of the column.
     * @param auditType the audit type.
     */
    private void addAuditDetail(List auditDetails, String columnName, String oldValue, String newValue, int auditType) {
        AuditDetail auditDetail = new AuditDetail();

        if (auditType == AuditType.INSERT) {
            auditDetail.setNewValue(newValue);
        } else if (auditType == AuditType.DELETE) {
            auditDetail.setOldValue(oldValue);
        } else if (auditType == AuditType.UPDATE) {
            if (oldValue.equals(newValue)) {
                return;
            }

            auditDetail.setNewValue(newValue);
            auditDetail.setOldValue(oldValue);
        }

        auditDetail.setColumnName(columnName);
        auditDetails.add(auditDetail);
    }

    /**
     * <p>
     * Retrieves a company from the datastore with the provided id. If no company with that id exists, then a null is
     * returned.
     * </p>
     *
     * @param id The id of the company to retrieve.
     *
     * @return The company with specified id, or null if it wasn't found.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company retrieveCompany(long id) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validatePositive(id, "id");

        Connection conn = null;

        try {
            conn = this.createConnection();

            return retrieveCompany(conn, id);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves a company from the datastore with the provided id under the given connection. If no company with that
     * id exists, then a null is returned.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param id The id of the company to retrieve.
     *
     * @return The company with specified id, or null if it wasn't found.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private Company retrieveCompany(Connection conn, long id) throws CompanyDAOException {
        Company company = this.getCompany(conn, id);

        if (company == null) {
            return null;
        }

        company = getAddressAndContact(company);

        company.setChanged(false);
        return company;
    }

    /**
     * <p>
     * Get the address and contact for the given company.
     * </p>
     *
     * @param company the company to get the address and contact.
     *
     * @return the company which will hold the retrieved address and contact.
     *
     * @throws CompanyDAOException if it fails to get the address and contact for the given company.
     */
    private Company getAddressAndContact(Company company) throws CompanyDAOException {
        try {
            // get the contact
            Contact[] contacts = contactManager.searchContacts(ContactFilterFactory.createEntityIDFilter(
                        company.getId(), ContactType.COMPANY));

            if (contacts.length != 1) {
                throw new CompanyDAOException("Company " + company.getId() + " contains " + contacts.length
                    + " contacts.", company);
            }

            // get address
            Address[] addresses = addressManager.searchAddresses(AddressFilterFactory.createEntityIDFilter(
                        company.getId(), AddressType.COMPANY));

            if (addresses.length != 1) {
                throw new CompanyDAOException("Company " + company.getId() + " contains " + addresses.length
                    + " addresses.", company);
            }

            company.setContact(contacts[0]);
            company.setAddress(addresses[0]);

            return company;
        } catch (ContactException e) {
            throw new CompanyDAOException("Errors happen when try to get the contact or address.", e, company);
        }
    }

    /**
     * <p>
     * Get the record of company from the database with given company id under given connection.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param id the company id.
     *
     * @return the retrieved company instance.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private Company getCompany(Connection conn, long id) throws CompanyDAOException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.prepareStatement(SQL_RETRIEVE_COMPANY_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Company company = new Company();
            company.setId(id);
            company.setCompanyName(rs.getString(NAME_COLUMN));
            company.setPassCode(rs.getString(PASSCODE_COLUMN));
            company.setCreationDate(this.timestamp2Date(rs.getTimestamp(CREATION_DATE_COLUMN)));
            company.setCreationUser(rs.getString(CREATION_USER_COLUMN));
            company.setModificationDate(this.timestamp2Date(rs.getTimestamp(MODIFICATION_DATE_COLUMN)));
            company.setModificationUser(rs.getString(MODIFICATION_USER_COLUMN));

            return company;
        } catch (SQLException e) {
            throw new CompanyDAOException("Something wrong in the database related operations.", e, null);
        } catch (IllegalArgumentException e) {
            throw new CompanyDAOException("Column value is invalid.", e, null);
        } finally {
            releaseResource(rs, statement);
        }
    }

    /**
     * <p>
     * Updates the given company in the data store. The company is considered to have been modified by the specified
     * user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * <p>
     * The modification date and modification user of the Company argument will be updated.
     * The changed flag will be set to false.
     * </p>
     *
     * @param company The company to update in the data store.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @throws IllegalArgumentException if the company or user is null, or user is an empty String, or if any field in
     *         company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to update was not found in the data store.
     */
    public void updateCompany(Company company, String user, boolean doAudit) throws CompanyDAOException {
        validateCompany(company);
        TimeTrackerCompanyHelper.validateString(user, "user");
        TimeTrackerCompanyHelper.validatePositive(company.getId(), "id");

        Connection conn = null;

        try {
            conn = this.createConnection();

            updateCompany(conn, company, user, doAudit);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the given company in the data store under the given connection. The company is considered to have been
     * modified by the specified user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * <p>
     * The modification date and modification user of the Company argument will be updated.
     * The changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param company The company to update in the data store.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to update was not found in the data store.
     */
    private void updateCompany(Connection conn, Company company, String user, boolean doAudit)
        throws CompanyDAOException {
        if (!company.isChanged() && !company.getAddress().isChanged() && !company.getContact().isChanged()) {
            return;
        }

        // get the old one
        Company oldCompany = this.getCompany(conn, company.getId());

        if (oldCompany == null) {
            throw new CompanyNotFoundException("Can not find the company with id: " + company.getId() + ".", company);
        }

        // update the new one
        Date now = new Date();
        updateCompany(conn, company, user, now);

        try {
            // update the contact and address
            if (company.getContact().isChanged()) {
                contactManager.updateContact(company.getContact(), doAudit);
            }

            if (company.getAddress().isChanged()) {
                addressManager.updateAddress(company.getAddress(), doAudit);
            }
        } catch (ContactException e) {
            throw new CompanyDAOException("Errors happen when try to update the contact or address.", e, company);
        }

        // audit if it is required
        if (doAudit) {
            try {
                auditManager.createAuditRecord(buildAuditHeader(user, now, oldCompany, company, AuditType.UPDATE));
            } catch (AuditManagerException e) {
                throw new CompanyDAOException("Errors happen when try to do the audit.", e, company);
            }
        }
    }

    /**
     * <p>
     * Update an instance of <code>Company</code> into the database with given modification user and modification date
     * under given connection. The changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param company the company to update.
     * @param user the modification user.
     * @param now the modification date.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private void updateCompany(Connection conn, Company company, String user, Date now) throws CompanyDAOException {
        Timestamp timestamp = this.date2Timestamp(now);

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQL_UPDATE_COMPANY_BY_ID);

            int index = 0;
            statement.setString(++index, company.getCompanyName());
            statement.setString(++index, company.getPassCode());
            statement.setTimestamp(++index, timestamp);
            statement.setString(++index, user);
            statement.setLong(++index, company.getId());

            statement.executeUpdate();

            // everything is ok
            company.setModificationDate(now);
            company.setModificationUser(user);
            company.setChanged(false);
        } catch (SQLException e) {
            throw new CompanyDAOException("Something wrong in the database related operations.", e, company);
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Removes the provided company from the data store. Audit is to be performed if doAudit is true. The user argument
     * is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * @param company The company to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     *
     * @throws IllegalArgumentException if the company is null, or if doAudit is true and either user is null or empty
     *         string, or if company's id is not positive.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to be deleted was not found in the data store.
     */
    public void deleteCompany(Company company, boolean doAudit, String user) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateNotNull(company, "company");
        TimeTrackerCompanyHelper.validatePositive(company.getId(), "id");

        if (doAudit) {
            TimeTrackerCompanyHelper.validateString(user, "user");
        }

        Connection conn = null;

        try {
            conn = this.createConnection();

            deleteCompany(conn, company, doAudit, user);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Removes the provided company from the data store under the given connection. Audit is to be performed if doAudit
     * is true. The user argument is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param company The company to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to be deleted was not found in the data store.
     */
    private void deleteCompany(Connection conn, Company company, boolean doAudit, String user)
        throws CompanyDAOException {
        // get the old one
        Company oldCompany = this.retrieveCompany(conn, company.getId());

        if (oldCompany == null) {
            throw new CompanyNotFoundException("Can not find the company with id: " + company.getId() + ".", company);
        }

        // delete the contact and address
        try {
            contactManager.deassociate(oldCompany.getContact(), oldCompany.getId(), doAudit);
            addressManager.deassociate(oldCompany.getAddress(), oldCompany.getId(), doAudit);
            contactManager.removeContact(oldCompany.getContact().getId(), doAudit);
            addressManager.removeAddress(oldCompany.getAddress().getId(), doAudit);
        } catch (ContactException e) {
            throw new CompanyDAOException("Errors happen when try to delete the contact or address.", e, oldCompany);
        }

        // delete the old one
        deleteCompany(conn, oldCompany);

        // audit if it is required
        if (doAudit) {
            try {
                Date now = new Date();
                auditManager.createAuditRecord(buildAuditHeader(user, now, oldCompany, null, AuditType.DELETE));
            } catch (AuditManagerException e) {
                throw new CompanyDAOException("Errors happen when try to do the audit.", e, oldCompany);
            }
        }
    }

    /**
     * <p>
     * Delete the record of company from the database with given company id under given connection.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param company the company to delete.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private void deleteCompany(Connection conn, Company company) throws CompanyDAOException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQL_DELETE_COMPANY_BY_ID);
            statement.setLong(1, company.getId());

            statement.executeUpdate();

            // everything is ok
            company.setChanged(false);
        } catch (SQLException e) {
            throw new CompanyDAOException("Something wrong in the database related operations.", e, company);
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Enumerates all the companies that are present within the data store.
     * </p>
     *
     * @return A list of all the companies present in the data store.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] listCompanies() throws CompanyDAOException {
        Connection conn = null;

        try {
            conn = this.createConnection();

            Company[] companies = listCompanies(conn, SQL_RETRIEVE_ALL_COMPANY);

            // get the contact and address for each company
            for (int i = 0; i < companies.length; i++) {
                companies[i] = getAddressAndContact(companies[i]);
                companies[i].setChanged(false);
            }

            return companies;
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Enumerates all the companies that are present within the data store under the given connection.
     * </p>
     *
     * @param conn the given connection to do the db operations.
     * @param sql the sql to enumerates all the companies.
     *
     * @return A list of all the companies present in the data store.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private Company[] listCompanies(Connection conn, String sql) throws CompanyDAOException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();

            List ret = new ArrayList();

            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong(COMPANY_ID_COLUMN));
                company.setCompanyName(rs.getString(NAME_COLUMN));
                company.setPassCode(rs.getString(PASSCODE_COLUMN));
                company.setCreationDate(this.timestamp2Date(rs.getTimestamp(CREATION_DATE_COLUMN)));
                company.setCreationUser(rs.getString(CREATION_USER_COLUMN));
                company.setModificationDate(this.timestamp2Date(rs.getTimestamp(MODIFICATION_DATE_COLUMN)));
                company.setModificationUser(rs.getString(MODIFICATION_USER_COLUMN));
                company.setChanged(false);

                ret.add(company);
            }

            return (Company[]) ret.toArray(new Company[ret.size()]);
        } catch (SQLException e) {
            throw new CompanyDAOException("Something wrong in the database related operations.", e, null);
        } catch (IllegalArgumentException e) {
            throw new CompanyDAOException("Column value is invalid.", e, null);
        } finally {
            releaseResource(rs, statement);
        }
    }

    /**
     * <p>
     * Enumerates all the companies that are present within the data store under the given filter.
     * </p>
     *
     * @param filter the given filter to search the records.
     *
     * @return A list of all the companies present in the data store under the given filter.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    private Company[] listCompanies(Filter filter) throws CompanyDAOException {
        try {
            CustomResultSet rs = (CustomResultSet) searchBundle.search(filter);

            List ret = new ArrayList();

            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong(COMPANY_ID_COLUMN));
                company.setCompanyName(rs.getString(NAME_COLUMN));
                company.setPassCode(rs.getString(PASSCODE_COLUMN));
                company.setCreationDate(this.timestamp2Date(rs.getTimestamp(CREATION_DATE_COLUMN)));
                company.setCreationUser(rs.getString(CREATION_USER_COLUMN));
                company.setModificationDate(this.timestamp2Date(rs.getTimestamp(MODIFICATION_DATE_COLUMN)));
                company.setModificationUser(rs.getString(MODIFICATION_USER_COLUMN));
                company.setChanged(false);

                ret.add(company);
            }

            return (Company[]) ret.toArray(new Company[ret.size()]);
        } catch (SearchBuilderException e) {
            throw new CompanyDAOException("Error occur when search companies from search builder.", e, null);
        } catch (InvalidCursorStateException e) {
            throw new CompanyDAOException("Fails to retrieve the data from the customResultSet.", e, null);
        } catch (IllegalArgumentException e) {
            throw new CompanyDAOException("Column value is invalid.", e, null);
        }
    }

    /**
     * <p>
     * Returns a list of all the companies within the datastore that satisfy the filters that are provided. The filter
     * is defined using classes from the Search Builder v1.3 component.
     * </p>
     *
     * @param filter The filter that is used as criterion to facilitate the search.
     *
     * @return A list of companies that fulfill the given criterion. If no company fulfills it, then an empty array is
     *         returned.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] search(Filter filter) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateNotNull(filter, "filter");

        Company[] companies = listCompanies(filter);

        // get the contact and address for each company
        for (int i = 0; i < companies.length; i++) {
            companies[i] = getAddressAndContact(companies[i]);
            companies[i].setChanged(false);
        }

        return companies;
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated by the DAO and
     * assigned to the company. The Company is also considered to have been created by the specified user. Audit is to
     * be performed if doAudit is true.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the company in the returned array corresponds to the index of the
     *         company in the method argument.
     *
     * @throws IllegalArgumentException if companies is null or contains null elements, or user is null or an empty
     *         String, or if any field in company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] createCompanies(Company[] companies, String user, boolean doAudit) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateNotNull(companies, "companies");

        for (int i = 0; i < companies.length; i++) {
            this.validateCompany(companies[i]);
        }

        TimeTrackerCompanyHelper.validateString(user, "user");

        Connection conn = null;

        try {
            conn = this.createConnection();

            for (int i = 0; i < companies.length; i++) {
                companies[i] = this.createCompany(conn, companies[i], user, doAudit);
            }

            return companies;
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves the companies with the specified ids from the datastore. If there is no company with a given id, null
     * will be returned in the corresponding index.
     * </p>
     *
     * @param ids The ids of the companies to retrieve.
     *
     * @return A list of companies with the given ids. The index of the company corresponds to the index of the id in
     *         the method argument.
     *
     * @throws IllegalArgumentException if ids is null or contains non positive element.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] retrieveCompanies(long[] ids) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateNotNull(ids, "ids");

        for (int i = 0; i < ids.length; i++) {
            TimeTrackerCompanyHelper.validatePositive(ids[i], "The " + i + "th id");
        }

        Connection conn = null;

        try {
            conn = this.createConnection();

            StringBuffer buffer = new StringBuffer();
            buffer.append(" where company_id in (");
            for (int i = 0; i < ids.length; i++) {
                buffer.append(ids[i]);
                if (i != ids.length - 1) {
                    buffer.append(",");
                }
            }
            buffer.append(")");

            Company[] companies = listCompanies(conn, SQL_RETRIEVE_ALL_COMPANY + buffer.toString());

            Map companiesMap = new HashMap();
            // get the contact and address for each company
            for (int i = 0; i < companies.length; i++) {
                companies[i] = getAddressAndContact(companies[i]);
                companies[i].setChanged(false);
                companiesMap.put(new Long(companies[i].getId()), companies[i]);
            }

            Company[] ret = new Company[ids.length];
            for (int i = 0; i < ids.length; i++) {
                ret[i] = (Company) companiesMap.get(new Long(ids[i]));
            }
            return ret;
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the given companies in the data store. The companies are considered to have been modified by the
     * specified user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @throws IllegalArgumentException if the companies array is null or contains null elements, or user is null or is
     *         an empty String, or if any field in company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if some of the passed companies is not found.
     */
    public void updateCompanies(Company[] companies, String user, boolean doAudit) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateNotNull(companies, "companies");

        for (int i = 0; i < companies.length; i++) {
            validateCompany(companies[i]);
            TimeTrackerCompanyHelper.validatePositive(companies[i].getId(), "The " + i + "th id");
        }

        TimeTrackerCompanyHelper.validateString(user, "user");

        Connection conn = null;

        try {
            conn = this.createConnection();

            for (int i = 0; i < companies.length; i++) {
                this.updateCompany(conn, companies[i], user, doAudit);
            }
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes the specified companies from the data store. Audit is to be performed if doAudit is true. The user
     * argument is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     *
     * @throws IllegalArgumentException if companies is null or has null elements, or if doAudit is true and either
     *         user is null or empty string, or the companies have duplicate entries, or if company's id is not
     *         positive.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if some of the passed companies is not found.
     */
    public void deleteCompanies(Company[] companies, boolean doAudit, String user) throws CompanyDAOException {
        TimeTrackerCompanyHelper.validateNotNull(companies, "companies");

        Set ids = new HashSet();

        for (int i = 0; i < companies.length; i++) {
            TimeTrackerCompanyHelper.validateNotNull(companies[i], "The " + i + "th company");
            Long id = new Long(companies[i].getId());
            TimeTrackerCompanyHelper.validatePositive(id.longValue(), "The " + i + "th id");

            if (ids.contains(id)) {
                throw new IllegalArgumentException("Duplicate entries for the same id as " + id + " exist.");
            }

            ids.add(id);
        }

        if (doAudit) {
            TimeTrackerCompanyHelper.validateString(user, "user");
        }

        Connection conn = null;

        try {
            conn = this.createConnection();

            for (int i = 0; i < companies.length; i++) {
                this.deleteCompany(conn, companies[i], doAudit, user);
            }
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Get the audit manager being used.
     * </p>
     *
     * @return audit manager being used.
     */
    public AuditManager getAuditManager() {
        return this.auditManager;
    }

    /**
     * <p>
     * Get the address manager being used.
     * </p>
     *
     * @return the address manager being used.
     */
    public AddressManager getAddressManager() {
        return this.addressManager;
    }

    /**
     * <p>
     * Get the contact manager being used.
     * </p>
     *
     * @return Contact Manager being used.
     */
    public ContactManager getContactManager() {
        return this.contactManager;
    }

    /**
     * <p>
     * This method is not supported currently.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return no return.
     *
     * @throws UnsupportedOperationException always thrown as this method is not supported currently.
     */
    public Company[] createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * This method is not supported currently.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed.
     *
     * @throws UnsupportedOperationException always thrown as this method is not supported currently.
     */
    public void updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * This method is not supported currently.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param doAudit Indicates whether audit is to be performed.
     * @param user the user who performed the deletion.
     *
     * @throws UnsupportedOperationException always thrown as this method is not supported currently.
     */
    public void deleteCompaniesNonAtomically(Company[] companies, boolean doAudit, String user) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * Gets the next ID using the ID generator.
     * </p>
     *
     * @return the next generated unique ID.
     *
     * @throws InvalidIdException if a negative ID is generated.
     * @throws CompanyDAOException if a new ID cannot be generated.
     */
    private long getNextID() throws CompanyDAOException {
        try {
            long id = this.idGenerator.getNextID();
            if (id <= 0) {
                throw new InvalidIdException("The id generated by the IDGeneratorFactory is not positive.", id);
            }
            return id;
        } catch (IDGenerationException e) {
            throw new CompanyDAOException("Next id cannot be generated.", e, null);
        }
    }

    /**
     * <p>
     * Get database connection from the db connection factory.
     * </p>
     *
     * @return A database connection.
     *
     * @throws CompanyDAOException If can't get connection.
     */
    private Connection createConnection() throws CompanyDAOException {
        try {
            // create a DB connection
            return connectionFactory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new CompanyDAOException("Can't get the connection from database.", e, null);
        }
    }

    /**
     * <p>
     * Release the connection.
     * </p>
     *
     * @param conn the connection.
     */
    private void releaseConnection(Connection conn) {
        try {
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // ignore it
        }
    }

    /**
     * <p>
     * Releases the JDBC resources.
     * </p>
     *
     * @param resultSet the resultSet set to be closed.
     * @param statement the SQL statement to be closed.
     */
    private void releaseResource(ResultSet resultSet, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException se) {
                // ignore
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException se) {
                // ignore
            }
        }
    }

    /**
     * Converts a {@link java.util.Date} to a {@link java.sql.Timestamp}.
     *
     * @param date the date to convert.
     *
     * @return a {@link java.sql.Timestamp} instance.
     */
    private Timestamp date2Timestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Converts a {@link java.sql.Timestamp} to a {@link java.util.Date}.
     *
     * @param date the date to convert
     *
     * @return a {@link java.util.Date} instance.
     */
    private Date timestamp2Date(Timestamp date) {
        return new Date(date.getTime());
    }
}
