/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailManager;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy tests for <code>DbRejectEmailDAO</code>.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectEmailManagerAccuracyTests extends TestCase {
    /** Represents private instance used for test. */
    private RejectEmailManager manager = null;

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
        AccuracyTestHelper.tearDownDataBase();

        manager = new RejectEmailManager(RejectEmailManager.class.getName());
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
        assertNotNull("The instance should be created.", manager);
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectEmail</code>. Create an email without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = manager.createRejectEmail(email, "user", false);

        RejectEmail retrieved = manager.retrieveRejectEmail(email.getId());

        assertTrue("The retrieved email should equal to the original one.",
            AccuracyTestHelper.isRejectEmailEqual(email, retrieved));
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectEmail</code>. Create an email with audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreateRejectEmailAccuracy2() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = manager.createRejectEmail(email, "user", true);

        RejectEmail retrieved = manager.retrieveRejectEmail(email.getId());

        assertTrue("The retrieved email should equal to the original one.",
            AccuracyTestHelper.isRejectEmailEqual(email, retrieved));
    }
    /**
     * <p>
     * Accuracy test for method <code>retrieveRejectEmail</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);

        email = manager.createRejectEmail(email, "user", false);
        manager.createRejectEmail(AccuracyTestHelper.createRejectEmail(2, 2), "user", false);

        RejectEmail retrieved = manager.retrieveRejectEmail(email.getId());

        assertTrue("The retrieved email should be equal to the original one.",
            AccuracyTestHelper.isRejectEmailEqual(email, retrieved));
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveRejectEmail</code>. Retrieve an un-exist item, null should be return.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveRejectEmailAccuracy2() throws Exception {
        assertNull("Null should be return.", manager.retrieveRejectEmail(1));
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectEmail</code>. Without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = manager.createRejectEmail(email, "user1", false);

        // Update and retrieve
        email.setBody("new message body");
        manager.updateRejectEmail(email, "user2", false);

        RejectEmail email2 = manager.retrieveRejectEmail(email.getId());

        assertEquals("The id should be equal.", email.getId(), email2.getId());
        assertEquals("The company id should be equal.", email.getCompanyId(), email2.getCompanyId());
        assertEquals("The body should be equal.", email.getBody(), email2.getBody());
        assertEquals("The creation date should be equal.",
            AccuracyTestHelper.DATEFORMAT.format(email.getCreationDate()),
            AccuracyTestHelper.DATEFORMAT.format(email2.getCreationDate()));
        assertEquals("The creation user should be equal.", email.getCreationUser(), email2.getCreationUser());
        assertEquals("The modification should be set to user2.", "user2", email2.getModificationUser());
        assertTrue("The modification date should be after the creation date",
            email2.getModificationDate().compareTo(email2.getCreationDate()) >= 0);
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectEmail</code>. With audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateRejectEmailAccuracy2() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = manager.createRejectEmail(email, "user1", false);

        // Update and retrieve
        email.setBody("new message body");
        manager.updateRejectEmail(email, "user2", true);

        RejectEmail email2 = manager.retrieveRejectEmail(email.getId());

        assertEquals("The id should be equal.", email.getId(), email2.getId());
        assertEquals("The company id should be equal.", email.getCompanyId(), email2.getCompanyId());
        assertEquals("The body should be equal.", email.getBody(), email2.getBody());
        assertEquals("The creation date should be equal.",
            AccuracyTestHelper.DATEFORMAT.format(email.getCreationDate()),
            AccuracyTestHelper.DATEFORMAT.format(email2.getCreationDate()));
        assertEquals("The creation user should be equal.", email.getCreationUser(), email2.getCreationUser());
        assertEquals("The modification should be set to user2.", "user2", email2.getModificationUser());
        assertTrue("The modification date should be after the creation date",
            email2.getModificationDate().compareTo(email2.getCreationDate()) >= 0);
    }
    /**
     * <p>
     * Accuracy test for method <code>deleteRejectEmail</code>. Without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);

        email = manager.createRejectEmail(email, "user", false);

        assertNotNull("The email with the id exist.", manager.retrieveRejectEmail(email.getId()));

        manager.deleteRejectEmail(email, "user", false);

        assertNull("The email with the id should be deleted.", manager.retrieveRejectEmail(email.getId()));
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteRejectEmail</code>. With audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testdeleteRejectEmailAccuracy2() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email.setCompanyId(3);
        email.setBody("emailBody");
        email = manager.createRejectEmail(email, "user", false);

        assertNotNull("The email with the id exist.", manager.retrieveRejectEmail(email.getId()));

        manager.deleteRejectEmail(email, "user", true);

        assertNull("The email with the id should be deleted.", manager.retrieveRejectEmail(email.getId()));
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectEmails</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testlistRejectEmailsAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);

        email = manager.createRejectEmail(email, "user", false);

        RejectEmail[] retrieved = manager.listRejectEmails();

        assertEquals("There should be only one email.", 1, retrieved.length);
        assertTrue("The email should be equal.", AccuracyTestHelper.isRejectEmailEqual(email, retrieved[0]));
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectEmails</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testlistRejectEmailsAccuracy2() throws Exception {
        manager.createRejectEmail(AccuracyTestHelper.createRejectEmail(1, 1), "user", false);
        manager.createRejectEmail(AccuracyTestHelper.createRejectEmail(2, 2), "user", false);

        RejectEmail[] retrieved = manager.listRejectEmails();
        assertEquals("There should two email.", 2, retrieved.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectEmails</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testlistRejectEmailsAccuracy3() throws Exception {
        RejectEmail[] retrieved = manager.listRejectEmails();
        assertEquals("Empty array should be return.", 0, retrieved.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy1() throws Exception {
        RejectEmail email = manager.createRejectEmail(AccuracyTestHelper.createRejectEmail(1, 1), "user", false);
        manager.createRejectEmail(AccuracyTestHelper.createRejectEmail(2, 2), "user", false);

        RejectEmail[] retrieved = manager.searchRejectEmails((new RejectEmailSearchBuilder()).hasCompanyIdFilter(1));

        assertEquals("Just one result match.", 1, retrieved.length);
        assertTrue("The two should be equal.", AccuracyTestHelper.isRejectEmailEqual(email, retrieved[0]));
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Test search with modification user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy4() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        email1 = manager.createRejectEmail(email1, "user", false);
        email2 = manager.createRejectEmail(email2, "user", false);

        // Search RejectEmail with modification user 'user'
        Filter filter = new RejectEmailSearchBuilder().modifiedByUserFilter("user");
        RejectEmail[] emails = manager.searchRejectEmails(filter);
        assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Test search with creation user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy5() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        email1 = manager.createRejectEmail(email1, "user", false);
        email2 = manager.createRejectEmail(email2, "user", false);

        // Search RejectEmail with creation user 'user'
        Filter filter = new RejectEmailSearchBuilder().createdByUserFilter("user");
        RejectEmail[] emails = manager.searchRejectEmails(filter);
        assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Test search with email body.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy6() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        email1 = manager.createRejectEmail(email1, "user1", false);
        email2 = manager.createRejectEmail(email2, "user2", false);

        // Search RejectEmail with body 'email1'
        Filter filter = new RejectEmailSearchBuilder().hasBodyFilter("email1");
        RejectEmail[] emails = manager.searchRejectEmails(filter);
        assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);

        // Search RejectEmail with body 'email2'
        filter = new RejectEmailSearchBuilder().hasBodyFilter("email2");
        assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Should return empty array if nothing match.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy() throws Exception {
        Filter filter = new RejectEmailSearchBuilder().hasCompanyIdFilter(1);
        assertEquals("Empty array should be returned.", 0, manager.searchRejectEmails(filter).length);
    }
}
