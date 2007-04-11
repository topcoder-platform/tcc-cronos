/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.ejb.LocalHomeRate;
import com.topcoder.timetracker.rates.ejb.LocalRate;
import com.topcoder.timetracker.rates.ejb.RateEjb;
import com.topcoder.util.config.ConfigManager;

public class AccuracyBase extends TestCase {
    /**
     * Represents the root path of the accuracy test files.
     */
    private static final String PATH = "test_files" + File.separator + "accuracytests";
    
    /**
     * Sets up the test environment.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        loadConfigFile("dbconfig.xml");
        loadConfigFile("rate_config.xml");
        
    }
    
    /**
     * Removes all the configuration environments.
     * 
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        removeTables();
        clearNamespaces();
    }
    
    /**
     * Loads the configuration file to ConfigManager.
     * 
     * @param fileName the file name to load
     * 
     * @throws Exception to JUnit
     */
    protected void loadConfigFile(String fileName) throws Exception {
        // create the file
        File file = new File(PATH, fileName);
        
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(file.getAbsolutePath());
    }
    
    /**
     * Clears all the namespaces in the ConfigManager.
     *
     * @throws Exception to JUnit
     */
    protected void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for(Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();
            if (cm.existsNamespace(namespace)) {
                cm.removeNamespace(namespace);
            }
        }
    }
    
    /**
     * Creates the Rate with specific arguments.
     * 
     * @return the rate with the specific argument.
     */
    protected Rate createRate(int rateId, Date creationDate, Date modificationDate, String creationUser, 
        String modificationUser, String description, double rate, Company company) {
        
        Rate rateInstance = new Rate();
        rateInstance.setId(rateId);
        rateInstance.setCreationDate(creationDate);
        rateInstance.setCreationUser(creationUser);
        rateInstance.setModificationDate(modificationDate);
        rateInstance.setModificationUser(modificationUser);
        
        rateInstance.setDescription(description);
        rateInstance.setRate(rate);
        rateInstance.setCompany(company);
        
        return rateInstance;
    }
    
    
    /**
     * Creates the default rate with the specific rate id and company id.
     * 
     * @param rateId the rate id
     * @param companyId the company id
     * @return the rate with the specific rate id and company id
     */
    protected Rate createDefaultRate(int rateId, int companyId) {
        Company company = new Company();
        company.setId(companyId);
        return createRate(rateId, new Date(), new Date(), "reviewer", "reviewer", "for accuracy tests of " + rateId + " " + companyId, 
            1.0, company);
    }
    
    /**
     * Removes the tables in the database.
     *
     * @throws Exception to JUnit
     */
    protected void removeTables() throws Exception {
        String[] tables = new String[] {
            "rate", "comp_rate"
        };
        
        for (int i = 0; i < tables.length; i++) {
            String sql = "DELETE FROM " + tables[i];
            executeUpdate(sql, new Object[0]);
        }
        
        
    }
    
    /**
     * Executes the sql statements with specific arguments.
     * 
     * @param sql the sql to execute
     * 
     * @param objects the objects
     * 
     * @throws Exception to JUnit
     */
    protected void executeUpdate(String sql, Object[] objects) throws Exception {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            setElement(ps, i + 1, objects[i]);
        }
        
        ps.executeUpdate();
        
    }
    
    /**
     * Executes the sql statements with specific arguments.
     * 
     * @param sql the sql to execute
     * 
     * @param objects the objects
     * 
     * @return the result set
     * 
     * @throws Exception to JUnit
     */
    protected ResultSet executeQuery(String sql, Object[] objects) throws Exception {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            setElement(ps, i + 1, objects[i]);
        }
        
        return ps.executeQuery();
        
    }
    
    
    /**
     * Creates the connection.
     * 
     * @return the connection
     * @throws Exception to JUnit
     */
    protected Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());
        return factory.createConnection();
    }
    
    /**
     * Sets the element to the prepared statement.
     * 
     * @param ps the prepared statement
     * 
     * @param index the index
     * 
     * @param obj the object
     * 
     * @throws Exception to JUnit
     */
    protected void setElement(PreparedStatement ps, int index, Object obj) throws Exception {
        if (obj instanceof Integer) {
            ps.setInt(index, ((Integer) obj).intValue());
        } else if (obj instanceof String) {
            ps.setString(index, (String) obj);
        } else if (obj instanceof Long) {
            ps.setLong(index, ((Long) obj).longValue());
        } else if (obj instanceof Date) {
            ps.setTimestamp(index, new Timestamp(((Date) obj).getTime()));
        } else if (obj instanceof Double) {
            ps.setDouble(index, ((Double) obj).doubleValue());
        } else {
            throw new UnsupportedOperationException("The type is not supported.;");
        }
    }
    
    /**
     * Checks if a specific rate is in the database.
     * 
     * @param rate the rate
     * 
     * @return true if exists, otherwise false
     * 
     * @throws Exception to JUnit
     */
    protected boolean existsRate(Rate rate) throws Exception {
        long rateId = rate.getId();
        long companyId = rate.getCompany().getId();
        String sql = "SELECT * FROM comp_rate WHERE rate_id = ? AND company_id = ?";
        ResultSet rs = executeQuery(sql, new Object[] {
            new Long(rateId), new Long(companyId)
        });
        
        if(!rs.next()) {
            return false;
        }
        
        String user = rs.getString("modification_user");
        return rate.getModificationUser().equals(user);
    }
    
    
    /**
     * Inserts some test data to the table: rate for accuracy tests.
     * 
     * @throws Exception to JUnit
     */
    protected void addToRate() throws Exception {
        String sql = "INSERT INTO rate (rate_id, description, creation_date," +
                " creation_user, modification_date, modification_user) " 
            + "VALUES(?, ?, ?, ?, ?, ?)";
        
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + 1, i + 1);
        }
        for (int i = 0; i < rates.length; i++) {
            Object[] objs = new Object[6];
            
            objs[0] = new Long(rates[i].getId());
            objs[1] = rates[i].getDescription();
            objs[2] = rates[i].getCreationDate();
            objs[3] = rates[i].getCreationUser();
            objs[4] = rates[i].getModificationDate();
            objs[5] = rates[i].getModificationUser();
            
            executeUpdate(sql, objs);
        }
    }
    
    /**
     * Inserts some test data to the table: rate for accuracy tests.
     * 
     * @param startFrom the beginning index of the rate id
     * 
     * @throws Exception to JUnit
     */
    protected void addToRate(int startFrom) throws Exception {
        String sql = "INSERT INTO rate (rate_id, description, creation_date," +
                " creation_user, modification_date, modification_user) " 
            + "VALUES(?, ?, ?, ?, ?, ?)";
        
        Rate[] rates = new Rate[10];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = createDefaultRate(i + startFrom, i + 1);
        }
        for (int i = 0; i < rates.length; i++) {
            Object[] objs = new Object[6];
            
            objs[0] = new Long(rates[i].getId());
            objs[1] = rates[i].getDescription();
            objs[2] = rates[i].getCreationDate();
            objs[3] = rates[i].getCreationUser();
            objs[4] = rates[i].getModificationDate();
            objs[5] = rates[i].getModificationUser();
            
            executeUpdate(sql, objs);
        }
    }
    
    /**
     * Sets up the JNDI environment for accuracy tests.
     * 
     * @throws Exceptioin to JUnit
     */
    protected void initializeJNDIEnvironment() throws Exception {
        MockContextFactory.setAsInitial();

        Context context = new InitialContext();
        MockContainer mockContainer = new MockContainer(context);

        context.bind("java:comp/env/of_namespace", 
            "objectfactory.InformixRatePersistence");
        context.bind("java:comp/env/of_rate_persistence_key", 
            "ratePersistence");
        
        context.bind("of_rate_persistence", "ratePersistence");

        SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(
            "rate.rateLocalHome", LocalHomeRate.class, LocalRate.class, new RateEjb());
        mockContainer.deploy(beanDescriptor);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
        
    }
    
    /**
     * Clears the JNDI environment for accuracy tests.
     * 
     * @throws Exception to JUnit
     */
    protected void clearJNDIEnvironment() throws Exception {
        MockContextFactory.revertSetAsInitial();
    }
    
    
}
