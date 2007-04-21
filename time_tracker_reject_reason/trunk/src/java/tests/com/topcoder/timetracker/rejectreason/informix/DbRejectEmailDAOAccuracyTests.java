/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder;
import com.topcoder.timetracker.rejectreason.TestHelper;

import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Accuracy tests for <code>DbRejectEmailDAO</code> class. Instantiation tests and database CRUDE tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbRejectEmailDAOAccuracyTests extends TestHelper {
    /** A date formatter for date comparison. */
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /** The audit manager to create the DAO object. */
    private MockAuditManager audit = null;

    /** The DbRejectEmailDAO instance to be tested. */
    private DbRejectEmailDAO dao = null;

    /**
     * Sets up test environment. Configuration is loaded and new instance of DbRejectEmailDAO is created.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        audit = new MockAuditManager();
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        dao = new DbRejectEmailDAO(factory, CONNECTION_NAME, audit, ID_GENERATOR_NAME);
    }

    /**
     * Tests public constant field SEARCH_COMPANY_ID.
     */
    public void testConstantField_searchCompanyId() {
        assertEquals("The constant field SEARCH_COMPANY_ID is not set correctly.", "search_company_id",
            DbRejectEmailDAO.SEARCH_COMPANY_ID);
    }

    /**
     * Tests public constant field SEARCH_BODY.
     */
    public void testConstantField_searchBody() {
        assertEquals("The constant field SEARCH_BODY is not set correctly.", "search_body",
            DbRejectEmailDAO.SEARCH_BODY);
    }

    /**
     * Tests public constant field SEARCH_CREATED_DATE.
     */
    public void testConstantField_searchCreationDate() {
        assertEquals("The constant field SEARCH_CREATED_DATE is not set correctly.", "search_created_date",
            DbRejectEmailDAO.SEARCH_CREATED_DATE);
    }

    /**
     * Tests public constant field SEARCH_CREATED_USER.
     */
    public void testConstantField_searchCreationUser() {
        assertEquals("The constant field SEARCH_CREATED_USER is not set correctly.", "search_created_user",
            DbRejectEmailDAO.SEARCH_CREATED_USER);
    }

    /**
     * Tests public constant field SEARCH_MODIFICATION_DATE.
     */
    public void testConstantField_searchModificationDate() {
        assertEquals("The constant field SEARCH_MODIFICATION_DATE is not set correctly.", "search_modification_date",
            DbRejectEmailDAO.SEARCH_MODIFICATION_DATE);
    }

    /**
     * Tests public constant field SEARCH_MODIFICATION_USER.
     */
    public void testConstantField_searchModificationUser() {
        assertEquals("The constant field SEARCH_MODIFICATION_USER is not set correctly.", "search_modification_user",
            DbRejectEmailDAO.SEARCH_MODIFICATION_USER);
    }

    /**
     * Tests public constant field APPLICATION_AREA.
     */
    public void testConstantField_searchApplicationArea() {
        assertEquals("The constant field SEARCH_MODIFICATION_USER is not set correctly.",
            ApplicationArea.TT_CONFIGURATION, DbRejectEmailDAO.APPLICATION_AREA);
    }

    /**
     * Tests constructor accuracy.
     */
    public void testConstructor_accuracy() {
        assertNotNull("Unable to instantiate DbRejectEmailDAO.", dao);
    }

    /**
     * Tests createRejectEmail method accuracy. No audit.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_accuracy_noAudit()
        throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);

            // Assert fields are set properly
            assertNotNull("The created RejectEmail should not be null.", rejectEmail);
            assertTrue("The id is not set correctly.", rejectEmail.getId() > 0);
            assertEquals("The company id is not set correctly.", 3, rejectEmail.getCompanyId());
            assertEquals("The body is not set correctly.", "emailBody", rejectEmail.getBody());
            assertNotNull("The creation date is not set correctly.", rejectEmail.getCreationDate());
            assertNotNull("The modification date is not set correctly.", rejectEmail.getModificationDate());
            assertEquals("The creation user is not set correctly.", "lever", rejectEmail.getCreationUser());
            assertEquals("The modification user is not set correctly.", "lever", rejectEmail.getModificationUser());
            assertEquals("No audit should be done.", 0, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests createRejectEmail method accuracy. With audit.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_accuracy_withAudit()
        throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", true);

            // Assert fields are set properly
            assertNotNull("The created RejectEmail should not be null.", rejectEmail);
            assertTrue("The id is not set correctly.", rejectEmail.getId() > 0);
            assertEquals("The company id is not set correctly.", 3, rejectEmail.getCompanyId());
            assertEquals("The body is not set correctly.", "emailBody", rejectEmail.getBody());
            assertNotNull("The creation date is not set correctly.", rejectEmail.getCreationDate());
            assertNotNull("The modification date is not set correctly.", rejectEmail.getModificationDate());
            assertEquals("The creation user is not set correctly.", "lever", rejectEmail.getCreationUser());
            assertEquals("The modification user is not set correctly.", "lever", rejectEmail.getModificationUser());

            assertEquals("The operation should be audited.", 2, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests createRejectEmail method audit accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_audit() throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", true);

            List headerList = audit.getAuditHeader();
            assertEquals("The operation should be audited.", 2, headerList.size());

            Map headers = new HashMap();
            headers.put(((AuditHeader) headerList.get(0)).getTableName(), headerList.get(0));
            headers.put(((AuditHeader) headerList.get(1)).getTableName(), headerList.get(1));

            // Check audit for reject_email table
            AuditHeader header1 = (AuditHeader) headers.get("reject_email");
            assertNotNull("The audit header for reject_email table should not be null.", header1);
            assertEquals("The action type is not audited correctly.", AuditType.INSERT, header1.getActionType());
            assertEquals("The entity id is not audited correctly.", rejectEmail.getId(), header1.getEntityId());
            assertEquals("The company id is not audited correctly.", rejectEmail.getCompanyId(),
                header1.getCompanyId());
            assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header1.getApplicationArea());
            assertEquals("The creation date is not set correctly.", rejectEmail.getCreationDate(),
                header1.getCreationDate());
            assertEquals("The creation user is not audit correctly.", "lever", header1.getCreationUser());
            assertEquals("The audit details is not audit correctly.", 6, header1.getDetails().length);

            // Check audit for comp_rej_email table
            AuditHeader header2 = (AuditHeader) headers.get("comp_rej_email");
            assertNotNull("The audit header for comp_rej_email table should not be null.", header2);
            assertEquals("The action type is not audited correctly.", AuditType.INSERT, header2.getActionType());
            assertEquals("The entity id is not audited correctly.", rejectEmail.getId(), header2.getEntityId());
            assertEquals("The company id is not audited correctly.", rejectEmail.getCompanyId(),
                header2.getCompanyId());
            assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header2.getApplicationArea());
            assertEquals("The creation date is not set correctly.", rejectEmail.getCreationDate(),
                header2.getCreationDate());
            assertEquals("The creation user is not audit correctly.", "lever", header2.getCreationUser());
            assertEquals("The audit details is not audit correctly.", 6, header2.getDetails().length);
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests retrieveRejectEmail methods with an id not in persistence. Null should be return.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectEmail_notPersisted()
        throws Exception {
        assertNull("Null should be returned.", dao.retrieveRejectEmail(999));
    }

    /**
     * Tests retrieveRejectEmail method accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectEmail_accuracy() throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            // Persist and retrieve
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);

            RejectEmail rejectEmail2 = dao.retrieveRejectEmail(rejectEmail.getId());

            // Assert fields are set properly
            assertNotNull("The retrieved RejectEmail should not be null.", rejectEmail2);
            assertEquals("The id is not correct.", rejectEmail.getId(), rejectEmail2.getId());
            assertEquals("The company id is not correct.", rejectEmail.getCompanyId(), rejectEmail2.getCompanyId());
            assertEquals("The body is not correct.", rejectEmail.getBody(), rejectEmail2.getBody());
            assertEquals("The creation date is not correct.", FORMAT.format(rejectEmail.getCreationDate()),
                FORMAT.format(rejectEmail2.getCreationDate()));
            assertEquals("The creation user is not correct.", rejectEmail.getCreationUser(),
                rejectEmail2.getCreationUser());
            assertEquals("The modification date is not correct.", FORMAT.format(rejectEmail.getModificationDate()),
                FORMAT.format(rejectEmail2.getModificationDate()));
            assertEquals("The modification user is not correct.", rejectEmail.getModificationUser(),
                rejectEmail2.getModificationUser());
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests updateRejectEmail method accuracy. No audit.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_accuracy_noAudit()
        throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);

            // Update and retrieve
            rejectEmail.setBody("newBody");
            dao.updateRejectEmail(rejectEmail, "momo", false);

            RejectEmail rejectEmail2 = dao.retrieveRejectEmail(rejectEmail.getId());

            // Assert fields are set properly
            assertEquals("The id is not correct.", rejectEmail.getId(), rejectEmail2.getId());
            assertEquals("The company id is not correct.", rejectEmail.getCompanyId(), rejectEmail2.getCompanyId());
            assertEquals("The body is not updated correctly.", "newBody", rejectEmail2.getBody());
            assertEquals("The creation date is not correct.", FORMAT.format(rejectEmail.getCreationDate()),
                FORMAT.format(rejectEmail2.getCreationDate()));
            assertEquals("The creation user is not correct.", rejectEmail.getCreationUser(),
                rejectEmail2.getCreationUser());
            assertTrue("The modification date is not correct.",
                rejectEmail2.getModificationDate().compareTo(rejectEmail2.getCreationDate()) >= 0);
            assertEquals("The modification user is not updated correctly.", "momo", rejectEmail2.getModificationUser());
            assertEquals("No audit should be done.", 0, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests updateRejectEmail method accuracy. With audit.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_accuracy_withAudit()
        throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);

            // Update and retrieve
            rejectEmail.setBody("newBody");
            dao.updateRejectEmail(rejectEmail, "momo", true);

            RejectEmail rejectEmail2 = dao.retrieveRejectEmail(rejectEmail.getId());

            // Assert fields are set properly
            assertEquals("The id is not correct.", rejectEmail.getId(), rejectEmail2.getId());
            assertEquals("The company id is not correct.", rejectEmail.getCompanyId(), rejectEmail2.getCompanyId());
            assertEquals("The body is not updated correctly.", "newBody", rejectEmail2.getBody());
            assertEquals("The creation date is not correct.", FORMAT.format(rejectEmail.getCreationDate()),
                FORMAT.format(rejectEmail2.getCreationDate()));
            assertEquals("The creation user is not correct.", rejectEmail.getCreationUser(),
                rejectEmail2.getCreationUser());
            assertTrue("The modification date is not correct.",
                rejectEmail2.getModificationDate().compareTo(rejectEmail2.getCreationDate()) >= 0);
            assertEquals("The modification user is not updated correctly.", "momo", rejectEmail2.getModificationUser());
            assertEquals("The operation should be audited.", 1, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests updateRejectEmail method audit accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_audit() throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);

            // Update and retrieve
            rejectEmail.setBody("newBody");
            dao.updateRejectEmail(rejectEmail, "momo", true);

            List headerList = audit.getAuditHeader();
            assertEquals("The operation should be audited.", 1, headerList.size());

            // Check audit for reject_email table
            AuditHeader header = (AuditHeader) headerList.get(0);
            assertNotNull("The audit header for reject_email table should not be null.", header);
            assertEquals("The action type is not audited correctly.", AuditType.UPDATE, header.getActionType());
            assertEquals("The table name is not audited correctly.", "reject_email", header.getTableName());
            assertEquals("The entity id is not audited correctly.", rejectEmail.getId(), header.getEntityId());
            assertEquals("The company id is not audited correctly.", rejectEmail.getCompanyId(), header.getCompanyId());
            assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
                header.getApplicationArea());
            assertEquals("The creation date is not set correctly.", rejectEmail.getModificationDate(),
                header.getCreationDate());
            assertEquals("The creation user is not audit correctly.", "momo", header.getCreationUser());
            assertEquals("The audit details is not audit correctly.", 3, header.getDetails().length);
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }
    }

    /**
     * Tests deleteRejectEmail method accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_accuracy_noAudit()
        throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        // Persist the reject email and then delete it
        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", false);
        }

        assertNull("The reject email should have been deleted.", dao.retrieveRejectEmail(rejectEmail.getId()));
    }

    /**
     * Tests deleteRejectEmail method audit accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_audit() throws Exception {
        // Create a RejectEmail to persist
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setCompanyId(3);
        rejectEmail.setBody("emailBody");

        // Persist the reject email and then delete it
        try {
            rejectEmail = dao.createRejectEmail(rejectEmail, "lever", false);
        } finally {
            dao.deleteRejectEmail(rejectEmail, "user", true);
        }

        assertNull("The reject email should have been deleted.", dao.retrieveRejectEmail(rejectEmail.getId()));

        List headerList = audit.getAuditHeader();
        assertEquals("The operation should be audited.", 2, headerList.size());

        Map headers = new HashMap();
        headers.put(((AuditHeader) headerList.get(0)).getTableName(), headerList.get(0));
        headers.put(((AuditHeader) headerList.get(1)).getTableName(), headerList.get(1));

        // Check audit for reject_email table
        AuditHeader header1 = (AuditHeader) headers.get("reject_email");
        assertNotNull("The audit header for reject_email table should not be null.", header1);
        assertEquals("The action type is not audited correctly.", AuditType.DELETE, header1.getActionType());
        assertEquals("The entity id is not audited correctly.", rejectEmail.getId(), header1.getEntityId());
        assertEquals("The company id is not audited correctly.", rejectEmail.getCompanyId(), header1.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
            header1.getApplicationArea());
        assertEquals("The creation date is not set correctly.", FORMAT.format(rejectEmail.getCreationDate()),
            FORMAT.format(header1.getCreationDate()));
        assertEquals("The creation user is not audit correctly.", "user", header1.getCreationUser());
        assertEquals("The audit details is not audit correctly.", 6, header1.getDetails().length);

        // Check audit for comp_rej_email table
        AuditHeader header2 = (AuditHeader) headers.get("comp_rej_email");
        assertNotNull("The audit header for comp_rej_email table should not be null.", header2);
        assertEquals("The action type is not audited correctly.", AuditType.DELETE, header2.getActionType());
        assertEquals("The entity id is not audited correctly.", rejectEmail.getId(), header2.getEntityId());
        assertEquals("The company id is not audited correctly.", rejectEmail.getCompanyId(), header2.getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectEmailDAO.APPLICATION_AREA,
            header2.getApplicationArea());
        assertEquals("The creation date is not set correctly.", FORMAT.format(rejectEmail.getCreationDate()),
            FORMAT.format(header2.getCreationDate()));
        assertEquals("The creation user is not audit correctly.", "user", header2.getCreationUser());
        assertEquals("The audit details is not audit correctly.", 6, header2.getDetails().length);
    }

    /**
     * Tests listRejectEmails methods when no RejectEmail in persistence. Empty array should be returned.
     *
     * @throws Exception pass to JUnit.
     */
    public void testListRejectEmails_notPersisted() throws Exception {
        assertEquals("Empty array should be returned.", 0, dao.listRejectEmails().length);
    }

    /**
     * Tests listRejectEmails method accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testListRejectEmails_accuracy() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            // Persist email1 and retrieve
            email1 = dao.createRejectEmail(email1, "user1", false);

            RejectEmail[] emails = dao.listRejectEmails();

            assertEquals("There should be 1 RejectEmail in persistence now.", 1, emails.length);
            assertEquals("The id is not correct.", email1.getId(), emails[0].getId());
            assertEquals("The company id is not correct.", email1.getCompanyId(), emails[0].getCompanyId());
            assertEquals("The body is not correct.", email1.getBody(), emails[0].getBody());
            assertEquals("The creation date is not correct.", FORMAT.format(email1.getCreationDate()),
                FORMAT.format(emails[0].getCreationDate()));
            assertEquals("The creation user is not correct.", email1.getCreationUser(), emails[0].getCreationUser());
            assertEquals("The modification date is not correct.", FORMAT.format(email1.getModificationDate()),
                FORMAT.format(emails[0].getModificationDate()));
            assertEquals("The modification user is not correct.", email1.getModificationUser(),
                emails[0].getModificationUser());

            // Persist another reject email
            dao.createRejectEmail(email2, "momo", false);
            emails = dao.listRejectEmails();
            assertEquals("There should be 2 RejectEmail in persistence now.", 2, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }

    /**
     * Tests searchRejectEmails methods when no RejectEmail in persistence. Empty array should be returned.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_notPersisted() throws Exception {
        Filter filter = new RejectEmailSearchBuilder().hasCompanyIdFilter(1);
        assertEquals("Empty array should be returned.", 0, dao.searchRejectEmails(filter).length);
    }

    /**
     * Tests searchRejectEmails by company id.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_byCompanyId() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            email1 = dao.createRejectEmail(email1, "user1", false);
            email2 = dao.createRejectEmail(email2, "user2", false);

            // Search RejectEmail with company id equals 1
            Filter filter = new RejectEmailSearchBuilder().hasCompanyIdFilter(1);
            RejectEmail[] emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);

            // Search RejectEmail with company id equals 4
            filter = new RejectEmailSearchBuilder().hasCompanyIdFilter(4);
            emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }

    /**
     * Tests searchRejectEmails by body.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_byBody() throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            email1 = dao.createRejectEmail(email1, "user1", false);
            email2 = dao.createRejectEmail(email2, "user2", false);

            // Search RejectEmail with body 'email1'
            Filter filter = new RejectEmailSearchBuilder().hasBodyFilter("email1");
            RejectEmail[] emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);

            // Search RejectEmail with body 'email2'
            filter = new RejectEmailSearchBuilder().hasBodyFilter("email2");
            assertEquals("The number of RejectEmails returned is not correct.", 1, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }

    /**
     * Tests searchRejectEmails by creation user.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_byCreationUser()
        throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            email1 = dao.createRejectEmail(email1, "user", false);
            email2 = dao.createRejectEmail(email2, "user", false);

            // Search RejectEmail with creation user 'user'
            Filter filter = new RejectEmailSearchBuilder().createdByUserFilter("user");
            RejectEmail[] emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }

    /**
     * Tests searchRejectEmails by modification user.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_byModificationUser()
        throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            email1 = dao.createRejectEmail(email1, "user", false);
            email2 = dao.createRejectEmail(email2, "user", false);

            // Search RejectEmail with modification user 'user'
            Filter filter = new RejectEmailSearchBuilder().modifiedByUserFilter("user");
            RejectEmail[] emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }

    /**
     * Tests searchRejectEmails by creation date.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_byCreationDate()
        throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            email1 = dao.createRejectEmail(email1, "user", false);
            email2 = dao.createRejectEmail(email2, "user", false);

            // Search RejectEmail whose creation date within certain datetime range
            Date now = new Date(new java.util.Date().getTime());
            Filter filter = new RejectEmailSearchBuilder().createdWithinDateRangeFilter(new Date(now.getTime()
                - 1000000), new Date(now.getTime() + 1000000));
            RejectEmail[] emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }

    /**
     * Tests searchRejectEmails by modification date.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_byModificationDate()
        throws Exception {
        // Create two RejectEmailsto persist
        RejectEmail email1 = new RejectEmail();
        email1.setCompanyId(1);
        email1.setBody("email1");

        RejectEmail email2 = new RejectEmail();
        email2.setCompanyId(4);
        email2.setBody("email2");

        try {
            email1 = dao.createRejectEmail(email1, "user", false);
            email2 = dao.createRejectEmail(email2, "user", false);

            // Search RejectEmail whose modification date within certain datetime range
            Date now = new Date(new java.util.Date().getTime());
            Filter filter = new RejectEmailSearchBuilder().modifiedWithinDateRangeFilter(new Date(now.getTime()
                - 1000000), new Date(now.getTime() + 1000000));
            RejectEmail[] emails = dao.searchRejectEmails(filter);
            assertEquals("The number of RejectEmails returned is not correct.", 2, emails.length);
        } finally {
            dao.deleteRejectEmail(email1, "user", false);
            dao.deleteRejectEmail(email2, "user", false);
        }
    }
}
