/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.vo {
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.model.Model;

    import mx.collections.ArrayCollection;

    /**
     * <p>
     * This is the DTO class to hold project data.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since My Project Overhaul Assembly.
     */
    [Bindable]
    public class Project {

        /**
         * Id of the project.
         */
        public var id:String;

        /**
         * Name of the project.
         */
        public var name:String;

        /**
         * Owner of the project.
         */
        public var owner:String;

        /**
         * Description of the project.
         */
        public var description:String;

        /**
         * Status of the project.
         */
        public var status:String;

        /**
         * Users for this project.
         */
        public var users:ArrayCollection;

        /**
         * Contests for this project.
         */
        public var contests:ArrayCollection;

        /**
         * Start date of the project.
         */
        public var start:Date=null;

        /**
         * End date of the project.
         */
        public var end:Date=null;

        /**
         * Start date filter that is applied on this project contests.
         */
        public var startDateFilter:String="";

        /**
         * End date filter that is applied on this project contests.
         */
        public var endDateFilter:String="";

        /**
         * A simple constructor that sets the member variables from the parameters.
         *
         * @param _id id of the project.
         * @param _name of the project.
         * @param _descr description of the project.
         * @param _status status of the project.
         * @param _owner create owner of the project.
         * @param _contests contests for the project.
         */
        public function Project(_id:String, _name:String, _descr:String, _status:String, _owner:String, _contests:ArrayCollection=null) {
            id=_id;
            this.name=_name;
            this.description=_descr;
            this.status=_status;
            this.owner=_owner;
            this.contests=_contests;
            contests.filterFunction=Model.filterDate;
        }
    }
}