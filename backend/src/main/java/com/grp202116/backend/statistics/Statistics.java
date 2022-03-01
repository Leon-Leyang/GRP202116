package com.grp202116.backend.statistics;

import com.grp202116.backend.mapper.AnnotationMapper;
import com.grp202116.backend.mapper.DataMapper;
import com.grp202116.backend.mapper.PredictionMapper;
import com.grp202116.backend.pojo.AnnotationDO;
import com.grp202116.backend.pojo.DataDO;
import com.grp202116.backend.pojo.PredictionDO;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * The class Statistics contains the basic information of the statistics
 * controls the statistics of data, labels and project.
 * The calculation will be done in the backend then send to the frontend to list the Statistics
 */
public class Statistics {
    @Resource
    private DataMapper dataMapper;
    @Resource
    private AnnotationMapper annotationMapper;
    @Resource
    private PredictionMapper predictionMapper;


    BigInteger projectId;
    BigInteger datasNumber;
    BigInteger annotationsNumber;
    BigInteger predictionsNumber;
    Float completionPercentage;
    List<PredictionDO> predictions;
    List<AnnotationDO> annotations;

    /**
     * The construct method called once a project is created
     * and initialize the basic data object
     * @param projectId the created project id
     */
    public Statistics(BigInteger projectId){
        this.projectId = projectId;
        this.setDatasNumber(countDatas());
        this.setCompletionPercentage(calculateCompletionPercentage());
        this.setAnnotations(getAnnotationsFromDB());
        this.setAnnotationsNumber(BigInteger.valueOf(getAnnotations().size()));
        this.setPredictions(getPredictionsFromDB());
        this.setPredictionsNumber(BigInteger.valueOf(getPredictions().size()));
    }

    /**
     * Get the number of the data
     * @return the number of the data
     */
    public BigInteger getDatasNumber() {
        return datasNumber;
    }

    /**
     * Set the number of the data
     * @param datasNumber the number of the data
     */
    public void setDatasNumber(BigInteger datasNumber) {
        this.datasNumber = datasNumber;
    }

    /**
     * Get the number of the Annotation
     * @return the number of the Annotation
     */
    public BigInteger getAnnotationsNumber() {
        return annotationsNumber;
    }

    /**
     * Set the number of the Annotation
     * @param annotationsNumber the number of the Annotation
     */
    public void setAnnotationsNumber(BigInteger annotationsNumber) {
        this.annotationsNumber = annotationsNumber;
    }

    /**
     * Get the annotations object list
     * @return the annotations object list
     */
    public List<AnnotationDO> getAnnotations() {
        return annotations;
    }

    /**
     * Set the annotation data object list
     * @param annotations the annotation data object list
     */
    public void setAnnotations(List<AnnotationDO> annotations) {
        this.annotations = annotations;
    }

    /**
     * Get the number of the predictions
     * @return
     */
    public BigInteger getPredictionsNumber() {
        return predictionsNumber;
    }

    public void setPredictionsNumber(BigInteger predictionsNumber) {
        this.predictionsNumber = predictionsNumber;
    }

    public Float getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Float completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public List<PredictionDO> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionDO> predictions) {
        this.predictions = predictions;
    }

    public BigInteger countDatas(){
        List<DataDO> datas = dataMapper.listByProjectId(this.projectId);
        return BigInteger.valueOf(datas.size());
    }

    public List<AnnotationDO> getAnnotationsFromDB(){
        List<AnnotationDO> annotations = annotationMapper.listByProjectId(this.projectId);
        return annotations;
    }

    public List<PredictionDO> getPredictionsFromDB(){
        List<PredictionDO> predictions = predictionMapper.listByProjectId(this.projectId);
        return predictions;
    }

    public float calculateCompletionPercentage(){
        return 0;
    }
}
