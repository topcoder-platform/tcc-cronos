/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.model {
    import com.topcoder.flex.Helper;
    import com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.vo.User;

    import flash.events.Event;
    import flash.net.URLLoader;
    import flash.net.URLLoaderDataFormat;
    import flash.net.URLRequest;

    import mx.collections.ArrayCollection;

    /**
     * <p>
     * This is the data model class for storing underlying data for this widget.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since Cockpit Share Submission Integration
     */
    [Bindable]
    public class Model {

        /**
         * Singleton instance of this class.
         */
        private static var _instance:Model;

        /**
         * Gets the singleton instance of this class.
         *
         * @return singleton instance of this class.
         */
        public static function get instance():Model {
            if (!_instance) {
                _instance=new Model();
            }
            return _instance;
        }

        /**
         * A default empty contructor.
         */
        public function Model() {
        }

        /**
         * Logged in user.
         */
        public var user:String;

        /**
         * Represents the index of currently visible view.
         */
        private var _selectedIndex:int=0;

        /**
         * Represents the previously selected view index.
         */
        public var lastIndex:int;

        /**
         * Represents the list of users.
         */
        public var userList:ArrayCollection;

        /**
         * Represents the currently shown user in user view.
         */
        public var currentUser:User;

        /**
         * Represents the index of sort column in Data Grid.
         */
        public var sortIndex:int=-1;

        /**
         * Represents the search keyword.
         */
        public var keyWords:String="";

        /**
         * Represents whether search keyword highlight to be done or not.
         */
        public var highLight:Boolean=false;

        /**
         * Bound variable used to trigger refresh of user list.
         * For every required refresh, it is simply toggled.
         */
        public var refresh:Boolean=false;

        /**
         * Bound variable used to trigger the refresh of user tree.
         * For every required refresh, it is simply toggled.
         */
        public var refreshTree:Boolean=false;

        /**
         * True if full tree need a refresh otherwise false.
         */
        public var refreshFullTree:Boolean=false;

        /**
         * Gets the index of selected view.
         *
         * @return the index of selected view.
         */
        public function get selectedIndex():int {
            return _selectedIndex;
        }

        /**
         * Sets the index of view to be selected
         *
         * It preserves the last view index in 'lastIndex' variable.
         *
         * @param value the index of view to be selected.
         */
        public function set selectedIndex(value:int):void {
            lastIndex=_selectedIndex;
            _selectedIndex=value;
            highLight=false;
        }

        /**
         * Filters the users by given user object.
         *
         * @param obj user object.
         */
        public function doFilter(obj:Object):Boolean {
            if (keyWords.length <= 0) {
                return true;
            }
            var user:User=obj as User;
            if (user && user.name) {
                if (user.name.toLowerCase().indexOf(keyWords.toLowerCase()) >= 0) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Clears the search.
         */
        public function clearSearch():void {
            if (userList) {
                userList.refresh();
                refresh=!refresh;
                selectedIndex=0;
            }
        }

        /**
         * Does the search.
         */
        public function doSearch():void {
            if (userList && userList.length>0) {
                userList.refresh();
                refresh=!refresh;
                selectedIndex=1;
            } else {
                Helper.showAlertMessage("No users to search for!");
            }
        }
    }
}
