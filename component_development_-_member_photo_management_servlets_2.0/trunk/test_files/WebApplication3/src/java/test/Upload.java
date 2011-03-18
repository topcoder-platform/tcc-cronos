/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.topcoder.web.memberphoto.servlet.MemberPhotoUploadServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author MicroSky
 */
public class Upload extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("-----------" + request.getSession(true).getAttribute("member_id_session_key"));
        System.out.println("-----------" + request.getParameter("member_id"));
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        MemberPhotoUploadServlet bean = (MemberPhotoUploadServlet) wac.getBean("uploadServlet");
        bean.doPost(request, response);
    }
}
