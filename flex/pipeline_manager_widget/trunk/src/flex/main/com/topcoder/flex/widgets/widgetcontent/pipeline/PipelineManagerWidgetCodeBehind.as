/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.progress.ProgressWindowManager;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.log.TCLog;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.model.Model;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.webservice.DateSearchCriteria;
    
    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    import mx.containers.HBox;
    import mx.containers.Panel;
    import mx.controls.DataGrid;
    import mx.core.Application;
    import mx.rpc.AbstractOperation;
    import mx.rpc.soap.SOAPHeader;
    import mx.rpc.soap.WebService;
    
    
    /**
     * <p>
     * This is the code behind script part for the Pipeline widget. It implements the IWidget interface.
     * It extends from the Panel.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
     * <p>
     * Version 1.0.1(Cockpit Pipeline Manager Widget Release Assembly V1.0) Change Notes:
     * -implements the reload functionality.
     * </p>
     * @author snow01
     * @version 1.0.1
     */
    [Bindable]
    public class PipelineManagerWidgetCodeBehind extends HBox implements IWidget {
        /**
         * The name of the widget.
         */
        private var _name:String="PipelineManager";
        
        /**
         * The framework of the widget.
         */
        private var _framework:IWidgetFramework=null;
        
        /**
         * The container for this widget.
         */
        private var _container:IWidgetContainer;
        
        private var _maximized:Boolean=false;
        
        /**
         * The allowclose flag.
         */
        private var _allowclose:Boolean=true;
        
        /**
         * Pipeline service facade
         */ 
        public var pws:WebService;
        
        /**
         * If we are doing local (IDE) testing.
         */
        public var isLocalTesting:Boolean=!Application.application.parameters.username ? true : false;
        
        /**
         * User name for webservice calls.
         */
        public var username:String=Application.application.parameters.username;
            
        /**
         * Password for webservice calls.
         */    
        public var password:String="";
        
        /**
         * Data Model class for this widget. 
         */
        public var model:Model;
        
        /**
         * WSSE Security constant.
         */
        private static const WSSE_SECURITY:QName=new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
        
        /**
         * PipelineWidgetCodeBehind constructor.
         */
        public function PipelineManagerWidgetCodeBehind() {
            super();
            model = Model.getInstance(this.uid);
            model.widgetFramework=this.widgetFramework;
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
         * This action will reload this widget.
         */
        public function reload():void {
        	if (!model.startDate || !model.endDate) {
                model.startDate = model.generateSundayTime(new Date());
            	model.endDate = new Date(model.startDate.time + 4 * 7 * 24 * 3600 * 1000);
            }
        	loadContests(model.startDate, model.endDate, false);
        }
        
        /**
         * This action will show the user the configuration xml for this widget.
         */
        public function showConfiguration():void {
        
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
            _maximized=false;
        }
        
        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
            this.percentWidth=100;
            _maximized=true;
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
            return false;
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
            this._name=name;
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
            this._framework=framework;
        }
        
        /**
         * Simple getter for the framework of this widget.
         *
         * @return the current set framework fo this widget. Could be null if not set.
         */
        public function get widgetFramework():IWidgetFramework {
            return _framework;
        }
        
        /**
         * Set the attributes for this widget based on the given Map.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
        }
        
        /**
         * Simple setter for the allowclose of this widget.
         *
         * @param allow the flag allowclose of this widget.
         */
        public function set allowclose(allow:Boolean):void {
            _allowclose=allow;
        }
        
        /**
         * Simple getter for the name of this widget.
         *
         * @return the allowclose flag fo this widget. Could be null if not set.
         */
        public function get allowclose():Boolean {
            return _allowclose;
        }
        
        /**
         * Simple setter for the container of this widget.
         *
         * @param container of this widget.
         */
        public function set container(container:IWidgetContainer):void {
            _container=container;
        }
        
        /**
         * Simple getter for the container of this widget.
         *
         * @return the container this widget. Could be null if not set.
         */
        public function get container():IWidgetContainer {
            if (_container == null) {
                _container=parent as IWidgetContainer;
            }
            return _container;
        }
        
        /**
         * Shows the loading progress bar.
         */ 
        public function showLoadingProgress():void {
            if (this.container) {
                ProgressWindowManager.showProgressWindow(this.container);
            }
        }
        
        /**
         * Hides the loading progress bar.
         */
        public function hideLoadingProgress():void {
            if (this.container) {
                ProgressWindowManager.hideProgressWindow(this.container);
            }
        }
        
        /**
         * Gets the webservice security header for specified user name and password.
         * 
         * @param username the username
         * @param password the password.
         * @return the soap header.
         */
        public function getHeader(username:String, password:String):SOAPHeader {
            var userToken:String = "UsernameToken-"+Math.round(Math.random()*999999).toString();
            var headerXML : XML =  <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
            <wsse:UsernameToken wsu:Id={userToken} xmlns:wsu='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd'>
            <wsse:Username>{username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest">{password}</wsse:Password>
            </wsse:UsernameToken>
            </wsse:Security>;
            
            var header : SOAPHeader = new SOAPHeader( WSSE_SECURITY, headerXML );
            return header;
        }
         
        /**
         * Loads the data from webservice.
         */
        public function loadData():void {
            if (this.container) {
                this.container.startMaximize();
            }
            
            // calculate the default start and end date.
            model.startDate = model.generateSundayTime(new Date());
            model.endDate = new Date(model.startDate.time + 4 * 7 * 24 * 3600 * 1000);
            
            TCLog.instance.debug("Model.startDate: " + model.startDate);
            TCLog.instance.debug("Model.endDate: " + model.endDate);
            loadContests(model.startDate, model.endDate, false);
        }
        
        /**
         * Loads the contests from the backend webservice for specified startDate and endDate critiera.
         * 
         * @param startDate the start date critiera.
         * @param endDate the end date criteria.
         * @param overdue if to load overdue contests.
         */ 
        public function loadContests(startDate:Date, endDate:Date, overdue:Boolean):void {
        	showLoadingProgress();
            
            this.pws.clearHeaders();
            this.pws.addHeader(getHeader(username, password));
            
            var getCommonPipelineData:AbstractOperation=this.pws.getOperation("getCommonPipelineData");
            getCommonPipelineData.send(startDate, endDate, overdue);
        }
        
        /**
         * Loads the prize change history for specified contest ids and their types.
         * On success specified handler is called.
         * 
         * @param contestIds the start date critiera.
         * @param contestTypes the type of contests.
         * @param handler the result event handler.
         */
        public function loadPrizeChangeHistory(contestIds:ArrayCollection, contestTypes:ArrayCollection, handler:Function):void {
        	showLoadingProgress();
            
            TCLog.instance.debug("username: " + username);
            TCLog.instance.debug("password: " + password);
            TCLog.instance.debug("pws: " + this.pws.wsdl);
            
            this.pws.clearHeaders();
            this.pws.addHeader(getHeader(username, password));
            
            var getContestPrizeChangeHistories:AbstractOperation=this.pws.getOperation("getContestPrizeChangeHistories");
            getContestPrizeChangeHistories.addEventListener("result", handler);
            getContestPrizeChangeHistories.send(contestIds, contestTypes);
        }
        
        /**
         * Loads the date change history for specified contest ids and their types.
         * On success specified handler is called.
         * 
         * @param contestIds the start date critiera.
         * @param contestTypes the type of contests.
         * @param handler the result event handler.
         */
        public function loadDateChangeHistory(contestIds:ArrayCollection, contestTypes:ArrayCollection, handler:Function):void {
        	showLoadingProgress();
            
            TCLog.instance.debug("username: " + username);
            TCLog.instance.debug("password: " + password);
            TCLog.instance.debug("pws: " + this.pws.wsdl);
            
            this.pws.clearHeaders();
            this.pws.addHeader(getHeader(username, password));
            
            var getContestDateChangeHistories:AbstractOperation=this.pws.getOperation("getContestDateChangeHistories");
            getContestDateChangeHistories.addEventListener("result", handler);
            getContestDateChangeHistories.send(contestIds, contestTypes);
        }
    }
}
