/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.security.GroupPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.dao.SecurityGroupDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.SecurityGroup;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserGroup;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.actions.registration.BaseRegistrationAction;

/**
 * <p>
 * This class is a factory for creating mock DAOs. Methods used only by Spring.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockFactory {

    /**
     * <p>
     * Represents ID using for testing.
     * </p>
     */
    public static final long ID = 1;

    /**
     * <p>
     * Represents servlet redirected url for testing.
     * </p>
     */
    public static final String REDIRECTED_URL = "/success.jsp";

    /**
     * <p>
     * Represents AuditDAO instance for testing.
     * </p>
     */
    private static AuditDAO auditDAO;

    /**
     * <p>
     * Represents UserDAO instance for testing.
     * </p>
     */
    private static UserDAO userDAO;

    /**
     * <p>
     * Represents PrincipalMgrRemote instance for testing.
     * </p>
     */
    private static PrincipalMgrRemote principalMgr;

    /**
     * <p>
     * Represents RegistrationTypeDAO instance for testing.
     * </p>
     */
    private static RegistrationTypeDAO registrationTypeDAO;

    /**
     * <p>
     * Represents SecurityGroupDAO instance for testing.
     * </p>
     */
    private static SecurityGroupDAO securityGroupDAO;

    /**
     * <p>
     * Represents user for testing.
     * </p>
     */
    private static User user;
    // Initialize mock DAOs.
    static {
        setUserDAO(mock(UserDAO.class));
        setAuditDAO(mock(AuditDAO.class));
        setSecurityGroupDAO(mock(SecurityGroupDAO.class));
        setRegistrationTypeDAO(mock(RegistrationTypeDAO.class));
        setPrincipalMgr(mock(PrincipalMgrRemote.class));
        try {
            initDAOs();
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Prevents from instantiating.
     * </p>
     */
    private MockFactory() {
    }

    /**
     * <p>
     * Initializes DAO.
     * </p>
     * @throws Exception if any error occurs
     */
    public static void initDAOs() throws Exception {
        long id = 1L;
        user = createUser(id, "first", "last", "handle");
        when(userDAO.find(id)).thenReturn(user);
        initPrincipalMgr();
        initSecurityGroups();
        initRegistrationTypeDAO();
        Coder coder = new Coder();
        user.setCoder(coder);
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(2);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
    }

    /**
     * <p>
     * Initializes registration type DAO.
     * </p>
     */
    private static void initRegistrationTypeDAO() {
        RegistrationType registrationType = new RegistrationType(1);
        when(registrationTypeDAO.getCompetitionType()).thenReturn(registrationType);
        when(registrationTypeDAO.getSoftwareType()).thenReturn(registrationType);
        when(registrationTypeDAO.getHighSchoolType()).thenReturn(registrationType);
        when(registrationTypeDAO.getCorporateType()).thenReturn(registrationType);
        when(registrationTypeDAO.getMinimalType()).thenReturn(registrationType);
        when(registrationTypeDAO.getStudioType()).thenReturn(registrationType);
        when(registrationTypeDAO.getTeacherType()).thenReturn(registrationType);
        when(registrationTypeDAO.getOpenAIMType()).thenReturn(registrationType);
        when(registrationTypeDAO.getTruveoType()).thenReturn(registrationType);
        when(registrationTypeDAO.getCompetitionType()).thenReturn(registrationType);
        when(registrationTypeDAO.getCompetitionType()).thenReturn(registrationType);
    }

    /**
     * <p>
     * Initializes user's security groups.
     * </p>
     */
    private static void initSecurityGroups() {
        Set<UserGroup> securityGroups = new HashSet<UserGroup>();
        user.setSecurityGroups(securityGroups);
        UserGroup userGroup = new UserGroup();
        SecurityGroup securityGroup = new SecurityGroup();
        Set<RegistrationType> registrationTypes = new HashSet<RegistrationType>();
        RegistrationType registrationType1 = new RegistrationType(1);
        RegistrationType registrationType2 = new RegistrationType(2);
        registrationTypes.add(registrationType1);
        registrationTypes.add(registrationType2);
        BaseStressTest.setFieldValue("registrationTypes", registrationTypes, securityGroup, SecurityGroup.class);
        userGroup.setSecurityGroup(securityGroup);
        userGroup.setId(1L);
        userGroup.setSecurityStatusId(SecurityGroup.ACTIVE);
        securityGroups.add(userGroup);
    }

    /**
     * <p>
     * Creates User instance with given values.
     * </p>
     * @param id the user's ID
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param handle the user's handle
     * @return created User instance with given values
     */
    public static User createUser(Long id, String firstName, String lastName, String handle) {
        User userEntity = new User();
        userEntity.setId(id);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setHandle(handle);
        Email email = new Email();
        email.setAddress("primary_email@test.com");
        email.setPrimary(true);
        email.setId(1L);
        userEntity.addEmailAddress(email);
        return userEntity;
    }

    /**
     * <p>
     * Retrieves UserDAO instance for this component.
     * </p>
     * @return UserDAO instance for this component
     */
    public static UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * <p>
     * Retrieves AuditDAO instance for this component.
     * </p>
     * @return AuditDAO instance for this component
     */
    public static AuditDAO getAuditDAO() {
        return auditDAO;
    }

    /**
     * <p>
     * Resets mock DAOs.
     * </p>
     */
    public static void resetDAOs() {
        reset(getAuditDAO());
        reset(getUserDAO());
        reset(getPrincipalMgr());
        reset(getSecurityGroupDAO());
        reset(getRegistrationTypeDAO());
    }

    /**
     * <p>
     * Initializes principalMgr and set users secret question.
     * </p>
     * @throws Exception if any error occurs
     */
    private static void initPrincipalMgr() throws Exception {
        // init PrincipalMgrRemote
        UserPrincipal principal = mock(UserPrincipal.class);
        when(principalMgr.createUser(anyLong(), anyString(), anyString(), any(TCSubject.class), anyString()))
                .thenReturn(principal);
        Collection<GroupPrincipal> groups = new ArrayList<GroupPrincipal>();
        groups.add(new GroupPrincipal("Anonymous", 1));
        groups.add(new GroupPrincipal("Users", 2));
        groups.add(new GroupPrincipal("Admins", 3));
        when(principalMgr.getGroups(any(TCSubject.class), anyString())).thenReturn(groups);
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the user field instance value
     */
    public static User getUser() {
        return user;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param user the user to set
     */
    public static void setUser(User user) {
        MockFactory.user = user;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the id field instance value
     */
    public static long getId() {
        return ID;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the redirectedUrl field instance value
     */
    public static String getRedirectedUrl() {
        return REDIRECTED_URL;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param auditDAO the auditDAO to set
     */
    public static void setAuditDAO(AuditDAO auditDAO) {
        MockFactory.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param userDAO the userDAO to set
     */
    public static void setUserDAO(UserDAO userDAO) {
        MockFactory.userDAO = userDAO;
    }

    /**
     * <p>
     * Creates com.topcoder.shared.security.User instance with given values.
     * </p>
     * @param id the user id
     * @param username the user name
     * @param password the user password
     * @return created com.topcoder.shared.security.User instance with given values
     */
    public static com.topcoder.shared.security.User createSecurityUser(Long id, String username, String password) {
        return new SimpleUser(id, username, password);
    }

    /**
     * <p>
     * Creates BasicAuthentication instance with given values.
     * </p>
     * @param securityUser the user to set
     * @return created BasicAuthentication instance with given values
     */
    public static BasicAuthentication createWebAuthentication(com.topcoder.shared.security.User securityUser) {
        BasicAuthentication authentication = mock(BasicAuthentication.class);
        when(authentication.getActiveUser()).thenReturn(securityUser);
        return authentication;
    }

    /**
     * <p>
     * Creates HttpServletRequest mock instance.
     * </p>
     * @return created HttpServletRequest mock instance
     */
    public static HttpServletRequest getServletRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        return request;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the principalMgr field instance value
     */
    public static PrincipalMgrRemote getPrincipalMgr() {
        return principalMgr;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param principalMgr the principalMgr to set
     */
    public static void setPrincipalMgr(PrincipalMgrRemote principalMgr) {
        MockFactory.principalMgr = principalMgr;
    }

    /**
     * <p>
     * Creates WebAuthentication user in session of given action.
     * </p>
     * @param action the action to create WebAuthentication user in
     */
    public static void createUserInSession(BaseRegistrationAction action) {
        Map<String, Object> session = action.getSession();
        if (session == null) {
            session = new HashMap<String, Object>();
        }
        session.put(action.getAuthenticationSessionKey(),
                createWebAuthentication(createSecurityUser(getUser().getId(), "user", "password")));
        action.setSession(session);
    }

    /**
     * <p>
     * Creates session with WebAuthentication in it.
     * </p>
     * @return created session with WebAuthentication in it
     */
    public static Map<String, Object> getSession() {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationSessionKey",
                createWebAuthentication(createSecurityUser(getUser().getId(), "handle", "password")));
        SessionInfo sessionInfo = mock(SessionInfo.class);
        session.put("sessionInfoSessionKey", sessionInfo);
        when(sessionInfo.getAbsoluteServletPath()).thenReturn("http://topcoder.com/tc");
        return session;
    }

    /**
     * <p>
     * Retrieves user's roles.
     * </p>
     * @return retrieved user's roles
     */
    public static Set<RegistrationType> getUserRoles() {
        Set<RegistrationType> registrationTypes = new HashSet<RegistrationType>();
        registrationTypes.add(new RegistrationType(1));
        registrationTypes.add(new RegistrationType(2));
        return registrationTypes;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the registrationTypeDAO field instance value
     */
    public static RegistrationTypeDAO getRegistrationTypeDAO() {
        return registrationTypeDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param registrationTypeDAO the registrationTypeDAO to set
     */
    public static void setRegistrationTypeDAO(RegistrationTypeDAO registrationTypeDAO) {
        MockFactory.registrationTypeDAO = registrationTypeDAO;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the securityGroupDAO field instance value
     */
    public static SecurityGroupDAO getSecurityGroupDAO() {
        return securityGroupDAO;
    }

    /**
     * <p>
     * Sets the namesake field instance.
     * </p>
     * @param securityGroupDAO the securityGroupDAO to set
     */
    public static void setSecurityGroupDAO(SecurityGroupDAO securityGroupDAO) {
        MockFactory.securityGroupDAO = securityGroupDAO;
    }
}
