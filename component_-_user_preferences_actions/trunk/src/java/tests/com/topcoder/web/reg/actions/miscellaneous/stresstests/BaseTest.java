/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jivesoftware.base.UserManager;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ResultFilter;
import com.jivesoftware.forum.WatchManager;
import com.opensymphony.xwork2.ActionContext;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.dao.PreferenceGroupDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.MemberContactBlackList;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.BasePreferencesAction;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockUser;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * This is base class for Unit tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseTest extends StrutsSpringTestCase {

    /**
     * <p>
     * Represents submit action name.
     * </p>
     */
    protected static final String SUBMIT_ACTION = "submit";

    /**
     * <p>
     * Represents test files directory name.
     * </p>
     */
    private static final String TEST_FILES = "test_files"
        + File.separator;

    /**
     * <p>
     * Represents application context file name.
     * </p>
     */
    private static final String APPLICATION_CONTEXT_FILE_NAME = TEST_FILES + "applicationContext.xml";

    /**
     * <p>
     * Represents AuditDAO instance for testing.
     * </p>
     */
    private AuditDAO auditDao;

    /**
     * <p>
     * Represents DataInterfaceBean instance for testing.
     * </p>
     */
    private DataInterfaceBean dataInterfaceBean;

    /**
     * <p>
     * Represents ForumFactory instance for testing.
     * </p>
     */
    private ForumFactory forumFactory;

    /**
     * <p>
     * Represents MemberContactBlackListDAO instance for testing.
     * </p>
     */
    private MemberContactBlackListDAO memberContactBlackListDao;

    /**
     * <p>
     * Represents PreferenceGroupDAO instance for testing.
     * </p>
     */
    private PreferenceGroupDAO preferenceGroupDao;

    /**
     * <p>
     * Represents UserDAO instance for testing.
     * </p>
     */
    private UserDAO userDao;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        new FileSystemXmlApplicationContext(APPLICATION_CONTEXT_FILE_NAME);
        userDao = mock(UserDAO.class);
        auditDao = mock(AuditDAO.class);
        preferenceGroupDao = mock(PreferenceGroupDAO.class);
        memberContactBlackListDao = mock(MemberContactBlackListDAO.class);
        dataInterfaceBean = mock(DataInterfaceBean.class);
        forumFactory = mock(ForumFactory.class);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        userDao = null;
        auditDao = null;
        preferenceGroupDao = null;
        memberContactBlackListDao = null;
        dataInterfaceBean = null;
        forumFactory = null;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the dataInterfaceBean
     */
    protected DataInterfaceBean getDataInterfaceBean() {
        return dataInterfaceBean;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the forumFactory
     */
    protected ForumFactory getForumFactory() {
        return forumFactory;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the memberContactBlackListDao
     */
    protected MemberContactBlackListDAO getMemberContactBlackListDao() {
        return memberContactBlackListDao;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the preferenceGroupDao
     */
    protected PreferenceGroupDAO getPreferenceGroupDao() {
        return preferenceGroupDao;
    }

    /**
     * <p>
     * Sets DAOs to given BasePreferencesAction instances.
     * </p>
     * @param basePreferencesAction the BasePreferencesAction instance to set into
     */
    protected void setBasePreferencesActionDAOs(BasePreferencesAction basePreferencesAction) {
        basePreferencesAction.setUserDao(userDao);
        basePreferencesAction.setAuditDao(auditDao);
    }

    /**
     * <p>
     * Creates mock BasicAuthentication instance.
     * </p>
     * @return created mock BasicAuthentication instance
     */
    public static BasicAuthentication createAuthentication() {
        BasicAuthentication authentication = mock(BasicAuthentication.class);
        com.topcoder.shared.security.User user = new SimpleUser(1, "user", "password");
        when(authentication.getActiveUser()).thenReturn(user);
        return authentication;
    }

    /**
     * <p>
     * Creates forum User instance with predefined values.
     * </p>
     * @return created forum User instance
     * @throws Exception if any error occurs
     */
    private static com.jivesoftware.base.User createForumUser() throws Exception {
        com.jivesoftware.base.User user = new MockUser();
        user.setProperty("showRatings", "Yes");
        user.setProperty("ratingHighlightThreshold", "2");
        user.setProperty("ratingHighlightMinCount", "3");
        user.setProperty("ratingCollapseThreshold", "4");
        user.setProperty("ratingCollapseMinCount", "5");
        user.setProperty("ratingCollapseMinMessages", "6");
        user.setProperty("jiveAutoWatchNewTopics", "Yes");
        user.setProperty("jiveAutoWatchReplies", "Yes");
        user.setProperty("markWatchesRead", "Yes");
        user.setProperty("watchFrequency", "10");
        user.setProperty("jiveForumRange", "1");
        user.setProperty("jiveThreadRange", "2");
        user.setProperty("jiveMessageRange", "3");
        user.setProperty("jiveHistoryRange", "3");
        user.setProperty("jiveSearchRange", "4");
        user.setProperty("jiveThreadMode", "5");
        user.setProperty("jiveFlatMode", "Yes");
        user.setProperty("jiveDisplayMemberPhoto", "Yes");
        user.setProperty("jiveDisplayAllMemberPhotos", "Yes");
        user.setProperty("jiveShowPrevNextThreads", "Yes");
        user.setProperty("collapseRead", "Yes");
        user.setProperty("collapseReadDays", "Yes");
        user.setProperty("collapseReadPosts", "Yes");
        user.setProperty("collapseReadShowReplied", "Yes");
        return user;
    }

    /**
     * <p>
     * Creates User instance with given values.
     * </p>
     * @param id the user's id
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param handle the user's handle
     * @return created User instance with given values
     */
    public static User createUser(Long id, String firstName, String lastName, String handle) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setHandle(handle);
        return user;
    }

    /**
     * <p>
     * Prepares mock return data for given ForumFactory instance.
     * </p>
     * @param forumFactory the ForumFactory to make mock return data
     * @throws Exception if any error occurs
     */
    public static void prepareForumFactory(ForumFactory forumFactory) throws Exception {
        UserManager userManager = mock(UserManager.class);
        WatchManager watchManager = mock(WatchManager.class);
        when(forumFactory.getUserManager()).thenReturn(userManager);
        when(forumFactory.getWatchManager()).thenReturn(watchManager);
        when(userManager.getUser(anyLong())).thenReturn(createForumUser());
        when(forumFactory.getUserMessageCount(any(com.jivesoftware.base.User.class), any(ResultFilter.class))).thenReturn(
            2);
        // create forum messages
        List<ForumMessage> messages = new ArrayList<ForumMessage>();
        ForumMessage forumMessage1 = mock(ForumMessage.class);
        messages.add(forumMessage1);
        ForumMessage forumMessage2 = mock(ForumMessage.class);
        messages.add(forumMessage2);
        Iterator<ForumMessage> iteratorMessages = messages.iterator();
        when(forumFactory.getUserMessages(any(com.jivesoftware.base.User.class), any(ResultFilter.class))).thenReturn(
            iteratorMessages);
    }

    /**
     * <p>
     * Prepares mock return data for given MemberContactBlackListDAO instance.
     * </p>
     * @param memberContactBlackListDAO the MemberContactBlackListDAO to make mock return data
     */
    public static void prepareMemberContactBlackListDAO(MemberContactBlackListDAO memberContactBlackListDAO) {
        Long user1Id = 1L;
        List<User> blockedUsers1 = new ArrayList<User>();
        User user3 = createUser(3L, "First3", "Last3", "handle3");
        blockedUsers1.add(createUser(2L, "First2", "Last2", "handle2"));
        blockedUsers1.add(user3);
        List<User> previouslyBlockedUsers1 = new ArrayList<User>();
        previouslyBlockedUsers1.add(user3);
        previouslyBlockedUsers1.add(createUser(4L, "First4", "Last4", "handle4"));
        when(memberContactBlackListDAO.getBlockedUsers(user1Id)).thenReturn(blockedUsers1);
        when(memberContactBlackListDAO.getPreviouslyBlockedUsers(user1Id)).thenReturn(previouslyBlockedUsers1);
        MemberContactBlackList memberContactBlackList = new MemberContactBlackList();
        when(memberContactBlackListDAO.find(any(User.class), any(User.class))).thenReturn(memberContactBlackList);
    }

    /**
     * <p>
     * Inserts keys value into session.
     * </p>
     * @param key the session key
     * @param value the session key value
     */
    public static void putKeyValueToSession(String key, Object value) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session == null) {
            session = new HashMap<String, Object>();
        }
        session.put(key, value);
        ActionContext.getContext().setSession(session);
    }

}
