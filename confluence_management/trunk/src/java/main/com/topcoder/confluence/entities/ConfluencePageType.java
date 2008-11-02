/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This enum represents the page type, that can be base/version page of the component, or base/version page of the
 * application.
 * </p>
 * <p>
 * We won't specify how each enum name is mapped to XML schema so enum names will be mapped as enum values.
 * </p>
 * <p>
 * <b>Thread Safety:</b> this class is immutable so thread-safe
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlRootElement(name = "pageType")
@XmlType
@XmlEnum(String.class)
public enum ConfluencePageType {

    /**
     * <p>
     * This field corresponds to component base page. can be used with such ConfluenceAssetType values:
     * COMPONENT_DESIGN, COMPONENT_DEVELOPMENT.
     * </p>
     */
    COMPONENT_BASE_PAGE,

    /**
     * <p>
     * This field corresponds to component version page. can be used with such ConfluenceAssetType values:
     * COMPONENT_DESIGN, COMPONENT_DEVELOPMENT.
     * </p>
     */
    COMPONENT_VERSION_PAGE,

    /**
     * <p>
     * This field corresponds to application base page. can be used with such ConfluenceAssetType values:
     * APPLICATION_SPECIFICATION, APPLICATION_ARCHITECTURE, APPLICATION_ASSEMBLY, APPLICATION_TESTING.
     * </p>
     */
    APPLICATION_BASE_PAGE,

    /**
     * <p>
     * This field corresponds to application version page. can be used with such ConfluenceAssetType values:
     * APPLICATION_SPECIFICATION, APPLICATION_ARCHITECTURE, APPLICATION_ASSEMBLY, APPLICATION_TESTING.
     * </p>
     */
    APPLICATION_VERSION_PAGE
}
