/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.failuretests;

import junit.framework.TestCase;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.studio.ConversionException;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.StudioFileType;

/**
 * Failure test for class ProjectToContestConverterImpl.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectToContestConverterImplFailureTest extends TestCase {

    /**
     * Represents instance of ProjectToContestConverterImpl for test.
     */
    private ProjectToContestConverterImpl converter;

    /**
     * Represents instance of Project for test.
     */
    private Project project;

    /**
     * Represents instance of Contest for test.
     */
    private Contest contest;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        converter = new ProjectToContestConverterImpl();
        project = TestHelper.createProject();
        contest = TestHelper.createContest();
        super.setUp();
    }

    /**
     * Failure test for ctor method ProjectToContestConverterImpl(String autoPilotSwitchPropertyName). With null
     * autoPilotSwitchPropertyName, IllegalArgumentException expected.
     */
    public void testProjectToContestConverterImpl_NullAutoPilotSwitchPropertyName() {
        try {
            new ProjectToContestConverterImpl((String) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ProjectToContestConverterImpl(String autoPilotSwitchPropertyName). With empty
     * autoPilotSwitchPropertyName, IllegalArgumentException expected.
     */
    public void testProjectToContestConverterImpl_EmptyAutoPilotSwitchPropertyName() {
        try {
            new ProjectToContestConverterImpl(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With null project, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_NullProject() throws Exception {
        try {
            converter.convertProjectToContest((Project) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_CONTEST_NAME, not
     * type of String, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_CONTEST_NAME_Obj() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, new Object());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_CONTEST_NAME, not
     * type of String, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_CONTEST_NAME_Long() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, new Long(1));
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_PROJECT_ID, not
     * type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_PROJECT_ID_Str() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_PROJECT_ID, not
     * type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_PROJECT_ID_Int() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID, new Integer(1));
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of
     * PROPERTY_TC_DIRECT_PROJECT_ID, not type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_TC_DIRECT_PROJECT_ID_Str() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of
     * PROPERTY_TC_DIRECT_PROJECT_ID, not type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_TC_DIRECT_PROJECT_ID_Int() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID, new Integer(1));
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_FORUM_ID, not
     * type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_FORUM_ID_Str() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_FORUM_ID, not
     * type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_FORUM_ID_Int() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID, new Integer(1));
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_EVENT_ID, not
     * type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_EVENT_ID_Str() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_EVENT_ID, not
     * type of Long, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_EVENT_ID_Int() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID, new Integer(1));
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_SUBMISSIONS, not
     * type of Set of Submission, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_SUBMISSIONS_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_SUBMISSIONS, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_FILE_TYPES, not
     * type of Set of StudioFileType, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_FILE_TYPES_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FILE_TYPES, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_RESULTS, not type
     * of Set of ContestResult, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_RESULTS_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_RESULTS, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_DOCUMENTS, not
     * type of Set of Document, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_DOCUMENTS_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_DOCUMENTS, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_CONFIG, not type
     * of Set of ContestConfig, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_CONFIG_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONFIG, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_CONTEST_TYPE, not
     * type of Set of ContestType, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_CONTEST_TYPE_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_TYPE, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_START_DATE, not
     * type of Date, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_START_DATE_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of PROPERTY_END_DATE, not
     * type of Date, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_END_DATE_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid type of
     * PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, not type of Date, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE_String() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, new String());
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectToContest(Project project). With invalid createdUser, ConversionException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectToContest_Invalid_CreatedUser() throws Exception {
        project.setCreationUser("112user");
        try {
            converter.convertProjectToContest(project);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestToProject(Contest contest). With null contest, IllegalArgumentException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestToProject_NullContest() throws Exception {
        try {
            converter.convertContestToProject((Contest) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestToProject(Contest contest). With null ContestChannel, ConversionException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestToProject_NullContestChannel() throws Exception {
        contest.setContestChannel(null);
        try {
            converter.convertContestToProject(contest);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestToProject(Contest contest). With null Status, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestToProject_NullStatus() throws Exception {
        contest.setStatus(null);
        try {
            converter.convertContestToProject(contest);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestToProject(Contest contest). With negative ContestId, ConversionException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestToProject_NullContestId() throws Exception {
        contest.setContestId(-1L);
        try {
            converter.convertContestToProject(contest);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectStatusToContestStatus(ProjectStatus projectStatus). With null
     * projectStatus, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestToProject_NullProjectStatus() throws Exception {
        try {
            converter.convertProjectStatusToContestStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestStatusToProjectStatus(ContestStatus contestStatus). With null
     * contestStatus, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestStatusToProjectStatus_NullContestStatus() throws Exception {
        try {
            converter.convertContestStatusToProjectStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestStatusToProjectStatus(ContestStatus contestStatus). With null
     * contestStatusId, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestStatusToProjectStatus_NullContestStatusId() throws Exception {
        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(null);
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestStatusToProjectStatus(ContestStatus contestStatus). With negative
     * contestStatusId, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestStatusToProjectStatus_NegativeContestStatusId() throws Exception {
        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(-1L);
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestStatusToProjectStatus(ContestStatus contestStatus). With null name,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestStatusToProjectStatus_NullName() throws Exception {
        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setName(null);
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestStatusToProjectStatus(ContestStatus contestStatus). With empty name,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestStatusToProjectStatus_EmptyName() throws Exception {
        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setName(" ");
        try {
            converter.convertContestStatusToProjectStatus(contestStatus);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectCategoryToContestChannel(ProjectCategory projectCategory). With null
     * projectCategory, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectCategoryToContestChannel_NullProjectCategory() throws Exception {
        try {
            converter.convertProjectCategoryToContestChannel(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestChannelToProjectCategory(ContestChannel channel). With null contestStatus,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestChannelToProjectCategory_NullContestStatus() throws Exception {
        try {
            converter.convertContestChannelToProjectCategory(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestChannelToProjectCategory(ContestChannel channel). With null
     * contestChannelId, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestChannelToProjectCategory_NullContestChannelId() throws Exception {
        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(null);
        try {
            converter.convertContestChannelToProjectCategory(channel);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestChannelToProjectCategory(ContestChannel channel). With negative
     * contestChannelId, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestChannelToProjectCategory_NegativeContestChannelId() throws Exception {
        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(-1L);
        try {
            converter.convertContestChannelToProjectCategory(channel);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestChannelToProjectCategory(ContestChannel channel). With null name,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestChannelToProjectCategory_NullName() throws Exception {
        ContestChannel channel = new ContestChannel();
        channel.setName(null);
        try {
            converter.convertContestChannelToProjectCategory(channel);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestChannelToProjectCategory(ContestChannel channel). With empty name,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestChannelToProjectCategory_EmptyName() throws Exception {
        ContestChannel channel = new ContestChannel();
        channel.setName(" ");
        try {
            converter.convertContestChannelToProjectCategory(channel);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertContestChannelToProjectCategory(ContestChannel channel). With null fileType,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertContestChannelToProjectCategory_NullFileType() throws Exception {
        ContestChannel channel = new ContestChannel();
        channel.setFileType(null);
        try {
            converter.convertContestChannelToProjectCategory(channel);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectTypeToStudioFileType(ProjectType projectType). With null projectType,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectTypeToStudioFileType_NullProjectType() throws Exception {

        try {
            converter.convertProjectTypeToStudioFileType(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertStudioFileTypeToProjectType(StudioFileType studioFileType). With null
     * studioFileType, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertStudioFileTypeToProjectType_NullStudioFileType() throws Exception {

        try {
            converter.convertStudioFileTypeToProjectType(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertStudioFileTypeToProjectType(StudioFileType studioFileType). With negative
     * studioFileType, ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertStudioFileTypeToProjectType_NegativeStudioFileType() throws Exception {
        StudioFileType studioFileType = new StudioFileType();
        studioFileType.setStudioFileType(-1);
        try {
            converter.convertStudioFileTypeToProjectType(studioFileType);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertStudioFileTypeToProjectType(StudioFileType studioFileType). With null Extension,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertStudioFileTypeToProjectType_NullExtension() throws Exception {
        StudioFileType studioFileType = new StudioFileType();
        studioFileType.setExtension(null);
        try {
            converter.convertStudioFileTypeToProjectType(studioFileType);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertStudioFileTypeToProjectType(StudioFileType studioFileType). With empty Extension,
     * ConversionException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertStudioFileTypeToProjectType_EmptyExtension() throws Exception {
        StudioFileType studioFileType = new StudioFileType();
        studioFileType.setExtension(" ");
        try {
            converter.convertStudioFileTypeToProjectType(studioFileType);
            fail("ConversionException expected");
        } catch (ConversionException e) {
            // expected
        }
    }

    /**
     * Failure test for method convertProjectFilterToContestFilter(Filter filter). With null filter, ConversionException
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConvertProjectFilterToContestFilter_NullFilter() throws Exception {

        try {
            converter.convertProjectFilterToContestFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
