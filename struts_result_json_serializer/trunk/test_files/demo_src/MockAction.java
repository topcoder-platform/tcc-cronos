package com.topcoder.service.actions.ajax;


import java.io.IOException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.service.actions.AggregateDataModel;

public class MockAction extends ActionSupport  {

    private Project project;

    private CustomFormatAJAXResult ajaxResult;

    private String message;

    public MockAction() {

    }
    public String execute() {
        if(ajaxResult == null) {
            ajaxResult = new CustomFormatAJAXResult();
        }

        if(project == null){
            project = new Project();
            project.setId(100);
            project.setDescription("project_description");
            project.setName("project_name");
        }
        MockActionInvocation invocation = new MockActionInvocation();
        MockAbstractAction action = new MockAbstractAction();

        AggregateDataModel dataModel = new AggregateDataModel();
        System.out.println("mockProject = null : " + (project == null));

        dataModel.setData("result", project);

        dataModel.setAction("tc");

        action.setModel(dataModel);

        invocation.setAction(action);

        try {
            ajaxResult.execute(invocation);
        } catch (AJAXDataPreProcessingException e) {
        } catch (AJAXDataSerializationException e) {
        } catch (AJAXDataPostProcessingException e) {
        } catch (IOException e) {
        }
        return "SUCCESS";
    }

    public void setProject(Project project){
        this.project = project;
    }

    public void setAjaxResult(CustomFormatAJAXResult ajaxResult){
        this.ajaxResult = ajaxResult;
    }

    public CustomFormatAJAXResult getAjaxResult(){
        return this.ajaxResult;
    }

    public Project getProject() {
        return this.project;
    }
}
