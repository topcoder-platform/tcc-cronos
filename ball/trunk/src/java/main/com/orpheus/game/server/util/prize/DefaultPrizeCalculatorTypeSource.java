/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util.prize;

import com.orpheus.game.server.framework.prize.PrizeCalculatorType;
import com.orpheus.game.server.framework.prize.PrizeCalculatorTypeSource;
import com.orpheus.game.server.framework.prize.PrizeException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>A default implementation of {@link PrizeCalculatorTypeSource} interface. The instances of this class are
 * initialized based on the configuration parameters provided by the configuration files.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DefaultPrizeCalculatorTypeSource implements PrizeCalculatorTypeSource {

    /**
     * <p>A <code>String</code> providing the default confiuration namespace to be used for instantiating the instance
     * of this class.</p>
     */
    public static final String DEFAULT_NAMESPACE = DefaultPrizeCalculatorTypeSource.class.getName();

    /**
     * <p>A <code>Map</code> collecting the existing prize amount calculator types. Maps the <code>Integer</code>
     * providing the ID of the type to <code>PrizeCalculatorType</code> instance referenced by the specified ID.
     * </p>
     */
    private final Map types;

    /**
     * <p>Constructs new <code>DefaultPrizeCalculatorTypeSource</code> instance which is initialized based on
     * confuguration parameters provided by the default configuration namespace.</p>
     *
     * @throws PrizeException if a configuration is invalid.
     * @see    #DEFAULT_NAMESPACE
     */
    public DefaultPrizeCalculatorTypeSource() throws PrizeException {
        this.types = new LinkedHashMap();
        ConfigManager cm = ConfigManager.getInstance();
        try {
            String factoryNamespace = cm.getString(DEFAULT_NAMESPACE, "ObjectFactoryNamespace");
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(factoryNamespace));
            String[] typeKeys = cm.getStringArray(DEFAULT_NAMESPACE, "TypeKeys");
            for (int i = 0; i < typeKeys.length; i++) {
                PrizeCalculatorType type = (PrizeCalculatorType) factory.createObject(typeKeys[i]);
                Integer typeId = new Integer(type.getId());
                if (this.types.containsKey(typeId)) {
                    throw new PrizeException("Duplicate prize amount calculator type with ID [" + typeId + "]");
                } else {
                    this.types.put(typeId, type);
                }
            }
        } catch (Exception e) {
            throw new PrizeException("Could not instantiate the prize calculator type source due to unexpected error",
                                     e);
        }

    }

    /**
     * <p>Gets the list of all existing prize amount calculator types provided by this source.</p>
     *
     * @return a <code>Collection</code> of <code>PrizeCalculatorType</code> objects representing the existing
     *         prize amount calculator types.
     */
    public Collection getAllTypes() {
        return new ArrayList(this.types.values());
    }

    /**
     * <p>Gets the prize amount calculator type referenced by the specified ID.</p>
     *
     * @param id a <code>int</code> providing the ID of desired prize amount calculator type.
     * @return a <code>PrizeCalculatorType</code> mapped to specified ID.
     * @throws PrizeException if there is no prize amount calculator type mapped to specified ID or an unexpected error
     * is encountered.
     */
    public PrizeCalculatorType getPrizeCalculatorType(int id) throws PrizeException {
        Integer idObject = new Integer(id);
        if (this.types.containsKey(idObject)) {
            return (PrizeCalculatorType) this.types.get(idObject);
        } else {
            throw new PrizeException("No prize amount calculator type mapped to ID [" + id + "]");
        }
    }
}
