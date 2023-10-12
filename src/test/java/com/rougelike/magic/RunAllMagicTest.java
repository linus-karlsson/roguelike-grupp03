package com.rougelike.magic;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@Suite
@RunWith(org.junit.platform.runner.JUnitPlatform.class)
@SuiteDisplayName("Magic Tests")
@SelectPackages("com.rougelike.magic")
@IncludeClassNamePatterns({ "^.*Test$", "^.*Tests$" })

class RunAllMagicTest {

}
