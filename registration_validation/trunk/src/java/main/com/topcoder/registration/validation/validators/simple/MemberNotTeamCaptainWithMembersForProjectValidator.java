/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.registration.validation.AbstractConfigurableValidator;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * A simple validator that checks that a member is not a captain of a team that
 * has other team members in positions. Team Manager is used here.
 * <p>
 * This class extends AbstractConfigurableValidator that implements most of the
 * validation methods, so this class only needs to implement the getMessage
 * method.
 * </p>
 * <p>
 * The DataValidationRegistrationValidator manager, available via the
 * getRegistrationValidator method, provides all necessary managers and services
 * for this validator. It also provides the Log for logging.
 * </p>
 *
 * <p>
 * To provide a good view as the steps are progressing in each method, the
 * <b>Logging Wrapper</b> component is used in each method. To configure this
 * component, the <b>ConfigManager</b> component is used.
 * </p>
 *
 * <p>
 * Here is the sample configuration file for this class.
 *
 * <pre>
 *                &lt;Config name=&quot;test.Validator;&gt;
 *                    &lt;Property name=&quot;teamCaptainRoleId&quot;&gt;
 *                        &lt;Value&gt;2&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleName&quot;&gt;
 *                        &lt;Value&gt;myBundle&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleLocaleLanguage&quot;&gt;
 *                        &lt;Value&gt;en&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleLocaleCountry&quot;&gt;
 *                        &lt;Value&gt;ca&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;bundleLocaleVariant&quot;&gt;
 *                        &lt;Value&gt;Traditional_WIN&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;messageKey&quot;&gt;
 *                        &lt;Value&gt;myMessageKey&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;defaultMessage&quot;&gt;
 *                        &lt;Value&gt;./test_files/myTemplate.txt&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is mutable and thus is not thread-safe, but the
 * actual properties and managers used to validate are immutable, so this class
 * will be effectively thread-safe in the context of its usage in this
 * component.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class MemberNotTeamCaptainWithMembersForProjectValidator extends
        AbstractConfigurableValidator {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "MemberNotTeamCaptainWithMembersForProjectValidator";

    /**
     * <p>
     * Name of the property that stores the ID of the team captain role.
     * </p>
     * <p>
     * This property is required. This must can be parsed into a non-negative
     * long value.
     * </p>
     *
     */
    private static final String TEAM_CAMPTAIN_ROLEID_PROPERTYNAME = "teamCaptainRoleId";

    /**
     * <p>
     * Represents the ID of the team captain role.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never
     * change.
     * </p>
     *
     */
    private final long teamCaptainRoleId;

    /**
     * Initializes the resource bundle and the team captain role ID based on the
     * input parameters.
     *
     * @param bundleInfo
     *            The resource bundle info
     * @param teamCaptainRoleId
     *            the ID of the team captain role
     * @throws IllegalArgumentException
     *             if any of the parameters in bundleInfo is null or an empty
     *             string (for string parameters)
     * @throws IllegalArgumentException
     *             if teamCaptainRoleId is negative
     */
    public MemberNotTeamCaptainWithMembersForProjectValidator(
            BundleInfo bundleInfo, long teamCaptainRoleId) {
        super(bundleInfo);
        RegistrationValidationHelper.validateNotNegative(teamCaptainRoleId,
                "teamCaptainRoleId");
        this.teamCaptainRoleId = teamCaptainRoleId;
    }

    /**
     * Initializes the resource bundle and the team captain role ID from
     * configuration information from the <b>ConfigManager</b>.
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws RegistrationValidationConfigurationException
     *             If any configuration error occurs, such as unknown namespace
     *             or missing required values.
     */
    public MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) {
        super("temporary");
        RegistrationValidationHelper.validateStringNotNullOrEmpty(namespace,
                "namespace");

        // Creates BundleInfo from configuration information
        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo(namespace);
        // Sets the BundleInfo
        // If the bundleName is null, then only sets the defaultMessage,
        // Else sets bundleInfo using the
        // super.setResourceBundleInfo(BundleInfo)
        if (bundleInfo.getBundleName() == null) {
            getBundleInfo().setDefaultMessage(bundleInfo.getDefaultMessage());
        } else {
            setResourceBundleInfo(bundleInfo);
        }

        // set the team captain role ID
        this.teamCaptainRoleId = RegistrationValidationHelper.getLong(
                namespace, TEAM_CAMPTAIN_ROLEID_PROPERTYNAME).intValue();
    }

    /**
     * <p>
     * Checks that a member is not a captain of a team that has other team
     * members in positions.
     * </p>
     *
     *
     * @return null if obj is valid. Otherwise, an error message is returned.
     * @param obj
     *            Object to validate.
     * @throws IllegalArgumentException
     *             If obj is null or not a ValidationInfo object
     * @throws ValidationProcessingException
     *             If an unexpected system error occurs
     */
    public String getMessage(Object obj) {

        String methodName = CLASS_NAME + ".getMessage(Object obj)";
        Log logger = getRegistrationValidator().getLog();

        // log entrance
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        try {
            RegistrationValidationHelper.validateIsValidationInfo(obj,
                    "Object to validate");

            ValidationInfo validationInfo = (ValidationInfo) obj;

            // Gets the roleId that a member is registered with.
            long registrationRoleId = validationInfo.getRegistration()
                    .getRoleId();

            // Initializes the message to be null.
            // The message will be filled if the validation is failed.
            String message = null;

            // Proceeds to validate if the member is registered with this.roleId
            if (registrationRoleId == this.teamCaptainRoleId) {

                // Find resource in validationInfo.project.resources whose
                // custom property &quot;External Reference ID&quot; is equal to
                // validationInfo.registrationInfo.userId
                Resource resource = RegistrationValidationHelper.findResource(
                        validationInfo, logger);

                // match this resource.id to a teamHeader in
                // validationInfo.project.teams
                TeamHeader[] teams = validationInfo.getProject().getTeams();
                TeamHeader teamHeader = null;
                if (resource != null) {
                    for (int i = 0; i < teams.length; i++) {
                        if (teams[i].getCaptainResourceId() == resource.getId()) {
                            teamHeader = teams[i];
                            break;
                        }
                    }
                }
                boolean isFilled = isFilled(teamHeader);
                if (isFilled) {
                    String data = RegistrationValidationHelper
                            .buildStandInfo(validationInfo);
                    data = RegistrationValidationHelper
                            .appendInnerDataValue(data,
                                    "TEAM_CAPTAIN_ROLE", ""
                                            + this.teamCaptainRoleId);
                    // Log variable value
                    RegistrationValidationHelper.log(logger, Level.DEBUG,
                        "the value of data stirng is [" + data + "]");
                    String messageTemplate = this
                            .getValidationMessage();
                    message = RegistrationValidationHelper.fillMessage(messageTemplate,
                        data, logger, methodName);
                }
            }
            // Log return value
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "return value is [" + message + "]");
            // Log exit
            RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
            return message;
        } catch (ValidationProcessingException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            RegistrationValidationHelper.log(logger, Level.ERROR, "Error[" + e.getClass().getName()
                + "] occurs in " + methodName + ", cause:" + e.getMessage());
            throw e;
        }
    }

    /**
     * <p>
     * Checks if a team is filled.
     * </p>
     *
     *
     * @return true if the team is filled. Otherwise, return false.
     * @param teamHeader
     *            Object to validate.
     */
    private boolean isFilled(TeamHeader teamHeader) {
        Log logger = getRegistrationValidator().getLog();

        if (teamHeader != null) {
            TeamManager teamManager = getRegistrationValidator()
                    .getTeamManager();

            // get detailed team information
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Starts calling TeamManager#getTeam(long)");
            Team detailedTeamInformation = teamManager
                    .getTeam(teamHeader.getTeamId());
            RegistrationValidationHelper.log(logger, Level.DEBUG,
                "Finishes calling TeamManager#getTeam(long)");

            // Fills the message if the detailed team has no position
            // that are filled
            TeamPosition[] positions = detailedTeamInformation
                    .getPositions();
            for (int i = 0; i < positions.length; i++) {
                if (positions[i].getFilled()) {
                    return true;
                }
            }
        }
        return false;
    }
}
