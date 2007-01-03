/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;
import com.cronos.timetracker.common.DBUtils;
import com.cronos.timetracker.common.Utils;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.encryption.EncryptionException;
import com.topcoder.search.builder.database.DatabaseSearchStringBuilder;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Database implementation of the CompanyDAO interface. It persisting and retrieving the company
 * information from the database.
 * </p>
 * <p>
 * This DAO is capable of performing the basic CRUDE operations in non-batch
 * mode. This means that only single entities are processed at a time.
 * </p>
 * <p>
 * The DAO is capable of performing the operations on multiple Companies
 * simultaneously (batch mode). While in batch mode, the operation can be done
 * atomically, or separately. If done atomically, then a failure at any one of
 * the specified entries will mean that the entire batch will be rolled back.
 * Otherwise, only the company where a failure occurred will be rolled back.
 * </p>
 * <p>
 * Thread Safety: The class itself is thread safe because of immutability, but
 * the thread safety issue of the class extends to the back-end data store. The
 * Transaction Isolation level of the JDBC connections will affect the overall
 * thread safety of this class. The class may be reused by multiple concurrent
 * threads - depending on usage and configuration.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class DbCompanyDAO implements CompanyDAO {

    /**
     * The SQL query. Deletes the company from database.
     */
    private static final String DELETE_COMPANY = "DELETE FROM Company WHERE company.company_id = ?";

    /**
     * The SQL query. Deletes the address from database.
     */
    private static final String DELETE_ADDRESS = "DELETE FROM Address WHERE address.address_id = ?";

    /**
     * The SQL query. Deletes the contact from database.
     */
    private static final String DELETE_CONTACT = "DELETE FROM Contact WHERE contact.contact_id = ?";

    /**
     * The SQL query. Deletes the company address from database.
     */
    private static final String DELETE_COMPANY_ADDRESS =
        "DELETE FROM Company_Address WHERE company_address.company_id = ?";

    /**
     * The SQL query. Deletes the company contact from database.
     */
    private static final String DELETE_COMPANY_CONTACT =
        "DELETE FROM Company_Contact WHERE company_contact.company_id = ?";

    /**
     * The SQL query. Updates the company in database.
     */
    private static final String UPDATE_COMPANY =
        "UPDATE Company SET company.name = ?, company.passcode = ?, company.modification_date = ?, "
        + "company.modification_user = ? WHERE Company.company_id = ?";

    /**
     * The SQL query. Updates the company contact in database.
     */
    private static final String UPDATE_COMPANY_CONTACT =
        "UPDATE company_contact SET company_contact.contact_id = ?, company_contact.modification_date = ?, "
        + "company_contact.modification_user = ? WHERE company_contact.company_id = ?";

    /**
     * The SQL query. Updates the company address in database.
     */
    private static final String UPDATE_COMPANY_ADDRESS =
        "UPDATE Company_Address SET company_address.address_id = ?, company_address.modification_date = ?, "
        + "company_address.modification_user = ? WHERE Company_Address.company_id = ?";

    /**
     * The SQL query. Inseert the company into the database.
     */
    private static final String INSERT_COMPANY =
        "INSERT INTO company(company_id, name, passcode, creation_date, creation_user, modification_date, "
        + "modification_user) VALUES (?, ?, ?, ?, ? ,?, ?)";

    /**
     * The SQL query. Inseert the company address into the database.
     */
    private static final String INSERT_COMPANY_ADDRESS =
        "INSERT INTO Company_Address(company_id, address_id, creation_date, creation_user, modification_date, "
        + "modification_user) VALUES(?,?,?,?,?,?)";

    /**
     * The SQL query. Inseert the company contact into the database.
     */
    private static final String INSERT_COMPANY_CONTACT =
        "INSERT INTO Company_Contact(company_id, contact_id, creation_date, creation_user, "
        + "modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The 'create' operation flag. It is used in batch mode.
     */
    private static final int CREATE_OP = 1;

    /**
     * The 'delete' operation flag. It is used in batch mode.
     */
    private static final int DELETE_OP = 2;

    /**
     * The 'update' operation flag. It is used in batch mode.
     */
    private static final int UPDATE_OP = 3;

    /**
     * The base SQL query.
     */
    private static final String BASE_QUERY =
        "SELECT Company.company_id, Company.name, Company.passcode, Company.creation_date "
        + "AS CompanyCreationDate, Company.creation_user AS CompanyCreationUser, Company.modification_date "
        + "AS CompanyModificationDate, Company.modification_user AS CompanyModificationUser, "
        + "address.address_id, address.line1, address.line2, address.zip_code, address.city, "
        + "address.creation_date AS AddrCreationDate, address.creation_user AS AddrCreationUser, "
        + "address.modification_date AS AddrModificationDate, address.modification_user AS AddrModificationUser, "
        + "state_name.state_name_id, state_name.name AS StateName, state_name.abbreviation, "
        + "state_name.creation_date AS StateCreationDate, state_name.modification_date AS StateModificationDate, "
        + "state_name.creation_user AS StateCreationUser, state_name.modification_user AS StateModificationUser, "
        + "contact.contact_id, contact.first_name, contact.last_name, contact.phone, contact.email, "
        + "contact.creation_date AS ContactCreationDate, contact.modification_date AS ContactModificationDate, "
        + "contact.creation_user AS ContactCreationUser, contact.modification_user AS ContactModificationUser "
        + "FROM company, company_address, company_contact, contact, address, state_name "
        + "WHERE company.company_id = company_contact.company_id AND company.company_id = company_address.company_id "
        + "AND company_address.address_id = address.address_id AND company_contact.contact_id = contact.contact_id "
        + "AND address.state_name_id = state_name.state_name_id ";

    /**
     * The base filte SQL query.
     */
    private static final String BASE_FILTER_QUERY = BASE_QUERY + " AND ";


    /**
     * <p>
     * This is the connection factory where where connections to the data store
     * will be retrieved. It may not be null.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setConnectionFactory
     * </p>
     * <p>
     * Accessed In: getConnectionFactory
     * </p>
     * <p>
     * Utilized In: createCompany, retrieveCompany, updateCompany,
     * deleteCompany, listCompanies, searchCompanies, createCompanies,
     * retrieveCompanies, updateCompanies, deleteCompanies
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * This is the connection name which will be used to retrieve a database
     * connection from the connection factory. It may not be null.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setConnectionName
     * </p>
     * <p>
     * Accessed In: getConnectionName
     * </p>
     * <p>
     * Utilized In: createCompany, retrieveCompany, updateCompany,
     * deleteCompany, listCompanies, searchCompanies, createCompanies,
     * retrieveCompanies, updateCompanies, deleteCompanies
     * </p>
     */
    private String connectionName;

    /**
     * <p>
     * This is the algorithm name that is used when encrypting/decrypting the
     * company passcode into the datastore.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setAlgorithmName
     * </p>
     * <p>
     * Accessed In: getAlgorithmName
     * </p>
     * <p>
     * Utilized In: createCompany, retrieveCompany, updateCompany,
     * createCompanies, retrieveCompanies and updateCompanies.
     * </p>
     */
    private String algorithmName;

    /**
     * <p>
     * An instance of the id generator that used to generate ids for the
     * entities.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: getIdGenerator
     * </p>
     * <p>
     * Utilized In: createCompany, updateCompany, createCompanies,and
     * updateCompanies.
     * </p>
     */
    private IDGenerator idGenerator;

    /**
     * <p>
     * This is the search string builder that is used to perform the searches
     * based on any provided filters. It is preconfigured with an SQL Statement,
     * and is coupled with another preconfigured Search Builder filter that is
     * used to tie the separate tables together.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Modified In: Not modified
     * </p>
     * <p>
     * Utilized In: searchCompanies
     * </p>
     */
    private DatabaseSearchStringBuilder searchStringBuilder;

    /**
     * <p>
     * Constructor that accepts the connection factory, connection name, id
     * generator name, and algorithm name. Whether the DAO will function in
     * atomic batch mode is also specified here.
     * </p>
     *
     * @param connectionFactory The connection factory to retrieve DB
     *        connections from.
     * @param connectionName The connection name to provide to the connection
     *        factory.
     * @param algorithmName The name of the algorithm that is used for
     *        encrypting/decrypting the company passcode.
     * @param idGeneratorName Name of the id generator to use.
     * @throws IDGenerationException if error occur while creating IDGenerator.
     * @throws IllegalArgumentException if connectionName, algorithmName,
     *         idGeneratorName or connectionFactory is null, or if
     *         connectionName or algorithmName or idGeneratorName is an empty
     *         String.
     */
    public DbCompanyDAO(DBConnectionFactory connectionFactory, String connectionName, String algorithmName,
            String idGeneratorName) throws IDGenerationException {

        Utils.checkNull(connectionFactory, "connectionFactory");
        Utils.checkString(connectionName, "connectionName", false);
        Utils.checkString(idGeneratorName, "idGeneratorName", false);
        Utils.checkString(algorithmName, "algorithmName", false);

        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
        this.algorithmName = algorithmName;
        this.idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);

        Map aliases = new HashMap();
        aliases.put(CompanyDAO.SEARCH_COMPANY_NAME, "company.name");
        aliases.put(CompanyDAO.SEARCH_CONTACT_FIRST_NAME, "contact.first_name");
        aliases.put(CompanyDAO.SEARCH_CONTACT_LAST_NAME, "contact.last_name");
        aliases.put(CompanyDAO.SEARCH_CONTACT_PHONE, "contact.phone");
        aliases.put(CompanyDAO.SEARCH_CONTACT_EMAIL, "contact.email");
        aliases.put(CompanyDAO.SEARCH_STREET_ADDRESS1, "address.line1");
        aliases.put(CompanyDAO.SEARCH_STREET_ADDRESS2, "address.line2");
        aliases.put(CompanyDAO.SEARCH_CITY, "address.city");
        aliases.put(CompanyDAO.SEARCH_STATE, "state_name.name");
        aliases.put(CompanyDAO.SEARCH_ZIP_CODE, "address.zip_code");
        aliases.put(CompanyDAO.SEARCH_CREATED_DATE, "company.creation_date");
        aliases.put(CompanyDAO.SEARCH_CREATED_USER, "company.creation_user");
        aliases.put(CompanyDAO.SEARCH_MODIFICATION_DATE, "company.modification_date");
        aliases.put(CompanyDAO.SEARCH_MODIFICATION_USER, "company.modification_user");

        searchStringBuilder = new DatabaseSearchStringBuilder(BASE_FILTER_QUERY, aliases);
    }

    /**
     * <p>
     * Creates a datastore entry for the given company. An id is automatically
     * generated by the DAO and assigned to the company. The Company is also
     * considered to have been created by the specified user.
     * </p>
     *
     * @return The same company object with the id, creationUser,
     *         modfiicationUser, creationDate, modificationDate modified
     *         appropriately.
     * @param company The company to define within the data store.
     * @param user The user responsible for creating the Company entry within
     *        the datastore.
     * @throws IllegalArgumentException if the company or user is null, or user
     *         is an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     */
    public Company createCompany(Company company, String user) throws CompanyDAOException {
        checkCompany(company);
        Utils.checkString(user, "user", false);
        Connection conn = createConnection();
        try {
            createCompany(conn, company, user);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new CompanyDAOException("Error occurs while creating company.", ex, company);
        } catch (EncryptionException ex) {
            DBUtils.rollback(conn);
            throw new CompanyDAOException("Error occurs while encrypting passcode.", ex, company);
        } finally {
            DBUtils.close(conn);
        }

        return company;
    }

    /**
     * Checks if all required fields in company are set.
     *
     * @param company the company to check.
     * @throws CompanyDAOException if any value is missing.
     */
    private void checkCompany(Company company) throws CompanyDAOException {
        Utils.checkNull(company, "company");

        checkAddress(company);
        checkContact(company);
        if (company.getCompanyName() == null) {
            throw new CompanyDAOException("The company's name should be set.", null, company);
        }

        if (company.getPasscode() == null) {
            throw new CompanyDAOException("The company's passcode should be set.", null, company);
        }

    }

    /**
     * Checks if all required fields in contact are set.
     *
     * @param company the company to check.
     * @throws CompanyDAOException if any value is missing.
     */
    private static void checkContact(Company company) throws CompanyDAOException {
        Contact contact = company.getContact();
        if (contact == null) {
            throw new CompanyDAOException("The contact should be set.", null, company);
        }

        if (contact.getEmailAddress() == null) {
            throw new CompanyDAOException("The contact's email should be set.", null, company);
        }

        if (contact.getFirstName() == null) {
            throw new CompanyDAOException("The contact's first name should be set.", null, company);
        }

        if (contact.getLastName() == null) {
            throw new CompanyDAOException("The contact's last name should be set.", null, company);
        }

        if (contact.getPhoneNumber() == null) {
            throw new CompanyDAOException("The contact's phone number should be set.", null, company);
        }
    }

    /**
     * Checks if all required fields in address are set.
     *
     * @param company the address to check.
     * @throws CompanyDAOException if any value is missing.
     */
    private static void checkAddress(Company company) throws CompanyDAOException {
        Address address = company.getAddress();
        if (address == null) {
            throw new CompanyDAOException("The address should be set.", null, company);
        }

        if (address.getCity() == null) {
            throw new CompanyDAOException("The address' city should be set.", null, company);
        }

        if (address.getLine1() == null) {
            throw new CompanyDAOException("The address' line 1 should be set.", null, company);
        }

        if (address.getZipCode() == null) {
            throw new CompanyDAOException("The address' zip code be set.", null, company);
        }
    }

    /**
     * Creates the company in the database.
     *
     * @param conn the connection to be used.
     * @param company the company to be inserted.
     * @param user the creation user.
     * @return the Company.
     * @throws CompanyDAOException if any error occurs.
     * @throws SQLException if any error occurs.
     */
    private Company createCompany(Connection conn, Company company, String user) throws CompanyDAOException,
            SQLException {

        long companyId = generateId(company.getId());
        long addressId = generateId(company.getAddress().getId());
        long contactId = generateId(company.getContact().getId());

        String queryCompCon = INSERT_COMPANY_CONTACT;
        String queryCompAdd = INSERT_COMPANY_ADDRESS;

        insertCompany(conn, company, companyId, user);
        DBUtils.insertAddress(conn, company.getAddress(), addressId, user);
        DBUtils.insertContact(conn, company.getContact(), contactId, user);
        DBUtils.execute(conn, queryCompAdd, companyId, addressId, user);
        DBUtils.execute(conn, queryCompCon, companyId, contactId, user);

        company.setId(companyId);
        company.getAddress().setId(addressId);
        company.getContact().setId(contactId);

        return company;
    }

    /**
     * Inserts the copany into the table.
     *
     * @param conn the connection to be used.
     * @param company the company.
     * @param companyId the company id.
     * @param user the creation user.
     * @throws SQLException if any error occurs.
     */
    private void insertCompany(Connection conn, Company company, long companyId, String user) throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(INSERT_COMPANY);
            pstmt.setLong(1, companyId);
            pstmt.setString(2, company.getCompanyName());
            pstmt.setString(3, Utils.encrypt(algorithmName, company.getPasscode()));

            Date now = DBUtils.initUserAndDates(pstmt, user, 4);
            pstmt.executeUpdate();
            DBUtils.setCreationFields(company, user, now);
        } finally {
            DBUtils.close(pstmt);
        }
        company.setChanged(false);
    }

    /**
     * Creates the database connection with auto commit set to false.
     *
     * @return the database connection.
     * @throws CompanyDAOException if connection cannot be created.
     */
    private Connection createConnection() throws CompanyDAOException {
        try {
            Connection conn = connectionFactory.createConnection(connectionName);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            return conn;
        } catch (DBConnectionException ex) {
            throw new CompanyDAOException("Error occurs while creating exception.", ex, null);
        } catch (SQLException ex) {
            throw new CompanyDAOException("Error occurs while creating exception.", ex, null);
        }
    }

    /**
     * Generates the new id only if the previous id was not set (is == 0).
     *
     * @param previousId the previously generated or set id.
     * @return the id (new or existing).
     * @throws CompanyDAOException if error occur during operation.
     */
    private long generateId(long previousId) throws CompanyDAOException {
        if (previousId > 0) {
            return previousId;
        }

        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException ex) {
            throw new CompanyDAOException("Error occur while generating the id.", ex, null);
        }
    }

    /**
     * <p>
     * Retrieves a company from the datastore with the provided id. If no
     * company with that id exists, then a null is returned.
     * </p>
     *
     * @return The company with specified id, or null if it wasn't found.
     * @param id The id of the company to retrieve.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     */
    public Company retrieveCompany(long id) throws CompanyDAOException {
        Utils.checkPositive(id, "id");

        Filter filter = new EqualToFilter("company.company_id", new Long(id));
        Company[] companies = searchCompanies(filter);
        if (companies.length > 0) {
            return companies[0];
        }
        return null;
    }

    /**
     * Creates <code>Company</code> instance using the values from <code>ResultSet</code>.
     *
     * @param rs the ResultSet object.
     * @return the Company created from result set.
     * @throws SQLException if any database related error occurs.
     */
    private Company populateCompany(ResultSet rs) throws SQLException {
        Company company = new Company();
        // set the current algorithm name
        company.setAlgorithmName(algorithmName);
        company.setPasscode(Utils.decrypt(algorithmName, rs.getString("passcode")));
        company.setCompanyName(rs.getString("name"));
        company.setId(rs.getLong("company_id"));

        company.setCreationDate(rs.getTimestamp("CompanyCreationDate"));
        company.setModificationDate(rs.getTimestamp("CompanyModificationDate"));
        company.setCreationUser(rs.getString("CompanyCreationUser"));
        company.setModificationUser(rs.getString("CompanyModificationUser"));

        company.setAddress(DBUtils.populateAddress(rs));
        company.setContact(DBUtils.populateContact(rs));

        company.setChanged(false);
        return company;
    }

    /**
     * <p>
     * Updates the given company in the data store. The company is considered to
     * have been modified by the specified user.
     * </p>
     *
     * @param company The company to update in the data store.
     * @param user The user responsible for performing the update.
     * @throws IllegalArgumentException if the company or user is null, or user
     *         is an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     * @throws CompanyNotFoundException if the company to update was not found
     *         in the data store.
     */
    public void updateCompany(Company company, String user) throws CompanyDAOException {
        checkCompany(company);
        Utils.checkString(user, "user", false);

        Connection conn = createConnection();
        try {
            updateCompany(conn, company, user);
            conn.commit();
        } catch (EncryptionException ex) {
            DBUtils.rollback(conn);
            throw new CompanyDAOException("Error occur during encryption.", ex, company);
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new CompanyDAOException("Error occur during update.", ex, company);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * Updates the company.
     *
     * @param conn the connection to be used.
     * @param company the company to update.
     * @param user the update user.
     * @throws CompanyDAOException if error ocuurs.
     * @throws SQLException if database error occurs.
     */
    private void updateCompany(Connection conn, Company company, String user) throws CompanyDAOException,
        SQLException {

        checkCompany(company);
        try {
            int count = updateCompanyImpl(conn, company, user);
            if (count == 0) {
                throw new CompanyNotFoundException("Missing company.", null, company);
            }

            DBUtils.updateAddress(conn, company.getAddress(), user);
            DBUtils.updateContact(conn, company.getContact(), user);
            // update references
            DBUtils.executeUpdate(conn, UPDATE_COMPANY_ADDRESS, company.getAddress().getId(), company.getId(), user);
            DBUtils.executeUpdate(conn, UPDATE_COMPANY_CONTACT, company.getContact().getId(), company.getId(), user);

        } catch (EncryptionException ex) {
            throw new CompanyDAOException("Error occur during encryption.", ex, company);
        }

        company.setChanged(false);
    }

    /**
     * Updates the company table.
     *
     * @param conn the connection.
     * @param company the company.
     * @param username the username to be used.
     * @return the nuber of rows affected.
     * @throws SQLException if error ocuurs.
     */
    private int updateCompanyImpl(Connection conn, Company company, String username) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(UPDATE_COMPANY);

            pstmt.setString(1, company.getCompanyName());
            pstmt.setString(2, Utils.encrypt(algorithmName, company.getPasscode()));

            // set times
            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(3, time);
            pstmt.setString(4, username);
            pstmt.setLong(5, company.getId());

            int result = pstmt.executeUpdate();
            DBUtils.setModificationFields(company, username, time);
            return result;
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * <p>
     * Removes the provided company from the data store.
     * </p>
     *
     * @param company The company to delete.
     * @throws IllegalArgumentException if the company is null.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     * @throws CompanyNotFoundException if the company to delete was not found
     *         in the data store.
     */
    public void deleteCompany(Company company) throws CompanyDAOException {
        Utils.checkNull(company, "company");

        Connection conn = createConnection();
        try {
            deleteCompany(conn, company);
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new CompanyDAOException("Error occur while deleting the company.", ex, company);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * Deletes the comapny and all it references.
     *
     * @param conn the connection to use.
     * @param company the company to be deleted.
     * @throws CompanyDAOException if error occurs.
     * @throws SQLException if error occurs.
     */
    private void deleteCompany(Connection conn, Company company) throws CompanyDAOException, SQLException {
        DBUtils.executeDelete(conn, DELETE_COMPANY_CONTACT, company.getId());
        DBUtils.executeDelete(conn, DELETE_COMPANY_ADDRESS, company.getId());
        DBUtils.executeDelete(conn, DELETE_CONTACT, company.getContact().getId());
        DBUtils.executeDelete(conn, DELETE_ADDRESS, company.getAddress().getId());


        int count = DBUtils.executeDelete(conn, DELETE_COMPANY, company.getId());
        if (count == 0) {
            throw new CompanyNotFoundException("Company not exists.", null, company);
        }
    }

    /**
     * <p>
     * Enumerates all the companies that are present within the data store.
     * </p>
     *
     * @return A list of all the companies present in the data store.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     */
    public Company[] listCompanies() throws CompanyDAOException {
        return listCompanies(BASE_QUERY, false);
    }

    /**
     * Returns the array of companies that match the query.
     *
     * @param query the search query.
     * @param fillStatement indicates if there are any additional values for query.
     * @return the array of companies found.
     * @throws CompanyDAOException if any error occurs.
     */
    private Company[] listCompanies(String query, boolean fillStatement) throws CompanyDAOException {
        Connection conn = createConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
             pstmt = conn.prepareStatement(query);
            if (fillStatement) {
                DBUtils.fillStatement(pstmt, searchStringBuilder.getValues());
            }

            rs = pstmt.executeQuery();
            List result = new ArrayList();
            while (rs.next()) {
                result.add(populateCompany(rs));
            }

            return (Company[]) result.toArray(new Company[result.size()]);
        } catch (SQLException ex) {
            throw new CompanyDAOException("Error occur while retrieving the reject email.", ex, null);
        } catch (EncryptionException ex) {
            throw new CompanyDAOException("Error occur while decryption the passcode.", ex, null);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Returns a list of all the companies within the datastore that satisfy the
     * filters that are provided. The filters are defined using classes from the
     * Search Builder v1.2 component.
     * </p>
     *
     * @return A list of companies that fulfill the given criterion. If no
     *         company fulfills it, then an empty array is returned.
     * @param filter The filter that is used as criterion to facilitate the
     *        search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     */
    public Company[] searchCompanies(Filter filter) throws CompanyDAOException {
        Utils.checkNull(filter, "filter");

        String query = null;
        synchronized (searchStringBuilder) {
            query = searchStringBuilder.buildSearchString(filter);
        }

        return listCompanies(query, true);
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is
     * automatically generated by the DAO and assigned to the company. The
     * Company is also considered to have been created by the specified user.
     * </p>
     *
     * @return The same company objects with the id, creationUser,
     *         modfiicationUser, creationDate, modificationDate modified
     *         appropriately. The index of the company in the returned array
     *         corresponds to the index of the company in the method argument.
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param atomicBatchMode Whether the operation will be performed in atomic
     *        batch mode or not.
     * @throws IllegalArgumentException if companies is null or contains null
     *         elements, or user is null or an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple
     *         entities while processing them in non-atomic batch mode.
     */
    public Company[] createCompanies(Company[] companies, String user, boolean atomicBatchMode)
            throws CompanyDAOException {

        Utils.checkString(user, "user", false);
        return batchOperation(companies, user, atomicBatchMode, CREATE_OP);
    }

    /**
     * <p>
     * Retrieves the companies with the specified ids from the datastore.
     * </p>
     *
     * @return A list of companies with the given ids. The index of the company
     *         corresponds to the index of the id in the method argument.
     * @param ids The ids of the companies to retrieve.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     */
    public Company[] retrieveCompanies(long[] ids) throws CompanyDAOException {
        Utils.checkNull(ids, "ids");

        List idsList = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            idsList.add(new Long(ids[i]));
        }

        return searchCompanies(new InFilter("company.company_id", idsList));
    }

    /**
     * <p>
     * Updates the given companies in the data store. The companies are
     * considered to have been modified by the specified user.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param atomicBatchMode Whether the operation will be performed in atomic
     *        batch mode or not.
     * @throws IllegalArgumentException if the companies array is null or
     *         contains null elements, or user is null or is an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple
     *         entities while processing them in non-atomic batch mode.
     */
    public void updateCompanies(Company[] companies, String user, boolean atomicBatchMode)
        throws CompanyDAOException {
        Utils.checkString(user, "user", false);
        batchOperation(companies, user, atomicBatchMode, UPDATE_OP);
    }

    /**
     * <p>
     * Deletes the specified companies from the data store.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param atomicBatchMode Whether the operation will be performed in atomic
     *        batch mode or not.
     * @throws IllegalArgumentException if companies is null or has null
     *         elements.
     * @throws CompanyDAOException if a problem occurs while accessing the
     *         datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple
     *         entities while processing them in non-atomic batch mode.
     */
    public void deleteCompanies(Company[] companies, boolean atomicBatchMode) throws CompanyDAOException {
        batchOperation(companies, null, atomicBatchMode, DELETE_OP);
    }

    /**
     * It executes operation in batch mode.
     *
     * @param companies the Company array to process.
     * @param user the user.
     * @param atomicBatchMode Whether the operation will be performed in atomic
     *        batch mode or not.
     * @param operation the operation to perform.
     * @return the operation result.
     * @throws CompanyDAOException if any error occurs.
     */
    private Company[] batchOperation(Company[] companies, String user, boolean atomicBatchMode, int operation)
            throws CompanyDAOException {

        Utils.checkNull(companies, "companies");

        for (int i = 0; i < companies.length; i++) {
            if (companies[i] == null) {
                throw new IllegalArgumentException("Companies array contains null at index: " + i);
            }
        }

        Connection conn = createConnection();
        Throwable[] exceptions = new Throwable[companies.length];
        boolean wasError = false;
        try {
            for (int i = 0; i < companies.length; i++) {
                try {
                    if (operation == CREATE_OP) {
                        companies[i] = createCompany(conn, companies[i], user);
                    } else if (operation == UPDATE_OP) {
                        updateCompany(conn, companies[i], user);
                    } else if (operation == DELETE_OP) {
                        deleteCompany(conn, companies[i]);
                    }
                } catch (Exception ex) {
                    exceptions[i] = ex;
                    wasError = true;
                    // rollback the last operation or whole block if in atomic mode
                    DBUtils.rollback(conn);
                    if (atomicBatchMode) {
                        throw new BatchCompanyDAOException("Error occurs during create.", exceptions, companies);
                    }
                } finally {
                    if (!atomicBatchMode) {
                        conn.commit();
                    }
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(conn);
            throw new CompanyDAOException("Errr occurs while creating companies.", ex, null);
        } finally {
            DBUtils.close(conn);
        }

        if (wasError) {
            throw new BatchCompanyDAOException("Error occurs during batch operation.", exceptions, companies);
        }

        return companies;
    }
}
