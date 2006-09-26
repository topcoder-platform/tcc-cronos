/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Map;

/**
 * <p>
 * This is the interface that defines the screening logic to be used in a
 * particular situation. The results of screening are encapsulated within a
 * RuleResult.
 * </p>
 * <p>
 * Thread-Safety: Implementations are not required to be thread-safe. This
 * component is expected to use the ScreeningRules in a thread-safe manner.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public interface ScreeningRule {
    /**
     * <p>
     * Performs screening on the specified screening task. Optional data may be
     * provided in the context Map. The implementation may also place additional
     * data in the context Map for other rules to use.
     * </p>
     * <p>
     * Note that any exceptions other than IAE are expected to be caught by the
     * implementation and either handled properly, or returned as part of the
     * RuleResult.
     * </p>
     * @return The results of screening.
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public RuleResult[] screen(ScreeningTask screeningTask, Map context);

    /**
     * <p>
     * This method is provided for those rules that may use up additional
     * resources in the process of screening. Some rules may modify the file
     * system and add temporary files. This method provides those rules the
     * opportunity to release the resources after the screening is finished. The
     * component guarantees that the cleanup method will be invoked before the
     * next screen method is called.
     * </p>
     * <p>
     * Note that any exceptions other than IAE are expected to be caught by the
     * implementation and handled properly.
     * </p>
     * @param screeningTask
     *            The screeningTask for which cleanup must be performed.
     * @param context
     *            The screening context which may contain some resources to be
     *            released.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public void cleanup(ScreeningTask screeningTask, Map context);
}
