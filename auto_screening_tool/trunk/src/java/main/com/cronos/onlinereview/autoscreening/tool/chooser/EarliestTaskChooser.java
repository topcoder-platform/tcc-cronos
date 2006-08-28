/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.autoscreening.tool.chooser;

import java.util.Arrays;
import java.util.Date;

import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTaskChooser;

/**
 * <p>
 * This is the default implementation of the TaskChooser interface. It chooses a
 * task according to the task with the earliest create date.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe, because it doesn't have any state.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class EarliestTaskChooser implements ScreeningTaskChooser {

    /**
     * Initializes a new instance of the EarliestTaskChooser class.
     */
    public EarliestTaskChooser() {
        // empty
    }

    /**
     * <p>
     * Choose an appropriate task to screen from the list of available tasks
     * that are provided as choices.
     * </p>
     * <P>
     * This implementation chooses a task according to the task with the
     * earliest create date.
     * </p>
     * @return the task with the earliest create date.
     * @param tasks
     *            A list of tasks to choose from.
     * @throws IllegalArgumentException
     *             if the array is null, contains null elements, or is empty.
     * @throws ScreeningException
     *             if some screening task in the array does not have create date
     */
    public ScreeningTask chooseScreeningTask(ScreeningTask[] tasks) {
        // validate arguments
        if (tasks == null) {
            throw new IllegalArgumentException("tasks should not be null.");
        }
        if (Arrays.asList(tasks).contains(null)) {
            throw new IllegalArgumentException("tasks should not contain null elements.");
        }
        if (tasks.length == 0) {
            throw new IllegalArgumentException("tasks should not be empty.");
        }

        ScreeningTask selectedTask = null;

        // enumerate each task in the list
        for (int i = 0; i < tasks.length; ++i) {
            Date createDate = tasks[i].getCreationDate();

            // createDate of tasks[i] should never be null at this time
            if (createDate == null) {
                throw new ScreeningException("tasks[" + i
                    + "].getCreationDate() should not return null");
            }

            // check if the selectedTask should be updated by tasks[i]
            if (selectedTask == null || createDate.before(selectedTask.getCreationDate())) {
                selectedTask = tasks[i];
            }
        }

        return selectedTask;
    }
}
