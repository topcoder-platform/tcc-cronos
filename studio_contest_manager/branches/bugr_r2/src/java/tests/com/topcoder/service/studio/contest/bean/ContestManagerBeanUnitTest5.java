/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.TestHelper;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;

/**
 * <p>
 * Unit tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.3
 */
public class ContestManagerBeanUnitTest5 extends TestCase {
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
    private MockEntityManager entityManager;

    /**
     * <p>
     * entities created during tests. They will be removed in the end of test.
     * </p>
     */
    private List entities = null;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        entities = new ArrayList();
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
/*        if (entityManager != null) {
            try {
                TestHelper.removeContests(entityManager, entities);
                entityManager.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        DBUtil.clearDatabase();
    }

    /**
     * <p>
     * Gets the test suite for <code>ContestManagerBean</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for
     *         <code>ContestManagerBean</code> class.
     */
    public static Test suite() {
        return new TestSuite(ContestManagerBeanUnitTest5.class);
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
        context
                .addEntry(
                        "documentContentManagerClassName",
                        "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys",
                "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass().getDeclaredMethod("initialize",
                new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        entityManager = new MockEntityManager();
        context.addEntry("contestManager", entityManager);
    }












    /*public void testGetUserContests1() throws Exception {
        entityManager.getTransaction().begin();
        StudioFileType fileType = new StudioFileType();
        TestHelper.populateStudioFileType(fileType);
        entityManager.persist(fileType);

        ContestChannel channel = new ContestChannel();
        TestHelper.populateContestChannel(channel);
        entityManager.persist(channel);

        ContestType contestType = new ContestType();
        TestHelper.populateContestType(contestType);
        contestType.setContestType(1L);
        entityManager.persist(contestType);

        ContestStatus status = new ContestStatus();
        status.setDescription("description");
        status.setName("Name");
        status.setContestStatusId(10L);
        status.setStatusId(1L);
        entityManager.persist(status);

        Date date = new Date();
        ContestGeneralInfo generalInfo = new ContestGeneralInfo();
        generalInfo.setBrandingGuidelines("guideline");
        generalInfo.setDislikedDesignsWebsites("disklike");
        generalInfo.setGoals("goal");
        generalInfo.setOtherInstructions("instruction");
        generalInfo.setTargetAudience("target audience");
        generalInfo.setWinningCriteria("winning criteria");

        ContestMultiRoundInformation multiRoundInformation = new ContestMultiRoundInformation();
        multiRoundInformation.setMilestoneDate(new Date());
        multiRoundInformation.setRoundOneIntroduction("round one");
        multiRoundInformation.setRoundTwoIntroduction("round two");

        ContestSpecifications specifications = new ContestSpecifications();
        specifications.setAdditionalRequirementsAndRestrictions("none");
        specifications.setColors("white");
        specifications.setFonts("Arial");
        specifications.setLayoutAndSize("10px");

        PrizeType prizeType = new PrizeType();
        prizeType.setDescription("Good");
        prizeType.setPrizeTypeId(1L);
        entityManager.persist(prizeType);

        MilestonePrize milestonePrize = new MilestonePrize();
        milestonePrize.setAmount(10.0);
        milestonePrize.setCreateDate(new Date());
        milestonePrize.setNumberOfSubmissions(1);
        milestonePrize.setType(prizeType);

        Contest entity = new Contest();

        TestHelper.populateContest(entity, date, channel, contestType, status, generalInfo, multiRoundInformation,
                specifications, milestonePrize);

        Set<Medium> media = new HashSet<Medium>();
        Medium medium1 = new Medium();
        medium1.setMediumId(1L);
        medium1.setDescription("Web");
        entityManager.persist(medium1);
        media.add(medium1);
        Medium medium2 = new Medium();
        medium2.setMediumId(2L);
        medium2.setDescription("Application");
        entityManager.persist(medium2);
        media.add(medium2);
        entity.setMedia(media);

        ContestResource resource2 = new ContestResource();
        resource2.setName("myresource");
        entityManager.persist(resource2);

        Set<ContestResource> resources = new HashSet<ContestResource>();
        resources.add(resource2);
        entity.setResources(resources);


        // save the entity
        entityManager.persist(entity);
        //entityManager.persist(contestType);


        //entityManager.persist(property);



        // save the entity
        //entityManager.persist(entity);

        ContestGeneralInfo entity = new ContestGeneralInfo();
        entity.setGoals("test_goals");
        entity.setTargetAudience("test_audience");
        entity.setBrandingGuidelines("test_guidelines");
        entity.setDislikedDesignsWebsites("test_websites");
        entity.setOtherInstructions("test_instructions");
        entity.setWinningCriteria("test_criteria");

        // save the entity
        //entityManager.persist(entity);
        ContestGeneralInfo persisted = (ContestGeneralInfo) entityManager.find(
                ContestGeneralInfo.class, new Long(81));
        ContestType
        persisted.setGoals("goals123");
        entityManager.merge(persisted);
System.out.println("getGoals="+persisted.getGoals());
System.out.println("getWinningCriteria="+persisted.getWinningCriteria());

        entityManager.getTransaction().commit();
    }*/











    /**
     * <p>
     * Accuracy test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If the contest with specified user name doesn't exist, empty list is
     * returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests() throws Exception {
        List<Contest> allContests = bean.getUserContests("not found");
        assertEquals("The size should be 0.", 0, allContests.size());
    }

    /**
     * <p>
     * Accuracy test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If the contests with specified user name exist, the contests should be
     * returned successfully.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests1() throws Exception {
        TestHelper.initContests(entityManager, entities);
        List<Contest> allContests  = bean.getUserContests("myresource");
        assertEquals("the count is wrong.", 2, allContests.size());
    }

    /**
     * <p>
     * Failure test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If the EntityManager has been closed,
     * <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests_Failure1() throws Exception {
        EntityManager em = (EntityManager) context.lookup("contestManager");
        em.close();

        try {
            bean.getUserContests("username");
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If any persistence exception is thrown,
     * <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests_Failure2() throws Exception {
        try {
            entityManager.enablePersistenceException(true);
            bean.getUserContests("username");

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If any transaction exception is thrown,
     * <code>ContestManagementException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests_Failure3() throws Exception {
        try {
            entityManager.enableTransactionException(true);
            bean.getUserContests("username");

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If username is null, <code>IllegalArgumentException</code> is thrown
     * instead.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests_NullUsername() throws Exception {

        try {
            bean.getUserContests(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getUserContests(String)</code>.
     * </p>
     *
     * <p>
     * If username is empty, <code>IllegalArgumentException</code> is thrown
     * instead.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUserContests_EmptyUsername() throws Exception {

        try {
            bean.getUserContests("   ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getAllContests()</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetAllContests_Accuracy() throws Exception {
        TestHelper.initContests(entityManager, entities);
        List<Contest> allContests = bean.getAllContests();
        assertEquals("The size should be 2.", 2, allContests.size());
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContests()</code>.
     * </p>
     *
     * <p>
     * If any transaction exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContests_Failure1() throws Exception {
        try {
            entityManager.enableTransactionException(true);
            bean.getAllContests();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContests()</code>.
     * </p>
     *
     * <p>
     * If any persistence exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContests_Failure2() throws Exception {
        try {
            entityManager.enablePersistenceException(true);
            bean.getAllContests();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContests()</code>.
     * </p>
     *
     * <p>
     * If the entityManager is closed, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContests_Failure3() throws Exception {
        try {
            entityManager.close();
            entityManager.enablePersistenceException(true);
            bean.getAllContests();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig() throws Exception {

        Contest contest = TestHelper.initContests(entityManager, entities);

        ContestProperty property = new ContestProperty();
        property.setDescription("description");

        ContestConfig config = new ContestConfig();
        Identifier id = new Identifier();
        id.setContest(contest);
        id.setProperty(property);
        config.setId(id);
        config.setValue("value");

        bean.addConfig(config);
        ContestConfig persist = bean.getConfig(config.getId());
        assertEquals("The contest config should match.", "value", persist.getValue());
    }

    /**
     * <p>
     * Failure test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig_Failure1() throws Exception {
        try {

            entityManager.close();
            bean.getConfig(new Identifier());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig_Failure2() throws Exception {
        try {
            entityManager.enablePersistenceException(true);
            bean.getConfig(new Identifier());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for <code>getConfig(long)</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfig_Failure3() throws Exception {
        try {

            entityManager.enableTransactionException(true);
            bean.getConfig(new Identifier());

            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // success
        }
    }
}
