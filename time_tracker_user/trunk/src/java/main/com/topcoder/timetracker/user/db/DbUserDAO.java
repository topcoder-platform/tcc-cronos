/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchStrategy;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.security.authorization.persistence.GeneralPrincipal;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactException;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserDAO;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.UserStatusDAO;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.UserTypeDAO;
import com.topcoder.timetracker.user.Util;
import com.topcoder.timetracker.user.db.DbUtil.SqlType;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an implementation of the <code>UserDAO</code> interface that utilizes a database as persistence store.
 * </p>
 * <p>
 * It delegates certain Contact-related information to the Time Tracker Contact component and Audit-related
 * information to Time Tracker Audit component.
 * </p>
 * <p>
 * In addition, the <code>DbUserDAO</code> also inserts a <code>Principal</code> for authorization in the
 * Authorization component every time a user is created, and deletes the given principal every time a user is
 * deleted.
 * </p>
 * <p>
 * Thread Safety: This implementation is required to be thread safe. The implementation should maintain the state
 * that it had during construction.
 * </p>
 * <p>
 * <strong>Version 3.2.1</strong> adds user status id and user type id handling for database operation.
 * </p>
 *
 * @author ShindouHikaru, biotrail, George1, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class DbUserDAO implements UserDAO {

    /**
     * <p>
     * Represents the column names mapping used for <code>MappedUserFilterFactory.</code>
     * </p>
     * <p>
     * It is created when declared and initialized in a static initialization block.
     * </p>
     * <p>
     * It will not changed after initialization, including the reference and content.
     * </p>
     */
    private static final Map COLUMNNAMES_MAP = new HashMap();

    /**
     * <p>
     * Represents the mapping from alias names to the column names. It is used in the
     * {@link DbUserDAO#searchUsers(Filter)} to search users.
     * </p>
     * <p>
     * It is created when declared and initialized in a static initialization block.
     * </p>
     * <p>
     * It will not changed after initialization, including the reference and content.
     * </p>
     */
    private static final Map ALIAS_MAP = new HashMap();

    /**
     * <p>
     * Represents the context string for searching. It is used in the {@link DbUserDAO#searchUsers(Filter)} to
     * search users.
     * </p>
     * <p>
     * It is created when declared and never changed afterwards.
     * </p>
     */
    private static final String CONTEXT =
        "SELECT DISTINCT user_account_id AS id," +
               "user_account.company_id AS company_id," +
               "user_account.account_status_id AS account_status_id," +
               "user_account.user_status_id AS user_status_id," +
               "user_account.user_type_id AS user_type_id," +
               "user_name," +
               "password," +
               "billable_type," +
               "user_account.creation_date AS creation_date," +
               "user_account.creation_user AS creation_user," +
               "user_account.modification_date AS modification_date," +
               "user_account.modification_user AS modification_user," +
               "address.address_id AS address_id," +
               "contact.contact_id AS contact_id," +
               "user_status.company_id AS user_status_company_id," +
               "user_status.description AS user_status_description," +
               "user_status.active AS user_status_active," +
               "user_status.creation_date AS user_status_creation_date," +
               "user_status.creation_user AS user_status_creation_user," +
               "user_status.modification_date AS user_status_modification_date," +
               "user_status.modification_user AS user_status_modification_user," +
               "user_type.company_id AS user_type_company_id," +
               "user_type.description AS user_type_description," +
               "user_type.active AS user_type_active," +
               "user_type.creation_date AS user_type_creation_date," +
               "user_type.creation_user AS user_type_creation_user," +
               "user_type.modification_date AS user_type_modification_date," +
               "user_type.modification_user AS user_type_modification_user " +
          "FROM user_account " +
          "LEFT JOIN user_status " +
            "ON user_account.user_status_id = user_status.user_status_id " +
          "LEFT JOIN user_type " +
            "ON user_account.user_type_id = user_type.user_type_id," +
               "address," +
               "contact," +
               "address_relation," +
               "contact_relation," +
               "address_type," +
               "contact_type " +
         "WHERE address_relation.entity_id = user_account_id " +
           "AND contact_relation.entity_id = user_account_id " +
           "AND address.address_id = address_relation.address_id " +
           "AND contact.contact_id = contact_relation.contact_id " +
           "AND address_relation.address_type_id = address_type.address_type_id " +
           "AND contact_relation.contact_type_id = contact_type.contact_type_id " +
           "AND address_type.description = 'USER' " +
           "AND contact_type.description = 'USER' " +
           "AND ";

    /**
     * <p>
     * Represents the sql script for inserting a user account record.
     * </p>
     */
    private static final String INSERT_USER_ACCOUNT =
        "insert into user_account(user_account_id, "
            + "company_id, account_status_id, user_name, password, user_status_id, user_type_id, billable_type, "
            + "creation_date, creation_user, modification_date, modification_user) values "
            + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script for updating a user account record.
     * </p>
     */
    private static final String UPDATE_USER_ACCOUNT =
        "UPDATE user_account " +
           "SET company_id = ?, " +
               "account_status_id = ?, " +
               "user_name = ?, " +
               "password = ?, " +
               "user_status_id = ?, " +
               "user_type_id = ?, " +
               "billable_type = ?, " +
               "creation_date = ?, " +
               "creation_user = ?, " +
               "modification_date = ?, " +
               "modification_user = ? " +
         "WHERE user_account_id = ?";

    /**
     * <p>
     * Represents the sql script for deleting a user account record.
     * </p>
     */
    private static final String DELETE_USER_ACCOUNT = "delete from user_account where user_account_id = ?";

    /**
     * Represents the SQL script to update user name on time entries if the name of a user has been changed.
     */
    private static final String UPDATE_TIME_ENTRIES =
        "UPDATE time_entry SET creation_user = ? WHERE creation_user = ?";

    /**
     * Represents the SQL script to update user name on expense entries if the name of a user has been changed.
     */
    private static final String UPDATE_EXPENSE_ENTRIES =
        "UPDATE expense_entry SET creation_user = ? WHERE creation_user = ?";

    /**
     * Represents the SQL script to update user name on fixed billing entries if the name of a user has been
     * changed.
     */
    private static final String UPDATE_FIXED_BILLING_ENTRIES =
        "UPDATE fix_bill_entry SET creation_user = ? WHERE creation_user = ?";

    /**
     * <p>
     * Represents the sql script for selecting the contact id for the given user id.
     * </p>
     */
    private static final String SEL_CONTACT_ID =
        "select contact_id from contact_relation where entity_id" + " = ? and contact_type_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting the address id for the given user id.
     * </p>
     */
    private static final String SEL_ADDRESS_ID =
        "select address_id from address_relation where entity_id" + " = ? and address_type_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting a user account record.
     * </p>
     */
    private static final String SEL_USER_ACCOUNT =
        "select company_id, account_status_id, "
            + "user_name, password, user_status_id, user_type_id, billable_type, creation_date, creation_user, "
            + "modification_date, modification_user from user_account where user_account_id = ?";

    /**
     * <p>
     * Represents the sql script for selecting all the user ids in the database.
     * </p>
     */
    private static final String SEL_ALL_USER_IDS = "select user_account_id from user_account";

    /**
     * <p>
     * This is a static block and is used to initialize the <code>COLUMNNAMES_MAP</code> and
     * <code>ALIAS_MAP</code> variables.
     * </p>
     */
    static {
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.CREATION_DATE_COLUMN_NAME, "user_account.creation_date");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.MODIFICATION_DATE_COLUMN_NAME,
            "user_account.modification_date");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.CREATION_USER_COLUMN_NAME, "user_account.creation_user");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.MODIFICATION_USER_COLUMN_NAME,
            "user_account.modification_user");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.USERNAME_COLUMN_NAME, "user_account.user_name");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.PASSWORD_COLUMN_NAME, "user_account.password");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.LAST_NAME_COLUMN_NAME, "contact.last_name");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.FIRST_NAME_COLUMN_NAME, "contact.first_name");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.PHONE_NUMBER_COLUMN_NAME, "contact.phone");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.EMAIL_COLUMN_NAME, "contact.email");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.ADDRESS1_COLUMN_NAME, "address.line1");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.ADDRESS2_COLUMN_NAME, "address.line2");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.CITY_COLUMN_NAME, "address.city");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.STATE_COLUMN_NAME, "address.state_name_id");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.ZIPCODE_COLUMN_NAME, "address.zip_code");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.COMPANY_ID_COLUMN_NAME, "user_account.company_id");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.STATUS_COLUMN_NAME, "user_account.account_status_id");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.USER_STATUS_ID_COLUMN_NAME, "user_account.user_status_id");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.USER_STATUS_NAME_COLUMN_NAME,
            "user_status.user_status_name");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.USER_TYPE_ID_COLUMN_NAME, "user_account.user_type_id");
        COLUMNNAMES_MAP.put(MappedUserFilterFactory.USER_TYPE_NAME_COLUMN_NAME, "user_type.user_type_name");

        for (Iterator it = COLUMNNAMES_MAP.values().iterator(); it.hasNext();) {
            String columnName = (String) it.next();

            ALIAS_MAP.put(columnName, columnName);
        }
    }

    /**
     * <p>
     * This is the class provided by the Time Tracker Contract component to persist and retrieve the Contact into
     * from the data store.
     * </p>
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     * <p>
     * It will never be null.
     * </p>
     */
    private final ContactManager contactManager;

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for searching the data store for Users
     * using this implementation.
     * </p>
     * <p>
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     * <p>
     * It will never be null.
     * </p>
     */
    private final MappedUserFilterFactory filterFactory;

    /**
     * <p>
     * This is the connection name that is provided to the connection factory when a connection is acquired.
     * </p>
     *
     * <p>
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * If not specified, then the default connection is used.
     * </p>
     */
    private final String connName;

    /**
     * <p>
     * This is the connection factory that is used to acquire a connection to the persistent store when it is
     * needed.
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
    private final DBConnectionFactory connFactory;

    /**
     * <p>
     * This is the id generator that is used to generate an id for any new users that are added to the persistent
     * store.
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
    private final IDGenerator idGenerator;

    /**
     * <p>
     * This is the <code>DatabaseSearchStrategy</code> used to search the database.
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
    private final SearchStrategy searchStrategy;

    /**
     * <p>
     * This is the AuditManager from the Time Tracker Audit component that is used to perform audits upon a
     * database change.
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
    private final AuditManager auditManager;

    /**
     * <p>
     * This is an instance of AuthorizationPersistence that is used to create, update and remove the Users as
     * Principals within the Authorization component.
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
    private final AuthorizationPersistence authorizationPersistence;

    /**
     * <p>
     * This is the class provided by the Time Tracker Contract component to persist and retrieve the Address
     * into/from the data store.
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
     * This variable indicates whether transactions should be used in the operations or not.
     * </p>
     *
     * <p>
     * If true, then the DAO will start a transaction for each method call, and rollback in case any exception
     * occurs. If false, then no transactions are used.
     * </p>
     *
     * <p>
     * Note that for using with EJBs, this has to be false.
     * </p>
     *
     * <p>
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     */
    private final boolean useTransactions;

    /**
     * <p>
     * This is the UserStatusDAO which is used to retrieve any UserStatus related to the User.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: getUsers
     * </p>
     * <p>
     * Valid Values: Non-null UserStatusDAO instances
     * </p>
     */
    private UserStatusDAO userStatusDao;

    /**
     * <p>
     * This is the UserTypeDAO which is used to retrieve any UserTypes related to the User.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: getUsers
     * </p>
     * <p>
     * Valid Values: Non-null UserTypeDAO instances
     * </p>
     */
    private UserTypeDAO userTypeDao;

    /**
     * <p>
     * Constructor for a <code>DbUserDAO</code> that accepts the necessary parameters for the DAO to function
     * property.
     * </p>
     * <p>
     * Note, the given connection name can be null, which means default connection should be used.
     * </p>
     *
     * @param connFactory
     *            The connection factory to use.
     * @param connName
     *            The connection name to use (or null if the default connection should be used).
     * @param idGen
     *            The name of the id generator to use.
     * @param searchsStrategyNamespace
     *            The configuration namespace of the database search strategy that will be used.
     * @param auditManager
     *            The AuditManager used to create audit entries.
     * @param contactManager
     *            The ContactManager used to persist and retrieve Time Tracker Contact information.
     * @param authPersistence
     *            The authorization persistence to use.
     * @param addressManager
     *            The Address manager used to persist Address information
     * @param userStatusDao
     *            The user status DAO to use
     * @param userTypeDao
     *            The user type DAO to use
     * @param useTransactions
     *            Whether transactions should be used or not. (This should be false if used with EJBs)
     *
     * @throws IllegalArgumentException
     *             if any argument except for connName is null or if connName, idGen or searchsStrategyNamespace is
     *             an empty String.
     *
     * @throws ConfigurationException
     *             if fails to create a <code>DatabaseSearchStrategy</code> instance using the given namespace or
     *             if it is failed to create the id generator
     */
    public DbUserDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchsStrategyNamespace, AuditManager auditManager, ContactManager contactManager,
        AuthorizationPersistence authPersistence, AddressManager addressManager, UserStatusDAO userStatusDao,
        UserTypeDAO userTypeDao, boolean useTransactions) throws ConfigurationException {
        Util.checkNull(connFactory, "connFactory");

        if (connName != null) {
            Util.checkString(connName, "connName");
        }

        Util.checkString(idGen, "idGen");
        Util.checkString(searchsStrategyNamespace, "searchsStrategyNamespace");
        Util.checkNull(auditManager, "auditManager");
        Util.checkNull(contactManager, "contactManager");
        Util.checkNull(authPersistence, "authPersistence");
        Util.checkNull(addressManager, "addressManager");
        Util.checkNull(userStatusDao, "userStatusDao");
        Util.checkNull(userTypeDao, "userTypeDao");

        this.connFactory = connFactory;
        this.connName = connName;

        this.idGenerator = DbUtil.createIDGenerator(idGen);

        // create the searchStrategy using the given namespace
        try {
            this.searchStrategy = new DatabaseSearchStrategy(searchsStrategyNamespace);
        } catch (SearchBuilderConfigurationException e) {
            throw new ConfigurationException("SearchBuilderConfigurationException occurs "
                + "when creating DatabaseSearchStrategy instance.", e);
        }

        this.authorizationPersistence = authPersistence;
        this.useTransactions = useTransactions;

        this.addressManager = addressManager;
        this.contactManager = contactManager;
        this.auditManager = auditManager;
        this.userStatusDao = userStatusDao;
        this.userTypeDao = userTypeDao;

        this.filterFactory = new MappedUserFilterFactory(COLUMNNAMES_MAP);
    }

    /**
     * <p>
     * Adds the specified users into the persistent store.
     * </p>
     * <p>
     * Unique user ids will automatically be generated and assigned to the users.
     * </p>
     * <p>
     * There is also the option to perform an audit.
     * </p>
     * <p>
     * If transaction is required, then if any of the users fail to be added, then the entire batch operation will
     * be rolled back. Otherwise, the failure will occur only for those entities where an exception occurred.
     * </p>
     *
     * @param users
     *            An array of users for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required in the persistence
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void addUsers(User[] users, boolean audit) throws DataAccessException {
        checkUsers(users);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, useTransactions);
            stat = conn.prepareStatement(INSERT_USER_ACCOUNT);

            Throwable[] causes = new Throwable[users.length];
            boolean success = true;

            for (int i = 0; i < users.length; i++) {
                try {
                    if (users[i].getUserStatus() != null) {
                        if (users[i].getUserStatus().getCompanyId() != users[i].getCompanyId()) {
                            throw new IllegalArgumentException(
                                "The company id in user status is not consistent");
                        }
                    }
                    if (users[i].getUserType() != null) {
                        if (users[i].getUserType().getCompanyId() != users[i].getCompanyId()) {
                            throw new IllegalArgumentException(
                                "The company id in user type is not consistent");
                        }
                    }

                    long userId = idGenerator.getNextID();
                    users[i].setId(userId);

                    // sets up the contact relation
                    DbUtil.fillPreparedStatement(stat, createInsertUserAccountParams(users[i]));
                    DbUtil.executeUpdate(stat, -1, -1);
                    Contact contact = users[i].getContact();
                    contactManager.addContact(contact, audit);
                    contactManager.associate(contact, userId, audit);

                    // sets up the address relation
                    Address address = users[i].getAddress();
                    addressManager.addAddress(address, audit);
                    addressManager.associate(address, userId, audit);

                    addPrincipal(users[i]);

                    if (audit) {
                        audit(users[i], null);
                    }

                    users[i].setChanged(false);
                } catch (ContactException e) {
                    causes[i] = e;
                    success = false;
                } catch (IDGenerationException e) {
                    causes[i] = e;
                    success = false;
                } catch (AuthorizationPersistenceException e) {
                    causes[i] = e;
                    success = false;
                } catch (AuditManagerException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                } catch (UnrecognizedEntityException e) {
                    throw new IllegalArgumentException("user status and/or user type is not valid");
                }
            }
            DbUtil.finishBatchOperation(conn, causes, success, "Failed to add users.", useTransactions);

        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given users.
     * </p>
     *
     * @param users
     *            the given users to check
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required in the persistence
     */
    private void checkUsers(User[] users) {
        Util.checkNull(users, "users");
        if (users.length == 0) {
            throw new IllegalArgumentException("The given users is empty.");
        }

        for (int i = 0; i < users.length; i++) {
            Util.checkNull(users[i], "user in users");

            // null user name is not allowed
            if (users[i].getUsername() == null) {
                throw new IllegalArgumentException("Some user have null user name.");
            }

            // null password is not allowed
            if (users[i].getPassword() == null) {
                throw new IllegalArgumentException("Some user have null password.");
            }

            // null status is not allowed
            if (users[i].getStatus() == null) {
                throw new IllegalArgumentException("Some user have null status.");
            }

            // null address is not allowed
            if (users[i].getAddress() == null) {
                throw new IllegalArgumentException("Some user have null address.");
            }

            // null contact is not allowed
            if (users[i].getContact() == null) {
                throw new IllegalArgumentException("Some user have null contact.");
            }

            DbUtil.checkTimeTrackerBean(users[i]);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to insert a user account.
     * </p>
     *
     * @param user
     *            the User to insert to database
     * @return the parameters needed to insert a user account.
     */
    private List createInsertUserAccountParams(User user) {
        List params = new ArrayList();

        params.add(new Long(user.getId()));

        prepareUserAccountParams(params, user);

        return params;
    }

    /**
     * <p>
     * This method creates the parameters needed to prepare parameters for user account query.
     * </p>
     *
     * @param user
     *            the User to insert to database
     * @param params
     *            the list of parameters as in/out argument
     * @since 3.2.1
     */
    private void prepareUserAccountParams(List params, User user) {
        // the company id can be null in the database
        if (user.getCompanyId() == 0) {
            params.add(new SqlType(Types.INTEGER));
        } else {
            params.add(new Long(user.getCompanyId()));
        }

        params.add(new Long(user.getStatus().getId()));
        params.add(user.getUsername());
        params.add(user.getPassword());

        // the user status id can be null in the database
        if (user.getUserStatus() == null) {
            params.add(new SqlType(Types.INTEGER));
        } else {
            params.add(new Long(user.getUserStatus().getId()));
        }
        // the user type id can be null in the database
        if (user.getUserType() == null) {
            params.add(new SqlType(Types.INTEGER));
        } else {
            params.add(new Long(user.getUserType().getId()));
        }
	params.add(new Integer(user.getBillableType()));
        DbUtil.prepareTimeTrackerBeanParams(params, user);
    }

    /**
     * <p>
     * This method audit the given user using the Time Tracker Audit component.
     * </p>
     * <p>
     * The <code>oldUser</code> is the user instance before update and the <code>newUser</code> is the user
     * instance after update.
     * </p>
     *
     * <p>
     * For INSERT operation, <code>oldUser</code> should be null while <code>newUser</code> should not be null.
     * For UPDATE operation, both <code>oldUser</code> and <code>newUser</code> should not be null. For DELETE
     * operation, <code>oldUser</code> should not be null while <code>newUser</code> should be null.
     * </p>
     *
     * @param newUser
     *            the user instance after update
     * @param oldUser
     *            the user instance before update
     *
     * @throws AuditManagerException
     *             if fails to audit the user.
     */
    private void audit(User newUser, User oldUser) throws AuditManagerException {
        User user = (newUser == null) ? oldUser : newUser;
        AuditHeader header = new AuditHeader();
        header.setCompanyId(user.getCompanyId());
        header.setApplicationArea(ApplicationArea.TT_USER);
        header.setTableName("user_account");
        header.setEntityId(user.getId());
        header.setCreationUser(user.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        header.setResourceId(user.getId());

        List auditDetails = new ArrayList();

        if (newUser != null && oldUser == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(createAuditDetail("user_account_id", null, String.valueOf(newUser.getId())));
            auditDetails.add(createAuditDetail("company_id", null, String.valueOf(newUser.getCompanyId())));
            auditDetails.add(createAuditDetail("account_status_id", null, String.valueOf(newUser.getStatus()
                .getId())));
            auditDetails.add(createAuditDetail("user_name", null, newUser.getUsername()));
            auditDetails.add(createAuditDetail("password", null, newUser.getPassword()));
            auditDetails.add(createAuditDetail("user_status_id", null, newUser.getUserStatus() == null ? null
                : String.valueOf(newUser.getUserStatus().getId())));
            auditDetails.add(createAuditDetail("user_type_id", null, newUser.getUserType() == null ? null
                : String.valueOf(newUser.getUserType().getId())));
            auditDetails.add(createAuditDetail("creation_date", null, newUser.getCreationDate().toString()));
            auditDetails.add(createAuditDetail("creation_user", null, newUser.getCreationUser()));
            auditDetails.add(createAuditDetail("modification_date", null, newUser.getModificationDate()
                .toString()));
            auditDetails.add(createAuditDetail("modification_user", null, newUser.getModificationUser()));
        } else if (newUser == null && oldUser != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(createAuditDetail("user_account_id", String.valueOf(oldUser.getId()), null));
            auditDetails.add(createAuditDetail("company_id", String.valueOf(oldUser.getCompanyId()), null));
            auditDetails.add(createAuditDetail("account_status_id", String.valueOf(oldUser.getStatus()
                .getId()), null));
            auditDetails.add(createAuditDetail("user_name", oldUser.getUsername(), null));
            auditDetails.add(createAuditDetail("password", oldUser.getPassword(), null));
            auditDetails.add(createAuditDetail("user_status_id", oldUser.getUserStatus() == null ? null
                : String.valueOf(oldUser.getUserStatus().getId()), null));
            auditDetails.add(createAuditDetail("user_type_id", oldUser.getUserType() == null ? null : String
                .valueOf(oldUser.getUserType().getId()), null));
            auditDetails.add(createAuditDetail("creation_date", oldUser.getCreationDate().toString(), null));
            auditDetails.add(createAuditDetail("creation_user", oldUser.getCreationUser(), null));
            auditDetails.add(createAuditDetail("modification_date", oldUser.getModificationDate().toString(),
                null));
            auditDetails.add(createAuditDetail("modification_user", oldUser.getModificationUser(), null));
        } else {
            // for update operation
            header.setActionType(AuditType.UPDATE);
            auditDetails.add(createAuditDetail("user_account_id", String.valueOf(oldUser.getId()), String
                .valueOf(newUser.getId())));
            auditDetails.add(createAuditDetail("company_id", String.valueOf(oldUser.getCompanyId()), String
                .valueOf(newUser.getCompanyId())));
            auditDetails.add(createAuditDetail("account_status_id", String.valueOf(newUser.getStatus()
                .getId()), String.valueOf(oldUser.getStatus().getId())));
            auditDetails.add(createAuditDetail("user_name", oldUser.getUsername(), newUser.getUsername()));
            auditDetails.add(createAuditDetail("password", oldUser.getPassword(), newUser.getPassword()));
            auditDetails.add(createAuditDetail("user_status_id", oldUser.getUserStatus() == null ? null
                : String.valueOf(oldUser.getUserStatus().getId()), newUser.getUserStatus() == null ? null
                : String.valueOf(newUser.getUserStatus().getId())));
            auditDetails.add(createAuditDetail("user_type_id", oldUser.getUserType() == null ? null : String
                .valueOf(oldUser.getUserType().getId()), newUser.getUserType() == null ? null : String
                .valueOf(newUser.getUserType().getId())));
            auditDetails.add(createAuditDetail("creation_date", oldUser.getCreationDate().toString(), newUser
                .getCreationDate().toString()));
            auditDetails.add(createAuditDetail("creation_user", oldUser.getCreationUser(), newUser
                .getCreationUser()));
            auditDetails.add(createAuditDetail("modification_date", oldUser.getModificationDate().toString(),
                newUser.getModificationDate().toString()));
            auditDetails.add(createAuditDetail("modification_user", oldUser.getModificationUser(), newUser
                .getModificationUser()));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));

        auditManager.createAuditRecord(header);
    }

    /**
     * <p>
     * This method creates a <code>AuditDetail</code> instance based on the give parameters.
     * </p>
     *
     * @param columnName
     *            the column name
     * @param oldValue
     *            the old column value
     * @param newValue
     *            the new column value
     *
     * @return the <code>AuditDetail</code> instance based on the give parameters.
     */
    private AuditDetail createAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();
        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);
        detail.setPersisted(false);

        return detail;
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Users array.
     * </p>
     * <p>
     * There is also the option to perform an audit.
     * </p>
     * <p>
     * If the old user name is not the same as the new user name, then the old principal will be removed from
     * database and the new principal will add to the database.
     * </p>
     * <p>
     * If transaction is required, then if any of the users fail to be added, then the entire batch operation will
     * be rolled back. Otherwise, the failure will occur only for those entities where an exception occurred.
     * </p>
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * @param users
     *            An array of users for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if users is null, empty or contains null values, or some user contains null property which is
     *             required in the persistence
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void updateUsers(User[] users, boolean audit) throws DataAccessException {
        checkUsers(users);

        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statementTime = null;
        PreparedStatement statementFixedBilling = null;
        PreparedStatement statementExpense = null;

        try {
            conn = DbUtil.getConnection(connFactory, connName, useTransactions);

            Throwable[] causes = new Throwable[users.length];
            boolean success = true;

            statement = conn.prepareStatement(UPDATE_USER_ACCOUNT);
            statementTime = conn.prepareStatement(UPDATE_TIME_ENTRIES);
            statementFixedBilling = conn.prepareStatement(UPDATE_FIXED_BILLING_ENTRIES);
            statementExpense = conn.prepareStatement(UPDATE_EXPENSE_ENTRIES);

            for (int i = 0; i < users.length; i++) {

                // skips the user if it hasn't been changed
                if (!users[i].isChanged()) {
                    continue;
                }

                try {
                    if (users[i].getUserStatus() != null) {
                        if (users[i].getUserStatus().getCompanyId() != users[i].getCompanyId()) {
                            throw new IllegalArgumentException(
                                "The company id in user status is not consistent");
                        }
                    }
                    if (users[i].getUserType() != null) {
                        if (users[i].getUserType().getCompanyId() != users[i].getCompanyId()) {
                            throw new IllegalArgumentException(
                                "The company id in user type is not consistent");
                        }
                    }

                    final long userId = users[i].getId();
                    final User oldUser = getSimpleUser(conn, userId);

                    DbUtil.fillPreparedStatement(statement, createUpdateUserAccountParams(users[i]));

                    // update user_account set company_id = ?, account_status_id = ?,
                    // user_name = ?, password = ?, user_status_id = ?, user_type_id = ?,
                    // creation_date = ?, creation_user = ?,
                    // modification_date = ?, modification_user = ? where user_account_id = ?
                    DbUtil.executeUpdate(statement, -1, -1);

                    // update the contact relation if necessary
                    long contactId = getContactId(conn, userId);
                    long addressId = getAddressId(conn, userId);
                    if (contactId != users[i].getContact().getId()) {
                        contactManager.deassociate(contactManager.retrieveContact(contactId), userId, audit);
                        contactManager.associate(users[i].getContact(), userId, audit);
                    }

                    // update the address relation if necessary
                    if (addressId != users[i].getAddress().getId()) {
                        addressManager.deassociate(addressManager.retrieveAddress(addressId), userId, audit);
                        addressManager.associate(users[i].getAddress(), userId, audit);
                    }

                    contactManager.updateContact(users[i].getContact(), audit);
                    addressManager.updateAddress(users[i].getAddress(), audit);

                    if (!oldUser.getUsername().equals(users[i].getUsername())) {
                        List params = new ArrayList();

                        params.add(users[i].getUsername());
                        params.add(oldUser.getUsername());
                        DbUtil.fillPreparedStatement(statementExpense, params);
                        DbUtil.fillPreparedStatement(statementFixedBilling, params);
                        DbUtil.fillPreparedStatement(statementTime, params);

                        DbUtil.executeUpdate(statementExpense, -1, -1);
                        DbUtil.executeUpdate(statementFixedBilling, -1, -1);
                        DbUtil.executeUpdate(statementTime, -1, -1);

                        // update the principal
                        removePrincipal(oldUser.getUsername());
                        addPrincipal(users[i]);
                    }

                    if (audit) {
                        audit(users[i], oldUser);
                    }

                    users[i].setChanged(false);
                } catch (ContactException e) {
                    causes[i] = e;
                    success = false;
                } catch (AuthorizationPersistenceException e) {
                    causes[i] = e;
                    success = false;
                } catch (AuditManagerException e) {
                    causes[i] = e;
                    success = false;
                } catch (DataAccessException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to update users.", useTransactions);

        } catch (SQLException e) {
            throw new DataAccessException("Error when preparing PreparedStatement", e);
        } finally {
            DbUtil.closeStatement(statementExpense);
            DbUtil.closeStatement(statementFixedBilling);
            DbUtil.closeStatement(statementTime);
            DbUtil.closeStatement(statement);
            DbUtil.closeConnection(conn);

        }
    }

    /**
     * <p>
     * This method creates the parameters needed to update a user account.
     * </p>
     *
     * @param user
     *            the User to update to database
     * @return the parameters needed to update a user account.
     */
    private List createUpdateUserAccountParams(User user) {
        List params = new ArrayList();

        prepareUserAccountParams(params, user);
        params.add(new Long(user.getId()));

        return params;
    }

    /**
     * <p>
     * This method removes all the principals for the given user name from database.
     * </p>
     *
     * @param oldUserName
     *            the user name to search its principals
     *
     * @throws AuthorizationPersistenceException
     *             if fails to remove the principals
     */
    private void removePrincipal(String oldUserName) throws AuthorizationPersistenceException {
        Collection principals = authorizationPersistence.searchPrincipals(oldUserName);

        for (Iterator it = principals.iterator(); it.hasNext();) {
            Principal principal = (Principal) it.next();
            authorizationPersistence.removePrincipal(principal);
        }
    }

    /**
     * <p>
     * This method adds a principal for the given user name to database.
     * </p>
     *
     * @param user
     *            the <code>User</code> instance to add a principal for it
     *
     * @throws AuthorizationPersistenceException
     *             if fails to add the principal
     */
    private void addPrincipal(User user) throws AuthorizationPersistenceException {
        Principal principal = new GeneralPrincipal(user.getId(), user.getUsername());
        authorizationPersistence.addPrincipal(principal);
    }

    /**
     * <p>
     * This method queries the database to get the contact that is associated with the given user.
     * </p>
     *
     * <p>
     * If the contact record cannot be found, then DataAccessException will be thrown.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param userId
     *            the user id to get its contact id
     * @return the contact id for the user
     *
     * @throws SQLException
     *             if a database access error occurs
     * @throws DataAccessException
     *             if the contact record cannot be found
     */
    private long getContactId(Connection conn, long userId) throws SQLException, DataAccessException {
        // select contact_id from contact_relation where entity_id = ? and contact_type_id = ?
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SEL_CONTACT_ID);

            List params = new ArrayList();
            params.add(new Long(userId));
            params.add(new Long(ContactType.USER.getId()));
            DbUtil.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            // get the contact id
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new DataAccessException("Failed to get the contact id for the user with id [" + userId
                    + "].");
            }

        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method queries the database to get the address that is associated with the given user.
     * </p>
     *
     * <p>
     * If the address record cannot be found, then DataAccessException will be thrown.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param userId
     *            the user id to get its address id
     * @return the address id for the user
     *
     * @throws SQLException
     *             if a database access error occurs
     * @throws DataAccessException
     *             if the address record cannot be found
     */
    private long getAddressId(Connection conn, long userId) throws SQLException, DataAccessException {
        // select address_id from address_relation where entity_id = ? and address_type_id = ?
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SEL_ADDRESS_ID);

            List params = new ArrayList();
            params.add(new Long(userId));
            params.add(new Long(AddressType.USER.getId()));
            DbUtil.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            // get the address id
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new DataAccessException("Failed to get the address id for the user with id [" + userId
                    + "].");
            }

        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the project with the specified user ids.
     * </p>
     * <p>
     * There is also the option to perform an audit.
     * </p>
     * <p>
     * If transaction is required, then if any of the users fail to be added, then the entire batch operation will
     * be rolled back. Otherwise, the failure will occur only for those entities where an exception occurred.
     * </p>
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * @param userIds
     *            An array of userIds for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public void removeUsers(long[] userIds, boolean audit) throws DataAccessException {
        checkUserIds(userIds);

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, useTransactions);
            stat = conn.prepareStatement(DELETE_USER_ACCOUNT);

            Throwable[] causes = new Throwable[userIds.length];
            boolean success = true;

            for (int i = 0; i < userIds.length; i++) {
                try {
                    User user = getSimpleUser(conn, userIds[i]);

                    // remove the contact relation
                    long contactId = getContactId(conn, userIds[i]);
                    Contact contact = contactManager.retrieveContact(contactId);
                    contactManager.deassociate(contact, userIds[i], audit);
                    contactManager.removeContact(contactId, audit);

                    // remove the address relation
                    long addressId = getAddressId(conn, userIds[i]);
                    Address address = addressManager.retrieveAddress(addressId);
                    addressManager.deassociate(address, userIds[i], audit);
                    addressManager.removeAddress(addressId, audit);

                    // remove the user account
                    List params = new ArrayList();
                    params.add(new Long(userIds[i]));
                    DbUtil.fillPreparedStatement(stat, params);
                    DbUtil.executeUpdate(stat, -1, -1);

                    // remove the principal
                    removePrincipal(user.getUsername());

                    if (audit) {
                        audit(null, user);
                    }

                } catch (ContactException e) {
                    causes[i] = e;
                    success = false;
                } catch (DataAccessException e) {
                    causes[i] = e;
                    success = false;
                } catch (AuthorizationPersistenceException e) {
                    causes[i] = e;
                    success = false;
                } catch (AuditManagerException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            DbUtil.finishBatchOperation(conn, causes, success, "Failed to remove users.", useTransactions);
        } catch (SQLException e) {
            throw new DataAccessException("Error creating preparing statement", e);
        } finally {
            DbUtil.closeStatement(stat);
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given user id array.
     * </p>
     *
     * @param userIds
     *            the user id array to check
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     */
    private void checkUserIds(long[] userIds) {
        Util.checkNull(userIds, "userIds");

        if (userIds.length == 0) {
            throw new IllegalArgumentException("The given user ids array is empty.");
        }

        for (int i = 0; i < userIds.length; i++) {
            if (userIds[i] <= 0) {
                throw new IllegalArgumentException("The given user id array contains negative long value.");
            }
        }
    }

    /**
     * <p>
     * Retrieves an array of <code>User</code> objects that reflects the data in the persistent store on the Time
     * Tracker User with the specified <code>userIds</code>.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a user with the provided id was not found in the persistence
     * store.
     * </p>
     *
     * @return The users corresponding to the provided ids.
     * @param userIds
     *            An array of userIds for which the operation should be performed.
     *
     * @throws IllegalArgumentException
     *             if userIds is null, empty or contains values &lt;= 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException
     *             if a problem occurs during the batch operation.
     */
    public User[] getUsers(long[] userIds) throws DataAccessException {
        checkUserIds(userIds);

        Connection conn = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, useTransactions);

            Throwable[] causes = new Throwable[userIds.length];
            boolean success = true;

            User[] users = new User[userIds.length];
            for (int i = 0; i < users.length; i++) {
                try {
                    users[i] = getUser(conn, userIds[i]);
                } catch (PersistenceException e) {
                    causes[i] = e;
                    success = false;
                } catch (DataAccessException e) {
                    causes[i] = e;
                    success = false;
                } catch (SQLException e) {
                    causes[i] = e;
                    success = false;
                }
            }

            if (!success) {
                if (causes.length > 1) {
                    throw new BatchOperationException("Failed to get users.", causes);
                } else {
                    throw new DataAccessException("Failed to get user.", causes[0]);
                }
            }

            return users;
        } finally {
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves the <code>User</code> instance from database for the given user id.
     * </p>
     *
     * <p>
     * The contact and address relations will be included.
     * </p>
     *
     * <p>
     * Note, if the user id is cannot be found in the database, then UnrecognizedEntityException will be thrown.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param userId
     *            the user id to find the user
     * @return the <code>User</code> instance for the user
     *
     * @throws SQLException
     *             if a database access error occurs
     * @throws UnrecognizedEntityException
     *             if the user id cannot be located in the database
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws PersistenceException
     *             if fails to retrieve the address or contact relation
     */
    private User getUser(Connection conn, long userId) throws SQLException, DataAccessException,
        PersistenceException {
        User user = getSimpleUser(conn, userId);
        long addressId = getAddressId(conn, userId);
        long contactId = getContactId(conn, userId);

        try {
            user.setAddress(addressManager.retrieveAddress(addressId));
        } catch (AssociationException ae) {
            throw new PersistenceException("Error retrieving user's address information.", ae);
        }
        try {
            user.setContact(contactManager.retrieveContact(contactId));
        } catch (AssociationException ae) {
            throw new PersistenceException("Error retrieving user's contact information.", ae);
        }

        user.setChanged(false);

        return user;
    }

    /**
     * <p>
     * Retrieves the <code>User</code> instance from database for the given user id.
     * </p>
     *
     * <p>
     * The contact and address relations will not be included.
     * </p>
     *
     * <p>
     * Note, if the user id is cannot be found in the database, then UnrecognizedEntityException will be thrown.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @param userId
     *            the user id to find the user
     * @return the <code>User</code> instance for the user
     *
     * @throws SQLException
     *             if a database access error occurs
     * @throws UnrecognizedEntityException
     *             if the user id cannot be located in the database
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    private User getSimpleUser(Connection conn, long userId) throws SQLException, DataAccessException {
        // select company_id, account_status_id, user_name, password,
        // creation_date, creation_user, modification_date, modification_user
        // from user_account where user_account_id = ?
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SEL_USER_ACCOUNT);

            List params = new ArrayList();
            params.add(new Long(userId));
            DbUtil.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(userId);
                int pos = 1;
                long companyId = rs.getLong(pos++);
                if (companyId != 0) {
                    user.setCompanyId(companyId);
                }

                // get all the columns from database and set the java bean
                user.setStatus(getStatusById(rs.getLong(pos++)));
                user.setUsername(rs.getString(pos++));
                user.setPassword(rs.getString(pos++));
                Object userStatusId = rs.getObject(pos);
                if (userStatusId != null) {
                    UserStatus[] userStatuses = userStatusDao.getUserStatuses(new long[] {rs.getLong(pos)});
                    user.setUserStatus(userStatuses[0]);
                }
                pos++;
                Object userTypeId = rs.getObject(pos);
                if (userTypeId != null) {
                    UserType[] userTypes = userTypeDao.getUserTypes(new long[] {rs.getLong(pos)});
                    user.setUserType(userTypes[0]);
                }
                pos++;
		user.setBillableType(rs.getInt(pos++));
                user.setCreationDate(rs.getDate(pos++));
                user.setCreationUser(rs.getString(pos++));
                user.setModificationDate(rs.getDate(pos++));
                user.setModificationUser(rs.getString(pos++));

                return user;
            } else {
                throw new UnrecognizedEntityException(userId);
            }

        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method gets the <code>Status</code> instance by the given status id.
     * </p>
     *
     * <p>
     * If the status id is not defined in the <code>Status</code>, then DataAccessException will be thrown.
     * </p>
     *
     * @param statusId
     *            the status id
     * @return the <code>Status</code> instance for the given status id
     *
     * @throws DataAccessException
     *             if the status id is not defined in the <code>Status</code>
     */
    private static Status getStatusById(long statusId) throws DataAccessException {
        Status status = null;

        // go through all the enums in the Status
        for (Iterator it = Enum.getEnumList(Status.class).iterator(); it.hasNext();) {
            Status statusEnum = (Status) it.next();
            if (statusEnum.getId() == statusId) {
                status = statusEnum;
            }
        }

        // no status can be found
        if (status == null) {
            throw new DataAccessException("The status id [" + statusId + "] is unkown.");
        }

        return status;
    }

    /**
     * <p>
     * Searches the persistent store for any users that satisfy the criteria that was specified in the provided
     * search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>UserFilterFactory</code> returned by {@link UserDAO#getUserFilterFactory()}, or a composite Search
     * Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code> from Search
     * Builder component) that combines the filters created using <code>UserFilterFactory</code>.
     * </p>
     *
     * <p>
     * If no records cannot found by the filter, then an empty array will be returned.
     * </p>
     *
     * @return The users satisfying the conditions in the search filter.
     * @param filter
     *            The filter used to search for users.
     *
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] searchUsers(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result =
                (CustomResultSet) searchStrategy.search(CONTEXT, filter, Collections.EMPTY_LIST, ALIAS_MAP);

            int size = result.getRecordCount();

            // get all the users from the search result
            User[] users = new User[size];
            for (int i = 0; i < size; ++i) {
                result.next();
                users[i] = getUser(result);
            }

            return users;
        } catch (SearchBuilderException sbe) {
            throw new DataAccessException("Failed to search the users according to the given filter.", sbe);
        } catch (InvalidCursorStateException icse) {
            throw new DataAccessException("Failed to retrieve the users.", icse);
        } catch (PersistenceException pe) {
            throw new DataAccessException("Failed to retrieve users' associated information.", pe);
        }
    }

    /**
     * <p>
     * Creates a <code>User</code> instance from the given <code>CustomResultSet</code> instance.
     * </p>
     *
     * @param result the <code>CustomResultSet</code> instance.
     * @return the <code>User</code> created from the given <code>CustomResultSet</code> instance.
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private User getUser(CustomResultSet result) throws InvalidCursorStateException, PersistenceException {
        User user = new User();

        setBeansProperties(user, result, "");
        user.setCompanyId(result.getLong("company_id"));

        try {
            user.setStatus(getStatusById(result.getLong("account_status_id")));
        } catch (DataAccessException dae) {
            throw new InvalidCursorStateException("Invalid status", dae);
        }

        if (result.getObject("user_status_id") != null) {
            user.setUserStatus(getUserStatus(result));
        }
        if (result.getObject("user_type_id") != null) {
            user.setUserType(getUserType(result));
        }

        user.setUsername(result.getString("user_name"));
        user.setPassword(result.getString("password"));

        if (result.getObject("billable_type") != null) {
            user.setBillableType(result.getInt("billable_type"));
        } else {
            user.setBillableType(-1);
        }

        try {
            user.setAddress(this.addressManager.retrieveAddress(result.getLong("address_id")));
        } catch (AssociationException ae) {
            throw new PersistenceException("Error retrieving user's address information.", ae);
        }
        try {
            user.setContact(this.contactManager.retrieveContact(result.getLong("contact_id")));
        } catch (AssociationException ae) {
            throw new PersistenceException("Error retrieving user's contact information.", ae);
        }

        user.setChanged(false);
        return user;
    }

    /**
     * <p>
     * Creates a <code>UserStatus</code> instance from the given <code>CustomResultSet</code> instance.
     * </p>
     *
     * @param result the <code>CustomResultSet</code> instance.
     * @return the <code>UserStatus</code> created from the given <code>CustomResultSet</code> instance.
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance.
     */
    private static UserStatus getUserStatus(CustomResultSet result) throws InvalidCursorStateException {
        UserStatus status = new UserStatus();

        setBeansProperties(status, result, "user_status_");
        status.setCompanyId(result.getLong("user_status_company_id"));
        status.setDescription(result.getString("user_status_description"));
        status.setActive(result.getInt("user_status_active") != 0);

        status.setChanged(false);
        return status;
    }

    /**
     * <p>
     * Creates a <code>UserType</code> instance from the given <code>CustomResultSet</code> instance.
     * </p>
     *
     * @param result the <code>CustomResultSet</code> instance.
     * @return the <code>UserType</code> created from the given <code>CustomResultSet</code> instance.
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance.
     */
    private static UserType getUserType(CustomResultSet result) throws InvalidCursorStateException {
        UserType type = new UserType();

        setBeansProperties(type, result, "user_type_");
        type.setCompanyId(result.getLong("user_type_company_id"));
        type.setDescription(result.getString("user_type_description"));
        type.setActive(result.getInt("user_type_active") != 0);

        type.setChanged(false);
        return type;
    }

    /**
     * <p>
     * Initializes common properties of various time tracker beans.
     * </p>
     *
     * @param bean a time tracker bean to initialize its properties.
     * @param result the <code>CustomResultSet</code> instance to initialize properties from.
     * @param prefix the prefix to use while initializing properties of the bean.
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance.
     */
    private static void setBeansProperties(TimeTrackerBean bean, CustomResultSet result, String prefix)
            throws InvalidCursorStateException {
        bean.setId(result.getLong(prefix + "id"));
        bean.setCreationDate(result.getDate(prefix + "creation_date"));
        bean.setCreationUser(result.getString(prefix + "creation_user"));
        bean.setModificationDate(result.getDate(prefix + "modification_date"));
        bean.setModificationUser(result.getString(prefix + "modification_user"));
    }

    /**
     * <p>
     * Retrieves the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when
     * searching for users.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by the
     * given factory should be used with {@link UserDAO#searchUsers(Filter)}.
     * </p>
     *
     * @return the <code>UserFilterFactory</code> that is capable of creating SearchFilters to use when searching
     *         for users.
     */
    public UserFilterFactory getUserFilterFactory() {
        return this.filterFactory;
    }

    /**
     * <p>
     * Retrieves all the users that are currently in the persistent store.
     * </p>
     *
     * @return An array of all users retrieved from the persistent store.
     *
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public User[] getAllUsers() throws DataAccessException {
        Connection conn = null;
        try {
            conn = DbUtil.getConnection(connFactory, connName, useTransactions);

            // get all the user ids in the database
            long[] userIds = getAllUserIds(conn);

            // get each complete user by each id
            User[] users = new User[userIds.length];
            for (int i = 0; i < userIds.length; i++) {
                users[i] = getUser(conn, userIds[i]);
            }

            return users;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to get all the users.", e);
        } catch (PersistenceException e) {
            throw new DataAccessException("Failed to get all the users.", e);
        } finally {
            DbUtil.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method queries the database for all the user account ids.
     * </p>
     *
     * @param conn
     *            the database connection to access the database
     * @return all the use account ids in the database
     *
     * @throws SQLException
     *             if a database access error occurs
     */
    private long[] getAllUserIds(Connection conn) throws SQLException {
        // select user_account_id from user_account
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SEL_ALL_USER_IDS);
            rs = pstmt.executeQuery();

            List ids = new ArrayList();

            while (rs.next()) {
                ids.add(new Long(rs.getLong(1)));
            }

            long[] longIds = new long[ids.size()];
            for (int i = 0; i < longIds.length; i++) {
                longIds[i] = ((Long) ids.get(i)).longValue();
            }

            return longIds;
        } finally {
            DbUtil.closeResultSet(rs);
            DbUtil.closeStatement(pstmt);
        }
    }

}
