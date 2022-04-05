<template>
<div style="font-size: 1.25rem; font-weight: 500"> 
    <v-container class="ML-container">
        <v-row>
            <v-col>
                <v-card
                  v-scroll.self="onScroll"
                  class="overflow-y-auto"
                  max-height="400">
                    <v-card-text>
                        <div class="mb-4">

                            <el-form ref="form" :model="form" :label-position="right"><!--why-->

                            <el-form-item label="Model Version:">
                                <el-input v-model="form.version" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>

                            <el-form-item label="Script Type:">

                            <el-select v-model="value" placeholder="please select the ML model script type" @change="c1">
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

                            <el-form-item label="Model File:" style="margin-top:20px">
                                <el-input v-model="form.modelPath" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>
                            
                            <el-form-item label="Class File:" style="margin-top:20px">
                                <el-input v-model="form.labelsPath" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
                            </el-form-item>

                            <el-form-item label="Model Description:" style="margin-bottom:20px!important">
                                <el-input type="textarea" v-model="form.description"></el-input>
                            </el-form-item>
                            <!--end-->  

                            <!--If choosing others, then it will not display the following-->
                            <el-form-item v-if="isImgCla" label="Image Size:" > <!--style="margin-bottom:0"-->
                                <el-input v-model="form.params.imgSize" clearable maxlength="" size="mini" placeholder="(For image classification)"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isImgCla" label="Mean:">
                                <el-input v-model="form.params.mean" clearable maxlength="" size="mini" placeholder="(For image classification) [0.485, 0.456, 0.406] by default"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isImgCla" label="Standard Deviation:">
                                <el-input v-model="form.params.std" clearable maxlength="" size="mini" placeholder="(For image classification) [0.229, 0.224, 0.225] by default"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isObjDetect" label="Threashold:">
                                <el-input v-model="form.params.threshold" clearable maxlength="" size="mini" placeholder="(For object detection)"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isSemSeg" label="Mean:">
                                <el-input v-model="form.params.mean" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.485, 0.456, 0.406] by default"></el-input>
                            </el-form-item>
                            <el-form-item v-if="isSemSeg" label="Standard Deviation:">
                                <el-input v-model="form.params.std" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.229, 0.224, 0.225] by default"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isKpLb" label="Threashold:">
                                <el-input v-model="form.params.threshold" clearable maxlength="" size="mini" placeholder="(For keypoint labeling)"></el-input>
                            </el-form-item>
                            

                            <el-form-item v-if="isTC" label="Vocabulary File:" style="margin-top:20px">
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
                             <!--   <el-button type="primary" @click="onSubmit">Save</el-button>
                                <el-button>Cancel</el-button>-->
                                <div style="display:flex;flex-direction:row">
                            <v-btn
                            depressed
                            color="pink lighten-1"
                            >
                            Save
                            </v-btn>
                            <v-spacer></v-spacer>
                            
                             <v-btn
                            depressed
                            color="error"
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
  }

  .el-select .el-input {
    width: 300px;
  }  
</style>

<script>
  export default {

    data() {
    return {
        form: {
            modelRoot:'',
            version: '',
            description:'',
            modelPath:'',
            labelsPath:'',
            type: '',
            resource: '',
            params:{
                mean:'[0.485, 0.456, 0.406]',
                std:'[0.229, 0.224, 0.225]',
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
            label: 'Basic Types',
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

        items: [ ],
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
        }
    },
    methods: {
      onSubmit() {
          console.log('submit!');
          this.items.push(this.form)
          console.log('submit!');
          this.$store.state.currentMLList = this.items
          console.log('item', this.items, this.$store.state.currentMLList)
          this.form = {
            modelRoot:'',
            version: '',
            description:'',
            modelPath:'',
            labelsPath:'',
            type: '',
            resource: '',
            params:{
                mean:'[0.485, 0.456, 0.406]',
                std:'[0.229, 0.224, 0.225]',
                imgSize:null,
                threshold:null,
                vocabPath:null,
                tokenNum:null,
                sequenceLen:null,
            }
        }
        console.log('item', this.items)
          this.value = null
      },
    
      c1(selectValue) {
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

  }
</script>