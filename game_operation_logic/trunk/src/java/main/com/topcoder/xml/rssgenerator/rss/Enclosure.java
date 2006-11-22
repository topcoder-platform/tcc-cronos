/*
 * Enclosure.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.FeedNode;


/**
 * <p>
 * Enclosure class models the enclosure node.
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
public class Enclosure extends FeedNode {
    /**
     * <p>
     * Create a new enclosure node with the attribute values. If the attribute value is null or empty string, the
     * attribute will not be added.
     * </p>
     *
     * @param url the url attribute value.
     * @param length the length attribute value.
     * @param type the type attribute value.
     */
    public Enclosure(String url, Integer length, String type) {
        super(RSSConstants.ENCLOSURE);

        // only add the attribute if value is non-null and non-empty
        if (!isNullOrEmpty(url)) {
            addAttribute(RSSConstants.URL, url);
        }
        if (length != null) {
            addAttribute(RSSConstants.LENGTH, length.toString());
        }
        if (!isNullOrEmpty(type)) {
            addAttribute(RSSConstants.TYPE, type);
        }
    }

    /**
     * <p>
     * Create a new enclosure node with no attributes.
     * </p>
     */
    public Enclosure() {
        super(RSSConstants.ENCLOSURE);
    }

    /**
     * <p>
     * Return the url attribute value.
     * </p>
     *
     * @return the url attribute value.
     */
    public String getUrl() {
        return getAttribute(RSSConstants.URL);
    }

    /**
     * <p>
     * Set the url attribute value.
     * </p>
     *
     * @param url the url attribute to set.
     *
     * @throws IllegalArgumentException if url is empty.
     */
    public void setUrl(String url) {
        setAttribute(RSSConstants.URL, url);
    }

    /**
     * <p>
     * Return the length attribute value.
     * </p>
     *
     * @return the length attribute value.
     */
    public Integer getLength() {
        return getAttributeAsInteger(RSSConstants.LENGTH);
    }

    /**
     * <p>
     * Set the length attribute value.
     * </p>
     *
     * @param length the length attribute to set.
     */
    public void setLength(Integer length) {
        setAttribute(RSSConstants.LENGTH, length);
    }

    /**
     * <p>
     * Return the type attribute value.
     * </p>
     *
     * @return the type attribute value.
     */
    public String getType() {
        return getAttribute(RSSConstants.TYPE);
    }

    /**
     * <p>
     * Set the type attribute value.
     * </p>
     *
     * @param type the type attribute value to set.
     *
     * @throws IllegalArgumentException if type is empty.
     */
    public void setType(String type) {
        setAttribute(RSSConstants.TYPE, type);
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
