<!--This is the setting page
In this page user can change project name, description, labeling Interafce,
and also can delete all data or delete all annotation or delete all prediction
@author LinjingSUN YingjiaLI-->
<template>
<v-card style="height:100%;background:#f1f2fa;padding:10px">
  <v-container style="height:80%">
    <v-row
      justify="space-between"
    >
      <v-col
        cols="12"
        md="4"
      >

      <div style="font-size:30px; font-weight: 500;margin-bottom:20px">
        Project Name:</div>
        
        <el-input v-model="name"
            :counter="max"
            :rules="rules"
            outlined
            style="font-size:2rem"></el-input>
  

       <div style="font-size:30px; font-weight: 500; margin-top: 25px">
        Project Description:</div>

        <el-input
          type="textarea"
          :rows="3" 
          placeholder="(Optional)"
          v-model="description"
          :disabled="isUpdating"
          outlined
          style="font-size:1.7rem"
          >
        </el-input>
        
      </v-col>

      <v-col
        cols="12"
        md="6"
        style="height:100%"
      >
          <div style="font-size:30px; font-weight: 500">
            Used Template:</div>
            

          <v-card height="88%" class="LIcardA">
          <v-container>
          <v-row
          class="fill-height"
            align="center"
            justify="center"
            style="width:100%;"
            
          >
          
              <v-col
                :key="i"
                cols="12"
                md="4"
              >
               <img :src= nowTemplateImage
                    width="225px">
              <v-card-title style="font-size:0.5rem" class="LIcard">
                  {{ nowTemplateTitle }}
              </v-card-title> 
              </v-col>   
          
          </v-row>
          </v-container>
          </v-card>
    
          <v-dialog
      v-model="dialog"
      width="1600"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          style="margin-top:30px"
          fab
          dark
          large
          v-bind="attrs"
          v-on="on"
          color="cyan"
          @click="editTemplate"
        >
          <v-icon dark x-large>
            mdi-pencil
          </v-icon>
        </v-btn>
      </template>

      <v-card height="700">
        <SettingLS style="height:650px"></SettingLS>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="cancel()"
          >
            Cancel
          </v-btn>          
          <v-btn
            color="primary"
            text
            @click="confirm()"
          >
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
   <v-card-actions>
     <v-spacer></v-spacer>
      <v-btn
        color="#7986CB"
        depressed
        @click="save()"
        style="font-size: 2.5rem; font-weight: 500; margin-top:6rem; height:4rem"
      >
        <v-icon left x-large>
          mdi-update
        </v-icon>
        SAVE
      </v-btn>
    </v-card-actions>
       </v-col>


    </v-row>

    <v-row>
        <v-col>
        <div style="font-size:30px; font-weight: 500">
        Manipulation:</div>
        </v-col>
    </v-row> 

  <div style="margin-top:45px">
   <v-card outlined>
    <v-row align="center"
    justify="space-around" style="padding:20px 20px 20px 20px">

    
    <v-dialog
      v-model="dialoga"
      persistent
      max-width="1000"
    >
      <template v-slot:activator="{ on, attrs }">
        
        <v-btn
        rounded
          color="#B71C1C"
          dark
          v-bind="attrs"
          v-on="on"
          style="font-size:30px; font-weight: 500"
          height="60px"
        >
          Delete All Data
        </v-btn>
         
      </template>
      <v-card>
        <v-card-title class="text-h5">
          Delete All Data?
        </v-card-title>
        <v-card-text>This will empty all the data files in this project.</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="deleteAllData(),dialoga = false"
          >
            Confirm
          </v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="dialoga = false"
          >
            Cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    

   
    <v-dialog
      v-model="dialogb"
      persistent
      max-width="1000"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
        rounded
          color="#B71C1C"
          dark
          v-bind="attrs"
          v-on="on"
          style="font-size:30px; font-weight: 500"
          height="60px"
        >
          Delete All Annotations
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="text-h5">
          Delete All Annotations?
        </v-card-title>
        <v-card-text>This will empty all the annotations you have made in this project.</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="deleteAllAnno(),dialogb = false"
          >
            Confirm
          </v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="dialogb = false"
          >
            Cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
   

    
    <v-dialog
      v-model="dialogc"
      persistent
      max-width="1000"
      
    ><!-- max-height="1000!impotant"-->
      <template v-slot:activator="{ on, attrs }">
        <v-btn
        rounded
          color="#B71C1C"
          dark
          v-bind="attrs"
          v-on="on"
          style="font-size:30px; font-weight: 500"
          height="60px"
        >
          Delete All Predictions
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="text-h5">
          Delete All Predictions?
        </v-card-title>
        <v-card-text>This will empty all the existing predictions in this project.</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="deleteAllPredict(),dialogc = false"
          >
            Confirm
          </v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="dialogc = false"
          >
            Cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
   

    </v-row>
    </v-card> 
  </div>

</v-container>

</v-card>
</template> 
 
    

<style>

.LIcardA{
  overflow-y: scroll;
  width: 100%;
}

.LIcard{
  padding:0!important;
}


</style>

<script>



