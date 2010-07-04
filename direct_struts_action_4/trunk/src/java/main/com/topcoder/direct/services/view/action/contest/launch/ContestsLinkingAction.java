/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action is responsible for linking the contests.
 * </p>
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 *  &lt;bean id=&quot;ContestsLinkingAction&quot;
 *  class=&quot;com.topcoder.direct.services.view.action.contest.launch.ContestsLinkingAction&quot;
 *  scope=&quot;prototype&quot;&gt;
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
public class ContestsLinkingAction extends BaseDirectStrutsAction {
    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.ContestsLinkingAction.";
    /**
     * <p>
     * Represents the Serial Version UID of this class.
     * </p>
     */
    private static final long serialVersionUID = 5087051968534467018L;
    /**
     * <p>
     * Represents the contest id to link.
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
     * The parent project ids to update.
     * </p>
     * <p>
     * Initialized in corresponding setter.
     * </p>
     * <p>
     * It can be null, but can not contain non-positive elements.
     * </p>
     */
    private long[] parentProjectIds;
    /**
     * <p>
     * The child project ids to update.
     * </p>
     * <p>
     * Initialized in corresponding setter.
     * </p>
     * <p>
     * It can be null, but can not contain non-positive elements.
     * </p>
     */
    private long[] childProjectIds;

    /**
     * Default do-nothing constructor.
     */
    public ContestsLinkingAction() {
    }

    /**
     * <p>
     * This execute method is responsible for linking the contests.
     * </p>
     * <p>
     * No result is wrapped in AggregateDataModel and stored as model variable of the base
     * AbstractAction.
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
            getDirectServiceFacade().updateProjectLinks(tcSubject, contestId, parentProjectIds,
                childProjectIds);
        } catch (Exception e) {
            setResult(e);
            throw e;
        }
    }

    /**
     * Get the contest id to link.
     * @return the contest id to link.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Set the contest id to link.
     * </p>
     * <p>
     * Struts 2 validation is used to check that the argument is greater than 0.
     * </p>
     * @param contestId the contest id to link.
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdGreaterThanZero",
        fieldName = "contestId", expression = "contestId > 0", message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Get the parent project ids to update.
     * @return The parent project ids to update.
     */
    public long[] getParentProjectIds() {
        return parentProjectIds;
    }

    /**
     * <p>
     * Set the parent project ids to update.
     * </p>
     * <p>
     * It can be null, but if it contains non-positive elements, add field error.
     * </p>
     * @param parentProjectIds The parent project ids to update.
     */
    public void setParentProjectIds(long[] parentProjectIds) {
        validateArray(parentProjectIds, "parentProjectIds");
        this.parentProjectIds = parentProjectIds;
    }

    /**
     * Get the child project ids to update.
     * @return The child project ids to update.
     */
    public long[] getChildProjectIds() {
        return childProjectIds;
    }

    /**
     * <p>
     * Set the child project ids to update.
     * </p>
     * <p>
     * It can be null, but if it contains non-positive elements, add field error.
     * </p>
     * @param childProjectIds The child project ids to update.
     */
    public void setChildProjectIds(long[] childProjectIds) {
        validateArray(childProjectIds, "childProjectIds");
        this.childProjectIds = childProjectIds;
    }

    /**
     * <p>Validate the array contains only positive elements.<p/>
     * @param array the array to be validated.
     * @param field the field name of the array.
     */
    private void validateArray(long[] array, String field) {
        if (array != null) {
            for (long x : array) {
                if (x <= 0) {
                    addFieldError(field, field + " must not contain non-positive element.");
                    return;
                }
            }
        }
    }
}
