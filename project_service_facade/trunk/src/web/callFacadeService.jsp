<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.0
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page is used ot handle the requests from index.jsp for invoking the selected web service operation
  - with provided parameters and displaying the results of the call. In fact, this page acts like a web service client
  - demonstrating the code which could be used for calling the web service. 
--%>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="com.topcoder.service.facade.project.ProjectServiceFacade" %>
<%@ page import="com.topcoder.service.project.ProjectData" %>
<%@ page import="org.jboss.ws.core.StubExt" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String calledOperation = null;
    Object callResult = null;
    Throwable error = null;
    try {
        // Determine the requested operation
        String operation = request.getParameter("operation");
        calledOperation = "operation";

        // Obtain a client stub for accessing the web service
        URL wsdlLocation = new URL(getServletConfig().getServletContext().getInitParameter("facade_wsdl"));
        QName serviceName = new QName("http://ejb.project.facade.service.topcoder.com/",
                                      "ProjectServiceFacadeBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        ProjectServiceFacade port = service.getPort(ProjectServiceFacade.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                         request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        // Call the appropriate wen service operation passing provided parameters
        if ("createProject".equals(operation)) {
            String name = request.getParameter("pname");
            String desc = request.getParameter("pdesc");
            ProjectData newProject = new ProjectData();
            newProject.setName(name);
            newProject.setDescription(desc);
            newProject = port.createProject(newProject);
            callResult = "Created new project: ID = " + newProject.getProjectId() + ", name = " + newProject.getName()
                         + ", desc = " + newProject.getDescription();
        } else if ("updateProject".equals(operation)) {
            String name = request.getParameter("uname");
            String desc = request.getParameter("udesc");
            String uid = request.getParameter("uid");
            ProjectData project = new ProjectData();
            project.setName(name);
            project.setDescription(desc);
            project.setProjectId(new Long(uid));
            port.updateProject(project);
            callResult = "Updated project: ID = " + project.getProjectId() + ", name = " + project.getName()
                         + ", desc = " + project.getDescription();
        } else if ("deleteProject".equals(operation)) {
            String did = request.getParameter("did");
            boolean result = port.deleteProject(Long.parseLong(did));
            callResult = "Deleted project: ID = " + did + ", success = " + result;
        } else if ("getProject".equals(operation)) {
            String pid = request.getParameter("pid");
            ProjectData project = port.getProject(Long.parseLong(pid));
            callResult = "Retrieved project: ID = " + project.getProjectId() + ", name = " + project.getName()
                         + ", desc = " + project.getDescription();
        } else if ("getProjectsForUser".equals(operation)) {
            String userid = request.getParameter("userid");
            List<ProjectData> projects = port.getProjectsForUser(Long.parseLong(userid));
            StringBuilder b = new StringBuilder();
            for (ProjectData p : projects) {
                b.append("    Project: ID = ").append(p.getProjectId()).append(", name = ").append(p.getName()).
                        append(", desc = ").append(p.getDescription()).append("<br/>");
            }
            callResult = "Retrieved projects for user: " + userid + "<br/>" + b.toString();
        } else if ("getAllProjects".equals(operation)) {
            List<ProjectData> projects = port.getAllProjects();
            StringBuilder b = new StringBuilder();
            for (ProjectData p : projects) {
                b.append("    Project: ID = ").append(p.getProjectId()).append(", name = ").append(p.getName()).
                        append(", desc = ").append(p.getDescription()).append("<br/>");
            }
            callResult = "Retrieved all projects:<br/>" + b.toString();
        }
    } catch (Throwable e) {
        error = e;
    }
    if (error != null) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        callResult = "ERROR!<br/>" + sw.getBuffer().toString().replaceAll("\\n", "<br/>");
    }
%>
<html>
  <head>
      <title>Project Service Facade Demo</title>
  </head>
  <body>
      <p>Called Project Service Facade operation: <%=calledOperation%></p>
      <p>Resulf of the call: <%=callResult%></p>
      <a href="index.jsp">Back to list of available operations</a>
  </body>
</html>
