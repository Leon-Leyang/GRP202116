<template>
<div style="background:rgb(218, 253, 249);height:100%">
  <v-container style="height:100%">
   <div style="margin: 10px 10px 12px 14px">
<v-row style="height:10%">
  <v-btn
      fab
      small
      icon
    >
    <v-icon dark>
      mdi-delete
    </v-icon>
    </v-btn>
  <v-spacer>
  </v-spacer>
  <v-btn
      color="amber"
      @click="xx()"
      depressed
      rounded
      class="white--text"
  >  
      Import<v-icon
        right
        dark
      > mdi-database-import
      </v-icon>
  </v-btn>
  <v-btn
      style="margin-left:20px; margin-right:10px"
      color="blue-grey"
      @click="xx()"
      rounded
      depressed
      class="white--text"
      
  >
      Export<v-icon
        right
        dark
      > mdi-cloud-upload
      </v-icon>
  </v-btn>
</v-row>
    </div>
<el-table
  :data="tableData"
  style="width: 100%; height:85%"
  :default-sort = "{prop: 'id', order: 'ascending'}" 
  v-cloak
  stripe
  ><!--class="blue lighten-5"-->
  <el-table-column
    type="selection"
    >
  </el-table-column>


  <el-table-column show-overflow-tooltip sortable label="ID" prop="dataId" align="center">
  </el-table-column> 
  <el-table-column show-overflow-tooltip sortable label="Last Modification Time" prop="updateTime" align="center">
  </el-table-column> 
  <el-table-column show-overflow-tooltip sortable label="Import Time" prop="createTime" align="center">
  </el-table-column>  
  <el-table-column show-overflow-tooltip label="Preview" prop="url" align="center">
    <template slot-scope="scope">
                <el-popover placement="right">
                  <img :src="scope.row.url" />
                  <img
                    slot="reference"
                    :src="scope.row.url"
                    :alt="scope.row.url"
                    style="max-height: 50px; max-width: 130px"
                  />
                </el-popover>
      </template>
  </el-table-column> 
  <el-table-column show-overflow-tooltip label="Anno" prop="sta" align="center">
    <template slot-scope="tableData">
      <button @click="enterData(tableData.row)">Enter</button>
    </template>
  </el-table-column> 
  <!-- <el-table-column label="Annotation" width="100" prop="anno" align="center"></el-table-column>  -->
  <!-- <el-table-column label="Prediction" width="100" prop="predi" align="center"></el-table-column>        -->
</el-table>
<el-pagination class="pager" layout="prev, pager, next"  :total="10" :current-page.sync="config.page" @current-change="changePage" :page-size="20"></el-pagination>
</v-container>
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
      enterData(data){
        console.log('data', data)
        this.$store.state.currentDataId = data.dataId
        this.$store.state.realDataId = data.realDataId
        console.log('cur', this.$store.state.currentDataId)
        console.log('real', this.$store.state.realDataId)
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
        console.log('curraList', this.tableData)

        for(var i=0; i<this.tableData.length; i++){
          this.tableData[i].realDataId = this.tableData[i].dataId
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
          else if(this.tableData[i].url.indexOf(".webp")!=-1){
            this.tableData[i].url = this.tableData[i].url.replace(".webp", "")
            this.tableData[i].url = require("..//..//..//..//../GRP202116/" + this.tableData[i].url + '.webp' )
          }
          // else if(this.tableData[i].url.indexOf("txt")!=-1){
          //   this.tableData[i].url = this.tableData[i].url.replace(".txt", "")
          //   this.tableData[i].url = require("..//..//..//..//../GRP202116/" + this.tableData[i].url + '.txt' )
          // }  
          // else if(this.tableData[i].url.indexOf(".doc")!=-1){
          //   this.tableData[i].url = this.tableData[i].url.replace(".doc", "")
          //   this.tableData[i].url = require("..//..//..//..//../GRP202116/" + this.tableData[i].url + '.doc' )
          // }                  
          console.log('tabelda',this.tableData[i].url)
        }
        this.$store.state.currentDataList = this.tableData

        console.log('asd', this.$store.state.currentDataList)
      },1000);

    },  

  }
</script>

<style>
.el-table th.el-table__cell>.cell{
  padding-left: 14px!important;
}
</style>