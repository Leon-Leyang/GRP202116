
<template>
<v-card style="height:100%;background:rgb(218, 253, 249)">
  <v-container style="height:80%">
    <v-row
      justify="space-between"
      style="height:60%"
    >
      <v-col
        cols="12"
        md="4"
      >

      <div style="font-size: 1.25rem; font-weight: 500">
        Project Name:</div>
        
        <el-input clearable maxlength="" size="small" suffix-icon="el-icon-edit el-input__icon"></el-input>
  

       <div style="font-size: 1.25rem; font-weight: 500">
        Project Description:</div>

        <el-input
          type="textarea"
          :rows="3"
          placeholder="(Optional)"
          v-model="textarea"
          >
        </el-input>
        
      </v-col>

      <v-col
        cols="12"
        md="6"
        style="height:100%"
      ><!--7-->
          <div style="font-size: 1.25rem; font-weight: 500">
            Used Template:</div>
            <p></p>

          <v-card height="88%" class="LIcardA">
          <v-container>
          <v-row
          class="fill-height"
            align="center"
            justify="center"
            style="width:100%;"
            
          >
          <template v-for="(item, i) in items">
              <v-col
                :key="i"
                cols="12"
                md="4"
              >
              <v-img :src="item.img"
                      width="225px"/>
              <v-card-title style="font-size:0.25rem" class="LIcard">
                  {{ item.title }}
              </v-card-title> 
              </v-col>   
          </template>
          </v-row>
          </v-container>
          </v-card>
    
          <div style="height:20%">
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

          <v-btn
            class="mx-2"
            fab
            dark
            small
            color="cyan"
          >
            <v-icon dark>
              mdi-pencil
            </v-icon>
          </v-btn>
          </div>

       </v-col>


    </v-row>

    <v-row>
        <v-col>
        <!--<v-col offset-md="6">5-->
        <div style="font-size: 1.25rem; font-weight: 500">
        Manipulation:</div>
        </v-col>
    </v-row> 
    <v-row style="margin:0" justify="center">  
       <v-col>
       <v-card rounded="0" outlined style="height:34px">      
        <v-checkbox label="Delete All Data"  
        style="margin:0"></v-checkbox>
       </v-card>
       
       <v-card rounded="0" outlined style="height:34px">
         <v-checkbox
          label="Delete All Annotations"
         style="margin:0"></v-checkbox>
        </v-card>
        <v-card rounded="0" outlined style="height:34px">
         <v-checkbox
          v-model="allowSpaces"
          label="Delete All Predictions"
         style="margin:0"></v-checkbox>
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

.LIcardA{
  overflow-y: scroll;
  width: 100%;
}

.LIcard{
  padding:0!important;
}

/*
  .content{
    width:90%;
    margin: 0 auto;
  }


.v-input--selection-controls {
   margin-top:0!important;
}

.v-card__subtitle, .v-card__text, .v-card__title{
        padding: 0;
}

.v-text-field {
    padding-top: 0px!important;
    margin-top: 0px!important; 
    margin-bottom: 20px;
}

.v-messages{
    min-height: 0!important;
}*/
</style>

<script>

export default {
    data: () => ({

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
      ],
    
    
    })
}

</script>