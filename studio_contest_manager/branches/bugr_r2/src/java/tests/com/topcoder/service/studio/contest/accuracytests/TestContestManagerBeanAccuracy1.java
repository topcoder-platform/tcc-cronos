/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.bean.ContestManagerBean;
import com.topcoder.service.studio.contest.bean.MockEntityManager;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


/**
 * <p>
 * Accuracy tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author myxgyy
 * @version 1.3
 */
public class TestContestManagerBeanAccuracy1 extends TestCase {
    /**
     * <p>
     * The <code>ContestManagerBean</code> instance for testing.
     * </p>
     */
    private ContestManagerBean bean;

    /**
     * <p>
     * The mock <code>SessionContext</code> for simulating the ejb
     * environment.
     * </p>
     */
    private MockSessionContext context;

    /**
     * <p>
     * The mock <code>EntityManager</code> for testing.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        bean = new ContestManagerBean();

        context = new MockSessionContext();

        Field field = bean.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(bean, context);

        initContext();

        DBUtil.clearDatabase();
        DBUtil.initDatabase();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    protected void tearDown() throws Exception {
        DBUtil.clearDatabase();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * <p>
     * Initialize the context.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void initContext() throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("auditChange", new Boolean(false));
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        //context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
            "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass()
                            .getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        entityManager = MockEntityManager.EMF.createEntityManager();
        context.addEntry("contestManager", entityManager);
    }

    /**
     * <p>
     * Accuracy test case for <code>getUserContests(String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests() throws Exception {
        List<Contest> allContests = bean.getUserContests("not_exist");
        assertEquals("The size should be 0.", 0, allContests.size());
    }

    /**
     * <p>
     * Accuracy test for <code>getUserContests(String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests1() throws Exception {
        // initContests(entityManager);
        List<Contest> contests = bean.getUserContests("acc1");
        assertEquals("The size should be 3.", 3, contests.size());
    }

    /**
     * <p>
     * Accuracy test for <code>getUserContests(String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests2() throws Exception {
        // initContests(entityManager);
        List<Contest> contests = bean.getUserContests("acc2");
        assertEquals("The size should be 2.", 2, contests.size());
    }

    /**
    * <p>
    * Accuracy test for <code>getConfig(Identifier)</code>.
    * </p>
    *
    * @throws Exception to JUnit.
    */
    public void testGetConfig() throws Exception {
        ContestProperty property = new ContestProperty();
        property.setDescription("description");

        ContestConfig config = new ContestConfig();
        Identifier id = new Identifier();
        id.setProperty(property);
        config.setId(id);
        config.setValue("value");

        bean.addConfig(config);

        ContestConfig persist = bean.getConfig(config.getId());
        assertEquals("The contest config should match.", "value",
            persist.getValue());
    }
}
