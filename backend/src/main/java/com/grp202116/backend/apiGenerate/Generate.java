package com.grp202116.backend.apiGenerate;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.springframework.boot.SpringApplication;

public class Generate {public static void main(String[] args) {
    DocsConfig config = new DocsConfig();
    config.setProjectPath("C:\\Users\\lenovo\\Desktop\\GRPsoftware\\202116\\GRP202116\\backend"); // root project path
    config.setProjectName("GRP202116"); // project name
    config.setApiVersion("V1.0");       // api version
    config.setDocsPath("C:\\Users\\lenovo\\Desktop\\GRPsoftware\\202116\\GRP202116\\backend\\API"); // api docs target path
    config.setAutoGenerate(Boolean.TRUE);  // auto generate
    Docs.buildHtmlDocs(config); // execute to generate

}
}
