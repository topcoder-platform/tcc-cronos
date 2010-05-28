/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.Duration;

import com.cronos.onlinereview.external.ProjectRetrieval;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.ContestDependencyAutomation;
import com.topcoder.management.project.ContestSale;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectSpec;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.SaleStatus;
import com.topcoder.management.project.SaleType;
import com.topcoder.management.project.SimplePipelineData;
import com.topcoder.management.project.SimpleProjectContestData;
import com.topcoder.management.project.SimpleProjectPermissionData;
import com.topcoder.management.project.SoftwareCapacityData;
import com.topcoder.management.project.ValidationException;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.template.PhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplateException;
import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectDoesNotExistException;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.project.service.Util;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.TCSubject;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Full implementation of the <code>ProjectServices</code> interface. This implementation makes
 * use of a large array of components to accomplish its task of retrieving project data: <b>User
 * Data Store Persistence</b> component to retrieve external project information, the <b>Project
 * Phases</b> and <b>Phase and Resource Management</b> to get project header and phase
 * information, <b>Resource Manager</b> to get resource information, and <b>Team Manager</b> to
 * get team information.
 * </p>
 * <p>
 * To provide a good view as the steps are progressing in each method, the <b>Logging Wrapper</b>
 * component is used in each method. To configure this component, the <b>ConfigManager</b> and
 * <b>ObjectFactory</b> components are used.
 * </p>
 * <p>
 * Also provided are configuration-backed and programmatic constructors. This allows the user to
 * either create all internal supporting objects from configuration, or to simply pass the instances
 * directly.
 * </p>
 *
 * <p>
 * Modifications in version 1.1: <code>createProject</code> and <code>updateProject</code>
 * methods are added.
 * </p>
 *
 * <p>
 * Here is the sample configuration for this class
 *
 * <pre>
 *  &lt;CMConfig&gt;
 *  &lt;!-- Configuration for ProjectServicesImpl --&gt;
 *  &lt;Config name=&quot;com.topcoder.project.service.impl.ProjectServicesImpl&quot;&gt;
 *  &lt;Property name=&quot;specNamespace&quot;&gt;
 *  &lt;Value&gt;com.topcoder.util.objectfactory&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectRetrievalKey&quot;&gt;
 *  &lt;Value&gt;projectRetrievalKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *  &lt;Value&gt;resourceManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;phaseManagerKey&quot;&gt;
 *  &lt;Value&gt;phaseManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectManagerKey&quot;&gt;
 *  &lt;Value&gt;projectManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectLinkManagerKey&quot;&gt;
 *  &lt;Value&gt;projectLinkManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;scorecardManagerKey&quot;&gt;
 *  &lt;Value&gt;scorecardManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;reviewManagerKey&quot;&gt;
 *  &lt;Value&gt;reviewManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *  &lt;Value&gt;teamManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;loggerName&quot;&gt;
 *  &lt;Value&gt;SystemLog&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;activeProjectStatusId&quot;&gt;
 *  &lt;Value&gt;1&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;!-- Configuration for ObjectFactory --&gt;
 *  &lt;Config name=&quot;com.topcoder.util.objectfactory&quot;&gt;
 *  &lt;Property name=&quot;projectRetrievalKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockProjectRetrieval&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockResourceManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;phaseManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockPhaseManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockProjectManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectLinkManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.management.project.persistence.link.ProjectLinkManagerImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;params&quot;&gt;
 *  &lt;Property name=&quot;param1&quot;&gt;
 *  &lt;Property name=&quot;name&quot;&gt;
 *  &lt;Value&gt;projectManagerKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;scorecardManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.management.scorecard.ScorecardManagerImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;reviewManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.management.review.DefaultReviewManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.MockTeamManager&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support creating/updating/query contest
 * sale for software contest.
 * </p>
 *
 * <p>
 * Updated for Cockpit Project Admin Release Assembly v1.0: new methods added to support retrieval of project and their permissions.
 * </p>
 *
 * <p>
 * Version 1.1.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve SimplePipelineData for given date range.
 * </p>
 * <p>
 * Version 1.2 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 *     - added service that retrieves a list of capacity data (date, number of scheduled contests) starting from
 *       tomorrow for a given contest type
 * </p>
 * <p>
 * Changes in v1.2.1 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Version 1.2.2 (Cockpit Contest Eligibility) changelog:
 *     - added a method for create private contest's roles
 * </p>
 * <p>
 * Version 1.3 (Cockpit Spec Review Backend Service Update v1.0) changelog:
 *     - Added project link, scorecard and review managers creation.
 *     - Added method to create specification review project for an existing project.
 *     - Added method to get scorecard and review information for a specific project.
 *     - Added method to get the corresponding specification review project id for a given project id.
 *     - Added method to get open phases names for a given project id.
 *     - Added method to add comments to an existing review.
 * </p>
 * <p>
 * Version 1.3.1 (BR3074) changelog:
 *     - Added method to link the design and development contests.
 * Changes in v1.4 (Cockpit Release Assembly - Contest Repost and New Version v1.0):
 * - Added method to re open failed software contest.
 * - Added method to create new version for development or design contest.
 * </p>
 * <p>
 * Changes in v1.4.1(Cockpit Security Facade V1.0)
 *  - findAllTcDirectProjects, findAllTcDirectProjectsForUser,
 *    getSimplePipelineData, getDesignComponents these methods add paremeter TCSubject in order to replacing
 *    the current permission checking security info.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable but operates on non thread safe objects,
 * thus making it potentially non thread safe.
 * </p>
 *
 * @author argolite, moonli, pulky
 * @author fabrizyo, znyyddf, murphydog, waits
 * @version 1.4.1
 * @since 1.0
 */
public class ProjectServicesImpl implements ProjectServices {

    /**
     * <p>
     * Represents the default namespace used by the default constructor to access configuration info
     * in the construction.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.project.service.impl.ProjectServicesImpl";

    /**
     * <p>
     * Represents the <b>specNamespace</b> property key.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the <b>userRetrieval</b> property key.
     * </p>
     */
    private static final String PROJECT_RETRIEVAL_KEY = "projectRetrievalKey";

    /**
     * <p>
     * Represents the <b>ResourceManager</b> property key.
     * </p>
     */
    private static final String RESOURCE_MANAGER_KEY = "resourceManagerKey";

    /**
     * <p>
     * Represents the <b>phaseManagerKey</b> property key.
     * </p>
     */
    private static final String PHASE_MANAGER_KEY = "phaseManagerKey";

    /**
     * <p>
     * Represents the <b>projectManagerKey</b> property key.
     * </p>
     */
    private static final String PROJECT_MANAGER_KEY = "projectManagerKey";

    /**
     * <p>
     * Represents the <b>teamManagerKey</b> property key.
     * </p>
     */
    private static final String TEAM_MANAGER_KEY = "teamManagerKey";

    /**
     * <p>
     * Represents the <b>teamManagerKey</b> property key.
     * </p>
     */
    private static final String PROJECT_PHASE_TEMPLATE_KEY = "projectPhaseTemplateKey";

    /**
     * <p>
     * Represents the <b>loggerName</b> property key.
     * </p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the <b>activeProjectStatusId</b> property key.
     * </p>
     */
    private static final String ACTIVE_PROJECT_STATUS_ID = "activeProjectStatusId";

    /**
     * <p>
     * Represents the <b>activeProjectStatusId</b> property key.
     * </p>
     */
    private static final String ACTIVE_PROJECT_CATEGORY_ID = "activeProjectCategoryId";

    /**
     * <p>
     * Represents the <b>externalReferenceID</b> property key.
     * </p>
     */
    private static final String EXTERNAL_REFERENCE_ID = "External Reference ID";

    /**
     * <p>
     * Represents the "Submitter" resource role id.
     * </p>
     *
     * @since 1.3
     */
    private static final int SUBMITTER_ROLE_ID = 1;

    /**
     * <p>
     * Represents the <b>projectLinkManagerKey</b> property key.
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_LINK_MANAGER_KEY = "projectLinkManagerKey";

    /**
     * <p>
     * Represents the <b>scorecardManagerKey</b> property key.
     * </p>
     *
     * @since 1.3
     */
    private static final String SCORECARD_MANAGER_KEY = "scorecardManagerKey";

    /**
     * <p>
     * Represents the <b>reviewManagerKey</b> property key.
     * </p>
     *
     * @since 1.3
     */
    private static final String REVIEW_MANAGER_KEY = "reviewManagerKey";


    /**
     * <p>
     * Represents the reviewer resource role id
     * </p>
     *
     * @since 1.3
     */
    private static final int REVIEWER_RESOURCE_ROLE_ID = 4;

    /**
     * <p>
     * Represents the scorecard id phase attribute key
     * </p>
     *
     * @since 1.3
     */
    private static final String SCORECARD_ID_PHASE_ATTRIBUTE_KEY = "Scorecard ID";

    /**
     * <p>
     * Represents the review phase type name
     * </p>
     *
     * @since 1.3
     */
    private static final String REVIEW_PHASE_TYPE_NAME = "Review";

    /**
     * <p>
     * Represents the resource reviewer property name
     * </p>
     *
     * @since 1.3
     */
    private static final String RESOURCE_REVIEWER_PROPERTY = "reviewer";


    /**
     * <p>
     * Represents on value for the autopilot option project property
     * </p>
     *
     * @since 1.3
     */
    private static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_VALUE_ON = "On";

    /**
     * <p>
     * Represents the "billing project" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String BILLING_PROJECT_PROJECT_PROPERTY_KEY = "Billing Project";

    /**
     * <p>
     * Represents the "confidentiality type" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY = "Confidentiality Type";

    /**
     * <p>
     * Represents the "version id" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String VERSION_ID_PROJECT_PROPERTY_KEY = "Version ID";

    /**
     * <p>
     * Represents the "component id" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String COMPONENT_ID_PROJECT_PROPERTY_KEY = "Component ID";

    /**
     * <p>
     * Represents the "external reference id" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY = "External Reference ID";

    /**
     * <p>
     * Represents the "external reference id" resource property key
     * </p>
     *
     * @since 1.3
     */
    private static final String EXTERNAL_REFERENCE_ID_RESOURCE_PROPERTY_KEY = "External Reference ID";

    /**
     * <p>
     * Represents the "project version" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_VERSION_PROJECT_PROPERTY_KEY = "Project Version";

    /**
     * <p>
     * Represents the "payments" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String PAYMENTS_PROJECT_PROPERTY_KEY = "Payments";



    /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY = "Autopilot Option";

    /**
     * <p>
     * Represents the "project name" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String PROJECT_NAME_PROJECT_PROPERTY_KEY = "Project Name";


    /**
     * <p>
     * Represents the "Developer Forum ID" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String FORUM_ID_PROJECT_PROPERTY_KEY = "Developer Forum ID";


    /**
     * <p>
     * Represents the "Root Catelog ID" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY = "Root Catalog ID";

    /**
     * <p>
     * Represents the application project type id
     * </p>
     *
     * @since 1.3
     */
    private static final int APPLICATION_PROJECT_TYPE_ID = 2;

    /**
     * <p>
     * Represents the application project type name
     * </p>
     *
     * @since 1.3
     */
    private static final String APPLICATION_PROJECT_TYPE = "Application";

    /**
     * <p>
     * Represents the specification review project category id
     * </p>
     *
     * @since 1.3
     */
    private static final int SPEC_REVIEW_PROJECT_CATEGORY_ID = 27;

    /**
     * <p>
     * Represents the specification review prject category name
     * </p>
     *
     * @since 1.3
     */
    private static final String SPEC_REVIEW_PROJECT_CATEGORY = "Spec Review";

    /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     *
     * @since 1.3
     */
    private static final String NOTES_PROJECT_PROPERTY_KEY = "Notes";


