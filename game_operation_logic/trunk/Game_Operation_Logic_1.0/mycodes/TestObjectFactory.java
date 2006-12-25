import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

public class TestObjectFactory {

    /**
     *@param args
     * @throws IllegalReferenceException 
     * @throws SpecificationConfigurationException 
     */
    public static void main(String[] args) throws Exception {
        ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory("theObj"));
        Object obj = factory.createObject("test");
    }

}
