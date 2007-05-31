/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

import com.topcoder.util.config.ConfigManagerException;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.NamingException;

/**
 * <p>
 * This is a Stateless <code>SessionBean</code> handles the actual manager requests for expense entry operations, as
 * defined by the corresponding local interface <code>ExpenseEntryLocal</code>. As such, it extends
 * <code>BasicExpenseEntryBean</code> and implements the batch methods, but with a wrinkle. All atomic batch
 * operations simply delegate all operations to the <code>ExpenseEntryDAO</code> instance it obtained from the
 * <code>ObjectFactory</code>, but all non-atomic calls are handled via the configured basic ExpenseEntry EJB, so the
 * later can perform each operation atomically without involving the transaction that this instance is part of. For
 * the duration of the calls, the local transaction is suspended and does not take part in the calls to the basic
 * ExpenseEntry EJB.
 * </p>
 *
 * <p>
 * Transactions for this bean are handled by the EJB Container. It is expected that the transaction level declared in
 * the deployment descriptor for this class will be REQUIRED. Transactional level should be set to be NotSupported for
 * the four non-atomical batch method, which in turn forces the individual calls from them to spawn their own separate
 * transactions that can be rolled back individially if their fail, thus ensuring we have container-managed non-atomic
 * batch operations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This object is mutable and thread-safe, as the container handles this.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseEntryBean extends BasicExpenseEntryBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -4218882682990558608L;

    /**
     * <p>
     * Represents the namespace used to retrieve the jndi_context property value.
     * </p>
     */
    private static final String NAMESPACE = ExpenseEntryBean.class.getName();

    /**
     * <p>
     * Represents the property name to retrieve the jndi_context value from the confit manager.
     * </p>
     */
    private static final String JNDI_CONTEXT_PROPERTY = "jndi_context";

    /**
     * <p>
     * Represents the basic ExpenseEntryLocal instance used to perform non-atomic batch operations. All such batch
     * operations will delegate to the equivalent single entity operations, with the caveat that the
     * basicExpenseEntryLocal instance will perform these single operations in its own transaction space and will take
     * care of rolling back each on its own, so that the transaction of this instance is not affected.
     * </p>
     *
     * <p>
     * It is initialized in the ejbCreate method, and not changed afterwards. There will be one instantiation per one
     * session bean lifetime. It will not be null after being set. Should an error occur while instantiating, the
     * container will discard this bean and adapt with another one. Either way, as far as the user is concerned, it
     * will not be null when the business methods are called.
     * </p>
     */
    private BasicExpenseEntryLocal basicExpenseEntryLocal = null;

    /**
     * Empty constructor.
     */
    public ExpenseEntryBean() {
    }

    /**
     * <p>
     * Creates the bean. Initializes the persistence instance to be used by this component.
     * </p>
     *
     * @throws CreateException If any error occurs while instantiating
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();

        try {
            //if context name is not specified, uses default context
            String contextName = ExpenseEntryHelper.getStringPropertyValue(NAMESPACE, JNDI_CONTEXT_PROPERTY, false);
            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            BasicExpenseEntryLocalHome home = (BasicExpenseEntryLocalHome)
                context.lookup("java:comp/env/ejb/BasicExpenseEntry");
            this.basicExpenseEntryLocal = home.create();
        } catch (ConfigurationException e) {
            throw new CreateException("JNDIUtil is not configured properly. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        } catch (ConfigManagerException e) {
            throw new CreateException("JNDIUtil is not configured properly. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        } catch (NamingException e) {
            throw new CreateException("failed to create BasicExpenseEntryLocal via JNDIUtil. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        } catch (CreateException e) {
            throw new CreateException("failed to obtain BasicExpenseEntryLocal. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        } catch (ClassCastException e) {
            throw new CreateException("Fails to create the BasicExpenseEntryLocalHome. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        }
    }

    /**
     * <p>
     * Adds atomically set of entries to the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entries the entries to add.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void addEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        try {
            this.getDAO().addEntries(entries, auditFlag);
        } catch (PersistenceException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        } catch (InsufficientDataException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Adds a set of entries to the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire addition will fail if a single expense entry addition fails.
     * </p>
     *
     * <p>
     * If the addition is non-atomic then it means each expense entry is added individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the entries to add.
     * @param isAtomic whether the operation should be atomic or not.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return the entries that failed to be added in non atomic mode (null in atomic mode).
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.(in atomic mode only)
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(entries, "entries");
        ExpenseEntryHelper.validateObjectArray(entries, "entries", 1);

        if (isAtomic) {
            addEntries(entries, auditFlag);

            return null;
        } else {
            List errors = new ArrayList();

            for (int i = 0; i < entries.length; i++) {
                try {
                    if (!basicExpenseEntryLocal.addEntry(entries[i], auditFlag)) {
                        errors.add(entries[i]);
                    }
                } catch (Exception e) {
                    errors.add(entries[i]);
                }
            }

            return (ExpenseEntry[]) errors.toArray(new ExpenseEntry[errors.size()]);
        }
    }

    /**
     * <p>
     * Updates atomically a set of entries in the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entries the entries to update.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void updateEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        try {
            this.getDAO().updateEntries(entries, auditFlag);
        } catch (PersistenceException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        } catch (InsufficientDataException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates a set of entries in the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire update will fail if a single expense entry update fails.
     * </p>
     *
     * <p>
     * If the update is non-atomic then it means each expense entry is updated individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entries the ids of the entries to update
     * @param isAtomic whether the operation should be atomic or not
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be updated in non atomic mode (null in atomic mode)
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.(in atomic mode only)
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(entries, "entries");
        ExpenseEntryHelper.validateObjectArray(entries, "entries", 1);

        if (isAtomic) {
            updateEntries(entries, auditFlag);

            return null;
        } else {
            List errors = new ArrayList();

            for (int i = 0; i < entries.length; i++) {
                try {
                    if (!basicExpenseEntryLocal.updateEntry(entries[i], auditFlag)) {
                        errors.add(entries[i]);
                    }
                } catch (Exception e) {
                    errors.add(entries[i]);
                }
            }

            return (ExpenseEntry[]) errors.toArray(new ExpenseEntry[errors.size()]);
        }
    }

    /**
     * <p>
     * Deletes atomically a set of entries from the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * @param entryIds the ids of the entries to delete.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception)
     */
    public void deleteEntries(long[] entryIds, boolean auditFlag) throws PersistenceException {
        try {
            this.getDAO().deleteEntries(entryIds, auditFlag);
        } catch (PersistenceException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Deletes a set of entries from the database. Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * If the deletion is atomic then it means that entire retrieval will fail if a single expense entry deletion
     * fails.
     * </p>
     *
     * <p>
     * If the deletion is non-atomic then it means each expense entry is deleted individually. If it fails, that won't
     * affect the others. A list with the failed ids is returned to the user (empty if no error occurs).
     * </p>
     *
     * @param entryIds the ids of the entries to delete
     * @param isAtomic whether the operation should be atomic or not
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be deleted in non atomic mode (null in atomic mode)
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public long[] deleteEntries(long[] entryIds, boolean isAtomic, boolean auditFlag) throws PersistenceException {
        ExpenseEntryHelper.validateNotNull(entryIds, "entryIds");

        if (entryIds.length == 0) {
            throw new IllegalArgumentException("EntryIds should not be empty array.");
        }

        if (isAtomic) {
            deleteEntries(entryIds, auditFlag);

            return null;
        } else {
            List errors = new ArrayList();

            for (int i = 0; i < entryIds.length; i++) {
                try {
                    if (!basicExpenseEntryLocal.deleteEntry(entryIds[i], auditFlag)) {
                        errors.add(new Long(entryIds[i]));
                    }
                } catch (Exception e) {
                    errors.add(new Long(entryIds[i]));
                }
            }

            long[] ret = new long[errors.size()];

            for (int i = 0; i < errors.size(); i++) {
                ret[i] = ((Long) errors.get(i)).longValue();
            }

            return ret;
        }
    }

    /**
     * <p>
     * Retrieves atomically a set of entries with given ids from the database.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     *
     * @return the entries that were retrieved in both modes.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds) throws PersistenceException {
        try {
            return this.getDAO().retrieveEntries(entryIds);
        } catch (PersistenceException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * <p>
     * If the retrieval is atomic then it means that entire retrieval will fail if a single expense entry retrieval
     * fails.
     * </p>
     *
     * <p>
     * If the retrieval is non-atomic then it means each expense entry is retrieved individually. If it fails that
     * won't affect the others. The potentially partial list of results will be returned.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that were retrieved in either mode, or empty if none found.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).(in
     *         atomic mode only)
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds, boolean isAtomic) throws PersistenceException {
        ExpenseEntryHelper.validateNotNull(entryIds, "entryIds");

        if (entryIds.length == 0) {
            throw new IllegalArgumentException("EntryIds should not be empty array.");
        }

        if (isAtomic) {
            return retrieveEntries(entryIds);
        } else {
            List ret = new ArrayList();

            for (int i = 0; i < entryIds.length; i++) {
                try {
                    ExpenseEntry entry = basicExpenseEntryLocal.retrieveEntry(entryIds[i]);
                    if (entry != null) {
                        ret.add(entry);
                    }
                } catch (Exception e) {
                    // ignore
                }
            }

            return (ExpenseEntry[]) ret.toArray(new ExpenseEntry[ret.size()]);
        }
    }
}
