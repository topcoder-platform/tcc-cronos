/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.messagegenerators;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.clientcockpit.phases.EmailMessageGenerationException;
import com.topcoder.clientcockpit.phases.EmailMessageGenerator;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.Node;
import com.topcoder.util.file.fieldconfig.TemplateFields;


/**
 * <p>
 * This class implements the <code>EmailMessageGenerator</code> interface and it is used to generate messages
 * using a template and a phase. It can handle <code>Long</code>, <code>Date</code> and <code>String</code>
 * objects as template data.
 * </p>
 *
 * <p>
 *    <strong>Thread Safety:</strong>
 *    This class is thread safe because it has no state.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultEmailMessageGenerator implements EmailMessageGenerator {

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public DefaultEmailMessageGenerator() {
        //empty
    }

    /**
     * <p>
     * This method will generate a message from the given template by using the phase to retrieve the template data.
     * </p>
     *
     * <p>
     * For each non-readOnly field within template, its name is used as the key to get the value from phase's attributes
     * (if not found, then find the phase's project's attributes).
     * </p>
     *
     * @param phase The phase.
     * @param template The template.
     *
     * @return generated message.
     *
     * @throws IllegalArgumentException If given template or phase is null.
     * @throws EmailMessageGenerationException If any error occurs while generating the message (also if some template
     *         data does not exist in either the phase or project attributes, or if it is not <code>Long</code>,
     *         <code>Date</code> or <code>String</code>).
     */
    public String generateMessage(Template template, Phase phase) throws EmailMessageGenerationException {
        ExceptionUtils.checkNull(phase, null, null, "Phase should not be null.");
        ExceptionUtils.checkNull(template, null, null, "Template should not be null.");
        try {
            //The XsltTemplate.getFields() never returns null
            TemplateFields fields = template.getFields();
            //The TemplateFields.getNodes() never returns null
            for (Node node : fields.getNodes()) {
                if (node instanceof Field) {
                    Field field = (Field) node;
                    //Only apply the data if Field is not readOnly
                    if (!field.isReadOnly()) {
                        //Use field name as the key
                        String key = field.getName();

                        //Get the value of the node from phase.attributes
                        Serializable value = phase.getAttribute(key);

                        //If null was returned get the value of the node from phase.project.attributes
                        if (value == null && phase.getProject() != null) {
                            value = phase.getProject().getAttribute(key);
                        }

                        //Neither exist in phase nor project attributes
                        if (value == null) {
                            throw new EmailMessageGenerationException("Template data [" + key
                                + "] does not exist in either the phase or project attributes.");
                        }

                        //Must be type of Long, Date or String
                        if (value instanceof Long || value instanceof Date || value instanceof String) {
                            field.setValue(value.toString());
                        } else {
                            throw new EmailMessageGenerationException("Template data [" + key
                                + "] type is neither Long, Date nor String: " + value.getClass());
                        }
                    }
                }
            }
            return DocumentGenerator.getInstance().applyTemplate(fields);
        } catch (BaseException e) {
            throw e instanceof EmailMessageGenerationException ? (EmailMessageGenerationException) e
                : new EmailMessageGenerationException("Error while generating email message body.", e);
        } catch (ConfigManagerException e) {
            throw new EmailMessageGenerationException("Error while generating email message body.", e);
        }
    }
}
