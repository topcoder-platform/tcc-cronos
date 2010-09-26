/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {

    /**
     * <p>
     * A renderer for name.
     * </p>
     * 
     * 
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author tuyide, TCSASSEMBLER
     * @version 1.0.1
     * @since bugr-3285
     */
    public class RepostRenderer extends RendererBase {
        
        /**
         * Applies the corresponding filter on click on various names.
         * 
         * Updated for Version 1.0.1
         *    - Uncommented the code.
         */ 
        override protected function doFilter():void {
			if (model) {
				model.filter.repost = content.text;
				model.filterDetail();
			}
        }
        
        override protected function renderLabel():void {
            this.content.text=detail.repost ? "YES" : "";
        }
    
    }
}
