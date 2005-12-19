/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * TCS Project Template Selector 1.0 (Mock)
 *
 * @ ComponentVersion.java
 */
package com.topcoder.buildutility.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class ComponentVersion for test purpose only.
 *
 * @author mgmg
 *
 * @version 1.0
 */
public class ComponentVersion {
    /**
     * <p>The component version id.</p>
     */
    private long id;

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     *
     */
    private String description;

    /**
     * Version
     *
     */
    private String version;

    /**
    * <p>The technology types list of component version.</p>
    */
    private List technologyTypes = new ArrayList();

    /**
     * <p>The component version's attributes.</p>
     */
    private Map attributes = new HashMap();

    /**
     * Empty Constructor.
     *
     */
    public ComponentVersion() {
        this.id = 0;
    }

    /**
     * Constructor
     *
     * @param ID
     * @param attributes
     * @param technologyTypes
     */
    public ComponentVersion(long ID, Map attributes, TechnologyType[] technologyTypes) {
        this.id = ID;
        this.attributes = attributes;

        for (int i = 0; i < technologyTypes.length; i++) {
            this.technologyTypes.add(technologyTypes[i]);
        }
    }

    /**
     * Constructor
     * 
     * @param attributeNames
     * @param attributeValues
     * @param technology
     */
    public ComponentVersion(String[] attributeNames, String[] attributeValues, TechnologyType[] technology) {
    	this("", "", attributeNames, attributeValues, technology);
    }

    /**
     * <p>Creates a ComponentVersion instance with the specified id.</p>
     *
     * @param id the id of the component version
     */
    public ComponentVersion(long id) {
        this.id = id;
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public ComponentVersion(Long id) {
        this.id = id.longValue();
    }

    /**
     * Creates a new ComponentVersion object.
     *
     * @param id Id
     * @param name Name
     * @param description Description
     * @param version Version
     */
    public ComponentVersion(long id, String name, String description, String version) {
        this.id = id;
    }

    /**
     * Constructor.
     *
     * <p>
     * Mock Constructor
     * </p>
     * @param name the name of the ComponentVersion
     * @param description the description of the ComponentVersion
     * @param attributesNames the attributesNames of the ComponentVersion
     */
    public ComponentVersion(String name, String description, String[] attributesNames,
        String[] attributesValues, TechnologyType[] technologyTypes) {
        // Set up properties.
        for (int i = 0; i < attributesNames.length; i++) {
            this.attributes.put(attributesNames[i], attributesValues[i]);
        }

        for (int i = 0; i < technologyTypes.length; i++) {
            this.technologyTypes.add(technologyTypes[i]);
        }
    }

    /**
     * <p>Identifier that uniquely distinguishes this component version from all others.</p>
     *
     * @return long unique identifier id
     */
    public Long getId() {
        return new Long(id);
    }

    /**
     * Set the Id.
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Returns the name of the component.</p>
     *
     * @return null, since there is no need to use this property
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Returns a description of the component.</p>
     *
     * @return null, since there is no need to use this property
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description.
     *
     * @param des
     */
    public void setDescription(String des) {
        this.description = des;
    }

    /**
     * <p>Returns the version number using TopCoder's defined build versioning format. Please consult the Build
     * Versioning and Lifetime support documentation for a description of this string.</p>
     *
     * @return null, since there is no need to use this property
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Set the version
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * <p>Returns the vallue associated with the specified attribute.</p>
     *
     * @param name name of the attribute
     * @return String attribute value
     */
    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    /**
     * <p>Set attribute using specified name and value.</p>
     *
     * @param name the name of the attribute
     * @param value the value of the attribute
     */
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Default comment.
     *
     * @param name Name
     * @param value Value
     */
    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * <p>Returns an array of strings that are the names of the attributes. Use the names and the getAttribute
     * method to obtain the list of values corresponding to each name.</p>
     *
     * @return the array of attribute names, if there is no entries in attribute, returns an empty array
     */
    public String[] getAttributeNames() {
        return (String[]) attributes.keySet().toArray(new String[0]);
    }

    /**
     * <p>Returns a list of external components by version that this component depends upon.</p>
     *
     * @return empty array, since there is no need to use this property
     */
    public ExternalComponentVersion[] getExternalDependencies() {
        return new ExternalComponentVersion[0];
    }

    /**
     * <p>Returns a list of other components from within TopCoder's component catalog that this component
     * depends upon.</p>
     *
     * @return empty array, since there is no need to use this property
     */
    public ComponentVersion[] getDependencies() {
        return new ComponentVersion[0];
    }

    /**
     * <p>Returns a list of the technology types associated with this component.</p>
     *
     * @return the list of technology types of this component, can ben an empty array.
     */
    public TechnologyType[] getTechnologyTypes() {
        return (TechnologyType[]) technologyTypes.toArray(new TechnologyType[0]);
    }

    /**
     * <p>Add a technology type to the list.</p>
     *
     * @param technologyType the technology type to add
     */
    public void addTechnologyType(TechnologyType technologyType) {
        technologyTypes.add(technologyType);
    }

    /**
     * <p>Add a new TechnologyType instance with the given name.</p>
     *
     * @param name the name of the TechnologyType
     */
    public void setTechnologyType(String name) {
        technologyTypes.add(new TechnologyType(name, null));
    }

    /**
     *
     * @param typeName the technologyType name.
     * @param description the description of the type.
     */
    public void setTechnologyType(String typeName, String description) {
        this.technologyTypes.add(new TechnologyType(typeName, description));
    }
}
