/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.game.server.admin.OrpheusAdminFunctions;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom implementation of {@link Handler} interface which is used by <code>Orpheus Game Server Administration
 * </code> module for sending emails to sponsors or winners in case the sponsor's account, domain, image or winner's
 * claim for winnnig a game is rejected for some reason.</p>
 *
 * <p>The purpose of this handler is to locate the desired user profile, construct appropriate email body and put
 * message details to action context to be re-used by subsequent message handler which will send a message to intended
 * recipient. Currently there is no such handler provided by any of existing components.</p>
 *
 * @author isv
 * @version 1.0
 */
public class UserMessagePreHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user ID from.</p>
     */
    public static final String USER_ID_PARAM_NAME_CONFIG = "user-id-request-param";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the rejection message from.</p>
     */
    public static final String REJECT_MESSAGE_PARAM_NAME_CONFIG = "reject-message-param";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the rejection message from.</p>
     */
    public static final String REJECT_REASON_PARAM_NAME_CONFIG = "reject-reason-param";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user ID from.</p>
     */
    public static final String FAILURE_RESULT_ATTR_NAME_CONFIG = "fail-request-attribute";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user ID from.</p>
     */
    public static final String FAILURE_RESULT_NAME_CONFIG = "fail-result";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user ID from.</p>
     */
    public static final String MESSAGE_SUBJECT_CONFIG = "message-subject";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user ID from.</p>
     */
    public static final String TEMPLATE_FILE_CONFIG = "template-file";

    /**
     * <p>A <code>UserProfileManager</code> instance which will be used to search for sponsor.</p>
     */
    private final UserProfileManager userProfileManager;

    /**
     * Creates a UserMessagePreHandler instance configured from the given xml element. It will initialize the
     * userProfileManager instance and other instance variables. It will throw an IllegalArgumentException if
     * configuration details are missing in the handlerElement argument.<br/> Impl Notes: <ol> <li>If
     * handlerElement.getTagName() is not &quot;handler&quot;, throw an IllegalArgumentException.</li> <li>Initialize
     * objFactoryNS with the value of &quot;object-factory-ns&quot; child element. If element or value is missing throw
     * an IllegalArgumentException.</li> <li>ObjectFactory factory = new ObjectFactory(new
     * ConfigManagerSpecificationFactory(objFactoryNS), ObjectFactory.BOTH);<br/> userProfileManager =
     * objectFactory.createObject(&quot;UserProfileManager&quot;);</li> <li>Initialize sponsorIdRequestParamName with
     * the value of &quot;sponsor-id-request-param&quot; child element. If element or value is missing throw an
     * IllegalArgumentException.</li> <li>Initialize failedResult with the value of &quot;fail-result&quot; child
     * element. If element or value is missing throw an IllegalArgumentException.</li> <li>Initialize
     * failRequestAttrName with the value of &quot;fail-request-attribute&quot; child element. If element or value is
     * missing throw an IllegalArgumentException.</li> </ol>
     *
     * @param element the XML element containing configuration for this handler.
     * @throws IllegalArgumentException if handlerElement is null, or contains invalid data.
     */
    public UserMessagePreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [handlerElement] is NULL");
        }
        readAsString(element, USER_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, TEMPLATE_FILE_CONFIG, true);
        readAsString(element, MESSAGE_SUBJECT_CONFIG, true);
        readAsString(element, REJECT_REASON_PARAM_NAME_CONFIG, true);
        readAsString(element, REJECT_MESSAGE_PARAM_NAME_CONFIG, true);
        readAsString(element, FAILURE_RESULT_NAME_CONFIG, true);
        readAsString(element, FAILURE_RESULT_ATTR_NAME_CONFIG, true);
        this.userProfileManager = getUserProfileManager(element);
    }

    /**
     * <p>Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers.</p>
     *
     * @param context the ActionContext object.
     * @return null or a non-empty resultCode string.
     * @throws HandlerExecutionException if fail to execute this handler.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();

        try {
            long userId = getLong(USER_ID_PARAM_NAME_CONFIG, request);

            UserProfile userProfile = this.userProfileManager.getUserProfile(userId);
            String emailAddress = (String) userProfile.getProperty(BaseProfileType.EMAIL_ADDRESS);
            String body = getMessageBody(getString(TEMPLATE_FILE_CONFIG), userProfile,
                                         request.getParameter(getString(REJECT_REASON_PARAM_NAME_CONFIG)),
                                         request.getParameter(getString(REJECT_MESSAGE_PARAM_NAME_CONFIG)));
            context.setAttribute("to", new String[] {emailAddress});
            context.setAttribute("subject", getString(MESSAGE_SUBJECT_CONFIG));
            context.setAttribute("body", body);
        } catch (Exception e) {
            HandlerResult handlerResult
                = new HandlerResult(ResultCode.EXCEPTION_OCCURRED,
                                    "Could not prepare message for user ["
                                    + getLong(USER_ID_PARAM_NAME_CONFIG, request) + "]", e);
            request.setAttribute(getString(FAILURE_RESULT_ATTR_NAME_CONFIG), handlerResult);
            return getString(FAILURE_RESULT_NAME_CONFIG);
        }

        // return null for successful execution.
        return null;
    }

    /**
     * <p>Generates the body of an email message to be sent to user.</p>
     *
     * @param templateFile a <code>String</code> providing the name of the template file.
     * @param userProfile a <code>UserProfile</code> providing the details for user account.
     * @param reason a <code>String</code> providing the reject reason.
     * @param message a <code>String</code> provuiding the reject message.
     * @return a <code>String</code> providing the content of the email body.
     * @throws Exception if an unrecoverable error is reported by document generator.
     */
    private String getMessageBody(String templateFile, UserProfile userProfile, String reason, String message)
        throws Exception {
        // Locate the desired template
        DocumentGenerator docGenerator = DocumentGenerator.getInstance();
        Template template = docGenerator.getTemplate(TEMPLATE_SOURCE_ID, templateFile);

        // Build the list of template fields populated with values from parameters map
        com.topcoder.util.file.fieldconfig.Node[] nodes
            = new com.topcoder.util.file.fieldconfig.Node[3];
        nodes[0] = new Field("credentials-handle", OrpheusAdminFunctions.getHandle(userProfile), "credentials-handle",
                             true);
        nodes[1] = new Field("reject-reason", reason, "reject-reason", true);
        nodes[2] = new Field("reject-message", message, "reject-message", true);

        // Generate the message body from template
        TemplateFields fields = new TemplateFields(nodes, template);
        return docGenerator.applyTemplate(fields);
    }
}
