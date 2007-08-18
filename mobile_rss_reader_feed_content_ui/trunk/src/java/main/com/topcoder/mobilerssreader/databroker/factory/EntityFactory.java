package com.topcoder.mobilerssreader.databroker.factory;

import com.topcoder.mobilerssreader.databroker.entity.Entity;
import java.io.*;

/**
 * EntityFactory interface works with other concrete factories as abstract factory method to create different Entity
 * instances.
 * <p>
 * It defines a&nbsp; method createEntity, which creates Entity instances according to input objectId and byte array
 * content.
 * </p>
 * <p>
 * Any entity factory instance should be registered into MobileDataBroker instance, and then can be used by
 * MobileDataBroker internally to create related Entities.
 * </p>
 * <p>
 * Thread safety: As an interface, it has no member, and its implementation should provide synchronized way to create
 * entity..
 * </p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm68c9]
 */
public class EntityFactory {

    /**
     * <p>
     * Create Entity instance. Sub class should implements its own way to create related Entity instance. For example,
     * in TagFactory#createEntity method, it should implement this method to create Tag entity.
     * </p>
     * 
     * @poseidon-object-id [I7ff0f7d9m1138469cc34mm63da]
     * @param objectId
     *            the new created object id of entity, which is index of record in RecordStore, which should not be less
     *            than 1
     * @param content
     *            byte array of entity content stored as record in RecordStore, which should not be null
     * @return a new created Entity instance
     * @throws IllegalArgumentException
     *             if objectId is less than 1, content is null
     */
    protected Entity createEntity(int objectId, byte[] content) {
        // your code here
        return null;
    }
}
