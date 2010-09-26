/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    /**
     * <p>
     * This is the data class that defines a numeric filter.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class NumbericFilter {
        public function NumbericFilter() {
        }
        
        public var field:String;
        
        public var minValue:Number=-1;
        
        public var maxValue:Number=-1;
        
        public function setFilter(field:String, min:String, max:String):void {
            this.field=field;
            this.minValue=(min && min.length > 0) ? parseInt(min) : -1;
            this.maxValue=(max && max.length > 0) ? parseInt(max) : -1;
        }
    }
}