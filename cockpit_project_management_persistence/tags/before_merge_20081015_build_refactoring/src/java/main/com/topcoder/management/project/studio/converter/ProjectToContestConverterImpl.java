/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.ConversionException;
import com.topcoder.management.project.studio.ProjectToContestConverter;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.util.errorhandling.ExceptionUtils;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.Transaction;

/**
 * <p>
 * This class is the default way of converting Project entities from "TC Project Management" component into Contest
 * entities from "TC Contest and Submission Entities" component.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is thread-safe, since it does not maintain state while performing
 * the conversions.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectToContestConverterImpl implements ProjectToContestConverter {

    public static final String STUDIO_FILE_TYPE_ID = "STUDIO_FILE_TYPE_ID";

    public static final String STUDIO_FILE_TYPE_EXTENSION = "STUDIO_FILE_TYPE_EXTENSION";

    public static final String STUDIO_CONTEST_CHANNEL_ID = "CONTEST.CONTEST_CHANNEL_ID";

    public static final String STUDIO_CONTEST_CHANNEL_NAME = "CONTEST_CHANNEL_LU.CONTEST_CHANNEL_DESC";

    public static final String STUDIO_CONTEST_STATUS_ID = "CONTEST.CONTEST_STATUS_ID";

    public static final String STUDIO_CONTEST_DETAILED_STATUS_ID = "CONTEST.CONTEST_DETAILED_STATUS_ID";

    public static final String STUDIO_CONTEST_STATUS_NAME = "CONTEST_STATUS_LU.NAME";

    public static final String STUDIO_CONTEST_ID = "CONTEST.CONTEST_ID";

    public static final String STUDIO_CONTEST_NAME = "CONTEST.NAME";

    public static final String STUDIO_CONTEST_PROJECT_ID = "CONTEST.PROJECT_ID";

    public static final String STUDIO_CONTEST_DIRECT_PROJECT_ID = "CONTEST.TC_DIRECT_PROJECT_ID";

    public static final String STUDIO_CONTEST_FORUM_ID = "CONTEST.FORUM_ID";

    public static final String STUDIO_CONTEST_EVENT_ID = "CONTEST.EVENT_ID";

    public static final String STUDIO_CONTEST_START_DATE = "CONTEST.START_TIME";

    public static final String STUDIO_CONTEST_END_DATE = "CONTEST.END_TIME";

    public static final String STUDIO_CONTEST_WINNER_ANNOUNCEMENT_DEADLINE = "CONTEST.WINNER_ANNOUNCEMENT_TIME";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a <code>Contest</code> name
     * (Contest.setName/getName).
     * </p>
     */
    public static final String PROPERTY_CONTEST_NAME = "ProjectToContestConverter.CONTEST_NAME";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a <code>Contest</code>
     * project id (Contest.setProjectId/getProjectId).
     * </p>
     */
    public static final String PROPERTY_PROJECT_ID = "ProjectToContestConverter.PROJECT_ID";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a <code>Contest</code> TC
     * direct project id (Contest.setTcDirectProjectId/getTcDirectProjectId).
     * </p>
     */
    public static final String PROPERTY_TC_DIRECT_PROJECT_ID = "ProjectToContestConverter.TC_DIRECT_PROJECT_ID";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a <code>Contest</code> forum
     * id (Contest.setForumId/getForumId).
     * </p>
     */
    public static final String PROPERTY_FORUM_ID = "ProjectToContestConverter.FORUM_ID";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a <code>Contest</code> event
     * id (Contest.setEventId/getEventd).
     * </p>
     */
    public static final String PROPERTY_EVENT_ID = "ProjectToContestConverter.EVENT_ID";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest's Submissions
     * (Contest.setSubmissions/getSubmissions).
     * </p>
     */
    public static final String PROPERTY_SUBMISSIONS = "ProjectToContestConverter.SUBMISSIONS";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a <code>Contest</code>
     * StudioFileTypes (Contest.setFileTypes/getFileTypes).
     * </p>
     */
    public static final String PROPERTY_FILE_TYPES = "ProjectToContestConverter.FILE_TYPES";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest Results
     * (Contest.setResults/getResults).
     * </p>
     */
    public static final String PROPERTY_RESULTS = "ProjectToContestConverter.RESULTS";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest documents
     * (Contest.setDocuments/getDocuments).
     * </p>
     */
    public static final String PROPERTY_DOCUMENTS = "ProjectToContestConverter.DOCUMENTS";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest config
     * (Contest.setConfig/getConfig).
     * </p>
     */
    public static final String PROPERTY_CONFIG = "ProjectToContestConverter.CONFIG";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest type
     * (Contest.setContestType/getContestType).
     * </p>
     */
    public static final String PROPERTY_CONTEST_TYPE = "ProjectToContestConverter.CONTEST_TYPE";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest start date
     * (Contest.setStartDate/getStartDate).
     * </p>
     */
    public static final String PROPERTY_START_DATE = "ProjectToContestConverter.START_DATE";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest end date
     * (Contest.setEndDate/getEndDate).
     * </p>
     */
    public static final String PROPERTY_END_DATE = "ProjectToContestConverter.END_DATE";

    /**
     * <p>
     * This is a static String contest that is used as the property key when adding a custom property to a
     * <code>Project</code> entity through <code>Project.setProperty</code> for a Contest winner announcement
     * deadline (Contest.setWinnerAnnouncementDeadline/getWinnerAnnouncementDeadline).
     * </p>
     */
    public static final String PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE = "ProjectToContestConverter."
        + "WINNER_ANNOUNCEMENT_DEADLINE";

    /**
     * <p>
     * Represents the size of the special AND filter.
     * </p>
     */
    private static final int SPECIAL_AND_FILTER_SIZE = 2;

    /**
     * <p>
     * Represents the filter that always represents "true", so it will not restrict the search results.
     * </p>
     */
    private static final Filter TRUE_FILTER = new NotFilter(new NullFilter(STUDIO_CONTEST_ID));

    /**
     * <p>
     * Represents the filter that always represents "false", so no records will be selected if this filter is
     * applied.
     * </p>
     */
    private static final Filter FALSE_FILTER = new NullFilter(STUDIO_CONTEST_ID);

    /**
     * <p>
     * Represents the set which holds all property names in this class.
     * </p>
     */
    private static final Set<String> PROPERTY_NAMES = new HashSet<String>(Arrays.asList(new String[] {
        PROPERTY_CONTEST_NAME, PROPERTY_PROJECT_ID, PROPERTY_TC_DIRECT_PROJECT_ID, PROPERTY_FORUM_ID,
        PROPERTY_EVENT_ID, PROPERTY_SUBMISSIONS, PROPERTY_FILE_TYPES, PROPERTY_RESULTS, PROPERTY_DOCUMENTS,
        PROPERTY_CONFIG, PROPERTY_CONTEST_TYPE, PROPERTY_START_DATE, PROPERTY_END_DATE,
        PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE }));

    /**
     * <p>
     * Represents the list of property names in ContestManager interface.
     * </p>
     */
    private static final List<String> CONTEST_PROPERTY_NAMES = Arrays.asList(new String[] {
        STUDIO_CONTEST_CHANNEL_ID, STUDIO_CONTEST_CHANNEL_NAME,
        STUDIO_CONTEST_DIRECT_PROJECT_ID, STUDIO_CONTEST_END_DATE,
        STUDIO_CONTEST_EVENT_ID, STUDIO_CONTEST_FORUM_ID,
        STUDIO_CONTEST_ID, STUDIO_CONTEST_NAME,
        STUDIO_CONTEST_PROJECT_ID, STUDIO_CONTEST_START_DATE,
        STUDIO_CONTEST_STATUS_ID, STUDIO_CONTEST_STATUS_NAME,
        STUDIO_CONTEST_WINNER_ANNOUNCEMENT_DEADLINE, STUDIO_FILE_TYPE_EXTENSION,
        STUDIO_FILE_TYPE_ID });

    /**
     * <p>
     * Represents the mapping from project property name to contest property name.
     * </p>
     */
    private static final Map<String, String> PROJECT_PROPERTY_NAME_TO_CONTEST = new HashMap<String, String>();

    /**
     * <p>
     * Represents the default value of autoPilotSwitchPropertyName.
     * </p>
     */
    private static final String DEFAULT_AUTO_PILOT_SWITCH_PROPERTY_NAME = "AutoPilotOption";

    /**
     * <p>
     * This is a variable that is used to store the auto pilot switch property name. This is used so that the
     * converter will know which Property Value filter is being used by the auto pilot.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: getAutopilotSwitch
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: convertProjectFilterToContestFilter
     * </p>
     * <p>
     * Valid Values: Not null and not empty Strings.
     * </p>
     */
    private final String autoPilotSwitchPropertyName;

    /**
     * <p>
     * Initializes the PROJECT_PROPERTY_NAME_TO_CONTEST map.
     * </p>
     */
    static {
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_CONTEST_NAME, STUDIO_CONTEST_NAME);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_PROJECT_ID, STUDIO_CONTEST_PROJECT_ID);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_TC_DIRECT_PROJECT_ID,
            STUDIO_CONTEST_DIRECT_PROJECT_ID);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_FORUM_ID, STUDIO_CONTEST_FORUM_ID);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_EVENT_ID, STUDIO_CONTEST_EVENT_ID);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_START_DATE, STUDIO_CONTEST_START_DATE);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_END_DATE, STUDIO_CONTEST_END_DATE);
        PROJECT_PROPERTY_NAME_TO_CONTEST.put(PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE,
            STUDIO_CONTEST_WINNER_ANNOUNCEMENT_DEADLINE);
    }

    /**
     * <p>
     * Constructs an instance of this class.
     * </p>
     */
    public ProjectToContestConverterImpl() {
        this.autoPilotSwitchPropertyName = DEFAULT_AUTO_PILOT_SWITCH_PROPERTY_NAME;
    }

    /**
     * <p>
     * Constructs an instance of this class with specified auto pilot name.
     * </p>
     * @param autoPilotSwitchPropertyName
     *        The variable that is used to store the auto pilot switch property name
     * @throws IllegalArgumentException
     *         If <code>autoPilotSwitchPropertyName</code> is null or an empty string
     */
    public ProjectToContestConverterImpl(String autoPilotSwitchPropertyName) {
        ExceptionUtils.checkNullOrEmpty(autoPilotSwitchPropertyName, null, null, "autoPilotSwitchPropertyname");

        this.autoPilotSwitchPropertyName = autoPilotSwitchPropertyName;
    }

    /**
     * <p>
     * Converts the specified <code>Project</code> to a <code>Contest</code>.
     * </p>
     * @param project
     *        The project to convert to a contest
     * @return The Contest which is a result of converting the project
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    @SuppressWarnings("unchecked")
    public Contest convertProjectToContest(Project project) throws ConversionException {
        ExceptionUtils.checkNull(project, null, null, "[project] should not be null.");

        Contest contest = new Contest();

        contest.setContestId(project.getId());
        // sets the contest name
        Object name = project.getProperty(PROPERTY_CONTEST_NAME);
        if (name != null) {
            checkProjectProperty(name, PROPERTY_CONTEST_NAME, String.class);
            contest.setName(name.toString());
        }
        // sets contest channel
        contest.setContestChannel(convertProjectCategoryToContestChannel(project.getProjectCategory()));
        // sets project id
        Object projectId = project.getProperty(PROPERTY_PROJECT_ID);
        if (projectId != null) {
            checkProjectProperty(projectId, PROPERTY_PROJECT_ID, Long.class);
            contest.setProjectId((Long) projectId);
        }
        // set TC direct project ID
        Object tcProjectId = project.getProperty(PROPERTY_TC_DIRECT_PROJECT_ID);
        if (tcProjectId != null) {
            checkProjectProperty(tcProjectId, PROPERTY_TC_DIRECT_PROJECT_ID, Long.class);
            contest.setTcDirectProjectId((Long) tcProjectId);
        }
        // set status
        contest.setStatus(convertProjectStatusToContestStatus(project.getProjectStatus()));
        // set the forum ID
        Object forumId = project.getProperty(PROPERTY_FORUM_ID);
        if (forumId != null) {
            checkProjectProperty(forumId, PROPERTY_FORUM_ID, Long.class);
            contest.setForumId((Long) forumId);
        }
        // set the event ID
        Object eventId = project.getProperty(PROPERTY_EVENT_ID);
        if (eventId != null) {
            checkProjectProperty(eventId, PROPERTY_EVENT_ID, Long.class);
            contest.setEventId((Long) eventId);
        }
        // set the submissions
        Object submissions = project.getProperty(PROPERTY_SUBMISSIONS);
        if (submissions != null) {
            checkProjectProperty(submissions, PROPERTY_SUBMISSIONS, Set.class, Submission.class);
            contest.setSubmissions((Set<Submission>) submissions);
        }
        // set the file types
        Object fileTypes = project.getProperty(PROPERTY_FILE_TYPES);
        if (fileTypes != null) {
            checkProjectProperty(fileTypes, PROPERTY_FILE_TYPES, Set.class, StudioFileType.class);
            contest.setFileTypes((Set<StudioFileType>) fileTypes);
        }
        // set the results
        Object results = project.getProperty(PROPERTY_RESULTS);
        if (results != null) {
            checkProjectProperty(results, PROPERTY_RESULTS, Set.class, ContestResult.class);
            contest.setResults((Set<ContestResult>) results);
        }
        // set the documents
        Object docs = project.getProperty(PROPERTY_DOCUMENTS);
        if (docs != null) {
            checkProjectProperty(docs, PROPERTY_DOCUMENTS, Set.class, Document.class);
            contest.setDocuments((Set<Document>) docs);
        }
        // set the config
        Object config = project.getProperty(PROPERTY_CONFIG);
        if (config != null) {
            checkProjectProperty(config, PROPERTY_CONFIG, Set.class, ContestConfig.class);
            contest.setConfig((Set<ContestConfig>) config);
        }
        // set the contest type
        Object contestType = project.getProperty(PROPERTY_CONTEST_TYPE);
        if (contestType != null) {
            checkProjectProperty(contestType, PROPERTY_CONTEST_TYPE, ContestType.class);
            contest.setContestType((ContestType) contestType);
        }
        Object startDate = project.getProperty(PROPERTY_START_DATE);
        if (startDate != null) {
            checkProjectProperty(startDate, PROPERTY_START_DATE, Date.class);
            contest.setStartDate((Date) startDate);
        }
        // set the end date
        Object endDate = project.getProperty(PROPERTY_END_DATE);
        if (endDate != null) {
            checkProjectProperty(endDate, PROPERTY_END_DATE, Date.class);
            contest.setEndDate((Date) endDate);
        }
        // set the winner announcement deadline
        Object deadline = project.getProperty(PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE);
        if (deadline != null) {
            checkProjectProperty(deadline, PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, Date.class);
            contest.setWinnerAnnoucementDeadline((Date) deadline);
        }
        // set the created user
        String createdUser = project.getCreationUser();
        if (createdUser != null) {
            try {
                contest.setCreatedUser(Long.parseLong(createdUser));
            } catch (NumberFormatException e) {
                throw new ConversionException(
                    "The creation user of the project should be null or an string representation of a Long value.",
                    e);
            }
        }
        return contest;
    }

    /**
     * <p>
     * Checks whether given value is of valid type.
     * </p>
     * @param value
     *        The object value to check
     * @param propertyName
     *        Name of the property
     * @param types
     *        At least one element, the first elements represents the main type of the object, if the seconds the
     *        element present, it represents the type of inner element of a Set
     * @throws ConversionException
     *         If given value is not of specified type
     */
    private void checkProjectProperty(Object value, String propertyName, Class... types)
        throws ConversionException {
        if (!types[0].isInstance(value)) {
            throw new ConversionException("The value of property " + propertyName
                + " should be null or an instance of " + types[0].getName() + ".");
        }
        if (types.length > 1) {
            for (Object obj : (Iterable) value) {
                if (!types[1].isInstance(obj)) {
                    throw new ConversionException("The type of elements of value of property " + propertyName
                        + " should be " + types[1].getName() + ".");
                }
            }
        }
    }

    /**
     * <p>
     * Converts the specified <code>Contest</code> to a <code>Project</code>.
     * </p>
     * @param contest
     *        The contest to convert to a project
     * @return The project which is a result of converting the contest
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public Project convertContestToProject(Contest contest) throws ConversionException {
        ExceptionUtils.checkNull(contest, null, null, "[contest] should not be null.");

        if (contest.getContestChannel() == null) {
            throw new ConversionException("The contestChannel of given Contest should not be null.");
        }
        if (contest.getStatus() == null) {
            throw new ConversionException("The status of given Contest should not be null.");
        }
        // creates a Project instance
        StudioFileType[] types = contest.getFileTypes().toArray(
                new StudioFileType[] {});
        if ( types.length == 0)
        {
            throw new ConversionException("Contest must have a file type. contest id: " + contest.getContestId());
        }
        ProjectCategory category = convertFileTypeToProjectCategory(types[0]);
        ProjectStatus status = convertContestStatusToProjectStatus(contest.getStatus());
        Project project = null;
        if (contest.getContestId() == null) {
            project = new Project(category, status);
        } else if (contest.getContestId() <= 0) {
            throw new ConversionException("The contest ID of given Contest should not be negative.");
        } else {
            project = new Project(contest.getContestId().longValue(), category, status);
        }

        // sets the name property
        project.setProperty(PROPERTY_CONTEST_NAME, contest.getName());
        // sets the project id property
        project.setProperty(PROPERTY_PROJECT_ID, contest.getProjectId());
        // sets the TC direct project id
        project.setProperty(PROPERTY_TC_DIRECT_PROJECT_ID, contest.getTcDirectProjectId());
        // sets the forum id
        project.setProperty(PROPERTY_FORUM_ID, contest.getForumId());
        // sets the event id
        project.setProperty(PROPERTY_EVENT_ID, contest.getEventId());
        // sets the submissions
        project.setProperty(PROPERTY_SUBMISSIONS, contest.getSubmissions());
        // sets the file types
        project.setProperty(PROPERTY_FILE_TYPES, contest.getFileTypes());
        // sets the results
        project.setProperty(PROPERTY_RESULTS, contest.getResults());
        // sets the documents
        project.setProperty(PROPERTY_DOCUMENTS, contest.getDocuments());
        // sets the config
        project.setProperty(PROPERTY_CONFIG, contest.getConfig());
        // sets the contest type
        project.setProperty(PROPERTY_CONTEST_TYPE, contest.getContestType());
        // sets the start date
        project.setProperty(PROPERTY_START_DATE, contest.getStartDate());
        // sets the end date
        project.setProperty(PROPERTY_END_DATE, contest.getEndDate());
        // sets the winner announcement deadline
        project.setProperty(PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, contest.getWinnerAnnoucementDeadline());
        // sets the creation date
        if (contest.getCreatedUser() != null) {
            project.setCreationUser(contest.getCreatedUser().toString());
        }

        return project;
    }

    /**
     * <p>
     * Converts the given <code>ProjectStatus</code> to a <code>ContestStatus</code>.
     * </p>
     * @param projectStatus
     *        The ProjectStatus to convert to a ContestStatus.
     * @return The ContestStatus which is the result of converting the ProjectStatus
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ContestStatus convertProjectStatusToContestStatus(ProjectStatus projectStatus)
        throws ConversionException {
        ExceptionUtils.checkNull(projectStatus, null, null, "[projectStatus] should not be null.");

        ContestStatus status = new ContestStatus();

        status.setContestStatusId(projectStatus.getId());
        status.setDescription(projectStatus.getDescription());
        status.setName(projectStatus.getName());

        return status;
    }

    /**
     * <p>
     * Converts the given <code>ContestStatus</code> to a <code>ProjectStatus</code>.
     * </p>
     * @param contestStatus
     *        The ContestStatus to convert to a ProjectStatus.
     * @return The ProjectStatus which is the converted form of the ContestStatus
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ProjectStatus convertContestStatusToProjectStatus(ContestStatus contestStatus)
        throws ConversionException {
        ExceptionUtils.checkNull(contestStatus, null, null, "[contestStatus] can't be null.");

        if (contestStatus.getContestStatusId() == null || contestStatus.getContestStatusId() <= 0) {
            throw new ConversionException(
                "The contest status ID of given ContestStatus should be a positive number.");
        }
        if (contestStatus.getName() == null || contestStatus.getName().trim().length() == 0) {
            throw new ConversionException("The name of given ContestStatus should not be null or empty.");
        }

        // creates a project status
        ProjectStatus status = new ProjectStatus(contestStatus.getContestStatusId(), contestStatus.getName());

        // sets the description
        status.setDescription(contestStatus.getDescription() == null ? "" : contestStatus.getDescription());

        return status;
    }

    /**
     * <p>
     * Converts a <code>ProjectCategory</code> to a <code>ContestChannel</code>.
     * </p>
     * @param projectCategory
     *        a ProjectCategory to a ContestChannel.
     * @return The ContestChannel which is the result of converting the ProjectCategory
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ContestChannel convertProjectCategoryToContestChannel(ProjectCategory projectCategory)
        throws ConversionException {
        ExceptionUtils.checkNull(projectCategory, null, null, "[projectCategory] should not be null.");

        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(projectCategory.getId());
        channel.setDescription(projectCategory.getDescription());

        return channel;
    }

    /**
     * <p>
     * Converts a <code>ContestChannel</code> to a <code>ProjectCategory</code>.
     * </p>
     * @param channel
     *        The ContestChannel to convert to a ProjectCategory.
     * @return The ProjectCategory which is the result of converting the ContestChannel
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ProjectCategory convertFileTypeToProjectCategory(StudioFileType fileType)
        throws ConversionException {
        ExceptionUtils.checkNull(fileType, null, null, "[fileType] should not be null.");

        // creates a project category
        ProjectCategory category = new ProjectCategory(fileType.getStudioFileType(), fileType.getDescription(),
            convertStudioFileTypeToProjectType(fileType));
        // sets the description
        category.setDescription(fileType.getDescription() == null ? "" : fileType.getDescription());

        return category;
    }

    /**
     * <p>
     * Converts a <code>ProjectType</code> to a <code>StudioFileType</code>.
     * </p>
     * @param projectType
     *        The ProjectType to convert to a StudioFileType.
     * @return The StudioFileType which is the result of converting the ProjectType
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public StudioFileType convertProjectTypeToStudioFileType(ProjectType projectType) throws ConversionException {
        ExceptionUtils.checkNull(projectType, null, null, "[projectType] should not be null.");

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription(projectType.getDescription());
        fileType.setStudioFileType(projectType.getId());
        fileType.setExtension(projectType.getName());

        return fileType;
    }

    /**
     * <p>
     * Converts a <code>StudioFileType</code> to a <code>ProjectType</code>.
     * </p>
     * @param studioFileType
     *        The StudioFileType to convert to a ProjectType
     * @return The ProjectType which is the result of converting the StudioFileType
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ProjectType convertStudioFileTypeToProjectType(StudioFileType studioFileType)
        throws ConversionException {
        ExceptionUtils.checkNull(studioFileType, null, null, "[studioFileType] should not be null.");

        if (studioFileType.getStudioFileType() <= 0) {
            throw new ConversionException("The studioFileType of given StudioFileType should not be less than 1.");
        }
        if (studioFileType.getExtension() == null || studioFileType.getExtension().trim().length() == 0) {
            throw new ConversionException("The extension of given StudioFileType should not be null or empty.");
        }
        // creates a ProjectType
        ProjectType type = new ProjectType(studioFileType.getStudioFileType(), studioFileType.getExtension());
        // sets the description
        type.setDescription(studioFileType.getDescription() == null ? "" : studioFileType.getDescription());

        return type;
    }

    /**
     * <p>
     * Converts the specified <code>ProjectFilter</code> to a <code>ContestFilter</code>.
     * </p>
     * <p>
     * It is expected that the given <code>Filter</code> should be either a simple Filter created directly using
     * <code>ProjectFilterUtility</code> (from Project Management component), or an
     * <code>AssociativeFilter</code> (<code>AndFilter</code>/<code>OrFilter</code>/<code>NotFilter</code>)
     * containing either more AssociativeFilters, or simple Filters created using <code>ProjectFilterUtility</code>.
     * </p>
     * <p>
     * The returned <code>Filter</code> should contain the same associative hiearchy, but with the simple Filters
     * converted to the appropriate simple Filters for the <code>ContestManager</code> component,
     * </p>
     * @param filter
     *        The Filter from Project Management to convert.
     * @return The Filter appropriate for Contest Manager that is the result of performing the conversion
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public Filter convertProjectFilterToContestFilter(Filter filter) throws ConversionException {
        ExceptionUtils.checkNull(filter, null, null, "[filter] can't be null.");

        return processFilter(filter);
    }

    /**
     * <p>
     * Converts the special AND filter if possible.
     * @param filter
     *        The AND filter to convert
     * @return An EqualToFilter, or null if given filter can't be converted
     */
    private Filter processSpecialANDFilter(AndFilter filter) {
        List filters = filter.getFilters();
        if (filters.size() == SPECIAL_AND_FILTER_SIZE) {
            Filter first = (Filter) filters.get(0);
            Filter second = (Filter) filters.get(1);
            // check if these two filters meet the requirement for special AND filter
            if (first instanceof EqualToFilter && second instanceof EqualToFilter
                && ((EqualToFilter) first).getName().equals(ProjectFilterUtility.PROJECT_PROPERTY_NAME)
                && ((EqualToFilter) second).getName().equals(ProjectFilterUtility.PROJECT_PROPERTY_VALUE)) {
                // cast them to EqualToFilter
                EqualToFilter theFirst = (EqualToFilter) first;
                EqualToFilter theSecond = (EqualToFilter) second;

                if (PROJECT_PROPERTY_NAME_TO_CONTEST.keySet().contains(theFirst.getValue())) {
                    return new EqualToFilter(PROJECT_PROPERTY_NAME_TO_CONTEST.get(theFirst.getValue()), theSecond
                        .getValue());
                } else if (autoPilotSwitchPropertyName.equals(theFirst.getValue())) {
                    return buildAutoPilotSwitchFilter(theSecond.getValue());
                } else {
                    return TRUE_FILTER;
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * Converts the filter.
     * </p>
     * @param filter
     *        The filter to convert
     * @return The converted filter
     */
    private Filter processFilter(Filter filter) {
        if (filter instanceof NotFilter) {
            // process the NotFilter
            return new NotFilter(processFilter(((NotFilter) filter).getFilter()));

        } else if (filter instanceof OrFilter) {
            // process the OrFilter
            List<Filter> innerFilters = new ArrayList<Filter>();
            for (Object theFilter : ((OrFilter) filter).getFilters()) {
                innerFilters.add(processFilter((Filter) theFilter));
            }
            return new OrFilter(innerFilters);

        } else if (filter instanceof AndFilter) {
            // process the AndFilter
            Filter res = processSpecialANDFilter((AndFilter) filter);
            if (res != null) {
                return res;
            }
            List<Filter> innerFilters = new ArrayList<Filter>();
            for (Object theFilter : ((AndFilter) filter).getFilters()) {
                innerFilters.add(processFilter((Filter) theFilter));
            }
            return new AndFilter(innerFilters);

        } else if (filter instanceof EqualToFilter) {
            // Process EqualToFilter
            return processEqualToFilter((EqualToFilter) filter);

        } else if (filter instanceof InFilter) {
            // Process InFilter
            return processInFilter((InFilter) filter);
        }
        // for unknown filters, just return TRUE_FILTER
        return TRUE_FILTER;
    }

    /**
     * <p>
     * Processes the EqualToFilter.
     * </p>
     * @param theFilter
     *        The EqualToFilter to process
     * @return The converted filter
     */
    private Filter processEqualToFilter(EqualToFilter theFilter) {
        // PROJECT_TYPE_ID
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_TYPE_ID)) {
            return new EqualToFilter(STUDIO_FILE_TYPE_ID, theFilter.getValue());
        }
        // PROJECT_TYPE_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_TYPE_NAME)) {
            return new EqualToFilter(STUDIO_FILE_TYPE_EXTENSION, theFilter.getValue());
        }
        // PROJECT_CATEGORY_ID
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_CATEGORY_ID)) {
            return new EqualToFilter(STUDIO_CONTEST_CHANNEL_ID, theFilter.getValue());
        }
        // PROJECT_CATEGORY_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_CATEGORY_NAME)) {
            return new EqualToFilter(STUDIO_CONTEST_CHANNEL_NAME, theFilter.getValue());
        }
        // PROJECT_STATUS_ID
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_STATUS_ID)) {
            return new EqualToFilter(STUDIO_CONTEST_STATUS_ID, theFilter.getValue());
        }
        // PROJECT_STATUS_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_STATUS_NAME)) {
            return new EqualToFilter(STUDIO_CONTEST_STATUS_NAME, theFilter.getValue());
        }
        // PROJECT_PROPERTY_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_PROPERTY_NAME)) {
            return PROPERTY_NAMES.contains(theFilter.getValue()) ? TRUE_FILTER : FALSE_FILTER;
        }
        // PROJECT_PROPERTY_VALUE
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_PROPERTY_VALUE)) {
            List<Filter> innerFilters = new ArrayList<Filter>();
            for (String propertyName : CONTEST_PROPERTY_NAMES) {
                innerFilters.add(new EqualToFilter(propertyName, theFilter.getValue()));
            }
            return new OrFilter(innerFilters);
        }
        return TRUE_FILTER;
    }

    /**
     * <p>
     * Processes the InFilter.
     * </p>
     * @param theFilter
     *        The InFilter to process
     * @return The converted filter
     */
    private Filter processInFilter(InFilter theFilter) {
        // STUDIO_CONTEST_ID
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_TYPE_ID)) {
            return new InFilter(STUDIO_FILE_TYPE_ID, theFilter.getList());
        }
        // PROJECT_TYPE_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_TYPE_NAME)) {
            return new InFilter(STUDIO_FILE_TYPE_EXTENSION, theFilter.getList());
        }
        // PROJECT_CATEGORY_ID
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_CATEGORY_ID)) {
            return new InFilter(STUDIO_CONTEST_CHANNEL_ID, theFilter.getList());
        }
        // PROJECT_CATEGORY_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_CATEGORY_NAME)) {
            return new InFilter(STUDIO_CONTEST_CHANNEL_NAME, theFilter.getList());
        }
        // PROJECT_STATUS_ID
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_STATUS_ID)) {
            return new InFilter(STUDIO_CONTEST_DETAILED_STATUS_ID, theFilter.getList());
        }
        // PROJECT_STATUS_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_STATUS_NAME)) {
            return new InFilter(STUDIO_CONTEST_STATUS_NAME, theFilter.getList());
        }
        // PROJECT_PROPERTY_NAME
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_PROPERTY_NAME)) {
            for (Object val : theFilter.getList()) {
                if (PROPERTY_NAMES.contains(val)) {
                    return TRUE_FILTER;
                }
            }
            return FALSE_FILTER;
        }
        // PROJECT_PROPERTY_VALUE
        if (theFilter.getName().equals(ProjectFilterUtility.PROJECT_PROPERTY_VALUE)) {
            List<Filter> innerFilters = new ArrayList<Filter>();
            for (String propertyName : CONTEST_PROPERTY_NAMES) {
                innerFilters.add(new InFilter(propertyName, theFilter.getList()));
            }
            return new OrFilter(innerFilters);
        }
        return TRUE_FILTER;
    }

    /**
     * <p>
     * Retrieves the variable that is used to store the auto pilot switch property name.
     * </p>
     * <p>
     * This is used so that the converter will know which Property Value filter is being used by the auto pilot.
     * </p>
     * @return The variable that is used to store the auto pilot switch property name
     */
    public String getAutoPilotSwitchPropertyName() {
        return autoPilotSwitchPropertyName;
    }

    /**
     * <p>
     * This is used to build a filter for studio contests that indicates whether auto pilot is enabled for the
     * given contest or not.
     * </p>
     * <p>
     * The default is to return an always true filter (meaning, autopilot is enabled for all contests). It may be
     * overridden by subclasses to customize which contests should have autopilot enabled.
     * </p>
     * @param filterValue
     *        The filter value to use. (This may be ignored by certain implementations)
     * @return A Filter that would always return a true result for Studio Contest Manager
     */
    protected Filter buildAutoPilotSwitchFilter(Comparable filterValue) {
        return TRUE_FILTER;
    }
}
