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
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressDAO;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.Helper;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.State;

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
 * This class is the Informix database implementation of the <code>AddressDAO</code>.
 * It provides general retrieve/update/remove/add functionality to access the database.
 * And it provides method to search addresses with filter.
 * </p>
 *
 * <p>
 * This class will be instantiated by <code>AddressBean</code> to perform CURD operations.
 * So the transaction atomic is managed by EJB container, the connection isolation level
 * is specified by container administrator.
 * </p>
 *
 * <p>
 *  <strong>Sample configuration:</strong>
 *  <pre>
 *    &lt;Config name="com.topcoder.timetracker.contact.persistence.InformixAddressDAO"&gt;
 *      &lt;Property name="connection_factory_namespace"&gt;
 *         &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;!-- use JNDI connection --&gt;
 *      &lt;Property name="connection_name"&gt;
 *         &lt;Value&gt;InformixJNDIConnection&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="idgenerator_name"&gt;
 *         &lt;Value&gt;AddressIDGenerator&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="search_bundle_name"&gt;
 *         &lt;Value&gt;AddressSearch&lt;/Value&gt;
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
public class InformixAddressDAO implements AddressDAO {

    /**
     * <p>The count of columns of <em>address</em> table.</p>
     */
    private static final int ADDRESS_COLUMNS_COUNT = 11;

    /**
     * <p>The count of columns of <em>address_relation</em> table.</p>
     */
    private static final int ADDRESS_RELATION_COLUMNS_COUNT = 7;

    /**
     * <p>The count of primary key columns of <em>address_relation</em> table.</p>
     */
    private static final int ADDRESS_RELATION_PK_COLUMNS_COUNT = 3;

    /**
     * <p>SQL clause used to select all records from <em>address</em> table left join <em>address_relation</em>.</p>
     *
     * <p>
     * Since the address may not be associated, in other words, mapped to zero entity, so "left join" is used for
     * table <em>address_relation</em>. But address must have state and country, so "inner join" is used for tables
     * <em>state_name</em> and <em>country_name</em>.
     * </p>
     */
    private static final String SELECT_ADDRESS =
        "Select address.*,state_name.name as state_name,state_name.abbreviation,"
        + "state_name.creation_date as state_creation_date,state_name.modification_date as state_modification_date,"
        + "state_name.creation_user as state_creation_user,state_name.modification_user as state_modification_user,"
        + "country_name.name as country_name,"
        + "country_name.creation_date as country_creation_date,"
        + "country_name.modification_date as country_modification_date,"
        + "country_name.creation_user as country_creation_user,"
        + "country_name.modification_user as country_modification_user, address_relation.address_type_id,"
        + "address_relation.entity_id,address_relation.creation_date as relation_creation_date,"
        + "address_relation.modification_date as relation_modification_date,"
        + "address_relation.creation_user as relation_creation_user,"
        + "address_relation.modification_user as relation_modification_user "
        + "from address inner join state_name on address.state_name_id = state_name.state_name_id inner join "
        + "country_name on address.country_name_id = country_name.country_name_id left join address_relation "
        + "on address.address_id = address_relation.address_id ";

    /**
     * <p>SQL clause used to select all records from <em>country_name</em> table.</p>
     */
    private static final String SELECT_COUNTRY =
        "select country_name.country_name_id, country_name.name as country_name,"
        + "country_name.creation_date as country_creation_date,"
        + "country_name.modification_date as country_modification_date,"
        + "country_name.creation_user as country_creation_user,"
        + "country_name.modification_user as country_modification_user from country_name order by country_name_id";

    /**
     * <p>SQL clause used to select all records from <em>state_name</em> table.</p>
     */
    private static final String SELECT_STATE =
        "select state_name.state_name_id,state_name.name as state_name,state_name.abbreviation,"
        + "state_name.creation_date as state_creation_date,state_name.modification_date as state_modification_date,"
        + "state_name.creation_user as state_creation_user,state_name.modification_user as state_modification_user "
        + "from state_name order by state_name_id";

    /**
     * <p>SQL clause used to delete records from <em>address</em> table based on given address id.</p>
     */
    private static final String DELETE_ADDRESS = "delete from address where address_id = ?";

    /**
     * <p>SQL clause used to insert a new record into <em>address</em> table.</p>
     */
    private static final String INSERT_ADDRESS =
        "insert into address(line1,line2,city,state_name_id,country_name_id,"
        + "zip_code,creation_user,modification_user,creation_date,modification_date,address_id) values "
        + "( ?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * <p>SQL clause used to update an existing record within <em>address</em> table.</p>
     */
    private static final String UPDATE_ADDRESS =
        "update address set line1=?,line2=?,city=?,state_name_id=?,"
        + "country_name_id=?,zip_code=?,creation_user=?,modification_user=?,creation_date=?,modification_date=? "
        + "where address_id=?";

    /**
     * <p>SQL clause used to insert a new record into <em>address_relation</em> table.</p>
     */
    private static final String ASSOCIATE =
        "insert into address_relation (creation_user,modification_user,"
        + "creation_date,modification_date,address_id,address_type_id,entity_id) values (?,?,?,?,?,?,?) ";

    /**
     * <p>SQL clause used to delete an existing record from <em>address_relation</em> table.</p>
     */
    private static final String DEASSOCIATE =
        "delete from address_relation where address_id=? and address_type_id=? and entity_id=?";

    /**
     * <p>SQL clause used to delete records from <em>address_relation</em> table based on the address id.</p>
     */
    private static final String DELETE_RELATIONS =
        "delete from address_relation where address_id=?";

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
     * It is referenced by <code>searchAddress()</code> method.
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
     * Represents the IDGenerator used to generate the id of the address.
     * This variable is set in constructor,is immutable and never be null.
     * It is referenced by <code>addAddress()</code> and <code>addAddresses()</code> methods.
     * </p>
     */
    private final IDGenerator addressIDGenerator;

    /**
     * <p>
     * Constructs <code>InformixAddressDAO</code> with default namespace:
     *  <ul>
     *   <li>com.topcoder.timetracker.contact.persistence.InformixAddressDAO</li>
     *  </ul>
     *  See class doc for sample configuration.
     * </p>
     *
     * @throws ConfigurationException if any required properties are missing or invalid. Or any error occurs while
     *         creating <code>DBConnectionFactory</code>, <code>IDGenerator</code>, <code>SearchBundle</code>,
     *         <code>AuditManager</code> based on the configuration within default namespace.
     */
    public InformixAddressDAO() throws ConfigurationException {
        this(InformixAddressDAO.class.getName());
    }

