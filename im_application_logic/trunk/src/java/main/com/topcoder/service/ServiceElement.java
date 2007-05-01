package com.topcoder.service;

import java.util.Map;
import java.util.HashMap;

/**
 * Service element class can be used to represent a service requester or responder. It could contain various
 * properties, but diffrent service engine may require different property values. if two service elements have
 * same properties, they will be considered as same, even they have different id. ID SHOULD NOT be used by
 * application directly. It will be used by this component internally for efficiency.
 * <p>
 * This class is mutable, and not thread safe. But it is expected to be used in a single thread.
 * </p>
 * 
 */
public class ServiceElement {

    /**
     * <p>
     * Represents properties map.
     * </p>
     * It maps a string key to an object. The key shouldn't be null or empty, but there is no constrait on the
     * value.
     * <p>
     * It iwll be initialized in ctor, and never be changed later. But its content could be changed.
     * </p>
     * 
     */
    private Map properties = new HashMap();

    /**
     * Represents the unique id used to identify this serviceElement. It will be -1 intially, and will be set
     * by persistence layer. This value should never be used by application.
     * 
     */
    private long id = -1;

    /**
     * Empty ctor.
     * 
     */
    public ServiceElement() {
    }

    /**
     * <p>
     * Does ...
     * </p>
     * 
     * 
     * @param properties
     *            the properties map
     * @throws IllegalArgumentException
     *             if arg is null, or it contains null/empty key.
     */
    public ServiceElement(Map properties) {
        this.properties = properties;
    }

    /**
     * Get specified property. Or null if there is no such property
     * 
     * 
     * @param name
     *            the property name
     * @return the property value
     * @throws IllegalArgumentException
     *             if name is null or empty
     */
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    /**
     * Set or override property value
     * 
     * 
     * @param name
     *            the name of property
     * @param value
     *            value of property
     * @return the old property value, or null if there was no such property
     * @throws IllegalArgumentException
     *             if name is null or empty
     */
    public Object setProperty(String name, Object value) {
        this.properties.put(name, value);
        return null;
    }

    /**
     * Remove the specified property
     * 
     * 
     * @param name
     *            name of property
     * @return value of the property, or null if there is no such property
     * @throws IllegalArgumentException
     *             if name is null or empty
     */
    public Object removeProperty(String name) {
        this.properties.remove(name);
        return null;
    }

    /**
     * Get all the properties. Return an unmodifiable map
     * 
     * 
     * @return an unmodifialbe map
     */
    public Map getAllProperties() {
        return this.properties;
    }

    /**
     * Clear all the properties.
     * 
     */
    public void clearProperties() {
        this.properties.clear();
    }

    /**
     * Application should never use this method. This method is only used in this component.
     * 
     * 
     * @return
     */
    public long getId() {
        return this.id;
    }

    /**
     * Application should never use this method. This method is only used in this component
     * 
     * 
     * @param id
     *            new id
     * @throws IllegalArgumentException
     *             if id less than -1.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Compare the two properties map.
     * <p>
     * IMPORTANT: id should be ignored.
     * </p>
     * 
     * 
     * @param obj
     *            the obj to compare to
     * @return whether they are equals
     */
    public boolean equals(Object obj) {
        return (obj instanceof ServiceElement) && ((ServiceElement) obj).properties.equals(this.properties);
    }

    /**
     * return properites.hashCode()
     * 
     * 
     * @return
     */
    public int hashCode() {
        return this.properties.hashCode();
    }
}
