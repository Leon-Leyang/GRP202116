<!-- 项目进入后的具体页面 -->
<template>
  <v-card id="problock">

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
          <Table :tableData="tableData" :tableLabel="tableLabel" :config="config" @changePage="getList()" />
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
        tableData:[],
        config: {          //重要的地方
              page: 1,
              total: 30,
              loading: false
          },
      }
    },
    methods: {
      getData() {
          this.$axios.get('/data/project/'+this.projectId)
              .then(res => {
                console.log('data', res)
              })
              .catch((error) => {
              // here you will have access to error.response
                console.log(error.response)
                });
      },
    },
    mounted() {
      this.projectId = this.$route.params.projectId
      console.log('projectId',this.projectId)
      this.getData()
    },
  }
</script>

<style>
#problock{
    margin: 0 auto;
    width: 90%;
}
</style>