/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Map;

import org.w3c.dom.Element;

import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;


/**
 * <p>
 * <code>TestTargetObjectHandler</code> class implements the <code>Handler</code> interface from the
 * <code>FrontController</code> component. It tests target objects to determine whether they are correct. The current
 * game ID, domain name, target sequence number, and the UTF-8 encoded text from which the hash was computed will be
 * read from request parameters of configurable name. The handler finds the hosting slot corresponding to the provided
 * game ID and domain for the logged-in player via the game persistence component, and will use the provided sequence
 * number to choose from it the domain target object to be compared with the provided text. It will return a
 * configurable result string if the texts differ, or will return null if they match.
 *
 * To follow the <code>Handler</code> implementation's contract in order to make it work properly with
 * <code>FrontController</code> component, two constructors are defined, one takes the configurable parameters, and
 * the other takes an <code>Element</code> object which contains all configurable parameters.
 *
 * Thread-safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class TestTargetObjectHandler implements Handler {

    /**
     * <p>
     * Represents the key used to get the game id from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String gameIdParamKey;

    /**
     * <p>
     * Represents the key used to get the domain name from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String domainNameParamKey;

    /**
     * <p>
     * Represents the key used to get the sequence number from the request parameter in execute method. It is
     * initialized in constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String sequenceNumberParamKey;

    /**
     * <p>
     * Represents the key used to get the text from the request parameter in execute method. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String textParamKey;

    /**
     * <p>
     * Represents the result code to return in the execute method if the test failed. It is initialized in constructor
     * and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String testFailedResultCode;

    /**
     * <p>
     * Represents the result code to return in the execute method if the user is not logged-in. It is initialized in
     * constructor and never changed afterwards. It must be non-null, non-empty string.
     * </p>
     */
    private final String notLoggedInResultCode;

    /**
     * <p>
     * Constructor with a Map of attributes, this constructor will extract values from the attributes Map, and then
     * assign to corresponding instance variable. The map should contain keys gameIdParamKey, domainNameParamKey,
     * sequenceNumberParamKey, textParamKey, testFailedResultCode and notLoggedInResultCode.
     * </p>
     *
     * @param attributes
     *            a Map of attributes to initialize this handler.
     * @throws IllegalArgumentException
     *             if the argument is null or any value above is missing or null/empty string.
     */
    public TestTargetObjectHandler(Map attributes) {
        ImplementationHelper.checkObjectValid(attributes, "attributes");
        gameIdParamKey = ImplementationHelper.getString(attributes, "gameIdParamKey");
        domainNameParamKey = ImplementationHelper.getString(attributes, "domainNameParamKey");
        notLoggedInResultCode = ImplementationHelper.getString(attributes, "notLoggedInResultCode");
        sequenceNumberParamKey = ImplementationHelper.getString(attributes, "sequenceNumberParamKey");
        testFailedResultCode = ImplementationHelper.getString(attributes, "testFailedResultCode");
        textParamKey = ImplementationHelper.getString(attributes, "textParamKey");
    }

    /**
     * <p>
     * Constructor with an xml Element object. The constructor will extract necessary configurable values from this xml
     * element.
     *
     * Here is what the xml element likes:
     *
     * <pre>
     *
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *      &lt;domain_name_param_key&gt;domainName&lt;/domain_name_param_key&gt;
     *      &lt;sequence_number_param_key&gt;seqNo&lt;/sequence_number_param_key&gt;
     *      &lt;text_param_key&gt;text&lt;/text_param_key&gt;
     *      &lt;test_failed_result_code&gt;test_failed&lt;/test_failed_result_code&gt;
     *      &lt;not_logged_in_result_code&gt;not_logged_in&lt;/not_logged_in_result_code&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element
     *            the xml Element to extract the configurable values.
     * @throws IllegalArgumentException
     *             if the argument is null or any value mentioned in implementation note is missing or empty string.
     */
    public TestTargetObjectHandler(Element element) {
        ImplementationHelper.checkObjectValid(element, "element");
        gameIdParamKey = ImplementationHelper.getElement(element, "game_id_param_key");
        domainNameParamKey = ImplementationHelper.getElement(element, "domain_name_param_key");
        notLoggedInResultCode = ImplementationHelper.getElement(element, "not_logged_in_result_code");
        sequenceNumberParamKey = ImplementationHelper.getElement(element, "sequence_number_param_key");
        testFailedResultCode = ImplementationHelper.getElement(element, "test_failed_result_code");
        textParamKey = ImplementationHelper.getElement(element, "text_param_key");
    }

    /**
     * <p>
     * It tests target objects to determine whether they are correct. The current game ID, domain name, target sequence
     * number, and the UTF-8 encoded text from which the hash was computed will be read from request parameters of
     * configurable name. It finds the hosting slot corresponding to the provided game ID and domain for the logged-in
     * player via the game persistence component, and will use the provided sequence number to choose from it the domain
     * target object to be compared with the provided text.
     *
     * The <code>LoginHandler</code> will be used to get logged-in user's details, and a proper resultCode is returned
     * if user is not logged in, and a test failed result code will be returned if the test mentioned fails, otherwise
     * null will be returned if the test succeeds.
     * </p>
     *
     * @param context
     *            the ActionContext object.
     * @return proper result codes if the test fails or the user is not logged in, or return null if the test succeeds.
     * @throws IllegalArgumentException
     *             if the given argument is null.
     * @throws HandlerExecutionException
     *             if fail to get required attributes from LoginHandler or request parameter, or GameDataHelper
     *             operation fails.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        ImplementationHelper.checkObjectValid(context, "context");
        GameDataHelper helper = GameDataHelper.getInstance();
        try {
            // gets the user profile from the LoginHandler.
            UserProfile userProfile = LoginHandler.getAuthenticatedUser(context.getRequest().getSession(true));
            // if the user profile is missing return the result code.
            if (userProfile == null) {
                return notLoggedInResultCode;
            }
            long profileId = ((Long) userProfile.getIdentifier()).longValue();
            // gets the domain name, sequence number and text from the request parameter.
            String domainName = context.getRequest().getParameter(domainNameParamKey);
            checkValidString(domainName, "domain name");
            String seqNum = context.getRequest().getParameter(sequenceNumberParamKey);
            checkValidString(seqNum, "sequence number");
            String text = context.getRequest().getParameter(textParamKey);
            checkValidParam(text, "text");
            // parses the game id from the request parameter.
            long gameId = Long.parseLong(context.getRequest().getParameter(gameIdParamKey));

            // gets the hosting slot using the helper.
            HostingSlot slot = helper.findSlotForDomain(gameId, profileId, domainName);
            if (slot == null) {
                throw new HandlerExecutionException(
                        "Failed to execute test target object handler., No hosting slot present");
            }
            DomainTarget[] domainTargets = slot.getDomainTargets();
            for (int i = 0; i < domainTargets.length; i++) {
                // if the sequence number and text matches return null.
                if (Integer.parseInt(seqNum) == domainTargets[i].getSequenceNumber()
                        && text.equals(domainTargets[i].getIdentifierText().replaceAll("[\n\r \t\f\u200b]+", ""))) {
                    return null;
                }
            }
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to execute test target object handler.", castException);
        } catch (NumberFormatException formatException) {
            throw new HandlerExecutionException("Failed to execute test target object handler.", formatException);
        } catch (IllegalArgumentException exception) {
            // session is null.
            return notLoggedInResultCode;
        }
        // if no match return failed result code.
        return testFailedResultCode;
    }

    /**
     * Checks the parameter given is not null.
     *
     * @param str
     *            request parameter value to test.
     * @param name
     *            name of the parameter
     * @throws HandlerExecutionException
     *             if the string is null.
     */
    private void checkValidString(String str, String name) throws HandlerExecutionException {
        checkValidParam(str, name);
        if (str.trim().length() == 0) {
            throw new HandlerExecutionException("Failed to execute test target object handler, invalid '" + name
                    + "' in request.");
        }
    }

    /**
     * Checks the string is a valid string.
     *
     * @param str
     *            string to test.
     * @param name
     *            parameter name.
     * @throws HandlerExecutionException
     *             if the string is null or empty.
     */
    private void checkValidParam(String str, String name) throws HandlerExecutionException {
        if (str == null) {
            throw new HandlerExecutionException("Failed to execute test target object handler, missing '" + name
                    + "' in request.");
        }
    }
}
