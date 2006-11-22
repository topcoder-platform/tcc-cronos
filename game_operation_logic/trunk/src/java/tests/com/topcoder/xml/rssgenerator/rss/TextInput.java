/*
 * TextInput.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

/**
 * <p>
 * TextInput class models the textInput node.
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
public class TextInput extends RSSElement {
    /**
     * <p>
     * Create a textInput node with sub element values. If the node value is null or empty string, the child node will
     * not be added.
     * </p>
     *
     * @param title the title of the textInput.
     * @param desc the desc of the textInput.
     * @param textName the textName of the textInput.
     * @param link the link of the textInput.
     */
    public TextInput(String title, String desc, String textName, String link) {
        super(RSSConstants.TEXT_INPUT, title, link, desc);

        // only add the child node if value is non-null and non-empty
        if (!isNullOrEmpty(textName)) {
            setChildValue(RSSConstants.TEXT_NAME, textName);
        }
    }

    /**
     * <p>
     * Create a new textInput node with no child nodes.
     * </p>
     */
    public TextInput() {
        super(RSSConstants.TEXT_INPUT);
    }

    /**
     * <p>
     * Return the textName of the textInput.
     * </p>
     *
     * @return the text name of the textInput.
     */
    public String getTextName() {
        return getChildValue(RSSConstants.TEXT_NAME);
    }

    /**
     * <p>
     * Set the textName of the textInput.
     * </p>
     *
     * @param textName the textName of the textInput.
     *
     * @throws IllegalArgumentException if textName is empty.
     */
    public void setTextName(String textName) {
        setChild(RSSConstants.TEXT_NAME, textName);
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
