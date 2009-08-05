package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget
{
	import mx.controls.TileList;
	import mx.controls.listClasses.IListItemRenderer;

	public class CustomTileList extends TileList
	{
		public function CustomTileList()
		{
			super();
		}
		
		public function select(item:Object) {
			this.selectedItem=item;
			super.selectItem(itemToItemRenderer(item), false, false, true);
		}
	}
}