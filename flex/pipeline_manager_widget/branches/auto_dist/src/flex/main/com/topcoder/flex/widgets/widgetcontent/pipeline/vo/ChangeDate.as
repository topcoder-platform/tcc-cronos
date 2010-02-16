/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    /**
     * <p>
     * This is the data class that holds one data change data.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    [Bindable]
    public class ChangeDate {
        public function ChangeDate() {
            super();
        }
        
        public var init:Date;
        
        public var change:String;
        
        public var oldDate:Date;
        
        public var newDate:Date;
        
        public var times:int;
        
        public var name:String;
        
        public var manager:String;
        
        public var client:String;
        
        public var type:String;
    }
}