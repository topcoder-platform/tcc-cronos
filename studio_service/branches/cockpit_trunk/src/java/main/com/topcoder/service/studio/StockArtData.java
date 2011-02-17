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
 * It is the DTO class which is used to transfer stock art data. The information can
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
@XmlType(name = "stockArtData", propOrder = { "name", "url", "fileNumber" })
public class StockArtData implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 5583967623706532054L;

    /**
     * Represents the stock art name.
     */
    private String name;

    /**
     * Represents the stock art URL.
     */
    private String url;

    /**
     * Represents the file number.
     */
    private String fileNumber;

    /**
     * Empty constructor.
     */
    public StockArtData() {
    }

    /**
     * Gets the stock art name.
     *
     * @return the stock art name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the stock art name.
     *
     * @param name the stock art name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the stock art url.
     *
     * @return the stock art url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the stock art url.
     *
     * @param url the stock art url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the stock art file number.
     *
     * @return the stock art file number
     */
    public String getFileNumber() {
        return fileNumber;
    }

    /**
     * Sets the stock art file number.
     *
     * @param url the stock art file number
     */
    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

}


