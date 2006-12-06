/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.orpheus.game.persistence.CustomDownloadSource;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.RemoteCustomDownloadSource;
import com.orpheus.game.persistence.ejb.GameDataBean;
import com.topcoder.web.frontcontroller.results.DownloadData;
import junit.framework.TestCase;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.InputStream;

/**
 * Accuracy test cases for <code>RemoteCustomDownloadSource</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class RemoteCustomDownloadSourceAccTests extends TestCase {

    /**
     * The namespace to initializeRemoteCustomDownloadSource.
     */
    private static final String NAMESPACE = CustomDownloadSource.class.getName() + ".remote";

    /**
     * The context used in test cases.
     */
    private Context context;

    /**
     * Mock user transaction.
     */
    private MockUserTransaction mockTransaction;

    /**
     * Set up for each test case.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearAllConfigurationNS();

        //add config files
        AccuracyHelper.loadBaseConfig();

        // create test data in the database
        AccuracyHelper.setupDatabase();

        // set up MockContextFactory
        MockContextFactory.setAsInitial();

        // initialize the context
        context = new InitialContext();

        // set up MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // create descriptor
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataHome.class, GameData.class, new GameDataBean());

        // deploy
        mockContainer.deploy(sampleServiceDescriptor);

        // set up MockUserTransaction
        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * Clean up for each test case.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.cleanupDatabase();
        AccuracyHelper.clearAllConfigurationNS();
    }

    /**
     * Accuracy test of the constructor.
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        CustomDownloadSource cds = new
                RemoteCustomDownloadSource(NAMESPACE);
        assertNotNull("Fail to instantiate the CustomDownloadSource instance.", cds);
    }

    /**
     * Accuracy test of the <code>getDownloadData()</code> method. Data should be retrieved from the database
     * properly.
     *
     * @throws Exception into JUnit.
     */
    public void testGetDownloadData() throws Exception {
        CustomDownloadSource source = new RemoteCustomDownloadSource(NAMESPACE);

        DownloadData data = source.getDownloadData("1");
        assertNotNull("Cannot retrieve download data.", data);
        assertEquals("Not the expected media type.", "html/text", data.getMediaType());
        assertEquals("Not the expected suggested name.", "name", data.getSuggestedName());

        InputStream is = data.getContent();
        assertEquals("Not the expected content.", "content", AccuracyHelper.convertISToString(is));
    }
}
