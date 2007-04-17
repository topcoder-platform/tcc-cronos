/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder;
import com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAO;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Accuracy tests for <code>DbRejectEmailDAO</code>.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class DbRejectEmailDAOAccuracyTests extends TestCase {
    /** Represents the connection name. */
    private static final String CONNECTION_NAME = "informix_connect";

    /** Represents private instance used for test. */
    private DbRejectEmailDAO dao = null;

    /** Represents private instance of DB factory. */
    private DBConnectionFactory dbFactory = null;

    /** Represents the audit manager. */
    private MockAuditManager auditManager = null;

    /**
     * Setup the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig("db_config.xml");
        AccuracyTestHelper.loadXMLConfig("SearchBuilder_RejectEmail.xml");
        AccuracyTestHelper.loadXMLConfig("SearchBuilder_RejectReason.xml");
        AccuracyTestHelper.loadXMLConfig("TimeTrackerRejectReason.xml");
        AccuracyTestHelper.loadXMLConfig("SearchBundle.xml");

        AccuracyTestHelper.tearDownDataBase();
        auditManager = new MockAuditManager();
        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        dao = new DbRejectEmailDAO(dbFactory, CONNECTION_NAME, auditManager,
                AccuracyTestHelper.ID_GENERATOR_NAME);
    }

    /**
     * <p>
     * Tear down the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        auditManager.clear();
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("The instance should be created.", dao);
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectEmail</code>. Create an email without audit.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testcreateRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = dao.createRejectEmail(email, "user", false);

        RejectEmail retrieved = dao.retrieveRejectEmail(email.getId());

        assertTrue("The retrieved email should equal to the original one.", AccuracyTestHelper
                .isRejectEmailEqual(email, retrieved));

        // no audit
        assertEquals("The audit list should be empty.", 0, auditManager.getAuditHeaders().size());
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectEmail</code>. Create an email with audit.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testcreateRejectEmailAccuracy2() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = dao.createRejectEmail(email, "user", true);

        RejectEmail retrieved = dao.retrieveRejectEmail(email.getId());

        assertTrue("The retrieved email should equal to the original one.", AccuracyTestHelper
                .isRejectEmailEqual(email, retrieved));

        // with audit, the audit list should contain 2 audit header.
        assertEquals("The audit list should contain two audit headers.", 2, auditManager.getAuditHeaders()
                .size());
    }

    /**
     * <p>
     * Accuracy test for method <code>createRejectEmail</code>. Create an email with audit. Test if the
     * audit information is right.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testcreateRejectEmailAccuracy3() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = dao.createRejectEmail(email, "user", true);

        List headerList = auditManager.getAuditHeaders();
        assertEquals("The operation should be audited.", 2, headerList.size());

        Map headers = new HashMap();
        headers.put(((AuditHeader) headerList.get(0)).getTableName(), headerList.get(0));
        headers.put(((AuditHeader) headerList.get(1)).getTableName(), headerList.get(1));

        // Check audit for reject_email table
        AuditHeader header1 = (AuditHeader) headers.get("reject_email");
        assertNotNull("The audit header for reject_email table should not be null.", header1);
        assertEquals("The action type is not audited correctly.", AuditType.INSERT, header1.getActionType());
        assertEquals("The entity id is not audited correctly.", email.getId(), header1.getEntityId());
        assertEquals("The company id is not audited correctly.", email.getCompanyId(), header1.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header1.getApplicationArea());
        assertEquals("The creation date is not set correctly.", email.getCreationDate(), header1
                .getCreationDate());
        assertEquals("The creation user is not audit correctly.", "user", header1.getCreationUser());

        // Check audit for comp_reject_email table
        AuditHeader header2 = (AuditHeader) headers.get("comp_rej_email");
        assertNotNull("The audit header for comp_reject_email table should not be null.", header2);
        assertEquals("The action type is not audited correctly.", AuditType.INSERT, header2.getActionType());
        assertEquals("The entity id is not audited correctly.", email.getId(), header2.getEntityId());
        assertEquals("The company id is not audited correctly.", email.getCompanyId(), header2.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header2.getApplicationArea());
        assertEquals("The creation date is not set correctly.", email.getCreationDate(), header2
                .getCreationDate());
        assertEquals("The creation user is not audit correctly.", "user", header2.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveRejectEmail</code>.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testretrieveRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);

        email = dao.createRejectEmail(email, "user", false);
        dao.createRejectEmail(AccuracyTestHelper.createRejectEmail(2, 2), "user", false);

        RejectEmail retrieved = dao.retrieveRejectEmail(email.getId());

        assertTrue("The retrieved email should be equal to the original one.", AccuracyTestHelper
                .isRejectEmailEqual(email, retrieved));
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveRejectEmail</code>. Retrieve an un-exist item, null should be
     * return.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testretrieveRejectEmailAccuracy2() throws Exception {
        assertNull("Null should be return.", dao.retrieveRejectEmail(1));
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectEmail</code>. Without audit.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testupdateRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = dao.createRejectEmail(email, "user1", false);

        // Update and retrieve
        email.setBody("new message body");
        dao.updateRejectEmail(email, "user2", false);

        RejectEmail email2 = dao.retrieveRejectEmail(email.getId());

        assertEquals("The id should be equal.", email.getId(), email2.getId());
        assertEquals("The company id should be equal.", email.getCompanyId(), email2.getCompanyId());
        assertEquals("The body should be equal.", email.getBody(), email2.getBody());
        assertEquals("The creation date should be equal.", AccuracyTestHelper.DATEFORMAT.format(email
                .getCreationDate()), AccuracyTestHelper.DATEFORMAT.format(email2.getCreationDate()));
        assertEquals("The creation user should be equal.", email.getCreationUser(), email2.getCreationUser());
        assertEquals("The modification should be set to user2.", "user2", email2.getModificationUser());
        assertTrue("The modification date should be after the creation date", email2.getModificationDate()
                .compareTo(email2.getCreationDate()) >= 0);
        assertEquals("The audit list should be empty.", 0, auditManager.getAuditHeaders().size());
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectEmail</code>. With audit.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testupdateRejectEmailAccuracy2() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = dao.createRejectEmail(email, "user1", false);

        // Update and retrieve
        email.setBody("new message body");
        dao.updateRejectEmail(email, "user2", true);

        RejectEmail email2 = dao.retrieveRejectEmail(email.getId());

        assertEquals("The id should be equal.", email.getId(), email2.getId());
        assertEquals("The company id should be equal.", email.getCompanyId(), email2.getCompanyId());
        assertEquals("The body should be equal.", email.getBody(), email2.getBody());
        assertEquals("The creation date should be equal.", AccuracyTestHelper.DATEFORMAT.format(email
                .getCreationDate()), AccuracyTestHelper.DATEFORMAT.format(email2.getCreationDate()));
        assertEquals("The creation user should be equal.", email.getCreationUser(), email2.getCreationUser());
        assertEquals("The modification should be set to user2.", "user2", email2.getModificationUser());
        assertTrue("The modification date should be after the creation date", email2.getModificationDate()
                .compareTo(email2.getCreationDate()) >= 0);

        assertEquals("The audit list should contains only one header.", 1, auditManager.getAuditHeaders()
                .size());
    }

    /**
     * <p>
     * Accuracy test for method <code>updateRejectEmail</code>. With audit. Test the audit information.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testupdateRejectEmailAccuracy3() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email = dao.createRejectEmail(email, "user1", false);

        // Update and retrieve
        email.setBody("new message body");
        dao.updateRejectEmail(email, "user2", true);

        // Check audit for reject_email table
        AuditHeader header = (AuditHeader) auditManager.getAuditHeaders().get(0);
        assertNotNull("The audit header for reject_email table should not be null.", header);
        assertEquals("The action type is not audited correctly.", AuditType.UPDATE, header.getActionType());
        assertEquals("The table name is not audited correctly.", "reject_email", header.getTableName());
        assertEquals("The entity id is not audited correctly.", email.getId(), header.getEntityId());
        assertEquals("The company id is not audited correctly.", email.getCompanyId(), header.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header.getApplicationArea());
        assertEquals("The creation date is not set correctly.", email.getModificationDate(), header
                .getCreationDate());
        assertEquals("The creation user is not audit correctly.", "user2", header.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteRejectEmail</code>. Without audit.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testdeleteRejectEmailAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);

        email = dao.createRejectEmail(email, "user", false);

        assertNotNull("The email with the id exist.", dao.retrieveRejectEmail(email.getId()));

        dao.deleteRejectEmail(email, "user", false);

        assertNull("The email with the id should be deleted.", dao.retrieveRejectEmail(email.getId()));
    }

    /**
     * <p>
     * Accuracy test for method <code>deleteRejectEmail</code>. With audit.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testdeleteRejectEmailAccuracy2() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);
        email.setCompanyId(3);
        email.setBody("emailBody");
        email = dao.createRejectEmail(email, "user", false);

        assertNotNull("The email with the id exist.", dao.retrieveRejectEmail(email.getId()));

        dao.deleteRejectEmail(email, "user", true);

        assertNull("The email with the id should be deleted.", dao.retrieveRejectEmail(email.getId()));

        List headerList = auditManager.getAuditHeaders();
        assertEquals("The operation should be audited.", 2, headerList.size());

        Map headers = new HashMap();
        headers.put(((AuditHeader) headerList.get(0)).getTableName(), headerList.get(0));
        headers.put(((AuditHeader) headerList.get(1)).getTableName(), headerList.get(1));

        // Check audit for reject_email table
        AuditHeader header1 = (AuditHeader) headers.get("reject_email");
        assertNotNull("The audit header for reject_email table should not be null.", header1);
        assertEquals("The action type is not audited correctly.", AuditType.DELETE, header1.getActionType());
        assertEquals("The entity id is not audited correctly.", email.getId(), header1.getEntityId());
        assertEquals("The company id is not audited correctly.", email.getCompanyId(), header1.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header1.getApplicationArea());
        assertEquals("The creation date is not set correctly.", email.getCreationDate().getTime(), header1
                .getCreationDate().getTime(), 5);
        assertEquals("The creation user is not audit correctly.", "user", header1.getCreationUser());

        // Check audit for comp_rej_email table
        AuditHeader header2 = (AuditHeader) headers.get("comp_rej_email");
        assertNotNull("The audit header for comp_reject_email table should not be null.", header2);
        assertEquals("The action type is not audited correctly.", AuditType.DELETE, header2.getActionType());
        assertEquals("The entity id is not audited correctly.", email.getId(), header2.getEntityId());
        assertEquals("The company id is not audited correctly.", email.getCompanyId(), header2.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header2.getApplicationArea());
        assertEquals("The creation date is not set correctly.", email.getCreationDate().getTime(), header2
                .getCreationDate().getTime(), 5);
        assertEquals("The creation user is not audit correctly.", "user", header2.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectEmails</code>.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testlistRejectEmailsAccuracy1() throws Exception {
        RejectEmail email = AccuracyTestHelper.createRejectEmail(1, 1);

        email = dao.createRejectEmail(email, "user", false);

        RejectEmail[] retrieved = dao.listRejectEmails();

        assertEquals("There should be only one email.", 1, retrieved.length);
        assertTrue("The email should be equal.", AccuracyTestHelper.isRejectEmailEqual(email, retrieved[0]));
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectEmails</code>.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testlistRejectEmailsAccuracy2() throws Exception {
        dao.createRejectEmail(AccuracyTestHelper.createRejectEmail(1, 1), "user", false);
        dao.createRejectEmail(AccuracyTestHelper.createRejectEmail(2, 2), "user", false);

        RejectEmail[] retrieved = dao.listRejectEmails();
        assertEquals("There should two email.", 2, retrieved.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>listRejectEmails</code>.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testlistRejectEmailsAccuracy3() throws Exception {
        RejectEmail[] retrieved = dao.listRejectEmails();
        assertEquals("Empty array should be return.", 0, retrieved.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy1() throws Exception {
        RejectEmail email = dao.createRejectEmail(AccuracyTestHelper.createRejectEmail(1, 1), "user", false);
        dao.createRejectEmail(AccuracyTestHelper.createRejectEmail(2, 2), "user", false);

        RejectEmail[] retrieved = dao.searchRejectEmails((new RejectEmailSearchBuilder())
                .hasCompanyIdFilter(1));

        assertEquals("Just one result match.", 1, retrieved.length);
        assertTrue("The two should be equal.", AccuracyTestHelper.isRejectEmailEqual(email, retrieved[0]));
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Test search with modification user.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy4() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        email1 = dao.createRejectEmail(email1, "user", false);
        email2 = dao.createRejectEmail(email2, "user", false);

        // Search RejectEmail with modification user 'user'
        Filter filter = new RejectEmailSearchBuilder().modifiedByUserFilter("user");
        RejectEmail[] emails = dao.searchRejectEmails(filter);
        assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Test search with creation user.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy5() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        email1 = dao.createRejectEmail(email1, "user", false);
        email2 = dao.createRejectEmail(email2, "user", false);

        // Search RejectEmail with creation user 'user'
        Filter filter = new RejectEmailSearchBuilder().createdByUserFilter("user");
        RejectEmail[] emails = dao.searchRejectEmails(filter);
        assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Test search with email body.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy6() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        email1 = dao.createRejectEmail(email1, "user1", false);
        email2 = dao.createRejectEmail(email2, "user2", false);

        // Search RejectEmail with body 'email1'
        Filter filter = new RejectEmailSearchBuilder().hasBodyFilter("email1");
        RejectEmail[] emails = dao.searchRejectEmails(filter);
        assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);

        // Search RejectEmail with body 'email2'
        filter = new RejectEmailSearchBuilder().hasBodyFilter("email2");
        assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>searchRejectEmails</code>. Should return empty array if nothing
     * match.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testsearchRejectEmailsAccuracy() throws Exception {
        Filter filter = new RejectEmailSearchBuilder().hasCompanyIdFilter(1);
        assertEquals("Empty array should be returned.", 0, dao.searchRejectEmails(filter).length);
    }
}
