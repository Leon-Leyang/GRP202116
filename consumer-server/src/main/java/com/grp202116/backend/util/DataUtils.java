package com.grp202116.backend.util;

import com.grp202116.backend.pojo.DataDO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataUtils {
    private static List<DataDO> dataList;
    private static BigInteger projectId;
    private static String projectPath;

    public static List<DataDO> uploadProjectData(List<File> fileList, BigInteger projectId, String type) {
        dataList = new ArrayList<>();
        DataUtils.projectId = projectId;
        projectPath = "../files/" + projectId + "/";
        File project = new File(projectPath);
        if (!project.exists()) if (project.mkdirs()) System.out.println("New directory created at: " + projectPath);

        for (File file : fileList) {
            try {
                if (!file.exists()) {
                    System.out.println(file.getPath() + " is not a file or directory");
                    continue;
                }
                moveToLocal(file, type);
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
     */
    private static void moveToLocal(File file, String type) throws IOException {

        if (type.equals("image classification") || type.equals("object detection")
                || type.equals("semantic segmentation") || type.equals("keypoint labeling")) {
            if (file.isDirectory()) readDirectory(file, "image");
            else readSingleFile(file, "image");
        } else if (type.equals("text classification") || type.equals("named entity recognition")) {
            if (file.isDirectory()) readDirectory(file, "text");
            else readSingleFile(file, "text");
        }
    }

    private static void readSingleFile(File file, String dataType) throws IOException {
        String realType = FilenameUtils.getExtension(file.getName());

        if (dataType.equals("image")) {
            if (!realType.equals("jpg") && !realType.equals("png") && !realType.equals("gif")
                    && !realType.equals("bmp") && !realType.equals("svg") && !realType.equals("webp"))
                return;
        } else {
            if (!realType.equals("txt") && !realType.equals("csv") && !realType.equals("tsv")
                    && !realType.equals("json"))
                return;
            else splitText(file, realType);
        }

        DataDO data = new DataDO();
        data.setProjectId(projectId);
        data.setType(realType);

        Date date = new Date();
        data.setUpdateTime(date);
        data.setCreateTime(date);

        File targetFile = new File(projectPath + UUID.randomUUID() + "." + realType);
        data.setUrl(targetFile.getPath());

        try {
            Files.copy(file.toPath(), targetFile.toPath());
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        }

        dataList.add(data);
    }

    private static void splitText(File file, String realType) throws IOException {
        BufferedReader bf = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
        String line;

        while ((line = bf.readLine()) != null) {
            DataDO data = new DataDO();
            data.setProjectId(projectId);
            data.setType(realType);

            Date date = new Date();
            data.setUpdateTime(date);
            data.setCreateTime(date);

            File targetFile = new File(projectPath + UUID.randomUUID() + ".txt");
            if (targetFile.createNewFile()) System.out.println("New file created at: " + targetFile.getPath());
            data.setUrl(targetFile.getPath());

            BufferedWriter bw = Files.newBufferedWriter(targetFile.toPath(), StandardCharsets.UTF_8);
            bw.write(line);
            bw.close();

            dataList.add(data);
        }

        bf.close();
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

    public static List<File> multipartToFile(MultipartFile[] multipart) {
        List<File> fileList = new ArrayList<>();

        try {
            for (MultipartFile multipartFile : multipart) {
                File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getName());
                multipartFile.transferTo(convertFile);
                fileList.add(convertFile);
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }
}

