/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.navigation;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/navigation/implementations/multiModule")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class KotlinGotoImplementationMultiModuleTestGenerated extends AbstractKotlinGotoImplementationMultiModuleTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInMultiModule() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/navigation/implementations/multiModule"), Pattern.compile("^([^\\.]+)$"), TargetBackend.ANY, false);
    }

    @TestMetadata("expectClass")
    public void testExpectClass() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/expectClass/");
    }

    @TestMetadata("expectClassFun")
    public void testExpectClassFun() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/expectClassFun/");
    }

    @TestMetadata("expectClassProperty")
    public void testExpectClassProperty() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/expectClassProperty/");
    }

    @TestMetadata("expectClassSuperclass")
    public void testExpectClassSuperclass() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/expectClassSuperclass/");
    }

    @TestMetadata("expectClassSuperclassFun")
    public void testExpectClassSuperclassFun() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/expectClassSuperclassFun/");
    }

    @TestMetadata("expectClassSuperclassProperty")
    public void testExpectClassSuperclassProperty() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/expectClassSuperclassProperty/");
    }

    @TestMetadata("suspendFunImpl")
    public void testSuspendFunImpl() throws Exception {
        runTest("idea/testData/navigation/implementations/multiModule/suspendFunImpl/");
    }
}