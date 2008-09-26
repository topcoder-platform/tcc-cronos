/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb.bean;

import com.jivesoftware.forum.mock.MockForumFactory;
import com.jivesoftware.forum.mock.MockUser;
import com.jivesoftware.forum.mock.MockWatchManager;

import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.ServiceConfigurationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Field;


/**
 * <p>
 * Unit tests for <code>JiveForumServiceBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiveForumServiceBeanUnitTest extends TestCase {
    /**
     * <p>The <code>JiveForumServiceBean</code> instance for testing.</p>
     */
    private JiveForumServiceBean bean;

    /**
     * <p>The mock <code>SessionContext</code> for simulating the ejb environment.</p>
     */
    private MockSessionContext context;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JiveForumServiceBeanUnitTest.class);
    }

    /**
     * <p>
     * Sets up the environment.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bean = new JiveForumServiceBean();

        context = new MockSessionContext();
        context.setAdminUserId("1");
        context.setApplicationCategoryId("1");
        context.setAssemblyCompetitionCategoryId("1");
        context.setComponentCategoryId("1");
        context.setTestingCompetitionCategoryId("1");

        Field field = bean.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(bean, context);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>JiveForumServiceBean()</code>.
     * As it's trivial method, we just test whether the bean can be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Unable to create JiveForumServiceBean instance.", bean);
    }

    /**
     * <p>
     * Accuracy test for <code>watch(long, long, EntityType)</code>.
     * </p>
     *
     * <p>
     * As it's trivial method, we just test it can be processed successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testWatch() throws Exception {
        bean.initialize();
        bean.watch(1, 1, EntityType.FORUM);

        assertTrue("The forum should be watched.",
            MockWatchManager.isForumWatched());

        MockWatchManager.clearForumWatched();
    }

    /**
     * <p>
     * Accuracy test for <code>isWatched(long, long, EntitytType)</code>.
     * </p>
     *
     * <p>
     * As it's trivial method, we just test whether it can be proccessed successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testIsWatched() throws Exception {
        bean.initialize();

        assertFalse("The forum should not be watched at first.",
            bean.isWatched(1, 1, EntityType.FORUM));

        bean.watch(1, 1, EntityType.FORUM);
        assertTrue("The forum should be watched now.",
            bean.isWatched(1, 1, EntityType.FORUM));

        //clear the watch info
        MockWatchManager.clearForumWatched();
    }

    /**
     * <p>
     * Accuracy test for <code>setUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * As it's trivial method, we just test whether it can be processed successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetUserRole() throws Exception {
        bean.initialize();

        bean.setUserRole(1, 1, UserRole.MODERATOR);

        //Verify
        MockForumFactory forumFactory = MockForumFactory.getInstance();
        MockUser user = (MockUser) forumFactory.getUserManager().getUser(1);

        assertEquals("The user belong to the moderator group.", 1,
            user.getGroup().getID());

        user.setGroup(null);
    }

    /**
     * <p>
     * Accuracy test for <code>getUserRole(long, long, UserRole)</code>.
     * </p>
     *
     * <p>
     * As it's trivial method, we just test whether it can be processed successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRole() throws Exception {
        bean.initialize();

        bean.setUserRole(1, 1, UserRole.CONTRIBUTOR);

        assertEquals("The user should be contributor.", UserRole.CONTRIBUTOR,
            bean.getUserRole(1, 1));

        //Clear
        MockForumFactory forumFactory = MockForumFactory.getInstance();
        MockUser user = (MockUser) forumFactory.getUserManager().getUser(1);

        user.setGroup(null);
    }

    /**
     * <p>
     * Accuracy test for <code>createCategory(CategoryConfiguration)</code>.
     * </p>
     *
     * <p>
     * As it's trivial method, we just test whether it can be processed successfully.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateCategory() throws Exception {
        bean.initialize();

        CategoryConfiguration categoryConfiguration = new CategoryConfiguration();
        categoryConfiguration.setName("category");
        categoryConfiguration.setDescription("a new category");
        categoryConfiguration.setVersionText("1.0");
        categoryConfiguration.setComponentId(1);
        categoryConfiguration.setVersionId(1);
        categoryConfiguration.setRootCategoryId(10);
        categoryConfiguration.setPublic(false);
        categoryConfiguration.setModeratorUserId(1);
        categoryConfiguration.setTemplateCategoryId(1);

        bean.createCategory(categoryConfiguration);
    }

    /**
     * <p>
     * Accuracy test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * The test case will use a mock <code>SessionContext</code> to simulate the ejb environment.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testInitialize() throws Exception {
        bean.initialize();
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the adminUserId is null, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure1() throws Exception {
        try {
            context.setAdminUserId(null);

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the adminUserId isn't a string, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure2() throws Exception {
        try {
            context.setAdminUserId(new Object());

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the adminUserId isn't a valid long value, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure3() throws Exception {
        try {
            context.setAdminUserId("111kks");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the adminUserId isn't above 0, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure4() throws Exception {
        try {
            context.setAdminUserId("-1");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the applicationCategoryId is null, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure5() throws Exception {
        try {
            context.setApplicationCategoryId(null);

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the applicationCategoryId isn't a string, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure6() throws Exception {
        try {
            context.setApplicationCategoryId(new Object());

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the applicationCategoryId isn't a valid long value, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure7() throws Exception {
        try {
            context.setApplicationCategoryId("111kks");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the applicationCategoryId isn't above 0, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure8() throws Exception {
        try {
            context.setApplicationCategoryId("-1");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the assemblyCompetitionCategoryId is null,
     * <code>ServiceConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure9() throws Exception {
        try {
            context.setAssemblyCompetitionCategoryId(null);

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the assemblyCompetitionCategoryId isn't a string,
     * <code>ServiceConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure10() throws Exception {
        try {
            context.setAssemblyCompetitionCategoryId(new Object());

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the assemblyCompetitionCategoryId isn't a valid long value,
     * <code>ServiceConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure11() throws Exception {
        try {
            context.setAssemblyCompetitionCategoryId("111kks");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the assemblyCompetitionCategoryId isn't above 0,
     * <code>ServiceConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure12() throws Exception {
        try {
            context.setAssemblyCompetitionCategoryId("-1");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the componentCategoryId is null, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure13() throws Exception {
        try {
            context.setComponentCategoryId(null);

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the componentCategoryId isn't a string, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure14() throws Exception {
        try {
            context.setComponentCategoryId(new Object());

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the componentCategoryId isn't a valid long value, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure15() throws Exception {
        try {
            context.setComponentCategoryId("111kks");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the componentCategoryId isn't above 0, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure16() throws Exception {
        try {
            context.setComponentCategoryId("-1");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the testingCompetitionCategoryId is null, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure17() throws Exception {
        try {
            context.setTestingCompetitionCategoryId(null);

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the testingCompetitionCategoryId isn't a string, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure18() throws Exception {
        try {
            context.setTestingCompetitionCategoryId(new Object());

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the testingCompetitionCategoryId isn't a valid long value,
     * <code>ServiceConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure19() throws Exception {
        try {
            context.setTestingCompetitionCategoryId("111kks");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the testingCompetitionCategoryId isn't above 0, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure20() throws Exception {
        try {
            context.setTestingCompetitionCategoryId("-1");

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>initialize()</code>.
     * </p>
     *
     * <p>
     * Failure cause: If fail to create the <code>ForumFactory</code>, <code>ServiceConfigurationException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInitialize_Failure21() throws Exception {
        try {
            MockForumFactory.setException(true);

            bean.initialize();
            fail("ServiceConfigurationException is expected.");
        } catch (ServiceConfigurationException e) {
            MockForumFactory.setException(false);

            //success
        }
    }
}
