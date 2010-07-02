/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline {
    import flash.events.MouseEvent;
    import flash.net.URLRequest;
    import flash.net.navigateToURL;

    import mx.controls.LinkButton;

  /**
   * The renderer for the contest_name column in data grid.
   * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
   * @author TCSDEVELOPER
   * @version 1.0
   */
    public class ContestLinkButton extends LinkButton {
      /**
       * The base url for the link of contents;
       */
        private var urlBase:String = "http://studio.topcoder.com/?module=ViewContestDetails&ct="

    /**
     * The ctor.
     */
        public function ContestLinkButton() {
            super();
            this.addEventListener(MouseEvent.CLICK, handleClick);
        }

    /**
     * Set the label.
     *
     */
        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            super.updateDisplayList(unscaledWidth, unscaledHeight);

            this.label = data.contest_name;
            this.styleName = "DataGridContestLink";
        }

    /**
     * Click on the label links to url.
     */
        private function handleClick(event:MouseEvent):void {
            navigateToURL(new URLRequest(urlBase+data.contest_id));
        }
    }
}
