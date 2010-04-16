package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This purpose of this interceptor is to adapt the default validation workflow of Struts 2 to the validation
 * workflow of the Struts Framework TC component. It retrieves the validation errors of fields (also called
 * properties in Struts Framework) provided by the validation annotations and fills the ValidationErrors with
 * the errors messages. In this mode the component can completely re-use all validation annotations provided
 * by Struts 2
 * </p>
 * <p>
 * Thread safety: an Interceptor must be thread safe (from its javadoc: Interceptors <b>must</b> be stateless
 * and not assume that a new instance will be created for each request or Action.) . This class is stateless
 * and then it's thread safe.
 * </p>
 */
public class ValidationErrorsInterceptor extends AbstractInterceptor {

    /**
     * Gets the action from the actionInvocation parameter and returns it.
     * 
     * @param actionInvocation the action invocation
     * @return the action from the action invocation
     * @throws IllegalStateException if actionInvocation is null or the action within it is null
     */
    private static Object getAction(ActionInvocation actionInvocation) {
        if (actionInvocation == null) {
            throw new IllegalArgumentException("actionInvocation cannot be null");
        }

        // fetch the action
        Object obj = actionInvocation.getAction();
        if (obj == null || !(obj instanceof AbstractAction)) {
            throw new IllegalArgumentException("the action within actionInvocation cannot be null and it must "
                + "be instance of AbstractAction");
        }

        return obj;
    }

    /**
     * <p>
     * Default constructor it does nothing.
     * </p>
     */
    public ValidationErrorsInterceptor() {
    }

    /**
     * <p>
     * Intercept the action after its validation and add the ValidationErrors instanced constructed from the
     * field errors in the ActionSupport.
     * </p>
     * <p>
     * Impl:
     * </p>
     * <ol>
     * <li>invoke the action invocation (we perform the logic after the action invocation, then after the
     * field errors are filled) and save the result to a String instance</li>
     * <li>create the ValidationErrors instance</li>
     * <li>get the field errors from the action (cast it to AsbtractAction)</li>
     * <li>if there are no errors then skip the follow steps and return directly the result of action
     * invocation</li>
     * <li>for each field which has errors create a ValidationErrorRecord, set the property name as the field
     * name and set to it the error messages of the field</li>
     * <li>set these ValidationErrorRecord instances to ValidationErrors</li>
     * <li>return the result of previous action invocation</li>
     * </ol>
     * 
     * @throws IllegalArgumentException if the actionInvocation is null, if the action in the actionInvocation
     *             is not an AbstractAction
     * @throws Exception the exception thrown by the actionInvocation.invoke() method
     * @param actionInvocation the action invocation which contains the action invocation information, can't
     *            be null
     * @param return the result of the action invocation
     * @return the result of the action invocation
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ExceptionUtils.checkNull(actionInvocation, null, null, "actionInvocation cannot be null");

        AbstractAction action = (AbstractAction) getAction(actionInvocation);
        action.clearFieldErrors();

        try {
            actionInvocation.invoke();
        } catch (Exception e) {
            action.getModel().setData("return", e);
            return ActionSupport.SUCCESS;
        }

        Map<String, List<String>> fieldErrors = action.getFieldErrors();
        if (fieldErrors.isEmpty()) {            
            return ActionSupport.SUCCESS;
        }

        ValidationErrors validationErrors = new ValidationErrors();
        List<ValidationErrorRecord> errorRecords = new ArrayList<ValidationErrorRecord>();
        for (String fieldName : fieldErrors.keySet()) {
            ValidationErrorRecord record = new ValidationErrorRecord();
            record.setPropertyName(fieldName);
            List<String> errors = fieldErrors.get(fieldName);
            record.setMessages(errors.toArray(new String[errors.size()]));
            errorRecords.add(record);
        }
        validationErrors.setErrors(errorRecords.toArray(new ValidationErrorRecord[errorRecords.size()]));
        action.getModel().setData("return", validationErrors);

        return ActionSupport.SUCCESS;
    }
}
