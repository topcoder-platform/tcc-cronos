/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action returns the active contest prize.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="activeContestPrizeRetrievalAction" class="activeContestPrizeRetrievalAction"&gt;
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
public class ActiveContestPrizeRetrievalAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.activeContestPrizeRetrievalAction.";

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
     * Default constructor, creates new instance.
     */
    public ActiveContestPrizeRetrievalAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for returning active contest prize.
     * The result of the action is stored in the <code>AggregateDataModel</code>.
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

            // get the contest prize and store it in the model
            setResult(getDirectServiceFacade().getContestPrize(tcSubject, contestId, studio));

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
}
