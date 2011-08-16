/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;

/**
 * This class represents the external content type (e.g. Font, Stock Art).
 *
 * Mutable and not thread-safe.
 *
 * @author Standlove, flexme
 * @version 1.0
 */
public class ExternalContentType implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -2429705750755203710L;

    /**
     * Represents the external content type id. Can be any value. It has getter and setter.
     */
    private long id;

    /**
     * Represents the external content type name. Can be any value. It has getter and setter.
     */
    private String name;

    /**
     * Returns id.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name value to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
