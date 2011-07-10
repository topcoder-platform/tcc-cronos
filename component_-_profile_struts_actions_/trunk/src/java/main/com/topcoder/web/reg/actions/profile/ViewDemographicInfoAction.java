/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseDisplayProfileAction and processes the output by taking the passed user and sets it to the
 * output. It also provides the full set of questions.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewDemographicInfoAction extends BaseDisplayProfileAction {

    /**
     * <p>
     * Represents performAdditionalTasks() method constant signature.
     * </p>
     */
    private static final String PERFORM_ADDITIONAL_TASKS_METHOD_SIGNATURE =
            "ViewDemographicInfoAction.performAdditionalTasks()";

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
     * Represents the list of demographic questions.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set by the
     * execute method.
     * </p>
     */
    private List<DemographicQuestion> demographicQuestions;

    /**
     * <p>
     * Creates an instance of ViewDemographicInfoAction.
     * </p>
     */
    public ViewDemographicInfoAction() {
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
    @PostConstruct
    @Override
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
     * This method will simply set this to the output and audit the call.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processOutputData(User user) {
        setDisplayedUser(user);
    }

    /**
     * <p>
     * This method is called by the execute method to perform any additional tasks that the extension may require.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void performAdditionalTasks(User user) throws ProfileActionException {
        try {
            this.demographicQuestions = new ArrayList<DemographicQuestion>(this.demographicQuestionDAO.getQuestions());
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(getLogger(), PERFORM_ADDITIONAL_TASKS_METHOD_SIGNATURE,
                    "Error occurred in underlying DAO.", e);
        }
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

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<DemographicQuestion> getDemographicQuestions() {
        return demographicQuestions;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param demographicQuestions the namesake field instance value to set
     */
    public void setDemographicQuestions(List<DemographicQuestion> demographicQuestions) {
        this.demographicQuestions = demographicQuestions;
    }
}