/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.jivesoftware.forum.ForumFactory;
import com.topcoder.commons.utils.ValidationUtility;

/**
 * <p>
 * This is the base class for forum actions.
 * </p>
 * <p>
 * It aggregates the forum factory. All its instance variables have public setters/getters and will be injected.
 * </p>
 * <p>
 * Thread safety: This class is mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseForumAction extends BasePreferencesAction {

    /**
     * <p>
     * Represents the forum factory which will be used to get different forum managers.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter.
     * </p>
     */
    private ForumFactory forumFactory;

    /**
     * Protected constructor.
     */
    protected BaseForumAction() {
        super();
    }

    /**
     * <p>
     * Checks if fields properly injected.
     * </p>
     * @throws Exception if any field has invalid value after injection
     */
    public void prepare() throws Exception {
        super.prepare();
        ValidationUtility
                .checkNotNull(forumFactory, "forumFactory", UserPreferencesActionConfigurationException.class);
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the forumFactory
     */
    public ForumFactory getForumFactory() {
        return forumFactory;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param forumFactory the forumFactory to set
     */
    public void setForumFactory(ForumFactory forumFactory) {
        this.forumFactory = forumFactory;
    }
}
