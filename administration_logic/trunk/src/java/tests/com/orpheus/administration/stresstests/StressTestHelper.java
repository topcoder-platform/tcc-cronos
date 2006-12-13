/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.xerces.parsers.DOMParser;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Stress testing helper class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
class StressTestHelper {
    /**
     * Represents the admin data home.
     */
    public static final String ADMIN_DATA_JNDI_NAME = "AdminDataHome";

    /**
     * Represents the game data home.
     */
    public static final String GAME_DATA_JNDI_NAME = "GameDataHome";

    /**
     * Represents the date format.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm";

    /**
     * Represent the file name of insert data sql scripts.
     */
    public static final String PROFILE_SESSION_KEY = "user_profile_session_key";

    /**
     * <p>
     * Represents the small iteration number for stress testing.
     * </p>
     */
    public static final int SMALL = 1;

    /**
     * <p>
     * Represents the medium iteration number for stress testing.
     * </p>
     */
    public static final int MEDIUM = 1;

    /**
     * <p>
     * Represents the large iteration number for stress testing.
     * </p>
     */
    public static final int LARGE = 1;

    /**
     * Represent the file name of insert data sql scripts.
     */
    private static final String INSERT_SQL_FILE = "test_files/sql/insert.sql";

    /**
     * Represent the file name of delete data sql scripts.
     */
    private static final String DELETE_SQL_FILE = "test_files/sql/delete.sql";

    /**
     * <p>
     * private constructor preventing instantiation.
     * </p>
     */
    private StressTestHelper() {
    }

    /**
     * <p>
     * Load all namespaces and setup database for testing.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public static void prepareTest() throws Exception {
        Locale.setDefault(Locale.US);
        ConfigManager cfg = ConfigManager.getInstance();

        releaseNamespace();
        // load namespace
        cfg.add("stresstests/Administration_Logic.xml");
        cfg.add("DBConnectionFactoryImpl.xml");

        executeSqlFile(DELETE_SQL_FILE);
        executeSqlFile(INSERT_SQL_FILE);

        deployEJB();
    }

    /**
     * <p>
     * Release all test namespaces and clear database data from ConfigManager.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public static void clearTestEnvironment() throws Exception {
        executeSqlFile(DELETE_SQL_FILE);
        releaseNamespace();
    }

    /**
     * <p>
     * Release all test namespaces from ConfigManager.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    static void releaseNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Execute the sql statements in a file.
     * @param filename
     *            the sql file.
     * @throws Exception
     *             to JUnit
     */
    private static void executeSqlFile(String filename) throws Exception {
        Reader file = new FileReader(filename);
        char[] buffer = new char[1024];
        int retLength = 0;
        StringBuffer content = new StringBuffer();

        while ((retLength = file.read(buffer)) >= 0) {
            content.append(buffer, 0, retLength);
        }

        file.close();

        List sqls = new ArrayList();
        int lastIndex = 0;

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        // get the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Statement stmt = conn.createStatement();

        try {
            // excute the sql in the file
            for (int i = 0; i < sqls.size(); i++) {
                stmt.executeUpdate((String) sqls.get(i));
            }
        } finally {
            stmt.close();
            conn.close();
        }
    }

    /**
     * Gets the document element from the given xml string.
     * @param xml
     *            the xml string
     * @return the document element of the given xml string
     * @throws IOException
     *             if failed to read xml
     * @throws SAXException
     *             if failed to parse the given xml
     */
    public static Element loadXmlString(String xml) throws SAXException, IOException {
        // parse the xml source
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new StringReader(xml)));

        // return the document element
        return parser.getDocument().getDocumentElement();
    }

    /**
     * Deploy ejb using mock ejb.
     * @throws Exception
     *             to Junit
     */
    public static void deployEJB() throws Exception {
        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(new SessionBeanDescriptor(GAME_DATA_JNDI_NAME, GameDataHome.class, GameData.class,
            new MockGameDataBean()));
        mockContainer.deploy(new SessionBeanDescriptor(ADMIN_DATA_JNDI_NAME, AdminDataHome.class, AdminData.class,
            new MockAdminDataBean()));
    }
}
