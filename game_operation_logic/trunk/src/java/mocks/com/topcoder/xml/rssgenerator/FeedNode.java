/*
 * FeedNode.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Represent the leaf node of the xml tree, and it is also the parent class of the FeedBranch to provide attributes
 * functionality. Leaf node is the lowest level nodes in the xml tree which has no children.
 * </p>
 * 
 * <p>
 * For example:
 * <pre>
 * &lt;item id = &quot;item1&quot;&gt;
 * &nbsp;&nbsp;&nbsp; &lt;title&gt;rss generator&lt;/title&gt;
 * &nbsp;&nbsp;&nbsp; &lt;link&gt;http://www.topcoder.com/rss_generator.html&lt;/link&gt;
 * &nbsp;&nbsp;&nbsp; &lt;description&gt;rss generator description&lt;/description&gt;
 * &lt;/item&gt;
 * </pre>
 * </p>
 * 
 * <p>
 * The title, link and description nodes are all leaf nodes and each has a name and value. The name of the link node is
 * 'link', and its value is 'http://www.topcoder.com/rss_generator.html'. And all the nodes in the xml tree can have
 * zero or more attributes, take the item node above for example, the 'id', 'item1' is a key-value pair of an
 * attribute.
 * </p>
 * 
 * <p>
 * Note that the FeedNode is mutable, so that the value and attributes can be changed programmatically.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public class FeedNode implements Serializable {
    /**
     * <p>
     * Represents the name of the node, it is initialized in the constructor and can never changed afterwards. The node
     * name should be non-null, non-empty string.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Represents the value of the leaf node, it is not required if it is a branch node. It is assigned in the setValue
     * method, and can be null or empty string.
     * </p>
     */
    private String value = null;

    /**
     * <p>
     * Represents the attribute map of the node, the key and value of the map are all non-null, non-empty strings. It
     * is initialized in the constructor to an empty map, and accessed in the add/get/remove/clear methods.
     * </p>
     */
    private Map attributes = null;

    /**
     * <p>
     * Create a new instance of FeedNode with the specified name. Initially, the node contains no attributes, and its
     * value is null. The given name is used to identify the node, and rendered as the xml tag name, which should be
     * non-null, non-empty string.
     * </p>
     *
     * @param name the name of the node.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public FeedNode(String name) {
        checkNullOrEmpty(name);
        this.name = name;
        attributes = new HashMap();
    }

    /**
     * <p>
     * Return the name of the node, which is a non-null, non-empty string.
     * </p>
     *
     * @return the name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Return the value of the node.
     * </p>
     *
     * @return the value of the node.
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>
     * Assign the value of the node, this method is only expected to be called if it is a leaf node. The given value
     * can be null or empty, which represents an empty tag.
     * </p>
     *
     * @param value the value of the node to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>
     * Return the value of the attribute with the specified key. If there is no corresponding attribute in node with
     * the given attribute name, null will be returned.
     * </p>
     *
     * @param key the key of the attribute value to retrieve.
     *
     * @return the value of attribute corresponding to the given key, or null if there is none.
     *
     * @throws NullPointerException if the key is null.
     * @throws IllegalArgumentException if the key is empty.
     */
    public String getAttribute(String key) {
        checkNullOrEmpty(key);
        return (String) attributes.get(key);
    }

    /**
     * <p>
     * Add the attribute to the node. If there is no attribute with the same key as the given one, a new entry will be
     * created with the given key-value pair, otherwise, the old value will be overriden by the new value.
     * </p>
     *
     * @param key the key of attribute to add.
     * @param value the value of attribute to add.
     *
     * @throws NullPointerException if key or value is null.
     * @throws IllegalArgumentException if the key or value is empty.
     */
    public void addAttribute(String key, String value) {
        checkNullOrEmpty(key);
        checkNullOrEmpty(value);
        attributes.put(key, value);
    }

    /**
     * <p>
     * Remove the attribute from the node. If there is no attribute with the same key as the given one, nothing happens
     * and null is returned. Otherwise, the removed attribute value is returned.
     * </p>
     *
     * @param key the key of the attribute to remove.
     *
     * @return the removed attribute value corresponding to the key, or null if there is no match.
     *
     * @throws NullPointerException if the key is null.
     * @throws IllegalArgumentException if the key is empty.
     */
    public String removeAttribute(String key) {
        checkNullOrEmpty(key);
        return (String) attributes.remove(key);
    }

    /**
     * <p>
     * Clear all the attributes of the node.
     * </p>
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * <p>
     * Return an unmodifiable shallow copy of the attributes map.
     * </p>
     *
     * @return an unmodifiable shallow copy of the attributes map.
     */
    public Map getAttributes() {
        return Collections.unmodifiableMap(attributes);
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
