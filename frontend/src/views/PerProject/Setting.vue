
<template>
<v-card>
  <v-container>
    <v-row
      justify="space-between"
    >
      <v-col
        cols="12"
        md="4"
      >

      <div style="font-size: 1.25rem; font-weight: 500">
        Project Name:</div>
        
        <v-form ref="form">
          <v-text-field
            v-model="name"
            :counter="max"
            :rules="rules"
            outlined
          ></v-text-field> <!-- filled
              color="blue-grey lighten-2" -->
        </v-form>
  

       <div style="font-size: 1.25rem; font-weight: 500">
        Project Description:</div>

        <v-textarea
              v-model="description"
              :disabled="isUpdating"
              outlined
            > <!--filled
              color="blue-grey lighten-2" -->
        </v-textarea>
        
      </v-col>

      <v-col
        cols="12"
        md="6"
      ><!--7-->
      <div style="font-size: 1.25rem; font-weight: 500">
        Used Template:</div>
        <p></p>

        <v-card>
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
            <v-col
              :key="i"
              cols="12"
              md="4"
            >
            <!--
            <v-img src="/images/1.JPG" width="250px"/>-->
            <v-img :src= "nowTemplateImage"
                    width="225px"/>
            <v-card-title style="font-size:0.25rem; padding=0">
                {{ nowTemplateTitle }}
            </v-card-title> 
            </v-col>   
        </v-row>
        </v-card>
    
    <v-btn
      class="mx-2"
      fab
      dark
      small
      color="pink"
    >
      <v-icon dark>
        mdi-eye
      </v-icon>
    </v-btn>


    <v-dialog
      v-model="dialog"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          class="mx-2"
          fab
          dark
          small
          v-bind="attrs"
          v-on="on"
          color="cyan"
          @click="editTemplate"
        >
          <v-icon dark>
            mdi-pencil
          </v-icon>
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
          Privacy Policy
        </v-card-title>

        <v-card-text>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="dialog = false"
          >
            Cancel
          </v-btn>          
          <v-btn
            color="primary"
            text
            @click="dialog = false"
          >
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    </v-col>
    </v-row>

    <v-row style="padding:12px">
        <!--<v-col offset-md="6">5-->
        <div style="font-size: 1.25rem; font-weight: 500">
        Manipulation:</div>
    </v-row> 
    <v-row>  
       <v-col offset-md="1">
       <v-card rounded="0" outlined>      
        <v-checkbox label="Delete All Data"       
        ></v-checkbox>
       </v-card>
       
       <v-card rounded="0" outlined>
         <v-checkbox
          label="Delete All Annotations"
        ></v-checkbox>
        </v-card>
        <v-card rounded="0" outlined>
         <v-checkbox
          v-model="allowSpaces"
          label="Delete All Predictions"
        ></v-checkbox>
         </v-card>
         </v-col>  
    </v-row>
    
  </v-container>

   <v-card-actions>
      <v-switch
        v-model="autoUpdate"
        :disabled="isUpdating"
        class="mt-0"
        color="green lighten-2"
        hide-details
        label="Auto Update"
      ></v-switch>
      <v-spacer></v-spacer>
      <v-btn
        :disabled="autoUpdate"
        :loading="isUpdating"
        color="blue-grey darken-3"
        depressed
        @click="isUpdating = true"
      >
        <v-icon left>
          mdi-update
        </v-icon>
        Update Now
      </v-btn>
    </v-card-actions>

</v-card>
</template> 
 
    

<style>
</style>

<script>

export default {
  data() {
    return {
      dialog: false,
      items: [
                {
                title: 'Semantic Segmentation'+"\n"+' with Polygons',
                img: require('../../assets/logo.png'),
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
                img: '../../assets/logo.png',
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
                img: '../../assets/logo.png',
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
                img: '../../assets/logo.png',
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
                img: '../../assets/logo.png',
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
                img: './images/a.jpg',
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
                img: './images/b.jpg',
                config:`<View>
    <Header value="Please read the text" />
    <Text name="text" value="$text" />
    <Header value="Provide one sentence summary" />
    <TextArea name="answer" toName="text" showSubmitButton="true" maxSubmissions="1" editable="true" required="true" />
</View>`
                },
                {
                title: 'Text Classification',
                img: './images/c.jpg',
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
                img: './images/d.jpg',
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
      nowTemplateTitle:'',
      nowTemplateImage:'',
       name: this.$store.state.currentProject.name,
       description: this.$store.state.currentProject.description,
    }
  },
  mounted() {
    console.log('this.$store.state.currentProject', this.$store.state.currentProject)
    this.nowTemplateTitle = this.$store.state.currentConfigTitle
    for(var i=0; i<this.items.length; i++){
      if(this.nowTemplateTitle == this.items[i].title){
        this.nowTemplateImage = this.items[i].img
      }
    }
    console.log('ti,im', this.$store.state.currentConfigTitle,this.nowTemplateImage, this.$store.state.currentConfig)
},


}

</script>