package com.grp202116.consumerserver.service.statistics;

import com.grp202116.consumerserver.mapper.AnnotationMapper;
import com.grp202116.consumerserver.mapper.DataMapper;
import com.grp202116.consumerserver.mapper.PredictionMapper;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.PredictionDO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class Statistics contains the basic information of the statistics
 * controls the statistics of data, labels and project.
 * The calculation will be done in the backend then send to the frontend to list
 * the Statistics
 *
 * @author Henghui Zhang; Yujie Chen
 * @version 1.2
 */
@Component
public class Statistics {
    @Resource
    private DataMapper dataMapper;
    @Resource
    private AnnotationMapper annotationMapper;
    @Resource
    private PredictionMapper predictionMapper;

    public static Statistics Statistics;

    @PostConstruct
    public void init() {
        Statistics = this;
        Statistics.dataMapper = this.dataMapper;
        Statistics.annotationMapper = this.annotationMapper;
        Statistics.predictionMapper = this.predictionMapper;
    }

    BigInteger projectId;
    BigInteger dataListNumber;
    BigInteger labeledDataListNumber;
    BigInteger annotationsNumber;
    BigInteger predictionsNumber;
    Float completionPercentage;
    Float averageAnnotations;
    Float averagePredictions;
    Float averageTextWordsNumber;
    List<DataDO> dataList;
    List<PredictionDO> predictions;
    List<AnnotationDO> annotations;

    /**
     * The construct method called once a project is created
     * and initialize the basic data object
     *
     * @param projectId the created project id
     */
    public Statistics setProjectId(BigInteger projectId) throws IOException {
        this.projectId = projectId;
        this.setDataListNumber(countDataList());
        this.setLabeledDataListNumber(countLabeledDataListNumber());
        this.setCompletionPercentage(calculateCompletionPercentage());
        this.setAnnotations(getAnnotationsFromDB());
        this.setAnnotationsNumber(BigInteger.valueOf(getAnnotations().size()));
        this.setPredictions(getPredictionsFromDB());
        this.setPredictionsNumber(BigInteger.valueOf(getPredictions().size()));
        this.setAverageAnnotations(countAverageAnnotations());
        this.setAveragePredictions(countAveragePredictions());
        this.setAverageTextWordsNumber(countAverageTextWordsNumber());
        return this;
    }

    /**
     * Set the Data objects list
     *
     * @param dataList the Data objects list
     */
    public void setDataList(List<DataDO> dataList) {
        this.dataList = dataList;
    }

    /**
     * Get the Data objects list
     *
     * @return the Data objects list
     */
    public List<DataDO> getDataListFromDB() {
        return dataMapper.listByProjectId(this.projectId);
    }

    /**
     * Get the number of the data
     *
     * @return the number of the data
     */
    public BigInteger getDataListNumber() {
        return dataListNumber;
    }

    /**
     * Set the number of the data
     *
     * @param dataListNumber the number of the data
     */
    public void setDataListNumber(BigInteger dataListNumber) {
        this.dataListNumber = dataListNumber;
    }

    /**
     * Set the number of labeled Data
     *
     * @param labeledDataListNumber the number of the labeled Data
     */
    public void setLabeledDataListNumber(BigInteger labeledDataListNumber) {
        this.labeledDataListNumber = labeledDataListNumber;
    }

    /**
     * Count the number of labeled Data
     *
     * @return the number of labeled Data
     */
    public BigInteger countLabeledDataListNumber() {

        Set<Object> labeledData = new HashSet<>();
        for (AnnotationDO annotationDO : annotations) {
            labeledData.add(annotationDO.getDataId());
        }

        return BigInteger.valueOf(labeledData.size());

    }

    /**
     * Get the number of labeled Data
     *
     * @return the number of labeled Data
     */
    public BigInteger getLabeledDataListNumber() {
        return labeledDataListNumber;
    }

    /**
     * Get the number of the Annotation
     *
     * @return the number of the Annotation
     */
    public BigInteger getAnnotationsNumber() {
        return annotationsNumber;
    }

    /**
     * Set the number of the Annotation
     *
     * @param annotationsNumber the number of the Annotation
     */
    public void setAnnotationsNumber(BigInteger annotationsNumber) {
        this.annotationsNumber = annotationsNumber;
    }

    /**
     * Get the annotations object list
     *
     * @return the annotations object list
     */
    public List<AnnotationDO> getAnnotations() {
        return annotations;
    }

    /**
     * Set the annotation data object list
     *
     * @param annotations the annotation data object list
     */
    public void setAnnotations(List<AnnotationDO> annotations) {
        this.annotations = annotations;
    }

