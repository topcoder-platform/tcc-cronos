/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonSearchBuilder;
import com.topcoder.timetracker.rejectreason.TestHelper;

/**
 * Accuracy tests for <code>DbRejectReasonDAO</code> class. Instantiation tests and database CRUDE tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbRejectReasonDAOAccuracyTests extends TestHelper {
    /** A date formatter for date comparison. */
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /** The audit manager to create the DAO object. */
    private MockAuditManager audit = null;

    /** The DbRejectReasonDAO instance to be tested. */
    private DbRejectReasonDAO dao = null;

    /**
     * Sets up test environment. Configuration is loaded and new instance of DbRejectReasonDAO is created.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        audit = new MockAuditManager();
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        dao = new DbRejectReasonDAO(factory, CONNECTION_NAME, audit, ID_GENERATOR_NAME);
    }

    /**
     * Tests public constant field SEARCH_COMPANY_ID.
     */
    public void testConstantField_searchCompanyId() {
        assertEquals("The constant field SEARCH_COMPANY_ID is not set correctly.", "search_company_id",
                DbRejectReasonDAO.SEARCH_COMPANY_ID);
    }

    /**
     * Tests public constant field SEARCH_DESCRIPTION.
     */
    public void testConstantField_searchDescription() {
        assertEquals("The constant field SEARCH_DESCRIPTION is not set correctly.", "search_description",
                DbRejectReasonDAO.SEARCH_DESCRIPTION);
    }

    /**
     * Tests public constant field SEARCH_ACTIVE_STATUS.
     */
    public void testConstantField_searchActiveStatus() {
        assertEquals("The constant field SEARCH_ACTIVE_STATUS is not set correctly.", "search_active_status",
                DbRejectReasonDAO.SEARCH_ACTIVE_STATUS);
    }

    /**
     * Tests public constant field SEARCH_CREATED_DATE.
     */
    public void testConstantField_searchCreationDate() {
        assertEquals("The constant field SEARCH_CREATED_DATE is not set correctly.", "search_created_date",
                DbRejectReasonDAO.SEARCH_CREATED_DATE);
    }

    /**
     * Tests public constant field SEARCH_CREATED_USER.
     */
    public void testConstantField_searchCreationUser() {
        assertEquals("The constant field SEARCH_CREATED_USER is not set correctly.", "search_created_user",
                DbRejectReasonDAO.SEARCH_CREATED_USER);
    }

    /**
     * Tests public constant field SEARCH_MODIFICATION_DATE.
     */
    public void testConstantField_searchModificationDate() {
        assertEquals("The constant field SEARCH_MODIFICATION_DATE is not set correctly.",
                "search_modification_date", DbRejectReasonDAO.SEARCH_MODIFICATION_DATE);
    }

    /**
     * Tests public constant field SEARCH_MODIFICATION_USER.
     */
    public void testConstantField_searchModificationUser() {
        assertEquals("The constant field SEARCH_MODIFICATION_USER is not set correctly.",
                "search_modification_user", DbRejectReasonDAO.SEARCH_MODIFICATION_USER);
    }

    /**
     * Tests public constant field APPLICATION_AREA.
     */
    public void testConstantField_searchApplicationArea() {
        assertEquals("The constant field SEARCH_MODIFICATION_USER is not set correctly.",
                ApplicationArea.TT_CONFIGURATION, DbRejectReasonDAO.APPLICATION_AREA);
    }

    /**
     * Tests constructor accuracy.
     */
    public void testConstructor_accuracy() {
        assertNotNull("Unable to instantiate DbRejectReasonDAO.", dao);
    }

    /**
     * Tests createRejectReason method accuracy. No audit.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateRejectReason_accuracy_noAudit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);

            // Assert fields are set properly
            assertNotNull("The created RejectReason should not be null.", rejectReason);
            assertTrue("The id is not set correctly.", rejectReason.getId() > 0);
            assertEquals("The company id is not set correctly.", 3, rejectReason.getCompanyId());
            assertEquals("The description is not set correctly.", "description", rejectReason
                    .getDescription());
            assertEquals("The active status is not set correctly.", true, rejectReason.getActive());
            assertNotNull("The creation date is not set correctly.", rejectReason.getCreationDate());
            assertNotNull("The modification date is not set correctly.", rejectReason.getModificationDate());
            assertEquals("The creation user is not set correctly.", "lever", rejectReason.getCreationUser());
            assertEquals("The modification user is not set correctly.", "lever", rejectReason
                    .getModificationUser());
            assertEquals("No audit should be done.", 0, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests createRejectReason method accuracy. With audit.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateRejectReason_accuracy_withAudit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", true);

            // Assert fields are set properly
            assertNotNull("The created RejectReason should not be null.", rejectReason);
            assertTrue("The id is not set correctly.", rejectReason.getId() > 0);
            assertEquals("The company id is not set correctly.", 3, rejectReason.getCompanyId());
            assertEquals("The description is not set correctly.", "description", rejectReason
                    .getDescription());
            assertEquals("The active status is not set correctly.", true, rejectReason.getActive());
            assertNotNull("The creation date is not set correctly.", rejectReason.getCreationDate());
            assertNotNull("The modification date is not set correctly.", rejectReason.getModificationDate());
            assertEquals("The creation user is not set correctly.", "lever", rejectReason.getCreationUser());
            assertEquals("The modification user is not set correctly.", "lever", rejectReason
                    .getModificationUser());

            assertEquals("The operation should be audited.", 2, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests createRejectReason method audit accuracy.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateRejectReason_audit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", true);

            List headerList = audit.getAuditHeader();
            assertEquals("The operation should be audited.", 2, headerList.size());

            Map headers = new HashMap();
            headers.put(((AuditHeader) headerList.get(0)).getTableName(), headerList.get(0));
            headers.put(((AuditHeader) headerList.get(1)).getTableName(), headerList.get(1));

            // Check audit for reject_reason table
            AuditHeader header1 = (AuditHeader) headers.get("reject_reason");
            assertNotNull("The audit header for reject_reason table should not be null.", header1);
            assertEquals("The action type is not audited correctly.", AuditType.INSERT, header1
                    .getActionType());
            assertEquals("The entity id is not audited correctly.", rejectReason.getId(), header1
                    .getEntityId());
            assertEquals("The company id is not audited correctly.", rejectReason.getCompanyId(), header1
                    .getCompanyId());
            assertEquals("The application area is not audited correctly.",
                    DbRejectReasonDAO.APPLICATION_AREA, header1.getApplicationArea());
            assertEquals("The creation date is not set correctly.", rejectReason.getCreationDate(), header1
                    .getCreationDate());
            assertEquals("The creation user is not audit correctly.", "lever", header1.getCreationUser());
            assertEquals("The audit details is not audit correctly.", 7, header1.getDetails().length);

            // Check audit for comp_rej_reason table
            AuditHeader header2 = (AuditHeader) headers.get("comp_rej_reason");
            assertNotNull("The audit header for comp_rej_reason table should not be null.", header2);
            assertEquals("The action type is not audited correctly.", AuditType.INSERT, header2
                    .getActionType());
            assertEquals("The entity id is not audited correctly.", rejectReason.getId(), header2
                    .getEntityId());
            assertEquals("The company id is not audited correctly.", rejectReason.getCompanyId(), header2
                    .getCompanyId());
            assertEquals("The application area is not audited correctly.",
                    DbRejectReasonDAO.APPLICATION_AREA, header2.getApplicationArea());
            assertEquals("The creation date is not set correctly.", rejectReason.getCreationDate(), header2
                    .getCreationDate());
            assertEquals("The creation user is not audit correctly.", "lever", header2.getCreationUser());
            assertEquals("The audit details is not audit correctly.", 6, header2.getDetails().length);
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests retrieveRejectReason methods with an id not in persistence. Null should be return.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRetrieveRejectReason_notPersisted() throws Exception {
        assertNull("Null should be returned.", dao.retrieveRejectReason(999));
    }

    /**
     * Tests retrieveRejectReason method accuracy.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRetrieveRejectReason_accuracy() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            // Persist and retrieve
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);

            RejectReason rejectReason2 = dao.retrieveRejectReason(rejectReason.getId());

            // Assert fields are set properly
            assertNotNull("The retrieved RejectReason should not be null.", rejectReason2);
            assertEquals("The id is not correct.", rejectReason.getId(), rejectReason2.getId());
            assertEquals("The company id is not correct.", rejectReason.getCompanyId(), rejectReason2
                    .getCompanyId());
            assertEquals("The description is not correct.", rejectReason.getDescription(), rejectReason2
                    .getDescription());
            assertEquals("The active status is not correct.", rejectReason.getActive(), rejectReason2
                    .getActive());
            assertEquals("The creation date is not correct.", FORMAT.format(rejectReason.getCreationDate()),
                    FORMAT.format(rejectReason2.getCreationDate()));
            assertEquals("The creation user is not correct.", rejectReason.getCreationUser(), rejectReason2
                    .getCreationUser());
            assertEquals("The modification date is not correct.", FORMAT.format(rejectReason
                    .getModificationDate()), FORMAT.format(rejectReason2.getModificationDate()));
            assertEquals("The modification user is not correct.", rejectReason.getModificationUser(),
                    rejectReason2.getModificationUser());
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests updateRejectReason method accuracy. No audit.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateRejectReason_accuracy_noAudit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);

            // Update and retrieve
            rejectReason.setDescription("another description");
            dao.updateRejectReason(rejectReason, "momo", false);

            RejectReason rejectReason2 = dao.retrieveRejectReason(rejectReason.getId());

            // Assert fields are set properly
            assertEquals("The id is not correct.", rejectReason.getId(), rejectReason2.getId());
            assertEquals("The company id is not correct.", rejectReason.getCompanyId(), rejectReason2
                    .getCompanyId());
            assertEquals("The description is not updated correctly.", "another description", rejectReason2
                    .getDescription());
            assertEquals("The active status is not correct.", true, rejectReason2.getActive());
            assertEquals("The creation date is not correct.", FORMAT.format(rejectReason.getCreationDate()),
                    FORMAT.format(rejectReason2.getCreationDate()));
            assertEquals("The creation user is not correct.", rejectReason.getCreationUser(), rejectReason2
                    .getCreationUser());
            assertTrue("The modification date is not correct.", rejectReason2.getModificationDate()
                    .compareTo(rejectReason2.getCreationDate()) >= 0);
            assertEquals("The modification user is not updated correctly.", "momo", rejectReason2
                    .getModificationUser());
            assertEquals("No audit should be done.", 0, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests updateRejectReason method accuracy. With audit.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateRejectReason_accuracy_withAudit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);

            // Update and retrieve
            rejectReason.setDescription("another description");
            dao.updateRejectReason(rejectReason, "momo", true);

            RejectReason rejectReason2 = dao.retrieveRejectReason(rejectReason.getId());

            // Assert fields are set properly
            assertEquals("The id is not correct.", rejectReason.getId(), rejectReason2.getId());
            assertEquals("The company id is not correct.", rejectReason.getCompanyId(), rejectReason2
                    .getCompanyId());
            assertEquals("The description is not updated correctly.", "another description", rejectReason2
                    .getDescription());
            assertEquals("The active status is not correct.", true, rejectReason2.getActive());
            assertEquals("The creation date is not correct.", FORMAT.format(rejectReason.getCreationDate()),
                    FORMAT.format(rejectReason2.getCreationDate()));
            assertEquals("The creation user is not correct.", rejectReason.getCreationUser(), rejectReason2
                    .getCreationUser());
            assertTrue("The modification date is not correct.", rejectReason2.getModificationDate()
                    .compareTo(rejectReason2.getCreationDate()) >= 0);
            assertEquals("The modification user is not updated correctly.", "momo", rejectReason2
                    .getModificationUser());
            assertEquals("The operation should be audited.", 1, audit.getAuditHeader().size());
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests updateRejectReason method audit accuracy.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateRejectReason_audit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);

            // Update and retrieve
            rejectReason.setDescription("description2");
            dao.updateRejectReason(rejectReason, "momo", true);

            List headerList = audit.getAuditHeader();
            assertEquals("The operation should be audited.", 1, headerList.size());

            // Check audit for reject_reason table
            AuditHeader header = (AuditHeader) headerList.get(0);
            assertNotNull("The audit header for reject_reason table should not be null.", header);
            assertEquals("The action type is not audited correctly.", AuditType.UPDATE, header
                    .getActionType());
            assertEquals("The table name is not audited correctly.", "reject_reason", header.getTableName());
            assertEquals("The entity id is not audited correctly.", rejectReason.getId(), header
                    .getEntityId());
            assertEquals("The company id is not audited correctly.", rejectReason.getCompanyId(), header
                    .getCompanyId());
            assertEquals("The application area is not audited correctly.",
                    DbRejectReasonDAO.APPLICATION_AREA, header.getApplicationArea());
            assertEquals("The creation date is not set correctly.", rejectReason.getModificationDate(),
                    header.getCreationDate());
            assertEquals("The creation user is not audit correctly.", "momo", header.getCreationUser());
            assertEquals("The audit details is not audit correctly.", 3, header.getDetails().length);

            // Modify two fields and update
            rejectReason.setDescription("description3");
            rejectReason.setActive(false);
            dao.updateRejectReason(rejectReason, "user", true);
            header = (AuditHeader) headerList.get(1);
            assertEquals("The audit details is not audit correctly.", 4, header.getDetails().length);
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }
    }

    /**
     * Tests deleteRejectReason method accuracy.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testDeleteRejectReason_accuracy_noAudit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        // Persist the reject Reason and then delete it
        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);
        } finally {
            dao.deleteRejectReason(rejectReason, "user", false);
        }

        assertNull("The reject reason should have been deleted.", dao.retrieveRejectReason(rejectReason
                .getId()));
    }

    /**
     * Tests deleteRejectReason method audit accuracy.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testDeleteRejectReason_audit() throws Exception {
        // Create a RejectReason to persist
        RejectReason rejectReason = new RejectReason();
        rejectReason.setCompanyId(3);
        rejectReason.setDescription("description");
        rejectReason.setActive(true);

        // Persist the reject Reason and then delete it
        try {
            rejectReason = dao.createRejectReason(rejectReason, "lever", false);
        } finally {
            dao.deleteRejectReason(rejectReason, "user", true);
        }

        assertNull("The reject reason should have been deleted.", dao.retrieveRejectReason(rejectReason
                .getId()));

        List headerList = audit.getAuditHeader();
        assertEquals("The operation should be audited.", 2, headerList.size());

        Map headers = new HashMap();
        headers.put(((AuditHeader) headerList.get(0)).getTableName(), headerList.get(0));
        headers.put(((AuditHeader) headerList.get(1)).getTableName(), headerList.get(1));

        // Check audit for reject_reason table
        AuditHeader header1 = (AuditHeader) headers.get("reject_reason");
        assertNotNull("The audit header for reject_reason table should not be null.", header1);
        assertEquals("The action type is not audited correctly.", AuditType.DELETE, header1.getActionType());
        assertEquals("The entity id is not audited correctly.", rejectReason.getId(), header1.getEntityId());
        assertEquals("The company id is not audited correctly.", rejectReason.getCompanyId(), header1
                .getCompanyId());
        assertEquals("The creation date is not set correctly.",
                FORMAT.format(rejectReason.getCreationDate()), FORMAT.format(header1.getCreationDate()));
        assertEquals("The creation user is not audit correctly.", "user", header1.getCreationUser());
        assertEquals("The audit details is not audit correctly.", 7, header1.getDetails().length);

        // Check audit for comp_rej_reason table
        AuditHeader header2 = (AuditHeader) headers.get("comp_rej_reason");
        assertNotNull("The audit header for comp_rej_reason table should not be null.", header2);
        assertEquals("The action type is not audited correctly.", AuditType.DELETE, header2.getActionType());
        assertEquals("The entity id is not audited correctly.", rejectReason.getId(), header2.getEntityId());
        assertEquals("The company id is not audited correctly.", rejectReason.getCompanyId(), header2
                .getCompanyId());
        assertEquals("The application area is not audited correctly.", DbRejectReasonDAO.APPLICATION_AREA,
                header2.getApplicationArea());
        assertEquals("The creation date is not set correctly.",
                FORMAT.format(rejectReason.getCreationDate()), FORMAT.format(header2.getCreationDate()));
        assertEquals("The creation user is not audit correctly.", "user", header2.getCreationUser());
        assertEquals("The audit details is not audit correctly.", 6, header2.getDetails().length);
    }

    /**
     * Tests listRejectReasons methods when no RejectReason in persistence. Empty array should be returned.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testListRejectReasons_notPersisted() throws Exception {
        assertEquals("Empty array should be returned.", 0, dao.listRejectReasons().length);
    }

    /**
     * Tests listRejectReasons method accuracy.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testListRejectReasons_accuracy() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            // Persist reason1 and retrieve
            reason1 = dao.createRejectReason(reason1, "user1", false);

            RejectReason[] reasons = dao.listRejectReasons();

            assertEquals("There should be 1 RejectReason in persistence now.", 1, reasons.length);
            assertEquals("The id is not correct.", reason1.getId(), reasons[0].getId());
            assertEquals("The company id is not correct.", reason1.getCompanyId(), reasons[0].getCompanyId());
            assertEquals("The description is not correct.", reason1.getDescription(), reasons[0]
                    .getDescription());
            assertEquals("The active status is not correct.", reason1.getActive(), reasons[0].getActive());
            assertEquals("The creation date is not correct.", FORMAT.format(reason1.getCreationDate()),
                    FORMAT.format(reasons[0].getCreationDate()));
            assertEquals("The creation user is not correct.", reason1.getCreationUser(), reasons[0]
                    .getCreationUser());
            assertEquals("The modification date is not correct.", FORMAT
                    .format(reason1.getModificationDate()), FORMAT.format(reasons[0].getModificationDate()));
            assertEquals("The modification user is not correct.", reason1.getModificationUser(), reasons[0]
                    .getModificationUser());

            // Persist another reject reason
            dao.createRejectReason(reason2, "momo", false);
            reasons = dao.listRejectReasons();
            assertEquals("There should be 2 RejectReason in persistence now.", 2, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons methods when no RejectReason in persistence. Empty array should be returned.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_notPersisted() throws Exception {
        Filter filter = new RejectReasonSearchBuilder().hasCompanyIdFilter(1);
        assertEquals("Empty array should be returned.", 0, dao.searchRejectReasons(filter).length);
    }

    /**
     * Tests searchRejectReasons by company id.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byCompanyId() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            reason1 = dao.createRejectReason(reason1, "user1", false);
            reason2 = dao.createRejectReason(reason2, "user2", false);

            // Search RejectReason with company id equals 1
            Filter filter = new RejectReasonSearchBuilder().hasCompanyIdFilter(1);
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);

            // Search RejectReason with company id equals 4
            filter = new RejectReasonSearchBuilder().hasCompanyIdFilter(4);
            reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons by description.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byDescription() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            reason1 = dao.createRejectReason(reason1, "user1", false);
            reason2 = dao.createRejectReason(reason2, "user2", false);

            // Search RejectReason with description 'desctiption1'
            Filter filter = new RejectReasonSearchBuilder().hasDescriptionFilter("description1");
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);

            // Search RejectReason with description 'description2'
            filter = new RejectReasonSearchBuilder().hasDescriptionFilter("description2");
            assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons by active status.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byActiveStatus() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");
        reason1.setActive(false);

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");
        reason2.setActive(true);

        try {
            reason1 = dao.createRejectReason(reason1, "user1", false);
            reason2 = dao.createRejectReason(reason2, "user2", false);

            // Search RejectReason with active status true
            Filter filter = new RejectReasonSearchBuilder().hasActiveStatusFilter(1);
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);

            // Search RejectReason with active status false
            filter = new RejectReasonSearchBuilder().hasActiveStatusFilter(0);
            assertEquals("The number of RejectReasons returned is not correct.", 1, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons by creation user.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byCreationUser() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            reason1 = dao.createRejectReason(reason1, "user", false);
            reason2 = dao.createRejectReason(reason2, "user", false);

            // Search RejectReason with creation user 'user'
            Filter filter = new RejectReasonSearchBuilder().createdByUserFilter("user");
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 2, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons by modification user.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byModificationUser() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            reason1 = dao.createRejectReason(reason1, "user", false);
            reason2 = dao.createRejectReason(reason2, "user", false);

            // Search RejectReason with modification user 'user'
            Filter filter = new RejectReasonSearchBuilder().modifiedByUserFilter("user");
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 2, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons by creation date.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byCreationDate() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            reason1 = dao.createRejectReason(reason1, "user", false);
            reason2 = dao.createRejectReason(reason2, "user", false);

            // Search RejectReason whose creation date within certain datetime range
            Filter filter = new RejectReasonSearchBuilder().createdWithinDateRangeFilter(new Timestamp(System
                    .currentTimeMillis() - 10000), new Timestamp(System.currentTimeMillis() + 10000));
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 2, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }

    /**
     * Tests searchRejectReasons by modification date.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchRejectReasons_byModificationDate() throws Exception {
        // Create two RejectReasonsto persist
        RejectReason reason1 = new RejectReason();
        reason1.setCompanyId(1);
        reason1.setDescription("description1");

        RejectReason reason2 = new RejectReason();
        reason2.setCompanyId(4);
        reason2.setDescription("description2");

        try {
            reason1 = dao.createRejectReason(reason1, "user", false);
            reason2 = dao.createRejectReason(reason2, "user", false);

            // Search RejectReason whose modification date within certain datetime range
            Filter filter = new RejectReasonSearchBuilder().createdWithinDateRangeFilter(new Timestamp(System
                    .currentTimeMillis() - 10000), new Timestamp(System.currentTimeMillis() + 10000));
            RejectReason[] reasons = dao.searchRejectReasons(filter);
            assertEquals("The number of RejectReasons returned is not correct.", 2, reasons.length);
        } finally {
            dao.deleteRejectReason(reason1, "user", false);
            dao.deleteRejectReason(reason2, "user", false);
        }
    }
}
