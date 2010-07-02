/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {

    /**
     * <p>
     * A renderer for name.
     * </p>
     * 
     * Version 1.0.1 (Pipeline Conversion Cockpit Integration Assembly 2 v1.0) Change Notes:
     *    - On name clicks now filter is applied.
     * 
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01, TCSASSEMBLER
     * @version 1.0.1
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    public class NameRenderer extends RendererBase {
        
        /**
         * Applies the corresponding filter on click on various names.
         * 
         * Updated for Version 1.0.1
         *    - Uncommented the code.
         */ 
        override protected function doFilter():void {
			if (model) {
				model.filter.name = detail.name;
				model.filterDetail();
			}
        }
        
        override protected function renderLabel():void {
            this.content.text=detail.name;
        }
    
    }
}