    /**
     * <p>
     * Constructs <code>InformixAddressDAO</code> with the given namespace.
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
    public InformixAddressDAO(String namespace) throws ConfigurationException {
        this(namespace, null);
    }

    /**
     * <p>
     * Constructs <code>InformixAddressDAO</code> with the given namespace.
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
    public InformixAddressDAO(String namespace, AuditManager auditManager)
        throws ConfigurationException {
        this.connectionName = Helper.getPropertyValue(namespace, "connection_name", false);
        this.connectionFactory = DAOHelper.getConnectionFactory(namespace);
        this.addressIDGenerator = DAOHelper.getIDGenerator(namespace);
        this.searchBundle = DAOHelper.getSearchBundle(namespace);

        if (auditManager != null) {
            this.auditManager = auditManager;
        } else {
            this.auditManager = DAOHelper.getAuditManager(namespace);
        }
    }

    /**
     * <p>
     * Validate given <code>Address</code> and convert its fields into an array of objects.
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
     *   <li>The line1, postal code, creation/modification user must not be null, must not be empty.
     *   The line2 could be null.</li>
     *   <li>For update, the creation date must not be null and must not exceed current date.</li>
     *   <li>For update, the id must be positive.</li>
     *  </ul>
     * </p>
     *
     * @param address The address to validate and convert.
     * @param objects The array of objects to hold the fields' values of given <code>Address</code>.
     * @param forUpdate Indicate whether the given address is to be updated or added.
     *
     * @throws InvalidPropertyException If given <code>Address</code> is not valid.
     */
    private void convertAddress(Address address, Object[] objects, boolean forUpdate)
        throws InvalidPropertyException {
        String usage = forUpdate ? " Address to be updated" : " Address to be added";
        Helper.validateNotNullWithIAE(address, usage);

        int i = 0;

        String line1 = address.getLine1();
        Helper.validateNotNullWithIPE(line1, "Line1 of" + usage);
        objects[i++] = line1;

        String line2 = address.getLine2();
        //line2 is null-able
        objects[i++] = line2;

        String city = address.getCity();
        Helper.validateNotNullWithIPE(city, "City of" + usage);
        objects[i++] = city;

        State state = address.getState();
        Helper.validateNotNullWithIPE(state, "State of" + usage);
        Helper.validatePositiveWithIPE(state.getId(), "State id of" + usage);
        objects[i++] = new Long(state.getId());

        Country country = address.getCountry();
        Helper.validateNotNullWithIPE(country, "Country of" + usage);
        Helper.validatePositiveWithIPE(country.getId(), "Country id of" + usage);
        objects[i++] = new Long(country.getId());

        String postalCode = address.getPostalCode();
        Helper.validateNotNullWithIPE(postalCode, "Postal code of" + usage);
        objects[i++] = postalCode;

        //Convert creation/modification user/date
        this.convertAddressPartial(address, objects, i, usage, new Boolean(forUpdate));

        if (forUpdate) {
            //For update, validate id
            long addressId = address.getId();
            Helper.validatePositiveWithIPE(addressId, "Id of" + usage);
            objects[objects.length - 1] = new Long(addressId);
        } else {
            //For add, do not validate id
            objects[objects.length - 1] = null;
        }
    }

    /**
     * <p>
     * Validate partial fields of given <code>Address</code> and convert them into an array of objects.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  <ul>
     *   <li>For all cases, the creation/modification user must not be null.</li>
     *   <li>For associate, the creation date must not be null and must not exceed modification date,
     *   the modification date must not be null and must not exceed current date.</li>
     *   <li>For add, the creation/modification date will be set as current date.</li>
     *   <li>For update, the creation date must not be null and must not exceed current date.
     *   The modification date will be set as current date</li>
     *  </ul>
     * </p>
     *
     * @param address The address to validate and convert.
     * @param objects The array of objects to hold the fields' values of given <code>Address</code>.
     * @param i The index within objects array at which begin to hold the fields' values.
     * @param usage The usage of the given <code>Address</code>.
     * @param condition True means update; False means add; Null means associate.
     *
     * @throws InvalidPropertyException If given address is not valid.
     */
    private void convertAddressPartial(Address address, Object[] objects, int i, String usage,
                                       Boolean condition)
        throws InvalidPropertyException {
        String creationUser = address.getCreationUser();
        Helper.validateNotNullWithIPE(creationUser, "Creation user of" + usage);
        objects[i++] = creationUser;

        String modificationUser = address.getModificationUser();
        Helper.validateNotNullWithIPE(modificationUser, "Modification user of" + usage);
        objects[i++] = modificationUser;

        Date currentDate = new Date();

        if ((condition == null) || condition.booleanValue()) {
            if (condition != null) {
                //For update, set modification date as current date
                address.setModificationDate(currentDate);
            }

            Date creationDate = address.getCreationDate();
            Date modificationDate = address.getModificationDate();
            //For update & associate, validate creation date <= modification date <= current date
            Helper.validateNotExceed(creationDate, modificationDate, "Creation date of" + usage,
                                     "modification date");
            Helper.validateNotExceed(creationDate, modificationDate,
                                     "Modification date of" + usage, "current date");
            objects[i++] = creationDate;
            objects[i++] = modificationDate;

        } else {
            //For add, set creation and modification date as current date
            address.setCreationDate(currentDate);
            address.setModificationDate(currentDate);
            objects[i++] = currentDate;
            objects[i++] = currentDate;
        }
    }

