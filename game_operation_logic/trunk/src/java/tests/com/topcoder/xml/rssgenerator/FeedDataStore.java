/*
 * FeedDataStore.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator;

/**
 * <p>
 * An interface to load and create a Feed from a data store. It only has a single getFeed method taking no arguments to
 * retrieve a specific Feed instance.
 * </p>
 * 
 * <p>
 * Extra information should be provided in its implementation classes before calling the getFeed method (possibly in
 * the constructor), since this interface knows nothing about the data source. For example, an xml file can be
 * specified in the constructor if loading from the file data source; the database connection and a id to uniquely
 * identify a Feed can be specified if loading from the database data source.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public interface FeedDataStore {
    /**
     * <p>
     * Return the Feed instance loaded and created from the data in the data store. If there is problem with the data
     * store (the file i/o error, database fails), or the data in the data store is insufficient or unrecognizable,
     * FeedRetrievalException will be thrown.
     * </p>
     *
     * @return the Feed instance created from the data in data store.
     *
     * @throws FeedRetrievalException if the creation fails.
     */
    Feed getFeed() throws FeedRetrievalException;
}
