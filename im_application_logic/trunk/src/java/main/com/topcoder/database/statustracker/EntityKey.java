/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * <p>
 * This class encapsulates a compound primary key of an entity instance, and the actual Entity type
 * associated with it.
 * </p>
 *
 * <p>
 * This class acts as an unique identifier for an entity instance to be tracked.
 * It is used to store and retrieve the status of an entity instance in persistent storage by an
 * EntityStatusTracker implementation.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is immutable and therefore thread-safe.
 * </p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 * @see Entity
 */
public class EntityKey {
    /**
     * <p>
     * This constant represents the separator character used in this class.
     * </p>
     */
    private static final String SEPARATOR = "|";

    /**
     * <p>
     * This constant represents the regular expression for the separator character used in this class.
     * </p>
     */
    private static final String SEPARATOR_REGEX = "[|]";

    /**
     * <p>
     * This variable represents the entity type of this entity instance key.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null.
     * </p>
     */
    private final Entity type;

    /**
     * <p>
     * This variable represents the map between primary key column names as Strings and their values,
     * as Objects, which uniquely identify an entity instance of this object's Entity type.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null.
     * No keys will be null or empty after trim.
     * No values will be null, if they are of String type, then they will be not empty after trim.
     * This map must never be changed.
     * </p>
     */
    private final Map pkValues;

    /**
     * <p>
     * This variable is a quasi-serialized representation of the map between primary key
     * column names as Strings and primary key column values as Strings.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null or empty.
     * It is returned by toString() method.
     * </p>
     */
    private final String serializedValues;

    /**
     * <p>
     * Construct this entity primary key using the given parameters.
     * </p>
     *
     * <p>
     * Note, the keys in the values map are of type String, and the values in the values map are of type Object.
     * </p>
     *
     * @param type the entity type of this entity key.
     * @param values a map between primary key column names as Strings and primary key column values
     * as Objects.
     *
     * @throws IllegalArgumentException if type is null, or if values is null, or if the keys of the values
     * map do not correspond exactly to the primary key values in the Entity type, or if there are any nulls
     * or empty Strings (after trim) as keys in the values map, or if there are any nulls objects as values in
     * the values map, if the value in the values map is of String map, then it cannot be empty after trim or
     * contains a pipe (|) character.
     */
    public EntityKey(Entity type, Map values) {
        checkArguments(type, values);

        this.type = type;
        pkValues = Collections.unmodifiableMap(values);

        // generates the serializedValues for toString() method
        Set keys = values.keySet();
        String[] columns = (String[]) keys.toArray(new String[keys.size()]);
        StringBuffer concatedValues = new StringBuffer();
        // make sure all the key columns in lexicographic order
        Arrays.sort(columns);
        for (int i = 0; i < columns.length; i++) {
            // separator character is used to separate all the primary column values
            if (i != 0) {
                concatedValues.append(SEPARATOR);
            }
            concatedValues.append(values.get(columns[i]));
        }
        this.serializedValues = concatedValues.toString();
    }

    /**
     * <p>
     * Construct this entity primary key using the given parameters.
     * </p>
     *
     * @param type the entity type of this entity key.
     * @param serializedValues a quasi-serialized representation of the map between primary key
     * column names as Strings and primary key column values as Strings.
     *
     * @throws IllegalArgumentException if type is null, or if serializedValues is null or empty after trim,
     * or if the keys of the resulting values map do not correspond exactly to the primary key values in the
     * given Entity type.
     */
    public EntityKey(Entity type, String serializedValues) {
        Util.checkNull(type, "type");
        Util.checkString(serializedValues, "serializedValues");

        // deserialize the values string
        String[] tokens = serializedValues.split(SEPARATOR_REGEX);
        String[] columns = type.getPrimaryKeyColumns();

        // the length of values and columns should be equal
        if (tokens.length != columns.length) {
            throw new IllegalArgumentException("The length of values is not equals to the length of primary "
                + "key columns.");
        }

        // make sure all the key columns in lexicographic order
        Arrays.sort(columns);

        // build a primary key values map
        Map tempValues = new HashMap();
        for (int i = 0; i < columns.length; i++) {
            tempValues.put(columns[i], tokens[i]);
        }

        this.type = type;
        this.pkValues = Collections.unmodifiableMap(tempValues);
        this.serializedValues = serializedValues;
    }

