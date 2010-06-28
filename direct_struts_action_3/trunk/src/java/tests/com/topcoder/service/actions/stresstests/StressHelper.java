/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.actions.stresstests;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

import java.io.File;

/**
 * <p> This is the helper class used by stress tests. </p>
 *
 * @author yuanyeyuanye
 * @version 1.0
 */
public class StressHelper {
    /**
     * Clear the logs.
     */
    static void clearLog() {
        File[] files = new File("test_files/stress").listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".log")) {
                file.delete();
            }
        }
    }

    /**
     * <p> Construct the action used for test. </p>
     *
     * @param type Type for the action.
     * @param <T>  Type for the action.
     *
     * @return Instance used for test.
     *
     * @throws Exception If any exception occurs.
     */
    static <T extends BaseDirectStrutsAction> T createAction(Class<T> type) throws Exception {
        T ret = type.newInstance();
        ret.prepare();
        return ret;
    }
}
