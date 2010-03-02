/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
	import mx.collections.ArrayCollection;
	
    
    /**
     * <p>
     * This is the data class that defines a filter criteria
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * <p>
     * Version 1.0.1(Cockpit Pipeline Manager Widget Release Assembly V1.0) Change Notes:
     * - add new filter : excluded status
     * </p>
     * @author snow01
     * @version 1.0.1
     */
    [Bindable]
    public class FilterCriteria {
        public function FilterCriteria() {
            excludeClients=[];
            numFilter=[];
        }
        
        public var type:String;
        
        public var typeLabel:String;
        
        public var start:Date;
        
        public var end:Date;
        
        public var startAdd:Date;
        
        public var endAdd:Date;
        
        public var confidence:String;
        
        public var co:Array=[1, 1, 1];
        
        public var excludeClients:Array;
        
        public var numFilter:Array;
        
        public var ca:String;
        
        public var pa:String;
        
        public var ws:String;
        
        public var psr:String;
        
        public var dc:String;
        
        public var repost:String;
        
        public var rRole:String;
        
        public var rKey:String;
        
        public var client:String;
        
        public var project:String;
        
        public var name:String;
        
        public var status:String;
        
        public var category:String;
        
        public var resourceItem:String;
        
        public var resourceName:String;
        /**
         * @since 1.0.1
        */
        public var excludedStatus:ArrayCollection = new ArrayCollection([]);

        public var cpname:String;

    
    }
}
