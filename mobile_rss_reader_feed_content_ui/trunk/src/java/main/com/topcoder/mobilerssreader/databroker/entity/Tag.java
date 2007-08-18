
package com.topcoder.mobilerssreader.databroker.entity;
import java.io.*;

/**
 * This class extends Entity to represent Tag.
 * <p>This class inherits member name, objectId&nbsp; to describle current state of Tag instance. These members are initialized in constructor and retrieved from &quot;getter&quot; method.</p>
 * <p>It implements getBytes method to provide its own byte array representation, which will be store into its RMS storage.</p>
 * <p>It also implements getTypeId method to provide its own type id, which will be used to name its RMS storage and refer it.</p>
 * <p>Thread safety: All&nbsp; members are immutable.&nbsp; Hence, it is thread safe.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6619]
 */
public class Tag extends Entity {

/**
 * <p>Represents type id of Tag class. Its value is &quot;Tag&quot; string. It will be used to refered as class type and RMS storage name.</p>
 * <p>It is initialized as final and static string.</p>
 * <p>It is used in method &quot;getTypeId&quot; to get its value.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6c4d]
 */
    public static final String TYPEID = "Tag";

/**
 * <p>Constructor with specified name and objectId.</p>
 * <p>Implementation:</p>
 * <p>1. this.name = name;</p>
 * <p>2. this.objectId = objectId.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6d45]
 * @param name name of this entity, which should not be null or empty
 * @param objectId the string value of this object id, which should not be empty and may be null
 * @throws IllegalArgumentException if name is null or empty, objectId is empty
 */
    public  Tag(String name, String objectId) {        
        super(name, objectId);
    } 

/**
 * <p>Constructor with name. It is used when user creates a new Entity object without knowing its objectId.</p>
 * <p>Implementation:</p>
 * <p>1. this(name, null);</p>
 * <p></p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6cd9]
 * @param name name of this entity, which should not be null or empty
 * @throws IllegalArgumentException if name is null or empty.
 */
    public  Tag(String name) {        
        this(name, null);
    } 

/**
 * <p>Get byte array of Tag. If any Object member is null, throw IllegalStateException.</p>
 * <p>Implementation:</p>
 * <p>1. open a ByteArrayOutputStream and DataOutputStream. [ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos);]</p>
 * <p>2. write byte array of name's length(an int value) and byte array of name string</p>
 * <p>3.&nbsp; write byte array of objectId's length(an int value) and byte array of objectId string. Especially, if objectId is null, that is user doesn't know this object's id in record store, set objectId to be -1.</p>
 * <p>4&nbsp; get all byte array [byte[] array = baos.toByteArray();]</p>
 * <p>9. close ByteArrayOutputStream and DataOutputStream.</p>
 * <p>10. return array.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6c96]
 * @return byte array that represents Tag object
 * @throws IOException if fails happen in writing content into byte array
 * @throws IllegalStateException if any Object member is null
 */
    public byte[] getBytes() {        
        // your code here
        return null;
    } 

/**
 * <p>Get type id of Tag</p>
 * <p>Implementation:</p>
 * <p>1. return TYPEID;</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6c71]
 * @return type id of Tag
 */
    public String getTypeId() {        
        // your code here
        return null;
    } 

/**
 * <p>Get EntityId instance that represents this entity.</p>
 * <p>Implementation:</p>
 * <p>1. return new EntityId(super.objectId, TYPEID);</p>
 * 
 * @poseidon-object-id [I14a7a8dam113c98ea041mm5381]
 * @return EntityId instance that represents this entity
 */
    public com.topcoder.mobilerssreader.databroker.entity.EntityId getEntityId() {        
        // your code here
        return null;
    } 
 }
