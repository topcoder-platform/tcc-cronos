/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.VisaLetterEventDAO;
import com.topcoder.web.common.dao.VisaLetterRequestDAO;
import com.topcoder.web.common.model.VisaLetterEvent;
import com.topcoder.web.common.model.VisaLetterRequest;

/**
 * <p>
 * This actions gets all the pending visa letter statuses of the current user.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because it's mutable. However, dedicated instance of
 * struts action will be created by the struts framework to process the user request, so the struts actions don’t need
 * to be thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class VisaLetterStatusAction extends BaseAction {
    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = -4372983514096009262L;
    /**
     * <p>
     * The Log object used for logging. It is final and won't change once it is initialized as part of variable
     * declaration to: LogManager.getLog(VisaLetterStatusAction.class.toString()).It is used throughout the class
     * wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(VisaLetterStatusAction.class.toString());
    /**
     * <p>
     * The VisaLetterEventDAO instance used to perform persistence operation on VisaLetterEvent. It is set through
     * setter and doesn't have a getter.It cannot be null. It does not need to be initialized when the instance is
     * created.It is used in execute(), setVisaLetterEventDAO(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private VisaLetterEventDAO visaLetterEventDAO;
    /**
     * <p>
     * The VisaLetterRequestDAO instance used to perform persistence operation on VisaLetterRequest. It is set through
     * setter and doesn't have a getter.It cannot be null. It does not need to be initialized when the instance is
     * created.It is used in execute(), setVisaLetterRequestDAO(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private VisaLetterRequestDAO visaLetterRequestDAO;
    /**
     * <p>
     * The retrieved visa statuses. It is accessed through getter and doesn't have a setter.It can be null or empty.
     * After the struts execution, it won't be null. The contained values can't be null. It does not need to be
     * initialized when the instance is created.It is used in getVisaLetterEvents(), execute().
     * </p>
     */
    private List < VisaLetterEvent > visaLetterEvents;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public VisaLetterStatusAction() {
        // do nothing
    }

    /**
     * <p>
     * Execute the action logic of getting visa letter statuses.
     * </p>
     * @return SUCCESS if no error occurs
     * @throws UserDocumentationManagementActionsException
     *             if any error occurs Implementation
     */
    public String execute() throws UserDocumentationManagementActionsException {
        LoggingWrapperUtility.logEntrance(LOG, "VisaLetterStatusAction#execute()", null, null);
        this.audit();
        List < VisaLetterEvent > allEvents = visaLetterEventDAO.findShowStatus();
        visaLetterEvents = new ArrayList < VisaLetterEvent >();
        if(allEvents != null) {    
            for (VisaLetterEvent event : allEvents) {
                VisaLetterRequest request = visaLetterRequestDAO.find(this.getUserId(), event.getId());
                if (request != null) {
                    visaLetterEvents.add(event);
                }
            }
        }
        LoggingWrapperUtility.logExit(LOG, "VisaLetterStatusAction#execute()", new Object[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Getter for the namesake instance variable.
     * </p>
     * @return the list of VisaLetterEvent objects retrieved
     */
    public List < VisaLetterEvent > getVisaLetterEvents() {
        return visaLetterEvents;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param visaLetterEventDAO
     *            The VisaLetterEventDAO instance used to perform persistence operation on VisaLetterEvent.
     */
    public void setVisaLetterEventDAO(VisaLetterEventDAO visaLetterEventDAO) {
        this.visaLetterEventDAO = visaLetterEventDAO;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param visaLetterRequestDAO
     *            The VisaLetterRequestDAO instance used to perform persistence operation on VisaLetterRequest.
     */
    public void setVisaLetterRequestDAO(VisaLetterRequestDAO visaLetterRequestDAO) {
        this.visaLetterRequestDAO = visaLetterRequestDAO;
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
        ValidationUtility.checkNotNull(visaLetterEventDAO, "visaLetterEventDAO",
                UserDocumentationManagementActionsConfigurationException.class);
        ValidationUtility.checkNotNull(visaLetterRequestDAO, "visaLetterRequestDAO",
                UserDocumentationManagementActionsConfigurationException.class);
    }
}
