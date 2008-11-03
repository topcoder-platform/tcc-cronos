/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This enum represents the asset type, such as component design, component development, application specification,
 * application architecture, application assembly, application testing.
 * </p>
 * <p>
 * We won't specify how each enum name is mapped to xml schema so enum names will be mapped as enum values.
 * </p>
 * <p>
 * <b>Thread Safety:</b> this class is immutable so thread-safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlRootElement(name = "assetType")
@XmlType
@XmlEnum(String.class)
public enum ConfluenceAssetType {

    /**
     * <p>
     * This field corresponds to component design asset type. can be used with such ConfluencePageType values:
     * COMPONENT_BASE_PAGE, COMPONENT_VERSION_PAGE.
     * </p>
     */
    COMPONENT_DESIGN,

    /**
     * <p>
     * This field corresponds to component development asset type. can be used with such ConfluencePageType values:
     * COMPONENT_BASE_PAGE, COMPONENT_VERSION_PAGE.
     * </p>
     */
    COMPONENT_DEVELOPMENT,

    /**
     * <p>
     * This field corresponds to application specification asset type. can be used with such ConfluencePageType
     * values: APPLICATION_BASE_PAGE, APPLICATION_VERSION_PAGE.
     * </p>
     */
    APPLICATION_SPECIFICATION,

    /**
     * <p>
     * This field corresponds to application architecture asset type. can be used with such ConfluencePageType
     * values: APPLICATION_BASE_PAGE, APPLICATION_VERSION_PAGE.
     * </p>
     */
    APPLICATION_ARCHITECTURE,

    /**
     * <p>
     * This field corresponds to application assembly asset type. can be used with such ConfluencePageType values:
     * APPLICATION_BASE_PAGE, APPLICATION_VERSION_PAGE.
     * </p>
     */
    APPLICATION_ASSEMBLY,

    /**
     * <p>
     * This field corresponds to application testing asset type. can be used with such ConfluencePageType values:
     * APPLICATION_BASE_PAGE, APPLICATION_VERSION_PAGE.
     * </p>
     */
    APPLICATION_TESTING
}
