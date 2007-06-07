/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.RatingType;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import com.topcoder.util.log.basic.BasicLogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.BundleInfo;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the
 * test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * Represents the namespace for this component.
     * </p>
     */
    public static final String NAMESPACE =
        "com.topcoder.registration.validation.DataValidationRegistrationValidator";

    /**
     * <p>
     * The sample configuration file for this component.
     * </p>
     */
    public static final String CONFIG_FILE = "test_files" + File.separator
            + "survey_content_config.xml";

    /**
     * <p>
     * The sample xml configuration file for this component.
     * </p>
     */
    public static final String XML_CONFIG_PATH = "test_files" + File.separator
            + "xml_config.xml";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName
     *            config file to set up environment
     *
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.add("unit" + File.separator + fileName);
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * This method parses the given file to a <code>Document</code> instance.
     * </p>
     *
     * @param path
     *            the xml file to parse
     * @return the <code>Document</code> instance represents the file
     *
     * @throws Exception
     *             if fails to parse the file
     */
    public static Document createDocumentByFile(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        InputStream is = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            is = new FileInputStream(new File(path));

            // parse the xml file stream
            return builder.parse(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * <p>
     * This method parses the given xml string to a <code>Document</code>
     * instance.
     * </p>
     *
     * @param xmlString
     *            the xml string to parse
     * @return the <code>Document</code> instance represents the xml string
     *
     * @throws Exception
     *             if fails to parse the xml string
     */
    public static Document createDocumentByString(String xmlString)
        throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        if (xmlString == null) {
            return builder.newDocument();
        } else {
            return builder.parse(new InputSource(new StringReader(xmlString)));
        }
    }

     /**
     * Creates a DefaultRegistrationValidator instance and set it to the validator.
     *
     * @param validator
     *            the validator which needs to have access to managers, services, and logger
     *            in the DefaultRegistrationValidator
     * @param loggerFileName
     *            the log file name of the log in the DefaultRegistrationValidator instance
     *
     */
    public static void setDefaultRegistrationValidator(
        ConfigurableValidator validator, String loggerFileName) {
        try {
            // create a print stream to the file with auto flushing
            PrintStream ps = new PrintStream(new FileOutputStream("test_files"
                    + File.separator + loggerFileName, true), true);
            // specify the basic logger with the above print stream
            LogManager.setLogFactory(new BasicLogFactory(ps));

            DataValidationRegistrationValidator dataValidationRegistrationValidator
                = new DataValidationRegistrationValidator();
            dataValidationRegistrationValidator.getProjectServices();
            validator
                    .setRegistrationValidator(dataValidationRegistrationValidator);
        } catch (FileNotFoundException e) {
            // do nothing
        }
    }

     /**
     * Gets a logger to log the test.
     *
     * @param loggerFileName
     *            the log file name of the log in the DefaultRegistrationValidator instance
     * @return the logger
     *
     * @throws Exception
     *             if any error occurs
     */
    public static Log getLogger(String loggerFileName) throws Exception {
        // create a print stream to the file with auto flushing
        PrintStream ps = new PrintStream(new FileOutputStream("test_files"
                + File.separator + loggerFileName, true), true);
        // specify the basic logger with the above print stream
        LogManager.setLogFactory(new BasicLogFactory(ps));
        return LogManager.getLog();
    }

    /**
     * Creates an ExternalUser instance for test.
     *
     * @return the created ExternalUser instance
     */
    private static ExternalUser createExternalUserForTest() {
        long userId = 1;
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUserImpl user = new ExternalUserImpl(userId, "handle1",
                "firstName", "lastName", "email");
        user.addAlternativeEmail("alternativeEmail_1@topcoder.com");
        user.addAlternativeEmail("alternativeEmail_2@topcoder.com");
        user.addRatingInfo(ratingInfo);

        return user;
    }

    /**
     * Creates a FullProjectData instance for test.
     *
     * @return the created FullProjectData instance
     */
    private static FullProjectData createFullProjectDataForTest() {

        FullProjectData project = new FullProjectData(new Date(),
            (new DefaultWorkdaysFactory()).createWorkdaysInstance());

        Resource resource1 = new Resource(11, new ResourceRole());
        resource1.setProperty("External Reference ID", new Long(1));
        Resource resource2 = new Resource(22, new ResourceRole());
        resource2.setProperty("External Reference ID", new Long(2));
        project.setResources(new Resource[] {resource1, resource2 });

        ProjectCategory projectCategory = new ProjectCategory(1,
            "projectCategoryName", new ProjectType(1, "ProjectType"));
        ProjectStatus projectStatus = new ProjectStatus(1, "projectStatusName");
        Project projectHeader = new Project(projectCategory, projectStatus);
        project.setProjectHeader(projectHeader);

        TeamHeader team1 = new TeamHeader();
        team1.setTeamId(1);
        team1.setCaptainResourceId(11);
        TeamHeader team2 = new TeamHeader();
        team2.setTeamId(2);
        team2.setCaptainResourceId(22);
        project.setTeams(new TeamHeader[] {team1, team2 });

        project.setTechnologies(new String[] {"Java", "SQL"});

        Phase phase = new Phase(project, 7);
        phase.setPhaseStatus(PhaseStatus.OPEN);
        phase.setId(1);
        project.addPhase(phase);

        return project;
    }

    /**
     * Creates a RegistrationInfo instance for test.
     *
     * @return the created RegistrationInfo instance
     */
    private static RegistrationInfo createRegistrationInfoForTest() {
        RegistrationInfo registration = new RegistrationInfoImpl();
        registration.setProjectId(2);
        registration.setRoleId(3);
        registration.setUserId(1);
        return registration;
    }

    /**
     * Creates a ValidationInfo instance for test.
     *
     * @return the created ValidationInfo instance
     */
    public static ValidationInfo createValidationInfoForTest() {
        ExternalUser user = createExternalUserForTest();
        FullProjectData project = createFullProjectDataForTest();
        RegistrationInfo registration = createRegistrationInfoForTest();

        ValidationInfo validationInfo = new ValidationInfo();
        validationInfo.setUser(user);
        validationInfo.setRegistration(registration);
        validationInfo.setProject(project);
        return validationInfo;
    }

    /**
     * <p>
     * Compares the two input BundleInfo.
     * </p>
     *
     * @param bundleInfo1
     *            the firt bundleInfo to be compared
     * @param bundleInfo2
     *            the second bundleInfo to be compared
     * @return null if bundleInfo1 equals to bundleInfo2, and else return the
     *         message discribing which field is different.
     *
     */
    public static String compareBundleInfos(BundleInfo bundleInfo1,
            BundleInfo bundleInfo2) {
        String result = "";
        if ((bundleInfo1 != null) && (bundleInfo2 != null)) {

            Locale locale1 = bundleInfo1.getLocale();
            Locale locale2 = bundleInfo2.getLocale();
            if ((locale1 == null) && (locale2 != null)) {
                result = result
                        + "the locale of the  first bundleInfo is  null,"
                        + "  the locale of the second bundleInfo is not null.";

            } else if ((locale1 != null) && (locale2 == null)) {
                result = result
                        + "the locale of the  first bundleInfo is not null,"
                        + "  the locale of the second bundleInfo is null.";
            }
            if (bundleInfo1.getBundleName().compareTo(
                    bundleInfo2.getBundleName()) != 0) {
                result = result + "the bundleName of the first bundleInfo is ["
                        + bundleInfo1.getBundleName()
                        + "], the bundleName of the second bundleInfo is ["
                        + bundleInfo2.getBundleName() + "]";
            }
            if (bundleInfo1.getMessageKey().compareTo(
                    bundleInfo2.getMessageKey()) != 0) {
                result = result + "the messageKey of the first bundleInfo is ["
                        + bundleInfo1.getMessageKey()
                        + "], the messageKey of the second bundleInfo is ["
                        + bundleInfo2.getMessageKey() + "]";
            }
            if (bundleInfo1.getDefaultMessage().compareTo(
                    bundleInfo2.getDefaultMessage()) != 0) {
                result = result
                        + "the defaultMessage of the first bundleInfo is ["
                        + bundleInfo1.getDefaultMessage()
                        + "], the defaultMessage of the second bundleInfo is ["
                        + bundleInfo2.getDefaultMessage() + "]";
            }
        } else if ((bundleInfo1 != null) && ((bundleInfo2 == null))) {
            result = result + "the first bundleInfo is null,"
                    + " the second bundleInfo is not null.";
        } else if ((bundleInfo1 == null) && ((bundleInfo2 != null))) {
            result = result + "the first bundleInfo is not null,"
                    + "  the second bundleInfo is null.";
        }
        return (result.compareTo("") == 0) ? null : result;
    }
}