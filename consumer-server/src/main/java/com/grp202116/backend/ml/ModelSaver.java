package com.grp202116.backend.ml;

import ai.djl.training.util.DownloadUtils;
import ai.djl.training.util.ProgressBar;
import com.grp202116.backend.pojo.ModelDO;

import java.io.IOException;

/**
 *
 */
public class ModelSaver {
    private ModelDO model = new ModelDO();

    public ModelSaver(ModelDO model) {
        this.model = model;
    }

    public void save() {
        //String path = "ml/" + model.getEngine() + "/" + model.getName();
//        try {
//            DownloadUtils.download(model.getUrl(), path, new ProgressBar());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
