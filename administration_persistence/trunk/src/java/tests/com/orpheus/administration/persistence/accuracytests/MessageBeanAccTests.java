/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.administration.persistence.DuplicateEntryException;
import com.orpheus.administration.persistence.EntryNotFoundException;
import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageBean;
import com.orpheus.administration.persistence.MessageRemote;
import com.orpheus.administration.persistence.MessageRemoteHome;
import com.orpheus.administration.persistence.impl.RSSSearchCriteriaTranslator;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.SearchCriteria;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>Accuracy test cases for class MessageBean.</p>
 * @author waits
 * @version 1.0
 */
public class MessageBeanAccTests extends TestCase {
    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** MessageRemote interface. */
    MessageRemote remote = null;

    /**
     * create instance.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //add config files
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.ADMINISTRATION_PERSISTENCE_CONFIG_FILE);
        TestHelper.addConfigFile(TestHelper.SEARCH_BUNDLE_CONFIG_FILE);
        //      insert data to database for testing
        connection = TestHelper.getConnection();

        //clear the database
        TestHelper.clearDatabase(connection);

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor msgRemoteDes = new SessionBeanDescriptor("admin/Message", MessageRemoteHome.class,
                MessageRemote.class, new MessageBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(msgRemoteDes);

        //      Lookup the home
        Object msgHomeObj = context.lookup("admin/Message");

        // PortableRemoteObject does not do anything in MockEJB but it does no harm to call it
        MessageRemoteHome msgHome = (MessageRemoteHome) PortableRemoteObject.narrow(msgHomeObj,
                MessageRemoteHome.class);

        // create the bean
        remote = msgHome.create();

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * <p>
     * Test the create message.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        remote.createMessage(toPersist);

        //get from database
        Message msg = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toPersist, msg);
    }

    /**
     * Test the updateMessage(message) method.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        remote.createMessage(toPersist);

        //update the message and persist
        timestamp = new Date(2000);

        Message toUpdate = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT + TestHelper.CONTENT, timestamp);
        remote.updateMessage(toUpdate);

        //get message from db
        Message updated = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toUpdate, updated);

        try {
            remote.createMessage(updated);
            fail("The message should be in db now.");
        } catch (DuplicateEntryException e) {
            //good
        }
    }

    /**
     * <p>
     * Test deleteMessage(message) method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testDeleteMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        remote.createMessage(toPersist);

        //delete the message
        remote.deleteMessage(toPersist);

        try {
            remote.updateMessage(toPersist);
            fail("Should be deleted.");
        } catch (EntryNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Test find message method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testFindMessages() throws Exception {
        //create message
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        remote.createMessage(toPersist);

        //find the mssages
        Message[] messages = remote.findMessages(new RSSSearchCriteriaTranslator().assembleSearchCriteriaDTO(
                    new SearchCriteria() {
                    public Filter getSearchFilter() {
                        return new EqualToFilter("guid", TestHelper.GUID);
                    }
                }));

        //verify
        assertEquals("The size of messages array is invalid.", 1, messages.length);
        TestHelper.assertEquals(toPersist, messages[0]);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            TestHelper.clearConfigManager();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
