package com.topcoder.mobilerssreader.databroker.entity;

import java.io.*;
import java.util.Date;
import java.util.Vector;

import com.topcoder.mobile.httphandler.URL;

/**
 * <p>
 * This class extends Entry class to RSS feed content object in this component.
 * </p>
 * <p>
 * It contains two part. One is meta data for RSS feed content, another is a list of RSS entries.
 * </p>
 * <p>
 * Its meta data includes name(title), objectId, description, link, updatedDate. All of them are initialized in
 * constructor and can be retrieved by &quot;getter&quot; methods.
 * </p>
 * <p>
 * The list of RSS entries is maintained by some container operations, like
 * &quot;addRSSEntry&quot;/&quot;updateRSSEntry&quot;/ &quot;removeRSSEntry&quot;. All operations are synchronized on
 * member &quot;entries&quot;.
 * </p>
 * <p>
 * It implements getBytes method to provide its own byte array representation, which will be store into its RMS storage.
 * </p>
 * <p>
 * It also implements getTypeId method to provide its own type id, which will be used to name its RMS storage and refer
 * it.
 * </p>
 * <p>
 * Thread safety: Meta member are mutable and their &quot;setter&quot; are synchronized. The entries vector is modified
 * in synchronized way. Hence, this class is thread safe.
 * </p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7a50]
 */
public class RSSFeedContent extends com.topcoder.mobilerssreader.databroker.entity.Entity {

    /**
     * <p>
     * Represents description of RSS feed content.
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
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6ff4]
     */
    private String description;

    /**
     * <p>
     * Represents link of the RSS feed content.
     * </p>
     * <p>
     * It is initialized in constructor and should not be null.
     * </p>
     * <p>
     * It is got in relative &quot;getter&quot; methods.
     * </p>
     * 
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6fda]
     */
    private URL link;

    /**
     * <p>
     * Represents updated date of&nbsp; rss feed content.
     * </p>
     * <p>
     * It is initialized in constructor and should not be null.
     * </p>
     * <p>
     * It is got in the &quot;getter&quot; method.
     * </p>
     * 
     * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm6fc0]
     */
    private Date updatedDate;

    /**
     * <p>
     * Represents the array of entries that this feed content has. It is a dynamic vector, whose element is instances of
     * RSSFeedContentEntry. The RSSFeedContentEntry instance can be added/removed/got from it.
     * </p>
     * <p>
     * It is initialized as an empty Vector in constructor. It should not be null and may be empty before adding new
     * RSSFeedContentEntry. Its element, that is RSSFeedContentEntry, should not be null.
     * </p>
     * <p>
     * It is used in methods like &quot;addRSSEntry&quot;, &quot;removeRSSEntry&quot;, &quot;updateRSSEntry&quot;,
     * &quot;getRSSEntry&quot; and &quot;getAllRSSEntries&quot;.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm7486]
     */
    private final Vector entries = new Vector();

    /**
     * <p>
     * Represents type id of RSSFeedContent class. Its value is &quot;RSSFeedContent&quot; string. It will be used to
     * refered as class type and RMS storage name.
     * </p>
     * <p>
     * It is initialized as final and static string.
     * </p>
     * <p>
     * It is used in method &quot;getTypeId&quot; to get its value.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm740b]
     */
    public static final String TYPEID = "RSSFeedContent";

    /**
     * <p>
     * Constructor of RSSFeedContent.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. super(name, objectId).
     * </p>
     * <p>
     * 2. set parameters to their relative members. Specially, updatedDate should copy its value into member
     * &quot;updatedDate&quot; to make thread safety
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm73d2]
     * @param name
     *            name of the RSSFeedContent, which should not be null or empty
     * @param objectId
     *            object id of the RSSFeedContent, which may be null and should not be empty,
     * @param description
     *            description string of the RSSFeedContent, which should not be null
     * @param link
     *            url of the RSSFeedContent, which should not be null
     * @param updatedDate
     *            updated date of the RSS feed content, which should not be null
     * @throws IllegalArgumentException
     *             if any parameter doesn't satisfy its condition
     */
    public RSSFeedContent(String name, String objectId, String description, URL link, Date updatedDate) {
        super(name, objectId);
        this.description = description;
        this.link = link;
        this.updatedDate = updatedDate;

    }

