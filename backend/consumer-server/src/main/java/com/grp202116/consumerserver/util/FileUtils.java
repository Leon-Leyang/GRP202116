package com.grp202116.consumerserver.util;

import com.grp202116.consumerserver.pojo.DataDO;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * This is the public interface, which receives a list of {@link File}
     * and move it to a local directory
     *
     * @param fileList  the list of files
     * @param projectId the id of a project
     * @param type      the type of project, "image" or "text"
     * @return the list of {@link DataDO} to be inserted into the database
     */
    public static List<DataDO> uploadProjectData(List<File> fileList, BigInteger projectId, String type) {
        dataList = new ArrayList<>();
        FileUtils.projectId = projectId;
        projectPath = ".." + File.separator + "files" + File.separator + projectId + File.separator;
        File project = new File(projectPath);
        try {
            Files.createDirectories(project.toPath());
            logger.info("New directory created in: " + project.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (File file : fileList) {
            try {
                if (!file.exists()) {
                    logger.info(file.getPath() + "  is not a file nor a directory");
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
     * Move a {@link File} to a local address, depend on the type of project
     *
     * @param file the file to be copied
     * @param type the type of the submitted project
     * @throws IOException copy may produce exception
     */
    private static void moveToLocal(File file, String type) throws IOException {
        if (type.equals("image")) {
            if (file.isDirectory()) readDirectory(file, type);
            else readSingleFile(file, type);
        } else if (type.equals("text")) {
            if (file.isDirectory()) readDirectory(file, type);
            else readSingleFile(file, type);
        }
    }

    /**
     * Copy a file and create the corresponding {@link DataDO}
     *
     * @param file     the file to be copied
     * @param dataType the type of this file
     * @throws IOException copy may throw exception
     */
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
            if (targetFile.exists()) Files.delete(targetFile.toPath());
            Files.copy(file.toPath(), targetFile.toPath());
            logger.info(file.getPath() + "is copied to" + targetFile.getPath());
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        }

        dataList.add(data);
    }

    /**
     * Split a text file, save it and create corresponding {@link DataDO}
     *
     * @param file     the file to be read
     * @param realType the real type of a the file
     * @throws IOException copy may throw exception
     */
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

            data.setUrl(line);

            dataList.add(data);
        }
        File targetFile = new File(projectPath + UUID.randomUUID() + "." + realType);
        if (targetFile.createNewFile()) logger.info("New file created at: " + targetFile.getPath());

        bf.close();
    }

    /**
     * Read all files under a directory
     *
     * @param file     the directory
     * @param dataType the type of data
     * @throws IOException copy throws exception
     */
    private static void readDirectory(File file, String dataType) throws IOException {
        File[] files = file.listFiles();
        if (files == null) return;
        for (File inFile : files) {
            if (!inFile.exists()) return;
            else if (inFile.isDirectory()) readDirectory(inFile, dataType);
            else readSingleFile(inFile, dataType);
        }
    }

    /**
     * Convert a list of {@link MultipartFile} to a list of {@link File}
     *
     * @param multipart the list of multipart files
     * @return a list of files
     */
    public static List<File> multipartToFile(MultipartFile[] multipart) {
        List<File> fileList = new ArrayList<>();

        try {
            for (MultipartFile multipartFile : multipart) {
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

    /**
     * Delete an entire directory
     *
     * @param directoryPath the path of a directory
     */
    public static void deleteDirectory(String directoryPath) {
        Path directory = Paths.get(directoryPath);
        File directoryFile = new File(directoryPath);
        if (!directoryFile.isDirectory()) return;

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
        logger.info(directoryFile.getPath() + " is deleted");
    }

    /**
     * Delete a file
     *
     * @param filePath url of the file to be deleted
     * @deprecated
     */
    @Deprecated
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

