/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ProjectBudget;

/**
 * <p>
 * This action is responsible for modifying the project budget.
 * </p>
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 *  &lt;bean id=&quot;ProjectBudgetModifyingAction&quot;
 *  class=&quot;com.topcoder.direct.services.view.action.contest.launch.ProjectBudgetModifyingAction&quot;
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
public class ProjectBudgetModifyingAction extends BaseDirectStrutsAction {
    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.ProjectBudgetModifyingAction.";
    /**
     * <p>
     * Represents the Serial Version UID of this class.
     * </p>
     */
    private static final long serialVersionUID = 8325118431102160306L;
    /**
     * <p>
     * Represents the id of the selected billing project.
     * </p>
     * <p>
     * Must be positive.
     * </p>
     */
    private long billingProjectId;
    /**
     * Represents the amount of change.
     */
    private double changedAmount;

    /**
     * Default do-nothing constructor.
     */
    public ProjectBudgetModifyingAction() {
    }

    /**
     * <p>
     * This execute method is responsible for modifying the project budget.
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
     * @throws IllegalStateException if <code>DirectServiceFacade</code> hasn't been injected
     * @throws Exception if any other error occurs during method execution
     */
    @Override
    protected void executeAction() throws Exception {
        try {
            ActionHelper.checkInjectedFieldNull(getDirectServiceFacade(), "DirectServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            ProjectBudget budget =
                getDirectServiceFacade().updateProjectBudget(tcSubject, billingProjectId,
                    changedAmount);
            setResult(budget);
        } catch (Exception e) {
            setResult(e);
            throw e;
        }
    }

    /**
     * Get the id of the selected billing project.
     * @return the id of the selected billing project.
     */
    public long getBillingProjectId() {
        return this.billingProjectId;
    }

    /**
     * <p>
     * Set the id of the selected billing project.
     * </p>
     * <p>
     * Struts 2 validation is used to check that the argument is greater than 0.
     * </p>
     * @param billingProjectId the id of the selected billing project.
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "billingProjectIdGreaterThanZero",
        fieldName = "billingProjectId", expression = "billingProjectId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setBillingProjectId(long billingProjectId) {
        this.billingProjectId = billingProjectId;
    }

    /**
     * Get the amount of change.
     * @return the amount of change.
     */
    public double getChangedAmount() {
        return this.changedAmount;
    }

    /**
     * Set the amount of change.
     * @param changedAmount the amount of change.
     */
    public void setChangedAmount(double changedAmount) {
        this.changedAmount = changedAmount;
    }
}
