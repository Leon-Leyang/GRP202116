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

public class ModelDriver {
    ModelDO model;

    public ModelDriver(ModelDO model){
        this.model = model;
    }

    public void runModel(){

        // Define the preprocessing pipeline
        Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                .addTransform(new Resize(256))
                .addTransform(new CenterCrop(224, 224))
                .addTransform(new ToTensor())
                .addTransform(new Normalize(
                        new float[] {0.485f, 0.456f, 0.406f},
                        new float[] {0.229f, 0.224f, 0.225f}))
                .optApplySoftmax(true)
                .build();

//        Criteria<Image, Classifications> criteria = Criteria.builder()
//                .setTypes(Image.class, Classifications.class) // defines input and output data type
//                .optModelUrls(model.getUrl())
//                .optModelName(model.getName()) // specify model file prefix
//                .optEngine("PyTorch")
//                .optProgress(new ProgressBar())
//                .build();


        // Define the criteria of the model
//        Criteria<Image, Classifications> criteria = Criteria.builder()
//                .setTypes(Image.class, Classifications.class)
//                .optModelUrls(model.getUrl())
//                .optOption("mapLocation", "true") // this model requires mapLocation for GPU
//                .optTranslator(translator)
//                .optProgress(new ProgressBar()).build();
        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optModelPath(Paths.get("C:/Users/Leon/Desktop/GRP/GRP202116/ml/models/resnet18"))
                .optOption("mapLocation", "true") // this model requires mapLocation for GPU
                .optTranslator(translator)
                .optProgress(new ProgressBar()).build();


        // Get the model from the criteria
        ZooModel<Image, Classifications> zooModel = null;
        try {
            zooModel = criteria.loadModel();
        } catch (IOException | ModelNotFoundException | MalformedModelException e) {
            e.printStackTrace();
        }


        // Get the test sample
        Image img = null;
        try {
            img = ImageFactory.getInstance().fromFile(Paths.get("C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/testing.jpg"));
            img.getWrappedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Predict on the test sample
        try{
            Predictor<Image, Classifications> predictor = zooModel.newPredictor();
            try {
                Classifications classifications = predictor.predict(img);
                System.out.println(classifications);
                System.out.println(classifications.best().getClassName());
                System.out.println(classifications.best().getProbability());
            } catch (TranslateException e) {
                e.printStackTrace();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }











    }







    public void updatePredictions() {
        //
    }

    public static void main(String[] args){
        ModelDO model = new ModelDO();
        ModelDriver modelDriver = new ModelDriver(model);
        modelDriver.runModel();
    }
}
