
package com.topcoder.mobilerssreader.databroker.entity;
import java.io.*;

/**
 * This class represents id for all kinds of Entity. It is used in Mobile Data Broker to load Entity(s), look up Entity(s), and delete Entity(s).
 * <p>It uses type id to find the correct RMS storage, and uses object id to find the index of relative record in RMS storage.</p>
 * <p>To help persistence operations, a constructor with byte array parameter is to initialize object of EntityId and a &quot;getBytes&quot; method is to get byte array representation of this object.</p>
 * <p>Thread safety: In this class, it synchrionizes on the methods that modify mutable members &quot;objectId&quot; and &quot;typeId&quot;. Hence, it is thread safe.</p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7a07]
 */
public class EntityId {

/**
 * <p>Represents id of this object. It is the index of its RMS. If it is a new object to be stored into RMS, its value should&nbsp; be null. If it is loaded from RMS, its value should be equal to the string representation of its index in RMS.</p>
 * <p>It is initialized in the constructor, and should not be empty and may be null.</p>
 * <p>It is set/got in relative &quot;setter&quot;, &quot;getter&quot; methods.</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm7a43]
 */
    private String objectId;

/**
 * <p>Represents id of this object type. Every sub class of Entity has its own static type id, which identifies the name of RMS record store that it will use.</p>
 * <p>It is initialized in the constructor, and should not be null or empty.</p>
 * <p>It is set/got in relative &quot;setter&quot;, &quot;getter&quot; methods.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm7a28]
 */
    private String typeId;

/**
 * <p>Constructor with user specified object id and type id.</p>
 * <p>Implementation:</p>
 * <p>1. this.objectId = objectId;</p>
 * <p>2. this.typeId = typeId.</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm79e5]
 * @param objectId a String object id of Entity class, which should not be empty
 * @param typeId a String type id of Entity class, which should not be null or empty
 * @throws IllegalArgumentException if objectId is empty, or typeId is null or empty
 */
    public  EntityId(String objectId, String typeId) {        
        // your code here
    } 

/**
 * <p>Constructor with byte array. This constructor will initialize this object from the byte array. The byte array contains four parts:<br/>
 *  1. byte array of &quot;objectId&quot; string length's int value.<br/>
 *  2. byte array of &quot;objectId&quot; string<br/>
 *  3. byte array of &quot;typeId&quot; string length's int value.<br/>
 *  4. byte array of &quot;typeId&quot; string.</p>
 * <p>Implementation:</p>
 * <p>1. read 4 bytes from &quot;initialContent&quot;, and convert it into an int &quot;length&quot;.</p>
 * <p>2. read &quot;length&quot; bytes from &quot;initialContent&quot;, and convert it into &quot;objectId&quot;.</p>
 * <p>3. read 4 bytes from &quot;initialContent&quot;, and convert it into an int &quot;length&quot;.</p>
 * <p>4. read &quot;length&quot; bytes from &quot;initialContent&quot;, and convert it into &quot;typeId&quot;.</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm7962]
 * @param initialContent the byte array of "objectId" string length, "objectId" string, "typeId" string length and "typeId" string, which should not be null or empty.
 * @throws IllegalArgumentException if initialContent is null
 */
    public  EntityId(byte[] initialContent) {        
        // your code here
    } 

/**
 * <p>Get byte array representation of EntityId. It will return the byte array of &quot;objectId&quot; string length, &quot;objectId&quot; string, &quot;typeId&quot; string length and &quot;typeId&quot; string. This byte array is used to store into RMS storage.</p>
 * <p>Implementation:</p>
 * <p>1. open a ByteArrayOutputStream<br/>
 *  [ByteArrayOutputStream baos = new ByteArrayOutputStream();]</p>
 * <p>2. write byte array of &quot;objectId&quot; string length, byte array of &quot;objectId&quot; string</p>
 * <p>3. write byte array of &quot;typeId&quot; string length, byte array of &quot;typeId&quot; string</p>
 * <p>4. get byte array. [byte[] array = baos.toByteArray();]</p>
 * <p>5. close ByteArrayOutputStream</p>
 * <p>6. return byte array.</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm790b]
 * @return  the byte array of "objectId" string length, "objectId" string, "typeId" string length and "typeId" string.
 */
    public byte[] getBytes() {        
        // your code here
        return null;
    } 

/**
 * <p>Set member objectId. If objectId is null, it means that this EntityId is new created one without knowing its object id.</p>
 * <p>Implementation:</p>
 * <p>1. this.objectId = objectId [synchronized on objectId]</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm78e4]
 * @param objectId a String that represents object id, which should not be empty and may be null
 * @throws IllegalArgumentException if objectId is  empty
 */
    public void setObjectId(String objectId) {        
        // your code here
    } 

/**
 * <p>Get member objectId</p>
 * <p>Implementation:</p>
 * <p>1. return objectId.</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm789d]
 * @return the member objectId
 */
    public String getObjectId() {        
        // your code here
        return null;
    } 

/**
 * <p>Set member typeId.</p>
 * <p>Implementation:</p>
 * <p>1. this.typeId = typeId [synchronized on typeId]</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm7876]
 * @param typeId a String that represents type id, which should not be null or empty
 * @throws IllegalArgumentException if typeId is null or empty
 */
    public void setTypeId(String typeId) {        
        // your code here
    } 

/**
 * <p>Get member typeId.</p>
 * <p>Implementation:</p>
 * <p>1. return typeId.</p>
 * 
 * @poseidon-object-id [Im61e9fcbm1137f4dc98fmm782e]
 * @return the member typeId
 */
    public String getTypeId() {        
        // your code here
        return null;
    } 

/**
 * <p>Override equals method to compare whether two EntityId are equal or not.</p>
 * <p>Implementation:</p>
 * <p>1. EntityId otherId = (EntityId)other.</p>
 * <p>2. If (this.objectId.equals(otherId.getObjectId()) or this.objectId == null and otherId.getObjectId() == null) and this.typeId.equals(otherId.getTypeId()) are both true, return true. Otherwise, return false.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6719]
 * @param object another instance of EntityId, which should not be null
 * @return true if objectId and typeId are equal in both instances. Otherwise return false
 * @throws IllegalArgumentException if object is null
 */
    public boolean equals(Object object) {        
        // your code here
        return false;
    } 

/**
 * <p>Return hash code of this object.</p>
 * <p>Implementation:</p>
 * <p>1. return (typeId + objectId).hashcode();</p>
 * 
 * @poseidon-object-id [I45d30951m113c2397497mm4a4e]
 * @return hash code of EntityId object
 */
    public int hashCode() {        
        // your code here
        return 0;
    } 
 }
