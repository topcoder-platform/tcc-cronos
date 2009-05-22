package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget
{
    import flash.display.Loader;
    import flash.events.Event;
    import flash.system.LoaderContext;

    import mx.controls.Image;

    public class TCImage extends Image {
        private var loader:Loader = null;

        public function TCImage() {
            super();
	    this.loaderContext = new LoaderContext();
	    this.loaderContext.checkPolicyFile = true; 

            // Listen for the event dispatched after the image
            // source has been changed
            addEventListener("sourceChanged", handleSourceChanged);
        }

        private function handleSourceChanged(event:Event):void {
            // load the image immediately, once the source is set
            if (loader == null) {
                loader = new Loader();
                loader.contentLoaderInfo.addEventListener(Event.COMPLETE, handleLoadComplete);
            }
        }

        private function handleLoadComplete(event:Event):void {
            this.source = event.currentTarget.content;
            this.validateNow();
        }
    }
}