    /**
     * <p>
     * Represents the project properties that need to be cloned when creating a specification review project
     * </p>
     *
     * @since 1.3
     */
    private static final String[] SPEC_REVIEW_PROJECT_PROPERTIES_TO_CLONE = new String[] {
        ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.COMPONENT_ID_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY, ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY};

    
    /**
     * <p>
     * Represents the project properties that need to be cloned when creating a new version for dev/design
     * </p>
     */
    private static final String[] NEW_VERSION_PROJECT_PROPERTIES_TO_CLONE = new String[] {
        ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.COMPONENT_ID_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY, ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.STATUS_NOTIFICATION_PROJECT_PROPERTY_KEY, ProjectPropertyType.TIMELINE_NOTIFICATION_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.PUBLIC_PROJECT_PROPERTY_KEY, ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ELIGIBILITY_PROJECT_PROPERTY_KEY, ProjectPropertyType.NOTES_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.PAYMENTS_PROJECT_PROPERTY_KEY, ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY, ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY, ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY, ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY, ProjectPropertyType.MILESTONE_BONUS_COST_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY, ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY, 
        ProjectPropertyType.COST_LEVEL_PROJECT_PROPERTY_KEY, ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY,
        ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY};

    /**
     * <p>
     * Represents the <code>ProjectRetrieval</code> instance that is used to retrieve the project
     * technologies information. It is set in the constructor to a non-null value, and will never
     * change.
     * </p>
     */
    private final ProjectRetrieval projectRetrieval;

    /**
     * <p>
     * Represents the <code>ResourceManager</code> instance that is used to retrieve resources. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * Represents the <code>PhaseManager</code> instance that is used to obtain phase information
     * about a project. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final PhaseManager phaseManager;

    /**
     * <p>
     * Represents the <code>TeamManager</code> instance that is used to retrieve teams. It is set
     * in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final TeamManager teamManager;

    /**
     * <p>
     * Represents the <code>ProjectManager</code> instance that is used to retrieve projects. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ProjectManager projectManager;

    /**
     * <p>
     * Represents the <code>ProjectLinkManager</code> instance that is used to manage project links. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since 1.3
     */
    private final ProjectLinkManager projectLinkManager;

    /**
     * <p>
     * Represents the <code>ScorecardManager</code> instance that is used to manage scorecards. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since 1.3
     */
    private final ScorecardManager scorecardManager;

    /**
     * <p>
     * Represents the <code>ReviewManager</code> instance that is used to manage reviews. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since 1.3
     */
    private final ReviewManager reviewManager;

    /**
     * <p>
     * Used extensively in this class to log information. This will include logging method entry and
     * exit, errors, debug information for calls to other components, etc.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging will take place. It
     * will be set in the constructor and will not change.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Represents the id of the active project status.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never change.
     * </p>
     */
    private final long activeProjectStatusId;

    /**
     * <p>
     * Represents the ids of the active project categories.
     * </p>
     * <p>
     * It is set in the constructor to non-negative values, and will never change.
     * </p>
     */
    private final List<Long> activeCategoriesList = new ArrayList<Long>();

    /**
     * <p>
     * Represents the <code>DefaultPhaseTemplate</code> instance that is used to generate project phases. It is set in
     * the constructor to a non-null value, and will never change.
     * </p>
     *
     * @since BUGR-1473
     */
    private final PhaseTemplate template;


