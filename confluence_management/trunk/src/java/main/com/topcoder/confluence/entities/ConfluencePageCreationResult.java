/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.topcoder.confluence.Helper;

/**
 * <p>
 * This class stores the info about outcome of creation of the page, and the page itself.
 * </p>
 * <p>
 * <b>Thread Safety:</b> this class is immutable so thread-safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"actionTaken", "page" })
@XmlRootElement(name = "pageCreationResult")
public class ConfluencePageCreationResult {

    /**
     * <p>
     * this field stores instance of ConfluencePageCreatedAction and shows the outcome of creating action. it is
     * initialized in constructor and never changes after that.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Accessed through getter.
     * </p>
     */
    private final ConfluencePageCreatedAction actionTaken;

    /**
     * <p>
     * This field represents the Page object that stores all needed info about Confluence page. It is initialized
     * in the constructor and never changed after that.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Accessed through getter.
     * </p>
     */
    private final Page page;

    /**
     * <p>
     * This is the constructor which sets corresponding fields.
     * </p>
     *
     * @param page
     *            the Page object that was created/updated/not affected at all. cannot be null
     * @param actionTaken
     *            the outcome of the page creation. cannot be null
     * @throws IllegalArgumentException
     *             if page is null, if actionTaken is null
     */
    public ConfluencePageCreationResult(Page page, ConfluencePageCreatedAction actionTaken) {
        Helper.checkNull(page, "page");
        Helper.checkNull(actionTaken, "actionTaken");
        this.page = page;
        this.actionTaken = actionTaken;
    }

    /**
     * <p>
     * Simple getter for the corresponding field.
     * </p>
     *
     * @return the Page object
     */
    public Page getPage() {
        return page;
    }

    /**
     * <p>
     * Returns the base page url of the aggregated page.
     * </p>
     *
     * @return the base page url of the Page
     */
    public String getBasePageUrl() {
        return page.getBasePageUrl();
    }

    /**
     * <p>
     * Returns the version page url of the aggregated page.
     * </p>
     *
     * @return the version page url of the Page
     */
    public String getVersionUrl() {
        return page.getVersionUrl();
    }

    /**
     * <p>
     * Simple getter for the corresponding field.
     * </p>
     *
     * @return the action taken on aggregated page
     */
    public ConfluencePageCreatedAction getActionTaken() {
        return actionTaken;
    }

}
