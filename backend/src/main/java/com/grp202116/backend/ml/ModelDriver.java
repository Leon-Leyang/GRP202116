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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.grp202116.backend.pojo.ModelDO;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * The class ModelDriver aim to run the ml model using PyTorch
 */
public class ModelDriver {
    ModelDO model;

    public static final List<String> ToolTags = Arrays.asList(new String[]{"Labels", "Choices"});

    public ModelDriver(ModelDO model){
        this.model = model;
    }

    public ParsedConfig parseConfig(){
        String config = "<View>" +
                "        <Labels name=\"label\" toName=\"text\">\n" +
                "          <Label value=\"Date\"></Label>\n" +
                "          <Label value=\"Time\"></Label>\n" +
                "          <Label value=\"Location\"></Label>\n" +
                "        </Labels>\n" +
                "        <Text name=\"text\" value=\"$text\"></Text>\n" +
                "      </View>";


        // Object to store information extracted from config
        ParsedConfig parsedConfig = new ParsedConfig();

        try {

            // Create mew DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Create Document from the config
            byte[] configBytes = config.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream configBuffer = new ByteArrayInputStream(configBytes);
            Document doc = builder.parse(configBuffer);

            doc.getDocumentElement().normalize();

            // Visit all child nodes rooted at <View>
            NodeList childNodes = doc.getDocumentElement().getChildNodes();
            for (int index = 0; index < childNodes.getLength(); index++) {
                Node node = childNodes.item(index);

                // Filter TEXT_NODE
                if(node.getNodeType() == Node.ELEMENT_NODE){

                    // Find the tool tag
                    if(ToolTags.contains(node.getNodeName())){
                        Element element = (Element)node;
                        parsedConfig.setFromName(element.getAttribute("name"));
                        parsedConfig.setToName(element.getAttribute("toName"));
                        parsedConfig.setType(node.getNodeName().toLowerCase());
                    }
                }

            }
            System.out.println("from_name: " + parsedConfig.getFromName());
            System.out.println("to_name: " + parsedConfig.getToName());
            System.out.println("type: " + parsedConfig.getType());




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return parsedConfig;
    }

    public void runModel(){

        ParsedConfig parsedConfig = parseConfig();

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
//                System.out.println(classifications);
//                System.out.println(classifications.best().getClassName());
//                System.out.println(classifications.best().getProbability());

                String id = UUID.randomUUID().toString();

                Value value = new Value();
                value.setChoices(new String[]{classifications.best().getClassName()});
                value.setScore(classifications.best().getProbability());

                ResultItem resultItem = new ResultItem();
                resultItem.setId(id);
                resultItem.setFrom_name(parsedConfig.getFromName());
                resultItem.setTo_name(parsedConfig.getToName());
                resultItem.setType(parsedConfig.getType());
                resultItem.setValue(value);

                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(resultItem);

                System.out.println(json);
            } catch (TranslateException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
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
        //modelDriver.parseConfig();
    }
}
