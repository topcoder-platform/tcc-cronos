/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageBean;
import com.orpheus.administration.persistence.MessageRemote;
import com.orpheus.administration.persistence.MessageRemoteHome;
import com.orpheus.administration.persistence.RemoteOrpheusMessageDataStore;
import com.orpheus.administration.persistence.impl.RSSItemTranslator;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.RSSPersistenceException;
import com.topcoder.util.rssgenerator.SearchCriteria;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>Accuracy test cases for class RemoteOrpheusMessageDataStore.</p>
 * @author waits
 * @version 1.0
 */
public class RemoteOrpheusMessageDataStoreAccTests extends TestCase {
    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** RemoteOrpheusMessageDataStore instance. */
    private RemoteOrpheusMessageDataStore remote = null;
    private RSSItemTranslator translator = null;

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
        //for mock user transaction
        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        this.remote = new RemoteOrpheusMessageDataStore(TestHelper.REMOTE_MESSENGER_DATA_SOURCE_NAMESPACE);

        translator = new RSSItemTranslator();
    }

    /**
     * <p>
     * Test createFeed method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateFeed() throws Exception {
        try {
            remote.createFeed(null);
            fail("This method is unsupported.");
        } catch (UnsupportedOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test updateFeed method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateFeed() throws Exception {
        try {
            remote.updateFeed(null);
            fail("This method is unsupported.");
        } catch (UnsupportedOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test deleteFeed method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testDeleteFeed() throws Exception {
        try {
            remote.deleteFeed(null);
            fail("This method is unsupported.");
        } catch (UnsupportedOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test create Item method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testCreateItem() throws Exception {
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, new Date(1000));

        remote.createItem((RSSItem) translator.assembleMessageVO(toPersist));

        //    	get from database
        Message msg = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toPersist, msg);
    }

    /**
     * <p>
     * Test update Item method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testUpdateItem() throws Exception {
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, new Date(1000));

        //persis the item
        RSSItem item = (RSSItem) translator.assembleMessageVO(toPersist);
        remote.createItem(item);

        //update the item
        Message toUpdate = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT + TestHelper.CONTENT, new Date(2000));
        RSSItem updatedItem = (RSSItem) translator.assembleMessageVO(toUpdate);

        remote.updateItem(updatedItem);

        //    	get from database
        Message msg = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toUpdate, msg);
    }

    /**
     * <p>
     * Test fineItem method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testFindItem() throws Exception {
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, new Date(1000));

        //persis the item
        RSSItem item = (RSSItem) translator.assembleMessageVO(toPersist);
        remote.createItem(item);

        RSSItem[] items = remote.findItems(new SearchCriteria() {
                    public Filter getSearchFilter() {
                        return new EqualToFilter("guid", TestHelper.GUID);
                    }
                });

        assertEquals("The items size is invalid.", 1, items.length);
        TestHelper.assertEquals(toPersist, translator.assembleMessageDTO(items[0]));
    }

    /**
     * <p>
     * Test delete Item method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testDeleteItem() throws Exception {
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, new Date(1000));
        RSSItem item = (RSSItem) translator.assembleMessageVO(toPersist);
        remote.createItem(item);

        //delete item
        remote.deleteItem(item);

        try {
            remote.updateItem(item);
            fail("Should be deleted.");
        } catch (RSSPersistenceException e) {
            //good
        }
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
