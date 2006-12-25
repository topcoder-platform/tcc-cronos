/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.InputStream;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.orpheus.game.persistence.ejb.GameDataBean;
import com.topcoder.web.frontcontroller.results.DownloadData;
import com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException;

import junit.framework.TestCase;

/**
 * Unit test for <code>LocalCustomDownloadSource</code> exception.
 *
 * @author waits
 * @version 1.0
 */
public class LocalCustomDownloadSourceUnitTests extends TestCase {

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** mock user trnasaction. */
    private MockUserTransaction mockTransaction;

    /** the ballColor lists. */
    private List colors = null;

    /**
     * Deploys and creates EJBs needed for our tests.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();

        //prepared database/data
        List results = TestHelper.prepareDatabase();
        colors = (List) results.get(2);

        //add config files
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);

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
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("game/GameDataBean",
                GameDataLocalHome.class, GameDataLocal.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
    }

    /**
     * test the ctor.
     *
     * @throws Exception into JUnit
     */
    public void testCtor() throws Exception {
        CustomDownloadSource source = new LocalCustomDownloadSource(TestHelper.LOCAL_NAMESPACE);
        assertNotNull("Fail to instantiate the CustomDownloadSource instance.", source);
    }
    /**
     * <p>
     * Test the ctor of the LocalCustomDownloadSource, the namespace is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new LocalCustomDownloadSource(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the ctor of the LocalCustomDownloadSource, the namespace is empty, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new LocalCustomDownloadSource(" ");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the ctor of the LocalCustomDownloadSource, the namespace does not exist, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCtor_notExistNamespace() throws Exception {
        try {
            new LocalCustomDownloadSource("notExistNamespace");
            fail("The namespace does not exist.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the ctor of the LocalCustomDownloadSource, the 'jndiEjbReference' is missing, InstantiationException
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCtor_missingEJBReference() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.addConfigFile("invalidConfig/CustomDownload_Local_Config_missingJNDI.xml");

        try {
            new LocalCustomDownloadSource(TestHelper.REMOTE_NAMESPACE);
            fail("the 'jndiEjbReference' is missing.");
        } catch (InstantiationException e) {
            //good
        }
    }

    /**
     * Test the getDownloadData method. It is an accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testGetDownloadData() throws Exception {
        CustomDownloadSource source = new LocalCustomDownloadSource(TestHelper.LOCAL_NAMESPACE);

        DownloadData data = source.getDownloadData("" + ((BallColor) this.colors.get(0)).getImageId());
        assertNotNull("The data is null.", data);
        assertEquals("The mediaType is not the same.", "html/text", data.getMediaType());
        assertEquals("The name is not the same.", "html", data.getSuggestedName());

        InputStream stream = data.getContent();
        byte[] out = new byte["This is the content.".getBytes().length];
        stream.read(out);
        assertEquals("The content is not the same.", "This is the content.", new String(out));
    }

    /**
     * Test the ejbDownloadData method. It is an accuracy test case.
     *
     * @throws Exception into JUnit
     */
    public void testGetEJBDownloadData() throws Exception {
        CustomDownloadSource source = new LocalCustomDownloadSource(TestHelper.LOCAL_NAMESPACE);

        DownloadData data = source.ejbGetDownloadData("" + ((BallColor) this.colors.get(0)).getImageId());
        assertNotNull("The data is null.", data);
        assertEquals("The mediaType is not the same.", "html/text", data.getMediaType());
        assertEquals("The name is not the same.", "html", data.getSuggestedName());

        InputStream stream = data.getContent();
        byte[] out = new byte["This is the content.".getBytes().length];
        stream.read(out);
        assertEquals("The content is not the same.", "This is the content.", new String(out));
    }

    /**
     * Test the getDownloadData method. The download data does not exist, DownloadDataRetrievalException expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetDownloadData_notExistData() throws Exception {
        CustomDownloadSource source = new LocalCustomDownloadSource(TestHelper.LOCAL_NAMESPACE);

        long downloadId = ((BallColor) this.colors.get(0)).getImageId() +
            ((BallColor) this.colors.get(1)).getImageId();

        try {
            source.getDownloadData("" + downloadId);
            fail("not Exist download data.");
        } catch (DownloadDataRetrievalException e) {
            //good
        }
    }

    /**
     * remove the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.clearDatabase();
    }

}