    /**
     * <p>
     * Get an instance of <code>AuditHeader</code> with details to be audited.
     * </p>
     *
     * <p>
     * When add, the old address is null.
     * When delete, the new address is null.
     * When update, both old and new address are not null.
     * </p>
     *
     * @param oldAddress The old <code>Address</code>.
     * @param newAddress The new <code>Address</code>.
     *
     * @return <code>AuditHeader</code> with details.
     */
    private AuditHeader getAuditRecord(Address oldAddress, Address newAddress) {
        boolean insert = oldAddress == null;
        boolean delete = newAddress == null;
        boolean update = (oldAddress != null) && (newAddress != null);

        List details = new ArrayList();

        if (!update) {
            details.add(DAOHelper.getAuditDetail("address_id", insert ? null : oldAddress.getId() + "",
                     delete ? null : newAddress.getId() + ""));
        }

        if ((!update) || (update && !oldAddress.getLine1().equals(newAddress.getLine1()))) {
            details.add(DAOHelper.getAuditDetail("line1", insert ? null : oldAddress.getLine1(),
                                                 delete ? null : newAddress.getLine1()));
        }

        if ((!update) || ((update && ((oldAddress.getLine2() != null)
            && !oldAddress.getLine2().equals(newAddress.getLine2())))
            || ((oldAddress.getLine1() != null) && !oldAddress.getLine1().equals(newAddress.getLine1())))) {
            details.add(DAOHelper.getAuditDetail("line2", insert ? null : oldAddress.getLine2(),
                                                 delete ? null : newAddress.getLine2()));
        }

        if ((!update) || (update && !oldAddress.getCity().equals(newAddress.getCity()))) {
            details.add(DAOHelper.getAuditDetail("city", insert ? null : oldAddress.getCity(),
                                                 delete ? null : newAddress.getCity()));
        }

        if ((!update) || (update && (oldAddress.getState().getId() != newAddress.getState().getId()))) {
            details.add(DAOHelper.getAuditDetail("state_name_id",
                                                 insert ? null : (oldAddress.getState().getId() + ""),
                                                 delete ? null : (newAddress.getState().getId() + "")));
        }

        if ((!update) || (update && (oldAddress.getCountry().getId() != newAddress.getCountry().getId()))) {
            details.add(DAOHelper.getAuditDetail("contry_name_id",
                                                 insert ? null : (oldAddress.getCountry().getId() + ""),
                                                 delete ? null : (newAddress.getCountry().getId() + "")));
        }

        if ((!update) || (update && !oldAddress.getPostalCode().equals(newAddress.getPostalCode()))) {
            details.add(DAOHelper.getAuditDetail("zip_code",
                                                 insert ? null : oldAddress.getPostalCode(),
                                                 delete ? null : newAddress.getPostalCode()));
        }

        this.createAuditRecordPartial(details, oldAddress, newAddress);

        AuditHeader auditHeader = DAOHelper.getAuditHeader(
            update ? AuditType.UPDATE : (delete ? AuditType.DELETE : AuditType.INSERT),
            insert ? newAddress.getCreationUser() : update ? newAddress.getModificationUser()
                : oldAddress.getModificationUser(), "address", insert ? newAddress.getId()
                : oldAddress.getId());
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
     * When add, the old address is null.
     * When delete, the new address is null.
     * When update, both old and new address are not null.
     * </p>
     *
     * @param details The list to hold details.
     * @param oldAddress The old <code>Address</code>.
     * @param newAddress The new <code>Address</code>.
     */
    private void createAuditRecordPartial(List details, Address oldAddress, Address newAddress) {
        boolean insert = oldAddress == null;
        boolean delete = newAddress == null;
        boolean update = (oldAddress != null) && (newAddress != null);

        //For DATETIME YEAR TO SECOND, the precision is second
        if ((!update) || (update && (oldAddress.getCreationDate().getTime() / Helper.MILLISECOND
            != newAddress.getCreationDate().getTime() / Helper.MILLISECOND))) {
            details.add(DAOHelper.getAuditDetail("creation_date",
                                                 insert ? null : oldAddress.getCreationDate().toString(),
                                                 delete ? null : newAddress.getCreationDate().toString()));
        }

        if ((!update) || (update && !oldAddress.getCreationUser().equals(newAddress.getCreationUser()))) {
            details.add(DAOHelper.getAuditDetail("creation_user",
                                                 insert ? null : oldAddress.getCreationUser(),
                                                 delete ? null : newAddress.getCreationUser()));
        }

        if ((!update) || (update && (oldAddress.getModificationDate().getTime() / Helper.MILLISECOND
                != newAddress.getModificationDate().getTime() / Helper.MILLISECOND))) {
            details.add(DAOHelper.getAuditDetail("modification_date",
                                                 insert ? null : oldAddress.getModificationDate().toString(),
                                                 delete ? null : newAddress.getModificationDate().toString()));
        }

        if ((!update)  || (update && !oldAddress.getModificationUser().equals(newAddress.getModificationUser()))) {
            details.add(DAOHelper.getAuditDetail("modification_user",
                                                 insert ? null : oldAddress.getModificationUser(),
                                                 delete ? null : newAddress.getModificationUser()));
        }
    }
    /**
     * <p>
     * Add the given <code>Address</code> into the database.
     * </p>
     *
     * <p>
     * The given <code>Address</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of address will not be validated. The previous value will be ignored and replaced by id got from
     *      <code>IDGenerator</code>.</li>
     *   <li>The line1 of address must be non-null, non-empty.</li>
     *   <li>The line2 of address could be null. If it is not null, then it must be non-empty.</li>
     *   <li>The city of address must be non-null, non-empty.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty.</li>
     *   <li>The creation/modification user must be non-null, non-empty.</li>
     *   <li>The creation/modification date will not be validated. The previous values will be ignored and replaced
     *       by current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  After adding successfully, the changed status of given <code>Address</code> will be set as false.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>AddressType</code>, so if the type of given address is null, no audit will be performed even
     *  the doAudit flag is true.
     * </p>
     *
     * @param address non null address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     */
    public void addAddress(Address address, boolean doAudit)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException {
        this.insertAddresses(new Address[]{address}, doAudit, false);
    }

    /**
     * <p>
     * Add the given array of <code>Address</code> into the database.
     * </p>
     *
     * <p>
     * Any given <code>Address</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of address will not be validated. The previous value will be ignored and replaced by id got from
     *      <code>IDGenerator</code>.</li>
     *   <li>The line1 of address must be non-null, non-empty.</li>
     *   <li>The line2 of address could be null. If it is not null, then it must be non-empty.</li>
     *   <li>The city of address must be non-null, non-empty.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty.</li>
     *   <li>The creation/modification user must be non-null, non-empty.</li>
     *   <li>The creation/modification date will not be validated. The previous values will be ignored and replaced
     *       by current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  After adding successfully, the changed status of given <code>Address</code> will be set as false.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>AddressType</code>, so if the type of the given address is null, no audit will be performed for it even
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
     * @param addresses The non-null array contains non-null <code>Address</code>. Possibly empty.
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the given array is null or contains null member
     * @throws InvalidPropertyException if the properties of any given address is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AuditException if exception occurs when audit
     */
    public void addAddresses(Address[] addresses, boolean doAudit)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException {
        this.insertAddresses(addresses, doAudit, true);
    }

    /**
     * <p>
     * Add the given array of <code>Address</code> into the database.
     * </p>
     *
     * <p>
     * Any given <code>Address</code> should have all fields set with valid values except
     * the creation/modification date and id. The creation/modification previously set will
     * be ignored and will be set as current date. Also the previous value of id will be ignored
     * and will be generated by <code>IDGenerator</code>.
     * </p>
     *
     * <p>
     *  <strong>Called by:</strong>
     *  <code>addAddress()</code> and <code>addAddresses()</code>.
     * </p>
     *
     * @param addressArray The non-null array contains non-null <code>Address</code>. Possibly empty.
     * @param doAudit whether this action should be audited
     * @param batch Indicate whether to execute batch insertion or a single insertion.
     *
     * @throws IllegalArgumentException if the given array is null or contains null member
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AuditException if exception occurs when audit
     */
    private void insertAddresses(Address[] addressArray, boolean doAudit, boolean batch)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException {
        List addressList = Helper.validateObjectsArrayWithIAE(addressArray, "Addresses to be added");
        int size = addressList.size();
        if (size == 0) {
            return;
        }
        String operation = "adding Address";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            List parameters = new ArrayList();
            for (int i = 0; i < size; i++) {
                Address address = (Address) addressList.get(i);

                //Validate given address and convert it
                Object[] params = new Object[ADDRESS_COLUMNS_COUNT];
                this.convertAddress(address, params, false);
                parameters.add(params);
            }

            for (int i = 0; i < size; i++) {
                Address address = (Address) addressList.get(i);
                //Get id from IDGenerator
                long id = DAOHelper.getNextId(this.addressIDGenerator, operation);
                address.setId(id);

                Object[] params = (Object[]) parameters.get(i);
                params[params.length - 1] = new Long(id);

                //Do audit
                if (doAudit) {
                    if (address.getAddressType() == null) {
                        //no given type, no audit
                        continue;
                    }
                    AuditHeader auditHeader = this.getAuditRecord(null, address);
                    //When an address is newly added, no association, so no related id to set, leave it as -1
                    DAOHelper.setApplicationAreaAndRelatedId(auditHeader, address.getAddressType(), -1);
                    DAOHelper.audit(this.auditManager, auditHeader, operation);
                }
            }

            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);
            ps = DAOHelper.prepareStatement(con, INSERT_ADDRESS, operation);

            for (int i = 0; i < size; i++) {
                Object[] params = (Object[]) parameters.get(i);
                DAOHelper.setUpPreparedStatement(ps, params, batch, operation);
            }

            if (batch) {
                DAOHelper.executeBatch(ps, operation);
            } else {
                DAOHelper.executeUpdate(ps, operation);
            }

            //Set changed status to false
            this.setChangedFalse(addressArray);
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }

