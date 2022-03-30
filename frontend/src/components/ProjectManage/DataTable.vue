<template>
<div>
<el-table
  :data="tableData"
  style="width: 100%"
  :default-sort = "{prop: 'id', order: 'ascending'}" 
  v-cloak
  stripe
  >
  <el-table-column
    type="selection"
    width="50">
  </el-table-column>


  <el-table-column show-overflow-tooltip sortable label="ID" width="100" prop="dataId" align="center">
  </el-table-column> 
  <el-table-column show-overflow-tooltip sortable label="Last Modification Time" width="200" prop="updateTime" align="center">
  </el-table-column> 
  <el-table-column show-overflow-tooltip sortable label="Import Time" width="200" prop="createTime" align="center">
  </el-table-column>  
  <el-table-column show-overflow-tooltip label="Preview" width="200" prop="url" align="center">
    <template slot-scope="scope">
                <el-popover placement="right">
                  <img :src="scope.row.url" />
                  <img
                    slot="reference"
                    :src="scope.row.url"
                    :alt="scope.row.url"
                    style="height: 50px; width: 130px"
                  />
                </el-popover>
      </template>
  </el-table-column> 
  <el-table-column show-overflow-tooltip label="Anno" width="100" prop="sta" align="center">
    <template slot-scope="tableData">
      <button @click="enterData(tableData.row.dataId)">Enter</button>
    </template>
  </el-table-column> 
  <!-- <el-table-column label="Annotation" width="100" prop="anno" align="center"></el-table-column>  -->
  <!-- <el-table-column label="Prediction" width="100" prop="predi" align="center"></el-table-column>        -->
</el-table>
<el-pagination class="pager" layout="prev, pager, next"  :total="10" :current-page.sync="config.page" @current-change="changePage" :page-size="20"></el-pagination>

</div>
</template>

<script>
  export default {
    name: 'DataTable',
    data(){
      return{ 
        tableData:[],
      }
    },
    props: {
      config: Object,
    },
    methods: {
      enterData(dataId){
        console.log('dataId', dataId)
        this.$store.state.currentDataId = dataId
        console.log('cur', this.$store.state.currentDataId)
        console.log('culist', this.$store.state.currentDataList)
        console.log('lisy', this.$store.state.currentDataList[this.$store.state.currentDataId - 1].dataId)
        this.$router.push({  
                    path: '/data',
                    name: 'Data',  
                }) 
      }
    },
    mounted() {
      clearTimeout(this.timer); 
 
      this.timer = setTimeout(()=>{  
        console.log('currentDataList', this.$store.state.currentDataList)
        this.tableData = this.$store.state.currentDataList
        for(var i=0; i<this.tableData.length; i++){
          this.tableData[i].dataId = i + 1
          this.tableData[i].url = this.tableData[i].url.replaceAll("\\", "/")
          this.tableData[i].url = this.tableData[i].url.replace("../", "")

          if(this.tableData[i].url.indexOf(".jpg")!=-1){
            this.tableData[i].url = this.tableData[i].url.replace(".jpg", "")
            this.tableData[i].url = require("..//..//..//..//../GRP202116/" + this.tableData[i].url + '.jpg' )
          }
          else if(this.tableData[i].url.indexOf(".JPEG")!=-1){
            this.tableData[i].url = this.tableData[i].url.replace(".JPEG", "")
            this.tableData[i].url = require("..//..//..//..//../GRP202116/" + this.tableData[i].url + '.JPEG' )
          }
          else if(this.tableData[i].url.indexOf(".png")!=-1){
            this.tableData[i].url = this.tableData[i].url.replace(".png", "")
            this.tableData[i].url = require("..//..//..//..//../GRP202116/" + this.tableData[i].url + '.png' )
          }
          console.log('tabelda',this.tableData[i].url)
        }
        this.$store.state.currentDataList = this.tableData
        console.log('asd', this.$store.state.currentDataList)
      },500);

    },  

  }
</script>

<style>

</style>