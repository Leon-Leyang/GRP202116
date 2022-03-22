package com.grp202116.backend.util;

import com.grp202116.backend.pojo.DataDO;
import org.apache.tika.Tika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataUtils {
    private static List<DataDO> dataList;
    private static BigInteger projectId;
    private static String projectPath;
    private static final Tika tika;

    static {
        tika = new Tika();
    }

    public static List<DataDO> uploadProjectData(List<String> urlList, BigInteger projectId, String type) {
        dataList = new ArrayList<>();
        DataUtils.projectId = projectId;
        projectPath = "../files/" + projectId + "/";

        for (String url : urlList) {
            try {
                moveToLocal(url, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }

    /**
     * CV:
     * image classification
     * object detection
     * semantic segmentation
     * keypoint labeling
     * <p>
     * NLP:
     * text classification
     * taxonomy
     * named entity recognition
     * machine translation
     *
     * @param url
     * @param type
     */
    private static void moveToLocal(String url, String type) throws IOException {
        File file = new File(url);

        if (!file.exists()) throw new FileNotFoundException();

        if (type.equals("image classification") || type.equals("object detection")
                || type.equals("semantic segmentation") || type.equals("keypoint labeling")) {
            if (file.isDirectory()) readDirectory(file, "image");
            else readSingleFile(file, "image");
        } else if (type.equals("text classification") || type.equals("taxonomy")
                || type.equals("named entity recognition") || type.equals("machine translation")) {
            if (file.isDirectory()) readDirectory(file, "text");
            else readSingleFile(file, "text");
        }
    }

    private static void readSingleFile(File file, String dataType) throws IOException {
        String realType = tika.detect(file);
        if (dataType.equals("image") && !realType.substring(0, 5).equals(dataType)) return;
        else if (!realType.equals("text/plain")) return;

        DataDO data = new DataDO();
        data.setProjectId(projectId);
        data.setType(realType);

        Date date = new Date();
        data.setUpdateTime(date);
        data.setCreateTime(date);

        File targetFile = new File(projectPath + file.getName());
        Files.copy(file.toPath(), targetFile.toPath());

        dataList.add(data);
    }

    private static void readDirectory(File file, String dataType) throws IOException {
        File[] files = file.listFiles();
        if (files == null) return;
        for (File inFile : files) {
            if (!inFile.exists()) return;
            else if (inFile.isDirectory()) readDirectory(inFile, dataType);
            else readSingleFile(inFile, dataType);
        }
    }
}