    /**
     * <p>
     * Retrieve <code>Address</code> with given id. According to design, any address may be associated with
     * zero/one entity at one time, so if the address is found to be associated with multiple entities, an
     * <code>AssociationException</code> will be raised. If the address is not associated with any entity,
     * its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Address</code>(if not null) will have the changed status set as false.
     * </p>
     *
     * @param id the positive id of the address
     *
     * @return the address with given id, null if not found
     *
     * @throws IllegalArgumentException if the id is non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AssociationException if the address is found to be associated with multiple entities
     */
    public Address retrieveAddress(long id) throws PersistenceException, AssociationException {
        Helper.validatePositiveWithIAE(id, "Id of address to be retrieved");
        Address[] addresses = this.listToArray(this.extractKeySet(
            this.selectAddresses(null, new long[]{id}, "retrieving Address by id")));
        return addresses.length == 0 ? null : addresses[0];
    }

    /**
     * <p>
     * Retrieve an array of <code>Address</code> with given ids. According to design, any address may be associated with
     * zero/one entity at one time, so if any address is found to be associated with multiple entities, an
     * <code>AssociationException</code> will be raised. If the address is not associated with any entity, its type will
     * be left as null.
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
     *  The returned <code>Address</code> will have the changed status set as false.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(same id), there will be only one record
     *  pulled out from database and returned for all the duplicate members.
     * </p>
     *
     * @param ids the non-null, possible empty ids of the addresses
     *
     * @return the non-null array of <code>Address</code> found corresponding to given ids.
     *
     * @throws IllegalArgumentException if ids array is null or any id is non-positive
     * @throws PersistenceException if error occurs while accessing database
     * @throws AssociationException if any address is found to be associated with multiple entities
     */
    public Address[] retrieveAddresses(long[] ids) throws PersistenceException, AssociationException {
        if (Helper.validateIdsArrayWithIAE(ids, "Ids of Addresses to be retrieved")) {
            return new Address[0];
        }
        return this.listToArray(this.extractKeySet(
            this.selectAddresses(null, ids, "retrieving Addresses by ids")));
    }

    /**
     * <p>
     * The map constructed from <code>selectAddresses()</code> method will have the addresses as the keys.
     * Extract the keys out as a list.
     * </p>
     *
     * @param map The map constructed from <code>selectAddresses()</code> method
     *
     * @return List of <code>Address</code> extracted from map's keys.
     */
    private List extractKeySet(Map map) {
        List keys = new ArrayList(map.keySet());
        return keys;
    }

    /**
     * <p>
     * Convert the <code>List</code> of <code>Address</code> to array.
     * </p>
     *
     * @param list The <code>List</code> contains <code>Address</code>
     *
     * @return An array containing all <code>Address</code>es in the given list
     */
    private Address[] listToArray(List list) {
        return (Address[]) (list.toArray(new Address[list.size()]));
    }

    /**
     * <p>
     * Set the changed status of all addresses to false.
     * </p>
     *
     * @param addresses Array of <code>Address</code>
     */
    private void setChangedFalse(Address[] addresses) {
        for (int i = 0; i < addresses.length; i++) {
            addresses[i].setChanged(false);
        }
    }

    /**
     * <p>
     * Retrieve <code>Address</code> with given ids.
     * </p>
     *
     * <p>
     *  According to design, given an address may be associated to zero/one entity, so it is appropriate to
     *  construct a map to hold the mapping between address and its association.
     * </p>
     *
     * <p>
     *  <strong>Called by:</strong>
     *  <code>retrieveAddress()</code>, <code>retrieveAddresses()</code>, <code>getAllAddresses()</code>,
     *  <code>deleteAddresses()</code>, <code>modifyAddresses()</code> and <code>handleAssociation</code>.
     * </p>
     *
     * @param conn Will be null when called by <code>retrieveAddress()</code>, <code>retrieveAddresses()</code> and
     *        <code>getAllAddresses()</code>; Will not be null when called by <code>deleteAddresses()</code>,
     *        <code>modifyAddresses()</code> and <code>handleAssociation</code>.
     * @param ids the ids of the addresses. Possibly empty. Possibly null when called by <code>getAllAddresses()</code>
     * @param operation Represents the meaningful operation performed
     *
     * @return the non-null map contains mappings from <code>Address</code> to its association.
     *
     * @throws PersistenceException if error occurs while accessing database
     * @throws AssociationException if any address is found to be associated with multiple entities
     */
    private Map selectAddresses(Connection conn, long[] ids, String operation)
        throws PersistenceException, AssociationException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String inClause = null;

        if (ids != null) {
            inClause = DAOHelper.convertIds(new StringBuffer(" WHERE address.address_id in "), ids,
                                                "ID of Address");
        }

