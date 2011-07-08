/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.ejb.pacts.BasePayment;
import com.topcoder.web.ejb.pacts.payments.PaymentStatusFactory;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;
import com.topcoder.web.tc.controller.legacy.pacts.common.PactsConstants;

/**
 * <p>
 * This action gets the payment history records of the current user. It can get the full list of history records or only
 * pending records. It can get only a range of the history records sorted on certain column. It uses DataInterfaceBean
 * to retrieve the data.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class PaymentHistoryAction extends BaseRangeAction {
    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = -4944887528856472591L;

    /**
     * Variable for description column number.
     */
    private static final int DESCRIPTION_COL = 1;

    /**
     * Variable for type column number.
     */
    private static final int TYPE_COL = 2;

    /**
     * Variable for due date column number.
     */
    private static final int DUE_DATE_COL = 3;

    /**
     * Variable for net payment column number.
     */
    private static final int NET_PAYMENT_COL = 4;

    /**
     * Variable for status column number.
     */
    private static final int STATUS_COL = 5;

    /**
     * Variable for paid date column number.
     */
    private static final int PAID_DATE_COL = 6;

    /**
     * <p>
     * The Log object used for logging. It is final and won't change once it is initialized as part of variable
     * declaration to: LogManager.getLog(PaymentHistoryAction.class.toString()).It is used throughout the class wherever
     * logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(PaymentHistoryAction.class.toString());

    /**
     * <p>
     * The id of the row of payment_type_lu that represents the Problem Payment. It is set through setter and doesn't
     * have a getter.It must be non-negative.It can be changed after it is initialized as part of variable
     * declaration.It is used in execute(), setProblemPaymentTypeId(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private long problemPaymentTypeId = 3;
    /**
     * <p>
     * The id of the row of payment_type_lu that represents the Charity Payment. It is set through setter and doesn't
     * have a getter.It must be non-negative.It can be changed after it is initialized as part of variable
     * declaration.It is used in execute(), setCharityPaymentTypeId(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private long charityPaymentTypeId = 5;
    /**
     * <p>
     * The retrieved payment history. It is accessed through getter and doesn't have a setter.It can be null or empty.
     * After the struts execution, it won't be null. The contained values can't be null.It does not need to be
     * initialized when the instance is created. Usage: It is used in execute(), getPayments().
     * </p>
     */
    private List < BasePayment > payments;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public PaymentHistoryAction() {
        // do nothing
    }

    /**
     * <p>
     * Sort the result.
     * </p>
     * @param result
     *            the result to sort
     * @param sortColumn
     *            the column to sort
     * @param sortAscending
     *            whether the sorting is in ascending order
     */
    private static void sortResult(List < BasePayment > result, int sortColumn, boolean sortAscending) {
        switch (sortColumn) {
        case DESCRIPTION_COL:
            Collections.sort(result, new Comparator < BasePayment >() {
                public int compare(BasePayment arg0, BasePayment arg1) {
                    return arg0.getDescription().compareTo(arg1.getDescription());
                }
            });
            break;
        case TYPE_COL:
            Collections.sort(result, new Comparator < BasePayment >() {
                public int compare(BasePayment arg0, BasePayment arg1) {
                    return arg0.getPaymentTypeDesc().compareTo(arg1.getPaymentTypeDesc());
                }
            });
            break;
        case DUE_DATE_COL:
            Collections.sort(result, new Comparator < BasePayment >() {
                public int compare(BasePayment arg0, BasePayment arg1) {
                    Date date0 = arg0.getDueDate();
                    Date date1 = arg1.getDueDate();
                    if (date0 == null && date1 == null) {
                        return 0;
                    }
                    if (date0 == null && date1 != null) {
                        return -1;
                    }
                    if (date0 != null && date1 == null) {
                        return 1;
                    }
                    return date0.compareTo(date1);
                }
            });
            break;
        case NET_PAYMENT_COL:
            Collections.sort(result, new Comparator < BasePayment >() {
                public int compare(BasePayment arg0, BasePayment arg1) {
                    if (arg0.getNetAmount() == arg1.getNetAmount()) {
                        return 0;
                    }

                    return arg0.getNetAmount() < arg1.getNetAmount() ? -1 : 1;
                }
            });
            break;
        case STATUS_COL:
            Collections.sort(result, new Comparator < BasePayment >() {
                public int compare(BasePayment arg0, BasePayment arg1) {
                    return arg0.getCurrentStatus().getDesc().compareTo(arg1.getCurrentStatus().getDesc());
                }
            });
            break;
        case PAID_DATE_COL:
            Collections.sort(result, new Comparator < BasePayment >() {
                public int compare(BasePayment arg0, BasePayment arg1) {
                    Date date0 = arg0.getPaidDate();
                    Date date1 = arg1.getPaidDate();
                    if (date0 == null && date1 == null) {
                        return 0;
                    }
                    if (date0 == null && date1 != null) {
                        return -1;
                    }
                    if (date0 != null && date1 == null) {
                        return 1;
                    }
                    return date0.compareTo(date1);
                }
            });
            break;
        }
        if (!sortAscending) {
            Collections.reverse(result);
        }
    }

    /**
     * <p>
     * Execute the action logic of getting payment history.
     * </p>
     * @return SUCCESS if no error occurs
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs
     */
    public String execute() throws UserDocumentationManagementActionsException {
        LoggingWrapperUtility.logEntrance(LOG, "PaymentHistoryAction#execute()", null, null);
        this.audit();
        DataInterfaceBean dib = new DataInterfaceBean();
        this.setDefaultStartAndEndRankValues(PactsConstants.PAYMENT_HISTORY_PAGE_SIZE);
        this.setDefaultSortColumnValue();
        Map < Object, Object > criteria = new HashMap < Object, Object >();
        criteria.put(PactsConstants.USER_ID, String.valueOf(this.getUserId()));
        try {
            payments = dib.findCoderPayments(criteria);
            List < BasePayment > paymentsToRemove = new ArrayList < BasePayment >();
            for (BasePayment basePayment : payments) {
                if (basePayment.getPaymentType() == problemPaymentTypeId
                        || basePayment.getPaymentType() == charityPaymentTypeId) {
                    paymentsToRemove.add(basePayment);
                } else {
                    if (!isFullList()) {
                        if (basePayment.getCurrentStatus().equals(
                                PaymentStatusFactory
                                        .createStatus(PaymentStatusFactory.PaymentStatus.CANCELLED_PAYMENT_STATUS))
                                || basePayment
                                        .getCurrentStatus()
                                        .equals(
                                                PaymentStatusFactory
                                                        .createStatus(PaymentStatusFactory.PaymentStatus.DELETED_PAYMENT_STATUS))
                                || basePayment
                                        .getCurrentStatus()
                                        .equals(
                                                PaymentStatusFactory
                                                        .createStatus(PaymentStatusFactory.PaymentStatus.EXPIRED_PAYMENT_STATUS))
                                || basePayment.getCurrentStatus().equals(
                                        PaymentStatusFactory
                                                .createStatus(PaymentStatusFactory.PaymentStatus.PAID_PAYMENT_STATUS))) {
                            paymentsToRemove.add(basePayment);
                        }
                    }
                }
            }

            payments.removeAll(paymentsToRemove);
            sortResult(payments, getSortColumn(), isAscending());
            int endIndex = this.getEndRank();
            if (endIndex >= payments.size()) {
                endIndex = payments.size();
            }
            if (payments.size() > 0) {
                payments.subList(getStartRank() - 1, endIndex);
            }

        } catch (RemoteException e) {
            LoggingWrapperUtility.logException(LOG, "PaymentHistoryAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error communicating with EJB", e);
        }

        LoggingWrapperUtility.logExit(LOG, "PaymentHistoryAction#execute()", new Object[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the list of payments retrieved
     */
    public List < BasePayment > getPayments() {
        return payments;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param problemPaymentTypeId
     *            The id of the row of payment_type_lu that represents the Problem Payment.
     */
    public void setProblemPaymentTypeId(long problemPaymentTypeId) {
        this.problemPaymentTypeId = problemPaymentTypeId;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param charityPaymentTypeId
     *            The id of the row of payment_type_lu that represents the Charity Payment.
     */
    public void setCharityPaymentTypeId(long charityPaymentTypeId) {
        this.charityPaymentTypeId = charityPaymentTypeId;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the injected
     * values are valid.
     * </p>
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNegative(charityPaymentTypeId, "charityPaymentTypeId",
                UserDocumentationManagementActionsConfigurationException.class);
        ValidationUtility.checkNotNegative(problemPaymentTypeId, "problemPaymentTypeId",
                UserDocumentationManagementActionsConfigurationException.class);
    }
}
