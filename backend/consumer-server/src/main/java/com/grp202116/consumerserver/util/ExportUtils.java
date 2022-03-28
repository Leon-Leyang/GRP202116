package com.grp202116.consumerserver.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ByteArrayResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportUtils {

    private static List<Object> dataList;
    private static BufferedWriter bufferedWriter;
    private static String format;
    private static boolean isAnnotation;
    private static File targetFile;

    /**
     * Create the file to be exported
     */
    private static void createTemp() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

        if (isAnnotation) {
            targetFile = new File(System.getProperty("java.io.tmpdir") + File.separator
                    + "annotation" + simpleDateFormat.format(date) + "." + format);
        } else {
            targetFile = new File(System.getProperty("java.io.tmpdir") + File.separator
                    + "prediction_" + simpleDateFormat.format(date) + "." + format);
        }
    }

    public static File getTargetFile() {
        return targetFile;
    }

    public static ByteArrayResource exportFile(boolean isAnnotation, String format, List<Object> dataList) {
        ExportUtils.format = format;
        ExportUtils.isAnnotation = isAnnotation;
        createTemp();

        ExportUtils.dataList = dataList;
        ByteArrayResource resource = null;

        try {
            if (targetFile.createNewFile()) System.out.println("Temporary File Creation Success");
            else return null;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    targetFile), StandardCharsets.UTF_8));
            if (format.equals("json")) writeJSON();
            else if (format.equals("csv")) writeCSV();
            else writeTSV();
            bufferedWriter.close();

            resource = new ByteArrayResource(Files.readAllBytes(targetFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resource;
    }

    private static void writeJSON() throws IOException {
        for (Object data : dataList) {
            String jsonString = JSONObject.toJSONString(data, true);
            bufferedWriter.write(jsonString);
            bufferedWriter.newLine();
        }
    }

    private static void writeCSV() throws IOException {
        for (Object data : dataList) {
            StringBuilder stringBuilder = new StringBuilder();
            bufferedWriter.write(stringBuilder.append("\"").append(data).append("\",").toString());
            bufferedWriter.newLine();
        }
    }

    private static void writeTSV() throws IOException {
        for (Object data : dataList) {
            StringBuilder stringBuilder = new StringBuilder();
            bufferedWriter.write(stringBuilder.append("\"").append(data).append("\t,").toString());
            bufferedWriter.newLine();
        }
    }
}
