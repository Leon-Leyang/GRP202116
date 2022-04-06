package com.grp202116.consumerserver.service.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This ExportUtils class contains methods related to file exporting.
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ExportUtils {

    private static List<Object> dataList;
    private static BufferedWriter bufferedWriter;
    private static String format;
    private static boolean isAnnotation;
    private static File targetFile;

    private static final Logger logger = LoggerFactory.getLogger(ExportUtils.class);

    /**
     * Create the file to be exported
     */
    private static void createTemp() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

        if (isAnnotation) {
            targetFile = new File(System.getProperty("java.io.tmpdir") + File.separator
                    + "annotation_" + simpleDateFormat.format(date) + "." + format);
        } else {
            targetFile = new File(System.getProperty("java.io.tmpdir") + File.separator
                    + "prediction_" + simpleDateFormat.format(date) + "." + format);
        }
    }

    /**
     * Get the target file
     *
     * @return {@link #targetFile}
     */
    public static File getTargetFile() {
        return targetFile;
    }

    /**
     * This is the public interface for exporting a list of {@link Object}
     *
     * @param isAnnotation the status of annotation
     * @param format json or csv
     * @param dataList the list of data
     * @return a {@link ByteArrayResource} containing files
     */
    public static ByteArrayResource exportFile(boolean isAnnotation, String format, List<Object> dataList) {
        ExportUtils.format = format;
        ExportUtils.isAnnotation = isAnnotation;
        createTemp();

        ExportUtils.dataList = dataList;
        ByteArrayResource resource = null;

        try {
            if (targetFile.createNewFile()) logger.info("Temporary File Creation Success");
            else return null;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    targetFile), StandardCharsets.UTF_8));
            writeFormat(format);
            bufferedWriter.close();

            resource = new ByteArrayResource(Files.readAllBytes(targetFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resource;
    }

    /**
     * Write lines to a file according to the provided format.
     *
     * @param format the format of the specified file
     * @throws IOException output exception
     */
    private static void writeFormat(String format) throws IOException {
        switch (format) {
            case "json":
                for (Object data : dataList) {
                    String jsonString = JSONObject.toJSONString(data, true);
                    bufferedWriter.write(jsonString);
                    bufferedWriter.newLine();
                }
                break;
            case "csv":
                for (Object data : dataList) {
                    StringBuilder stringBuilder = new StringBuilder();
                    bufferedWriter.write(stringBuilder.append("\"").append(data).append("\",").toString());
                    bufferedWriter.newLine();
                }
                break;
            case "tsv":
                for (Object data : dataList) {
                    StringBuilder stringBuilder = new StringBuilder();
                    bufferedWriter.write(stringBuilder.append("\"").append(data).append("\t,").toString());
                    bufferedWriter.newLine();
                }
                break;
        }
    }
}
