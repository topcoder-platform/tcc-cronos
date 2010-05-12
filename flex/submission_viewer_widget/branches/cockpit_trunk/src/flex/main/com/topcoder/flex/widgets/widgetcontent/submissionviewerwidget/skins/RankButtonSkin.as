package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.skins
{
	import mx.skins.ProgrammaticSkin;
	
	public class RankButtonSkin extends ProgrammaticSkin
	{
		public function RankButtonSkin()
		{
		}
		
		override protected function updateDisplayList( unscaledWidth:Number,
                                                       unscaledHeight:Number ):void
        {
            var backgroundColor:Number = 0xcfcfcf;
            
            graphics.clear();
            
            graphics.beginFill(backgroundColor);
            graphics.drawCircle(12, 12, 10);
            graphics.endFill();
        }


	}
}