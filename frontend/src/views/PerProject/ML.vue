<!--This is the ML page
In this page user can import ml, run ml and train ml
@author LinjingSUN YingjiaLi-->
<template>
<div style="font-size: 1.25rem; font-weight: 500;background:#f1f2fa;height:100%"> 
    <v-container class="ML-container">
        <v-row>
            <v-col>
                <v-card
                  v-scroll.self="onScroll"
                  class="overflow-y-auto"
                  max-height="630px" style="margin-top:20px">
                    <v-card-text>
                        <div class="mb-4">

                            <el-form ref="form" :model="form" :label-position="right"><!--why-->

                            <el-form-item label="Model Version:">
                                <el-input v-model="form.version" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>

                            <el-form-item label="Script Type:">

                            <el-select v-model="value" placeholder="Please Select the ML Model Script Type" @change="selectType">
                                <el-option-group
                                v-for="group in options"
                                :key="group.label"
                                :label="group.label">
                                <el-option
                                    v-for="item in group.options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                                </el-option-group>
                            </el-select>
                            </el-form-item>


                            <el-form-item label="Model Project Root Path:">
                                <el-input v-model="form.modelRoot" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>

                            <el-form-item label="Model Path:" style="margin-top:20px">
                                <el-input v-model="form.modelPath" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>
                            
                            <el-form-item label="Class File Path:" style="margin-top:20px">
                                <el-input v-model="form.labelsPath" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>

                            <el-form-item label="Model Description:" style="margin-bottom:20px!important">
                                <el-input placeholder="(Optional)" type="textarea" v-model="form.description"></el-input>
                            </el-form-item>
                            <!--end-->  

                            <!--If choosing others, then it will not display the following-->
                            <el-form-item v-if="isImgCla" label="Image Size:" > <!--style="margin-bottom:0"-->
                                <el-input v-model="form.params.imgSize" clearable maxlength="" size="mini" placeholder="(For image classification)"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isImgCla" label="Mean:">
                                <el-input v-model="form.params.mean" clearable maxlength="" size="mini" placeholder="(For image classification) [0.5, 0.5, 0.5] by default"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isImgCla" label="Standard Deviation:">
                                <el-input v-model="form.params.std" clearable maxlength="" size="mini" placeholder="(For image classification) [0.5, 0.5, 0.5] by default"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isObjDetect" label="Threshold:">
                                <el-input v-model="form.params.threshold" clearable maxlength="" size="mini" placeholder="(For object detection)"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isSemSeg" label="Mean:">
                                <el-input v-model="form.params.mean" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.5, 0.5, 0.5] by default"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isSemSeg" label="Standard Deviation:">
                                <el-input v-model="form.params.std" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.5, 0.5, 0.5] by default"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isKpLb" label="Threashold:">
                                <el-input v-model="form.params.threshold" clearable maxlength="" size="mini" placeholder="(For keypoint labeling)"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isTC" label="Vocabulary File Path:" style="margin-top:20px">
                                <el-input v-model="form.params.vocabPath" clearable maxlength="" size="mini" placeholder="(For keypoint labeling)"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isTC" label="Token Number:">
                                <el-input v-model="form.params.tokenNum" clearable maxlength="" size="mini" placeholder="(For text classification)"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isTC" label="Sequence Length:">
                                <el-input v-model="form.params.sequenceLen" clearable maxlength="" size="mini" placeholder="(For text classification)"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isNER" label="Sequence Length:">
                                <el-input v-model="form.params.sequenceLen" clearable maxlength="" size="mini" placeholder="(For named entity recognition)"></el-input>
                            </el-form-item>

                            <el-form-item style="margin-top:40px">


                             <!--
                                <el-button type="primary" @click="onSubmit">Save</el-button>
                                <el-button>Cancel</el-button>-->

                            <div style="display:flex;flex-direction:row">
                            <v-btn
                            
                            depressed
                            color="pink lighten-1"
                            @click="onSubmit"
                            style="font-size: 2rem"
                            >
                            Save
                            </v-btn>
                            <v-spacer></v-spacer>
                            
                             <v-btn
                            
                            depressed
                            color="error"
                            style="font-size: 2rem"
                            >
                            Cancel
                            </v-btn>
                            </div>
                            
                            </el-form-item>

                            
                           
                            </el-form>

                        </div>
                    </v-card-text>
                </v-card>
            </v-col>

            <v-col>
                <v-container class="ml-container">
                <v-card class="r-card">
                <v-container>
                <v-data-iterator
                    :items="items"
                    >

                    <template v-slot:default="props">
                        <v-row>
                        <v-col
                            v-for="item in props.items"
                            :key="item.version"
                            cols="12"
                            md="4"
                        >
                            <v-card>
                                <v-card-title class="text-h5">
                                    {{ item.version }}
                                </v-card-title>

                                <v-card-subtitle>{{item.description}}</v-card-subtitle>
                            
                                <!-- <v-progress-linear
                                color="light-blue"
                                height="7"
                                rounded
                                :value="item.process"
                                ></v-progress-linear>  -->

                            <v-card-actions>

                              <!-- ML Test -->
                              <v-dialog
                                v-model="dialog"
                                persistent
                                max-width="600px"
                              >
                                <template v-slot:activator="{ on, attrs }">
                                  
                                  <v-btn
                                    color="yellow"
                                    v-bind="attrs"
                                    v-on="on"
                                    width="72px"
                                    @click="chooseVersion(item.version, item.type)"
                                  >
                                    Run
                                  </v-btn>
                                  
                                </template>


                                <v-card>
                                  <v-card-title>
                                    <span class="text-h5">Run</span>
                                  </v-card-title>
                                  <v-card-text>
                                    <v-container>
                                      <v-row  v-if="nowType == 'Customization'">   
                                        <v-col cols="12">
                                        Fill in the Customized Run Script Path Here:
                                        <el-input placeholder="(Only For Customization Type)" type="textarea" v-model="testScript"></el-input>
                                        </v-col>
                                      </v-row>
                                    </v-container>
                                  </v-card-text>
                                  <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn text @click="cancelTest()">
                                      Cancel
                                    </v-btn>
                                    <v-btn
                                      color="blue darken-1"
                                      text
                                      @click="MLTest(nowVersion)"
                                    >
                                      Start
                                    </v-btn>
                                  </v-card-actions>
                                </v-card>
                              </v-dialog>

                              <!-- ML Train -->
                              <v-dialog
                                v-model="dialogTrain"
                                persistent
                                max-width="600px"
                              >
                                <template v-slot:activator="{ on, attrs }">
                                  <v-btn
                                    color="green"
                                    dark
                                    v-bind="attrs"
                                    v-on="on"
                                    width="72px"
                                    @click="chooseVersion(item.version,item.type)"
                                  >
                                    Train
                                  </v-btn>
                                </template>
                                <v-card>
                                  <v-card-title>
                                    <span class="text-h5">Train</span>
                                  </v-card-title>
                                  <v-card-text>
                                    <v-container>
                                      <v-row>
                                        <v-col
                                          cols="12"
                                          sm="6"
                                          md="4"
                                          v-if="nowType == 'Image Classification'"
                                        >
                                          <v-text-field
                                            v-model="trainObject.params.trainFrac"
                                            label="Train Proportion:"
                                            hint="range from 0 to 1"
                                            persistent-hint
                                            required
                                          ></v-text-field>
                                        </v-col>
                                        
                                        <v-col
                                        v-if="nowType == 'Image Classification'"
                                          cols="12"
                                          sm="6"
                                          md="4"
                                        >
                                          <v-text-field
                                            v-model="trainObject.params.batchSize"
                                            label="Batch Size:"
                                            hint="number"
                                            persistent-hint
                                            required
                                          ></v-text-field>
                                        </v-col>
                                        <v-col
                                        v-if="nowType == 'Image Classification'"
                                          cols="12"
                                          sm="6"
                                          md="4"
                                        >
                                          <v-text-field
                                          v-model="trainObject.params.workerNum"
                                            label="Worker Number:"
                                            hint="number"
                                            persistent-hint
                                            required
                                          ></v-text-field>
                                        </v-col>

                                        <v-col cols="12"
                                        v-if="nowType == 'Image Classification'">
                                          
                                            <v-switch
                                              v-model="trainObject.params.shuffle"
                                              :label="`Shuffle`"
                                            ></v-switch>
                                        </v-col>
                                      
                                        <v-col cols="12"
                                        v-if="nowType == 'Image Classification'">
                                          <v-text-field
                                          v-model="trainObject.params.epochNum"
                                            label="Epoch Number:"
                                            hint="number"
                                            persistent-hint
                                            required
                                          ></v-text-field>
                                        </v-col>
                                        
                                        <v-col cols="12"
                                        v-if="nowType == 'Image Classification'">
                                          <v-text-field
                                          v-model="trainObject.params.learningRate"
                                            label="Learning Rate:"
                                            hint="number"
                                            persistent-hint
                                            required
                                          
                                          ></v-text-field>
                                        </v-col>
                                        <v-col
                                        v-if="nowType == 'Image Classification'"
                                          cols="12"
                                          sm="6"
                                        >
                                          <v-select
                                          v-model="trainObject.params.optimizer"
                                            :items="['SGD', 'ASGD', 'Rprop', 'Adagrad','Adadelta','RMSprop','Adam','Adamax','SparseAdam','LBFGS']"
                                            label="Optimizer"
                                            required
                                          ></v-select>
                                        </v-col>
                                        <v-col
                                        v-if="nowType == 'Image Classification'"
                                          cols="12"
                                          sm="6"
                                        >
                                          <v-autocomplete
                                          v-model="trainObject.params.lossFunc"
                                            :items="['CrossEntropyLoss', 'NLLLoss']"
                                            label="Loss Function"
                                            required
                                          ></v-autocomplete><!--multiple-->
                                        </v-col>

                                        <v-col cols="12">
                                          <v-text-field
                                          v-model="trainObject.params.savePath"
                                            label="Where to save the model:"
                                            hint="local path"
                                            persistent-hint
                                            required
                                          ></v-text-field>
                                        </v-col>

                                        <v-col cols="12"
                                        v-if="nowType != 'Image Classification'">
                                          <div style="margin-top:30px">
                                            Fill in the Customized Train Script Here:
                                            <el-input type="textarea" placeholder="(For Every Type Except Image Classification)" v-model="trainObject.script_url"></el-input>                                            
                                          </div>
                                        </v-col>
                                        
                                      </v-row>
                                    </v-container>
                                    <!--<small>*indicates required field</small>-->
                                  </v-card-text>
                                  <v-card-actions>
                                    <v-spacer></v-spacer>
                                    
                                    <v-btn
                                      text
                                      @click="cancelTrain()"
                                    >
                                      Cancel
                                    </v-btn>
                                 
                                    <v-btn
                                      color="blue darken-1"
                                      text
                                      @click="MLTrain(nowVersion)"
                                    >
                                      Start
                                    </v-btn>
                                  </v-card-actions>
                                </v-card>
                              </v-dialog>

                            </v-card-actions>

                            </v-card>
                        </v-col>
                        </v-row>
                    </template>

                    
                </v-data-iterator>
                </v-container>
                </v-card>
                </v-container>

            </v-col>
        </v-row>
    </v-container>