    /**
     * <p>
     * Default constructor.
     * </p>
     *
     * @throws ConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services
     */
    public ProjectServicesImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Namespace constructor. Initializes this instance from configuration info from the <b>Config
     * Manager</b>. It will use the <b>Object Factory</b> to create instances of required objects.
     * </p>
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws ConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services.
     */
    public ProjectServicesImpl(String namespace) {
        Util.checkStrNotNullOrEmpty(namespace, "namespace");

        ConfigManager cm = ConfigManager.getInstance();
        try {
            // gets namespace for ConfigManagerSpecificationFactory
            String specNamespace = cm.getString(namespace, SPEC_NAMESPACE);
            // creates an instance of ObjectFactory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(specNamespace));

            // gets the value of userRetrievalKey and creates an instance by ObjectFactory
            this.projectRetrieval = (ProjectRetrieval) createObject(cm, objectFactory, namespace,
                    PROJECT_RETRIEVAL_KEY);
            // gets the value of resourceManagerKey and creates an instance by ObjectFactory
            this.resourceManager = (ResourceManager) createObject(cm, objectFactory, namespace, RESOURCE_MANAGER_KEY);
            // gets the value of phaseManagerKey and creates an instance by ObjectFactory
            this.phaseManager = (PhaseManager) createObject(cm, objectFactory, namespace, PHASE_MANAGER_KEY);
            // gets the value of projectManagerKey and creates an instance by ObjectFactory
            this.projectManager = (ProjectManager) createObject(cm, objectFactory, namespace, PROJECT_MANAGER_KEY);

            // gets the value of projectLinkManagerKey and creates an instance by ObjectFactory
            this.projectLinkManager = (ProjectLinkManager) createObject(cm, objectFactory, namespace,
                PROJECT_LINK_MANAGER_KEY);

            // gets the value of scorecardManagerKey and creates an instance by ObjectFactory
            this.scorecardManager = (ScorecardManager) createObject(cm, objectFactory, namespace,
                SCORECARD_MANAGER_KEY);

            // gets the value of reviewManagerKey and creates an instance by ObjectFactory
            this.reviewManager = (ReviewManager) createObject(cm, objectFactory, namespace, REVIEW_MANAGER_KEY);

            // gets the value of teamManagerKey and creates an instance by ObjectFactory
            this.teamManager = (TeamManager) createObject(cm, objectFactory, namespace, TEAM_MANAGER_KEY);

            // BUGR-1473
            this.template = (PhaseTemplate) createObject(cm, objectFactory, namespace, PROJECT_PHASE_TEMPLATE_KEY);

            // gets name of the log and gets the logger instance from LogManager if necessary
            String logName = cm.getString(namespace, LOGGER_NAME);
            this.logger = ((logName == null) ? null : LogManager.getLog(logName));

            // gets the value of activeProjectStatusId
            String activeStatusId = cm.getString(namespace, ACTIVE_PROJECT_STATUS_ID);
            // parses it to 'long' value, if error occurs or negative value returned, throw an
            // exception
            long theActiveProjectStatusId = Long.parseLong(activeStatusId);
            if (theActiveProjectStatusId < 0) {
                throw new ConfigurationException("Value of [activeProjectStatusId] should not be negative.");
            }
            this.activeProjectStatusId = theActiveProjectStatusId;

            log(Level.DEBUG, "Looking for activeCategoryIds");
            int categoryEntryNumber = 0;
            while (true) {
                // increase the innerValidatorNumber to get the key for the next innerValidator.
                categoryEntryNumber++;
                String keyPropertyName = ACTIVE_PROJECT_CATEGORY_ID + categoryEntryNumber;
                // Creates innerValidator using the ObjectFactory

                String categoryIdStr = cm.getString(namespace, keyPropertyName);
                if (categoryIdStr == null) {
                    break;
                } else {
                    long categoryId = Long.parseLong(categoryIdStr);
                    if (categoryId < 0) {
                        throw new ConfigurationException("Value of [" + ACTIVE_PROJECT_CATEGORY_ID
                                + categoryEntryNumber + "] should not be negative.");
                    }
                    log(Level.DEBUG, "Adding activeCategoryId: " + categoryId);
                    this.activeCategoriesList.add(categoryId);
                }
            }

        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("Given namespace can't be recognized by ConfigManager.", ex);
        } catch (IllegalReferenceException ex) {
            throw new ConfigurationException("IllegalReferenceException occurred when initializing ObjectFactory.",
                    ex);
        } catch (SpecificationConfigurationException ex) {
            throw new ConfigurationException(
                    "SpecificationConfigurationException occurred when initializing ObjectFactory.", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new ConfigurationException("The configuration for ObjectFactory is invalid.", ex);
        } catch (NumberFormatException ex) {
            throw new ConfigurationException("Long value in configuration can not be converted to 'long' type.", ex);
        } catch (IllegalArgumentException ex) {
            throw new ConfigurationException("Some configuration value for ObjectFactory is null or empty.", ex);
        } catch (ClassCastException ex) {
            throw new ConfigurationException("Object created by ObjectFactory can not be casted to specific type.",
                    ex);
        }
    }

    /**
     * <p>
     * Parameter constructor.
     * </p>
     *
     * @param projectRetrieval
     *            the ProjectRetrieval instance that is used to retrieve the project technologies
     *            information
     * @param resourceManager
     *            the ResourceManager instance that is used to retrieve resources
     * @param phaseManager
     *            the PhaseManager instance that is used to obtain phase information about a project
     * @param teamManager
     *            the TeamManager instance that is used to retrieve teams
     * @param projectManager
     *            the ProjectManager instance that is used to retrieve projects
     * @param projectLinkManager
     *            the ProjectLinkManager instance that is used to manage project links
     * @param scorecardManager
     *            the ScorecardManager instance that is used to manage scorecards
     * @param reviewManager
     *            the ReviewManager instance that is used to manage reviews
     * @param logger
     *            used to log information
     * @param activeProjectStatusId
     *            the id of the active project status
     * @throws IllegalArgumentException
     *             if projectRetrieval or resourceManager or phaseManager or teamManager or
     *             projectManager is null, or activeProjectStatusId is negative
     */
    public ProjectServicesImpl(ProjectRetrieval projectRetrieval, ResourceManager resourceManager,
            PhaseManager phaseManager, TeamManager teamManager, ProjectManager projectManager, Log logger,
            long activeProjectStatusId, PhaseTemplate phaseTemplate, ProjectLinkManager projectLinkManager,
            ScorecardManager scorecardManager, ReviewManager reviewManager) {
        Util.checkObjNotNull(projectRetrieval, "projectRetrieval", null);
        Util.checkObjNotNull(resourceManager, "resourceManager", null);
        Util.checkObjNotNull(phaseManager, "phaseManager", null);
        Util.checkObjNotNull(teamManager, "teamManager", null);
        Util.checkObjNotNull(projectManager, "projectManager", null);
        Util.checkObjNotNull(projectLinkManager, "projectLinkManager", null);
        Util.checkObjNotNull(scorecardManager, "scorecardManager", null);
        Util.checkObjNotNull(reviewManager, "reviewManager", null);
        Util.checkIDNotNegative(activeProjectStatusId, "activeProjectStatusId", null);

        this.projectRetrieval = projectRetrieval;
        this.resourceManager = resourceManager;
        this.phaseManager = phaseManager;
        this.teamManager = teamManager;
        this.projectManager = projectManager;
        this.projectLinkManager = projectLinkManager;
        this.scorecardManager = scorecardManager;
        this.reviewManager = reviewManager;
        this.logger = logger;
        this.activeProjectStatusId = activeProjectStatusId;
        this.template = phaseTemplate;
    }


    /**
     * <p>
     * Creates new object by ObjectFactory.
     * </p>
     *
     * @param cm
     *            ConfigManager instance
     * @param objFactory
     *            ObjectFactory instance
     * @param namespace
     *            the namespace of configuration
     * @param propertyName
     *            the property name in configuration
     * @return the new created object
     * @throws IllegalArgumentException
     *             if any property value in configuration is null or empty
     * @throws InvalidClassSpecificationException
     *             if configuration for ObjectFactory is invalid
     * @throws UnknownNamespaceException
     *             if given namespace is unknown by ConfigManager
     */
    private Object createObject(ConfigManager cm, ObjectFactory objFactory, String namespace, String propertyName)
            throws InvalidClassSpecificationException, UnknownNamespaceException {
        // gets the property value by ConfigManager
        String propertyValue = cm.getString(namespace, propertyName);
        // creates a new object using ObjectFactory
        return objFactory.createObject(propertyValue);
    }

    /**
     * <p>
     * Logs messages if necessary.
     * </p>
     *
     * @param level
     *            the log level
     * @param msg
     *            the log message
     */
    private void log(Level level, String msg) {
        if (logger != null) {
            logger.log(level, msg);
        }
    }

    /**
     * <p>
     * Logs the exceptions.
     * </p>
     *
     * @param ex
     *            The exception to log.
     * @param msg
     *            The message
     * @since 1.1
     */
    private void logError(Exception ex, String msg) {
        if (logger != null) {
            logger.log(Level.ERROR, ex, msg);
        }
    }

    /**
     * <p>
     * Logs debug level message while calling external Topcoder classes.
     * </p>
     *
     * @param msg
     *            the logging message
     */
    private void logDebug(String msg) {
        log(Level.DEBUG, msg);
    }

    /**
     * <p>
     * This method finds all active projects along with all known associated information. Returns
     * empty array if no projects found.
     * </p>
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findActiveProjects() {
        log(Level.INFO, "Enters ProjectServicesImpl#findActiveProjects method.");
        // finds the active projects
        Project[] projects = findActiveProjectsHeaders();
        // assembles the FullProjectDatas
        FullProjectData[] fullDatas = assembleFullProjectDatas(projects);

        log(Level.INFO, "Exits ProjectServicesImpl#findActiveProjects method.");
        return fullDatas;
    }

    /**
     * <p>
     * This method finds all active projects. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findActiveProjectsHeaders() {
        log(Level.INFO, "Enters ProjectServicesImpl#findActiveProjectsHeaders method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects
            logDebug("Starts calling ProjectManager#searchProjects method.");

            Filter filter = ProjectFilterUtility.buildStatusIdEqualFilter(activeProjectStatusId);

            for (long categoryId : activeCategoriesList) {
                Filter filterForCategory = ProjectFilterUtility.buildCategoryIdEqualFilter(categoryId);
                filter = ProjectFilterUtility.buildAndFilter(filterForCategory, filter);
            }

            projects = projectManager.searchProjects(filter);

            logDebug("Finished calling ProjectManager#searchProjects method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#findActiveProjectsHeaders method.");
            throw new ProjectServicesException("PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findActiveProjectsHeaders method.");
        return projects;
    }

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return Project array with project info, or empty array if none found
     */
    public Project[] findAllTcDirectProjects(TCSubject tcSubject) {
        log(Level.INFO, "Enters ProjectServicesImpl#findAllTcDirectProjects(tcSubject) method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getAllTcDirectProjects(tcSubject) method.");

            projects = projectManager.getAllTcDirectProjects();

            logDebug("Finished calling ProjectManager#getAllTcDirectProjects(tcSubject) method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#findAllTcDirectProjects(tcSubject) method.");
            throw new ProjectServicesException("PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findAllTcDirectProjects(tcSubject) method.");
        return projects;

    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param operator The user to search for projects
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return   Project array with project info, or empty array if none found
     */
    public Project[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String user) {
        log(Level.INFO, "Enters ProjectServicesImpl#findAllTcDirectProjectsForUser(tcSubject, user) method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getAllTcDirectProjects(tcSubject, user) method.");

            projects = projectManager.getAllTcDirectProjects(user);

            logDebug("Finished calling ProjectManager#getAllTcDirectProjects(tcSubject, user) method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#findAllTcDirectProjectsForUser(tcSubject, user) method.");
            throw new ProjectServicesException("PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findAllTcDirectProjectsForUser(tcSubject, user) method.");
        return projects;

    }

    /**
     * <p>
     * This method finds all projects along with all known associated information that match the
     * search criteria. Returns empty array if no projects found.
     * </p>
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        log(Level.INFO, "Enters ProjectServicesImpl#findFullProjects method.");
        Util.checkObjNotNull(filter, "filter", logger);

        // filter ProjectHeaders
        Project[] projects = findProjectsHeaders(filter);
        // assembles FullProjectDatas
        FullProjectData[] fullProjects = assembleFullProjectDatas( projects);

        log(Level.INFO, "Exits ProjectServicesImpl#findFullProjects method.");
        return fullProjects;
    }

    /**
     * <p>
     * This method finds all projects that match the search criteria. Returns empty array if no
     * projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findProjectsHeaders(Filter filter) {
        log(Level.INFO, "Enters ProjectServicesImpl#findProjectsHeaders method.");
        Util.checkObjNotNull(filter, "filter", logger);

        Project[] projects = null;
        try {
            // searches projects using given filter
            logDebug("Starts calling ProjectManager#searchProjects method.");
            projects = projectManager.searchProjects(filter);
            logDebug("Finished calling ProjectManager#searchProjects method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#findProjectsHeaders method.");
            throw new ProjectServicesException("PersistenceException occurred when searching projects.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findProjectsHeaders method.");
        return projects;
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null
     * if not found.
     * </p>
     *
     * <p>
     * Module Contest Service Software Contest Sales Assembly change: fetch the contest sale info.
     * </p>
     *
     * @return the project along with all known associated information
     * @param projectId
     *            the ID of the project to retrieve
     * @throws IllegalArgumentException
     *             if projectId is negative
     * @throws ProjectServicesException
     *             if there is a system error while performing the search
     * @throws TeamPersistenceException
     *             if error occurred when searching teams
     */
    public FullProjectData getFullProjectData(long projectId) {
        log(Level.INFO, "Enters ProjectServicesImpl#getFullProjectData method.");
        Util.checkIDNotNegative(projectId, "projectId", logger);

        FullProjectData fullProjectData = null;
        try {
            // gets the phase project
            logDebug("Starts calling PhaseManager#getPhases method.");
            com.topcoder.project.phases.Project phaseProject = phaseManager.getPhases(projectId);
            logDebug("Finished calling PhaseManager#getPhases method.");
            if (phaseProject == null) {
                log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
                return null;
            }

            // creates an instance of FullProjectData with phaseProject
            fullProjectData = new FullProjectData(phaseProject.getStartDate(), phaseProject.getWorkdays());
            Phase[] allPhases = phaseProject.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                fullProjectData.addPhase(allPhases[i]);
            }

            // gets the project header
            logDebug("Starts calling ProjectManager#getProject method.");
            Project projectHeader = projectManager.getProject(projectId);
            logDebug("Finished calling ProjectManager#getProject method.");
            // if not found, return null
            if (projectHeader == null) {
                log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
                return null;
            }
            // sets the project header
            fullProjectData.setProjectHeader(projectHeader);

            // searches the resources associated with give project
            logDebug("Starts calling ResourceManager#searchResources method.");
            Resource[] resources = resourceManager.searchResources(ResourceFilterBuilder
                    .createProjectIdFilter(projectId));
            logDebug("Finished calling ResourceManager#searchResources method.");
            // sets the resources to fullProjectData
            fullProjectData.setResources(resources);

            // searches the teams associated with given project
            logDebug("Starts calling TeamManager#findTeams method.");
            TeamHeader[] teams = teamManager.findTeams(projectId);
            logDebug("Finished calling TeamManager#findTeams method.");
            // sets the teams to fullProjectData
            fullProjectData.setTeams(teams);

//COMMENT OUT, we dont need to set technologies here
/*            String externalProjectIdStr = (String) projectHeader.getProperty(EXTERNAL_REFERENCE_ID);
            if (externalProjectIdStr != null) {
                long externalProjectId = Long.parseLong(externalProjectIdStr);
                // retrieves the external project with given project id
                logDebug("Starts calling ProjectRetrieval#retrieveProject method.");
                ExternalProject externalProject = projectRetrieval.retrieveProject(externalProjectId);
                logDebug("Finished calling ProjectRetrieval#retrieveProject method.");
                // gets the technologies associated with give project and sets them to
                // fullProjectData
                fullProjectData.setTechnologies(externalProject.getTechnologies());
            } else {
                logDebug("Project " + projectId + " is missing external reference");
            }
*/

            // get the contest sale
            fullProjectData.setContestSales(this.getContestSales(projectId));
        } catch (NumberFormatException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("PhaseManagementException occurred when retrieving project phases.",
                    ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("PhaseManagementException occurred when retrieving project phases.",
                    ex);
        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("PersistenceException occurred when retrieving project.", ex);
        } catch (SearchBuilderException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("SearchBuilderException occurred when searching resources.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException("ResourcePersistenceException occurred when searching resources.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
        return fullProjectData;
    }

    /**
     * <p>
     * Helper method to obtains full project information for each of the passed projects. The
     * returned array will be of the same dimension as the passed array, and each
     * <code>Project</code> in projects will correspond to its <code>FullProjectData</code> at
     * the same index in the returned array. Since this is a helper method, no parameter checking is
     * done.
     * </p>
     *
     * @return FullProjectData array with the full project info.
     * @param projects
     *            The projects whose full associated information needs to be retrieved
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    private FullProjectData[] assembleFullProjectDatas(Project[] projects) {
        log(Level.INFO, "Enters ProjectServicesImpl#assembleFullProjectDatas method.");

        FullProjectData[] fullProjects = new FullProjectData[projects.length];
        for (int i = 0; i < projects.length; i++) {
            fullProjects[i] = getFullProjectData(projects[i].getId());
        }

        log(Level.INFO, "Exits ProjectServicesImpl#assembleFullProjectDatas method.");
        return fullProjects;
    }

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and
     * resources) will be assigned as new, for this reason there is no exception like 'project
     * already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its
     * properties and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date will be the current
     * date time when the project is created. The id in Project will be ignored: a new id will be
     * created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project
     * header previous saved will be set to project Phases. The phases' ids will be set to 0 (id not
     * set) and then new ids will be created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved
     * will be set to resources. The ids of resources' phases ids must be null. See &quot;id problem
     * with resources&quot; thread in design forum. The resources could be empty or null, null is
     * treated like empty: no resources are saved. The resources' ids will be set to UNSET_ID of
     * Resource class and therefore will be persisted as new resources'.
     * </p>
     * <p>
     * The logging must performed in the same manner of other methods. Read the 1.4.1 section of
     * Component Specification for further details.
     * </p>
     *
     * <p>
     * Module Contest Service Software Contest Sales Assembly change: return the wrapped value for project header, phases, resources info.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param operator
     *            the operator used to audit the operation, cannot be null or empty
     * @return the created project.
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null or the phases of projectPhases are empty;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to
     *             projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: resource.id != Resource.UNSET_ID or a required field
     *             of the resource is not set : if resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since 1.1
     */
    public FullProjectData createProject(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, String operator) {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#createProject method.");

        ExceptionUtils.checkNull(projectHeader, null, null, "The parameter[projectHeader] should not be null.");

        // check projectPhases
        ExceptionUtils.checkNull(projectPhases, null, null, "The parameter[projectPhases] should not be null.");
        if (projectPhases.getAllPhases().length == 0) {
            throw new IllegalArgumentException("The phases of projectPhases should not be empty.");
        }
        // if the project of phases (for each phase: phase.project)
        // is not equal to projectPhases, throws IAE
        for (Phase phase : projectPhases.getAllPhases()) {
            phase.setProject(projectPhases);
            /*if (!phase.getProject().equals(projectPhases)) {
                throw new IllegalArgumentException(
                        "The Project of phase in projectPhases should equal to the projectPhases.");
            }*/
        }

        // check projectResources
        if (projectResources != null) {
            Util.checkArrrayHasNull(projectResources, "projectResources");
            for (Resource resource : projectResources) {
                // check resource.id, if resource.id != Resource.UNSET_ID then throw IAE
                if (Resource.UNSET_ID != resource.getId()) {
                    throw new IllegalArgumentException("The resource.id should be UNSET_ID.");
                }
                ExceptionUtils.checkNull(resource.getResourceRole(), null, null,
                        "The ResourceRole of resource in projectResources should not be null.");
            }
        }

        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        try {
            // call projectManager.createProject(projectHeader,operator)
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#createProject method.");
            projectManager.createProject(projectHeader, operator);
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#createProject method.");

            // at this point projectHeader will have the id set
            // set the projectPhases.id to projectHeader.id
            projectPhases.setId(projectHeader.getId());

            // for each phase in projectPhases.phases:
            for (Phase phase : projectPhases.getAllPhases()) {
                // set the phase.id to 0 (id not set)
                phase.setId(0);
            }

            // call phaseManager.updatePhases(projectPhases,operator)
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#updatePhases method.");
            phaseManager.updatePhases(projectPhases, operator);
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#updatePhases method.");

            // if projectResources are not null and not empty:
            if (projectResources != null && projectResources.length > 0) {
                // for each resource: set the project to projectHeader.id
                for (Resource resource : projectResources) {
                    resource.setProject(projectHeader.getId());
                }

                // call resourceManager.updateResources(projectResources, projectHeader.getId(),
                // operator);
                Util.log(logger, Level.DEBUG, "Starts calling ResourceManager#updateResources method.");
                projectResources = resourceManager.updateResources(projectResources, projectHeader.getId(), operator);
                Util.log(logger, Level.DEBUG, "Finished calling ResourceManager#updateResources method.");
            }

            // creates an instance of FullProjectData with phaseProject
            FullProjectData projectData = new FullProjectData(projectPhases.getStartDate(), projectPhases.getWorkdays());
            projectData.setId(projectPhases.getId());
            Phase[] allPhases = projectPhases.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                projectData.addPhase(allPhases[i]);
            }

            // sets the project header
            projectData.setProjectHeader(projectHeader);

            // sets the resources to fullProjectData
            projectData.setResources(projectResources);

            return projectData;
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#createProject method : " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ValidationException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ValidationException occurred in ProjectServicesImpl#createProject method : " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (PhaseManagementException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseManagementException occurred in ProjectServicesImpl#createProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ResourcePersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ResourcePersistenceException occurred in ProjectServicesImpl#createProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        }
         catch (Exception e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "Exception occurred in ProjectServicesImpl#createProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#createProject method.");
        }
    }

    /**
     * <p>
     * Update the project and all related data. First it updates the projectHeader a
     * com.topcoder.management.project.Project instance. All related items will be updated. If items
     * are removed from the project, they will be deleted from the persistence. Likewise, if new
     * items are added, they will be created in the persistence. For the project, its properties and
     * associating scorecards, the operator parameter is used as the modification user and the
     * modification date will be the current date time when the project is updated. See the source
     * code of Project Management component, ProjectManager: there is a 'reason' parameter in
     * updateProject method.
     * </p>
     * <p>
     * Then it updates the phases a com.topcoder.project.phases.Project instance. The id of
     * projectHeader previous saved must be equal to projectPhases' id. The
     * projectPhases.phases.project's id must be equal to projectHeader's id. The phases of the
     * specified project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store. If any phases are missing from the
     * input, they are deleted. All other phases are updated.
     * </p>
     * <p>
     * At last it updates the resources, they can be empty. Any resources in the array with UNSET_ID
     * are assigned an id and updated in the persistence. Any resources with an id already assigned
     * are updated in the persistence. Any resources associated with the project in the persistence
     * store, but not appearing in the array are removed. The resource.project must be equal to
     * projectHeader's id. The resources which have a phase id assigned ( a resource could not have
     * the phase id assigned), must have the phase id contained in the projectPhases.phases' ids.
     * </p>
     * <p>
     * The logging must performed in the same manner of other methods. Read the 1.4.1 section of
     * Component Specification for further details.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectHeaderReason
     *            the reason of projectHeader updating.
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null or projectHeader.id is nonpositive;</li>
     *             <li>if projectHeaderReason is null or empty;</li>
     *             <li>if projectPhases is null, or if the phases of projectPhases are empty, or if
     *             the projectPhases.id is not equal to projectHeader.id, or for each phase: if the
     *             phase.object is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>for each resource: if resource.getResourceRole() is null, or if the resource
     *             role is associated with a phase type but the resource is not associated with a
     *             phase, or if the resource.phase (id of phase) is set but it's not in
     *             projectPhases.phases' ids, or if the resource.project (project's id) is not equal
     *             to projectHeader's id;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectDoesNotExistException
     *             if the project doesn't exist in persistent store.
     * @throws ProjectServicesException
     *             if there is a system error while performing the update operation
     * @since 1.1
     */
    public FullProjectData updateProject(Project projectHeader, String projectHeaderReason,
            com.topcoder.project.phases.Project projectPhases, Resource[] projectResources, String operator) {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#updateProject method.");

        // check projectHeader
        ExceptionUtils.checkNull(projectHeader, null, null, "The parameter[projectHeader] should not be null.");
        if (projectHeader.getId() <= 0) {
            throw new IllegalArgumentException("The projectHeader.id must be positive.");
        }

        // check projectHeaderReason
        ExceptionUtils.checkNullOrEmpty(projectHeaderReason, null, null,
                "The parameter[projectHeaderReason] should not be null or empty.");

        if (template != null)
        {
            projectPhases.setWorkdays(template.getWorkdays());
        }


        validateUpdatePhases(projectHeader, projectPhases);
        validateUpdateResources(projectHeader, projectPhases, projectResources);

        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        try {
            // get the project calling projectManager.getProject(projectHeader.getId())
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#createProject method.");
            Project project = projectManager.getProject(projectHeader.getId());
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#createProject method.");
            // if the project does not exist then throw the ProjectDoesNotExistException
            if (project == null) {
                ProjectDoesNotExistException pde = new ProjectDoesNotExistException(
                        "The project with the specified id does not exist.", projectHeader.getId());
                Util.log(logger, Level.ERROR, pde.getMessage());
                throw pde;
            }


            // call projectManager.updateProject(projectHeader,projectHeaderReason,operator)
            Util.log(logger, Level.DEBUG, "Starts calling ProjectManager#updateProject method.");
            projectManager.updateProject(projectHeader, projectHeaderReason, operator);
            Util.log(logger, Level.DEBUG, "Finished calling ProjectManager#updateProject method.");


            // recalcuate phase dates in case project start date changes
            Phase[] phases = projectPhases.getAllPhases();
            Map phasesMap = new HashMap();
            for (Phase p : phases) {
                        phasesMap.put(new Long(p.getId()), p);
                        p.setScheduledStartDate(null);
                        p.setScheduledEndDate(null);
                        p.setFixedStartDate(null);
             }
            phaseManager.fillDependencies(phasesMap, new long[]{projectPhases.getId()});
            for (Phase p : phases) {
                        p.setScheduledStartDate(p.calcStartDate());
                        p.setScheduledEndDate(p.calcEndDate());
                        // only set Reg with fixed dates
                        if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId())
                        {
                            p.setFixedStartDate(p.calcStartDate());
                        }
                        

            }

             // Adjust the depending projects timelines if necessary
            ContestDependencyAutomation auto
                = new ContestDependencyAutomation(phaseManager, projectManager, projectLinkManager);
            if (template != null)
            {
                auto
                = new ContestDependencyAutomation(phaseManager, projectManager, projectLinkManager, template.getWorkdays());
            }
            adjustDependentProjects(projectPhases, phaseManager, auto, operator);


            // call phaseManager.updatePhases(projectPhases,operator)
            Util.log(logger, Level.DEBUG, "Starts calling PhaseManager#updatePhases method.");
            phaseManager.updatePhases(projectPhases, operator);
            Util.log(logger, Level.DEBUG, "Finished calling PhaseManager#updatePhases method.");

            // if projectResources are not null and not empty, call
            // resourceManager.updateResources(projectResources, projectHeader.getId(), operator);
            if (projectResources != null && projectResources.length > 0) {
                Util.log(logger, Level.DEBUG, "Starts calling ResourceManager#updateResources method.");
                resourceManager.updateResources(projectResources, projectHeader.getId(), operator);
                Util.log(logger, Level.DEBUG, "Finished calling ResourceManager#updateResources method.");
            }


            // creates an instance of FullProjectData with phaseProject
            FullProjectData projectData = new FullProjectData(projectPhases.getStartDate(), projectPhases.getWorkdays());
            projectData.setId(projectPhases.getId());
            Phase[] allPhases = projectPhases.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                projectData.addPhase(allPhases[i]);
            }

            // sets the project header
            projectData.setProjectHeader(projectHeader);

            // sets the resources to fullProjectData
            projectData.setResources(projectResources);

            return projectData;
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersistenceException occurred in ProjectServicesImpl#updateProject method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ValidationException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ValidationException occurred in ProjectServicesImpl#updateProject method : " + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (PhaseManagementException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseManagementException occurred in ProjectServicesImpl#updateProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        } catch (ResourcePersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "ResourcePersistenceException occurred in ProjectServicesImpl#updateProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        }
        catch (Exception e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "Exception occurred in ProjectServicesImpl#updateProject method : "
                            + e.getMessage(), e);
            logError(e, pse.getMessage());
            throw pse;
        }
        finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#updateProject method.");
        }
    }

