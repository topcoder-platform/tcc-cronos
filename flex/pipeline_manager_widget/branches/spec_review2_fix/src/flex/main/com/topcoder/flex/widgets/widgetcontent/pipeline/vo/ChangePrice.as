/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    /**
     * <p>
     * This is the data class that one price change data.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class ChangePrice {
        public function ChangePrice() {
            super();
        }
        
        public var init:int;
        
        public var change:String;
        
        public var oldPrice:int;
        
        public var newPrice:int;
        
        public var times:int;
        
        public var name:String;
        
        public var manager:String;
        
        public var client:String;
        
        public var type:String;
    
    }
}