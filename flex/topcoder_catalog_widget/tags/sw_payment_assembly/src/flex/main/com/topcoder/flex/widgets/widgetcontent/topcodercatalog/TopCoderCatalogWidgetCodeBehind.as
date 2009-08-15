/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.topcodercatalog {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.progress.ProgressWindowManager;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.topcodercatalog.model.Model;
    
    import flash.utils.Dictionary;
    
    import mx.containers.Canvas;

    /**
     * <p>
     * This is the code behind mxml widget component for the topcoder catalog widget.
     * It implements the IWidget interface. It extends from the Canvas.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since Cockpit Catalog Widget Integration
     */
    [Bindable]
    public class TopCoderCatalogWidgetCodeBehind extends Canvas implements IWidget {
        /**
         * The name of the widget.
         */
        private var _name:String="TopCoder Catalog Widget";

        /**
         * The reference to IWidgetFramework.
         */
        private var _widgetFramework:IWidgetFramework=null;

        /**
         * The container for this widget.
         */
        private var _container:IWidgetContainer;

        /**
         * The allowclose flag.
         */
        private var _allowclose:Boolean=true;

        /**
         * Instance of the data model class for this widget.
         */
        private var _model:Model=Model.instance;
        
        /**
         * Widget refresh callback function.
         * 
         * @since 1.0.1
         */ 
        private var _refreshCallbackFn:Function=null;

        /**
         * A default empty constructor.
         */
        public function TopCoderCatalogWidgetCodeBehind() {
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
            return this._name;
        }

        /**
         * Simple setter for the framework of this widget.
         *
         * @param framework the framework of this widget.
         */
        public function set widgetFramework(framework:IWidgetFramework):void {
            this._widgetFramework=framework;
        }

        /**
         * Simple getter for the framework of this widget.
         *
         * @return the current set framework fo this widget. Could be null if not set.
         */
        public function get widgetFramework():IWidgetFramework {
            return this._widgetFramework;
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
            return this._container;
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
         * Set the attributes for this widget based on the given Map.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
        }

        /**
         * This action will reload this widget.
         * 
         * Updated for Version 1.0.1
         *    - on refresh button click refresh callback function is executed.
         */
        public function reload():void {
            if (refreshCallbackFn != null) {
                refreshCallbackFn();
            }
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
         * Set the configuration for this widget based on the given xml.
         *
         * @param config the xml configuration data for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function configure(config:XML):void {
        }

        /**
         * Shows the loading progress window for this widget.
         *
         * Loading progress window is a modal window that hides the widget through transparent layer
         * and it keeps showing animation.
         *
         * Loading progress window gets cancelled after some time automatically.
         */
        public function showLoadingProgress():void {
            if (this.container) {
                ProgressWindowManager.showProgressWindow(this.container);
            }
        }

        /**
         * Hides the loading progress window for this widget.
         *
         * Loading progress window is a modal window that hides the widget through transparent layer
         * and it keeps showing animation.
         *
         * Loading progress window gets cancelled after some time automatically.
         */
        public function hideLoadingProgress():void {
            if (this.container) {
                ProgressWindowManager.hideProgressWindow(this.container);
            }
        }

        /**
         * Gets the password.
         *
         * @return the password.
         */
        public function get model():Model {
            return this._model;
        }

        /**
         * Sets the password.
         *
         * @param m the password.
         */
        public function set model(m:Model):void {
            this._model=m;
        }
        
        /**
         * Gets the refresh callback function.
         *
         * @return the callback function.
         * @since 1.0.1
         */
        public function get refreshCallbackFn():Function {
            return this._refreshCallbackFn;
        }

        /**
         * Sets the refresh callback function.
         *
         * @param f the callback function.
         * @since 1.0.1
         */
        public function set refreshCallbackFn(f:Function):void {
            this._refreshCallbackFn=f;
        }
    }
}
