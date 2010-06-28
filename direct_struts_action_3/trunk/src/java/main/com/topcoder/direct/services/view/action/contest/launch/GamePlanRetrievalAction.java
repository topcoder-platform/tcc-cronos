/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action returns the game plan.
 * </p>
 *
 * <p>
 * <strong>Demo of how this action could be used in a JSP:</strong>
 * <pre>
 * &lt;%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 *     pageEncoding="ISO-8859-1"%&gt;
 * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"&gt;
 * &lt;%@taglib uri="/struts-tags" prefix="s"%&gt;
 * &lt;html&gt;
 * &lt;head&gt;
 * &lt;meta http-equiv="Content-Type" content="text/html; charset=UTF-8"&gt;
 * &lt;title&gt;Game Plan Retrieval Action&lt;/title&gt;
 *
 * &lt;link href="&lt;s:url value="/css/main.css"/&gt;" rel="stylesheet"
 * type="text/css"/&gt;
 *
 * &lt;/head&gt;
 * &lt;body&gt;
 *
 * &lt;!-- display any action errors --&gt;
 * &lt;div style="color: #ff0000;"&gt;&lt;s:iterator value="actionErrors"&gt;
 *     &lt;span&gt;&lt;s:property escape="false" /&gt;&lt;/span&gt;
 * &lt;/s:iterator&gt;&lt;/div&gt;
 *
 * &lt;h3&gt;GamePlanRetrievalAction&lt;/h3&gt;
 * &lt;s:form action="gamePlanRetrievalAction"&gt;
 *     &lt;table&gt;
 *         &lt;tr&gt;
 *             &lt;td&gt;Project Id (must be positive)&lt;/td&gt;
 *             &lt;td&gt;&lt;s:textfield name="projectId"/&gt;&lt;/td&gt;
 *         &lt;/tr&gt;
 *         &lt;tr&gt;
 *             &lt;td&gt;&lt;s:submit value="Get Game Plan" /&gt;&lt;/td&gt;
 *             &lt;td&gt;&lt;/td&gt;
 *         &lt;/tr&gt;
 *     &lt;/table&gt;
 * &lt;/s:form&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="gamePlanRetrievalAction" class="gamePlanRetrievalAction"&gt;
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
public class GamePlanRetrievalAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.gamePlanRetrievalAction.";

    /**
     * <p>
     * Represents the project ID used to retrieve the needed project details.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive.
     * </p>
     */
    private long projectId;

    /**
     * Default constructor, creates new instance.
     */
    public GamePlanRetrievalAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for returning the game plan.
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

            // get the project game plan and store in model
            setResult(getDirectServiceFacade().getProjectGamePlan(tcSubject, projectId));

        } catch (Exception e) {
            // store exception in model and rethrow it
            setResult(e);
            throw e;
        }
    }

    /**
     * Getter for project ID.
     *
     * @return the project ID
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Setter for project ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param projectId the project ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "projectIdValues",
        fieldName = "projectId", expression = "projectId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
