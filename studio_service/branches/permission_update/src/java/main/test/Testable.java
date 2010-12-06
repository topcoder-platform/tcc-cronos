package test;

import java.lang.annotation.*;

/**
 * Created Sun Apr 06 00:36:07 CST 2008 <br/>
 *
 * @author JUnit 4 Synchronizer
 * @version $Revision: $ <br/> $Date: $ <br/> $Author: $
 */
@Retention(value = RetentionPolicy.SOURCE)
@Target( { ElementType.CONSTRUCTOR, ElementType.METHOD } )
public @interface Testable {

    public abstract String value() default "";

} // Testable annotation type
