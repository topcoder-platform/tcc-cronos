/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.Set;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Level;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseSaveProfileAction to save the name and contact info for the user, and generates the
 * appropriate template data with the updated contact info data
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SaveDemographicInfoAction extends BaseSaveProfileAction {

    /**
     * <p>
     * Represents max college description string length.
     * </p>
     */
    private static final int MAX_COLLEGE_DESCRIPTION = 50;

    /**
     * <p>
     * Represents max demographic field length.
     * </p>
     */
    private static final int MAX_FIELD_LENGTH = 255;

    /**
     * <p>
     * Represents validate() method signature constant name.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "SaveDemographicInfoAction.validate()";

    /**
     * <p>
     * This is the DemographicQuestionDAO instance used to get demographic question info.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null. This field will be injected by the container (expected to be done
     * only once), and is not expected to change afterward.
     * </p>
     */
    private DemographicQuestionDAO demographicQuestionDAO;

    /**
     * <p>
     * Creates an instance of SaveDemographicInfoAction.
     * </p>
     */
    public SaveDemographicInfoAction() {
    }

    /**
     * <p>
     * Checks that all required values have been injected by the system right after construction and injection.
     * </p>
     * <p>
     * This is used to obviate the need to check these values each time in the execute method.
     * </p>
     * @throws ProfileActionConfigurationException - If any required value has not been injected into this class
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(demographicQuestionDAO, "demographicQuestionDAO",
                ProfileActionConfigurationException.class);
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will update the specific parts of the User that have been modified.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processInputData(User user) throws ProfileActionException {
        Set<DemographicResponse> userDemographicResponse = user.getDemographicResponses();
        StringBuilder previousValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        for (DemographicResponse newDemographicResponse : getSavedUser().getDemographicResponses()) {
            String previousAnswerValue = null;
            String newAnswerValue = newDemographicResponse.getAnswer().getText();
            // check if already contains response
            if (userDemographicResponse.contains(newDemographicResponse)) {
                DemographicResponse previousUserDemographicResponse = null;
                for (DemographicResponse demographicResponse : userDemographicResponse) {
                    if (demographicResponse.equals(newDemographicResponse)) {
                        previousUserDemographicResponse = demographicResponse;
                        break;
                    }
                }
                previousAnswerValue = previousUserDemographicResponse.getAnswer().getText();
            } else {
                // add new demographic response
                user.addDemographicResponse(newDemographicResponse);
            }
            // add values audit
            Helper.processAuditData(previousValues, newValues, previousAnswerValue, newAnswerValue,
                    newDemographicResponse.getQuestion().getText());
        }
        // make audit
        Helper.makeAudit(this, previousValues, newValues);
    }

    /**
     * <p>
     * This method is called by the execute method to generate the email template data.
     * </p>
     * @param user the user
     * @return the template data string
     * @throws ProfileActionException - If there are any errors while performing this operation
     */
    protected String generateEmailTemplateData(User user) {
        StringBuilder emailTemplateDataBuilder = new StringBuilder(Helper.getPreparedEmailTemplate(user));
        for (DemographicResponse demographicResponse : user.getDemographicResponses()) {
            emailTemplateDataBuilder.append(Helper.createTag(demographicResponse.getQuestion().getText(),
                    demographicResponse.getAnswer().getText(), true));
        }
        return Helper.createTag("DATA", emailTemplateDataBuilder.toString(), false);
    }

    /**
     * <p>
     * Validates input parameters.
     * </p>
     */
    @Override
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
        for (DemographicResponse demographicResponse : getSavedUser().getDemographicResponses()) {
            DemographicQuestion demographicQuestion = demographicResponse.getQuestion();
            DemographicAnswer demographicAnswer = demographicResponse.getAnswer();
            if (demographicQuestion.getText() != null) {
                if ("GPA".equals(demographicQuestion.getText())) {
                    try {
                        Double.parseDouble(demographicQuestion.getText());
                    } catch (NumberFormatException e) {
                        Helper.addFieldError(this, true, demographicQuestion.getText(), "GPA.value.invalid",
                                VALIDATE_METHOD_SIGNATURE);
                    }
                } else if ("College Major Description".equals(demographicQuestion.getText())) {
                    Helper.addFieldError(this, demographicAnswer.getText().length() > MAX_COLLEGE_DESCRIPTION,
                            demographicQuestion.getText(), "College_Major_Description.length.invalid",
                            VALIDATE_METHOD_SIGNATURE);
                } else {
                    Helper.addFieldError(this, demographicAnswer.getText().length() > MAX_FIELD_LENGTH,
                            demographicQuestion.getText(), demographicQuestion.getText() + ".length.invalid",
                            VALIDATE_METHOD_SIGNATURE);
                }
            }
        }
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public DemographicQuestionDAO getDemographicQuestionDAO() {
        return demographicQuestionDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param demographicQuestionDAO the namesake field instance value to set
     */
    public void setDemographicQuestionDAO(DemographicQuestionDAO demographicQuestionDAO) {
        this.demographicQuestionDAO = demographicQuestionDAO;
    }
}