/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This action is responsible for getting a list of accounting audit records by
 * searching criteria given by AccountingAuditRecordCriteria. It supports
 * pagination.
 * </p>
 * <p>
 * <strong> Thread Safety: </strong> This class is not thread-safe because it's
 * mutable. However, dedicated instance of struts action will be created by the
 * struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AuditHistoryAction extends BaseBillingCostAuditAction {
    /**
     * The Log object used for logging. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized. It is
     * used throughout the class wherever logging is needed.
     */
    private static final Log LOG = LogManager.getLog(AuditHistoryAction.class
            .toString());
    /**
     * The search criteria for the audit history. It has both getter and setter.
     * It is nullable. It does not need to be initialized when the instance is
     * created.
     */
    private AccountingAuditRecordCriteria criteria;
    /**
     * The accounting audit records matching the search criteria. It is accessed
     * through getter and doesn't have a setter. It is nullable. After the
     * struts execution, it won't be null. It does not need to be initialized
     * when the instance is created.
     */
    private PagedResult<AccountingAuditRecord> accountingAuditRecords;

    /**
     * This is the default constructor for the class.
     *
     */
    public AuditHistoryAction() {
    }

    /**
     * <p>
     * Execute the action logic of getting accounting audit records.
     * </p>
     *
     * @throws Exception if any error occurs
     * @return SUCCESS if no error occurs
     */
    public String execute() throws Exception {
        final String methodName = "AuditHistoryAction.execute";
        Helper.logEnterMethod(LOG, methodName);
        try {
            // Perform auditing of this action call
            audit();
            if (criteria == null) {
                criteria = new AccountingAuditRecordCriteria();
            }
            // Perform the search
            accountingAuditRecords = getBillingCostAuditService()
                    .getAccoutingAuditHistory(criteria, getPageNumber(),
                            getPageSize());
        } catch (Exception e) {
            throw Helper.logAndThrow(LOG, methodName, e);
        }
        Helper.logExitMethod(LOG, methodName, SUCCESS);
        return SUCCESS;
    }

    /**
     * Getter for the accounting audit records.
     *
     * @return the accounting audit records
     */
    public PagedResult<AccountingAuditRecord> getAccountingAuditRecords() {
        return accountingAuditRecords;
    }

    /**
     * Getter for the search criteria for the audit history.
     *
     * @return The search criteria for the audit history
     */
    public AccountingAuditRecordCriteria getCriteria() {
        return criteria;
    }

    /**
     * Setter for the search criteria for the audit history.
     *
     * @param criteria The search criteria for the audit history
     */
    public void setCriteria(AccountingAuditRecordCriteria criteria) {
        this.criteria = criteria;
    }
}
