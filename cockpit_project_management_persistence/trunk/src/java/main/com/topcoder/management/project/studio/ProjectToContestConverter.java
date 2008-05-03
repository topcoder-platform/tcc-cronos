/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.StudioFileType;

/**
 * <p>
 * This is an interface that defines the Strategy for converting entities to/from "Project Management" and "Contest
 * and Submission Entities" components. It offers methods of converting the different data classes between each. It
 * also offers a method of converting the Filter names built by Project Filter Utility, into acceptable Filter
 * names by the Contest Manager component.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> Implementations are required to be thread-safe. The logic exposed by the
 * methods in this interface are expected to require no mutable state information.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public interface ProjectToContestConverter {

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
    public Contest convertProjectToContest(Project project) throws ConversionException;

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
    public Project convertContestToProject(Contest contest) throws ConversionException;

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
        throws ConversionException;;

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
        throws ConversionException;;

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
        throws ConversionException;;

    /**
     * <p>
     * Converts a <code>ContestChannel</code> to a <code>ProjectCategory</code>.
     * </p>
     * @param contestChannel
     *        The ContestChannel to convert to a ProjectCategory.
     * @return The ProjectCategory which is the result of converting the ContestChannel
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ProjectCategory convertContestChannelToProjectCategory(ContestChannel contestChannel)
        throws ConversionException;;

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
    public StudioFileType convertProjectTypeToStudioFileType(ProjectType projectType)
        throws ConversionException;;

    /**
     * <p>
     * Converts a <code>StudioFileType</code> to a <code>ProjectType</code>.
     * </p>
     * @param fileType
     *        The StudioFileType to convert to a ProjectType
     * @return The ProjectType which is the result of converting the StudioFileType
     * @throws IllegalArgumentException
     *         If the argument is null
     * @throws ConversionException
     *         If a problem occurs while performing the conversion
     */
    public ProjectType convertStudioFileTypeToProjectType(StudioFileType fileType) throws ConversionException;

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
    public Filter convertProjectFilterToContestFilter(Filter filter) throws ConversionException;;
}
