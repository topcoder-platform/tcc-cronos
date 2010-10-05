/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline {
	import com.topcoder.flex.model.IWidgetFramework;
	import com.topcoder.flex.widgets.model.IWidget;
	import com.topcoder.flex.widgets.model.IWidgetContainer;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Panel;
	import mx.controls.DataGrid;


	/**
	 * <p>
	 * This is the code behind script part for the Pipeline widget. It implements the IWidget interface.
	 * It extends from the Panel.
	 * </p>
	 * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
	 * @author TCSDEVELOPER
	 * @version 1.0
	 */
	 public class PipelineWidgetCodeBehind extends Panel implements IWidget {
		/**
		 * The name of the widget.
		 */
		private var _name:String = "Pipeline";
		
		/**
		 * The framework of the widget.
		 */
		private var _framework:IWidgetFramework = null;

		/**
		 * The container for this widget.
		 */
		private var _container:IWidgetContainer;
		
		/**
		 * The data grid.
		 */		 
		public var dataGrid:DataGrid;

		/**
		 * The allowclose flag.
		 */
		private var _allowclose:Boolean=true;
		
		/**
		 * The data provider for the data grid.
		 */
		[Bindable] private var _activeContents:ArrayCollection = null;
		
		/**
		 * PipelineWidgetCodeBehind constructor.
		 */
		public function PipelineWidgetCodeBehind() {
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
		[Bindable]public function get activeContents():ArrayCollection {
			return this._activeContents;
		}
		/**
		 * Sets the data for the data grid.
		 */
		public function set activeContents(value:ArrayCollection):void {
			this._activeContents = value;
			this.dataGrid.dataProvider = this._activeContents;
		}
        /**
         * This action will reload this widget.
         */
        public function reload():void {
        	//trigger to load the data
        	var temp:ArrayCollection = new ArrayCollection(this._activeContents.source);
        	this._activeContents = temp;
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
        	
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
        	
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
        public function setAttributes(map:Dictionary):void
        {
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

	}
}