    /**
     * <p>
     * Constructor of RSSFeedContent.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. this(name, objectId,description, link, updatedDate).
     * </p>
     * <p>
     * 2. Add new enteries into member &quot;entries&quot;
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm720c]
     * @param name
     *            name of the RSSFeedContent, which should not be null or empty
     * @param objectId
     *            object id of the RSSFeedContent, which may be null and should not be empty,
     * @param description
     *            description string of the RSSSubscription, which should not be null
     * @param link
     *            url of the RSSSubscription, which should not be null
     * @param updatedDate
     *            updated date of the RSS feed content, which should not be null
     * @param entries
     *            new added RSSEntry instances, which should not be null and empty
     * @throws IllegalArgumentException
     *             if any parameter doesn't satisfy its condition
     */
    public RSSFeedContent(String name, String objectId, String description, URL link, Date updatedDate,
            RSSFeedContentEntry[] entries) {
        this(name, objectId, description, link, updatedDate);
        for (int i = 0; i < entries.length; i++) {
            this.entries.addElement(entries[i]);
        }
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
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6fb3]
     * @return the description member
     */
    public String getDescription() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Get link of the RSSFeedContent
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
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6f8d]
     * @return the link url of the entity
     */
    public URL getLink() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Get the copy of updated date.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return a copy of updatedDate
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6f68]
     * @return the copy of updated date
     */
    public java.util.Date getUpdatedDate() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Add new RSS feed content entry into this feed content.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. synchronized on member &quot;entries&quot;, and call enteries.addElement(entry)
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6f42]
     * @param entry
     *            new RSS entry for this feed content, which should not be null
     * @throws IllegalArgumentException
     *             if entry is null
     */
    public void addRSSEntry(com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry entry) {
        // your code here
    }

    /**
     * <p>
     * Update entry in feed content.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. synchronized on member &quot;enteries&quot;, call enteries.setElementAt(entry, index).
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6efa]
     * @param index
     *            the specified index, which should be greater than 0 and less than enteries' capacity
     * @param entry
     *            entry to update the feed content, which should not be null
     * @throws IllegalArgumentException
     *             if index is less than 0 or greater than enteries' capacity, entry is null
     */
    public void updateRSSEntry(int index, com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry entry) {
        // your code here
    }

    /**
     * <p>
     * Remove the specified entry from member &quot;enteries&quot;
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. synchronized on &quot;enteries&quot; member, call removeElement(entry) method.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6e81]
     * @param entry
     *            rss entry to be removed from the feed content, which should not be null
     * @throws IllegalArgumentException
     *             if entry is null
     */
    public void removeRSSEntry(com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry entry) {
        // your code here
    }

    /**
     * <p>
     * Get entry from member &quot;enteries&quot;
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return enteries.elementAt(index)
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6e39]
     * @param index
     *            the specified index to get entry from member "enteries", which should not be less than 0 or greater
     *            than capacity
     * @return the instance of RSSEntry stored at "index" position.
     * @throws IllegalArgumentException
     *             if index is less than 0 or greater than capacity
     */
    public com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry getRSSEntry(int index) {
        // your code here
        return null;
    }

    /**
     * <p>
     * Get all enteries in feed content.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. synchronized on &quot;entries&quot;, and copy all elements in &quot;entries&quot; into RSSEntry[], return it.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6df0]
     * @return array of all entries in feed content
     */
    public RSSFeedContentEntry[] getAllRSSEntries() {
        synchronized (entries) {
            RSSFeedContentEntry[] contentEntries = new RSSFeedContentEntry[entries.size()];
            for (int i = 0; i < entries.size(); i++) {
                contentEntries[i] = (RSSFeedContentEntry) entries.elementAt(i);
            }
            return contentEntries;
        }

    }

    /**
     * <p>
     * Get byte array of RSSFeedContent. If any Object member is null, throw IllegalStateException.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. open a ByteArrayOutputStream and DataOutputStream. [ByteArrayOutputStream baos = new ByteArrayOutputStream();<br/>
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
     * 5. write byte array of link's length(an int value) and byte array of link string
     * </p>
     * <p>
     * 6.write byte array of updatedDate(a long time value)
     * </p>
     * <p>
     * 7 for every element in entries, call RSSEntry.getBytes() and write into ByteArrayOutputStream
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
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6dc9]
     * @return byte array of this RSSSubscription instance
     * @throws IllegalStateException
     *             if any Object member is null
     * @throws IOException
     *             if fails happen in writing content into byte array
     */
    public byte[] getBytes() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Get type id of RSSSubscription
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
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6da1]
     * @return type id of the RSSSubscription
     */
    public String getTypeId() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Set member description.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1 this.description = description;
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm4c82]
     * @param description
     *            new updated description, which should not be null or empty
     * @throws IllegalArgumentException
     *             if description is null or empty
     */
    public synchronized void setDescription(String description) {
        // your code here
    }

    /**
     * <p>
     * Set updated link for this RSSFeedContent.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. this.link = link
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm4c3a]
     * @param link
     *            updated new link, which should not be null
     * @throws IllegalArgumentException
     *             if link is null or empty
     */
    public synchronized void setLink(com.topcoder.mobile.httphandler.URL link) {
        // your code here
    }

    /**
     * <p>
     * Set new updatedDate
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. copy updatedDate into member &quot;updatedDate&quot;
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm4be7]
     * @param updatedDate
     *            new updated Date, which should not be null
     * @throws IllegalArgumentException
     *             if updatedDate is null
     */
    public synchronized void setUpdatedDate(java.util.Date updatedDate) {
        // your code here
    }

    /**
     * <p>
     * Get EntitId instance that represents this entity.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. return new EntityId(super.objectId, TYPEID);
     * </p>
     * <p>
     * </p>
     * 
     * @poseidon-object-id [I14a7a8dam113c98ea041mm53a9]
     * @return EntityId instance that represents this entity
     */
    public com.topcoder.mobilerssreader.databroker.entity.EntityId getEntityId() {
        // your code here
        return null;
    }
}
