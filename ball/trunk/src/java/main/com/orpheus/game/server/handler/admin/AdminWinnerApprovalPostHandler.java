/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.orpheus.game.server.OrpheusFunctions;
import com.orpheus.game.persistence.Game;
import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.user.persistence.UserConstants;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.naming.Context;

/**
 * <p>A custom implementation of {@link Handler} interface which is to be used in conjunction with <code>
 * PendingWinnerApprovalHandler</code> and is intended to send the notification email to a game winner as well as
 * prepare a <code>RSS</code> feed to notify the game players that the game have been won.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminWinnerApprovalPostHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the user ID from.</p>
     */
    public static final String USER_ID_PARAM_NAME_CONFIG = "user-id-request-param";

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
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * Creates a AdminWinnerApprovalPostHandler instance configured from the given xml element. It will initialize the
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
    public AdminWinnerApprovalPostHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [handlerElement] is NULL");
        }
        readAsString(element, USER_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, TEMPLATE_FILE_CONFIG, true);
        readAsString(element, MESSAGE_SUBJECT_CONFIG, true);
        readAsString(element, EMAIL_RECIPIENTS_CONFIG, true);
        readAsString(element, FAILURE_RESULT_NAME_CONFIG, true);
        readAsString(element, FAILURE_RESULT_ATTR_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
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
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);

            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            Game game = gameDataEJBAdapter.getGame(gameId);
            // Send an email to game winner
            UserProfile userProfile = this.userProfileManager.getUserProfile(userId);
            String emailAddress = (String) userProfile.getProperty(BaseProfileType.EMAIL_ADDRESS);
            String body = getMessageBody(getString(TEMPLATE_FILE_CONFIG), game);
            String subject = getString(MESSAGE_SUBJECT_CONFIG);
            sendEmail(context, emailAddress, body, subject);
            // Send an email to administrators
            String adminMessageBody = "The claim from player "
                                      + userProfile.getProperty(UserConstants.CREDENTIALS_HANDLE)
                                      + " for winning the game " + game.getName()
                                      + " is approved by Administrator\n\nThe Ball Team";
            String adminMessageSubject = "Game Winner Approved";
            String[] recipients = getString(EMAIL_RECIPIENTS_CONFIG).split(",");
            for (int i = 0; i < recipients.length; i++) {
                sendEmail(context, recipients[i], adminMessageBody, adminMessageSubject);
            }
            // Notify all other players on game winning
            String s = "Congratulations to " + OrpheusFunctions.getHandle(userProfile) + " for winning Ball Game "
                       + game.getName() + "!  This game is now over, but remember, there are still many other chances "
                       + "for you to Find The Ball and WIN SOME MONEY!!!";
            broadcastGameMessage(game, s);
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
     * @param game a <code>Game</code> providing the details for game account.
     * @return a <code>String</code> providing the content of the email body.
     * @throws Exception if an unrecoverable error is reported by document generator.
     */
    private String getMessageBody(String templateFile, Game game) throws Exception {
        // Locate the desired template
        DocumentGenerator docGenerator = DocumentGenerator.getInstance();
        Template template = docGenerator.getTemplate(TEMPLATE_SOURCE_ID, templateFile);

        // Build the list of template fields populated with values from parameters map
        com.topcoder.util.file.fieldconfig.Node[] nodes
            = new com.topcoder.util.file.fieldconfig.Node[1];
        nodes[0] = new Field("GAME_NAME", game.getName(), "game-name", true);
        
        // Generate the message body from template
        TemplateFields fields = new TemplateFields(nodes, template);
        return docGenerator.applyTemplate(fields);
    }
}
