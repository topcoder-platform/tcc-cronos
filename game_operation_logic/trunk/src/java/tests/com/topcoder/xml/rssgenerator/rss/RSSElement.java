/*
 * RSSElement.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.FeedBranch;


/**
 * <p>
 * A class to model the common elements of the feed in the rss2.0, rss1.0, rss0.92, and rss0.91 specifications,
 * including:
 * 
 * <ul>
 * <li>
 * title
 * </li>
 * <li>
 * link
 * </li>
 * <li>
 * description
 * </li>
 * </ul>
 * </p>
 * 
 * <p>
 * Lots of nodes in all the rss specifications are expected to have title, link and description child nodes, so this
 * class is designed to ease the creation of those nodes by providing a constructor taking those 3 arguments, and
 * corresponding getter methods to access the values of those child nodes.
 * </p>
 * 
 * <p>
 * Note that passing a null value to the setter methods will remove the child node from the branch. The getter methods
 * return null if the child node does not exist.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public class RSSElement extends FeedBranch {
    /**
     * <p>
     * Create a new RSSElement instance with the given name and no child nodes.
     * </p>
     *
     * @param name the name of the element.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    protected RSSElement(String name) {
        super(name);
    }

    /**
     * <p>
     * Create a new instance of RSSElement with the given name, title, link, and description arguments. This
     * constructor will add the child node for each of the title, link and description values. If the node value is
     * null or empty string, the child node will not be added.
     * </p>
     *
     * @param name the name of the element.
     * @param title the title of the element.
     * @param link the link of the element.
     * @param desc the description of the element.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    protected RSSElement(String name, String title, String link, String desc) {
        super(name);

        // only add the child node if value is non-null and non-empty
        if (!isNullOrEmpty(title)) {
            setChildValue(RSSConstants.TITLE, title);
        }
        if (!isNullOrEmpty(link)) {
            setChildValue(RSSConstants.LINK, link);
        }
        if (!isNullOrEmpty(desc)) {
            setChildValue(RSSConstants.DESCRIPTION, desc);
        }
    }

    /**
     * <p>
     * Return the title of the element.
     * </p>
     *
     * @return the title of the element.
     */
    public String getTitle() {
        return getChildValue(RSSConstants.TITLE);
    }

    /**
     * <p>
     * Return the link of the element.
     * </p>
     *
     * @return the link of the element.
     */
    public String getLink() {
        return getChildValue(RSSConstants.LINK);
    }

    /**
     * <p>
     * Return the description of the element.
     * </p>
     *
     * @return the description of the element.
     */
    public String getDescription() {
        return getChildValue(RSSConstants.DESCRIPTION);
    }

    /**
     * <p>
     * Set the value of title sub element.
     * </p>
     *
     * @param title the title value of the title sub element.
     *
     * @throws IllegalArgumentException if title is empty.
     */
    public void setTitle(String title) {
        setChild(RSSConstants.TITLE, title);
    }

    /**
     * <p>
     * Set the link value of the link sub element.
     * </p>
     *
     * @param link the value of the link sub element.
     *
     * @throws IllegalArgumentException if link is empty.
     */
    public void setLink(String link) {
        setChild(RSSConstants.LINK, link);
    }

    /**
     * <p>
     * Set the description value of the sub element.
     * </p>
     *
     * @param desc the value of the description sub element.
     *
     * @throws IllegalArgumentException if desc is empty.
     */
    public void setDescription(String desc) {
        setChild(RSSConstants.DESCRIPTION, desc);
    }

    /**
     * <p>
     * Common operations shared by the setter methods. The name argument must not be null.
     * </p>
     * 
     * <p>
     * If value is null, remove the child node.
     * </p>
     * 
     * <p>
     * If value is empty string, throw IllegalArgumentException.
     * </p>
     * 
     * <p>
     * Otherwise, assign the value to the child node.
     * </p>
     *
     * @param name the name of the child node.
     * @param value the value of the child node.
     *
     * @throws IllegalArgumentException if value is empty.
     */
    private void setChild(String name, String value) {
        if (value == null) {
            removeChildren(name);
        } else if (value.trim().length() == 0) {
            throw new IllegalArgumentException("value is empty");
        } else {
            setChildValue(name, value);
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
