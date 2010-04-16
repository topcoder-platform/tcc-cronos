package com.topcoder.service.actions;

import com.opensymphony.xwork2.Preparable;

/**
 * <p>
 * This is the base action of all actions in this component. It sets the AggregateDataModel to the model using
 * the prepare logic of struts framework (in the assembly/development the logic of this class could be
 * implemented directly in AbstractAction and delegate the logic of the action to the template method It also
 * manages the exceptions thrown by the template methods
 * </p>
 * <p>
 * Thread safe: in Struts 2 framework the action is constructed for every request so the thread safety is not
 * required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 */
public abstract class BaseDirectStrutsAction extends AbstractAction implements Preparable {
    
    /**
     * Represents the key to use for <code>return</code> in the model.
     */
    private final String MODEL_KEY_RETURN = "return";
    
    /**
     * <p>
     * Default constructor it does nothing.
     * </p>
     */
    protected BaseDirectStrutsAction() {
    }

    /**
     * <p>
     * Prepare the action before its logic. This method is called by its related interceptor before of the
     * setters/getters of parameters (params interceptor)
     * </p>
     * <p>
     * Impl: create and the set the model using the related setters of AbstractAction
     * </p>
     * 
     * @param return
     * @return
     */
    public void prepare() {
        setModel(new AggregateDataModel());
    }

    /**
     * <p>
     * It calls the executeAction to perform the logic of the action. If some error occurs then set the
     * exception with the same "return" key to the model. It returns ActionSupport.SUCCESS as described in the
     * forum
     * </p>
     * 
     * @param return return ActionSupport.SUCCESS
     * @return return ActionSupport.SUCCESS
     */
    public String execute() {
        try {
            executeAction();
        } catch (Exception e) {
            getModel().setData(MODEL_KEY_RETURN, e);
        }
        return SUCCESS;
    }

    /**
     * <p>
     * This is the template method where the action logic will be performed by children classes.
     * </p>
     * 
     * @throws Exception if some error occurs
     * @param return
     * @return
     */
    protected abstract void executeAction() throws Exception;

    /**
     * <p>
     * Set the result of the action to the aggregate model. Set the object to the model map using the "return"
     * key
     * </p>
     * 
     * @param result the result to set to the model
     * @param return
     * @return
     */
    public void setResult(Object result) {
        getModel().setData(MODEL_KEY_RETURN, result);
    }

    /**
     * <p>
     * Return the result from the aggregate model. Use the "return" key to return it (it can be null if it's
     * not present)
     * </p>
     * 
     * @param return the result from the model.
     * @return the result from the model.
     */
    public Object getResult() {
        return getModel().getData(MODEL_KEY_RETURN);
    }
}
