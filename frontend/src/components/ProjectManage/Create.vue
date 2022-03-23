<template>
<div>
<el-tabs v-model="active">
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
        <span>Please enter the path of the folder where the file to be marked resides:</span>

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

     <el-tab-pane name="4">
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
      <template v-for="(item, i) in items1">
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
      <template v-for="(item, i) in items2">
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
         

    <!-- Button block -->
    <div style="display: flex; float: right; width:35%">
        <!-- Next Button -->
        <el-button type="primary" @click="nextStep" style="margin-right:10%">Next</el-button>
        <el-button class="next" @click="nextStep" style="float: right" icon="el-icon-d-arrow-right" circle></el-button>
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
    data:() => ({
      icons: ['mdi-arrow-right'],
      items1: [
        {
          title: 'Polygons',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/1.jpg',
        },
        {
          title: 'Masks',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/2.jpg',
        },
        {
          title: 'Bounding Boxes',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/3.jpg',
        },
        {
          title: 'Keypoints',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/4.jpg',
        },

        {
          title: 'Polylines',
          text: 'Greatest Rock Hits',
          subtext: 'Lose yourself in rock tunes.',
          img: 'https://images.unsplash.com/photo-1429514513361-8fa32282fd5f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3264&q=80',
        },
        {
          title: 'Segmentation Lines',
          text: 'Ambient Bass',
          subtext: 'Chill beats to mellow you out.',
          img: 'https://images.unsplash.com/photo-1542320868-f4d80389e1c4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=3750&q=80',
        },
        {
          title: 'New Releases',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: 'https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80',
        },
        {
          title: 'New Releases',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: 'https://images.unsplash.com/photo-1429514513361-8fa32282fd5f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3264&q=80',
        }
      ],

      items2: [
        {
          title: 'Highlighter',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/a.jpg',
        },
        {
          title: 'Input Text',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/2.jpg',
        },
        {
          title: 'Radio Button',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/3.jpg',
        },
        {
          title: 'Checklist',
          text: `It's New Release Friday`,
          subtext: 'Newly released songs. Updated daily.',
          img: '/images/d.jpg',
        }
      ],


      transparent: 'rgba(255, 255, 255, 0)',
   


        
            active:'1',
        
      })  ,
    
    methods: {
        nextStep() {
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
