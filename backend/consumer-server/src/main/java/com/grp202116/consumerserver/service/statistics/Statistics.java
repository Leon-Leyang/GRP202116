package com.grp202116.consumerserver.service.statistics;

import com.grp202116.consumerserver.mapper.AnnotationMapper;
import com.grp202116.consumerserver.mapper.DataMapper;
import com.grp202116.consumerserver.mapper.ModelMapper;
import com.grp202116.consumerserver.mapper.PredictionMapper;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.PredictionDO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Resource
    private ModelMapper modelMapper;

    public static Statistics statistics;

    @PostConstruct
    public void init() {
        statistics = this;
        statistics.dataMapper = this.dataMapper;
        statistics.annotationMapper = this.annotationMapper;
        statistics.predictionMapper = this.predictionMapper;
        statistics.modelMapper = this.modelMapper;
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
    String modelAccuracy;
    List<DataDO> dataList;
    List<PredictionDO> predictions;
    List<AnnotationDO> annotations;
    List<ModelDO> models;
    List<Tag> tags;
    List<Tag> predictionTags;
    /**
     * The construct method called once a project is created
     * and initialize the basic data object
     *
     * @param projectId the created project id
     */
    public Statistics setupStatistics(BigInteger projectId) throws IOException {
        this.projectId = projectId;
        this.setDataList(getDataListFromDB());
        this.setDataListNumber(countDataList());
        this.setAnnotations(getAnnotationsFromDB());
        this.setAnnotationsNumber(countAnnotationNumber());
        this.setCompletionPercentage(getCompletionPercentage());
        this.setAverageAnnotations(getAverageAnnotations());
        this.setAveragePredictions(getAveragePredictions());
        this.setAverageTextWordsNumber(getAverageTextWordsNumber());
        this.setLabeledDataListNumber(countLabeledDataListNumber());
        this.setCompletionPercentage(calculateCompletionPercentage());
        this.setPredictions(getPredictionsFromDB());
        this.setPredictionsNumber(BigInteger.valueOf(getPredictions().size()));
        this.setAverageAnnotations(countAverageAnnotations());
        this.setAveragePredictions(countAveragePredictions());
        this.setAverageTextWordsNumber(countAverageTextWordsNumber());
        this.setTags(getTags());
        this.setPredictionsTags(getPredictionsTags());
        this.setModelList(getModelFromDB());
        return this;
    }

    public String getModelAccuracy() {
        return modelAccuracy;
    }

    public void setModelAccuracy(String modelAccuracy) {
        this.modelAccuracy = modelAccuracy;
    }

    public void setModelList(List<ModelDO> models) {
        this.models = models;
    }
    public List<ModelDO> getModelFromDB() {
        return statistics.modelMapper.getByProjectId(this.projectId);
    }

    public List<ModelDO> getModels() {
        return models;
    }


    /**
     * Set the prediction tags
     * @param predictionTags the prediction tags list
     */
    public void setPredictionsTags(List<Tag> predictionTags) {
        this.predictionTags = predictionTags;
    }

    /**
     * Get the prediction tag list
     * @return the prediction tag list
     */
    public List<Tag> getPredictionsTags(){
        List<Tag> tags = new ArrayList<>();
        String toolPattern = "(?<=(type(\\\\)?\": ?(\\\\)?\")).*?(?=(\\\\)?\")";
        for(PredictionDO predictionDO:getPredictionsFromDB()){
            String typeName = "";
            if(predictionDO.getResult()!=null){
                String result = predictionDO.getResult();
                Pattern tool =Pattern.compile(toolPattern);
                Matcher m = tool.matcher(result);
                if (m.find()) {
                    typeName = m.group(0);
                }
                String tagPattern = "(?<="+typeName+"\": ?\\[\").*?(?=\")";
                Pattern tag = Pattern.compile(tagPattern);
                Matcher m1 = tag.matcher(result);
                if (m1.find()) {
                    String tagName = m1.group(0);
                    if(tags.isEmpty()){
                        tags.add(new Tag(tagName));
                    }
                    else{
                        boolean flag  = false;
                        for(Tag tag1:tags){
                            if(tag1.getName().equalsIgnoreCase(tagName)){
                                tag1.setNumber(tag1.getNumber()+1);
                                flag = true;
                            }
                        }
                        if(flag==false){
                            tags.add(new Tag(tagName));
                        }
                    }
                }
            }

        }
        return tags;
    }

    /**
     * Set the annotation tag list
     * @param tags the annotation tag list
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Get the annotation tag list
     * @return the annotation tag list
     */
    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        String toolPattern = "(?<=(type(\\\\)?\":(\\\\)?\")).*?(?=(\\\\)?\")";
        for(AnnotationDO annotationDO:getAnnotationsFromDB()){
            String toolName = "";
            if(annotationDO.getResult()!=null){
                String result = annotationDO.getResult();
                Pattern tool =Pattern.compile(toolPattern);
                Matcher m = tool.matcher(result);
                if (m.find()) {
                    toolName = m.group(0);
                }
                String tagPattern = "(?<="+toolName+"\":\\[\").*?(?=\")";
                Pattern tag = Pattern.compile(tagPattern);
                Matcher m1 = tag.matcher(result);

                if (m1.find()) {
                    String tagName = m1.group(0);
                    if(tags.isEmpty()){
                        tags.add(new Tag(tagName));
                    }
                    else{
                        boolean flag  = false;
                        for(Tag tag1:tags){
                            if(tag1.getName().equalsIgnoreCase(tagName)){
                                tag1.setNumber(tag1.getNumber()+1);
                                flag = true;
                            }
                        }
                        if(flag==false){
                            tags.add(new Tag(tagName));
                        }
                    }
                }
            }

        }
        return tags;
    }

    private BigInteger countAnnotationNumber() {
        BigInteger number = BigInteger.valueOf(0);
        for(AnnotationDO annotationDO: annotations){
            if(annotationDO.getResult()!=null) {
                number = number.add(BigInteger.valueOf(1));
            }
        }
        return number;
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
        return statistics.dataMapper.listByProjectId(this.projectId);
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
            if(annotationDO.getResult()!=null){
                labeledData.add(annotationDO.getDataId());
            }

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
        return statistics.annotationMapper.listByProjectId(this.projectId);
    }

    /**
     * Get the Prediction object list from the DB and give the value to the
     * Statistics list
     *
     * @return the Prediction object list from the DB
     */
    public List<PredictionDO> getPredictionsFromDB() {
        return statistics.predictionMapper.listByProjectId(this.projectId);
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
        return getPredictionsNumber().floatValue() / getDataListNumber().floatValue()* 100;
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
