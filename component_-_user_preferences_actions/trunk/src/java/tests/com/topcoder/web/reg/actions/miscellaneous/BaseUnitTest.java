/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jivesoftware.base.UserManager;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ResultFilter;
import com.jivesoftware.forum.WatchManager;
import com.opensymphony.xwork2.ActionContext;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.dao.PreferenceGroupDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.MemberContactBlackList;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.Preference;
import com.topcoder.web.common.model.PreferenceGroup;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockUser;
import com.topcoder.web.tc.controller.legacy.pacts.bean.DataInterfaceBean;

/**
 * <p>
 * This is base class for Unit tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseUnitTest extends StrutsSpringTestCase {

    /**
     * <p>
     * Represents basic authentication session key.
     * </p>
     */
    protected static final String AUTH_SESSION_KEY = "sessionKey";

    /**
     * <p>
     * Represents discard action name.
     * </p>
     */
    protected static final String DISCARD_ACTION = "discard";

    /**
     * <p>
     * Represents email body template file name.
     * </p>
     */
    protected static final String EMAIL_BODY_TEMPLATE_NAME = "template.txt";

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
    private static final String TEST_FILES = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator;

    /**
     * <p>
     * Represents application context file name.
     * </p>
     */
    private static final String APPLICATION_CONTEXT_FILE_NAME = TEST_FILES + "applicationContext.xml";

    /**
     * <p>
     * Represents base preferences action bean name.
     * </p>
     */
    private static final String BASE_PREFERENCES_ACTION_BEAN_NAME = "mockBasePreferencesAction";

    /**
     * <p>
     * Represents document generator bean name.
     * </p>
     */
    private static final String DOCUMENT_GENERATOR_BEAN_NAME = "documentGenerator";

    /**
     * <p>
     * Represents logger bean name.
     * </p>
     */
    private static final String LOGGER_BEAN_NAME = "logger";

    /**
     * <p>
     * Represents AuditDAO instance for testing.
     * </p>
     */
    private AuditDAO auditDao;

    /**
     * <p>
     * Represents ApplicationContext instance for testing.
     * </p>
     */
    private ApplicationContext context;

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
    public void setUp() throws Exception {
        super.setUp();
        context = new FileSystemXmlApplicationContext(APPLICATION_CONTEXT_FILE_NAME);
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
    public void tearDown() throws Exception {
        super.tearDown();
        context = null;
        userDao = null;
        auditDao = null;
        preferenceGroupDao = null;
        memberContactBlackListDao = null;
        dataInterfaceBean = null;
        forumFactory = null;
    }

    /**
     * <p>
     * Retrieves auditDAO from context.
     * </p>
     * @return AuditDAO instance from context
     */
    protected AuditDAO getAuditDAO() {
        return auditDao;
    }

    /**
     * <p>
     * Creates BasePreferencesAction instance with valid parameters.
     * </p>
     * @return created BasePreferencesAction instance with valid parameters
     */
    protected BasePreferencesAction getBasePreferencesAction() {
        BasePreferencesAction result = getBean(BASE_PREFERENCES_ACTION_BEAN_NAME);
        setBasePreferencesActionDAOs(result);
        return result;
    }

    /**
     * <p>
     * Retrieves bean for given name from context.
     * </p>
     * @param <T> the type of bean
     * @param beanName the bean name
     * @return retrieved bean for given name from context
     */
    protected < T > T getBean(String beanName) {
        return (T) context.getBean(beanName);
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
     * Retrieves documentGenerator from context.
     * </p>
     * @return DocumentGenerator instance from context
     */
    protected DocumentGenerator getDocumentGenerator() {
        return (DocumentGenerator) context.getBean(DOCUMENT_GENERATOR_BEAN_NAME);
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
     * Retrieves logger from context.
     * </p>
     * @return Log instance from context
     */
    protected Log getLog() {
        return (Log) context.getBean(LOGGER_BEAN_NAME);
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
     * Retrieves userDAO from context.
     * </p>
     * @return UserDAO instance from context
     */
    protected UserDAO getUserDAO() {
        return userDao;
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
    public static com.jivesoftware.base.User createForumUser() throws Exception {
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
     * @param primaryEmailExist the flag that indicates that user should have or not primary email
     * @return created User instance with given values
     */
    public static User createUser(Long id, String firstName, String lastName, String handle,
            boolean primaryEmailExist) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setHandle(handle);
        if (primaryEmailExist) {
            Email email = new Email();
            email.setAddress("primary_email@test.com");
            email.setPrimary(true);
            email.setId(1L);
            user.addEmailAddress(email);
        }
        return user;
    }

    /**
     * <p>
     * Prepares mock return data for given DataInterfaceBean instance.
     * </p>
     * @param dataInterfaceBean the DataInterfaceBean to make mock return data
     * @throws Exception if any error occurs
     */
    public static void prepareDataInterfaceBean(DataInterfaceBean dataInterfaceBean) throws Exception {
        when(dataInterfaceBean.getUserAccrualThreshold(anyLong())).thenReturn(1.4);
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
        when(forumFactory.getUserMessageCount(any(com.jivesoftware.base.User.class), any(ResultFilter.class)))
                .thenReturn(2);
        // create forum messages
        List < ForumMessage > messages = new ArrayList < ForumMessage >();
        ForumMessage forumMessage1 = mock(ForumMessage.class);
        messages.add(forumMessage1);
        ForumMessage forumMessage2 = mock(ForumMessage.class);
        messages.add(forumMessage2);
        Iterator < ForumMessage > iteratorMessages = messages.iterator();
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
        List < User > blockedUsers1 = new ArrayList < User >();
        User user3 = createUser(3L, "First3", "Last3", "handle3", true);
        blockedUsers1.add(createUser(2L, "First2", "Last2", "handle2", true));
        blockedUsers1.add(user3);
        List < User > previouslyBlockedUsers1 = new ArrayList < User >();
        previouslyBlockedUsers1.add(user3);
        previouslyBlockedUsers1.add(createUser(4L, "First4", "Last4", "handle4", true));
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
        Map < String, Object > session = ActionContext.getContext().getSession();
        if (session == null) {
            session = new HashMap < String, Object >();
        }
        session.put(key, value);
        ActionContext.getContext().setSession(session);
    }

    /**
     * <p>
     * Sets object field value via reflection.
     * </p>
     * @param fieldName the field name
     * @param fieldValue the field value
     * @param obj the object to set
     * @param clazz the object class
     */
    public static void setFieldValue(String fieldName, Object fieldValue, Object obj, Class < ? > clazz) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (SecurityException e) {
            // never happened
        } catch (NoSuchFieldException e) {
            // never happened
        } catch (IllegalArgumentException e) {
            // never happened
        } catch (IllegalAccessException e) {
            // never happened
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Creates MemberContactBlackList and set it to given action.
     * </p>
     * @param action the BlakcList action to set field
     */
    public static void setMemberContactBlackList(BlackListAction action) {
        MemberContactBlackListDAO memberContactBlackListDao = mock(MemberContactBlackListDAO.class);
        prepareMemberContactBlackListDAO(memberContactBlackListDao);
        action.setMemberContactBlackListDao(memberContactBlackListDao);
    }

    /**
     * <p>
     * Creates and set notifications for given user.
     * </p>
     * @param user the user to set notifications
     */
    public static void setUserNotifications(User user) {
        Notification notification1 = new Notification();
        notification1.setId(1);
        notification1.setName("Notification1");
        notification1.setStatus("Yes");
        notification1.setSort(1);
        // prepare notifications
        user.addNotification(notification1);
        Notification notification2 = new Notification();
        notification2.setId(2);
        notification2.setName("Notification2");
        notification2.setStatus("No");
        notification2.setSort(2);
        user.addNotification(notification2);
    }

    /**
     * <p>
     * Sets up user's preferences for given action.
     * </p>
     * @param user the user to change
     * @param action the action to be changed
     */
    public static void setUserPreferences(User user, ConfigurePrivacyPreferencesAction action) {
        int id = 1;
        // setup preferences for preferenceGroupDAO
        Set < Preference > preferences = new HashSet < Preference >();
        Preference preference1 = new Preference();
        preference1.setId(id);
        preferences.add(preference1);
        Preference preference2 = new Preference();
        preference2.setId(id + 1);
        preferences.add(preference2);
        // setup user
        UserDAO userDAO = action.getUserDao();
        // prepare user's registrationTypes
        Set < UserGroup > securityGroups = new HashSet < UserGroup >();
        UserGroup userGroup = new UserGroup();
        userGroup.setId(new Long(id));
        userGroup.setSecurityStatusId(SecurityGroup.ACTIVE);
        // setup registration types in security group
        Set < RegistrationType > registrationTypes = new HashSet < RegistrationType >();
        SecurityGroup securityGroup = new SecurityGroup();
        RegistrationType registrationType = new RegistrationType(id);
        // set preferences via reflection
        setFieldValue("preferences", preferences, registrationType, RegistrationType.class);
        registrationTypes.add(registrationType);
        setFieldValue("registrationTypes", registrationTypes, securityGroup, SecurityGroup.class);
        userGroup.setSecurityGroup(securityGroup);
        securityGroups.add(userGroup);
        user.setSecurityGroups(securityGroups);
        // add user's user preferences
        createUserPreference(user, preference1, "UserPreference1");
        createUserPreference(user, preference2, "UserPreference2");
        when(userDAO.find(new Long(id))).thenReturn(user);
        // setup mock preferenceGroupDAO
        PreferenceGroupDAO preferenceGroupDAO = action.getPreferenceGroupDao();
        PreferenceGroup preferenceGroup = new PreferenceGroup();
        preferenceGroup.setId(id);
        preferenceGroup.setPreferences(preferences);
        when(preferenceGroupDAO.find(anyInt())).thenReturn(preferenceGroup);
    }

    /**
     * <p>
     * Creates UserPreference instance with given values.
     * </p>
     * @param user the user
     * @param preference the preference
     * @param value the preference value
     */
    private static void createUserPreference(User user, Preference preference, String value) {
        UserPreference userPreference1 = new UserPreference();
        userPreference1.setId(new UserPreference.Identifier(user, preference));
        userPreference1.setValue(value);
        user.addUserPreference(userPreference1);
    }
}
