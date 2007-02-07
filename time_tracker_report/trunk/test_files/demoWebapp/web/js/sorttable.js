var use_css=false;
var use_layers=false;   
var use_dom=false;

if (document.all)    { use_css    = true; }
if (document.layers) { use_layers = true; }
if (document.getElementById) { use_dom=true; }

var sort_object;
var sort_column;
var reverse=0;

// Constructor for SortTable object
function SortTable(name) {
	// Properties
	this.name = name;
	this.sortcolumn="";
	this.dosort=true;
	this.tablecontainsforms=false;

	// Methods
	this.AddLine = AddLine;
	this.AddColumn = AddColumn;
	this.WriteRows = WriteRows;
	this.SortRows = SortRows;
	this.AddLineProperties = AddLineProperties;
	this.AddLineSortData = AddLineSortData;

	// Structure
	this.Columns = new Array();
	this.Lines = new Array();
	this.LineProperties = new Array();
}

// Add a line to the grid
function AddLine() {
	var index = this.Lines.length;
	this.Lines[index] = new Array();
	for (var i=0; i<arguments.length; i++) {
		this.Lines[index][i] = new Object();
		this.Lines[index][i].text = arguments[i];
		this.Lines[index][i].data = arguments[i];
	}
}

// Define properties for the <TR> of the last line added
function AddLineProperties(prop) {
	var index = this.Lines.length-1;
	this.LineProperties[index] = prop;
	}
// Define sorting data for the last line added
function AddLineSortData() {
	var index = this.Lines.length-1;
	for (var i=0; i<arguments.length; i++) {
		if (arguments[i] != '') {
			this.Lines[index][i].data = arguments[i];
			}
		}
	}

// Add a column definition to the table
// Arguments:
//   name = name of the column
//   td   = any arguments to go into the <TD> tag for this column (ex: BGCOLOR="red")
//   align= Alignment of data in cells
//   type = type of data in this column (numeric, money, etc) - default alphanumeric
function AddColumn(name,td,align,type) {
	var index = this.Columns.length;
	this.Columns[index] = new Object;
	this.Columns[index].name = name;
	this.Columns[index].td   = td;
	this.Columns[index].align=align;
	this.Columns[index].type = type;
	if (type == "form") {
		 this.tablecontainsforms=true; 
		 if (use_layers) { 
		 	this.dosort=false;
			}
		}
	}

// Print out the original set of rows in the grid
function WriteRows() {
	var open_div = "";
	var close_div = "";
	for (var i=0; i<this.Lines.length; i++) {
	document.write("<TR "+this.LineProperties[i]+">");
		for (var j=0; j<this.Columns.length; j++) {
			var div_name = "d"+this.name+"-"+i+"-"+j;
			if (use_css || use_dom) {
				if (this.Columns[j].align != '') {
					var align = " ALIGN="+this.Columns[j].align;
					}
				else {
					var align = "";
					}
				open_div = "<DIV ID=\""+div_name+"\" "+align+">";
				close_div= "</DIV>";
				}
			else if (use_layers) {
				// If the table contains form elements, don't use <LAYER> tags or the
				// form will be forced closed.
				if (!this.dosort) {
					if (this.Columns[j].align != '') {
						open_div="<SPAN CLASS=\""+this.Columns[j].align+"\">";
						}
					}
				else {
					open_div = "<ILAYER NAME=\""+div_name+"\" WIDTH=100%>";
					open_div+= "<LAYER NAME=\""+div_name+"x\" WIDTH=100%>";
					if (this.Columns[j].align != '') {
						open_div+= "<SPAN CLASS=\""+this.Columns[j].align+"\">";
						}
					}
				if (this.Columns[j].align != '') {
	 				close_div = "</SPAN>";
					}
				if (this.dosort) {
					close_div += "</LAYER></ILAYER>";
					}
				}
			document.write("<TD "+this.Columns[j].td+">"+open_div+this.Lines[i][j].text+close_div+"</TD>");
			}
		document.write("</TR>");
		}
	}

