<template>
  <v-card id="problock" style="background-color:#D1C4E9">
  <!-- <v-btn @click="back">Back</v-btn>
  <v-btn @click="download(id,name)"> Down Load </v-btn>
    <v-radio-group v-model="format" row>
      <v-radio
        v-for="n in fileFormat"
        :key="n"
        :label="n"
        :value="n"
      ></v-radio>
    </v-radio-group> -->
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
        background-color="deep-purple lighten-4"
        
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
import { Modal } from "antd";

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

      download(id, name) {
        console.log('format', this.format)
        console.log('id$name', id, name)
        this.$axios.get('/project/'+ id +'/data_export/annotations/'+ this.format, {
          responseType: 'blob'
        }).then((res)=> {
          console.log('resas', res.headers['content-disposition'])
          const content = res.data
          const filename = window.decodeURI(res.headers['content-disposition'].split('=')[1], "UTF-8");
          const blob = new Blob([content])
          console.log('content blob', content, blob)
          if ('download' in document.createElement('a')) {
            const elink = document.createElement('a');
            elink.download = filename ;
            elink.style.display = 'none';
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href);
            document.body.removeChild(elink);
            Modal.success({
              title: "Success",
              okText:"Confirm",
              content: "Start download...",
              onOk: () => {}
            })
          } else {
            navigator.msSaveBlob(blob, filename)
          }
        }, function(err) {
          console.log('err', err)
        })
      }

    },
    watch:{
      tabs:function(val){
        console.log('val', val)
        this.$store.state.currentMLList = null
        console.log('label', this.$store.state.currentConfig)
        this.projectId = this.$store.state.currentProjectId
        console.log('liugo', this.$store.state.currentDataList)
      }
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