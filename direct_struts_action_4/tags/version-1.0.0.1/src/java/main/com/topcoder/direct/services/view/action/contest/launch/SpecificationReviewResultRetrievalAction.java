/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action is responsible for retrieving the specification review result.
 * </p>
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 *  &lt;bean id=&quot;SpecificationReviewResultRetrievalAction&quot;
 *  class=&quot;com.topcoder.direct.services.view.action.contest.launch.SpecificationReviewResultRetrievalAction&quot;
 *  scope=&quot;prototype&quot;&gt;
 *  &lt;property name=&quot;contestServiceFacade&quot; ref=&quot;ContestServiceFacade&quot;&gt;&lt;/property&gt;
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
public class SpecificationReviewResultRetrievalAction extends BaseDirectStrutsAction {
    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.SpecificationReviewResultRetrievalAction.";
    /**
     * <p>
     * Represents the Serial Version UID of this class.
     * </p>
     */
    private static final long serialVersionUID = -4805117184720527309L;
    /**
     * <p>
     * Represents the contest id to retrieve the specification review result for.
     * </p>
     * <p>
     * Must be positive.
     * </p>
     */
    private long contestId;

    /**
     * Default do-nothing constructor.
     */
    public SpecificationReviewResultRetrievalAction() {
    }

    /**
     * <p>
     * This execute method is responsible for for retrieving the specification review result.
     * </p>
     * <p>
     * The result is wrapped in AggregateDataModel and stored as model variable of the base
     * AbstractAction.
     * </p>
     * <p>
     * All the action's variables should be set before the execution.
     * </p>
     * <p>
     * All the inner exception will be caught and wrapped into AggregateDataModel using "result" key
     * before being re-thrown.
     * </p>
     * @throws IllegalStateException if <code>ContestServiceFacade</code> hasn't been injected
     * @throws Exception if any other error occurs during method execution
     */
    @Override
    protected void executeAction() throws Exception {
        try {
            ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "ContestServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            ScorecardReviewData data =
                getContestServiceFacade().getScorecardAndReview(tcSubject, contestId);
            setResult(data);
        } catch (Exception e) {
            setResult(e);
            throw e;
        }
    }

    /**
     * Get the contest id to retrieve the specification review result for.
     * @return the contest id to retrieve the specification review result for.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>
     * Set the contest id to retrieve the specification review result for.
     * </p>
     * <p>
     * Struts 2 validation is used to check that the argument is greater than 0.
     * </p>
     * @param contestId the contest id to retrieve the specification review result for.
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdGreaterThanZero",
        fieldName = "contestId", expression = "contestId > 0", message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }
}
