/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ProjectSummaryData;

/**
 * <p>
 * This action returns the project contest data and the project summary data.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="projectContestDataRetrievalAction" class="projectContestDataRetrievalAction"&gt;
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
public class ProjectContestDataRetrievalAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.projectContestDataRetrievalAction.";

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
    public ProjectContestDataRetrievalAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for returning the project contest and project summary data.
     * The result of the action is stored in the <code>AggregateDataModel</code>.
     * </p>
     *
     * <p>
     * All the action's required variables should be set before the execution. All exceptions will be caught and
     * stored in the <code>AggregateDataModel</code> and then rethrown.
     * </p>
     *
     * @throws IllegalStateException if <code>ContestServiceFacade</code> instance has not been injected
     * @throws Exception if some other error occurs when executing this action
     */
    protected void executeAction() throws Exception {

        try {
            ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // get the contest data and project summary data
            List<CommonProjectContestData> contestData = getContestServiceFacade().getCommonProjectContestDataByPID(
                tcSubject, projectId);
            List<ProjectSummaryData> projectSummaryData = getContestServiceFacade().getProjectData(tcSubject);

            // store the result data in the model
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("contestData", contestData);
            result.put("projectSummaryData", projectSummaryData);
            setResult(result);

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
