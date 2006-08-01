/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.login;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.naming.Context;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.security.NoSuchUserException;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.security.admin.PrincipalMgrRemoteHome;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit tests utilities.
 * <p>
 * This class defines some common routines to help unit tests. It includes routines to load configurations,
 * to clear configurations, to create user via EJB and get private field value via reflection.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestUtil {
    /**
     * Represents the test_files directory in which all the test files reside in.
     */
    public static final String TEST_DIR = "test_files/";

    /**
     * Empty private constructor.
     */
    private TestUtil() {
        // empty
    }

    /**
     * Load all the configurations required to do testing.
     *
     * @throws Exception to JUnit.
     */
    public static void loadAllConfigurations() throws Exception {
        clearAllConfigurations();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File(TEST_DIR + "OnlineReviewLogin.xml").getAbsolutePath());
        cm.add(new File(TEST_DIR + "OnlineReviewLogin_invalid.xml").getAbsolutePath());
        cm.add("com.topcoder.naming.jndiutility",
                new File(TEST_DIR + "JNDIUtils.properties").getAbsolutePath(),
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * Clear all the configurations in ConfigManager.
     *
     * @throws Exception to JUnit.
     */
    public static void clearAllConfigurations() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
    }

    /**
     * Get field value via reflection.
     *
     * @param obj the object to retrieve value from
     * @param name the name of the field
     * @return the value of the field
     * @throws Exception to JUnit.
     */
    public static Object getFieldValue(Object obj, String name) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * Create a user with given name and password. If the user does already exist,
     * the password will be updated.
     *
     * @param name the name of the user
     * @param password the passowrd of the user
     * @return the generated id of the user.
     * @throws Exception to JUnit.
     */
    public static UserPrincipal createUser(String name, String password) throws Exception {
        // retrieve remote principal manager from configuration
        String contextName = Util.getRequiredPropertyString(TestUtil.class.getName(), "context_name");
        Context context = JNDIUtils.getContext(contextName);
        String principalBean = Util.getRequiredPropertyString(TestUtil.class.getName(), "principal_bean_name");
        PrincipalMgrRemoteHome home = (PrincipalMgrRemoteHome) context.lookup(principalBean);
        PrincipalMgrRemote mgr = home.create();

        // create or update the user
        UserPrincipal principal = null;
        try {
            principal = mgr.getUser(name);
            mgr.editPassword(principal, password, null);
        } catch (NoSuchUserException e) {
            principal = mgr.createUser("myname", "mypw", null);
        }

        return principal;
    }

    /**
     * Remove the specified user from remote principal manager.
     *
     * @param principal the principla to remove
     *
     * @throws Exception to JUnit.
     */
    public static void removeUser(UserPrincipal principal) throws Exception {
        // retrieve remote principal manager from configuration
        String contextName = Util.getRequiredPropertyString(TestUtil.class.getName(), "context_name");
        Context context = JNDIUtils.getContext(contextName);
        String principalBean = Util.getRequiredPropertyString(TestUtil.class.getName(), "principal_bean_name");
        PrincipalMgrRemoteHome home = (PrincipalMgrRemoteHome) context.lookup(principalBean);
        PrincipalMgrRemote mgr = home.create();

        mgr.removeUser(principal, null);
    }
}
