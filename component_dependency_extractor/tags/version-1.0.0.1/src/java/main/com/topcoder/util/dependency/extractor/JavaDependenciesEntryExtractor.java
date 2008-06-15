/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
 * This subclass of BaseDependenciesEntryExtractor can extract dependencies from build or distribution files of Java
 * components from TopCoder software catalog. If accepts build.xml and *.jar files in extract() methods.
 * </p>
 * <p>
 * <b>Sample Usage</b>
 *
 * <pre>
 * // Extract compile dependencies for Java component
 * BaseDependenciesEntryExtractor extractor = new JavaDependenciesEntryExtractor(null);
 * Set&lt;DependencyCategory&gt; categories = new HashSet&lt;DependencyCategory&gt;();
 * categories.add(DependencyCategory.COMPILE);
 * extractor.setExtractedCategories(categories);
 * // Extract from build.xml
 * entry = extractor.extract(DEMO_DIR + File.separator + &quot;file_upload/build.xml&quot;);
 * // Extract from distribution file
 * entry = extractor.extract(DEMO_DIR + File.separator + &quot;file_upload-2.2.0.jar&quot;);
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
public class JavaDependenciesEntryExtractor extends BaseDependenciesEntryExtractor {
    /**
     * <p>
     * Supported build file name.
     * </p>
     */
    private static final String BUILD_FILE_NAME = "build.xml";

    /**
     * <p>
     * Supported file extension from which build file will be retrieved.
     * </p>
     */
    private static final String JAR_FILE_EXTENSION = ".jar";

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
     * Constant for attribute file.
     * </p>
     */
    private static final String ATTRIBUTE_FILE = "file";

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
     * Constant for attribute location.
     * </p>
     */
    private static final String ATTRIBUTE_LOCATTION = "location";

    /**
     * <p>
     * Constant for attribute path.
     * </p>
     */
    private static final String ATTRIBUTE_PATH = "path";

    /**
     * <p>
     * Dependency file pattern1. It is "file_path/component_name-component_version.jar", where component_version is not
     * empty and starts with digit and contains only digits, dots and optional letter at the end. component_name will
     * not include "/" character.
     * </p>
     */
    private static final Pattern JAR_FILE_PATTERN1 = Pattern.compile(".*/([^/]+)-(\\d[\\d.]*[a-zA-z]?)\\.jar");

    /**
     * <p>
     * Dependency file pattern2. It is "file_path/component_version/component_name.jar", where component_version is not
     * empty and starts with digit and contains only digits, dots and optional letter at the end.component_name will not
     * include "/" character.
     * </p>
     */
    private static final Pattern JAR_FILE_PATTERN2 = Pattern.compile(".*/(\\d[\\d.]*[a-zA-z]?)/([^/]+)\\.jar");

    /**
     * <p>
     * The list of properties defined in the build file that are already parsed. Collection instance is initialized and
     * never changed after that. Cannot be null, cannot contain null. Keys cannot be empty. Content is modified in
     * processPropertyElement(). Is used in most methods of this class.
     * </p>
     */
    private final Map<String, String> properties = new HashMap<String, String>();

    /**
     * <p>
     * The list of path-list structures currently extracted from build files. Collection instance is initialized and
     * never changed after that. Cannot be null, cannot contain null. Keys cannot be empty. Content is modified in
     * processPathElement(). Is also used in processPathListDependency().
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
     * used in processFile() and processPropertyElement(). Is LocalBuildFileProvider if dependencies are currently
     * extracted from a build file on the disk. Is ZipBuildFileProvider if dependencies are currently extracted from a
     * JAR component distribution file.
     * </p>
     */
    private BuildFileProvider fileProvider = null;

    /**
     * <p>
     * The list of macrodef elements currently found in build files. Collection instance is initialized in the
     * constructor and never changed after that. Cannot be null, cannot contain null keys and values. Keys cannot be
     * empty. Content is modified in processFile(). Is used in processTargetElement().
     * </p>
     */
    private final Map<String, Element> macroElements = new HashMap<String, Element>();

