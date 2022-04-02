
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
     <template v-for="(item, i) in items">
        <v-col
          :key="i"
          cols="12"
          md="4"
        >
        <!--
        <v-img src="/images/1.JPG" width="250px"/>-->
        <v-img :src="item.img"
                width="225px"/>
        <v-card-title style="font-size:0.25rem; padding=0">
            {{ item.title }}
        </v-card-title> 
        </v-col>   
     </template>
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

        <!--
        <p></p>
        <v-card>      
        <v-checkbox
          label="De"       
        ></v-checkbox>
         <v-checkbox 
          label="Dele"
        ></v-checkbox>
         <div style="margin: 0">
         <v-checkbox
          v-model="allowSpaces"
          label="Delete"
        ></v-checkbox>  </div>
        </v-card> 
         -->

      <!--</v-col>-->
    
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
/*
  .content{
    width:90%;
    margin: 0 auto;
  }
*/

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
}
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
      ],
      
       name: 'Hey!',
       description: "This is a project for the ..."
    
    })
}

</script>