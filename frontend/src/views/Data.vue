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
                <!-- <span v-if="dataType == 'text'" @click="enterData(item)">{{item.url}}</span> -->
              </div>
              </v-list-item-action>
            </v-list-item>

            <v-divider></v-divider>
          </template>

        </v-virtual-scroll>
                  <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pagesize"
            layout="prev, next"
            :total="total"
            class="pager"></el-pagination>
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
      listQuery: {
        page: 1,
        limit: 20
        },
      total:null,
      list:null,   
      currentPage:null,     
      start: 1,
      pagesize: 7,  
      pageNum:null,    
      newId:0,  
    }
  },

  methods: {
    enterData(data){
      this.predicts = []
      this.annos = []  
      console.log('annopre', this.annos,this.predicts) 

      console.log('dataId', data.dataId)
      this.$store.state.currentDataId = data.listNumber
      this.$store.state.realDataId = data.dataId
      console.log('cur', this.$store.state.currentDataId,this.$store.state.realDataId)
      console.log('culist', this.$store.state.currentPageList)
      this.newLS(data)
      console.log('enter', data)
    },
    prev(){
      this.predicts = [] 
      this.annos = []   
      console.log('annopre', this.annos,this.predicts) 
      console.log('import', this.$store.state.currentDataId)
      this.newId = this.$store.state.currentDataId % 7
      if(this.newId == 1){
        if(this.currentPage > 1){
          // this.$store.state.currentDataId++
          console.log('type1', this.$store.state.currentProjectId, this.currentPage, this.pagesize)
          this.currentPage --
          this.$store.state.currentDataId --
          console.log('this.$store.state.currentDataId', this.currentPage,this.$store.state.currentDataId)
          this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);
          // clearTimeout(this.timer);
        // this.timer = setTimeout(()=>{   
          console.log('tag1', this.currentPage,this.$store.state.currentPageList)

        // clearTimeout(this.timer);
        setTimeout(()=>{   
          console.log('tag', this.$store.state.currentPageList[6].dataId)
          this.$store.state.realDataId = this.$store.state.currentPageList[6].dataId
          console.log('import', this.$store.state.currentDataId,this.$store.state.realDataId)        
          this.currentDataId = this.$store.state.currentDataId         
          console.log('cyr', this.$store.state.currentPageList,this.$store.state.realDataId, this.currentDataId)
          // console.log('this.$store.state.currentPageList[this.$store.state.currentDataId - 1]', this.$store.state.currentDataId)
          this.newLS(this.$store.state.currentPageList[6])},800)
        }else{
          console.log('no next')
        }
      }else{
        console.log('newId', this.newId)
        this.$store.state.currentDataId--
        console.log('listnum', this.$store.state.currentPageList)
        console.log('this.$store.state.currentDataId', this.$store.state.currentDataId)
        let lam = this.$store.state.currentDataId % 7
        console.log('lam', lam)
        if(lam == 0){
          lam = 7
        }
        this.$store.state.realDataId = this.$store.state.currentPageList[lam- 1].dataId
        console.log('next', this.$store.state.realDataId, this.$store.state.currentPageList[lam - 1])        
        this.newLS(this.$store.state.currentPageList[lam- 1])        
      }
    },
    next(){
      this.predicts = [] 
      this.annos = []   
      console.log('annopre', this.annos,this.predicts) 
      console.log('import', this.$store.state.currentDataId)
      this.newId = this.$store.state.currentDataId % 7
      if(this.newId == 0){
        if(this.currentPage < this.pageNum){
          // this.$store.state.currentDataId++
          console.log('type1', this.$store.state.currentProjectId, this.currentPage, this.pagesize)
          this.currentPage ++
          this.$store.state.currentDataId ++
          this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);
          // clearTimeout(this.timer);
        // this.timer = setTimeout(()=>{   
          console.log('tag1', this.currentPage,this.$store.state.currentPageList)

        // clearTimeout(this.timer);
        setTimeout(()=>{   
          console.log('tag', this.$store.state.currentPageList[0].dataId)
          this.$store.state.realDataId = this.$store.state.currentPageList[0].dataId
          console.log('import', this.$store.state.currentDataId,this.$store.state.realDataId)        
          this.currentDataId = this.$store.state.currentDataId         
          console.log('cyr', this.$store.state.currentPageList,this.$store.state.realDataId, this.currentDataId)
          // console.log('this.$store.state.currentPageList[this.$store.state.currentDataId - 1]', this.$store.state.currentDataId)
          this.newLS(this.$store.state.currentPageList[0])},800)
        }else{
          console.log('no next')
        }
      }else{
        console.log('newId', this.newId)
        if(this.currentPage == this.pageNum){
          if( this.newId >= this.total % 7){
            return;
          }
        }
        this.$store.state.currentDataId++
        console.log('listnum', this.$store.state.currentPageList)
        console.log('this.$store.state.currentDataId', this.$store.state.currentDataId)
        let lam = this.$store.state.currentDataId % 7
        console.log('lam', lam)
        if(lam == 0){
          lam = 7
        }
        this.$store.state.realDataId = this.$store.state.currentPageList[lam- 1].dataId
        console.log('next', this.$store.state.realDataId, this.$store.state.currentPageList[lam - 1])        
        this.newLS(this.$store.state.currentPageList[lam- 1])        
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
      this.labelStudio.destroy()
      console.log('this.$store.state.currentDataId % 7', this.$store.state.currentDataId % 7)
      let vars = 0;
      if(this.$store.state.currentDataId % 7 == 0){
        vars = 7;
      }else{
        vars = this.$store.state.currentDataId % 7;
      }
      console.log('vars', vars)
      this.$store.state.currentPageList[vars -1].annotated
      if(this.$store.state.currentPageList[vars  -1].annotated == 0 | this.$store.state.currentPageList[vars  -1].predicted == 0 ){
        if(this.$store.state.currentPageList[vars  -1].annotated == 0){
          var annotationlist = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
          annotationlist[0].dataId = this.$store.state.realDataId
          axios.put('/annotation/data/' + this.$store.state.realDataId, annotationlist[0])
          .then((res)=>{
            console.log('annotalist up', res)
            this.$store.state.currentPageList[vars  -1].annotated = 1
          })
          clearTimeout(this.timer);
          this.timer = setTimeout(()=>{
            this.getAnno()
          },100)
        }
        console.log('t/f', this.$store.state.currentPageList[vars  -1].predicted == 0)
        if(this.$store.state.currentPageList[vars  -1].predicted == 0){
          this.predicts = []
          if(vars != 0){
            this.getAnno()
          }
          console.log('pre', this.predicts )
        }
      }else{
        this.getAnno()
        this.getPredict()
      }
      console.log('des', '')
      console.log('this', this.annos)
      if(this.annos == [] & this.predicts != []){
        this.annos = this.predicts
      }
      console.log('anno,pre',this.annos,this.predicts)
      console.log('data', data)
      if(this.dataType == 'image'){
        setTimeout(()=>{
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
                image: this.$store.state.currentPageList[vars - 1].url
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
          console.log('this.labelStudio', this.labelStudio)
        },1500)
      }else{
        setTimeout(()=>{        
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
                text: this.$store.state.currentPageList[vars - 1].url
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
        },1500)
      }
    },
    loadData(projectId, pageNum, pageSize){
      this.$axios.get('/data/page/'+ projectId +"/"+ pageNum +"/"+ pageSize)
      .then((res) => {
              console.log("currentPageList", res)
              this.$store.state.currentPageList = res.data.list
              console.log('this.$store.state.currentPageList', this.$store.state.currentPageList)
              this.total = res.data.total
              this.convertData()
      })
      // this.$set(this.tableData,1,this.tableData)
    },  
    handleSelectionChange(val) {
        this.multipleSelection = val;
        console.log('valk', val,this.multipleSelection)
    },   
    // handleSizeChange(val){
    //   this.pagesize = val;
    //   this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);        
    // },
    handleCurrentChange(val) {
        this.currentPage = val;
        this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);
    },
    convertData(){
      if(this.$store.state.currentProject.type == 'image'){
        clearTimeout(this.timer); 
        this.timer = setTimeout(()=>{  
            console.log('currentDataList', this.$store.state.currentPageList)
            this.tableData = this.$store.state.currentPageList
            console.log('curraList', this.tableData)

            for(var i=0; i<this.tableData.length; i++){
              this.tableData[i].url = this.tableData[i].url.replaceAll("\\", "/")
              this.tableData[i].url = this.tableData[i].url.replace("../frontend/public/", "")
              console.log('tabelda',this.tableData[i].url)
            }
            this.$store.state.currentPageList = this.tableData

            console.log('asd', this.$store.state.currentPageList)
          },200);

      }else{
        clearTimeout(this.timer); 
        this.timer = setTimeout(()=>{  
          this.tableData = this.$store.state.currentPageList
          for(var i=0; i<this.tableData.length; i++){
            console.log('tabelda',this.tableData[i].url)
          }        
          this.$store.state.currentPageList = this.tableData
          console.log('table text', this.tableData)
        },200);

      }        
    },
  },


  mounted() {
    this.currentPage = this.$store.state.currentPage
    this.total = this.$store.state.totalNum
    var more = 0;
    if((this.total % 7) != 0){
      more= 1;
    }else{
      more = 0;
    }
    this.pageNum = this.total/7 + more
    this.dataType = this.$store.state.currentProject.type
    this.currentDataId = this.$store.state.currentDataId
    this.tableData = this.$store.state.currentPageList
    console.log('curren', this.$store.state.currentPageList,this.$store.state.currentDataId)
    console.log('tisada', this.tableData)
    // console.log('confi', this.$store.state.currentConfig)
    this.newId = this.$store.state.currentDataId % 7
    if(this.$store.state.currentDataId <= 7){
      if(this.newId == 0){
        this.newId = 7
      }
      console.log('imag', this.$store.state.currentPageList[this.$store.state.currentDataId -1].annotated == 0 | this.$store.state.currentPageList[this.$store.state.currentDataId -1].predicted == 0 )
      var temp = this.$store.state.currentPageList[this.$store.state.currentDataId -1].annotated
      if(this.$store.state.currentPageList[this.$store.state.currentDataId -1].annotated == 0 | this.$store.state.currentPageList[this.$store.state.currentDataId -1].predicted == 0 ){
        console.log('te', this.$store.state.currentPageList[this.$store.state.currentDataId -1].annotated == 0)
        if(this.$store.state.currentPageList[this.$store.state.currentDataId -1].annotated == 0){
          var annotationlist = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
          annotationlist[0].dataId = this.$store.state.realDataId
          axios.put('/annotation/data/' + this.$store.state.realDataId, annotationlist[0])
          .then((res)=>{
            console.log('annotalist up', res)
            this.$store.state.currentPageList[this.$store.state.currentDataId -1].annotated = 1
          })
          // clearTimeout(this.timer);
          // this.timer = setTimeout(()=>{
            this.getAnno()
          // },500)
        }
        if(this.$store.state.currentPageList[this.$store.state.currentDataId -1].predicted == 0){
          this.predicts = []
            if(temp != 0){
              this.getAnno()
            }
          console.log('empty pre', this.predicts)
        }
      }else{
        this.getAnno()
        this.getPredict()
      }}
    else{
      if(this.newId != 0 ){
        console.log('imag', this.$store.state.currentPageList[this.newId -1].annotated == 0 | this.$store.state.currentPageList[this.newId -1].predicted == 0 )
        var templ = this.$store.state.currentPageList[this.newId -1].annotated
        if(this.$store.state.currentPageList[this.newId -1].annotated == 0 | this.$store.state.currentPageList[this.newId -1].predicted == 0 ){
          console.log('te', this.$store.state.currentPageList[this.newId -1].annotated == 0)
          if(this.$store.state.currentPageList[this.newId -1].annotated == 0){
            var annotationlistt = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
            annotationlistt[0].dataId = this.$store.state.realDataId
            axios.put('/annotation/data/' + this.$store.state.realDataId, annotationlistt[0])
            .then((res)=>{
              console.log('annotalist up', res)
              this.$store.state.currentPageList[this.newId -1].annotated = 1
            })
            // clearTimeout(this.timer);
            // this.timer = setTimeout(()=>{
              this.getAnno()
            // },500)
          }
          if(this.$store.state.currentPageList[this.newId -1].predicted == 0){
            this.predicts = []
              if(templ != 0){
                this.getAnno()
              }
            console.log('empty pre', this.predicts)
          }
        }else{
          this.getAnno()
          this.getPredict()
        }   
      }else{
        this.newId = 7
        console.log('imag', this.$store.state.currentPageList[this.newId -1].annotated == 0 | this.$store.state.currentPageList[this.newId -1].predicted == 0 )
        var templa = this.$store.state.currentPageList[this.newId -1].annotated
        if(this.$store.state.currentPageList[this.newId -1].annotated == 0 | this.$store.state.currentPageList[this.newId -1].predicted == 0 ){
          console.log('te', this.$store.state.currentPageList[this.newId -1].annotated == 0)
          if(this.$store.state.currentPageList[this.newId -1].annotated == 0){
            var annotationlisttt = [{"createTime":null,"projectId":null,"dataId":null,"type":null,"updateTime":null,"result":null,"annotationId":null}]
            annotationlisttt[0].dataId = this.$store.state.realDataId
            axios.put('/annotation/data/' + this.$store.state.realDataId, annotationlisttt[0])
            .then((res)=>{
              console.log('annotalist up', res)
              this.$store.state.currentPageList[this.newId -1].annotated = 1
            })
            // clearTimeout(this.timer);
            // this.timer = setTimeout(()=>{
              this.getAnno()
            // },500)
          }
          if(this.$store.state.currentPageList[this.newId -1].predicted == 0){
            this.predicts = []
              if(templa != 0){
                this.getAnno()
              }
            console.log('empty pre', this.predicts)
          }
        }else{
          this.getAnno()
          this.getPredict()
        }   
      }
   
    }

    setTimeout(()=>{
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
              image: this.$store.state.currentPageList[this.newId - 1].url
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
              text: this.$store.state.currentPageList[this.newId - 1].url
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
    },1500)

  },
};
</script>
