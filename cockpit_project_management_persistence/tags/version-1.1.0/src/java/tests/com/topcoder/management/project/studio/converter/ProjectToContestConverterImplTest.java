/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.converter;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.ConversionException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;

import junit.framework.TestCase;

/**
 * This is a test case for <code>ProjectToContestConverterImpl</code> class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectToContestConverterImplTest extends TestCase {

    /**
     * Represents an instance of ProjectToContestConverterImpl.
     */
    private ProjectToContestConverterImpl converter;

    /**
     * Represents an instance of Project.
     */
    private Project project;

    /**
     * Represents an instance of ProjectType.
     */
    private ProjectType projectType;

    /**
     * Represents an instance of ProjectCategory.
     */
    private ProjectCategory category;

    /**
     * Represents an instance of ProjectStatus.
     */
    private ProjectStatus status;

    /**
     * Represents an instance of Contest.
     */
    private Contest contest;

    /**
     * Represents a contest channel.
     */
    private ContestChannel channel;

    /**
     * Represents a contest status.
     */
    private ContestStatus contestStatus;

    /**
     * Represents a contest file type.
     */
    private StudioFileType fileType;

    /**
     * Represensts a contest type.
     */
    private ContestType contestType;

    /**
     * Represents an Date.
     */
    private Date date;

    /**
     * Sets up the test environment.
     * @throws Exception
     *         to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        converter = new ProjectToContestConverterImpl();
        date = new Date();

        projectType = new ProjectType(1, "type", "this is a project type.");
        category = new ProjectCategory(2, "j2ee", "this is a project category", projectType);
        status = new ProjectStatus(3, "opening", "this is a project status");

        project = new Project(4, category, status);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, "contest name");
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID, new Long(123));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID, new Long(1234));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID, new Long(232));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID, new Long(435));
        Set<Submission> submissions = new HashSet<Submission>();
        submissions.add(new Submission());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_SUBMISSIONS, submissions);
        Set<StudioFileType> fileTypes = new HashSet<StudioFileType>();
        fileTypes.add(new StudioFileType());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FILE_TYPES, fileTypes);
        Set<ContestResult> results = new HashSet<ContestResult>();
        results.add(new ContestResult());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_RESULTS, results);
        Set<Document> docs = new HashSet<Document>();
        docs.add(new Document());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_DOCUMENTS, docs);
        Set<ContestConfig> configs = new HashSet<ContestConfig>();

        ContestConfig o = new ContestConfig();
        Identifier id = new Identifier();
        id.setContest(new Contest());
        id.setProperty(new ContestProperty());
        o.setId(id);
        configs.add(o);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONFIG, configs);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_TYPE, new ContestType());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE, new Date());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE, new Date());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, new Date());
        project.setCreationUser("12345");

        fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        channel = new ContestChannel();
        channel.setContestChannelId(new Long(2));
        channel.setDescription("This is a channel");

        contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(new Long(24));
        contestStatus.setDescription("This is a status");
        contestStatus.setName("name");

        contestType = new ContestType();
        contestType.setContestType(new Long(234));
        contestType.setDescription("this is a contest type");

        contest = new Contest();
        contest.setContestChannel(channel);
        contest.setContestId(new Long(24));
        contest.setContestType(contestType);
        contest.setCreatedUser(new Long(34654));
        contest.setEndDate(date);
        contest.setStatus(contestStatus);
        contest.setStartDate(date);

        fileTypes.add(fileType);
        contest.setFileTypes(fileTypes);
    }

    /**
     * Tears down the test environment.
     * @throws Exception
     *         to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        converter = null;
    }

    /**
     * Test for default constructor.
     * <p>
     * Tests it for accuracy, non-null object should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Should not be null.", converter);
        assertEquals("Pilot property name mismatched.", "AutoPilotOption", converter
            .getAutoPilotSwitchPropertyName());
    }

    /**
     * Test for constructor with autoPilotSwitchPropertyName parameter.
     * <p>
     * Tests it against null string, expects IAE.
     * </p>
     */
    public void testCtor2WithNullString() {
        try {
            new ProjectToContestConverterImpl(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with autoPilotSwitchPropertyName parameter.
     * <p>
     * Tests it against empty string, expects IAE.
     * </p>
     */
    public void testCtor2WithEmptyString() {
        try {
            new ProjectToContestConverterImpl("   ");
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with autoPilotSwitchPropertyName parameter.
     * <p>
     * Tests it for accuracy with "auto_pilot", expects non-null instance created.
     * </p>
     */
    public void testCtor2Accuracy() {
        converter = new ProjectToContestConverterImpl("auto_pilot");

        assertEquals("Auto pilot name mismatched.", "auto_pilot", converter.getAutoPilotSwitchPropertyName());
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against null project, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectToContestWithNullProject() throws Exception {
        try {
            converter.convertProjectToContest(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against contest name not String, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectToContestWithContestNameNotString() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, new Object());

        try {
            converter.convertProjectToContest(project);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against invalid submission set, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectToContestWithInvalidSubmissionSet() throws Exception {
        Set<Submission> subs = new HashSet<Submission>();
        subs.add(null);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, subs);

        try {
            converter.convertProjectToContest(project);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against createdUser not a Long value, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectToContestWithCreatedUserNotLongValue() throws Exception {
        project.setCreationUser("user");

        try {
            converter.convertProjectToContest(project);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it for accuracy, valid Contest should be converted.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectToContestAccuracy() throws Exception {
        Contest theContest = converter.convertProjectToContest(project);

        assertEquals("Created user mismatched.", new Long(12345), theContest.getCreatedUser());
        assertEquals("winner announcement date mismatched.", date, theContest.getWinnerAnnoucementDeadline());
        assertEquals("End date mismatched.", date, theContest.getEndDate());
        assertEquals("Start date mismatched.", date, theContest.getStartDate());
        assertEquals("Event id mismatched.", new Long(435), theContest.getEventId());
        assertEquals("Forum id mismatched.", new Long(232), theContest.getForumId());
        assertEquals("Tc direct project id mismatched.", new Long(1234), theContest.getTcDirectProjectId());
    }

    /**
     * Test for <code>convertContestToProject</code> method.
     * <p>
     * Tests it against null contest, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestToProjectWithNullContest() throws Exception {
        try {
            converter.convertContestToProject(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against ContestChannel not set, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestToProjectWithContestChannelNotSet() throws Exception {
        contest.setContestChannel(null);

        try {
            converter.convertContestToProject(contest);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against ContestStatus not set, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestToProjectWithContestStatusNotSet() throws Exception {
        contest.setStatus(null);

        try {
            converter.convertContestToProject(contest);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it against contest ID zero, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestToProjectWithZeroContestID() throws Exception {
        contest.setContestId(new Long(0));

        try {
            converter.convertContestToProject(contest);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it for accuracy with valid contest, non-null Project should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestToProjectAcc() throws Exception {
        Project theProject = converter.convertContestToProject(contest);

        assertEquals("Project Id mismatched.", 24, theProject.getId());
        assertEquals("Start date mismatched.", date, theProject
            .getProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE));
        assertEquals("Creationg user mismatched.", "34654", theProject.getCreationUser());
    }

    /**
     * Test for <code>convertProjectToContest</code> method.
     * <p>
     * Tests it for accuracy with valid contest that id is not set, non-null Project should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestToProjectAcc2() throws Exception {
        contest.setContestId(null);

        Project theProject = converter.convertContestToProject(contest);

        assertEquals("Project Id mismatched.", 0, theProject.getId());
        assertEquals("Start date mismatched.", date, theProject
            .getProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE));
        assertEquals("Creationg user mismatched.", "34654", theProject.getCreationUser());
    }

    /**
     * Test for <code>convertProjectStatusToContestStatus</code> method.
     * <p>
     * Tests it against null project status, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectStatusToContestStatusWithNullArg() throws Exception {
        try {
            converter.convertProjectStatusToContestStatus(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectStatusToContestStatus</code> method.
     * <p>
     * Tests it for accuracy with valid project status, non-null contest status should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectStatusToContestStatus() throws Exception {
        ContestStatus theContestStatus = converter.convertProjectStatusToContestStatus(status);

        assertEquals("ID mismatched.", new Long(3), theContestStatus.getContestStatusId());
    }

    /**
     * Test for <code>convertContestStatusToProjectStatus</code> method.
     * <p>
     * Tests it against null project status, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestStatusToProjectStatusWithNullArg() throws Exception {
        try {
            converter.convertContestStatusToProjectStatus(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertContestStatusToProjectStatus</code> method.
     * <p>
     * Tests it against null status ID, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestStatusToProjectStatusWithNullStatusID() throws Exception {
        contestStatus.setContestStatusId(null);
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertContestStatusToProjectStatus</code> method.
     * <p>
     * Tests it against negative status ID, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestStatusToProjectStatusWithNegativeStatusID() throws Exception {
        contestStatus.setContestStatusId(new Long(-1));
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertContestStatusToProjectStatus</code> method.
     * <p>
     * Tests it against null status name, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestStatusToProjectStatusWithNullStatusName() throws Exception {
        contestStatus.setName(null);
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertContestStatusToProjectStatus</code> method.
     * <p>
     * Tests it against empty status name, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestStatusToProjectStatusWithEmptyStatusName() throws Exception {
        contestStatus.setName("   ");
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertContestStatusToProjectStatus</code> method.
     * <p>
     * Tests it for accuracy, valid ProjectStatus should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestStatusToProjectStatusAcc() throws Exception {
        ProjectStatus theStatus = converter.convertContestStatusToProjectStatus(contestStatus);

        assertEquals("Status id mismatched.", 24, theStatus.getId());
        assertEquals("Status name mismatched.", "name", theStatus.getName());
        assertEquals("Status description mismatched.", "This is a status", theStatus.getDescription());
    }

    /**
     * Test for <code>convertProjectCategoryToContestChannel</code> method.
     * <p>
     * Tests it against null category, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectCategoryToContestChannelWithNullArg() throws Exception {
        try {
            converter.convertProjectCategoryToContestChannel(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectCategoryToContestChannel</code> method.
     * <p>
     * Tests it for accuracy, valid ContestChannel should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectCategoryToContestChannelAccuracy() throws Exception {
        ContestChannel theChannel = converter.convertProjectCategoryToContestChannel(category);

        assertEquals("Channel id mismatched.", new Long(2), theChannel.getContestChannelId());
    }

    /**
     * Test for <code>convertContestChannelToProjectCategory</code> method.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertContestChannelToProjectCategoryWithNullArgument() throws Exception {
        try {
            converter.convertFileTypeToProjectCategory(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectTypeToStudioFileType</code> method.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectTypeToStudioFileTypeWithNullArgument() throws Exception {
        try {
            converter.convertProjectTypeToStudioFileType(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectTypeToStudioFileType</code> method.
     * <p>
     * Tests it for accuracy, expects valid StudioFileType.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectTypeToStudioFileTypeAccuracy() throws Exception {
        StudioFileType theFileType = converter.convertProjectTypeToStudioFileType(projectType);

        assertEquals("ID mismatched.", 1, theFileType.getStudioFileType());
        assertEquals("Name mismatched.", "type", theFileType.getExtension());
        assertEquals("Description mismatched.", "this is a project type.", theFileType.getDescription());
    }

    /**
     * Test for <code>convertStudioFileTypeToProjectType</code> method.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertStudioFileTypeToProjectTypeWithNullArgument() throws Exception {
        try {
            converter.convertStudioFileTypeToProjectType(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertStudioFileTypeToProjectType</code> method.
     * <p>
     * Tests it against negative file type id, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertStudioFileTypeToProjectTypeWithNegativeID() throws Exception {
        fileType.setStudioFileType(-1);
        try {
            converter.convertStudioFileTypeToProjectType(fileType);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertStudioFileTypeToProjectType</code> method.
     * <p>
     * Tests it against null extension, expects ConversionException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertStudioFileTypeToProjectTypeWithExtensionNull() throws Exception {
        fileType.setExtension(null);
        try {
            converter.convertStudioFileTypeToProjectType(fileType);
            fail("Conversion exception should be thrown.");
        } catch (ConversionException e) {
            // success
        }
    }

    /**
     * Test for <code>convertStudioFileTypeToProjectType</code> method.
     * <p>
     * Tests it for accuracy, valid ProjectType should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertStudioFileTypeToProjectTypeAcc() throws Exception {
        ProjectType type = converter.convertStudioFileTypeToProjectType(fileType);

        assertEquals("Type id mismatched.", 34, type.getId());
        assertEquals("Description mismatched.", "PS", type.getDescription());
    }

    /**
     * Test for <code>convertProjectFilterToContestFilter</code> method.
     * <p>
     * Tests it against null filter, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectFilterToContestFilterWithNullArgument() throws Exception {
        try {
            converter.convertProjectFilterToContestFilter(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>convertProjectFilterToContestFilter</code> method.
     * <p>
     * Tests it for accuracy with valid fiter, the contest filter should be converted successfully.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testConvertProjectFilterToContestFilterAcc() throws Exception {
        Filter f1 = ProjectFilterUtility.buildCategoryIdEqualFilter(1);
        Filter f2 = ProjectFilterUtility.buildCategoryIdInFilter(Arrays.asList(new Long[] {new Long(1),
            new Long(2), new Long(3) }));
        Filter f3 = ProjectFilterUtility.buildCategoryNameEqualFilter("tc");
        Filter f4 = ProjectFilterUtility.buildCategoryNameInFilter(Arrays
            .asList(new String[] {"tc", "aol" }));
        Filter f5 = ProjectFilterUtility
            .buildProjectPropertyNameEqualFilter(ProjectToContestConverterImpl.PROPERTY_EVENT_ID);
        Filter f6 = ProjectFilterUtility.buildProjectPropertyNameInFilter(Arrays
            .asList(new String[] {ProjectToContestConverterImpl.PROPERTY_EVENT_ID,
                ProjectToContestConverterImpl.PROPERTY_FORUM_ID }));
        Filter f7 = ProjectFilterUtility.buildProjectPropertyValueEqualFilter("value");
        Filter f8 = ProjectFilterUtility.buildProjectPropertyValueInFilter(Arrays.asList(new String[] {"a",
            "b" }));
        Filter f9 = ProjectFilterUtility.buildStatusIdEqualFilter(234);
        Filter f10 = ProjectFilterUtility.buildStatusIdInFilter(Arrays.asList(new Long[] {new Long(1),
            new Long(45) }));
        Filter f11 = ProjectFilterUtility.buildStatusNameEqualFilter("opening");
        Filter f12 = ProjectFilterUtility.buildStatusNameInFilter(Arrays.asList(new String[] {"opening",
            "closing" }));
        Filter f13 = ProjectFilterUtility.buildTypeIdEqualFilter(1);
        Filter f14 = ProjectFilterUtility.buildTypeIdInFilter(Arrays.asList(new Long[] {(long) 1, (long) 2,
            (long) 3 }));
        Filter f15 = ProjectFilterUtility.buildTypeNameEqualFilter("type1");
        Filter f16 = ProjectFilterUtility
            .buildTypeNameInFilter(Arrays.asList(new String[] {"a", "b", "c" }));

        Filter specialAnd1 = ProjectFilterUtility.buildAndFilter(ProjectFilterUtility
            .buildProjectPropertyNameEqualFilter(ProjectToContestConverterImpl.PROPERTY_EVENT_ID),
            ProjectFilterUtility.buildProjectPropertyValueEqualFilter("value"));

        Filter specialAnd2 = ProjectFilterUtility.buildAndFilter(ProjectFilterUtility
            .buildProjectPropertyNameEqualFilter("AutoPilotOption"), ProjectFilterUtility
            .buildProjectPropertyValueEqualFilter("value"));

        Filter specialAnd3 = ProjectFilterUtility.buildAndFilter(ProjectFilterUtility
            .buildProjectPropertyNameEqualFilter("unknown"), ProjectFilterUtility
            .buildProjectPropertyValueEqualFilter("value"));

        Filter unknown = new LikeFilter("a", "SS:a");

        Filter notFilter = ProjectFilterUtility.buildNotFilter(f1);
        Filter andFilter = ProjectFilterUtility.buildAndFilter(f2, f3);
        Filter orFilter = new OrFilter(Arrays.asList(new Filter[] {f4, f5, f6, f7, f8, f9, f10, f11, f12,
            f13, f14, f15, f16, specialAnd1, notFilter, andFilter, specialAnd2, specialAnd3, unknown }));

        Filter res = converter.convertProjectFilterToContestFilter(orFilter);

        assertNotNull("'res' should not be null.", res);
    }

    /**
     * <p>
     * Tests for <code>getAutoPilotSwitchPropertyName</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, "AutoPilotOption" should be returned.
     * </p>
     */
    public void testGetAutoPilotSwitchPropertyName() {
        assertEquals("Pilot switch property name mismatched.", "AutoPilotOption", converter
            .getAutoPilotSwitchPropertyName());
    }

    /**
     * Test for <code>buildAutoPilotSwitchFilter</code> method.
     * <p>
     * Tests it for accuracy, "always true" filter should be returned.
     * </p>
     */
    public void testBuildAutoPilotSwitchFilter() {
        Filter filter = converter.buildAutoPilotSwitchFilter("value");

        assertTrue("Should be a NotFilter", filter instanceof NotFilter);
        Filter inner = ((NotFilter) filter).getFilter();
        assertTrue("Should be a NullFilter", inner instanceof NullFilter);
        assertEquals("filter name mismatched.", ProjectToContestConverterImpl.STUDIO_CONTEST_ID, ((NullFilter) inner)
            .getName());
    }
}