    /**
     * <p>
     * The logger instance to be used for logging extraction errors and info. Is initialized in the constructor and and
     * can be modified in setLogger(). Can be null if logging is not required. Is used in all methods of this class.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * The list of virtual paths of build files that are currently being processed. It is used in processImportElement()
     * to detect build files circular dependency and avoid it. Collection instance is initialized in the constructor and
     * never changed after that. Cannot be null, cannot contain null.
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
    public JavaDependenciesEntryExtractor(Log logger) {
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
        logEnter("JavaDependenciesEntryExtractor#extract(String) filePath is '" + filePath + "'");
        try {
            return internalExtract(filePath);
        } finally {
            logExit("JavaDependenciesEntryExtractor#extract(String) filePath is '" + filePath + "'");
        }
    }

    /**
     * <p>
     * This is the internal method which actually extracts the component dependencies entry from the given file. It will
     * be called by public method extract(...).
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

        resultComponent = new Component(TEMPORARY_COMPONENT_NAME, "", ComponentLanguage.JAVA);
        resultDependencies = new ArrayList<ComponentDependency>();

        try {
            if (isBuildFile(filePath)) {
                // build.xml
                fileProvider = new LocalBuildFileProvider(filePath);
            } else {
                String fileNameWithoutExt = BuildFileParsingHelper.getFileNameWithoutExtension(filePath);
                // inside logging_wrapper-2.0.jar
                // build.xml must be as logging_wrapper-2.0/build.xml
                fileProvider = new ZipBuildFileProvider(filePath, fileNameWithoutExt + "/" + BUILD_FILE_NAME);
                String[] values = BuildFileParsingHelper.parseFileNameWithoutExtension(fileNameWithoutExt);
                resultComponent.setName(values[0]);
                resultComponent.setVersion(values[1]);
            }

            // clear properties, paths, macroElements and filesInProgress.
            properties.clear();
            paths.clear();
            macroElements.clear();
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
     * Checks whether the given file can be processed by this extractor. Current it supports build.xml and *.jar files.
     * </p>
     *
     * @param filePath the source file path (cannot be null or empty)
     * @return true if file format is supported by this extractor, false otherwise
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public boolean isSupportedFileType(String filePath) {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        return isBuildFile(filePath) || isJarFile(filePath);
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
     * Checks if the file name is "build.xml".
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
     * Checks to see if a usable jar file name.
     * </p>
     *
     * @param filePath file path
     * @return true if it is a valid jar file name otherwise false
     */
    private boolean isJarFile(String filePath) {
        return filePath.endsWith(JAR_FILE_EXTENSION);
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
        // see http://forums.topcoder.com/?module=Thread&threadID=615090&start=0
        InputStream stream = null;
        boolean isMainFile = relativePath.length() == 0;
        try {
            try {
                stream = fileProvider.getFileStream(relativePath);
            } catch (BuildFileProvisionException e) {
                // if it is main file, stop execution by throwing exception
                if (isMainFile) {
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
            if (!root.hasAttribute(ATTRIBUTE_BASE_DIR)) {
                logWarningException("no basedir is defined. '.' will be used.", null);
            }
            String baseDirValue = root.getAttribute(ATTRIBUTE_BASE_DIR);
            properties.put(ATTRIBUTE_BASE_DIR, Utils.isStringNullOrEmpty(baseDirValue) ? "." : baseDirValue);
            BuildFileParsingHelper.handleChildElements(root, new BuildFileParsingHelper.ChildElementHandler() {
                public void handle(Element child) {
                    if ("property".equals(child.getTagName())) {
                        processPropertyElement(child);
                    } else if ("import".equals(child.getTagName())) {
                        processImportElement(child);
                    } else if ("path".equals(child.getTagName())) {
                        processPathElement(child);
                    } else if ("macrodef".equals(child.getTagName())) {
                        macroElements.put(child.getAttribute(ATTRIBUTE_NAME), child);
                    } else if ("target".equals(child.getTagName())) {
                        if ("compile".equals(child.getAttribute(ATTRIBUTE_NAME))
                            && extractedCategories.contains(DependencyCategory.COMPILE)) {
                            processTargetElement(child, DependencyCategory.COMPILE);
                        } else if ("test".equals(child.getAttribute(ATTRIBUTE_NAME))
                            && extractedCategories.contains(DependencyCategory.TEST)) {
                            processTargetElement(child, DependencyCategory.TEST);
                        }
                    }
                }
            });
        } catch (FactoryConfigurationError e) {
            handleException(e, relativePath);
        } catch (ParserConfigurationException e) {
            handleException(e, relativePath);
        } catch (SAXException e) {
            handleException(e, relativePath);
        } catch (IOException e) {
            handleException(e, relativePath);
        } finally {
            Utils.closeInputStreamSafely(stream);
        }
    }

    /**
     * <p>
     * Handles the exception.
     * </p>
     *
     * @param e exception
     * @param relativePath relative path
     *
     * @throws DependenciesEntryPersistenceException if it is main file, wrap it and throw it out
     */
    private void handleException(Throwable e, String relativePath) throws DependenciesEntryPersistenceException {
        boolean isMainFile = relativePath.length() == 0;
        String errorMessage = e.getClass().getSimpleName() + " occurs when getting processing file with relative path<"
            + relativePath + "> : " + e.getMessage();
        if (isMainFile) {
            throw logException(new DependenciesEntryPersistenceException(errorMessage, e));
        } else {
            logWarningException(errorMessage, e);
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
        if (element.hasAttribute(ATTRIBUTE_FILE)) {
            String propertyFilePath = resolveString(element.getAttribute(ATTRIBUTE_FILE));
            try {
                InputStream stream = fileProvider.getFileStream(propertyFilePath);
                Properties props = new Properties();
                props.load(stream);
                for (Enumeration<String> e = (Enumeration<String>) props.propertyNames(); e.hasMoreElements();) {
                    String name = e.nextElement();
                    if (!properties.containsKey(name)) {
                        String value = resolveString(props.getProperty(name));
                        properties.put(name, value);
                    }
                }
            } catch (BuildFileProvisionException e) {
                logWarningException("property file<" + propertyFilePath + "> can not be retrieved.", e);
                return;
            } catch (IOException e) {
                logWarningException("property file<" + propertyFilePath + "> can not be loaded properly.", e);
                return;
            }
        } else if (element.hasAttribute(ATTRIBUTE_NAME) && element.hasAttribute(ATTRIBUTE_VALUE)) {
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
            logWarningException("'name','value' attribute pair or 'file' attribute is not found in property element.",
                null);
        }
    }

    /**
     * <p>
     * Processes the given &lt;import&gt; element from the current build file.
     * </p>
     *
     * @param element the import element to be processed (cannot be null)
     */
    private void processImportElement(Element element) {
        if (!element.hasAttribute(ATTRIBUTE_FILE)) {
            logWarningException("import element doesn't have 'file' attribute defined.", null);
            return;
        }

        String filePath = resolveString(element.getAttribute(ATTRIBUTE_FILE));
        // avoid cyclic dependencies
        if (!filesInProgress.contains(filePath)) {
            filesInProgress.add(filePath);
            try {
                processFile(filePath);
            } catch (DependenciesEntryPersistenceException e) {
                // see http://forums.topcoder.com/?module=Thread&threadID=615138&start=0&mc=1#983158
                logWarningException("error occurs when handling import element with filePath<" + filePath + ">", e);
            }
            filesInProgress.remove(filePath);
        }
    }

    /**
     * <p>
     * Processes the given &lt;path&gt; element from the current build file.
     * </p>
     *
     * @param element the path element to be processed (cannot be null)
     */
    private void processPathElement(Element element) {
        if (!element.hasAttribute(ATTRIBUTE_ID)) {
            logWarningException("path element doesn't have 'id' attribute defined.", null);
            return;
        }

        paths.put(element.getAttribute(ATTRIBUTE_ID), parsePathElement(element));
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
                if ("junit".equals(name) && category == DependencyCategory.TEST) {
                    processChildrenClassPath(child, category);
                } else if ("javac".equals(name) && category == DependencyCategory.COMPILE) {
                    processChildrenClassPath(child, category);
                } else if (macroElements.containsKey(name)) {
                    processMacroDefElement(macroElements.get(name), category);
                }
            }
        });
    }

