/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    
    import flash.utils.Dictionary;
    
    import mx.containers.Panel;
    import mx.containers.VBox;
    import mx.containers.ViewStack;
    import mx.controls.Alert;
    import mx.rpc.soap.mxml.WebService;
	import com.topcoder.flex.util.progress.ProgressWindow;
	import com.topcoder.flex.util.progress.ProgressWindowManager;
	
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
     public class SubmissionViewerWidgetCodeBehind extends VBox implements IWidget { // BUGR-1142
        /**
         * The name of the widget.
         */
        private var _name:String = "Submission Viewer";

		private var _framework:IWidgetFramework = null;

		/**
		 * The container for this widget.
		 */
		private var _container:IWidgetContainer;
	
		private var _minPage:VBox;
        
        private var _main:ViewStack;
		
		public var _maximize:Function;
		
		public var _restore:Function;
		
		// Module Cockpit My Projects Release Assembly 1
        // 1.1.7
        // Submission Viewer widget should only have the current contest in the drop down.
        // to be selected contest id.
		[Bindable]public var toBeLoadedContestId:Number;
		
		// Module Cockpit My Projects Release Assembly 1
        // 1.1.7
        // Submission Viewer widget should only have the current contest in the drop down.
        // reference to ContestServiceFacade
        public var contestServiceFacade:WebService = null;
        
        private var p:ProgressWindow=null;
		 
        private var _pid:String=null;
        [Bindable]public function get pid():String {
            return this._pid;
        }
        public function set minPage(page:VBox):void
        {
        	_minPage=page;
        }
        
        public function set main(page:ViewStack):void
        {
        	_main=page;
        }
        
        public function get minPage():VBox
        {
        	return _minPage;
        }
        
        public function get main():ViewStack
        {
        	return _main;
        }

		/**
		 * The allowclose flag.
		 */
		private var _allowclose:Boolean=true;
        
        /**
         * The data for the widget.
         */
        [Bindable] private var _submissionData:String = null;
        
         /**
         * The data for the widget.
         */
        [Bindable] private var _contestData:String = null;
        
        /**
         * SubmissionViewerWidgetCodeBehind constructor.
         */
        public function SubmissionViewerWidgetCodeBehind() {
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
         * The submissions contents.
         */
        [Bindable]public function get submissionData():String {
            return this._submissionData;
        }
        /**
         * Sets the submission data for the widget.
         */
        public function set submissionData(value:String):void {
            this._submissionData = value;
        }
        
        /**
         * The contests contents.
         */
        [Bindable]public function get contestData():String {
            return this._contestData;
        }
        /**
         * Sets the contests data for the widget.
         */
        public function set contestData(value:String):void {
            this._contestData = value;
        }
        /**
         * This action will reload this widget.
         */
        public function reload():void {
            //trigger to load the data
            this._submissionData = new String(this._submissionData);
            this._contestData = new String(this._contestData);
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
			
            main.visible=false;
            main.includeInLayout=false;
            
            minPage.includeInLayout=true;
            minPage.visible=true;
			
			if (_restore) {
				_restore();
			}
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
        	main.visible=true;
            main.includeInLayout=true;
            
            minPage.includeInLayout=false;
            minPage.visible=false;
			
			if (_maximize) {
				_maximize();
			}
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
        
        /**
         * Set the attributes for this widget based on the given Map.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
        	
        	if(map["contestid"])
        	{
        		_defaultcontestid=map["contestid"];
        	}
        	if(map.hasOwnProperty("pid"))
        	{
        		_pid=map["pid"];
        	}
        	
        	// Module Cockpit My Projects Release Assembly 1
            // 1.1.7
            // Submission Viewer widget should only have the current contest in the drop down.
        	if(map.hasOwnProperty("toBeLoadedContestId"))
        	{
        		toBeLoadedContestId=map["toBeLoadedContestId"];
        	}
        	if(map.hasOwnProperty("reload"))
        	{
        	    showLoadingProgress();
        	    
        	    // reload the widget data.
        	    if (pid) {
    			    contestServiceFacade.getContestDataOnlyByPID(pid);
    			} 
    			else {					  
            		contestServiceFacade.getContestDataOnly();
    			}
        	}
        }
        
        /**
         * The default contest id.
         */
        private var _defaultcontestid:int = 0;
        
        public function get defaultcontestid():int
        {
        	return _defaultcontestid;
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
                //p = ProgressWindowManager.showProgressWindow(DisplayObjectContainer(this.container));
                p = ProgressWindowManager.showProgressWindow(this.container);
            }
        }
        
        // BUGR-1393
        public function hideLoadingProgress():void {
            if(p)
			{
				//ProgressWindowManager.hideProgressWindow(p);
        		ProgressWindowManager.hideProgressWindow(this.container, p);
        		p=null;
			}    
        }
    }
}
