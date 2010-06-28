/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action sends the contest receipt to the given email addresses.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="contestReceiptSendingAction" class="contestReceiptSendingAction"&gt;
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
public class ContestReceiptSendingAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.contestReceiptSendingAction.";

    /**
     * Represents regular expression used to validate email addresses.
     */
    private static final String EMAIL_REGEX = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+"
        + "((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|"
        + "(\\.mil)|(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|(\\.arpa)|(\\.coop)|(\\.int)|"
        + "(\\.jobs)|(\\.museum)|(\\.name)|(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$)\\b";


    /**
     * Represents maximum length for the additional email addresses.
     */
    private static final int MAX_ADDITIONAL_EMAIL_LEN = 4096;

    /**
     * Represents the delimiters used to separate email addresses.
     */
    private static final String EMAIL_DELIMITERS = ";|,";

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
     * Represents the additional email addresses to send the contest receipt to. The email addresses are separated
     * using semicolon or comma.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Can be null or empty string, cannot exceed 4096 characters, and each email address must be valid.
     * </p>
     */
    private String additionalEmailAddresses;

    /**
     * Default constructor, creates new instance.
     */
    public ContestReceiptSendingAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for sending the contest receipt to the given email addresses.
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

            // send the contest receipt using additional email addresses (if present) and store result in model
            setResult(getDirectServiceFacade().sendContestReceiptByEmail(tcSubject, contestId, studio,
                additionalEmailAddresses == null || additionalEmailAddresses.trim().length() == 0 ? null
                    : additionalEmailAddresses.split(EMAIL_DELIMITERS)));

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
     * Getter for additional email addresses.
     *
     * @return the additional email addresses
     */
    public String getAdditionalEmailAddresses() {
        return additionalEmailAddresses;
    }

    /**
     * <p>
     * Setter for additional email addresses.
     * </p>
     *
     * <p>
     * Can be null or empty string, cannot exceed 4096 characters, and each email address must be valid.
     * Any errors are added as field errors to model.
     * </p>
     *
     * @param additionalEmailAddresses the additional email addresses
     */
    public void setAdditionalEmailAddresses(String additionalEmailAddresses) {
        this.additionalEmailAddresses = additionalEmailAddresses;

        // null or empty value is allowed
        if (additionalEmailAddresses == null || additionalEmailAddresses.trim().length() == 0) {
            return;
        }

        // make sure it's not too long
        if (additionalEmailAddresses.trim().length() > MAX_ADDITIONAL_EMAIL_LEN) {
            addFieldError("additionalEmailAddresses",
                "additionalEmailAddresses must not exceed " + MAX_ADDITIONAL_EMAIL_LEN + " chars");
            return;
        }

        // validate all email addresses, and store each invalid email address
        String[] emails = additionalEmailAddresses.split(EMAIL_DELIMITERS);
        StringBuilder builder = new StringBuilder();
        String comma = "";
        for (String email : emails) {
            String trimmedEmail = email.trim();
            if (!trimmedEmail.matches(EMAIL_REGEX)) {
                builder.append(comma).append(trimmedEmail.equals("") ? "[empty]" : trimmedEmail);
                comma = ",";
            }
        }

        // if any email addresses were invalid, add them to field errors
        if (builder.toString().length() != 0) {
            addFieldError("additionalEmailAddresses",
                "The following email addresses were invalid: " + builder.toString());
        }

    }

}
