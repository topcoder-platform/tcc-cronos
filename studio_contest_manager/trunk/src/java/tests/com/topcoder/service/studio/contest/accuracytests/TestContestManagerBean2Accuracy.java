package com.topcoder.service.studio.contest.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.bean.ContestManagerBean;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class TestContestManagerBean2Accuracy extends TestCase {
    /** Represents the ContestManagerBean instance for testing. */
    private ContestManagerBean manager = null;

    /**
     * <p>
     * The mock <code>SessionContext</code> for simulating the ejb environment.
     * </p>
     */
    private MockSessionContext context;

    /** Represents the EntityManager for testing. */
    private EntityManager entityManager = null;

    /** Represents the EntityTransaction instance for testing. */
    private EntityTransaction transaction = null;

    /**
     * Set up.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        DBUtil.clearDatabase();

        manager = new ContestManagerBean();

        context = new MockSessionContext();

        context.addEntry("unitName", "contest_submission");
        context.addEntry("activeContestStatusId", new Long(10));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "System.out");

        context.addEntry("documentContentManagerClassName",
                "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys", "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        // create EntityManager.
        entityManager = Persistence.createEntityManagerFactory("contest_submission").createEntityManager();

        context.addEntry("contest_submission", entityManager);

        Field field = manager.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(manager, context);

        Method method = manager.getClass().getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(manager, new Object[0]);
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             to junit
     */
    public void tearDown() throws Exception {
        // DBUtil.clearDatabase();
    }

    /**
     * Test method <code>List<Contest> searchContests(Filter filter) </code>.
     *
     * <p>
     * In this test case,complex filter condition will be built.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCOntestsWithFilter_ComplexFilter_1() throws Exception {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
        Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

        Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc",
                "review", "dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

        Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
                "review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

        Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(1);

        Filter filter = ContestFilterFactory.createNotFilter(filter1);

        Filter filter2 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
        Filter filter3 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
        Filter channelNameFilter = ContestFilterFactory.createOrFilter(new Filter[] {filter2, filter3});

        Filter startDateFilter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

        Filter f1 = ContestFilterFactory.createStudioContestNameFilter("dev");
        Filter f2 = ContestFilterFactory.createStudioContestNameFilter("dev_2");

        Filter nameFilter = ContestFilterFactory.createOrFilter(new Filter[] {f1, f2});
        Filter[] filters = new Filter[] {filter, channelNameFilter, startDateFilter, nameFilter};
        Filter searchFilter = ContestFilterFactory.createAndFilter(filters);

        // all contest match the complex search condition.
        List<Contest> ret = manager.searchContests(searchFilter);

        assertEquals("Equalt is expected to 2.", 2, ret.size());
    }

    /**
     * Test method <code>List<Contest> searchContests(Filter filter) </code>.
     *
     * <p>
     * In this test case,complex filter condition will be built.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCOntestsWithFilter_ComplexFilter_2() throws Exception {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
        Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

        Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc",
                "review", "dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

        Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
                "review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

        Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(1);

        Filter filter = ContestFilterFactory.createNotFilter(filter1);

        Filter filter2 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
        Filter filter3 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
        Filter channelNameFilter = ContestFilterFactory.createOrFilter(new Filter[] {filter2, filter3});

        Filter startDateFilter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

        Filter f1 = ContestFilterFactory.createStudioContestNameFilter("dev");
        Filter f2 = ContestFilterFactory.createStudioContestNameFilter("dev_2");

        Filter nameFilter = ContestFilterFactory.createOrFilter(new Filter[] {f1, f2});
        Filter[] filters = new Filter[] {filter, channelNameFilter, startDateFilter, nameFilter};
        Filter searchFilter = ContestFilterFactory.createAndFilter(filters);

        // all contest match the complex search condition.
        List<Contest> ret = manager.searchContests(ContestFilterFactory.createNotFilter(searchFilter));

        assertEquals("Equalt is expected to 0.", 0, ret.size());
    }

    /**
     * Test method <code>List<Contest> searchContests(Filter filter) </code>.
     *
     * <p>
     * In this test case,complex filter condition will be built.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchCOntestsWithFilter_ComplexFilter_3() throws Exception {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
        Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

        Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc",
                "review", "dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

        Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
                "review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

        Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(1);

        Filter filter = ContestFilterFactory.createNotFilter(filter1);

        Filter filter2 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
        Filter filter3 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
        Filter channelNameFilter = ContestFilterFactory.createOrFilter(new Filter[] {filter2, filter3});

        Filter startDateFilter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

        Filter f1 = ContestFilterFactory.createStudioContestNameFilter("dev");
        Filter f2 = ContestFilterFactory.createStudioContestNameFilter("dev_2");

        Filter nameFilter = ContestFilterFactory.createOrFilter(new Filter[] {f1, f2});
        Filter[] filters = new Filter[] {filter, channelNameFilter, startDateFilter, nameFilter};
        Filter searchFilter = ContestFilterFactory.createAndFilter(filters);

        Filter notFilter1 = ContestFilterFactory.createNotFilter(searchFilter);
        Filter notFilter2 = ContestFilterFactory.createNotFilter(notFilter1);
        // all contest match the complex search condition.
        List<Contest> ret = manager.searchContests(notFilter2);

        assertEquals("Equalt is expected to 2.", 2, ret.size());
    }

}