    /**
     * <p>
     * Creates a new contest sale and returns the created contest sale.
     * </p>
     *
     * @param contestSaleData the contest sale to create
     *
     * @return the created contest sale.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSaleData createContestSale(ContestSaleData contestSaleData) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#createContestSale method.");

        ExceptionUtils.checkNull(contestSaleData, null, null, "The parameter[contestSaleData] should not be null.");

        try {
            ContestSale contestSale = convertContestSaleData(contestSaleData);

            contestSale = projectManager.createContestSale(contestSale);

            return convertContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#createContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#createContestSale method.");
        }
    }

    /**
     * <p>
     * Gets contest sale by id, and return the retrieved contest sale. If
     * the contest sale doesn't exist, null is returned.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return the retrieved contest sale, or null if id doesn't exist
     *
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSaleData getContestSale(long contestSaleId) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#getContestSale method.");

        try {
            ContestSale contestSale = projectManager.getContestSale(contestSaleId);

            return convertContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#getContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#getContestSale method.");
        }
    }

    /**
     * <p>
     * Gets contest sales by contest id, and return the retrieved contest sales.
     * </p>
     *
     * @param contestId the contest id of the contest sale
     *
     * @return the retrieved contest sales, or empty if none exists
     *
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public List<ContestSaleData> getContestSales(long contestId) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#getContestSales method.");

        try {
            List<ContestSale> contestSales = projectManager.getContestSales(contestId);

            List<ContestSaleData> ret = new ArrayList<ContestSaleData>();

            for (ContestSale contestSale : contestSales) {
                ret.add(convertContestSale(contestSale));
            }

            return ret;
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#getContestSales method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#getContestSales method.");
        }
    }

    /**
     * <p>
     * Updates the contest sale data.
     * </p>
     *
     * @param contestSaleData the contest sale to update
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public void editContestSale( ContestSaleData contestSaleData) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#editContestSale method.");

        ExceptionUtils.checkNull(contestSaleData, null, null, "The parameter[contestSaleData] should not be null.");

        try {
            ContestSale contestSale = convertContestSaleData(contestSaleData);

            projectManager.editContestSale(contestSale);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#editContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#editContestSale method.");
        }
    }

    /**
     * <p>
     * Removes contest sale, return true if the contest sale exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return true if the contest sale exists and removed successfully,
     *         return false if it doesn't exist
     *
     * @throws ProjectServicesException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public boolean removeContestSale( long contestSaleId) throws ProjectServicesException {
        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#removeContestSale method.");

        try {
            return projectManager.removeContestSale(contestSaleId);
        } catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PersisteceException occurred in ProjectServicesImpl#removeContestSale method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#removeContestSale method.");
        }
    }

    /**
     * This method used to convert ContestSaleData object into ContestSale object.
     *
     * @param data ContestSaleData object to convert.
     *
     * @return converted ContestSale instance
     *
     * @throws PersistenceException when error reported by manager
     */
    private ContestSale convertContestSaleData(ContestSaleData data) throws PersistenceException {
        ContestSale result = new ContestSale();
        result.setContestSaleId(data.getContestSaleId());
        result.setContestId(data.getContestId());
        result.setPayPalOrderId(data.getPaypalOrderId());
        result.setPrice(data.getPrice());
        result.setCreateDate(data.getCreateDate());

        SaleStatus status = projectManager.getSaleStatus(data.getSaleStatusId());
        result.setStatus(status);
        result.setSaleReferenceId(data.getSaleReferenceId());

        SaleType saleType = projectManager.getSaleType(data.getSaleTypeId());
        result.setSaleType(saleType);

        return result;
    }

