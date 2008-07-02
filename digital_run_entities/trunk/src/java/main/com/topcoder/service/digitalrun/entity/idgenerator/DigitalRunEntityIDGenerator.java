/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.idgenerator;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

import com.topcoder.service.digitalrun.entity.Helper;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * <code>DigitalRunEntityIDGenerator</code> is used to generate the id for all the entities defined in this
 * component.
 * </p>
 *
 * <p>
 * This class implements the <code>Configurable</code> interface to allow application users to configure
 * different id generator names for different entities.
 * </p>
 *
 * <p>
 * Assume that we configure the id generator for DigitalRunPoints entity, the id configuration element should be:
 *
 * <pre>
 * &lt;id name=&quot;id&quot; column=&quot;dr_points_type_id&quot; type=&quot;long&quot;&gt;
 *  &lt;generator class=&quot;com.topcoder.service.digitalrun.entity.idgenerator.DigitalRunEntityIDGenerator&quot;&gt;
 *      &lt;param name=&quot;id_generator_name&quot;&gt;DigitalRunPoints_IDGenerator&lt;/param&gt;
 *  &lt;/generator&gt;
 * &lt;/id&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is mutable and not thread safe. However, generate method is thread safe as it does not
 * modify any inner status, and can be called by multi-threads.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunEntityIDGenerator implements Configurable, IdentifierGenerator {

    /**
     * <p>
     * The id generator name initialized in the configure method, and used in generate method to get the named id
     * generator from the IDGeneratorFactory. It should be non-null and non-empty string after set.
     * </p>
     */
    private String idGeneratorName;

    /**
     * Create the instance.
     */
    public DigitalRunEntityIDGenerator() {
        // empty
    }

    /**
     * <p>
     * Configures the generator. This method always assume that the params properties should have a key named
     * 'id_generator_name', and the value is the non-null and non-empty string indicating the id generator name
     * value
     * </p>
     *
     * <p>
     * The &quot;DigitalRunEntityIDGenerator_IDGenerator&quot; will be loaded and set on the class variable.
     * </p>
     *
     * @param dialect
     *            the sql dialect, not used
     * @param params
     *            the params properties
     * @param type
     *            the hibernate type, not used
     * @throws IllegalArgumentException
     *             if params is <code>null</code>, it misses the required key, or the retrieved generator name
     *             is <code>null</code>, or empty string.
     */
    public void configure(Type type, Properties params, Dialect dialect) {
        Helper.checkNull(params, "params");
        try {
            String val = (String) params.get("id_generator_name");
            Helper.checkString(val, "params#id_generator_name");

            this.idGeneratorName = val;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The 'id_generator_name' is not a string.", e);
        }
    }

    /**
     * <p>
     * Generate the id for the entities. The returned type is <code>java.lang.Long</code>.
     * </p>
     *
     * @param session
     *            the session implementor, not used
     * @param obj
     *            the object parameter, not used.
     * @return the generated id, it is of <code>Long</code> type
     * @throws HibernateException
     *             if failed to get the id generator or failed to generate the id.
     */
    public Serializable generate(SessionImplementor session, Object obj) {
        try {
            // get the idgenerator from the name and return the next id
            IDGenerator gen = IDGeneratorFactory.getIDGenerator(idGeneratorName);
            return new Long(gen.getNextID());
        } catch (IDGenerationException e) {
            throw new HibernateException("Failed to get the id generator.", e);
        }
    }
}
