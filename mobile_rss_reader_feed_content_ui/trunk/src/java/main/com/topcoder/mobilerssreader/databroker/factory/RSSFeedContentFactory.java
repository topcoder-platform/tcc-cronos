package com.topcoder.mobilerssreader.databroker.factory;

import com.topcoder.mobilerssreader.databroker.entity.Entity;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import java.io.*;
import java.io.IOException;

/**
 * This class implements EntityFactory interface to create RSSFeedContent entity instance from input objectId and
 * content.
 * <p>
 * It relates to RSSFeedContent class and its typeid. If user want to persist and load RSSFeedContent entity, he can
 * register the RSSFeedContentFactory class into MobileDataBroker&nbsp; first.
 * </p>
 * <p>
 * Thread safety: This class has no member, there is no chance to modify its state. Hence it is thread safe.
 * </p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm68a7]
 */
public class RSSFeedContentFactory extends EntityFactory {

    /**
     * <p>
     * Empty constructor.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm62c5]
     */
    public RSSFeedContentFactory() {
        // your code here
    }

    /**
     * <p>
     * Create RSSFeedContent entity.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. Parse byte array content to create a new instance of RSSFeedContent.
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.a Constructor ByteArrayInputStream and DataInputStream. [ByteArrayInputStream bais = new
     * ByteArrayInputStream(content); <br/> DataInputStream dis = new DataInputStream(bais);]
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.b read int value of name length, and then read name string according to the int value.
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.c read int value of objectId length, and then read objectId string according to the int value. If
     * objectId is equal to -1, use the input objectId.
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.d read int value of description length, and then read description string according to the int
     * value.
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.e read int value of link length, and then read link string according to the int value.
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.f read long value of updated date, and then create a new updatedDate instance.
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.g&nbsp;create RSSFeedContent instance.[RSSFeedContent entity = new RSSFeedContent(name, objectId,
     * description, link, updatedDate)]
     * </p>
     * <p>
     * 2. Then read bytes to create every RSSEntry instance.
     * </p>
     * <p>
     * &nbsp;&nbsp;&nbsp; 2.a For every RSS entries in content,&nbsp; read the following fields from content: name
     * string length(int value), name string, objectId string length(int value), objectId string, &nbsp;description
     * string length(int value), description string, link string length(int value), link string, isRead(boolean value),
     * updatedDate(long value).&nbsp;&nbsp;
     * </p>
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp; 2.b Create RSSEntry instance. [RSSEntry rssEntry = new RSSEntry(name, objectId,
     * description, link, isRead, updatedDate)]
     * </p>
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp; 2.c Add this new created instance into RSSFeedContent. [entity.addRSSEntry(rssEntry);]
     * </p>
     * <p>
     * 3. close ByteArrayInputStream and DataInputStream.
     * </p>
     * <p>
     * 4. return the new created RSSFeedContent.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6354]
     * @param objectId
     *            the new created object id of entity, which is index of record in RecordStore, which should not be less
     *            than 1
     * @param content
     *            byte array of entity content stored as record in RecordStore, which should not be null
     * @return a new created Entity instance
     * @throws IllegalArgumentException
     *             if objectId is less than 1, content is null
     * @throws IOException
     *             if failure happens in reading content
     */
    public Entity createEntity(int objectId, byte[] content) {
        // your code here
        return null;
    }
}
