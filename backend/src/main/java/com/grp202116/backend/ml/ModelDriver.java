package com.grp202116.backend.ml;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.CenterCrop;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import com.grp202116.backend.pojo.ModelDO;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * The class ModelDriver aim to run the ml model using PyTorch
 */
public class ModelDriver {
    ModelDO model;

    public ModelDriver(ModelDO model){
        this.model = model;
    }

    public void runModel(){

        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class) // defines input and output data type
                .optModelUrls(model.getUrl())
                .optModelName(model.getName()) // specify model file prefix
                .optEngine("PyTorch")
                .optProgress(new ProgressBar())
                .build();

        ZooModel<Image, Classifications> zooModel = null;
        try {
            zooModel = criteria.loadModel();
        } catch (IOException | ModelNotFoundException | MalformedModelException e) {
            e.printStackTrace();
        }

        Image img = null;
        try {
            img = ImageFactory.getInstance().fromFile(Paths.get("ml/resources/testing.jpg"));
            img.getWrappedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Predictor<Image, Classifications> predictor = zooModel.newPredictor();
        Classifications classifications = null;
        try {
            classifications = predictor.predict(img);
        } catch (TranslateException e) {
            e.printStackTrace();
        }

        System.out.println(classifications);
    }

    public void updatePredictions() {
        //
    }
}