</div>
</template>

<style>
  .ML-container{
    max-width: 1262px;
  }
  

  .ml-container{
    margin:10px 0px 20px 0;
    padding: 0;
  }

  .r-card{
    width:100%; 
    margin-top:20px
  }

  .el-select .el-input {
    width: 350px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
  .content{
      margin-left:20px;
      width:90%;
      margin-top:6px;
  }

.v-text-field--outlined fieldset {
    border-collapse: collapse;
    border-style: solid;
    border-color: #DCDFE6!important;
    border-width: 1px;
    bottom: 0;/* */
    top: -5px;/* */
    /*height: 32px;*/
    left: 0;
    right: 0;
    pointer-events: none;
    position: absolute;
    transition-duration: 0.15s;
    transition-property: color;
    transition-timing-function: cubic-bezier(0.25, 0.8, 0.25, 1);
}

  .v-input {
    height: 40px;
}
  
  .el-form-item {
    margin-bottom: 0px!important;
    margin-left: 20px;
    margin-right: 20px;
}

 
    
</style>

<script>
  export default {
    data() {
    return {
      nowVersion:'',
      nowType:'',
      Type:'',
      form: {
        modelRoot:'',
        version: '',
        description:'',
        modelPath:'',
        labelsPath:'',
        type: '',
        resource: '',
        params:{
            mean:'[0.5, 0.5, 0.5]',
            std:'[0.5, 0.5, 0.5]',
            imgSize:null,
            threshold:null,
            vocabPath:null,
            tokenNum:null,
            sequenceLen:null,
        }
      },

      type:null,

      typeOptions:[
      {label:'Image Classification',value:0},
      {label:'Object Detection',value:1},
      {label:'Keypoint Labeling',value:2},
      {label:'Semantic Segmentation with Masks',value:3},
      {label:'Text Classification',value:4},
      {label:'Name Entity Recognition',value:5},
      {label:'Customization',value:6},
      ],

      options: [{
        label: 'Supportive Types',
        options: [{
            value: '0',
            label: 'Image Classification'
        }, {
            value: '1',
            label: 'Object Detection'
        },{
            value: '2',
            label: 'Keypoint Labeling'
        },{
            value: '3',
            label: 'Semantic Segmentation with Masks'
        },{
            value: '4',
            label: 'Text Classification'
        },{
            value: '5',
            label: 'Name Entity Recognition'
        },]
        }, 
        {
        label: 'Others',
        options: [  
        {label:'Customization',value: '6'}]
        }],
      value: '',
      testScript:null,
      runObject: {
        version:'',
        script_url:null
      },
      trainObject:{
        version:null,
        params:{
          trainFrac: null,
          batchSize:null,
          workerNum:null,
          shuffle:false,
          epochNum:null,
          learningRate:null,
          optimizer:null,
          lossFunc:null,
          savePath:null,
        },
        script_url:null,
      },

      dialog:false,
      dialogTrain: false,

      numOfML:null,

      items: [ ],
      prevMLList:[],
      newMLList:[],
      }
    },
    watch:{
      value: function(val){
          if(val == 0){
              this.$store.state.currentMLType = 'Image Classification'
          }else if(val == 1){
              this.$store.state.currentMLType = 'Object Detection'
          }else if(val == 2){
              this.$store.state.currentMLType = 'Keypoint Labeling'
          }else if(val == 3){
              this.$store.state.currentMLType = 'Semantic Segmentation with Masks'
          }else if(val == 4){
              this.$store.state.currentMLType = 'Text Classification'
          }else if(val == 5){
              this.$store.state.currentMLType = 'Name Entity Recognition'
          }else if(val == 6){
              this.$store.state.currentMLType = 'Customization'
          }
          this.form.type = this.$store.state.currentMLType
      },
      testScript: function(val){
        this.testScript = val
      }
    },
    methods:{
      show(){
        console.log('teset',this.nowType,this.$store.state.nowType)
      },
      chooseVersion(version,type){
        console.log('nowversion', version)
        this.nowVersion = version
        this.nowType = type
        this.Type = type
        this.$store.state.nowType = type
        console.log('testTYpe', this.nowType, this.nowType == 'Customization',this.nowType != 'Customization', this.$store.state.nowType)

      },
      onSubmit() {
          console.log('submit1!');
          console.log('form', this.form)
          console.log('tag', this.newMLList)
          this.newMLList.push(this.form)
          console.log('tag', this.newMLList)
          
          this.items.push(this.form)
          console.log('submit2!');
          this.$store.state.currentMLList = this.items
          console.log('currentMLList.', this.$store.state.currentMLList)

          console.log('item', this.items)
          this.form = {
            modelRoot:'',
            version: '',
            description:'',
            modelPath:'',
            labelsPath:'',
            type: '',
            resource: '',
            params:{
                mean:'[0.5, 0.5, 0.5]',
                std:'[0.5, 0.5, 0.5]',
                imgSize:null,
                threshold:null,
                vocabPath:null,
                tokenNum:null,
                sequenceLen:null,
            }
          }
          console.log('item', this.items)
          this.value = null

          console.log('this.$store.state.currentMLList ml page, newMLList', this.$store.state.currentMLList, this.newMLList)
          if(this.newMLList != []){
            for(var mln = 0; mln<this.newMLList.length; mln++){
              console.log('mln', mln)
              this.newMLList[mln].params = JSON.stringify(this.newMLList[mln].params)
              console.log('params', this.newMLList[mln].params)
            }
            this.$axios.post(`/model/create/`+ this.$store.state.currentProjectId, JSON.stringify(this.newMLList))
                .then(res => {
                console.log("ml", res)
            })
            console.log('this.$store.state.currentMLList', this.$store.state.currentMLList)                  
          }

          console.log('this.$store.state.currentMLList', this.$store.state.currentMLList)
      },
      cancelTest(){
        this.testScript = null
        this.dialog = false
      },      
      MLTest(version){
        console.log('nee', this.testScript)
        console.log('version', version)

        this.dialog = false
        this.runObject.version = version
        this.runObject.script_url = this.testScript
        console.log('runObject', this.runObject, JSON.stringify(this.runObject))
        this.$axios.post('/model/run/'+ this.$store.state.currentProjectId, JSON.stringify(this.runObject))
        .then((res)=>{
          console.log('test model', res)
        })   
        this.testScript = null
        this.form = {
          modelRoot:'',
          version: '',
          description:'',
          modelPath:'',
          labelsPath:'',
          type: '',
          resource: '',
          params:{
              mean:'[0.5, 0.5, 0.5]',
              std:'[0.5, 0.5, 0.5]',
              imgSize:null,
              threshold:null,
              vocabPath:null,
              tokenNum:null,
              sequenceLen:null,
          }
        }    
      },
      cancelTrain(){
        this.trainObject={
          version:null,
          params:{
            trainFrac: null,
            batchSize:null,
            workerNum:null,
            shuffle:false,
            epochNum:null,
            learningRate:null,
            optimizer:null,
            lossFunc:null,
            savePath:null,
          },
          script_url:null,
        }   
        this.dialogTrain = false
      },       
      MLTrain(version){
        this.dialogTrain = false
        console.log('train', version, this.trainObject)
        this.trainObject.version = version
        var bool = this.trainObject.params.shuffle
        if(bool == false){
          this.trainObject.params.shuffle = 'False'
        }else{
          this.trainObject.params.shuffle = 'True'
        }
        this.$axios.post('/model/train/'+ this.$store.state.currentProjectId,JSON.stringify(this.trainObject))
        .then((res)=>{
          console.log('train model', res)
        }) 
        this.trainObject={
          version:null,
          params:{
            trainFrac: null,
            batchSize:null,
            workerNum:null,
            shuffle:false,
            epochNum:null,
            learningRate:null,
            optimizer:null,
            lossFunc:null,
            savePath:null,
          },
          script_url:null,
        }        
      },

    
      selectType(selectValue) {
          this.isObjDetect = false;
          this.isImgCla = false;
          this.isSemSeg = false;
          this.isKpLb = false;
          this.isTC = false;
          this.isNER = false;
      if (selectValue == 0) {
        this.isImgCla = true;
      } 
      if (selectValue == 1) {
        this.isObjDetect = true;
      } 
      if (selectValue == 3) {
        this.isSemSeg = true;
      } 
      if (selectValue == 2) {
        this.isKpLb = true;
      } 
      if (selectValue == 4) {
        this.isTC = true;
      }
      if (selectValue == 5) {
        this.isNER = true;
      }
    },

    
    },
    mounted() {
      this.$store.state.pageLocate = 'NotData'
      console.log('ml.currentProjectId', this.$store.state.currentProjectId)
      this.$axios.get('/model/'+ this.$store.state.currentProjectId)
          .then(res => {
            console.log('have get ML', res)
            this.items = res.data
            this.$store.state.currentMLList = this.items
            this.numOfML = this.items.length
            this.prevMLList = this.items
          })
          .catch((error) => {
              // here you will have access to error.response
              console.log(error.response)
          });
    },
  }
</script>