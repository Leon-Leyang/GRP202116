<template>
  <div>
    <button @click="prev">prev</button>

    <!-- <button @click="next">next</button> -->

    <!-- Create the Label Studio container -->
    <div id="label-studio"></div>
  </div>
</template>

<script>
// @ is an alias to /src
import LabelStudio from "@heartexlabs/label-studio";
import "@heartexlabs/label-studio/build/static/css/main.css";

export default {
  name: "Home",
  data() {
    return {
      labelStudio: '',
      id:0,
      config: `
      <View>

  <!-- Image with Polygons -->
  <View style="padding: 25px;
               box-shadow: 2px 2px 8px #AAA">
    <Header value="Label the image with polygons"/>
    <Image name="img" value="$image"/>
    <Text name="text1"
          value="Select label, start to click on image"/>

    <PolygonLabels name="tag" toName="img">
      <Label value="Airbus" background="blue"/>
      <Label value="Boeing" background="red"/>
    </PolygonLabels>
  </View>

  <!-- Text with multi-choices -->
  <View style="margin-top: 20px; padding: 25px;
               box-shadow: 2px 2px 8px #AAA;">
    <Header value="Classify the text"/>
    <Text name="text2" value="$text"/>

    <Choices name="" toName="img" choice="multiple">
      <Choice alias="wisdom" value="Wisdom"/>
      <Choice alias="long" value="Long"/>
    </Choices>
  </View>

</View>
`
    }
  },

  //inject: ["app"],
  methods: {
      prev() {
    this.labelStudio.destroy()
    this.labelStudio = new LabelStudio("label-studio", {
      config: this.config,

      interfaces: [
        "panel",
        "update",
        "submit",
        "skip",
        "controls",
        //"review",
        "infobar",
        "topbar",
        "instruction",
        "side-column",
        "ground-truth",
        "annotations:history",
        "annotations:tabs",
        "annotations:menu",
        "annotations:current",
        "annotations:add-new",
        "annotations:delete",
        'annotations:view-all',
        "predictions:tabs",
        "predictions:menu",
        "auto-annotation",
        "edit-history",
        //"topbar:prevnext",
      ],

      user: {
        pk: 1,
        firstName: "James",
        lastName: "Dean",
      },

      task: {
        annotations: [{
          id: "1001",
          lead_time: 15.053,
          result: [
            {
              "original_width": 2242,
              "original_height": 2802,
              "image_rotation": 0,
              "origin": "manual",
              "value": {
                "x": 17.46666666666666,
                "y": 79.29562433297758,
                "width": 23.6,
                "height": 13.447171824973319,
                "rotation": 0,
                "rectanglelabels": [
                  "Hello"
                ]
              },
              "id": "dYjaasY56i",
              "from_name": "tag",
              "to_name": "img",
              "type": "rectanglelabels"
            },
            {
              "original_width": 2242,
              "original_height": 2802,
              "image_rotation": 0,
              "origin": "prediction",
              "value": {
                "x": 37.46666666666666,
                "y": 79.29562433297758,
                "width": 23.6,
                "height": 13.447171824973319,
                "rotation": 0,
                "rectanglelabels": [
                  "World"
                ]
              },
              "id": "dYjaasY57i",
              "from_name": "tag",
              "to_name": "img",
              "type": "rectanglelabels"
            },
          ]
        }],
        predictions: [{
          id: "1001",
          lead_time: 15.053,
          result: [
            {
              "original_width": 2242,
              "original_height": 2802,
              "image_rotation": 0,
              "origin": "prediction",
              "value": {
                "x": 87.46666666666666,
                "y": 79.29562433297758,
                "width": 23.6,
                "height": 13.447171824973319,
                "rotation": 0,
                "rectanglelabels": [
                  "Hello"
                ]
              },
              "id": "dYjaasY56i",
              "from_name": "tag",
              "to_name": "img",
              "type": "rectanglelabels"
            },
          ]
        }],
        id: 1,
        data: {
          image:
              "https://cdn.mos.cms.futurecdn.net/C9JVYkqCELkdERaqd9gFbj-1200-80.jpg",
        },
      },


      onLabelStudioLoad: function (LS) {
        var c = LS.annotationStore.addAnnotation({
          userGenerate: true,
        });
        LS.annotationStore.selectAnnotation(c.id);
      },

      onUpdateAnnotation: function (LS, annotation) {
        // retrive an annotation 
        console.log(annotation.serializeAnnotation())
        console.log(annotation._initialAnnotationObj)
        console.log(JSON.stringify(annotation.serializeAnnotation()))
        console.log('this', LS.task)
        console.log('tag', this.LabelStudio.task)
        this.$axios.post('/annotations/data/1', annotation.serializeAnnotation())
      }
    });
    }
  },
  mounted() {
    this.labelStudio = new LabelStudio("label-studio", {
      config: this.config,

      interfaces: [
        "panel",
        "update",
        "submit",
        "skip",
        "controls",
        //"review",
        "infobar",
        "topbar",
        "instruction",
        "side-column",
        "ground-truth",
        "annotations:history",
        "annotations:tabs",
        "annotations:menu",
        "annotations:current",
        "annotations:add-new",
        "annotations:delete",
        'annotations:view-all',
        "predictions:tabs",
        "predictions:menu",
        "auto-annotation",
        "edit-history",
        //"topbar:prevnext",
      ],

      user: {
        pk: 1,
        firstName: "James",
        lastName: "Dean",
      },

      task: {
        annotations: [{
          id: "1001",
          lead_time: 15.053,
          result: [
            {
              "original_width": 2242,
              "original_height": 2802,
              "image_rotation": 0,
              "origin": "manual",
              "value": {
                "x": 17.46666666666666,
                "y": 79.29562433297758,
                "width": 23.6,
                "height": 13.447171824973319,
                "rotation": 0,
                "rectanglelabels": [
                  "Hello"
                ]
              },
              "id": "dYjaasY56i",
              "from_name": "tag",
              "to_name": "img",
              "type": "rectanglelabels"
            },
            {
              "original_width": 2242,
              "original_height": 2802,
              "image_rotation": 0,
              "origin": "prediction",
              "value": {
                "x": 37.46666666666666,
                "y": 79.29562433297758,
                "width": 23.6,
                "height": 13.447171824973319,
                "rotation": 0,
                "rectanglelabels": [
                  "World"
                ]
              },
              "id": "dYjaasY57i",
              "from_name": "tag",
              "to_name": "img",
              "type": "rectanglelabels"
            },
          ]
        }],
        predictions: [{
          id: "1001",
          lead_time: 15.053,
          result: [
            {
              "original_width": 2242,
              "original_height": 2802,
              "image_rotation": 0,
              "origin": "prediction",
              "value": {
                "x": 87.46666666666666,
                "y": 79.29562433297758,
                "width": 23.6,
                "height": 13.447171824973319,
                "rotation": 0,
                "rectanglelabels": [
                  "Hello"
                ]
              },
              "id": "dYjaasY56i",
              "from_name": "tag",
              "to_name": "img",
              "type": "rectanglelabels"
            },
          ]
        }],
        id: 2,
        data: {
          image:
              "https://htx-misc.s3.amazonaws.com/opensource/label-studio/examples/images/nick-owuor-astro-nic-visuals-wDifg5xc9Z4-unsplash.jpg",
        },
      },


      onLabelStudioLoad: function (LS) {
        var c = LS.annotationStore.addAnnotation({
          userGenerate: true,
        });
        LS.annotationStore.selectAnnotation(c.id);
      },

      onUpdateAnnotation: function (LS, annotation) {
        // retrive an annotation 
        console.log(annotation.serializeAnnotation())
        console.log(annotation._initialAnnotationObj)
        console.log(JSON.stringify(annotation.serializeAnnotation()))
        console.log('this', LS.task)
        console.log('tag', this.LabelStudio.task)
        this.$axios.post('/annotations/data/1', annotation.serializeAnnotation())
      }
    });
    console.log(this.labelStudio)
  },
};
</script>
