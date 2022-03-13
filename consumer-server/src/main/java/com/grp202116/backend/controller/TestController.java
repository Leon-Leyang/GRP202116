package com.grp202116.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.grp202116.backend.util.HttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

/**
 * Just for test
 */
@RestController
public class TestController {

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/java-user")
    public String JavaUser() {
        return "{'username': 'java', 'password': 'java'}";
    }

    @RequestMapping("/python-user")
    public String PythonUser() {
        return restTemplate.getForEntity("http://sidecar-server/getUser", String.class).getBody();
    }

    @GetMapping("/model/{modelId}")
    public String ModelTest(@PathVariable BigInteger modelId) {
        restTemplate.getForEntity("http://sidecar-server/model/" + modelId, String.class).getBody();
        System.out.println(restTemplate.getForEntity("http://sidecar-server/model/" + modelId, String.class).getBody());
        return "{Success}";
    }

    //@PathVariable BigInteger modelId
    //project_type, data, configs, predictions, model_path, model_version, labels):
    @PostMapping("/model")
    public String runModel() {
        String configs = "<View>" +
                "  <Image name=\" image \" value=\" $image \" zoom=\" true \"/>" +
                " <BrushLabels name=\" tag \" toName=\" image \">" +
                "     <Label value=\" Airplane \" background=\" rgba(255, 0, 0, 0.7) \"/>" +
                "                               <Label value=\" Car \" background=\" rgba(0, 0, 255, 0.7) \"/>" +
                "                           </BrushLabels>" +
                "                       </View>";

        String labels = "'Background', 'Aeroplane', 'Bicycle', 'Bird', 'Boat', 'Bottle', 'Bus', 'Car', 'Cat', 'Chair', 'Cow'," +
                "'Dining table', 'Dog', 'Horse', 'Motorbike', 'Person', 'Potted plant', 'Sheep', 'Sofa', 'Train'," +
                " 'Tv/monitor'";


        JSONObject param = new JSONObject();
        param.put("project_type", "Semantic Segmentation Mask");
        param.put("data", "./puppy.webp");
        param.put("configs", configs);
        param.put("predictions", "");
        param.put("model_path", "../ml/models/fcn/fcn.pth");
        param.put("model_version", "undefined");
        param.put("labels", labels);

        String response = restTemplate.postForObject("http://sidecar-server/model",
                HttpUtils.parseJsonToFlask(JSONObject.toJSONString(param)), String.class);
        System.out.println(JSONObject.parse(response));
        return response;
    }
}