import SettingLS from '../../components/ProjectManage/SettingLS.vue'
export default {
  components:{
    SettingLS
  },
  data() {
    return {
      dialog: false,
      dialoga: false,
      dialogb: false,
      dialogc: false,
      items: [
                {
                title: 'Semantic Segmentation'+"\n"+' with Polygons',
                img: '/images/1.png',
                config:`<View>
    <Image name="img" value="$image" showMousePos="true" zoom="true"></Image>
    <PolygonLabels name="tag" toName="img" strokewidth="5" fillcolor="red" pointstyle="circle" pointsize="small">
        <Label value="Hello" background="red"></Label>
        <Label value="World" background="blue"></Label>  
    </PolygonLabels>
</View>`
                },
                {
                title: 'Semantic Segmentation'+"\n"+' with Masks',
                img: '/images/2.png',
                config:`<View>
    <Image name="image" value="$image" zoom="true"/>
    <BrushLabels name="tag" toName="image">
            <Label value="Airplane" background="rgba(255, 0, 0, 0.7)"/>
            <Label value="Car" background="rgba(0, 0, 255, 0.7)"/>
    </BrushLabels>
</View>`
                },
                {
                title: 'Object Detection'+"\n"+' with Bounding Boxes',
                img: './images/3.png',
                config:`<View>
    <Image name="img" value="$image"></Image>
    <RectangleLabels name="tag" toName="img" fillOpacity="0.5" strokeWidth="5">
            <Label value="Planet"></Label>
            <Label value="Moonwalker" background="blue"></Label>
    </RectangleLabels>
</View>`
                },
                {
                title: 'Keypoint Labeling',
                img: './images/4.png',
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
                img: './images/5.png',
                config:` <View>
    <Image name="image" value="$image"/>
    <Choices name="choice" toName="image">
        <Choice value="Adult content"/>
        <Choice value="Weapons" />
    <Choice value="Violence" />
    </Choices>
</View>`
                },
                {
                title: 'Named Entity Recognition',
                img: './images/a.png',
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
                title: 'Machine Translation',
                img: './images/b.png',
                config:`<View>
    <Header value="Please read the text" />
    <Text name="text" value="$text" />
    <Header value="Provide one sentence summary" />
    <TextArea name="answer" toName="text" showSubmitButton="true" maxSubmissions="1" editable="true" required="true" />
</View>`
                },
                {
                title: 'Text Classification',
                img: './images/c.png',
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
                title: 'Text Taxonomy',
                img: './images/d.png',
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
      ],
/*
   items: [
        {
          title: 'Polygons',
          img: '/images/1.JPG',
        },
        {
          title: 'Masks',     
          img: '/images/2.JPG',
        },
         {
          title: 'Masks',     
          img: '/images/2.JPG',
        },
         {
          title: 'Masks',     
          img: '/images/2.JPG',
        },
         {
          title: 'Masks',     
          img: '/images/2.JPG',
        },
      ],*/

      nowTemplateTitle:'',
      nowTemplateImage:'',
      name: this.$store.state.currentProject.name,
      description: this.$store.state.currentProject.description,
      beforeConfig:``,
    }
  },
  methods: {
    confirm(){
      this.dialog = false
    },
    cancel(){
      this.$store.state.currentConfig = this.beforeConfig
      this.dialog = false
    },
    save(){
      console.log('confifg', this.$store.state.currentProject)
      this.$store.state.currentProject.name = this.name
      this.$store.state.currentProject.description = this.description
      this.$store.state.currentProject.configs = this.$store.state.currentConfig
      console.log('confifg', this.$store.state.currentProject.configs,this.$store.state.currentConfig)
      var operateForm = this.$store.state.currentProject
      console.log("test",operateForm)
      operateForm.createTime = "2022-04-03T16:00:00.000+00:00"
      operateForm.updateTime = "2022-04-03T16:00:00.000+00:00"
      this.$axios.post(`/project/edit`, JSON.stringify(operateForm))
          .then(res => {
          console.log('edit',res)
      })      
    },
    deleteAllData(){
      this.$axios.delete('/data/project/'+ this.$store.state.currentProjectId)
      .then((res)=>{
        console.log('deleteAllData', res)
        console.log('this.$store.state.currentDataList', this.$store.state.currentDataList)
        this.$store.state.currentDataList = []
      })
    },
    deleteAllAnno(){
      this.$axios.delete('/annotation/project/'+ this.$store.state.currentProjectId)
      .then((res)=>{
        console.log('deleteAllAnno', res)
      })      
    },
    deleteAllPredict(){
      this.$axios.delete('/prediction/project/'+ this.$store.state.currentProjectId)
      .then((res)=>{
        console.log('deleteAllPredict', res)
      })      
    },    
  },
  mounted() {
    console.log('this.$store.state.currentProject', this.$store.state.currentProject)
    this.nowTemplateTitle = this.$store.state.currentConfigTitle
    // this.$store.state.beforeConfig = this.$store.state.currentConfig
    this.beforeConfig = this.$store.state.currentConfig
    for(var i=0; i<this.items.length; i++){
      if(this.nowTemplateTitle == this.items[i].title){
        this.nowTemplateImage = this.items[i].img
      }
    }
    console.log('ti,im', this.$store.state.currentConfigTitle,this.nowTemplateImage, this.$store.state.currentConfig)
},


}


</script>
    <style>
      .el-input__inner{
        height: 60px!important;
      }
    </style>