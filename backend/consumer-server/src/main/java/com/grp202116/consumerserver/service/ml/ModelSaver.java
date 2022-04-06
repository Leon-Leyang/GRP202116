package com.grp202116.consumerserver.service.ml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.service.util.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

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
    private final ModelDO model;
    private static final Logger logger = LoggerFactory.getLogger(ModelSaver.class);

    /**
     * Construct a model saver based on a provided {@link ModelDO}
     *
     * @param model a specified model
     */
    public ModelSaver(ModelDO model) {
        String modelDirectory = ".." + File.separator + "ml" + File.separator + "models" + File.separator +
                model.getProjectId() + "_" + model.getVersion() + File.separator;
        File directory = new File(modelDirectory);
        ModelSaver.modelPath = modelDirectory;
        if (!directory.exists()) {
            try {
                Files.createDirectories(directory.toPath());
                logger.info("New directory created at: " + modelDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.model = model;
    }

    /**
     * Return the model in this model saver
     *
     * @return {@link ModelDO}
     */
    public ModelDO getModel() {
        return model;
    }

    /**
     * Save a model by copying it to a local address
     *
     * @param url      the url of the specified mode
     * @param isScript true if it is a script
     * @return the saved path
     */
    private String saveFile(String url, boolean isScript) {
        if (url == null) return null;
        File modelFile = new File(url);
        if (!modelFile.exists()) return null;
        if (!isScript) {
            if (!FilenameUtils.getExtension(modelFile.getName()).equals("pth")) {
                logger.warn("This is not a model .pth file.");
                return null;
            }
        } else {
            if (!FilenameUtils.getExtension(modelFile.getName()).equals("txt") &&
                    !FilenameUtils.getExtension(modelFile.getName()).equals("csv")) {
                logger.warn("This is not a .txt nor .csv file.");
                return null;
            }
        }

        return getFilePath(modelFile);
    }

    /**
     * This method saves required files to a local directory
     *
     * @return true if something went wrong false otherwise
     */
    public boolean saveModel() {
        String path = saveFile(model.getModelPath(), false);
        if (path == null) {
            FileUtils.deleteDirectory(modelPath);
            return true;
        } else model.setModelPath(path);

        String labelPath = saveFile(model.getLabelsPath(), true);
        if (labelPath == null) {
            FileUtils.deleteDirectory(modelPath);
            return true;
        } else model.setLabelsPath(labelPath);

        if (saveModelParams()) {
            FileUtils.deleteDirectory(modelPath);
            return true;
        }
        return false;
    }

    /**
     * Save params of model
     *
     * @return true if not a valid target path
     */
    private boolean saveModelParams() {
        JSONObject params = JSON.parseObject(model.getParams());
        String vocPath = params.getString("vocabPath");
        if (vocPath != null) {
            String targetPath = saveFile(vocPath, true);
            if (targetPath == null) return true;
            else {
                params.put("vocabPath", targetPath);
                model.setParams(JSONObject.toJSONString(params));
            }
        }
        return false;
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
        File targetFile = new File(".." + File.separator + "flask-server" + File.separator +
                "ml" + File.separator + "models" + File.separator + customFile.getName());
        try {
            if (targetFile.exists()) {
                Files.delete(targetFile.toPath());
            }
            Files.copy(customFile.toPath(), targetFile.toPath());
            logger.info(customFile.getPath() + "is copied to" + targetFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FilenameUtils.getBaseName(customFile.getName());
    }
}
