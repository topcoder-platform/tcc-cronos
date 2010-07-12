/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action will delete the contest.
 * </p>
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 *  &lt;bean id=&quot;ContestDeletingAction&quot;
 *      class=&quot;com.topcoder.direct.services.view.action.contest.launch.ContestDeletingAction&quot;
 *      scope=&quot;prototype&quot;&gt;
 *  &lt;property name=&quot;directServiceFacade&quot; ref=&quot;DirectServiceFacade&quot;&gt;&lt;/property&gt;
 *  &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <p>
 *
 * <pre>
 *  &lt;package name=&quot;direct_struts_action_4&quot; namespace=&quot;/&quot;
 *      extends=&quot;struts-default&quot;&gt;
 *  &lt;global-results&gt;
 *  &lt;result name=&quot;success&quot;&gt;/success.jsp&lt;/result&gt;
 *  &lt;result name=&quot;input&quot;&gt;/input.jsp&lt;/result&gt;
 *  &lt;/global-results&gt;
 *  &lt;action name=&quot;*&quot; class=&quot;{1}&quot; /&gt;
 *  &lt;/package&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable and the values of this
 * class will change based on the request parameters. It's not required to be thread safe because in
 * Struts 2 the actions (different from Struts 1) are created for every request.
 * </p>
 * @author Urmass, zju_jay
 * @version 1.0
 */
public class ContestDeletingAction extends BaseDirectStrutsAction {
    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.ContestDeletingAction.";

    /**
     * <p>
     * Represents the Serial Version UID of this class.
     * </p>
     */
    private static final long serialVersionUID = 8020559877023240824L;
    /**
     * <p>
     * Represents the contest id of the contest to delete.
     * </p>
     * <p>
     * Initialized in corresponding setter.
     * </p>
     * <p>
     * Must be positive.
     * </p>
     */
    private long contestId;
    /**
     * <p>
     * Specifies whether the the project is studio contest.
     * </p>
     * <p>
     * Initialized in corresponding setter.
     * </p>
     */
    private boolean studio;

    /**
     * Default do-nothing constructor.
     */
    public ContestDeletingAction() {
    }

    /**
     * <p>
     * This execute method is responsible for deleting the contest.
     * </p>
     * <p>
     * All the action's variables should be set before the execution.
     * </p>
     * <p>
     * All the inner exception will be caught and wrapped into AggregateDataModel using "result" key
     * before being re-thrown.
     * </p>
     * @throws IllegalStateException if <code>DirectServiceFacade</code> hasn't been injected
     * @throws Exception if any other error occurs during method execution
     */
    @Override
    protected void executeAction() throws Exception {
        try {
            ActionHelper.checkInjectedFieldNull(getDirectServiceFacade(), "DirectServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            getDirectServiceFacade().deleteContest(tcSubject, contestId, studio);
        } catch (Exception e) {
            setResult(e);
            throw e;
        }
    }

    /**
     * Get the contest id of the contest to delete.
     * @return the contest id of the contest to delete.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>
     * Set the contest id of the contest to delete.
     * </p>
     * <p>
     * Struts 2 validation is used to check that the argument is greater than 0.
     * </p>
     * @param contestId the contest id of the contest to delete.
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdGreaterThanZero",
        fieldName = "contestId", expression = "contestId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Get whether the the project is studio contest.
     * @return true if the project is studio contest.
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Set whether the the project is studio contest.
     * @param studio true if the the project is studio contest.
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }
}
