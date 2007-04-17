/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This interface specifies the contract for implementations of a Address DAOs. An <code>AddressDAO</code> is
 * responsible for accessing the database.
 * </p>
 *
 * <p>
 *  <strong>Implementation Notes:</strong>
 *  The implementation of this interface will be created either by the <code>ObjectFactory</code> in the constructor
 *  of the <code>AddressBean</code>. The implementation will be called in the corresponding methods in the
 *  <code>AddressBean</code>.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  Implementations of this interface should be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public interface AddressDAO {

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
     *   <li>The line1 of address must be non-null, non-empty, with length &lt;=100.</li>
     *   <li>The line2 of address could be null.
     *       If it is not null, then it must be non-empty, with length &lt;=100.</li>
     *   <li>The city of address must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty, with length &lt;=10.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
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
    void addAddress(Address address, boolean doAudit)
        throws InvalidPropertyException, IDGenerationException, PersistenceException, AuditException;

    /**
     *<p>
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
     *   <li>The line1 of address must be non-null, non-empty, with length &lt;=100.</li>
     *   <li>The line2 of address could be null.
     *       If it is not null, then it must be non-empty, with length &lt;=100.</li>
     *   <li>The city of address must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty, with length &lt;=10.</li>
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
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
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws PersistenceException if exception occurs while accessing database
     * @throws BatchOperationException if any operation in the batch is failed
     * @throws AuditException if exception occurs when audit
     */
    void addAddresses(Address[] addresses, boolean doAudit)
        throws InvalidPropertyException, IDGenerationException, AuditException, PersistenceException;

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
     *   <li>The line1 of address must be non-null, non-empty, with length &lt;=100.</li>
     *   <li>The line2 of address could be null.
     *       If it is not null, then it must be non-empty, with length &lt;=100.</li>
     *   <li>The city of address must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty, with length &lt;=10.</li>
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
    void updateAddress(Address address, boolean doAudit)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException,
        AssociationException;

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
     *   <li>The line1 of address must be non-null, non-empty, with length &lt;=100.</li>
     *   <li>The line2 of address could be null.
     *       If it is not null, then it must be non-empty, with length &lt;=100.</li>
     *   <li>The city of address must be non-null, non-empty, with length &lt;=30.</li>
     *   <li>The state of address must be non-null, must be with positive state id.</li>
     *   <li>The country of address must be non-null, must be with positive country id.</li>
     *   <li>The postal code of address must be non-null, non-empty, with length &lt;=10.</li>
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
    void updateAddresses(Address[] addresses, boolean doAudit)
        throws InvalidPropertyException, AuditException, EntityNotFoundException, PersistenceException
        , AssociationException;

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
    Address retrieveAddress(long id) throws PersistenceException, AssociationException;

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
    Address[] retrieveAddresses(long[] ids) throws PersistenceException, AssociationException;

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
    Address[] getAllAddresses() throws PersistenceException, AssociationException;

    /**
     * <p>
     * Remove address from persistence by id. The association to entity will also be removed.
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
    void removeAddress(long id, boolean doAudit) throws PersistenceException, AuditException, AssociationException;

    /**
     * <p>
     * Remove the addresses from persistence corresponding to given ids.  The association to entity will also be
     * removed.
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
    void removeAddresses(long[] ids, boolean doAudit) throws PersistenceException, AuditException, AssociationException;

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
    Address[] searchAddresses(Filter filter) throws PersistenceException, AssociationException;

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
     *   <li>The creation/modification user must be non-null, non-empty, with length &lt;=64.</li>
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
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException;

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
     *   <li>The modification user must be non-null, non-empty, with length &lt;=64.</li>
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
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException;

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
    State[] getAllStates() throws PersistenceException;

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
    Country[] getAllCountries() throws PersistenceException;
}
