<html>
<head>
<script language='javascript' src="js/AJAXProcessor.js"></script>
<script language='javascript' src="js/WidgetBridgeNamespace.js"></script>
<script language='javascript' src="js/ProjectService.js"></script>
<script language='javascript' src="js/Project.js"></script>
<script language='javascript' src="js/IllegalArgumentException.js"></script>
<script language='javascript' src="js/InvalidResponseException.js"></script>

<script language="JavaScript">
/**
 * Demos createProject.
 */
function testProjectCreateProject() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// create a project
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(1);
	project.setName("ProjectCreate");
	project.setDescription("creating");

	// success callback	
	var success = function(proj) {
		alert("Success = Project returned : "+proj.getProjectID()+"; "+proj.getName()+"; "+proj.getDescription());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.createProject(project,success,error);
}

/**
 * Demos updateProject.
 */
function testProjectUpdateProject() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// create a project
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(1);
	project.setName("PrjectUpdate");
	project.setDescription("updating");

	// success callback	
	var success = function(proj) {
		alert("Success = Project returned : "+proj.getProjectID()+"; "+proj.getName()+"; "+proj.getDescription());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.updateProject(project,success,error);
}

/**
 * Demos deleteProject.
 */
function testProjectDeleteProject() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// create a project
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(1);
	project.setName("ProjectCreate");
	project.setDescription("creating");
	
	// success callback	
	var success = function() {
		alert("Success = Project Deleted!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	projectService.deleteProject(project,success,error);
}

/**
 * Demos getProject.
 */
function testProjectGetProject() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// success callback	
	var success = function(proj) {
		alert("Success = Project returned : "+proj.getProjectID()+"; "+proj.getName()+"; "+proj.getDescription());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.getProject(1,success,error);
}

/**
 * Demos getProjectsForUser.
 */
function testProjectGetProjectsForUser() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// success callback	
	var success = function(projects) {
		for (var i = 0; i < projects.length; i++) {
			alert("Success = Project returned index["+i+"]: "+projects[i].getProjectID()+"; "+projects[i].getName()+"; "+projects[i].getDescription());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.getProjectsForUser(1,success,error);
}

/**
 * Demos getAllProjects.
 */
function testProjectGetAllProjects() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// success callback	
	var success = function(projects) {
		for (var i = 0; i < projects.length; i++) {
			alert("Success = Project returned index["+i+"]: "+projects[i].getProjectID()+"; "+projects[i].getName()+"; "+projects[i].getDescription());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.getAllProjects(success,error);
}

// ==== errors demo ====

/**
 * Demos error in createProject.
 */
function testProjectCreateProjectError() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// create a project
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(50);
	project.setName("ProjectCreate");
	project.setDescription("creating");

	// success callback	
	var success = function(proj) {
		alert("Success = Project returned : "+proj.getProjectID()+"; "+proj.getName()+"; "+proj.getDescription());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.createProject(project,success,error);
}

/**
 * Demos error in updateProject.
 */
function testProjectUpdateProjectError() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// create a project
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(50);
	project.setName("PrjectUpdate");
	project.setDescription("updating");

	// success callback	
	var success = function(proj) {
		alert("Success = Project returned : "+proj.getProjectID()+"; "+proj.getName()+"; "+proj.getDescription());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.updateProject(project,success,error);
}

/**
 * Demos error in deleteProject.
 */
function testProjectDeleteProjectError() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// create a project
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(50);
	project.setName("ProjectCreate");
	project.setDescription("creating");

	// success callback	
	var success = function() {
		alert("Success = Project Deleted!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	projectService.deleteProject(project,success,error);
}

/**
 * Demos error in getProject.
 */
function testProjectGetProjectError() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// success callback	
	var success = function(proj) {
		alert("Success = Project returned : "+proj.getProjectID()+"; "+proj.getName()+"; "+proj.getDescription());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.getProject(50,success,error);
}

/**
 * Demos error in getProjectsForUser.
 */
function testProjectGetProjectsForUserError() {
	// initialize the project service
	var projectService = new js.topcoder.widgets.bridge.ProjectService("ajaxBridge");
	// success callback	
	var success = function(projects) {
		for (var i = 0; i < projects.length; i++) {
			alert("Success = Project returned index["+i+"]: "+projects[i].getProjectID()+"; "+projects[i].getName()+"; "+projects[i].getDescription());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	projectService.getProjectsForUser(50,success,error);
}

</script>
</head>

<body>
Widget Webservices Bridge Demo
<br />
<br />
<span style="color:blue"><b>Project Services Demo</b></span>
<br />
<ul>
	<li>Project.createProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectCreateProject()" /></li>
	<li>Project.updateProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectUpdateProject()" /></li>
	<li>Project.deleteProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectDeleteProject()" /></li>
	<li>Project.getProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectGetProject()" /></li>
	<li>Project.getGetProjectsForUser() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectGetProjectsForUser()" /></li>
	<li>Project.getAllProjects() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectGetAllProjects()" /></li>
</ul>
<br />
<br />
<span style="color:red"><b>Some Project Services Error Demo</b></span>
<br />
<ul>
	<li>Project.createProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectCreateProjectError()" /></li>
	<li>Project.updateProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectUpdateProjectError()" /></li>
	<li>Project.deleteProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectDeleteProjectError()" /></li>
	<li>Project.getProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectGetProjectError()" /></li>
	<li>Project.getGetProjectsForUser() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectGetProjectsForUserError()" /></li>
</ul>
<br />
<br />
<a href="index.jsp">Back to Index</a>
</body>
</html>