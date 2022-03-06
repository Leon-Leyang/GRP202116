<template>
  <div>
    <!-- Create the Label Studio container -->
    <div id="label-studio"></div>
    <button @click="test">test</button>
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
    }
  },

  //inject: ["app"],
  methods: {
    test() {
      console.log(this.labelStudio)
    }
  },
  mounted() {
    this.labelStudio = new LabelStudio("label-studio", {
      config: `
      <View>
        <Image name="img" value="$image"></Image>
        <RectangleLabels name="tag" toName="img">
          <Label value="Hello"></Label>
          <Label value="World"></Label>
        </RectangleLabels>
      </View>
    `,

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
        this.$axios.post('/annotations/data/1', annotation.serializeAnnotation())
      }
    });
    console.log(this.labelStudio)
  },
};
</script>
