package com.topcoder.service.studio;

import com.topcoder.testing.generation.failure.FailureTestGenerator;
import com.topcoder.testing.generation.failure.configurators.ReflectionBasedConfigurator;
import com.topcoder.testing.generation.failure.checks.ExceptionFailureCheck;
import com.topcoder.testing.generation.failure.definitions.ClassTestDefinition;
import com.topcoder.service.studio.ejb.StudioServiceBean;

import java.io.FileWriter;
import java.io.File;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is immutable and thread safe.
 * </p>
 */
public class FailureTestGen {

    public static void main(String[] args) throws Exception {
        FailureTestGenerator gen = new FailureTestGenerator();
        gen.addInvalidValueForAnyType("null" , new ExceptionFailureCheck("IllegalArgumentException"));
        gen.addInvalidValueForType(String.class, "\" \""
                , new ExceptionFailureCheck("IllegalArgumentException")
        );
        gen.addInvalidValueForType(String.class, "null"
                , new ExceptionFailureCheck("IllegalArgumentException")
        );
        //generate(gen, ContestData.class);
        generate(gen, ContestStatusData.class);

        generate(gen, StudioServiceBean.class);
    }

    public static void generate(FailureTestGenerator gen, Class clz) throws Exception {
        String pack = clz.getPackage().getName().replaceAll("studio", "studio.failuretests");
        String path = "src/java/tests/" + pack.replaceAll("\\.", "/") + "/" + clz.getSimpleName() + "FailureTests.java";
//        System.out.println("path:" + path);
        System.out.println("suite.addTestSuite(" + clz.getSimpleName() + "FailureTests.class);");
        ClassTestDefinition ct = ReflectionBasedConfigurator.makeClassTestDefinition(clz);
        ReflectionBasedConfigurator.addAllMethods(ct, clz);
        File f = new File(path);
        if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(path);
        gen.generateTests(fw, pack, ct);
        fw.close();
    }
}
