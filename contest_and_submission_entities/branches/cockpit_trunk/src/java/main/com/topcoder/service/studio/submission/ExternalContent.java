/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.Set;

/**
 * This class represents the external content (e.g. font, stock-art).
 *
 * Mutable and not thread-safe.
 *
 * @author Standlove, flexme
 * @version 1.0
 */
public class ExternalContent implements Serializable {
    /**
     * Represents the serial version unique id. 
     */
    private static final long serialVersionUID = 4786177612337472191L;

    /**
     * Represents the external content id. Can be any value. It has getter and setter.
     */
    private long id;

    /**
     * Represents the submission declaration. Can be any value. It has getter and setter.
     */
    private SubmissionDeclaration declaration;

    /**
     * Represents the external content properties. Can be any value. It has getter and setter.
     */
    private Set<ExternalContentProperty> properties;

    /**
     * Represents the content type. Can be any value. It has getter and setter.
     */
    private ExternalContentType contentType;

    /**
     * Represents the display position. Can be any value. It has getter and setter.
     */
    private int displayPosition;

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
     * Returns properties.
     *
     * @return properties
     */
    public Set<ExternalContentProperty> getProperties() {
        return properties;
    }

    /**
     * Sets properties.
     *
     * @param properties value to set
     */
    public void setProperties(Set<ExternalContentProperty> properties) {
        this.properties = properties;
    }

    /**
     * Returns content type.
     *
     * @return content type
     */
    public ExternalContentType getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType value to set
     */
    public void setContentType(ExternalContentType contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns display position.
     *
     * @return display position
     */
    public int getDisplayPosition() {
        return displayPosition;
    }

    /**
     * Sets display position.
     *
     * @param displayPosition value to set
     */
    public void setDisplayPosition(int displayPosition) {
        this.displayPosition = displayPosition;
    }

    /**
     * Returns declaration.
     *
     * @return declaration
     */
    public SubmissionDeclaration getDeclaration() {
        return declaration;
    }

    /**
     * Sets declaration.
     *
     * @param declaration value to set
     */
    public void setDeclaration(SubmissionDeclaration declaration) {
        this.declaration = declaration;
    }
}