    /**
     * This method converts ContestSale object into ContestSaleData object.
     *
     * @param contestSale ContestSale instance to convert
     *
     * @return converted ContestSaleDate object
     */
    private ContestSaleData convertContestSale(ContestSale contestSale) {
        ContestSaleData contestSaleData = new ContestSaleData();
        contestSaleData.setContestSaleId(contestSale.getContestSaleId());
        contestSaleData.setContestId(contestSale.getContestId());
        contestSaleData.setPaypalOrderId(contestSale.getPayPalOrderId());
        contestSaleData.setPrice(contestSale.getPrice());
        contestSaleData.setCreateDate(contestSale.getCreateDate());
        contestSaleData.setSaleStatusId(contestSale.getStatus().getSaleStatusId());
        contestSaleData.setSaleReferenceId(contestSale.getSaleReferenceId());
        contestSaleData.setSaleTypeId(contestSale.getSaleType().getSaleTypeId());

        return contestSaleData;
    }

    /**
     * <p>
     * Validates the projectPhases in the updateProject method.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectPhases is null</li>
     *             <li>if the phases of projectPhases are empty, </li>
     *             <li>if the projectPhases.id is not equal to projectHeader.id;</li>
     *             <li>for each phase: if the phase.object is not equal to projectPhases, </li>
     *             </ul>
     */
    private void validateUpdatePhases(Project projectHeader, com.topcoder.project.phases.Project projectPhases) {
        // if projectPhases is null or the phases of projectPhases are empty or the projectPhases.id
        // is not equal to projectHeader.id, throws IAE
        ExceptionUtils.checkNull(projectPhases, null, null, "The parameter[projectPhases] should not be null.");
        if (projectPhases.getAllPhases().length == 0) {
            throw new IllegalArgumentException("The phases of projectPhases should not be empty.");
        }
        if (projectPhases.getId() != projectHeader.getId()) {
            throw new IllegalArgumentException("The projectPhases.id should equal to projectHeader.id.");
        }
        // for each phase: if the phase.object is not equal to projectPhases or the
        // projectPhases.phases.project.id is not equal to projectHeader.id, throws IAE
        for (Phase phase : projectPhases.getAllPhases()) {
            phase.setProject(projectPhases);
            /*if (!phase.getProject().equals(projectPhases)) {
                throw new IllegalArgumentException(
                        "The Project of phase in projectPhases should equal to the projectPhases.");
            }*/
        }
    }

    /**
     * <p>
     * Validates the projectResources in the updateProject method.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is
     *            treated like empty.
     * @throws IllegalArgumentException
     *             if projectResources contains null entries or for each resource:
     *             <ul>
     *             <li>if resource.getResourceRole() is null</li>
     *             <li>if the resource role is associated with a phase type but the resource is not
     *             associated with a phase</li>
     *             <li>if the resource.phase (id of phase) is set but it's not in
     *             projectPhases.phases' ids</li>
     *             <li>if the resource.project (project's id) is not set or not equal to
     *             projectHeader.id;</li>
     *             </ul>
     */
    private void validateUpdateResources(Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources) {
        // check projectResources
        if (projectResources != null) {
            // if projectResources contains null entries;
            Util.checkArrrayHasNull(projectResources, "projectResources");
            // for each resource:
            for (Resource resource : projectResources) {
                ResourceRole resourceRole = resource.getResourceRole();
                // if resource.getResourceRole() is null, throws IAE
                ExceptionUtils.checkNull(resourceRole, null, null,
                        "The ResourceRole of resource in projectResources should not be null.");

                Long phaseType = resourceRole.getPhaseType();
                Long phaseId = resource.getPhase();
                // if the resourceRole#phaseType!=null then also resource#phase must be not null.
                if (phaseType != null && phaseId == null) {
                    throw new IllegalArgumentException("The resource role is associated with a phase type"
                            + " but the resource is not associated with a phase.");
                }

                // if the resource.phase (id of phase) is set but it's not in projectPhases.phases'
                // ids, throws IAE
                if (phaseId != null) {
                    // Represents the phaseId is in projectPhases.phases' ids or not
                    boolean inPhasesIds = false;
                    for (Phase phase : projectPhases.getAllPhases()) {
                        if (phaseId == phase.getId()) {
                            inPhasesIds = true;
                            break;
                        }
                    }
                    if (!inPhasesIds) {
                        throw new IllegalArgumentException(
                                "The resource.phase (id of phase) is set but it's not in projectPhases.phases' ids.");
                    }
                }

                // if the resource.project (project's id) is not set or not equal to
                // projectHeader.id, throws IAE
                Long projectId = resource.getProject();
                if (projectId == null) {
                    throw new IllegalArgumentException("The resource.project must be set.");
                } else if (projectHeader.getId() != projectId) {
                    throw new IllegalArgumentException("The resource.project must equal to prohectHeader.id.");
                }
            }
        }
    }

