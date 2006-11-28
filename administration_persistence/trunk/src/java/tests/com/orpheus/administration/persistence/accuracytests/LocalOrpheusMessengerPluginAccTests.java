/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.orpheus.administration.persistence.LocalOrpheusMessengerPlugin;
import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageBean;
import com.orpheus.administration.persistence.MessageLocal;
import com.orpheus.administration.persistence.MessageLocalHome;
import com.orpheus.administration.persistence.impl.AdminMessageTranslator;

import com.topcoder.message.messenger.MessageAPI;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>Accuracy test cases for class LocalOrpheusMessengerPlugin.</p>
 * @author waits
 * @version 1.0
 */
public class LocalOrpheusMessengerPluginAccTests extends TestCase {
    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** LocalOrpheusMessengerPlugin instance. */
    private LocalOrpheusMessengerPlugin plugin = null;

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
        SessionBeanDescriptor msgLocalDes = new SessionBeanDescriptor("admin/Message", MessageLocalHome.class,
                MessageLocal.class, new MessageBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(msgLocalDes);
        //for mock user transaction
        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        this.plugin = new LocalOrpheusMessengerPlugin(TestHelper.LOCAL_MESSENGER_PLUGIN_NAMESPACE);
    }

    /**
     * <p>
     * Test createMessage method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateMessage() throws Exception {
        this.plugin.createMessage();
    }

    /**
     * Test the send message method.
     *
     * @throws Exception into Junit
     */
    public void testSendMessage() throws Exception {
        Date timestamp = new Date(1000);
        Message toPersist = TestHelper.buildMessage(TestHelper.GUID, TestHelper.CATEGORY, TestHelper.CONTENT_TYPE,
                TestHelper.CONTENT, timestamp);
        AdminMessageTranslator translator = new AdminMessageTranslator();
        //send message
        plugin.sendMessage((MessageAPI) translator.assembleMessageVO(toPersist));

        //get from database
        Message msg = TestHelper.getMessageFromDB(connection, TestHelper.GUID);

        //verify
        TestHelper.assertEquals(toPersist, msg);
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
