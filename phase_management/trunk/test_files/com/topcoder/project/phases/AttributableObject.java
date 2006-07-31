
package com.topcoder.project.phases;
import java.util.*;

/**
 * <p>The class to provide a extensible way for including custom attributes for both Project and Phase class. The attributes are stored in key-value pairs,&nbsp; the type of key and value can vary in different situations, so they are both stored in Object type for best extensibility. This class is serializable.</p>
 *
 *
 * @author TCSDESIGNER
 * @version 2.0
 */
public abstract class AttributableObject implements java.io.Serializable {

/**
 * <p>Represents the map to store the custom attributes. This variable will be initialized in the constructor to an empty HashMap and accessed in those add/get/remove/clear methods.</p>
 *
 */
    private final Map attributes = new HashMap();

/**
 * <p>Create a new instance of AttributableObject. </p>
 *
 */
    protected  AttributableObject() {
        // your code here
    }

/**
 * <p>Return the corresponding value to the specified key, and null is returned if there is no corresponding value. This method will simply return attributes.get(key).</p>
 *
 *
 * @return the corresponding value to the specified key, and null is returned if there is no corresponding value.
 * @param key the key of the value to return
 * @throws IllegalArgumentException if the given key is null
 */
    public java.io.Serializable getAttribute(java.io.Serializable key) {
        // your code here
        return null;
    }

/**
 * <p>Return a unmodifiable shallow copy of inner attributes map. This method will simply return Collections.unmodifiableMap(attributes).</p>
 *
 *
 * @return a unmodifiable shallow copy of inner attributes map.
 */
    public Map getAttributes() {
        // your code here
        return null;
    }

/**
 * <p>Add the key-value pair into the attributes map, if the key is already exist, the old value will be overridden by the new value.&nbsp; This method will simply store the pair by calling attributes.put(key, value) method.</p>
 *
 *
 * @param key the key of the attribute to add
 * @param value the value of the attribute to add
 * @throws IllegalArgumentException if the given key or value is null
 */
    public void setAttribute(java.io.Serializable key, java.io.Serializable value) {
        // your code here
    }

/**
 * <p>Remove key-value pair from the attributes map, if the key does not exist, nothing will happen. The removed value will be returned if the key exists, null will be returned if the key does not exist.</p>
 *
 *
 * @return the removed value to the key if exist, null otherwise
 * @param key the key to remove
 * @throws IllegalArgumentException if the given key is null
 */
    public java.io.Serializable removeAttribute(java.io.Serializable key) {
        // your code here
        return null;
    }

/**
 * <p>Clear all the attributes from the map. This method will simply call attributes.clear.</p>
 *
 */
    public void clearAttributes() {
        // your code here
    }
 }