    /**
     * <p>
     * Persist the project and all related data. All ids (of project header, project phases and resources) will be
     * assigned as new, for this reason there is no exception like 'project already exists'.
     * </p>
     * <p>
     * First it persist the projectHeader a com.topcoder.management.project.Project instance. Its properties and
     * associating scorecards, the operator parameter is used as the creation/modification user and the creation date
     * and modification date will be the current date time when the project is created. The id in Project will be
     * ignored: a new id will be created using ID Generator (see Project Management CS). This id will be set to Project
     * instance.
     * </p>
     * <p>
     * Then it persist the phases a com.topcoder.project.phases.Project instance. The id of project header previous
     * saved will be set to project Phases. The phases' ids will be set to 0 (id not set) and then new ids will be
     * created for each phase after persist operation.
     * </p>
     * <p>
     * At last it persist the resources, they can be empty.The id of project header previous saved will be set to
     * resources. The ids of resources' phases ids must be null. See &quot;id problem with resources&quot; thread in
     * design forum. The resources could be empty or null, null is treated like empty: no resources are saved. The
     * resources' ids will be set to UNSET_ID of Resource class and therefore will be persisted as new resources's.
     * </p>
     *
     * @param projectHeader
     *            the project's header, the main project's data
     * @param projectPhases
     *            the project's phases
     * @param projectResources
     *            the project's resources, can be null or empty, can't contain null values. Null is treated like empty.
     * @param operator
     *            the operator used to audit the operation, can be null but not empty
     * @throws IllegalArgumentException
     *             if any case in the following occurs:
     *             <ul>
     *             <li>if projectHeader is null;</li>
     *             <li>if projectPhases is null;</li>
     *             <li>if the project of phases (for each phase: phase.project) is not equal to projectPhases;</li>
     *             <li>if projectResources contains null entries;</li>
     *             <li>if for each resources: a required field of the resource is not set : if
     *             resource.getResourceRole() is null;</li>
     *             <li>if operator is null or empty;</li>
     *             </ul>
     * @throws ProjectServicesException
     *             if there is a system error while performing the create operation
     * @since BUGR-1473
     */
    public FullProjectData createProjectWithTemplate( Project projectHeader, com.topcoder.project.phases.Project projectPhases,
            Resource[] projectResources, String operator) {

        Util.log(logger, Level.INFO, "Enters ProjectServicesImpl#createProjectWithTemplate method.");

        ExceptionUtils.checkNull(projectHeader, null, null, "The parameter[projectHeader] should not be null.");

        // check projectPhases
        ExceptionUtils.checkNull(projectPhases, null, null, "The parameter[projectPhases] should not be null.");
        try {
            String category = projectHeader.getProjectCategory().getName();
            String type = projectHeader.getProjectCategory().getProjectType().getName();

            String[] templates = template.getAllTemplateNames();

            String templateName = null;
            boolean categoryMatch = false;
            for (String t : templates )
            {
                if (category.equalsIgnoreCase(t))
                {
                    templateName = t;
                    categoryMatch = true;
                    break;
                }
            }

            if (!categoryMatch)
            {
                for (String t : templates )
                {
                    if (type.equalsIgnoreCase(t))
                    {
                        templateName = t;
                        break;
                    }
                }

            }

            if (templateName == null)
            {
                throw new PhaseTemplateException("No template found for type "+ type+" or category "+category);
            }
            // apply a template with name category with a given start date
            com.topcoder.project.phases.Project newProjectPhases = template
                    .applyTemplate(templateName, projectPhases.getStartDate());

            long screenTemplateId = 0L;
            long reviewTemplateId = 0L;
            long approvalTemplateId = 0L;
            long projectTypeId = projectHeader.getProjectCategory().getId();

            try
            {
                screenTemplateId = projectManager.getScorecardId(projectHeader.getProjectCategory().getId(), 1);
                reviewTemplateId = projectManager.getScorecardId(projectHeader.getProjectCategory().getId(), 2);
                approvalTemplateId = projectManager.getScorecardId(ProjectCategory.GENERIC_SCORECARDS.getId(), 3);
            }
            catch (Exception e)
            {
                //TODO default to user spec (6) for now
                Util.log(logger, Level.INFO, "Default scorecard not found for project type " + projectHeader.getProjectCategory().getId() + ", used project type 6 as default");
                screenTemplateId = projectManager.getScorecardId(6, 1);
                reviewTemplateId = projectManager.getScorecardId(6, 2);
                approvalTemplateId = projectManager.getScorecardId(ProjectCategory.GENERIC_SCORECARDS.getId(), 3);
            }

            // Start BUGR-3616
            // get billing project id from the project information
            String billingProject = projectHeader.getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

            long billingProjectId = 0;

            if (billingProject != null && !billingProject.equals("") && !billingProject.equals("0")) {
                billingProjectId = Long.parseLong(billingProject);
            }

            // check whether billing project id requires approval phase
            boolean requireApproval = projectManager.requireApprovalPhase(billingProjectId);

            if (!requireApproval) {
                // remove the approval phase from the end if not required
                Phase lastPhase = newProjectPhases.getAllPhases()[newProjectPhases.getAllPhases().length - 1];

                if (lastPhase.getPhaseType().getName().equalsIgnoreCase("Approval")) {

                    newProjectPhases.removePhase(lastPhase);

                    Util.log(logger, Level.DEBUG, "Approval phase is removed since Approval required is"
                            + requireApproval);

                }
            }

            // set the project info of type "Approval Required"
            projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, String
                    .valueOf(requireApproval));
            // End BUGR-3616

            for (Phase p : newProjectPhases.getAllPhases()) {
                    p.setPhaseStatus(PhaseStatus.SCHEDULED);
                    p.setScheduledStartDate(p.calcStartDate());
                    p.setScheduledEndDate(p.calcEndDate());
                    if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId())
                    {
                        p.setFixedStartDate(p.calcStartDate());
                    }
                    

                    if (p.getPhaseType().getName().equals("Registration"))
                    {
                        p.setAttribute("Registration Number", "0");
                    }
                    else if (p.getPhaseType().getName().equals("Submission"))
                    {
                        p.setAttribute("Submission Number", "0");
                    }
                    else if (p.getPhaseType().getName().equals("Screening"))
                    {
                        p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(screenTemplateId));
                    }
                    else if (p.getPhaseType().getName().equals(REVIEW_PHASE_TYPE_NAME))
                    {
                        p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(reviewTemplateId));
                        if (category.equals(SPEC_REVIEW_PROJECT_CATEGORY)) {
                            // specification reviews only require one reviewer.
                            p.setAttribute("Reviewer Number", "1");
                        } else {
                            p.setAttribute("Reviewer Number", "3");
                        }
                    }
                    else if (p.getPhaseType().getName().equals("Appeals"))
                    {
                        p.setAttribute("View Response During Appeals", "No");
                    }
                    else if (p.getPhaseType().getName().equals("Approval"))
                    {
                       p.setAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY, String.valueOf(approvalTemplateId));
                       p.setAttribute("Reviewer Number", "1");
                    }
            }


            return this.createProject(projectHeader, newProjectPhases, projectResources, operator);

        } catch (PhaseTemplateException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseTemplateException occurred in ProjectServicesImpl#createProjectWithTemplate method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        }
        catch (PersistenceException e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseTemplateException occurred in ProjectServicesImpl#createProjectWithTemplate method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        }
        catch (Exception e) {
            ProjectServicesException pse = new ProjectServicesException(
                    "PhaseTemplateException occurred in ProjectServicesImpl#createProjectWithTemplate method : " + e.getMessage(),
                    e);
            logError(e, pse.getMessage());
            throw pse;
        }finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesImpl#createProjectWithTemplate method.");
        }
    }
    /**
     * <p>
     * Get SimpleProjectContestData for all projects.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return List of SimpleProjectContestData
     * @throws ProjectServicesException if any error occurs
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(TCSubject tcSubject)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getSimpleProjectContestData(tcSubject) method.");

        // represents the active projects
        List<SimpleProjectContestData> ret = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getSimpleProjectContestData(tcSubject) method.");

            ret = projectManager.getSimpleProjectContestData();

            logDebug("Finished calling ProjectManager#getSimpleProjectContestData(tcSubject) method.");

        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectContestData(tcSubject) method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }

        log(Level.INFO,
                "Exits ProjectServicesImpl#getSimpleProjectContestData(tcSubject) method.");
        return ret;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData( long pid)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getSimpleProjectContestData method.");

        // represents the active projects
        List<SimpleProjectContestData> ret = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getSimpleProjectContestData method.");

            ret = projectManager.getSimpleProjectContestData(pid);

            logDebug("Finished calling ProjectManager#getSimpleProjectContestData method.");

        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectContestData method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }

        log(Level.INFO,
                "Exits ProjectServicesImpl#getSimpleProjectContestData method.");
        return ret;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(
            String user) throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getSimpleProjectContestDataByUser method.");

        // represents the active projects
        List<SimpleProjectContestData> ret = null;
        try {
            // find all projects which have tc direct project id
            logDebug("Starts calling ProjectManager#getSimpleProjectContestDataByUser method.");

            ret = projectManager.getSimpleProjectContestDataByUser(user);

            logDebug("Finished calling ProjectManager#getSimpleProjectContestDataByUser method.");

        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectContestDataByUser method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }

        log(Level.INFO,
                "Exits ProjectServicesImpl#getSimpleProjectContestData method.");
        return ret;
    }

    /**
     * <p>
     * Gets the list of project their read/write/full permissions.
     * </p>
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param createdUser
     *            the specified user for which to get the permission
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of project their read/write/full permissions.
     *
     * @throws ProjectServicesException exception if error during retrieval from persistence.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(TCSubject tcSubject, long createdUser)
            throws ProjectServicesException {
        String method = "ProjectServicesBean#getSimpleProjectPermissionDataForUser(tcSubject, createdUser) method.";

        log(Level.INFO, "Enters ProjectServicesImpl#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");

        // represents the active projects
        List<SimpleProjectPermissionData> ret = null;
        try {
            logDebug("Starts calling ProjectManager#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");

            ret = projectManager.getSimpleProjectPermissionDataForUser(createdUser);

            logDebug("Finished calling ProjectManager#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getSimpleProjectPermissionDataForUser(tcSubject, createdUser)  method.");
        return ret;
    }

    /**
     * Gets the list of simple pipeline data in between specified start and end date.
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ProjectServicesException
     *             if error during retrieval from database.
     * @since 1.1.1
     */
    public List<SimplePipelineData> getSimplePipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getSimplePipelineData method.");

        List<SimplePipelineData> ret = null;
        try {
            logDebug("Starts calling ProjectManager#getSimplePipelineData method.");

            ret = projectManager.getSimplePipelineData(-1, startDate, endDate, overdueContests);

            logDebug("Finished calling ProjectManager#getSimplePipelineData method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getSimplePipelineData method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getSimplePipelineData method.");
        return ret;
    }

    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     *
     * @param userId
     *            the user id.
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ProjectServicesException
     *             if error during retrieval from database.
     * @since 1.1.1
     */
    public List<SimplePipelineData> getSimplePipelineData( long userId, Date startDate, Date endDate,
            boolean overdueContests) throws ProjectServicesException {
        log(Level.INFO, "Enters ProjectServicesImpl#getSimplePipelineData method.");

        List<SimplePipelineData> ret = null;
        try {
            logDebug("Starts calling ProjectManager#getSimplePipelineData method.");

            ret = projectManager.getSimplePipelineData(userId, startDate, endDate, overdueContests);

            logDebug("Finished calling ProjectManager#getSimplePipelineData method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in ProjectServicesImpl#getSimplePipelineData method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getSimplePipelineData method.");
        return ret;
    }


    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting
     * from tomorrow.
     *
     * @param contestType the contest type
     *
     * @return the list of capacity data
     *
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     *
     * @since 1.2
     */
    public List<SoftwareCapacityData> getCapacity( int contestType) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getCapacity(" + contestType + ") method.";

        log(Level.INFO, "Enters " + method);
        try {
            return projectManager.getCapacity(contestType);
        } catch (PersistenceException ex) {
            log(Level.ERROR, "ProjectServicesException occurred in " + method + " method.");
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * Get all design components.
     * <p>
     * Update in v1.4.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param userId The dummy user id
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @throws ProjectServicesException if any other error occurs
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject, long userId)
            throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getDesignComponents method.");

        List<DesignComponents> ret = null;
        try {
            ret = projectManager.getDesignComponents(userId);
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getDesignComponents method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getDesignComponents method.");
        return ret;
    }
     /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The contest id
     * @throws ProjectServicesException
     *             if any other error occurs
     * @since 1.2.1
     */
    public long getDevelopmentContestId( long contestId)
        throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getDevelopmentContestId method.");

        long ret;
        try {
            ret = projectManager.getDevelopmentContestId(contestId);
        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getDevelopmentContestId method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getDevelopmentContestId method.");
        return ret;
    }


     /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws ProjectServicesException
    {
        String method = "checkContestPermission(" + contestId + ", " + readonly + ", " + userId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.checkContestPermission(contestId, readonly, userId);
        } catch (PersistenceException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

    }

     /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param tcprojectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId) throws ProjectServicesException
    {
        String method = "checkContestPermission(" + tcprojectId + ", " + readonly + ", " + userId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.checkProjectPermission(tcprojectId, readonly, userId);
        } catch (PersistenceException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


     /**
     * <p>
     * get project ids by tc direct id
     * </p>
     *
     * @param tcprojectId tc direct project id
     *
     * @return list of project ids
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public List<Long> getProjectIdByTcDirectProject( long tcprojectId) throws ProjectServicesException
    {
        String method = "getProjectIdByTcDirectProject(" + tcprojectId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getProjectIdByTcDirectProject(tcprojectId);
        } catch (PersistenceException e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * <p>
     * Searches the resources in the persistence store using the given filter.
     * The filter can be formed using the field names and utility methods in ResourceFilterBuilder.
     * The return will always be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * In order to invoke this method correctly, one should properly set the resourceSearchBundle.
     * <pre>
     * A sample of the context of the search bundle is:
     *                  SELECT resource.resource_id
     *                  FROM resource
     *                  LEFT OUTER JOIN resource_submission
     *                  ON resource.resource_id = resource_submission.resource_id
     *                  LEFT OUTER JOIN resource_info
     *                  ON resource.resource_id = resource_info.resource_id
     *                  LEFT OUTER JOIN resource_info_type_lu
     *                  ON resource_info.resource_info_type_id =
     *                  resource_info_type_lu.resource_info_type_id
     *                  WHERE
     * </pre>
     *
     * Note, make sure the selected column is only one column and of the type: long in the configuration.
     *
     * </p>
     *
     * @param filter the filter to use
     *
     * @return The loaded resources
     *
     * @throws ProjectServicesException if there is an error executing the filter
     */
    public Resource[] searchResources( Filter filter) throws ProjectServicesException
    {
        String method = "searchResources(" + filter + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return resourceManager.searchResources(filter);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * <p>
     * Updates the given resource in the persistence store.
     * </p>
     *
     * <p>
     * If the resource is new (id is UNSET_ID), then an id should be assigned and
     * the resource added to the persistence store. Otherwise the resource data
     * in the persistence store would be updated.
     * </p>
     *
     *
     * @param resource the resource to update
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if a required field of the resource is not set (if resource.getResourceRole()
     *         is null), or if the resource role is associated with a phase type and the resource is not associated
     *         with a phase, or if resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the resource
     */
    public Resource updateResource( Resource resource, String operator) throws ProjectServicesException
    {

        String method = "updateResource(" + resource + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return resourceManager.updateResource(resource, operator);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * <p>
     * get forum id by project id
     * </p>
     *
     * @param projectId project id
     *
     * @return forum id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getForumId( long projectId) throws ProjectServicesException
    {

        String method = "getForumId(" + projectId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getForumId(projectId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


     /**
     * check if user has contest permission, it checks contest permission only (not project permission)
     *
     * @param contestId the contest id
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean hasContestPermission( long contestId, long userId)  throws ProjectServicesException
     {

        String method = "hasContestPermission(" + contestId + "," + userId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.hasContestPermission(contestId, userId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * <p>
     * Removes the given resource in the persistence store (by id).
     * If the id does not exist in the persistence, nothing would be removed.
     * </p>
     *
     * @param resource the resource to remove
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if the id of the resource is UNSET_ID, or the resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the persistence store
     */
    public void removeResource( Resource resource, String operator) throws ProjectServicesException
    {
     String method = "removeResource(" + resource + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            resourceManager.removeResource(resource, operator);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * Search resources by project id and role id
     *
     * @param projectId project id
     * @param roleId role id
     *
     * @return array of resoureces
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public Resource[] searchResources( long projectId, long roleId) throws ProjectServicesException
     {
        String method = "searchResources(" + projectId + "," + roleId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return resourceManager.searchResources(projectId, roleId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * <p>
     * get tc direct project id by project id
     * </p>
     *
     * @param projectId project id
     *
     * @return tc direct project id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getTcDirectProject( long projectId) throws ProjectServicesException
    {

        String method = "getTcDirectProject(" + projectId + ")";

        Util.log(logger, Level.INFO, "Enters " + method);
        try {
            return projectManager.getTcDirectProject(projectId);
        } catch (Exception e) {
            Util.log(logger, Level.ERROR, "ProjectServicesException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectManager." ,e);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * This method creates a Specification Review project associated to a project determined by parameter
     *
     * @param projectId the project id to create a Specification Review for
     * @param specReviewPrize the prize to set for the Specification Review project
     * @param operator the operator used to audit the operation, cannot be null or empty
     *
     * @return the created project
     *
     * @throws ProjectServicesException if any error occurs in the underlying services or if the specification
     * review already exists
     * @throws IllegalArgumentException if operator is null or empty or prize is negative.
     *
     * @since 1.3
     */
    public FullProjectData createSpecReview( long projectId, double specReviewPrize, String operator)
        throws ProjectServicesException {

        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#createSpecReview(" + projectId + ", " + specReviewPrize + ", " +
            operator + ") method.";
        log(Level.INFO, "Enters " + method);

        // check non-negative specification review prize
        if (specReviewPrize < 0) {
            throw new IllegalArgumentException("The parameter[specReviewPrize] should not be negative.");
        }

        // check if there is already a spec review project linked
        long specReviewProjectId = getSpecReviewProjectId(projectId);
        if (specReviewProjectId > 0) {
            throw new ProjectServicesException("There is already an associated specification review project: " +
                specReviewProjectId);
        }

        Project specReview = null;
        FullProjectData projectData = null;
        try {
            // get original project data
            FullProjectData fullProjectData = getFullProjectData(projectId);
            if (fullProjectData == null) {
                throw new ProjectServicesException("Project was not found: " + projectId);
            }
            Project project = fullProjectData.getProjectHeader();

            // set project start date to current
            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();

            // spec review starts 48 hrs before project starts or now if 48 hrs earlier is past
            Duration fotyEightHours = DatatypeFactory.newInstance().newDurationDayTime(false, 0, 48, 0, 0);

            XMLGregorianCalendar fotyEightHoursEarlier = getXMLGregorianCalendar(fullProjectData.getStartDate());
            fotyEightHoursEarlier.add(fotyEightHours);

            projectPhases.setStartDate(getDate(fotyEightHoursEarlier));
            if (projectPhases.getStartDate().before(new Date()))
            {
                projectPhases.setStartDate(new Date());
            }

            // create project header
            Project projectHeader = new Project();
            ProjectType projectType = new ProjectType(APPLICATION_PROJECT_TYPE_ID, APPLICATION_PROJECT_TYPE,
                APPLICATION_PROJECT_TYPE);
            ProjectCategory projectCategory = new ProjectCategory(SPEC_REVIEW_PROJECT_CATEGORY_ID,
                SPEC_REVIEW_PROJECT_CATEGORY, SPEC_REVIEW_PROJECT_CATEGORY, projectType);

            projectHeader.setProjectCategory(projectCategory);
            projectHeader.setProjectStatus(project.getProjectStatus());

            // clone some original project properties
            for (String key : SPEC_REVIEW_PROJECT_PROPERTIES_TO_CLONE) {
                projectHeader.setProperty(key, project.getProperty(key));
            }

            // set new properties for the spec review
            projectHeader.setProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY,
                project.getProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY) + " " + SPEC_REVIEW_PROJECT_CATEGORY);
            // Dont turn on yet
            //projectHeader.setProperty(AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY,
            //    AUTOPILOT_OPTION_PROJECT_PROPERTY_VALUE_ON);
            projectHeader.setProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY,
                          project.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY));
            projectHeader.setProperty(ProjectPropertyType.PAYMENTS_PROJECT_PROPERTY_KEY, "0");
            projectHeader.setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, "false");
            

            projectHeader.setProperty(ProjectPropertyType.NOTES_PROJECT_PROPERTY_KEY, "Contest Detail: http://www.topcoder.com/tc?module=ProjectDetail&pj="+projectId);

            // create mock ProjectSpec object
            ProjectSpec projectSpec = new ProjectSpec();
            projectSpec.setDetailedRequirements(SPEC_REVIEW_PROJECT_CATEGORY);
            projectSpec.setSubmissionDeliverables(SPEC_REVIEW_PROJECT_CATEGORY);
            projectSpec.setEnvironmentSetupInstructions(SPEC_REVIEW_PROJECT_CATEGORY);
            projectSpec.setFinalSubmissionGuidelines(SPEC_REVIEW_PROJECT_CATEGORY);

            projectHeader.setProjectSpec(projectSpec);
            projectHeader.setTcDirectProjectId(project.getTcDirectProjectId());

            // clone resources (reset Id so that they are regenerated in persistence layer)
            com.topcoder.management.resource.Resource[] resources = fullProjectData.getResources();
            for (com.topcoder.management.resource.Resource resource : resources) {
                resource.setId(com.topcoder.management.resource.Resource.UNSET_ID);
            }

            // add operator as a submitter
            com.topcoder.management.resource.Resource[] extendedResources =
                new com.topcoder.management.resource.Resource[resources.length + 1];

            System.arraycopy(resources, 0, extendedResources, 0, resources.length);
            ResourceRole submitterRole = new ResourceRole(SUBMITTER_ROLE_ID);

            com.topcoder.management.resource.Resource submitter =
                new com.topcoder.management.resource.Resource(com.topcoder.management.resource.Resource.UNSET_ID,
                    submitterRole);

            submitter.setProperty(EXTERNAL_REFERENCE_ID_RESOURCE_PROPERTY_KEY, operator);
            extendedResources[extendedResources.length - 1] = submitter;

            // create spec review project
            projectData = createProjectWithTemplate(projectHeader, projectPhases, extendedResources,
                operator);

            projectHeader.setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");

            // link it to the original project
            projectLinkManager.addProjectLink(projectId, projectData.getProjectHeader().getId(),
                                                    ProjectLinkType.REQUIRES_SPEC_REVIEW);

            specReview = projectData.getProjectHeader();
        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", ex);
        }
        catch (DatatypeConfigurationException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return projectData;
    }
    /**
     * Creates re-open contest for the given contest. since version 1.4.
     *
     * @param contest the contest to repost
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    public FullProjectData createReOpenContest( FullProjectData contest, String operator) throws ProjectServicesException {
        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#createReOpenContest(" + contest + ", " + operator + ") method.";
        log(Level.INFO, "Enters " + method);

        try {
            //1. create the new re-open contest
            //1.1 copy the project header
            Project projectHeader = new Project();
            projectHeader.setProjectCategory(contest.getProjectHeader().getProjectCategory());
            projectHeader.setProjectStatus(ProjectStatus.ACTIVE);
            ProjectSpec spec = contest.getProjectHeader().getProjectSpec();
            spec.setProjectSpecId(-1);
            projectHeader.setProjectSpec(spec);
            // clone some original project properties
            for (String key : NEW_VERSION_PROJECT_PROPERTIES_TO_CLONE) {
                projectHeader.setProperty(key, contest.getProjectHeader().getProperty(key));
            }
            //make the fee to zero
            projectHeader.setProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY, "0");
            projectHeader.setTcDirectProjectId(contest.getProjectHeader().getTcDirectProjectId());
            projectHeader.setTcDirectProjectName(contest.getProjectHeader().getTcDirectProjectName());

            //1.2 clone resources:Only observers, managers, client managers and copilots should be copied in resources.
            List<com.topcoder.management.resource.Resource> newResourcesList = new ArrayList<com.topcoder.management.resource.Resource>();
            com.topcoder.management.resource.Resource[] resources = contest.getResources();
            for (com.topcoder.management.resource.Resource resource : resources) {
                if (resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_OBSERVER_ID){
                    resource.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                    newResourcesList.add(resource);
                }
            }

            //1.3 Set the start time to 1 day + current time.
            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();
            projectPhases.setStartDate(contest.getStartDate());
            com.topcoder.management.resource.Resource[] newResources =
                 (com.topcoder.management.resource.Resource[]) newResourcesList.toArray(new com.topcoder.management.resource.Resource[newResourcesList.size()]);

            //1.4 create the project here
            FullProjectData reOpendedProject =
                createProjectWithTemplate(projectHeader, projectPhases, newResources, operator);

            //1.5 link the project to the original one
            projectLinkManager.addProjectLink(reOpendedProject.getProjectHeader().getId(),
                                                    contest.getProjectHeader().getId(), ProjectLinkType.REPOST_FOR);

            // update links
            // get all dependents
            ProjectLink[] dependents = projectLinkManager.getSourceProjectLinks(contest.getProjectHeader().getId());
            if (dependents != null && dependents.length > 0)
            {
                for (ProjectLink link : dependents)
                {
                    //ignore repost link
                    if (link.getType().getId() != ProjectLinkType.REPOST_FOR)
                    {
                        // delete existing link
                    projectLinkManager.removeProjectLink(link.getSourceProject().getId(),
                                                      link.getDestProject().getId(), link.getType().getId());

                    // depenpends link to reposted
                    projectLinkManager.addProjectLink(link.getSourceProject().getId(),
                                                      reOpendedProject.getProjectHeader().getId(), link.getType().getId());

                    // existing link changes to related_to
                    projectLinkManager.addProjectLink(link.getSourceProject().getId(),
                                                      contest.getProjectHeader().getId(), ProjectLinkType.IS_RELATED_TO);

                    }


                }

                // adjust dependents dates if necessary
                Phase[] originalPhases = phaseManager.getPhases(contest.getProjectHeader().getId()).getAllPhases();
                Phase[] reopenedPhases = phaseManager.getPhases(reOpendedProject.getProjectHeader().getId()).getAllPhases();


                Phase originalLastPhase = getLastPhase(originalPhases);
                Phase reopenedLastPhase = getLastPhase(reopenedPhases);

                // check diff between orignal end date and reopened end date
                if (originalLastPhase != null && reopenedLastPhase != null) {
                    Date originalEndDate = originalLastPhase.getScheduledEndDate();
                    Date reopenedEndDate = reopenedLastPhase.getScheduledEndDate();
                    if (reopenedEndDate.compareTo(originalEndDate) != 0)
                    {
                        long diff = reopenedEndDate.getTime() - originalEndDate.getTime();
                        if (diff != 0)
                        {
                             ContestDependencyAutomation auto
                                    = new ContestDependencyAutomation(phaseManager, projectManager, projectLinkManager);
                             if (template != null)
                             {
                                 auto
                                    = new ContestDependencyAutomation(phaseManager, projectManager, projectLinkManager, template.getWorkdays());
                             }
                             List<Phase[]> affectedPhases = auto.adjustDependingProjectPhases(reOpendedProject.getProjectHeader().getId(), diff);
                             for (Phase[] affectedProjectPhases : affectedPhases)
                             {
                                phaseManager.updatePhases(affectedProjectPhases[0].getProject(), operator);
                             }
                        }
                    }
                }
            }

            return reOpendedProject;

        } catch (PersistenceException e) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", e);
        } catch (PhaseManagementException e) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating ProjectLinkManager.", e);
        }finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }
    /**
     * Creates new version for development and design contest for the given contest.since version 1.4.
     *
     * @param contest the contest to create new version
     * @param operator the operator
     * @return new contest for the repost one
     * @throws ProjectServicesException if any error occurs
     */
    public FullProjectData createNewVersionContest( FullProjectData contest, String operator) throws ProjectServicesException {
        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#createNewVersionContest(" + contest + ", " + operator + ") method.";
        log(Level.INFO, "Enters " + method);

        try {
            //1.1 copy the project header
            Project projectHeader = new Project();
            projectHeader.setProjectCategory(contest.getProjectHeader().getProjectCategory());
            projectHeader.setProjectStatus(ProjectStatus.ACTIVE);

            ProjectSpec spec = contest.getProjectHeader().getProjectSpec();
            spec.setProjectSpecId(-1);
            projectHeader.setProjectSpec(spec);
            // clone some original project properties
            for (String key : NEW_VERSION_PROJECT_PROPERTIES_TO_CLONE) {
                projectHeader.setProperty(key, contest.getProjectHeader().getProperty(key));
            }
            projectHeader.setTcDirectProjectId(contest.getProjectHeader().getTcDirectProjectId());
            projectHeader.setTcDirectProjectName(contest.getProjectHeader().getTcDirectProjectName());

            //1.2 clone resources:Only observers, managers, client managers and copilots should be copied in resources.
            List<com.topcoder.management.resource.Resource> newResourcesList = new ArrayList<com.topcoder.management.resource.Resource>();
            com.topcoder.management.resource.Resource[] resources = contest.getResources();
            for (com.topcoder.management.resource.Resource resource : resources) {
                if (resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_MANAGER_ID
                        ||resource.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_OBSERVER_ID){
                    resource.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                    newResourcesList.add(resource);
                }
            }

            //1.3 Set the start time to 1 day + current time.
            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project();
            projectPhases.setStartDate(contest.getStartDate());

            com.topcoder.management.resource.Resource[] newResources =
                 (com.topcoder.management.resource.Resource[]) newResourcesList.toArray(new com.topcoder.management.resource.Resource[newResourcesList.size()]);

            //1.4 create the project here
            FullProjectData reOpendedProject =
                createProjectWithTemplate(projectHeader, projectPhases, newResources, operator);

            return reOpendedProject;
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services, if an invalid
     * number of reviewers or reviews are found or if the code fails to retrieve scorecard id.
     *
     * @since 1.3
     */
    public ScorecardReviewData getScorecardAndReview( long projectId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getScorecardAndReview(" + projectId + ") method.";

        ScorecardReviewData scorecardReviewData = new ScorecardReviewData();

        log(Level.INFO, "Enters " + method);
        try {
            // Build resources filter
            Filter filterProject = ResourceFilterBuilder.createProjectIdFilter(projectId);
            Filter filterRole = ResourceFilterBuilder.createResourceRoleIdFilter(REVIEWER_RESOURCE_ROLE_ID);
            Filter filterRoles = new AndFilter(filterProject, filterRole);

            // Search for the reviewers
            Resource[] reviewers = resourceManager.searchResources(filterRoles);
            if (reviewers.length > 1) {
                throw new ProjectServicesException("Invalid number of reviewers found: " + reviewers.length);
            }

            Review review = null;
            if (reviewers.length == 1) {
                // build reviews filter
                Filter filterReviewer = new EqualToFilter(RESOURCE_REVIEWER_PROPERTY, reviewers[0].getId());

                // Search for the reviews
                Review[] reviews = reviewManager.searchReviews(filterReviewer, true);
                if (reviews.length > 1) {
                    throw new ProjectServicesException("Invalid number of reviews found: " + reviewers.length);
                }

                if (reviews.length == 1) {
                    review = reviews[0];
                }
            }

            // if we have the review, we can get scorecard id from there, otherwise we need to search in review phase
            long scorecardId = -1;
            if (review != null) {
                // set review in DTO
                scorecardReviewData.setReview(review);
                scorecardId = review.getScorecard();
            } else {
                com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);

                if (projectPhases != null) {
                    Set<Phase> phases = projectPhases.getPhases();
                    Iterator<Phase> iter = phases.iterator();

                    while (iter.hasNext() && scorecardId < 0) {
                        Phase phase = iter.next();
                        if (phase.getPhaseType().getName().equals(REVIEW_PHASE_TYPE_NAME)) {
                            scorecardId = Long.parseLong(phase.getAttribute(SCORECARD_ID_PHASE_ATTRIBUTE_KEY).toString());
                        }
                    }
                }
            }

            if (scorecardId < 0) {
                throw new ProjectServicesException("Failed to find scorecard id");
            }

            // get scorecard and set it in DTO
            scorecardReviewData.setScorecard(scorecardManager.getScorecard(scorecardId));
        } catch (ReviewManagementException ex) {
            log(Level.ERROR, "ReviewManagementException occurred in " + method);
            throw new ProjectServicesException("ReviewManagementException occurred when operating Review Manager.", ex);
        } catch (com.topcoder.management.scorecard.PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating Scorecard Manager.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ResourcePersistenceException occurred in " + method);
            throw new ProjectServicesException("ResourcePersistenceException occurred when operating Resource Manager.",
                ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating Phase Manager.", ex);
        } catch (SearchBuilderException ex) {
            log(Level.ERROR, "SearchBuilderException occurred in " + method);
            throw new ProjectServicesException("SearchBuilderException occurred when operating Search Builder.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return scorecardReviewData;
    }

    /**
     * This method retrieves the corresponding specification review project id of a given project.
     * The code will rely on the project links to retrieve the specification project id.
     *
     * @param projectId the project id to search for
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     *
     * @return the associated specification review project id, or -1 if it was not found.
     *
     * @since 1.3
     */
    public long getSpecReviewProjectId( long projectId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getSpecReviewProjectId(" + projectId + ") method.";

        log(Level.INFO, "Enters " + method);
        long specReviewProjectId = -1;
        try {
            ProjectLink[] projectLinks = projectLinkManager.getDestProjectLinks(projectId);
            for (int i = 0; i < projectLinks.length && specReviewProjectId < 0; i++) {
                if (projectLinks[i].getType().getId() == ProjectLinkType.REQUIRES_SPEC_REVIEW) {
                    specReviewProjectId = projectLinks[i].getDestProject().getId();
                }
            }
        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating ProjectLinkManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return specReviewProjectId;
    }

    /**
     * This method retrieves open phases names for a given project id
     *
     * @param projectId the project id to search for
     * @return a set with open phases names
     *
     * @throws ProjectServicesException if any error occurs during retrieval of information.
     *
     * @since 1.3
     */
    public Set<String> getOpenPhases( long projectId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#getOpenPhases(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);

        Set<String> openPhases = new HashSet<String>();
        try {
            com.topcoder.project.phases.Project projectPhases = phaseManager.getPhases(projectId);

            for (Phase phase : projectPhases.getPhases()) {
                if (phase.getPhaseStatus().getName().equals(PhaseStatus.OPEN.getName())) {
                    openPhases.add(phase.getPhaseType().getName());
                }
            }
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating PhaseManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }

        return openPhases;
    }

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null or operator is null or empty
     *
     * @since 1.3
     */
    public void addReviewComment( long reviewId, Comment comment, String operator) throws ProjectServicesException {
        // check comment
        ExceptionUtils.checkNull(comment, null, null, "The parameter[comment] should not be null.");

        // check operator
        ExceptionUtils.checkNullOrEmpty(operator, null, null, "The parameter[operator] should not be null or empty.");

        String method = "ProjectServicesImpl#addReviewComment(" + reviewId + ", " + comment + ", " + operator +
            ") method.";

        log(Level.INFO, "Enters " + method);
        try {
            reviewManager.addReviewComment(reviewId, comment, operator);
        } catch (ReviewManagementException ex) {
            log(Level.ERROR, "ReviewManagementException occurred in " + method);
            throw new ProjectServicesException("ReviewManagementException occurred when operating Review Manager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

     /**
     * <p>
     * update phases
     * </p>
     *
     * @param project project
     * @param operator operator
     *
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public void updatePhases( com.topcoder.project.phases.Project project, String operator) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#updatePhases(" + project.getId() + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            phaseManager.updatePhases(project, operator);

        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred in " + method);
            throw new ProjectServicesException("PhaseManagementException occurred when operating PhaseManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * Update the given project
     *
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the project instance.
     */
    public void updateProject( Project project, String reason, String operator) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#updateProject(" + project.getId() + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            projectManager.updateProject(project, reason, operator);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating PhaseManager.", ex);
        }
        catch (ValidationException ex) {
            log(Level.ERROR, "ValidationException occurred in " + method);
            throw new ProjectServicesException("ValidationException occurred when operating PhaseManager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * <p>
     * check if it is dev only
     * </p>
     *
     * @param projectId  project id
     *
     * @return boolean
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public boolean isDevOnly( long projectId) throws ProjectServicesException
    {
        String method = "ProjectServicesImpl#isDevOnly(" + projectId + ") method.";
        log(Level.INFO, "Enters " + method);


        try {
            return projectManager.isDevOnly(projectId);

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating PhaseManager.", ex);
        }
       finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }

    /**
     * This method links the development contest to its design contest. It simply call a method in project link manager.
     *
     * @param developmentContestId the development contest id
     *
     * @throws ProjectServicesException if any unexpected error occurs in the underlying services.
     *
     * @since 1.3.1
     */
    public void linkDevelopmentToDesignContest( long developmentContestId) throws ProjectServicesException {
        String method = "ProjectServicesImpl#linkDevelopmentToDesignContest(" + developmentContestId + ") method.";
        log(Level.INFO, "Enters " + method);
        log(Level.ERROR, "Enters1 " + developmentContestId);
        log(Level.ERROR, "Enters2 " + getDevelopmentContestId( developmentContestId));
        try {
            long designId = getDesignContestId(developmentContestId);
            if (designId != 0)
            {
                projectLinkManager.addProjectLink(developmentContestId, designId, ProjectLinkType.FOR_DESIGN);
            }

        } catch (PersistenceException ex) {
            log(Level.ERROR, "PersistenceException occurred in " + method);
            throw new ProjectServicesException("PersistenceException occurred when operating Rroject Link Manager.", ex);
        } finally {
            Util.log(logger, Level.INFO, "Exits " + method);
        }
    }


    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The contest id
     * @throws ProjectServicesException
     *             if any other error occurs
     * @since 1.2.1
     */
    public long getDesignContestId(long contestId)
        throws ProjectServicesException {
        log(Level.INFO,
                "Enters ProjectServicesImpl#getDesignContestId method.");

        long ret;
        try {
            ret = projectManager.getDesignContestId(contestId);
        } catch (PersistenceException ex) {
            log(
                    Level.ERROR,
                    "ProjectServicesException occurred in ProjectServicesImpl#getDesignContestId method.");
            throw new ProjectServicesException(
                    "PersistenceException occurred when operating ProjectManager.",
                    ex);
        }
        log(Level.INFO,
                "Exits ProjectServicesImpl#getDesignContestId method.");
        return ret;
    }


    /**
     * <p>
     * Converts specified <code>XMLGregorianCalendar</code> instance into
     * <code>Date</code> instance.
     * </p>
     *
     * @param calendar
     *            an <code>XMLGregorianCalendar</code> representing the date to
     *            be converted.
     * @return a <code>Date</code> providing the converted value of specified
     *         calendar or <code>null</code> if specified <code>calendar</code>
     *         is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>Adjusts the timelines for projects depending on specified project if necessary.</p>
     *
     * @param mainProject a <code>Project</code> providing the project details.
     * @param phaseManager a <code>PhaseManager</code> to be used for managing phases.
     * @param auto a <code>ContestDependencyAutomation</code> to be used for processing dependencies.
     * @param operator a <code>String</code> providing the operator for audit.
     * @throws PhaseManagementException if an unexpected error occurs.
     * @throws com.topcoder.management.project.PersistenceException if an unexpected error occurs.
     * @since 1.3
     */
    static void adjustDependentProjects(com.topcoder.project.phases.Project mainProject, PhaseManager phaseManager,
                                        ContestDependencyAutomation auto, String operator)
        throws PhaseManagementException, com.topcoder.management.project.PersistenceException {
        List<Phase[]> phases = auto.adjustDependingProjectPhases(mainProject.getAllPhases());
        for (Phase[] p : phases) {
            if (p.length > 0) {
                com.topcoder.project.phases.Project projectPhases = p[0].getProject();
                phaseManager.updatePhases(projectPhases, operator);
            }
        }
    }

    /**
     * <p>Gets the last phase from specified list of project phase. Current implementation looks up for the <code>Final
     * Review</code> phase but this may change later.</p>
     *
     * @param phases a <code>Phase</code> array providing current project phases.
     * @return a <code>Phase</code> providing the last phase or <code>null</code> if there is no such phase found,
     */
    private Phase getLastPhase(Phase[] phases) {
        Phase lastPhase = null;
        for (int i = 0; i < phases.length; i++) {
            Phase phase = phases[i];
            PhaseType phaseType = phase.getPhaseType();
            if ((phaseType != null) && phaseType.getName().equalsIgnoreCase("Final Review")) {
                lastPhase = phase;
            }
        }
        return lastPhase;
    }


     /**
     *  Get project only (no phase or resources)
     */
    public Project getProject(long projectId) throws ProjectServicesException
    {

        log(Level.INFO,
				"Enters ProjectServicesImpl#getProject method.");

		Project project = null;
		try {
			project = projectManager.getProject(projectId);
		} catch (PersistenceException ex) {
			log(
					Level.ERROR,
					"ProjectServicesException occurred in ProjectServicesImpl#getProject method.");
			throw new ProjectServicesException(
					"PersistenceException occurred when operating getProject.",
					ex);
		} 
		log(Level.INFO,
				"Exits ProjectServicesImpl#getProject method.");
		return project;
    }



     /**
     * Gets all resource roles in the persistence store.
     *
     * @return All resource roles in the persistence store
     * @throws ProjectServicesException
     *             If there is an error reading the persistence store.
     */
    public ResourceRole[] getAllResourceRoles() throws ProjectServicesException
    {
        log(Level.INFO,
				"Enters ProjectServicesImpl#getAllResourceRoles method.");

		ResourceRole[] allroles = null;
		try {
			allroles = resourceManager.getAllResourceRoles();
		} catch (ResourcePersistenceException ex) {
			log(
					Level.ERROR,
					"ResourcePersistenceException occurred in ProjectServicesImpl#getAllResourceRoles method.");
			throw new ProjectServicesException(
					"PersistenceException occurred when operating getAllResourceRoles.",
					ex);
		} 
		log(Level.INFO,
				"Exits ProjectServicesImpl#getAllResourceRoles method.");
		return allroles;

    }

}
