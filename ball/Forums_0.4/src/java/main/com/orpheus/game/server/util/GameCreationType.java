/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.ConfigManager;

import java.util.Enumeration;

/**
 * <p>An enumeration over the game creation types (schemas) supported currently the <code>Admin</code> module. There are
 * no hard-coded items. Instead the enumeration is initialized from configuration file when the class is loaded.</p>
 *
 * @author isv
 * @version 1.0
 */
public class GameCreationType extends Enum {

    /**
     * <p>A <code>String</code> providing the configuration namespace to be used for static initialization of items in
     * this enumeration.</p>
     */
    public static final String NAMESPACE = "com.orpheus.game.server.GameCreationTypes";

    static {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            Enumeration typeNames = cm.getPropertyNames(NAMESPACE);
            while (typeNames.hasMoreElements()) {
                String typeName = (String) typeNames.nextElement();
                new GameCreationType(typeName, cm.getString(NAMESPACE, typeName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>A <code>String</code> providing the ID of this game completion type.</p>
     */
    private final String id;

    /**
     * <p>A <code>String</code> providing the description of this game completion type.</p>
     */
    private final String description;

    /**
     * <p>Constructs new <code>GameCreationType</code> instance with specified ID and description.</p>
     *
     * @param id a <code>String</code> providing the ID of the type.
     * @param description a <code>String</code> providing the description of the type.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or empty. 
     */
    private GameCreationType(String id, String description) {
        if ((id == null) || (id.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [id] is not valid. [" + id + "]");
        }
        if ((description == null) || (description.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [description] is not valid. [" + description + "]");
        }
        this.id = id;
        this.description = description;
    }

    /**
     * <p>Gets the ID of this game completion type.</p>
     *
     * @return a <code>String</code> providing the ID of this game completion type.
     */
    public String getId() {
        return this.id;
    }

    /**
     * <p>Gets the description for this game completion type.</p>
     *
     * @return a <code>String</code> providing the description for this game completion type.
     */
    public String getDescription() {
        return this.description;
    }
}
