/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactDAO;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.Helper;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * <p>
 * The SLSB EJB that handles the actual manager requests.
 * It simply delegates all operations to the <code>ContactDAO</code> it obtains from the Object Factory.
 * </p>
 *
 *  * <p>
 *  <strong>Sample deployment descriptor:</strong>
 *  <pre>
 *    &lt;session&gt;
 *     &lt;ejb-name&gt;ContactEJB&lt;/ejb-name&gt;
 *      &lt;local-home&gt;com.topcoder.timetracker.contact.ejb.ContactHomeLocal&lt;/local-home&gt;
 *      &lt;local&gt;com.topcoder.timetracker.contact.ejb.ContactLocal&lt;/local&gt;
 *      &lt;ejb-class&gt;com.topcoder.timetracker.contact.ejb.ContactBean&lt;/ejb-class&gt;
 *     &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *     &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *     &lt;env-entry&gt;
 *      &lt;env-entry-name&gt;SpecificationNamespace&lt;/env-entry-name&gt;
 *       &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *       &lt;env-entry-value&gt;myNamespace&lt;/env-entry-value&gt;
 *     &lt;/env-entry&gt;
 *     &lt;env-entry&gt;
 *       &lt;env-entry-name&gt;ContactDAOKey&lt;/env-entry-name&gt;
 *       &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *       &lt;env-entry-value&gt;ContactDAO&lt;/env-entry-value&gt;
 *     &lt;/env-entry&gt;
 *     &lt;resource-ref&gt;
 *       &lt;res-ref-name&gt;jdbc/Contact&lt;/res-ref-name&gt;
 *       &lt;res-type&gt;javax.sql.DataSource&lt;/res-type&gt;
 *       &lt;res-auth&gt;Container&lt;/res-auth&gt;
 *       &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;
 *     &lt;/resource-ref&gt;
 *  &lt;/session&gt;
 *  </pre>
 * </p>
 *
 * <p>
 *  <strong>Thread Safety</strong>
 * This object is mutable and thread-safe, as the container handles this.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ContactBean implements SessionBean {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 6335581276342798476L;

    /**
     * <p>
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants
     * to rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * <code>setSessionContext()</code> method invoked by the container.
     * </p>
     */
    private SessionContext sessionContext = null;

    /**
     * <p>
     * Represents the data access object for performing the persistence operations for the contact entries.
     * It is initialized in the <code>ejbCreate()</code> method, and not changed afterwards. There will be one
     * instantiation per one session bean lifetime.
     * </p>
     */
    private ContactDAO contactDAO = null;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public ContactBean() {
        // does nothing
    }

    /**
     * <p>
     * Creates the bean. Initialize the <code>ContactDAO</code> through Object Factory with the environment value
     * configured in EJB deployment descriptor. See class doc for sample deployment descriptor.
     * </p>
     *
     * @throws CreateException If any error occurs while instantiating <code>ContactDAO</code>
     */
    public void ejbCreate() throws CreateException {
        this.contactDAO = (ContactDAO) Helper.getDAO(false);
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
        // does nothing
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
        // does nothing
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
        // does nothing
    }

    /**
     * <p>
     * Sets the session context which will be used to notify the container the transaction
     * should be rolled back.
     * </p>
     *
     * @param ctx session context
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
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
        try {
            this.contactDAO.addContact(contact, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (InvalidPropertyException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (IDGenerationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            this.contactDAO.addContacts(contacts, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (InvalidPropertyException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (IDGenerationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
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
        try {
            return this.contactDAO.retrieveContact(id);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            return this.contactDAO.retrieveContacts(ids);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
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
        try {
            this.contactDAO.removeContact(id, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            this.contactDAO.removeContacts(ids, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
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
        try {
            this.contactDAO.updateContact(contact, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (InvalidPropertyException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (EntityNotFoundException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            this.contactDAO.updateContacts(contacts, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (InvalidPropertyException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (EntityNotFoundException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
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
        try {
            return this.contactDAO.getAllContacts();
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            return this.contactDAO.searchContacts(filter);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
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
        try {
            this.contactDAO.associate(contact, entityId, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (InvalidPropertyException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
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
     * @throws InvalidPropertyException if the id of contact is non-positive, or modification user is invalid,
     *         or its type is null.
     * @throws PersistenceException if exception occurs while accessing database
     * @throws AuditException if exception occurs when audit
     * @throws AssociationException if the contact is found to be associated with multiple entities or itself does not
     *         exist in persistence.
     */
    public void deassociate(Contact contact, long entityId, boolean doAudit)
        throws InvalidPropertyException, AuditException, PersistenceException, AssociationException {
        try {
            this.contactDAO.deassociate(contact, entityId, doAudit);
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (InvalidPropertyException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (PersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AuditException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (AssociationException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }
}
