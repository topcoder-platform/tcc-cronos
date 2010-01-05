/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {
    
    /**
     * <p>
     * A renderer for type.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class TypeRenderer extends RendererBase {
    	    	
        override protected function renderLabel():void {
            this.content.text = detail.type;
        }
        
        override protected function doFilter():void {
            if (model) {
                model.filter.type=detail.type;
                model.filterDetail();
            }
        }
    }
}
