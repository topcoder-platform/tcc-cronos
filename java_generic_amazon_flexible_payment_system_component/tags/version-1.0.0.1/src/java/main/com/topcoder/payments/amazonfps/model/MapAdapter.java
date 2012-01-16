/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>
 * The {@code MapAdapter} class is a subclass of {@code XmlAdapter} that supports marshalling and unmarshalling
 * of {@code Map<String, String>} properties with use of {@code MapEntry[]} value type. In this component such
 * adapter is required to support serialization and deserialization of {@code PaymentDetails#parameters} property.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is thread safe when collections passed to it are used by the caller in
 * thread safe manner.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class MapAdapter extends XmlAdapter<MapEntry[], Map<String, String>> {
    /**
     * Constructs new {@code MapAdapter} instance.
     */
    public MapAdapter() {
        // Empty
    }

    /**
     * Converts the given map of strings to an array of map entries.
     *
     * @param value
     *              the map of strings to be converted
     *
     * @return the list of map entries (can be null if the input parameter is null, otherwise it's not null array)
     */
    @Override
    public MapEntry[] marshal(Map<String, String> value) {
        if (value == null) {
            return null;
        }

        MapEntry[] mapEntries = new MapEntry[value.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : value.entrySet()) {
            mapEntries[i++] = new MapEntry(entry.getKey(), entry.getValue());
        }
        return mapEntries;
    }

    /**
     * Converts the given array of map entries to a map of strings.
     *
     * @param value
     *              the array of {@code MapEntry} instances to be converted
     *
     * @return the map of strings (can be null if the input parameter is null, otherwise it's not null map)
     */
    @Override
    public Map<String, String> unmarshal(MapEntry[] value) {
        if (value == null) {
            return null;
        }

        Map<String, String> result = new HashMap<String, String>();
        for (MapEntry mapEntry : value) {
            result.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return result;
    }
}
