/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is a container for path list information. It holds references to another path list collections and stores the
 * own list of file paths. This class is used by DotNetDependenciesEntryExtractor to store the parsed content of
 * &lt;references&gt;, &lt;fileset&gt;, &lt;assemblyfileset&gt; elements and by JavaDependenciesEntryExtractor to store
 * the parsed content of &lt;path&gt; and &lt;classpath&gt; elements.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because it holds mutable collections.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
class PathList {
    /**
     * <p>
     * The file paths included into the current path list. Collection instance is initialized in the constructor and
     * never changed after that. Cannot be null, cannot contain null or empty. Can be modified in addPath(). Can be read
     * in getFilePaths().
     * </p>
     */
    private final List<String> filePaths = new ArrayList<String>();

    /**
     * <p>
     * Creates an instance of PathList.
     * </p>
     */
    public PathList() {
        // Do nothing.
    }

    /**
     * <p>
     * Adds the file path to this path list.
     * </p>
     *
     * @param path the file path to be included (cannot be null or empty)
     */
    public void addPath(String path) {
        // no checking on this since it is used in package level
        // http://forums.topcoder.com/?module=Thread&threadID=615068&start=0
        filePaths.add(path);
    }

    /**
     * <p>
     * Retrieves the paths for this path list.
     * </p>
     *
     * @return the paths for this path list (cannot be null, cannot contain null or empty)
     */
    public List<String> getFilePaths() {
        return new ArrayList<String>(this.filePaths);
    }
}