    /**
     * <p>
     * Returns the map between primary key column names and their values to uniquely identify an
     * entity instance of this object's Entity type.
     * </p>
     *
     * <p>
     * Note, the returned map will never be null or empty.
     * </p>
     *
     * @return Returns an unmodifiable key-value mapping of the primary key represented by this object.
     * Will never be null.
     */
    public Map getValues() {
        return pkValues;
    }

    /**
     * <p>
     * Returns the entity type of this entity instance key.
     * </p>
     *
     * @return Returns the Entity type of this EntityKey, will never be null.
     */
    public Entity getType() {
        return type;
    }

    /**
     * <p>
     * Returns a "serialized" version of the primary key values.
     * </p>
     *
     * <p>
     * Note, the order of the keys should always be in lexicographic order, so that it is consistently
     * reproducible.
     * </p>
     *
     * @return a serialized version of the primary key/value pairs.
     */
    public String toString() {
        return serializedValues;
    }

    /**
     * <p>
     * This method performs the argument checking for the constructor EntityKey(Entity,Map).
     * </p>
     *
     * @param type the entity type of this entity key.
     * @param values a map between primary key column names as Strings and primary key column values
     * as Objects.
     *
     * @throws IllegalArgumentException if type is null, or if values is null, or if the keys of the values
     * map do not correspond exactly to the primary key values in the Entity type, or if there are any nulls
     * or empty Strings (after trim) as keys in the values map, or if there are any nulls objects as values in
     * the values map, if the value in the values map is of String map, then it cannot be empty after trim or
     * contains a pipe (|) character.
     */
    private void checkArguments(Entity type, Map values) {
        Util.checkNull(type, "type");
        Util.checkNull(values, "values");
        String[] primaryKeys = type.getPrimaryKeyColumns();

        // the size of values and the length of primary keys should be equal
        if (values.size() != primaryKeys.length) {
            throw new IllegalArgumentException("The length of the values map is not equal to "
                + "the length of primary key columns in the entity.");
        }

        // a set is used to store all the primary keys
        // the type is String.
        Set primaryKeysSet = new HashSet();
        for (int i = 0; i < primaryKeys.length; i++) {
            primaryKeysSet.add(primaryKeys[i]);
        }

        for (Iterator it = values.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            Object keyObj = entry.getKey();
            Object valueObj = entry.getValue();

            // the keys must be of String type
            if (!(keyObj instanceof String)) {
                throw new IllegalArgumentException("The values map contains non-string key.");
            }
            String key = (String) keyObj;

            // null or empty (trimmed) string is not allowed
            if (key == null) {
                throw new IllegalArgumentException("The values map contains null key string.");
            }
            if (key.trim().length() == 0) {
                throw new IllegalArgumentException("The values map contains empty key string.");
            }

            // the key should be one of primary keys
            if (!primaryKeysSet.contains(key)) {
                throw new IllegalArgumentException("The values map contains a key (" + key + ") that does not "
                    + "correspond exactly to the primary key values in the Entity type.");
            }

            // null value is not allowed
            if (valueObj == null) {
                throw new IllegalArgumentException("The values map contains null value object.");
            }

            // if the value is of String type, then empty (trimmed) string is not allowed
            // if the value contains the separator character, it is not allowed too
            if (valueObj instanceof String) {
                String value = (String) valueObj;
                if (value.trim().length() == 0) {
                    throw new IllegalArgumentException("The values map contains empty value string.");
                }
                if (value.indexOf(SEPARATOR) >= 0) {
                    throw new IllegalArgumentException("At least one value in the values map contains a pipe (|) "
                        + "character.");
                }
            }
        }
    }
}