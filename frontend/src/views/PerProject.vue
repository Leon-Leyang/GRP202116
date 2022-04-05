<!-- 项目进入后的具体页面 -->
<template>
  <v-card id="problock">
  <button @click="back">Back</button>
  <button @click="download(id,name)" type="button"> Down Load </button>
<!-- <v-btn @click="deleteSelectBtn()"></v-btn> -->
    <v-radio-group v-model="format" row>
      <v-radio
        v-for="n in fileFormat"
        :key="n"
        :label="n"
        :value="n"
      ></v-radio>
    </v-radio-group>
    <v-tabs
        v-model="tabs"
        centered
        grow
        color="yellow"
        background-color="rgb(113, 223, 207)"
    >
        <v-tab>
            Data
        </v-tab>
        <v-tab>
            Statistics
        </v-tab>
        <v-tab>
            Machine Learning Model
        </v-tab>
        <v-tab>
            Settings
        </v-tab>
    </v-tabs>
    <v-divider></v-divider>
    <v-tabs-items v-model="tabs">
      <v-tab-item>
        <v-card flat class="tabcard" v-if="tabs==0">
          <Table :dataList="dataList" :config="config" />
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
        config: {          //Important
              page: 1,
              total: 30,
              loading: false
          },
      }
    },
    methods: {
      back(){
        this.$router.push({  
            path: '/',
            name: 'Home', 
        }) 
      },
      deleteSelectBtn(){
        for(var i = 0;i<this.$store.state.selectData.length;i++){
          this.$axios.delete('/data/'+this.$store.state.selectData[i])
          .then((res)=>{
            console.log('delete select data', res)
          })
        }

      },
      download(id, name) {
        console.log('format', this.format)
        console.log('id$name', id, name)
        this.$axios.get('/project/'+ id +'/data_export/annotations/csv', {
          responseType: 'blob'
        }).then((res)=> {
          console.log('res', res)
          const content = res.data;
          const blob = new Blob([content])
          console.log('content blob', content, blob)
          if ('download' in document.createElement('a')) {
            const elink = document.createElement('a');
            elink.download = name;
            elink.style.display = 'none';
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href);
            document.body.removeChild(elink);
          } else {
            navigator.msSaveBlob(blob, name)
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
        console.log('projectId',this.projectId)
              this.$axios.get('/data/project/'+ this.projectId)
                  .then(res => {
                      console.log('res', res)
                      this.$store.state.currentDataList = res.data
                      // this.$store.state.currentDataList.
                      console.log('store datalist', this.$store.state.currentDataList)
                  })
                  .catch((error) => {
                  // here you will have access to error.response
                  console.log(error.response)
                  });
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
      console.log('projectId',this.projectId)
            this.$axios.get('/data/project/'+ this.projectId)
                .then(res => {
                    console.log('res', res)
                    this.$store.state.currentDataList = res.data
                    // this.$store.state.currentDataList.
                    console.log('store datalist', this.$store.state.currentDataList)
                })
                .catch((error) => {
                // here you will have access to error.response
                console.log(error.response)
                });
      console.log('liugo', this.$store.state.currentDataList)
      
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
    height:480px/*100%*/;
 
}

.v-tabs-slider{
  color:yellow;
}



</style>