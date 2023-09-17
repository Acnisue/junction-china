package com.acanisue.apprunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.io.File;

import static com.acanisue.util.Constant.FILEPATH;

@Component
@Slf4j
public class AppStartupRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("===》检查文件夹《===");
        for (String s : FILEPATH) {
            String realPath = System.getProperty("user.dir")+s;
            File file =new File(realPath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    log.info(realPath+"==》目录创建成功");
                }else {
                    log.warn(realPath+"==》目录创建失败");
                }
            }
        }
        log.info("《===检查文件夹完成===》");
    }
}