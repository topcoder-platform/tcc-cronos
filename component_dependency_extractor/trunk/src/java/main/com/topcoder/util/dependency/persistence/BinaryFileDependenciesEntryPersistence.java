/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentDependencyConfigurationException;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryPersistence;
import com.topcoder.util.dependency.DependenciesEntryPersistenceException;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.Utils;

/**
 * <p>
 * This implementation of DependenciesEntryPersistence loads (saves) dependency entries from (to) binary files. Unlike
 * XML format, binary format has very small overhead, thus the size of binary dependency files is 2-3 times less that
 * size of XML files with the same amount of information. Also binary files can be loaded and saved much faster that XML
 * files (generally, more than 10 times faster). Thus this type of persistence is preferred for large dependency lists.
 * This class simply uses ObjectOutputStream to write data to the binary file and ObjectInputStream to read data.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe when multiple instances of it don't use the same file
 * simultaneously.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class BinaryFileDependenciesEntryPersistence implements DependenciesEntryPersistence {
    /**
     * <p>
     * Configuration key : The name of the file where the list of dependency entries is (can be) stored. The value for
     * this key is String and not empty and required.
     * </p>
     */
    private static final String KEY_FILE_NAME = "file_name";

    /**
     * <p>
     * The path of the file that is used for storing the list of dependency entries in binary format. Is initialized in
     * the constructor and never changed after that. Cannot be null or empty. Is used in load() and save().
     * </p>
     */
    private final String filePath;

    /**
     * <p>
     * Creates an instance of BinaryFileDependenciesEntryPersistence with the given file path.
     * </p>
     *
     * @param filePath the persistence file path(cannot be null or empty)
     *
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public BinaryFileDependenciesEntryPersistence(String filePath) {
        Utils.checkStringNullOrEmpty(filePath, "filePath");
        this.filePath = filePath;
    }

    /**
     * <p>
     * Creates an instance of BinaryFileDependenciesEntryPersistence from the given configuration object.
     * </p>
     *
     * @param configuration the configuration object for this class (cannot be null)
     *
     * @throws IllegalArgumentException if configuration is null
     * @throws ComponentDependencyConfigurationException if error occurred while reading the configuration
     */
    public BinaryFileDependenciesEntryPersistence(ConfigurationObject configuration)
        throws ComponentDependencyConfigurationException {
        Utils.checkNull(configuration, "configuration");
        this.filePath = Utils.getRequiredStringValue(configuration, KEY_FILE_NAME);
    }

    /**
     * <p>
     * Loads the list of dependency entries from the binary file with the given name.
     * </p>
     *
     * @return the loaded list of entries (cannot be null; cannot contain null)
     *
     * @throws DependenciesEntryPersistenceException if file doesn't exist, has invalid format or I/O error occurred
     */
    public List<DependenciesEntry> load() throws DependenciesEntryPersistenceException {
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            List<DependenciesEntry> result = new ArrayList<DependenciesEntry>();

            int entryNum = stream.readInt();
            for (int i = 0; i < entryNum; i++) {
                String componentName = stream.readUTF();
                String componentVersion = stream.readUTF();
                // possible out of bound exception
                ComponentLanguage language = ComponentLanguage.values()[stream.readInt()];
                Component component = new Component(componentName, componentVersion, language);

                // dependencies
                List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
                int dependencyNum = stream.readInt();
                for (int j = 0; j < dependencyNum; j++) {
                    String name = stream.readUTF();
                    String version = stream.readUTF();
                    DependencyCategory category = DependencyCategory.values()[stream.readInt()];
                    DependencyType type = DependencyType.values()[stream.readInt()];
                    String path = stream.readUTF();
                    dependencies.add(new ComponentDependency(name, version, language, category, type, path));
                }

                // create a new entry
                result.add(new DependenciesEntry(component, dependencies));
            }

            return result;
        } catch (FileNotFoundException e) {
            throw new DependenciesEntryPersistenceException(
                "FileNotFoundException occurs while creating file input stream for " + filePath + " : "
                    + e.getMessage(), e);
        } catch (IOException e) {
            throw new DependenciesEntryPersistenceException("IOException occurs while creating reading from file<"
                + filePath + "> : " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new DependenciesEntryPersistenceException(
                "IllegalArgumentException occurs while  constructing objects : " + e.getMessage(), e);
        } catch (IndexOutOfBoundsException e) {
            throw new DependenciesEntryPersistenceException(
                "IndexOutOfBoundsException occurs while getting enum value : " + e.getMessage(), e);
        } finally {
            Utils.closeInputStreamSafely(stream);
        }
    }

    /**
     * <p>
     * Saves the provided list of dependency entries to the binary file with the given name.
     * </p>
     *
     * @param entries the list of entries to be saved (cannot be null; cannot contain null)
     *
     * @throws IllegalArgumentException if entries is null or contains null
     * @throws DependenciesEntryPersistenceException if I/O error occurred
     */
    public void save(List<DependenciesEntry> entries) throws DependenciesEntryPersistenceException {
        Utils.checkCollection(entries, "entries", true);
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new BufferedOutputStream((new FileOutputStream(filePath))));
            stream.writeInt(entries.size());
            for (DependenciesEntry entry : entries) {
                // write each entry
                stream.writeUTF(entry.getTargetComponent().getName());
                stream.writeUTF(entry.getTargetComponent().getVersion());
                stream.writeInt(entry.getTargetComponent().getLanguage().ordinal());
                // write dependencies
                stream.writeInt(entry.getDependencies().size());
                for (ComponentDependency dependency : entry.getDependencies()) {
                    stream.writeUTF(dependency.getName());
                    stream.writeUTF(dependency.getVersion());
                    stream.writeInt(dependency.getCategory().ordinal());
                    stream.writeInt(dependency.getType().ordinal());
                    stream.writeUTF(dependency.getPath());
                }
            }
        } catch (FileNotFoundException e) {
            throw new DependenciesEntryPersistenceException(
                "FileNotFoundException occurs while creating file output stream for " + filePath + " : "
                    + e.getMessage(), e);
        } catch (IOException e) {
            throw new DependenciesEntryPersistenceException("IOException occurs while creating writing to file<"
                + filePath + "> : " + e.getMessage(), e);
        } finally {
            Utils.closeOutputStreamSafely(stream);
        }
    }
}
