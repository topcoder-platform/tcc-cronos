/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.ejb.pacts.assignmentdocuments.AssignmentDocument;
import com.topcoder.web.ejb.pacts.assignmentdocuments.AssignmentDocumentType;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;
import com.topcoder.web.tc.controller.legacy.pacts.common.PactsConstants;

/**
 * <p>
 * This action gets the assignment document history records of the current user. It can get the full list of history
 * records or only pending records. It can get only a range of the history records sorted on certain column. It uses
 * DataInterfaceBean to retrieve the data.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class AssignmentDocumentHistoryAction extends BaseRangeAction {

    /**
     * A private variable representing column number of submission title.
     */
    private static final int SUBMISSION_TITLE_COL = 1;

    /**
     * A private variable representing column number of time left.
     */
    private static final int TIME_LEFT_COL = 2;

    /**
     * A private variable representing column number of status.
     */
    private static final int STATUS_COL = 3;

    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = 2409897824204005975L;
    /**
     * <p>
     * The Log object used for logging.It's a constant. So it can only be that constant value It is final and won't
     * change once it is initialized as part of variable declaration to:
     * LogManager.getLog(AssignmentDocumentHistoryAction.class.toString()).It is used throughout the class wherever
     * logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(AssignmentDocumentHistoryAction.class.toString());
    /**
     * <p>
     * The retrieved assignment document history. It is accessed through getter and doesn't have a setter. It can be
     * null or empty. After the struts execution, it won't be null. The contained values can't be null. It does not need
     * to be initialized when the instance is created.It is used in execute(), getAssignmentDocuments().
     * </p>
     */
    private List < AssignmentDocument > assignmentDocuments;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public AssignmentDocumentHistoryAction() {
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
    private static void sortResult(List < AssignmentDocument > result, int sortColumn, boolean sortAscending) {
        switch (sortColumn) {
        case SUBMISSION_TITLE_COL:
            Collections.sort(result, new Comparator < AssignmentDocument >() {
                public int compare(AssignmentDocument arg0, AssignmentDocument arg1) {
                    return arg0.getSubmissionTitle().toLowerCase().compareTo(arg1.getSubmissionTitle().toLowerCase());
                }
            });
            break;
        case TIME_LEFT_COL:
            Collections.sort(result, new Comparator < AssignmentDocument >() {
                public int compare(AssignmentDocument arg0, AssignmentDocument arg1) {
                    return arg0.getDaysLeftToExpire().compareTo(arg1.getDaysLeftToExpire());
                }
            });
            break;
        case STATUS_COL:
            Collections.sort(result, new Comparator < AssignmentDocument >() {
                public int compare(AssignmentDocument arg0, AssignmentDocument arg1) {
                    return arg0.getStatus().getDescription().toLowerCase().compareTo(
                            arg1.getStatus().getDescription().toLowerCase());
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
     * Execute the action logic of getting assignment document history.
     * </p>
     * @return SUCCESS if no error occurs
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs
     */
    public String execute() throws UserDocumentationManagementActionsException {
        LoggingWrapperUtility.logEntrance(LOG, "AssignmentDocumentHistoryAction#execute()", null, null);
        this.audit();
        DataInterfaceBean dib = new DataInterfaceBean();
        this.setDefaultStartAndEndRankValues(PactsConstants.ASSIGNMENT_DOCUMENT_HISTORY_PAGE_SIZE);
        this.setDefaultSortColumnValue();
        try {
            assignmentDocuments = dib.getAssignmentDocumentByUserId(this.getUserId(),
                    AssignmentDocumentType.COMPONENT_COMPETITION_TYPE_ID, !isFullList());
        } catch (RemoteException e) {
            LoggingWrapperUtility.logException(LOG, "AssignmentDocumentHistoryAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error getting assignment documents history", e);
        }
        sortResult(assignmentDocuments, getSortColumn(), isAscending());
        int endIndex = this.getEndRank();
        if (endIndex >= assignmentDocuments.size()) {
            endIndex = assignmentDocuments.size();
        }
        if (assignmentDocuments.size() > 0) {
            ValidationUtility.checkPositive(getStartRank(), "startRank", UserDocumentationManagementActionsException.class);
            assignmentDocuments = assignmentDocuments.subList(getStartRank() - 1, endIndex);
        }
        LoggingWrapperUtility.logExit(LOG, "AssignmentDocumentHistoryAction#execute()", new Object[] { SUCCESS });
        return SUCCESS;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the list of AssignmentDocuments retrieved
     */
    public List < AssignmentDocument > getAssignmentDocuments() {
        return assignmentDocuments;
    }
}
