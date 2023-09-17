package com.acanisue.util;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编写者：Acanisue
 * 日期；2023/7/14 11:04
 */
public interface Constant {
    String JAR_PATH= System.getProperty("user.dir");
     String[] FILEPATH = {
             File.separator+"static"+File.separator+"image",
             File.separator+"static"+File.separator+"temp",
             File.separator+"static"+File.separator+"video",
    };
}
