<%
    ServletContext servletContext = pageContext.getServletContext();
    servletContext.log("paypalConfirmation.jsp : Got following request from PayPal : ["
                       + request.getQueryString() + "]");
    final String paymentType = request.getParameter("USER3");
    if ("Submission".equals(paymentType)) {
        final String pageURL = servletContext.getInitParameter("view-submissions-confirmation-page");
        servletContext.log("paypalConfirmation.jsp : Redirecting to View Submissions Widget : [" + pageURL + "]");
        response.sendRedirect(pageURL + '?' + request.getQueryString());
    } else if ("Launch".equals(paymentType)) {
        final String pageURL = servletContext.getInitParameter("launch-project-confirmation-page");
        servletContext.log("paypalConfirmation.jsp : Redirecting to Launch Project Widget : [" + pageURL + "]");
        response.sendRedirect(pageURL + '?' + request.getQueryString());
    } else {
        servletContext.log("paypalConfirmation.jsp : Payment type is not valid. Responding with status code "
                           + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " : [" + paymentType + "]");
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid payment type");
    }
%>