/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;
import com.topcoder.web.tc.controller.legacy.pacts.common.IllegalUpdateException;
import com.topcoder.web.tc.controller.legacy.pacts.common.NoObjectFoundException;

/**
 * <p>
 * This action affirms an affidavit. It uses DataInterfaceBean to execute the update on affidavit.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class AffirmAffidavitAction extends BaseAction {
    /**
     * Generated Serial version number.
     */
    private static final long serialVersionUID = 4377220011630137705L;
    /**
     * <p>
     * The Log object used for logging.It's a constant. So it can only be that constant value It is final and won't
     * change once it is initialized as part of variable declaration to:
     * LogManager.getLog(AffirmAffidavitAction.class.toString()).It is used throughout the class wherever logging is
     * needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(AffirmAffidavitAction.class.toString());
    /**
     * <p>
     * The date format string used to parse the birthday. It is set through setter and doesn't have a getter. It cannot
     * be null but can be empty.It can be changed after it is initialized as part of variable declaration to:
     * "yyyy/MM/dd". Usage: It is used in execute(), setDateFormatString(), validate(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private String dateFormatString = "yyyy/MM/dd";
    /**
     * <p>
     * The id of the affidavit to affirm. It has both getter and setter.It must be non-negative.It does not need to be
     * initialized when the instance is created.It is used in setAffidavitId(), execute(), getAffidavitId().
     * </p>
     */
    private long affidavitId;
    /**
     * <p>
     * The string value of the birthday. It has both getter and setter.It cannot be null or empty.It does not need to be
     * initialized when the instance is created.It is used in execute(), getBirthday(), validate(), setBirthday().
     * </p>
     */
    private String birthday;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public AffirmAffidavitAction() {
        // do nothing
    }

    /**
     * <p>
     * Execute the action logic of affirming an affidavit.
     * </p>
     * @return SUCCESS if no error occurs
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs
     */
    public String execute() throws UserDocumentationManagementActionsException {
        LoggingWrapperUtility.logEntrance(LOG, "AffirmAffidavitAction#execute()", null, null);
        this.audit();
        DataInterfaceBean dib = new DataInterfaceBean();
        SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
        try {
            Date date = df.parse(birthday);
            dib.affirmAffidavit(affidavitId, date);
        } catch (ParseException e) {
            LoggingWrapperUtility.logException(LOG, "AffirmAffidavitAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error parsing dateFormatString", e);
        } catch (SQLException e) {
            LoggingWrapperUtility.logException(LOG, "AffirmAffidavitAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error querying the database", e);
        } catch (IllegalUpdateException e) {
            LoggingWrapperUtility.logException(LOG, "AffirmAffidavitAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Affidavit expired or already updated", e);
        } catch (NoObjectFoundException e) {
            LoggingWrapperUtility.logException(LOG, "AffirmAffidavitAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Affidavit not found", e);
        } catch (RemoteException e) {
            LoggingWrapperUtility.logException(LOG, "AffirmAffidavitAction#execute()", e);
            throw new UserDocumentationManagementActionsException("Error communicating with EJB", e);
        }
        LoggingWrapperUtility.logExit(LOG, "AffirmAffidavitAction#execute()", new Object[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Validate the input parameters.
     * </p>
     */
    public void validate() {
        SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
        try {
            df.parse(birthday);
        } catch (ParseException e) {
            this.addFieldError("birthday", getText("birthday.invalid"));
        }
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the affidavitId
     */
    public long getAffidavitId() {
        return affidavitId;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param dateFormatString
     *            The date format string used to parse the birthday.
     */
    public void setDateFormatString(String dateFormatString) {
        this.dateFormatString = dateFormatString;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param affidavitId
     *            The id of the affidavit to affirm.
     */
    public void setAffidavitId(long affidavitId) {
        this.affidavitId = affidavitId;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param birthday
     *            The string value of the birthday.
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the injected
     * values are valid.
     * </p>
     * @throws UserDocumentationManagementActionsConfigurationException
     *             if any of the injected values is invalid.
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dateFormatString, "",
                UserDocumentationManagementActionsConfigurationException.class);
    }
}
