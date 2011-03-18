/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.File;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 *
 * @author MicroSky
 */
public class MockHelper {
    /**
     * Creates and initializes http request to upload photo on the server.
     * @return request created
     * @throws Exception if it occurs deeper
     */
    public MockHttpServletRequest createUploadRequest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setContentType("multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M");
        request.addHeader("Content-type", "multipart/form-data; boundary=--HttpUnit-part0-aSgQ2M");

        // create the webRequest
        PostMethodWebRequest webRequest = new PostMethodWebRequest("http://localhost:8080/");
        webRequest.setMimeEncoded(true);
        webRequest.setParameter("homer.png", new UploadFileSpec[] {new UploadFileSpec(new File(
                "test_files/stresstests/homer.png")) });

        ServletUnitClient client = runner.newClient();
        InvocationContext ic = client.newInvocation(webRequest);

        byte[] data = readContent(ic.getRequest().getInputStream());
        request.setContent(data);

        request.setParameter("submitted_flag", "true");
        request.setParameter("member_id", "3");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", 3l);
        request.setSession(session);

        return request;
    }
}
