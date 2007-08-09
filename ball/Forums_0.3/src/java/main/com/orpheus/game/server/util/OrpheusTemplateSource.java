/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.file.templatesource.TemplateSource;
import com.topcoder.util.file.templatesource.TemplateSourceException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * <p>A custom implementation of {@link TemplateSource} interface to be utilized in context of the <code>Orpheus Game
 * Server</code> application for loading the content of templates resolving them against the <code>CLASSPATH</code>.
 * This plugin is used to resolve the issue with current version of <code>Document Generator</code> component which
 * operates with physical paths when locating the templates.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusTemplateSource implements TemplateSource {

    /**
     * <p>Constructs new <code>OrpheusTemplateSource</code> instance. This implementation does nothing.</p>
     */
    public OrpheusTemplateSource() {
    }

    /**
     * <p>Constructs new <code>OrpheusTemplateSource</code> instance. Such a constructor is required by the <code>
     * Document Generator</code> framework. This implementation does nothing.</p>
     *
     * @param namespace a <code>String</code> providing the configuration namespace.
     * @param sourceId a <code>String</code> providing the identifier for this source.
     */
    public OrpheusTemplateSource(String namespace, String sourceId) {
    }

    /**
     * <p>Retrieves the text of the template with the given name. This implementation resolves the specified template
     * name against the <code>CLASSPATH</code>.</p>
     *
     * @param templateName a <code>String</code> providing the name of the template.
     * @return a <code>String</code> providing the text of the template.
     * @throws IllegalArgumentException if specified <code>templateName</code> is <code>null</code> or empty.
     * @throws TemplateSourceException indicates an error while retrieving the template.
     */
    public String getTemplate(String templateName) throws TemplateSourceException {
        if ((templateName == null) || (templateName.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [templateName] is not valid. [" + templateName + "]");
        }

        StringBuffer template = new StringBuffer();
        BufferedReader reader = null;

        try {
            InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templateName);
            reader = new BufferedReader(new InputStreamReader(templateStream));

            // Buffer for reading from file.
            char[] buffer = new char[1024];

            // Count of characters that was read.
            int count = 0;

            // Read data from file until EOF.
            while ((count = reader.read(buffer)) != -1) {
                template.append(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new TemplateSourceException("Cannot read template [" + templateName + "] from CLASSPATH", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Print stack trace and ignore it
                    e.printStackTrace();
                }
            }
        }

        // Return the content of template.
        return new String(template);
    }

    /**
     * <p>Adds the template to the template source with the given name. This implementation does nothing.</p>
     *
     * @param templateName a <code>String</code> providing the name of the template.
     * @param templateText a <code>String</code> providing the text of the template.
     */
    public void addTemplate(String templateName, String templateText) {
    }

    /**
     * <p>Removes the template with the given name from the template source. This implementation does nothing.</p>
     *
     * @param templateName a <code>String</code> providing the name of the template.
     */
    public void removeTemplate(String templateName) {
    }
}
