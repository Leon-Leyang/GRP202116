<!-- 项目进入后的具体页面 -->
<template>
  <v-card id="problock">
<button @click="back">Back</button>
    <v-tabs
        v-model="tabs"
        centered
        grow
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
        <v-card flat>
          <Table :dataList="dataList" :config="config" />
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
            <Statistics></Statistics>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <ML></ML>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
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
      }
    },
    mounted() {
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
}
</style>