/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.topcodercatalog {
    import com.topcoder.flex.widgets.widgetcontent.topcodercatalog.model.Model;

    import flash.events.Event;

    import mx.containers.VBox;
    import mx.effects.Resize;
    import mx.events.EffectEvent;

    /**
     * <p>
     * This is the base class shadow like popup bar that animates from the top of the widget.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since Cockpit Catalog Widget Integration
     */
    [Event(name="onClose", type="flash.events.Event")]

    public class ShadowPopUp extends VBox {

        /**
         * Instance of the data model class for this widget.
         */
        [Bindable]
        protected var model:Model=Model.instance;

        /**
         * The resize animation effect player.
         */
        private var resize:Resize;

        /**
         * Height of the popup - this is stored to show the resize animation from 0 height to the full height.
         */
        private var tmpHeight:Number=-1;

        /**
         * Indicates if popup is currently shown or not.
         */
        public var isShown:Boolean;

        /**
         * A simple constructor that creates resize effect player.
         */
        public function ShadowPopUp() {
            super();
            resize=new Resize();
            resize.target=this;
            resize.addEventListener(EffectEvent.EFFECT_END, handleEnd);
            resize.duration=500;
        }

        /**
         * Updates the popup to show / hide in animated way.
         *
         * @param show true if show else false.
         */
        public function showHidePanel(show:Boolean):void {
            this.isShown=show;
            model.lockScreen=show;
            resize.widthFrom=width;
            resize.widthTo=width;
            if (show) {
                tmpHeight=Math.max(tmpHeight, this.getExplicitOrMeasuredHeight());
                this.visible=true;
                resize.heightFrom=0;
                resize.heightTo=tmpHeight;
                resize.play();
            } else {
                resize.heightFrom=tmpHeight;
                resize.heightTo=0;
                resize.play();
            }
        }

        /**
         * Custom Data provider for this shadow popup - behavior in this is customized by specific sub classes.
         */
        public function set popupData(data:Object):void {
            // can be over-ridden by sub classes.
        }

        /**
         * Handler for animation effect end event.
         *
         * @param event animation effect end event.
         */
        public function handleEnd(event:EffectEvent):void {
            this.visible=isShown;
            if (!isShown) {
                dispatchEvent(new Event("onClose"));
            }
        }
    }
}