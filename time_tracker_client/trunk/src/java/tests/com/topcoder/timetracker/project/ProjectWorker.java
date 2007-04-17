
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a bean class representing a user that is working on the project.  It contains various properties, such as
 * the id of the user who is working on the project, and the id of the project which the user is working
 * on.  The pay rate and work timeline of the worker is also available.
 * </p>
 * <p>
 * It also extends from the base TimeTrackerBean to include the  creation and modification details.
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7cc0]
 */
public class ProjectWorker extends com.topcoder.timetracker.common.TimeTrackerBean {

/**
 * <p>
 * This is the date when the user started work (or is scheduled to start work).
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
 * @poseidon-object-id [Im3563a79m110bba7264amm7ca8]
 */
    private java.util.Date startDate;

/**
 * <p>
 * This is the date when the user ended work (or is scheduled to end work).
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
 * @poseidon-object-id [Im3563a79m110bba7264amm7c97]
 */
    private java.util.Date endDate;

/**
 * <p>
 * This is the rate of pay for the worker.  It represents the amount that can be billed for each hour that
 * is spent at work.
 * </p>
 * <p>
 * Initial Value: -1 (This indicates an uninitialized value)
 * </p>
 * <p>
 * Accessed In: getPayRate
 * </p>
 * <p>
 * Modified In: setPayRate
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: -1, or values that are >= 0.
 * </p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7c86]
 */
    private double payRate;

/**
 * <p>
 * This is an identifier that is used to represent the the project that this user is working on.
 * </p>
 * <p>
 * Initial Value: 0 (This indicates an uninitialized value)
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
 * Valid Values: Ids that are >= 0.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7c9d]
 */
    private long projectId;

/**
 * <p>
 * This is a number that uniquely identifies the user that is working on the project.  It is used
 * to tie in with the Time Tracker User component.
 * </p>
 * <p>
 * Initial Value: 0 (This indicates an uninitialized value)
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
 * Valid Values: Ids that are >= 0.
 * </p>
 * 
 * @poseidon-object-id [Im12955c6cm110be84a1c6mm6dbd]
 */
    private long userId;

/**
 * <p>Default Constructor.</p>
 * 
 * @poseidon-object-id [Im3563a79m110bba7264amm7c68]
 */
    public  ProjectWorker() {        
        // your code here
    } 

/**
 * <p>
 * Gets the date when the user started work (or is scheduled to start work).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6a8b]
 * @return the date when the user started work (or is scheduled to start work).
 */
    public java.util.Date getStartDate() {        
        return startDate;
    } 

/**
 * <p>
 * Sets the date when the user started work (or is scheduled to start work).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6a70]
 * @param startDate the date when the user started work (or is scheduled to start work).
 * @throws IllegalArgumentException if startDateis null.
 */
    public void setStartDate(java.util.Date startDate) {        
        startDate = startDate;
    } 

/**
 * <p>
 * Gets the date when the user ended work (or is scheduled to end work).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6a4c]
 * @return the date when the user ended work (or is scheduled to end work).
 */
    public java.util.Date getEndDate() {        
        return endDate;
    } 

/**
 * <p>
 * Sets the date when the user ended work (or is scheduled to end work).
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6a31]
 * @param endDate the date when the user ended work (or is scheduled to end work).
 * @throws IllegalArgumentException if endDate is null.
 */
    public void setEndDate(java.util.Date endDate) {        
        endDate = endDate;
    } 

/**
 * <p>
 * Gets the rate of pay for the worker.  It represents the amount that can be billed for each hour that
 * is spent at work.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6a0d]
 * @return the rate of pay for the worker.
 */
    public double getPayRate() {        
        return payRate;
    } 

/**
 * <p>
 * Sets the rate of pay for the worker.  It represents the amount that can be billed for each hour that
 * is spent at work.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm69f2]
 * @param payRate the rate of pay for the worker.
 * @throws IllegalArgumentException if payRate is a negative number.
 */
    public void setPayRate(double payRate) {        
        payRate = payRate;
    } 

/**
 * <p>
 * Gets an identifier that is used to represent the project that this user is working on.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm69ce]
 * @return an identifier that is used to represent the the project that this user is working on.
 */
    public long getProjectId() {        
        return projectId;
    } 

/**
 * <p>
 * Sets an identifier that is used to represent the project that this user is working on.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm69b3]
 * @param projectId an identifier that is used to represent the the project that this user is working on.
 * @throws IllegalArgumentException if projectId is <= 0.
 */
    public void setProjectId(long projectId) {        
        projectId = projectId;
    } 

/**
 * <p>
 * Gets a number that uniquely identifies the user that is working on the project.  It is used
 * to tie in with the Time Tracker User component.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm698f]
 * @return a number that uniquely identifies the user that is working on the project.
 */
    public long getUserId() {        
        return userId;
    } 

/**
 * <p>
 * Sets a number that uniquely identifies the user that is working on the project.  It is used
 * to tie in with the Time Tracker User component.
 * </p>
 * 
 * 
 * @poseidon-object-id [I53313f8bm110c107ff31mm6974]
 * @param userId a number that uniquely identifies the user that is working on the project.
 * @throws IllegalArgumentException if userId is <= 0.
 */
    public void setUserId(long userId) {        
        userId = userId;
    } 
 }
