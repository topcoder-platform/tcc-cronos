/*
 * Guid.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.FeedNode;


/**
 * <p>
 * Guid class models the guid node in the rss item.
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
public class Guid extends FeedNode {
    /**
     * <p>
     * Create a guid with the given value. The given value can be null or empty, which represents an empty tag.
     * </p>
     *
     * @param value the value to set to guid node.
     */
    public Guid(String value) {
        super(RSSConstants.GUID);
        setValue(value);
    }

    /**
     * <p>
     * Create a guid node with the given value and isPermaLink attribute. The given value can be null or empty, which
     * represents an empty tag. If the attribute value is null, the attribute will not be added.
     * </p>
     *
     * @param value the value of the node.
     * @param isPermaLink the isPermaLink attribute.
     */
    public Guid(String value, Boolean isPermaLink) {
        this(value);

        // only add the attribute if value is non-null
        if (isPermaLink != null) {
            addAttribute(RSSConstants.IS_PERMA_LINK, isPermaLink.toString());
        }
    }

    /**
     * <p>
     * Create a guid node with no value and no attributes.
     * </p>
     */
    public Guid() {
        super(RSSConstants.GUID);
    }

    /**
     * <p>
     * Return the isPermaLink attribute.
     * </p>
     *
     * @return the isPermaLink attribute.
     */
    public Boolean getIsPermaLink() {
        return getAttributeAsBoolean(RSSConstants.IS_PERMA_LINK);
    }

    /**
     * <p>
     * Set the isPermaLink attribute to this node.
     * </p>
     *
     * @param isPermaLink the isPermaLink attribute of the node.
     */
    public void setIsPermaLink(Boolean isPermaLink) {
        setAttribute(RSSConstants.IS_PERMA_LINK, isPermaLink);
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
     * Otherwise, return the Boolean form of the attribute value.
     * </p>
     *
     * @param key the key of attribute.
     *
     * @return the attribute value as a Boolean, or null if there is no match.
     */
    private Boolean getAttributeAsBoolean(String key) {
        String value = getAttribute(key);

        // if there is no such attribute, return null
        if (value == null) {
            return null;
        }
        return Boolean.valueOf(value);
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
    private void setAttribute(String key, Boolean value) {
        if (value == null) {
            removeAttribute(key);
        } else {
            addAttribute(key, value.toString());
        }
    }
}
