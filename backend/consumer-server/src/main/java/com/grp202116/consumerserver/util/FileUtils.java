package com.grp202116.consumerserver.util;

import com.grp202116.consumerserver.pojo.DataDO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This is the util class which deals with file input or output,
 * including methods for saving files to a local address.
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class FileUtils {

    private static List<DataDO> dataList;
    private static BigInteger projectId;
    private static String projectPath;

    public static List<DataDO> uploadProjectData(List<File> fileList, BigInteger projectId, String type) {
        dataList = new ArrayList<>();
        FileUtils.projectId = projectId;
        projectPath = ".." + File.separator + "files" + File.separator + projectId + File.separator;
        File project = new File(projectPath);
        try {
            Files.createDirectories(project.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if (!project.exists()) if (project.mkdirs()) System.out.println("New directory created at: " + projectPath);

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
        if (type.equals("image")) {
            if (file.isDirectory()) readDirectory(file, type);
            else readSingleFile(file, type);
        } else if (type.equals("text")) {
            if (file.isDirectory()) readDirectory(file, type);
            else readSingleFile(file, type);
        }


//        if (type.equalsIgnoreCase("image classification") || type.equalsIgnoreCase("object detection")
//                || type.equalsIgnoreCase("semantic segmentation") || type.equalsIgnoreCase("keypoint labeling")) {
//            if (file.isDirectory()) readDirectory(file, "image");
//            else readSingleFile(file, "image");
//        } else if (type.equalsIgnoreCase("text classification") || type.equalsIgnoreCase("named entity recognition")) {
//            if (file.isDirectory()) readDirectory(file, "text");
//            else readSingleFile(file, "text");
//        }
    }

    private static void readSingleFile(File file, String dataType) throws IOException {
        String realType = FilenameUtils.getExtension(file.getName());

        if (dataType.equals("image")) {
            if (!realType.equalsIgnoreCase("jpg") && !realType.equalsIgnoreCase("png")
                    && !realType.equalsIgnoreCase("gif") && !realType.equalsIgnoreCase("jpeg")
                    && !realType.equalsIgnoreCase("bmp") && !realType.equalsIgnoreCase("svg")
                    && !realType.equalsIgnoreCase("webp"))
                return;
        } else {
            if (!realType.equalsIgnoreCase("txt") && !realType.equalsIgnoreCase("csv")
                    && !realType.equalsIgnoreCase("tsv") && !realType.equalsIgnoreCase("json"))
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
                System.out.println(multipartFile.getOriginalFilename());
                File convertFile = new File(System.getProperty("java.io.tmpdir") + File.separator
                        + multipartFile.getOriginalFilename());
                multipartFile.transferTo(convertFile);
                fileList.add(convertFile);
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }

    public static void deleteDirectory(String directoryPath) {
        Path directory = Paths.get(directoryPath);

        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String filePath) {
        if (filePath == null) return;
        Path file = Paths.get(filePath);

        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

