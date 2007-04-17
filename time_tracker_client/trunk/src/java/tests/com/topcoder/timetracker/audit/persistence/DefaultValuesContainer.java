
package com.topcoder.timetracker.audit.persistence;
import com.topcoder.timetracker.audit.AuditConfigurationException;

/**
 * This package-level container is provided to allow easy storage of default values for Audit Detail records. At first, it extends the AuditDetail object, defaulting all values to null/-1 as shown on the class diagram. Then, for each member in configuration, the new default value is loaded, and the appropriate setXXX is called. Afterwards, whenever an AuditDetail is loaded from persistence, if any records are null the corresponding setXXX(defaultValues#getXXX()) can be called to replace it with the desired default value.
 * Similar to AuditDetail, for efficiency this is not threadsafe (does not forbid simultaneous read/writes of members) so it is assumed that application will use it in a threadsafe manner if that is required.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm782f]
 */
class DefaultValuesContainer extends com.topcoder.timetracker.audit.AuditDetail {

/**
 * Initializes default values based on configuration parameters. Each member is checked for a configuration parameter of the same name. If one exists, then the config value is read, converted to the right type if required: parseLong(str) for IDs. Then the relevant setXXX is called with the new value to use. If the values do not exist, or are invalid, then simply no getXXX is called, and AuditDetail's default value for that member remains instead. The usage can be seen in the Construction Sequence Diagram.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7835]
 * @param namespace Namespace within configuration to load default values from.
 * @throws IllegalArgumentException if the parameter is null or empty
 * @throws AuditConfigurationException is there are problems initialising from configuration
 */
     DefaultValuesContainer(String namespace) {        
        // your code here
    } 
 }
