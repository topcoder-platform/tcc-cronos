/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.bean.ContestManagerBean;
import com.topcoder.service.studio.contest.documentcontentservers.SocketDocumentContentServer;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Accuracy test cases for class <code>ContestManagerBean </code>.
 *
 * @author Chenhong
 * @version 1.1
 * @since 1.0
 */
public class TestContestManagerBeanAccuracy extends TestCase {
	/** Represents the ContestManagerBean instance for testing. */
	private ContestManagerBean manager = null;

	/**
	 * <p>
	 * The mock <code>SessionContext</code> for simulating the ejb
	 * environment.
	 * </p>
	 */
	private MockSessionContext context;

	/** Represents the EntityManager for testing. */
	private EntityManager entityManager = null;

	/**
	 * Represents the EntityTransaction instance for testing.
	 */
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
		context.addEntry("serverPort", new Integer(2000));

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
		DBUtil.clearDatabase();

		Thread.sleep(1000);
	}

	/**
	 * Test the ctor.
	 */
	public void testContestManagerBean() {
		assertNotNull("The ContestManagerBean should be created.", manager);
	}

	/**
	 * Test the CRUD method <code>Contest createContest(Contest contest) </code>.
	 * <p>
	 * The contest should be persisted into the database. The transaction will
	 * be commited.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testCreateContest() throws Exception {
		transaction = entityManager.getTransaction();

		transaction.begin();
		// persist StudioFileType.
		StudioFileType fileType = new StudioFileType();
		fileType.setDescription("desc");
		fileType.setExtension("ext");
		fileType.setImageFile(false);
		fileType.setSort(new Integer(10));
		entityManager.persist(fileType);

		// Persist ContestChannel.
		ContestChannel contestChannel = new ContestChannel();
		contestChannel.setDescription("desc");
		contestChannel.setName("name");
		contestChannel.setFileType(fileType);
		entityManager.persist(contestChannel);

		// Persist ContestType.
		ContestType contestType = new ContestType();
		contestType.setDescription("desc");
		contestType.setRequirePreviewFile(false);
		contestType.setRequirePreviewImage(false);
		entityManager.persist(contestType);

		// Persist ContestStatus.
		ContestStatus status = new ContestStatus();
		status.setDescription("description");
		status.setName("status");
		entityManager.persist(status);

		Date date = new Date();

		// create Contest for create.
		Contest contest = new Contest();
		contest.setName("contest1");
		contest.setCreatedUser(new Long(1));
		contest.setContestChannel(contestChannel);
		contest.setContestType(contestType);
		contest.setEndDate(date);
		contest.setEventId(new Long(1));
		contest.setForumId(new Long(1));
		contest.setProjectId(new Long(1));
		contest.setTcDirectProjectId(new Long(1));
		contest.setStartDate(new Date());
		contest.setStatus(status);
		contest.setWinnerAnnoucementDeadline(new Date());
		// Create the Contest in the bean.
		manager.createContest(contest);

		// try to find the Entity.
		Contest c = this.entityManager.find(Contest.class, contest.getContestId());
		assertNotNull("The contest must be created.", c);

		assertEquals("The name must be contest1.", "contest1", c.getName());

		assertEquals("The create User must be 1.", new Long(1), c.getCreatedUser());

		assertEquals("The status name must be 'status'", "status", c.getStatus().getName());

		// the Contest should be commited.
		entityManager.getTransaction().commit();
	}

	/**
	 * Test method <code>Contest getContest(long contestId) </code>.
	 *
	 * <p>
	 * The contest with id 1 does not existed. Null is expected.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContest_1() throws Exception {
		Contest ret = manager.getContest(100L);
		assertNull("The contest does not existed.", ret);
	}

	/**
	 * Test method <code>Contest getContest(long contestId) </code>.
	 *
	 * <p>
	 * The contest with id 1 does not existed. Null is expected.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContest_2() throws Exception {
		Long id = DBUtil.persisteOneContest(entityManager).getContestId();
		assertNotNull("The Contest id should not be null.", id);
		Contest ret = manager.getContest(id);

		assertNotNull("The Contest with id=" + id + " must exist.", ret);
	}

	/**
	 * Test method
	 * <code>List<Contest> getContestsForProject(long tcDirectProjectId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestsForProject_1() throws Exception {
		DBUtil.persisteOneContest(entityManager);

		List<Contest> ret = manager.getContestsForProject(1);
		assertEquals("There should be one Contest with project 1.", 1, ret.size());
		assertEquals("The contest name is 'contest1'", "contest1", ret.get(0).getName());
	}

	/**
	 * Test method
	 * <code>List<Contest> getContestsForProject(long tcDirectProjectId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestsForProject_2() throws Exception {

		List<Contest> ret = manager.getContestsForProject(1000);

		assertTrue("The return list should be empty.", ret.isEmpty());
	}

	/**
	 * Test method <code>void updateContest(Contest contest) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContest_1() throws Exception {
		// First persist one Contest.
		Contest contest = DBUtil.persisteOneContest(entityManager);

		contest.setCreatedUser(new Long(111));
		contest.setEventId(new Long(11));
		contest.setForumId(new Long(1111));
		contest.setStartDate(new Date());
		contest.setTcDirectProjectId(new Long(5));

		manager.updateContest(contest);
		Contest ret = manager.getContest(contest.getContestId());

		assertEquals("Equal is expected.", new Long(111), ret.getCreatedUser());
		assertEquals("Equal is expected.", new Long(11), ret.getEventId());

		assertEquals("Equal is expected.", new Long(5), ret.getTcDirectProjectId());
		assertEquals("Equal is expected.", new Long(1111), ret.getForumId());

	}

	/**
	 * Test method <code>void updateContest(Contest contest) </code>.
	 *
	 * <p>
	 * In this test case the ContestChannel is updated.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContest_2() throws Exception {
		// First persist one Contest.
		Contest contest = DBUtil.persisteOneContest(entityManager);

		ContestChannel channel = DBUtil.persistContestChannel(entityManager);

		contest.setContestChannel(channel);

		manager.updateContest(contest);
		Contest ret = manager.getContest(contest.getContestId());

		assertEquals("The ContestChannel name should be 'update'.", "update", ret.getContestChannel().getName());
	}

	/**
	 * Test method <code>void updateContest(Contest contest) </code>.
	 * <p>
	 * In this test case the ContestStatus of the Contest will be updated.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContest_3() throws Exception {
		// First persist one Contest.
		Contest contest = DBUtil.persisteOneContest(entityManager);

		ContestStatus status = DBUtil.persistContestStatus(entityManager);

		contest.setStatus(status);

		manager.updateContest(contest);
		Contest ret = manager.getContest(contest.getContestId());

		assertEquals("The ContestStatus name should be 'update'.", "update", ret.getStatus().getName());
	}

	/**
	 * Test method <code>void updateContest(Contest contest) </code>.
	 * <p>
	 * In this test case, the ContestType will be updated.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContest_4() throws Exception {
		// First persist one Contest.
		Contest contest = DBUtil.persisteOneContest(entityManager);

		ContestType type = DBUtil.persistContestType(entityManager);

		contest.setContestType(type);

		manager.updateContest(contest);
		Contest ret = manager.getContest(contest.getContestId());

		assertEquals("The ContestType des should be 'update'.", "update", ret.getContestType().getDescription());
	}

	/**
	 * Test method
	 * <code>updateContestStatus(long contestId, long newStatusId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContestStatusLongLong() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		ContestStatus status = DBUtil.persistContestStatus(entityManager);

		List<ContestStatus> list = new ArrayList<ContestStatus>();
		list.add(status);

		// Set the status to the next possible status.
		contest.getStatus().setStatuses(list);

		manager.updateContestStatus(contest.getContestId(), status.getContestStatusId());

		Contest ret = manager.getContest(contest.getContestId());

		assertEquals("Equal is expected.", status.getContestStatusId(), ret.getStatus().getContestStatusId());
	}

	/**
	 * Test method <code>long getClientForContest(long contestId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetClientForContest() throws Exception {
		DBUtil.prepareProject();
		Contest contest = DBUtil.persisteOneContest(entityManager);

		long ret = manager.getClientForContest(contest.getContestId());

		assertEquals("The client id must be 10.", 10, ret);
	}

	/**
	 * Test method <code> long getClientForProject(long projectId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetClientForProject() throws Exception {
		DBUtil.prepareProject();

		long ret = manager.getClientForProject(1L);

		assertEquals("Equal to 10.", 10, ret);
	}

	/**
	 * Test method
	 * <code>ContestStatus addContestStatus(ContestStatus contestStatus) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddContestStatus() throws Exception {
		entityManager.getTransaction().begin();

		ContestStatus status = new ContestStatus();
		status.setDescription("new");
		status.setName("review");

		ContestStatus added = manager.addContestStatus(status);

		ContestStatus ret = entityManager.find(ContestStatus.class, added.getContestStatusId());

		assertNotNull("The ContestStatus should be persisted.", ret);

		assertEquals("The name should be 'review'", "review", ret.getName());
		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code>updateContestStatus(ContestStatus contestStatus) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContestStatusContestStatus() throws Exception {
		ContestStatus status = DBUtil.persistContestStatus(entityManager);

		entityManager.getTransaction().begin();

		status.setDescription("update");
		status.setName("screening");

		manager.updateContestStatus(status);

		ContestStatus ret = entityManager.find(ContestStatus.class, status.getContestStatusId());

		assertEquals("Des should be 'update'", "update", ret.getDescription());

		assertEquals("Name should be 'screening'", "screening", ret.getName());
		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code>boolean removeContestStatus(long contestStatusId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveContestStatus_1() throws Exception {
		boolean ret = manager.removeContestStatus(100);

		assertFalse("The ContestStatus with id 100 does not exist.", ret);
	}

	/**
	 * Test method
	 * <code>boolean removeContestStatus(long contestStatusId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveContestStatus_2() throws Exception {
		ContestStatus status = DBUtil.persistContestStatus(entityManager);

		entityManager.getTransaction().begin();

		boolean ret = manager.removeContestStatus(status.getContestStatusId());

		assertTrue("True is expected.", ret);

		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code>ContestStatus getContestStatus(long contestStatusId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestStatus_1() throws Exception {
		assertNull("Not exist.", manager.getContestStatus(11111));
	}

	/**
	 * Test method
	 * <code>ContestStatus getContestStatus(long contestStatusId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestStatus_2() throws Exception {
		ContestStatus status = DBUtil.persistContestStatus(entityManager);

		ContestStatus ret = manager.getContestStatus(status.getContestStatusId());

		assertEquals("Equal is expected.", status.getDescription(), ret.getDescription());
		assertEquals("Equal is expected.", status.getName(), ret.getName());
	}

	/**
	 * Test method <code>Document addDocument(Document document) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddDocument() throws Exception {
		entityManager.getTransaction().begin();

		// persist StudioFileType.
		StudioFileType fileType = new StudioFileType();
		fileType.setDescription("desc");
		fileType.setExtension("ext");
		fileType.setImageFile(false);
		fileType.setSort(new Integer(10));
		entityManager.persist(fileType);

		Document document = new Document();
		document.setCreateDate(new Date());

		MimeType type = new MimeType();
		type.setDescription("des");
		type.setStudioFileType(fileType);
		entityManager.persist(type);

		document.setMimeType(type);

		document.setOriginalFileName("name");

		FilePath path = new FilePath();
		path.setModifyDate(new Date());
		path.setPath("test_files");
		entityManager.persist(path);

		document.setPath(path);

		document.setSystemFileName("test");

		DocumentType documentType = new DocumentType();
		documentType.setDescription("documentType");

		entityManager.persist(documentType);

		document.setType(documentType);

		Document doc = manager.addDocument(document);

		// the document must be committed into the database.
		entityManager.getTransaction().commit();

		assertNotNull("The document created should not be null.", doc);

		assertEquals("SThe system file name should be 'test'", "test", doc.getSystemFileName());
	}

	/**
	 * Test method <code> void updateDocument(Document document) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateDocument() throws Exception {
		Document document = DBUtil.persisteDocument(entityManager);

		entityManager.getTransaction().begin();

		StudioFileType fileType = new StudioFileType();
		fileType.setDescription("desc");
		fileType.setExtension("ext");
		fileType.setImageFile(false);
		fileType.setSort(new Integer(10));
		entityManager.persist(fileType);

		MimeType type = new MimeType();
		type.setDescription("update");
		type.setStudioFileType(fileType);
		entityManager.persist(type);

		document.setMimeType(type);

		FilePath path = new FilePath();
		path.setModifyDate(new Date());
		path.setPath("update");
		entityManager.persist(path);
		document.setPath(path);

		DocumentType documentType = new DocumentType();
		documentType.setDescription("update");

		entityManager.persist(documentType);

		document.setType(documentType);

		document.setOriginalFileName("update");
		document.setSystemFileName("update");

		manager.updateDocument(document);

		entityManager.getTransaction().commit();

		// validate if update succesfully.
		Document ret = entityManager.find(Document.class, document.getDocumentId());
		assertEquals("Equal to 'update'", "update", ret.getMimeType().getDescription());
		assertEquals("Equal to 'update'", "update", ret.getOriginalFileName());
		assertEquals("Equal to 'update'", "update", ret.getPath().getPath());

		assertEquals("Equal to 'update'", "update", ret.getType().getDescription());
	}

	/**
	 * Test method <code>Document getDocument(long documentId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetDocument_1() throws Exception {
		assertNull("The document does not exist.", manager.getDocument(1));
	}

	/**
	 * Test method <code>Document getDocument(long documentId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetDocument_2() throws Exception {
		Document document = DBUtil.persisteDocument(entityManager);

		Document ret = manager.getDocument(document.getDocumentId());

		assertNotNull("The document should exist.", ret);
	}

	/**
	 * Test method <code>boolean removeDocument(long documentId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveDocument_1() throws Exception {
		boolean ret = manager.removeDocument(1);

		assertFalse(ret);
	}

	/**
	 * Test method <code>boolean removeDocument(long documentId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveDocument_2() throws Exception {
		Document document = DBUtil.persisteDocument(entityManager);

		entityManager.getTransaction().begin();
		boolean ret = manager.removeDocument(document.getDocumentId());

		assertTrue(ret);

		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code>void addDocumentToContest(long documentId, long contestId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddDocumentToContest() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		Document document = DBUtil.persisteDocument(entityManager);

		Document document2 = DBUtil.persisteDocument(entityManager);

		entityManager.getTransaction().begin();
		manager.addDocumentToContest(document2.getDocumentId(), contest.getContestId());
		entityManager.getTransaction().commit();

		assertTrue(contest.getDocuments().contains(document2));
		assertTrue(document2.getContests().contains(contest));
	}

	/**
	 * Test method
	 * <code>boolean removeDocumentFromContest(long documentId, long contestId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveDocumentFromContest_1() throws Exception {
		Document document = DBUtil.persisteDocument(entityManager);
		Contest contest = DBUtil.persisteOneContest(entityManager);

		boolean ret = manager.removeDocumentFromContest(document.getDocumentId(), contest.getContestId());

		assertFalse(ret);
	}

	/**
	 * Test method
	 * <code>boolean removeDocumentFromContest(long documentId, long contestId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveDocumentFromContest_2() throws Exception {
		Document document = DBUtil.persisteDocument(entityManager);
		Contest contest = DBUtil.persisteOneContest(entityManager);

		entityManager.getTransaction().begin();
		manager.addDocumentToContest(document.getDocumentId(), contest.getContestId());
		boolean ret = manager.removeDocumentFromContest(document.getDocumentId(), contest.getContestId());

		assertTrue(ret);
		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code>ContestChannel addContestChannel(ContestChannel contestChannel) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddContestChannel() throws Exception {

		entityManager.getTransaction().begin();

		StudioFileType fileType = new StudioFileType();
		fileType.setDescription("updateContest");
		fileType.setExtension("doc");
		fileType.setImageFile(false);
		fileType.setSort(new Integer(100));

		entityManager.persist(fileType);

		ContestChannel channel = new ContestChannel();
		channel.setDescription("des1");
		channel.setFileType(fileType);
		channel.setName("name1");

		ContestChannel ret = manager.addContestChannel(channel);
		entityManager.getTransaction().commit();

		assertNotNull(ret);
		assertEquals("Name should be 'name1'", "name1", ret.getName());
	}

	/**
	 * Test method
	 * <code>updateContestChannel(ContestChannel contestChannel) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testUpdateContestChannel() throws Exception {
		ContestChannel channel = DBUtil.persistContestChannel(entityManager);

		entityManager.getTransaction().begin();

		StudioFileType fileType = new StudioFileType();
		fileType.setDescription("updateContest");
		fileType.setExtension("doc");
		fileType.setImageFile(false);
		fileType.setSort(new Integer(100));

		entityManager.persist(fileType);

		channel.setDescription("des2");
		channel.setFileType(fileType);
		channel.setName("name2");

		manager.updateContestChannel(channel);
		entityManager.getTransaction().commit();

		ContestChannel ret = entityManager.find(ContestChannel.class, channel.getContestChannelId());

		assertEquals("Name should be 'name2'", "name2", ret.getName());

	}

	/**
	 * Test method
	 * <code>boolean removeContestChannel(long contestChannelId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveContestChannel_1() throws Exception {
		assertFalse(manager.removeContestChannel(1));
	}

	/**
	 * Test method
	 * <code>boolean removeContestChannel(long contestChannelId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemoveContestChannel_2() throws Exception {
		ContestChannel channel = DBUtil.persistContestChannel(entityManager);

		entityManager.getTransaction().begin();

		boolean ret = manager.removeContestChannel(channel.getContestChannelId());

		assertTrue(ret);

		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code>ContestChannel getContestChannel(long contestChannelId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestChannel_1() throws Exception {
		assertNull(manager.getContestChannel(1));
	}

	/**
	 * Test method
	 * <code>ContestChannel getContestChannel(long contestChannelId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestChannel_2() throws Exception {
		ContestChannel c = DBUtil.persistContestChannel(entityManager);

		assertNotNull(manager.getContestChannel(c.getContestChannelId()));
	}

	/**
	 * Test method
	 * <code> ContestConfig addConfig(ContestConfig contestConfig) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddConfig() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		entityManager.getTransaction().begin();
		ContestConfig config = new ContestConfig();
		config.setValue("value");

		ContestProperty property = new ContestProperty();
		property.setDescription("description");

		entityManager.persist(property);

		config.setProperty(property);
		config.setContest(contest);

		ContestConfig ret = manager.addConfig(config);

		entityManager.getTransaction().commit();

		assertNotNull(ret);

		assertEquals("The value should be 'value'", "value", ret.getValue());
	}

	/**
	 * Test method
	 * <code>saveDocumentContent(long documentId, byte[] documentContent) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSaveDocumentContent() throws Exception {
		Document document = DBUtil.persisteDocument(entityManager);

		SocketDocumentContentServer server = new SocketDocumentContentServer(2000, 0);
		server.start();

		Thread.sleep(2000);
		try {
			manager.saveDocumentContent(document.getDocumentId(), "abc".getBytes());

		} finally {
			server.stop();

			Thread.sleep(2000);
		}
	}

	/**
	 * Test method <code>byte[] getDocumentContent(long documentId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetDocumentContent() throws Exception {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		for (int i = 0; i < 1100; i++) {
			stream.write("abcde".getBytes());
		}

		Document document = DBUtil.persisteDocument(entityManager);

		SocketDocumentContentServer server = new SocketDocumentContentServer(2000, 0);
		server.start();

		try {

			byte[] content = manager.getDocumentContent(document.getDocumentId());

			assertEquals("The size should be equal", 3, content.length);
		} finally {
			server.stop();
		}
	}

	/**
	 * Test method
	 * <code> public boolean existDocumentContent(long documentId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testExistDocumentContent() throws Exception {
		Document doc = DBUtil.persisteDocument(entityManager);

		SocketDocumentContentServer server = new SocketDocumentContentServer(2000, 0);
		server.start();

		try {
			boolean ret = manager.existDocumentContent(doc.getDocumentId());

			assertTrue(ret);
		} finally {
			server.stop();
		}
	}

	/**
	 * Test method <code>List<ContestStatus> getAllContestStatuses() </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetAllContestStatuses_1() throws Exception {
		List<ContestStatus> ret = manager.getAllContestStatuses();

		assertTrue(ret.isEmpty());
	}

	/**
	 * Test method <code>List<ContestStatus> getAllContestStatuses() </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetAllContestStatuses_2() throws Exception {
		DBUtil.persistContestStatus(entityManager);

		List<ContestStatus> ret = manager.getAllContestStatuses();

		assertEquals("Equal to 1.", 1, ret.size());
	}

	/**
	 * Test method <code>List<ContestChannel> getAllContestChannels() </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetAllContestChannels() throws Exception {
		DBUtil.persistContestChannel(entityManager);

		List<ContestChannel> ret = manager.getAllContestChannels();

		assertEquals("Equal to 1.", 1, ret.size());
	}

	/**
	 * Test method <code>List<StudioFileType> getAllStudioFileTypes() </code>.
	 *
	 * @throws Exception
	 *             to junit.
	 */
	public void testGetAllStudioFileTypes() throws Exception {
		DBUtil.persistContestChannel(entityManager);

		List<StudioFileType> ret = manager.getAllStudioFileTypes();

		assertEquals("Euqal to 1.", 1, ret.size());
	}

	/**
	 * Test method <code> ContestTypeConfig addContestTypeConfig </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddContestTypeConfig() throws Exception {
		entityManager.getTransaction().begin();
		ContestTypeConfig config = new ContestTypeConfig();
		config.setPropertyValue("value");
		config.setRequired(true);

		ContestType type = new ContestType();
		type.setDescription("d");
		type.setRequirePreviewFile(true);
		type.setRequirePreviewImage(false);

		entityManager.persist(type);

		config.setType(type);

		ContestProperty contestProperty = new ContestProperty();
		contestProperty.setDescription("des");
		entityManager.persist(contestProperty);

		config.setProperty(contestProperty);

		manager.addContestTypeConfig(config);

		entityManager.getTransaction().commit();

	}

	/**
	 * Test method
	 * <code>void addPrizeToContest(long contestId, long prizeId) </code>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testAddPrizeToContest() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		entityManager.getTransaction().begin();
		Prize prize = new Prize();
		prize.setAmount(new Double(1000));
		prize.setCreateDate(new Date());
		prize.setPlace(new Integer(1));

		PrizeType prizeType = new PrizeType();
		prizeType.setDescription("des");
		entityManager.persist(prizeType);

		prize.setType(prizeType);

		entityManager.persist(prize);

		entityManager.getTransaction().commit();

		// The above code should be ok.

		Prize ret = entityManager.find(Prize.class, prize.getPrizeId());

		entityManager.getTransaction().begin();
		manager.addPrizeToContest(contest.getContestId(), ret.getPrizeId());

		entityManager.getTransaction().commit();
	}

	/**
	 * Test method
	 * <code> boolean removePrizeFromContest(long contestId, long prizeId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testRemovePrizeFromContest() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		entityManager.getTransaction().begin();
		Prize prize = new Prize();
		prize.setAmount(new Double(1000));
		prize.setCreateDate(new Date());
		prize.setPlace(new Integer(1));

		PrizeType prizeType = new PrizeType();
		prizeType.setDescription("des");
		entityManager.persist(prizeType);

		prize.setType(prizeType);

		entityManager.persist(prize);

		entityManager.getTransaction().commit();

		// The above code should be ok.

		Prize ret = entityManager.find(Prize.class, prize.getPrizeId());

		entityManager.getTransaction().begin();
		manager.addPrizeToContest(contest.getContestId(), ret.getPrizeId());

		boolean res = manager.removePrizeFromContest(contest.getContestId(), ret.getPrizeId());
		entityManager.getTransaction().commit();

		assertTrue("The prize should be removed.", res);
	}

	/**
	 * Test method <code>List<Prize> getContestPrizes(long contestId) </code>.
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testGetContestPrizes() throws Exception {

		Contest contest = DBUtil.persisteOneContest(entityManager);

		List<Prize> list = manager.getContestPrizes(contest.getContestId());

		assertTrue(list.isEmpty());
	}

	/**
	 * Test method <code>public List<Contest> getAllContests() </code>.
	 *
	 * <p>
	 * Persists one Contset and try to get it back.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 * @since 1.1
	 */
	public void testGetAllContests_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		List<Contest> ret = manager.getAllContests();

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getName(), ret.get(0).getName());
	}

	/**
	 * Test method <code>public List<Contest> getAllContests() </code>.
	 *
	 * <p>
	 * In this case, no contest in the database.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 * @since 1.1
	 */
	public void testGetAllContests_2() throws Exception {
		List<Contest> ret = manager.getAllContests();

		assertEquals("Equalt is expected to 0.", 0, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * In this test case, first persist a Contest with the name and try to
	 * filter it out.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_contest_name() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestNameFilter(contest.getName());
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getName(), ret.get(0).getName());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * In this test case, no contest with the name to search.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchContestsWithFilter_contest_name_2() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestNameFilter("does not existed.");
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 0.", 0, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestFileTypeIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchContestsWithFilter_StudioContestFileTypeIdFilter_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		long id = contest.getContestChannel().getFileType().getStudioFileType();
		Filter filter = ContestFilterFactory.createStudioContestFileTypeIdFilter(id);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
		Long result = new Long(ret.get(0).getContestChannel().getFileType().getStudioFileType());
		assertEquals("Equal is expected.", new Long(id), result);
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestFileTypeIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchContestsWithFilter_StudioContestFileTypeIdFilter_2() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Contest anotherContest = DBUtil.persisteOneContest(entityManager);

		long id = anotherContest.getContestChannel().getFileType().getStudioFileType();
		Filter filter = ContestFilterFactory.createStudioContestFileTypeIdFilter(id);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", anotherContest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioFileTypeExtensionFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_StudioFileTypeExtensionFilter() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		String extension = contest.getContestChannel().getFileType().getExtension();
		Filter filter = ContestFilterFactory.createStudioFileTypeExtensionFilter(extension);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());

		assertEquals(extension, ret.get(0).getContestChannel().getFileType().getExtension());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestChannelIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_StudioFileTypeExtensionFilter_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		long id = contest.getContestChannel().getContestChannelId();
		Filter filter = ContestFilterFactory.createStudioContestChannelIdFilter(id);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestChannelIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_StudioFileTypeExtensionFilter_2() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Contest anotherContest = DBUtil.persisteOneContest(entityManager);

		long id = anotherContest.getContestChannel().getContestChannelId();
		Filter filter = ContestFilterFactory.createStudioContestChannelIdFilter(id);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", anotherContest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if createStudioContestChannelNameFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestChannelNameFilter_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestChannelNameFilter("name");

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if createStudioContestChannelNameFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestChannelNameFilter_2() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Contest anotherContest = DBUtil.persisteOneContest(entityManager);

		// both contests have the same channel name.

		Filter filter = ContestFilterFactory.createStudioContestChannelNameFilter("name");

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestStatusIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestStatusIdFilter() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		long id = contest.getStatus().getContestStatusId();
		Filter filter = ContestFilterFactory.createStudioContestStatusIdFilter(id);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if ContestStatusNameFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createContestStatusNameFilter_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		String statusName = contest.getStatus().getName();
		Filter filter = ContestFilterFactory.createStudioContestStatusNameFilter(statusName);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if ContestStatusNameFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createContestStatusNameFilter_2() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		// create another 2 contest.
		DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		// all contestStatus of the the contests are the same.

		String statusName = contest.getStatus().getName();
		Filter filter = ContestFilterFactory.createStudioContestStatusNameFilter(statusName);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 3.", 3, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestId can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestId() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestIdFilter(contest.getContestId());

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestNameFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestNameFilter_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestNameFilter(contest.getName());

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
		assertEquals("Equal is expected.", contest.getName(), ret.get(0).getName());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestNameFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestNameFilter_2() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestNameFilter(contest.getName());

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());

	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestProjectIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestProjectIdFilter() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestProjectIdFilter(1);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
		assertEquals("Equal is expected.", ret.get(0).getProjectId(), new Long(1));
		assertEquals("Equal is expected.", ret.get(1).getProjectId(), new Long(1));
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestDirectProjectIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestDirectProjectIdFilter() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestDirectProjectIdFilter(1);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
		assertEquals("Equal is expected.", ret.get(0).getTcDirectProjectId(), new Long(1));
		assertEquals("Equal is expected.", ret.get(1).getTcDirectProjectId(), new Long(1));
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestForumIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestForumIdFilter() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestForumIdFilter(1);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
		assertEquals("Equal is expected.", ret.get(0).getForumId(), new Long(1));
		assertEquals("Equal is expected.", ret.get(1).getForumId(), new Long(1));
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestEventIdFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestEventIdFilter() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestEventIdFilter(1);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());

		assertEquals("Equal is expected.", contest.getContestId(), ret.get(0).getContestId());
		assertEquals("Equal is expected.", ret.get(0).getEventId(), new Long(1));
		assertEquals("Equal is expected.", ret.get(1).getEventId(), new Long(1));
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestStartDateFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestStartDateFilter_1() throws Exception {
		Contest contest = DBUtil.persisteOneContest(entityManager);
		DBUtil.persisteOneContest(entityManager);

		Filter filter = ContestFilterFactory.createStudioContestStartDateFilter(new Date());

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 0.", 0, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestStartDateFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestStartDateFilter_2() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Filter filter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("txt", ret.get(0).getContestChannel().getFileType().getExtension());
		assertEquals("Equal is expected.", "dev", ret.get(0).getName());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestEndDateFilter can work properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestEndDateFilter() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Filter filter = ContestFilterFactory.createStudioContestEndDateFilter(endDate);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("txt", ret.get(0).getContestChannel().getFileType().getExtension());
		assertEquals("Equal is expected.", "dev", ret.get(0).getName());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if StudioContestWinnerAnnouncementDeadlineDateFilter can work
	 * properly.
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_createStudioContestWinnerAnnouncementDeadlineDateFilter() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Filter filter = ContestFilterFactory.createStudioContestWinnerAnnouncementDeadlineDateFilter(winDate);

		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("txt", ret.get(0).getContestChannel().getFileType().getExtension());
		assertEquals("Equal is expected.", "dev", ret.get(0).getName());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the AndFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_AndFilter_1() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter filter2 = ContestFilterFactory.createStudioContestStatusNameFilter("review_2");

		Filter filter3 = ContestFilterFactory.createStudioContestProjectIdFilter(2);

		Filter filter = ContestFilterFactory.createAndFilter(new Filter[] { filter1, filter2, filter3 });

		// the contest2 matchs.
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 1.", 1, ret.size());

		assertEquals("txt2", ret.get(0).getContestChannel().getFileType().getExtension());
		assertEquals("Equal is expected.", "dev_2", ret.get(0).getName());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the AndFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_AndFilter_2() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter filter2 = ContestFilterFactory.createStudioContestStatusNameFilter("review_2");

		Filter filter3 = ContestFilterFactory.createStudioContestProjectIdFilter(2);

		Filter f4 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");

		Filter filter = ContestFilterFactory.createAndFilter(new Filter[] { filter1, filter2, filter3, f4 });

		// no contest match.
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 0.", 0, ret.size());

	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the AndFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_AndFilter_3() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(3), new Long(2), winDate, new Long(10));

		Contest contest3 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channe4", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter filter2 = ContestFilterFactory.createStudioContestStatusNameFilter("review_2");

		Filter filter3 = ContestFilterFactory.createStudioContestProjectIdFilter(2);

		Filter filter = ContestFilterFactory.createAndFilter(new Filter[] { filter1, filter2, filter3 });

		// no contest match.
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 0.", 0, ret.size());

	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the OFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_OrFilter_1() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter filter2 = ContestFilterFactory.createStudioContestStatusNameFilter("review");

		Filter filter3 = ContestFilterFactory.createStudioContestProjectIdFilter(2);

		Filter filter = ContestFilterFactory.createOrFilter(new Filter[] { filter1, filter2, filter3 });

		// both contests matchs the filter condition.
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 2.", 2, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the OFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_OrFilter_2() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Contest contest3 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestChannelNameFilter("channel13");
		Filter filter2 = ContestFilterFactory.createStudioContestStatusNameFilter("screening");

		Filter filter3 = ContestFilterFactory.createStudioContestProjectIdFilter(12);

		Filter f4 = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

		Filter filter = ContestFilterFactory.createOrFilter(new Filter[] { filter1, filter2, filter3, f4 });

		// both contests matchs the filter condition.
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 3.", 3, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the NotFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_NotFilter_1() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(2);

		Filter filter = ContestFilterFactory.createNotFilter(filter1);

		// no contest match project id not (2);
		List<Contest> ret = manager.searchContests(filter);

		assertEquals("Equalt is expected to 0.", 0, ret.size());
	}

	/**
	 * Test method <code>List<Contest> searchContests(Filter filter) </code>.
	 *
	 * <p>
	 * Test if the NotFilter filter can work properly
	 * </p>
	 *
	 * @throws Exception
	 *             to junit
	 */
	public void testSearchCOntestsWithFilter_NotFilter_2() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(1), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(2);

		Filter filter = ContestFilterFactory.createNotFilter(filter1);

		// all contests match project id not (2);
		List<Contest> ret = manager.searchContests(filter);

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
	public void testSearchCOntestsWithFilter_ComplexFilter_1() throws Exception {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 1000 * 10000);
		Date winDate = new Date(System.currentTimeMillis() + 10000 * 1000000);

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(1);

		Filter filter = ContestFilterFactory.createNotFilter(filter1);

		Filter filter2 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
		Filter filter3 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter channelNameFilter = ContestFilterFactory.createOrFilter(new Filter[] { filter2, filter3 });

		Filter startDateFilter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

		Filter f1 = ContestFilterFactory.createStudioContestNameFilter("dev");
		Filter f2 = ContestFilterFactory.createStudioContestNameFilter("dev_2");

		Filter nameFilter = ContestFilterFactory.createOrFilter(new Filter[] { f1, f2 });
		Filter[] filters = new Filter[] { filter, channelNameFilter, startDateFilter, nameFilter };
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

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(1);

		Filter filter = ContestFilterFactory.createNotFilter(filter1);

		Filter filter2 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
		Filter filter3 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter channelNameFilter = ContestFilterFactory.createOrFilter(new Filter[] { filter2, filter3 });

		Filter startDateFilter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

		Filter f1 = ContestFilterFactory.createStudioContestNameFilter("dev");
		Filter f2 = ContestFilterFactory.createStudioContestNameFilter("dev_2");

		Filter nameFilter = ContestFilterFactory.createOrFilter(new Filter[] { f1, f2 });
		Filter[] filters = new Filter[] { filter, channelNameFilter, startDateFilter, nameFilter };
		Filter searchFilter = ContestFilterFactory.createAndFilter(filters);

		// no contest match the complex search condition.
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

		Contest contest1 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt", "channel", "tc", "review",
				"dev", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Contest contest2 = DBUtil.createOneContest(entityManager, startDate, endDate, "txt2", "channel2", "tc2",
				"review_2", "dev_2", new Long(1), new Long(2), new Long(2), new Long(2), winDate, new Long(10));

		Filter filter1 = ContestFilterFactory.createStudioContestProjectIdFilter(1);

		Filter filter = ContestFilterFactory.createNotFilter(filter1);

		Filter filter2 = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
		Filter filter3 = ContestFilterFactory.createStudioContestChannelNameFilter("channel2");
		Filter channelNameFilter = ContestFilterFactory.createOrFilter(new Filter[] { filter2, filter3 });

		Filter startDateFilter = ContestFilterFactory.createStudioContestStartDateFilter(startDate);

		Filter f1 = ContestFilterFactory.createStudioContestNameFilter("dev");
		Filter f2 = ContestFilterFactory.createStudioContestNameFilter("dev_2");

		Filter nameFilter = ContestFilterFactory.createOrFilter(new Filter[] { f1, f2 });
		Filter[] filters = new Filter[] { filter, channelNameFilter, startDateFilter, nameFilter };
		Filter searchFilter = ContestFilterFactory.createAndFilter(filters);

		Filter notFilter1 = ContestFilterFactory.createNotFilter(searchFilter);
		Filter notFilter2 = ContestFilterFactory.createNotFilter(notFilter1);
		// all contest match the complex search condition.
		List<Contest> ret = manager.searchContests(notFilter2);

		assertEquals("Equalt is expected to 2.", 2, ret.size());
	}
}
