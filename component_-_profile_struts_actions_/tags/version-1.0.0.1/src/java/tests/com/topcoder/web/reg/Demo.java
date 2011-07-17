/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.reg.actions.profile.SaveNameAndContactAction;
import com.topcoder.web.reg.actions.profile.ViewNameAndContactAction;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class shows API usage of this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseUnitTest {

    /**
     * <p>
     * Represents ViewNameAndContactAction instance for testing.
     * </p>
     */
    private ViewNameAndContactAction viewAction;

    /**
     * <p>
     * Represents ActionProxy for edit action instance for testing.
     * </p>
     */
    private ActionProxy viewActionProxy;

    /**
     * <p>
     * Represents ActionProxy for save action instance for testing.
     * </p>
     */
    private ActionProxy saveActionProxy;

    /**
     * <p>
     * Represents SaveNameAndContactAction instance for testing.
     * </p>
     */
    private SaveNameAndContactAction saveAction;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        viewActionProxy = getActionProxy("/editNameAndContactAction");
        saveActionProxy = getActionProxy("/saveNameAndContactAction");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        MockFactory.resetDAOs();
        viewAction = null;
        saveAction = null;
        viewActionProxy = null;
        saveActionProxy = null;
    }

    /**
     * <p>
     * Shows basic API usage of this component.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDemo() throws Exception {
        // In this component we have 3 types of actions: view, edit and save
        // view and edit have are concrete implementations of BaseDisplayProfileAction, so there usage is similar
        // execute view action proxy, logged in user will be retrieved from database and viewAction.displayedUser
        // values will
        // be changed to retrieved values. This action.displayedUser will be passed to JSP
        viewAction = (ViewNameAndContactAction) viewActionProxy.getAction();
        viewAction.setDisplayedUser(MockFactory.createUser(1L, "first", "last", "handle"));
        System.out.println(viewActionProxy.execute());
        // Result should be SUCCESS
        // execute save action proxy, logged in user will be retrieved from database and his values will be changed to
        // saveAction.savedUser, after that user will be saved to database.
        saveAction = (SaveNameAndContactAction) saveActionProxy.getAction();
        saveAction.setSavedUser(MockFactory.getUser());
        System.out.println(saveActionProxy.execute());
        // Result should be SUCCESS
        System.out.println(((ActionSupport) saveActionProxy.getAction()).getFieldErrors());
        // There should be no field errors
    }
}
