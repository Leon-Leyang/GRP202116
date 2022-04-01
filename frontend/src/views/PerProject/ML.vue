<template>
<div style="font-size: 1.25rem; font-weight: 500"> 
    <v-container class="ml-container">
  <el-form ref="form" :model="form" :label-position="right"><!--why-->

   <el-form-item label="Model Version:">
    <el-input v-model="form.name" clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
  </el-form-item>

  <el-form-item label="Model Type:">
    <!--<el-select clearable placeholder="please select the model type" size="small" @change="lyj">
      <el-option label="Image Classification" ></el-option>
      <el-option label="Object Detection" ></el-option>
      <el-option label="Keypoint Labeling" ></el-option>
      <el-option label="Semantic Segmentation with Masks" ></el-option>
      <el-option label="Text Classification" ></el-option>
      <el-option label="Name Entity Recognition" ></el-option>
      <el-option label="Customization" ></el-option>
          <el-option
                  v-for="type in typeOptions"
                  :key="type.value"
                  :label="type.label"
                  :value="type.value"
                ></el-option>
    </el-select>-->

     <!--
     <el-select v-model="type" clearable @change="c1" style="width: 70px">
    <el-option  v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
 </el-select>-->
 
   <el-select v-model="value"  clearable placeholder="please select the ML model type" @change="c1">
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

<!--If choosing others, then it will not display the following ??-->
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
  <!--end-->
 

  <el-form-item label="Model Description:" style="margin-bottom:20px!important">
    <el-input type="textarea" v-model="form.desc"></el-input>
  </el-form-item>
  
  <!--
  <el-form-item label="Upload Customized Backend:" style="margin-top:10px">
  <v-file-input
    label="File Input"
    outlined
    dense
  ></v-file-input>
  </el-form-item>
  -->

  <v-divider></v-divider>

  <el-form-item v-if="isImgCla" label="image size:"> <!--style="margin-bottom:0"-->
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For image classification)"></el-input>
  </el-form-item>
 <v-divider></v-divider>

  <el-form-item v-if="isObjDetect" label="threashold:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For object detection)"></el-input>
  </el-form-item>
 <v-divider></v-divider>

  <el-form-item v-if="isSemSeg" label="mean:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For semantic segmentation) [0.485, 0.456, 0.406] by default"></el-input>
  </el-form-item>
  <el-form-item v-if="isSemSeg" label="standard deviation:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="[0.229, 0.224, 0.225] by default"></el-input>
  </el-form-item>
   <v-divider></v-divider>

  <el-form-item v-if="isKpLb" label="threashold:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For keypoint labeling)"></el-input>
  </el-form-item>
   <v-divider></v-divider>

  <el-form-item v-if="isTC" label="vocabulary file:" style="margin-top:20px">
    <v-file-input
                label="File Input"
                outlined
                dense
                ></v-file-input>
  </el-form-item>
  <el-form-item v-if="isTC" label="token number:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For text classification)"></el-input>
  </el-form-item>
   <el-form-item v-if="isTC" label="sequence length:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For text classification)"></el-input>
  </el-form-item>
   <v-divider></v-divider>

  <el-form-item v-if="isNER" label="sequence length:">
    <el-input v-model="form.name" clearable maxlength="" size="mini" placeholder="(For named entity recognition)"></el-input>
  </el-form-item>

  <el-form-item style="margin-top:40px">
    <el-button type="primary" @click="onSubmit">Save</el-button>
    <el-button>Cancel</el-button>
  </el-form-item>

  <div>
        <el-button type="primary" @click="$router.push('/ML-Test')">Test</el-button>
        <el-button type="primary" @click="$router.push('/ML-Train')">Train</el-button>
  </div>

</el-form>
</v-container>
</div>


</template>

<style>

  .ml-container{
      width:50%;
      margin-left:0;
  }

  .el-select .el-input {
    width: 230px;
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
          label: 'Existing Types',
          options: [{
            value: '0',
            label: 'Image Classification'
          }, {
            value: '1',
            label: 'Object Detection'
          }]
        }, {
          label: 'Others',
          options: [{
            value: '2',
            label: 'Keypoint Labeling'
          }, {
            value: '3',
            label: 'Semantic Segmentation with Masks'
          }, {
            value: '4',
            label: 'Text Classification'
          }, {
            value: '5',
            label: 'Name Entity Recognition'
          },
          {label:'Customization',value:6}]
        }],
        value: ''
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