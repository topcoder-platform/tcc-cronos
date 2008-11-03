/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.messagegenerators;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

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
import com.topcoder.util.file.fieldconfig.Condition;
import com.topcoder.util.file.fieldconfig.Loop;


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
public class DefaultEmailMessageGenerator implements EmailMessageGenerator, Serializable {

	/**
	 * Used to format date fields
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("EEE d-MMM-yy HH:mm");
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
            TemplateFields fields = template.getFields();
            for (Node node : fields.getNodes()) {
                if (node instanceof Field) {
                    process((Field) node, phase);
                } else if (node instanceof Condition) {
                    process((Condition) node, phase);
                } else if (node instanceof Loop) {
                    process((Loop) node, phase);
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

    /**
     * <p>Fills the specified template field with data taken from the contest corresponding to specified phase.</p>
     *
     * @param field a <code>Field</code> providing the template field which has to be filled with data from the
     *        specified phase.
     * @param phase a <code>Phase</code> referencing the contest and status which the message is generated for.
     * @throws EmailMessageGenerationException If any error occurs while generating the message (also if some template
     *         data does not exist in either the phase or project attributes, or if it is not <code>Long</code>,
     *         <code>Date</code> or <code>String</code>).
     */
    private void process(Field field, Phase phase) throws EmailMessageGenerationException {
        //Only apply the data if Field is not readOnly
        if (!field.isReadOnly()) {
            //Use field name as the key
            String key = field.getName();
            Serializable value = getValue(phase, key);

            //Must be type of Long, Date or String
            if (value instanceof Number || value instanceof String) {
                field.setValue(value.toString());
            } else if (value instanceof Date) {                        	
            	field.setValue(sdf.format(value));
            } else {
                throw new EmailMessageGenerationException("Template data [" + key
                    + "] type is neither Long, Date nor String: " + value.getClass());
            }
        }
    }

    /**
     * <p>Gets the value matching the specified key from the specified phase.</p>
     *
     * @param phase a <code>Phase</code> referencing the contest and status which the message is generated for.
     * @param key a <code>String</code> providing the key to get the matching value for.
     * @return a <code>Serializable</code> providing the value matching the specified key. 
     * @throws EmailMessageGenerationException If any error occurs while generating the message (also if some template
     *         data does not exist in either the phase or project attributes, or if it is not <code>Long</code>,
     *         <code>Date</code> or <code>String</code>).
     */
    private Serializable getValue(Phase phase, String key) throws EmailMessageGenerationException {
        //Get the value of the node from phase.attributes
        //If null was returned get the value of the node from phase.project.attributes
        Serializable value = phase.getAttribute(key);
        if (value == null && phase.getProject() != null) {
            value = phase.getProject().getAttribute(key);
        }

        //Neither exist in phase nor project attributes
        if (value == null) {
            throw new EmailMessageGenerationException("Template data [" + key
                + "] does not exist in either the phase or project attributes.");
        }
        return value;
    }

    /**
     * <p>Process the specified template condition by filling the fields included to condition with data taken from the
     * contest corresponding to specified phase.</p>
     *
     * @param condition a <code>Condition</code> representing the condition in template which is to be processed.
     * @param phase a <code>Phase</code> referencing the contest and status which the message is generated for.
     * @throws EmailMessageGenerationException If any error occurs while generating the message (also if some template
     *         data does not exist in either the phase or project attributes, or if it is not <code>Long</code>,
     *         <code>Date</code> or <code>String</code>).
     */
    private void process(Condition condition, Phase phase) throws EmailMessageGenerationException {
        condition.setValue(String.valueOf(getValue(phase, condition.getName())));
        Node[] nodes = condition.getSubNodes().getNodes();
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (node instanceof Field) {
                process((Field) node, phase);
            } else if (node instanceof Condition) {
                process((Condition) node, phase);
            } else if (node instanceof Loop) {
                process((Loop) node, phase);
            }
        }
    }

    /**
     * <p>Process the specified template loop by filling the fields included to loop with data taken from the contest
     * corresponding to specified phase.</p>
     *
     * @param loop a <code>Loop</code> representing the loop in template which is to be processed.
     * @param phase a <code>Phase</code> referencing the contest and status which the message is generated for.
     * @throws EmailMessageGenerationException If any error occurs while generating the message (also if some template
     *         data does not exist in either the phase or project attributes, or if it is not <code>Long</code>,
     *         <code>Date</code> or <code>String</code>).
     */
    private void process(Loop loop, Phase phase) throws EmailMessageGenerationException {
        Node[] nodes = loop.getSampleLoopItem().getNodes();
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            if (node instanceof Field) {
                process((Field) node, phase);
            } else if (node instanceof Condition) {
                process((Condition) node, phase);
            } else if (node instanceof Loop) {
                process((Loop) node, phase);
            }
        }
    }
}
