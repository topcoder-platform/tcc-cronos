
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a bean class that represents a Project Manager.  A ProjectManager is a user that is responsible for
 * managing the project.  Various properties are available, such as the ids of the project and
 * user involved.  The timeline when the user is expected to manage the project is also provided.
 * </p>
 * <p>
 * It also extends from the base TimeTrackerBean to include the creation and modification details.
 * </p>
 * <p>
 * Note that a project may have more than one ProjectManager at any given time.
 * </p>  
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7da8]
 */
public class ProjectManager extends com.topcoder.timetracker.common.TimeTrackerBean {

/**
 * <p>
 * This is the date when the user started managing the project (or is scheduled to manage the project).
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getStartDate
 * </p>
 * <p>
 * Modified In: setStartDate
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null values, or a Date object
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7d90]
 */
    private java.util.Date startDate;

/**
 * <p>
 * This is the date when the user stopped managing the project (or is scheduled to stop managing the project).
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getEndDate
 * </p>
 * <p>
 * Modified In: setEndDate
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Null values, or a Date object
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7d7f]
 */
    private java.util.Date endDate;

/**
 * <p>
 * This is an identifier that is used to represent the the project that this user is managing.
 * </p>
 * <p>
 * Initial Value: -1 (This indicates an uninitialized value)
 * </p>
 * <p>
 * Accessed In: getProjectId
 * </p>
 * <p>
 * Modified In: setProjectId
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: -1, or ids that are > 0.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7c8c]
 */
    private long projectId;

/**
 * <p>
 * This is a number that uniquely identifies the user that is managing the project.  It is used
 * to tie in with the Time Tracker User component.
 * </p>
 * <p>
 * Initial Value: -1 (This indicates an uninitialized value)
 * </p>
 * <p>
 * Accessed In: getUserId
 * </p>
 * <p>
 * Modified In: setUserId
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: -1, or ids that are > 0.
 * </p>
 * 
 * @poseidon-object-id [Im12955c6cm110be84a1c6mm6da9]
 */
    private long userId;

/**
 * <p>Default Constructor.</p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7d6e]
 */
    public  ProjectManager() {        
        // your code here
    } 

/**
 * <p>
 * Gets the date when the user started managing the project (or is scheduled to manage the project).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6950]
 * @return the date when the user started managing the project (or is scheduled to manage the project).
 */
    public java.util.Date getStartDate() {        
        return startDate;
    } 

/**
 * <p>
 * Sets the date when the user started managing the project (or is scheduled to manage the project).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6935]
 * @param startDate the date when the user started managing the project (or is scheduled to manage the project).
 * @throws IllegalArgumentException if startDate is null.
 */
    public void setStartDate(java.util.Date startDate) {        
        this.startDate = startDate;
    } 

/**
 * <p>
 * Gets the date when the user stopped managing the project (or is scheduled to stop managing the project).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6911]
 * @return the date when the user stopped managing the project (or is scheduled to stop managing the project).
 */
    public java.util.Date getEndDate() {        
        return endDate;
    } 

/**
 * <p>
 * Sets the date when the user stopped managing the project (or is scheduled to stop managing the project).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm68f6]
 * @param endDate the date when the user stopped managing the project (or is scheduled to stop managing the project).
 * @throws IllegalArgumentException if endDate is null.
 */
    public void setEndDate(java.util.Date endDate) {        
        this.endDate = endDate;
    } 

/**
 * <p>
 * Gets an identifier that is used to represent the project that this user is managing.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm68d2]
 * @return an identifier that is used to represent the project that this user is managing.
 */
    public long getProjectId() {        
        return projectId;
    } 

/**
 * <p>
 * Sets an identifier that is used to represent the project that this user is managing.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm68b7]
 * @param projectId an identifier that is used to represent the project that this user is managing.
 * @throws IllegalArgumentException if projectId is <= 0.
 */
    public void setProjectId(long projectId) {        
        this.projectId = projectId;
    } 

/**
 * <p>
 * Gets a number that uniquely identifies the user that is managing the project.  It is used
 * to tie in with the Time Tracker User component.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6893]
 * @return a number that uniquely identifies the user that is managing the project.
 */
    public long getUserId() {        
        return userId;
    } 

/**
 * <p>
 * Sets a number that uniquely identifies the user that is managing the project.  It is used
 * to tie in with the Time Tracker User component.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6878]
 * @param userId a number that uniquely identifies the user that is managing the project.
 * @throws IllegalArgumentException if clientId is <= 0.
 */
    public void setUserId(long userId) {        
        this.userId = userId;
    } 
 }
