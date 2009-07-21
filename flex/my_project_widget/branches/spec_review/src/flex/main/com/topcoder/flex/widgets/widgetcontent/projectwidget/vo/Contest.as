/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.vo {
    import com.topcoder.flex.util.date.DateUtil;

    /**
     * <p>
     * This is the DTO class to hold contest data.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since My Project Overhaul Assembly.
     */
    [Bindable]
    public class Contest {

        /**
         * Id of the contest.
         */
        public var id:String;

        /**
         * Name of the contest.
         */
        public var name:String;

        /**
         * Start date of the contest in string form.
         */
        public var startDate:String;

        /**
         * End date of the contest in string form.
         */
        public var endDate:String;

        /**
         * Number of registrants for the contest.
         */
        public var registrants:int;

        /**
         * Number of submissions to the contest.
         */
        public var submissions:int;

        /**
         * Number of forum posts to the contest.
         */
        public var forumPosts:int;

        /**
         * Id of the forum.
         */
        public var forumId:String

        /**
         * Status of the contest.
         */
        public var status:String;

        /**
         * Start date of the contest.
         */
        public var start:Date;

        /**
         * End date of the contest.
         */
        public var end:Date;

        /**
         * Date of the contest.
         */
        public var date:Date;

        /**
         * Type of the contest.
         */
        public var type:String;
        
        /**
        * It's type group for the contest.
        * Eg. Studio, Architecture etc.
        */
        public var typeGroup:String;

        /**
        * It's project name for the contest.
        */
        public var projName:String;
        
        /**
        * It's project id for the contest.
        */
        public var projId:String;
        
        public var permission:String;
        
        /**
         * Review status
         * 
         * For the contest hard-code it to Pending for now.
         * 
         * @since Cockpit Launch Contest Widget - Inline Spec Reviews - Part 1.
         */ 
        public var reviewStatus:String=ReviewStatus.PENDING;

        /**
         * A simple constructor which assigns parameters to member variables.
         *
         * @param _id id of the contest.
         * @param _name name of the contest.
         * @param _stDt start date of the contest.
         * @param _eDt end date of the contest.
         * @param _regs number of registrants to the contest.
         * @param _subs number of submissions to the contest.
         * @param _fPost number of forum posts to the contest.
         * @param _fId forum id of the contest
         * @param _sts status type of the contest.
         * @param _type type of the contest.
         */
        public function Contest(_pName:String, _pid:String,_id:String, _name:String, _stDt:String, _eDt:String, _regs:int, _subs:int, _fPost:int, _fId:String, _sts:String, _type:String) {
            this.projName=_pName;
            this.projId=_pid;
            this.id=_id;
            this.name=_name;

            this.registrants=_regs;
            this.submissions=_subs;
            this.forumPosts=_fPost;
            this.forumId=_fId;
            this.status=_sts;

            this.startDate=_stDt;
            this.endDate=_eDt;

            this.start=DateUtil.parseFromString(this.startDate);
            this.end=DateUtil.parseFromString(this.endDate);

            this.type=_type;
            this.typeGroup="Studio";
        }
    }
}
