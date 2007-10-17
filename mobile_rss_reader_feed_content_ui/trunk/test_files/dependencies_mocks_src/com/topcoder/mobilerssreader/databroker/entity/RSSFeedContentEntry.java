package com.topcoder.mobilerssreader.databroker.entity;

import com.topcoder.mobile.httphandler.URL;
import java.io.*;
import java.util.Date;

/**
 * This class represents RSS entry of RSS Feed Content.
 * <p>
 * This class contains member description, link, isRead, updatedDate to describle current state of RSSEntry instance.
 * These members are initialized in constructor and retrieved from &quot;getter&quot; method.
 * </p>
 * <p>
 * It implements getBytes method to provide its own byte array representation, which will be store into its RMS storage.
 * </p>
 * <p>
 * It also implements getTypeId method to provide its own type id, which will be used to name its RMS storage and refer
 * it.
 * </p>
 * <p>
 * Thread safety: All&nbsp; members are immutable. And for object member, copy constructor is used to make it thread
 * safe. Hence, it is thread safe.
 * </p>
 * <p>
 * </p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7a38]
 */
public class RSSFeedContentEntry {

    /**
     * <p>
     * Represents description of RSS entry.
     * </p>
     * <p>
     * It is initialized in constructor. it should not be null and may be empty.
     * </p>
     * <p>
     * It is got in relative&nbsp; &quot;getter&quot; methods.
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6fa4]
     */
    private final String description;

    /**
     * <p>
     * Represents link of the RSS entry.
     * </p>
     * <p>
     * It is initialized in constructor and should not be null
     * </p>
     * <p>
     * It is got in relative &quot;getter&quot; methods.
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6f8e]
     */
    private final URL link;

    /**
     * <p>
     * Represents whether this RSS entry is read or not.
     * </p>
     * <p>
     * It is initialized in constructor and not modified after that.
     * </p>
     * <p>
     * It is got from &quot;getter&quot; method.
     * </p>
     * 
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6f74]
     */
    private final boolean isRead;

    /**
     * <p>
     * Represents updated date of&nbsp; rss entry updated date.
     * </p>
     * <p>
     * It is initialized in constructor and should not be null.
     * </p>
     * <p>
     * It is got in the &quot;getter&quot; method.
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6f59]
     */
    private final Date updatedDate;

    /**
     * <p>
     * Represents type id of RSSEntry class. Its value is &quot;RSSEntry&quot; string. It will be used to refered as
     * class type.
     * </p>
     * <p>
     * It is initialized as final and static string.
     * </p>
     * <p>
     * It is used in method &quot;getTypeId&quot; to get its value.
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6c75]
     */
    public static final String TYPEID = "RSSEntry";

    /**
     * <p>
     * Represents name of this entity.
     * </p>
     * <p>
     * It is initialized in constructor. It should not be null or empty.
     * </p>
     * <p>
     * It is got in relative &quot;getter&quot; methods.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5a2b]
     */
    private final String name;

    /**
     * <p>
     * Represents ...
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm59d0]
     */
    private final String objectId;

    /**
     * <p>
     * Constructor of RSSEntry.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. set parameters to their relative members. Specially, updatedDate should copy its value into member
     * &quot;updatedDate&quot; to make thread safety
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6c49]
     * @param name
     *            name of the RSSEntry, which should not be null or empty
     * @param objectId
     *            object id of the RSSEntry, which may be null and should not be empty,
     * @param description
     *            description string of the ESSEntry, which should not be null
     * @param link
     *            url of the RSSEntry, which should not be null
     * @param isRead
     *            whether the entry is read or not
     * @param updatedDate
     *            updated date of the RSSEntry, which should not be null
     * @throws IllegalArgumentException
     *             if any parameter doesn't satisfy its condition
     */
    public RSSFeedContentEntry(String name, String objectId, String description, URL link, boolean isRead,
            java.util.Date updatedDate) {
        this.name = name;
        this.objectId = objectId;
        this.description = description;
        this.link = link;
        this.isRead = isRead;
        this.updatedDate = updatedDate;

    }

    /**
     * <p>
     * Get description member.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return description
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm69f1]
     * @return the description member
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Get linkof the RSSEntry
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return link.
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm69c7]
     * @return the link url of the entity
     */
    public URL getLink() {
        // your code here
        return link;
    }

    /**
     * <p>
     * Check whether the RSSEntry is read or not
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return isRead
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm69a0]
     * @return whether RSSEntry is read or not
     */
    public boolean isRead() {
        // your code here
        return isRead;
    }

    /**
     * <p>
     * Get the copy of updated date.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return updatedDate.clone();
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm697a]
     * @return the copy of updated date
     */
    public java.util.Date getUpdatedDate() {
        // your code here
        return updatedDate;
    }

    /**
     * <p>
     * Get byte array of RSSEntry. If any Object member is null, throw IllegalStateException.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. open a ByteArrayOutputStream and DataOutputStream. [ByteArrayOutputStream baos = new ByteArrayOutputStream();
     * DataOutputStream dos = new DataOutputStream(baos);]
     * </p>
     * <p>
     * 2. write byte array of name's length(an int value) and byte array of name string
     * </p>
     * <p>
     * 3.&nbsp; write byte array of objectId's length(an int value) and byte array of objectId string. Especially, if
     * objectId is null, that is user doesn't know this object's id in record store, set objectId to be -1.
     * </p>
     * <p>
     * 4. write byte array of description's length(an int value) and byte array of description string
     * </p>
     * <p>
     * 5. write byte array of link's length(an int value) and byte array of&nbsp; link string
     * </p>
     * <p>
     * 6.write isRead boolean.
     * </p>
     * <p>
     * 7. write byte array of updatedDate(a long time value)
     * </p>
     * <p>
     * 8&nbsp; get all byte array [byte[] array = baos.toByteArray();]
     * </p>
     * <p>
     * 9. close ByteArrayOutputStream and DataOutputStream.
     * </p>
     * <p>
     * 10. return array.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6954]
     * @return the byte array that represents RSSEntry
     * @throws IOException
     *             if fails happen in writing content into byte array
     * @throws IllegalStateException
     *             if any Object member is null
     */
    public byte[] getBytes() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Get type id of RSSEntry
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. reurn TYPEID;
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm692d]
     * @return type id of RSS entry
     */
    public String getTypeId() {
        // your code here
        return TYPEID;
    }

    /**
     * <p>
     * Return member &quot;name&quot;.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return name
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm59fa]
     * @return member "name"
     */
    public String getName() {
        // your code here
        return name;
    }

    /**
     * <p>
     * Get object id of this Entity.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return objectId
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm59a8]
     * @return object id of this entity
     */
    public String getObjectId() {
        // your code here
        return objectId;
    }
}
