/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.vo {
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.model.Model;
    
    import flash.utils.Dictionary;
    
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
        
        public var statusGroup:Dictionary=new Dictionary();
        
        public var typeGroup:Dictionary=new Dictionary();
        
        public var permission:String;
        
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
            
            if (!this.contests) {
                this.contests=new ArrayCollection();        
            }
            
            contests.filterFunction=Model.filterDate;
            
            for each (var c:Contest in this.contests) {
                var a1:Cate=this.statusGroup[c.status] as Cate;
                if (!a1) {
                    a1=new Cate();
                    a1.label=c.status;
                    a1.contests=new ArrayCollection();
                    this.statusGroup[c.status]=a1;
                }
                
                a1.contests.addItem(c);
                
                var a2:Cate=this.typeGroup[c.type] as Cate;
                if (!a2) {
                    a2=new Cate();
                    a2.label=c.type;
                    a2.contests=new ArrayCollection();
                    this.typeGroup[c.type]=a2;
                }
                
                a2.contests.addItem(c);
            }
        }
        
        public function addContest(c:Contest):void {
            this.contests.addItem(c);
            
            var a1:Cate=this.statusGroup[c.status] as Cate;
            if (!a1) {
                a1=new Cate();
                a1.label=c.status;
                a1.contests=new ArrayCollection();
                this.statusGroup[c.status]=a1;
            }
            
            a1.contests.addItem(c);
            
            var a2:Cate=this.typeGroup[c.type] as Cate;
            if (!a2) {
                a2=new Cate();
                a2.label=c.type;
                a2.contests=new ArrayCollection();
                this.typeGroup[c.type]=a2;
            }
            
            a2.contests.addItem(c);
        }
    }
}
