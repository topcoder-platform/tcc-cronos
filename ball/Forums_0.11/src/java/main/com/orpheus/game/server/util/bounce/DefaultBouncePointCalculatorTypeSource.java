/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.bounce;

import com.orpheus.game.server.framework.bounce.BouncePointCalculatorType;
import com.orpheus.game.server.framework.bounce.BouncePointCalculatorTypeSource;
import com.orpheus.game.server.framework.bounce.BouncePointException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>A default implementation of {@link BouncePointCalculatorTypeSource} interface. The instances of this class are
 * initialized based on the configuration parameters provided by the configuration files.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DefaultBouncePointCalculatorTypeSource implements BouncePointCalculatorTypeSource {

    /**
     * <p>A <code>String</code> providing the default confiuration namespace to be used for instantiating the instance
     * of this class.</p>
     */
    public static final String DEFAULT_NAMESPACE = DefaultBouncePointCalculatorTypeSource.class.getName();

    /**
     * <p>A <code>Map</code> collecting the existing bounce point calculator types. Maps the <code>Integer</code>
     * providing the ID of the type to <code>BouncePointCalculatorType</code> instance referenced by the specified ID.
     * </p>
     */
    private final Map types;

    /**
     * <p>Constructs new <code>DefaultBouncePointCalculatorTypeSource</code> instance which is initialized based on
     * confuguration parameters provided by the default configuration namespace.</p>
     *
     * @throws BouncePointException if a configuration is invalid.
     * @see    #DEFAULT_NAMESPACE
     */
    public DefaultBouncePointCalculatorTypeSource() throws BouncePointException {
        this.types = new LinkedHashMap();
        ConfigManager cm = ConfigManager.getInstance();
        try {
            String factoryNamespace = cm.getString(DEFAULT_NAMESPACE, "ObjectFactoryNamespace");
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(factoryNamespace));
            String[] typeKeys = cm.getStringArray(DEFAULT_NAMESPACE, "TypeKeys");
            for (int i = 0; i < typeKeys.length; i++) {
                BouncePointCalculatorType type = (BouncePointCalculatorType) factory.createObject(typeKeys[i]);
                Integer typeId = new Integer(type.getId());
                if (this.types.containsKey(typeId)) {
                    throw new BouncePointException("Duplicate bounce point calculator type with ID [" + typeId + "]");
                } else {
                    this.types.put(typeId, type);
                }
            }
        } catch (Exception e) {
            throw new BouncePointException("Could not instantiate the type source due to unexpected error", e);
        }

    }

    /**
     * <p>Gets the list of all existing bounce point calculator types provided by this source.</p>
     *
     * @return a <code>Collection</code> of <code>BouncePointCalculatorType</code> objects representing the existing
     *         bounce point calculator types.
     */
    public Collection getAllTypes() {
        return new ArrayList(this.types.values());
    }

    /**
     * <p>Gets the bounce point calculator type referenced by the specified ID.</p>
     *
     * @param id a <code>int</code> providing the ID of desired bounce point calculator type.
     * @return a <code>BouncePointCalculatorType</code> mapped to specified ID.
     * @throws BouncePointException if there is no bounce point calculator type mapped to specified ID or an unexpected
     * error is encountered.
     */
    public BouncePointCalculatorType getBouncePointCalculatorType(int id) throws BouncePointException {
        Integer idObject = new Integer(id);
        if (this.types.containsKey(idObject)) {
            return (BouncePointCalculatorType) this.types.get(idObject);
        } else {
            throw new BouncePointException("No bounce point calculator type mapped to ID [" + id + "]");
        }
    }
}
