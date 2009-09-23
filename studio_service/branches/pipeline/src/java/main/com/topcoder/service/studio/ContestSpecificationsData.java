/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * It is the DTO class which is used to transfer contest specifications data.
 * The information can be null or can be empty, therefore this check is not
 * present in the setters. It's the related to the equivalent
 * ContestSpecifications entity.
 * </p>
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.3
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestSpecificationsData", propOrder = { "colors", "fonts", "layoutAndSize",
        "additionalRequirementsAndRestrictions" })
public class ContestSpecificationsData implements Serializable {
    /**
     * The colors to be used. Can be any value. Has getter and setter.
     */
    private String colors;
    /**
     * The fonts to be used. Can be any value. Has getter and setter.
     */
    private String fonts;
    /**
     * The layout and size. Can be any value. Has getter and setter.
     */
    private String layoutAndSize;
    /**
     * The additional requirements and restrictions. Can be any value. Has
     * getter and setter.
     */
    private String additionalRequirementsAndRestrictions;

    /**
     * Creates an instance of ContestSpecificationsData.
     *
     */
    public ContestSpecificationsData() {
    }

    /**
     * Gets the value of the colors to be used.
     *
     * @return the value of the colors to be used.
     */
    public String getColors() {
        return this.colors;
    }

    /**
     * Sets the value of the colors to be used.
     *
     * @param colors the new value for the colors to be used.
     */
    public void setColors(String colors) {
        this.colors = colors;
    }

    /**
     * Gets the value of the fonts to be used.
     *
     * @return the value of the fonts to be used.
     */
    public String getFonts() {
        return this.fonts;
    }

    /**
     * Sets the value of the fonts to be used.
     *
     * @param fonts the new value for the fonts to be used.
     */
    public void setFonts(String fonts) {
        this.fonts = fonts;
    }

    /**
     * Gets the value of the layout and size attribute.
     *
     * @return the value of the layout and size.
     */
    public String getLayoutAndSize() {
        return this.layoutAndSize;
    }

    /**
     * Sets the value of the layout and size attribute.
     *
     * @param layoutAndSize the new value for the layout and size.
     */
    public void setLayoutAndSize(String layoutAndSize) {
        this.layoutAndSize = layoutAndSize;
    }

    /**
     * Gets the value of the additional requirements and restrictions attribute.
     *
     * @return the value of the additional requirements and restrictions
     *         attribute.
     */
    public String getAdditionalRequirementsAndRestrictions() {
        return this.additionalRequirementsAndRestrictions;
    }

    /**
     * Sets the value of the additional requirements and restrictions attribute.
     *
     * @param additionalRequirementsAndRestrictions the new value for the
     *        additional requirements and restrictions.
     */
    public void setAdditionalRequirementsAndRestrictions(String additionalRequirementsAndRestrictions) {
        this.additionalRequirementsAndRestrictions = additionalRequirementsAndRestrictions;
    }
}
