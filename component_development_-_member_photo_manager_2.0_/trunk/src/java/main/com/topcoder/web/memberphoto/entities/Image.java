/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.entities;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * <p>
 * This class represents the Image java bean. An Image can contain an image identifier and a file name. This is a
 * simple java bean (with a default no-arg constructor and for each property, a corresponding getter/setter
 * method). Any attribute in this bean is <i>optional</i> so no validation is performed here.
 * </p>
 *
 * <p>
 * This class is <code>Serializable</code>. The IoC should handle the validity if the passed values in setters.
 * </p>
 * <p>
 * Annotation : <code>Entity</code> - this annotation should be used to mark this java bean as an entity and to
 * ease its use with JPA.
 * </p>
 *
 * <p>
 * Thread safety: This class contains only mutable fields so therefore it is not thread safe.
 * </p>
 *
 * @author Mafy, cyberjag
 * @version 1.0
 */
@Entity
public class Image implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -7914326564066153433L;

    /**
     * <p>
     * This field represents the 'id' property of the Image. Represents the identifier of the image.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not assigned. It is accessed through
     * corresponding getter/setter methods. There are no restrictions at this moment for valid values. It can take
     * any value.
     * </p>
     */
    private long id;

    /**
     * <p>
     * This field represents the 'fileName' property of the Image. Represents the file name of the image.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not assigned. It is accessed through
     * corresponding getter/setter methods. There are no restrictions at this moment for valid values. It can take
     * any value.
     * </p>
     */
    private String fileName;

    /**
     * Default no-arg constructor. Constructs a new <code>{@link Image}</code> instance.
     */
    public Image() {
        // empty
    }

    /**
     * Getter for 'id' property. Please refer to the related 'id' field for more information.
     *
     * @return the value of the 'id' property. It can be any value.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for 'id' property. Please refer to the related 'id' field for more information.
     *
     * @param id
     *            the new id to be used for 'id' property. It can be any value.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for 'fileName' property. Please refer to the related 'fileName' field for more information.
     *
     * @return the value of the 'fileName' property. It can be any value.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter for 'fileName' property. Please refer to the related 'fileName' field for more information.
     *
     * @param fileName
     *            the new fileName to be used for 'fileName' property. It can be any value.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
