/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonSearchBuilder;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonSessionBean;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>RejectReasonSessionBean</code>.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectReasonSessionBeanAccuracyTests extends TestCase {
    /** Represents private instance used for test.*/
    private RejectReasonSessionBean bean;
    /**
     * Setup the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig("db_config.xml");
        AccuracyTestHelper.loadXMLConfig("SearchBuilder_RejectEmail.xml");
        AccuracyTestHelper.loadXMLConfig("SearchBuilder_RejectReason.xml");
        AccuracyTestHelper.loadXMLConfig("TimeTrackerRejectReason.xml");
        AccuracyTestHelper.loadXMLConfig("SearchBundle.xml");
        AccuracyTestHelper.loadXMLConfig("JNDIUtils.xml");
        AccuracyTestHelper.loadXMLConfig("obfactory_config.xml");

        AccuracyTestHelper.setUpEJB();
        bean = new RejectReasonSessionBean();
    }

    /**
     * <p>
     * Tear down the environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("The instance should be created.", bean);
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectReason</code>. Create an reason without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateRejectReasonAccuracy1() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);
        reason = bean.createRejectReason(reason, "user", false);

        RejectReason retrieved = bean.retrieveRejectReason(reason.getId());

        assertTrue("The retrieved reason should equal to the original one.",
            AccuracyTestHelper.isRejectReasonEqual(reason, retrieved));
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectReason</code>. Create an reason with audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateRejectReasonAccuracy2() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);
        reason = bean.createRejectReason(reason, "user", true);

        RejectReason retrieved = bean.retrieveRejectReason(reason.getId());

        assertTrue("The retrieved reason should equal to the original one.",
            AccuracyTestHelper.isRejectReasonEqual(reason, retrieved));
    }
    /**
     * <p>
     * Accuracy test for method <code>retrieveRejectReason</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveRejectReasonAccuracy1() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);

        reason = bean.createRejectReason(reason, "user", false);
        bean.createRejectReason(AccuracyTestHelper.createRejectReason(2, 2), "user", false);

        RejectReason retrieved = bean.retrieveRejectReason(reason.getId());

        assertTrue("The retrieved reason should be equal to the original one.",
            AccuracyTestHelper.isRejectReasonEqual(reason, retrieved));
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveRejectReason</code>. Retrieve an un-exist item, null should be return.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveRejectReasonAccuracy2() throws Exception {
        assertNull("Null should be return.", bean.retrieveRejectReason(1));
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectReason</code>. Without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateRejectReasonAccuracy1() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);
        reason = bean.createRejectReason(reason, "user1", false);

        // Update and retrieve
        reason.setDescription("new message des");
        bean.updateRejectReason(reason, "user2", false);

        RejectReason reason2 = bean.retrieveRejectReason(reason.getId());

        assertEquals("The id should be equal.", reason.getId(), reason2.getId());
        assertEquals("The company id should be equal.", reason.getCompanyId(), reason2.getCompanyId());
        assertEquals("The body should be equal.", reason.getDescription(), reason2.getDescription());
        assertEquals("The creation date should be equal.", AccuracyTestHelper.DATEFORMAT.format(reason.getCreationDate()),
            AccuracyTestHelper.DATEFORMAT.format(reason2.getCreationDate()));
        assertEquals("The creation user should be equal.", reason.getCreationUser(), reason2.getCreationUser());
        assertEquals("The modification should be set to user2.", "user2", reason2.getModificationUser());
        assertTrue("The modification date should be after the creation date",
            reason2.getModificationDate().compareTo(reason2.getCreationDate()) >= 0);
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectReason</code>. With audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateRejectReasonAccuracy2() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);
        reason = bean.createRejectReason(reason, "user1", false);

        // Update and retrieve
        reason.setDescription("new message body");
        bean.updateRejectReason(reason, "user2", true);

        RejectReason reason2 = bean.retrieveRejectReason(reason.getId());

        assertEquals("The id should be equal.", reason.getId(), reason2.getId());
        assertEquals("The company id should be equal.", reason.getCompanyId(), reason2.getCompanyId());
        assertEquals("The body should be equal.", reason.getDescription(), reason2.getDescription());
        assertEquals("The creation date should be equal.", AccuracyTestHelper.DATEFORMAT.format(reason.getCreationDate()),
            AccuracyTestHelper.DATEFORMAT.format(reason2.getCreationDate()));
        assertEquals("The creation user should be equal.", reason.getCreationUser(), reason2.getCreationUser());
        assertEquals("The modification should be set to user2.", "user2", reason2.getModificationUser());
        assertTrue("The modification date should be after the creation date",
            reason2.getModificationDate().compareTo(reason2.getCreationDate()) >= 0);
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteRejectReason</code>. Without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteRejectReasonAccuracy1() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);

        reason = bean.createRejectReason(reason, "user", false);

        assertNotNull("The reason with the id exist.", bean.retrieveRejectReason(reason.getId()));

        bean.deleteRejectReason(reason, "user", false);

        assertNull("The reason with the id should be deleted.", bean.retrieveRejectReason(reason.getId()));
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteRejectReason</code>. With audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteRejectReasonAccuracy2() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);
        reason.setCompanyId(3);
        reason.setDescription("reasonDescription");
        reason = bean.createRejectReason(reason, "user", false);

        assertNotNull("The reason with the id exist.", bean.retrieveRejectReason(reason.getId()));

        bean.deleteRejectReason(reason, "user", true);

        assertNull("The reason with the id should be deleted.", bean.retrieveRejectReason(reason.getId()));
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectReasons</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testlistRejectReasonsAccuracy1() throws Exception {
        RejectReason reason = AccuracyTestHelper.createRejectReason(1, 1);

        reason = bean.createRejectReason(reason, "user", false);

        RejectReason[] retrieved = bean.listRejectReasons();

        assertEquals("There should be only one reason.", 1, retrieved.length);
        assertTrue("The reason should be equal.", AccuracyTestHelper.isRejectReasonEqual(reason, retrieved[0]));
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectReasons</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testlistRejectReasonsAccuracy2() throws Exception {
        bean.createRejectReason(AccuracyTestHelper.createRejectReason(1, 1), "user", false);
        bean.createRejectReason(AccuracyTestHelper.createRejectReason(2, 2), "user", false);

        RejectReason[] retrieved = bean.listRejectReasons();
        assertEquals("There should two reason.", 2, retrieved.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectReasons</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testlistRejectReasonsAccuracy3() throws Exception {
        RejectReason[] retrieved = bean.listRejectReasons();
        assertEquals("Empty array should be return.", 0, retrieved.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectReasons</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectReasonsAccuracy1() throws Exception {
        RejectReason reason = bean.createRejectReason(AccuracyTestHelper.createRejectReason(1, 1), "user", false);
        bean.createRejectReason(AccuracyTestHelper.createRejectReason(2, 2), "user", false);

        RejectReason[] retrieved = bean.searchRejectReasons((new RejectReasonSearchBuilder()).hasCompanyIdFilter(1));

        assertEquals("Just one result match.", 1, retrieved.length);
        assertTrue("The two should be equal.", AccuracyTestHelper.isRejectReasonEqual(reason, retrieved[0]));
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectReasons</code>. Test search with modification user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectReasonsAccuracy4() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("reason1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("reason2");

        reason1 = bean.createRejectReason(reason1, "user", false);
        reason2 = bean.createRejectReason(reason2, "user", false);

        // Search RejectReason with modification user 'user'
        Filter filter = new RejectReasonSearchBuilder().modifiedByUserFilter("user");
        RejectReason[] reasons = bean.searchRejectReasons(filter);
        assertEquals("The number of RejectReasons returned is not correct.", 2, reasons.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectReasons</code>. Test search with creation user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectReasonsAccuracy5() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("reason1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("reason2");

        reason1 = bean.createRejectReason(reason1, "user", false);
        reason2 = bean.createRejectReason(reason2, "user", false);

        // Search RejectReason with creation user 'user'
        Filter filter = new RejectReasonSearchBuilder().createdByUserFilter("user");
        RejectReason[] reasons = bean.searchRejectReasons(filter);
        assertEquals("The number of RejectReasons returned is not correct.", 2, reasons.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectReasons</code>. Test search with reason body.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectReasonsAccuracy6() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("reason1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("reason2");

        reason1 = bean.createRejectReason(reason1, "user1", false);
        reason2 = bean.createRejectReason(reason2, "user2", false);

        // Search RejectReason with body 'reason1'
        Filter filter = new RejectReasonSearchBuilder().hasDescriptionFilter("reason1");
        RejectReason[] reasons = bean.searchRejectReasons(filter);
        assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);

        // Search RejectReason with body 'reason2'
        filter = new RejectReasonSearchBuilder().hasDescriptionFilter("reason2");
        assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectReasons</code>. Should return empty array if nothing match.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectReasonsAccuracy() throws Exception {
        Filter filter = new RejectReasonSearchBuilder().hasCompanyIdFilter(1);
        assertEquals("Empty array should be returned.", 0, bean.searchRejectReasons(filter).length);
    }
}
