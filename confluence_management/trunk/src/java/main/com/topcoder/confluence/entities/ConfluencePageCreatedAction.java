/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This enum represents the action that occurred while creating/retrieving pages from Confluence. Possible actions
 * are: base page and version page were both created, base page existed but version page was created, both base
 * page and version page existed.
 * </p>
 * <p>
 * We won't specify how each enum name is mapped to XML schema so enum names will be mapped as enum values.
 * </p>
 * <p>
 * <b>Thread Safety:</b> this class is immutable so thread-safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlRootElement(name = "pageCreatedAction")
@XmlType
@XmlEnum(String.class)
public enum ConfluencePageCreatedAction {

    /**
     * <p>
     * This field corresponds to such outcome of action: both base and version pages didn't exist so were created.
     * </p>
     */
    BASE_PAGE_AND_VERSION_CREATED,

    /**
     * <p>
     * This field corresponds to such outcome of action: base page existed, version page didn't exist so was
     * created.
     * </p>
     */
    BASE_PAGE_EXISTED_VERSION_CREATED,

    /**
     * <p>
     * This field corresponds to such outcome of action: both base and version pages existed.
     * </p>
     */
    BASE_PAGE_AND_VERSION_EXISTED
}
