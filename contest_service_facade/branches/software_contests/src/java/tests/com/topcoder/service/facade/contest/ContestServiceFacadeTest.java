/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import junit.framework.TestCase;
import junit.framework.Assert;
import com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.contest.MimeType;

import javax.xml.ws.Service;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.HashMap;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.io.FileInputStream;

import org.jboss.ws.core.StubExt;

/**
 * <p>A unit test case for <code>TopCoder Service Facade</code> web service.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestServiceFacadeTest extends TestCase {

    /**
     * <p>A <code>String</code> providing the handle for user granted <code>Cockpit User</code> role.</p>
     */
    public static final String USER_HANDLE = "heffan";

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit User</code> role.</p>
     */
    public static final long USER_ID = 132456L;

    /**
     * <p>A <code>String</code> providing the handle for user granted <code>Cockpit Administrator</code> role.</p>
     */
    public static final String ADMIN_HANDLE = "user";

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit Administrator</code> role.</p>
     */
    public static final long ADMIN_ID = 132458L;

    /**
     * <p>A <code>Connection</code> providing connection to target database.</p>
     */
    private Connection connection = null;

    /**
     * <p>A <code>String</code> providing the URL for <code>WSDL</code> for tested web service.</p>
     */
    private String wsdlUrl = null;

    /**
     * <p>A <code>List</code> providing the list of existing projects for user granted <code>Cockpit User</code> role.
     * </p>
     */
    private List<ProjectData> userProjects = null;

    /**
     * <p>A <code>List</code> providing the list of existing projects for user granted
     * <code>Cockpit Administrator</code> role.</p>
     */
    private List<ProjectData> adminProjects = null;

    /**
     * <p>A <code>List</code> providing the list of existing contests for user granted <code>Cockpit User</code> role.
     * </p>
     */
    private List<Competition> userContests = null;

    /**
     * <p>A <code>List</code> providing the list of existing contests for user granted
     * <code>Cockpit Administrator</code> role.</p>
     */
    private List<Competition> adminContests = null;

    /**
     * <p>A <code>List</code> collecting the SQL commands to be used in {@link #tearDown()} to restore the data affected
     * by tests.</p>
     */
    private List<String> cleanupSQL = null;

    /**
     * <p>Constructs new <code>TopCoderServiceFacadeTest</code> instance with specified test name.</p>
     *
     * @param name a <code>String</code> providing the name of the test.
     */
    public ContestServiceFacadeTest(String name) {
        setName(name);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void setUp() throws Exception {
        super.setUp();
        Properties props = new Properties();
        props.load(new FileInputStream("test_files/test.properties"));

        this.wsdlUrl = props.getProperty("wsdl.url");

        Class.forName(props.getProperty("jdbc.driver"));
        JDBCConnectionProducer connProvider = new JDBCConnectionProducer(props.getProperty("jdbc.url"),
                                                                         props.getProperty("jdbc.username"),
                                                                         props.getProperty("jdbc.password"));
        this.connection = connProvider.createConnection();
        this.connection.setAutoCommit(true);

        this.userProjects = getUserProjects(USER_ID);
        this.adminProjects = getUserProjects(ADMIN_ID);
        this.userContests = getUserContests(USER_ID);
        this.adminContests = getUserContests(ADMIN_ID);

        this.cleanupSQL = new ArrayList<String>();
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void tearDown() throws Exception {
        this.adminContests = null;
        this.userContests = null;
        this.adminProjects = null;
        this.userProjects = null;

        if (this.connection != null) {
            try {
                for (String sql : this.cleanupSQL) {
                    Statement stmt = this.connection.createStatement();
                    stmt.execute(sql);
                    stmt.close();
                }
            } finally {
                this.connection.close();
                this.connection = null;
            }
        }
        
        this.cleanupSQL = null;
        this.wsdlUrl = null;
        super.tearDown();
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllContestTypes()} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns all existing contest types.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllContestTypes() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<ContestTypeData> expectedTypes = getAllContestTypes();
        List<ContestTypeData> actualTypes = client.getAllContestTypes();
        Assert.assertEquals("Wrong number of contest types retrieved", expectedTypes.size(), actualTypes.size());
        for (ContestTypeData expectedType : expectedTypes) {
            boolean found = false;
            for (ContestTypeData actualType : actualTypes) {
                if (actualType.getContestTypeId().equals(expectedType.getContestTypeId())) {
                    found = true;
                    verifyContestTypes(expectedType, actualType);
                }
            }
            Assert.assertTrue("The expected contest type is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllMediums()} functionality for producing accurate
     * results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns all existing mediums.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllMediums() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<MediumData> expectedTypes = getAllMediums();
        List<MediumData> actualTypes = client.getAllMediums();
        Assert.assertEquals("Wrong number of mediums retrieved", expectedTypes.size(), actualTypes.size());
        for (MediumData expectedType : expectedTypes) {
            boolean found = false;
            for (MediumData actualType : actualTypes) {
                if (actualType.getMediumId().equals(expectedType.getMediumId())) {
                    found = true;
                    Assert.assertEquals("Wrong medium description",
                                        expectedType.getDescription(), actualType.getDescription());
                }
            }
            Assert.assertTrue("The expected medium is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllMediums()} functionality for producing accurate
     * results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns all existing contest statuses.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetStatusList() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<ContestStatusData> expectedTypes = getAllStatuses();
        List<ContestStatusData> actualTypes = client.getStatusList();
        Assert.assertEquals("Wrong number of contest statuses retrieved", expectedTypes.size(), actualTypes.size());
        for (ContestStatusData expectedType : expectedTypes) {
            boolean found = false;
            for (ContestStatusData actualType : actualTypes) {
                if (actualType.getStatusId() == expectedType.getStatusId()) {
                    found = true;
                    verifyContestStatuses(expectedType, actualType);
                }
            }
            Assert.assertTrue("The expected contest status is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getSubmissionFileTypes()} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns all existing submission types.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetSubmissionFileTypes() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<String> expectedTypes = getAllSubmissionFileTypes();
        String[] actualTypes = client.getSubmissionFileTypes().split(",");
        Assert.assertEquals("Wrong number of contest statuses retrieved", expectedTypes.size(), actualTypes.length);
        for (String expectedType : expectedTypes) {
            boolean found = false;
            for (String actualType : actualTypes) {
                if (actualType.trim().equals(expectedType.trim())) {
                    found = true;
                }
            }
            Assert.assertTrue("The expected contest status is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllContests()} functionality for producing accurate
     * results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns contests which are associated with the specified user only.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllContests_User() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<Competition> expectedContests = this.userContests;
        List<StudioCompetition> actualContests = client.getAllContests();
        Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
        for (Competition expected : expectedContests) {
            boolean found = false;
            for (Competition actual : actualContests) {
                if (actual.getCompetitionId().equals(expected.getCompetitionId())) {
                    found = true;
                    verifyContests(expected, actual, true);
                }
            }
            Assert.assertTrue("The expected contest is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllContestHeaders()} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns contests which are associated with the specified user only.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllContestHeaders_User() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<Competition> expectedContests = this.userContests;
        List<StudioCompetition> actualContests = client.getAllContestHeaders();
        Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
        for (Competition expected : expectedContests) {
            boolean found = false;
            for (Competition actual : actualContests) {
                if (actual.getCompetitionId().equals(expected.getCompetitionId())) {
                    found = true;
                    verifyContests(expected, actual, false);
                }
            }
            Assert.assertTrue("The expected contest is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllContests()} functionality for producing accurate
     * results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns all existing contests.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllContests_Admin() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<Competition> expectedContests = getAllContests();
        List<StudioCompetition> actualContests = client.getAllContests();
        Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
        for (Competition expected : expectedContests) {
            boolean found = false;
            for (Competition actual : actualContests) {
                if (actual.getCompetitionId().equals(expected.getCompetitionId())) {
                    found = true;
                    verifyContests(expected, actual, true);
                }
            }
            Assert.assertTrue("The expected contest is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getAllContestHeaders()} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns all existing contests.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllContestHeaders_Admin() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<Competition> expectedContests = getAllContests();
        List<StudioCompetition> actualContests = client.getAllContestHeaders();
        Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
        for (Competition expected : expectedContests) {
            boolean found = false;
            for (Competition actual : actualContests) {
                if (actual.getCompetitionId().equals(expected.getCompetitionId())) {
                    found = true;
                    verifyContests(expected, actual, false);
                }
            }
            Assert.assertTrue("The expected contest is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContest(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns details for existing contests created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContest_User_OwnContests() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.userContests.size(); i++) {
            Competition contest = this.userContests.get(i);
            StudioCompetition actualContest = client.getContest(contest.getId());
            verifyContests(contest, actualContest, true);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#updateContest(StudioCompetition)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * updated details for existing contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateContest() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.userContests.size(); i++) {
            StudioCompetition contest = (StudioCompetition) this.userContests.get(i);
            cleanUpContestPrizes(contest.getContestData().getContestId());
            client.updateContest(contest);
            StudioCompetition actualContest = (StudioCompetition) getContest(contest.getId());
            verifyContests(contest, actualContest, true);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#createContest(StudioCompetition, long)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * creates new contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateContest() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        StudioCompetition template = (StudioCompetition) this.userContests.get(0);
        template.getContestData().setContestId(-1);
        template.getContestData().setName("NewLy Added contest");
        StudioCompetition newContest = client.createContest(template, template.getContestData().getTcDirectProjectId());
        Competition actualContest = getContest(newContest.getContestData().getContestId());
        Assert.assertNotNull("Contest is not created", actualContest);
        verifyContests(actualContest, newContest, true);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#deleteContest(long)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * deletes contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testDeleteContest() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        StudioCompetition template = (StudioCompetition) this.userContests.get(0);
        template.getContestData().setContestId(-1);
        template.getContestData().setName("NewLy Added contest");
        StudioCompetition newContest = client.createContest(template, template.getContestData().getTcDirectProjectId());
        client.deleteContest(newContest.getId());
        Assert.assertFalse("Contest is not deleted", contestExists(newContest.getId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContest(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns details for existing contests created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContest_Admin_OwnContests() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (int i = 0; i < this.adminContests.size(); i++) {
            Competition contest = this.adminContests.get(i);
            StudioCompetition actualContest = client.getContest(contest.getId());
            verifyContests(contest, actualContest, true);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContest(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns details for existing contests created by other user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContest_Admin_AlienContests() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (int i = 0; i < this.userContests.size(); i++) {
            Competition contest = this.userContests.get(i);
            StudioCompetition actualContest = client.getContest(contest.getId());
            verifyContests(contest, actualContest, true);
        }
    }

    /**
     * <p>Failure test. Tests the {@link ContestServiceFacade#getContest(long)} functionality for properly handling the
     * case when user has insufficient permissions for performing the requested action.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * raises an error for existing contests created by other user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContest_User_AlienContests() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.adminContests.size(); i++) {
            Competition contest = this.adminContests.get(i);
            try {
                client.getContest(contest.getId());
                Assert.fail("SOAPFaultException should have been thrown");
            } catch (SOAPFaultException e) {
                // Expected behavior
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContestsForProject(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns details for existing contests for projects created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContestsForProject_User_OwnProjects() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.userProjects.size(); i++) {
            ProjectData project = this.userProjects.get(i);
            List<Competition> expectedContests = getContestsForProject(project.getProjectId());
            List<StudioCompetition> actualContests = client.getContestsForProject(project.getProjectId());
            Assert.assertEquals("Wrong number of contests retrieved for project: " + project.getProjectId(),
                                expectedContests.size(), actualContests.size());
            for (Competition expectedContest : expectedContests) {
                boolean found = false;
                for (StudioCompetition actualContest : actualContests) {
                    if (expectedContest.getId() == actualContest.getId()) {
                        found = true;
                        verifyContests(expectedContest,  actualContest,  true);
                    }
                }
                Assert.assertTrue("Existing contest is not retrieved", found);
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContestsForClient(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns details for existing contests for client.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContestsForClient() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<Competition> expectedContests = getContestsForClient(USER_ID);
        List<StudioCompetition> actualContests = client.getContestsForClient(USER_ID);
        Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
        for (Competition expectedContest : expectedContests) {
            boolean found = false;
            for (StudioCompetition actualContest : actualContests) {
                if (expectedContest.getId() == actualContest.getId()) {
                    found = true;
                    verifyContests(expectedContest, actualContest, true);
                }
            }
            Assert.assertTrue("Existing contest is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContestsForProject(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns details for existing contests for projects created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContestsForProject_Admin_OwnProjects() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (int i = 0; i < this.adminProjects.size(); i++) {
            ProjectData project = this.adminProjects.get(i);
            List<Competition> expectedContests = getContestsForProject(project.getProjectId());
            List<StudioCompetition> actualContests = client.getContestsForProject(project.getProjectId());
            Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
            for (Competition expectedContest : expectedContests) {
                boolean found = false;
                for (StudioCompetition actualContest : actualContests) {
                    if (expectedContest.getId() == actualContest.getId()) {
                        found = true;
                        verifyContests(expectedContest,  actualContest,  true);
                    }
                }
                Assert.assertTrue("Existing contest is not retrieved", found);
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContestsForProject(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns details for existing contests for projects created by other user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContestsForProject_User_AlienProjects() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.adminProjects.size(); i++) {
            ProjectData project = this.adminProjects.get(i);
            List<Competition> expectedContests = getContestsForProject(project.getProjectId());
            List<StudioCompetition> actualContests = client.getContestsForProject(project.getProjectId());
            Assert.assertEquals("Wrong number of contests retrieved", expectedContests.size(), actualContests.size());
            for (Competition expectedContest : expectedContests) {
                boolean found = false;
                for (StudioCompetition actualContest : actualContests) {
                    if (expectedContest.getId() == actualContest.getId()) {
                        found = true;
                        verifyContests(expectedContest,  actualContest,  true);
                    }
                }
                Assert.assertTrue("Existing contest is not retrieved", found);
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContestsForProject(long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns details for existing contests for projects created by other user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContestsForProject_Admin_AlienProjects() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (int i = 0; i < this.userProjects.size(); i++) {
            ProjectData project = this.userProjects.get(i);
            List<Competition> expectedContests = getContestsForProject(project.getProjectId());
            List<StudioCompetition> actualContests = client.getContestsForProject(project.getProjectId());
            Assert.assertEquals("Wrong number of contests retrieved for project: " + project.getProjectId(),
                                expectedContests.size(), actualContests.size());
            for (Competition expectedContest : expectedContests) {
                boolean found = false;
                for (StudioCompetition actualContest : actualContests) {
                    if (expectedContest.getId() == actualContest.getId()) {
                        found = true;
                        verifyContests(expectedContest,  actualContest,  true);
                    }
                }
                Assert.assertTrue("Existing contest is not retrieved", found);
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#updateContestStatus(long, long)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * updates status for existing contests created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateContestStatus_User_OwnContests() throws Exception {
        List<ContestStatusData> statuses = getAllStatuses();
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.userContests.size(); i++) {
            Competition contest = this.userContests.get(i);
            for (ContestStatusData status : statuses) {
                client.updateContestStatus(contest.getId(), status.getStatusId());
                StudioCompetition actualContest = (StudioCompetition) getContest(contest.getId());
                Assert.assertEquals("Status is not updated correctly",
                                    status.getStatusId(), actualContest.getContestData().getStatusId());
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#updateContestStatus(long, long)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * updates status for existing contests created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateContestStatus_Admin_OwnContests() throws Exception {
        List<ContestStatusData> statuses = getAllStatuses();
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (int i = 0; i < this.adminContests.size(); i++) {
            Competition contest = this.adminContests.get(i);
            for (ContestStatusData status : statuses) {
                client.updateContestStatus(contest.getId(), status.getStatusId());
                StudioCompetition actualContest = (StudioCompetition) getContest(contest.getId());
                Assert.assertEquals("Status is not updated correctly",
                                    status.getStatusId(), actualContest.getContestData().getStatusId());
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#updateContestStatus(long, long)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * updates status for existing contests created by other user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateContestStatus_Admin_AlienContests() throws Exception {
        List<ContestStatusData> statuses = getAllStatuses();
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (int i = 0; i < this.userContests.size(); i++) {
            StudioCompetition contest = (StudioCompetition) this.userContests.get(i);
            for (ContestStatusData status : statuses) {
                if (status.getStatusId() != 6) {
                    client.updateContestStatus(contest.getId(), status.getStatusId());
                    StudioCompetition actualContest = (StudioCompetition) getContest(contest.getId());
                    Assert.assertEquals("Status is not updated correctly",
                                        status.getStatusId(), actualContest.getContestData().getStatusId());
                }
            }
        }
    }

    /**
     * <p>Failure test. Tests the {@link ContestServiceFacade#updateContestStatus(long, long)} functionality for
     * properly handling the case when user has insufficient permissions for performing the requested action.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * raises an error for existing contests created by other user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateContestStatus_User_AlienContests() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (int i = 0; i < this.adminContests.size(); i++) {
            Competition contest = this.adminContests.get(i);
            try {
                client.getContest(contest.getId());
                Assert.fail("SOAPFaultException should have been thrown");
            } catch (SOAPFaultException e) {
                // Expected behavior
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getMimeTypeId(String)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns correct MIME type IDs for all existing content types.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetMimeTypeId() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<MimeType> expectedTypes = getAllMimeTypes();
        for (MimeType expectedType : expectedTypes) {
            long mimeTypeId = client.getMimeTypeId(expectedType.getDescription());
            Assert.assertEquals("Wrong MIME type ID returned", expectedType.getMimeTypeId().longValue(), mimeTypeId);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#createContestPayment(ContestPaymentData, String)}
     * functionality for accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * creates payment successfully for existing contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateContestPayment_User_OwnContests() throws Exception {
        long contestId = this.userContests.get(0).getId();
        this.cleanupSQL.add("DELETE FROM contest_payment WHERE contest_id = " + contestId);
        
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        ContestPaymentData contestPayment = new ContestPaymentData();
        contestPayment.setContestId(contestId);
        contestPayment.setCreateDate(new Date());
        contestPayment.setPaymentStatusId(1L);
        contestPayment.setPaypalOrderId("PayPal#100");
        contestPayment.setPrice(100.00);
        ContestPaymentData createdPayment = client.createContestPayment(contestPayment, "token1");
        verifyContestPayments(contestPayment, createdPayment);
        Assert.assertTrue("Contest is not created in database", contestPaymentExists(contestPayment.getContestId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getContestPayment(long)} functionality for accurate
     * behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * creates payment successfully for existing contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetContestPayment_User_OwnContests() throws Exception {
        long contestId = this.userContests.get(0).getId();
        this.cleanupSQL.add("DELETE FROM contest_payment WHERE contest_id = " + contestId);

        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        ContestPaymentData contestPayment = new ContestPaymentData();
        contestPayment.setContestId(contestId);
        contestPayment.setCreateDate(new Date());
        contestPayment.setPaymentStatusId(1L);
        contestPayment.setPaypalOrderId("PayPal#100");
        contestPayment.setPrice(100.00);
        client.createContestPayment(contestPayment, "token1");

        ContestPaymentData actualPayment = client.getContestPayment(contestId);
        verifyContestPayments(contestPayment, actualPayment);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#editContestPayment(ContestPaymentData)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * updates payment successfully for existing contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testEditContestPayment_User_OwnContests() throws Exception {
        long contestId = this.userContests.get(0).getId();
        this.cleanupSQL.add("DELETE FROM contest_payment WHERE contest_id = " + contestId);

        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        ContestPaymentData contestPayment = new ContestPaymentData();
        contestPayment.setContestId(contestId);
        contestPayment.setCreateDate(new Date());
        contestPayment.setPaymentStatusId(1L);
        contestPayment.setPaypalOrderId("PayPal#100");
        contestPayment.setPrice(100.00);
        client.createContestPayment(contestPayment, "token1");

        contestPayment.setPaymentStatusId(2L);
        contestPayment.setCreateDate(new Date(System.currentTimeMillis() - 202024310L));
        contestPayment.setPrice(200.00);
        client.editContestPayment(contestPayment);

        ContestPaymentData actualPayment = client.getContestPayment(contestId);
        verifyContestPayments(contestPayment, actualPayment);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#removeContestPayment(long)} functionality for accurate
     * behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * removes payment successfully for existing contest created by user.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testRemoveContestPayment_User_OwnContests() throws Exception {
        long contestId = this.userContests.get(0).getId();
        this.cleanupSQL.add("DELETE FROM contest_payment WHERE contest_id = " + contestId);

        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        ContestPaymentData contestPayment = new ContestPaymentData();
        contestPayment.setContestId(contestId);
        contestPayment.setCreateDate(new Date());
        contestPayment.setPaymentStatusId(1L);
        contestPayment.setPaypalOrderId("PayPal#100");
        contestPayment.setPrice(100.00);
        client.createContestPayment(contestPayment, "token1");

        boolean result = client.removeContestPayment(contestId);
        Assert.assertTrue("The payment is not reported deleted", result);
        Assert.assertFalse("The payment is not deleted from the database", contestPaymentExists(contestId));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#uploadDocument(UploadedDocument)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * adds document successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUploadDocument() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);

        UploadedDocument document = new UploadedDocument();
        document.setContestId(-1);
        document.setDescription("Description");
        document.setDocumentId(-1);
        document.setDocumentTypeId(1L);
        document.setFile("Content".getBytes());
        document.setFileName("Filename.txt");
        document.setMimeTypeId(1);
        document.setPath("path");

        UploadedDocument uploadedDocument = client.uploadDocument(document);
        Assert.assertTrue("The document is not added to the database " + uploadedDocument.getDocumentId(),
                          documentExists(uploadedDocument.getDocumentId()));
        document.setDocumentId(uploadedDocument.getDocumentId());
        verifyDocuments(document, uploadedDocument);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#uploadDocumentForContest(UploadedDocument)} functionality
     * for accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * adds document for specified contest successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUploadDocumentForContest() throws Exception {
        StudioCompetition contest = findContestForDocumentUpload(this.userContests);
        long contestId = contest.getContestData().getContestId();
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);

        UploadedDocument document = new UploadedDocument();
        document.setContestId(contestId);
        document.setDescription("Description");
        document.setDocumentId(-1);
        document.setDocumentTypeId(1L);
        document.setFile("Content".getBytes());
        document.setFileName("Filename.txt");
        document.setMimeTypeId(1);
        document.setPath("path");

        UploadedDocument uploadedDocument = client.uploadDocumentForContest(document);
        document.setDocumentId(uploadedDocument.getDocumentId());
        Assert.assertTrue("The document is not added to the database",
                          documentExists(uploadedDocument.getDocumentId()));
        Assert.assertTrue("The document is not added to the contest",
                          contestDocumentExists(contestId, uploadedDocument.getDocumentId()));
        verifyDocuments(document, uploadedDocument);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#addDocumentToContest(long, long)} functionality
     * for accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * adds document for specified contest successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testAddDocumentToContest() throws Exception {
        StudioCompetition contest = findContestForDocumentUpload(this.userContests);
        long contestId = contest.getContestData().getContestId();
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);

        UploadedDocument document = new UploadedDocument();
        document.setContestId(contestId);
        document.setDescription("Description");
        document.setDocumentId(-1);
        document.setDocumentTypeId(1L);
        document.setFile("Content".getBytes());
        document.setFileName("Filename.txt");
        document.setMimeTypeId(1);
        document.setPath("path");

        UploadedDocument uploadedDocument = client.uploadDocument(document);
        client.addDocumentToContest(uploadedDocument.getDocumentId(), contestId);
        Assert.assertTrue("The document is not added to the contest",
                          contestDocumentExists(contestId, uploadedDocument.getDocumentId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#removeDocumentFromContest(UploadedDocument)}
     * functionality for accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * removes document from specified contest successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testRemoveDocumentFromContest() throws Exception {
        StudioCompetition contest = findContestForDocumentUpload(this.userContests);
        long contestId = contest.getId();
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);

        UploadedDocument document = new UploadedDocument();
        document.setContestId(contestId);
        document.setDescription("Description");
        document.setDocumentId(-1);
        document.setDocumentTypeId(1L);
        document.setFile("Content".getBytes());
        document.setFileName("Filename.txt");
        document.setMimeTypeId(1);
        document.setPath("path");

        UploadedDocument uploadedDocument = client.uploadDocumentForContest(document);
        uploadedDocument.setContestId(contestId);
        client.removeDocumentFromContest(uploadedDocument);
        Assert.assertFalse("The document is not removed from the contest " + contestId + ", "
                           + contest.getContestData().getStatusId(),
                          contestDocumentExists(contestId, uploadedDocument.getDocumentId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#removeDocument(long)} functionality for accurate
     * behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * removes document from specified contest successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testRemoveDocument() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);

        StudioCompetition contest = findContestForDocumentUpload(this.userContests);
        UploadedDocument document = new UploadedDocument();
        document.setContestId(contest.getId());
        document.setDescription("Description");
        document.setDocumentId(-1);
        document.setDocumentTypeId(1L);
        document.setFile("Content".getBytes());
        document.setFileName("Filename.txt");
        document.setMimeTypeId(1);
        document.setPath("path");

        UploadedDocument uploadedDocument = client.uploadDocument(document);
        client.removeDocument(uploadedDocument.getDocumentId());
        Assert.assertFalse("The document is not removed from the database",
                          documentExists(uploadedDocument.getDocumentId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#retrieveSubmissionsForContest(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * retrieves all submissions for specified contest successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testRetrieveSubmissionsForContest() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (Competition contest : this.userContests) {
            List<SubmissionData> expectedSubmissions = getContestSubmissions(contest.getId());
            List<SubmissionData> actualSubmissions = client.retrieveSubmissionsForContest(contest.getId());
            Assert.assertEquals("Wrong number of submissions retrieved",
                                expectedSubmissions.size(), actualSubmissions.size());
            for (SubmissionData expectedSubmission : expectedSubmissions) {
                boolean found = false;
                for (SubmissionData actualSubmission : actualSubmissions) {
                    if (actualSubmission.getSubmissionId() == expectedSubmission.getSubmissionId()) {
                        found = true;
                        verifySubmissions(expectedSubmission, actualSubmission);
                    }
                }
                Assert.assertTrue("The submission is not retrieved " + expectedSubmission.getSubmissionId(), found);
            }
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#retrieveAllSubmissionsByMember(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * retrieves all submissions for specified user successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testReetrieveAllSubmissionsByMember() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<SubmissionData> expectedSubmissions = getUserSubmissions(USER_ID);
        List<SubmissionData> actualSubmissions = client.retrieveAllSubmissionsByMember(USER_ID);
        Assert.assertEquals("Wrong number of submissions retrieved",
                            expectedSubmissions.size(), actualSubmissions.size());
        for (SubmissionData expectedSubmission : expectedSubmissions) {
            boolean found = false;
            for (SubmissionData actualSubmission : actualSubmissions) {
                if (actualSubmission.getSubmissionId() == expectedSubmission.getSubmissionId()) {
                    found = true;
                    verifySubmissions(expectedSubmission, actualSubmission);
                }
            }
            Assert.assertTrue("The submission is not retrieved " + expectedSubmission.getSubmissionId(), found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#retrieveSubmission(long)} functionality for accurate
     * behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * retrieves specified submission details successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testRetrieveSubmission() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<SubmissionData> expectedSubmissions = getUserSubmissions(USER_ID);
        for (SubmissionData expectedSubmission : expectedSubmissions) {
            SubmissionData actualSubmission = client.retrieveSubmission(expectedSubmission.getSubmissionId());
            verifySubmissions(expectedSubmission, actualSubmission);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#updateSubmission(SubmissionData)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * updates specified submission details successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateSubmission() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<SubmissionData> expectedSubmissions = getUserSubmissions(ADMIN_ID);
        for (SubmissionData expectedSubmission : expectedSubmissions) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            expectedSubmission.setSubmittedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            client.updateSubmission(expectedSubmission);
            SubmissionData actualSubmission = getSubmission(expectedSubmission.getSubmissionId());
            verifySubmissions(expectedSubmission, actualSubmission);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#removeSubmission(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * removes specified submission details successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testRemoveSubmission() throws Exception {
        ContestServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        SubmissionData submission = createSubmission(USER_ID, this.userContests.get(0).getId());
        client.removeSubmission(submission.getSubmissionId());
        Assert.assertFalse("The submission was not removed from DB",
                           submisisonExists(submission.getSubmissionId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#markForPurchase(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * marks submission for purchase successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testMarkForPurchase() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        SubmissionData submission = createSubmission(USER_ID, this.userContests.get(0).getId());
        client.markForPurchase(submission.getSubmissionId());
        SubmissionPayment payment = getSubmissionPayment(submission.getSubmissionId());
        Assert.assertNotNull("No payment added for submission marked for purchase", payment);
        Assert.assertEquals("Wrong payment status", 3, payment.getStatus().getPaymentStatusId().longValue());
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#markForPurchase(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * purchaes submission successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testPurchaseSubmission() throws Exception {
        long contestId = this.userContests.get(0).getId();
        String paypalOrderId = "PayPal#200";
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        SubmissionData submission = createSubmission(USER_ID, contestId);

        cleanUpContestPrizes(contestId);
        Prize prize = createPrize(1, 100.00, contestId);
        client.markForPurchase(submission.getSubmissionId());
        client.purchaseSubmission(submission.getSubmissionId(), paypalOrderId, "token2");
        SubmissionPayment payment = getSubmissionPayment(submission.getSubmissionId());
        Assert.assertNotNull("No payment added for submission marked for purchase", payment);
        Assert.assertEquals("Wrong payment status", 1, payment.getStatus().getPaymentStatusId().longValue());
        Assert.assertEquals("Wrong PayPal order ID", paypalOrderId, payment.getPayPalOrderId());
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#setSubmissionPlacement(long, int)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * sets submission placement successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testSetSubmissionPlacement() throws Exception {
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        SubmissionData submission = createSubmission(USER_ID, this.userContests.get(0).getId());
        client.setSubmissionPlacement(submission.getSubmissionId(), 3);
        int actualPlacement = getContestResult(submission.getSubmissionId(), submission.getContestId());
        Assert.assertEquals("Wrong placement", 3, actualPlacement);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#setSubmissionPrize(long, long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * sets submission prize successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testSetSubmissionPrize() throws Exception {
        long contestId = this.userContests.get(0).getId();
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        SubmissionData submission = createSubmission(USER_ID, contestId);
        cleanUpContestPrizes(contestId);
        Prize prize = createPrize(3, 100.00, contestId);
        client.setSubmissionPrize(submission.getSubmissionId(), prize.getPrizeId());
        Assert.assertTrue("Submission is not linked to prize",
                          submissionPrizeExists(submission.getSubmissionId(), prize.getPrizeId()));
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#addChangeHistory(List)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     *  for purchase successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testAddChangeHistory() throws Exception {
        StudioCompetition contest = (StudioCompetition) this.userContests.get(0);
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<ChangeHistoryData> history = new ArrayList<ChangeHistoryData>();
        ChangeHistoryData rec = new ChangeHistoryData();
        rec.setContestId(contest.getId());
        rec.setFieldName("name");
        rec.setNewData("new name");
        rec.setOldData("old name");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        rec.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        rec.setTransactionId(10222392L);
        rec.setUserAdmin(false);
        rec.setUserName(USER_HANDLE);
        history.add(rec);
        client.addChangeHistory(history);
        List<ChangeHistoryData> actualHistoryRecords = getChangeHistory(contest.getId());
        Assert.assertEquals("Wrong number of history records", history.size(), actualHistoryRecords.size());
        boolean found = false;
        for (ChangeHistoryData actualHistory : actualHistoryRecords) {
            if (actualHistory.getTransactionId().equals(rec.getTransactionId())
                && actualHistory.getFieldName().equals(rec.getFieldName())) {
                found = true;
                verifyHistories(rec, actualHistory);
            }
        }
        Assert.assertTrue("No history record added", found);
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getChangeHistory(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * retrieves the history data successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetChangeHistory() throws Exception {
        StudioCompetition contest = (StudioCompetition) this.userContests.get(0);
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<ChangeHistoryData> history = new ArrayList<ChangeHistoryData>();
        ChangeHistoryData rec = new ChangeHistoryData();
        rec.setContestId(contest.getId());
        rec.setFieldName("name");
        rec.setNewData("new name");
        rec.setOldData("old name");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        rec.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        rec.setTransactionId(10222392L);
        rec.setUserAdmin(false);
        rec.setUserName(USER_HANDLE);
        history.add(rec);
        client.addChangeHistory(history);

        List<ChangeHistoryData> expectedHistoryRecords = getChangeHistory(contest.getId());
        List<ChangeHistoryData> actualHistoryRecords = client.getChangeHistory(contest.getId());
        Assert.assertEquals("Wrong number of history records",
                            expectedHistoryRecords.size(), actualHistoryRecords.size());
        for (ChangeHistoryData expected : expectedHistoryRecords) {
            boolean found = false;
            for (ChangeHistoryData actualHistory : actualHistoryRecords) {
                if (actualHistory.getTransactionId().equals(expected.getTransactionId())
                    && actualHistory.getFieldName().equals(expected.getFieldName())) {
                    found = true;
                    verifyHistories(expected, actualHistory);
                }
            }
            Assert.assertTrue("No history record found", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ContestServiceFacade#getLatestChanges(long)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * retrieves the latest changes history data successfully.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetLatestChanges() throws Exception {
        StudioCompetition contest = (StudioCompetition) this.userContests.get(0);
        ContestServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<ChangeHistoryData> history = new ArrayList<ChangeHistoryData>();
        ChangeHistoryData rec = new ChangeHistoryData();
        rec.setContestId(contest.getId());
        rec.setFieldName("name");
        rec.setNewData("new name");
        rec.setOldData("old name");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        rec.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        rec.setTransactionId(10222392L);
        rec.setUserAdmin(false);
        rec.setUserName(USER_HANDLE);
        history.add(rec);
        client.addChangeHistory(history);

        List<ChangeHistoryData> expectedHistoryRecords = getChangeHistory(contest.getId());
        List<ChangeHistoryData> actualHistoryRecords = client.getLatestChanges(contest.getId());
        Assert.assertEquals("Wrong number of history records",
                            expectedHistoryRecords.size(), actualHistoryRecords.size());
        for (ChangeHistoryData expected : expectedHistoryRecords) {
            boolean found = false;
            for (ChangeHistoryData actualHistory : actualHistoryRecords) {
                if (actualHistory.getTransactionId().equals(expected.getTransactionId())
                    && actualHistory.getFieldName().equals(expected.getFieldName())) {
                    found = true;
                    verifyHistories(expected, actualHistory);
                }
            }
            Assert.assertTrue("No history record found", found);
        }
    }

    /**
     * <p>Disassociates any prizes from specified contest</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @throws Exception if an unexpected error occurs.
     */
    private void cleanUpContestPrizes(long contestId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("DELETE FROM contest_prize_xref WHERE contest_id = ?");
            ps.setLong(1, contestId);
            ps.executeUpdate();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the payment for specified submission from the database.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @return a <code>SubmissionPayment</code> providing the data for submission payment.
     * @throws Exception if an unexpected error occurs.
     */
    private SubmissionPayment getSubmissionPayment(long submissionId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT p.* " +
                                                  "FROM submission_payment p " +
                                                  "WHERE submission_id = ?");
            ps.setLong(1, submissionId);
            rs = ps.executeQuery();

            if (rs.next()) {
                PaymentStatus s = new PaymentStatus();
                SubmissionPayment payment = new SubmissionPayment();
                payment.setCreateDate(rs.getTimestamp("create_date"));
                payment.setPayPalOrderId(rs.getString("paypal_order_id"));
                payment.setPrice(rs.getDouble("price"));
                s.setPaymentStatusId(rs.getLong("payment_status_id"));
                payment.setStatus(s);

                return payment;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the payment for specified submission from the database.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>int</code> providing the submission placement.
     * @throws Exception if an unexpected error occurs.
     */
    private int getContestResult(long submissionId, long contestId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT p.* " +
                                                  "FROM contest_result p " +
                                                  "WHERE submission_id = ? AND contest_id = ?");
            ps.setLong(1, submissionId);
            ps.setLong(2, contestId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("placed");
            } else {
                return 0;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Checks that the specified submission is linked to specified prize.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @param prizeId a <code>long</code> providing the ID of a prize.
     * @return <code>true</code> if submission is linked to prize; <code>false</code> otherwise.
     * @throws Exception if an unexpected error occurs.
     */
    private boolean submissionPrizeExists(long submissionId, long prizeId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT p.* " +
                                                  "FROM submission_prize_xref p " +
                                                  "WHERE submission_id = ? AND prize_id = ?");
            ps.setLong(1, submissionId);
            ps.setLong(2, prizeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Creates new submission with sample data in the database.</p>
     *
     * @param userId user ID.
     * @param contestId contest ID.
     * @return a <code>SubmissionData</code> providing the data for created submission.
     * @throws Exception if an unexpected error occurs.
     */
    private SubmissionData createSubmission(long userId, long contestId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT FIRST 1 studio_submission_seq.NEXTVAL FROM contest");
            rs = ps.executeQuery();
            rs.next();
            long submissionId = rs.getLong(1);
            rs.close();

            ps = this.connection.prepareStatement("INSERT INTO submission (submission_id, submitter_id, contest_id, " +
                                                  "create_date, original_file_name, system_file_name, path_id, " +
                                                  "submission_type_id, mime_type_id, rank, submission_date," +
                                                  "submission_status_id, modify_date) VALUES " +
                                                  "(?,?,?,CURRENT,'filename.txt'," +
                                                  "'systemFilename.txt',2031,1,4,1,CURRENT,1,CURRENT)");
            ps.setLong(1, submissionId);
            ps.setLong(2, userId);
            ps.setLong(3, contestId);
            ps.executeUpdate();

            return getSubmission(submissionId);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Creates new prize with sample data in the database.</p>
     *
     * @param place place.
     * @param amount amount.
     * @param contestId contest ID.
     * @return a <code>Prize</code> providing the data for created prize.
     * @throws Exception if an unexpected error occurs.
     */
    private Prize createPrize(int place, double amount, long contestId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT FIRST 1 studio_prize_seq.NEXTVAL FROM contest");
            rs = ps.executeQuery();
            rs.next();
            long prizeId = rs.getLong(1);
            rs.close();

            ps = this.connection.prepareStatement("INSERT INTO prize (prize_id, place, amount, prize_type_id," +
                                                  " create_date) " +
                                                  "VALUES (?,?,?,1,CURRENT)");
            ps.setLong(1, prizeId);
            ps.setInt(2, place);
            ps.setDouble(3, amount);
            ps.executeUpdate();

            ps = this.connection.prepareStatement("INSERT INTO contest_prize_xref (contest_id, prize_id, create_date) "
                                                  + "VALUES (?,?,CURRENT)");
            ps.setLong(1, contestId);
            ps.setLong(2, prizeId);
            ps.executeUpdate();

            return getPrize(prizeId);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of submissions for the specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of submissions for.
     * @return a <code>List</code> of <code>SubmissionData</code> providing the details for located submissions.
     * @throws Exception if an SQL error occurs.
     */
    private List<SubmissionData> getContestSubmissions(long contestId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT s.* FROM submission s WHERE contest_id = ? " +
                                                  "AND s.submission_status_id <> 2");
            ps.setLong(1, contestId);
            rs = ps.executeQuery();

            List<SubmissionData> result = new ArrayList<SubmissionData>();
            while (rs.next()) {
                result.add(getSubmission(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of history records for the specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of history records for.
     * @return a <code>List</code> of <code>ChangeHistoryData</code> providing the details for located history.
     * @throws Exception if an SQL error occurs.
     */
    private List<ChangeHistoryData> getChangeHistory(long contestId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT s.* FROM contest_change_history s " +
                                                  "WHERE contest_id = ? ORDER BY timestamp DESC");
            ps.setLong(1, contestId);
            rs = ps.executeQuery();

            List<ChangeHistoryData> result = new ArrayList<ChangeHistoryData>();
            while (rs.next()) {
                ChangeHistoryData rec = new ChangeHistoryData();
                rec.setContestId(rs.getLong("contest_id"));
                rec.setFieldName(rs.getString("field_name"));
                rec.setNewData(rs.getString("new_data"));
                rec.setOldData(rs.getString("old_data"));
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(rs.getTimestamp("timestamp"));
                rec.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
                rec.setTransactionId(rs.getLong("transaction_id"));
                rec.setUserAdmin(rs.getBoolean("is_user_admin"));
                rec.setUserName(rs.getString("username"));

                result.add(rec);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the client for <code>TopCoder Service Facade</code> web service authenticated as specified user.</p>
     *
     * @param username a <code>String</code> providing the username to be used for authenticating the client to web
     *        service.
     * @return a <code>TopCoderServiceFacade</code> representing the client interface to web service authenticated as
     *         specified user.
     * @throws Exception if an unexpected error occurs.
     */
    private ContestServiceFacade createWebServiceClient(String username) throws Exception {
        URL wsdlUrl = new URL(this.wsdlUrl);
        QName serviceName = new QName("http://ejb.contest.facade.service.topcoder.com/",
                                      "ContestServiceFacadeBeanService");
        ContestServiceFacade service = Service.create(wsdlUrl, serviceName).getPort(ContestServiceFacade.class);

        StubExt stubExt = (StubExt) service;
        URL securityConfigURL = getClass().getClassLoader().getResource("ws/jboss-wsse-client.xml");
        stubExt.setSecurityConfig(securityConfigURL.toURI().toString());
        stubExt.setConfigName("Standard WSSecurity Client");
        Map<String,Object> context = ((BindingProvider) service).getRequestContext();
        context.put(BindingProvider.USERNAME_PROPERTY, username);
        context.put(BindingProvider.PASSWORD_PROPERTY, "password");

        return service;
    }

    /**
     * <p>Verifies that the specified competitions are equal.</p>
     *
     * @param expected a <code>Competition</code> providing the expected competition data.
     * @param actual a <code>Competition</code> providng the actual competition data.
     * @param full <code>true</code> if contests provide full data; <code>false</code> if header data is provided only.
     */
    private void verifyContests(Competition expected, Competition actual, boolean full) {
        assertEquals("Contest id is invalid", expected.getCompetitionId(), actual.getCompetitionId());
        assertEquals("Contest id is invalid", expected.getId(), actual.getId());
        assertEquals("Contest author is invalid" + expected.getId(),
                     expected.getCreatorUserId(), actual.getCreatorUserId());
        assertEquals("Contest start time is invalid" + expected.getId(),
                     getDate(expected.getStartTime()), getDate(actual.getStartTime()));
        if (full) {
            assertEquals("Contest admin fee is invalid" + expected.getId(),
                         expected.getAdminFee(), actual.getAdminFee(), 0.001);
            assertEquals("Contest DR points is invalid" + expected.getId(),
                         expected.getDrPoints(), actual.getDrPoints(), 0.001);
        }
    }

    /**
     * <p>Verifies that the specified history records are equal.</p>
     *
     * @param expected a <code>ChangeHistoryData</code> providing the expected history data.
     * @param actual a <code>ChangeHistoryData</code> providng the actual history data.
     */
    private void verifyHistories(ChangeHistoryData expected, ChangeHistoryData actual) {
        assertEquals("Contest id is invalid", expected.getContestId(), actual.getContestId());
        assertEquals("Filed name is invalid", expected.getFieldName(), actual.getFieldName());
        assertEquals("Transaction ID is invalid", expected.getTransactionId(), actual.getTransactionId());
        assertEquals("Old Value is invalid", expected.getOldData(), actual.getOldData());
        assertEquals("New Value is invalid", expected.getNewData(), actual.getNewData());
        assertEquals("Username is invalid", expected.getUserName(), actual.getUserName());
    }


    /**
     * <p>Verifies that the specified competitions are equal.</p>
     *
     * @param expected a <code>SubmissionData</code> providing the expected submission data.
     * @param actual a <code>SubmissionData</code> providng the actual submission data.
     */
    private void verifySubmissions(SubmissionData expected, SubmissionData actual) {
        assertEquals("Submission id is invalid", expected.getSubmissionId(), actual.getSubmissionId());
        assertEquals("Contest id is invalid", expected.getContestId(), actual.getContestId());
        assertEquals("Date id is invalid", expected.getSubmittedDate(), actual.getSubmittedDate());
        assertEquals("Submitter id is invalid", expected.getSubmitterId(), actual.getSubmitterId());
    }

    /**
     * <p>Verifies that the specified contest types are equal.</p>
     *
     * @param expected a <code>ContestTypeData</code> providing the expected contest type data.
     * @param actual a <code>ContestTypeData</code> providng the actual contest type data.
     */
    private void verifyContestTypes(ContestTypeData expected, ContestTypeData actual) {
        assertEquals("Contest type id is invalid", expected.getContestTypeId(), actual.getContestTypeId());
        assertEquals("Contest type description is invalid", expected.getDescription(), actual.getDescription());
        assertEquals("Contest type preview file flag is invalid",
                     expected.isRequirePreviewFile(), actual.isRequirePreviewFile());
        assertEquals("Contest type preview image flag is invalid",
                     expected.isRequirePreviewImage(), actual.isRequirePreviewImage());
    }

    /**
     * <p>Verifies that the specified documents are equal.</p>
     *
     * @param expected a <code>ContestTypeData</code> providing the expected contest type data.
     * @param actual a <code>ContestTypeData</code> providng the actual contest type data.
     */
    private void verifyDocuments(UploadedDocument expected, UploadedDocument actual) {
        assertEquals("Document id is invalid", expected.getDocumentId(), actual.getDocumentId());
        assertEquals("Document description is invalid", expected.getDescription(), actual.getDescription());
        assertEquals("Document type is invalid", expected.getDocumentTypeId(), actual.getDocumentTypeId());
        assertEquals("Document file name is invalid", expected.getFileName(), actual.getFileName());
        assertEquals("Document MIME type ID is invalid", expected.getMimeTypeId(), actual.getMimeTypeId());
    }

    /**
     * <p>Verifies that the specified contest payments are equal.</p>
     *
     * @param expected a <code>ContestPaymentData</code> providing the expected contest payment data.
     * @param actual a <code>ContestPaymentData</code> providng the actual contest payment data.
     */
    private void verifyContestPayments(ContestPaymentData expected, ContestPaymentData actual) {
        assertEquals("Contest id is invalid", expected.getContestId(), actual.getContestId());
        assertEquals("Payment status ID is invalid", expected.getPaymentStatusId(), actual.getPaymentStatusId());
        assertEquals("Paypal Order ID flag is invalid", expected.getPaypalOrderId(), actual.getPaypalOrderId());
        assertEquals("Payment amount is invalid", expected.getPrice(), actual.getPrice());
    }

    /**
     * <p>Verifies that the specified contest statusses are equal.</p>
     *
     * @param expected a <code>ContestStatusData</code> providing the expected contest status data.
     * @param actual a <code>ContestStatusData</code> providng the actual contest status data.
     */
    private void verifyContestStatuses(ContestStatusData expected, ContestStatusData actual) {
        assertEquals("Contest status id is invalid", expected.getStatusId(), actual.getStatusId());
        assertEquals("Contest status description is invalid", expected.getDescription(), actual.getDescription());
        assertEquals("Contest status name is invalid", expected.getName(), actual.getName());
    }

    /**
     * <p>Checks whether the specified contest exists in database or not.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return <code>true</code> if respective record is found in database table; <code>false</code> otherwise.
     * @throws SQLException if an SQL error occurs.
     */
    private boolean contestExists(long contestId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM contest WHERE contest_id = ? AND deleted = 0");
            ps.setLong(1, contestId);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Checks whether the payment for specified contest exists in database or not.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return <code>true</code> if respective record is found in database table; <code>false</code> otherwise.
     * @throws SQLException if an SQL error occurs.
     */
    private boolean contestPaymentExists(long contestId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM contest_payment WHERE contest_id = ?");
            ps.setLong(1, contestId);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Checks whether the specified submission exists in database or not.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @return <code>true</code> if respective record is found in database table; <code>false</code> otherwise.
     * @throws SQLException if an SQL error occurs.
     */
    private boolean submisisonExists(long submissionId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM submission WHERE submission_id = ? " +
                                                  "AND submission_status_id = 1");
            ps.setLong(1, submissionId);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    

    /**
     * <p>Checks whether the specified document exists in database or not.</p>
     *
     * @param documentId a <code>long</code> providing the ID of a document.
     * @return <code>true</code> if respective record is found in database table; <code>false</code> otherwise.
     * @throws SQLException if an SQL error occurs.
     */
    private boolean documentExists(long documentId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM document WHERE document_id = ?");
            ps.setLong(1, documentId);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Checks whether the specified contest document exists in database or not.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @param documentId a <code>long</code> providing the ID of a document.
     * @return <code>true</code> if respective record is found in database table; <code>false</code> otherwise.
     * @throws SQLException if an SQL error occurs.
     */
    private boolean contestDocumentExists(long contestId, long documentId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM contest_document_xref " +
                                                  "WHERE contest_id = ? AND document_id = ?");
            ps.setLong(1, contestId);
            ps.setLong(2, documentId);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of projects created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of projects for.
     * @return a <code>List</code> of <code>ProjectData</code> providing the details for located projects.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ProjectData> getUserProjects(long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project WHERE user_id = ? ORDER BY project_id");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            List<ProjectData> result = new ArrayList<ProjectData>();
            while (rs.next()) {
                ProjectData project = new ProjectData();
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setProjectId(rs.getLong("project_id"));
                result.add(project);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of contests created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of contests for.
     * @return a <code>List</code> of <code>Competition</code> providing the details for located contests.
     * @throws SQLException if an SQL error occurs.
     */
    private List<Competition> getUserContests(long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.*, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 25) AS admin_fee, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 24) AS dr_points, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 1) AS short_summary " +
                "FROM contest c " +
                "WHERE c.create_user_id = ? AND (c.deleted IS NULL OR c.deleted = 0) " +
                "AND NOT c.tc_direct_project_id IS NULL");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            List<Competition> result = new ArrayList<Competition>();
            while (rs.next()) {
                result.add(getCompetition(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of submissions created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of submissions for.
     * @return a <code>List</code> of <code>SubmissionData</code> providing the details for located submissions.
     * @throws Exception if an SQL error occurs.
     */
    private List<SubmissionData> getUserSubmissions(long userId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT s.* FROM submission s WHERE submitter_id = ? " +
                                                  "AND submission_status_id <> 2");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            List<SubmissionData> result = new ArrayList<SubmissionData>();
            while (rs.next()) {
                result.add(getSubmission(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of payments for contests created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of contests for.
     * @return a <code>List</code> of <code>Competition</code> providing the details for located contests.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ContestPaymentData> getUserContestPayments(long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT p.* " +
                "FROM contest c " +
                "INNER JOIN contest_payment p ON c.contest_id = p.contest_id " +
                "WHERE c.create_user_id = ? " +
                "AND (c.deleted IS NULL OR c.deleted = 0) " +
                "AND NOT c.tc_direct_project_id IS NULL");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            List<ContestPaymentData> result = new ArrayList<ContestPaymentData>();
            while (rs.next()) {
                ContestPaymentData payment = new ContestPaymentData();
                payment.setContestId(rs.getLong("contest_id"));
                payment.setCreateDate(rs.getTimestamp("create_date"));
                payment.setPaymentStatusId(rs.getLong("payment_status_id"));
                payment.setPaypalOrderId(rs.getString("paypal_order_id"));
                payment.setPrice(rs.getDouble("price"));
                result.add(payment);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of contests created by the specified user.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a user to get the list of contests for.
     * @return a <code>List</code> of <code>Competition</code> providing the details for located contests.
     * @throws SQLException if an SQL error occurs.
     */
    private List<Competition> getContestsForProject(long projectId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.*, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 25) AS admin_fee, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 24) AS dr_points, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 1) AS short_summary " +
                "FROM contest c " +
                "WHERE (c.deleted IS NULL OR c.deleted = 0) " +
                "AND c.tc_direct_project_id = ?");
            ps.setLong(1, projectId);
            rs = ps.executeQuery();

            List<Competition> result = new ArrayList<Competition>();
            while (rs.next()) {
                result.add(getCompetition(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of contests created by the specified user.</p>
     *
     * @param clientId a <code>long</code> providing the ID of a user to get the list of contests for.
     * @return a <code>List</code> of <code>Competition</code> providing the details for located contests.
     * @throws SQLException if an SQL error occurs.
     */
    private List<Competition> getContestsForClient(long clientId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.*, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 25) AS admin_fee, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 24) AS dr_points, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 1) AS short_summary " +
                "FROM contest c, " +
                "tc_direct_project p " +
                "WHERE (c.deleted IS NULL OR c.deleted = 0) " +
                "AND c.tc_direct_project_id = p.project_id " +
                "AND p.user_id = ?");
            ps.setLong(1, clientId);
            rs = ps.executeQuery();

            List<Competition> result = new ArrayList<Competition>();
            while (rs.next()) {
                result.add(getCompetition(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Locates the contest of status appropriate for document uploading from specified list of contests.</p>
     *
     * @param contests a <code>List</code> of contests.
     * @return a <code>StudioCompetition</code> with status appropriate for document uploading.
     */
    private StudioCompetition findContestForDocumentUpload(List<Competition> contests) throws Exception {
        for (Competition c : contests) {
            StudioCompetition contest = (StudioCompetition) c;
            long statusId = contest.getContestData().getStatusId();
            if (statusId == 15 || statusId == 1 || statusId == 5 || statusId == 9) {
                return contest;
            }
        }
        StudioCompetition contest = (StudioCompetition) contests.get(0);
        setContestStatus(contest.getId(), 15);
        contest.getContestData().setStatusId(15);
        return contest;
    }

    /**
     * <p>Sets the status of specified contest to specified value.</p>
     *
     * @param contestId a <code>long</code> providing contest ID.
     * @param statusId a <code>long</code> providing status ID.
     * @throws Exception if an unexpected error occurs.
     */
    private void setContestStatus(long contestId, int statusId) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement("UPDATE contest SET contest_detailed_status_id = ? " +
                                                  "WHERE contest_id = ?");
            ps.setLong(1, statusId);
            ps.setLong(2, contestId);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the competition from the data provided by the current row of specified result set.</p>
     *
     * @param rs a <code>ResultSet</code> providing the data for contest.
     * @return a <code>Competition</code> providing the contest data.
     * @throws SQLException if an SQL error occurs.
     */
    private Competition getCompetition(ResultSet rs) throws SQLException {
        ContestData data = new ContestData();

        data.setContestChannelId(1);
        data.setContestDescriptionAndRequirements("Desc");
        data.setContestId(-1);
        data.setContestTypeId(1);
        data.setCreatorUserId(rs.getLong("create_user_id"));
        data.setDocumentationUploads(new ArrayList<UploadedDocument>());
        data.setDurationInHours(96);
        data.setFinalFileFormat("doc");
        data.setForumId(-1);
        data.setForumPostCount(0);
        data.setLaunchDateAndTime(getXMLGregorianCalendar(rs.getTimestamp("start_time")));
        data.setMaximumSubmissions(10);
        data.setMedia(new ArrayList<MediumData>());
        data.setName("New Contest");
        data.setNotesOnWinnerSelection("Notes on winner");
        data.setNumberOfRegistrants(100);
        data.setOtherFileFormats("Other");
        data.setOtherRequirementsOrRestrictions("Other reqs");
        data.setPrizeDescription("Pirze desc");
        data.setProjectId(-1);
        data.setRequiredOrRestrictedColors("colros");
        data.setRequiredOrRestrictedFonts("Fonts");
        data.setSizeRequirements("Size");
        data.setStatusId(rs.getLong("contest_detailed_status_id"));
        data.setSubmissionCount(0);
        data.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(rs.getTimestamp("start_time")));


        StudioCompetition contest = new StudioCompetition(data);
        double fee = rs.getDouble("admin_fee");
        if (!rs.wasNull()) {
            contest.setAdminFee(fee);
        } else {
            contest.setAdminFee(-1);
        }
        data.setContestId(rs.getLong("contest_id"));
        contest.setCompetitionId(rs.getLong("contest_id"));
        contest.setCreatorUserId(rs.getLong("create_user_id"));
        double dr = rs.getDouble("dr_points");
        if (!rs.wasNull()) {
            contest.setDrPoints(dr);
        } else {
            contest.setDrPoints(-1);
        }
        contest.setEligibility("");
        contest.setEndTime(getXMLGregorianCalendar(rs.getTimestamp("end_time")));
        contest.setId(rs.getLong("contest_id"));
        contest.setPrizes(new ArrayList<CompetitionPrize>());
        contest.setProject(null);
        contest.setShortSummary(rs.getString("short_summary"));
        contest.setStartTime(getXMLGregorianCalendar(rs.getTimestamp("start_time")));
        contest.setType(CompetionType.STUDIO);
        data.setStatusId(rs.getLong("contest_detailed_status_id"));
        data.setTcDirectProjectId(rs.getLong("tc_direct_project_id"));
        return contest;
    }

    /**
     * <p>Gets the submission from the data provided by the current row of specified result set.</p>
     *
     * @param rs a <code>ResultSet</code> providing the data for submission.
     * @return a <code>SubmissionData</code> providing the submission data.
     * @throws Exception if an SQL error occurs.
     */
    private SubmissionData getSubmission(ResultSet rs) throws Exception {
        SubmissionData submission = new SubmissionData();
        submission.setContestId(rs.getLong("contest_id"));
        submission.setRank(rs.getInt("rank"));
        submission.setSubmissionId(rs.getLong("submission_id"));
        Timestamp date = rs.getTimestamp("submission_date");
        if (date != null) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            submission.setSubmittedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        } else {
            submission.setSubmittedDate(null);
        }
        submission.setSubmitterId(rs.getLong("submitter_id"));
        return submission;
    }

    /**
     * <p>Gets the list of all existing projects.</p>
     *
     * @return a <code>List</code> of <code>ProjectData</code> providing the details for all existing projects.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ProjectData> getAllProjects() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project");
            rs = ps.executeQuery();

            List<ProjectData> result = new ArrayList<ProjectData>();
            while (rs.next()) {
                ProjectData project = new ProjectData();
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setProjectId(rs.getLong("project_id"));
                result.add(project);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing contests.</p>
     *
     * @return a <code>List</code> of <code>Competition</code> providing the details for all existing contests.
     * @throws SQLException if an SQL error occurs.
     */
    private List<Competition> getAllContests() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.*, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 25) AS admin_fee, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 24) AS dr_points, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 1) AS short_summary " +
                "FROM contest c " +
                "WHERE (c.deleted IS NULL OR c.deleted = 0) AND NOT c.tc_direct_project_id IS NULL");
            rs = ps.executeQuery();

            List<Competition> result = new ArrayList<Competition>();
            while (rs.next()) {
                result.add(getCompetition(rs));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing contest types.</p>
     *
     * @return a <code>List</code> of <code>ContestTypeData</code> providing the details for all existing contest types.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ContestTypeData> getAllContestTypes() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM contest_type_lu");
            rs = ps.executeQuery();

            List<ContestTypeData> result = new ArrayList<ContestTypeData>();
            while (rs.next()) {
                ContestTypeData record = new ContestTypeData();
                record.setContestTypeId(rs.getLong("contest_type_id"));
                record.setDescription(rs.getString("contest_type_desc"));
                record.setRequirePreviewFile(rs.getBoolean("require_preview_file"));
                record.setRequirePreviewImage(rs.getBoolean("require_preview_image"));
                result.add(record);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing MIME types.</p>
     *
     * @return a <code>List</code> of <code>MimeType</code> providing the details for all existing MIME types.
     * @throws SQLException if an SQL error occurs.
     */
    private List<MimeType> getAllMimeTypes() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM mime_type_lu");
            rs = ps.executeQuery();

            List<MimeType> result = new ArrayList<MimeType>();
            while (rs.next()) {
                MimeType record = new MimeType();
                record.setMimeTypeId(rs.getLong("mime_type_id"));
                record.setDescription(rs.getString("mime_type_desc"));
                result.add(record);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing mediums.</p>
     *
     * @return a <code>List</code> of <code>MediumData</code> providing the details for all existing mediums.
     * @throws SQLException if an SQL error occurs.
     */
    private List<MediumData> getAllMediums() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM medium_lu");
            rs = ps.executeQuery();

            List<MediumData> result = new ArrayList<MediumData>();
            while (rs.next()) {
                MediumData record = new MediumData();
                record.setMediumId(rs.getLong("medium_id"));
                record.setDescription(rs.getString("medium_desc"));
                result.add(record);
            }
            
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing contest statuses.</p>
     *
     * @return a <code>List</code> of <code>ContestStatusData</code> providing the details for all existing contest
     *         statuses.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ContestStatusData> getAllStatuses() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM contest_detailed_status_lu");
            rs = ps.executeQuery();

            List<ContestStatusData> result = new ArrayList<ContestStatusData>();
            while (rs.next()) {
                ContestStatusData record = new ContestStatusData();
                record.setStatusId(rs.getLong("contest_detailed_status_id"));
                record.setDescription(rs.getString("contest_detailed_status_desc"));
                record.setName(rs.getString("name"));
                result.add(record);
            }

            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing submission file types.</p>
     *
     * @return a <code>List</code> of <code>String</code> providing the details for all existing submission file types.
     * @throws SQLException if an SQL error occurs.
     */
    private List<String> getAllSubmissionFileTypes() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM file_type_lu");
            rs = ps.executeQuery();

            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString("extension"));
            }

            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of projects created by the specified user.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a user to get the list of projects for.
     * @return a <code>List</code> of <code>ProjectData</code> providing the details for located projects.
     * @throws SQLException if an SQL error occurs.
     */
    private ProjectData getProject(long projectId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id = ?");
            ps.setLong(1, projectId);
            rs = ps.executeQuery();

            if (rs.next()) {
                ProjectData project = new ProjectData();
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setProjectId(rs.getLong("project_id"));
                return project;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Get the contest referenced by the specified ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get details for.
     * @return a <code>Competition</code> providing the details for requested contest or <code>null</code> if such
     *         contest is not found.
     * @throws SQLException if an SQL error occurs.
     */
    private Competition getContest(long contestId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.*, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 25) AS admin_fee, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 24) AS dr_points, " +
                "(SELECT property_value FROM contest_config g " +
                "WHERE g.contest_id = c.contest_id AND g.property_id = 1) AS short_summary " +
                "FROM contest c " +
                "WHERE c.contest_id = ?");
            ps.setLong(1, contestId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getCompetition(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Get the submission referenced by the specified ID.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to get details for.
     * @return a <code>SubmissionData</code> providing the details for requested submission or <code>null</code> if such
     *         contest is not found.
     * @throws Exception if an SQL error occurs.
     */
    private SubmissionData getSubmission(long submissionId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT s.* FROM submission s WHERE s.submission_id = ?");
            ps.setLong(1, submissionId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getSubmission(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Get the prize referenced by the specified ID.</p>
     *
     * @param prizeId a <code>long</code> providing the ID of a prize to get details for.
     * @return a <code>Prize</code> providing the details for requested prize or <code>null</code> if such
     *         prize is not found.
     * @throws Exception if an SQL error occurs.
     */
    private Prize getPrize(long prizeId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT s.* FROM prize s WHERE s.prize_id = ?");
            ps.setLong(1, prizeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Prize data = new Prize();
                data.setAmount(rs.getDouble("amount"));
                data.setPlace(rs.getInt("place"));
                data.setPrizeId(prizeId);
                data.setCreateDate(rs.getTimestamp("create_date"));
                return data;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of competitions created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of competitons for.
     * @return a <code>Map</code> mapping the project IDs to competition IDs. 
     * @throws SQLException if an SQL error occurs.
     */
    private Map<Long, Long> getCompetitions(long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.* FROM competition c, tc_direct_project p "
                                                  + "WHERE c.project_id = p.project_id AND p.user_id = ?");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            Map<Long, Long> result = new HashMap<Long, Long>();
            while (rs.next()) {
                result.put(rs.getLong("project_id"), rs.getLong("competition_id"));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Converts specified <code>XMLGregorianCalendar</code> instance into <code>Date</code> instance.</p>
     *
     * @param calendar an <code>XMLGregorianCalendar</code> representing the date to be converted.
     * @return a <code>Date</code> providing the converted value of specified calendar or <code>null</code> if specified
     *         <code>calendar</code> is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>Converts specified <code>Date</code> instance into <code>XMLGregorianCalendar</code> instance.</p>
     *
     * @param date a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value of specified date or <code>null</code>
     *         if specified <code>date</code> is <code>null</code> or if it can't be converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }
}
