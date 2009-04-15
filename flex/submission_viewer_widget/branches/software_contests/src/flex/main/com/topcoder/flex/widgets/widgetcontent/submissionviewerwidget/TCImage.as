package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget
{
    import flash.events.Event;

    import mx.controls.Image;

    public class TCImage extends Image {
        public function TCImage() {
            super();

            // Listen for the event dispatched after the image
            // source has been changed
            addEventListener("sourceChanged", handleSourceChanged);
        }

        private function handleSourceChanged(event:Event):void {
            // load the image immediately, once the source is set
            this.visible = true;
            this.load(this.source);
            this.validateNow();
        }
    }
}