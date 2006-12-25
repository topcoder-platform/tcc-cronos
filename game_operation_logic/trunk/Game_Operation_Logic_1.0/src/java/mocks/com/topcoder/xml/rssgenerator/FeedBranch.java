/*
 * FeedBranch.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Represent the branch node in the xml tree, it has multiple child nodes which can be of type either FeedBranch or
 * FeedNode.
 * </p>
 * 
 * <p>
 * For example:
 * <pre>
 * &lt;channel&gt;
 * &lt;title&gt;topcoder components&lt;/title&gt;
 * &lt;item&gt;
 * &nbsp;&nbsp;&nbsp; &lt;title&gt;rss generator&lt;/title&gt;
 * &nbsp;&nbsp;&nbsp; &lt;link&gt;http://www.topcoder.com/rss_generator.html&lt;/link&gt;
 * &nbsp;&nbsp;&nbsp; &lt;description&gt;rss generator description&lt;/description&gt;
 * &lt;/item&gt;
 * &lt;item&gt;
 * &nbsp;&nbsp;&nbsp; &lt;title&gt;directory vaildation&lt;/title&gt;
 * &nbsp;&nbsp;&nbsp; &lt;link&gt;http://www.topcoder.com/directory_validation.html&lt;/link&gt;
 * &nbsp;&nbsp;&nbsp; &lt;description&gt;directory validation description&lt;/description&gt;
 * &lt;/item&gt;
 * &lt;/channel&gt;
 * </pre>
 * </p>
 * 
 * <p>
 * The channel node can be represented as a FeedBranch instance with name 'channel', and it has 3 child nodes: title
 * node can be represented as a FeedNode instance with name 'title', and the other two item nodes can be represented
 * as a FeedBranch instance respectively with 3 FeedNode instances for their children.
 * </p>
 * 
 * <p>
 * Note that the FeedBranch is mutable, so that the children can be added or removed programmatically.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public class FeedBranch extends FeedNode {
    /**
     * <p>
     * Represents the child nodes of the branch. The key of the map is the name of the child nodes retrieved by
     * FeedNode.getName() method, the value of the map is a list of child nodes with the corresponding name. The child
     * nodes can be accessed efficiently by the node name while stored in this way. A getChild(name) is designed to
     * get the 1st node with the specified name which can be used if the node occurs 1 time always, a
     * getChildren(name) is designed to retrieve all the nodes with the specified name which can be used to retrieve
     * the items of feed. This variable is initialized in the constructor to an empty map.
     * </p>
     */
    private Map children = null;

    /**
     * <p>
     * Create a new instance of FeedBranch with the specified branch name. Initially the branch node has no children
     * and attributes which can be added later by addChild or addAttribute methods. The given name is used to identify
     * the node, and rendered as the xml tag name, which should be non-null, non-empty string.
     * </p>
     *
     * @param name the name of the branch node.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public FeedBranch(String name) {
        super(name);
        children = new HashMap();
    }

    /**
     * <p>
     * Add a child node to the branch node, which can be instances of classes extended from FeedNode, for example
     * FeedBranch etc.
     * </p>
     * 
     * <p>
     * This method will not try to detect the loop case - the added child contains this branch. The client should be
     * responsible to avoid such case.
     * </p>
     *
     * @param child the child to add into the branch.
     *
     * @throws NullPointerException if the child is null.
     */
    public void addChild(FeedNode child) {
        if (child == null) {
            throw new NullPointerException("child is null");
        }

        String name = child.getName();
        List nodes = (List) children.get(name);

        // if there is no such child tag, create a new entry
        if (nodes == null) {
            nodes = new LinkedList();
            children.put(name, nodes);
        }
        nodes.add(child);
    }

    /**
     * <p>
     * Remove the specified child node from the branch. If the child does not exist in the branch, nothing happens. If
     * the child appears several times in the branch, only the first child is removed.
     * </p>
     *
     * @param child the child to remove from the branch.
     *
     * @throws NullPoniterException if the child is null.
     */
    public void removeChild(FeedNode child) {
        if (child == null) {
            throw new NullPointerException("child is null");
        }

        List nodes = (List) children.get(child.getName());

        // if there is no such child tag, return silently
        if (nodes == null) {
            return;
        }
        nodes.remove(child);
    }

    /**
     * <p>
     * Remove all the child nodes with the given node name. If there is no children with the specified node name,
     * nothing happens, and an empty list will be returned. Otherwise, the removed child nodes will be returned.
     * </p>
     *
     * @param name the name of child nodes to remove.
     *
     * @return the removed children with the specified name, or an empty list if there is no match.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public List removeChildren(String name) {
        checkNullOrEmpty(name);

        List nodes = (List) children.remove(name);

        // if there is no such child tag, return empty list
        if (nodes == null) {
            return new LinkedList();
        }
        return nodes;
    }

    /**
     * <p>
     * Return the first child node of the specified node name, or null if there is no children with the specified name.
     * This method is usually called when there is only 1 child in the branch having the given node name.
     * </p>
     *
     * @param name the name of the child to retrieve.
     *
     * @return the first child node of the specified name, or null if there is no match.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public FeedNode getChild(String name) {
        checkNullOrEmpty(name);

        List nodes = (List) children.get(name);

        // if there is no such child tag, return null
        if ((nodes == null) || nodes.isEmpty()) {
            return null;
        }
        return (FeedNode) nodes.get(0);
    }

    /**
     * <p>
     * Return all the child nodes with the specified node name, or an empty list if there is no children corresponding
     * to the given name. This method is usually called when there are multiple child nodes in the branch with the
     * same name, such as the item node in the feed.
     * </p>
     *
     * @param name the name of the children to retrieve.
     *
     * @return the children with the specified node name, or an empty list if there is no match.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public List getChildren(String name) {
        checkNullOrEmpty(name);

        List nodes = (List) children.get(name);

        // if there is no such child tag, return empty list
        if (nodes == null) {
            return new LinkedList();
        }
        return new LinkedList(nodes); // clone the list
    }

    /**
     * <p>
     * Return an unmodifiable shallow copy of the inner children map. The key of the map is the name of the child nodes
     * retrieved by FeedNode.getName() method, the value of the map is a list of child nodes with the corresponding
     * name.
     * </p>
     *
     * @return an unmodifiable shallow copy of the inner children map.
     */
    public Map getChildren() {
        return Collections.unmodifiableMap(children);
    }

    /**
     * <p>
     * Clear all the child nodes in the branch. This method will clear up all its children added previously.
     * </p>
     */
    public void clearChildren() {
        children.clear();
    }

    /**
     * <p>
     * Return the value of the first child node of the given name. If there is no such child, simply return null.
     * </p>
     * 
     * <p>
     * Note that null can also be returned if the first child node of the given name contains no value.
     * </p>
     *
     * @param childName the name of the child node.
     *
     * @return the value of the first child node, or null if there is no matching node.
     *
     * @throws NullPointerException if the childName is null.
     * @throws IllegalArgumentException if the childName is empty.
     */
    protected String getChildValue(String childName) {
        FeedNode node = getChild(childName);

        // if there is no such child tag, return null
        if (node == null) {
            return null;
        }
        return node.getValue();
    }

    /**
     * <p>
     * Assign the new value to the first child of the given name. If the child does not exist, create a new one with
     * the newValue. The newValue can be null or empty, which represents an empty tag.
     * </p>
     *
     * @param childName the name of the child.
     * @param newValue the new value of the child.
     *
     * @throws NullPointerException if the childName is null.
     * @throws IllegalArgumentException if the childName is empty.
     */
    protected void setChildValue(String childName, String newValue) {
        FeedNode node = getChild(childName);

        // if there is no such child tag, create a new one
        if (node == null) {
            node = new FeedNode(childName);
            addChild(node);
        }
        node.setValue(newValue);
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