    /**
     * Get the number of the predictions
     *
     * @return the number of the predictions
     */
    public BigInteger getPredictionsNumber() {
        return predictionsNumber;
    }

    /**
     * Set the number of the predictions
     *
     * @param predictionsNumber
     */
    public void setPredictionsNumber(BigInteger predictionsNumber) {
        this.predictionsNumber = predictionsNumber;
    }

    /**
     * Get the percentage value completed in one project according to the
     * data labeled and the number of all the data
     *
     * @return
     */
    public Float getCompletionPercentage() {
        return completionPercentage;
    }

    /**
     * Set the completed percentage of the project
     *
     * @param completionPercentage the completed percentage
     */
    public void setCompletionPercentage(Float completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    /**
     * Get the prediction data object list
     *
     * @return the predictions object list
     */
    public List<PredictionDO> getPredictions() {
        return predictions;
    }

    /**
     * Set the prediction data object list
     *
     * @param predictions the prediction data object list
     */
    public void setPredictions(List<PredictionDO> predictions) {
        this.predictions = predictions;
    }

    /**
     * Get the size of the data in the project according to the project id and read
     * value from the DB
     *
     * @return the size of the data
     */
    public BigInteger countDataList() {
        return BigInteger.valueOf(dataList.size());
    }

    /**
     * Get the annotations object list from the DB and give the value to the
     * Statistics list
     *
     * @return the annotations object list from the DB
     */
    public List<AnnotationDO> getAnnotationsFromDB() {
        return annotationMapper.listByProjectId(this.projectId);
    }

    /**
     * Get the Prediction object list from the DB and give the value to the
     * Statistics list
     *
     * @return the Prediction object list from the DB
     */
    public List<PredictionDO> getPredictionsFromDB() {
        return predictionMapper.listByProjectId(this.projectId);
    }

    /**
     * Get the percentage of the annotated data files in all data files for each
     * project
     *
     * @return the percentage of the annotated data files in all data files for each
     *         project
     */
    public float calculateCompletionPercentage() {

        return getLabeledDataListNumber().floatValue() / getDataListNumber().floatValue() * 100;

    }

    /**
     * Set the average number of the Annotations
     *
     * @param averageAnnotations the average number of the Annotations
     */
    public void setAverageAnnotations(Float averageAnnotations) {
        this.averageAnnotations = averageAnnotations;
    }

    /**
     * Get the average number of the Annotations
     *
     * @return the average number of the Annotations
     */
    public Float getAverageAnnotations() {
        return averageAnnotations;
    }

    /**
     * Count the average number of the Annotations
     *
     * @return the average number of the Annotations
     */
    public Float countAverageAnnotations() {
        return getAnnotationsNumber().floatValue() / getDataListNumber().floatValue();
    }

    /**
     * Set the average number of Predictions
     *
     * @param averagePredictions the average number of Predictions
     */
    public void setAveragePredictions(Float averagePredictions) {
        this.averagePredictions = averagePredictions;
    }

    /**
     * Get the average number of Predictions
     *
     * @return the average number of Predictions
     */
    public Float getAveragePredictions() {
        return averagePredictions;
    }

    /**
     * Count the average number of Predictions
     *
     * @return the average number of Predictions
     */
    public Float countAveragePredictions() {
        return getPredictionsNumber().floatValue() / getDataListNumber().floatValue();
    }

    /**
     * Get the average words number of the text type data
     *
     * @return average words number
     */
    public Float getAverageTextWordsNumber() {
        return averageTextWordsNumber;
    }

    /**
     * Set the average words number of the text type data
     *
     * @param averageTextWordsNumber he average words number of the text type data
     */
    public void setAverageTextWordsNumber(Float averageTextWordsNumber) {
        this.averageTextWordsNumber = averageTextWordsNumber;
    }

    /**
     * Count the text number of the data
     *
     * @param url the url link
     * @return the text number of the data
     * @throws IOException the file exist exception
     */
    public int countTextNumber(String url) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(url));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            sb.append(line);
        }
        br.close();
        String content = sb.toString();
        return content.replaceAll(" ", "").length();

    }

    /**
     * Count and calculate the average words of the text data
     *
     * @return the average words of the text data
     * @throws IOException the file if exist exception
     */
    public Float countAverageTextWordsNumber() throws IOException {
        int textDataType = 0;
        int wordSum = 0;
        for (DataDO dataDO : dataList) {
            if (dataDO.getType().equalsIgnoreCase("text")) {
                textDataType++;
                wordSum = wordSum + countTextNumber(dataDO.getUrl());
            }
        }
        return (float) wordSum / (float) textDataType;
    }
}
