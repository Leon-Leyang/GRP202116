<template>
<div style="font-size: 1.25rem; font-weight: 500"> 
  <v-container class="ML-container">
<v-row>
  <v-col>
<v-container class="ml-container">

  <el-form ref="form" :model="form" :label-position="right"><!--why-->

   <el-form-item label="Model Version:">
    <el-input v-model="form.name" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
  </el-form-item>

  <el-form-item label="Model Type:">

   <el-select v-model="value" placeholder="please select the ML model type" @change="c1">
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
    <el-input v-model="form.name" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
  </el-form-item>

  <el-form-item label="Model File:" style="margin-top:20px">
     <v-file-input
                label="File Input"
                outlined
                dense
                ></v-file-input>
  </el-form-item>
  
  <el-form-item label="Class File:" style="margin-top:20px">
     <v-file-input
                label="File Input"
                outlined
                dense
                ></v-file-input>
  </el-form-item>

  <el-form-item label="Model Description:" style="margin-bottom:20px!important">
    <el-input type="textarea" v-model="form.desc"></el-input>
  </el-form-item>
  <!--end-->  

  <!--If choosing others, then it will not display the following-->
  <el-form-item v-if="isImgCla" label="Image Size:" > <!--style="margin-bottom:0"-->
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For image classification)"></el-input>
  </el-form-item>
  <el-form-item v-if="isImgCla" label="Mean:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For image classification) [0.485, 0.456, 0.406] by default"></el-input>
  </el-form-item>
  <el-form-item v-if="isImgCla" label="Standard Deviation:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For image classification) [0.229, 0.224, 0.225] by default"></el-input>
  </el-form-item>
 

  <el-form-item v-if="isObjDetect" label="Threashold:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For object detection)"></el-input>
  </el-form-item>
 

  <el-form-item v-if="isSemSeg" label="Mean:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.485, 0.456, 0.406] by default"></el-input>
  </el-form-item>
  <el-form-item v-if="isSemSeg" label="Standard Deviation:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.229, 0.224, 0.225] by default"></el-input>
  </el-form-item>
   

  <el-form-item v-if="isKpLb" label="Threashold:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For keypoint labeling)"></el-input>
  </el-form-item>
   

  <el-form-item v-if="isTC" label="Vocabulary File:" style="margin-top:20px">
    <v-file-input
                label="File Input"
                outlined
                dense
                ></v-file-input>
  </el-form-item>
  <el-form-item v-if="isTC" label="Token Number:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For text classification)"></el-input>
  </el-form-item>
   <el-form-item v-if="isTC" label="Sequence Length:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For text classification)"></el-input>
  </el-form-item>
 

  <el-form-item v-if="isNER" label="Sequence Length:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For named entity recognition)"></el-input>
  </el-form-item>

  <el-form-item style="margin-top:40px">
    <el-button type="primary" @click="onSubmit">Save</el-button>
    <el-button>Cancel</el-button>
  </el-form-item>

</el-form>
</v-container>
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
            :key="item.name"
            cols="12"
            md="4"
          >
            <v-card>
                <v-card-title class="text-h5">
                    {{ item.name }}
                </v-card-title>

                <v-card-subtitle>{{item.description}}</v-card-subtitle>
              
                <v-progress-linear
                color="light-blue"
                height="7"
                rounded
                :value="item.process"
                ></v-progress-linear> 

              <v-card-actions>
                
                  
               <MLTest></MLTest>
                
                
                 
                <MLTrain></MLTrain>
                
                
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
    width: 280px;
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
  import Mock from 'mockjs';
  /*import MLTest from '@/views/PerProject/ML/ML-Test-';*/
  import MLTest from './ML/ML-Test-.vue';
  import MLTrain from './ML/ML-Train-.vue';
  export default {
    components: {
        MLTest,
        MLTrain
    },
    data() {
    return {
        form: {
          name: '',
          region: '',
          desc: '',
          type: [],
          resource: ''
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

          items: [
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            
          },
        
        ],
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
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
    
    }
  }
</script>