<!--用代码设置label interface-->
<template>
<div>
  <div style="display:flex;height:540px;width:100%">
      <!-- Two-way Data-Binding -->
  <!-- <codemirror v-model="code" :options="cmOptions" /> -->

    <!-- Or manually control the data synchronization -->
  <div style="width:40%;margin-left:20px;height:100%">
    <codemirror
      ref="cmEditor"
      :value="code"
      :options="cmOptions"
      @ready="onCmReady"
      @focus="onCmFocus"
      @input="onCmCodeChange"
    />
  </div>
  <v-container
    id="scroll-target"
    style="width:60%;margin-right:30px;height:100%"
    class="overflow-y-auto"
  >
    <v-row
      v-scroll:#scroll-target="onScroll"
      align="center"
      style="height: 1000px"
    >
    <div>
      <div id="label-studio" style="width:60%;margin-right:30px"></div>
    </div>
    </v-row>
  </v-container>

    <!-- <button @click="prev">prev</button> -->

    <!-- <button @click="next">next</button> -->

    <!-- Create the Label Studio container -->
  </div>
</div>
</template>

<script>
// @ is an alias to /src
import LabelStudio from "@heartexlabs/label-studio";
import "@heartexlabs/label-studio/build/static/css/main.css";
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'

// import language js
import 'codemirror/mode/javascript/javascript.js'

// import theme style
import 'codemirror/theme/base16-dark.css'

// import more 'codemirror/some-resource...'


export default {
  name: "Home",
  component:{
    codemirror,
  },
  data() {
    return {

      //codemirror para
      code: '',
      cmOptions: {
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'base16-dark',
        lineNumbers: true,
        line: true,
        // more CodeMirror options...
      },
      labelStudio: '',
      id:0,
      config: ``,
      offsetTop: 0,
    }
  },

  //inject: ["app"],
  methods: {
    onScroll (e) {
      this.offsetTop = e.target.scrollTop
    },
    onCmReady(cm) {
      console.log('the editor is readied!', cm)
    },
    onCmFocus(cm) {
      console.log('the editor is focused!', cm)
    },
    onCmCodeChange(newCode) {
      console.log('this is new code', newCode)
      this.code = newCode
      this.config = newCode
      this.$store.state.currentConfig = this.config
      this.labelStudio.destroy()
      this.labelStudio = new LabelStudio("label-studio", {
        config: this.$store.state.currentConfig,

        interfaces: [
          "panel",
          // "update",
          // "submit",
          // "skip",
          // "controls",
          //"review",
          // "infobar",
          // "topbar",
          // "instruction",
          // "side-column",
          // "ground-truth",
          // "annotations:history",
          // "annotations:tabs",
          // "annotations:menu",
          // "annotations:current",
          // "annotations:add-new",
          // "annotations:delete",
          // 'annotations:view-all',
          // "predictions:tabs",
          // "predictions:menu",
          // "auto-annotation",
          // "edit-history",
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
    },
    confirmConfig() {
        this.$router.push({  
                    path: '/per-project',
                    name: 'PerProject', 
                }) 
    }
  },
  computed: {
    codemirror() {
      return this.$refs.cmEditor.codemirror
    }
  },
  mounted() {
    this.code = this.$store.state.currentConfig
    this.config = this.$store.state.currentConfig
    console.log('tagnow', this.config)
    this.labelStudio = new LabelStudio("label-studio", {
      config: this.config,

      interfaces: [
        "panel",
        // "update",
        // "submit",
        // "skip",
        "controls",
        //"review",
        "infobar",
        // "topbar",
        // "instruction",
        // "side-column",
        // "ground-truth",
        // "annotations:history",
        // "annotations:tabs",
        // "annotations:menu",
        // "annotations:current",
        // "annotations:add-new",
        // "annotations:delete",
        // 'annotations:view-all',
        // "predictions:tabs",
        // "predictions:menu",
        // "auto-annotation",
        // "edit-history",
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
    console.log(this.labelStudio);
    console.log('the current CodeMirror instance object:', this.codemirror)
  },
};
</script>

<style>
.CodeMirror-scroll {
  overflow: scroll !important;
  margin-bottom: 0;
  margin-right: 0;
  padding-bottom: 0;
  height: 100%;
  outline: none;
  position: relative;
  border: 1px solid #dddddd;
}
</style>
<style scoped>
.code-mirror{
  font-size : 13px;
  line-height : 150%;
  text-align: left;
}
</style>