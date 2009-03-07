/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.com.ProjectsContainer;
    
    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    import mx.containers.Panel;
    import mx.rpc.soap.WebService;
    import mx.core.Application;
    
    // BUGR-1393
    import mx.managers.PopUpManager;
	import com.topcoder.flex.util.progress.ProgressWindow;
	import flash.display.DisplayObject;
    	
    /**
     * <p>
     * This is the code behind script part for the project widget. It implements the IWidget interface.
     * It extends from the Panel.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
     * @author TCSDEVELOPER
     * @version 1.0
     */
     public class ProjectWidgetCodeBehind extends Panel implements IWidget {
        /**
         * The name of the widget.
         */
        private var _name:String = "My Projects";
        
        
	/**
	 * The framework of the widget.
	 */
	private var _framework:IWidgetFramework = null;

	/**
	 * The container for this widget.
	 */
	private var _container:IWidgetContainer;
	
	 protected var username:String=Application.application.parameters.username;
	protected var password:String = "";
        
        /**
         * The data for the widget.
         */
        [Bindable] private var _result:XML = null;
        
        private var _projects:ArrayCollection;
        
        private var _prjList:ProjectsContainer;
        
        private var _ContestServiceFacadeBean:WebService; 
        
        private var _pid:String=null;
        
        
        
            
        
        
        private var isMax:Boolean=false;

         /**
         * The allowclose flag.
         */
        private var _allowclose:Boolean=true;
        
        // BUGR-1393
        private var p:ProgressWindow=null;
        
        /**
         * ProjectWidgetCodeBehind constructor.
         */
        public function ProjectWidgetCodeBehind() {
            super();            
        }
        /**
         * goBack is intended to act as a "Back Button" only for the context of
         * the single widget.
         *
         * <p>For example, I may have 5 widgets open on my page. One of those
         * widgets contains a wizard style interface. Suppose I'm on screen 2 out
         * of 4 screens in the wizard. The "Back" button in the widget would take
         * me back to the previous screen of the widget. Essentially, this acts
         * like a browser's "Back" button but it is specific to each widget.</p>
         */
        public function goBack():void {
            
        }
        /**
         * The active contents.
         */
        [Bindable]public function get result():XML {
            return this._result;
        }
        
        [Bindable]public function get pid():String {
            return this._pid;
        }
        /**
         * Sets the data for the widget.
         */
        public function set result(value:XML):void {
            this._result = value;
        }
        /**
         * This action will reload this widget.
         */
        public function reload():void {
            loadData();
            
        }

        /**
         * This action will show the user the configuration xml for this widget.
         */
        public function showConfiguration():void {
            
        }
        
        public function get ContestServiceFacadeBean():WebService
        {
        	return _ContestServiceFacadeBean;
        }
        
        public function set ContestServiceFacadeBean(w:WebService):void
        {
        	_ContestServiceFacadeBean=w;
        }
        
        public function loadData():void
        {
            showLoadingProgress();
        	if(pid)
        	{
        		ContestServiceFacadeBean.clearHeaders();
        	ContestServiceFacadeBean.addHeader(ProjectWidget.getHeader(username,password));
        	ContestServiceFacadeBean.getSimpleProjectContestDataByPID.send();	
        	}
        	else
        	{
			ContestServiceFacadeBean.clearHeaders();
        	ContestServiceFacadeBean.addHeader(ProjectWidget.getHeader(username,password));
        	ContestServiceFacadeBean.getSimpleProjectContestData();	
        	}
            
        }

        /**
         * This action signals that the widget should show the user help
         * information.
         */
        public function showHelp():void {
            
        }

        /**
         * This action will close this widget (i.e. most probably remove it
         * completely).
         */
        public function close():void {
            
        }

        /**
         * This action will minimize this widget.
         */
        public function minimize():void {
            
        }

        /**
         * This action will restpre this widget (for example from a menu bar).
         */
        public function restore():void {
            prjList.dataProvider= getSubArray(projects,10);
            isMax=false;
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
        	prjList.dataProvider=projects;
        	isMax=true;
        }

        /**
         * Set a specific attribute value for the given key.
         *
         * @param attributeKey the key for the attribute. Cannot be null.
         * @param value the value for the specified key. Cannot be null.
         *
         * @throws ArgumentError if either input is null.
         */
        public function setAttributeValue(attributeKey:String, value:Object):void {         
        }

        /**
         * Get a specific attribute value for the given key. If not found,
         * returns null.
         *
         * @param attributeKey the key for the attribute. Cannot be null.
         *
         * @return the value for this key, or null if there is no mapping.
         *
         * @throws ArgumentError if the input is null.
         */
        public function getAttributeValue(attributeKey:String):Object {
            return null;
        }

        /**
         * Set the configuration for this widget based on the given xml.
         *
         * @param config the xml configuration data for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function configure(config:XML):void {            
        }

        /**
         * This will tell the caller if the widget is in minimzie mode or not.
         *
         * @return true if the widget is minimized, false otherwise.
         */
        public function isMinimized():Boolean {
            return false;
        }

        /**
         * This will tell the caller if the widget is in maximize mode or not.
         *
         * @return true if the widget is maximized, false otherwise.
         */
        public function isMaximized():Boolean {
            return isMax;
        }

        /**
         * Get this widget's configuration xml data.
         *
         * @return the xml configuration data for this widget.
         */
        public function getConfiguration():XML {
            return null;
        }

        /**
         * Simple setter for the name of this widget.
         *
         * @param name the name of this widget.
         */
        override public function set name(name:String):void {
            this._name = name;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the current set name fo this widget. Could be null if not set.
         */
        override public function get name():String {
            return _name;
        }
        /**
         * Simple setter for the framework of this widget.
         *
         * @param framework the framework of this widget.
         */
        public function set widgetFramework(framework:IWidgetFramework):void {
        	this._framework = framework;
        }

        /**
         * Simple getter for the framework of this widget.
         *
         * @return the current set framework fo this widget. Could be null if not set.
         */
        public function get widgetFramework():IWidgetFramework {
        	return _framework;
        }
        public function get projects():ArrayCollection{
        	return _projects;
        }
        public function get prjList():ProjectsContainer{
        	return _prjList;
        }
        public function set projects(p:ArrayCollection):void
        {
        	_projects=p;
        }
        public function set prjList(p:ProjectsContainer):void
        {
        	_prjList=p;
        }
        
        public function getSubArray(a:ArrayCollection, n:int):ArrayCollection
        {
        	var ret:ArrayCollection=new ArrayCollection();
        	for(var i:int=0;i<a.length && i<n;i++)
        	{
        		ret.addItem(a.getItemAt(i));	
        	}
        	return ret;
        }
        
        /**
         * Set the attributes for this widget based on the given Map.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void
        {
        	if(map.hasOwnProperty("pid"))
        	{
        		_pid=map["pid"];
        	}
        }
        
        /**
         * Simple setter for the allowclose of this widget.
         *
         * @param allow the flag allowclose of this widget.
         */
        public function set allowclose(allow:Boolean):void
        {
        	_allowclose=allow;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the allowclose flag fo this widget. Could be null if not set.
         */
        public function get allowclose():Boolean
        {
        	return _allowclose;
        }

	/**
         * Simple setter for the container of this widget.
         *
         * @param container of this widget.
         */
        public function set container(container:IWidgetContainer):void
        {
        	_container=container;
        }

        /**
         * Simple getter for the container of this widget.
         *
         * @return the container this widget. Could be null if not set.
         */
        public function get container():IWidgetContainer
       {
        	if(_container==null)
        	{
        		_container=parent as IWidgetContainer;
        	}
        	return _container;
       }
       
       // BUGR-1393
        public function showLoadingProgress():void {
            if (p == null) {
    			p=ProgressWindow(PopUpManager.createPopUp((DisplayObject)(
													this.parentApplication),ProgressWindow, true));;
    	
                PopUpManager.centerPopUp(p);
            }
        }
        
        // BUGR-1393
        public function hideLoadingProgress():void {
            if(p)
			{
				PopUpManager.removePopUp(p);
        		p=null;
			}    
        }
    }
}
