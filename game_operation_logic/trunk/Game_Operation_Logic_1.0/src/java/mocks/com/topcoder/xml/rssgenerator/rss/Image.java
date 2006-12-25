/*
 * Image.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

/**
 * <p>
 * Image class models the image node, which is a branch node containing several sub elements.
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
public class Image extends RSSElement {
    /**
     * <p>
     * Create a new image node with sub element values. If the node value is null or empty string, the child node will
     * not be added.
     * </p>
     *
     * @param url the url of the image.
     * @param title the title of the image.
     * @param link the link of the image
     */
    public Image(String url, String title, String link) {
        super(RSSConstants.IMAGE, title, link, null);

        // only add the child node if value is non-null and non-empty
        if (!isNullOrEmpty(url)) {
            setChildValue(RSSConstants.URL, url);
        }
    }

    /**
     * <p>
     * Create an image node with no child nodes.
     * </p>
     */
    public Image() {
        super(RSSConstants.IMAGE);
    }

    /**
     * <p>
     * Return the value of its url sub element.
     * </p>
     *
     * @return the value of its url sub element.
     */
    public String getUrl() {
        return getChildValue(RSSConstants.URL);
    }

    /**
     * <p>
     * Set the url child value.
     * </p>
     *
     * @param url the url of the image.
     *
     * @throws IllegalArgumentException if url is empty.
     */
    public void setUrl(String url) {
        setChild(RSSConstants.URL, url);
    }

    /**
     * <p>
     * Return the width value of the image.
     * </p>
     *
     * @return the width of the image.
     */
    public Integer getWidth() {
        return getChildAsInteger(RSSConstants.WIDTH);
    }

    /**
     * <p>
     * Set the width of the image.
     * </p>
     *
     * @param width the width of the image.
     */
    public void setWidth(Integer width) {
        setChild(RSSConstants.WIDTH, width);
    }

    /**
     * <p>
     * Return the height of the image.
     * </p>
     *
     * @return the height of the image.
     */
    public Integer getHeight() {
        return getChildAsInteger(RSSConstants.HEIGHT);
    }

    /**
     * <p>
     * Set the height of the image.
     * </p>
     *
     * @param height the height of the image.
     */
    public void setHeight(Integer height) {
        setChild(RSSConstants.HEIGHT, height);
    }

    /**
     * <p>
     * Common operations shared by the getter methods. The name argument must not be null.
     * </p>
     * 
     * <p>
     * If there is no child with the given name, return null.
     * </p>
     * 
     * <p>
     * Otherwise, return the Integer form of the child value.
     * </p>
     *
     * @param name the name of the child node.
     *
     * @return the child value as an Integer, or null if there is no match.
     */
    private Integer getChildAsInteger(String name) {
        String value = getChildValue(name);

        // if there is no such child tag, return null
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
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
     * Common operations shared by the setter methods. The name argument must not be null.
     * </p>
     * 
     * <p>
     * If value is null, remove the child node.
     * </p>
     * 
     * <p>
     * Otherwise, assign the value in string form to the child node.
     * </p>
     *
     * @param name the name of the child node.
     * @param value the value of the child node.
     */
    private void setChild(String name, Integer value) {
        String str = (value == null) ? null : value.toString();

        setChild(name, str);
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
