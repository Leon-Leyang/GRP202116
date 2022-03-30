<!-- 正常标注界面 -->
<template>
<div>
  <div>
    <button @click="test" style="width:100px;color:red">prev</button>

    <button @click="next" style="width:100px;color:red">next</button>
  </div>
  <div style="display:flex">
    <div style="width:10%">
      <el-table
        :data="tableData"
        style="width: 100%"
        :default-sort = "{prop: 'id', order: 'ascending'}" 
        v-cloak
        stripe
        >
        <el-table-column label="Preview" prop="url" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.url" @click="enterData(scope.row.dataId)" style="width:100%"/>
          </template>
        </el-table-column> 

        <!-- <el-table-column label="Annotation" width="100" prop="anno" align="center"></el-table-column>  -->
        <!-- <el-table-column label="Prediction" width="100" prop="predi" align="center"></el-table-column>        -->
      </el-table>
    </div>

    <!-- Create the Label Studio container -->

    <div id="label-studio"></div>
  </div>
</div>
</template>

<script>
// @ is an alias to /src
import LabelStudio from "@heartexlabs/label-studio";
import "@heartexlabs/label-studio/build/static/css/main.css";
import axios  from "axios";

export default {
  name: "Home",
  props:{
    configInterface: String,
    imageURL: String,
    imageId: Number,

  },
  data() {
    return {
      tableData:[],
      labelStudio: '',
      currentDataId:0,
      LS:[],
      projectId:0,
      annoDataList:[],
      annoPerResult:[],
      annos:[],
      annoIdList:[],
      //test param
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
      `,
      //logic param
      // config: this.configInterface,
    }
  },

  methods: {
    enterData(dataId){
      console.log('dataId', dataId)
      this.$store.state.currentDataId = dataId
      console.log('cur', this.$store.state.currentDataId)
      console.log('culist', this.$store.state.currentDataList)
      this.newLS(dataId)
    },
    prev(){
      this.$store.state.currentDataId--
      this.newLS(this.$store.state.currentDataId)
    },
    next(){
      this.$store.state.currentDataId++
      this.newLS(this.$store.state.currentDataId)
    },
    test(){
              this.$router.push({  
                    path: '/test',
                    name: 'Test',  
                }) 
    },
    getAnno(){
      console.log("currentid",this.$store.state.currentDataId)
      this.$axios.get('/annotation/data/' + this.$store.state.currentDataId, {
              params: {
                  // page: this.config.page,
              }
          })
          .then(res => {
            console.log('Anno', res)
              this.annoDataList = res.data.map(item => {
                  return {...item}
              })
              for(let i = 0; i < this.annoDataList.length; i++){
                this.annoIdList[i] = this.annoDataList[i].annotation_id
              }
              var arr=[]
              for(let i = 0; i < this.annoIdList.length; i++){
                if(arr.indexOf(this.annoIdList[i]) == -1){
                  arr.push(this.annoIdList[i]); 
                }
              }
              this.annoIdList = arr
              for(let j = 0; j < this.annoIdList.length; j++){
                for(let i = 0; i < this.annoDataList.length; i++){
                  if(this.annoDataList[i].annotation_id == this.annoIdList[j]){
                    this.annoPerResult[i].original_width = this.annoDataList[i].original_width
                    this.annoPerResult[i].original_height = this.annoDataList[i].original_height
                    this.annoPerResult[i].value = this.annoDataList[i].value
                    this.annoPerResult[i].id = this.annoDataList[i].result_id
                    this.annoPerResult[i].from_name = this.annoDataList[i].from_name
                    this.annoPerResult[i].to_name = this.annoDataList[i].to_name
                    this.annoPerResult[i].type = this.annoDataList[i].type
                  }
                  this.annos[j].id = this.annoIdList[j]
                  this.annos[j].result = this.annoPerResult
                  this.annoPerResult = []
                }                
              }
              // this.config.total = res.data.length
              // this.config.loading = false
              console.log(" this.annos", this.annos)
          // console.log("dew",row)

          })
          .catch((error) => {
  // here you will have access to error.response
              console.log(error.response)
          });
    },


    newLS(dataId){
      this.labelStudio.destroy()
      this.labelStudio = new LabelStudio("label-studio", {
        config: this.config,
        interfaces: [
          "panel",
          "update",
          "submit",
          "skip",
          "controls",
          "review",
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
          id: dataId,
          data: {
            image: this.$store.state.currentDataList[dataId - 1].url
          },
        },

  //steam vr  lagecy vr

        onSubmitAnnotation: function(ls, annotation) {
          console.log(annotation)
          console.log('ls', ls)
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
        },
      });
    }
  },


  mounted() {
    this.getAnno(),
    this.currentDataId = this.$store.state.currentDataId
    console.log('project', this.$store.state.currentProject)
    this.projectId = this.$store.state.currentProjectId
    this.tableData = this.$store.state.currentDataList
    console.log('tisada', this.tableData)
      this.labelStudio = new LabelStudio("label-studio", {
        config: this.config,

        interfaces: [
          "panel",
          "update",
          "submit",
          "skip",
          "controls",
          // "review", //==> the problem to show update!!!
          "infobar",
          "topbar",
          "instruction",
          "side-column",
          "ground-truth",
          "annotations:history",
          "annotations:tabs",
          "annotations:menu",
          // "annotations:current", //==>show the name in the rightbar
          "annotations:add-new",
          "annotations:delete",
          'annotations:view-all',
          "predictions:tabs", //==> Reject | Fix+Accept
          "predictions:menu", //==> Reject | Accept
          // "auto-annotation",
          // "edit-history",
          "topbar:prevnext",
        ],

        user: {
          pk: 1,
          firstName: "James",
          lastName: "Dean",
        },

        task: {
          annotations: this.annos,
          predictions: [{
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
          id: 2,
          data: {
            // image:"https://www.helpguide.org/wp-content/uploads/king-charles-spaniel-resting-head-768.jpg" 
            image: this.$store.state.currentDataList[this.$store.state.currentDataId - 1].url
          },
        },


        // onLabelStudioLoad: function (LS) {
        //   var c = LS.annotationStore.addAnnotation({
        //     userGenerate: true,
        //   });
        //   LS.annotationStore.selectAnnotation(c.id);
        // },

        onUpdateAnnotation: function (LS, annotation) {
          // retrive an annotation 
          console.log('tag', LS,annotation)
        
          var myDate = new Date();
          myDate.toLocaleDateString();                    
          myDate.toLocaleString( ); 
          // var id = this.projectId;
          var annotationObject = annotation.serializeAnnotation()
          var previousAnnotations= LS.task.app.annotationStore.annotations.target
          console.log('tin', annotationObject)
          var annotationlist = []
          for(var i = 0; i < annotationObject.length; i++){
            annotationlist[i]=new Object()
            annotationlist[i].result_id = annotationObject[i].id
            // annotationlist[i].project_id = this.projectId
            annotationlist[i].data_id = LS.task.id
            annotationlist[i].type = annotationObject[i].type
            annotationlist[i].result = annotationObject[i].value
            annotationlist[i].create_time = myDate.toLocaleString()
            annotationlist[i].annotation_id = annotation.pk
            annotationlist[i].original_width = annotation._initialAnnotationObj[i].original_width
            annotationlist[i].original_height = annotation._initialAnnotationObj[i].original_height
            
            for(annotation in previousAnnotations ){
              if(annotation.value.id==annotationObject[i].id){
                  annotationlist[i].create_time = annotation.value.createdDate
              }
            }
            
            annotationlist[i].update_time = myDate.toLocaleString()
            annotationlist[i].from_name = annotationObject[i].from_name
            annotationlist[i].to_name = annotationObject[i].to_name          
          }

        
          console.log('nuy',annotationlist)
          console.log(annotation.serializeAnnotation())
          
          // console.log('this', LS.task)
          // console.log('tag', this.LabelStudio.task)
          axios.put('/annotation/data/'+ LS.task.id, annotationlist)
              .then((res) => {
                    console.log('annotation', res)
              })
          
          
          // this.$axios.post('/annotations/data/1', annotationlist)

        }
      });
      this.labelStudio.destroy()
    console.log(this.labelStudio.options)
    this.$store.state.nowLS = this.labelStudio
  },
};
</script>
