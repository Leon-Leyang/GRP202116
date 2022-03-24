<template>
<div>
  <!-- Basic information -->
  <el-form :inline="inline" :model="form" ref="form" :rules="rules" label-width="100px" class="demo-ruleForm"  style=" padding: 30px 60px 30px 30px;">
  <el-tabs v-model="active">
      <!-- Basic information -->
      <el-tab-pane name="1" >
          <span slot="label">Basic Info</span>
                  <!-- Project Name -->
                  <el-form-item label="Project Name" prop="name">
                      <el-input v-model="form.name"></el-input>
                  </el-form-item>

                  <!-- Project Description -->
                  <el-form-item label="Description" style="margin-top:50px">
                      <el-input type="textarea" v-model="form.description" placeholder="(Optional)" rows="10"></el-input>
                  </el-form-item>   
      </el-tab-pane>

      <!-- Setting Label Interface -->
      <el-tab-pane name="2">
      <span slot="label">Setup Labeling Interface</span>

      <v-tabs vertical>
        <v-tab>
          <v-icon left>
            mdi-image-area
          </v-icon>
          For Image
        </v-tab>
    
        <v-tab>
          <v-icon left>
            mdi-card-text
          </v-icon>
          For Text
        </v-tab>

        
    <!--1st-->
        <v-tab-item class="tabitem"> <!--删掉tabitem,card-->
          
            
            <v-container class="con">

        <v-row
        class="fill-height"
        align="center"
        justify="center"
      >
        <template v-for="(item, i) in imageTool">
          <v-col
            :key="i"
            cols="12"
            md="4"
          >
            <v-hover v-slot="{ hover }">
              <v-card
                :elevation="hover ? 12 : 2"
                :class="{ 'on-hover': hover }"
              >
                <v-img
                  :src="item.img"
                  height="225px"
                >
                  <v-card-title class="text-h6 white--text">
                    <v-row
                      class="fill-height flex-column"
                      justify="space-between"
                    >
                      <p class="mt-4 subheading text-left">
                        {{ item.title }}
                      </p>
                      
                    <!-- 
                    <div><p></p><p></p></div>
                    <div><p></p></div>
                    -->
                    
                      <div> <!--class="align-self-center"-->
                        <v-btn
                          v-for="(icon, index) in icons"
                          :key="index"
                          :class="{ 'show-btns-1': hover }"
                          :color="transparent"
                          x-large
                          icon
                        >
                        
                          <v-icon
                            :class="{ 'show-btns-1': hover }"
                            :color="transparent"
                            
                          ><!-- :size="large" not work-->
                            {{ icon }}
                          </v-icon>
                          
                          
                        </v-btn>
                      </div>
                    </v-row>
                  </v-card-title>
                </v-img>
              </v-card>
            </v-hover>
          </v-col>
        </template>
      </v-row>

      </v-container>
          
        </v-tab-item>
    


      <!--2nd-->
        <v-tab-item class="tabitem"> 
          <!--删掉card-->
            
            <v-container class="con">

      <v-row
        class="fill-height"
        align="center"
        justify="center"
      >
        <template v-for="(item, i) in textTool">
          <v-col
            :key="i"
            cols="12"
            md="5"
          >
            <v-hover v-slot="{ hover }">
              <v-card
                :elevation="hover ? 12 : 2"
                :class="{ 'on-hover': hover }"
              >
                <v-img
                  :src="item.img"
                  height="225px"
                >
                  <v-card-title class="text-h6 black--text">
                    <v-row
                      class="fill-height flex-column"
                      justify="space-between"
                    >
                      <p class="mt-4 subheading text-left">
                        {{ item.title }}
                      </p>
      <div> <!--class="align-self-center"-->
                        <v-btn
                          v-for="(icon, index) in icons"
                          :key="index"
                          :class="{ 'show-btns-2': hover }"
                          :color="transparent"
                          icon
                          x-large
                        >
                        
                          <v-icon
                            :class="{ 'show-btns-2': hover }"
                            :color="transparent" 
                          >{{ icon }}
                          </v-icon>
                          
                          
                        </v-btn>
                      </div>
                    </v-row>
                  </v-card-title>
                </v-img>
              </v-card>
            </v-hover>
          </v-col>
        </template>
      </v-row>

    </v-container>
        
        </v-tab-item>
      </v-tabs>
      

      </el-tab-pane>

      <!-- Import ML -->
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
      <!-- Import Data -->
      <el-tab-pane name="4">
          <span slot="label">Import Data</span>
          <span>Please upload the appropriate type of file</span>
          <el-upload
            class="upload-demo"
            ref="upload"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">Select</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">Upload</el-button>
            <div slot="tip" class="el-upload__tip">Please upload the appropriate type of file</div>
          </el-upload>

          <!-- Import dataset -->
          <!-- <el-upload
          class="upload-demo"
          drag
          action="/api/dataset"
          v-model="form.data" 
          multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drag & drop files here</div>
          <div class="el-upload__text"><em>Click to add</em></div>
          <div class="el-upload__tip" slot="tip">( The format of uploading file should be <span style="font-style:italic; color: #719DDD">.txt, .jpg, .png, .gif, .bmp, .svg, .webp, .csv, .tsv, .json</span> )</div>
          </el-upload> -->
      </el-tab-pane>

  </el-tabs>
  </el-form>
    <!-- Button block -->
    <!-- <div style="display: flex; float: right; width:35%">
        <el-button class="next" @click="nextStep" style="float: right" icon="el-icon-d-arrow-right" circle></el-button>
    </div> -->
