/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p>
 * It is the DTO class which is used to transfer fonts data. The information can
 * be null or can be empty, therefore this check is not present in the setters.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author flexme
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fontsData", propOrder = { "name", "url" })
public class FontsData implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 5583967623706527054L;

    /**
     * Represents the fonts name.
     */
    private String name;

    /**
     * Represents the fonts URL.
     */
    private String url;

    /**
     * Empty constructor.
     */
    public FontsData() {
    }

    /**
     * Gets the fonts name.
     *
     * @return the fonts name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the fonts name.
     *
     * @param name the fonts name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the fonts url.
     *
     * @return the fonts url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the fonts url.
     *
     * @param url the fonts url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}

