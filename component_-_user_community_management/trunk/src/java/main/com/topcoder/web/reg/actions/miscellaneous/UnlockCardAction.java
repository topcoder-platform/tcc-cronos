/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.shared.util.ApplicationServer;
import com.topcoder.web.common.NavigationException;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;

/**
 * This is a Struts action that is responsible for unlocking TopCoder member
 * card. The action should be configured to refer to JSP page with card
 * instructions in case of success.
 * <p>
 * Thread Safety: This class is mutable and not thread safe. But it will be used
 * in thread safe manner by Struts. It's assumed that request scope will be set
 * up for all Struts actions in Spring configuration, thus actions will be
 * accessed from a single thread only. This action uses TransactionManager to
 * perform all database changes in a single transaction. The injected AuditDAO
 * instance is expected to be thread safe.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class UnlockCardAction extends BaseRatedUserCommunityManagementAction implements ServletRequestAware {
    /**
     * <p>
     * The serial version uid.
     * </p>
     */
    private static final long serialVersionUID = -6473730177593717093L;

    /**
     * The value indicating whether user's member card is unlocked. It is
     * initialized in execute() and is used for passing data from this Struts
     * action to JSP. Has a getter.
     */
    private boolean cardUnlocked;

    /**
     * The audit DAO used for auditing member card unlock operation. Is expected
     * to be initialized by Spring via setter injection. Cannot be null after
     * initialization (validation is performed in @PostConstruct method
     * checkInit()). Is used in execute().
     */
    private AuditDAO auditDAO;

    /**
     * The HTTP servlet request associated with this action. Is initialized by
     * Struts via setter. Can be any value. Is used in execute().
     */
    private HttpServletRequest request;

    /**
     * Creates an instance of UnlockCardAction.
     */
    public UnlockCardAction() {
    }

    /**
     * Checks whether this action was initialized by Spring properly.
     *
     * @throws UserCommunityManagementInitializationException
     *             if the class was not initialized properly (e.g. when required
     *             properly is not specified)
     */
    @Override
    @PostConstruct
    protected void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(auditDAO, "auditDAO",
            UserCommunityManagementInitializationException.class);

    }

    /**
     * Executes the Struts action. This method just checks whether the user is
     * rated, and if not throws an exception. Then it checks whether the member
     * card is already unlocked, and if not unlocks it using CardHelper. Then
     * unlocking operation is audited.
     *
     * @throws NavigationException
     *             if the user is not rated
     * @throws TCWebException
     *             if any other error occurred
     * @return SUCCESS to indicate that the operation was successful
     */
    @Override
    public String execute() throws TCWebException {
        final String methodSignature = "UnlockCardAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        TransactionManager transactionManager = null;
        try {
            // Check whether the user is rated
            if (!isRated()) {
                throw new NavigationException("Sorry you have not been rated in competition.");
            }
            long userId = getUserId();
            // Check whether the member card is unlocked:
            boolean unlocked = CardHelper.isUnlocked(userId);
            if (!unlocked) {
                try {
                    // Create initial context:
                    InitialContext context = new InitialContext();
                    // Lookup transaction manager:
                    transactionManager = (TransactionManager) context.lookup(ApplicationServer.TRANS_MANAGER);
                    // Begin transaction
                    transactionManager.begin();
                    // Unlock the member card:
                    CardHelper.setUnlocked(userId, true);

                    // Get user handle:
                    String handle = getUserHandle();
                    // Get IP address of the user from request:
                    String ipAddress = request.getRemoteAddr();

                    // Create audit record instance:
                    AuditRecord auditRecord = new AuditRecord();
                    // Set operation type to the record:
                    auditRecord.setOperationType("unlock card");
                    // Set user handle to the record:
                    auditRecord.setHandle(handle);
                    // Set IP address to the record:
                    auditRecord.setIpAddress(ipAddress);
                    // Set timestamp to the record:
                    auditRecord.setTimestamp(new Date());
                    // Save audit record:
                    auditDAO.audit(auditRecord);
                    // Commit the transaction:
                    transactionManager.commit();
                } catch (ClassCastException e) {
                    throw new TCWebException("The transaction manager is invalid", e);
                } catch (SecurityException e) {
                    throw new TCWebException("SecurityException error occurs", e);
                } catch (IllegalStateException e) {
                    throw new TCWebException("IllegalStateException error occurs", e);
                } catch (RollbackException e) {
                    throw new TCWebException("RollbackException error occurs", e);
                } catch (HeuristicMixedException e) {
                    throw new TCWebException("HeuristicMixedException error occurs", e);
                } catch (HeuristicRollbackException e) {
                    throw new TCWebException("HeuristicRollbackException error occurs", e);
                } catch (SystemException e) {
                    throw new TCWebException("SystemException error occurs", e);
                } catch (NamingException e) {
                    throw new TCWebException("NamingException error occurs", e);
                } catch (NotSupportedException e) {
                    throw new TCWebException("NotSupportedException error occurs", e);
                }
            }
            cardUnlocked = true;
        } catch (TCWebException e) {
            // Rollback the transaction
            try {
                if (transactionManager != null && transactionManager.getStatus() == Status.STATUS_ACTIVE) {
                    transactionManager.rollback();
                }
            } catch (IllegalStateException e1) {
                // ignore
            } catch (SecurityException e1) {
                // ignore
            } catch (SystemException e1) {
                // ignore
            }
            // for logging
            throw LoggingWrapperUtility.logException(getLog(), methodSignature, e);
        }
        LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{cardUnlocked});
        return SUCCESS;
    }

    /**
     * Sets servlet request for this action.
     *
     * @param request
     *            the HTTP servlet request
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Sets the audit DAO used for auditing member card unlock operation.
     *
     * @param auditDAO
     *            the audit DAO used for auditing member card unlock operation
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * Retrieves the value indicating whether user's member card is unlocked.
     *
     * @return the value indicating whether user's member card is unlocked
     */
    public boolean isCardUnlocked() {
        return cardUnlocked;
    }
}