    /**
     * <p>
     * Processes the given &lt;macrodef&gt; element from the current build file.
     * </p>
     *
     * @param element the macrodef element to be processed (cannot be null)
     * @param category the dependency category currently parsed (cannot be null)
     */
    private void processMacroDefElement(Element element, final DependencyCategory category) {
        BuildFileParsingHelper.handleChildElements(element, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(final Element subElement) {
                BuildFileParsingHelper.handleChildElements(subElement,
                    new BuildFileParsingHelper.ChildElementHandler() {
                        // nested inside <macrodef..> --> <sequential> --> <junit> or <javac>
                        public void handle(Element child) {
                            String name = child.getTagName();
                            if ("junit".equals(name) && category == DependencyCategory.TEST) {
                                processChildrenClassPath(child, category);
                            } else if ("javac".equals(name) && category == DependencyCategory.COMPILE) {
                                processChildrenClassPath(child, category);
                            }
                        }
                    });
            }
        });
    }

    /**
     * <p>
     * Processes all children &lt;classpath&gt; subelements of the given element.
     * </p>
     *
     * @param category the dependency category currently parsed (cannot be null)
     * @param parent the parent element (cannot be null)
     */
    private void processChildrenClassPath(final Element parent, final DependencyCategory category) {
        BuildFileParsingHelper.handleChildElements(parent, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(Element child) {
                if ("classpath".equals(child.getTagName())) {
                    processClassPathElement(child, category);
                }
            }
        });
    }

    /**
     * <p>
     * Processes the given &lt;classpath&gt; element from the current build file.
     * </p>
     *
     * @param element the classpath element to be processed (cannot be null)
     * @param category the dependency category currently parsed (cannot be null)
     */
    private void processClassPathElement(Element element, DependencyCategory category) {
        PathList pathList = parsePathElement(element);
        processPathListDependency(pathList, category);
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
        if (!path.endsWith(JAR_FILE_EXTENSION)) {
            return;
        }

        // decide type: INTERNAL or EXTERNAL
        // see http://forums.topcoder.com/?module=Thread&threadID=615092&start=0
        DependencyType type = path.indexOf("lib/tcs/") != -1 ? DependencyType.INTERNAL : DependencyType.EXTERNAL;
        if (!extractedTypes.contains(type)) {
            return;
        }

        String name = null;
        String version = null;
        Matcher matcher1 = JAR_FILE_PATTERN1.matcher(path);
        Matcher matcher2 = JAR_FILE_PATTERN2.matcher(path);
        if (matcher1.matches()) {
            // "file_path/component_name-component_version.jar"
            name = matcher1.group(1);
            version = matcher1.group(2);
        } else if (matcher2.matches()) {
            // "file_path/component_version/component_name.jar"
            name = matcher2.group(2);
            version = matcher2.group(1);
        } else {
            name = BuildFileParsingHelper.getFileNameWithoutExtension(path);
            version = "";
        }

        updateDependencies(new ComponentDependency(name, version, ComponentLanguage.JAVA, category, type, path));
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
     * Parses the given &lt;path&gt; element to a new PathList.
     * </p>
     *
     * @param element the path element to be parsed (cannot be null)
     * @return the path list instance with extracted information (cannot be null)
     */
    private PathList parsePathElement(final Element element) {
        final PathList pathList = new PathList();
        handleLocationAttribute(element, pathList);
        handlePathAttribute(element, pathList);
        handleRefidAttribute(element, pathList);

        BuildFileParsingHelper.handleChildElements(element, new BuildFileParsingHelper.ChildElementHandler() {
            public void handle(Element child) {
                if ("path".equals(child.getTagName())) {
                    // example: <path refid="path_id" />
                    handleRefidAttribute(child, pathList);
                } else if ("pathelement".equals(child.getTagName())) {
                    // examples: <pathelement location="lib/helper.jar"/>
                    handleLocationAttribute(child, pathList);
                    handlePathAttribute(child, pathList);
                }
            }
        });

        return pathList;
    }

    /**
     * <p>
     * Handles location attribute for path or pathelement element.
     * </p>
     *
     * @param element path or pathelement element
     * @param pathList path list
     */
    private void handleLocationAttribute(Element element, PathList pathList) {
        String location = resolveString(element.getAttribute(ATTRIBUTE_LOCATTION));
        if (!Utils.isStringNullOrEmpty(location)) {
            pathList.addPath(location);
        }

    }

    /**
     * <p>
     * Handles path attribute for path or pathelement element.
     * </p>
     *
     * @param element path or pathelement element
     * @param pathList path list
     */
    private void handlePathAttribute(Element element, PathList pathList) {
        String path = resolveString(element.getAttribute(ATTRIBUTE_PATH));
        if (!Utils.isStringNullOrEmpty(path)) {
            String[] filePaths = path.split("[:;]");
            for (String filePath : filePaths) {
                String resolvedFilePath = resolveString(filePath);
                if (!Utils.isStringNullOrEmpty(resolvedFilePath)) {
                    pathList.addPath(resolvedFilePath);
                }
            }
        }
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
    private void logWarningException(String message, Throwable e) {
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
