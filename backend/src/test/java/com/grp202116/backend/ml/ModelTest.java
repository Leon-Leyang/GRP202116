package com.grp202116.backend.ml;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.*;
import java.awt.image.*;

import ai.djl.*;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.util.*;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import ai.djl.training.util.*;

import java.io.IOException;

public class ModelTest {
    public static void main(String[] args) {
//        try {
//            DownloadUtils.download("https://djl-ai.s3.amazonaws.com/mlrepo/model/cv/image_classification/ai/djl/pytorch/resnet/0.0.1/traced_resnet18.pt.gz",
//                    "build/pytorch_models/resnet18/resnet18.pt", new ProgressBar());
//            DownloadUtils.download("https://djl-ai.s3.amazonaws.com/mlrepo/model/cv/image_classification/ai/djl/pytorch/synset.txt",
//                    "build/pytorch_models/resnet18/synset.txt", new ProgressBar());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
//                .addTransform(new Resize(256))
//                .addTransform(new CenterCrop(224, 224))
//                .addTransform(new ToTensor())
//                .addTransform(new Normalize(
//                        new float[]{0.485f, 0.456f, 0.406f},
//                        new float[]{0.229f, 0.224f, 0.225f}))
//                .optApplySoftmax(true)
//                .build();
        Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                .addTransform(new Resize(256))
                .addTransform(new CenterCrop(224, 224))
                .addTransform(new ToTensor())
                .addTransform(new Normalize(
                        new float[]{0.485f, 0.456f, 0.406f},
                        new float[]{0.229f, 0.224f, 0.225f}))
                .optApplySoftmax(true)
                .build();

        Criteria<String, Classifications> criteria = Criteria.builder()
                .setTypes(String.class, Classifications.class) // defines input and output data type
                //.optTranslator(translator)
                .optModelPath(Paths.get("ml/models/cnn")) // search models in specified path
                //.optModelName("best_steps_3000") // specify model file prefix
                .optProgress(new ProgressBar())
                .optEngine("PyTorch")
                .build();

        ZooModel<String, Classifications> model = null;
        try {
            model = criteria.loadModel();
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

        Predictor<String, Classifications> predictor = model.newPredictor();
        Classifications classifications = null;
        try {
            classifications = predictor.predict("img");
        } catch (TranslateException e) {
            e.printStackTrace();
        }

        System.out.println(classifications);
    }
}
