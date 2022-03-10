<template>
  <v-card>
    <v-tabs vertical  class="bar">
      <v-tab>
        Data
      </v-tab>
      <v-tab>
        Annotation
      </v-tab>
      <v-tab>
        Prediction
      </v-tab>
      <v-tab-item class="content">
        <v-card flat>
          <Data></Data>
        </v-card>
      </v-tab-item>
      <v-tab-item class="content">
        <v-card flat>
          <Annotation></Annotation>
        </v-card>
      </v-tab-item>
      <v-tab-item class="content">
        <v-card flat>
          <Prediction></Prediction>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-card>
</template>

<script>
import Data from '@/views/PerProject/Satistics/Data'
import Annotation from '@/views/PerProject/Satistics/Annotation'
import Prediction from '@/views/PerProject/Satistics/Prediction'
export default {
    components: {
      Data,
      Annotation,
      Prediction
    }  

}
</script>

<style>
  .content{
    width:80%;
    margin: 0 auto;
  }
  .bar{
    padding-top: 20px;
  }
</style>