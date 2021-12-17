package com.github.taojintianxia.cornucopia.jdbctest.validation;

import com.google.common.base.Strings;

public class SysbenchParamValidator {

    public static void validateSysbenchParam() {
        if (Strings.isNullOrEmpty(System.getProperty("conf"))) {
            throw new RuntimeException("\"-Dconf\" has not been set");
        }
        if (Strings.isNullOrEmpty(System.getProperty("time"))) {
            throw new RuntimeException("\"-Dtime\" has not been set");
        }
        if (Strings.isNullOrEmpty(System.getProperty("thread"))) {
            throw new RuntimeException("\"-Dthread\" has not been set");
        }
        if (Strings.isNullOrEmpty(System.getProperty("table-size"))) {
            throw new RuntimeException("\"-Dtable-size\" has not been set");
        }
        if (Strings.isNullOrEmpty(System.getProperty("script"))) {
            throw new RuntimeException("\"-Dscript\" has not been set");
        }
    }
}
