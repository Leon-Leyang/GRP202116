<template>
<div>

<el-tabs v-model="active" style="height:500px">
    <el-tab-pane name="1" >
        <span slot="label">Basic Info</span>

        <!-- Basic information -->
            <el-form :inline="inline" :model="form" ref="form" :rules="rules" label-width="100px" class="demo-ruleForm"  style=" padding: 30px 60px 30px 30px;">

                <!-- Project Name -->
                <el-form-item label="Project Name" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>

                <!-- Project Description -->
                <el-form-item label="Description" style="margin-top:50px">
                    <el-input type="textarea" v-model="form.description" placeholder="(Optional)" rows="10"></el-input>
                </el-form-item>   

            </el-form>
  
    </el-tab-pane>

    <el-tab-pane name="2">
        <span slot="label">Import Data</span>

        <!-- Import dataset -->
        <el-upload
        class="upload-demo"
        drag
        action="/api/dataset"
        v-model="form.data" 
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Drag & drop files here</div>
        <div class="el-upload__text"><em>Click to add</em></div>
        <div class="el-upload__tip" slot="tip">( The format of uploading file should be <span style="font-style:italic; color: #719DDD">.txt, .jpg, .png, .gif, .bmp, .svg, .webp, .csv, .tsv, .json</span> )</div>
        </el-upload>
    </el-tab-pane>

    <el-tab-pane name="3" >
        <span slot="label">Import ML model</span>
        <!-- Import ML Model -->
        <div>
        <el-upload
        class="upload-demo"
        drag
        action="/api/ml"
        v-model="form.ml" 
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Drag & drop files here</div>
        <div class="el-upload__text"><em>Click to add</em></div>
        <div class="el-upload__tip" slot="tip">( The format of uploading file should be <span style="font-style:italic; color: #719DDD">.pth</span> )</div>
        </el-upload>
        </div>
    </el-tab-pane>

    <!-- Button block -->
    <div style="display: flex; float: right; width:35%">
        <!-- Next Button -->
        <!-- <el-button class="next" @click="nextStep" style="float: right" icon="el-icon-d-arrow-right" circle></el-button> -->
        <!-- Complete Button -->
        <!-- <el-button type="primary" @click="modify">Completed</el-button> -->
    </div>
    </el-tabs>
</div>
</template>

<script>
  export default {
    name: "Create",
    props: {
      inline: Boolean,
      form: Object,
    },
    data() {
        return {
            active:'1',
        }
    },
    methods: {
        nextStep() {
            let num = Number(this.active)
            if(num<3){
                num++;
            }        
            // num<3 && num++
            this.active = num.toString()
        },
    },
    mounted() {
        console.log(this.form.projectId)
    },
  }
</script>