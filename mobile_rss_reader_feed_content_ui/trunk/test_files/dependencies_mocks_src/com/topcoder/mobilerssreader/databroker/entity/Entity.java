
package com.topcoder.mobilerssreader.databroker.entity;
import java.io.*;

/**
 * This abstract class represents Entity in Mobile Data Broker. As an abstract class of all entities in this component, it contains common properties, like name and objectId.
 * <p>Property name is used to represent entity's name or title. Property objectId is used to represent entity's id in its own RMS storage.</p>
 * <p>This class provides two template methods to get different byte array representations and different type ids, which are implemented in Entity's sub classes.</p>
 * <p>Thread safety:&nbsp;&nbsp;In this class, one member is immutable. Another is synchronized on set operation. Hence, it is thread safe.</p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7abb]
 */
public abstract class Entity {

/**
 * <p>Represents name of this entity. In some sub class of Entity, it is treated as title.</p>
 * <p>It is initialized in constructor. It should not be null or empty.</p>
 * <p>It is got in relative &quot;getter&quot; methods.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7939]
 */
    private final String name;

/**
 * <p>Represents id of this object. It is the index of its RMS. If it is a new object to be stored into RMS, its value should be null. If it is loaded from RMS, its value should be equal to the string representation of its index in RMS.</p>
 * <p>It is initialized in the constructor, and should not be empty and may be null.</p>
 * <p>It is got in relative &quot;getter&quot; methods.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm790a]
 */
    private String objectId;

/**
 * <p>Constructor with specified name and objectId.</p>
 * <p>Implementation:</p>
 * <p>1. this.name = name;</p>
 * <p>2. this.objectId = objectId.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm78e7]
 * @param name name of this entity, which should not be null or empty
 * @param objectId the string value of this object id, which should not be empty and may be null
 * @throws IllegalArgumentException if name is null or empty, objectId is empty
 */
    protected  Entity(String name, String objectId) {        
        this.name = name;
        this.objectId = objectId;
    } 

/**
 * <p>Constructor with name. It is used when user creates a new Entity object without knowing its objectId.</p>
 * <p>Implementation:</p>
 * <p>1. this(name, null);</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7868]
 * @param name name of this entity, which should not be null or empty
 * @throws IllegalArgumentException if name is null or empty.
 */
    protected  Entity(String name) {        
        this(name, null);
    } 

/**
 * <p>Get name of this Entity.</p>
 * <p>Implementation:</p>
 * <p>1. return name</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7821]
 * @return name of this entity.
 */
    public String getName() {        
        return name;
    } 

/**
 * <p>Get object id of this Entity.</p>
 * <p>Implementation:</p>
 * <p>1. return objectId</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm77b3]
 * @return the member objectId
 */
    public String getObjectId() {        
        return objectId;
    } 

/**
 * <p>Abstract method to get byte array representation of Entity's members. Every sub class of Entity has its own byte array representation. However, their format follows the same pattern:1. If the member is an integer type, store byte array of int value; 2. If the member is a string type, store byte array of string length(int value) and string itself.; 3. If the member is a Date type, store byte array of Date's long value; 4. If the member is a Vector type, store byte array of each element.</p>
 * <p>This method is used by Mobile Data Broker to get byte array of every Entity and store into RMS.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7745]
 * @return byte array representation of Entity's members
 */
    public abstract byte[] getBytes();

/**
 * <p>Abstract method to get sub class type id.</p>
 * <p>Different sub class of Entity has its own type id. Through the template method, it can be got without knowing its concrete type.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm771e]
 * @return type id of sub class of Entity
 */
    public abstract String getTypeId();

/**
 * <p>Protected method to set objectId member. It is used in MobileDataBroker to set objectId.</p>
 * <p>Implementation:</p>
 * <p>1. this.objectId = objectId. [synchronized on objectId]</p>
 * 
 * @poseidon-object-id [Im24c05902m1138bc1c825mm67d6]
 * @param objectId the objectId to be set, which should not be null or empty
 * @throws IllegalArgumentException if objectId is null or empty.
 */
    public void setObjectId(String objectId) {        
        // your code here
    } 

/**
 * <p>Get EntityId instance that represents this entity. This method is abstract and should be implemented in its sub classes</p>
 * 
 * @poseidon-object-id [I14a7a8dam113c98ea041mm540a]
 * @return EntityId instance that represents this entity
 */
    public abstract com.topcoder.mobilerssreader.databroker.entity.EntityId getEntityId();
 }
