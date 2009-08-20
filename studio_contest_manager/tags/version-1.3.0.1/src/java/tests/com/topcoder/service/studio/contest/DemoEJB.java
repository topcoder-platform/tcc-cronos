/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;

/**
 * <p>
 * Demo Studio Manager 1.3.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.3
 * @since 1.1
 */
public class DemoEJB extends TestCase {
    /**
     * <p>
     * The JBoss's url for naming service.
     * </p>
     */
    private static String url;

    /**
     * <p>
     * EntityManager proxy.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * entities created during tests. They will be removed in the end of test.
     * </p>
     */
    private List entities = new ArrayList();

    static {
        try {
            Properties prop = new Properties();
            prop.load(DemoEJB.class.getResourceAsStream("/jndi.properties"));
            url = prop.getProperty("java.naming.provider.url");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (SecurityException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contest_submission");
        entityManager = emf.createEntityManager();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        if (entityManager != null) {
            try {
                TestHelper.removeContests(entityManager, entities);
                entityManager.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    /**
     * <p>
     * Gets the test suite for <code>DemoEJB</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>DemoEJB</code> class.
     */
    public static Test suite() {
        return new TestSuite(DemoEJB.class);
    }

    /**
     * <p>
     * EJB demo test for studio manager functions.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo() throws Exception {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
        props.setProperty("java.naming.provider.url", url);

        InitialContext ctx;

        SocketDocumentContentServer server = new SocketDocumentContentServer(30000, 10, "test");

        // set up test data
        TestHelper.initContests(entityManager, entities);

        try {
            // start document server
            server.start();

            // lookup JNDI and get the bean
            ctx = new InitialContext(props);
            ContestManager bean = (ContestManager) ctx.lookup("ContestManagerBean/remote");

            // demo for 1.0 features
            List<ContestChannel> channels = bean.getAllContestChannels();
            assertEquals("It should have 1 channel.", 1, channels.size());

            List<StudioFileType> fileTypes = bean.getAllStudioFileTypes();
            assertEquals("It should have 1 file type.", 1, fileTypes.size());

            List<ContestStatus> contestStatues = bean.getAllContestStatuses();
            assertEquals("It should have 2 status.", 2, contestStatues.size());

            // demo for 1.1 features
            // get all the available contests
            List<Contest> allContests = bean.getAllContests();
            assertEquals("It should have 2 contests totally.", 2, allContests.size());

            // Do a search for some contests
            // create a search filter to search by contest id
            Filter filter1 = ContestFilterFactory.createStudioContestFileTypeIdFilter(1);
            // use it to search
            List<Contest> contestById = bean.searchContests(filter1);
            assertEquals("It should have 2 contests for file type id 1.", 2, contestById.size());

            // create a search filter to search by type extension
            Filter filter2 = ContestFilterFactory.createStudioFileTypeExtensionFilter("jpeg");
            // use it to search
            List<Contest> contestByFileTypeExtension = bean.searchContests(filter2);
            assertEquals("It should have 2 contests for file type extension 'jpeg'.", 2, contestByFileTypeExtension
                .size());

            // create a search filter to search by forum id
            Filter filter3 = ContestFilterFactory.createStudioContestForumIdFilter(2);
            // use it to search
            List<Contest> contestByForumId = bean.searchContests(filter3);
            assertEquals("It should have only 1 contest for forum id of 2.", 1, contestByForumId.size());

            // we could also do a complex search using the OR filter
            // We will combine the first two of the above filters into a single complex filter
            Filter compositeOr = ContestFilterFactory.createOrFilter(filter1, filter2);
            // use it to search
            List<Contest> contestByForumIdorContestId = bean.searchContests(compositeOr);

            // Retrieve all contests for which test_user is a resource
            List<Contest> contests = bean.getUserContests("my name");
            assertEquals("It should have no user contest with user name my name.", 0, contests.size());
        } finally {
            server.stop();
        }
    }
}
