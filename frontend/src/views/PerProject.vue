<!--This is the page after user enter one project,
contain four parts: DataList, Statistic, ML, Setting
@author LinjingSUN YingjiaLi-->
<template>
  <v-card id="problock" style="background-color:#9FA8DA">
    <v-btn
      color="blue-grey"
      @click="back"
      text
      large
      style="font-size:23px;margin-top:10px"
    >  
      <v-icon
        left x-large
      > mdi-chevron-left
      </v-icon>
    </v-btn>
    <v-tabs
        v-model="tabs"
        centered
        grow
        color="#5E35B1"
        background-color="#9FA8DA"
        
    >
        <v-tab style="font-size:25px">
            Data
        </v-tab>
        <v-tab style="font-size:25px">
            Statistics
        </v-tab>
        <v-tab style="font-size:25px">
            Machine Learning Model
        </v-tab>
        <v-tab style="font-size:25px">
            Settings
        </v-tab>
    </v-tabs>
    <v-divider></v-divider>
    <v-tabs-items v-model="tabs">
      <v-tab-item>
        <v-card flat class="tabcard" v-if="tabs==0">
          <Table/>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat v-if="tabs==1" class="tabcard">
            <Statistics></Statistics>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat v-if="tabs==2" class="tabcard">
          <ML></ML>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat v-if="tabs==3" class="tabcard">
          <Setting></Setting>
        </v-card>
      </v-tab-item>
    </v-tabs-items>

  </v-card>
</template>


<script>
import Table from '../components/ProjectManage/DataTable';
import Statistics from '@/views/PerProject/Statistics'
import ML from '@/views/PerProject/ML'
import Setting from '@/views/PerProject/Setting'

  export default {
    components: {
      Statistics,
      ML,
      Setting,
      Table,
    },
    data () {
      return {
        format:'json',
        fileFormat:['json','csv','tsv'],
        id:null,
        name:null,
        projectId:0,
        tabs: null,
        dataList:[],
      }
    },
    methods: {
      back(){
        this.$router.push({  
            path: '/',
            name: 'Home', 
        }) 
      },

    },
    mounted() {
      this.id = this.$store.state.currentProjectId
      this.name = this.$store.state.currentProject.name
      console.log('id name', this.id, this.name)
      this.$store.state.currentMLList = null
      console.log('label', this.$store.state.currentConfig)
      this.projectId = this.$store.state.currentProjectId      
    },
  }
</script>

<style>
#problock{
    margin: 0 auto;
    width: 90%;
   /* height: 90%;*/
}

.tabcard{
    height:700px;
 
}

.v-tabs-slider{
  color:#5E35B1;
}



</style>