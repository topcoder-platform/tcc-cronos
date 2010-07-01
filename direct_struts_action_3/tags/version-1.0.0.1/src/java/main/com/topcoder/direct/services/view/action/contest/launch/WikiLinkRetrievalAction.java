/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.net.URLEncoder;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * This action returns the wiki link.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="wikiLinkRetrievalAction" class="wikiLinkRetrievalAction"&gt;
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
public class WikiLinkRetrievalAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.wikiLinkRetrievalAction.";

    /**
     * <p>
     * Represents the project ID used to retrieve the needed project details.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive when <code>studio</code> is false, can be any value otherwise.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * Represents the contest ID used to retrieve the needed contest details.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive when <code>studio</code> is true, can be any value otherwise.
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
     * The link template used to assemble the result wiki link. It is expected that template contains
     * "[contest_type]" and "[contest_name]" substrings that will be replaced by the actual values.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Cannot be null, should contain "[contest_type]" and "[contest_name]" substrings.
     * </p>
     */
    private String linkTemplate;

    /**
     * Default constructor, creates new instance.
     */
    public WikiLinkRetrievalAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for assembling and returning the wiki link.
     * The result of the action is stored in the <code>AggregateDataModel</code>.
     * </p>
     *
     * <p>
     * All the action's required variables should be set before the execution. All exceptions will be caught and
     * stored in the <code>AggregateDataModel</code> and then rethrown.
     * </p>
     *
     * @throws IllegalStateException if <code>ContestServiceFacade</code> instance has not been injected, or
     *     if a software contest couldn't be found or some other required field such as assetDTO or category
     *     is null, or if contest type or contest name template variables are null for the contest
     * @throws Exception if some other error occurs when executing this action
     */
    protected void executeAction() throws Exception {
        try {
            ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            String contestType;
            String contestName;

            // get the contest type and name for the given contest
            if (studio) {
                // get the studio contest
                StudioCompetition competition = getContestServiceFacade().getContest(tcSubject, contestId);

                contestType = competition.getCategory();
                contestName = competition.getContestData().getName();

            } else {
                // get the software contest
                SoftwareCompetition competition = getContestServiceFacade().getSoftwareContestByProjectId(tcSubject,
                    projectId);

                checkNull(competition, "The software contest could not be found");
                checkNull(competition.getAssetDTO(), "The assetDTO for software competition cannot be null");
                checkNull(competition.getCategory(), "The category for software competition cannot be null");

                contestType = competition.getCategory().getName();
                contestName = competition.getAssetDTO().getName();
            }

            // make sure there are values for each template variable
            checkNull(contestType, "The contest type for the competition cannot be null");
            checkNull(contestName, "The contest name for the competition cannot be null");

            // plug the values into the template
            String template = linkTemplate.replaceFirst("\\[contest_type\\]", contestType).replaceFirst(
                "\\[contest_name\\]", contestName);
            String resultUrl = URLEncoder.encode(template, "UTF-8");

            // store the result data in the model
            setResult(resultUrl);

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
     * Setter for project ID. Struts 2 validation is used to check that the argument is greater than 0 if the
     * contest is not a studio contest.
     *
     * @param projectId the project ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "projectIdValues",
        fieldName = "projectId", expression = "studio || projectId > 0",
        message = "projectId must be > 0 for software contest")
    public void setProjectId(long projectId) {
        this.projectId = projectId;
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
     * Setter for contest ID. Struts 2 validation is used to check that the argument is greater than 0 if the
     * contest is a studio contest.
     *
     * @param contestId the contest ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdValues",
        fieldName = "contestId", expression = "!studio || contestId > 0",
        message = "contestId must be > 0 for studio contest")
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
     * Getter for link template.
     *
     * @return the link template
     */
    public String getLinkTemplate() {
        return linkTemplate;
    }

    /**
     * Setter for link template. Struts 2 validation is used to check that the argument is not null and
     * contains "[contest_name]" and "[contest_type]" substrings.
     *
     * @param linkTemplate the link template
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "linkTemplateValues",
        fieldName = "linkTemplate",
        expression = "linkTemplate != null && "
            + "linkTemplate.indexOf(\"[contest_type]\") != -1 && linkTemplate.indexOf(\"[contest_name]\") != -1",
        message = "linkTemplate cannot be null and must contain [contest_type] and [contest_name]")
    public void setLinkTemplate(String linkTemplate) {
        this.linkTemplate = linkTemplate;
    }

    /**
     * Checks that given value is not null.
     *
     * @param value the value to check
     * @param message the message to use for exception
     *
     * @throws IllegalStateException if value is null
     */
    private static void checkNull(Object value, String message) {
        if (value == null) {
            throw new IllegalStateException(message);
        }
    }
}
