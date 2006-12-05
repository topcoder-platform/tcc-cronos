
package com.topcoder.web.frontcontroller;
import com.topcoder.util.log.*;
import com.topcoder.util.objectfactory.*;
import com.topcoder.web.frontcontroller.Action;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.ResourceParsingException;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p> ResourceParser interface defines the contract to parse resource files in a directory specified by resourceRoot, and return an array Action objects. This interface is used by FrontController class. Its implementation must provide a constructor taking no argument in order to be created by FrontController class. Its implementation doesn't have to be thread-safe, since it is used in FrontController#init() method, which is only called once when the servlet is initialized. </p>
 * 
 * It will create Handler objects too.
 */
public interface ResourceParser {
/**
 * <p>Parse resource files in the directory specified by the given resource root, and return an array of Action objects. </p>
 * 
 * 
 * @return an array of Action objects.
 * @param resourceRoot the directory containing all resource files.
 * @throws IllegalArgumentException if the arg is null or empty string.
 * @throws ResourceParsingException if fail to parse resource files in the root.
 */
    public Action[] parseResource(String resourceRoot);
}

