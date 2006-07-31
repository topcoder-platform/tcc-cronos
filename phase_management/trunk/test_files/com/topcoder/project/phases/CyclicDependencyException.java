
package com.topcoder.project.phases;
import java.util.*;

/**
 * <p>This exception will be thrown if cyclic dependency is detected in the project. It may be thrown when calculate the start/end time of the project or phase.</p>
 *
 *
 * @author TCSDESIGNER
 * @version 2.0
 */
public class CyclicDependencyException extends RuntimeException {

/**
 * <p>Create a new instance CyclicDependencyException with the given error message. </p>
 *
 *
 * @param msg the error message of the exception
 */
    public  CyclicDependencyException(String msg) {
        // your code here
    }
 }
