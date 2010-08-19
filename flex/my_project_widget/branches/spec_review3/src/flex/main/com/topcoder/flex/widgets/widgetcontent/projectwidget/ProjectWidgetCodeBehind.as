/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.progress.ProgressWindowManager;
    import com.topcoder.flex.widgets.layout.Util;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.model.Model;
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.vo.Project;
    
    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.containers.HBox;
    import mx.core.Application;
    import mx.rpc.AbstractOperation;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.soap.SOAPHeader;
    import mx.rpc.soap.mxml.WebService;
    import mx.utils.ObjectUtil;

    /**
     * <p>
     * This is the code behind script part for the project widget. It implements the IWidget interface.
     * It extends from the HBox.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 2 [BUGR-1830]
     *    - Now Model instance is created in this class and other classes retrieves from it.
     * </p>
     * 
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
     * @author TCSDEVELOPER
     * @version 1.0
     * @since My Project Overhaul Assembly.
     */
    [Bindable]
    public class ProjectWidgetCodeBehind extends HBox implements IWidget {
        private static const WSSE_SECURITY:QName=new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");

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

        /**
         * Currently logged in username.
         */
        protected var username:String=Application.application.parameters.username;

        /**
         * Currently logged in user password.
         */
        protected var password:String="";

        /**
         * Indicates if refresh for my project is needed.
         * This gets set from external widget - mainly because some new contest has been created in launch widget.
         * Or some contest has been updated in launch widget.
         */
        public var _isRefreshNeeded:Boolean=false;

        /**
         * Reference to the contest service facade bean.
         */
        private var _ContestServiceFacadeBean:WebService;
        
        /**
         * Reference to the project service facade bean.
         */
        private var _ProjectServiceFacadeBean:WebService;

        /**
         * The project id only for which project contest data should be loaded.
         */
        private var _pid:String=null;

        /**
         * The configuration xml which contains the urls to for loading the data.
         */
        private var _config:XML=null;

        /**
         * True if contest is in max mode else false.
         */
        public var isMax:Boolean=false;

         /**
         * The allowclose flag.
         */
        private var _allowclose:Boolean=true;
        
        /**
         * The instance of data model
         * 
         * @since Cockpit Release Assembly 2 [BUGR-1830]
         */
        private var _model:Model=null;
        
        public var projectMap:Dictionary=new Dictionary();
        
        /**
         * ProjectWidgetCodeBehind constructor.
         * 
         * Updated for Cockpit Release Assembly 2 [BUGR-1830]
         *    - _model instance is instantiated.
         */
        public function ProjectWidgetCodeBehind() {
            this._model = new Model();
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
         * Gets the project id only for which project contest data should be loaded.
         */
        public function get pid():String {
            return this._pid;
        }
        /**
         * This action will reload the data for this widget.
         */
        public function reload():void {
            loadData();
        }

        /**
         * This action will show the user the configuration xml for this widget.
         */
        public function showConfiguration():void {
            
        }

        /**
         * Getter for the Contest service facade bean service reference.
         *
         * @return the Contest service facade bean reference.
         */
        public function get ContestServiceFacadeBean():WebService {
            return _ContestServiceFacadeBean;
        }

        /**
         * A simple setter for the contest service facade bean service.
         *
         * @param w the webservice to be set.
         */
        public function set ContestServiceFacadeBean(w:WebService):void {
            _ContestServiceFacadeBean=w;
        }
        
        /**
         * Getter for the Project service facade bean service reference.
         *
         * @return the Project service facade bean reference.
         */
        public function get ProjectServiceFacadeBean():WebService {
            return _ProjectServiceFacadeBean;
        }

        /**
         * A simple setter for the project service facade bean service.
         *
         * @param w the webservice to be set.
         */
        public function set ProjectServiceFacadeBean(w:WebService):void {
            _ProjectServiceFacadeBean=w;
        }

        /**
         * Loads the data from webservice.
         */
        public function loadData():void {
            showLoadingProgress();
            ProjectServiceFacadeBean.clearHeaders();
            ProjectServiceFacadeBean.addHeader(getHeader(username, password));
            var getAllProjectsOp:AbstractOperation=ProjectServiceFacadeBean.getOperation("getAllProjects");
            getAllProjectsOp.addEventListener("result", getAllProjectsHandler);
            getAllProjectsOp.send();
            
            
        }
        
        // TCCC-1023
        private function getAllProjectsHandler(e:ResultEvent):void {
            hideLoadingProgress();
            
            if (e && e.result) {
                var projects:ArrayCollection=null;
                
                if (e.result is ArrayCollection) {
                    projects=e.result as ArrayCollection;
                } else {
                    projects=new ArrayCollection();
                    projects.addItem(e.result);
                }
                
                model.proList=new ArrayCollection();
                for (var i:int=0; i < projects.length; i++) {
                    var prj:Object=projects[i];
                    var newProject:Project=new Project(prj.projectId,prj.name,prj.description,"","",null);
                    projectMap[newProject.id]=newProject;
                    model.proList.addItem(newProject);
                }       
                
                sortByName(model.proList); 
            
                showLoadingProgress();        
                if (pid) {
                    ContestServiceFacadeBean.clearHeaders();
                    ContestServiceFacadeBean.addHeader(getHeader(username, password));
                    ContestServiceFacadeBean.getCommonProjectContestDataByPID.send();
                } else {
                    if (this.container) {
                        this.container.startMaximize();
                    }
                    ContestServiceFacadeBean.clearHeaders();
                    ContestServiceFacadeBean.addHeader(getHeader(username, password));
                    ContestServiceFacadeBean.getCommonProjectContestData();
                }
            }
        }
        
        /**
         * Sorts the given collection by name
         *
         * @param coll specified array collection to be sorted.
         */
        public function sortByName(coll:ArrayCollection):void {
            if (!coll.sort) {
                var sort:Sort=new Sort();
                sort.compareFunction=compareName;
                coll.sort=sort;
            }

            coll.refresh();
        }
        
        /**
         * Callback function on sorting by name.
         */
        private function compareName(a:Object, b:Object, fields:Array=null):int {
            return ObjectUtil.stringCompare(a.name, b.name, true);
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
            isMax=false;
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
            this.percentWidth=100;
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
            this._config=config;
            if (_config && _config.pid) {
                _pid=Util.retrieveString(_config.pid);
            }
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
        	 
            return _config;
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
            return this._framework;
        }
        
        /**
         * Set the attributes for this widget based on the given Map.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
            if (map.hasOwnProperty("pid")) {
                this._pid=map["pid"];
                this._config=<widgetConfig/>
                this._config.pid=_pid;
            }

            if (map.hasOwnProperty("ContestUpdated") && map["ContestUpdated"] == true) {
                this._isRefreshNeeded=true;
            }

            if (map.hasOwnProperty("LayoutChange") && map["LayoutChange"] == "TAB_OPEN") {
                if (_isRefreshNeeded) {
                    this.reload();
                    this._isRefreshNeeded=false;
                }
            }
        }

        /**
         * Simple setter for the allowclose of this widget.
         *
         * @param allow the flag allowclose of this widget.
         */
        public function set allowclose(allow:Boolean):void {
            this._allowclose=allow;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the allowclose flag fo this widget. Could be null if not set.
         */
        public function get allowclose():Boolean {
            return this._allowclose;
        }

	    /**
         * Simple setter for the container of this widget.
         *
         * @param container of this widget.
         */
        public function set container(container:IWidgetContainer):void {
            this._container=container;
        }

        /**
         * Simple getter for the container of this widget.
         *
         * @return the container this widget. Could be null if not set.
         */
        public function get container():IWidgetContainer {
            if (_container == null) {
                this._container=parent as IWidgetContainer;
            }
            return this._container;
        }

        /**
         * Shows loading progress animation.
         */
        public function showLoadingProgress():void {
            if (this.container) {
                ProgressWindowManager.showProgressWindow(this.container);
            }
        }

        /**
         * Hides loading progress animation.
         */
        public function hideLoadingProgress():void {
            if (this.container) {
                ProgressWindowManager.hideProgressWindow(this.container);
            }
        }

        /**
         * Gets the webservice security header for given user name and password.
         *
         * @param username the user name.
         * @param password the password.
         */
        public function getHeader(username:String, password:String):SOAPHeader {
            var userToken:String="UsernameToken-" + Math.round(Math.random() * 999999).toString();
            var headerXML:XML=<wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                   <wsse:UsernameToken wsu:Id={userToken} xmlns:wsu='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd'>
                   <wsse:Username>{username}</wsse:Username>
                   <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest">{password}</wsse:Password>
                   </wsse:UsernameToken>
                 </wsse:Security>;
            var header:SOAPHeader=new SOAPHeader(WSSE_SECURITY, headerXML);
            return header;
        }
        
        /**
         * Gets the instance of data model class for this widget.
         * 
         * @return the instance of data model class for this widget.
         * 
         * @since Cockpit Release Assembly 2 [BUGR-1830] 
         */
        public function get model():Model {
            return this._model;
        }
        
        /**
         * Sets the instance of data model class for this widget.
         * 
         * @param m the instance of data model class for this widget.
         * 
         * @since Cockpit Release Assembly 2 [BUGR-1830] 
         */
        public function set model(m:Model):void {
            this._model=m;
        }
    }
}
