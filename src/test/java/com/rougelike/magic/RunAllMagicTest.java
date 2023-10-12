package com.rougelike.magic;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Magic Tests")
@SelectPackages("com.rougelike.magic")
@SelectClasses({SpellTest.class, MagicTest.class, MagicElementTypeTest.class})

 class RunAllMagicTest {

    }

