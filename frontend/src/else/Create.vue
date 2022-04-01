<template>
<div>
  <!-- Basic information -->
  <el-form :inline="inline" :model="operateForm" ref="operateForm" :rules="rules" label-width="100px" class="demo-ruleForm"  style=" padding: 30px 60px 30px 30px;">
  <el-tabs v-model="active">
      <!-- Basic information -->
      <el-tab-pane name="1" >
          <span slot="label">Basic Info</span>
                  <!-- Project Name -->
                  <el-form-item label="Project Name" prop="name">
                      <el-input v-model="operateForm.name"></el-input>
                  </el-form-item>

                  <!-- Project Description -->
                  <el-form-item label="Description" style="margin-top:50px">
                      <el-input type="textarea" v-model="operateForm.description" placeholder="(Optional)" rows="10"></el-input>
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
            cols="8"
            md="4"
          ><!--cols-->
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
          v-model="operateForm.ml" 
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
      operateForm: Object,
    },

    data:() => ({
      file:'',
      icons: ['mdi-arrow-right'],
      imageTool: [
        {
          title: 'Polygons',
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
          img: '/images/2.jpg',
          config:`<View>
                   <Image name="image" value="$image" zoom="true"/>
                   <BrushLabels name="tag" toName="image">
                        <Label value="Airplane" background="rgba(255, 0, 0, 0.7)"/>
                        <Label value="Car" background="rgba(0, 0, 255, 0.7)"/>
                   </BrushLabels>
                  </View>`
        },
        {
          title: 'Bounding Boxes',
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
          img: '/images/4.jpg',
          config:`<View>
                   <Image name="img" value="$image" zoom="true"></Image>
                   <KeyPointLabels name="tag" toName="img" fillcolor="red">
                       <Label value="Hello" background="yellow"></Label>
                       <Label value="World" background="blue"></Label>
                   </KeyPointLabels>
                </View>`
        },
        {
          title: 'Image Classification',
          img: '/images/5.jpg',
          config:` <View>
                     <Image name="image" value="$image"/>
                     <Choices name="choice" toName="image">
                         <Choice value="Adult content"/>
                         <Choice value="Weapons" />
                     <Choice value="Violence" />
                     </Choices>
                   </View>`
        }
      ],

      textTool: [
        {
          title: 'Highlighter',
          img: '/images/a.jpg',
          config:`<View>
                    <Labels name="label" toName="text">
                        <Label value="PER" background="red"/>
                        <Label value="ORG" background="darkorange"/>
                        <Label value="LOC" background="orange"/>
                        <Label value="MISC" background="green"/>
                    </Labels>
                    <Text name="text" value="$text"/>
                  </View>`
        },
        {
          title: 'Input Text',
          img: '/images/b.jpg',
          config:`<View>
                     <Header value="Please read the text" />
                     <Text name="text" value="$text" />
                     <Header value="Provide one sentence summary" />
                     <TextArea name="answer" toName="text" showSubmitButton="true" maxSubmissions="1" editable="true" required="true" />
                  </View>`
        },
        {
          title: 'Radio Button',
          img: '/images/c.jpg',
          config:`<View>
                    <Text name="text" value="$text"/>
                    <View style="box-shadow: 2px 2px 5px #999; padding: 20px; margin-top: 2em; border-radius: 5px;">
                       <Header value="Choose text sentiment"/>
                       <Choices name="sentiment" toName="text" choice="single" showInLine="true">
                           <Choice value="Positive"/>
                           <Choice value="Negative"/>
                           <Choice value="Neutral"/>
                       </Choices>
                    </View>
                 </View>`
        },
        {
          title: 'Checklist',
          img: '/images/d.jpg',
          config:`<View>
                     <Text name="text" value="$text"/>
                     <Taxonomy name="taxonomy" toName="text">
                        <Choice value="Archaea" />
                        <Choice value="Bacteria" />
                        <Choice value="Eukarya">
                            <Choice value="Human" />
                            <Choice value="Oppossum" />
                            <Choice value="Extraterrestial" />
                        </Choice>
                     </Taxonomy>
                   </View>`
        },
        /*
        {
          title: 'Qustion Answering',
          img: '/images/e.jpg',
          config:`<View>
                     <Header value="Please read the passage" />
                     <Text name="text" value="$text" granularity="word"/>
                     <Header value="Select a text span answering the following question:"/>
                     <Text name="question" value="$question"/>
                     <Labels name="answer" toName="text">
                         <Label value="Answer" maxUsage="1" background="red"/>
                     </Labels>
                  </View>`
        },
        */
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
        this.operateForm.projectId = 12;
        console.log(this.operateForm.projectId)
    },
  }
</script>


<style scoped>

.v-card {
  transition: opacity .2s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.8;
 }

.show-btns-1 {
  color: rgba(255, 255, 255, 1) !important;
}

.show-btns-2 {
  color: rgb(0, 0, 0) !important;
}


.con{
    height: 100%;/*500px也一样*/
    overflow-y:scroll;
}

.v-tab{
    justify-content:left;
    padding: 6px;/* */
}

.el-form{
  padding: 20px 50px 0px 50px!important;
}

</style>
