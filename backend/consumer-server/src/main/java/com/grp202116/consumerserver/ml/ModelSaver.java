package com.grp202116.consumerserver.ml;

import com.grp202116.consumerserver.pojo.ModelDO;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 */
public class ModelSaver {
    private static String modelPath;

    public ModelSaver(String type) {
        String modelDirectory = "../ml/models" + File.separator + type + File.separator;
        File directory = new File(modelDirectory);
        ModelSaver.modelPath = modelDirectory;
        if (!directory.exists()) if (directory.mkdirs())
            System.out.println("New directory created at: " + modelDirectory);
    }

    public String saveModel(String url) {
        if (url == null) return null;
        File modelFile = new File(url);
        if (!modelFile.exists()) return null;
        if (!FilenameUtils.getExtension(modelFile.getName()).equals("pth")) {
            System.out.println("Not a model file.");
            return null;
        }
        return getFilePath(modelFile);
    }

    public String saveLabels(String url) {
        File labelFile = new File(url);
        if (!labelFile.exists()) return null;
        if (!FilenameUtils.getExtension(labelFile.getName()).equals("txt") ||
                !FilenameUtils.getExtension(labelFile.getName()).equals("csv")) {
            System.out.println("Not a label file.");
            return null;
        }
        return getFilePath(labelFile);
    }

    private String getFilePath(File modelFile) {
        File targetFile = new File(modelPath + modelFile.getName());
        try {
            if (targetFile.createNewFile()) System.out.println("New file created.");
            Files.copy(modelFile.toPath(), targetFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile.getPath();
    }

    public static String saveCustom(String customFilePath) {
        File customFile = new File(customFilePath);
        if (!FilenameUtils.getExtension(customFile.getName()).equals("py")) {
            System.out.println("Not a python file.");
            return null;
        }
        File targetFile = new File("../ml/models" + File.separator + customFile.getName());
        try {
            if (targetFile.createNewFile()) System.out.println("New file created.");
            Files.copy(customFile.toPath(), targetFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FilenameUtils.getName(customFile.getName());
    }
}
