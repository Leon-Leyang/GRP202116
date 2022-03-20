package com.grp202116.backend.util;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * Generate the API docs for the backend
 */
public class GenerateAPI {
    public static void main(String[] args){
        DocsConfig config = new DocsConfig();
        config.setProjectPath("C:\\Users\\lenovo\\Desktop\\GRPsoftware\\202116\\GRP202116\\consumer-server");
        config.setProjectName("GRP202116");
        config.setApiVersion("V1.0");
        config.setDocsPath("C:\\Users\\lenovo\\Desktop\\GRPsoftware\\202116\\GRP202116\\consumer-server\\API");
        config.setAutoGenerate(Boolean.TRUE);
        Docs.buildHtmlDocs(config);
    }

}
