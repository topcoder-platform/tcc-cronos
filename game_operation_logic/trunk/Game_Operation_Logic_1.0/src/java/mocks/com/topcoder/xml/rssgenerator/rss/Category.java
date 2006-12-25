/*
 * Category.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.FeedNode;


/**
 * <p>
 * Category class models the category node in the rss feed or rss item.
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
public class Category extends FeedNode {
    /**
     * <p>
     * Create a new category node with the given value. The given value can be null or empty, which represents an empty
     * tag.
     * </p>
     *
     * @param value the value of the category node.
     */
    public Category(String value) {
        super(RSSConstants.CATEGORY);
        setValue(value);
    }

    /**
     * <p>
     * Create a new category node with the given value and domain attribute. The given value can be null or empty,
     * which represents an empty tag. If the attribute value is null or empty string, the attribute will not be added.
     * </p>
     *
     * @param value the value of the category node.
     * @param domain the domain attribute value.
     */
    public Category(String value, String domain) {
        this(value);

        // only add the attribute if value is non-null and non-empty
        if (!isNullOrEmpty(domain)) {
            addAttribute(RSSConstants.DOMAIN, domain);
        }
    }

    /**
     * <p>
     * Create a category node with no value and no attributes.
     * </p>
     */
    public Category() {
        super(RSSConstants.CATEGORY);
    }

    /**
     * <p>
     * Return the domain attribute.
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
     * @param domain the domain attribute to set.
     *
     * @throws IllegalArgumentException if domain is empty.
     */
    public void setDomain(String domain) {
        setAttribute(RSSConstants.DOMAIN, domain);
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
