/*
 * Source.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.FeedNode;


/**
 * <p>
 * Source models the source sub element in rss item.
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
public class Source extends FeedNode {
    /**
     * <p>
     * Create a new source node with the given value and url attribute. The given value can be null or empty, which
     * represents an empty tag. If the attribute value is null or empty string, the attribute will not be added.
     * </p>
     *
     * @param value the value of the source node.
     * @param url the url attribute.
     */
    public Source(String value, String url) {
        super(RSSConstants.SOURCE);
        setValue(value);

        // only add the attribute if value is non-null and non-empty
        if (!isNullOrEmpty(url)) {
            addAttribute(RSSConstants.URL, url);
        }
    }

    /**
     * <p>
     * Create a new source node with no value and no attributes.
     * </p>
     */
    public Source() {
        super(RSSConstants.SOURCE);
    }

    /**
     * <p>
     * Return the url attribute.
     * </p>
     *
     * @return the url attribute of the source.
     */
    public String getUrl() {
        return getAttribute(RSSConstants.URL);
    }

    /**
     * <p>
     * Set the url attribute to the source.
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
