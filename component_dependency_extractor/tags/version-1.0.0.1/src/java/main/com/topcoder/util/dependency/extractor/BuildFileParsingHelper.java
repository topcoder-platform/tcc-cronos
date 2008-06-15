/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.topcoder.util.dependency.ComponentDependency;

/**
 * <p>
 * This is a package helper class that can be used by DotNetDependenciesEntryExtractor and
 * JavaDependenciesEntryExtractor when parsing build files.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
class BuildFileParsingHelper {
    /**
     * <p>
     * property name pattern.
     * </p>
     */
    private static final Pattern PROPETY_PATTERN = Pattern.compile("\\$\\{([^${}]*)\\}");

    /**
     * <p>
     * Pattern for file name without extension. It is "component_name-component_version", where where component_version
     * is not empty and starts with digit and contains only digits, dots.
     * </p>
     */
    private static final Pattern FILE_NAME_WITHOUT_EXT_PATTERN = Pattern.compile("([^/]+)-(\\d[\\d.]*)");

    /**
     * <p>
     * Private empty constructor.
     * </p>
     */
    private BuildFileParsingHelper() {
        // Do nothing.
    }

    /**
     * <p>
     * Resolves the properties in the given string. The string has format " ... some text ... ${property1_name} ... more
     * text ... ${property2_name} ...". If properties contains mappings "property1_name"="value1", then this method
     * should return " ... some text ... value1 ... more text ... ..." (property with unknown name is just removed from
     * the input string).
     * </p>
     *
     * @param value the string with properties to be resolved (cannot be null)
     * @param properties the map of defined properties (cannot be null, cannot contain null)
     * @return the string with resolved properties (cannot be null)
     */
    public static String resolveProperties(String value, Map<String, String> properties) {
        // http://forums.topcoder.com/?module=Thread&threadID=615067&start=0&mc=1#982852

        // find out all ${property_name} in one pass
        Set<String> propertyNames = new HashSet<String>();
        Matcher matcher = PROPETY_PATTERN.matcher(value);
        while (matcher.find()) {
            propertyNames.add(matcher.group(1));
        }

        // resolve property names
        for (String propertyName : propertyNames) {
            value = value.replace("${" + propertyName + "}", properties.containsKey(propertyName) ? properties
                .get(propertyName) : "");
        }

        return value;
    }

    /**
     * <p>
     * Parses fileNameWithoutExt to get component name and version.
     * </p>
     *
     * @param fileNameWithoutExt file name without extension. it is not null.
     * @return a 2 element array, the first element is component name and the second one is component version
     */
    static String[] parseFileNameWithoutExtension(String fileNameWithoutExt) {
        // see http://forums.topcoder.com/?module=Thread&threadID=615092&start=0&mc=13#983186
        // parse fileNameWithoutExt as "component_name-component-version". If fileNameWithoutExt has invalid format
        // then use componentName=fileNameWithoutExt, componentVersion="".
        Matcher matcher = FILE_NAME_WITHOUT_EXT_PATTERN.matcher(fileNameWithoutExt);
        if (matcher.matches()) {
            return new String[] {matcher.group(1), matcher.group(2)};
        } else {
            return new String[] {fileNameWithoutExt, ""};
        }
    }

    /**
     * <p>
     * Gets file name without extension from file path.
     * </p>
     *
     * @param filePath file path. it is not null and it ends with .jar/.zip
     * @return file name without extension
     */
    static String getFileNameWithoutExtension(String filePath) {
        String fileName = getFileName(filePath);
        // logging_wrapper-2.0.jar --> logging_warpper-2.0
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * <p>
     * Gets file name from file path.
     * </p>
     *
     * @param filePath file path. it is not null and not empty
     * @return file name
     */
    static String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    /**
     * <p>
     * Sees if result dependency list contains newly created dependency. We look at name,version and category to
     * determine if it is already included.
     * </p>
     *
     * @param resultDependencies result dependency list
     * @param newDependency new dependency
     * @return true if it contains and false if it is not
     */
    static boolean contains(List<ComponentDependency> resultDependencies, ComponentDependency newDependency) {
        // see http://forums.topcoder.com/?module=Thread&threadID=615151&start=0&mc=1#983192
        for (ComponentDependency dependency : resultDependencies) {
            if (dependency.getName().equals(newDependency.getName())
                && dependency.getVersion().equals(newDependency.getVersion())
                && dependency.getCategory() == newDependency.getCategory()) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     * Loops through all child elements and takes action on each one.
     * </p>
     *
     * @param element not null <code>Element</code> object
     * @param handler to be used to handle each child element
     */
    static void handleChildElements(Element element, ChildElementHandler handler) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (!(childNode instanceof Element)) {
                continue;
            }
            handler.handle((Element) childNode);
        }
    }

    /**
     * <p>
     * Handler interface. It is used in handleChildElements(...) method to capture action on child element.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    static interface ChildElementHandler {
        /**
         * <p>
         * Handles child element.
         * </p>
         *
         * @param child element. It is not null.
         */
        void handle(Element child);
    }
}
