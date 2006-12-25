/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * The helper class for failure tests.
 *
 * @author assistant
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * Represents the max id.
     */
    public static final int MAX_ID = 5;

    /**
     * The private constructor to make this un-instantiatable.
     */
    private FailureTestHelper() {
        // do nothing
    }

    /**
     * Parse the string representation of an xml element.
     * @param xml the string representation of an xml element
     * @return the parsed element
     * @throws Exception to invoker
     */
    public static Element parseElement(String xml) throws Exception, IOException, ParserConfigurationException {
        // Create a builder factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        Document doc = factory.newDocumentBuilder().parse(new StringBufferInputStream(xml));
        return doc.getDocumentElement();
    }

    /**
     * Load the configurations.
     * @throws Exception to invoker
     */
    public static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failuretests/db_connection_factory.xml");
        cm.add("failuretests/game_persistence.xml");
        cm.add("failuretests/object_factory.xml");
        cm.add("failuretests/random_string_image.xml");
        cm.add("failuretests/colors.xml");
    }

    /**
     * Load the specified xml configuration file.
     * @param file the file location to load.
     * @throws Exception to invoker
     */
    public static void loadConfig(String file) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(file);
    }
    /**
     * Unload the configurations.
     * @throws Exception to invoker
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Load data.
     * @throws Exception to JUnit
     */
    public static void loadData() throws Exception, ConfigurationException {
        Connection conn = null;

        try {
            DBConnectionFactory dbFactory
                = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
            conn = dbFactory.createConnection();

            unloadData();

            // persist the colors
            PreparedStatement stmt =
                conn.prepareStatement("INSERT INTO download_obj(media_type,suggested_name,content) VALUES(?,?,?)");
            stmt.setString(1, "html/text");
            stmt.setString(2, "html");
            stmt.setBytes(3, "This is the content.".getBytes());
            stmt.execute();

            stmt = conn.prepareStatement("INSERT INTO ball_color(name,download_obj_id)VALUES(?,?)");
            for (int i = 0; i < 1; i++) {
                stmt.setString(1, "colorName" + i);
                stmt.setLong(2, getId("download_obj"));
                stmt.execute();
            }

            stmt.close();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Get the one and only one item's id in the table.
     * @param table the table to check
     * @return the id
     * @throws Exception to invoker
     */
    public static long getId(String table) throws Exception {
        Connection conn = null;

        try {
            DBConnectionFactory dbFactory
                = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
            conn = dbFactory.createConnection();
            PreparedStatement stmt = conn.prepareStatement("select id from " + table);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getLong(1);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return 1;
    }

    /**
     * Clear the database.
     *
     * @throws Exception to invoker
     */
    public static void unloadData() throws Exception {
        Connection conn = null;

        try {
            DBConnectionFactory dbFactory
                = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
            conn = dbFactory.createConnection();

            conn.createStatement().execute("DELETE FROM plugin_downloads");
            conn.createStatement().execute("DELETE FROM target_object");
            conn.createStatement().execute("DELETE FROM brn_tsr_for_slot");
            conn.createStatement().execute("DELETE FROM puzzle_for_slot");
            conn.createStatement().execute("DELETE FROM plyr_compltd_slot");
            conn.createStatement().execute("DELETE FROM plyr_compltd_game");
            conn.createStatement().execute("DELETE FROM plyr_regstrd_game");
            conn.createStatement().execute("DELETE FROM plyr_won_game");
            conn.createStatement().execute("DELETE FROM hosting_slot");
            conn.createStatement().execute("DELETE FROM effective_bid");
            conn.createStatement().execute("DELETE FROM bid");
            conn.createStatement().execute("DELETE FROM [image]");
            conn.createStatement().execute("DELETE FROM auction");
            conn.createStatement().execute("DELETE FROM hosting_block");
            conn.createStatement().execute("DELETE FROM game");
            conn.createStatement().execute("DELETE FROM ball_color");
            conn.createStatement().execute("DELETE FROM puzzle_attribute");
            conn.createStatement().execute("DELETE FROM puzzle_resource");
            conn.createStatement().execute("DELETE FROM puzzle");
            conn.createStatement().execute("DELETE FROM download_obj");
            conn.createStatement().execute("DELETE FROM domain");
            conn.createStatement().execute("DELETE FROM sponsor");
            conn.createStatement().execute("DELETE FROM player");
            conn.createStatement().execute("DELETE FROM contact_info");
            conn.createStatement().execute("DELETE FROM any_user");
        } finally {
            conn.close();
        }
    }
}
