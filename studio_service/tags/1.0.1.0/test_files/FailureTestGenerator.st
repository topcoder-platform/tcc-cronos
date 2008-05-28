group FailureTestGenerators;

FailureTests(namespace, classTest) ::= <<
/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

<if(namespace)>package <namespace>;


<endif>
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import com.topcoder.util.objectfactory.ObjectFactory;
<if(classTest.PackageName)>import <classTest.PackageName>.<classTest.Name>;
<endif>


/**
 * Generated failure test for the \<code><classTest.Name>.java\</code> class.
 *
 * @author kakarotto
 * @version 1.0
 */
public class <classTest.Name>FailureTests {
    /** The flag to denote whether to test with value overrides. */
    private boolean useOverrides = true;

    /** The flag to denote whether to test with default value. */
    private boolean useDefaults = true;

    /** The object to test. */
    private <classTest.Name> testObject;

    /** ObjectFactory instance for testing. */
    private ObjectFactory objectFactory;
    
    /**
     * Sets up testing environment
     *
     * @throws Exception when error occurs
     */
    @Before
    public void setUp() throws Exception {
        //objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory("defaultObjectFactory"));
        testObject = new <classTest.Name>();
    }
	
    /**
     * Tears down testing environment
     *
     * @throws Exception when error occurs
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Gets flag denoting whether to use default value for testing.
     *
     * @return flag denoting whether to use default value for testing
     */
    public boolean getUseDefaults() {
        return useDefaults;
    }
    
    /**
     * Sets flag denoting whether to use default value for testing.
     *
     * @param value the flag denoting whether to use default value for testing
     */
    public void setUseDefaults(boolean value) {
        useDefaults = value;
    }
    
    /**
     * Gets flag denoting whether to use value overrides for testing
     *
     * @return flag denoting whether to use default value for testing
     */
    public boolean getUseOverrides() {
        return useOverrides;
    }
    
    /**
     * Sets flag denoting whether to use value overrides for testing
     *
     * @param value flag denoting whether to use value overrides for testing
     */
    public void setUseOverrides(boolean value) {
        useOverrides = value;
    }
    
    /**
     * Gets the object to test.
     *
     * @return the object to test
     */
    public <classTest.Name> getTestObject() {
        return testObject;
    }
    
    /**
     * Sets the object to test.
     *
     * @param value the object to test
     */
    public void setTestObject(<classTest.Name> value) {
        testObject = value;
    }
    
    /**
     * Gets the ObjectFactory for testing.
     *
     * @return the ObjectFactory for testing
     */
    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }
    
    /**
     * Sets the ObjectFactory for testing.
     *
     * @param value ObjectFactory for testing
     */
    public void setObjectFactory(ObjectFactory value) {
        objectFactory = value;
    }

    <classTest.Methods : MethodFailureTest(it); separator="\n">
}
>>

MethodFailureTest(method) ::= <<

// This section contains tests for the \<code><method.Name>\</code> method.

<method.Tests : SingleCombinationTest(test=it, method=method); separator="\n\n">
>>

SingleCombinationTest(test, method) ::= <<
/**
 * Tests for the \<code><method.Name>\</code> method with parameters containing invalid values.
<({<test.Check.Template>Before})(test.Check)>
 */
@Test(expected=<test.Check.ExceptionType>.class)
public void test<method.NameInitialCap><i0>() {
    <if(test.HasDefaults)>    if (!getUseDefaults()) {
        return;
    }<endif>
    <if(test.HasOverrides)>    if (!getUseOverrides()) {
        return;
    }<endif>
    <if(!method.Void)>
    <({<test.Check.Template>Assign})({<method.Type.Name> res = })>
    <else>
    
    <endif>getTestObject().<method.Name>(<test.ArgumentValues ; separator=", ">);

}
>>

ExceptionFailureCheckBefore(check) ::= " * The \<code><check.ExceptionType>\</code> exception is expected."
ExceptionFailureCheckAssign(decl) ::= "    "
ExceptionFailureCheckAfter(check) ::= <<
    fail("<check.ExceptionType> is expected");
} catch (<check.ExceptionType> ex) {
    // Expected
>>

UnaryAssertionBefore(check) ::= ""
UnaryAssertionAssign(decl) ::= "    <decl>"
UnaryAssertionAfter(check) ::= "    <check.AssertionType>(res);"

BinaryAssertionBefore(check) ::= ""
BinaryAssertionAssign(decl) ::= "    <decl>"
BinaryAssertionAfter(check) ::= "    <check.AssertionType>(<check.Expected>, res);"