        try {
            con = (conn != null) ? conn : DAOHelper.createConnection(this.connectionFactory,
                                                              this.connectionName, operation);

            String query = (inClause == null) ? SELECT_ADDRESS : (SELECT_ADDRESS + inClause);
            ps = DAOHelper.prepareStatement(con, query, operation);
            DAOHelper.setUpPreparedStatement(ps, null, false, operation);
            rs = DAOHelper.executeQuery(ps, operation);

            Map addresses = new LinkedHashMap();
            while (rs.next()) {
                this.populateAddress(addresses, rs, null, operation);
            }
            return addresses;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } finally {
            DAOHelper.realeaseJDBCResource(rs, ps, (conn != null) ? null : con);
        }
    }

    /**
     * <p>
     * Populate <code>Address</code> with data from database.
     * </p>
     *
     * @param addresses The map contains mapping from <code>Address</code> to its association
     * @param rs The <code>ResultSet</code>. Will be null when called by <code>searchAddresses()</code>
     * @param crs The <code>CustomResultSet</code>. Will be null when called by <code>selectAddresses()</code>
     * @param operation Represents the meaningful operation performed
     *
     * @throws AssertionError If a <code>Contact</code> is associated more than once
     * @throws PersistenceException If error occurs while accessing database
     * @throws AssociationException if the address is found to be associated with multiple entities
     */
    private void populateAddress(Map addresses, ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException, AssociationException {
        try {
            Address address = new Address();
            address.setId((rs != null) ? rs.getLong("address_id") : crs.getLong("address_id"));
            if (addresses.containsKey(address)) {
                throw new AssociationException(
                    "Given an Address, it can be associated at most once. But address with id '"
                    + address.getId() + "' is associated with multiple entities currently.");
            }
            address.setLine1((rs != null) ? rs.getString("line1") : crs.getString("line1"));
            address.setLine2((rs != null) ? rs.getString("line2") : crs.getString("line2"));
            address.setCity((rs != null) ? rs.getString("city") : crs.getString("city"));
            address.setPostalCode((rs != null) ? rs.getString("zip_code") : crs.getString("zip_code"));

            address.setCountry(this.populateCountry(rs, crs, operation));
            address.setState(this.populateState(rs, crs, operation));
            address.setCreationDate((rs != null) ? rs.getTimestamp("creation_date")
                    : crs.getTimestamp("creation_date"));
            address.setCreationUser((rs != null) ? rs.getString("creation_user")
                    : crs.getString("creation_user"));
            address.setModificationDate((rs != null) ? rs.getTimestamp("modification_date")
                        : crs.getTimestamp("modification_date"));
            address.setModificationUser((rs != null) ? rs.getString("modification_user")
                        : crs.getString("modification_user"));
            AddressType type = this.populateAddressType(rs, crs, operation);
            if (type != null) {
                address.setAddressType(type);
                Address existingAddressAssociation = new Address();
                existingAddressAssociation.setAddressType(type);
                this.populateAddressRelation(existingAddressAssociation, rs, crs, operation);
                long entityId = (rs != null) ? rs.getLong("entity_id") : crs.getLong("entity_id");
                existingAddressAssociation.setId(entityId); //Here use the id field to store the entity id
                addresses.put(address, existingAddressAssociation);
            } else {
                //No association, put null
                addresses.put(address, null);
            }
            //Set changed to false
            address.setChanged(false);

        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }

    /**
     * <p>
     * Populate following columns' values from table <em>address_relation</em>:
     *  <ul>
     *   <li><em>creation_date</em></li>
     *   <li><em>creation_user</em></li>
     *   <li><em>modification_date</em></li>
     *   <li><em>modification_user</em></li>
     *  </ul>
     *  The values of these columns will be populated into given address.
     * </p>
     *
     * @param address <code>Address</code> to populate
     * @param rs The <code>ResultSet</code>. Will be null when invoked by <code>searchAddresses()</code>
     * @param crs The <code>CustomResultSet</code>. Will be null when invoked by <code>selectAddresses()</code>
     *        and <code>handleAssociation()</code>
     * @param operation Represents the meaningful operation performed
     *
     * @throws PersistenceException If error occurs while accessing database
     */
    private void populateAddressRelation(Address address, ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException {
        try {
            address.setCreationDate((rs != null) ? rs.getTimestamp("relation_creation_date")
                                                 : crs.getTimestamp("relation_creation_date"));
            address.setCreationUser((rs != null) ? rs.getString("relation_creation_user")
                                                 : crs.getString("relation_creation_user"));
            address.setModificationDate((rs != null) ? rs.getTimestamp("relation_modification_date")
                                                     : crs.getTimestamp("relation_modification_date"));
            address.setModificationUser((rs != null) ? rs.getString("relation_modification_user")
                                                     : crs.getString("relation_modification_user"));
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }
    /**
     * <p>
     * Check the address_type_id column and convert it to <code>AddressType</code>.
     * </p>
     *
     * @param rs The <code>ResultSet</code>. Will be null when called by <code>searchAddresses()</code>
     * @param crs The <code>CustomResultSet</code>. Will be null when called by <code>selectAddresses()</code>
     * @param operation Represents the operation performing
     *
     * @return <code>AddressType</code> corresponding to type id
     *
     * @throws PersistenceException If error occurs while accessing database or type id is not recognized.
     */
    private AddressType populateAddressType(ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException {
        try {
            Object addressTypeId = (rs != null) ? rs.getObject("address_type_id") : crs.getObject("address_type_id");
            if (addressTypeId == null) {
                return null;
            }
            int id = Integer.parseInt(addressTypeId.toString());
            Enum type = Enum.getEnumByOrdinal(id - 1, AddressType.class);
            if (type instanceof AddressType) {
                return (AddressType) type;
            } else {
                throw new PersistenceException("AddressType Id '" + addressTypeId + "' is not recognized.");
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
     * Populate <code>Country</code> with data from database.
     * </p>
     *
     * @param rs The <code>ResultSet</code>. Will be null when called by <code>searchAddresses()</code>.
     * @param crs The <code>CustomResultSet</code>. Will be null when called by <code>getAllCountries()</code>
     *        and <code>selectAddresses()</code>.
     * @param operation Represents the meaningful operation performed
     *
     * @return <code>Country</code>
     *
     * @throws PersistenceException If error occurs while accessing database
     */
    private Country populateCountry(ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException {
        try {
            Country country = new Country();
            country.setId((rs != null) ? rs.getLong("country_name_id")
                                       : crs.getLong("country_name_id"));
            country.setName((rs != null) ? rs.getString("country_name")
                                         : crs.getString("country_name"));
            country.setCreationDate((rs != null) ? rs.getTimestamp("country_creation_date")
                                                 : crs.getTimestamp("country_creation_date"));
            country.setCreationUser((rs != null) ? rs.getString("country_creation_user")
                                                 : crs.getString("country_creation_user"));
            country.setModificationDate((rs != null) ? rs.getTimestamp("country_modification_date")
                                                     : crs.getTimestamp("country_modification_date"));
            country.setModificationUser((rs != null) ? rs.getString("country_modification_user")
                                                     : crs.getString("country_modification_user"));

            country.setChanged(false);
            return country;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }

    /**
     * <p>
     * Populate <code>State</code> with data from database.
     * </p>
     *
     * @param rs The <code>ResultSet</code>. Will be null when called by <code>searchAddresses()</code>.
     * @param crs The <code>CustomResultSet</code>. Will be null when called by <code>getAllStates()</code>
     *        and <code>selectAddresses()</code>.
     * @param operation Represents the meaningful operation performed
     *
     * @return <code>State</code>
     *
     * @throws PersistenceException If error occurs while accessing database
     */
    private State populateState(ResultSet rs, CustomResultSet crs, String operation)
        throws PersistenceException {
        try {
            State state = new State();
            state.setId((rs != null) ? rs.getLong("state_name_id") : crs.getLong("state_name_id"));
            state.setName((rs != null) ? rs.getString("state_name") : crs.getString("state_name"));
            state.setAbbreviation((rs != null) ? rs.getString("abbreviation")
                                               : crs.getString("abbreviation"));
            state.setCreationDate((rs != null) ? rs.getTimestamp("state_creation_date")
                                               : crs.getTimestamp("state_creation_date"));
            state.setCreationUser((rs != null) ? rs.getString("state_creation_user")
                                               : crs.getString("state_creation_user"));
            state.setModificationDate((rs != null) ? rs.getTimestamp("state_modification_date")
                                                   : crs.getTimestamp("state_modification_date"));
            state.setModificationUser((rs != null) ? rs.getString("state_modification_user")
                                                   : crs.getString("state_modification_user"));

            state.setChanged(false);
            return state;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (InvalidCursorStateException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }

    /**
     * <p>
     * Remove address from persistence by id. The association to entity will also be removed and audited if enabled.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>AddressType</code>, so if the existing address to be removed is not associated with any entity, no audit
     *  will be performed even the doAudit flag is true.
     * </p>
     *
     * @param id the positive id of the address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the id non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the existing address is found to be associated with multiple entities
     */
    public void removeAddress(long id, boolean doAudit) throws PersistenceException, AuditException,
    AssociationException {
        this.deleteAddresses(new long[]{id}, doAudit, false);
    }

    /**
     * <p>
     * Remove the addresses from persistence corresponding to given ids.  The association to entity will also be
     * removed and audited if enabled.
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>AddressType</code>, so if the existing address to be removed is not associated with any entity, no audit
     *  will be performed for it even the doAudit flag is true.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(same id), the audit will be performed only once if enabled.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the addresses
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if any id non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing address is found to be associated with multiple entities
     */
    public void removeAddresses(long[] ids, boolean doAudit) throws PersistenceException, AuditException,
        AssociationException {
        this.deleteAddresses(ids, doAudit, true);
    }

    /**
     * <p>
     * Remove the addresses from persistence corresponding to given ids.
     * </p>
     *
     * <p>
     *  <strong>Called by:</strong>
     *  <code>removeAddress()</code> and <code>removeAddresses()</code>.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the addresses
     * @param doAudit whether this action should be audited
     * @param batch Indicate whether to execute batch deletion or a single deletion.
     *
     * @throws IllegalArgumentException if ids is null or any id non-positive
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing address is found to be associated with multiple entities
     */
    private void deleteAddresses(long[] ids, boolean doAudit, boolean batch)
        throws PersistenceException, AuditException, AssociationException {
        if (Helper.validateIdsArrayWithIAE(ids, "Ids of Address to be removed")) {
            return;
        }

        String operation = "removing Address";
        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);

            Map addressToAssociation = this.selectAddresses(con, ids, operation);
            if (addressToAssociation.size() == 0) {
                return;
            }
            if (doAudit) {

                List existingAddresses = this.extractKeySet(addressToAssociation);

                for (int i = 0; i < ids.length; i++) {
                    Address existingAddress = new Address();
                    existingAddress.setId(ids[i]);
                    int index = existingAddresses.indexOf(existingAddress);
                    if (index > -1) {
                        existingAddress = (Address) existingAddresses.remove(index);
                        if (existingAddress.getAddressType() == null) {
                            //no existing type, no audit
                            continue;
                        }
                        //Note: for current design, there is no way to know the user name who is performing deletion
                        //This may be fixed in future
                        Address addressRelation = (Address) addressToAssociation.get(existingAddress);
                        //Audit the deletion from table "address"
                        AuditHeader auditHeader = this.getAuditRecord(existingAddress, null);
                        DAOHelper.setApplicationAreaAndRelatedId(auditHeader, existingAddress.getAddressType(),
                            addressRelation.getId());
                        DAOHelper.audit(this.auditManager, auditHeader, operation);

                        //Audit the deletion from table "address_relation"
                        this.auditAddressRelation(ids[i], addressRelation.getId(), addressRelation, AuditType.DELETE,
                            addressRelation.getModificationUser(), operation);
                    }
                }
            }

            //==================================Delete from address_relation first.
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
            //====================================End delete from address_relation

            ps2 = DAOHelper.prepareStatement(con, DELETE_ADDRESS, operation);

            for (int i = 0; i < ids.length; i++) {
                Object[] params = new Object[]{new Long(ids[i])};
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
     * Update the <code>Address</code>.  This will only update the address and will not
     * update the association. If the address is not found to be existing in database,
     * <code>EntityNotFoundException</code> will be raised. Also according to design,
     * any address may be associated with zero/one entity at one time, so if the address to be updated
     * is found to be associated with multiple entities, an <code>AssociationException</code>
     * will be raised.
     * </p>
     *
     * <p>
     * The given <code>Address</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of address must be positive.</li>
     *   <li>The line1 of address must be non-null, non-empty.</li>
     *   <li>The line2 of address could be null. If it is not null, then it must be non-empty.</li>
     *   <li>The city of address must be non-null, non-empty.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty.</li>
     *   <li>The creation/modification user must be non-null, non-empty.</li>
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
     *   If the changed status of the given <code>Address</code> is found to be false,
     *   this method will simply return.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of given <code>Address</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>AddressType</code>, so if the existing address to be updated is not associated with any type,
     *  the type of the passed in address will be used, if the type of the passed in address is also null,
     *  then no audit will be performed even the doAudit flag is true.
     * </p>
     *
     * @param address non null address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the address can't be found
     * @throws AssociationException if the existing address is found to be associated with multiple entities
     */
    public void updateAddress(Address address, boolean doAudit)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException,
        AssociationException {
        this.modifyAddresses(new Address[]{address}, doAudit, false);
    }

    /**
     * <p>
     * Update the given array of <code>Address</code> into the database. This will only update the address and will not
     * update the association. If any address is not found to be existing in database,
     * <code>EntityNotFoundException</code> will be raised. Also according to design, any address may be associated with
     * zero/one entity at one time, so if any address to be updated is found to be associated with multiple entities, an
     * <code>AssociationException</code> will be raised.
     * </p>
     *
     * <p>
     * Any given <code>Address</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of address must be positive.</li>
     *   <li>The line1 of address must be non-null, non-empty.</li>
     *   <li>The line2 of address could be null. If it is not null, then it must be non-empty.</li>
     *   <li>The city of address must be non-null, non-empty.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty.</li>
     *   <li>The creation/modification user must be non-null, non-empty.</li>
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
     *   If the changed status of any given <code>Address</code> is found to be false,
     *   it will be ignored and this method will continue to next <code>Address</code>.
     *   </li>
     *   <li>
     *   After successfully updating, the changed status of any given <code>Address</code> will be set as false.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Audit:</strong>
     *  Since the <code>ApplicationArea</code> required by Audit component will be determined based on the
     *  <code>AddressType</code>, so if the existing address to be updated is not associated with any type,
     *  the type of the passed in address will be used, if the type of the passed in address is also null,
     *  then no audit will be performed even the doAudit flag is true.
     * </p>
     *
     * <p>
     *  <strong>Duplication:</strong>
     *  If the passed in array contains duplicate members(objects with same reference), it will only be updated once.
     *  And also the audit will be performed only once if it is enabled.
     * </p>
     *
     * @param addresses non null, possible empty array containing non null addresses
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the given array is null or it contains null member
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the address can't be found
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing address is found to be associated with multiple entities
     */
    public void updateAddresses(Address[] addresses, boolean doAudit)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException,
        AssociationException {
        this.modifyAddresses(addresses, doAudit, true);
    }

    /**
     * <p>
     * Update the given array of <code>Address</code>.
     * </p>
     *
     * <p>
     * Any given <code>Address</code> should have all fields set with valid values except
     * the modification date. The modification date previously set will be ignored and will be set as current date.
     * </p>
     *
     * @param addressArray non null, possible empty array containing non null addresses
     * @param doAudit whether this action should be audited
     * @param batch Indicate whether to execute batch modification or a single modification.
     *
     * @throws IllegalArgumentException if the given array is null or it contains null member
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the address can't be found
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AssociationException if any existing address is found to be associated with multiple entities
     */
    private void modifyAddresses(Address[] addressArray, boolean doAudit, boolean batch)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException
        , AssociationException {
        List addressList = Helper.validateObjectsArrayWithIAE(addressArray, "Addresses to be added");
        if (addressList.size() == 0) {
            return;
        }
        String operation = "updating Address";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            List parameters = new ArrayList();

            for (int i = 0; i < addressList.size(); i++) {
                Address address = (Address) addressList.get(i);

                //Check changed
                if (!address.isChanged()) {
                    addressList.remove(address);
                    i--;
                    continue;
                }

                //Validate given address, and convert it to Object[]
                Object[] params = new Object[ADDRESS_COLUMNS_COUNT];
                this.convertAddress(address, params, true);

                parameters.add(params);
            }

            int size = addressList.size();
            //All addresses are not changed, return
            if (size == 0) {
                return;
            }

            long[] ids = new long[size];
            for (int i = 0; i < size; i++) {
                ids[i] = ((Address) addressList.get(i)).getId();
            }

            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);

            Map addressToAssociation = this.selectAddresses(con, ids, operation);

            if (addressToAssociation.size() == 0) {
                throw new EntityNotFoundException("Nothing found for the given ids of Addresses to be updated: "
                    + DAOHelper.convertIds(new StringBuffer(), ids, "ids"));
            }
            List existingAddresses = this.extractKeySet(addressToAssociation);

            for (int i = 0; i < size; i++) {
                Address address = (Address) addressList.get(i);
                int index = existingAddresses.indexOf(address);
                if (index > -1) {
                    if (doAudit) {
                        //Audit
                        Address existingAddress = (Address) existingAddresses.get(index);
                        AddressType type = null;
                        long relatedId = -1;
                        if (existingAddress.getAddressType() != null) {
                            //use existing type
                            relatedId = ((Address) addressToAssociation.get(existingAddress)).getId();
                            type = existingAddress.getAddressType();
                        } else if (address.getAddressType() != null) {
                            //use given type
                            type = address.getAddressType();
                        } else {
                            //no type, no audit
                            continue;
                        }
                        AuditHeader auditHeader = this.getAuditRecord(existingAddress, address);
                        DAOHelper.setApplicationAreaAndRelatedId(auditHeader, type, relatedId);
                        DAOHelper.audit(this.auditManager, auditHeader, operation);
                    }
                } else {
                    //EntityNotFound
                    throw new EntityNotFoundException("Address with id '" + ids[i] + "' does not exist.");
                }
            }
            ps = DAOHelper.prepareStatement(con, UPDATE_ADDRESS, operation);
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

            this.setChangedFalse(addressArray);
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps, con);
        }
    }

    /**
     * <p>
     * Retrieve all the entries from table <em>address</em> within persistence. According to design, any address
     * may be associated with zero/one entity at one time, so if any address is found to be associated with multiple
     * entities, an <code>AssociationException</code> will be raised. If the address is not associated with any
     * entity, its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Address</code>(if not empty) will have the changed status set as false.
     * </p>
     *
     * @return non null, possible empty array containing all the non null addresses
     *
     * @throws PersistenceException if error occurs while accessing database
     * @throws AssociationException if any address is found to be associated with multiple entities
     */
    public Address[] getAllAddresses() throws PersistenceException, AssociationException {
        return this.listToArray(this.extractKeySet(
            this.selectAddresses(null, null, "retrieving all Addresses")));
    }

    /**
     * <p>
     * Search for the addresses matching criteria represented by given filter. According to design, any address
     * may be associated with zero/one entity at one time, so if any address is found to be associated with multiple
     * entities, an <code>AssociationException</code> will be raised. If the address is not associated with any
     * entity, its type will be left as null.
     * </p>
     *
     * <p>
     *  <strong>Changed status:</strong>
     *  The returned <code>Address</code>(if not empty) will have the changed status set as false.
     * </p>
     *
     * @param filter non null filter
     *
     * @return non null, possible empty array containing found addresses
     *
     * @throws IllegalArgumentException if the filter is null
     * @throws PersistenceException if exception occurs while searching or accessing database
     * @throws AssociationException if any address is found to be associated with multiple entities
     */
    public Address[] searchAddresses(Filter filter) throws PersistenceException, AssociationException {
        Helper.validateNotNullWithIAE(filter, "Filter to search address");

        String operation = "searching Address";

        try {
            CustomResultSet crs = (CustomResultSet) this.searchBundle.search(filter);
            Map addresses = new LinkedHashMap();
            while (crs.next()) {
                this.populateAddress(addresses, null, crs, operation);
            }
            return this.listToArray(this.extractKeySet(addresses));
        } catch (SearchBuilderException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } catch (ClassCastException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        }
    }

    /**
     * <p>
     * Deassociate the given <code>Address</code> with given entity id.
     * <ul>
     *   <li>If given <code>Address</code> is not associated with given entity id currently, nothing will happen.
     *   In other words, if the given <code>Address</code> is found to be associated with some other entity currently,
     *   that association will not be removed.</li>
     *   <li>If the given <code>Address</code> is found to be associated with multiple entities, an
     *   <code>AssociationException</code> will be raised.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     * The given <code>Address</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of address must be positive.</li>
     *   <li>The type of address must be non-null.</li>
     *   <li>The modification user must be non-null, non-empty.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * @param address non null address to be deassociated
     * @param entityId the non null positive id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null, or the entity id is non-positive
     * @throws InvalidPropertyException if the id of address is non-positive, or modification user is invalid,
     *         or its type is null.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the address is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    public void deassociate(Address address, long entityId, boolean doAudit)
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException {
        this.handleAssociation(address, entityId, doAudit, true);
    }

    /**
     * <p>
     * Handle associate/deassociate the given <code>Address</code> with given entity id.
     * The given <code>Address</code> should have id, type set with valid values.
     * </p>
     *
     * <p>
     * If this method is called by <code>associate()</code>, then this method will check the creation/modification
     * user/date. If this method is called by <code>deassociate()</code>, then this method will check the modification
     * user.
     * </p>
     *
     * <p>
     * For associate, if the relation already existed, simply return. If the address is associated with another entity,
     * delete that relation first.
     * For deassociate, if the relation does not exist, simply return.
     * </p>
     *
     * @param address non null address to associate/deassociate
     * @param entityId the non null positive id of entity
     * @param deassociate indicates whether to associate or deassociate the given address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null or the entity id non-positive
     * @throws InvalidPropertyException if the id of address is non-positive, or its type is null.
     *         Or creation/modification user/date are invalid for associate; Or modification user is invalid
     *         for deassociate.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the address is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    private void handleAssociation(Address address, long entityId, boolean doAudit, boolean deassociate)
        throws InvalidPropertyException, PersistenceException, AuditException, AssociationException {
        String usage = "Address to be " + (deassociate ? "deassociated" : "associated");
        //Validate address not null
        Helper.validateNotNullWithIAE(address, usage);

        //Validate entity id
        Helper.validatePositiveWithIAE(entityId, "Related entity id of " + usage);

        //Validate address id
        long addressId = address.getId();
        Helper.validatePositiveWithIPE(addressId, "Id of " + usage);

        //Validate address type
        AddressType addressType = address.getAddressType();
        Helper.validateNotNullWithIPE(addressType, "Type of " + usage);

        Object[] params = null;
        Object[] associateParams = null;
        if (deassociate) {
            //For deassociate, validate modification user
            String modificationUser = address.getModificationUser();
            Helper.validateNotNullWithIPE(modificationUser, "Modification user of " + usage);
        } else {
            //For associate, validate creation/modification user/date
            associateParams = new Object[ADDRESS_RELATION_COLUMNS_COUNT];
            this.convertAddressPartial(address, associateParams, 0, usage, null);
        }

        String operation = deassociate ? "deassociating" : "associating" + " Address relation";
        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            con = DAOHelper.createConnection(connectionFactory, connectionName, operation);
            Map addressToAssociation = this.selectAddresses(con, new long[]{addressId}, operation);

            if (addressToAssociation.size() == 0) {
                throw new AssociationException(usage + " with id '" + addressId + "' does not exist.");
            }
            Address addressAssociation = (Address) addressToAssociation.get(address);

            boolean exist = addressAssociation != null
                && addressAssociation.getAddressType().compareTo(addressType) == 0
                && addressAssociation.getId() == entityId;
            if ((exist && !deassociate) || (!exist && deassociate)) {
                //For deassociate, not existed, simply return
                //For associate, already existed, simply return
                return;
            } else if (deassociate || addressAssociation != null) {
                //For deassociate, existed, delete it
                //For associate, there is another entity associated, delete it
                params = new Object[ADDRESS_RELATION_PK_COLUMNS_COUNT];
                params[0] = new Long(addressId);
                params[1] = new Long(addressAssociation.getAddressType().getId());
                params[2] = new Long(addressAssociation.getId());
                ps1 = DAOHelper.prepareStatement(con, DEASSOCIATE, operation);
                DAOHelper.setUpPreparedStatement(ps1, params, false, operation);
                DAOHelper.executeUpdate(ps1, operation);
                if (doAudit) {
                    this.auditAddressRelation(addressId, addressAssociation.getId(), addressAssociation,
                        AuditType.DELETE, address.getModificationUser(), operation);
                }
            }
            if (!deassociate) {
                associateParams[associateParams.length - 2 - 1] = new Long(addressId);
                associateParams[associateParams.length - 2] = new Long(addressType.getId());
                associateParams[associateParams.length - 1] = new Long(entityId);
                ps2 = DAOHelper.prepareStatement(con, ASSOCIATE, operation);
                DAOHelper.setUpPreparedStatement(ps2, associateParams, false, operation);
                DAOHelper.executeUpdate(ps2, operation);
                if (doAudit) {
                    this.auditAddressRelation(addressId, entityId, address, AuditType.INSERT, address.getCreationUser(),
                        operation);
                }
            }
        } finally {
            DAOHelper.realeaseJDBCResource(null, ps1, null);
            DAOHelper.realeaseJDBCResource(null, ps2, con);
        }
    }

    /**
     * <p>
     * Audit the DELETION/INSERTION on <em>address_relation</em> table.
     * </p>
     *
     * @param addressId The id of <code>Address</code>
     * @param entityId The associated entity id
     * @param addressAssociate The instance of <code>Address</code> represents the address association.
     * @param actionType The action type, INSERT or DELETE
     * @param user The creation user for audit record
     * @param operation Represents current operation performing
     *
     * @throws AuditException If error occurs while audit
     */
    private void auditAddressRelation(long addressId, long entityId, Address addressAssociate, int actionType,
        String user, String operation) throws AuditException {
        boolean insert = actionType == AuditType.INSERT;
        AddressType addressType = addressAssociate.getAddressType();
        List details = new ArrayList();
        this.createAuditRecordPartial(details, insert ? null : addressAssociate, insert ? addressAssociate : null);
        details.add(DAOHelper.getAuditDetail("address_id", insert ? null : addressId + "",
            insert ? addressId + "" : null));
        details.add(DAOHelper.getAuditDetail("address_type_id", insert ? null : addressType.getId() + "",
            insert ? addressType.getId() + "" : null));
        details.add(DAOHelper.getAuditDetail("entity_id", insert ? null : entityId + "",
            insert ? entityId + "" : null));

        AuditHeader auditHeader = DAOHelper.getAuditHeader(actionType, user, "address_relation", addressId);
        DAOHelper.setApplicationAreaAndRelatedId(auditHeader, addressType, entityId);
        auditHeader.setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));
        DAOHelper.audit(this.auditManager, auditHeader, operation);
    }
    /**
     * <p>
     * Associate the given <code>Address</code> with given entity id.
     *  <ul>
     *   <li>If given <code>Address</code> has already been associated with given entity id, nothing will happen.
     *   </li>
     *   <li>If the given <code>Address</code> is found to be associated with some other entity currently, that
     *   association will be removed first and then the new association will be created.</li>
     *   <li>If the given <code>Address</code> is found to be associated with multiple entities, an
     *   <code>AssociationException</code> will be raised.
     *   </li>
     *  </ul>
     * </p>
     *
     * <p>
     * The given <code>Address</code> should not be null and should have fields set properly.
     * </p>
     *
     * <p>
     *  <strong>Validation Details:</strong>
     *  <ul>
     *   <li>The id of address must be positive.</li>
     *   <li>The type of address must be non-null.</li>
     *   <li>The creation/modification user must be non-null, non-empty.</li>
     *   <li>The creation date must not be null, and must not exceed modification date.</li>
     *   <li>The modification date must not be null, and must not exceed current date.</li>
     *  </ul>
     *  If any validation fails, <code>InvalidPropertyException</code> will be raised.
     * </p>
     *
     * @param address non null address to be associated
     * @param entityId the non null positive id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null or the entity id non-positive
     * @throws InvalidPropertyException if the id of address is non-positive, or creation/modification
     *         user/date of the address is invalid, or its type is null.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the address is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    public void associate(Address address, long entityId, boolean doAudit)
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException {
        this.handleAssociation(address, entityId, doAudit, false);
    }

    /**
     * <p>
     * Retrieves all entries from table <em>state_name</em> within persistence.
     * </p>
     *
     * <p>
     * All the <code>State</code>s returned will have changed status set as false.
     * </p>
     *
     * @return non null, possible empty array containing all the non null states
     *
     * @throws PersistenceException if exception occurs while accessing database
     */
    public State[] getAllStates() throws PersistenceException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String operation = "retrieving all states";

        try {
            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);
            ps = DAOHelper.prepareStatement(con, SELECT_STATE, operation);
            DAOHelper.setUpPreparedStatement(ps, null, false, operation);
            rs = DAOHelper.executeQuery(ps, operation);

            List stateList = new ArrayList();

            while (rs.next()) {
                State state = this.populateState(rs, null, operation);
                stateList.add(state);
            }

            return (State[]) stateList.toArray(new State[stateList.size()]);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } finally {
            DAOHelper.realeaseJDBCResource(rs, ps, con);
        }
    }

    /**
     * <p>
     * Retrieves all entries from table <em>country_name</em> within persistence.
     * </p>
     *
     * <p>
     * All the <code>Country</code>s returned will have changed status set as false.
     * </p>
     *
     * @return non null, possible empty array containing all the non null countries
     *
     * @throws PersistenceException if exception occurs while accessing database
     */
    public Country[] getAllCountries() throws PersistenceException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String operation = "retrieving all countries";

        try {
            con = DAOHelper.createConnection(this.connectionFactory, this.connectionName, operation);
            ps = DAOHelper.prepareStatement(con, SELECT_COUNTRY, operation);
            DAOHelper.setUpPreparedStatement(ps, null, false, operation);
            rs = DAOHelper.executeQuery(ps, operation);

            List countryList = new ArrayList();

            while (rs.next()) {
                Country country = this.populateCountry(rs, null, operation);
                countryList.add(country);
            }

            return (Country[]) countryList.toArray(new Country[countryList.size()]);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while " + operation, e);
        } finally {
            DAOHelper.realeaseJDBCResource(rs, ps, con);
        }
    }
}
