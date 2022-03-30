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
    mounted() {
      this.projectId = this.$store.state.currentProjectId
      console.log('projectId',this.projectId)
      console.log('list$', this.$store.state.currentDataList)
      this.$store.commit('changeDataList', this.projectId)
    },
  }
</script>

<style>
#problock{
    margin: 0 auto;
    width: 90%;
}
</style>