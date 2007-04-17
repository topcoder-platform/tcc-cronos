/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactDAO;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.Helper;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;

import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * This class is the Informix database implementation of the <code>ContactDAO</code>.
 * It provides general retrieve/update/remove/add functionality to access the database.
 * And it provides method to search Contacts with filter.
 * </p>
 *
 * <p>
 * This class will be instantiated by <code>ContactBean</code> to perform CURD operations.
 * So the transaction atomic is managed by EJB container, the connection isolation level
 * is specified by container administrator.
 * </p>
 *
 * <p>
 *  <strong>Sample configuration:</strong>
 *  <pre>
 *    &lt;Config name="com.topcoder.timetracker.contact.persistence.InformixContactDAO"&gt;
 *      &lt;Property name="connection_factory_namespace"&gt;
 *         &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;!-- use JNDI connection --&gt;
 *      &lt;Property name="connection_name"&gt;
 *         &lt;Value&gt;InformixJNDIConnection&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="idgenerator_name"&gt;
 *         &lt;Value&gt;ContactIDGenerator&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="search_bundle_name"&gt;
 *         &lt;Value&gt;ContactSearch&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="search_bundle_namespace"&gt;
 *         &lt;Value&gt;com.topcoder.timetracker.contact.persistence.SearchBundleManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="audit_manager_namespace"&gt;
 *         &lt;Value&gt;ObjectFactoryNS&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="AuditManager"&gt;
 *         &lt;Value&gt;AuditManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *   &lt;/Config&gt;
 *  </pre>
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This class is thread safe by being immutable. However the thread safety is also depended on database transactions.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class InformixContactDAO implements ContactDAO {

    /**
     * <p>The max length of <em>creation_user</em> and <em>modification_user</em>.</p>
     */
    private static final int USERNAME_MAX_LENGTH = 64;

    /**
     * <p>
     * The max length of columns <em>first_name</em> and <em>last_name</em>.
     * </p>
     */
    private static final int NAME_MAX_LENGTH = 64;

    /**
     * <p>
     * The max length of column <em>phone</em>.
     * </p>
     */
    private static final int PHONE_MAX_LENGTH = 30;

    /**
     * <p>
     * The max length of columns <em>email</em>.
     * </p>
     */
    private static final int EMAIL_MAX_LENGTH = 64;

    /**
     * <p>The count of columns of <em>contact</em> table.</p>
     */
    private static final int CONTACT_COLUMNS_COUNT = 9;

    /**
     * <p>The count of columns of <em>contact_relation</em> table.</p>
     */
    private static final int CONTACT_RELATION_COLUMNS_COUNT = 7;

    /**
     * <p>The count of primary key columns of <em>contact_relation</em> table.</p>
     */
    private static final int CONTACT_RELATION_PK_COLUMNS_COUNT = 3;

    /**
     * <p>
     * SQL clause used to select all records from <em>contact</em> table left join <em>contact_relation</em>.
     * </p>
     */
    private static final String SELECT_CONTACT =
        "select contact.*,contact_relation.contact_type_id, contact_relation.entity_id,"
        + "contact_relation.creation_date as relation_creation_date,"
        + "contact_relation.modification_date as relation_modification_date,"
        + "contact_relation.creation_user as relation_creation_user,"
        + "contact_relation.modification_user as relation_modification_user from contact"
        + " left join contact_relation on contact.contact_id = contact_relation.contact_id";

    /**
     * <p>
     * SQL clause used to delete all records from <em>contact</em> table.
     * </p>
     */
    private static final String DELETE_CONTACT = "delete from contact where contact_id = ?";

    /**
     * <p>
     * SQL clause used to insert a new record into <em>contact</em> table.
     * </p>
     */
    private static final String INSERT_CONTACT = "insert into contact(first_name,last_name,phone,email,"
        + "creation_user,modification_user,creation_date,modification_date,contact_id) values "
        + "( ?,?,?,?,?,?,?,?,?)";

    /**
     * <p>
     * SQL clause used to update an existing record within <em>contact</em> table.
     * </p>
     */
    private static final String UPDATE_CONTACT = "update contact set first_name=?,last_name=?,phone=?,email=?,"
        + "creation_user=?,modification_user=?,creation_date=?,modification_date=? "
        + "where contact_id=?";

    /**
     * <p>
     * SQL clause used to insert a new record into <em>contact_relation</em> table.
     * </p>
     */
    private static final String ASSOCIATE = "insert into contact_relation (creation_user,modification_user,"
        + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values (?,?,?,?,?,?,?) ";

    /**
     * <p>
     * SQL clause used to delete an existing record from <em>contact_relation</em> table.
     * </p>
     */
    private static final String DEASSOCIATE =
        "delete from contact_relation where contact_id=? and contact_type_id=? and entity_id=?";

    /**
     * <p>SQL clause used to delete records from <em>contact_relation</em> table based on the contact id.</p>
     */
    private static final String DELETE_RELATIONS =
        "delete from contact_relation where contact_id=?";

    /**
     * <p>
     * Represents the connection factory used to generate the <code>Connection</code>.
     * This variable is set in constructor, and is immutable and never be null.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the connection name used by the <code>DBConnectionFactory</code> to generate the
     * <code>Connection</code>.
     * This variable is set in constructor, is immutable and possible null, possible empty(trim'd).
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the <code>SearchBundle</code> will be used to perform search with filter.
     * This variable is set in constructor, is immutable and never be null.
     * It is referenced by <code>searchContacts()</code> method.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * Represents the <code>AuditManager</code> used by this DAO.
     * This variable is set in constructor, is immutable and never be null.
     * It is referenced by all methods which can be audited.</p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * Represents the IDGenerator used to generate the id of the contact.
     * This variable is set in constructor,is immutable and never be null.
     * It is referenced by <code>addContact()</code> and <code>addContacts()</code> methods.
     * </p>
     */
    private final IDGenerator contactIDGenerator;

    /**
     * <p>
     * Constructs <code>InformixContactDAO</code> with default namespace:
     *  <ul>
     *   <li>com.topcoder.timetracker.contact.persistence.InformixContactDAO</li>
     *  </ul>
     *  See class doc for sample configuration.
     * </p>
     *
     * @throws ConfigurationException if any required properties are missing or invalid. Or any error occurs while
     *         creating <code>DBConnectionFactory</code>, <code>IDGenerator</code>, <code>SearchBundle</code>,
     *         <code>AuditManager</code> based on the configuration within default namespace.
     */
    public InformixContactDAO() throws ConfigurationException {
        this(InformixContactDAO.class.getName());
    }

    /**
     * <p>
     * Constructs <code>InformixContactDAO</code> with the given namespace.
     * See class doc for sample configuration.
     * </p>
     *
     * @param namespace non null, non empty(trim'd) namespace
     *
     * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
     * @throws ConfigurationException if any required properties are missing or invalid. Or any error occurs while
     *         creating <code>DBConnectionFactory</code>, <code>IDGenerator</code>, <code>SearchBundle</code>,
     *         <code>AuditManager</code> based on the configuration within given namespace.
     */
    public InformixContactDAO(String namespace) throws ConfigurationException {
        this(namespace, null);
    }

    /**
     * <p>
     * Constructs <code>InformixContactDAO</code> with the given namespace.
     * See class doc for sample configuration.
     * </p>
     *
     * <p>
     * If given <code>AuditManager</code> is null, then an instance of it will created through
     * <code>ObjectFactory</code> with configured properties in given namespace.
     * </p>
     *
     * @param namespace non null, non empty(trim'd) namespace
     * @param auditManager possible null audit manager
     *
     * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
     * @throws ConfigurationException if any required properties are missing or invalid. Or any error occurs while
     *         creating <code>DBConnectionFactory</code>, <code>IDGenerator</code>, <code>SearchBundle</code>,
     *         <code>AuditManager</code> based on the configuration within given namespace.
     */
    public InformixContactDAO(String namespace, AuditManager auditManager)
        throws ConfigurationException {
        this.connectionName = Helper.getPropertyValue(namespace, "connection_name", false);
        this.connectionFactory = DAOHelper.getConnectionFactory(namespace);
        this.contactIDGenerator = DAOHelper.getIDGenerator(namespace);
        this.searchBundle = DAOHelper.getSearchBundle(namespace);

        if (auditManager != null) {
            this.auditManager = auditManager;
        } else {
            this.auditManager = DAOHelper.getAuditManager(namespace);
        }
    }

    /**
     * <p>
     * Add the given <code>Contact</code> into the database.
     * </p>
     *
     * <p>
     * The given <code>Contact</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of contact will not be validated. The previous value will be ignored and replaced by id got from
     *      <code>IDGenerator</code>.</li>
     *   <li>The first name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The last name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The phone number of contact must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The email address of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation/modification date will not be validated. The previous values will be ignored and replaced
     *       by current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  After adding successfully, the changed status of given <code>Contact</code> will be set as false.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>ContactType</code>, so if the type of given contact is null, no audit will be performed even
     *  the doAudit flag is true.
     * </p>
     *
     * @param contact non null contact
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null
     * @throws InvalidPropertyException if the properties of given contact is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     */
    public void addContact(Contact contact, boolean doAudit)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException {
        this.insertContacts(new Contact[] {contact}, doAudit, false);
    }
    /**
     * <p>
     * Add the given array of <code>Contact</code> into the database.
     * </p>
     *
     * <p>
     * Any given <code>Contact</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of contact will not be validated. The previous value will be ignored and replaced by id got from
     *      <code>IDGenerator</code>.</li>
     *   <li>The first name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The last name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The phone number of contact must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The email address of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation/modification date will not be validated. The previous values will be ignored and replaced
     *       by current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  After adding successfully, the changed status of given <code>Contact</code> will be set as false.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>ContactType</code>, so if the type of given contact is null, no audit will be performed even
     *  the doAudit flag is true.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(objects with same reference), there will be only one record
     *  inserted into database for all the duplicate members. And also the audit will be performed only once if it is
     *  enabled.
     * </p>
     *
     * @param contacts The non-null array contains non-null <code>Contact</code>. Possibly empty.
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the given array is null or contains null member
     * @throws InvalidPropertyException if the properties of any given contact is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AuditException if exception occurs when audit
     */
    public void addContacts(Contact[] contacts, boolean doAudit)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException {
        this.insertContacts(contacts, doAudit, true);
    }

    /**
     * <p>
     * Set the changed status of all contacts to false.
     * </p>
     *
     * @param contacts Array of <code>Contact</code>
     */
    private void setChangedFalse(Contact[] contacts) {
        for (int i = 0; i < contacts.length; i++) {
            contacts[i].setChanged(false);
        }
    }

    /**
     * <p>
     * Add the given array of <code>Contact</code> into the database.
     * </p>
     *
     * <p>
     * Any given <code>Contact</code> should have all fields set with valid values except
     * the creation/modification date and id. The creation/modification previously set will
     * be ignored and will be set as current date. Also the previous value of id will be ignored
     * and will be generated by <code>IDGenerator</code>.
     * </p>
     *
     * <p>
     *  <strong>Called by:</strong>
     *  <code>addContact()</code> and <code>addContacts()</code>.
     * </p>
     *
     * @param contactsArray The non-null array contains non-null <code>Contact</code>. Possibly empty.
     * @param doAudit whether this action should be audited
     * @param batch Indicate whether to execute batch insertion or a single insertion.
     *
     * @throws IllegalArgumentException if the given array is null or contains null member
     * @throws InvalidPropertyException if the properties of any given contact is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AuditException if exception occurs when audit
     */
    private void insertContacts(Contact[] contactsArray, boolean doAudit, boolean batch)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException {
        List contactList = Helper.validateObjectsArrayWithIAE(contactsArray, "Contacts to be added");
        int size = contactList.size();
        if (size == 0) {
            return;
        }
        String operation = "adding Contact";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            List parameters = new ArrayList();

            for (int i = 0; i < size; i++) {
                Contact contact = (Contact) contactList.get(i);
                //Validate given contact and convert it
                Object[] params = new Object[CONTACT_COLUMNS_COUNT];
                this.convertContact(contact, params, false);
                parameters.add(params);
            }

            for (int i = 0; i < size; i++) {
                Contact contact = (Contact) contactList.get(i);
                //Get id from IDGenerator
                long id = DAOHelper.getNextId(this.contactIDGenerator, operation);
                contact.setId(id);

                Object[] params = (Object[]) parameters.get(i);
                params[params.length - 1] = new Long(id);

                //Do audit
                if (doAudit) {
                    if (contact.getContactType() == null) {
                        //no given type, no audit
                        continue;
                    }
                    AuditHeader auditHeader = this.getAuditRecord(null, contact);
                    //When a contact is newly added, no association, so no related id to set, leave it as -1
                    DAOHelper.setApplicationAreaAndRelatedId(auditHeader, contact.getContactType(), -1);
                    DAOHelper.audit(this.auditManager, auditHeader, operation);
                }
            }

            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);
            ps = DAOHelper.prepareStatement(con, INSERT_CONTACT, operation);

            for (int i = 0; i < size; i++) {
                Object[] params = (Object[]) parameters.get(i);
                DAOHelper.setUpPreparedStatement(ps, params, batch, operation);
            }

            if (batch) {
                DAOHelper.executeBatch(ps, operation);
            } else {
                DAOHelper.executeUpdate(ps, operation);
            }

            this.setChangedFalse(contactsArray);
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }
    /**
     *<p>
     * Retrieve <code>Contact</code> with given id. According to design, any contact may be associated with
     * zero/one entity at one time, so if the contact is found to be associated with multiple entities, an
     * <code>AssociationException</code> will be raised. If the contact is not associated with any entity,
     * its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Contact</code>(if not null) will have the changed status set as false.
     * </p>
     *
     * @param id the positive id of the contact
     *
     * @return the contact with given id, null if not found
     *
     * @throws IllegalArgumentException if the id is non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AssociationException if the contact is found to be associated with multiple entities
     */
    public Contact retrieveContact(long id) throws PersistenceException, AssociationException {
        Helper.validatePositiveWithIAE(id, "Id of contact to be retrieved");
        Contact[] contacts = this.listToArray(this.extractKeySet(this.selectContacts(
            null, new long[] {id}, "retrieving Contact by id")));
        return contacts.length == 0 ? null : contacts[0];
    }
    /**
     * <p>
     * Retrieve an array of <code>Contact</code> with given ids. According to design, any contact may be associated
     * with zero/one entity at one time, so if the contact is found to be associated with multiple entities, an
     * <code>AssociationException</code> will be raised. If the contact is not associated with any entity,
     * its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  <ul>
     *   <li>
     *   If nothing is found corresponding to given ids, an empty array is returned.
     *   </li>
     *   <li>
     *   If some are found others are not found, the returned array will have length
     *   smaller than the length of given ids and will not contain null member.
     *   <li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Contact</code> will have the changed status set as false.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(same id), there will be only one record
     *  pulled out from database and returned for all the duplicate members.
     * </p>
     *
     * @param ids the non-null, possible empty ids of the contacts
     *
     * @return the non-null array of <code>Contact</code> found corresponding to given ids.
     *
     * @throws IllegalArgumentException if ids array is null or any id is non-positive
     * @throws PersistenceException if error occurs while accessing database
     * @throws AssociationException if any contact is found to be associated with multiple entities
     */
    public Contact[] retrieveContacts(long[] ids) throws PersistenceException, AssociationException {
        if (Helper.validateIdsArrayWithIAE(ids, "Ids of Contacts to be retrieved")) {
            return new Contact[0];
        }
        return this.listToArray(this.extractKeySet(this.selectContacts(null, ids, "retrieving Contacts by ids")));
    }

    /**
     * <p>
     * The map constructed from <code>selectContacts()</code> method will have the contacts as the keys.
     * Extract the keys out as a list.
     * </p>
     *
     * @param map The map constructed from <code>selectContacts()</code> method
     *
     * @return List of <code>Contact</code> extracted from map's keys.
     */
    private List extractKeySet(Map map) {
        List keys = new ArrayList(map.keySet());
        return keys;
    }

    /**
     * <p>
     * Convert the <code>List</code> of <code>Contact</code> to array.
     * </p>
     *
     * @param list The <code>List</code> contains <code>Contact</code>
     *
     * @return An array containing all <code>Contact</code>es in the given list
     */
    private Contact[] listToArray(List list) {
        return (Contact[]) (list.toArray(new Contact[list.size()]));
    }

    /**
     * <p>
     * Populate <code>Contact</code> with data from database.
     * </p>
     *
     * @param contacts The map contains mapping from <code>Contact</code> to its associtaion
     * @param rs The <code>ResultSet</code>. Will be null when called by <code>searchContacts()</code>
     * @param crs The <code>CustomResultSet</code>. Will be null when called by <code>selectContacts()</code>.
     * @param operation Represents the meaningful operation performed
     *
     * @throws AssertionError If a <code>Contact</code> is associated more than once
     * @throws PersistenceException If error occurs while accessing database
     * @throws AssociationException if the contact is found to be associated with multiple entities
     */
    private void populateContact(Map contacts, ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException, AssociationException {
        try {
            Contact contact = new Contact();
            contact.setId((rs != null) ? rs.getLong("contact_id") : crs.getLong("contact_id"));
            if (contacts.containsKey(contact)) {
                throw new AssociationException(
                        "Given an Contact, it can be associated at most once. But contact with id '"
                        + contact.getId() + "' is associated with mutiple entities currently.");
            }
            contact.setFirstName((rs != null) ? rs.getString("first_name") : crs.getString("first_name"));
            contact.setLastName((rs != null) ? rs.getString("last_name") : crs.getString("last_name"));
            contact.setPhoneNumber((rs != null) ? rs.getString("phone") : crs.getString("phone"));
            contact.setEmailAddress((rs != null) ? rs.getString("email") : crs.getString("email"));

            contact.setCreationDate((rs != null) ? rs.getTimestamp("creation_date")
                    : crs.getTimestamp("creation_date"));
            contact.setCreationUser((rs != null) ? rs.getString("creation_user")
                    : crs.getString("creation_user"));
            contact.setModificationDate((rs != null) ? rs.getTimestamp("modification_date")
                        : crs.getTimestamp("modification_date"));
            contact.setModificationUser((rs != null) ? rs.getString("modification_user")
                        : crs.getString("modification_user"));

            ContactType type = this.populateContactType(rs, crs, operation);
            if (type != null) {
                contact.setContactType(type);
                Contact existingContactAssociation = new Contact();
                existingContactAssociation.setContactType(type);
                this.populateContactRelation(existingContactAssociation, rs, crs, operation);
                long entityId = (rs != null) ? rs.getLong("entity_id") : crs.getLong("entity_id");
                existingContactAssociation.setId(entityId); //Here use the id field to store the entity id
                contacts.put(contact, existingContactAssociation);
            } else {
                //No association, put null
                contacts.put(contact, null);
            }
            //Set changed to false
            contact.setChanged(false);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }
    /**
     * <p>
     * Populate following columns' values from table <em>contact_relation</em>:
     *  <ul>
     *   <li><em>creation_date</em></li>
     *   <li><em>creation_user</em></li>
     *   <li><em>modification_date</em></li>
     *   <li><em>modification_user</em></li>
     *  </ul>
     *  The values of these volumns will be populated into given contact.
     * </p>
     *
     * @param contact <code>Contact</code> to populate
     * @param rs The <code>ResultSet</code>. Will be null when invoked by <code>searchContacts()</code>
     * @param crs The <code>CustomResultSet</code>. Will be null when invoked by <code>selectContacts()</code>
     *        and <code>handleAssociation()</code>
     * @param operation Represents the meaningful operation performed
     *
     * @throws PersistenceException If error occurs while accessing database
     */
    private void populateContactRelation(Contact contact, ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException {
        try {
            contact.setCreationDate((rs != null) ? rs.getTimestamp("relation_creation_date")
                                                 : crs.getTimestamp("relation_creation_date"));
            contact.setCreationUser((rs != null) ? rs.getString("relation_creation_user")
                                                 : crs.getString("relation_creation_user"));
            contact.setModificationDate((rs != null) ? rs.getTimestamp("relation_modification_date")
                                                     : crs.getTimestamp("relation_modification_date"));
            contact.setModificationUser((rs != null) ? rs.getString("relation_modification_user")
                                                     : crs.getString("relation_modification_user"));
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }
    /**
     * <p>
     * Check the contact_type_id column and convert it to <code>ContactType</code>.
     * </p>
     *
     * @param rs The <code>ResultSet</code>. Will be null when called by <code>searchContacts()</code>
     * @param crs The <code>CustomResultSet</code>. Will be null when called by <code>selectContacts()</code>
     * @param operation Represents the operation performing
     *
     * @return <code>ContactType</code> corresponding to type id
     *
     * @throws PersistenceException If error occurs while accessing datebase or type id is not recongnized.
     */
    private ContactType populateContactType(ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException {
        try {
            Object contactTypeId = (rs != null) ? rs.getObject("contact_type_id") : crs.getObject("contact_type_id");
            if (contactTypeId == null) {
                return null;
            }
            int id = Integer.parseInt(contactTypeId.toString());
            Enum type = Enum.getEnumByOrdinal(id - 1, ContactType.class);
            if (type instanceof ContactType) {
                return (ContactType) type;
            } else {
                throw new PersistenceException("ContactType Id '" + contactTypeId + "' is not recongnized.");
            }
        } catch (NumberFormatException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }
    /**
     * <p>
     * Retrieve an array of <code>Contact</code> with given ids.
     * </p>
     *
     * <p>
     *  According to design, given a contact may be associated to zero/one entity, so it is appropriate to
     *  construct a map to hold the mapping between contact and its association.
     * </p>
     *
     * <p>
     *  <strong>Called by:</strong>
     *  <code>retrieveContact()</code>, <code>retrieveContacts()</code>, <code>getAllContacts()</code>,
     *  <code>deleteContacts()</code>, <code>modifyContacts()</code> and <code>handleAssociation</code>.
     * </p>
     *
     * @param conn Will be null when called by <code>retrieveContact()</code>, <code>retrieveContacts()</code> and
     *        <code>getAllContacts()</code>; Will not be null when called by <code>deleteContacts()</code>,
     *        <code>modifyContacts()</code> and <code>handleAssociation</code>.
     * @param ids the ids of the Contacts. Possibly empty. Possibly null when called by <code>getAllContacts()</code>
     * @param operation Represents the meaningful operation performed
     *
     * @return the non-null map contains mappings from <code>Contact</code> to its association.
     *
     * @throws PersistenceException if error occurs while accessing database
     * @throws AssociationException if any contact is found to be associated with multiple entities
     */
    private Map selectContacts(Connection conn, long[] ids, String operation)
        throws PersistenceException, AssociationException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String inClause = null;

        if (ids != null) {
            inClause = DAOHelper.convertIds(new StringBuffer(" where contact.contact_id in "), ids,
                        "ID of Contact");
        }

        try {
            con = (conn != null) ? conn
                : DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);

            String query = (inClause == null) ? SELECT_CONTACT : (SELECT_CONTACT + inClause);
            ps = DAOHelper.prepareStatement(con, query, operation);
            DAOHelper.setUpPreparedStatement(ps, null, false, operation);
            rs = DAOHelper.executeQuery(ps, operation);

            Map contacts = new LinkedHashMap();
            while (rs.next()) {
                this.populateContact(contacts, rs, null, operation);
            }
            return contacts;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } finally {
            DAOHelper.realeaseJDBCResource(rs, ps, (conn != null) ? null : con);
        }
    }
    /**
     * <p>
     * Remove contact from persistence by id. The association to entity will also be removed.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>ContactType</code>, so if the existing contact to be removed is not associated with any entity, no audit
     *  will be performed even the doAudit flag is true.
     * </p>
     *
     * @param id the positive id of the contact
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the id non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the existing contact is found to be associated with multiple entities
     */
    public void removeContact(long id, boolean doAudit) throws PersistenceException, AuditException
    , AssociationException {
        this.deleteContacts(new long[] {id}, doAudit, false);
    }
    /**
     * <p>
     * Remove the contacts from persistence corresponding to given ids. The association to entity will also be removed.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>ContactType</code>, so if the existing contact to be removed is not associated with any entity, no audit
     *  will be performed even the doAudit flag is true.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(same id), the audit will be performed only once if enabled.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the contacts
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if any id non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing contact is found to be associated with multiple entities
     */
    public void removeContacts(long[] ids, boolean doAudit) throws PersistenceException, AuditException
    , AssociationException {
        this.deleteContacts(ids, doAudit, true);
    }
    /**
     * <p>
     * Remove the contacts from persistence corresponding to given ids.
     * </p>
     *
     * <p>
     *  <strong>Called by:</strong>
     *  <code>removeContact()</code> and <code>removeContacts()</code>.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the contacts
     * @param doAudit whether this action should be audited
     * @param batch Indicate whether to execute batch deletion or a single deletion.
     *
     * @throws IllegalArgumentException if ids is null or any id non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing contact is found to be associated with multiple entities
     */
    private void deleteContacts(long[] ids, boolean doAudit, boolean batch)
        throws PersistenceException, AuditException, AssociationException {
        if (Helper.validateIdsArrayWithIAE(ids, "Ids of Contact to be removed")) {
            return;
        }

        String operation = "removing Contact";
        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);
            Map contactToAssociation = this.selectContacts(con, ids, operation);
            if (contactToAssociation.size() == 0) {
                return;
            }
            if (doAudit) {

                List existingContacts = this.extractKeySet(contactToAssociation);

                for (int i = 0; i < ids.length; i++) {
                    Contact existingContact = new Contact();
                    existingContact.setId(ids[i]);
                    int index = existingContacts.indexOf(existingContact);
                    if (index > -1) {
                        existingContact = (Contact) existingContacts.remove(index);
                        if (existingContact.getContactType() == null) {
                            //no existing type, no audit
                            continue;
                        }
                        //Note: for current design, there is no way to know the user name who is performing deletion
                        //This may be fixed in future
                        Contact contactRelation = (Contact) contactToAssociation.get(existingContact);
                        //Audit the deletion from table "contact"
                        AuditHeader auditHeader = this.getAuditRecord(existingContact, null);
                        DAOHelper.setApplicationAreaAndRelatedId(auditHeader, existingContact.getContactType(),
                            contactRelation.getId());
                        DAOHelper.audit(this.auditManager, auditHeader, operation);

                        //Audit the deletion from table "contact_relation"
                        this.auditContactRelation(ids[i], contactRelation.getId(), contactRelation, AuditType.DELETE,
                                contactRelation.getModificationUser(), operation);
                    }
                }
            }

            //==================================Delete from contact_relation first.
            //If the foreign key is on delete cascade, this block could be commented
            ps1 = DAOHelper.prepareStatement(con, DELETE_RELATIONS, operation);

            for (int i = 0; i < ids.length; i++) {
                Object[] params = new Object[]{new Long(ids[i])};
                DAOHelper.setUpPreparedStatement(ps1, params, batch, operation);
            }

            if (batch) {
                DAOHelper.executeBatch(ps1, operation);
            } else {
                DAOHelper.executeUpdate(ps1, operation);
            }
            //====================================End from contact_relation

            ps2 = DAOHelper.prepareStatement(con, DELETE_CONTACT, operation);
            for (int i = 0; i < ids.length; i++) {
                Object[] params = new Object[] {new Long(ids[i])};
                DAOHelper.setUpPreparedStatement(ps2, params, batch, operation);
            }

            if (batch) {
                DAOHelper.executeBatch(ps2, operation);
            } else {
                DAOHelper.executeUpdate(ps2, operation);
            }
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps1, null);
            DAOHelper.realeaseJDBCResource(null, ps2, con);
        }
    }
    /**
     * <p>
     * Update the <code>Contact</code>. This will only update the contact and will not update the association.
     * If any contact is not found to be existing in database, <code>EntityNotFoundException</code> will be raised.
     * Also according to design, any contact may be associated with zero/one entity at one time, so if the contact
     * to be updated is found to be associated with multiple entities, an <code>AssociationException</code> will
     * be raised.
     * </p>
     *
     * <p>
     * Any given <code>Contact</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of contact must be positive.</li>
     *   <li>The first name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The last name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The phone number of contact must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The email address of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation date must not be null, and must not exceed current date.</li>
     *   <li>The modification date will not be validated. The previous value will be ignored and replaced
     *       by current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  <ul>
     *   <li>
     *   If the changed status of the given <code>Contact</code> is found to be false,
     *   this method will simply return.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of given <code>Contact</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>ContactType</code>, so if the existing contact to be updated is not associated with any type,
     *  the type of the passed in contact will be used, if the type of the passed in contact is also null,
     *  then no audit will be performed even the doAudit flag is true.
     * </p>
     *
     * @param contact non null contact to be updated
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null
     * @throws InvalidPropertyException if the properties of given contact is invalid
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the contact can't be found
     * @throws AssociationException if the existing contact is found to be associated with multiple entities
     */
    public void updateContact(Contact contact, boolean doAudit)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException
        , AssociationException {
        this.modifyContacts(new Contact[] {contact}, doAudit, false);
    }
    /**
     * <p>
     * Update the given array of <code>Contact</code>. This will only update the contact and will not update the
     * association. If any contact is not found to be existing in database, <code>EntityNotFoundException</code>
     * will be raised. Also according to design, any contact may be associated with zero/one entity at one time,
     * so if any contact to be updated is found to be associated with multiple entities, a runtime
     * <code>AssertionError</code> will be raised.
     * </p>
     *
     * <p>
     * Any given <code>Contact</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of contact must be positive.</li>
     *   <li>The first name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The last name of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The phone number of contact must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The email address of contact must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation date must not be null, and must not exceed current date.</li>
     *   <li>The modification date will not be validated. The previous value will be ignored and replaced
     *       by current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  <ul>
     *   <li>
     *   If the changed status of any given <code>Contact</code> is found to be false,
     *   it will be ignored and this method will continue to next <code>Contact</code>.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of any given <code>Contact</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>ContactType</code>, so if the existing contact to be updated is not associated with any type,
     *  the type of the passed in contact will be used, if the type of the passed in contact is also null,
     *  then no audit will be performed even the doAudit flag is true.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(objects with same reference), it will only be updated once.
     *  And also the audit will be performed only once if it is enabled.
     * </p>
     *
     * @param contacts non null, possible empty array containing non null contacts
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the given array is null or it contains null member
     * @throws InvalidPropertyException if the properties of given contact is invalid
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the contact can't be found
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing contact is found to be associated with multiple entities
     */
    public void updateContacts(Contact[] contacts, boolean doAudit)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException
        , AssociationException {
        this.modifyContacts(contacts, doAudit, true);
    }
    /**
     * <p>
     * Update the given array of <code>Contact</code>.
     * </p>
     *
     * <p>
     * Any given <code>Contact</code> should have all fields set with valid values except
     * the modification date. The modification date previously set will be ignored and will be set as current date.
     * </p>
     *
     * @param contactsArray non null, possible empty array containing non null contacts
     * @param doAudit whether this action should be audited
     * @param batch Indicate whether to execute batch modification or a single modification.
     *
     * @throws IllegalArgumentException if the given array is null or it contains null member
     * @throws InvalidPropertyException if the properties of given contact is invalid
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the contact can't be found
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing contact is found to be associated with multiple entities
     */
    private void modifyContacts(Contact[] contactsArray, boolean doAudit, boolean batch)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException,
        AssociationException {
        List contactList = Helper.validateObjectsArrayWithIAE(contactsArray, "Contacts to be added");
        if (contactList.size() == 0) {
            return;
        }
        String operation = "updating contact";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            List parameters = new ArrayList();

            for (int i = 0; i < contactList.size(); i++) {
                Contact contact = (Contact) contactList.get(i);

                //Check changed
                if (!contact.isChanged()) {
                    contactList.remove(contact);
                    i--;
                    continue;
                }

                //Validate given contact, and convert it to Object[]
                Object[] params = new Object[CONTACT_COLUMNS_COUNT];
                this.convertContact(contact, params, true);

                parameters.add(params);
            }

            int size = contactList.size();
            //All contacts are not changed, return
            if (size == 0) {
                return;
            }

            long[] ids = new long[size];
            for (int i = 0; i < size; i++) {
                ids[i] = ((Contact) contactList.get(i)).getId();
            }

            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);

            Map contactToAssociation = this.selectContacts(con, ids, operation);
            if (contactToAssociation.size() == 0) {
                throw new EntityNotFoundException("Nothing found for the given ids of Contacts to be updated: "
                    + DAOHelper.convertIds(new StringBuffer(), ids, "ids"));
            }
            List existingContacts = this.extractKeySet(this.selectContacts(con, ids, operation));
            for (int i = 0; i < size; i++) {
                Contact contact = (Contact) contactList.get(i);
                int index = existingContacts.indexOf(contact);

                if (index > -1) {
                    if (doAudit) {
                        //Audit
                        Contact existingContact = (Contact) existingContacts.get(index);
                        ContactType type = null;
                        long relatedId = -1;
                        if (existingContact.getContactType() != null) {
                            //use existing type
                            type = existingContact.getContactType();
                            relatedId = existingContact.getId();
                        } else if (contact.getContactType() != null) {
                            //use given type
                            type = contact.getContactType();
                        } else {
                            //no type, no audit
                            continue;
                        }
                        AuditHeader auditHeader = this.getAuditRecord(existingContact, contact);
                        DAOHelper.setApplicationAreaAndRelatedId(auditHeader, type, relatedId);
                        DAOHelper.audit(this.auditManager, auditHeader, operation);
                    }
                } else {
                    //EntityNotFound
                    throw new EntityNotFoundException("Contact with id '" + ids[i] + "' does not exist.");
                }
            }
            ps = DAOHelper.prepareStatement(con, UPDATE_CONTACT, operation);
            for (int i = 0; i < parameters.size(); i++) {
                //Set parameters
                Object[] params = (Object[]) parameters.get(i);
                DAOHelper.setUpPreparedStatement(ps, params, batch, operation);
            }
            if (batch) {
                DAOHelper.executeBatch(ps, operation);
            } else {
                DAOHelper.executeUpdate(ps, operation);
            }

            this.setChangedFalse(contactsArray);
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }
    /**
     * <p>
     * Retrieve all the entries from table <em>contact</em> within persistence. According to design, any contact
     * may be associated with zero/one entity at one time, so if any contact is found to be associated with multiple
     * entities, an <code>AssociationException</code> will be raised. If the contact is not associated with any
     * entity, its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Contact</code>(if not empty) will have the changed status set as false.
     * </p>
     *
     * @return non null, possible empty array containing all the non null contacts
     *
     * @throws PersistenceException if error occurs while accessing database
     * @throws AssociationException if any existing contact is found to be associated with multiple entities
     */
    public Contact[] getAllContacts() throws PersistenceException, AssociationException {
        return this.listToArray(this.extractKeySet(this.selectContacts(null, null, "retrieving all Contacts")));
    }
    /**
     * <p>
     * Search for the contacts matching criteria represented by given filter. According to design, any contact
     * may be associated with zero/one entity at one time, so if any contact is found to be associated with multiple
     * entities, an <code>AssociationException</code> will be raised. If the contact is not associated with any
     * entity, its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Contact</code>(if not empty) will have the changed status set as false.
     * </p>
     *
     * @param filter non null filter
     *
     * @return non null, possible empty array containing found contacts
     *
     * @throws IllegalArgumentException if the filter is null
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AssociationException if any existing contact is found to be associated with multiple entities
     */
    public Contact[] searchContacts(Filter filter) throws PersistenceException, AssociationException {
        Helper.validateNotNullWithIAE(filter, "Filter to search Contact");

        String operation = "searching Contact";

        try {
            CustomResultSet crs = (CustomResultSet) this.searchBundle.search(filter);
            Map contacts = new LinkedHashMap();
            while (crs.next()) {
                this.populateContact(contacts, null, crs, operation);
            }
            return this.listToArray(this.extractKeySet(contacts));
        } catch (SearchBuilderException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (ClassCastException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }
    /**
     * <p>
     * Deassociate the given <code>Contact</code> with given entity id.
     * <ul>
     *   <li>If given <code>Contact</code> is not associated with given entity id currently, nothing will happen.
     *   In other words, if the given <code>Contact</code> is found to be associated with some other entity currently,
     *   that association will not be removed.</li>
     *   <li>If the given <code>Contact</code> is found to be associated with multiple entities, an
     *   <code>AssociationException</code> will be raised.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     * The given <code>Contact</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of contact must be positive.</li>
     *   <li>The type of contact must be non-null.</li>
     *   <li>The modification user must be non-null, non-empty, with length &lt;=64.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * @param contact non null contact to be associated
     * @param entityId the non null positive id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null, or the entity id is non-positive
     * @throws InvalidPropertyException if the id of contact is non-positive, or creation/modification
     *         user/date of the contact is invalid, or its type is null.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the contact is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    public void deassociate(Contact contact, long entityId, boolean doAudit)
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException {
        this.handleAssociation(contact, entityId, doAudit, true);
    }
    /**
     * <p>
     * Get an instance of <code>AuditHeader</code> with details to be audited.
     * </p>
     *
     * <p>
     * When add, the old contact is null.
     * When delete, the new contact is null.
     * When update, both old and new contact are not null.
     * </p>
     *
     * @param oldContact The old <code>Contact</code>.
     * @param newContact The new <code>Contact</code>.
     *
     * @return <code>AuditHeader</code> with details.
     */
    private AuditHeader getAuditRecord(Contact oldContact, Contact newContact) {
        boolean insert = oldContact == null;
        boolean delete = newContact == null;
        boolean update = (oldContact != null) && (newContact != null);

        List details = new ArrayList();

        if (!update) {
            details.add(DAOHelper.getAuditDetail("contact_id", insert ? null : oldContact.getId() + "",
                     delete ? null : newContact.getId() + ""));
        }

        if ((!update) || (update && !oldContact.getFirstName().equals(newContact.getFirstName()))) {
            details.add(DAOHelper.getAuditDetail("first_name",
                    insert ? null : oldContact.getFirstName(),
                    delete ? null : newContact.getFirstName()));
        }

        if ((!update) || (update && !oldContact.getLastName().equals(newContact.getLastName()))) {
            details.add(DAOHelper.getAuditDetail("last_name",
                    insert ? null : oldContact.getLastName(),
                    delete ? null : newContact.getLastName()));
        }

        if ((!update) || (update && !oldContact.getPhoneNumber().equals(newContact.getPhoneNumber()))) {
            details.add(DAOHelper.getAuditDetail("phone",
                    insert ? null : oldContact.getPhoneNumber(),
                    delete ? null : newContact.getPhoneNumber()));
        }

        if ((!update) || (update && !oldContact.getEmailAddress().equals(newContact.getEmailAddress()))) {
            details.add(DAOHelper.getAuditDetail("email",
                    insert ? null : oldContact.getEmailAddress(),
                    delete ? null : newContact.getEmailAddress()));
        }

        this.createAuditRecordPartial(details, oldContact, newContact);
        AuditHeader auditHeader =
            DAOHelper.getAuditHeader(update ? AuditType.UPDATE : (delete ? AuditType.DELETE : AuditType.INSERT),
                update ? newContact.getModificationUser() : (insert ? newContact.getCreationUser()
                    : oldContact.getModificationUser()), "contact", insert ? newContact.getId()
                    : oldContact.getId());
        auditHeader.setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));

        return auditHeader;
    }
    /**
     * <p>
     * Create <code>AuditDetail</code> for following columns:
     * <ul>
     *   <li><em>creation_date</em></li>
     *   <li><em>creation_user</em></li>
     *   <li><em>modification_date</em></li>
     *   <li><em>modification_user</em></li>
     *  </ul>
     *  And add the created details to the given list.
     * </p>
     *
     * <p>
     * When add, the old contact is null.
     * When delete, the new contact is null.
     * When update, both old and new contact are not null.
     * </p>
     *
     * @param details The list to hold details.
     * @param oldContact The old <code>Contact</code>.
     * @param newContact The new <code>Contact</code>.
     */
    private void createAuditRecordPartial(List details, Contact oldContact, Contact newContact) {
        boolean insert = oldContact == null;
        boolean delete = newContact == null;
        boolean update = (oldContact != null) && (newContact != null);

        //For DATETIME YEAR TO SECOND, the precision is second
        if ((!update) || (update && (oldContact.getCreationDate().getTime() / DAOHelper.MILLISECOND
            != newContact.getCreationDate().getTime() / DAOHelper.MILLISECOND))) {
            details.add(DAOHelper.getAuditDetail("creation_date",
                                                 insert ? null : oldContact.getCreationDate().toString(),
                                                 delete ? null : newContact.getCreationDate().toString()));
        }

        if ((!update) || (update && !oldContact.getCreationUser().equals(newContact.getCreationUser()))) {
            details.add(DAOHelper.getAuditDetail("creation_user",
                                                 insert ? null : oldContact.getCreationUser(),
                                                 delete ? null : newContact.getCreationUser()));
        }

        if ((!update) || (update && (oldContact.getModificationDate().getTime() / DAOHelper.MILLISECOND
            != newContact.getModificationDate().getTime() / DAOHelper.MILLISECOND))) {
            details.add(DAOHelper.getAuditDetail("modification_date",
                                                 insert ? null : oldContact.getModificationDate().toString(),
                                                 delete ? null : newContact.getModificationDate().toString()));
        }

        if ((!update)  || (update && !oldContact.getModificationUser().equals(newContact.getModificationUser()))) {
            details.add(DAOHelper.getAuditDetail("modification_user",
                                                 insert ? null : oldContact.getModificationUser(),
                                                 delete ? null : newContact.getModificationUser()));
        }
    }
    /**
     * <p>
     * Validate given <code>Contact</code> and convert its fields into an array of objects.
     * </p>
     *
     * <p>
     * The creation/modification date will be set as current date when adding.
     * The modification date will be set as current date when updating.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  <ul>
     *   <li>The first_name, last_name, phone, email, creation/modification user must not be null,
     *   must not be empty, must within range of max length.</li>
     *   <li>For update, the creation date must not be null and must not exceed current date.</li>
     *   <li>For update, the id must be positive.</li>
     *  </ul>
     * </p>
     *
     * @param contact The contact to validate and convert.
     * @param objects The array of objects to hold the fields' values of given <code>Contact</code>.
     * @param forUpdate Indicate whether the given contact is to be updated or added.
     *
     * @throws InvalidPropertyException If given <code>Contact</code> is not valid.
     */
    private void convertContact(Contact contact, Object[] objects, boolean forUpdate)
        throws InvalidPropertyException {
        String usage = forUpdate ? " Contact to be updated" : " Contact to be added";
        Helper.validateNotNullWithIAE(contact, usage);

        int i = 0;

        String firstName = contact.getFirstName();
        Helper.validateStringWithMaxLengthWithIPE(firstName, NAME_MAX_LENGTH, "First name of" + usage);
        objects[i++] = firstName;

        String lastName = contact.getLastName();
        Helper.validateStringWithMaxLengthWithIPE(lastName, NAME_MAX_LENGTH, "Last name of" + usage);
        objects[i++] = lastName;

        String phone = contact.getPhoneNumber();
        Helper.validateStringWithMaxLengthWithIPE(phone, PHONE_MAX_LENGTH, "Phone of" + usage);
        objects[i++] = phone;

        String email = contact.getEmailAddress();
        Helper.validateStringWithMaxLengthWithIPE(email, EMAIL_MAX_LENGTH, "Email of" + usage);
        objects[i++] = email;

        this.convertContactPartial(contact, objects, i, usage, new Boolean(forUpdate));

        if (forUpdate) {
            //For update, validate id
            long contactId = contact.getId();
            Helper.validatePositiveWithIPE(contactId, "Id of" + usage);
            objects[objects.length - 1] = new Long(contactId);
        } else {
            //For add, do not validate id
            objects[objects.length - 1] = null;
        }
    }
    /**
     * <p>
     * Validate partial fields of given <code>Contact</code> and convert them into an array of objects.
     * </p>
     *
     * <p>
     * The creation/modification date will be set as current date when adding.
     * The modification date will be set as current date when updating.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  <ul>
     *   <li>The creation/modification user must not be null.</li>
     *   <li>For associate, the creation date must not be null and must not exceed modification date,
     *   the modification date must not be null and must not exceed current date.</li>
     *   <li>For update, the creation date must not be null and must not exceed current date.</li>
     *   <li>For update, the id must be positive.</li>
     *  </ul>
     * </p>
     *
     * @param contact The contact to validate and convert.
     * @param objects The array of objects to hold the fields' values of given <code>Contact</code>.
     * @param i The index within objects array at which begin to hold the fields' values.
     * @param usage The usage of the given <code>Contact</code>.
     * @param condition True means update; False means add; Null means associate.
     *
     * @throws InvalidPropertyException If given contact is not valid.
     */
    private void convertContactPartial(Contact contact, Object[] objects, int i, String usage,
        Boolean condition) throws InvalidPropertyException {
        String creationUser = contact.getCreationUser();
        Helper.validateStringWithMaxLengthWithIPE(creationUser, USERNAME_MAX_LENGTH, "Creation user of" + usage);
        objects[i++] = creationUser;

        String modificationUser = contact.getModificationUser();
        Helper.validateStringWithMaxLengthWithIPE(modificationUser, USERNAME_MAX_LENGTH,
            "Modification user of" + usage);
        objects[i++] = modificationUser;

        Date currentDate = new Date();

        if ((condition == null) || condition.booleanValue()) {
            if (condition != null) {
                //For update, set modification date as current date
                contact.setModificationDate(currentDate);
            }

            Date creationDate = contact.getCreationDate();
            Date modificationDate = contact.getModificationDate();
            //For update & associate, validate creation date &lt;= modification date &lt;= current date
            Helper.validateNotExceed(creationDate, modificationDate, "Creation date of" + usage,
                "modification date");
            Helper.validateNotExceed(creationDate, modificationDate,
                "Modification date of" + usage, "current date");
            objects[i++] = creationDate;
            objects[i++] = modificationDate;

        } else {
            //For add, set creation and modification date as current date
            contact.setCreationDate(currentDate);
            contact.setModificationDate(currentDate);
            objects[i++] = currentDate;
            objects[i++] = currentDate;
        }
    }
    /**
     * <p>
     * Associate the given <code>Contact</code> with given entity id.
     *  <ul>
     *   <li>If given <code>Contact</code> has already been associated with given entity id, nothing will happen.
     *   </li>
     *   <li>If the given <code>Contact</code> is found to be associated with some other entity currently, that
     *   association will be removed first and then the new association will be created.</li>
     *   <li>If the given <code>Contact</code> is found to be associated with multiple entities, an
     *   <code>AssociationException</code> will be raised.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     * The given <code>Contact</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of contact must be positive.</li>
     *   <li>The type of contact must be non-null.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
     *   <li>The creation date must not be null, and must not exceed modification date.</li>
     *   <li>The modification date must not be null, and must not exceed current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * @param contact non null contact  to be deassociated
     * @param entityId the non null positive id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null or the entity id non-positive
     * @throws InvalidPropertyException if the id of contact is non-positive, or creation/modification
     *         user/date of the contact is invalid, or its type is null.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the contact is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    public void associate(Contact contact, long entityId, boolean doAudit)
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException {
        this.handleAssociation(contact, entityId, doAudit, false);
    }
    /**
     * <p>
     * Audit the DELETION/INSERTION on <em>contact_relation</em> table.
     * </p>
     *
     * @param contactId The id of <code>Contact</code>
     * @param entityId The associated entity id
     * @param contactAssociate The instance of <code>Contact</code> represents the contact association.
     * @param actionType The action type, INSERT or DELETE
     * @param user The creation user for audit record
     * @param operation Represents current operation performing
     *
     * @throws AuditException If error occurs while audit
     */
    private void auditContactRelation(long contactId, long entityId, Contact contactAssociate, int actionType,
        String user, String operation) throws AuditException {
        boolean insert = actionType == AuditType.INSERT;
        ContactType contactType = contactAssociate.getContactType();
        List details = new ArrayList();
        this.createAuditRecordPartial(details, insert ? null : contactAssociate, insert ? contactAssociate : null);
        details.add(DAOHelper.getAuditDetail("contact_id", insert ? null : contactId + "",
            insert ? contactId + "" : null));
        details.add(DAOHelper.getAuditDetail("contact_type_id", insert ? null : contactType.getId() + "",
            insert ? contactType.getId() + "" : null));
        details.add(DAOHelper.getAuditDetail("entity_id", insert ? null : entityId + "",
            insert ? entityId + "" : null));

        AuditHeader auditHeader = DAOHelper.getAuditHeader(actionType, user, "contact_relation", contactId);
        DAOHelper.setApplicationAreaAndRelatedId(auditHeader, contactType, entityId);
        auditHeader.setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));
        DAOHelper.audit(this.auditManager, auditHeader, operation);
    }
    /**
     * <p>
     * Handle associate/deassociate the given <code>Contact</code> with given entity id.
     * The given <code>Contact</code> should have id, type set with valid values.
     * </p>
     *
     * <p>
     * If this method is called by <code>associate()</code>, then this method will check the creation/modification
     * user/date. If this method is called by <code>deassociate()</code>, then this method will check the modification
     * user.
     * </p>
     *
     * <p>
     * For associate, if the relation already existed, simply return.
     * For deassociate, if the relation does not exist, simply return.
     * </p>
     *
     * @param contact non null contact to associate/deassociate
     * @param entityId the non null positive id of entity
     * @param deassociate indicates whether to associate or deassociate the given contact
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null or the entity id non-positive
     * @throws InvalidPropertyException if the id of contact is non-positive, or its type is null.
     *         Or creation/modification user/date are invalid for associate; Or modification user is invalid
     *         for deassociate.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the contact is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    private void handleAssociation(Contact contact, long entityId, boolean doAudit, boolean deassociate)
        throws InvalidPropertyException, PersistenceException, AuditException, AssociationException {
        String usage = "Contact to be " + (deassociate ? "deassociated" : "associated");
        //Validate contact not null
        Helper.validateNotNullWithIAE(contact, usage);

        //Validate entity id
        Helper.validatePositiveWithIAE(entityId, "Related entity id of " + usage);

        //Validate contact id
        long contactId = contact.getId();
        Helper.validatePositiveWithIPE(contactId, "Id of " + usage);

        //Validate contact type
        ContactType contactType = contact.getContactType();
        Helper.validateNotNullWithIPE(contactType, "Type of " + usage);

        Object[] params = null;
        Object[] associateParams = null;
        if (deassociate) {
            //For deassociate, validate modification user
            String modificationUser = contact.getModificationUser();
            Helper.validateStringWithMaxLengthWithIPE(modificationUser,
                USERNAME_MAX_LENGTH, "Modification user of " + usage);
        } else {
            //For associate, validate creation/modication user/date
            associateParams = new Object[CONTACT_RELATION_COLUMNS_COUNT];
            this.convertContactPartial(contact, associateParams, 0, usage, null);
        }

        String operation = deassociate ? "deassociating" : "associating" + " Contact relation";
        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            con = DAOHelper.createConnection(connectionFactory, connectionName, operation);
            Map contactToAssociation = this.selectContacts(con, new long[]{contactId}, operation);

            if (contactToAssociation.size() == 0) {
                throw new AssociationException(usage + " with id '" + contactId + "' does not exist.");
            }
            Contact contactAssociation = (Contact) contactToAssociation.get(contact);

            boolean exist = contactAssociation != null
                && contactAssociation.getContactType().compareTo(contactType) == 0
                && contactAssociation.getId() == entityId;

            if ((exist && !deassociate) || (!exist && deassociate)) {
                //For deassociate, not existed, simply return
                //For associate, already existed, simply return
                return;
            } else if (deassociate || contactAssociation != null) {
                //For deassociate, existed, delete it
                //For associate, there is another entity associated, delete it
                params = new Object[CONTACT_RELATION_PK_COLUMNS_COUNT];
                params[0] = new Long(contactId);
                params[1] = new Long(contactAssociation.getContactType().getId());
                params[2] = new Long(contactAssociation.getId());
                ps1 = DAOHelper.prepareStatement(con, DEASSOCIATE, operation);
                DAOHelper.setUpPreparedStatement(ps1, params, false, operation);
                DAOHelper.executeUpdate(ps1, operation);
                if (doAudit) {
                    this.auditContactRelation(contactId, contactAssociation.getId(), contactAssociation,
                        AuditType.DELETE, contact.getModificationUser(), operation);
                }
            }
            if (!deassociate) {
                associateParams[associateParams.length - 2 - 1] = new Long(contactId);
                associateParams[associateParams.length - 2] = new Long(contactType.getId());
                associateParams[associateParams.length - 1] = new Long(entityId);
                ps2 = DAOHelper.prepareStatement(con, ASSOCIATE, operation);
                DAOHelper.setUpPreparedStatement(ps2, associateParams, false, operation);
                DAOHelper.executeUpdate(ps2, operation);
                if (doAudit) {
                    this.auditContactRelation(contactId, entityId, contact, AuditType.INSERT, contact.getCreationUser(),
                        operation);
                }
            }
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps1, null);
            DAOHelper.realeaseJDBCResource(null, ps2, con);
        }
    }
}
