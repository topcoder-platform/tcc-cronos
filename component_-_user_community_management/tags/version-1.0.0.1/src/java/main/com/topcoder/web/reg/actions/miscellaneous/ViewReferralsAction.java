/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.web.common.TCWebException;

/**
 * This is a Struts action that is responsible for showing a list of referrals
 * to the user. This action retrieves referrals data from persistence. Actual
 * response rendering is performed with use of JSP. The injected DataAccessInt
 * instance for this action must represent "informixoltp" database.
 * <p>
 * Thread Safety: This class is mutable and not thread safe. But it will be used
 * in thread safe manner by Struts. It's assumed that request scope will be set
 * up for all Struts actions in Spring configuration, thus actions will be
 * accessed from a single thread only.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class ViewReferralsAction extends BaseDataAccessUserCommunityManagementAction {
    /**
     * <p>
     * The serial version uid.
     * </p>
     */
    private static final long serialVersionUID = -8189967665846161969L;
    /**
     * The referrals of the current user. Is initialized in execute(). Cannot be
     * null, cannot contain null after initialization. This attributed is used
     * for passing data from this Struts action to JSP. Has a getter.
     */
    private List<ReferralData> referrals;

    /**
     * Creates an instance of ViewReferralsAction.
     */
    public ViewReferralsAction() {
    }

    /**
     * Executes the Struts action. This method retrieves referral data from the
     * database and passes it to JSP via referrals action property.
     *
     * @throws TCWebException
     *             if any error occurred
     * @return SUCCESS to indicate that the operation was successful
     */
    @Override
    public String execute() throws TCWebException {
        final String methodSignature = "ViewReferralsAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        try {
            // Create a data access request instance:
            Request request = new Request();
            // Get ID of the user:
            long userId = getUserId();
            // Set user ID to the request:
            request.setProperty("uid", String.valueOf(userId));
            // Set content handle to "referral_list":
            request.setContentHandle("referral_list");
            // Get data access instance to be used:
            DataAccessInt dataAccess = getDataAccess();
            // Perform request:
            Map<String, ResultSetContainer> resultData = null;
            try {
                resultData = dataAccess.getData(request);
            } catch (Exception e) {
                throw new TCWebException("Error occurs when retrieve the referal list", e);
            }
            // Get referral list from the result data:
            ResultSetContainer resultSetContainer = resultData.get("referral_list");
            if (resultSetContainer == null) {
                throw new TCWebException("Unable to retrive the referral list.");
            }
            // Create a list for obtained referrals:
            referrals = new ArrayList<ReferralData>();
            for (ResultSetContainer.ResultSetRow resultSetRow : resultSetContainer) {
                // Get coder ID from the result set row:
                long coderId = resultSetRow.getLongItem("coder_id");
                // Get rating of the coder from the result set row:
                int rating = resultSetRow.getIntItem("rating");
                // Get handle of the coder from the result set row:
                String handle = resultSetRow.getStringItem("handle");
                // Get registration date of the coder from the result set row:
                Date memberSince = resultSetRow.getTimestampItem("member_since");
                // Create referral data instance:
                ReferralData referralData = new ReferralData();
                // Set coder ID to the referral data instance:
                referralData.setCoderId(coderId);
                // Set rating to the referral data instance:
                referralData.setRating(rating);
                // Set handle to the referral data instance:
                referralData.setHandle(handle);
                // Set member since to the referral data instance:
                referralData.setMemberSince(memberSince);
                referrals.add(referralData);
            }
            LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{referrals});
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLog(), methodSignature, e);
        }
        return SUCCESS;
    }

    /**
     * Retrieves the referrals of the current user.
     *
     * @return the referrals of the current user
     */
    public List<ReferralData> getReferrals() {
        return referrals;
    }
}
