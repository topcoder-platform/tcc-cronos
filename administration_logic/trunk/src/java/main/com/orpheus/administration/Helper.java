/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.administration.entities.ResultCode;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.HostingSlot;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * This is a common utility class used in this component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Helper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     *
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static final void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException("The '" + name
                    + "' should not be null.");
        }
    }

    /**
     * Checks whether the given String is null/empty.
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the argument
     * @throws IllegalArgumentException
     *             if the given string is empty or given string is null
     * @return checked value back
     */
    public static final String checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        return arg;
    }

    /**
     * Checks whether the given array is null/empty or contains null element.
     *
     * @param arg
     *            the argument to check
     * @return true if ok, or false.
     * @throws IllegalArgumentException
     *             if the given array is null/empty or contains null element.
     */
    public static final boolean checkArrayNullEmptyContainsNullEmpty(
            String[] arg) {
        if (arg == null || arg.length == 0) {
            return false;
        }

        for (int i = 0; i < arg.length; i++) {
            if (arg[i] == null || arg[i].trim().length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the value of specified element.
     *
     * @param element
     *            the element to get value (guaranteeed to be not null)
     *
     * @return the text value of specified element
     * @throws IllegalArgumentException
     *             if element contains no content or the node content is empty
     */
    public static final String getValue(Element element) {
        // The first child text_node be treat as value of element
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE) {
                String value = child.getNodeValue();
                if (value.trim().length() == 0) {
                    throw new IllegalArgumentException(
                            "The element contains empty value.");
                }
                return value;
            }
        }

        throw new IllegalArgumentException("The element contains no content.");
    }

    /**
     * Get the xpath name for element node or attribute node.
     *
     * @param node
     *            the node to get full xpath name
     *
     * @return the full xpath name, for example:
     *         /handler/allowed_profile_types/value
     */
    private static final String getXPathName(Node node) {
        // Ignore non-element or non-attribute node
        if ((node == null)
                || (!(node.getNodeType() == Node.ELEMENT_NODE) && !(node
                        .getNodeType() == Node.ATTRIBUTE_NODE))) {
            return "";
        }

        // Retireve the name of this node
        String name = null;
        Node parent = null;

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            // is Element node
            name = ((Element) node).getTagName();
            parent = node.getParentNode();
        } else {
            // is Attr node
            Attr attr = (Attr) node;
            name = attr.getName();

            // Attribute's parent is owner element
            parent = attr.getOwnerElement();
        }

        // use "/" to seperate every node name
        name = "/" + name;

        if (parent == null) {
            return name;
        }

        return getXPathName(parent) + name;
    }

    /**
     * Get the element with given xpath.
     *
     * @param xpath
     *            the xpath for required element
     * @param element
     *            the element to get from
     *
     * @return the element corresponding to given xpath, null if can not find
     */
    private static final Element getElement(Element element, String xpath) {
        // Compare with given element's xpath
        String fullName = getXPathName(element);

        if (fullName.equals(xpath)) {
            // find it
            return element;
        }

        NodeList nodes = element.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element find = getElement((Element) node, xpath);

                // Find it, simply return
                if (find != null) {
                    return find;
                }
            }
        }

        // can not find
        return null;
    }

    /**
     * This method gets element value according to xpath.
     *
     * <pre>
     *      &lt;handler type=&quot;x&quot;&gt;
     *          &lt;allowed_profile_types&gt;
     *              &lt;value&gt;teacher&lt;/value&gt;
     *              &lt;value&gt;student&lt;/value&gt;
     *          &lt;/allowed_profile_types&gt;
     *
     *          &lt;failure_result&gt;auth_failed_result&lt;/failure_result&gt;
     *      &lt;/handler&gt;
     * </pre>
     *
     * The xpath is: the xpath is:"/handler/failure_result". This method will
     * return "auth_failed_result".
     *
     * @param element
     *            the root element
     * @param xpath
     *            the xpath
     * @return value of the given xpath
     * @throws IllegalArgumentException
     *             if the value retrieve is empty, or the element is missed or
     *             contains no content
     */
    public static final String getValue(Element element, String xpath) {
        Element targetElement = getElement(element, xpath);
        // IllegalArgumentException will be thrown if can not find
        if (targetElement == null) {
            throw new IllegalArgumentException("The xml is invalid. " + xpath
                    + " is missed.");
        }
        return getValue(targetElement);
    }

    /**
     * This method checks if the given Object is instance fo given Class.
     *
     * @param obj
     *            the obj to check
     * @param expected
     *            the expected class type
     * @param name
     *            the obj name
     * @throws IllegalArgumentException
     *             if obj is null, or class type is not given 'type'
     */
    public static final void checkClassType(Class obj, Class expected,
            String name) {
        checkNull(obj, name);
        if (!expected.isAssignableFrom(obj)) {
            throw new IllegalArgumentException("The '" + name
                    + "' should be a " + expected + ", but is:" + obj);
        }
    }

    /**
     * Check if the 'handlerElement' name is handler.
     * @param handlerElement the xml element
     */
    public static final void checkHandlerElement(Element handlerElement) {
        checkNull(handlerElement, "handlerElement");
        if (!"handler".equals(handlerElement.getTagName())) {
            throw new IllegalArgumentException(
                    "The name of 'handlerElement' should be 'handler'.");
        }
    }

    /**
     * Create userProfileManager using ObjectFactory.
     *
     * @param objFactoryNS the obj factory namespace
     * @return a UserProfileManager instance
     */
    public static final UserProfileManager createUserProfileManager(
            String objFactoryNS) {
        try {
            ObjectFactory factory = new ObjectFactory(
                    new ConfigManagerSpecificationFactory(objFactoryNS),
                    ObjectFactory.BOTH);
            return (UserProfileManager) factory
                    .createObject(UserProfileManager.class);
        } catch (SpecificationConfigurationException e) {
            throw new IllegalArgumentException(
                    "Failed to create ConfigManagerSpecificationFactory via namespace:'"
                            + objFactoryNS + "', caused by " + e.getMessage());
        } catch (IllegalReferenceException e) {
            throw new IllegalArgumentException(
                    "Failed to create ConfigManagerSpecificationFactory via namespace:'"
                            + objFactoryNS + "', caused by " + e.getMessage());
        } catch (InvalidClassSpecificationException e) {
            throw new IllegalArgumentException(
                    "Failed to create UserProfileManager, caused by "
                            + e.getMessage());
        }
    }

    /**
     * get sub-property String using propertyName.
     *
     * @param property the property
     * @param propertyName the property name
     * @return the String value
     * @throws ConfigurationException if the value is invalid
     */
    public static final String getSubPropertyString(Property property,
            String propertyName) throws ConfigurationException {
        Property subProperty = property.getProperty(propertyName);
        if (subProperty == null) {
            throw new ConfigurationException(propertyName
                    + " is missing in configuration.");
        }
        String value = subProperty.getValue();
        // Check the missing and empty
        if (((value == null) || (value.trim().length() == 0))) {
            throw new ConfigurationException(propertyName
                    + " is missing in configuration.");
        }

        return value;
    }

    /**
     * Get Property using propertyName and namespace.
     *
     * @param namespace the namespace
     * @param propertyName the property name
     * @return property
     * @throws ConfigurationException if property miss
     */
    public static final Property getProperty(String namespace,
            String propertyName) throws ConfigurationException {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            Property property = (Property) cm.getPropertyObject(namespace,
                    propertyName);
            if (property == null) {
                throw new ConfigurationException("The property:'"
                        + propertyName + "' does not exist.");
            }
            return property;
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The namespace:'" + namespace
                    + "' does not exist.");
        }
    }

    /**
     * Get sub-property value(int).
     *
     * @param jigsawProperty the jigsaw Property
     * @param propertyName the property name
     * @return int value
     * @throws ConfigurationException if value is incorrect.
     */
    public static final int getSubPropertyInt(Property jigsawProperty,
            String propertyName) throws ConfigurationException {
        try {
            String value = getSubPropertyString(jigsawProperty, propertyName);
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("The property:'" + propertyName
                    + "' can not be parsed as a integer.");
        }

    }

    /**
     * This method gets property value as String.
     * The String value is required.
     *
     * @param namespace the namespace
     * @param propertyName property name
     * @return the configed String value
     * @throws ConfigurationException if property is missed or empty
     */
    public static final String getPropertyString(String namespace,
            String propertyName) throws ConfigurationException {
        String value;

        try {
            value = ConfigManager.getInstance().getString(namespace,
                    propertyName);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The namespace '" + namespace
                    + "' is missing in configuration.", e);
        }

        // Check the missing and empty
        if (((value == null) || (value.trim().length() == 0))) {
            throw new ConfigurationException(propertyName
                    + " is missing in configuration.");
        }

        return value;
    }

    /**
     * This method gets property value as int.
     * The configed int value should be positive and is required.
     *
     * @param namespace the namespace
     * @param propertyName the property name
     * @return configed int value
     * @throws ConfigurationException if property is missed, empty or not positive integer
     */
    public static final int getPropertyInt(String namespace, String propertyName)
        throws ConfigurationException {
        String value = getPropertyString(namespace, propertyName);
        int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("The '" + propertyName
                    + "' can not be paresed as a integer.", e);
        }
        if (intValue <= 0) {
            throw new ConfigurationException("The '" + propertyName
                    + "' must be positive.");
        }
        return intValue;
    }

    /**
     * get Admin Data using adminDataJndiName.
     *
     * @param adminDataJndiName adminDataJndiName
     * @param request the HttpServletRequest instance
     * @param failRequestAttrName the failRequestAttrName to set to
     * @return a AdminData instance
     */
    public static final AdminData getAdminData(String adminDataJndiName,
            HttpServletRequest request, String failRequestAttrName) {
        try {
            ServiceLocator sl = new ServiceLocator();
            AdminDataHome home = (AdminDataHome) sl.getRemoteHome(
                    adminDataJndiName, AdminDataHome.class);
            return home.create();
        } catch (ServiceLocatorException e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get AdminData", failRequestAttrName, e);
            return null;
        } catch (RemoteException e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get AdminData", failRequestAttrName, e);
            return null;
        } catch (CreateException e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get AdminData", failRequestAttrName, e);
            return null;
        }
    }
    /**
     * get GameData using adminDataJndiName.
     *
     * @param gameDataJndiName gameDataJndiName
     * @param request the HttpServletRequest instance
     * @param failRequestAttrName the failRequestAttrName to set to
     * @return a GameData instance
     */
    public static final GameData getGameData(String gameDataJndiName,
            HttpServletRequest request, String failRequestAttrName) {
        try {
            ServiceLocator sl = new ServiceLocator();
            GameDataHome home = (GameDataHome) sl.getRemoteHome(
                    gameDataJndiName, GameDataHome.class);
            return home.create();
        } catch (ServiceLocatorException e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get GameData", failRequestAttrName, e);
            return null;
        } catch (RemoteException e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get GameData", failRequestAttrName, e);
            return null;
        } catch (CreateException e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to get GameData", failRequestAttrName, e);
            return null;
        }
    }

    /**
     * Process the failure condition: Exception occur.
     *
     * @param request the HttpServletRequest
     * @param msg the message set to request
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param e the Exception meet
     */
    public static final void processFailureExceptionOccur(
            HttpServletRequest request, String msg, String failRequestAttrName,
            Exception e) {
        HandlerResult handlerResult = new HandlerResult(
                ResultCode.EXCEPTION_OCCURRED, msg, e);
        request.setAttribute(failRequestAttrName, handlerResult);
    }
    /**
     * Process the failure condition: Parameter Not Given.
     *
     * @param request the HttpServletRequest
     * @param parameterName the parameterName missing
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureParameterNotGiven(
            HttpServletRequest request, String failRequestAttrName,
            String parameterName) {
        HandlerResult handlerResult = new HandlerResult(
                ResultCode.MISSING_PARAMETERS, "Parameter " + parameterName
                        + " not given.");
        request.setAttribute(failRequestAttrName, handlerResult);
    }
    /**
     * Process the failure condition: Illegal Long.
     *
     * @param request the HttpServletRequest
     * @param parameterName the parameterName missing
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    private static final void processFailureIllegalLong(
            HttpServletRequest request, String failRequestAttrName,
            String parameterName) {
        processFailureDataParseError(request, ResultCode.PARAMETER_NOT_LONG,
                failRequestAttrName, parameterName, "long");
    }
    /**
     * Process the failure condition: Illegal Integer.
     *
     * @param request the HttpServletRequest
     * @param parameterName the parameterName missing
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    private static final void processFailureIllegalInteger(
            HttpServletRequest request, String failRequestAttrName,
            String parameterName) {
        processFailureDataParseError(request, ResultCode.PARAMETER_NOT_INTEGER,
                failRequestAttrName, parameterName, "integer");
    }
    /**
     * Process the failure condition: Illegal Date.
     *
     * @param request the HttpServletRequest
     * @param parameterName the parameterName missing
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    private static final void processFailureIllegalDate(
            HttpServletRequest request, String failRequestAttrName,
            String parameterName) {
        processFailureDataParseError(request, ResultCode.PARAMETER_NOT_DATE,
                failRequestAttrName, parameterName, "Date");
    }
    /**
     * Process the failure condition: Data Parse Error.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param dataType data Type
     * @param parameterName parameter name
     * @param code ResultCode
     */
    private static final void processFailureDataParseError(
            HttpServletRequest request, ResultCode code,
            String failRequestAttrName, String parameterName, String dataType) {
        processFailure(request, code, failRequestAttrName,
                "Unable to parse parameter " + parameterName + " into "
                        + dataType);
    }

    /**
     * Process the failure condition: No Matching Ball Color.
     *
     * @param request the HttpServletRequest
     * @param msg the message to set
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureNoMatchingBallColor(
            HttpServletRequest request, String failRequestAttrName, String msg) {
        processFailure(request, ResultCode.NO_MATCHING_BALLCOLOR,
                failRequestAttrName, msg);
    }
    /**
     * Process the failure condition: No Matching Pending Winner.
     *
     * @param request the HttpServletRequest
     * @param msg the message to set
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureNoMatchingPendingWinner(
            HttpServletRequest request, String failRequestAttrName, String msg) {
        processFailure(request, ResultCode.NO_MATCHING_PENDING_WINNER ,
                failRequestAttrName, msg);
    }
    /**
     * Process the failure condition: Block Info Length Not Match.
     *
     * @param request the HttpServletRequest
     * @param requiredLength the required Length
     * @param actualLength the actual Length
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureBlockInfoLengthNotMatch(
            HttpServletRequest request, String failRequestAttrName,
            int requiredLength, int actualLength) {
        processFailure(
                request,
                ResultCode.BLOCK_INFO_LENGTH_NOT_MATCH,
                failRequestAttrName,
                "the blockInfo in request does not match block count in session, the reqired length is:"
                        + requiredLength
                        + ", but actual length is:"
                        + actualLength);
    }
    /**
     * Process the failure condition: Image Not Belong To Domain.
     *
     * @param request the HttpServletRequest
     * @param imageId the image Id
     * @param domainId the domain Id
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureImageNotBelongToDomain(
            HttpServletRequest request, String failRequestAttrName,
            long imageId, long domainId) {
        processFailure(request, ResultCode.IMAGE_NOT_BELONG_TO_DOMAIN,
                failRequestAttrName, "image id:" + imageId
                        + " not belong to domain id:" + domainId);
    }
    /**
     * Process the failure condition: Cannot Move Slot Beyond Last.
     *
     * @param request the HttpServletRequest
     * @param msg the message to set
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureCannotMoveSlotBeyondLast(
            HttpServletRequest request, String failRequestAttrName, String msg) {
        processFailure(request, ResultCode.CANNOT_MOVE_SLOT_BEYOND_LAST,
                failRequestAttrName, msg);
    }
    /**
     * Process the failure condition: Winner Not First.
     *
     * @param request the HttpServletRequest
     * @param msg the message to set
     * @param failRequestAttrName the failRequestAttrName to set to
     */
    public static final void processFailureWinnerNotFirst(
            HttpServletRequest request, String failRequestAttrName, String msg) {
        processFailure(request, ResultCode.WINNER_NOT_FIRST,
                failRequestAttrName, msg);
    }
    /**
     * Process the failure condition: Approval Not Pending.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param sponsorId the sponsor Id
     */
    public static final void processFailureApprovalNotPending(
            HttpServletRequest request, String failRequestAttrName,
            long sponsorId) {
        processFailure(request, ResultCode.APPROVAL_NOT_PENDING,
                failRequestAttrName, "sponsor(id:" + sponsorId
                        + ") is approval not pending");
    }
    /**
     * Process the failure condition: Sponsor Not elong To Domain.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param sponsorId sponsor Id
     * @param domainId domain Id
     */
    public static final void processFailureSponsorNotBelongToDomain(
            HttpServletRequest request, String failRequestAttrName,
            long sponsorId, long domainId) {
        processFailure(request, ResultCode.SPONSOR_NOT_BELONG_TO_DOMAIN,
                failRequestAttrName, "sponsor id:" + sponsorId
                        + " not belong to domain id:" + domainId);
    }
    /**
     * Process the failure condition: Sponsor Slot Finished Hosting.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param slotId slot Id
     */
    public static final void processFailureSlotFinishedHosting(
            HttpServletRequest request, String failRequestAttrName, Long slotId) {
        processFailure(request, ResultCode.SLOT_FINISHED_HOSTING,
                failRequestAttrName, "slot:" + slotId + " finished hosting");
    }
    /**
     * Process the failure condition: Slot Started Hosting.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param slotId slot Id
     */
    public static final void processFailureSlotStartedHosting(
            HttpServletRequest request, String failRequestAttrName, Long slotId) {
        processFailure(request, ResultCode.SLOT_STARTED_HOSTING,
                failRequestAttrName, "slot:" + slotId + " started hosting");
    }
    /**
     * Process the failure condition.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param msg message to set
     * @param code ResultCode
     */
    private static final void processFailure(HttpServletRequest request,
            ResultCode code, String failRequestAttrName, String msg) {
        HandlerResult handlerResult = new HandlerResult(code, msg);
        request.setAttribute(failRequestAttrName, handlerResult);
    }
    /**
     * Parse Long.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param parameterName parameter name
     * @param source source
     * @return Long value
     */
    public static final Long parseLong(HttpServletRequest request,
            String source, String parameterName, String failRequestAttrName) {
        try {
            return Long.valueOf(source);
        } catch (NumberFormatException e) {
            Helper.processFailureIllegalLong(request, failRequestAttrName,
                    parameterName);
            return null;
        }
    }
    /**
     * Parse Integer.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param parameterName parameter name
     * @param source source
     * @return Integer value
     */
    public static final Integer parseInteger(HttpServletRequest request,
            String source, String parameterName, String failRequestAttrName) {
        try {
            return Integer.valueOf(source);
        } catch (NumberFormatException e) {
            Helper.processFailureIllegalInteger(request, failRequestAttrName,
                    parameterName);
            return null;
        }
    }
    /**
     * Parse Date.
     *
     * @param request the HttpServletRequest
     * @param failRequestAttrName the failRequestAttrName to set to
     * @param parameterName parameter name
     * @param source source
     * @param dateFormat dateFormat
     * @return Date value
     */
    public static final Date parseDate(HttpServletRequest request,
            DateFormat dateFormat, String source, String parameterName,
            String failRequestAttrName) {
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            Helper.processFailureIllegalDate(request, failRequestAttrName,
                    parameterName);
            return null;
        }
    }
    /**
     * Get HttpSession.
     *
     * @param request the HttpServletRequest
     * @return HttpSession instance
     * @throws HandlerExecutionException if session missing
     */
    public static final HttpSession getSession(HttpServletRequest request)
        throws HandlerExecutionException {
        HttpSession session = request.getSession(false);
        // check if session is present
        if (session == null) {
            throw new HandlerExecutionException(
                    "There is no session associate with current request.");
        }
        return session;
    }
    /**
     * Get UserProfile of sponsor.
     *
     * @param request the HttpServletRequest
     * @param sponsorId the sponsor Id
     * @param userProfileManager the userProfileManager
     * @param failRequestAttrName the failRequestAttrName
     * @return UserProfile instance
     */
    public static final UserProfile getSponsor(long sponsorId,
            HttpServletRequest request, UserProfileManager userProfileManager,
            String failRequestAttrName) {
        synchronized (userProfileManager) {

            try {
                return userProfileManager.getUserProfile(sponsorId);
            } catch (UnknownProfileException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to retrieve user profile using userId:"
                                + sponsorId, failRequestAttrName, e);
                return null;
            } catch (UserProfileManagerException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to retrieve user profile using userId:"
                                + sponsorId, failRequestAttrName, e);
                return null;
            }

        }
    }
    /**
     * Check Slot for started or finished by comparing with now.
     *
     * @param request the HttpServletRequest
     * @param slot the slot
     * @param failRequestAttrName the failRequestAttrName
     * @return false if started or finished, or return true
     */
    public static final boolean checkSlotForStartedOrFinished(HostingSlot slot,
            HttpServletRequest request, String failRequestAttrName) {
        Long slotId = slot.getId();
        if (slot.getHostingEnd().compareTo(new Date()) <= 0) {
            // hosting end is before now
            Helper.processFailureSlotFinishedHosting(request,
                    failRequestAttrName, slotId);
            return false;
        }
        if (slot.getHostingStart().compareTo(new Date()) <= 0) {
            // hosting start is after now
            Helper.processFailureSlotStartedHosting(request,
                    failRequestAttrName, slotId);
            return false;
        }
        return true;
    }
    /**
     * Check is sponsor is approval.
     *
     * @param sponsor the sponsor
     * @return true if true if approval
     */
    public static final boolean checkApproval(UserProfile sponsor) {
        String approved = (String) sponsor.getProperty("IS_APPROVED");
        return approved != null && approved.trim().length() != 0;
    }

    /**
     * Return a new HostingSlot implement and copy all data from the old slot
     * instance using getId(), getDomain(), getImageId(), getBrainTeaserIds(),
     * getDomainTargets(), getWinningBid(), getHostingStart(), getHostingEnd()
     * and given sequenceNumber.
     *
     * @param oldSlot
     *            old HostingSlot
     * @param sequenceNumber
     *            the sequenceNumber
     * @return new HostingSlot implement
     */
    public static final HostingSlotImpl copySlot(HostingSlot oldSlot,
            int sequenceNumber) {
        // create new HostingSlotImpl
        HostingSlotImpl newSlot = new HostingSlotImpl();
        newSlot.setId(oldSlot.getId());
        newSlot.setDomain(oldSlot.getDomain());
        newSlot.setImageId(oldSlot.getImageId());
        newSlot.setBrainTeaserIds(oldSlot.getBrainTeaserIds());
        newSlot.setPuzzleId(oldSlot.getPuzzleId());
        newSlot.setDomainTargets(oldSlot.getDomainTargets());
        newSlot.setWinningBid(oldSlot.getWinningBid());
        newSlot.setHostingStart(oldSlot.getHostingStart());
        newSlot.setHostingEnd(oldSlot.getHostingEnd());
        newSlot.setSequenceNumber(sequenceNumber);
        return newSlot;
    }

    /**
     * Get request parameter. Return an the parameter value, null if the request value does not exist.
     *
     * @param request the HttpServletRequest
     * @param parameterName the parameter name
     * @param failRequestAttrName the failRequestAttrName
     * @return an the parameter value, null if the request value does not exist.
     */
    public static final String getRequestParameter(HttpServletRequest request,
            String parameterName, String failRequestAttrName) {
        String value = request.getParameter(parameterName);
        if (value == null || value.trim().length() == 0) {
            processFailureParameterNotGiven(request, failRequestAttrName,
                    parameterName);
            return null;
        }
        return value;
    }
}
