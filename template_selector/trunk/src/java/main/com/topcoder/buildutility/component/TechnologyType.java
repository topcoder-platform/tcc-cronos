/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Selector 1.0
 */
package com.topcoder.buildutility.component;

/**
 * <p>This class diagram for the technology type class is by no means fully completed.</p>
 *
 * <p>NOTE: this is a dummy implementation used to test the Template Selector component.</p>
 *
 * @author isv
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class TechnologyType {

    /**
     * The name of the technology.
     */
    private String name = null;

    /**
     * The description of the technology.
     */
    private String description = null;

    /**
     * <p>Creates a TechnologyType instance with the given name and description.</p>
     * <p>NOTE: This is an dummy implementation, so no validation are performed.</p>
     *
     * @param name the name the technology
     * @param description the description the technology
     */
    public TechnologyType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * <p>Creates a TechnologyType instance with the given name.</p>
     * <p>NOTE: This is an dummy implementation, so no validation are performed.</p>
     *
     * @param name the name the technology
     */
    public TechnologyType(String name) {
        this(name, null);
    }

    /**
     * <p>Returns the name of the technology.</p>
     *
     * @return the name of the technology.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Optional description of the technology.</p>
     *
     * @return the description of the technology.
     */
    public String getDescription() {
        return description;
    }
}