// Sort the table and re-write the results to the existing table
function SortRows(documentWriter, table,column) {
	sort_object = table;
	if (!sort_object.dosort) { return; }
	if (sort_column == column) { reverse=1-reverse;}
	else { reverse=0; }
	sort_column = column;
	// Save all form column contents into a temporary object
	// This is a nasty hack to keep the current values of form elements intact
	if (table.tablecontainsforms) {
		var iname="1";
		var tempcolumns = new Object();
		for (var i=0; i<table.Lines.length; i++) {
			for (var j=0; j<table.Columns.length; j++) {
				if(table.Columns[j].type == "form") {
					var cell_name = "d"+table.name+"-"+i+"-"+j;
					if (use_css) {
						tempcolumns[iname] = documentWriter.all[cell_name].innerHTML;
					}
					else {
						tempcolumns[iname] = documentWriter.getElementById(cell_name).innerHTML;
					}
					table.Lines[i][j].text = iname;
					iname++;
					}
				}
			}
		}
	
	if (table.Columns[column].type == "numeric") {
		// Sort by Float
		table.Lines.sort(	function by_name(a,b) {
									if (parseFloat(a[column].data) < parseFloat(b[column].data) ) { return -1; }
									if (parseFloat(a[column].data) > parseFloat(b[column].data) ) { return 1; }
									return 0;
									}
								);
		}
	else if (table.Columns[column].type == "money") {
		// Sort by Money
		table.Lines.sort(	function by_name(a,b) {
									if (parseFloat(a[column].data.substring(1)) < parseFloat(b[column].data.substring(1)) ) { return -1; }
									if (parseFloat(a[column].data.substring(1)) > parseFloat(b[column].data.substring(1)) ) { return 1; }
									return 0;
									}
								);
		}
	else if (table.Columns[column].type == "date") {
		// Sort by Date
		table.Lines.sort(	function by_name(a,b) {
									if (Date.parse(a[column].data) < Date.parse(b[column].data) ) { return -1; }
									if (Date.parse(a[column].data) > Date.parse(b[column].data) ) { return 1; }
									return 0;
									}
								);
		}

	else {
		// Sort by alphanumeric
		table.Lines.sort(	function by_name(a,b) {
									if ((a[column].data+"").toUpperCase() < (b[column].data+"").toUpperCase()) { return -1; }
									if ((a[column].data+"").toUpperCase() > (b[column].data+"").toUpperCase()) { return 1; }
									return 0;
									}
								);
		}

	if (reverse) { table.Lines.reverse(); }
	for (var i=0; i<table.Lines.length; i++) {
		for (var j=0; j<table.Columns.length; j++) {
			var cell_name = "d"+table.name+"-"+i+"-"+j;
			if (use_dom) {
				if(table.Columns[j].type == "form") {
					var iname = table.Lines[i][j].text;
					documentWriter.getElementById(cell_name).innerHTML = tempcolumns[iname];
					}
				else {
					documentWriter.getElementById(cell_name).innerHTML = table.Lines[i][j].text;
					}
				}
			else if (use_css) {
				if(table.Columns[j].type == "form") {
					var iname = table.Lines[i][j].text;
					documentWriter.all[cell_name].innerHTML = tempcolumns[iname];
					}
				else {
					documentWriter.all[cell_name].innerHTML = table.Lines[i][j].text;
					}
				}
			else if (use_layers) {
				var cell_namex= "d"+table.name+"-"+i+"-"+j+"x";
				if (table.Columns[j].align != '') {
					documentWriter.layers[cell_name].documentWriter.layers[cell_namex].documentWriter.write("<SPAN CLASS=\""+table.Columns[j].align+"\">");
					}
				documentWriter.layers[cell_name].documentWriter.layers[cell_namex].documentWriter.write(table.Lines[i][j].text);
				if (table.Columns[j].align != '') {
					documentWriter.layers[cell_name].documentWriter.layers[cell_namex].documentWriter.write("</SPAN>");
					}
				documentWriter.layers[cell_name].documentWriter.layers[cell_namex].documentWriter.close();
				}
			}
		}
	}