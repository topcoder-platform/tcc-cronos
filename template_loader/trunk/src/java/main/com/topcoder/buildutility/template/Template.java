/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;


/**
 * <p>This class is an object representation of a templates which are grouped into the template hierarchies. This class
 * does not implement any complex logic other than storing the values of the attributes of the template and returning
 * them via simple accessor methods.</p>
 *
 * <p>Thread safety: this class is thread safe. The private state of the instances of this class is never changed after
 * instantiation.</p>
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public final class Template {

    /**
     * <p>A <code>long</code> providing the ID of the template uniquely distinguishing it among other templates. This
     * instance field is initialized within the constructor and is never changed during the lifetime of <code>Template
     * </code> instance.</p>
     */
    private long id = 0;

    /**
     * <p>A <code>String</code> providing the name of the template uniquely distinguishing it among other templates.
     * This instance field is initialized within the constructor and is never changed during the lifetime of <code>
     * Template</code> instance. This field must never be <code>null</code>.</p>
     */
    private String name = null;

    /**
     * <p>A <code>String</code> providing the optional description of the template. This instance field is initialized
     * within the constructor and is never changed during the lifetime of <code>Template</code> instance. This field
     * may be <code>null</code>.</p>
     */
    private String description = null;

    /**
     * <p>A <code>String</code> providing the required name of destination file corresponding to this template. This
     * instance field is initialized within the constructor and is never changed during the lifetime of <code>Template
     * </code> instance. This field must not be <code>null</code>.</p>
     */
    private String fileName = null;

    /**
     * <p>A <code>String</code> providing the name of the template uniquely distinguishing it among other templates.
     * This instance field is initialized within the constructor and is never changed during the lifetime of <code>
     * Template</code> instance. This field must never be <code>null</code>.</p>
     */
    private String uri = null;

    /**
     * <p>A <code>String</code> providing the URI of a file server root which the template URI is resolved relatively
     * to. This instance field is initialized through the corresponding mutator method. This instance field must not
     * be <code>null</code>.</p>
     */
    private String fileServerUri = null;

    /**
     * <p>Constructs new <code>Template</code> instance with specified ID, name, description, suggested file name and
     * content file URI.</p>
     *
     * @throws NullPointerException if any of specified arguments (except <code>description</code>) is <code>null
     * </code>.
     * @throws IllegalArgumentException if any of specified arguments is an empty <code>String</code> being trimmed.
     *
     * @param id a <code>long</code> providing the ID of this template.
     * @param name a <code>String</code> providing the name of this template.
     * @param description a <code>String</code> providing the textual description of this template.
     * @param fileName a <code>String</code> providing the suggested file name for the template.
     * @param uri a <code>String</code> providing the relative URI referencing the file providing the content of the
     * template.
     */
    public Template(long id, String name, String description, String fileName, String uri) {

        // arguments validations
        TemplateLoaderHelper.checkString(name, "name");
        TemplateLoaderHelper.checkEmpty(description, "description");
        TemplateLoaderHelper.checkString(fileName, "fileName");
        TemplateLoaderHelper.checkString(uri, "uri");

        // initialize the inner fields
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.uri = uri;
    }

    /**
     * <p>Gets the name of this template. Each template name must uniquely identify that template (i.e. no two
     * templates can have the same name).</p>
     *
     * @return a <code>String</code> providing the name of the template.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Gets the description of this template. Descriptions are optional but they will help a great deal when
     * developers are trying to determine the purpose of the template.</p>
     *
     * @return a <code>String</code> description of the template or <code>null</code> if such a description is not
     * specified.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Gets the intended or suggested file name corresponding to the template. This file name is more than likely
     * not the name used to store the content on the server side. This file name was designed to be the suggested name
     * when the transformation occurs from the template to the actual file and the file is written in destination
     * location. The file name is required and cannot be null.</p>
     *
     * @return a <code>String</code> providing the suggested name of destination file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <p>Get the template as a data stream. A template must contain data.</p>
     *
     * <p>Note that it is responsibility of a caller to close the returned stream when the content of the template is
     * retrieved.</p>
     *
     * @throws SecurityException if a security manager exists and its checkRead method denies read access to the file.
     * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     * @throws IllegalStateException if the file server uri is not set.
     *
     * @return an <code>InputStream</code> providing the template content.
     */
    public synchronized InputStream getData() throws FileNotFoundException {
        if (fileServerUri == null) {
            throw new IllegalStateException("setFileServerUri must be called before using getData method");
        }
        // get the absolute URI and create a FileInputStream with it.
        return new FileInputStream(new File(fileServerUri, uri));

    }

    /**
     * <p>Gets the ID of this template.</p>
     *
     * @return a <code>long</code> providing the ID of this template.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Gets the URI referencing the file with content of this template relatively the configured file
     * server root.</p>
     *
     * @return a <code>String</code> providing the URI referencing the file providing the content of this template.
     */
    public String getUri() {
        return uri;
    }

    /**
     * <p>Sets the file server root URI which must be used to resolve the URI of a file providing the content of this
     * template. This method will be called immediately after instantiation of this <code>Template</code> instance.
     * Only <code>TemplateLoader</code> is allowed to call this method (indirectly through the <code>TemplateHierarchy
     * </code> class). Therefore this method is declared with package private access.</p>
     *
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     * @throws IllegalArgumentException if specified <code>fileServerUri</code> is an empty <code>String</code> being
     * trimmed.
     *
     * @param fileServerUri a <code>String</code> providing the URI of a file server root.
     */
    synchronized void setFileServerUri(String fileServerUri) {
        TemplateLoaderHelper.checkString(fileServerUri, "fileServerUri");
        this.fileServerUri = fileServerUri;
    }


}
