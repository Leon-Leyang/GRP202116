<!-- 正常标注界面 -->
<template>
<div>
  <div>
    <button @click="back" style="width:100px;color:red">Back</button>

    <button @click="prev" style="width:100px;color:red">prev</button>

    <button @click="next" style="width:100px;color:red">next</button>

    <button @click="accept" style="width:100px;color:red">accept</button>

    <button @click="reject" style="width:100px;color:red">reject</button>
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
            <img :src="scope.row.url" @click="enterData(scope.row)" style="width:100%"/>
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
      annos:[{id:null,result:null}],
      predictDataList:[],
      predicts:[{id:null,result:null}],
      preState: 'accept',
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
    enterData(data){
      console.log('dataId', data.dataId)
      this.$store.state.currentDataId = data.dataId
      this.$store.state.realDataId = data.realDataId
      console.log('cur', this.$store.state.currentDataId,this.$store.state.realDataId)
      console.log('culist', this.$store.state.currentDataList)
      this.newLS(data)
    },
    prev(){
      this.$store.state.currentDataId--
      this.$store.state.realDataId = this.$store.state.currentDataList[this.$store.state.currentDataId-1]
      this.newLS(this.$store.state.currentDataList[this.$store.state.currentDataId - 1])
    },
    next(){
      this.$store.state.currentDataId++
      this.$store.state.realDataId = this.$store.state.currentDataList[this.$store.state.currentDataId-1]
      this.newLS(this.$store.state.currentDataList[this.$store.state.currentDataId - 1])
    },
    accept(){
      this.preState = 'accept'
      // this.$axios.post('prediction/'+{predictionId}/{status})
      //接口
      this.next()
    },
    reject(){
      this.preState = 'reject'
      //接口
      this.next()
    },
    back(){
      this.$router.push({  
                  path: '/per-project',
                  name: 'PerProject', 
              }) 
    },
    getAnno(){
      console.log("currentid",this.$store.state.realDataId)
      this.$axios.get('/annotation/data/' + this.$store.state.realDataId, {
              params: {
                  // page: this.config.page,
              }
          })
          .then(res => {
            console.log('Anno', res)
            this.annoDataList = res.data.map(item => {
                return {...item}
            })
            for(var i = 0; i < this.annoDataList.length; i++){
              console.log('i', this.annoDataList[i])
              this.annos[i].id = this.annoDataList[i].annotationId
              this.annos[i].result = eval(this.annoDataList[i].result)
              console.log('tag', this.annos[i].result)

            }
            console.log('annos:', this.annos)
          })
          .catch((error) => {
  // here you will have access to error.response
              console.log(error.response)
          });
    },
    getPredict(){
      console.log("currentid",this.$store.state.realDataId)
      this.$axios.get('/prediction/data/' + this.$store.state.realDataId, {
              params: {
                  // page: this.config.page,
              }
          })
          .then(res => {
            console.log('Predict', res)
            this.predictDataList = res.data.map(item => {
                return {...item}
            })
            console.log('pre', this.predictDataList)
            for(var j = 0; j < this.predictDataList.length; j++){
              console.log('j', j)
              this.predicts[j].id = this.predictDataList[j].predictionId
              console.log('jas', this.predictDataList[j].predictionId)
              
              this.predicts[j].result = eval(this.predictDataList[j].result)
            console.log('preasd', this.predictDataList[j].result)

            }
            console.log('predicts:', this.predicts)            
          })
          .catch((error) => {
  // here you will have access to error.response
              console.log(error.response)
          });
    },

    newLS(data){
      this.getAnno()
      this.getPredict()
      this.labelStudio.destroy()
      this.labelStudio = new LabelStudio("label-studio", {
        config: this.$store.state.currentConfig,
        interfaces: [
          "panel",
          "update",
          "submit",
          // "skip",
          "controls",
          // "review",
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
          annotations: this.annos,
          predictions: this.predicts,
          id: this.$store.state.realDataId,
          data: {
            image: this.$store.state.currentDataList[data.dataId - 1].url
          },
        },

  //steam vr  lagecy vr

        onSubmitAnnotation: function(ls, annotation) {
          var result = annotation.serializeAnnotation()
          var annotationlist = [{"createTime":null,"projectId":0,"dataId":0,"type":null,"updateTime":null,"result":null,"annotationId":1}]
          // annotationlist[0].annotationId = annotation.pk
          annotationlist[0].dataId = ls.task.id
          annotationlist[0].result = JSON.stringify(result)
          console.log('re', annotationlist)

          axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist)
          .then((res)=>{
            console.log('annotalist sub', res)
          })
        },

        onLabelStudioLoad: function (LS) {
          var c = LS.annotationStore.addAnnotation({
            userGenerate: true,
          });
          LS.annotationStore.selectAnnotation(c.id);
        },

        onUpdateAnnotation: function (LS, annotation) {
          // retrive an annotation 
          console.log('xiaw', annotation.serializeAnnotation())
          console.log('LS', LS)
          console.log('annotation',annotation)

          
          var result = annotation.serializeAnnotation()
          var annotationlist = [{"createTime":null,"projectId":0,"dataId":0,"type":null,"updateTime":null,"result":null,"annotationId":1}]
          // annotationlist[0].annotationId = annotation.pk
          annotationlist[0].dataId = LS.task.id
          annotationlist[0].result = JSON.stringify(result)
          console.log('re', annotationlist)

          axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist)
          .then((res)=>{
            console.log('annotalist up', res)
          })
        },
      });
    },
  
  },


  mounted() {
    this.getAnno()
    this.getPredict()
    this.currentDataId = this.$store.state.currentDataId
    this.tableData = this.$store.state.currentDataList
    console.log('curren', this.$store.state.currentDataList,this.$store.state.currentDataId)
    console.log('tisada', this.tableData)
    // console.log('confi', this.$store.state.currentConfig)
    console.log('imag', this.$store.state.currentDataList[this.$store.state.currentDataId - 1].url)
    clearTimeout(this.timer);
    this.timer = setTimeout(()=>{
      console.log('this', this.annos[0].result.length)
      if(this.annos[0].result.length == 0){
        this.annos = this.predicts
      }
      this.labelStudio = new LabelStudio("label-studio", {
        // config: this.config,
        config: this.$store.state.currentConfig,
        interfaces: [
          "panel",
          "update",
          "submit",
          // "skip",
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
          predictions: this.predicts,
          id: this.$store.state.realDataId,
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
        onSubmitAnnotation: function(ls, annotation) {
          var result = annotation.serializeAnnotation()
          var annotationlist = [{"createTime":null,"projectId":0,"dataId":0,"type":null,"updateTime":null,"result":null,"annotationId":1}]
          // annotationlist[0].annotationId = annotation.pk
          annotationlist[0].dataId = ls.task.id
          annotationlist[0].result = JSON.stringify(result)
          console.log('re', annotationlist)

          axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist)
          .then((res)=>{
            console.log('annotalist sub', res)
          })
        },

        onUpdateAnnotation: function (LS, annotation) {
          // retrive an annotation 
          console.log('xiaw', annotation.serializeAnnotation())
          console.log('LS', LS)
          console.log('annotation',annotation)

          
          var result = annotation.serializeAnnotation()
          var annotationlist = [{"createTime":null,"projectId":0,"dataId":0,"type":null,"updateTime":null,"result":null,"annotationId":1}]
          // annotationlist[0].annotationId = annotation.pk
          annotationlist[0].dataId = LS.task.id
          annotationlist[0].result = JSON.stringify(result)
          console.log('re', annotationlist)

          axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist)
          .then((res)=>{
            console.log('annotalist up', res)
          })
        },
      });
    },500)
    console.log(this.labelStudio.options)
    this.$store.state.nowLS = this.labelStudio
  },
};
</script>
