/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestScheduleExtension;

/**
 * <p>
 * This action is used to extend the active contest schedule.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="activeContestScheduleExtensionAction" class="activeContestScheduleExtensionAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> Mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 *
 * @author Urmass, TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestScheduleExtensionAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.activeContestScheduleExtensionAction.";

    /**
     * <p>
     * Represents the contest ID used to retrieve the needed contest details.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * Specifies whether the project is a studio contest.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * Represents the registration extension in hours.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive.
     * </p>
     */
    private int registrationExtension;

    /**
     * <p>
     * Represents the milestone extension in hours.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive.
     * </p>
     */
    private int milestoneExtension;

    /**
     * <p>
     * Represents the submission extension in hours.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive.
     * </p>
     */
    private int submissionExtension;

    /**
     * Default constructor, creates new instance.
     */
    public ActiveContestScheduleExtensionAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for extending active contest schedule.
     * No result is stored in the <code>AggregateDataModel</code>.
     * </p>
     *
     * <p>
     * All the action's required variables should be set before the execution. All exceptions will be caught and
     * stored in the <code>AggregateDataModel</code> and then rethrown.
     * </p>
     *
     * @throws IllegalStateException if <code>DirectServiceFacade</code> instance has not been injected
     * @throws Exception if some other error occurs when executing this action
     */
    protected void executeAction() throws Exception {
        try {
            ActionHelper.checkInjectedFieldNull(getDirectServiceFacade(), "directServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // extend the contest schedule
            ContestScheduleExtension extension = new ContestScheduleExtension();
            extension.setContestId(contestId);
            extension.setStudio(studio);
            extension.setExtendRegistrationBy(registrationExtension);
            extension.setExtendMilestoneBy(milestoneExtension);
            extension.setExtendSubmissionBy(submissionExtension);
            getDirectServiceFacade().extendActiveContestSchedule(tcSubject, extension);

        } catch (Exception e) {
            // store exception in model and rethrow it
            setResult(e);
            throw e;
        }
    }

    /**
     * Getter for contest ID.
     *
     * @return the contest ID
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for contest ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param contestId the contest ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdValues",
        fieldName = "contestId", expression = "contestId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Getter for studio boolean value.
     *
     * @return the boolean value for studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Setter for studio boolean value.
     *
     * @param studio the boolean value for studio
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Getter for registration extension.
     *
     * @return the registration extension
     */
    public int getRegistrationExtension() {
        return registrationExtension;
    }

    /**
     * Setter for registration extension. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param registrationExtension the registration extension
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "registrationExtensionValues",
        fieldName = "registrationExtension", expression = "registrationExtension > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setRegistrationExtension(int registrationExtension) {
        this.registrationExtension = registrationExtension;
    }

    /**
     * Getter for milestone extension.
     *
     * @return the milestone extension
     */
    public int getMilestoneExtension() {
        return milestoneExtension;
    }

    /**
     * Setter for milestone extension. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param milestoneExtension the milestone extension
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "milestoneExtensionValues",
        fieldName = "milestoneExtension", expression = "milestoneExtension > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setMilestoneExtension(int milestoneExtension) {
        this.milestoneExtension = milestoneExtension;
    }

    /**
     * Getter for submission extension.
     *
     * @return the submission extension
     */
    public int getSubmissionExtension() {
        return submissionExtension;
    }

    /**
     * Setter for submission extension. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param submissionExtension the submission extension
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "submissionExtensionValues",
        fieldName = "submissionExtension", expression = "submissionExtension > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setSubmissionExtension(int submissionExtension) {
        this.submissionExtension = submissionExtension;
    }
}
