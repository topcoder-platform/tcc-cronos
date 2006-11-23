/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.AuctionPersistence;
import com.topcoder.util.auction.AuctionPersistenceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;

/**
 * <p>
 * Helper class that defines shared helper methods used in this component.
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
    /**
     * <p>
     * This class couldn't be instantiated.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the given object is null.
     * </p>
     * @param obj the object to check.
     * @param paramName the actual parameter name of the argument being checked.
     * @throws IllegalArgumentException if obj is null.
     */
    static final void checkNotNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument " + paramName + " cannot be null");
        }
    }

    /**
     * <p>
     * Checks whether the given string is null or empty.
     * </p>
     * @param str the string to check.
     * @param paramName the actual parameter name of the argument being checked.
     * @throws IllegalArgumentException if string is null or empty.
     */
    static final void checkString(String str, String paramName) {
        checkNotNull(str, paramName);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Argument '" + paramName
                + "' should be non-empty string");
        }
    }

    /**
     * <p>
     * Gets the trimmed string value of the specified property within the configuration. The value
     * cannot be empty string.
     * </p>
     * @param namespace the namespace to get property from.
     * @param propertyName the name of the property to retrieve.
     * @return the specified property from the specified namespace as String object.
     * @throws AuctionLogicConfigException if property doesn't exists, value is empty string or
     *         other configuration exception occurs during retrieving.
     */
    static String getString(String namespace, String propertyName) {
        String res;

        // get value
        try {
            res = ConfigManager.getInstance().getString(namespace, propertyName);
        } catch (UnknownNamespaceException e) {
            throw new AuctionLogicConfigException("The namespace '" + namespace + "' doesn't exist");
        }

        // check that value is not null
        if (res == null) {
            throw new AuctionLogicConfigException("The property '" + propertyName
                + "' doesn't exist");
        }
        // check that value is not empty string
        res = res.trim();
        if (res.length() == 0) {
            throw new AuctionLogicConfigException("The property '" + propertyName
                + "' cannot be empty");
        }

        return res;
    }

    /**
     * <p>
     * Retrieves from the handler element trimmed values of the nested properties.
     * </p>
     * <p>
     * The element is expected to follow the given DTD:
     *
     * <pre>
     * &lt;!ELEMENT handler (children[0], children[1]...children[n])&gt;
     * &lt;!ATTLIST handler type CDATA #REQUIRED&gt;
     * &lt;!ELEMENT children[0] (#PCDATA)&gt;
     * &lt;!ELEMENT children[1] (#PCDATA)&gt;
     * ...
     * &lt;!ELEMENT children[n] (#PCDATA)&gt;
     * </pre>
     *
     * The method doesn't check that element follows DTD but only extracts necessary data from it.
     * </p>
     * @param element the element to extract data from.
     * @param children the names of the child properties.
     * @return the String array that contains values of the child properties. The values are
     *         returned in the same order as it is specified in the 'children' array (i.e. the first
     *         element in the returned array is the value of the first property from the 'children'
     *         array).
     * @throws IllegalArgumentException if element is null, it's impossible to retrieve the
     *         required properties or any value is an empty string.
     */
    static String[] parseHandlerElement(Element element, String[] children) {
        Helper.checkNotNull(element, "element");

        // normalize element
        element.normalize();

        // maps property name to the index in the array
        Map properties = new HashMap();
        for (int i = 0; i < children.length; i++) {
            properties.put(children[i], new Integer(i));
        }

        // resulted array
        String[] res = new String[children.length];

        try {
            // extract child nodes
            NodeList nodes = element.getChildNodes();
            // iterates over the children
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE
                    && properties.containsKey(node.getNodeName())) {
                    // here, node is element and it's value should be retrieved
                    int index = ((Integer) properties.get(node.getNodeName())).intValue();

                    // get first child, it must be the text node that holds required value
                    Node child = node.getFirstChild();
                    if (child == null || child.getNodeType() != Node.TEXT_NODE) {
                        throw new IllegalArgumentException("Tag '" + node.getNodeName()
                            + "' must contain a character data");
                    }

                    // set trimmed value at the required place
                    res[index] = child.getNodeValue();
                }
            }
        } catch (DOMException e) {
            throw new IllegalArgumentException(
                "Unable to retrieve required data from the given element");
        }

        // check result values
        for (int i = 0; i < res.length; i++) {
            if (res[i] == null) {
                throw new IllegalArgumentException("The property '" + children[i]
                    + "' is not defined in the element");
            }

            res[i] = res[i].trim();
            if (res[i].length() == 0) {
                throw new IllegalArgumentException("The value of property property '" + children[i]
                    + "' couldn't be the empty string");
            }
        }

        return res;
    }

    /**
     * <p>
     * Gets the servlet context stored within the given action context.
     * </p>
     * @param context the action context to retrieve servlet context from.
     * @return the servlet context or null if it is not found.
     * @throws HandlerExecutionException if session is not associated with the context's request.
     */
    static ServletContext getServletContext(ActionContext context) throws HandlerExecutionException {
        // get request session
        // do not create new one if it doesn't exist
        HttpSession session = context.getRequest().getSession(false);

        // if it doesn't exist return null
        if (session == null) {
            throw new HandlerExecutionException(
                "Session is not associated with the context's request");
        }

        return session.getServletContext();
    }

    /**
     * <p>
     * Returns the object that is stored as attribute in the servlet context.
     * </p>
     * @param context the servlet context to get object from.
     * @param key the attribute key of the stored object.
     * @param expectedClass the expected class of the stored object.
     * @return the object that is stored as attribute in the servlet context.
     * @throws HandlerExecutionException if the object corresponding to the given key couldn't be
     *         retrieved or of incorrect type.
     */
    static Object getServletContextAttribute(ServletContext context, String key, Class expectedClass)
        throws HandlerExecutionException {
        Object object = context.getAttribute(key);
        // this check also work for null object
        if (!(expectedClass.isInstance(object))) {
            throw new HandlerExecutionException("Unable to get stored instance of the  "
                + expectedClass + " class");
        }
        return object;
    }

    /**
     * <p>
     * Returns the auction with the given id via the specified auction manager.
     * </p>
     * @param auctionManager the auction manager.
     * @param id the id of the required long.
     * @return the non-null auction.
     * @throws HandlerExecutionException if auction is not found or any error occurs during
     *         retrieving.
     */
    static Auction getAuction(AuctionManager auctionManager, long id)
        throws HandlerExecutionException {
        // get auction persistence from the auction manager
        AuctionPersistence auctionPersistence = auctionManager.getAuctionPersistence();
        if (auctionPersistence == null) {
            throw new HandlerExecutionException(
                "The auction persistence must present within auction manager");
        }
        // get auction from persistence
        Auction auction;
        try {
            auction = auctionPersistence.getAuction(id);
        } catch (AuctionPersistenceException e) {
            throw new HandlerExecutionException("Unable to retrieve auction from persistence", e);
        }
        if (auction == null) {
            throw new HandlerExecutionException(
                "Auction with the specified id doesn't exist in persistence");
        }

        return auction;
    }

    /**
     * <p>
     * Returns value of the specified request parameter as int.
     * </p>
     * @param context the action context that holds request.
     * @param key the request parameter name.
     * @return the request parameter value as int.
     * @throws HandlerExecutionException any error occurs during retrieving.
     */
    static int parseIntValue(ActionContext context, String key) throws HandlerExecutionException {
        // get value from the request parameter
        String str = context.getRequest().getParameter(key);
        if (str == null) {
            throw new HandlerExecutionException("Request parameter '" + key + "' is missed");
        }

        // convert from string to long
        int id;
        try {
            id = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Value is not an int number");
        }

        return id;
    }

    /**
     * <p>
     * Returns value of the specified request parameter as long.
     * </p>
     * @param context the action context that holds request.
     * @param key the request parameter name.
     * @return the request parameter value as long.
     * @throws HandlerExecutionException any error occurs during retrieving.
     */
    static long parseLongValue(ActionContext context, String key) throws HandlerExecutionException {
        // get value from the request parameter
        String str = context.getRequest().getParameter(key);
        if (str == null) {
            throw new HandlerExecutionException("Request parameter '" + key + "' is missed");
        }

        // convert from string to long
        long id;
        try {
            id = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Value is not a long number");
        }

        return id;
    }

    /**
     * <p>
     * Retrieves id of the logged user from the given context.
     * </p>
     * @param context action context to get id of the logged used from
     * @return id of the logged user.
     * @throws HandlerExecutionException if there is no logged-in user or any error occurs during
     *         retrieving.
     */
    static long getLoggedUserId(ActionContext context) throws HandlerExecutionException {
        // retrieve id of the currently logged-in user
        // get user profile
        UserProfile userProfile = LoginHandler.getAuthenticatedUser(context.getRequest()
            .getSession());
        if (userProfile == null) {
            throw new HandlerExecutionException("There is no logged-in user");
        }

        // get logged-in user id
        Object id = userProfile.getIdentifier();
        // use user id as bidder id
        if (!(id instanceof Long)) {
            throw new HandlerExecutionException("User id is expected to be Long instance");
        }

        return ((Long) id).longValue();
    }
}
