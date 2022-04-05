package com.grp202116.consumerserver.service.ml;

import com.grp202116.consumerserver.pojo.ModelDO;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This ModelSaver class contains methods related to saving of ml models.
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ModelSaver {
    private static String modelPath;
    private static final Logger logger = LoggerFactory.getLogger(ModelSaver.class);

    /**
     * Construct a model saver based on a provided {@link ModelDO}
     *
     * @param model a specified model
     */
    public ModelSaver(ModelDO model) {
        String modelDirectory = "../ml/models" + File.separator + model.getProjectId() +
                "_" + model.getVersion() + File.separator;
        File directory = new File(modelDirectory);
        ModelSaver.modelPath = modelDirectory;
        if (!directory.exists()) if (directory.mkdirs())
            logger.info("New directory created at: " + modelDirectory);
    }

    /**
     * Save a model by copying it to a local address
     *
     * @param url the url of the specified mode
     * @return the saved path
     */
    public String saveModel(String url) {
        if (url == null) return null;
        File modelFile = new File(url);
        if (!modelFile.exists()) return null;
        if (!FilenameUtils.getExtension(modelFile.getName()).equals("pth")) {
            logger.warn("This is not a model .pth file.");
            return null;
        }
        return getFilePath(modelFile);
    }

    /**
     * Save a text file by copying it to a local address
     *
     * @param url the url of the specified file
     * @return the saved path
     */
    public String saveLabels(String url) {
        File labelFile = new File(url);
        if (!labelFile.exists()) return null;
        if (!FilenameUtils.getExtension(labelFile.getName()).equals("txt") &&
                !FilenameUtils.getExtension(labelFile.getName()).equals("csv")) {
            logger.warn("This is not a .txt nor .csv file.");
            return null;
        }
        return getFilePath(labelFile);
    }

    /**
     * This is helper method for copying a file
     *
     * @param modelFile the file to be copied
     * @return the path of the saved file
     */
    private String getFilePath(File modelFile) {
        File targetFile = new File(modelPath + modelFile.getName());
        try {
            if (targetFile.exists()) Files.delete(targetFile.toPath());
            Files.copy(modelFile.toPath(), targetFile.toPath());
            logger.info(modelFile.getPath() + "is copied to" + targetFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile.getPath();
    }

    /**
     * Save a custom python script file
     *
     * @param customFilePath the url of the input file
     * @return the name of the input file
     */
    public static String saveCustom(String customFilePath) {
        File customFile = new File(customFilePath);
        if (!FilenameUtils.getExtension(customFile.getName()).equals("py")) {
            logger.warn("This custom script is not a python file.");
            return null;
        }
        File targetFile = new File("../flask-server/ml/models" + File.separator + customFile.getName());
        try {
            if (targetFile.exists()) {
                Files.delete(targetFile.toPath());
            }
            Files.copy(customFile.toPath(), targetFile.toPath());
            logger.info(customFile.getPath() + "is copied to"  + targetFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FilenameUtils.getName(customFile.getName()).substring(0, customFile.getName().length() - 3);
    }
}