</div>
</template>

<script>
  export default {
    name: "Create",
    props: {
      inline: Boolean,
      form: Object,
    },

    data:() => ({
      file:'',
      icons: ['mdi-arrow-right'],
      imageTool: [
        {
          title: 'Polygons',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/1.jpg',
          config:`<View>
                  <Image name="img" value="$image" showMousePos="true" zoom="true"></Image>
                  <PolygonLabels name="tag" toName="img" strokewidth="5" fillcolor="red" pointstyle="circle" pointsize="small">
                    <Label value="Hello" background="red"></Label>
                    <Label value="World" background="blue"></Label>  
                  </PolygonLabels>
                </View>`
        },
        {
          title: 'Masks',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/2.jpg',
          config:''
        },
        {
          title: 'Bounding Boxes',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/3.jpg',
          config:`<View>
                  <Image name="img" value="$image"></Image>
                  <RectangleLabels name="tag" toName="img" fillOpacity="0.5" strokeWidth="5">
                    <Label value="Planet"></Label>
                    <Label value="Moonwalker" background="blue"></Label>
                  </RectangleLabels>
                </View>`
        },
        {
          title: 'Keypoints',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/4.jpg',
          config:`<View>
                  <Image name="img" value="$image" zoom="true"></Image>
                  <KeyPointLabels name="tag" toName="img" fillcolor="red">
                    <Label value="Hello" background="yellow"></Label>
                    <Label value="World" background="blue"></Label>
                  </KeyPointLabels>
                </View>`
        },
      ],

      textTool: [
        {
          title: 'Highlighter',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/a.jpg',
          config:''
        },
        {
          title: 'Input Text',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/2.jpg',
          config:''
        },
        {
          title: 'Radio Button',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/3.jpg',
          config:''
        },
        {
          title: 'Checklist',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/d.jpg',
          config:''
        }
      ],


      transparent: 'rgba(255, 255, 255, 0)',
      active:'1',
        
      })  ,
    
    methods: {

        nextStep() {
            console.log('tag', this.file)
            let num = Number(this.active)
            if(num<4){ /*       */
                num++;
            }        
            // num<3 && num++
            this.active = num.toString()
        },
    },
    mounted() {
        this.form.projectId = 12;
        console.log(this.form.projectId)
    },
  }
</script>


<style scoped>

.v-card {
  transition: opacity .2s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.7;
 }

.show-btns-1 {
  color: rgba(255, 255, 255, 1) !important;
}

.show-btns-2 {
  color: rgb(0, 0, 0) !important;
}


.con{
    height: 380px;/*500px也一样*/
    overflow-y:scroll;
}

.v-tab{
    justify-content:left;
    padding: 6px;/* */
}

</style>
