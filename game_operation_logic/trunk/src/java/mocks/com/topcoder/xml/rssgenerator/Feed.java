/*
 * Feed.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator;

import com.topcoder.xml.rssgenerator.rss.RSSConstants;

import java.util.List;


/**
 * <p>
 * An abstract class to model the feed of different types. A feed is essentially an xml tree, which has a root and a
 * list of child nodes.
 * </p>
 * 
 * <p>
 * Feed of different types is quite different when represented in an xml tree, so a getXML abstract method which will
 * render the Feed instance into xml string is designed to be implemented in its subclasses to generate the correct
 * format for specific types such rss2.0, rss1.0, atom etc.
 * </p>
 * 
 * <p>
 * Each feed is required to have 1 or more items. A getItems abstract method is also designed to be implemented in
 * subclasses, the instances in the returned list are of FeedBranch type.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public abstract class Feed extends FeedBranch {
    /**
     * <p>
     * Represents the type of the feed. It is specified in the constructor, and can never be changed afterwards. This
     * value is used to identify the type of the Feed which can be possibly rss2.0, rss1.0, rss0.92 or atom1.0 etc. It
     * should be non-null, non-empty string.
     * </p>
     */
    private String type = null;

    /**
     * <p>
     * Create a new Feed instance with the feed type. Initially, there is no child nodes and attributes in the feed,
     * which are expected to be added later in order to generate correct and meaningful feed.
     * </p>
     *
     * @param type the type of the feed.
     *
     * @throws NullPointerException if the type is null.
     * @throws IllegalArgumentException if the type is empty.
     */
    protected Feed(String type) {
        super(RSSConstants.FEED_NAME);

        checkNullOrEmpty(type);
        this.type = type;
    }

    /**
     * <p>
     * Return the xml representation of the Feed instance. This method is abstract since different types of feeds will
     * have quite different xml representations. Its implementation is expected to render the correct root node and
     * all the child nodes and attributes.
     * </p>
     *
     * @return the xml representation of Feed instance.
     */
    public abstract String getXML();

    /**
     * <p>
     * Return a list of items of the Feed instance. The items are basically FeedBranch instances added by addChild
     * method. This method is abstract since the tag name of the item in feeds of different types is different.
     * Subclasses are expected to return the correct list.
     * </p>
     *
     * @return a list of items of the Feed instance.
     */
    public abstract List getItems();

    /**
     * <p>
     * Return the type of the feed.
     * </p>
     *
     * @return the type of the feed.
     */
    public String getType() {
        return type;
    }

    /**
     * <p>
     * Checks if the given string is null or empty.
     * </p>
     *
     * @param str the string to check against.
     *
     * @throws NullPointerException if str is null.
     * @throws IllegalArgumentException if str is empty.
     */
    private static void checkNullOrEmpty(String str) {
        if (str == null) {
            throw new NullPointerException("String argument is null");
        }
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("String argument is empty");
        }
    }
}
