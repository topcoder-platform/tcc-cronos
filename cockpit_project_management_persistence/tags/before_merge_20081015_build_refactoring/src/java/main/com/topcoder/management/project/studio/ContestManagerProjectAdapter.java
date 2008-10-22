/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This is an implementation of the <code>ProjectManager</code> interface from the "TC Project Management" 1.0
 * component. This implementation will transform the Project entities from TC Project Management to/from the
 * Contest entities found in TC Contest and Submission Entities 1.0 component. Once transformed, this
 * implementation will then delegate the actual operation to an instance of a <code>ContestManager</code> (from
 * "TC Studio Contest Manager" 1.0 component). This allows the Studio Contests to be managed as TC Projects, and
 * allows the Studio application to be used in confunction with the TC Auto Pilot component.
 * </p>
 * <p>
 * This is the sample usage code:
 *
 * <pre>
 * long projectId = 1;
 * long categoryId = 2;
 * long statusId = 3;
 *
 * // Initialize the Adapter
 * ProjectManager manager = new ContestManagerProjectAdapter(&quot;java:comp/env/contestManagerRemote&quot;);
 *
 * // Also create a ContestManager for reference
 * InitialContext ic = new InitialContext();
 * ContestManager contestMngr = (ContestManager) ic.lookup(&quot;java:comp/env/contestManagerRemote&quot;);
 *
 * // Initialize start/end dates.
 * Calendar startDate = Calendar.getInstance();
 * Calendar endDate = Calendar.getInstance();
 *
 * // Project ends in one week.
 * endDate.add(Calendar.DATE, 7);
 *
 * // Create a Project
 * ProjectType projectType = new ProjectType(projectId, &quot;Flash&quot;, &quot;Macromedia Flash Project&quot;);
 *
 * ProjectCategory category = new ProjectCategory(categoryId, &quot;Open&quot;, &quot;Open To All&quot;, projectType);
 *
 * ProjectStatus regStatus = new ProjectStatus(statusId, &quot;Registration&quot;, &quot;Registration Phase&quot;);
 *
 * Project targetProject = new Project(category, regStatus);
 *
 * // Set some custom properties in the Project
 * targetProject.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE, startDate.getTime());
 *
 * targetProject.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE, endDate.getTime());
 *
 * // Create the Project in the manager
 * manager.createProject(targetProject, &quot;Ignored Parameter&quot;);
 *
 * // Result should be visible in contest manager
 * Contest contest = contestMngr.getContest(projectId);
 *
 * // Should be project id
 * System.out.println(contest.getContestId());
 *
 * // Should be start date
 * System.out.println(contest.getStartDate());
 *
 * // Should be end date
 * System.out.println(contest.getEndDate());
 *
 * // Should be equivalent to Project Status
 * ContestStatus contestStatus = contest.getStatus();
 *
 * System.out.println(contestStatus.getName());
 * System.out.println(contestStatus.getDescription());
 * System.out.println(contestStatus.getContestStatusId());
 *
 * // Update the Status to Screening
 * ProjectStatus screenStatus = new ProjectStatus(4, &quot;Screening&quot;, &quot;Screening Phase&quot;);
 *
 * targetProject.setProjectStatus(screenStatus);
 *
 * // Status should be updated in contest manager too
 * contest = contestMngr.getContest(projectId);
 *
 * contestStatus = contest.getStatus();
 *
 * System.out.println(contestStatus.getName());
 * System.out.println(contestStatus.getDescription());
 * System.out.println(contestStatus.getContestStatusId());
 *
 * // Update the end date in the contest manager
 * contest.setEndDate(new Date());
 *
 * contestMngr.updateContest(contest);
 *
 * // Result should also be visible in Project Manager
 * targetProject = manager.getProject(projectId);
 *
 * // Should be the new end date.
 * targetProject.getProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE);
 *
 * // Perform a search
 * Filter filter = ProjectFilterUtility.buildCategoryIdEqualFilter(categoryId);
 *
 * // Should return our existing project
 * Project[] results = manager.searchProjects(filter);
 *
 * // Get all Project Categories
 * ProjectCategory[] categories = manager.getAllProjectCategories();
 *
 * // Get all Project Types
 * ProjectType[] projectCategories = manager.getAllProjectTypes();
 *
 * // Get all Project Statuses
 * ProjectStatus[] projectStatus = manager.getAllProjectStatuses();
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> For the CRUD operations (create/update/retrieve operations), the thread-safety
 * of this component relies on the thread-safety of underlying <code>ContestManager</code> implementation. It
 * introduces no additional thread-safety issues, since the conversion process is stateless. For the
 * "setContestManager" method, it is expected that this method will be called to configure the component, and not
 * to be called when the adapter has been deployed.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerProjectAdapter implements ProjectManager {

    /**
     * <p>
     * This is an instance of the <code>ContestManager</code> to which the ProjectPersistence will delegate after
     * it has converted the <code>Project</code> to a <code>Contest</code> (or vice-versa, in the case of
     * "retrieve" methods).
     * </p>
     * <p>
     * Note that if this class member is null when a Create/Retrieve/Update operation is called, then
     * <code>PersistenceException</code> will be thrown with appropriate exception message.
     * </p>
     * <p>
     * Initialized In: Constructor, although it may be initially null if default constructor is used.
     * </p>
     * <p>
     * Accessed In: getContestManager
     * </p>
     * <p>
     * Modified In: setContestManager
     * </p>
     * <p>
     * Utilized In: create/update/get methods.
     * </p>
     * <p>
     * Valid Values: Possibly null <code>ContestManager</code> implementations
     * </p>
     */
    private ContestManager manager;

    /**
     * <p>
     * This is an implementation of the <code>ProjectToContestConverter</code> that is used to convert entities
     * to/from "Project Management", and "Contest and Submission Entities" components. When passing attributes to
     * the <code>ContestManager</code>, this implementation will convert the "Project Management" entity into a
     * "Contest and Submission" entity, and when retrieving return values from the ContestManager, this
     * implementation will convert the "Contest and Submission" entity into a "Project Management" entity using the
     * ProjectToContestConverter.
     * </p>
     * <p>
     * Initialized In: Constructor (Default value of ProjectToContestConverterImpl)
     * </p>
     * <p>
     * Accessed In: getConverter
     * </p>
     * <p>
     * Modified In: setConverter
     * </p>
     * <p>
     * Utilized In: create/update/get methods.
     * </p>
     * <p>
     * Valid Values: Never null <code>ProjectToContestConverter</code> instance
     * </p>
     */
    private ProjectToContestConverter converter = new ProjectToContestConverterImpl();

    private Random randomizer = new Random(System.currentTimeMillis());

    /**
     * <p>
     * Constructs an instance of this class.
     * </p>
     * <p>
     * It is expected that <code>setContestManager</code> should be called before calling "Logic" methods of this
     * class.
     * </p>
     */
    public ContestManagerProjectAdapter() {
    }

    /**
     * <p>
     * Constructs an instance of this class with specified contest manager.
     * </p>
     * @param manager
     *        The manager to delegate to
     * @throws IllegalArgumentException
     *         If the manager is null
     */
    public ContestManagerProjectAdapter(ContestManager manager) {
        ExceptionUtils.checkNull(manager, null, null, "[manager] can't be null.");
        this.manager = manager;
    }

    /**
     * <p>
     * Constructs an instance of this class with the configuration in JNDI.
     * </p>
     * @param jndiName
     *        The JNDI name where the ContestManager to use can be found
     * @throws IllegalArgumentException
     *         If <code>jndiName</code> is null or an empty String
     * @throws ConfigurationException
     *         If a problem occurs while loading the ContestManager
     */
    public ContestManagerProjectAdapter(String jndiName) throws ConfigurationException {
        ExceptionUtils.checkNullOrEmpty(jndiName, null, null, "[jndiName] should not be null or empty string.");

        try {
            // create an initial context
            InitialContext context = new InitialContext();

            Object obj = context.lookup(jndiName);

            if (!(obj instanceof ContestManager)) {
                throw new ConfigurationException("The object associated with JNDI name [" + jndiName
                    + "] is not an instance of ContestManager.");
            }

            this.manager = (ContestManager) obj;
        } catch (NamingException e) {
            throw new ConfigurationException("Error occurred when looking ContestManager from JNDI.", e);
        }
    }

    /**
     * <p>
     * Constructs an instance of this class with specified ContestManager and converter.
     * </p>
     * @param converter
     *        The converter implementation to use when converting entities to/from "Project Management" and
     *        "Contest and Submission Entities"
     * @param manager
     *        The manager to delegate to
     * @throws IllegalArgumentException
     *         If either parameter is null
     */
    public ContestManagerProjectAdapter(ContestManager manager, ProjectToContestConverter converter) {
        ExceptionUtils.checkNull(manager, null, null, "[manager] can't be null.");
        ExceptionUtils.checkNull(converter, null, null, "[converter] can't be null.");

        this.manager = manager;
        this.converter = converter;
    }

    /**
     * <p>
     * Constructs an instance of this class with specified JNDI name and converter.
     * </p>
     * <p>
     * @param converter
     *        The converter implementation to use when converting entities to/from "Project Management" and
     *        "Contest and Submission Entities"
     * @param jndiName
     *        The JNDI name where the ContestManager to use can be found
     * @throws IllegalArgumentException
     *         If <code>jndiName</code> is null or an empty String, or <code>converter</code> is null
     * @throws ConfigurationException
     *         If a problem occurs while loading the ContestManager
     */
    public ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter)
        throws ConfigurationException {
        this(jndiName);
        ExceptionUtils.checkNull(converter, null, null, "[converter] can't be null.");

        this.converter = converter;
    }

    /**
     * <p>
     * Creates the project in persistence using the given project instance. It will transform the project into a
     * contest, and delegate to the <code>ContestManager#createContest</code> method.
     * </p>
     * <p>
     * The operator String is ignored for this implementation.
     * </p>
     * @param project
     *        The project to create
     * @param operator
     *        The creation user of this project (ignored for this implementation)
     * @throws IllegalArgumentException
     *         If <code>project</code> is null
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public void createProject(Project project, String operator) throws PersistenceException {
        ExceptionUtils.checkNull(project, null, null, "[project] can't be null.");
        checkManager();

        try {
            // converts the Project into a Contest
            Contest contest = converter.convertProjectToContest(project);

            // creates the Contest
            manager.createContest(contest);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting Project to Contest.", e);
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when creating the Contest by using ContestManager.");
        }
    }

    /**
     * <p>
     * Updates the given project instance into the persistent store.
     * </p>
     * <p>
     * It will transform the project into a contest, and delegate to the <code>ContestManager#updateContest</code>
     * method.
     * </p>
     * <p>
     * Reason and operator are ignored in this implementation.
     * </p>
     * <p>
     * If a request is made to update a project that doesn't exist in the data store,
     * <code>PersistenceException</code> is thrown.
     * </p>
     * @param reason
     *        The reason for updating (Ignored in this implementation)
     * @param project
     *        The project to update
     * @param operator
     *        The modification user of the project(Ignored in this implementation)
     * @throws IllegalArgumentException
     *         If <code>project</code> is null
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        ExceptionUtils.checkNull(project, null, null, "[project] can't be null.");
        checkManager();

        try {
            // converts the Project into a Contest
            Contest contest = converter.convertProjectToContest(project);

            // updates the Contest
            manager.updateContest(contest, this.randomizer.nextInt(), operator, false);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting Project to Contest.", e);
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when updating the Contest by using ContestManager.");
        }
    }

    /**
     * <p>
     * Retrieves the project instance from the persistence given its id. The project instance is retrieved with its
     * properties loaded. This will retrieve the <code>Contest</code> from the <code>ContestManager</code>
     * using <code>ContestManager#getContest</code>. The Contests are then converted to <code>Project</code>
     * entities and returned.
     * </p>
     * <p>
     * A null should be returned if no project is found.
     * </p>
     * @param id
     *        The id of the project to retrieve
     * @return The contest (converted to a Project) with specified id
     * @throws IllegalArgumentException
     *         If <code>id</code> is less than or equal to 0
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public Project getProject(long id) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("The given id should be positive.");
        }
        checkManager();

        try {
            // gets the Contest
            Contest contest = manager.getContest(id);

            // converts the Contest to Project and return
            return converter.convertContestToProject(contest);
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when retrieving the Contest by ContestManager.", e);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting Contest to Project.", e);
        }
    }

    /**
     * <p>
     * Retrieves an array of project instances from the persistence given its id. The project instance is retrieved
     * with its properties loaded. This will retrieve the <code>Contest</code> from the
     * <code>ContestManager</code> using <code>ContestManager#getContest</code>. The Contests are then
     * converted to <code>Project</code> entities and returned.
     * </p>
     * <p>
     * If no project is found, an empty array is returned.
     * </p>
     * @param ids
     *        A list of project ids to retrieve
     * @return An array of retrieved project ids
     * @throws IllegalArgumentException
     *         If ids is null or contains an id that is less than or equal to 0
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        ExceptionUtils.checkNull(ids, null, null, "[ids] can't be null.");
        for (long id : ids) {
            if (id <= 0) {
                throw new IllegalArgumentException("[ids] can't contain id that is less than or equal to 0.");
            }
        }
        checkManager();

        List<Project> projects = new ArrayList<Project>();
        for (long id : ids) {
            projects.add(getProject(id));
        }

        return projects.toArray(new Project[projects.size()]);
    }

    /**
     * <p>
     * Searches the projects based on the <code>Filter</code> provided. This will convert the Filters to a set of
     * Filters more suitable for the <code>ContestManager</code>, then provide the converted filters to the
     * ContestManager's <code>searchContest</code> method. The resulting Contests are then converted to Projects
     * before being returned. If no results are found, an empty array is returned.
     * </p>
     * <p>
     * This will support the same Filters as required by <code>ProjectManager</code> interface:
     * <ul>
     * <li>Project type id</li>
     * <li>Project type name</li>
     * <li>Project category id</li>
     * <li>Project category name</li>
     * <li>Project status id</li>
     * <li>Project status name</li>
     * <li>Project property name</li>
     * <li>Project property value</li>
     * <li>Project resource property name</li>
     * <li>Project resource property value</li>
     * </ul>
     * </p>
     * @param filter
     *        The search filter to use
     * @return An array of Projects, it can't be empty array if not project is found
     * @throws IllegalArgumentException
     *         If <code>filter</code> is null
     * @throws PersistenceException
     *         If a problem occurs while performing the search
     */
    public Project[] searchProjects(Filter filter) throws PersistenceException {
        ExceptionUtils.checkNull(filter, null, null, "[filter] can't be null.");
        checkManager();

        try {
            // converts the filter
            Filter convertedFilter = converter.convertProjectFilterToContestFilter(filter);

            // searches the contests
            List<Contest> contests = manager.searchContests(convertedFilter);

            // converts the Contests to Projects and return it
            return convertContestsToProjects(contests);
        } catch (ConversionException e) {
            throw new PersistenceException(
                "Error occurred when converting Contest to Project or converting project filter to contest filter.",
                e);
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when searching the contests by ContestManager.", e);
        }
    }

    /**
     * <p>
     * Converts a list of Contests to an array of Projects.
     * </p>
     * @param contests
     *        The list of Contests to convert
     * @return An array of Projects
     * @throws ConversionException
     *         If any error occurred during conversion
     */
    private Project[] convertContestsToProjects(List<Contest> contests) throws ConversionException {
        Project[] projects = new Project[contests.size()];

        for (int i = 0; i < projects.length; i++) {
//            projects[i] = converter.convertContestToProject(contests.get(i));
            Contest contest = contests.get(i);
            ProjectType t = new ProjectType(2, "TopCoder Direct");
            ProjectCategory c = new ProjectCategory(1, "x", t);
            ProjectStatus s = new ProjectStatus(2, "x");
            projects[i] = new Project(contest.getContestId(), c, s);
        }

        return projects;
    }

    /**
     * <p>
     * This will search for Projects associated with the provided user. For this implementation, it will search for
     * Contests that have the specified createdUser.
     * </p>
     * @param user
     *        The user whose projects should be retrieved
     * @return The projects for specified user
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public Project[] getUserProjects(long user) throws PersistenceException {
        checkManager();
        try {
            // creates an contest filter
            Filter filter = new EqualToFilter("createdUser", user);

            // searches the contests
            List<Contest> contests = manager.searchContests(filter);

            // converts the Contests to Projects and return it
            return convertContestsToProjects(contests);
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when getting the projects by user.", e);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting Contest to Project.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the ProjectTypes defined in this Persistence. For this implementation, the
     * <code>StudioFileType</code> corresponds to the ProjectType, and the implementation will delegate to
     * <code>ContestManager#getAllStudioFileTypes</code> and convert the returned <code>StudioFileType</code>
     * to <code>ProjectType</code>.
     * </p>
     * <p>
     * Empty array is returned if no such entities currently exist in persistence.
     * </p>
     * @return An array of all StudioFileTypes (converted to ProjectTypes)
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        checkManager();
        try {
            // gets all studio file types
            List<StudioFileType> types = manager.getAllStudioFileTypes();

            // converts the StudioFileTypes to ProjectTypes
            ProjectType[] projectTypes = new ProjectType[types.size()];
            for (int i = 0; i < projectTypes.length; i++) {
                projectTypes[i] = converter.convertStudioFileTypeToProjectType(types.get(i));
            }

            return projectTypes;
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when retrieving all studio file types.", e);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting ContestFiltType to ProjectType.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the ProjectCategories that exist in persistence.
     * </p>
     * <p>
     * Empty array is returned if no such entities currently exist in persistence.
     * </p>
     * @return An array of all StudioFileType converted to an array of ProjectCategory
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        checkManager();
        try {
            List<StudioFileType> allStudioFileTypes = manager.getAllStudioFileTypes();

            // converts ContestChannel to ProjectCategory
            ProjectCategory[] categories = new ProjectCategory[allStudioFileTypes.size()];
            for (int i = 0; i < allStudioFileTypes.size(); i++) {
                categories[i] = converter.convertFileTypeToProjectCategory(allStudioFileTypes.get(i));
            }

            return categories;
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when retrieving all studio contest channels.", e);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting ContestChannel to ProjectCategory.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the ProjectStatuses in persistence. For this implementation, the <code>ContestStatus</code>
     * corresponds to the <code>ProjectStatus</code>, so this will call
     * <code>ContestManager#getAllContestStatuses</code>, and convert the returned ContestStatus to a
     * ProjectStatus.
     * </p>
     * <p>
     * Empty array is returned if no such entities currently exist in persistence.
     * </p>
     * @return An array of all ContestStatus converted to an array of ProjectStatus
     * @throws PersistenceException
     *         If a problem occurs while performing the operation
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        checkManager();
        try {
            // gets all contest status
            List<ContestStatus> statuses = manager.getAllContestStatuses();

            // converts the contest status to project status
            ProjectStatus[] projectStatuses = new ProjectStatus[statuses.size()];
            for (int i = 0; i < projectStatuses.length; i++) {
                projectStatuses[i] = converter.convertContestStatusToProjectStatus(statuses.get(i));
            }

            return projectStatuses;
        } catch (ContestManagementException e) {
            throw new PersistenceException("Error occurred when retrieving all studio contest status.", e);
        } catch (ConversionException e) {
            throw new PersistenceException("Error occurred when converting ContestStatus to ProjectStatus.", e);
        }
    }

    /**
     * <p>
     * Checks whether the manager is set.
     * </p>
     * @throws PersistenceException
     *         If the manager is null
     */
    private void checkManager() throws PersistenceException {
        if (manager == null) {
            throw new PersistenceException("The 'manager' should have been set beforing calling 'work' methods.");
        }
    }

    /**
     * <p>
     * This retrieves an array of all ProjectPropertyTypes in persistence. This implementation is not required to
     * support this method, so it simply returns an empty array.
     * </p>
     * @return An empty array of ProjectPropertyTypes
     * @throws PersistenceException
     *         This exception will never be thrown in this method
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return new ProjectPropertyType[0];
    }

    /**
     * <p>
     * Retrieves the instance of the <code>ContestManager</code> which the ProjectPersistence will delegate to
     * after it has converted the Project to a contest (or vice-versa, in the case of "retrieve" methods).
     * </p>
     * @return The ContestManager being used
     */
    public ContestManager getContestManager() {
        return manager;
    }

    /**
     * <p>
     * Sets the instance of the <code>ContestManager</code> which the ProjectPersistence will delegate to after
     * it has converted the Project to a contest (or vice-versa, in the case of "retrieve" methods).
     * </p>
     * @param manager
     *        The ContestManager being used
     * @throws IllegalArgumentException
     *         If the argument is null
     */
    public void setContestManager(ContestManager manager) {
        ExceptionUtils.checkNull(manager, null, null, "[manager] can't be null.");
        this.manager = manager;
    }

    /**
     * <p>
     * Retrieves the <code>ProjectToContestConverter</code> that is used to convert entities to/from "Project
     * Management", and "Contest and Submission Entities" components.
     * </p>
     * @return the ProjectToContestConverter that is used to convert entities to/from "Project Management", and
     *         "Contest and Submission Entities" components
     */
    public ProjectToContestConverter getConverter() {
        return converter;
    }

    /**
     * <p>
     * Sets the <code>ProjectToContestConverter</code> that is used to convert entities to/from "Project
     * Management", and "Contest and Submission Entities" components.
     * </p>
     * @param converter
     *        The ProjectToContestConverter that is used to convert entities to/from "Project Management", and
     *        "Contest and Submission Entities" components
     * @throws IllegalArgumentException
     *         If the argument is null
     */
    public void setConverter(ProjectToContestConverter converter) {
        ExceptionUtils.checkNull(converter, null, null, "[converter] can't be null.");

        this.converter = converter;
    }
}
