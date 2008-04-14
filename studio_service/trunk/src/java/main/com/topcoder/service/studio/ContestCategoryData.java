/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p> It is the DTO class which is used to transfer contest category data. The information can be null or can be empty,
 * therefore this check is not present in the setters. It's the related to the equivalent ContestCategory entity.</p>
 *
 * <p> This class is not thread safe because it's highly mutable</p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestCategoryData",
        propOrder = {"contestCategoryId", "contestName", "contestDescription", "contestCategory"})
public class ContestCategoryData implements Serializable {
    /**
     * <p> Represents the contest Category Id</p>
     */
    private long contestCategoryId = -1;

    /**
     * <p> Represents the contest Name</p>
     */
    private String contestName;

    /**
     * <p> Represents the contest Description</p>
     */
    private String contestDescription;

    /**
     * <p> Represents the contest Category</p>
     */
    private String contestCategory;

    /**
     * <p> This is the default constructor. It does nothing.</p>
     */
    public ContestCategoryData() {
    }

    /**
     * <p> Return the contestCategoryId</p>
     *
     * @return the contestCategoryId
     */
    public long getContestCategoryId() {
        return contestCategoryId;
    }

    /**
     * <p> Set the contestCategoryId</p>
     *
     * @param contestCategoryId the contestCategoryId to set
     */
    public void setContestCategoryId(long contestCategoryId) {
        this.contestCategoryId = contestCategoryId;
    }

    /**
     * <p> Return the contestName</p>
     *
     * @return the contestName
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * <p> Set the contestName</p>
     *
     * @param contestName the contestName to set
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p> Return the contestDescription</p>
     *
     * @return the contestDescription
     */
    public String getContestDescription() {
        return contestDescription;
    }

    /**
     * <p> Set the contestDescription</p>
     *
     * @param contestDescription the contestDescription to set
     */
    public void setContestDescription(String contestDescription) {
        this.contestDescription = contestDescription;
    }

    /**
     * <p> Return the contestCategory</p>
     *
     * @return the contestCategory
     */
    public String getContestCategory() {
        return contestCategory;
    }

    /**
     * <p> Set the contestCategory</p>
     *
     * @param contestCategory the contestCategory to set
     */
    public void setContestCategory(String contestCategory) {
        this.contestCategory = contestCategory;
    }
}