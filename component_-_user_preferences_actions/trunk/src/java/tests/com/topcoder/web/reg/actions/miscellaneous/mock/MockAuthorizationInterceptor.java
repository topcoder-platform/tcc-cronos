/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jivesoftware.forum.ForumFactory;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.BasePreferencesAction;
import com.topcoder.web.reg.actions.miscellaneous.BaseUnitTest;
import com.topcoder.web.reg.actions.miscellaneous.BlackListAction;
import com.topcoder.web.reg.actions.miscellaneous.ForumRatingPreferencesAction;
import com.topcoder.web.reg.actions.miscellaneous.PaymentPreferencesAction;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * This is mock authorization interceptor that makes login and set required mock DAOs.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockAuthorizationInterceptor extends AbstractInterceptor {

    /**
     * <p>
     * Intercepts action invocation and provides needed mock managers before invocation.
     * </p>
     * @param actionInvocation the action invocation
     * @return actionInvocation.invoke()
     * @throws Exception if any error occurs
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        UserDAO userDao = mock(UserDAO.class);
        AuditDAO auditDao = mock(AuditDAO.class);
        BasicAuthentication authentication = BaseUnitTest.createAuthentication();
        BaseUnitTest.putKeyValueToSession("sessionKey", authentication);
        BasePreferencesAction action = (BasePreferencesAction) actionInvocation.getAction();
        // prepare logged in User
        User user = BaseUnitTest.createUser(1L, "First", "Last", "handle", true);
        when(userDao.find(1L)).thenReturn(user);
        action.setUserDao(userDao);
        action.setAuditDao(auditDao);
        // clear field errors
        action.clearFieldErrors();
        // prepare BlackListAction action
        if (action instanceof BlackListAction) {
            BaseUnitTest.setMemberContactBlackList((BlackListAction) action);
        }
        // prepare PaymentPreferencesAction
        if (action instanceof PaymentPreferencesAction) {
            DataInterfaceBean dataInterfaceBean = mock(DataInterfaceBean.class);
            BaseUnitTest.prepareDataInterfaceBean(dataInterfaceBean);
            ((PaymentPreferencesAction) action).setDataInterfaceBean(dataInterfaceBean);
        }
        // prepare ForumRatingPreferences
        if (action instanceof ForumRatingPreferencesAction) {
            ForumFactory forumFactory = mock(ForumFactory.class);
            BaseUnitTest.prepareForumFactory(forumFactory);
            ((ForumRatingPreferencesAction) action).setForumFactory(forumFactory);
        }
        String result = actionInvocation.invoke();
        return result;
    }
}
