/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.ContestData;
import com.topcoder.management.contest.coo.DependencyCategory;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.Helper;

/**
 * <p>
 * test helper providing testing data and helper utility.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class TestHelper extends TestCase {

	/**
	 * <p>
	 * generate development only COOReport for test.
	 * </p>
	 * @param projectId the project id.
     * @return the generated COOReport.
	 */
	public static COOReport getDevOnlyReport(long projectId) {
		COOReport report = new COOReport();
        List<ComponentDependency> componentDependencies = new ArrayList<ComponentDependency>();

        ComponentDependency dep = new ComponentDependency();
        dep.setCategory(DependencyCategory.COMPILE);
        dep.setType(DependencyType.INTERNAL);
        Component component = new Component();
        component.setDescription("exception handling.");
        component.setLicense("free for member.");
        component.setName("baseException");
        component.setVersion("2.0.0");
        component.setUrl("http://www.topcoder.com");
        dep.setComponent(component);

        componentDependencies.add(getComponentDependency(DependencyCategory.COMPILE, DependencyType.EXTERNAL, 1));
        componentDependencies.add(getComponentDependency(DependencyCategory.TEST, DependencyType.EXTERNAL, 2));
        componentDependencies.add(getComponentDependency(DependencyCategory.COMPILE, DependencyType.INTERNAL, 3));
        componentDependencies.add(getComponentDependency(DependencyCategory.TEST, DependencyType.INTERNAL, 4));
        componentDependencies.add(dep);
        report.setComponentDependencies(componentDependencies);

        ContestData contestData = getContestData("Java");
        contestData.setDevelopmentProjectId("456");
        report.setContestData(contestData);
        report.setProjectId(projectId);
        return report;
	}
	/**
	 * <p>
	 * generate design only COOReport for test.
	 * </p>
	 * @param projectId the project id.
     * @return the generated COOReport.
     */
	public static COOReport getDesignOnlyReport(long projectId) {
		COOReport report = new COOReport();
        List<ComponentDependency> componentDependencies = new ArrayList<ComponentDependency>();

        ComponentDependency dep = new ComponentDependency();
        dep.setCategory(DependencyCategory.COMPILE);
        dep.setType(DependencyType.INTERNAL);
        Component component = new Component();
        component.setDescription("exception handling.");
        component.setLicense("free for member.");
        component.setName("baseException");
        component.setVersion("2.0.0");
        component.setUrl("http://www.topcoder.com");
        dep.setComponent(component);

        componentDependencies.add(getComponentDependency(DependencyCategory.COMPILE, DependencyType.EXTERNAL, 1));
        componentDependencies.add(getComponentDependency(DependencyCategory.TEST, DependencyType.EXTERNAL, 2));
        componentDependencies.add(getComponentDependency(DependencyCategory.COMPILE, DependencyType.INTERNAL, 3));
        componentDependencies.add(getComponentDependency(DependencyCategory.TEST, DependencyType.INTERNAL, 4));
        componentDependencies.add(dep);
        report.setComponentDependencies(componentDependencies);

        ContestData contestData = getContestData("Java");
        contestData.setDesignProjectId("123");
        report.setContestData(contestData);
        report.setProjectId(projectId);
        return report;
	}
    
	/**
     * generate COOReport for test.
     *
     * @param projectId the project id.
     * @return the generated COOReport.
     */
    public static COOReport getCOOReport(long projectId) {
        COOReport report = new COOReport();
        List<ComponentDependency> componentDependencies = new ArrayList<ComponentDependency>();

        ComponentDependency dep = new ComponentDependency();
        dep.setCategory(DependencyCategory.COMPILE);
        dep.setType(DependencyType.INTERNAL);
        Component component = new Component();
        component.setDescription("exception handling.");
        component.setLicense("free for member.");
        component.setName("baseException");
        component.setVersion("2.0.0");
        component.setUrl("http://www.topcoder.com");
        dep.setComponent(component);

        componentDependencies.add(getComponentDependency(DependencyCategory.COMPILE, DependencyType.EXTERNAL, 1));
        componentDependencies.add(getComponentDependency(DependencyCategory.TEST, DependencyType.EXTERNAL, 2));
        componentDependencies.add(getComponentDependency(DependencyCategory.COMPILE, DependencyType.INTERNAL, 3));
        componentDependencies.add(getComponentDependency(DependencyCategory.TEST, DependencyType.INTERNAL, 4));
        componentDependencies.add(dep);
        report.setComponentDependencies(componentDependencies);

        ContestData contestData = getContestData("Java");
        report.setContestData(contestData);
        report.setProjectId(projectId);
        return report;
    }

    /**
     * generate ContestData for test.
     *
     * @param category the category of contest data.
     * @return the generated ContestData.
     */
    public static ContestData getContestData(String category) {
        ContestData contestData = new ContestData();
        contestData.setCategory(category);
        contestData.setComponentName("COO Generator");
        contestData.setContestEndDate(new Date());
        List<String> designReviewers = new ArrayList<String>();
        designReviewers.add("design review 1");
        designReviewers.add("design review 2");
        designReviewers.add("design review 3");
        contestData.setDesignReviewers(designReviewers);
        contestData.setDesignWinner("designWinner");
 //       contestData.setDesignSecondPlace("designSecondPlace");
        List<String> developmentReviewers = new ArrayList<String>();
        developmentReviewers.add("dev review 1");
        developmentReviewers.add("dev review 2");
        developmentReviewers.add("dev review 3");
        contestData.setDevelopmentReviewers(developmentReviewers);
        contestData.setDevelopmentWinner("developmentWinner");
        contestData.setDevelopmentSecondPlace("developmentSecondPlace");
        contestData.setSvnPath("Build.dependencies");       
        return contestData;
    }

    /**
     * generate component dependencies.
     *
     * @param category the dependency category.
     * @param type the dependency type.
     * @param seed to generate component dependencies.
     * @return generated dependency.
     */
    public static ComponentDependency getComponentDependency(
            DependencyCategory category, DependencyType type, long seed) {
        ComponentDependency dep = new ComponentDependency();
        dep.setCategory(category);
        dep.setType(type);
        Component component = new Component();
        component.setDescription("description" + seed);
        component.setLicense("GPL" + seed);
        component.setName("name" + seed);
        component.setVersion("1.0." + seed);
        component.setUrl("http://www.topcoder.com");
        dep.setComponent(component);
        return dep;
    }

    /**
     * create configuration file from file.
     *
     * @param file the configuration file name.
     * @return the configuration object from xml file.
     * @throws Exception to JUnit.
     */
    public static ConfigurationObject getConfiguration(String file) throws Exception {
        // load the ConfigurationObject from the input file
        ConfigurationObject cfgObject = null;
        try {
            // build the XMLFilePersistence
            XMLFilePersistence xmlFilePersistence = new XMLFilePersistence();
            cfgObject = xmlFilePersistence.loadFile("test", new File(file));
        } catch (ConfigurationParserException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        }
        return cfgObject;
    }

    /**
     * delete the file with given name.
     *
     * @param fileName the file name.
     * @throws Exception to JUNIT.
     */
    public static void deleteFile(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename the sql file.
     * @throws Exception to JUnit.
     */
    public static void executeSqlFile(String filename) throws Exception {
        Scanner sin = new Scanner(new FileInputStream(filename));
        List<String> sqls = new ArrayList<String>();
        Connection conn = getConn();
        while (sin.hasNext()) {
            String str = sin.nextLine().trim();
            if (str.length() != 0) {
                sqls.add(str);
            }
        }
        PreparedStatement ps = null;
        for (String sql : sqls) {
            // creates the prepared statement
            ps = conn.prepareStatement(sql);
            // do the update
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
            }
        }
        ps.close();
        sin.close();
        conn.close();
    }

    /**
     * get connection from configuration.
     *
     * @return the connection
     * @throws Exception to JUNIT.
     */
    public static Connection getConn() throws Exception {
        ConfigurationObject config = getConfiguration("test_files/componentManager.xml");
        config = Helper.getChild(config, "default");
        // get connection name
        String connectionName = Helper.getStringProperty(config, "connectionName", true);
        // get DBConnectionFactory from configuration
        ConfigurationObject dbConnectionFactoryConfig = Helper.getChild(config, "dbConnectionFactoryConfig");
        
        DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);

        return dbConnectionFactory.createConnection(connectionName);
 
    }
}
