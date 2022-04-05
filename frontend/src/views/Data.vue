<!-- 正常标注界面 -->
<template>
<div>
  <div>
    <button @click="back" style="width:100px;color:red">Back</button>

    <button @click="prev" style="width:100px;color:red">prev</button>

    <button @click="next" style="width:100px;color:red">next</button>

  </div>
  <div style="display:flex">
    <div id="label-studio" style="width: 90%"></div>
    <div style="width: 10%">
      <v-card
        elevation="16"
        max-width="400"
        class="mx-auto"
      >
        <v-virtual-scroll
          :bench="benched"
          :items="tableData"
          height="650"
          item-height="64"
        >
          <template v-slot:default="{ item }">
            <v-list-item :key="item">
              <v-list-item-action>
              <img :src="item.url" @click="enterData(item)" style="width:100%" v-if="dataType == 'image'" />
              <div  style="width:120px;white-space:nowrap;overflow:hidden;">
                <span v-if="dataType == 'text'" @click="enterData(item)">{{item.url}}</span>
              </div>
              </v-list-item-action>
            </v-list-item>

            <v-divider></v-divider>
          </template>
        </v-virtual-scroll>
      </v-card>
    </div>

    <!-- Create the Label Studio container -->

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
      annos:[],
      predictDataList:[],
      predicts:[],
      preState: 'accept',
      //test param
      id:0,
      config: ``,
      dataType:'',
      //logic param
      // config: this.configInterface,
    }
  },

  methods: {
    enterData(data){
      this.predicts.splice(0, this.predicts.length) 
      this.annos.splice(0, this.annos.length)  
      console.log('annopre', this.annos,this.predicts) 

      console.log('dataId', data.dataId)
      this.$store.state.currentDataId = data.dataId
      this.$store.state.realDataId = data.realDataId
      console.log('cur', this.$store.state.currentDataId,this.$store.state.realDataId)
      console.log('culist', this.$store.state.currentDataList)
      this.newLS(data)
      console.log('enter', data)
    },
    prev(){
      this.predicts.splice(0, this.predicts.length) 
      this.annos.splice(0, this.annos.length)  
      console.log('annopre', this.annos,this.predicts) 

      if(this.$store.state.currentDataId == 1){
        console.log('no prev')
      }else{
        this.$store.state.currentDataId--
        this.$store.state.realDataId = this.$store.state.currentDataList[this.$store.state.currentDataId-1].realDataId
        console.log('prev', this.$store.state.realDataId, this.$store.state.currentDataList[this.$store.state.currentDataId - 1])
        this.newLS(this.$store.state.currentDataList[this.$store.state.currentDataId - 1])        
      }

    },
    next(){
      this.predicts.splice(0, this.predicts.length) 
      this.annos.splice(0, this.annos.length)    
      console.log('annopre', this.annos,this.predicts) 
      if(this.$store.state.currentDataId == (this.$store.state.currentDataList.length)){
        console.log('no next')
      }else{
        this.$store.state.currentDataId++
        this.$store.state.realDataId = this.$store.state.currentDataList[this.$store.state.currentDataId-1].realDataId
        console.log('next', this.$store.state.realDataId, this.$store.state.currentDataList[this.$store.state.currentDataId - 1])
        this.newLS(this.$store.state.currentDataList[this.$store.state.currentDataId - 1])        
      }
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
            console.log('Anno', res,this.annos)
            this.annoDataList = res.data.map(item => {
                return {...item}
            })
            console.log('Anno', this.annoDataList)

            for(var i = 0; i < this.annoDataList.length; i++){
              console.log('i', i)
              this.annos.push({id: this.annoDataList[i].annotationId, result: eval(this.annoDataList[i].result)})
            }
            // this.annos = JSON.parse(this.annos)
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
              this.predicts.push({id: this.predictDataList[j].predictionId, result: eval(this.predictDataList[j].result)})
            }
            console.log('predicts:', this.predicts)            
          })
          .catch((error) => {
  // here you will have access to error.response
              console.log(error.response)
          });
    },

    newLS(data){
      var temp = this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated
      if(this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated == 0 | this.$store.state.currentDataList[this.$store.state.currentDataId -1].predicted == 0 ){
        if(this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated == 0){
          var annotationlist = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
          annotationlist[0].dataId = this.$store.state.realDataId
          axios.put('/annotation/data/' + this.$store.state.realDataId, annotationlist[0])
          .then((res)=>{
            console.log('annotalist up', res)
          })
          clearTimeout(this.timer);
          this.timer = setTimeout(()=>{
            this.getAnno()
          },100)
        }
        console.log('t/f', this.$store.state.currentDataList[this.$store.state.currentDataId -1].predicted == 0)
        if(this.$store.state.currentDataList[this.$store.state.currentDataId -1].predicted == 0){
          this.predicts = []
          if(temp != 0){
            this.getAnno()
          }
          console.log('pre', this.predicts )
        }
      }else{
        this.getAnno()
        this.getPredict()
      }
      console.log('des', '')
      this.labelStudio.destroy()
      console.log('this', this.annos)
      if(this.annos == [] & this.predicts != []){
        this.annos = this.predicts
      }
      console.log('anno,pre',this.annos,this.predicts)
      if(this.dataType == 'image'){
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

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
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

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
            .then((res)=>{
              console.log('annotalist up', res)
            })
          },
        });
      }else{
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
              text: this.$store.state.currentDataList[data.dataId - 1].url
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

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
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

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
            .then((res)=>{
              console.log('annotalist up', res)
            })
          },
        });
      }

    
    },
  
  },


  mounted() {
    this.dataType = this.$store.state.currentProject.type
    this.currentDataId = this.$store.state.currentDataId
    this.tableData = this.$store.state.currentDataList
    console.log('curren', this.$store.state.currentDataList,this.$store.state.currentDataId)
    console.log('tisada', this.tableData)
    // console.log('confi', this.$store.state.currentConfig)
    console.log('imag', this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated == 0 | this.$store.state.currentDataList[this.$store.state.currentDataId -1].predicted == 0 )
    var temp = this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated
    if(this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated == 0 | this.$store.state.currentDataList[this.$store.state.currentDataId -1].predicted == 0 ){
      console.log('te', this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated == 0)
      if(this.$store.state.currentDataList[this.$store.state.currentDataId -1].annotated == 0){
        var annotationlist = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
        annotationlist[0].dataId = this.$store.state.realDataId
        axios.put('/annotation/data/' + this.$store.state.realDataId, annotationlist[0])
        .then((res)=>{
          console.log('annotalist up', res)
        })
        // clearTimeout(this.timer);
        // this.timer = setTimeout(()=>{
          this.getAnno()
        // },500)
      }
      if(this.$store.state.currentDataList[this.$store.state.currentDataId -1].predicted == 0){
        this.predicts = []
          if(temp != 0){
            this.getAnno()
          }
        console.log('empty pre', this.predicts)
      }
    }else{
      this.getAnno()
      this.getPredict()
    }
    clearTimeout(this.timer);
    this.timer = setTimeout(()=>{

      //lack the multi people condition
      console.log('this', this.annos)
      if(this.annos == [] & this.predicts != []){
        this.annos = this.predicts
      }
      console.log('anno,pre',this.annos,this.predicts)
      if(this.dataType == 'image'){
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
            var annotationlist = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
            // annotationlist[0].annotationId = annotation.pk
            annotationlist[0].dataId = ls.task.id
            annotationlist[0].result = JSON.stringify(result)
            console.log('re', annotationlist)

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
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
            var annotationlist = [{"createTime":null,"projectId":0,"dataId":0,"type":null,"updateTime":null,"result":null,"annotationId":null}]
            // annotationlist[0].annotationId = annotation.pk
            annotationlist[0].dataId = LS.task.id
            annotationlist[0].result = JSON.stringify(result)
            console.log('re', annotationlist, annotationlist[0].dataId, JSON.stringify(annotationlist))

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
            .then((res)=>{
              console.log('annotalist up', res)
            })
          },
        });
      }else{
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
              text: this.$store.state.currentDataList[this.$store.state.currentDataId - 1].url
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
            var annotationlist = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
            // annotationlist[0].annotationId = annotation.pk
            annotationlist[0].dataId = ls.task.id
            annotationlist[0].result = JSON.stringify(result)
            console.log('re', annotationlist)

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
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
            var annotationlist = [{"createTime":null,"projectId":0,"dataId":0,"type":null,"updateTime":null,"result":null,"annotationId":null}]
            // annotationlist[0].annotationId = annotation.pk
            annotationlist[0].dataId = LS.task.id
            annotationlist[0].result = JSON.stringify(result)
            console.log('re', annotationlist, annotationlist[0].dataId, JSON.stringify(annotationlist))

            axios.put('/annotation/data/' + annotationlist[0].dataId, annotationlist[0])
            .then((res)=>{
              console.log('annotalist up', res)
            })
          },
        });
      }
    console.log(this.labelStudio.options)
    this.$store.state.nowLS = this.labelStudio
    },1000)

  },
};
</script>
