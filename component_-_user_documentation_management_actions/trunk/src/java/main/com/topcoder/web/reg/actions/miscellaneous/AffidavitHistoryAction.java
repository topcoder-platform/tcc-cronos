/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.rmi.RemoteException;
import java.sql.SQLException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;
import com.topcoder.web.tc.controller.legacy.pacts.common.PactsConstants;

/**
 * <p>
 * This action gets the affidavit history records of the current user. It can get the full list of history records or
 * only pending records. It can get only a range of the history records sorted on certain column. It uses
 * DataInterfaceBean to retrieve the data.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author makanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class AffidavitHistoryAction extends BaseRangeAction {
    /**
     * <p>
     * Generated serial version id.
     * </p>
     */
    private static final long serialVersionUID = 7136745317711592824L;
    /**
     * <p>
     * The Log object used for logging.It's a constant. So it can only be that constant value It is final and won't
     * change once it is initialized as part of variable declaration to:
     * LogManager.getLog(AffidavitHistoryAction.class.toString()).It is used throughout the class wherever logging is
     * needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(AffidavitHistoryAction.class.toString());
    /**
     * <p>
     * The retrieved affidavit history. It is accessed through getter and doesn't have a setter.It is nullable. After
     * the struts execution, it won't be null.It does not need to be initialized when the instance is created.It is used
     * in execute(), getResult().
     * </p>
     */
    private ResultSetContainer result;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public AffidavitHistoryAction() {
        // do nothing
    }

    /**
     * <p>
     * Execute the action logic of getting affidavit history.
     * </p>
     * @return SUCCESS if no error occurs
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs
     */
    public String execute() throws UserDocumentationManagementActionsException {
        LoggingWrapperUtility.logEntrance(LOG, "AffidavitHistoryAction#execute()", null, null);
        this.audit();
        this.setDefaultStartAndEndRankValues(PactsConstants.AFFIDAVIT_HISTORY_PAGE_SIZE);
        this.setDefaultSortColumnValue();
        try {
            DataInterfaceBean dib = new DataInterfaceBean();
            ResultSetContainer resultSetContainer = dib.getAffidavitHistory(this.getUserId(), isFullList(),
                    getSortColumn(), isAscending());
            result = new ResultSetContainer(resultSetContainer, this.getStartRank(), this.getEndRank());
        } catch (SQLException e) {
            LoggingWrapperUtility.logException(LOG, "AffidavitHistoryAction#execute()", e);
            throw new UserDocumentationManagementActionsException(
                    "Error getting Affidavit history results from database", e);
        } catch (RemoteException e) {
            LoggingWrapperUtility.logException(LOG, "AffidavitHistoryAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error in EJB for getting affidavit history", e);
        } catch (Exception e) {
            LoggingWrapperUtility.logException(LOG, "AffidavitHistoryAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error getting Affidavit history results", e);
        }
        LoggingWrapperUtility.logExit(LOG, "AffidavitHistoryAction#execute()", new Object[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the result of action
     */
    public ResultSetContainer getResult() {
        return result;
    }
}
