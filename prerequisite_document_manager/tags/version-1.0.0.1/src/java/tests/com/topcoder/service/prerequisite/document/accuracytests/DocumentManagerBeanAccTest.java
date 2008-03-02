/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.service.prerequisite.document.BaseTestCase;
import com.topcoder.service.prerequisite.document.DocumentManager;
import com.topcoder.service.prerequisite.document.ejb.DocumentManagerRemote;
import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;
import com.topcoder.service.prerequisite.document.model.MemberAnswer;

/**
 * <p>
 * Accuracy test for <code>DocumentManagerBean</code> class.
 * </p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class DocumentManagerBeanAccTest extends TestCase {
    /**
     * <p>
     * Represents the date format for parsing date string.
     * </p>
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * Represents the <code>DocumentManager</code> instance.
     * </p>
     */
    private DocumentManager manager;

    /**
     * <p>
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Get file path.
     * </p>
     *
     * @param path of file
     * @return file path.
     * @throws Exception to JUnit
     */
    private static String getFilePath(String path) throws Exception {
        return DocumentManagerBeanAccTest.class.getResource(path).toURI().getPath();
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename
     *            the file name.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = dbConnectionFactory.createConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            bufferedReader = new BufferedReader(new FileReader(filename));
            while (null != (sql = bufferedReader.readLine())) {
                stmt.executeUpdate(sql);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * <p>
     * Closes the connection. It will be used in finally block.
     * </p>
     *
     * @param conn
     *            the database connection.
     */
    public static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the result set. It will be used in finally block.
     * </p>
     *
     * @param rs
     *            the result set.
     */
    public static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the statement. It will be used in finally block.
     * </p>
     *
     * @param stmt
     *            the statement.
     */
    public static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        Map<String, File> map = new HashMap<String, File>();
        map.put("InformixDBConnectionFactory", new File(getFilePath("/InformixDBConnectionFactory.xml")));
        ConfigurationFileManager configurationFileManager =
            new ConfigurationFileManager(map);

        dbConnectionFactory = new DBConnectionFactoryImpl(configurationFileManager
                .getConfiguration("InformixDBConnectionFactory"));

        executeScriptFile(getFilePath("/clean.sql"));

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "user");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        manager = (DocumentManagerRemote) new InitialContext(env).lookup(BaseTestCase.JNDI_NAME);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        executeScriptFile(getFilePath("/clean.sql"));
        super.tearDown();
    }

    /**
     * <p>
     * Test addDocument, getDocument, getAllDocuments.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDocument() throws Exception {

        assertEquals(0, this.manager.getAllDocuments().size());
        assertNull(this.manager.getDocument(Long.MAX_VALUE));

        Document document = new Document();
        document.setCreateDate(new Date());
        document = this.manager.addDocument(document);

        //Get by id
        Document got = this.manager.getDocument(document.getDocumentId());

        this.assertDocument(document, got);

        //Get all
        List<Document> gots = this.manager.getAllDocuments();
        assertEquals(1, gots.size());
        this.assertDocument(document, gots.get(0));
    }

    /**
     * Assert two Documents equal.
     * @param one Document
     * @param two Document
     */
    private void assertDocument(Document one, Document two) {
        assertEquals(one.getDescription(), two.getDescription());
        assertEquals(one.getDocumentId().longValue(), two.getDocumentId().longValue());
        assertEquals(DATE_FORMAT.format(one.getCreateDate()), DATE_FORMAT.format(two.getCreateDate()));
    }

    /**
     * <p>
     * Test addDocumentName, getDocumentName, getAllDocumentNames.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDocumentName() throws Exception {

        assertEquals(0, this.manager.getAllDocumentNames().size());
        assertNull(this.manager.getDocumentName(Long.MAX_VALUE));

        DocumentName documentName = new DocumentName();
        documentName.setName("name");
        documentName = this.manager.addDocumentName(documentName);

        //Get by id
        DocumentName got = this.manager.getDocumentName(documentName.getDocumentNameId());

        this.assertDocumentName(documentName, got);

        //Get all
        List<DocumentName> gots = this.manager.getAllDocumentNames();
        assertEquals(1, gots.size());
        this.assertDocumentName(documentName, gots.get(0));
    }

    /**
     * Assert two DocumentNames equal.
     * @param one DocumentName
     * @param two DocumentName
     */
    private void assertDocumentName(DocumentName one, DocumentName two) {
        assertEquals(one.getName(), two.getName());
        assertEquals(one.getDocumentNameId().longValue(), two.getDocumentNameId().longValue());
    }

    /**
     * Assert two DocumentVersions equal.
     * @param one DocumentVersion
     * @param two DocumentVersion
     */
    private void assertDocumentVersion(DocumentVersion one, DocumentVersion two) {
        assertEquals(one.getContent(), two.getContent());
        assertEquals(one.getDocumentVersionId(), two.getDocumentVersionId());
        assertEquals(one.getDocumentVersion(), two.getDocumentVersion());
        assertEquals(DATE_FORMAT.format(one.getVersionDate()), DATE_FORMAT.format(two.getVersionDate()));
        this.assertDocument(one.getDocument(), two.getDocument());
        this.assertDocumentName(one.getDocumentName(), two.getDocumentName());
    }

    /**
     * Create a DocumentVersion.
     * @param document Document
     * @param documentName DocumentName
     * @param version version
     * @return DocumentVersion created
     */
    private DocumentVersion createDocumentVersion(Document document, DocumentName documentName,
        Integer version) {
        DocumentVersion dv = new DocumentVersion();
        dv.setContent("content");
        dv.setDocument(document);
        dv.setDocumentName(documentName);
        if (version != null) {
            dv.setDocumentVersion(version);
        }
        dv.setVersionDate(new Date());
        return dv;
    }

    /**
     * Add a DocumentVersion.
     * @param document Document
     * @param documentName DocumentName
     * @param version version
     * @return DocumentVersion added
     * @throws Exception to JUnit
     */
    private DocumentVersion addDocumentVersion(Document document, DocumentName documentName,
            Integer version) throws Exception {
        DocumentVersion dv = this.createDocumentVersion(document, documentName, version);

        return this.manager.addDocumentVersion(dv);
    }

    /**
     * Add a DocumentVersion with version number specified.
     * @return DocumentVersion added
     * @throws Exception to JUnit
     */
    private DocumentVersion addDocumentVersion() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = this.manager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("name");
        documentName = this.manager.addDocumentName(documentName);

        //Add a DocumentVersion with version number specified
        return this.addDocumentVersion(document, documentName, 1);
    }

    /**
     * <p>
     * Test addDocumentVersion, getDocumentVersion.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDocumentVersion() throws Exception {

        //Add a DocumentVersion with version number specified
        DocumentVersion dv = this.addDocumentVersion();

        //Get by id
        DocumentVersion got = this.manager.getDocumentVersion(dv.getDocumentVersionId());

        this.assertDocumentVersion(dv, got);

        //Add another DocumentVersion with null version number
        DocumentVersion dv2 = this.addDocumentVersion(dv.getDocument(), dv.getDocumentName(), null);

        //The version number should be set with the latest version of the corresponding document plus one.
        assertEquals(dv.getDocumentVersion() + 1, dv2.getDocumentVersion().intValue());

        //Get by id
        got = this.manager.getDocumentVersion(dv2.getDocumentVersionId());

        this.assertDocumentVersion(dv2, got);

        //Get by document id and version number
        this.assertDocumentVersion(dv,
            this.manager.getDocumentVersion(dv.getDocument().getDocumentId(), dv.getDocumentVersion()));
        this.assertDocumentVersion(dv2,
            this.manager.getDocumentVersion(dv2.getDocument().getDocumentId(), dv2.getDocumentVersion()));
    }

    /**
     * <p>
     * Test updateDocumentVersion.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateDocumentVersion() throws Exception {

        //Add a DocumentVersion with version number specified
        DocumentVersion dv = this.addDocumentVersion();

        Document document2 = new Document();
        document2.setCreateDate(new Date());
        document2 = this.manager.addDocument(document2);

        DocumentName documentName2 = new DocumentName();
        documentName2.setName("name2");
        documentName2 = this.manager.addDocumentName(documentName2);

        //Update the DocumentVersion with new properties
        dv.setContent("new content");
        dv.setDocument(document2);
        dv.setDocumentName(documentName2);
        dv.setDocumentVersion(2);
        dv.setVersionDate(new Date());

        //Update it
        this.manager.updateDocumentVersion(dv);

        this.assertDocumentVersion(dv, this.manager.getDocumentVersion(document2.getDocumentId(), 2));
    }

    /**
     * Assert two CompetitionDocuments equal.
     * @param one CompetitionDocument
     * @param two CompetitionDocument
     */
    private void assertCompetitionDocument(CompetitionDocument one, CompetitionDocument two) {
        assertEquals(one.getRoleId().longValue(), two.getRoleId().longValue());
        assertEquals(one.getCompetitionId().longValue(), two.getCompetitionId().longValue());
        assertEquals(one.getCompetitionDocumentId().longValue(), two.getCompetitionDocumentId().longValue());
        this.assertDocumentVersion(one.getDocumentVersion(), two.getDocumentVersion());
    }

    /**
     * Create a CompetitionDocument.
     * @param dv DocumentVersion
     * @param competitionId competitionId
     * @param roleId roleId
     * @return CompetitionDocument created
     */
    private CompetitionDocument createCompetitionDocument(DocumentVersion dv, long competitionId, long roleId) {
        CompetitionDocument cd = new CompetitionDocument();
        cd.setDocumentVersion(dv);
        cd.setCompetitionId(competitionId);
        cd.setRoleId(roleId);
        return cd;
    }

    /**
     * Add a CompetitionDocument.
     * @param dv DocumentVersion
     * @param competitionId competitionId
     * @param roleId roleId
     * @return CompetitionDocument added
     * @throws Exception to JUnit
     */
    private CompetitionDocument addCompetitionDocument(DocumentVersion dv, long competitionId, long roleId)
        throws Exception {
        CompetitionDocument cd = this.createCompetitionDocument(dv, competitionId, roleId);

        return this.manager.addCompetitionDocument(cd);
    }

    /**
     * Add a CompetitionDocument.
     * @param competitionId competitionId
     * @param roleId roleId
     * @return CompetitionDocument added
     * @throws Exception to JUnit
     */
    private CompetitionDocument addCompetitionDocument(long competitionId, long roleId) throws Exception {
        CompetitionDocument cd = this.createCompetitionDocument(this.addDocumentVersion(), competitionId, roleId);

        return this.manager.addCompetitionDocument(cd);
    }

    /**
     * <p>
     * Test addCompetitionDocument, getCompetitionDocument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCompetitionDocument() throws Exception {
        assertNull(this.manager.getCompetitionDocument(Long.MAX_VALUE));
        assertNull(this.manager.getCompetitionDocument(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE));
        assertEquals(0, this.manager.getCompetitionDocumentsByRole(Long.MAX_VALUE).size());
        assertEquals(0, this.manager.getCompetitionDocumentsByCompetition(Long.MAX_VALUE).size());
        assertEquals(0, this.manager.getCompetitionDocuments(Long.MAX_VALUE, Long.MAX_VALUE).size());

        CompetitionDocument cd = this.addCompetitionDocument(1L, 1L);

        //Get by id
        this.assertCompetitionDocument(cd, this.manager.getCompetitionDocument(cd.getCompetitionDocumentId()));

        //Get by documentVersionId, competitionId and roleId
        this.assertCompetitionDocument(cd, this.manager.getCompetitionDocument(
            cd.getDocumentVersion().getDocumentVersionId(),
            cd.getCompetitionId(), cd.getRoleId()));

        //Get by roleId
        this.assertCompetitionDocument(cd, this.manager.getCompetitionDocumentsByRole(cd.getRoleId()).get(0));

        //Get by competitionId
        this.assertCompetitionDocument(cd,
            this.manager.getCompetitionDocumentsByCompetition(cd.getCompetitionId()).get(0));

        //Get by competitionId and roleId
        this.assertCompetitionDocument(cd, this.manager.getCompetitionDocuments(
            cd.getCompetitionId(), cd.getRoleId()).get(0));
    }

    /**
     * <p>
     * Test deleteCompetitionDocument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCompetitionDocument() throws Exception {
        CompetitionDocument cd1 = this.addCompetitionDocument(1L, 1L);

        //cd2 and cd3 have same competitionId and roleId
        CompetitionDocument cd2 = this.addCompetitionDocument(1L, 2L);
        CompetitionDocument cd3 = this.addCompetitionDocument(1L, 2L);

        //cd4 and cd5 have same competitionId
        CompetitionDocument cd4 = this.addCompetitionDocument(2L, 1L);
        CompetitionDocument cd5 = this.addCompetitionDocument(2L, 2L);

        CompetitionDocument cd = new CompetitionDocument();
        cd.setCompetitionDocumentId(Long.MAX_VALUE);
        assertFalse(this.manager.deleteCompetitionDocument(cd));
        assertFalse(this.manager.deleteCompetitionDocuments(Long.MAX_VALUE));
        assertFalse(this.manager.deleteCompetitionDocuments(Long.MAX_VALUE, Long.MAX_VALUE));

        //Delete by id
        assertTrue(this.manager.deleteCompetitionDocument(cd1));

        //cd1 should be deleted
        assertNull(this.manager.getCompetitionDocument(cd1.getCompetitionDocumentId()));

        //Delete by competitionId and roleId
        assertTrue(this.manager.deleteCompetitionDocuments(cd2.getCompetitionId(), cd2.getRoleId()));

        //cd2 and cd3 should be deleted
        assertNull(this.manager.getCompetitionDocument(cd2.getCompetitionDocumentId()));
        assertNull(this.manager.getCompetitionDocument(cd3.getCompetitionDocumentId()));

        //Delete by competitionId
        assertTrue(this.manager.deleteCompetitionDocuments(cd4.getCompetitionId()));

        //cd4 and cd5 should be deleted
        assertNull(this.manager.getCompetitionDocument(cd4.getCompetitionDocumentId()));
        assertNull(this.manager.getCompetitionDocument(cd5.getCompetitionDocumentId()));
    }

    /**
     * <p>
     * Test updateCompetitionDocument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCompetitionDocument() throws Exception {
        CompetitionDocument cd = this.addCompetitionDocument(1L, 1L);

        //Update CompetitionDocument with new properties
        cd.setCompetitionId(2L);
        cd.setRoleId(2L);
        cd.setDocumentVersion(this.addDocumentVersion());

        //Update it
        this.manager.updateCompetitionDocument(cd);

        this.assertCompetitionDocument(cd, this.manager.getCompetitionDocument(cd.getCompetitionDocumentId()));
    }

    /**
     * Assert two MemberAnsweres equal.
     * @param one MemberAnswer
     * @param two MemberAnswer
     */
    private void assertMemberAnswer(MemberAnswer one, MemberAnswer two) {
        assertEquals(one.getAnswer(), two.getAnswer());
        assertEquals(DATE_FORMAT.format(one.getAnswerDate()), DATE_FORMAT.format(two.getAnswerDate()));
        assertEquals(one.getMemberAnswerId(), two.getMemberAnswerId());
        assertEquals(one.getMemberId(), two.getMemberId());

        this.assertCompetitionDocument(one.getCompetitionDocument(), two.getCompetitionDocument());
    }

    /**
     * Add a MemberAnswer.
     *
     * @param cd CompetitionDocument
     * @param answer answer
     * @param memberId memberId
     * @return MemberAnswer added
     * @throws Exception to JUnit
     */
    private MemberAnswer addMemberAnswer(CompetitionDocument cd, boolean answer, long memberId) throws Exception {
        MemberAnswer ma = new MemberAnswer();
        ma.setAnswer(answer);
        ma.setAnswerDate(new Date());
        ma.setMemberId(memberId);
        ma.setCompetitionDocument(cd);

        return this.manager.addMemberAnswer(ma);
    }

    /**
     * <p>
     * Test addMemberAnswer, deleteMemberAnswer, getMemberAnswer.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testMemberAnswer() throws Exception {
        MemberAnswer ma = this.addMemberAnswer(this.addCompetitionDocument(1L, 2L), true, 1);

        this.assertMemberAnswer(ma, this.manager.getMemberAnswer(ma.getMemberAnswerId()));

        //Delete it
        assertTrue(this.manager.deleteMemberAnswer(ma.getMemberAnswerId()));

        assertNull(this.manager.getMemberAnswer(ma.getMemberAnswerId()));

        assertFalse(this.manager.deleteMemberAnswer(ma.getMemberAnswerId()));
    }

    /**
     * <p>
     * Test updateMemberAnswer.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateMemberAnswer() throws Exception {
        MemberAnswer ma = this.addMemberAnswer(this.addCompetitionDocument(1L, 2L), false, 2);

        //Update MemberAnswer with new properties
        ma.setAnswer(false);
        ma.setAnswerDate(new Date());
        ma.setMemberId(2L);
        ma.setCompetitionDocument(this.addCompetitionDocument(1L, 1L));

        //Update it
        this.manager.updateMemberAnswer(ma);

        this.assertMemberAnswer(ma, this.manager.getMemberAnswer(ma.getMemberAnswerId()));
    }

    /**
     * <p>
     * Test isCompetitionDocumentAnswered.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testIsMemberAnswered() throws Exception {
        MemberAnswer ma = this.addMemberAnswer(this.addCompetitionDocument(1L, 2L), true, 2);

        assertTrue(this.manager.isCompetitionDocumentAnswered(ma.getCompetitionDocument().getCompetitionDocumentId(),
            ma.getMemberId()));

        //Delete it
        this.manager.deleteMemberAnswer(ma.getMemberAnswerId());

        //Now should be not-answered
        assertFalse(this.manager.isCompetitionDocumentAnswered(ma.getCompetitionDocument().getCompetitionDocumentId(),
                ma.getMemberId()));
    }

    /**
     * <p>
     * Test one-to-many DocumentVersion.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOneToManyDocumentVersion() throws Exception {
        Document document = new Document();
        document.setCreateDate(new Date());
        document = this.manager.addDocument(document);

        DocumentName documentName = new DocumentName();
        documentName.setName("name");
        documentName = this.manager.addDocumentName(documentName);

        DocumentVersion dv1 = this.addDocumentVersion(document, documentName, 1);
        DocumentVersion dv2 = this.addDocumentVersion(document, documentName, 2);

        document = this.manager.getDocument(document.getDocumentId());
        assertEquals(2, document.getDocumentVersions().size());
        Iterator <DocumentVersion> it = document.getDocumentVersions().iterator();

        DocumentVersion got1 = it.next();
        if (got1.getDocumentVersionId().longValue() == dv1.getDocumentVersionId().longValue()) {
            this.assertDocumentVersion(got1, dv1);
            this.assertDocumentVersion(it.next(), dv2);
        } else {
            this.assertDocumentVersion(got1, dv2);
            this.assertDocumentVersion(it.next(), dv1);
        }

        documentName = this.manager.getDocumentName(documentName.getDocumentNameId());
        assertEquals(2, documentName.getDocumentVersions().size());
        it = documentName.getDocumentVersions().iterator();

        got1 = it.next();
        if (got1.getDocumentVersionId().longValue() == dv1.getDocumentVersionId().longValue()) {
            this.assertDocumentVersion(got1, dv1);
            this.assertDocumentVersion(it.next(), dv2);
        } else {
            this.assertDocumentVersion(got1, dv2);
            this.assertDocumentVersion(it.next(), dv1);
        }
    }

    /**
     * <p>
     * Test one-to-many CompetitionDocument.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOneToManyCompetitionDocument() throws Exception {

        DocumentVersion dv = this.addDocumentVersion();

        CompetitionDocument cd1 = this.addCompetitionDocument(dv, 1L, 2L);
        CompetitionDocument cd2 = this.addCompetitionDocument(dv, 2L, 1L);

        dv = this.manager.getDocumentVersion(dv.getDocumentVersionId());
        assertEquals(2, dv.getCompetitionDocuments().size());
        Iterator <CompetitionDocument> it = dv.getCompetitionDocuments().iterator();

        CompetitionDocument got1 = it.next();
        if (got1.getCompetitionDocumentId().longValue() == cd1.getCompetitionDocumentId().longValue()) {
            this.assertCompetitionDocument(got1, cd1);
            this.assertCompetitionDocument(it.next(), cd2);
        } else {
            this.assertCompetitionDocument(got1, cd2);
            this.assertCompetitionDocument(it.next(), cd1);
        }
    }

    /**
     * <p>
     * Test one-to-many MemberAnswer.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOneToManyMemberAnswer() throws Exception {

        CompetitionDocument cd = this.addCompetitionDocument(1L, 1L);

        MemberAnswer ma1 = this.addMemberAnswer(cd, true, 1);
        MemberAnswer ma2 = this.addMemberAnswer(cd, false, 2);

        cd = this.manager.getCompetitionDocument(cd.getCompetitionDocumentId());
        assertEquals(2, cd.getMemberAnswers().size());
        Iterator <MemberAnswer> it = cd.getMemberAnswers().iterator();

        MemberAnswer got1 = it.next();
        if (got1.getMemberAnswerId().longValue() == ma1.getMemberAnswerId().longValue()) {
            this.assertMemberAnswer(got1, ma1);
            this.assertMemberAnswer(it.next(), ma2);
        } else {
            this.assertMemberAnswer(got1, ma2);
            this.assertMemberAnswer(it.next(), ma1);
        }
    }
}
