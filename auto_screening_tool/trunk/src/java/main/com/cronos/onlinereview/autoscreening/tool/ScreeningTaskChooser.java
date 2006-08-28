/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This is an interface that describes a strategy for determining the
 * ScreeningTask to screen. Implementations may use the different attributes of
 * the ScreeningTask (such as to prioritize certain Projects or
 * ProjectCategories).
 * </p>
 * <p>
 * The default version will use the earliest create date of the screening task.
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread-safe, but it is
 * recommended to create them in such a way that multiple concurrent invocations
 * of chooseTask can be performed safely.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public interface ScreeningTaskChooser {
    /**
     * <p>
     * Choose an appropriate task to screen from the list of available tasks
     * that are provided as choices.
     * </p>
     * <p>
     * The actual logic is implementation-dependent. Implementations are
     * required to return a non-null Task from the provided array.
     * </p>
     * @return The Task which was chosen from the list.
     * @param tasks
     *            A list of tasks to choose from.
     * @throws IllegalArgumentException
     *             if the array is null, contains null elements, or is empty.
     */
    public ScreeningTask chooseScreeningTask(ScreeningTask[] tasks);
}
