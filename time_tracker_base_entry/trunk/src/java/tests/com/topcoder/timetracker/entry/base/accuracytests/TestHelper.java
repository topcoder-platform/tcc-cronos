/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.base.CutoffTimeBean;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Assert;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;



/**
 * Test helper class.
 */
public class TestHelper {
    /** namespace for db connection. */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** Config file for the component. */
    public static final String CONFIG_FILE = "accuracy/config.xml";

    /** Namespace for this component's manager. */
    public static final String NAMESPACE = "com.topcoder.timetracker.base.entry";

    /** The namespace for the oracle graph loader. */
    public static final String EJB_NAMESPACE = "com.topcoder.timetracker.entry.base.ejb.EntrySessionBean";

    /** Object factory namespace. */
    public static final String OF_NAMESPACE = "com.topcoder.timetracker.entry.base.ejb.EntrySessionBean.objectfactory";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of a new
     * instance.
     * </p>
     */
    private TestHelper() {
    }

    
    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public static void setUpConfiguration() throws Exception {
        ConfigManager.getInstance().add(CONFIG_FILE);
        ConfigManager.getInstance()
                     .add("com.topcoder.naming.jndiutility", "accuracy/JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * Insert data for testing.
     * @param conn database connection
     * @throws Exception into JUnit
     */
    public static void insertData(Connection conn) throws Exception{
    	 Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.execute("insert into company(company_id,name,passcode,creation_date,creation_user,modification_date,modification_user)" +
             		"values(1,'topcoder','topcoder',CURRENT,USER,CURRENT,USER)");
         } finally {
             if (stmt != null) {
                 stmt.close();
             }           
         }
    }
    
    /**
     * Create database connection.
     * @return Connection
     * @throws Exception into Junit
     */
    public static Connection getConnection() throws Exception{
    	return new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE).createConnection();
    }
    /**
     * Clear the tables.
     * @param conn Database connection
     * @throws Exception into JUnit
     */
    public static void clearTable(Connection conn) throws Exception {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("delete from cut_off_time");
            stmt.execute("delete from company");
        } finally {
            if (stmt != null) {
                stmt.close();
            }           
        }
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Create CutoffTimeBean with the given companyId and cutofftime.
     *
     * @param companyId the companyId
     * @param cutoffTime the cutofftime
     *
     * @return CutoffTimeBean instance for testing
     */
    public static CutoffTimeBean createCutoffTimeBean(long companyId, Date cutoffTime) {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(companyId);
        bean.setCreationDate(new Timestamp(new Date().getTime()));
        bean.setCreationUser("creator");
        bean.setModificationDate(new Timestamp(new Date(System.currentTimeMillis() + 1000).getTime()));
        bean.setModificationUser("modifier");
        bean.setCutoffTime(cutoffTime);

        return bean;
    }

    /**
     * Assert the persited CutoffTimeBean with the expected CutoffTimeBean.
     *
     * @param expected CutoffTimeBean instance
     * @param persisted CutoffTimeBean instance
     */
    public static void assertCutoffTimeBean(CutoffTimeBean expected, CutoffTimeBean persisted) {
        Assert.assertTrue("The id is invalid.", persisted.getId() > 0);
        Assert.assertEquals("The companyId is not the one to persist.", expected.getCompanyId(),
            persisted.getCompanyId());
        Assert.assertEquals("The create user is not the one to persist", expected.getCreationUser(),
            persisted.getCreationUser());
        Assert.assertEquals("The modify user is not the one to persist", expected.getModificationUser(),
            persisted.getModificationUser());
        Assert.assertEquals("The cutoff time is invalid.", expected.getCutoffTime().getTime(),
            persisted.getCutoffTime().getTime());
    }
}
