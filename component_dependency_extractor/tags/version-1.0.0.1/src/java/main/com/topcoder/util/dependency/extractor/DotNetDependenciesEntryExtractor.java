/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.topcoder.util.dependency.BaseDependenciesEntryExtractor;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.Utils;
import com.topcoder.util.dependency.extractor.fileprovider.LocalBuildFileProvider;
import com.topcoder.util.dependency.extractor.fileprovider.ZipBuildFileProvider;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This subclass of BaseDependenciesEntryExtractor can extract dependencies from build or distribution files of .NET
 * components from TopCoder software catalog. If accepts default.build and *.zip files in extract() methods.
 * </p>
 * <p>
 * <b>Sample Usage</b>
 *
 * <pre>
 * // Extract external component dependencies for .NET component
 * extractor = new DotNetDependenciesEntryExtractor(null);
 * Set&lt;DependencyType&gt; types = new HashSet&lt;DependencyType&gt;();
 * types.add(DependencyType.EXTERNAL);
 * extractor.setExtractedTypes(types);
 * // Extract from default.build
 * entry = extractor.extract(DEMO_DIR + File.separator + &quot;object_factory/default.build&quot;);
 * // Extract from distribution file
 * entry = extractor.extract(DEMO_DIR + File.separator + &quot;object_factory-1.2.1.zip&quot;);
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe. The user must synchronize
 * calls to all its methods to use it in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class DotNetDependenciesEntryExtractor extends BaseDependenciesEntryExtractor {
    /**
     * <p>
     * Supported build file name.
     * </p>
     */
    private static final String BUILD_FILE_NAME = "default.build";

    /**
     * <p>
     * Supported file extension from which build file will be retrieved.
     * </p>
     */
    private static final String ZIP_FILE_EXTENSION = ".zip";

    /**
     * <p>
     * Constant for file extension .dll which is used to indicate .NET lib.
     * </p>
     */
    private static final String DLL_FILE_EXTENSION = ".dll";

    /**
     * <p>
     * Temporary component name.
     * </p>
     */
    private static final String TEMPORARY_COMPONENT_NAME = "?";

    /**
     * <p>
     * Constant for attribute basedir.
     * </p>
     */
    private static final String ATTRIBUTE_BASE_DIR = "basedir";

    /**
     * <p>
     * Constant for attribute name.
     * </p>
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * <p>
     * Constant for attribute value.
     * </p>
     */
    private static final String ATTRIBUTE_VALUE = "value";

    /**
     * <p>
     * Constant for attribute buildfile.
     * </p>
     */
    private static final String ATTRIBUTE_BUILD_FILE = "buildfile";

    /**
     * <p>
     * Constant for attribute id.
     * </p>
     */
    private static final String ATTRIBUTE_ID = "id";

    /**
     * <p>
     * Constant for attribute refid.
     * </p>
     */
    private static final String ATTRIBUTE_REF_ID = "refid";

    /**
     * <p>
     * Constant for attribute file.
     * </p>
     */
    private static final String ATTRIBUTE_FILE = "file";

    /**
     * <p>
     * Dependency file pattern1. It is "file_path\component_name\component_version\dll_name.dll", where
     * component_version is not empty and starts with digit and contains only digits, dots and optional letter at the
     * end.component_name will not include "\" character.
     * </p>
     */
    private static final Pattern DLL_FILE_PATTERN1 = Pattern
        .compile(".*\\\\([^\\\\]+)\\\\(\\d[\\d.]*[a-zA-z]?)\\\\[^\\\\]+\\.dll");

    /**
     * <p>
     * Dependency file pattern2. It is "file_path\component_name-component_version.dll", where component_version is not
     * empty and starts with digit and contains only digits, dots and optional letter at the end. component_name will
     * not include "\" character.
     * </p>
     */
    private static final Pattern DLL_FILE_PATTERN2 = Pattern.compile(".*\\\\([^\\\\]+)-(\\d[\\d.]*[a-zA-z]?)\\.dll");

    /**
     * <p>
     * Dependency file pattern1. It is "file_path\component_name\component_version\bin\dll_name.dll", where
     * component_version is not empty and starts with digit and contains only digits, dots and optional letter at the
     * end.component_name will not include "\" character.
     * </p>
     */
    private static final Pattern DLL_FILE_PATTERN3 = Pattern
        .compile(".*\\\\([^\\\\]+)\\\\(\\d[\\d.]*[a-zA-z]?)\\\\bin\\\\[^\\\\]+\\.dll");

    /**
     * <p>
     * The list of properties defined in the build file that are already parsed. Collection instance is initialized in
     * the constructor and never changed after that. Cannot be null, cannot contain null. Keys cannot be empty. Content
     * is modified in processPropertyElement(). Is used in most methods of this class.
     * </p>
     */
    private final Map<String, String> properties = new HashMap<String, String>();

    /**
     * <p>
     * The list of path-list structures currently extracted from build files. Collection instance is initialized in the
     * constructor and never changed after that. Cannot be null, cannot contain null. Keys cannot be empty. Content is
     * modified in processFileSetElement(). Is also used in processPathListDependency().
     * </p>
     */
    private final Map<String, PathList> paths = new HashMap<String, PathList>();

    /**
     * <p>
     * The extracted information about target component. Is initialized and used in extract(). Can be null only before
     * initialization. Its attributes are modified in processPropertyElement().
     * </p>
     */
    private Component resultComponent = null;

    /**
     * <p>
     * The list of extracted dependency components. Collection is initialized in extract(). Cannot be null after
     * initialization. Cannot contain null. Content is modified in processDependencyPath().
     * </p>
     */
    private List<ComponentDependency> resultDependencies = null;

    /**
     * <p>
     * The build file provider for this extractor. Is initialized in extract(). Cannot be null after initialization. Is
     * used in processFile(). Is LocalBuildFileProvider if dependencies are currently extracted from a build file on the
     * disk. Is ZipBuildFileProvider if dependencies are currently extracted from a ZIP component distribution file.
     * </p>
     */
    private BuildFileProvider fileProvider = null;

    /**
     * <p>
     * The logger instance to be used for logging extraction errors and info. Is initialized in the constructor and can
     * be modified in setLogger(). Can be null if logging is not required. Is used in all methods of this class.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * The list of virtual paths of build files that are currently being processed. It is used in
     * processIncludeElement() to detect build files circular dependency and avoid it. Collection instance is
     * initialized in the constructor and never changed after that. Cannot be null, cannot contain null.
     * </p>
     */
    private final Set<String> filesInProgress = new HashSet<String>();

    /**
     * <p>
     * Creates an instance of DotNetDependenciesEntryExtractor with the given logger.
     * </p>
     *
     * @param logger the logger to be used by this class (can be null if logging is not required)
     */
    public DotNetDependenciesEntryExtractor(Log logger) {
        super();
        this.logger = logger;
    }

    /**
     * <p>
     * Extracts the component dependencies entry from the given file.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return the extracted dependencies entry (cannot be null)
     *
     * @throws IllegalArgumentException if filePath is null or empty
     * @throws DependenciesEntryExtractionException if file with the given file doesn't exist, its type is not supported
     *             by the current extractor or a fatal error occurred during the extraction
     */
    public DependenciesEntry extract(String filePath) throws DependenciesEntryExtractionException {
        logEnter("DotNetDependenciesEntryExtractor#extract(String) filePath is '" + filePath + "'");
        try {
            return internalExtract(filePath);
        } finally {
            logExit("DotNetDependenciesEntryExtractor#extract(String) filePath is '" + filePath + "'");
        }
    }

    /**
     * <p>
     * Extracts the component dependencies entry from the given file.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return the extracted dependencies entry (cannot be null)
     *
     * @throws IllegalArgumentException if filePath is null or empty
     * @throws DependenciesEntryExtractionException if file with the given file doesn't exist, its type is not supported
     *             by the current extractor or a fatal error occurred during the extraction
     */
    private DependenciesEntry internalExtract(String filePath) throws DependenciesEntryExtractionException {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        if (!isSupportedFileType(filePath)) {
            throw logException(new DependenciesEntryExtractionException("file<" + filePath + "> is not supported."));
        }

        resultComponent = new Component(TEMPORARY_COMPONENT_NAME, "", ComponentLanguage.DOT_NET);
        resultDependencies = new ArrayList<ComponentDependency>();

        try {
            if (isBuildFile(filePath)) {
                // default.build
                fileProvider = new LocalBuildFileProvider(filePath);
            } else {
                String fileNameWithoutExt = BuildFileParsingHelper.getFileNameWithoutExtension(filePath);
                fileProvider = new ZipBuildFileProvider(filePath, fileNameWithoutExt + "/" + BUILD_FILE_NAME);
                String[] values = BuildFileParsingHelper.parseFileNameWithoutExtension(fileNameWithoutExt);
                resultComponent.setName(values[0]);
                resultComponent.setVersion(values[1]);
            }

            // clear properties, paths, macroElements and filesInProgress.
            properties.clear();
            paths.clear();
            filesInProgress.clear();

            processFile("");
        } catch (BuildFileProvisionException e) {
            throw logException(new DependenciesEntryExtractionException(
                "BuildFileProvisionException occurs when constructing ZipBuildFileProvider : " + e.getMessage(), e));
        } catch (DependenciesEntryPersistenceException e) {
            throw logException(new DependenciesEntryExtractionException(
                "DependenciesEntryPersistenceException occurs when processing file : " + e.getMessage(), e));
        } finally {
            if (fileProvider != null) {
                fileProvider.close();
            }
        }

        if (TEMPORARY_COMPONENT_NAME.equals(resultComponent.getName())) {
            throw logException(new DependenciesEntryExtractionException("component name can not be retrieved."));
        }

        logInfo(String.format("finish handling component from file<%1$s> successfully : name<%2$s> version<%3$s>",
            filePath, resultComponent.getName(), resultComponent.getVersion()));
        return new DependenciesEntry(resultComponent, resultDependencies);
    }

    /**
     * <p>
     * Checks whether the given file can be processed by this extractor. Currently it supports default.build and *.zip
     * files.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return true if file format is supported by this extractor, false otherwise
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public boolean isSupportedFileType(String filePath) {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        return isBuildFile(filePath) || isZipFile(filePath);
    }

    /**
     * <p>
     * Sets the logger for this extractor.
     * </p>
     *
     * @param logger the logger to be used (null if logging is not required)
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Checks if the file name is "default.build".
     * </p>
     *
     * @param filePath file path to be checked. not null and not empty.
     * @return true if the file name is "build.xml" or false if it is not
     */
    private boolean isBuildFile(String filePath) {
        return BUILD_FILE_NAME.equals(BuildFileParsingHelper.getFileName(filePath));
    }

    /**
     * <p>
     * Checks to see if a usable zip file name.
     * </p>
     *
     * @param filePath file path
     * @return true if it is a valid jar file name otherwise false
     */
    private boolean isZipFile(String filePath) {
        return filePath.endsWith(ZIP_FILE_EXTENSION);
    }

    /**
     * <p>
     * Processes the build file with the given relative virtual path.
     * </p>
     *
     * @param relativePath the relative path of the file to be processed (cannot be null)
     *
     * @throws DependenciesEntryPersistenceException if fatal error occurred when processing build file
     */
    private void processFile(String relativePath) throws DependenciesEntryPersistenceException {
        InputStream stream = null;
        try {
            try {
                stream = fileProvider.getFileStream(relativePath);
            } catch (BuildFileProvisionException e) {
                // if it is main file, stop execution by throwing exception
                if (relativePath.length() == 0) {
                    throw logException(new DependenciesEntryPersistenceException(
                        "BuildFileProvisionException occurs when getting inputStream for relative path<" + relativePath
                            + "> : " + e.getMessage(), e));
                } else {
                    logWarningException("file with relativePath<" + relativePath + "> is not found.", e);
                    return;
                }
            }
            // construct xml dom
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(stream);
            // initiate root
            final Element root = doc.getDocumentElement();
            String baseDirValue = root.getAttribute(ATTRIBUTE_BASE_DIR);
            properties.put(ATTRIBUTE_BASE_DIR, Utils.isStringNullOrEmpty(baseDirValue) ? "." : baseDirValue);
            BuildFileParsingHelper.handleChildElements(root, new BuildFileParsingHelper.ChildElementHandler() {
                public void handle(Element child) {
                    if ("property".equals(child.getTagName())) {
                        processPropertyElement(child);
                    } else if ("include".equals(child.getTagName())) {
                        processIncludeElement(child);
                    } else if ("fileset".equals(child.getTagName()) || "assemblyfileset".equals(child.getTagName())) {
                        processFileSetElement(child);
                    } else if ("target".equals(child.getTagName())) {
                        if ("compile".equals(child.getAttribute(ATTRIBUTE_NAME))
                            && extractedCategories.contains(DependencyCategory.COMPILE)) {
                            processTargetElement(child, DependencyCategory.COMPILE);
                        } else if (("compile_tests".equals(child.getAttribute(ATTRIBUTE_NAME)) || "copy_dependencies"
                            .equals(child.getAttribute(ATTRIBUTE_NAME)))
                            && extractedCategories.contains(DependencyCategory.TEST)) {
                            processTargetElement(child, DependencyCategory.TEST);
                        }
                    }
                }
            });
        } catch (FactoryConfigurationError e) {
            throw logException(new DependenciesEntryPersistenceException(
                "FactoryConfigurationError occurs when getting get document builder factory : " + e.getMessage(), e));
        } catch (ParserConfigurationException e) {
            throw logException(new DependenciesEntryPersistenceException(
                "ParserConfigurationException occurs when getting get document builder : " + e.getMessage(), e));
        } catch (SAXException e) {
            throw logException(new DependenciesEntryPersistenceException("SAXException occurs when parsing document : "
                + e.getMessage(), e));
        } catch (IOException e) {
            throw logException(new DependenciesEntryPersistenceException("IOException occurs when parsing document : "
                + e.getMessage(), e));
        } finally {
            Utils.closeInputStreamSafely(stream);
        }
    }

    /**
     * <p>
     * Processes the given &lt;property&gt; element from the current build file.
     * </p>
     *
     * @param element the property element to be processed (cannot be null)
     */
    private void processPropertyElement(Element element) {
        if (element.hasAttribute(ATTRIBUTE_NAME) && element.hasAttribute(ATTRIBUTE_VALUE)) {
            String name = element.getAttribute(ATTRIBUTE_NAME);
            String value = resolveString(element.getAttribute(ATTRIBUTE_VALUE));
            if (!properties.containsKey(name)) {
                if ("distfilename".equals(name)) {
                    if (!Utils.isStringNullOrEmpty(value)) {
                        resultComponent.setName(value);
                    }
                } else if ("component".equals(name) && TEMPORARY_COMPONENT_NAME.equals(resultComponent.getName())) {
                    if (!Utils.isStringNullOrEmpty(value)) {
                        resultComponent.setName(value.toLowerCase());
                    }
                } else if ("component_version".equals(name)) {
                    resultComponent.setVersion(value);
                }
                properties.put(name, value);
            }
        } else {
            logWarningException("'name','value' attribute pair is not found in property element.", null);
        }
    }

    /**
     * <p>
     * Processes the given &lt;include&gt; element from the current build file.
     * </p>
     *
     * @param element the include element to be processed (cannot be null)
     */
    private void processIncludeElement(Element element) {
        if (!element.hasAttribute(ATTRIBUTE_BUILD_FILE)) {
            logWarningException("include element doesn't have buildfile attribute defined.", null);
            return;
        }

        String filePath = resolveString(element.getAttribute(ATTRIBUTE_BUILD_FILE));
        // avoid cyclic dependencies
        if (!filesInProgress.contains(filePath)) {
            filesInProgress.add(filePath);
            try {
                processFile(filePath);
            } catch (DependenciesEntryPersistenceException e) {
                logWarningException("error occurs when handling include element with filePath<" + filePath + ">", e);
            }
            filesInProgress.remove(filePath);
        }
    }

    /**
     * <p>
     * Processes the given &lt;fileset&gt; or &lt;assemblyfileset&gt; element from the current build file.
     * </p>
     *
     * @param element the fileset element to be processed (cannot be null)
     */
    private void processFileSetElement(Element element) {
        if (!element.hasAttribute(ATTRIBUTE_ID)) {
            logWarningException("fileset element doesn't have 'id' attribute defined.", null);
            return;
        }

        paths.put(element.getAttribute(ATTRIBUTE_ID), parseFileSetElement(element));
    }

    /**
     * <p>
     * Parses the given &lt;fileset&gt; element to a new PathList.
     * </p>
     *
     * @param element the fileset element to be parsed (cannot be null)
     * @return the path list instance with extracted information (cannot be null)
     */
    private PathList parseFileSetElement(Element element) {
        final PathList pathList = new PathList();
        handleRefidAttribute(element, pathList);

        BuildFileParsingHelper.handleChildElements(element, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(Element child) {
                if (("include".equals(child.getTagName()) || "includes".equals(child.getTagName()))
                    && child.hasAttribute(ATTRIBUTE_NAME)) {
                    String resolvedPath = resolveString(child.getAttribute(ATTRIBUTE_NAME));
                    if (!Utils.isStringNullOrEmpty(resolvedPath)) {
                        pathList.addPath(resolvedPath);
                    }
                }
            }
        });
        return pathList;
    }

    /**
     * <p>
     * Handles refid attribute for path element.
     * </p>
     *
     * @param element path element
     * @param pathList path list
     */
    private void handleRefidAttribute(Element element, PathList pathList) {
        // check refid attributes
        String refid = resolveString(element.getAttribute(ATTRIBUTE_REF_ID));
        // see http://forums.topcoder.com/?module=Thread&threadID=615045&start=0
        if (!Utils.isStringNullOrEmpty(refid)) {
            if (paths.containsKey(refid)) {
                // since it returns shallow copy, we have to add one by one
                for (String filePath : paths.get(refid).getFilePaths()) {
                    pathList.addPath(filePath);
                }
            }
        }
    }

    /**
     * <p>
     * Processes the given &lt;target&gt; element from the current build file.
     * </p>
     *
     * @param element the target element to be processed (cannot be null)
     * @param category the dependency category currently parsed (cannot be null)
     */
    private void processTargetElement(Element element, final DependencyCategory category) {
        BuildFileParsingHelper.handleChildElements(element, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(Element child) {
                String name = child.getTagName();
                if ("copy".equals(name) && category == DependencyCategory.TEST) {
                    processCopyElement(child, category);
                } else if ("csc".equals(name)) {
                    processChildrenReferences(child, category);
                }
            }
        });
    }

    /**
     * <p>
     * Processes the given &lt;copy&gt; element from the current build file.
     * </p>
     *
     * @param element the copy element to be processed (cannot be null)
     * @param category the dependency category currently parsed (cannot be null)
     */
    private void processCopyElement(Element element, DependencyCategory category) {
        if (!element.hasAttribute(ATTRIBUTE_FILE)) {
            logWarningException("copy element doesn't have 'file' attribute defined.", null);
            return;
        }

        processDependencyPath(resolveString(element.getAttribute(ATTRIBUTE_FILE)), category);
    }

    /**
     * <p>
     * Processes all children &lt;refernces&gt; subelements of the given element.
     * </p>
     *
     * @param category the dependency category currently parsed (cannot be null)
     * @param parent the parent element (cannot be null)
     */
    private void processChildrenReferences(Element parent, final DependencyCategory category) {
        BuildFileParsingHelper.handleChildElements(parent, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(Element child) {
                if ("references".equals(child.getTagName())) {
                    processReferencesElement(child, category);
                }
            }
        });
    }

    /**
     * <p>
     * Processes the given &lt;references&gt; element from the current build file.
     * </p>
     *
     * @param element the references element to be processed (cannot be null)
     * @param category the dependency category currently parsed (cannot be null)
     */
    private void processReferencesElement(Element element, DependencyCategory category) {
        processPathListDependency(parseFileSetElement(element), category);
    }

    /**
     * <p>
     * Processes all dependencies of the specified PathList.
     * </p>
     *
     * @param category the dependency category currently parsed (cannot be null)
     * @param pathList the path list to be processed (cannot be null)
     */
    private void processPathListDependency(PathList pathList, DependencyCategory category) {
        for (String path : pathList.getFilePaths()) {
            processDependencyPath(path, category);
        }
    }

    /**
     * <p>
     * Processes the given path as a dependency component path. Tries to extract dependency component name, version and
     * type.
     * </p>
     *
     * @param category the dependency category currently parsed (cannot be null)
     * @param path the path to be processed (cannot be null)
     */
    private void processDependencyPath(String path, DependencyCategory category) {
        if (!path.endsWith(DLL_FILE_EXTENSION)) {
            return;
        }

        // decide type: INTERNAL or EXTERNAL
        // see http://forums.topcoder.com/?module=Thread&threadID=615092&start=0
        DependencyType type = path.indexOf("bin\\tcs\\") != -1 ? DependencyType.INTERNAL : DependencyType.EXTERNAL;
        if (!extractedTypes.contains(type)) {
            return;
        }

        String name = null;
        String version = null;
        Matcher matcher1 = DLL_FILE_PATTERN1.matcher(path);
        Matcher matcher2 = DLL_FILE_PATTERN2.matcher(path);
        Matcher matcher3 = DLL_FILE_PATTERN3.matcher(path);
        if (matcher1.matches()) {
            // "file_path\component_name\component_version\dll_name.dll"
            name = matcher1.group(1);
            version = matcher1.group(2);
        } else if (matcher2.matches()) {
            // "file_path\component_name-component_version.dll"
            name = matcher2.group(1);
            version = matcher2.group(2);
        } else if (matcher3.matches()) {
            // "file_path\component_name\component_version\bin\dll_name.dll"
            name = matcher3.group(1);
            version = matcher3.group(2);
        } else {
            name = BuildFileParsingHelper.getFileNameWithoutExtension(path);
            version = "";
        }

        updateDependencies(new ComponentDependency(name, version, ComponentLanguage.DOT_NET, category, type, path));
    }

    /**
     * <p>
     * Updates result dependencies. The new dependency is added when there is no item with the same name,version and
     * category can be found.
     * </p>
     *
     * @param newDependency new dependency
     */
    private void updateDependencies(ComponentDependency newDependency) {
        if (BuildFileParsingHelper.contains(resultDependencies, newDependency)) {
            return;
        }

        logInfo(String.format("Found a dependency with name<%1$s> version<%2$s> category<%3$s> type<%4$s>.",
            newDependency.getName(), newDependency.getVersion(), newDependency.getCategory(), newDependency.getType()));
        resultDependencies.add(newDependency);
    }

    /**
     * <p>
     * Gets resolved string. It is a shortcut method.
     * </p>
     *
     * @param stringValue string value which might contain property variable
     * @return resolved string value
     */
    private String resolveString(String stringValue) {
        return BuildFileParsingHelper.resolveProperties(stringValue, properties);
    }

    /**
     * <p>
     * Logs entrance.
     * </p>
     *
     * @param signature The signature of the method to be logged.
     */
    private void logEnter(String signature) {
        Utils.logEnter(logger, signature);
    }

    /**
     * <p>
     * Logs exit.
     * </p>
     *
     * @param signature The signature of the method to be logged.
     */
    private void logExit(String signature) {
        Utils.logExit(logger, signature);
    }

    /**
     * <p>
     * Logs information.
     * </p>
     *
     * @param message to be logged
     */
    private void logInfo(String message) {
        Utils.logInfo(logger, message);
    }

    /**
     * <p>
     * Logs warning message and exception in warning level.
     * </p>
     *
     * @param message warning message
     * @param e exception to be logged
     */
    private void logWarningException(String message, Exception e) {
        Utils.logWarningException(logger, message, e);
    }

    /**
     * <p>
     * Logs exception.
     * </p>
     *
     * @param <T> exception type
     * @param e exception to be logged
     * @return logged exception
     */
    private <T extends Exception> T logException(T e) {
        return Utils.logException(logger, e);
    }
}
