/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import com.opensymphony.xwork2.Preparable;

/**
 * <p>
 * This is the base action of all actions in this component.
 * </p>
 * <p>
 * It sets the <code>AggregateDataModel</code> to the model using the prepare logic of struts framework (in the
 * assembly/development the logic of this class could be implemented directly in <code>AbstractAction</code> and
 * delegate the logic of the action to the template method. It also manages the exceptions thrown by the template
 * methods.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public abstract class BaseDirectStrutsAction extends AbstractAction implements Preparable {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 3320745591427458154L;

    /**
     * <p>
     * Represents the result key to store result in the aggregate model.
     * </p>
     */
    private static final String RESULT_KEY = "result";

    /**
     * <p>
     * Creates a <code>BaseDirectStrutsAction</code> instance.
     * </p>
     */
    protected BaseDirectStrutsAction() {
    }

    /**
     * <p>
     * Prepare the action before its logic.
     * </p>
     * <p>
     * This method is used by its related interceptor before of the setters/getters of parameters (params interceptor).
     * </p>
     *
     * @throws Exception
     *             if any problem occurs.
     */
    public void prepare() throws Exception {
        setModel(new AggregateDataModel());
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * It uses <b>Template Method</b> Design Pattern to perform the logic of the concrete action. Each concrete action
     * should implement the {@link #executeAction()} method. If some error occurs then set the exception with the
     * {@link #RESULT_KEY} key to the model.
     * </p>
     *
     * @return <code>SUCCESS</code> always
     */
    @Override
    public String execute() {
        try {
            executeAction();
        } catch (Exception e) {
            setResult(e);
        }
        return SUCCESS;
    }

    /**
     * <p>
     * This is the template method where the action logic will be performed by children classes.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    protected abstract void executeAction() throws Exception;

    /**
     * <p>
     * Set the result of the action to the aggregate model.
     * </p>
     * <p>
     * the object will be under the {@link #RESULT_KEY} key in the model map.
     * </p>
     *
     * @param result
     *            the result to set to the model
     */
    public void setResult(Object result) {
        getModel().setData(RESULT_KEY, result);
    }

    /**
     * <p>
     * Gets the result from the aggregate model.
     * </p>
     * <p>
     * The result is under the {@link #RESULT_KEY} key in the model map (it can be null if it's not present).
     * </p>
     *
     * @return the result from the model.
     */
    public Object getResult() {
        return getModel().getData(RESULT_KEY);
    }
}
