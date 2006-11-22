/*
 * Cloud.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.FeedNode;


/**
 * <p>
 * Cloud class models the cloud node.
 * </p>
 * 
 * <p>
 * Note that passing a null value to the setter methods will remove the attribute from the node. The getter methods
 * return null if the attribute does not exist.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public class Cloud extends FeedNode {
    /**
     * <p>
     * Create a cloud node with the values of its attributes. If the attribute value is null or empty string, the
     * attribute will not be added.
     * </p>
     *
     * @param domain the domain attribute.
     * @param port the port attribute.
     * @param path the path attribute.
     * @param registerProcedure the registerProcedure attribute.
     * @param protocol the protocol attribute.
     */
    public Cloud(String domain, Integer port, String path, String registerProcedure, String protocol) {
        super(RSSConstants.CLOUD);

        // only add the attribute if value is non-null and non-empty
        if (!isNullOrEmpty(domain)) {
            addAttribute(RSSConstants.DOMAIN, domain);
        }
        if (port != null) {
            addAttribute(RSSConstants.PORT, port.toString());
        }
        if (!isNullOrEmpty(path)) {
            addAttribute(RSSConstants.PATH, path);
        }
        if (!isNullOrEmpty(registerProcedure)) {
            addAttribute(RSSConstants.REGISTER_PROCEDURE, registerProcedure);
        }
        if (!isNullOrEmpty(protocol)) {
            addAttribute(RSSConstants.PROTOCOL, protocol);
        }
    }

    /**
     * <p>
     * Create a new cloud node with no attributes.
     * </p>
     */
    public Cloud() {
        super(RSSConstants.CLOUD);
    }

    /**
     * <p>
     * Return the domain attribute value.
     * </p>
     *
     * @return the domain attribute value.
     */
    public String getDomain() {
        return getAttribute(RSSConstants.DOMAIN);
    }

    /**
     * <p>
     * Set the domain attribute value.
     * </p>
     *
     * @param domain the domain value.
     *
     * @throws IllegalArgumentException if domain is empty.
     */
    public void setDomain(String domain) {
        setAttribute(RSSConstants.DOMAIN, domain);
    }

    /**
     * <p>
     * Return the port attribute value.
     * </p>
     *
     * @return the port attribute value.
     */
    public Integer getPort() {
        return getAttributeAsInteger(RSSConstants.PORT);
    }

    /**
     * <p>
     * Set the port attribute value.
     * </p>
     *
     * @param port the port attribute value.
     */
    public void setPort(Integer port) {
        setAttribute(RSSConstants.PORT, port);
    }

    /**
     * <p>
     * Return the path attribute value.
     * </p>
     *
     * @return the path attribute value.
     */
    public String getPath() {
        return getAttribute(RSSConstants.PATH);
    }

    /**
     * <p>
     * Set the path attribute value.
     * </p>
     *
     * @param path the path attribute value.
     *
     * @throws IllegalArgumentException if path is empty.
     */
    public void setPath(String path) {
        setAttribute(RSSConstants.PATH, path);
    }

    /**
     * <p>
     * Return the registerProcedure attribute value.
     * </p>
     *
     * @return the registerProcedure attribute value.
     */
    public String getRegisterProcedure() {
        return getAttribute(RSSConstants.REGISTER_PROCEDURE);
    }

    /**
     * <p>
     * Set the registerProcedure attribute value.
     * </p>
     *
     * @param registerProcedure the registerProcedure attribute value.
     *
     * @throws IllegalArgumentException if registerProcedure is empty.
     */
    public void setRegisterProcedure(String registerProcedure) {
        setAttribute(RSSConstants.REGISTER_PROCEDURE, registerProcedure);
    }

    /**
     * <p>
     * Return the protocol attribute value.
     * </p>
     *
     * @return the protocol attribute value.
     */
    public String getProtocol() {
        return getAttribute(RSSConstants.PROTOCOL);
    }

    /**
     * <p>
     * Set the protocol attribute value.
     * </p>
     *
     * @param protocol the protocol attribute value.
     *
     * @throws IllegalArgumentException if protocol is empty.
     */
    public void setProtocol(String protocol) {
        setAttribute(RSSConstants.PROTOCOL, protocol);
    }

    /**
     * <p>
     * Common operations shared by the getter methods. The key argument must not be null.
     * </p>
     * 
     * <p>
     * If there is no attribute with the given key, return null.
     * </p>
     * 
     * <p>
     * Otherwise, return the Integer form of the attribute value.
     * </p>
     *
     * @param key the key of attribute.
     *
     * @return the attribute value as an Integer, or null if there is no match.
     */
    private Integer getAttributeAsInteger(String key) {
        String value = getAttribute(key);

        // if there is no such attribute, return null
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * <p>
     * Common operations shared by the setter methods. The key argument must not be null.
     * </p>
     * 
     * <p>
     * If value is null, remove the attribute.
     * </p>
     * 
     * <p>
     * If value is empty string, throw IllegalArgumentException.
     * </p>
     * 
     * <p>
     * Otherwise, add the attribute to the node.
     * </p>
     *
     * @param key the key of attribute.
     * @param value the value of attribute.
     *
     * @throws IllegalArgumentException if value is empty.
     */
    private void setAttribute(String key, String value) {
        if (value == null) {
            removeAttribute(key);
        } else if (value.trim().length() == 0) {
            throw new IllegalArgumentException("value is empty");
        } else {
            addAttribute(key, value);
        }
    }

    /**
     * <p>
     * Common operations shared by the setter methods. The key argument must not be null.
     * </p>
     * 
     * <p>
     * If value is null, remove the attribute.
     * </p>
     * 
     * <p>
     * Otherwise, add the attribute in string form to the node.
     * </p>
     *
     * @param key the key of attribute.
     * @param value the value of attribute.
     */
    private void setAttribute(String key, Integer value) {
        String str = (value == null) ? null : value.toString();

        setAttribute(key, str);
    }

    /**
     * <p>
     * Checks if the given string is null or empty.
     * </p>
     *
     * @param str the string to check against.
     *
     * @return true if str is null or empty, false otherwise.
     */
    private static boolean isNullOrEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }
}
