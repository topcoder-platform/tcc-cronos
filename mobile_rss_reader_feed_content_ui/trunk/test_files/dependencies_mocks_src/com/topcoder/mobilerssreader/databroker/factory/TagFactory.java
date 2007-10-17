package com.topcoder.mobilerssreader.databroker.factory;

import com.topcoder.mobilerssreader.databroker.entity.Entity;
import com.topcoder.mobilerssreader.databroker.entity.Tag;
import java.io.*;
import java.io.IOException;

/**
 * This class implements EntityFactory interface to create Tag entity instance from input objectId and content.
 * <p>
 * It relates to Tag class and its typeid. If user wants to persist and load Tag entity, he can register the
 * RSSSubscriptionFactory class into MobileDataBroker&nbsp;first.
 * </p>
 * <p>
 * Thread safety: This class has no member, there is no chance to modify its state. Hence it is thread safe.
 * </p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6877]
 */
public class TagFactory extends com.topcoder.mobilerssreader.databroker.factory.EntityFactory {

    /**
     * <p>
     * Empty constructor
     * </p>
     * 
     * @poseidon-object-id [I134f7188m113896f2eecmm6c00]
     */
    public TagFactory() {
        // your code here
    }

    /**
     * <p>
     * Create Tag entity.
     * </p>
     * <p>
     * Implementation:
     * </p>
     * <p>
     * 1. Parse byte array content to create a new instance of Tag.
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
     * &nbsp;&nbsp; 1.d&nbsp;create Tag instance.[Tag entity = new Tag(name, objectId)]
     * </p>
     * <p>
     * &nbsp;&nbsp; 1.i close ByteArrayInputStream and DataInputStream.
     * </p>
     * <p>
     * 2. return entity.
     * </p>
     * 
     * @poseidon-object-id [I134f7188m113896f2eecmm6bdd]
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
